# 零配置SSM+VUE前后端分离网上商城（已实现秒杀）
[前端代码地址](https://github.com/fanshanchao/shop-vue)
## 项目介绍
前后端分离的网上商城。实现了普通商城拥有的主要功能，**并且在2019.07.19添加了秒杀功能，并且对秒杀做了相应的优化**。并且利用shiro实现了后台权限管理，可以修改用户权限，修改用户角色。并且只有非商城会有的权限才可以进入后台管理页面。整个项目使用rest风格来获取数据。前后端通过json数据来进行交互。[项目演示地址](http://120.76.56.254:8084/#/)
## 项目代码结构
* --bean  项目所到的javabean
* --core  本来想把核心代码写在里面的，但好像用不到...
* --dao  访问数据库的代码
* --myweb spring，spring mvc，shiro的核心代码和整个项目的配置都在这里面
* --service 服务层代码
* --task 本来想把定时任务的代码写在里面，但是项目没用到定时任务

## 使用教程
### 后端
1. 运行/master/myweb/src/main/sql下的shop.sql文件，在你自己的数据库生成想对应的库和表。
2. 将本项目项目导入到你自己的idea或elipse中
2. 修改SSM-VUE-SHOP/myweb/src/main/resources路径下的javamail.properties为你自己所开启的邮箱stmp服务的账号和密码。（需要自己先去开始一个smtp服务，具体教程可百度）。
3. 修改/SSM-VUE-SHOP/blob/master/myweb/src/main/resources/dataSource.properties中mysql和redis的密码为你自己主机上的账号和密码。
4. 直接运行项目
5. 再到[前端代码地址](https://github.com/fanshanchao/shop-vue)按教程部署运行，然后就可以成功访问项目了。
### 注意点
1. 由于es安装麻烦，所以搜索功能被移除了。如果想使用这个可以自己去安装es，我再给你说下教程，这里嫌麻烦就没写了。

## 项目所用技术
### 后端
1. **SSM**：并且使用配置类无xml（mybatis还是用了xml）。
2. **shiro**：shiro完成了登陆验证以及用户权限管理部分。
3. **elasticsearch**：简单的使用了elasticsearch完成了搜索商品功能，但是权限安全方面还没有配置（xpath貌似要钱加上麻烦），无需登陆就可以访问修改里面的数据。所以各位大佬放过它。
4. **mysql**
5. **redis**：shiro的会话缓存和权限缓存用到了redis，以及注册时的邮箱验证码也暂存在了redis中，**秒杀优化也用到了redis**
### 前端
1. **vue全集桶**
2. **饿了吗开发的element框架**



## 项目所有模块简介

### 登陆注册模块（主要是shiro的问题）
#### 登陆
1. 先解决跨域问题，使用的是后端允许所有跨域请求，前端不用做任何配置。
2. 后端使用shiro保存登陆信息，后端设置登陆信息保存时间为30分钟，超时后需要重新登陆，并且登陆的信息缓存在redis中，配置好了shiro从redis中获取用户登陆信息缓存。shiro使用redis做缓存是使用了一个shiro-redis的[开源插件](https://github.com/alexxiyang/shiro-redis),使用这个插件只需要在shiro中配置redis的相关配置，至于访问redis那些操作就不用你做了。
3. 登陆成功后后端将返回一个sessionId给前端，前端每次访问api都在请求头携带这个sessionId，后端每次通过shiro来验证这个sessionId是否登陆。这里要重写shiro的会话管理器，因为shiro默认是从cookie中获取sessionId，需要改写成从请求头中获取这个sessionId。
4. 解决登陆后的另一个跨域问题：由于跨域请求会发送跨域请求，而跨域请求是复杂请求，会在请求先发送一个position请求然后再发送实际的post，get请求，而前面这次的position请求并不带shiro的Authorization字段（也就是保存了sessionId的请求头），所以会导致认证失败，所以即使你登陆了也访问不要需要登陆才可以访问的接口。**解决方法**:重写shiro的认证过滤器，只要是position请求都允许它通过。
5. 登出也需要解决上面的跨域问题，所以也要重写shiro的登出过滤器。
6. 由于shiro只能拦截指定url而不能细分到请求，所以需要重写shiro的工厂bean，然后使用自定义的url匹配规则，例如orders==get就代表了对order的get请求 从而实现rest风格匹配。具体重写方式可以看[这篇博客](https://segmentfault.com/a/1190000014545172)。
7. 虽然页面上有下次自动登陆和忘记密码功能，但是这两个功能是没有实现的。下次自动登陆感觉不是很有必要，也不是很安全就没有实现。忘记密码就纯粹是懒得写了...
#### 注册
1. 注册是使用邮件注册的，邮箱验证码通过javamail发送的。这里要注意部署到linux javamail后不能使用25端口，要改成465端口。
2. 验证码暂存在redis中，并且设置验证码2分钟后在redis中失效。
3. 密码是利用shiro工具加密后再存在shiro中的，存的是加密后的数据不是明文。
4. 登陆和注册的数据校验都只在前端实现了，这里还需要改进。一般来说都是前端和后端都需要进行数据校验。

### 后台管理模块
后台管理又分为**用户管理**，**商品管理**，**订单管理**三个模块。由于后台管理涉及到权限问题，所以后台管理最重要的就是权限管理这块。我这个项目是前后端都做了权限管理，前端根据后端返回的权限信息动态的生成路由和按钮。后端使用shiro做了权限管理，权限管理用的5张mysql表如下：
![Image text](https://github.com/fanshanchao/images/blob/master/shopImages/Snipaste_2019-05-23_20-24-17.png)
上面这5张表可以细分权限到按钮，例如t_permission(权限表)中存的usr:list 表示的就是查询所有用户信息的权限。

#### 用户管理
用户管理使用shiro加上面5张表做的，超级管理员用户全部的权限，并且可以改变用户的角色，改变角色的权限。改变角色和权限也是通过改变上面5张表实现的。
#### 商品管理
商品管理细分了各各权限例如一个管理员拥有goods:update权限就是拥有更新商品的权限。商品管理用到了下面几张表：
![Image text](https://github.com/fanshanchao/images/blob/master/shopImages/%E5%95%86%E5%93%81%E7%AE%A1%E7%90%86.png)
商品的图片存在用nginx搭建的一个图片服务器
#### 订单管理
订单管理权限部分和商品管理类似，必须要拥有相应的权限才能对订单做相应的操作。订单管理用到的下面记住muysql表：
![Image text](https://github.com/fanshanchao/images/blob/master/shopImages/%E8%AE%A2%E5%8D%95%E7%AE%A1%E7%90%86.png)
#### 评价管理
这个模板本来想写的，后来觉得太浪费时间，感觉就是一堆curd就没有写了。
### 秒杀功能（2019/07/19添加）
具有商品添加权限的管理员可以在后台管理页面创建秒杀。商城会员在秒杀开启之后可以进行抢购。

秒杀所用的mysql表：
![Image text](https://github.com/fanshanchao/images/blob/master/shopImages/%E7%A7%92%E6%9D%80mysql%E8%A1%A8.png)

秒杀优化使用的存储过程创建代码：
```
  CREATE  PROCEDURE `exec_seckill`(IN seckillId INT,IN userId INT,IN createTime DATETIME,OUT result INT,
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
```

#### 秒杀流程
![](https://github.com/fanshanchao/images/blob/master/shopImages/%E7%A7%92%E6%9D%80%E6%B5%81%E7%A8%8B.png)

#### 秒杀做的相应优化

1. 使用redis缓存秒杀地址
    - 存储到redis中的Seckill对象要自定义序列化来存储，例如protostuff工具
2. 前端到时暴露接口，按钮防重复点击秒杀

3. 事务竞争优化，减少事务锁时间，将多条mysql执行的事务放到存储过程中去。
    - 不要过度使用存储过程
4. 将插入明细放在事务的前面，可以优化一些重复秒杀，插入操作可以避免行级锁。

5. 将秒杀信息存入到redis中，执行秒杀操作从redis中获取秒杀信息，避免从mysql中去获取


#### 还可以使用的优化方案
![](https://github.com/fanshanchao/images/blob/master/shopImages/%E9%AB%98%E5%B9%B6%E5%8F%91%E4%BC%98%E5%8C%96%E6%9E%B6%E6%9E%84.png)


### 前台管理模块
前台管理分为**购物车管理**和**订单管理**，个人中心嫌麻烦也没有写了。
#### 购物车管理
购物车简单的用前端的vuex状态管理实现的，没有用到mysql表。所以购物车里面的商品仅保存在当前请求或者刷新页面之前。刷新或离开网页购物车里面的数据就没有了。
#### 订单管理
订单管理用到的就是前面订单的那几种表，只不过前台的订单不能处理订单，只能根据当前订单的状态做相应的事。
### 支付模块
支付简单的模拟了下，没有输入密码等验证操作，这个模块其实值得好好写一写的，以后要是还有时间就再研究下这个模块吧。
### 搜索商品模块
搜索功能用elasticseearch做的，学elasticsearch快一周，好在最后成功的用上了。elasticsearch里面的数据和mysql商品表里面的是一样的，每次向mysql添加商品修改商品也会发送请求到elasticsearch中去添加或修改商品（这里是通过使用elasticsearch的java api完成的）。[我的elasticsearch安装和使用笔记](https://segmentfault.com/n/1330000019063224)

## 部署过程
经过了上次部署，这次部署还是非常顺利的，大概花了一下午就搞好了。前端vue打包放在了nginx里，nginx也用作了图片服务器保存商品的图片，elasticsearch做了搜索功能，后端代码直接打包war放在tomcat的webapp目录下。
## 最后
写完发现也不是很难，但是也花了我一个半月的时间。这次写这个项目收获还是蛮大的，真正的体会到了前后端分离，不过项目中还有很多地方不符合前后端分离的规范。接口名称设计的就还有些问题。通过这个项目对shiro也有了很清晰的认识，其实很多功能都可以通过改写shiro的拦截器来完成。这个项目还有很多需要完善的地方，比如评价管理，物流管理，支付模块......elasticsearch也只是大概的学了下，还有很多地方都没有弄清楚，有时间再系统的学习一下。

项目还有很多细节地方都没有写出来，也还有很多不足和bug。有问题可以加我Q交流**1049709821**一起探讨,也可以在github上提交问题。



