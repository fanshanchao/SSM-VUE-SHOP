package fsc.site.service;

import fsc.site.dao.RoleMapperDao;
import fsc.site.pojo.Role;
import fsc.site.pojo.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/3 20:46
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapperDao roleMapperDao;

    /**
     * 返回所有角色的权限信息
     * @return
     */
    public List<RolePermission> getRolesPermission(){
        return roleMapperDao.getRolesPermission();
    }

    /**
     * 更新某个用户的角色
     * @param userId
     * @param roleId
     * @return
     */
    public Integer updateRole(Integer userId,Integer roleId){
        return roleMapperDao.updateRole(userId,roleId);
    }

    /**
     * 根据角色名查询角色信息
     * @param roleName
     * @return
     */
    public Role selectRoleId(String roleName){
        return roleMapperDao.selectRoleId(roleName);
    }

    /**
     * 更新一个角色的权限信息  开启事务进行操作
     * @param roleName
     * @param roleId
     * @param permissionList
     * @return
     */
    @Transactional
    public void updateRolePermission(String roleName,Integer roleId,List<Integer> permissionList){
        //先更新这个角色的名称
        roleMapperDao.updateRoleName(roleName,roleId);
        //删除这个角色的所有信息
        roleMapperDao.deleteRolePermission(roleId);
        //重新添加这个角色的所有权限信息
        roleMapperDao.batchInsertPermission(roleId,permissionList);
    }

    /**
     * 创建一个角色 并且赋予权限 使用事务
     * @param role
     * @return
     */
    @Transactional
    public Integer addRole(Role role,List<Integer> permissionList){
        roleMapperDao.addRole(role);
        //添加这个用户的权限
        roleMapperDao.batchInsertPermission(role.getRoleId(),permissionList);
        return  role.getRoleId();
    }
}
