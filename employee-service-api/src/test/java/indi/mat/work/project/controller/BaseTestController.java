package indi.mat.work.project.controller;

import indi.mat.work.project.AppTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest(classes = {AppTest.class})
@DirtiesContext
public abstract class BaseTestController {

    public MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

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
    }

}
