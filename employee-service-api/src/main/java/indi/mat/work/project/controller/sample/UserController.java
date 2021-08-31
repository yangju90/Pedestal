package indi.mat.work.project.controller.sample;

import indi.mat.work.project.service.sample.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;


    @RequestMapping("/get")
    public Object getById() {
        return userService.get();
    }

    @RequestMapping("/getById")
    public Object getById(Integer id) {
        return userService.getById(id);
    }

    @RequestMapping("/delete")
    public Object delete(Integer id) {
        userService.delete(id);
        return "删除成功！";
    }

}