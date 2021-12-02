/*
 Navicat Premium Data Transfer

 Source Server         : 200
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 192.168.31.200:3306
 Source Schema         : xd

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 02/12/2021 11:01:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `BLOB_DATA` blob NULL COMMENT '一个blob字段，存放持久化Trigger对象',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Trigger作为Blob类型存储（用于Quartz用户用JDBC创建他们自己定制的Trigger类型，JobStore 并不知道如何存储实例的时候）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日历名称',
  `CALENDAR` blob NOT NULL COMMENT '一个blob字段，存放持久化calendar对象',
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '以Blob类型存储Quartz的Calendar日历信息， quartz可配置一个日历来指定一个时间范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储Cron Trigger，包括Cron表达式和时区信息。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度器实例id',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度器实例名',
  `FIRED_TIME` bigint NOT NULL COMMENT '触发的时间',
  `SCHED_TIME` bigint NOT NULL COMMENT '定时器制定的时间',
  `PRIORITY` int NOT NULL COMMENT '优先级',
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '集群中job的名字',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '集群中job的所属组的名字',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行，默认为false，设置了RequestsRecovery为true，则会被重新执行',
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集群中job的名字',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集群中job的所属组的名字',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细描述信息',
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集群中个notejob实现类的全限定名,quartz就是根据这个路径到classpath找到该job类',
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否持久化,把该属性设置为1，quartz会把job持久化到数据库中',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否并发执行',
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否更新数据',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否接受恢复执行，默认为false，设置了RequestsRecovery为true，则该job会被重新执行',
  `JOB_DATA` blob NULL COMMENT '一个blob字段，存放持久化job对象',
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储每一个已配置的Job的详细信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储程序的非观锁的信息(假如使用了悲观锁)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已暂停的Trigger组的信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '之前配置文件中org.quartz.scheduler.instanceId配置的名字，就会写入该字段',
  `LAST_CHECKIN_TIME` bigint NOT NULL COMMENT '上次检查时间',
  `CHECKIN_INTERVAL` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储集群中note实例信息，quartz会定时读取该表的信息判断集群中每个实例的当前状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_ name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `REPEAT_COUNT` bigint NOT NULL COMMENT '重复的次数统计',
  `REPEAT_INTERVAL` bigint NOT NULL COMMENT '重复的间隔时间',
  `TIMES_TRIGGERED` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储简单的 Trigger，包括重复次数，间隔，以及已触的次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_ name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `INT_PROP_1` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `INT_PROP_2` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `LONG_PROP_1` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `LONG_PROP_2` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储CalendarIntervalTrigger和DailyTimeIntervalTrigger' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '触发器的名字',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细描述信息',
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL COMMENT '下一次触发时间，默认为-1，意味不会自动触发',
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `PRIORITY` int NULL DEFAULT NULL COMMENT '优先级',
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前触发器状态，设置为ACQUIRED,如果设置为WAITING,则job不会触发 （ WAITING:等待 PAUSED:暂停ACQUIRED:正常执行 BLOCKED：阻塞 ERROR：错误）',
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '触发器的类型，使用cron表达式',
  `START_TIME` bigint NOT NULL COMMENT '开始时间',
  `END_TIME` bigint NULL DEFAULT NULL COMMENT '结束时间',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程表名称，表qrtz_calendars的calendar_name字段外键',
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL COMMENT '措施或者是补偿执行的策略',
  `JOB_DATA` blob NULL COMMENT '一个blob字段，存放持久化job对象',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已配置的触发器的基本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for m_code
-- ----------------------------
DROP TABLE IF EXISTS `m_code`;
CREATE TABLE `m_code`  (
  `CODE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code id',
  `CODE_GROUP` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code分组',
  `CODE_NAME` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code名称',
  `CODE_VALUE` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code值',
  `CODE_ORDER` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code顺序',
  `CODE_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CODE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CODE_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `CODE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`CODE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统code列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of m_code
-- ----------------------------
INSERT INTO `m_code` VALUES ('27e54727-2f4a-4a61-8dfa-2b40d4a91617', 'platform', 'PC端', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('3af57768-9f67-4471-b54a-a73e118ba43b', 'file_type', '其它', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('41d7333f-2f7c-448b-9f5c-477146fbe18b', 'file_type', '音频', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('5a50dade-21f0-40b5-8f49-40e1bc0d63f7', 'del_flag', '禁用', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('5ddc2c02-5bff-4c25-af45-5f74547144aa', 'prompt_box_type', 'Toastr', '1', '2', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('649c4ec4-1d6c-47c3-b6e4-75a3b5f59f02', 'resource_type', '自定义资源', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('715a50f1-08a7-4eda-8a82-5a69b26afecb', 'platform', '移动端', '2', '2', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('89fdce2e-8266-4da4-92f0-c32a7c2943f8', 'file_type', '日志', '4', '4', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('9be9284c-4801-4f35-abf8-05c05ed12d4b', 'file_type', '文本', '3', '3', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('9eb0d3f0-dd9e-4edd-a404-aec5122aaf1c', 'prompt_box_type', '不显示', '-1', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('b3f0ae40-b68a-4cd4-bac7-44b8d718f045', 'yes_no', '否', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('b4ce7155-6f3f-48bb-853c-c1f74ef59487', 'request_method', 'put', '4', '4', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('b99bbe7e-3194-4391-82cd-35a513987d63', 'file_type', '图片', '5', '5', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('c4aae09c-b5ce-4522-a140-1a60db8f29f4', 'prompt_box_type', 'layer', '0', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('c4d98da3-f577-40d9-afa3-15c82078e710', 'platform', '全部', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('c55e143a-f702-4079-9705-1a3ab60be9d1', 'request_method', 'get', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('c6af1301-58fd-4a1c-ac7f-e8380e7de229', 'prompt_box_type', 'SmallPop', '2', '3', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('cc419d91-ce9e-4c39-b699-0a204d7a1e9b', 'request_method', 'post', '2', '2', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('d4088601-ce02-4091-b04e-fd9d588e4087', 'request_method', 'all method', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('d84d5c3c-74c6-40d8-a871-4b6342312adc', 'file_type', '视频', '2', '2', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('d8f89112-ccac-4305-8d1e-f8d741bd4e69', 'request_method', 'delete', '3', '3', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('d94f1671-0454-4755-939c-4f3e63a77319', 'resource_type', '系统资源', '0', '0', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('dde12ccd-6789-468e-be7a-f592b547b2e2', 'yes_no', '是', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('eea36b1c-a5f1-4674-b00d-124b76c37fed', 'platform', '其他', '3', '3', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `m_code` VALUES ('fabda942-710b-44f2-a81c-3dc14eb8831b', 'del_flag', '启用', '1', '1', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:05', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_auth
-- ----------------------------
DROP TABLE IF EXISTS `s_auth`;
CREATE TABLE `s_auth`  (
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `AUTH_NAME` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `AUTH_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_menu`;
CREATE TABLE `s_auth_menu`  (
  `AUTH_MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单-权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `AUTH_MENU_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_MENU_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_MENU_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_MENU_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_MENU_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_menu
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth_position
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_position`;
CREATE TABLE `s_auth_position`  (
  `AUTH_POSITION_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位-权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `POSITION_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位id',
  `AUTH_POSITION_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_POSITION_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_POSITION_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_POSITION_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_POSITION_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_POSITION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限-职位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_position
-- ----------------------------

-- ----------------------------
-- Table structure for s_auth_url
-- ----------------------------
DROP TABLE IF EXISTS `s_auth_url`;
CREATE TABLE `s_auth_url`  (
  `AUTH_URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求-权限id',
  `AUTH_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `AUTH_URL_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `AUTH_URL_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `AUTH_URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `AUTH_URL_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `AUTH_URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`AUTH_URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请求权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_auth_url
-- ----------------------------

-- ----------------------------
-- Table structure for s_config
-- ----------------------------
DROP TABLE IF EXISTS `s_config`;
CREATE TABLE `s_config`  (
  `CONFIG_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户配置id',
  `CONFIG_KEY` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置的Key',
  `CONFIG_VALUE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置的Value',
  `CONFIG_REMARK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置的注释',
  `CONFIG_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `CONFIG_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `CONFIG_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CONFIG_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `CONFIG_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `CONFIG_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`CONFIG_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_config
-- ----------------------------

-- ----------------------------
-- Table structure for s_department
-- ----------------------------
DROP TABLE IF EXISTS `s_department`;
CREATE TABLE `s_department`  (
  `DEPARTMENT_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门id',
  `DEPARTMENT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `DEPARTMENT_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `DEPARTMENT_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DEPARTMENT_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `DEPARTMENT_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `DEPARTMENT_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`DEPARTMENT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_department
-- ----------------------------

-- ----------------------------
-- Table structure for s_file
-- ----------------------------
DROP TABLE IF EXISTS `s_file`;
CREATE TABLE `s_file`  (
  `FILE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件id',
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `FILE_SIZE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件大小',
  `FILE_SUFFIX` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件后缀',
  `FILE_FOLDER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所属文件夹id',
  `FILE_OWNER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件所有者',
  `FILE_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `FILE_DETAIL_INFO` json NULL COMMENT '文件额外信息',
  `FILE_DEL_FLAG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `FILE_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `FILE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `FILE_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `FILE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`FILE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_file
-- ----------------------------

-- ----------------------------
-- Table structure for s_file_type
-- ----------------------------
DROP TABLE IF EXISTS `s_file_type`;
CREATE TABLE `s_file_type`  (
  `FILE_SUFFIX` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件后缀',
  `FILE_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件类型',
  `FILE_TYPE_DEL_FLAG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `FILE_TYPE_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `FILE_TYPE_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `FILE_TYPE_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `FILE_TYPE_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`FILE_SUFFIX`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_file_type
-- ----------------------------

-- ----------------------------
-- Table structure for s_folder
-- ----------------------------
DROP TABLE IF EXISTS `s_folder`;
CREATE TABLE `s_folder`  (
  `FOLDER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹ID',
  `FOLDER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹名称',
  `FOLDER_PARENT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父文件夹',
  `FOLDER_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹路径',
  `FOLDER_OWNER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹所有者',
  `FOLDER_RESOURCE_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源类型：系统资源，自定义资源',
  `FOLDER_DEL_FLAG` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `FOLDER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `FOLDER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `FOLDER_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `FOLDER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`FOLDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件夹信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_folder
-- ----------------------------

-- ----------------------------
-- Table structure for s_logger
-- ----------------------------
DROP TABLE IF EXISTS `s_logger`;
CREATE TABLE `s_logger`  (
  `LOGGER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志ID',
  `LOGGER_USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `LOGGER_LEVEL` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志级别',
  `LOGGER_THREAD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '输出日志的线程',
  `LOGGER_TIME` datetime NOT NULL COMMENT '产生日志的时间',
  `LOGGER_IFNO` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日志主体',
  PRIMARY KEY (`LOGGER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_logger
-- ----------------------------

-- ----------------------------
-- Table structure for s_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu`  (
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `MENU_TEXT` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单显示文本',
  `MENU_PARENT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单',
  `MENU_ORDER` int NOT NULL COMMENT '顺序',
  `MENU_URL` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击菜单发出的请求(请求id)',
  `MENU_GROUP` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为菜单组(Code)',
  `MENU_ICON` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `MENU_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `MENU_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MENU_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `MENU_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `MENU_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_menu
-- ----------------------------
INSERT INTO `s_menu` VALUES ('008157e3-9b5f-4c09-9cb4-08d0e154a340', '测试11', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 2, '', '1', '', '1', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('01328cd5-694f-4f2c-9779-b1a161b96a1b', '添加请求', 'c842cc3b-60b9-46ad-8afd-49c5829d08bc', 2, '3f1a1d6f-fcd0-11eb-8efc-dc71963a4a6b', '0', '', '1', '2021-08-14 15:23:10', '00000000-0000-0000-0000-000000000000', '2021-08-14 15:23:10', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('1102253e-5726-4cd9-8122-921535828249', '权限管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 5, '', '1', '', '1', '2021-08-19 11:21:39', '00000000-0000-0000-0000-000000000000', '2021-08-19 11:21:39', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('152bf703-7911-4c68-900f-3d9aaaf0dd43', '请求列表', 'c842cc3b-60b9-46ad-8afd-49c5829d08bc', 1, '17de105c-fcb1-11eb-8efc-dc71963a4a6b', '0', '', '1', '2021-08-14 11:46:26', '00000000-0000-0000-0000-000000000000', '2021-08-14 11:47:03', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('18569f7f-6f50-4353-b102-cc526515588a', '测试菜单1111', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 1, '01e90072-d6a3-48f5-ae46-a0dfbfc770d4', '0', '', '1', '2021-10-05 16:35:31', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:35:31', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('22e2e5ba-dafc-11eb-bf86-dc71963a4a6b', '菜单管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 2, '', '1', '', '1', '2021-07-02 14:11:50', '00000000-0000-0000-0000-000000000000', '2021-09-20 18:46:58', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('33b001bd-c746-44da-b4e1-5b3ce953e2e0', '测试菜单', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 7, '', '1', '', '1', '2021-10-05 16:29:27', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:29:27', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('4be4710d-842d-4186-9e30-64555db53277', '添加权限', '1102253e-5726-4cd9-8122-921535828249', 2, '87a72807-ad42-4832-8c45-4bd811b02e03', '0', '', '1', '2021-09-20 18:46:08', '00000000-0000-0000-0000-000000000000', '2021-09-20 18:46:08', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('50c24126-df8d-11eb-bf86-dc71963a4a6b', '添加用户', 'a758c55b-db18-11eb-bf86-dc71963a4a6b', 2, '8f6b29e1-df8d-11eb-bf86-dc71963a4a6b', '0', NULL, '1', '2021-07-08 09:39:08', '00000000-0000-0000-0000-000000000000', '2021-07-08 09:39:01', '000000000000');
INSERT INTO `s_menu` VALUES ('6eaee9e3-e053-11eb-bf86-dc71963a4a6b', '部门管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 3, NULL, '1', NULL, '1', '2021-07-09 09:17:17', '00000000-0000-0000-0000-000000000000', '2021-07-09 09:17:17', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('722fd376-b79b-4e55-8381-9d4eae2e9f61', '测试菜单', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 7, '', '1', '', '1', '2021-10-05 16:29:27', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:29:27', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('7241f47c-dbd9-11eb-bf86-dc71963a4a6b', '用户列表', 'a758c55b-db18-11eb-bf86-dc71963a4a6b', 1, 'ee43194b-db2c-11eb-bf86-dc71963a4a6b', '0', NULL, '1', '2021-07-03 16:35:53', '00000000-0000-0000-0000-000000000000', '2021-07-03 16:35:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('82c9bccd-d34c-40ae-b076-7ca43cc6175c', '测试11', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 2, '', '1', '', '1', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('a0bd6ae6-4abf-4f45-b407-21567b457c61', '测试菜单1111', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 1, '01e90072-d6a3-48f5-ae46-a0dfbfc770d4', '0', '', '1', '2021-10-05 16:35:31', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:35:31', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('a758c55b-db18-11eb-bf86-dc71963a4a6b', '用户管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 1, '', '1', '', '1', '2021-07-02 17:35:05', '00000000-0000-0000-0000-000000000000', '2021-09-20 18:47:07', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', '系统设置', NULL, 1, '', '1', 'fa fa-cog', '1', '2021-07-02 14:09:02', '00000000-0000-0000-0000-000000000000', '2021-07-22 18:31:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('b23e4881-e9d5-11eb-bf86-dc71963a4a6b', '菜单结构', '22e2e5ba-dafc-11eb-bf86-dc71963a4a6b', 1, '1d17d89d-e9d6-11eb-bf86-dc71963a4a6b', '0', '', '1', '2021-07-21 11:44:20', '00000000-0000-0000-0000-000000000000', '2021-07-22 18:30:43', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('bc986acc-6b4f-4487-ad82-9d4550822f38', '文件管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 6, '', '1', '', '1', '2021-08-19 13:24:41', '00000000-0000-0000-0000-000000000000', '2021-10-03 18:43:36', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('c05abccd-108d-4848-87d7-da291b546a60', '测试11', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 2, '', '1', '', '1', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('c842cc3b-60b9-46ad-8afd-49c5829d08bc', '请求管理', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 4, '', '1', '', '1', '2021-08-14 11:45:38', '00000000-0000-0000-0000-000000000000', '2021-08-14 11:46:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('cf4a5a2e-1695-4928-a1aa-28a3ec629e92', '测试菜单', 'b0d6b8cc-dafb-11eb-bf86-dc71963a4a6b', 7, '', '1', '', '1', '2021-10-05 16:29:27', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:40:26', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('d107f41e-e053-11eb-bf86-dc71963a4a6b', '部门结构', '6eaee9e3-e053-11eb-bf86-dc71963a4a6b', 1, '91e88722-e054-11eb-bf86-dc71963a4a6b', '0', NULL, '1', '2021-07-09 09:20:09', '00000000-0000-0000-0000-000000000000', '2021-07-09 09:20:09', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('e3d2a335-b85c-469c-b740-ba9ed96626a8', '文件列表', 'bc986acc-6b4f-4487-ad82-9d4550822f38', 1, 'd83a4beb-3af4-43c1-a4ec-4f2c93fd6471', '0', '', '1', '2021-10-03 19:13:20', '00000000-0000-0000-0000-000000000000', '2021-10-03 19:13:20', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('e4924c85-ec39-4ff1-b96c-7890e04e2d7a', '测试菜单', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 1, '01e90072-d6a3-48f5-ae46-a0dfbfc770d4', '0', '', '1', '2021-10-05 16:35:31', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:37:23', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('e94a60d4-d1f2-46a5-9904-7caf1e7e1fc9', '测试11', 'cf4a5a2e-1695-4928-a1aa-28a3ec629e92', 2, '', '1', '', '1', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000', '2021-10-05 16:37:46', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_menu` VALUES ('f114c034-d8f5-49ef-bb31-f53cc3b35595', '权限列表', '1102253e-5726-4cd9-8122-921535828249', 1, '0a53e0ad-491c-4ec1-8b81-ab36d8e9f834', '0', '', '1', '2021-09-01 20:29:41', '00000000-0000-0000-0000-000000000000', '2021-09-01 20:32:30', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_position
-- ----------------------------
DROP TABLE IF EXISTS `s_position`;
CREATE TABLE `s_position`  (
  `POSITION_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位id',
  `POSITION_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称',
  `POSITION_DEPARTMENT_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门id',
  `POSITION_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `POSITION_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `POSITION_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `POSITION_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `POSITION_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`POSITION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_position
-- ----------------------------

-- ----------------------------
-- Table structure for s_url
-- ----------------------------
DROP TABLE IF EXISTS `s_url`;
CREATE TABLE `s_url`  (
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `URL_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求地址',
  `URL_TYPE` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方式(Code)(get,post)',
  `URL_PLATFORM` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统平台(Code)(pc,移动端)',
  `URL_REMARKS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `URL_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `URL_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `URL_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `URL_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `URL_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`URL_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统请求' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_url
-- ----------------------------
INSERT INTO `s_url` VALUES ('01e90072-d6a3-48f5-ae46-a0dfbfc770d4', '/sys/position/[^/]*', '3', '0', '', '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-10-04 12:01:00', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('0259c53c-9dea-4e4b-afc4-5da8c26218ed', '/sys/user/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('049b7fd1-5557-4731-a914-0d5e598827d2', '/sys/menu', '2', '0', '添加菜单', '1', '2021-08-18 20:22:29', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:22:29', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('07c3bafb-edb0-44f1-b8e5-055609b6061a', '/sys/user', '1', '0', '获取用户列表', '1', '2021-08-18 20:00:44', '00000000-0000-0000-0000-000000000000', '2021-11-26 16:38:40', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('09965c06-ba9c-4e84-9542-2a69322c99a3', '/sys/auth/[^/]*', '3', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('0a53e0ad-491c-4ec1-8b81-ab36d8e9f834', '/html/system/auth-list.html', '1', '1', '打开权限列表页面', '1', '2021-09-01 20:32:00', '00000000-0000-0000-0000-000000000000', '2021-09-01 20:32:00', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('0a61f05f-85be-428d-8121-f2e4e4ce44ee', '/sys/menu/tree', '1', '0', '获取菜单(树状结构)', '1', '2021-08-18 20:22:05', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:22:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('0e9973cf-4e3d-48f6-8b94-a31f59c20afa', '/sys/file/[^/]*', '4', '0', NULL, '1', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('116e8c4c-7f5e-4d1f-905e-b5c8f65f8718', '/job/getAll', '1', '0', NULL, '1', '2021-10-27 21:23:03', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:03', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('148d513a-cd73-476d-93b2-356baff947c6', '/sys/menu/[^/]*', '3', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('153413c3-abee-4734-ba75-941835530929', '/sys/department/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('17c50f88-4a60-44bd-add6-b88764d8ef7a', '/sys/file/[^/]*', '3', '0', NULL, '1', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('17de105c-fcb1-11eb-8efc-dc71963a4a6b', '/html/system/url-list.html', '1', '1', '打开请求列表页面', '1', '2021-08-14 11:39:27', '00000000-0000-0000-0000-000000000000', '2021-08-14 11:39:30', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('19c13582-8527-4098-97fc-757419d5c544', '/sys/file/[^/]*', '1', '0', NULL, '1', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000', '2021-10-04 12:22:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('1c408355-9a2a-4a1e-b8f9-4eb044ff241a', '/sys/menu/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('1d0359b3-b65b-4d9e-b3c9-4011badc99cd', '/sys/file/video/content/[^/]*', '1', '0', NULL, '1', '2021-10-05 18:10:59', '00000000-0000-0000-0000-000000000000', '2021-10-05 18:10:59', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('1d17d89d-e9d6-11eb-bf86-dc71963a4a6b', '/html/system/menu-list.html', '1', '1', '打开菜单结构页面', '1', '2021-07-21 11:46:59', '00000000-0000-0000-0000-000000000000', '2021-07-21 11:46:56', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('21700dc1-9b66-4b50-ae9f-00f1f8662794', '/job/getAllRun', '1', '0', NULL, '1', '2021-10-27 21:23:03', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:23:03', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('22ae586c-2f6c-4e98-bb41-497ff45c106a', '/sys/department', '2', '0', '添加部门信息', '1', '2021-08-18 20:08:28', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:08:28', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('2555cf63-c413-11eb-bd64-dc71963a4a6b', '/html/system/home.html', '1', '1', '打开首页', '1', '2021-06-03 10:28:53', '00000000-0000-0000-0000-000000000000', '2021-08-15 17:25:39', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('2a1a98ce-2e0d-4c73-953f-3bc792e71327', '/sys/auth', '1', '0', '获取权限列表', '1', '2021-09-01 20:28:37', '00000000-0000-0000-0000-000000000000', '2021-09-01 20:28:37', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('30166288-ab7a-4343-9ce7-28518bde6a8d', '/sys/user/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('370a2a5d-0b53-4f73-9b3c-b951a10eba5e', '/sys/position', '2', '0', '添加职位', '1', '2021-08-18 20:09:31', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:09:31', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('3cb6177a-870d-4f5a-bfd2-03951693f10b', '/sys/department/all', '1', '0', '获取部门信息列表', '1', '2021-08-18 20:06:59', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:06:59', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('3dd28cc0-4ea3-43d1-9e08-f75304770fa5', '/sys/url', '2', '0', '添加请求信息', '1', '2021-08-18 20:24:23', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:24:23', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('3f1a1d6f-fcd0-11eb-8efc-dc71963a4a6b', '/html/system/url-detail.html', '1', '1', '打开请求详细页面', '1', '2021-08-14 15:22:18', '00000000-0000-0000-0000-000000000000', '2021-08-14 15:22:20', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('400700ba-7aed-4f7f-a32f-21017f5bc80f', '/sys/department/[^\\/]*', '1', '0', '获取部门的详细信息', '1', '2021-08-18 20:07:40', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:07:40', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('40a9f212-0f24-4a64-bd41-89bf0c34aca9', '/sys/menu', '1', '0', '获取用户的菜单列表', '1', '2021-08-18 19:58:57', '00000000-0000-0000-0000-000000000000', '2021-08-18 19:58:57', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('450e8ac6-cd34-41cd-bce6-f7ad3ec9ee03', '/job/stop', '1', '0', NULL, '1', '2021-10-28 20:37:49', '00000000-0000-0000-0000-000000000000', '2021-10-28 20:37:49', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('45b7af57-a6d7-4147-a8fc-9548b0c42c16', '/sys/auth', '2', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('471570ca-b860-4ea4-957f-8e485da1086f', '/sys/menu/[^\\/]*', '4', '0', '修改菜单信息', '1', '2021-08-18 20:23:16', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:23:16', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('4b323466-08ad-4c70-b2b3-48cbb828d0d8', '/sys/position/all', '1', '0', '获取职位列表', '1', '2021-08-18 20:10:18', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:10:18', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('4bb53baa-320f-49dd-a545-a52a21901457', '/job/restart', '1', '0', NULL, '1', '2021-10-28 20:37:49', '00000000-0000-0000-0000-000000000000', '2021-10-28 20:37:49', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('4e997be2-4aff-4114-970e-5cdc42ef7174', '/sys/fileType/[^/]*', '4', '0', NULL, '1', '2021-10-06 12:37:00', '00000000-0000-0000-0000-000000000000', '2021-10-06 12:37:00', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('53745962-3a61-45b1-ba82-9232dbffaced', '/sys/config', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('573dab83-e7c0-4a1e-b985-0f43b9d44092', '/sys/fileType', '1', '0', NULL, '1', '2021-10-03 12:18:50', '00000000-0000-0000-0000-000000000000', '2021-10-03 12:18:50', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('6073b2df-3c53-4109-8ddc-dc54f558921e', '/sys/url/[^\\/]*', '1', '0', '获取请求详细信息', '1', '2021-08-18 20:25:58', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:25:58', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('61d6b4c9-839c-437b-b05c-b3ae2f2d1e59', '/excel/export/dbTable', '1', '0', '', '1', '2021-11-13 18:36:27', '00000000-0000-0000-0000-000000000000', '2021-11-19 19:00:12', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('66de5151-3e96-4c26-8fe5-7c87b6ac1250', '/html/system/menu-detail.html', '1', '0', '打开菜单详细页面', '1', '2021-08-19 10:01:23', '00000000-0000-0000-0000-000000000000', '2021-08-19 10:03:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('69d8e5ba-fde2-4dfc-b68a-89b53a5dde56', '/sys/file', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('774f32b0-1d9e-47fe-84de-2d7e16adc20b', '/sys/file/image/content/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('7af4b757-660a-49ef-8964-09511cec892f', '/sys/department/[^/]*', '3', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('810d2658-2189-4a32-85ea-114eeb83a489', '/sys/url/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('811982d8-3148-4048-9d85-994e8c3bbe70', '/html/system/file-upload.html', '1', '1', '打开上传文件的页面', '1', '2021-10-02 00:20:56', '00000000-0000-0000-0000-000000000000', '2021-10-02 00:20:56', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('814044b3-45bf-43c3-896b-2b6ef7c3e841', '/sys/url', '1', '0', '获取请求列表-分页', '1', '2021-08-18 20:23:55', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:23:55', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('8280d482-5031-4009-9764-b84224228a18', '/sys/user/[^\\/]*', '1', '0', '获取用户的详细信息', '1', '2021-08-18 20:01:54', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:01:54', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('87a72807-ad42-4832-8c45-4bd811b02e03', '/html/system/auth-detail.html', '1', '0', '打开权限详细页面', '1', '2021-09-20 18:45:49', '00000000-0000-0000-0000-000000000000', '2021-09-20 18:45:49', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('8edfa8ae-c4e6-11eb-bd64-dc71963a4a6b', '/html/system/config.html', '1', '1', '打开系统配置页面', '1', '2021-06-04 11:40:08', '00000000-0000-0000-0000-000000000000', '2021-06-04 11:40:08', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('8f6b29e1-df8d-11eb-bf86-dc71963a4a6b', '/html/system/user-detail.html', '1', '1', '打开新增用户页面', '1', '2021-07-08 09:40:57', '00000000-0000-0000-0000-000000000000', '2021-07-08 09:40:54', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('914b16f7-8952-4331-a500-308320fcbefa', '/sys/file/download/[^/]*', '1', '0', NULL, '1', '2021-10-05 17:00:41', '00000000-0000-0000-0000-000000000000', '2021-10-05 17:00:41', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('91e88722-e054-11eb-bf86-dc71963a4a6b', '/html/system/department-list.html', '1', '1', '打开部门结构页面', '1', '2021-07-09 09:24:45', '000000000000', '2021-07-09 09:24:35', '000000000000');
INSERT INTO `s_url` VALUES ('92c21c3c-a7f4-41dd-83b2-a6bb3af8888a', '/sys/file/upload', '2', '0', NULL, '1', '2021-10-02 00:05:07', '00000000-0000-0000-0000-000000000000', '2021-10-02 00:05:07', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('974e4a77-47d0-4481-b0f4-38e3d0c3b517', '/sys/auth/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('9959a0b1-499f-4570-8cce-862df77f29de', '/sys/department/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('aa6ee452-580c-4102-87e2-8d35e9cd4724', '/sys/auth/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('aeffe66b-9ad8-4bc1-9ed8-426dc4d80ee6', '/sys/loginUser', '1', '0', '获取登录用户的详细信息', '1', '2021-08-18 19:59:26', '00000000-0000-0000-0000-000000000000', '2021-08-18 19:59:26', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('af5708b1-fc37-42b9-aedc-15c3a0490a6c', '/sys/url/[^/]*', '3', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('b36834ac-8d3e-4698-a66f-8724952a7382', '/sys/url/[^\\/]*', '4', '0', '修改请求信息', '1', '2021-08-18 20:25:13', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:25:32', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('b86f0665-cec8-4cb1-84c4-1bb13c8290da', '/sys/folder/resources', '1', '0', NULL, '1', '2021-10-01 16:01:04', '00000000-0000-0000-0000-000000000000', '2021-10-01 16:01:04', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ba5e1868-f549-4893-bc59-a4dd5917536c', '/sys/department/[^\\/]*', '4', '0', '修改部门信息', '1', '2021-08-18 20:08:02', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:08:02', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('bb8cd732-8299-48aa-828f-8b2edeb4ceee', '/sys/menu/[^\\/]*', '1', '0', '获取菜单详细信息', '1', '2021-08-18 20:22:49', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:22:49', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('c714a402-51f3-4d3c-9aee-2ee62d75bf94', '/sys/department/tree', '1', '0', '获取部门信息(树状结构)', '1', '2021-08-18 20:05:24', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:06:24', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('cda48799-30f5-4bc8-9fcd-eb4e6da5b8de', '/sys/user', '2', '0', '添加用户', '1', '2021-08-18 20:02:19', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:02:19', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d2d54e1d-25fb-47a5-99df-6dc4c54b6e63', '/job/start', '1', '0', NULL, '1', '2021-10-27 21:08:43', '00000000-0000-0000-0000-000000000000', '2021-10-27 21:08:43', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d3854960-5b4e-4c40-8652-5104c8b0ec18', '/sys/url/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d494f6ee-705c-4992-aff4-ef3a04d83964', '/sys/position/[^\\/]*', '4', '0', '修改职位信息', '1', '2021-08-18 20:20:35', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:20:35', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d665627e-b068-481a-8cc5-55e99c1018c3', '/sys/position/[^/]*', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d83a4beb-3af4-43c1-a4ec-4f2c93fd6471', '/html/system/file-manager.html', '1', '1', '打开文件管理画面', '1', '2021-10-01 14:34:37', '00000000-0000-0000-0000-000000000000', '2021-10-01 14:34:37', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('d8ebd651-cb5c-4375-a538-ec41f7c3d044', '/sys/logout', '1', '0', NULL, '1', '2021-11-28 20:30:58', '00000000-0000-0000-0000-000000000000', '2021-11-28 20:30:58', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('daf46f55-7482-4e01-aed6-9695350b5967', '/sys/user/[^\\/]*', '3', '0', '封禁 / 解禁账号', '1', '2021-09-21 12:43:29', '00000000-0000-0000-0000-000000000000', '2021-09-21 12:43:29', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('db9c2d9c-a35b-4415-b811-4bf13a54a381', '/sys/url/param/all', '1', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('eae3d68e-a64e-4599-b384-c8d808ed27ab', '/sys/menu/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ed8d3614-31a7-4c45-9db2-c198a0b95d17', '/sys/user/[^/]*', '3', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ee43194b-db2c-11eb-bf86-dc71963a4a6b', '/html/system/user-list.html', '1', '1', '打开用户列表页面', '1', '2021-07-02 20:00:37', '00000000-0000-0000-0000-000000000000', '2021-07-02 20:00:40', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ee6d1780-cfcc-465e-846e-2024fa6187f9', '/sys/position/[^/]*', '4', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('eebac119-259f-4399-8b6c-2a7be15c8f7b', '/sys/login', '2', '0', NULL, '1', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000', '2021-09-27 18:49:05', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('f097d743-4d8b-4887-a52e-4ac13008795e', '/sys/code', '1', '0', '获取code列表', '1', '2021-08-18 20:03:20', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:03:20', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('f7ad5660-1587-4d13-86d2-54a3b94ee3cc', '/sys/position/[^\\/]*', '1', '0', '获取职位详细信息', '1', '2021-08-18 20:20:00', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:20:00', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ff7c419a-1d15-4ac3-9c10-d93da3974776', '/sys/user/[^\\/]*', '4', '0', '修改用户信息', '1', '2021-08-18 20:02:49', '00000000-0000-0000-0000-000000000000', '2021-08-28 20:21:45', '00000000-0000-0000-0000-000000000000');
INSERT INTO `s_url` VALUES ('ffff6b87-6867-4078-9c8f-a2fbc46e766d', '/sys/position', '1', '0', '获取职位列表-分页', '1', '2021-08-18 20:09:16', '00000000-0000-0000-0000-000000000000', '2021-08-18 20:17:44', '00000000-0000-0000-0000-000000000000');

-- ----------------------------
-- Table structure for s_url_param
-- ----------------------------
DROP TABLE IF EXISTS `s_url_param`;
CREATE TABLE `s_url_param`  (
  `URL_PARAM_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数id',
  `URL_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求id',
  `URL_PARAM_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数名',
  `URL_PARAM_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数值，正则表达式',
  `URL_PARAM_REQUIRED` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否为必须参数(Code)',
  `URL_PARAM_REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数备注',
  `URL_PARAM_ERR_HINT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数异常时候的提示',
  `URL_PARAM_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `URL_PARAM_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `URL_PARAM_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `URL_PARAM_MODIFY_TIME` datetime NOT NULL COMMENT '编辑事件',
  `URL_PARAM_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`URL_PARAM_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请求参数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_url_param
-- ----------------------------

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `USER_NUMBER` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `USER_PWD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `USER_NAME` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `USER_HEAD` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '用户头像(文件id)',
  `USER_PLATFORM` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号能登陆的平台Code(pc,移动端)',
  `USER_QQ` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `USER_EMAIL` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `USER_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `USER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `USER_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `USER_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `USER_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_user
-- ----------------------------

-- ----------------------------
-- Table structure for s_user_position
-- ----------------------------
DROP TABLE IF EXISTS `s_user_position`;
CREATE TABLE `s_user_position`  (
  `USER_POSITION_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户-职位id',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `POSITION_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位ID',
  `USER_POSITION_DEL_FLAG` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '删除标识',
  `USER_POSITION_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `USER_POSITION_CREATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `USER_POSITION_MODIFY_TIME` datetime NOT NULL COMMENT '编辑时间',
  `USER_POSITION_MODIFY_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编辑者',
  PRIMARY KEY (`USER_POSITION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户-职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_user_position
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
