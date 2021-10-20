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


insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (1, '01', 'account', 'root', 1, null, '/account', null, '/account', 1, '??', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (2, '02', 'company', 'root', 1, null, '/company', null, '/company', 2, '??', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (3, '03', 'orders', 'root', 1, '/pages/orders', null, null, '/orders', 3, '??', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (4, '04', 'positions', 'root', 1, '/positions', null, null, '/positions', 4, '??', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (5, '05', 'talent', 'root', 1, '/pages/talent', null, null, '/talent', 5, '???', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (6, '06', 'timeSheet', 'root', 1, '/pages/timeSheet', null, null, '/timeSheet', 6, '???', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (7, '07', 'payment', 'root', 1, '/pages/payment', null, null, '/payment', 7, '??', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (8, '0101', 'accountRole', '01', 1, './AccountRole', '/account/role', null, '/api/account/role', 1, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (9, '0102', 'accountUser', '01', 1, './AccountUser', '/account/user', null, '/api/account/user', 2, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (11, '0201', 'queryCompany', '02', 1, './QueryCompany', '/company/query', null, '/api/company/info,/api/company/contact,/api/company/activity/log,/api/company/financial,/api/company/business/trade,/api/system/favorites', 1, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (12, '0202', 'createCompany', '02', 1, './CreateCompany', '/company/create', null, '/api/company', 2, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (13, '0203', 'companyProfile', '02', 1, './CompanyProfile', '/company/profile', null, '/api/company', 3, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (14, '0401', 'queryPositions', '04', 1, './Positions', '/positions/query', null, '/api/position/cost/template', 1, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

insert into indi_system_menu (id, menu_code, menu_name, p_code, menu_status, href, menu_path, icon, url_prefix, sort, memo, deleted, in_user, in_date, last_edit_user, last_edit_date)
values (15, '0402', 'costTemplate', '04', 1, './CostTemplate', '/positions/cost/template', null, '/api/position/cost/template', 2, '????', 0, 'staffing@newegg.com', 1630735516884, 'staffing@newegg.com', 1630735516884);

