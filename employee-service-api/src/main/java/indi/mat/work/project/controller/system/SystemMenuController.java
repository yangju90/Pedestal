package indi.mat.work.project.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.request.query.system.SystemMenuQuery;
import indi.mat.work.project.response.Response;
import indi.mat.work.project.service.system.ISystemMenuService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/menu")
public class SystemMenuController {
    @Autowired
    private ISystemMenuService systemMenuService;

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        return systemMenuService.getById(id);
    }

    @GetMapping("list")
    public List<SystemMenu> get() {
        return systemMenuService.selectAll();
    }

    @GetMapping("query")
    public Response<IPage<SystemMenu>> get(@ParameterObject SystemMenuQuery systemMenuQuery){
        IPage<SystemMenu> page =  systemMenuService.queryPage(systemMenuQuery);
        return Response.SUCCESS(page);
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
