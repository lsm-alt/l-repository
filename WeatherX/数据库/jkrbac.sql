/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.8.76
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : 192.168.8.76:3306
 Source Schema         : jkrbac

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 21/07/2025 13:59:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `dkey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dval` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`dkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('weather_low', NULL);

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 验证码表\r\n作用: 用于存储登录验证码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL COMMENT '日志主键',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作用户',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应结果',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `result` int(11) NULL DEFAULT NULL COMMENT '执行结果',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 日志审计表\r\n作用: 用于存储操作日志等审计信息。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1943491176501923842, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":0,\"children\":[],\"name\":\"七天预报\",\"url\":\"\",\"type\":0,\"icon\":\"icon-instagram-fill\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 70, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:02:59');
INSERT INTO `sys_log` VALUES (1943491303069241347, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":0,\"children\":[],\"name\":\"冬枣指数预报\",\"url\":\"\",\"type\":0,\"icon\":\"icon-idcard\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 31, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:03:30');
INSERT INTO `sys_log` VALUES (1943491371524476930, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":0,\"children\":[],\"name\":\"三夏农业生产气象\",\"url\":\"\",\"type\":0,\"icon\":\"icon-api\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 29, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:03:46');
INSERT INTO `sys_log` VALUES (1943494426517098498, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":1943491303069241346,\"children\":[],\"name\":\"预报制作\",\"url\":\"weather/jujubemaking\",\"type\":0,\"icon\":\"icon-edit-square\",\"permissions\":\"0\",\"sort\":0,\"parentName\":\"冬枣指数预报\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 31, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:15:54');
INSERT INTO `sys_log` VALUES (1943494642150461442, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":1943491303069241346,\"children\":[],\"name\":\"预报管理\",\"url\":\"weather/jujubemanage\",\"type\":0,\"icon\":\"icon-sound\",\"permissions\":\"0\",\"sort\":1,\"parentName\":\"冬枣指数预报\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 28, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:16:46');
INSERT INTO `sys_log` VALUES (1943495082866954241, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":1943491371524476929,\"children\":[],\"name\":\"预报制作\",\"url\":\"weather/threesummermaking\",\"type\":0,\"icon\":\"icon-fork\",\"permissions\":\"0\",\"sort\":0,\"parentName\":\"三夏农业生产气象\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 29, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:18:31');
INSERT INTO `sys_log` VALUES (1943495261603024899, 'admin', '保存菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.save()\n->请求参数:\n[{\"pid\":1943491371524476929,\"children\":[],\"name\":\"预报管理\",\"url\":\"weather/threesummermanage\",\"type\":0,\"icon\":\"icon-fullscreen\",\"permissions\":\"0\",\"sort\":0,\"parentName\":\"三夏农业生产气象\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 26, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:19:13');
INSERT INTO `sys_log` VALUES (1943505430902788097, 'admin', '更新菜单数据', '->请求方法:\ncom.jklxdata.rbac.system.controller.MenuController.update()\n->请求参数:\n[{\"id\":1943491176308985857,\"pid\":0,\"children\":[],\"name\":\"七天预报\",\"url\":\"weather/forecast\",\"type\":0,\"icon\":\"icon-instagram-fill\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 42, 1, '0:0:0:0:0:0:0:1', '2025-07-11 10:59:38');
INSERT INTO `sys_log` VALUES (1945712080929697794, 'admin', '删除用户数据', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.delete()\n->请求参数:\n[[1945691635903954946]]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 101, 1, '0:0:0:0:0:0:0:1', '2025-07-17 13:08:04');
INSERT INTO `sys_log` VALUES (1945712112928043010, 'admin', '删除用户数据', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.delete()\n->请求参数:\n[[1945685581094793218]]\n->异常堆栈:\ncn.hutool.core.io.IORuntimeException: FileSystemException: d:\\weatherX\\tmp\\2025-07-17 11-22-46.pdf: 另一个程序正在使用此文件，进程无法访问。\r\n\r\n	at cn.hutool.core.io.FileUtil.del(FileUtil.java:775)\r\n	at cn.hutool.core.io.FileUtil.del(FileUtil.java:735)\r\n	at com.codedata.rbac.service.impl.ForecastServiceImpl.delete(ForecastServiceImpl.java:70)\r\n	at com.codedata.rbac.service.impl.ForecastServiceImpl$$FastClassBySpringCGLIB$$510d6837.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy.invokeMethod(CglibAopProxy.java:386)\r\n	at org.springframework.aop.framework.CglibAopProxy.access$000(CglibAopProxy.java:85)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:704)\r\n	at com.codedata.rbac.service.impl.ForecastServiceImpl$$EnhancerBySpringCGLIB$$4f6495ff.del......', 24, 0, '0:0:0:0:0:0:0:1', '2025-07-17 13:08:12');
INSERT INTO `sys_log` VALUES (1945712152241254401, 'admin', '删除用户数据', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.delete()\n->请求参数:\n[[1945685581094793218]]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 11, 1, '0:0:0:0:0:0:0:1', '2025-07-17 13:08:21');
INSERT INTO `sys_log` VALUES (1945712264073981954, 'admin', '删除用户数据', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.delete()\n->请求参数:\n[[1945683224172769281,1945683274647023617,1945683901766774785,1945683973455818754,1945684111272259586,1945684253715017730,1945684535983288322,1945685447380381698]]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 64, 1, '0:0:0:0:0:0:0:1', '2025-07-17 13:08:48');
INSERT INTO `sys_log` VALUES (1945712318016925697, 'admin', '删除用户数据', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.delete()\n->请求参数:\n[[1944681562259869698,1944683609864654849,1945649145813446657,1945651600622116865,1945659383383961601,1945659383383961602,1945671451126370306,1945675025633890305,1945675251002126337,1945681035522027522]]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 75, 1, '0:0:0:0:0:0:0:1', '2025-07-17 13:09:01');
INSERT INTO `sys_log` VALUES (1945763525746012161, 'admin', '下载七天预报PDF', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.download()\n->请求参数:\n[1945712549290848258]\n->响应参数:\n{\"code\":0,\"msg\":\"\",\"data\":\"d:\\\\weatherX\\\\tmp\\\\2025-07-17 13-09-55.pdf\"}\n', 18, 1, '0:0:0:0:0:0:0:1', '2025-07-17 16:32:30');
INSERT INTO `sys_log` VALUES (1945763761855967234, 'admin', '下载七天预报PDF', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.download()\n->请求参数:\n[1945712549290848258]\n->响应参数:\n{\"code\":0,\"msg\":\"\",\"data\":\"d:\\\\weatherX\\\\tmp\\\\2025-07-17 13-09-55.pdf\"}\n', 3, 1, '0:0:0:0:0:0:0:1', '2025-07-17 16:33:26');
INSERT INTO `sys_log` VALUES (1945764010209095681, 'admin', '下载七天预报PDF', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.download()\n->请求参数:\n[1945712549290848258]\n->响应参数:\n{\"code\":0,\"msg\":\"\",\"data\":\"d:\\\\weatherX\\\\tmp\\\\2025-07-17 13-09-55.pdf\"}\n', 3, 1, '0:0:0:0:0:0:0:1', '2025-07-17 16:34:25');
INSERT INTO `sys_log` VALUES (1945764056564543490, 'admin', '下载七天预报PDF', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.download()\n->请求参数:\n[1945712549290848258]\n->响应参数:\n{\"code\":0,\"msg\":\"\",\"data\":\"d:\\\\weatherX\\\\tmp\\\\2025-07-17 13-09-55.pdf\"}\n', 5, 1, '0:0:0:0:0:0:0:1', '2025-07-17 16:34:36');
INSERT INTO `sys_log` VALUES (1945765534846943234, 'admin', '下载七天预报PDF', '->请求方法:\ncom.codedata.rbac.controller.ForecastController.download()\n->请求参数:\n[1945712549290848258]\n->响应参数:\n{\"code\":0,\"msg\":\"\",\"data\":\"d:\\\\weatherX\\\\tmp\\\\2025-07-17 13-09-55.pdf\"}\n', 5, 1, '0:0:0:0:0:0:0:1', '2025-07-17 16:40:29');
INSERT INTO `sys_log` VALUES (1947105759259041794, 'admin', '更新菜单数据', '->请求方法:\ncom.codedata.rbac.system.controller.MenuController.update()\n->请求参数:\n[{\"id\":1943491371524476929,\"pid\":0,\"children\":[],\"name\":\"三夏农业生产气象\",\"url\":\"weather/jujubemaking\",\"type\":0,\"icon\":\"icon-api\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 78, 1, '0:0:0:0:0:0:0:1', '2025-07-21 09:26:03');
INSERT INTO `sys_log` VALUES (1947105806071668737, 'admin', '更新菜单数据', '->请求方法:\ncom.codedata.rbac.system.controller.MenuController.update()\n->请求参数:\n[{\"id\":1943491371524476929,\"pid\":0,\"children\":[],\"name\":\"三夏农业生产气象\",\"url\":\"\",\"type\":0,\"icon\":\"icon-api\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 28, 1, '0:0:0:0:0:0:0:1', '2025-07-21 09:26:14');
INSERT INTO `sys_log` VALUES (1947105856017440769, 'admin', '删除菜单数据', '->请求方法:\ncom.codedata.rbac.system.controller.MenuController.delete()\n->请求参数:\n[1943494426449989634]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 24, 1, '0:0:0:0:0:0:0:1', '2025-07-21 09:26:26');
INSERT INTO `sys_log` VALUES (1947105874145222658, 'admin', '删除菜单数据', '->请求方法:\ncom.codedata.rbac.system.controller.MenuController.delete()\n->请求参数:\n[1943494642083352577]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 13, 1, '0:0:0:0:0:0:0:1', '2025-07-21 09:26:31');
INSERT INTO `sys_log` VALUES (1947105896416976898, 'admin', '更新菜单数据', '->请求方法:\ncom.codedata.rbac.system.controller.MenuController.update()\n->请求参数:\n[{\"id\":1943491303069241346,\"pid\":0,\"children\":[],\"name\":\"冬枣指数预报\",\"url\":\"weather/jujubemaking\",\"type\":0,\"icon\":\"icon-idcard\",\"permissions\":\"\",\"sort\":0,\"detail\":\"\",\"parentName\":\"一级菜单\"}]\n->响应参数:\n{\"code\":0,\"msg\":\"\"}\n', 41, 1, '0:0:0:0:0:0:0:1', '2025-07-21 09:26:36');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL,
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `permissions` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：system:user:list,system:user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：菜单   1：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单详情',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 系统菜单表\r\n作用: 用于存储云密码管理系统的菜单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '权限管理', NULL, NULL, 0, 'icon-safetycertificate', 0, NULL, 1, '2022-07-28 17:53:40', 1, '2022-09-01 17:05:18');
INSERT INTO `sys_menu` VALUES (2, 1, '角色管理', 'system/role', NULL, 0, 'icon-team', 2, NULL, 1, '2022-07-28 17:53:44', NULL, NULL);
INSERT INTO `sys_menu` VALUES (3, 1, '用户管理', 'system/user', NULL, 0, 'icon-user', 1, NULL, 1, '2022-07-28 17:53:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (4, 2, '查看', NULL, 'system:role:query,system:role:detail', 1, NULL, 0, NULL, 1, '2022-07-28 18:03:38', NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, 2, '新增', NULL, 'system:role:save,system:menu:select', 1, NULL, 1, NULL, 1, '2022-07-28 18:12:31', NULL, NULL);
INSERT INTO `sys_menu` VALUES (6, 2, '修改', NULL, 'system:role:update,system:menu:select', 1, NULL, 2, NULL, 1, '2022-07-28 18:12:35', NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, 2, '删除', NULL, 'system:role:delete', 1, NULL, 3, NULL, 1, '2022-07-28 18:12:37', NULL, NULL);
INSERT INTO `sys_menu` VALUES (8, 3, '查看', NULL, 'system:user:query,system:user:detail,system:user:select', 1, NULL, 0, NULL, 1, '2022-07-29 09:39:30', 1, '2022-08-05 16:02:21');
INSERT INTO `sys_menu` VALUES (9, 3, '新增', NULL, 'system:user:save,system:role:select', 1, NULL, 1, NULL, 1, '2022-07-29 09:39:33', NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 3, '修改', NULL, 'system:user:update,system:role:select', 1, NULL, 2, NULL, 1, '2022-07-29 09:39:35', NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 3, '删除', NULL, 'system:user:delete', 1, NULL, 3, NULL, 1, '2022-07-29 09:39:38', NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 1, '菜单管理', 'system/menu', NULL, 0, 'icon-unorderedlist', 3, NULL, 1, '2022-07-29 09:39:40', NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 12, '查看', NULL, 'system:menu:list,system:menu:detail', 1, NULL, 0, NULL, 1, '2022-07-29 10:04:58', NULL, NULL);
INSERT INTO `sys_menu` VALUES (14, 12, '新增', NULL, 'system:menu:save', 1, NULL, 1, NULL, 1, '2022-07-29 10:05:01', NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 12, '修改', NULL, 'system:menu:update', 1, NULL, 2, NULL, 1, '2022-07-29 10:05:03', NULL, NULL);
INSERT INTO `sys_menu` VALUES (16, 12, '删除', NULL, 'system:menu:delete', 1, NULL, 3, NULL, 1, '2022-07-29 10:05:05', NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 0, '系统管理', '', '', 0, 'icon-setting', 1, NULL, 1, '2022-07-29 10:24:14', 1, '2022-09-01 17:05:21');
INSERT INTO `sys_menu` VALUES (18, 17, '日志管理', 'system/log', '', 0, 'icon-filesearch', 0, NULL, 1, '2022-08-12 13:52:54', 1, '2022-08-12 13:52:54');
INSERT INTO `sys_menu` VALUES (19, 18, '详情', '', 'system:log:detail', 1, '', 1, NULL, 1, '2022-08-12 13:55:03', 1, '2022-08-12 13:55:50');
INSERT INTO `sys_menu` VALUES (20, 18, '查看', '', 'system:log:query', 1, '', 0, NULL, 1, '2022-08-12 13:55:26', 1, '2022-08-12 13:55:26');
INSERT INTO `sys_menu` VALUES (1943491176308985857, 0, '七天预报', 'weather/forecast', '', 0, 'icon-instagram-fill', 0, '', 1, '2025-07-11 10:02:59', 1, '2025-07-11 10:59:38');
INSERT INTO `sys_menu` VALUES (1943491303069241346, 0, '冬枣指数预报', 'weather/jujubemaking', '', 0, 'icon-idcard', 0, '', 1, '2025-07-11 10:03:30', 1, '2025-07-21 09:26:36');
INSERT INTO `sys_menu` VALUES (1943491371524476929, 0, '三夏农业生产气象', '', '', 0, 'icon-api', 0, '', 1, '2025-07-11 10:03:46', 1, '2025-07-21 09:26:14');
INSERT INTO `sys_menu` VALUES (1943495082804039682, 1943491371524476929, '预报制作', 'weather/threesummermaking', '0', 0, 'icon-fork', 0, NULL, 1, '2025-07-11 10:18:31', 1, '2025-07-11 10:18:31');
INSERT INTO `sys_menu` VALUES (1943495261603024898, 1943491371524476929, '预报管理', 'weather/threesummermanage', '0', 0, 'icon-fullscreen', 0, NULL, 1, '2025-07-11 10:19:13', 1, '2025-07-11 10:19:13');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `bak` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 角色表\r\n作用: 用于存储系统的角色信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色主键',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单主键',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 角色菜单表\r\n作用: 用于存储系统角色所可见的菜单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `expire_date` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 用户登录信息表\r\n作用: 用于存储系统用户登录的信息，进行权限校验' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_token
-- ----------------------------
INSERT INTO `sys_token` VALUES (1943490644907446273, 1, 'NjAwZThiMTktZjkzNS00MjEwLTkxNmMtZTA1MWQ0ZjA4YWRj', '2025-07-21 21:24:14', '2025-07-21 09:24:14', '2025-07-11 10:00:53');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `head_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `super_admin` int(11) NULL DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(11) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称: 用户表\r\n作用: 用于存储系统的用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '超级管理员', NULL, 2, 'admin@admin.com', '12345678900', 1, 1, NULL, NULL, 1, '2023-02-23 17:37:24');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户d',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '名称:用户角色表\r\n作用: 用于存储用户所对应的角色信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for weather_forecast
-- ----------------------------
DROP TABLE IF EXISTS `weather_forecast`;
CREATE TABLE `weather_forecast`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `forecast_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预报的目标日期',
  `time_with` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '七天天气预报信息',
  `district_with` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '地区天气预报信息',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件信息',
  `creator` bigint(20) NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weather_forecast
-- ----------------------------
INSERT INTO `weather_forecast` VALUES (1945712549290848258, '2025-07-17 13-09-55.pdf', '[{\"date\":\"17\",\"timeRange\":\"今天夜间到明天白天\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"41\"},{\"date\":\"18\",\"timeRange\":\"明天夜间-12日\",\"weather\":\"晴间多云\",\"windDirection\":\"东风转南风2～3级\",\"tempLow\":\"26\",\"tempHigh\":\"37\"},{\"date\":\"19\",\"timeRange\":\"后天夜间-13日\",\"weather\":\"晴转阴有雷雨\",\"windDirection\":\"南风转北风2～3级, 雷雨时阵风7～9级\",\"tempLow\":\"26\",\"tempHigh\":\"36\"},{\"date\":\"20\",\"timeRange\":\"14日\",\"weather\":\"多云\",\"windDirection\":\"东北风2～3级转东南风3～4级\",\"tempLow\":\"24\",\"tempHigh\":\"36\"},{\"date\":\"21\",\"timeRange\":\"15日\",\"weather\":\"多云转阴有雷雨\",\"windDirection\":\"南风转北风3～4级, 雷雨时阵风7～9级\",\"tempLow\":\"28\",\"tempHigh\":\"39\"},{\"date\":\"22\",\"timeRange\":\"16日\",\"weather\":\"阴转晴\",\"windDirection\":\"北风2～3级\",\"tempLow\":\"27\",\"tempHigh\":\"35\"},{\"date\":\"23\",\"timeRange\":\"17日\",\"weather\":\"晴间多云\",\"windDirection\":\"北风转南风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"36\"}]', '[{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"城区\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"37\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"中部\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"24\",\"tempHigh\":\"36\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"西部\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"37\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"南部\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"37\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"东部\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"37\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"北部\",\"weather\":\"晴间多云\",\"windDirection\":\"东风2～3级\",\"tempLow\":\"25\",\"tempHigh\":\"37\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"北部沿海\",\"weather\":\"晴间多云\",\"windDirection\":\"东风4～5级\",\"tempLow\":\"26\",\"tempHigh\":\"36\"},{\"timeRange\":\"今天夜间到明天白天：\",\"area\":\"海防\",\"weather\":\"晴间多云\",\"windDirection\":\"东风4～5级\",\"tempLow\":\"26\",\"tempHigh\":\"36\"}]', 'd:\\weatherX\\tmp\\2025-07-17 13-09-55.pdf', 1, '2025-07-17 13:09:56', NULL);

SET FOREIGN_KEY_CHECKS = 1;
