-- 创建用户表
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(100) NOT NULL COMMENT '加密后的密码',
  `salt` varchar(50) NOT NULL COMMENT '加密盐值',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) COMMENT '手机号唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建短信验证码表
CREATE TABLE `sms_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `type` tinyint(4) NOT NULL COMMENT '类型：1-注册，2-忘记密码',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_phone_type` (`phone`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信验证码表';    