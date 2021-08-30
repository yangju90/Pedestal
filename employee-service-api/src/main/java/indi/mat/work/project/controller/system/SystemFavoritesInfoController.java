package indi.mat.work.project.controller.system;

import indi.mat.work.project.model.system.SystemFavoritesInfo;
import indi.mat.work.project.service.system.SystemFavoritesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/favorites/info")
public class SystemFavoritesInfoController {

    @Autowired
    SystemFavoritesInfoService service;

    @GetMapping("list")
    public List<SystemFavoritesInfo> list(){
        return service.selectAll();
    }
}
