/*
Navicat MySQL Data Transfer

Source Server         : mylinuxsql
Source Server Version : 50719
Source Host           : 192.168.13.128:3306
Source Database       : chart_room

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-27 16:13:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for MESSAGES
-- ----------------------------
DROP TABLE IF EXISTS `MESSAGES`;
CREATE TABLE `MESSAGES` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `MESSAGE` varchar(255) DEFAULT NULL,
  `SEND_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
