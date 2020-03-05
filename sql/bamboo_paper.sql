/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : bamboo_paper

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-05 14:12:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bp_article
-- ----------------------------
DROP TABLE IF EXISTS `bp_article`;
CREATE TABLE `bp_article` (
  `a_id` varchar(40) NOT NULL COMMENT '文章id',
  `c_id` varchar(40) NOT NULL COMMENT '专栏id',
  `title` varchar(40) NOT NULL COMMENT '文章标题',
  `synopsis` varchar(40) NOT NULL COMMENT '文章简介',
  `content` text NOT NULL COMMENT '文章内容',
  `time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建的时间',
  `u_id` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bp_article
-- ----------------------------

-- ----------------------------
-- Table structure for bp_column
-- ----------------------------
DROP TABLE IF EXISTS `bp_column`;
CREATE TABLE `bp_column` (
  `c_id` varchar(40) NOT NULL COMMENT '专栏id',
  `title` varchar(40) NOT NULL COMMENT '专栏标题',
  `synopsis` varchar(100) NOT NULL COMMENT '专栏简介',
  `article` bigint(10) NOT NULL DEFAULT '0' COMMENT '文章数量',
  `u_id` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bp_column
-- ----------------------------
INSERT INTO `bp_column` VALUES ('98474e77-12d4-4cd1-b890-6405b848fddf', 'uni-app', 'uni-app入门', '0', '91dad9ef-c8cf-4579-aaea-6f7ce9cd0a8f');
INSERT INTO `bp_column` VALUES ('a4a5163d-06cc-4d3c-ac88-29670828aee2', 'Java基础(集合)', 'Java的基础集合系列', '0', '91dad9ef-c8cf-4579-aaea-6f7ce9cd0a8f');

-- ----------------------------
-- Table structure for bp_note
-- ----------------------------
DROP TABLE IF EXISTS `bp_note`;
CREATE TABLE `bp_note` (
  `n_id` varchar(40) NOT NULL COMMENT '随笔id',
  `u_id` varchar(40) NOT NULL COMMENT '用户id',
  `title` varchar(40) NOT NULL COMMENT '随笔标题',
  `content` text NOT NULL COMMENT '内容',
  `synopsis` varchar(100) DEFAULT NULL COMMENT '随笔简介',
  `time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '随笔创建时间',
  PRIMARY KEY (`n_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bp_note
-- ----------------------------
INSERT INTO `bp_note` VALUES ('78805d02-b73a-4e96-bff7-898da83c4c21', '91dad9ef-c8cf-4579-aaea-6f7ce9cd0a8f', 'demo', '<p>demo</p>', 'demo', '2020-03-05 00:25:21');

-- ----------------------------
-- Table structure for bp_user
-- ----------------------------
DROP TABLE IF EXISTS `bp_user`;
CREATE TABLE `bp_user` (
  `u_id` varchar(40) NOT NULL COMMENT '用户id',
  `username` varchar(40) NOT NULL COMMENT '用户名称',
  `img_url` varchar(255) DEFAULT NULL COMMENT '用户头像图片地址',
  `status` bigint(10) DEFAULT '1' COMMENT '状态码 0:博主 1:访客',
  `password` varchar(40) NOT NULL COMMENT '用户密码',
  `column` bigint(10) DEFAULT '0' COMMENT '专栏数量',
  `article` bigint(10) DEFAULT '0' COMMENT '文章数量',
  `account` varchar(40) NOT NULL COMMENT '账户',
  `note` bigint(10) DEFAULT '0',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bp_user
-- ----------------------------
INSERT INTO `bp_user` VALUES ('91dad9ef-c8cf-4579-aaea-6f7ce9cd0a8f', '李二三', 'http://bp-oss.oss-cn-beijing.aliyuncs.com/wx22a8a9e4eb60fe47.o6zAJs8ks7y8PtaO79Btlcq_q9zg.zSgevkOnLVaGb44374e62ae69f37a292dade2fc6ab58.png', '0', '86b848b034b70f3f3c0d38804649749d', '2', '0', '15838630117', '1');
INSERT INTO `bp_user` VALUES ('95bb559f-9d26-4c70-af20-bdba4566181d', '15838886666', '../../static/logo/logo.png', '1', '4e75e54faabf9d022dbd9d87b84a75ba', '0', '0', '15838886666', '0');
