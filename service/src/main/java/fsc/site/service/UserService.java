package fsc.site.service;

import com.github.pagehelper.PageHelper;
import fsc.site.dao.UserMapperDao;
import fsc.site.pojo.User;
import fsc.site.pojo.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapperDao userMapperDao;

    /**
     * 根据用户名获取用户的用户名和密码
     * @param name
     * @return
     */
    public User queryByUserName(String name){
        return userMapperDao.queryByUserName(name);
    }

    /**
     * 根据用户名返回用户的id
     * @param name
     * @return
     */
    public User queryIdByUserName(String name){
        return userMapperDao.queryIdByUserName(name);
    }

    /**
     * 查看数据库当前邮箱是否已经注册
     * @param email
     * @return
     */
    public User queryEmailByEmail(String email){
        return userMapperDao.queryEmailByEmail(email);
    }

    /**
     * 查看当前会员名是否已经被使用
     * @param userName
     * @return
     */
    public User queryEmailByUserName(String userName){
        return userMapperDao.queryEmailByUserName(userName);
    }

    /**
     * 添加一个用户到数据库 并且还要添加用户的权限信息 利用事务进行操作
     * @param user
     */
    @Transactional
    public int addUser(User user){
        userMapperDao.addUser(user);
        Integer userId = user.getUserId();
        Integer roleId = 3;
        //默认角色是会员
        userMapperDao.addUserRole(roleId,userId);
        return user.getUserId();
    }

    /**
     * 根据用户名查找用户的权限信息
     * @param userName
     * @return
     */
    public UserPermission getUserPermission(String userName){
        return userMapperDao.getUserPermission(userName);
    }

    /**
     * 根据用户的用户名查询用户所拥有的权限信息
     * @param userName
     * @return
     */
    public List<String> getUserPermissionList(String userName){
        return userMapperDao.getUserPermissionList(userName);
    }

    /**
     * 返回用户的所有信息 并且利用分页插件
     * 第一个参数是页数 第二个参数是当前页的数据条数
     * @return
     */
    public List<User> getUsers(int pageNum,int pageSize){
        //先用分页插件只查询第一页的第一个
        PageHelper.startPage(pageNum,pageSize);
        return userMapperDao.getUsers();
    }

    /**
     * 返回整个商城用户的数量
     * @return
     */
    public Integer getUserCount(){
        return userMapperDao.getUserCount();
    }

    /**
     * 更新一个用户
     * @param user
     * @return
     */
    public Integer updateUser(User user){
        return userMapperDao.updateUser(user);
    }

    /**
     * 锁住一个用户
     * @param userId
     * @return
     */
    public Integer lockUser(Integer userId){
        return userMapperDao.lockUser(userId);
    }

    /**
     * 解锁一个用户
     * @param userId
     * @return
     */
    public Integer unLockUser(Integer userId){
        return userMapperDao.unlockUser(userId);
    }
}
