package indi.mat.work.project.controller.system;

import indi.mat.work.project.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    @RequestMapping("/getById")
    public Object getById(Integer id) {
        return systemMenuService.getById(id);
    }

    @RequestMapping("/delete")
    public Object delete(Integer id) {
        systemMenuService.delete(id);
        return "删除成功！";
    }

    @RequestMapping("/deleteByName")
    public Object delete(String name) {
        systemMenuService.deleteName(name);
        return "删除成功！";
    }


    @RequestMapping("/get")
    public Object get(String name) {
        return systemMenuService.get(name);
    }

}
