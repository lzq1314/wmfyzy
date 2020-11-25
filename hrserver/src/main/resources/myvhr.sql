DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别 0代表男，1代表女',
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `phoneNumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `telPhoneNumber` varchar(255) DEFAULT NULL COMMENT '电话号',
  `idCardNumber` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `memberCardNumber` varchar(255) DEFAULT NULL COMMENT '会员卡号',
  `balance` decimal(9,2) DEFAULT 0 COMMENT '余额',
  `memberStatus` varchar(2) DEFAULT '0' COMMENT '用户状态 0正常、1销户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `menu` VALUES ('29', '/', '/home', 'Home', '会员中心', 'fa fa-user-circle-o', null, '1', '1', '1');
INSERT INTO `menu` VALUES ('30', '/member/basic/**', '/member/basic', 'MemberBasic', '会员基本信息', null, null, '1', '29', '1');
INSERT INTO `menu` VALUES ('31', '/shopping/basic/**', '/shopping/basic', 'ShoppingBasic', '会员购物车', null, null, '1', '29', '1');
INSERT INTO `menu` VALUES ('32', '/historyShopping/basic/**', '/historyShopping/basic', 'HistoryShoppingBasic', '消费记录', null, null, '1', '29', '1');
INSERT INTO `menu` VALUES ('33', '/rechargeHistory/basic/**', '/rechargeHistory/basic', 'RechargeHistoryBasic', '充值记录', null, null, '1', '29', '1');



INSERT INTO `menu_role` VALUES ('278', '29', '6');
INSERT INTO `menu_role` VALUES ('279', '30', '6');
INSERT INTO `menu_role` VALUES ('280', '31', '6');
INSERT INTO `menu_role` VALUES ('281', '32', '6');
INSERT INTO `menu_role` VALUES ('282', '33', '6');

DROP TABLE IF EXISTS `t_transactionRecords`;
CREATE TABLE `t_transactionRecords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_name` varchar(255) not NULL COMMENT '交易名称（充值/消费/退费）',
  `amount` decimal(9,2) not NULL COMMENT '金额',
  `transaction_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `phoneNumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `memberCardNumber` varchar(255) DEFAULT NULL COMMENT '会员卡号',
  `member_id` int(11) NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--订单表
DROP TABLE IF EXISTS `t_orders`;
CREATE TABLE `t_orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity` int(11) NOT NULL COMMENT '商品id',
  `member_id` int(11) NOT NULL COMMENT '会员id',
  `commodity_name` varchar(255) NOT NULL COMMENT '商品名称',
  `commodity_amount` decimal(9,2) not NULL COMMENT '商品金额',
  `commodity_count` int not NULL COMMENT '商品数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `phoneNumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `memberCardNumber` varchar(255) DEFAULT NULL COMMENT '会员卡号',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--商品表
DROP TABLE IF EXISTS `t_commodity`;
CREATE TABLE `t_commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(255) NOT NULL COMMENT '商品名称',
  `commodity_amount` decimal(9,2) not NULL COMMENT '商品金额',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `commodity_status` varchar(11) DEFAULT '0' COMMENT '0正常，1下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--订单表
DROP TABLE IF EXISTS `t_orders_history`;
CREATE TABLE `t_orders_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity` int(11) NOT NULL COMMENT '商品id',
  `member_id` int(11) NOT NULL COMMENT '会员id',
  `commodity_name` varchar(255) NOT NULL COMMENT '商品名称',
  `commodity_amount` decimal(9,2) not NULL COMMENT '商品金额',
  `commodity_count` int not NULL COMMENT '商品数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `phoneNumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `memberCardNumber` varchar(255) DEFAULT NULL COMMENT '会员卡号',
  `member_name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

