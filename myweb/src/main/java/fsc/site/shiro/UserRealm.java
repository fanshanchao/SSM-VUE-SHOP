package fsc.site.shiro;

import fsc.site.pojo.User;
import fsc.site.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/3/18 16:54
 *
 *  自定义relam以便于我们能够从数据库中获取数据去进行验证或授权
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 这个方法用来处理用户授权的
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户的用户名
        User user = (User) principalCollection.getPrimaryPrincipal();
        //从根据用户名获取这个用户所拥有的所有权限
        List<String> permissionList = userService.getUserPermissionList(user.getUserName());
        //返回授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        //返回授权信息
        return simpleAuthorizationInfo;
    }

    /**
     * 这个方法用来执行用户认证的
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户输入的账号
        String userName = (String) authenticationToken.getPrincipal();
        //从数据库中查询该用户名用户的信息
        User user = userService.queryByUserName(userName);
        if(user==null){
            //如果没查到当前用户 则抛出一个用户不存在异常 返回null会自动抛出异常
            return null;
        }
        //抛出一个用户被冻结异常
        if(user.getStatus()==1){
            throw new LockedAccountException();
        }
        //如果查询到了一个用户信息 就返回AuthenticationInfo shiro会自动帮你进行密码匹配过程 不匹配就会抛出异常  第三个参数是当前realm的方法
        //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,user.getUserPassword(),this.getName());
        //使用盐值加密过的密码  盐值类型是ByteSource类型
        ByteSource byteSource = ByteSource.Util.bytes(userName);//把盐进行加密 用一个唯一的值做盐
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getUserPassword(),byteSource,this.getName());
        return simpleAuthenticationInfo;
    }
    public static void main(String[] args) {
        //没有使用盐值后md5加密后的密码
        System.out.println(new SimpleHash("MD5","19990318",null,2));
        //使用盐值后加密的密码
        System.out.println(new SimpleHash("MD5","123456",ByteSource.Util.bytes("admin2"),2));
    }
}
