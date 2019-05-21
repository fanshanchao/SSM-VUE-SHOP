package fsc.site.dao;

import fsc.site.pojo.Role;
import fsc.site.pojo.RolePermission;
import fsc.site.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/3 20:30
 * 角色权限的dao
 */
@Repository("roleMapperDao")
public interface RoleMapperDao {
    //获取所有角色的权限信息
    List<RolePermission> getRolesPermission();
    //修改一个用户的角色
    Integer updateRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
    //修改一个角色的名字
    Integer updateRoleName(@Param("roleName") String roleName, @Param("roleId") Integer roleId);
    //删除这个角色的所有权限
    Integer deleteRolePermission(Integer roleId);
    //根据角色名查询一个角色的id 用户进行更新和插入操作保证用户名的唯一性
    Role selectRoleId(String roleName);
    //批量插入一个角色的权限
    Integer batchInsertPermission(@Param("roleId")Integer roleId,@Param("permissionList")List<Integer> permissionList);
    //添加一个角色
    Integer addRole(Role role);
}
