/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-09-20 14:36:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT 0 COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT 0 COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT 1 COMMENT '是否删除  0：已删除  1：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('6', '0', '研发部', '1', '1');
INSERT INTO `sys_dept` VALUES ('9', '0', '市场部', '0', '1');
INSERT INTO `sys_dept` VALUES ('11', '0', '运营部', '5', '1');
INSERT INTO `sys_dept` VALUES ('16', '0', '产品部', '4', '1');
INSERT INTO `sys_dept` VALUES ('18', '0', '测试部', '5', '1');
INSERT INTO `sys_dept` VALUES ('20', '18', '测试1部', '1', '1');
INSERT INTO `sys_dept` VALUES ('21', '20', '测试2部', '1', '1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT 0 COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `is_show` tinyint(1) DEFAULT 1 COMMENT '是否展示',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('3', '0', '系统管理', '', 'sys:admin', '0', 'layui-icon layui-icon-set-sm', '1', '2017-08-09 23:06:55', '1');
INSERT INTO `sys_menu` VALUES ('57', '91', '运行监控', '/druid/index.html', 'sys:run:monitor', '1', 'layui-icon layui-icon-vercode', '1', '2019-08-20 14:59:43', '1');
INSERT INTO `sys_menu` VALUES ('77', '0', '系统工具', '', 'sys:tool', '0', 'layui-icon layui-icon-util', '3', '2019-08-19 14:59:46', '1');
INSERT INTO `sys_menu` VALUES ('91', '0', '系统监控', '', 'sys:monitor', '0', 'layui-icon layui-icon-video', '2', '2019-08-22 14:59:48', '1');
INSERT INTO `sys_menu` VALUES ('171', '0', 'Echarts图表', '', 'echarts:stat', '0', 'layui-icon layui-icon-chart', '4', '2019-08-21 14:59:51', '1');
INSERT INTO `sys_menu` VALUES ('172', '171', '3D地球', '/echarts/world/world.html', 'echarts:3d:world', '1', 'layui-icon layui-icon-website', '1', '2019-09-18 14:59:54', '1');
INSERT INTO `sys_menu` VALUES ('173', '0', '超级管理', '', 'super:admin', '0', 'layui-icon layui-icon-fire', '0', '2019-08-21 14:59:57', '0');
INSERT INTO `sys_menu` VALUES ('174', '173', '极速菜单', '/sys/menu/admin', 'admin', '1', 'layui-icon layui-icon-next', '1', '2019-05-29 14:30:27', '1');
INSERT INTO `sys_menu` VALUES ('175', '91', '系统日志', '/sys/log', 'sys:log:list', '1', 'layui-icon layui-icon-date', '0', '2019-05-30 10:45:23', '1');
INSERT INTO `sys_menu` VALUES ('176', '175', '删除', '/sys/log/remove', 'sys:log:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:46:47', '1');
INSERT INTO `sys_menu` VALUES ('177', '175', '批量删除', '/sys/log/batchRemove', 'sys:log:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:46:47', '1');
INSERT INTO `sys_menu` VALUES ('178', '91', '在线用户', '/sys/online', 'sys:online:list', '1', 'layui-icon layui-icon-user', '0', '2019-05-30 10:47:49', '1');
INSERT INTO `sys_menu` VALUES ('179', '178', '强制下线', '/sys/online/forceLogout/{sessionId}', 'sys:online:forceLogout', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:47:52', '1');
INSERT INTO `sys_menu` VALUES ('184', '77', '计划任务', '/sys/task', 'sys:task:list', '1', 'layui-icon layui-icon-template-1', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('185', '184', '增加', '/sys/task/add', 'sys:task:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('186', '184', '删除', '/sys/task/remove', 'sys:task:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('187', '184', '批量删除', '/sys/task/batchRemove', 'sys:task:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('188', '184', '编辑', '/sys/task/edit/{id}', 'sys:task:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('189', '184', '改变任务状态', '/sys/task/changeJobStatus', 'sys:task:changeJobStatus', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:51:04', '1');
INSERT INTO `sys_menu` VALUES ('190', '3', '部门管理', '/sys/dept', 'sys:dept:list', '1', 'layui-icon layui-icon-group', '0', '2019-05-30 10:52:39', '1');
INSERT INTO `sys_menu` VALUES ('191', '190', '增加', '/sys/dept/add/{pId}', 'sys:dept:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:52:39', '1');
INSERT INTO `sys_menu` VALUES ('192', '190', '删除', '/sys/dept/remove', 'sys:dept:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:52:39', '1');
INSERT INTO `sys_menu` VALUES ('193', '190', '批量删除', '/sys/dept/batchRemove', 'sys:dept:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:52:39', '1');
INSERT INTO `sys_menu` VALUES ('194', '190', '编辑', '/sys/dept/edit/{deptId}', 'sys:dept:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:52:39', '1');
INSERT INTO `sys_menu` VALUES ('196', '3', '系统菜单', '/sys/menu', 'sys:menu:list', '1', 'layui-icon layui-icon-menu-fill', '0', '2019-05-30 10:54:07', '1');
INSERT INTO `sys_menu` VALUES ('197', '196', '增加', '/sys/menu/add/{pId}', 'sys:menu:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:07', '1');
INSERT INTO `sys_menu` VALUES ('198', '196', '删除', '/sys/menu/remove', 'sys:menu:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:07', '1');
INSERT INTO `sys_menu` VALUES ('199', '196', '编辑', '/sys/menu/edit/{id}', 'sys:menu:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:07', '1');
INSERT INTO `sys_menu` VALUES ('200', '3', '角色管理', '/sys/role', 'sys:role:list', '1', 'layui-icon layui-icon-friends', '0', '2019-05-30 10:54:12', '1');
INSERT INTO `sys_menu` VALUES ('201', '200', '增加', '/sys/role/add', 'sys:role:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:12', '1');
INSERT INTO `sys_menu` VALUES ('202', '200', '删除', '/sys/role/remove', 'sys:role:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:12', '1');
INSERT INTO `sys_menu` VALUES ('203', '200', '批量删除', '/sys/role/batchRemove', 'sys:role:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:12', '1');
INSERT INTO `sys_menu` VALUES ('204', '200', '编辑', '/sys/role/edit/{id}', 'sys:role:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:12', '1');
INSERT INTO `sys_menu` VALUES ('205', '3', '用户管理', '/sys/user', 'sys:user:list', '1', 'layui-icon layui-icon-username', '0', '2019-05-30 10:54:16', '1');
INSERT INTO `sys_menu` VALUES ('206', '205', '增加', '/sys/user/add', 'sys:user:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:16', '1');
INSERT INTO `sys_menu` VALUES ('207', '205', '删除', '/sys/user/remove', 'sys:user:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:16', '1');
INSERT INTO `sys_menu` VALUES ('208', '205', '批量删除', '/sys/user/batchRemove', 'sys:user:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:16', '1');
INSERT INTO `sys_menu` VALUES ('209', '205', '编辑', '/sys/user/edit/{id}', 'sys:user:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 10:54:16', '1');
INSERT INTO `sys_menu` VALUES ('210', '178', '列表', '/sys/online/list', 'sys:online:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:19:33', '1');
INSERT INTO `sys_menu` VALUES ('211', '175', '列表', '/sys/log/list', 'sys:log:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:20:48', '1');
INSERT INTO `sys_menu` VALUES ('213', '184', '列表', '/sys/task/list', 'sys:task:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:22:31', '1');
INSERT INTO `sys_menu` VALUES ('214', '205', '列表', '/sys/user/list', 'sys:user:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:25:56', '1');
INSERT INTO `sys_menu` VALUES ('215', '200', '列表', '/sys/role/list', 'sys:role:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:27:00', '1');
INSERT INTO `sys_menu` VALUES ('216', '196', '列表', '/sys/menu/list', 'sys:menu:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:27:34', '1');
INSERT INTO `sys_menu` VALUES ('217', '190', '列表', '/sys/dept/list', 'sys:dept:list', '2', 'layui-icon layui-icon-rate-half', '1', '2019-05-30 13:28:08', '1');
INSERT INTO `sys_menu` VALUES ('218', '0', '测试目录', '', 'sys:test', '0', 'layui-icon layui-icon-code-circle', '5', '2019-05-30 15:22:24', '1');
INSERT INTO `sys_menu` VALUES ('219', '218', '配置管理', '/chao/config', 'chao:config:list', '1', 'layui-icon layui-icon-survey', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('220', '219', '增加', '/chao/config/add', 'chao:config:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('221', '219', '删除', '/chao/config/remove', 'chao:config:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('222', '219', '列表', '/chao/config/list', 'chao:config:list', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('223', '219', '批量删除', '/chao/config/batchRemove', 'chao:config:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('224', '219', '编辑', '/chao/config/edit/{id}', 'chao:config:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-05-30 15:22:41', '1');
INSERT INTO `sys_menu` VALUES ('225', '218', '富文本管理', '/chao/richtext', 'chao:richtext:list', '1', 'layui-icon layui-icon-fonts-html', '0', '2019-06-06 17:30:27', '1');
INSERT INTO `sys_menu` VALUES ('226', '225', '增加', '/chao/richtext/add', 'chao:richtext:add', '2', 'layui-icon layui-icon-rate-half', '0', '2019-06-06 17:30:27', '1');
INSERT INTO `sys_menu` VALUES ('227', '225', '删除', '/chao/richtext/remove', 'chao:richtext:remove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-06-06 17:30:27', '1');
INSERT INTO `sys_menu` VALUES ('228', '225', '列表', '/chao/richtext/list', 'chao:richtext:list', '2', 'layui-icon layui-icon-rate-half', '0', '2019-06-06 17:30:27', '1');
INSERT INTO `sys_menu` VALUES ('229', '225', '批量删除', '/chao/richtext/batchRemove', 'chao:richtext:batchRemove', '2', 'layui-icon layui-icon-rate-half', '0', '2019-06-06 17:30:27', '1');
INSERT INTO `sys_menu` VALUES ('230', '225', '编辑', '/chao/richtext/edit/{id}', 'chao:richtext:edit', '2', 'layui-icon layui-icon-rate-half', '0', '2019-06-06 17:30:27', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `rights` varchar(303) DEFAULT '0' COMMENT '权限->id 请不要超过1000',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('61', '研发1号', '测试', '3450866591326812804583847523564531629751321907803404436691480360255496');
INSERT INTO `sys_role` VALUES ('62', '测试1号', '1', '0');
INSERT INTO `sys_role` VALUES ('63', '测试3号', '1', '0');
INSERT INTO `sys_role` VALUES ('64', '测试4号', '1', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `roles` varchar(303) DEFAULT '0' COMMENT '权限集合-id请不要超过1000',
  `dept_id` bigint(20) DEFAULT NULL,
  `dept_name` varchar(50) DEFAULT '',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `deleted` tinyint(4) DEFAULT 0 COMMENT '逻辑删除  1 删除',
  `status` tinyint(4) DEFAULT 1 COMMENT '0.禁用1.正常',
  `create_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', 'a66abb5684c45962d887564f08346e8d', '0', '6', '研发部', 'chaojunzi@qq.com', '15711066461', '0', '1', '2019-06-04 09:55:19');
INSERT INTO `sys_user` VALUES ('150', 'xuechao', '薛超', 'b66faf5855eefc11c2961d402bb140be', '2305843009213693952', '6', '研发部', '152@qq.com', '15711110000', '0', '1', '2019-08-19 14:49:40');
