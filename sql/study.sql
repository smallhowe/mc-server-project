/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2024-03-22 12:59:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for db_account
-- ----------------------------
DROP TABLE IF EXISTS `db_account`;
CREATE TABLE `db_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `groups` int(11) NOT NULL DEFAULT '0' COMMENT '用户权限组：0=普通用户,1=管理员',
  `exp` bigint(20) NOT NULL DEFAULT '0' COMMENT '账号经验值',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像url',
  `avatar_path` varchar(255) DEFAULT NULL,
  `game_id` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账户创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`username`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for db_carousel
-- ----------------------------
DROP TABLE IF EXISTS `db_carousel`;
CREATE TABLE `db_carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for db_message
-- ----------------------------
DROP TABLE IF EXISTS `db_message`;
CREATE TABLE `db_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `account_id` int(11) DEFAULT NULL COMMENT '指定接收人的账户id',
  `is_all` int(2) DEFAULT '0' COMMENT '是否为所有人接收消息 1=所有人接收 0=非所有人接收',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `msg_account_id` (`account_id`),
  CONSTRAINT `msg_account_id` FOREIGN KEY (`account_id`) REFERENCES `db_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for db_news
-- ----------------------------
DROP TABLE IF EXISTS `db_news`;
CREATE TABLE `db_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT '',
  `content` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for db_sign_in
-- ----------------------------
DROP TABLE IF EXISTS `db_sign_in`;
CREATE TABLE `db_sign_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `get_exp` int(20) NOT NULL COMMENT '用户获得的经验',
  `date_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sign_account_id` (`account_id`),
  CONSTRAINT `sign_account_id` FOREIGN KEY (`account_id`) REFERENCES `db_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
