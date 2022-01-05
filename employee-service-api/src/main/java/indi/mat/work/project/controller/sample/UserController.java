package indi.mat.work.project.controller.sample;

import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.model.user.LoginUser;
import indi.mat.work.project.service.sample.IUserService;
import indi.mat.work.project.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @RequestMapping("/get")
    public Object getById() {
                logger.info("处理湿哒哒无多无");
        logger.info("处理信息+ \" aaaa \" + bbbb + 'cccc'");
        logger.info("处理信息+ \" aaaa \" + bbbb + \n 'cccc'");
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setId(1L);
        systemMenu.setMenuCode("dadadada");
        logger.info("Object" + systemMenu);
        HashMap<Object, Object> mapL = new HashMap<>();
        mapL.put(systemMenu, systemMenu);
        logger.info("Map" + mapL);
        ArrayList list = new ArrayList();
        list.add(systemMenu);
        list.add(systemMenu);
        logger.info("List {}", list);
        logger.info("list {} {}", list , list);
        logger.info("JSONObject {}" , JsonUtil.toJsonString(systemMenu));
        try {
            if (true) {
                throw new RuntimeException("adadadwdwdwa");
            }
        }catch (Exception e){
            logger.error("{}", e);
        }
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

    @RequestMapping("/update/user")
    public LoginUser updateUser(@Validated @RequestBody LoginUser loginUser){
        return loginUser;
    }

}
