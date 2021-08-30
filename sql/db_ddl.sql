-- create database if not exists employee default charset utf8mb4 collate utf8mb4_general_ci;
-- use employee;

create table if not exists `user`(
    `id` BIGINT(20) NOT NULL COMMENT '主键ID',
    `name` VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    `age` INT(11) NULL DEFAULT NULL COMMENT '年龄',
    `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    `favorite` tinyint DEFAULT NULL,
    PRIMARY KEY (`id`)
);

create table if not exists `indi_account_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',

    `name` varchar(200) NOT NULL COMMENT '角色名称',
    `description` varchar(500) NULL COMMENT '角色描述',
    `role_type` varchar(20) NOT NULL COMMENT 'client 客户账号；内部人员账号；求职人员登录账号',
    `role_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 启动  0 停用  默认1',

    `deleted` tinyint(1) NOT NULL DEFAULT 0,
    `op_user` varchar(200) NOT NULL COMMENT '插入用户',
    `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',
    `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',

    PRIMARY KEY (`id`),
    INDEX `ix_account_role_name` (`name`)
) ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='角色表';



create table if not exists `indi_account_role_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',

    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
    `function_code` varchar(50) NOT NULL COMMENT '操作类型编码none/view/edit',

    `deleted` tinyint(1) NOT NULL DEFAULT 0,
    `op_user` varchar(200) NOT NULL COMMENT '插入用户',
    `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',
    `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',

    PRIMARY KEY (`id`),
    INDEX `ix_account_role_menu_role_id` (`role_id`,`menu_code`)
) ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='菜单权限表';



create table if not exists `indi_account_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',

    `customer_number` varchar(200) NOT NULL COMMENT '外部主键ID，注册产生',
    `email` varchar(45) NOT NULL COMMENT '用户邮箱 用户登录验证',
    `account_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1 启动  0 停用   默认1',
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
    `name` varchar(50) DEFAULT NULL COMMENT '姓名',
    `phone` varchar(50) DEFAULT NULL COMMENT '电话',
    `icon` varchar(200) DEFAULT NULL COMMENT '头像图标地址',
    `address1` varchar(200) DEFAULT NULL COMMENT '地址1',
    `address2` varchar(200) DEFAULT NULL COMMENT '地址2',
    `country` varchar(50) DEFAULT NULL COMMENT '所属国家',
    `state` varchar(50) DEFAULT NULL COMMENT '州',
    `city` varchar(50) DEFAULT NULL COMMENT '城市',
    `zipcode` varchar(20) DEFAULT NULL COMMENT '邮编',
    `content` varchar(500) DEFAULT NULL COMMENT '备注',

    `deleted` tinyint(1) NOT NULL DEFAULT 0,
    `op_user` varchar(200) NOT NULL COMMENT '插入用户',
    `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',
    `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',

    PRIMARY KEY (`id`),
    INDEX `ix_account_user_customer_number` (`customer_number`),
    INDEX `ix_account_user_email` (`email`)
) ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='账号表';



create table if not exists `indi_system_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',

    `menu_code` varchar(50) NOT NULL COMMENT '业务主键',
    `name` varchar(50) NOT NULL COMMENT '菜单名称',
    `p_code` varchar(50) NOT NULL COMMENT '父级菜单编码',
    `menu_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 启动  0 停用   默认1',
    `href` varchar(200) DEFAULT NULL COMMENT '模块相对路径',
    `path` varchar(200) DEFAULT NULL COMMENT 'url 前端展示跳转链接',
    `icon` varchar(200) DEFAULT NULL COMMENT '图标',
    `url_prefix` varchar(100) NULL COMMENT '模块的请求前缀',
    `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序值  默认0',
    `content` varchar(500) DEFAULT NULL COMMENT '备注',

    `deleted` tinyint(1) NOT NULL DEFAULT 0,
    `op_user` varchar(200) NOT NULL COMMENT '插入用户',
    `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',
    `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',

    PRIMARY KEY (`id`),
    INDEX `ix_menu_menu_code` (`menu_code`),
    INDEX `ix_menu_menu_url_prefix` (`url_prefix`)
) ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='菜单表';

create table if not exists `indi_system_favorites_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键',

    `account_id` bigint(20) NOT NULL COMMENT '用户账户id',
    `business_favorites_type` varchar(50) DEFAULT NULL COMMENT '收藏的业务类型',
    `favorite_id` bigint(20) NOT NULL COMMENT '收藏的业务id',
    `score` int NOT NULL COMMENT '评分',

    `deleted` tinyint(1) NOT NULL DEFAULT 0,
    `op_user` varchar(200) NOT NULL COMMENT '插入用户',
    `op_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
    `last_op_user` varchar(200) DEFAULT NULL COMMENT '最后更新用户',
    `last_op_date` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',

    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT charset=utf8mb4 COMMENT='菜单表';
