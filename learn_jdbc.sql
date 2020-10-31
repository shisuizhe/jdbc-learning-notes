/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : learn_jdbc

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 28/10/2020 16:05:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birth` date NULL DEFAULT NULL,
  `photo` mediumblob NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '汪峰', 'wf@126.com', '2010-02-02', NULL);
INSERT INTO `customer` VALUES (2, '王菲', 'wangf@163.com', '1988-12-26', NULL);
INSERT INTO `customer` VALUES (3, '林志玲', 'linzl@gmail.com', '1984-06-12', NULL);
INSERT INTO `customer` VALUES (4, '汤唯', 'tangw@sina.com', '1986-06-13', NULL);
INSERT INTO `customer` VALUES (5, '成龙', 'Jackey@gmai.com', '1955-07-14', NULL);
INSERT INTO `customer` VALUES (6, '迪丽热巴', 'reba@163.com', '1983-05-17', NULL);
INSERT INTO `customer` VALUES (7, '刘亦菲', 'liuyifei@qq.com', '1991-11-14', NULL);
INSERT INTO `customer` VALUES (8, '陈道明', 'bdf@126.com', '2014-01-17', NULL);
INSERT INTO `customer` VALUES (9, '周杰伦', 'zhoujl@sina.com', '1979-11-15', NULL);
INSERT INTO `customer` VALUES (10, '黎明', 'LiM@126.com', '1998-09-08', NULL);
INSERT INTO `customer` VALUES (11, '张学友', 'zhangxy@126.com', '1998-12-21', NULL);
INSERT INTO `customer` VALUES (12, '朱茵', 'zhuyin@126.com', '2014-01-16', NULL);
INSERT INTO `customer` VALUES (13, '贝多芬', 'beidf@126.com', '2014-01-17', NULL);

-- ----------------------------
-- Table structure for exam_student
-- ----------------------------
DROP TABLE IF EXISTS `exam_student`;
CREATE TABLE `exam_student`  (
  `flow_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL,
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `exam_card` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `grade` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`flow_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_student
-- ----------------------------
INSERT INTO `exam_student` VALUES (1, 4, '412824195263214584', '200523164754000', '张锋', '郑州', 85);
INSERT INTO `exam_student` VALUES (2, 4, '222224195263214584', '200523164754001', '孙朋', '大连', 56);
INSERT INTO `exam_student` VALUES (3, 6, '342824195263214584', '200523164754002', '刘明', '沈阳', 72);
INSERT INTO `exam_student` VALUES (4, 6, '100824195263214584', '200523164754003', '赵虎', '哈尔滨', 95);
INSERT INTO `exam_student` VALUES (5, 4, '454524195263214584', '200523164754004', '杨丽', '北京', 64);
INSERT INTO `exam_student` VALUES (6, 4, '854524195263214584', '200523164754005', '王小红', '太原', 60);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` int(0) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'AA', '2010-03-04');
INSERT INTO `order` VALUES (2, 'BB', '2000-02-01');
INSERT INTO `order` VALUES (3, 'GG', '1994-06-28');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456',
  `address` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '章子怡', 'qwerty', 'Beijing', '13788658672');
INSERT INTO `user` VALUES (2, '郭富城', 'abc123', 'HongKong', '15678909898');
INSERT INTO `user` VALUES (3, '林志颖', '654321', 'Taiwan', '18612124565');
INSERT INTO `user` VALUES (4, '梁静茹', '987654367', 'malaixiya', '18912340998');
INSERT INTO `user` VALUES (5, 'LadyGaGa', '123456', 'America', '13012386565');

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `balance` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_table
-- ----------------------------
INSERT INTO `user_table` VALUES ('AA', '123456', 1000);
INSERT INTO `user_table` VALUES ('BB', '654321', 1000);
INSERT INTO `user_table` VALUES ('CC', 'abcd', 2000);
INSERT INTO `user_table` VALUES ('DD', 'abcder', 3000);

SET FOREIGN_KEY_CHECKS = 1;
