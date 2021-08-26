package indi.mat.work.project.controller.system;

import indi.mat.work.project.service.system.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    @GetMapping("{id}")
    public Object getById(@PathVariable Integer id) {
        return systemMenuService.getById(id);
    }

    @DeleteMapping
    public Object delete(Integer id) {
        systemMenuService.delete(id);
        return "删除成功！";
    }

    @RequestMapping("/get")
    public Object get(String name) {
        return systemMenuService.get(name);
    }


    @RequestMapping("/deleteByName")
    public Object delete(String name) {
        systemMenuService.deleteName(name);
        return "删除成功！";
    }


}
