package fsc.site.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fsc.site.pojo.Response;
import fsc.site.pojo.ResponseGenerator;
import fsc.site.pojo.Role;
import fsc.site.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/3 20:53
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     * 返回所有角色的权限信息
     * @return
     */
    @RequestMapping(value = "rolePermissions",method = RequestMethod.GET)
    @RequiresPermissions("user:list")
    public Response rolePermission(){
        return ResponseGenerator.getSuccessReponseData(roleService.getRolesPermission());
    }

    /**
     * 更新某个角色的权限信息
     * @param roleId
     * @param object
     * @return
     */
    @RequestMapping(value = "rolePermissions/{roleId}",method = RequestMethod.PUT)
    @RequiresPermissions("role:update")
    public Response updateRolePermission(@PathVariable("roleId")Integer roleId,@RequestBody JSONObject object){
        //根据这个角色名查询角色信息
        String roleName = object.getString("roleName");
        Role role = roleService.selectRoleId(roleName);
        //判断获取的角色id和当前需要修改的角色id是否相等 如果不相等代表当前角色名已被使用 返回错误信息
        if(role!=null&&role.getRoleId()!=roleId){
            return ResponseGenerator.getFailureReponse("当前角色名已被使用");
        }
        //进行更新操作
        //获取permissionId的数组
        JSONArray array = object.getJSONArray("permissionList");
        Integer[] objects = array.toArray(new Integer[array.size()]);
        //用一个list来保存这个数组
        List<Integer> permissionList = Arrays.asList(objects);
        //进行更新操作
        roleService.updateRolePermission(roleName,roleId,permissionList);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 创建一个角色 并且分配它的权限
     * @return
     */
    @RequestMapping(value = "rolePermissions",method = RequestMethod.POST)
    @RequiresPermissions("role:update")
    public Response addRolePermission(@RequestBody JSONObject object){
        //获取角色名 查看当前角色名是否已被注册
        String roleName = object.getString("roleName");
        Role role = roleService.selectRoleId(roleName);
        if(role!=null){
            //返回错误信息告知当前角色名已被使用
            return ResponseGenerator.getFailureReponse("当前角色名已被使用");
        }
        //先添加一个角色 并且赋予权限 这里使用了事务
        role = new Role(roleName);
        //获取permissionId的数组
        JSONArray array = object.getJSONArray("permissionList");
        Integer[] objects = array.toArray(new Integer[array.size()]);
        //用一个list来保存这个数组
        List<Integer> permissionList = Arrays.asList(objects);
        roleService.addRole(role,permissionList);
        return ResponseGenerator.getSuccessReponse();
    }
    /**
     * 用来更新某个用户的角色
     * @param userId
     * @param object
     * @return
     */
    @RequestMapping(value = "roles/{userId}",method = RequestMethod.PUT)
    @RequiresPermissions("user:update")
    public Response roles(@PathVariable("userId")Integer userId, @RequestBody JSONObject object){
        //获取角色id
        Integer roleId = object.getInteger("roleId");
        //进行更新操作
        roleService.updateRole(userId,roleId);
        return ResponseGenerator.getSuccessReponse();
    }
}
