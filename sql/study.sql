/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2024-03-24 18:42:40
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_account
-- ----------------------------
INSERT INTO `db_account` VALUES ('1', null, 'admin', '$2a$10$..gFsGS0L3d25rPx6eYLqOFPw03NvEy47yg9CoaYvp8Ab0bNvfTEy', '0', '100', 'https://www.smallhowe.top:443/img/user_avatar/default-avatar.png', null, null, '2024-02-08 14:09:48');
INSERT INTO `db_account` VALUES ('4', '6', 'smallhowe', '$2a$10$h3DnEIIbVuk3jjmSdefyFuRS4NgsBxMHJ.cleSzEDPbYbH4h.NHQO', '1', '1700', 'http://localhost:8085/img/user_avatar/58209c14a33a43b1a9fb9a680df25c97.png', 'D:/static/images/user_avatar/58209c14a33a43b1a9fb9a680df25c97.png', 'smallhowe', '2024-03-06 01:57:27');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_carousel
-- ----------------------------
INSERT INTO `db_carousel` VALUES ('1', null, null, null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_message
-- ----------------------------
INSERT INTO `db_message` VALUES ('1', '标题1', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('2', '标题2', '内容', '1', '0', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('3', '标题3', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('4', '标题4', '内容', '1', '0', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('5', '标题5', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('6', '标题6', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('7', '标题7', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('8', '标题8', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('9', '标题9', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('10', '标题10', '内容', null, '1', '2024-02-24 20:26:58');
INSERT INTO `db_message` VALUES ('11', '标题11', '内容', '1', '0', '2024-02-24 20:26:58');

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_news
-- ----------------------------
INSERT INTO `db_news` VALUES ('14', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:34');
INSERT INTO `db_news` VALUES ('15', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:38');
INSERT INTO `db_news` VALUES ('16', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:39');
INSERT INTO `db_news` VALUES ('17', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:39');
INSERT INTO `db_news` VALUES ('18', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:40');
INSERT INTO `db_news` VALUES ('19', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:40');
INSERT INTO `db_news` VALUES ('20', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:41');
INSERT INTO `db_news` VALUES ('21', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-17 16:24:43');
INSERT INTO `db_news` VALUES ('22', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:48');
INSERT INTO `db_news` VALUES ('23', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:49');
INSERT INTO `db_news` VALUES ('24', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:49');
INSERT INTO `db_news` VALUES ('25', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:50');
INSERT INTO `db_news` VALUES ('26', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:51');
INSERT INTO `db_news` VALUES ('27', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:51');
INSERT INTO `db_news` VALUES ('28', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:52');
INSERT INTO `db_news` VALUES ('29', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:52');
INSERT INTO `db_news` VALUES ('30', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:53');
INSERT INTO `db_news` VALUES ('31', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:53');
INSERT INTO `db_news` VALUES ('32', '这是一条公告', '这是公告的内容', '张三', null, '2024-02-24 20:37:55');
INSERT INTO `db_news` VALUES ('33', '下载客户端教程', '首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先首先', 'Joyous小豪', null, '2024-03-05 16:15:33');

-- ----------------------------
-- Table structure for db_res
-- ----------------------------
DROP TABLE IF EXISTS `db_res`;
CREATE TABLE `db_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '文件名',
  `size` varchar(20) DEFAULT NULL COMMENT '文件大小',
  `url` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_res
-- ----------------------------
INSERT INTO `db_res` VALUES ('1', 'Minecraft服务器客户端', '1.12 GB', 'http://localhost:8085/resource/download/Minecraft客户端1.20.1.7z', 'D:/static/download/Minecraft客户端1.20.1.7z', '1.20.1', '完整版客户端下载');
INSERT INTO `db_res` VALUES ('2', 'mods文件', '488.41 MB', 'http://localhost:8085/resource/download/mods.zip', 'D:/static/download/mods.zip', '1.20.1', '如有客户端请下载此mods文件');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_sign_in
-- ----------------------------
INSERT INTO `db_sign_in` VALUES ('1', '4', '50', '2024-03-08 16:25:34');
INSERT INTO `db_sign_in` VALUES ('2', '4', '50', '2024-03-09 13:13:04');
INSERT INTO `db_sign_in` VALUES ('3', '4', '50', '2024-03-10 16:52:16');
INSERT INTO `db_sign_in` VALUES ('4', '4', '50', '2024-03-22 10:08:37');

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

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
