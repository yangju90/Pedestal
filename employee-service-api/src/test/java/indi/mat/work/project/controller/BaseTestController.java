package indi.mat.work.project.controller;

import indi.mat.work.project.AppTest;
import indi.mat.work.project.model.system.SystemMenu;
import indi.mat.work.project.service.system.SystemMenuService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;


@SpringBootTest(classes = {AppTest.class})
@DirtiesContext
public abstract class BaseTestController {

    public MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SystemMenuService systemMenuService;

    @BeforeEach
    void setup() throws SQLException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        dataSource.getConnection().createStatement().execute("CREATE TABLE if not exists `user` (\n" +
                "      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "      `name` varchar(30) DEFAULT NULL COMMENT '姓名',\n" +
                "      `age` int DEFAULT NULL COMMENT '年龄',\n" +
                "      `email` varchar(50) DEFAULT NULL COMMENT '邮箱',\n" +
                "      `favorite` tinyint DEFAULT NULL,\n" +
                "      PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';\n" +
                "\n" +
                "create table if not exists `indi_system_menu` (\n" +
                "      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',\n" +
                "\n" +
                "      `menu_code` varchar(50) NOT NULL COMMENT '业务主键',\n" +
                "      `name` varchar(50) NOT NULL COMMENT '菜单名称',\n" +
                "      `p_code` varchar(50) NOT NULL COMMENT '父级菜单编码',\n" +
                "      `menu_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 启动  0 停用   默认1',\n" +
                "      `href` varchar(200) DEFAULT NULL COMMENT '模块相对路径',\n" +
                "      `path` varchar(200) DEFAULT NULL COMMENT 'url 前端展示跳转链接',\n" +
                "      `icon` varchar(200) DEFAULT NULL COMMENT '图标',\n" +
                "      `url_prefix` varchar(100) NULL COMMENT '模块的请求前缀',\n" +
                "      `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序值  默认0',\n" +
                "      `content` varchar(500) DEFAULT NULL COMMENT '备注',\n" +
                "\n" +
                "      `deleted` tinyint(1) NOT NULL DEFAULT 0,\n" +
                "      `op_user` varchar(200) NOT NULL COMMENT '插入用户',\n" +
                "      `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',\n" +
                "      `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',\n" +
                "      `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',\n" +
                "\n" +
                "      PRIMARY KEY (`id`),\n" +
                "      INDEX `ix_menu_menu_code` (`menu_code`),\n" +
                "      INDEX `ix_menu_menu_url_prefix` (`url_prefix`)\n" +
                ") ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='菜单表';");

        setupSystemMenu();
    }

    private void setupSystemMenu(){
        systemMenuService.insert(new SystemMenu("01", "账户", "root", true, "/account", "./account", "small", "/account", 1, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("02", "公司", "root", true, "/company", "./company", "small", "/company", 2, "公司以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("03", "工作", "root", true, "/account", "./account", "small", "/account", 3, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("04", "支付", "root", true, "/account", "./account", "small", "/account", 4, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("05", "其他1", "root", true, "/account", "./account", "small", "/account", 5, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("06", "其他2", "root", true, "/account", "./account", "small", "/account", 6, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("07", "其他3", "root", true, "/account", "./account", "small", "/account", 7, "账户以及目录", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("0101", "账户用户", "01", true, "/accountUser", "./accountUser", "small", "/accountUser", 1, "账户用户", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("0102", "账户角色", "01", true, "/accountRole", "./accountRole", "small", "/accountRole", 2, "账户角色", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("0201", "查询公司", "02", true, "/companyQuery", "./companyQuery", "small", "/companyQuery", 1, "公司查询", false, "admin", new Date()));
        systemMenuService.insert(new SystemMenu("0202", "创建公司", "02", true, "/companyCreate", "./companyCreate", "small", "/companyCreate", 2, "公司创建", false, "admin", new Date()));
    }

}
