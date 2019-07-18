package fsc.site.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @Test
    public void getRolesPermission() {
        System.out.println(roleService.getRolesPermission());
    }
    @Test
    public void batchInsertPermission(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        //roleService.batchInsertPermission(8,list);
    }
}