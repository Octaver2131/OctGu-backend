-- 创建库
create database if not exists octgu;

-- 切换库
use octgu;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';

create table if not exists item
(
    id             int auto_increment comment '自增主键，唯一标识每条记录'
        primary key,
    itemName       varchar(100)                       not null comment 'item名字（如周边商品名称）',
    itemIp         varchar(200)                       not null comment 'item对应的二次元IP名称（如动漫、游戏、影视作品名）',
    itemCategory   varchar(50)                        not null comment 'item种类（如徽章、手办、立牌等）',
    unitPrice      decimal(10, 2)                     not null comment '单价（保留2位小数）',
    totalPrice     decimal(12, 2)                     not null comment '总价（保留2位小数）',
    purchaseNumber int                                not null comment '购买数量（非负整数）',
    purchaseTime   date                               not null comment '购买时间（仅到日，格式：YYYY-MM-DD）',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    isDelete       tinyint  default 0                 not null comment '是否删除',
    description    varchar(255)                       null,
    check (`purchaseNumber` >= 0)
)
    comment '二次元IP周边商品购买信息表（购买时间精确到日）' engine = InnoDB;