package indi.mat.work.project.controller.system;

import indi.mat.work.project.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class SystemMenuController {
    @Autowired
    private SystemMenuService ISystemMenuService;

    @GetMapping("{id}")
    public Object getById(@PathVariable Integer id) {
        return ISystemMenuService.getById(id);
    }

    @DeleteMapping
    public Object delete(Integer id) {
        ISystemMenuService.delete(id);
        return "删除成功！";
    }

    @RequestMapping("/get")
    public Object get(String name) {
        return ISystemMenuService.get(name);
    }


    @RequestMapping("/deleteByName")
    public Object delete(String name) {
        ISystemMenuService.deleteName(name);
        return "删除成功！";
    }


}
