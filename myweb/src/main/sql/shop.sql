/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-11-13 14:05:11
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
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('1', '帅气高帮男鞋', '高帮鞋，帅气，限量，上档次哦，欢迎购买', '299.00', '1', '98', '1554820502579timg.jpg', '0', '2');
INSERT INTO `t_goods` VALUES ('2', '红色高跟鞋', '高根鞋，好看，限量，上档次哦，欢迎购买', '99.99', '2', '98', '1554902123882u=2801716591,2074949373&fm=26&gp=0.jpg', '0', '2');
INSERT INTO `t_goods` VALUES ('3', '闪电红色男鞋', '新上市，著名设计师设计闪电鞋，爆红全世界，欢迎购买！', '599.00', '1', '997', '1554907142630u=3450433288,2995516568&fm=26&gp=0.jpg', '0', '3');
INSERT INTO `t_goods` VALUES ('4', '夏季女童豆豆鞋', '夏天必买，专门为儿童准备的豆豆鞋，非常的舒适，不买一定亏', '88.88', '5', '996', '1554907368070u=175396457,810716146&fm=26&gp=0.jpg', '0', '4');
INSERT INTO `t_goods` VALUES ('5', '秋季男童棉鞋', '舒适的脚感，堪比Boost,脚感就像踩屎一样，赶紧为你的孩子购买一双', '66.66', '3', '998', '1554907455967u=1720412097,2511182135&fm=26&gp=0.jpg', '0', '2');
INSERT INTO `t_goods` VALUES ('6', '真皮精品皮鞋', '采用外国专业养殖牛真皮执著，假一赔十，欢迎购买', '999.99', '3', '9998', '1554907593068u=3721482029,1196854761&fm=26&gp=0.jpg', '0', '2');
INSERT INTO `t_goods` VALUES ('7', '棕色马丁靴', '冬天必备，每个男人都需要拥有这么一双马丁靴，快来购买吧', '499.99', '1', '1000', '1557217344001u=4279105512,1400915299&fm=26&gp=0.jpg', '0', '0');

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
-- Records of t_goodstype
-- ----------------------------
INSERT INTO `t_goodstype` VALUES ('1', '男子');
INSERT INTO `t_goodstype` VALUES ('2', '女子');
INSERT INTO `t_goodstype` VALUES ('3', '男孩');
INSERT INTO `t_goodstype` VALUES ('5', '女孩');

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
-- Records of t_likes
-- ----------------------------
INSERT INTO `t_likes` VALUES ('1', '网站的点赞数', '5');

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
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('60', '12321321', '18711309775', '3123123112312', '', '2019-07-12 16:19:06', null, null, null, '0', '50', null);
INSERT INTO `t_order` VALUES ('61', 'vzdas', '18711309775', '湖南郴州如此casd', '', '2019-07-17 22:54:53', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('62', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 00:06:16', null, null, null, '0', '99', null);
INSERT INTO `t_order` VALUES ('63', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 00:07:08', null, null, null, '0', '99', null);
INSERT INTO `t_order` VALUES ('64', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 00:08:54', null, null, null, '0', '99', null);
INSERT INTO `t_order` VALUES ('65', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 00:10:19', null, null, null, '0', '99', null);
INSERT INTO `t_order` VALUES ('66', 'fanshanchao', '18711309775', '湖南省郴州汝城集龙dsas', '', '2019-07-18 22:06:13', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('67', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 22:10:54', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('68', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 22:11:30', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('69', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 22:12:32', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('70', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 22:17:57', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('71', 'fanshanchao', '18711309775', 'hunnasdnas', 'this.formData.message', '2019-07-18 22:24:56', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('72', 'fanxiansheng', '18711309775', 'huadsfsdfsdfasfsdaf', '', '2019-07-18 22:47:03', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('73', '范汕潮', '18711309775', '12312312312312312', '', '2019-07-18 23:59:12', null, null, null, '0', '199.99', null);
INSERT INTO `t_order` VALUES ('74', '11111111', '18711309775', 'fsdfsfasfsdffsd', '', '2019-07-19 12:47:57', null, null, null, '0', '199.99', '3');

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
-- Records of t_orderitem
-- ----------------------------
INSERT INTO `t_orderitem` VALUES ('96', '60', '3', '1', '50');
INSERT INTO `t_orderitem` VALUES ('97', '61', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('98', '62', '3', '1', '99');
INSERT INTO `t_orderitem` VALUES ('99', '63', '3', '1', '99');
INSERT INTO `t_orderitem` VALUES ('100', '64', '3', '1', '99');
INSERT INTO `t_orderitem` VALUES ('101', '65', '3', '1', '99');
INSERT INTO `t_orderitem` VALUES ('102', '66', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('103', '67', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('104', '68', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('105', '69', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('106', '70', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('107', '71', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('108', '72', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('109', '73', '7', '1', '199.99');
INSERT INTO `t_orderitem` VALUES ('110', '1', null, '1', '199.99');

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
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', 'user', '用户管理', 'user:list', '查看');
INSERT INTO `t_permission` VALUES ('2', 'user', '用户管理', 'user:update', '修改');
INSERT INTO `t_permission` VALUES ('3', 'user', '用户管理', 'user:add', '添加');
INSERT INTO `t_permission` VALUES ('4', 'user', '用户管理', 'user:delete', '删除');
INSERT INTO `t_permission` VALUES ('5', 'goods', '商品管理', 'goods:list', '查看');
INSERT INTO `t_permission` VALUES ('6', 'goods', '商品管理', 'goods:update', '修改');
INSERT INTO `t_permission` VALUES ('7', 'goods', '商品管理', 'goods:add', '添加');
INSERT INTO `t_permission` VALUES ('8', 'goods', '商品管理', 'goods:delete', '删除');
INSERT INTO `t_permission` VALUES ('9', 'order', '订单管理', 'order:list', '查看');
INSERT INTO `t_permission` VALUES ('10', 'order', '订单管理', 'order:update', '修改');
INSERT INTO `t_permission` VALUES ('11', 'order', '订单管理', 'order:add', '添加');
INSERT INTO `t_permission` VALUES ('12', 'order', '订单管理', 'order:delete', '删除');
INSERT INTO `t_permission` VALUES ('13', 'role', '用户管理', 'role:update', '权限');

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
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员');
INSERT INTO `t_role` VALUES ('2', '商品管理员');
INSERT INTO `t_role` VALUES ('3', '商城会员');
INSERT INTO `t_role` VALUES ('4', '游客');
INSERT INTO `t_role` VALUES ('5', '订单管理员');

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
-- Records of t_role_pre
-- ----------------------------
INSERT INTO `t_role_pre` VALUES ('1', '1', '1');
INSERT INTO `t_role_pre` VALUES ('2', '1', '2');
INSERT INTO `t_role_pre` VALUES ('3', '1', '3');
INSERT INTO `t_role_pre` VALUES ('4', '1', '4');
INSERT INTO `t_role_pre` VALUES ('5', '1', '5');
INSERT INTO `t_role_pre` VALUES ('6', '1', '6');
INSERT INTO `t_role_pre` VALUES ('7', '1', '7');
INSERT INTO `t_role_pre` VALUES ('8', '1', '8');
INSERT INTO `t_role_pre` VALUES ('9', '1', '9');
INSERT INTO `t_role_pre` VALUES ('10', '1', '10');
INSERT INTO `t_role_pre` VALUES ('11', '1', '11');
INSERT INTO `t_role_pre` VALUES ('12', '1', '12');
INSERT INTO `t_role_pre` VALUES ('18', '1', '13');
INSERT INTO `t_role_pre` VALUES ('19', '3', '5');
INSERT INTO `t_role_pre` VALUES ('28', '2', '5');
INSERT INTO `t_role_pre` VALUES ('29', '2', '7');
INSERT INTO `t_role_pre` VALUES ('30', '2', '6');
INSERT INTO `t_role_pre` VALUES ('31', '5', '9');
INSERT INTO `t_role_pre` VALUES ('32', '5', '12');
INSERT INTO `t_role_pre` VALUES ('33', '5', '10');

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
-- Records of t_seckill
-- ----------------------------
INSERT INTO `t_seckill` VALUES ('1', '1', '100', '100', '2019-07-04 23:50:35', '2019-07-04 23:50:39', '2019-07-04 23:50:43');
INSERT INTO `t_seckill` VALUES ('2', '2', '100', '12', '2019-07-04 17:11:16', '2019-07-04 17:11:16', '2019-07-04 17:11:16');
INSERT INTO `t_seckill` VALUES ('3', '3', '8', '50', '2019-07-10 23:10:19', '2019-07-10 23:10:24', '2019-07-12 23:10:30');
INSERT INTO `t_seckill` VALUES ('4', '4', '5', '99', '2019-07-12 16:22:06', '2019-07-12 17:00:00', '2019-07-12 18:00:00');
INSERT INTO `t_seckill` VALUES ('5', '7', '1000', '499.99', '2019-07-12 18:34:34', '2019-07-12 18:34:34', '2019-07-12 18:34:36');
INSERT INTO `t_seckill` VALUES ('6', '7', '0', '199.99', '2019-07-17 22:52:53', '2019-07-17 22:52:53', '2019-08-01 00:00:00');
INSERT INTO `t_seckill` VALUES ('7', '3', '8', '99', '2019-07-17 23:23:37', '2019-07-17 23:23:37', '2019-07-18 06:00:00');

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
-- Records of t_seckillitem
-- ----------------------------
INSERT INTO `t_seckillitem` VALUES ('1', '1', '2019-07-05 00:50:47', '0');
INSERT INTO `t_seckillitem` VALUES ('2', '2', '2019-07-04 17:16:07', '0');
INSERT INTO `t_seckillitem` VALUES ('3', '3', '2019-07-12 16:19:06', '0');
INSERT INTO `t_seckillitem` VALUES ('6', '3', '2019-07-19 12:47:57', '0');
INSERT INTO `t_seckillitem` VALUES ('7', '3', '2019-07-18 00:10:19', '0');

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
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '18711309775', '5823ca4d73546f7f906b5e9687a379d7', '摆脱', '1049709821@qq.com', '0');
INSERT INTO `t_user` VALUES ('2', 'admin', '928bfd2577490322a6e19b793691467e', '商品管理员', '2316400242@qq.com', '0');
INSERT INTO `t_user` VALUES ('3', 'root', '31f123ba21dc3d21a9b54a7a78f65829', '超级管理员', 'root@qq.com', '0');
INSERT INTO `t_user` VALUES ('18', 'admin2', 'ed718bc83ba6a810d68e939c7b77d862', '订单管理员', 'mahuateng@qq.com', '0');
INSERT INTO `t_user` VALUES ('19', '2312313', '321312312', '马云', 'mayun@qq.com', '0');
INSERT INTO `t_user` VALUES ('20', '1321321', '1312344', '雷军', 'leijun@qq.com', '0');
INSERT INTO `t_user` VALUES ('21', 'pertter', 'dad', '罗永浩', 'luoyonhao@qq.com', '0');
INSERT INTO `t_user` VALUES ('22', '3weqwe', 'fafa', 'jack', 'jack@qq.com', '0');
INSERT INTO `t_user` VALUES ('23', 'adasd', 'adsad', 'marin', 'marin@qq.com', '0');
INSERT INTO `t_user` VALUES ('24', 'dfadf', 'afs', '范善杰', 'fanshanjie@qq.com', '0');
INSERT INTO `t_user` VALUES ('25', 'defaf', 'fafad', 'whilte', 'kaikai@qq.com', '0');
INSERT INTO `t_user` VALUES ('31', 'da', 'dasd', 'dasd', 'dasds', '0');

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
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '3');
INSERT INTO `t_user_role` VALUES ('2', '2', '2');
INSERT INTO `t_user_role` VALUES ('3', '3', '1');
INSERT INTO `t_user_role` VALUES ('5', '3', '31');
INSERT INTO `t_user_role` VALUES ('6', '5', '18');
INSERT INTO `t_user_role` VALUES ('7', '3', '19');
INSERT INTO `t_user_role` VALUES ('8', '3', '20');
INSERT INTO `t_user_role` VALUES ('9', '3', '21');
INSERT INTO `t_user_role` VALUES ('10', '3', '22');
INSERT INTO `t_user_role` VALUES ('11', '3', '23');
INSERT INTO `t_user_role` VALUES ('12', '3', '24');
INSERT INTO `t_user_role` VALUES ('13', '3', '25');

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
