-- 用户表
CREATE TABLE IF NOT EXISTS `user`
(
    `id`              INT          NOT NULL AUTO_INCREMENT,
    `username`        VARCHAR(45)  NOT NULL COMMENT '用户名',
    `name`            VARCHAR(45)  COMMENT '用户姓名',
    `gender`          BOOLEAN      COMMENT '性别: 1: 男, 0: 女',
    `birthday`        DATE         COMMENT '生日',
    `openid`          VARCHAR(100) NOT NULL COMMENT '用户的唯一标识',
    `create_at`       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `avatar`          VARCHAR(255) COMMENT '用户头像',
    `doctor_id`       INT          COMMENT '关联的医生ID',
    `height`          INT          COMMENT '身高',
    `weight`          INT          COMMENT '体重',
    `medical_history` VARCHAR(255) COMMENT '病史',
    `allergy_history` VARCHAR(255) COMMENT '过敏史',
    PRIMARY KEY (`id`)
);

-- 吃药日记表
CREATE TABLE IF NOT EXISTS `medicine_diary`
(
    `id`        INT       NOT NULL AUTO_INCREMENT,
    `user_id`   INT       NOT NULL COMMENT '关联用户ID',
    `create_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日记生成时间',
    `content`   TEXT      NOT NULL COMMENT '日记内容',
    PRIMARY KEY (`id`)
);

-- 看病日记表
CREATE TABLE IF NOT EXISTS `consult_diary`
(
    `id`        INT       NOT NULL AUTO_INCREMENT,
    `user_id`   INT       NOT NULL COMMENT '关联用户ID',
    `create_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日记生成时间',
    `content`   TEXT      NOT NULL COMMENT '日记内容',
    PRIMARY KEY (`id`)
);

-- 健康数据表
CREATE TABLE IF NOT EXISTS `health_data`
(
    `id`                 INT       NOT NULL AUTO_INCREMENT,
    `user_id`            INT       NOT NULL COMMENT '关联用户ID',
    `create_at`          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `exercise_duration`  INT       NOT NULL COMMENT '运动时长(分钟)',
    `heart_rate`         INT       NOT NULL COMMENT '心率(次/分)',
    `sleep_duration`     INT       NOT NULL COMMENT '睡眠时长(小时)',
    `systolic_pressure`  INT       NOT NULL COMMENT '收缩压(mmHg)',
    `diastolic_pressure` INT       NOT NULL COMMENT '舒张压(mmHg)',
    PRIMARY KEY (`id`)
);

-- 文章表
CREATE TABLE IF NOT EXISTS `article`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `time`       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `title`      VARCHAR(255) NOT NULL COMMENT '文章标题',
    `content`    TEXT         NOT NULL COMMENT '文章内容',
    `author`     VARCHAR(45)  NOT NULL COMMENT '作者',
    `picture_url` VARCHAR(255) COMMENT '文章配图URL',
    PRIMARY KEY (`id`)
);

-- 医生表
CREATE TABLE IF NOT EXISTS `doctor`
(
    `id`                  INT          NOT NULL AUTO_INCREMENT,
    `user_id`             INT          NOT NULL COMMENT '关联用户ID',
    `name`                VARCHAR(45)  NOT NULL COMMENT '医生姓名',
    `major`               VARCHAR(45)  COMMENT '专业',
    `job`                 VARCHAR(45)  COMMENT '职称',
    `certificate_pic_url` VARCHAR(255) COMMENT '资格证图片URL',
    `photo_url`           VARCHAR(255) COMMENT '医生照片URL',
    `valid`               BOOLEAN      COMMENT '是否有效',
    PRIMARY KEY (`id`)
);

-- 医生患者表
CREATE TABLE IF NOT EXISTS `doctor_patient`
(
    `id`         INT NOT NULL AUTO_INCREMENT,
    `doctor_id`  INT NOT NULL COMMENT '关联医生ID',
    `patient_id` INT NOT NULL COMMENT '关联患者的用户ID',
    PRIMARY KEY (`id`)
);

-- 问题表
CREATE TABLE IF NOT EXISTS `question`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `user_id`   INT          NOT NULL COMMENT '关联用户ID',
    `create_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提问时间',
    `question`  VARCHAR(255) NOT NULL COMMENT '问题内容',
    PRIMARY KEY (`id`)
);

-- 回答表
CREATE TABLE IF NOT EXISTS `answer`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `question_id` INT          NOT NULL COMMENT '关联问题ID',
    `user_id`     INT          NOT NULL COMMENT '关联用户ID',
    `create_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回答时间',
    `answer`      VARCHAR(255) NOT NULL COMMENT '回答内容',
    PRIMARY KEY (`id`)
);

-- 管理员表
CREATE TABLE IF NOT EXISTS `admin`
(
    `id`       INT          NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45)  NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
);

-- 问诊记录表
CREATE TABLE IF NOT EXISTS `chat_message` (
    `id`        BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `sender`    INT          NOT NULL,
    `receiver`  INT          NOT NULL,
    `content`   TEXT         NOT NULL,
    `timestamp` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);