package indi.mat.work.project.controller.system;

import indi.mat.work.project.model.system.SystemFavoritesInfo;
import indi.mat.work.project.service.system.ISystemFavoritesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/system/favorites/info")
public class SystemFavoritesInfoController {

    @Autowired
    ISystemFavoritesInfoService service;

    @GetMapping("list")
    public List<SystemFavoritesInfo> list(){
        return service.selectAll();
    }
}
