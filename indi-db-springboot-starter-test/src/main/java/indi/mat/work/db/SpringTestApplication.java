package indi.mat.work.db;

import indi.mat.work.db.dao.TestDao;
import indi.mat.work.db.service.TestService;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
        System.out.println("======>Application Started");
    }


    @Autowired
    TestService testService;

    @RequestMapping("testDao")
    public String testDao() {
        testService.test();
        return "success";
    }

    @RequestMapping("testDao1")
    public String testDao1() {
        testService.test1();
        return "success";
    }
}
