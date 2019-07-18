package fsc.site.service;

import fsc.site.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void queryByUserName() {
        System.out.println(userService.queryByUserName("18711309775"));
    }
    @Test
    public void queryEmailByEmail(){
        System.out.println(userService.queryEmailByEmail("1049709821@qq.com"));
    }
    @Test
    public void addUser(){
        User user = new User("da","dasd","dasd","dasds",0);
        userService.addUser(user);
        System.out.println(user);
    }
    @Test
    public void getUserPermission(){
        System.out.println(userService.getUserPermission("root"));
    }
    @Test
    public void getUserPermissionList(){
        System.out.println(userService.getUserPermissionList("root"));
    }
    @Test
    public void getUsers(){
        System.out.println(userService.getUsers(1,8));
    }
}