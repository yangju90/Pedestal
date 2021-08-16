package indi.mat.work.project.controller.sample;


import indi.mat.work.project.AppTest;
import indi.mat.work.project.dao.sample.UserMapper;
import indi.mat.work.project.model.sample.User;
import indi.mat.work.project.service.sample.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AppTest.class})
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userService.get();
        assertEquals(5, userList.size());
        userList.forEach(x -> System.out.println(x.getEmail()));
    }
}
