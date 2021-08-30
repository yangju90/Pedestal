-- json 转化网站 https://json2csharp.com/json-to-pojo
-- https://www.cnblogs.com/klvchen/p/10137117.html 执行优化
-- limit 优化  https://www.cnblogs.com/weixiaotao/p/10646666.html
--             https://blog.csdn.net/qq_36276335/article/details/73824243

INSERT INTO user (id, name, age, email) VALUES (1, 'Jone', 18, 'aaaatest1@baomidou.com');
INSERT INTO user (id, name, age, email) VALUES (2, 'Jack', 20, 'aaaatest2@baomidou.com');
INSERT INTO user (id, name, age, email) VALUES (3, 'Tom', 28, 'aaaatest3@baomidou.com');
INSERT INTO user (id, name, age, email) VALUES (4, 'Sandy', 21, 'aaaatest4@baomidou.com');
INSERT INTO user (id, name, age, email) VALUES (5, 'Billie', 24, 'aaaatest5@baomidou.com');

------------ truncate table -------------
truncate table `indi_system_menu`;
INSERT INTO `indi_system_menu` VALUES (1, '01', '账户', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (2, '02', '公司', 'root', 1, '/company', './company', 'small', '/company', 1, '公司以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (3, '03', '工作', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (4, '04', '支付', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (5, '05', '其他1', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (6, '06', '其他2', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (7, '07', '其他3', 'root', 1, '/account', './account', 'small', '/account', 1, '账户以及目录', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (8, '0101', '账户用户', '01', 1, '/accountUser', './accountUser', 'small', '/accountUser', 1, '账户用户', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (9, '0102', '账户角色', '01', 1, '/accountRole', './accountRole', 'small', '/accountRole', 1, '账户角色', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (10, '0201', '查询公司', '02', 1, '/companyQuery', './companyQuery', 'small', '/companyQuery', 1, '公司查询', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
INSERT INTO `indi_system_menu` VALUES (11, '0202', '创建公司', '02', 1, '/companyCreate', './companyCreate', 'small', '/companyCreate', 1, '公司创建', 0, 'admin', '2021-8-30 19:08:35', '', NULL);
