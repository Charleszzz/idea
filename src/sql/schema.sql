--数据库初始化脚本

--创建数据库
CREATE DATABASE scekill;

--使用数据库
USE seckill;

--创建秒杀库存表
CREATE TABLE seckill(
  seckill_id bigint not null auto_increment comment '秒杀库存表ID',
  name VARCHAR(255) not null comment '商品名称',
  number int not null comment '库存数量',
  start_time TIMESTAMP not null comment '秒杀开始时间',
  end_time TIMESTAMP not null comment '秒杀结束时间',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 comment'秒杀库存表';

--初始化数据
INSERT into
  seckill(name,number,start_time,end_time)
values
  ('100元秒杀iPhone 6s',1000,'2016-1-1 00:00:00','2016-1-2 00:00:00'),
  ('200元秒杀iPhone ipad',1000,'2016-3-1 00:00:00','2016-3-2 00:00:00'),
  ('100元秒杀iPhone 6s',1000,'2016-5-19 00:00:00','2016-6-19 00:00:00'),
  ('100元秒杀iPhone 6s',1000,'2016-8-1 00:00:00','2016-9-2 00:00:00')


--秒杀成功明细表
--用户登录认证相关信息
CREATE TABLE success_killed(
  seckill_id BIGINT NOT NULL COMMENT '秒杀库存id',
  user_phone BIGINT NOT NULL COMMENT '用户手机号',
  state TINYINT NOT NULL DEFAULT -1 COMMENT '状态展示:-1表示无效,0表示成功,1表示已付款',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';