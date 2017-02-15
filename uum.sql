/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50544
Source Host           : 172.16.5.104:3306
Source Database       : uum

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-12-12 20:35:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for uum_account
-- ----------------------------
DROP TABLE IF EXISTS `uum_account`;
CREATE TABLE `uum_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(80) NOT NULL DEFAULT '' COMMENT '密码',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态0：正常，1：删除',
  `ctime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00',
  `mtime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='账号表';

-- ----------------------------
-- Records of uum_account
-- ----------------------------
INSERT INTO `uum_account` VALUES ('1', 'liangguangsheng', '', '0', '2016-01-01 00:00:00', '2016-12-02 16:51:04');

-- ----------------------------
-- Table structure for uum_account_role
-- ----------------------------
DROP TABLE IF EXISTS `uum_account_role`;
CREATE TABLE `uum_account_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '账号ID',
  `role_id` mediumint(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_role` (`uid`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色账号关联表';

-- ----------------------------
-- Records of uum_account_role
-- ----------------------------
INSERT INTO `uum_account_role` VALUES ('1', '1', '1');
INSERT INTO `uum_account_role` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for uum_menu
-- ----------------------------
DROP TABLE IF EXISTS `uum_menu`;
CREATE TABLE `uum_menu` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` mediumint(11) DEFAULT '0' COMMENT '父节点',
  `app_id` smallint(18) NOT NULL DEFAULT '0' COMMENT '应用名称',
  `name` varchar(18) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `tpl` varchar(80) NOT NULL DEFAULT '' COMMENT '模板地址',
  `icon` varchar(80) NOT NULL DEFAULT '' COMMENT '标签地址',
  `sort` smallint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `css` varchar(20) NOT NULL DEFAULT '' COMMENT 'css样式',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `ctime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00',
  `mtime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of uum_menu
-- ----------------------------
INSERT INTO `uum_menu` VALUES ('1', '0', '0', '账号管理', '', '', '0', '', '1', '2016-01-01 00:00:00', '2016-12-02 16:48:40');
INSERT INTO `uum_menu` VALUES ('2', '1', '0', '账号查询', 'http://big-mng.lgs.co/home/user_query.ftl', '', '0', '', '1', '2016-01-01 00:00:00', '2016-12-12 19:39:28');
INSERT INTO `uum_menu` VALUES ('3', '0', '0', '后台管理', '', '', '0', '', '1', '2016-01-01 00:00:00', '2016-12-02 16:51:13');
INSERT INTO `uum_menu` VALUES ('4', '3', '0', '用户管理', 'http://uum.lgs.com/account/account_mng.ftl', '', '0', '', '1', '2016-01-01 00:00:00', '2016-12-12 19:39:33');

-- ----------------------------
-- Table structure for uum_role
-- ----------------------------
DROP TABLE IF EXISTS `uum_role`;
CREATE TABLE `uum_role` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `parent_id` mediumint(9) NOT NULL DEFAULT '0' COMMENT '父节点id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `ctime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00',
  `mtime` timestamp NOT NULL DEFAULT '2016-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of uum_role
-- ----------------------------
INSERT INTO `uum_role` VALUES ('1', '账号管理员', '0', '0', '2016-01-01 00:00:00', '2016-12-02 16:51:28');
INSERT INTO `uum_role` VALUES ('2', '后台管理员', '0', '0', '2016-01-01 00:00:00', '2016-12-02 16:51:25');

-- ----------------------------
-- Table structure for uum_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `uum_role_menu`;
CREATE TABLE `uum_role_menu` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `menu_id` mediumint(11) NOT NULL DEFAULT '0' COMMENT '菜单ID',
  `role_id` mediumint(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_menu_role` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of uum_role_menu
-- ----------------------------
INSERT INTO `uum_role_menu` VALUES ('1', '1', '1');
INSERT INTO `uum_role_menu` VALUES ('2', '2', '1');
INSERT INTO `uum_role_menu` VALUES ('3', '3', '2');
INSERT INTO `uum_role_menu` VALUES ('4', '4', '2');
