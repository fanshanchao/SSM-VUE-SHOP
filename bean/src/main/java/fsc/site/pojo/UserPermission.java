package fsc.site.pojo;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/3/28 15:54
 * 这个类用来保存着这个用户的所有权限信息
 */
public class UserPermission {
    public UserPermission(){
    }
    //用户id
    private Integer userId;
    //用户昵称
    private String nickName;
    //角色id
    private Integer roleId;
    //角色名字
    private String roleName;
    //所拥有的菜单列表
    private List<String> menuList ;
    //拥有权限列表
    private List<String> permissionList;

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "UserPermission{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", menuList=" + menuList +
                ", permissionList=" + permissionList +
                '}';
    }
}
