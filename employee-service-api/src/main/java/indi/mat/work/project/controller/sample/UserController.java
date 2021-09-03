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
        logger.info("pMap" +map);
        ArrayList list = new ArrayList();
        list.add(systemMenu);
        list.add(systemMenu);
        logger.info("List {}", list);
        logger.info("list {} {}", list , list);
        logger.info("JSONObject {}" , JsonUtil.toJsonString(systemMenu));

        if(true) {
            throw new RuntimeException("adadadwdwdwa");
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

}
