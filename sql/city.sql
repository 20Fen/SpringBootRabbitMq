

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `pid` varchar(111) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `id` varchar(111) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('0', '陕西', '1');
INSERT INTO `city` VALUES ('0', '北京', '2');
INSERT INTO `city` VALUES ('0', '上海', '3');
INSERT INTO `city` VALUES ('0', '广州', '4');
INSERT INTO `city` VALUES ('0', '山西', '5');
INSERT INTO `city` VALUES ('2', '八里屯', '11');
INSERT INTO `city` VALUES ('1', '西安', '12');
INSERT INTO `city` VALUES ('4', '东莞', '13');
INSERT INTO `city` VALUES ('5', '运城', '14');
INSERT INTO `city` VALUES ('1', '咸阳', '15');
INSERT INTO `city` VALUES ('11', '汽车', '3');
INSERT INTO `city` VALUES ('12', '乾县', '3');
INSERT INTO `city` VALUES ('13', '宾县', '3');
INSERT INTO `city` VALUES ('14', '永寿', '3');
INSERT INTO `city` VALUES ('15', '常务', '3');
INSERT INTO `city` VALUES ('11', '神木', '3');
SET FOREIGN_KEY_CHECKS=1;
