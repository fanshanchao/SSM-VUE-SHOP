package fsc.site.controller;

import com.alibaba.fastjson.JSONObject;
import fsc.site.conf.SpringConfig;
import fsc.site.pojo.*;
import fsc.site.service.UserService;
import fsc.site.util.SendEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import fsc.site.util.RandomNumberUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    //注入发送邮件的几个bean
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private freemarker.template.Configuration emailConfiguration;

    //用于记录日志
    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
    /**
     * 这个用来处理用户登陆
     * @param user
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Response userLogin(@RequestBody JSONObject user){
        //获取当前主体
        Subject subject = SecurityUtils.getSubject();
        //根据当前提交的用户来生成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getString("userName"),user.getString("userPassword"));
        try {
            if(!subject.isAuthenticated()){
                //进行登陆认证
                subject.login(token);
                //如果用户勾选了记住我
                if(user.getBoolean("rememberMe")){
                    token.setRememberMe(true);
                }
            }else{
                //如果已经认证 返回一个已认证的信息给客户端
                return ResponseGenerator.getFailureReponse(ResponseCodeEnum.YET_LOGIN,"已经登陆，不可重复登陆");
            }
        }catch (IncorrectCredentialsException e){
            //密码错误
            return ResponseGenerator.getFailureReponse(ResponseCodeEnum.AUTH_ERROR,"密码错误");
        }catch (LockedAccountException e){
            return ResponseGenerator.getFailureReponse(ResponseCodeEnum.AUTH_ERROR,"用户被冻结");
        } catch (AuthenticationException e){
            //用户不存在
            return ResponseGenerator.getFailureReponse(ResponseCodeEnum.AUTH_ERROR,"用户不存在");
        }
        logger.info(user.getString("userName")+"登入");
        //查询到当前用户的用户角色信息 以及权限信息 返回给前端 还是只返回token比较好
        //UserPermission user1 = userService.getUserPermission(user.getString("userName"));
        //注意这一步很重要 成功登陆后 将session的id作为token返回给前端
        return ResponseGenerator.getSuccessReponse(subject.getSession().getId());
    }

    /**
     * 这个接口用来判断用户是否已经登陆过期
     * @return
     */
    @RequestMapping(value = "refresh",method =RequestMethod.GET )
    public Response refresh(){
        //获取当前主体
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return ResponseGenerator.getSuccessReponse();
        }
        return ResponseGenerator.getFailureReponse(ResponseCodeEnum.TIMEOUT_LOGIN,"登陆已过期");
    }

    /**
     * 这个接口用来注解获取验证码
     * @return
     */
    @RequestMapping(value = "getCode",method = RequestMethod.POST)
    public Response sendCode(@RequestBody JSONObject object){
        String email = object.getString("email");
        //如果邮箱为空返回一个错误信息码
        if(email.equals("")||email==null){
            return ResponseGenerator.getFailureReponse("邮箱有误");
        }
        //如果邮箱已经被注册 返回错误信息
        if(userService.queryEmailByEmail(email)!=null){
            return ResponseGenerator.getFailureReponse("当前邮箱已被注册");
        }
        //随机产生一个5位数
        int randomNumber = RandomNumberUtil.getFourNumber();
        //查看redis中是否存有重复的随机验证码 如果有就重新获取 以10次为上限 如果10次获取的还不是唯一验证码那么返回一个错误信息
        int i = 0;
        while(i<10&&redisTemplate.opsForValue().get(String.valueOf(randomNumber))!=null){
            randomNumber = RandomNumberUtil.getFourNumber();
            i++;
        }
        if(i==10){
            return ResponseGenerator.getFailureReponse("服务器繁忙，稍后再试");
        }
        //发送邮件到目的邮箱
        new SendEmail().sendCodeMailFreeMarker("商城注册验证码邮箱",randomNumber,email,javaMailSender,emailConfiguration);
        //向redis中存这个验证码 key为验证码 value为邮箱  并且这个验证码的存活时间为2分钟
        redisTemplate.opsForValue().set(String.valueOf(randomNumber),email,2L, TimeUnit.MINUTES);
        logger.info("存入一个验证码"+redisTemplate.opsForValue().get(String.valueOf(randomNumber)));
        //返回这个验证码 其实完全可以不返回这个验证码 因为验证码已经发送到邮件
        logger.info("生成一个验证码:"+randomNumber);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 这个控制器用来注册一个新用户
     * @return
     */
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public Response registerUser(@RequestBody JSONObject object){
        //获取email和用户名 查看是否已经注册
        String email = object.getString("email");
        String userName = object.getString("userName");
        //首先获取查看邮箱是否已经注册
        //如果邮箱已经被注册 返回错误信息
        if(userService.queryEmailByEmail(email)!=null){
            return ResponseGenerator.getFailureReponse("当前邮箱已被注册");
        }
        //查看用户名是否已经被使用
        if(userService.queryEmailByUserName(userName)!=null){
            return ResponseGenerator.getFailureReponse("当前用户名已被使用");
        }
        //查看验证码是否正确
        String verCode = object.getString("verCode");
        logger.info("redis中存的验证码是"+redisTemplate.opsForValue().get(verCode));
        //获取redis中的验证码对应的邮箱
        //如果验证码失效或者不正确
        if(redisTemplate.opsForValue().get(verCode)==null||!email.equals(redisTemplate.opsForValue().get(verCode))){
            return ResponseGenerator.getFailureReponse("验证码不正确或过期");
        }
        //获取密码
        String password = object.getString("userPassword");
        //将密码根据当前用户的id使用盐值加密
        String Md5Password = new SimpleHash("MD5",password, ByteSource.Util.bytes(userName),2).toString();
        //获取昵称
        String nickName = object.getString("nickName");
        //添加用户到用户信息
        userService.addUser(new User(userName,Md5Password,nickName,email,0));
        logger.info(userName+"注册成功");
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 这个处理器用来执行登出操作
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public Response userLogout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
                subject.logout();
            return ResponseGenerator.getSuccessReponse();
        }else{
            return ResponseGenerator.getFailureReponse(ResponseCodeEnum.NOT_LOGIN,"未登陆");
        }
    }

    /**
     * 根据用户名去获取用户的权限信息 注意这个获取的是当前已经登陆用户的权限 并不能指定用户名来获取
     * @return
     */
    @RequestMapping(value = "getPermission",method = RequestMethod.GET)
    public Response getPermission(){
        //如果当前用户没认证 那么返回错误信息
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return ResponseGenerator.getFailureReponse("未登陆");
        }
        User user = (User) subject.getPrincipal();
        //根据这个用户的用户名查询用户的权限信息
        UserPermission userPermission = userService.getUserPermission(user.getUserName());
        return ResponseGenerator.getSuccessReponseData(userPermission);
    }

    /**
     * 返回某个用户的权限信息 根据用户名查询出来的
     * @return
     */
    @RequestMapping(value = "usersPermissions/{userName}",method = RequestMethod.GET)
    @RequiresPermissions("user:update")
    public Response getUsersPermissions(@PathVariable("userName") String userName){
        List<String> result = userService.getUserPermissionList(userName);
        return ResponseGenerator.getSuccessReponseData(result);
    }

    /**
     * 这个用来返回用户的所有信息（包括用户角色名） 并且是利用分页查询的
     * @return
     */
    @RequestMapping(value = "users",method = RequestMethod.GET)
    @RequiresPermissions("user:list")
    public Response getUser(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        List<User> users = userService.getUsers(page,pageSize);
        return ResponseGenerator.getSuccessReponseData(users);
    }

    /**
     * 返回整个商城用户的所有信息
     * @return
     */
    @RequestMapping(value = "userCount",method = RequestMethod.GET)
    public Response userCount(){
        return ResponseGenerator.getSuccessReponseData(userService.getUserCount());
    }

    /**
     * 更新一个用户
     */
    @RequestMapping(value = "users/{userId}",method = RequestMethod.PUT)
    public Response updateUser(@RequestBody User user){
        userService.updateUser(user);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 冻结一个用户
     * @param
     * @return
     */
    @RequestMapping(value = "lockUser/{userId}",method = RequestMethod.GET)
    @RequiresPermissions("user:update")
    public Response lockUser(@PathVariable("userId") Integer userId){
        userService.lockUser(userId);
        return ResponseGenerator.getSuccessReponse();
    }
    /**
     * 解锁一个用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "unlockUser/{userId}",method = RequestMethod.GET)
    @RequiresPermissions("user:update")
    public Response unlockUser(@PathVariable("userId") Integer userId){
        userService.unLockUser(userId);
        return ResponseGenerator.getSuccessReponse();
    }
}


