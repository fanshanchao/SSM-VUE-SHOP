package fsc.site.pojo;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/3 20:33
 * 封装了一个角色的所有权限信息
 */
public class RolePermission {
    private Integer roleId;
    private String roleName;
    private List<String> menuList;
    private List<String> permissionList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", menuList=" + menuList +
                ", permissionList=" + permissionList +
                '}';
    }
}
