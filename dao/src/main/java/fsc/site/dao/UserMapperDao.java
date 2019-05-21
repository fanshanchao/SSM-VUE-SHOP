package fsc.site.dao;

import fsc.site.pojo.User;
import fsc.site.pojo.UserPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapperDao")
public interface UserMapperDao {
    User queryByUserName(String userName);
    //通过用户名查用户的id 和昵称
    User queryIdByUserName(String name);
    //通过邮箱查看是否有此用户
    User queryEmailByEmail(String email);
    //通过会员名查看当前会员们是否已经被使用
    User queryEmailByUserName(String userName);
    //添加用户到数据库中
    Integer addUser(User user);
    //添加用户角色信息到数据库中去
    Integer addUserRole(@Param("roleId") Integer roleId,@Param("userId") Integer userId);
    //获取用户的权限信息 根据用户名
    UserPermission getUserPermission(String userName);
    //根据用户的用户名查询用户所拥有的权限信息
    List<String> getUserPermissionList(String userName);
    //查询出用户的所有信息
    List<User> getUsers();
    //获取用户的数量
    Integer getUserCount();
    //根据更新一个用户
    Integer updateUser(User user);
    //锁住一个用户
    Integer lockUser(Integer userId);
    //解锁一个用户
    Integer unlockUser(Integer userId);

}
