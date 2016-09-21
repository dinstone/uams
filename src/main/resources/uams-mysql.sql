CREATE DATABASE `uams` /*!40100 DEFAULT CHARACTER SET utf8 */;
use `uams`;
CREATE TABLE `tb_local_account` (
  `username` varchar(32) NOT NULL,
  `password` varchar(16) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `status` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_oauth_account` (
  `access_id` varchar(32) NOT NULL,
  `access_token` varchar(32) NOT NULL,
  `expires` int(10) unsigned NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `account_type` varchar(45) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`access_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_user_profile` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  `email` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
