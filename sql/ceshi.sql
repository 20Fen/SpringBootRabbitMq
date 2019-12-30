
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ceshi
-- ----------------------------
DROP TABLE IF EXISTS `ceshi`;
CREATE TABLE `ceshi` (
  `id` varchar(254) NOT NULL,
  `plan_no` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `stat_time` timestamp NULL DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `doc` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `file` blob,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ceshi
-- ----------------------------
INSERT INTO `ceshi` VALUES ('1', 'a5edee4-32e-992-8822-2d78d470e4cc', '2019-12-24 16:05:30', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('11', 'a5edee4-32e-992-8822-2d78d470e4cc', '2019-12-24 16:05:30', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('112', 'a5edee4-32e-992-8822-2d78d470e4cc', '2019-12-24 16:05:30', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('2', 'a5edee4-32e-4992-8822-2d78d470e4cc', '2019-12-24 16:05:47', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('22', 'a5edee4-32e-4992-8822-2d78d470e4cc', '2019-12-24 16:05:47', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('222', 'a5edee4-32e-4992-8822-2d78d470e4cc', '2019-12-24 16:05:47', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('8226cfbb1-5433-4c3a-8f6d-c975252be184', 'a5edee4-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('826cfbb1-5433-4c3a-8f6d-c975252be184', 'a5edee4-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('8326cfbb13-5433-4c3a-8f6d-c975252be184', 'a5edee54-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('836cfbb13-5433-4c3a-8f6d-c975252be184', 'a5edee54-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('86cfbb1-5433-4c3a-8f6d-c975252be184', 'a5edee4-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
INSERT INTO `ceshi` VALUES ('86cfbb13-5433-4c3a-8f6d-c975252be184', 'a5edee54-32be-4992-8822-2d78d470e4cc', '2019-10-10 01:00:00', '2019-10-10 01:00:00', '2019-12-20 16:30:54', null, null, null, '2019-12-20 16:32:02');
SET FOREIGN_KEY_CHECKS=1;
