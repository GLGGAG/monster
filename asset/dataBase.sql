
drop table if exists mon_user;

drop table if exists mon_daily;

drop table if exists mon_team;

drop table if exists mon_position;

/*==============================================================*/
/* Table:用户表                                                 */
/*==============================================================*/
create table mon_user
(
  id                   int not null AUTO_INCREMENT comment '主键',
  user_name            varchar(20) NOT NULL comment         '用户姓名',
  user_no              INT  NOT NULL comment        '用户编号',
  phone                varchar(12)  NOT NULL comment        '用户手机号',
  pass_word            varchar(30) NOT NULL comment         '用户密码',
  team_no              INT NOT NULL comment         '用户归属的项目组编号',
  user_grade_type       VARCHAR(20) NOT NULL COMMENT '用户等级类型',

  create_time           DATETIME  NOT NULL DEFAULT current_timestamp comment '创建时间',
  update_time           DATETIME  NOT NULL DEFAULT  current_timestamp comment '修改时间',
  version              int UNSIGNED DEFAULT 0 comment '版本号',
  deleted              TINYINT DEFAULT FALSE comment '是否删除',
  primary key (id),
  KEY (user_no),
  KEY (phone)
);
/*==============================================================*/
/* Table:用户日报表                                             */
/*==============================================================*/
create table mon_daily
(
  id                    int not null AUTO_INCREMENT comment '主键',
  user_no               INT  NOT NULL comment '用户编号',
  today_content         TEXT NOT NULL comment          '当天日报内容',
  incomplete_cause      TEXT NOT NULL comment          '未完成原因',
  morrow_plan           TEXT NOT NULL comment           '次日工作计划',
  risk_point            TEXT NOT NULL comment           '风险点和对策',
  remark                TEXT NOT NULL comment           '备注',


  create_time           DATETIME  NOT NULL DEFAULT current_timestamp comment '创建时间',
  update_time           DATETIME  NOT NULL DEFAULT  current_timestamp comment '修改时间',
  version              int UNSIGNED DEFAULT 0 comment '版本号',
  deleted              TINYINT DEFAULT FALSE comment '是否删除',
  primary key (id),
  KEY (user_no)
);

/*==============================================================*/
/* Table:项目组设置                                             */
/*==============================================================*/
CREATE TABLE mon_team
(
  id                   int not null AUTO_INCREMENT comment  '主键',
  user_no              INT  NOT NULL comment         '用户编号',
  team_no              INT  NOT NULL COMMENT         '项目组编号',
  team_name            VARCHAR(20) NOT NULL comment          '项目组名称',

  create_time           DATETIME  NOT NULL DEFAULT current_timestamp comment '创建时间',
  update_time           DATETIME  NOT NULL DEFAULT  current_timestamp comment '修改时间',
  version               int UNSIGNED DEFAULT 0 comment '版本号',
  deleted               TINYINT DEFAULT FALSE comment '是否删除',
  primary key (id)
);














