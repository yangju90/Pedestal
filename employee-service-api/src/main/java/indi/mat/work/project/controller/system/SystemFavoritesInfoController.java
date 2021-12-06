package indi.mat.work.project.controller.system;

import indi.mat.work.project.model.system.SystemFavoritesInfo;
import indi.mat.work.project.service.proxy.ServiceDelegate;
import indi.mat.work.project.service.system.ISystemFavoritesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/system/favorites/info")
public class SystemFavoritesInfoController {

    @Autowired
    ISystemFavoritesInfoService service;

    @Autowired
    ServiceDelegate serviceDelegate;

    @GetMapping("list")
    public List<SystemFavoritesInfo> list(){
        return service.selectAll();
    }


    // 简单路由转发，复杂的还得需要Spring Cloud ApiGateway
    @RequestMapping(value = "proxy", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity proxy(HttpServletRequest request, HttpServletResponse response){
        return serviceDelegate.redirect(request, response, "https://www.woah.group/", "/api/system/favorites/info/proxy");
    }

}
