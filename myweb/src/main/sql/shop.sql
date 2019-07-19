/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-07-19 15:02:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_desc` varchar(255) DEFAULT NULL,
  `goods_price` double(10,2) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `goods_repertory` int(11) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `goods_status` int(1) DEFAULT NULL,
  `goods_sales` int(11) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_goodstype
-- ----------------------------
DROP TABLE IF EXISTS `t_goodstype`;
CREATE TABLE `t_goodstype` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_likes
-- ----------------------------
DROP TABLE IF EXISTS `t_likes`;
CREATE TABLE `t_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(255) DEFAULT NULL,
  `likenum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `confirm_date` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `pay_money` double DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_orderitem
-- ----------------------------
DROP TABLE IF EXISTS `t_orderitem`;
CREATE TABLE `t_orderitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `goods_number` int(11) DEFAULT NULL,
  `goods_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `permission_id` int(10) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(20) DEFAULT NULL,
  `menu_name` varchar(20) DEFAULT NULL,
  `permission_code` varchar(20) DEFAULT NULL,
  `permission_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_pre
-- ----------------------------
DROP TABLE IF EXISTS `t_role_pre`;
CREATE TABLE `t_role_pre` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) DEFAULT NULL,
  `permission_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_seckill
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill`;
CREATE TABLE `t_seckill` (
  `seckill_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀id,也是主键',
  `goods_id` varchar(225) NOT NULL COMMENT '秒杀的商品id',
  `seckill_repertory` int(11) NOT NULL COMMENT '秒杀商品库存',
  `seckill_price` double NOT NULL COMMENT '秒杀价格',
  `create_time` datetime NOT NULL COMMENT '秒杀创建时间',
  `start_time` datetime NOT NULL COMMENT '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`seckill_id`),
  KEY `start_time_index` (`start_time`),
  KEY `end_time_index` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='秒杀表';

-- ----------------------------
-- Table structure for t_seckillitem
-- ----------------------------
DROP TABLE IF EXISTS `t_seckillitem`;
CREATE TABLE `t_seckillitem` (
  `seckill_id` int(11) NOT NULL COMMENT '用于做联合主键，秒杀id',
  `user_id` int(11) NOT NULL COMMENT '用户id,用于联合主键',
  `create_time` datetime NOT NULL COMMENT '秒杀时间',
  `seckill_state` int(2) NOT NULL COMMENT '秒杀状态',
  PRIMARY KEY (`seckill_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀明细表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_password` char(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `userNameIndex` (`user_name`),
  KEY `emailIndex` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for exec_seckill
-- ----------------------------
DROP PROCEDURE IF EXISTS `exec_seckill`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `exec_seckill`(IN seckillId INT,IN userId INT,IN createTime DATETIME,OUT result INT,
IN receiverName VARCHAR(225),IN mobile VARCHAR(225),IN address VARCHAR(225),IN message VARCHAR(225),IN payMoney DOUBLE,IN goodsId INT)
BEGIN
	#定义一个变量用于看插入是否成功
	DECLARE insert_count INT DEFAULT 0;
	#定义一个变量用于保存订单id
	DECLARE order_id INT DEFAULT 0;
	#开启事务
	START TRANSACTION;
	INSERT IGNORE INTO t_seckillItem(seckill_id,user_id,create_time,seckill_state)
	VALUES(seckillId,userId,createTime,0);
	#影响行数插入到中
	SELECT ROW_COUNT() INTO insert_count;
	#如果影响行数等于0代表重复秒杀 返回-1
	IF(insert_count = 0) THEN
		ROLLBACK;
		SET result = -1;
	ELSEIF(insert_count < 0) THEN
		ROLLBACK;
		SET result = -2;
	#插入明细成功后执行减库存操作
	ELSE 
		UPDATE t_seckill
		SET seckill_repertory = seckill_repertory - 1
		WHERE seckill_id = seckillId
		AND seckill_repertory > 0
		AND end_time >= createTime
		AND start_time <= create_time;
		#再查看更新库存是否成功
		SELECT ROW_COUNT() INTO insert_count;
		IF(insert_count = 0) THEN
			ROLLBACK;
			SET result = -3;
		ELSEIF (insert_count < 0) THEN
			ROLLBACK;
			SET result = -2;
		#更新库存成功再进行添加订单操作
		ELSE
			INSERT IGNORE INTO t_order(receiver_name,mobile,address,message,create_date,status,pay_money,user_id)
			VALUES(receiverName,mobile,address,message,createTime,0,payMoney,userId);
			#查看插入是否成功
			SELECT LAST_INSERT_ID() INTO order_id;
			IF(order_id = 0) THEN
				ROLLBACK;
				SET result = -1;
			ELSEIF(order_id < 0) THEN
				ROLLBACK;
				SET result = -2;
			ELSE
				#插入订单成功后插入订单明细
				insert IGNORE into t_orderItem(order_id,goods_id,goods_number,goods_price)
				VALUES(insert_count,goodsId,1,payMoney);
				##插入后再判断插入是否成功
				SELECT ROW_COUNT() INTO insert_count;
				IF(insert_count = 0) THEN
					ROLLBACK;
					SET result = -1;
				ELSEIF(insert_count < 0) THEN
					ROLLBACK;
					SET result = -2;
				ELSE 
					#输出订单id
					SET result = order_id;
					COMMIT;
				END IF;
			END IF;	
		END IF;
	END IF;
END
;;
DELIMITER ;
