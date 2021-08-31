package indi.mat.work.project.controller.sample;


import indi.mat.work.project.controller.BaseTestController;
import indi.mat.work.project.model.sample.User;
import indi.mat.work.project.service.sample.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseTestController {

    private String PERFIX ="/api/system/menu";

    @Autowired
    private IUserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userService.get();
        assertEquals(5, userList.size());
        userList.forEach(x -> System.out.println(x.getEmail()));
    }

    @Test
    public void testGetUser() throws Exception {
        mvc.perform(get(PERFIX + "/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
