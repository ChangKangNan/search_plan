select version();

create table search_config
(
    id                         bigint auto_increment comment '主键' primary key,
    config_name                varchar(50)  not null comment '配置名称',
    page_tag                   varchar(80)  not null comment '页面标签',
    search_sql                 mediumtext   null comment '自定义搜索SQL',
    use_org_limit_table_alias  varchar(255) null comment '组织权限限制',
    use_orgs_limit_table_alias varchar(255) null comment '组织权限限制',
    has_code                   varchar(255) null comment 'hasCode',
    remark                     varchar(255) null comment '备注',
    create_time                datetime     not null comment '创建时间',
    update_time                datetime     null comment '更新时间',
    deleted                    tinyint(1)   not null comment '是否删除'
)
    comment '搜索定义表';


create table search_config_info
(
    id                      bigint auto_increment comment '主键' primary key,
    search_config_id        bigint               not null comment '搜索配置主键',
    page_tag                varchar(80)          not null comment '页面标签',
    search_name             varchar(50)          not null comment '搜索显示名',
    search_key              varchar(1024)        not null comment '搜索字段名',
    limit_search_conditions varchar(1000)        not null comment '限定搜索条件',
    limit_search_values     varchar(1000)        null comment '限定搜索值',
    search_value_source     varchar(20)          not null comment '搜索值数据来源（in：输入，dictionary：数据字典，values：限制输入值，url：链接）',
    search_type             varchar(20)          not null comment '搜索条件类型',
    sort_num                int(10)              null comment '条件排序',
    hidden                  tinyint(1) default 0 null comment '是否隐藏',
    change_tb               tinyint(1) default 0 null comment '是否影响表名',
    change_date_rule        varchar(100)         null comment '日期格式化规则',
    change_tb_name          varchar(255)         null comment '替换的表名',
    axis                    varchar(255)         null comment '坐标',
    point                   varchar(100)         null comment '触发点',
    default_order_by        tinyint(1)           null comment '默认排序列',
    default_order_by_desc   tinyint(1)           null comment '是否默认倒序',
    create_time             datetime             not null comment '创建时间',
    update_time             datetime             null comment '更新时间',
    deleted                 tinyint(1)           not null comment '是否删除'
)
    comment '搜索配置设置表';


create table search_config_inner_condition
(
    id                      bigint auto_increment comment '主键' primary key,
    search_config_id        bigint        not null comment '搜索配置主键',
    page_tag                varchar(80)   not null comment '页面标签',
    search_name             varchar(50)   not null comment '搜索显示名',
    search_key              varchar(1024) not null comment '搜索字段名',
    limit_search_conditions varchar(1000) not null comment '限定搜索条件',
    limit_search_values     varchar(1000) null comment '限定搜索值',
    search_value_source     varchar(20)   not null comment '搜索值数据来源（in：输入，dictionary：数据字典，values：限制输入值，url：链接）',
    search_type             varchar(20)   not null comment '搜索条件类型 输入框(input 下拉框(select) 数值输入框(number 日期框(date) 日期框特别标识(今天:#{day},本周:#{week},上周:#{last_week},本月:#{month},上月:#{last_month})',
    axis                    varchar(255)  null comment '内部坐标',
    relation                varchar(50)   not null comment '内部嵌入关系(and or)',
    default_value           varchar(255)  null comment '默认搜索值',
    create_time             datetime      not null comment '创建时间',
    update_time             datetime      null comment '更新时间',
    deleted                 tinyint(1)    not null comment '是否删除'
)
    comment '搜索方案内部条件配置表';

create table search_plan_new
(
    id            bigint auto_increment comment '主键' primary key,
    user_account  varchar(50)          null comment '用户账号',
    plan_name     varchar(50)          not null comment '方案名称',
    page_tag      varchar(100)         not null comment '页面标签',
    custom_marker varchar(255)         null comment '自定义标记',
    search_sql    mediumtext           null comment '自定义搜索SQL',
    sort_num      int(10)              null comment '条件排序',
    def           tinyint(1) default 0 null comment '是否为默认方案',
    create_org    bigint               null comment '创建者所在组织',
    create_time   datetime             null comment '创建时间',
    update_time   datetime             null comment '更新时间',
    deleted       tinyint(1) default 0 null comment '是否删除'
)
    comment '用户搜索方案表';

create table search_plan_new_condition
(
    id                               bigint auto_increment comment '主键' primary key,
    user_account                     varchar(50)          null comment '用户账号',
    page_tag                         varchar(100)         not null comment '页面标签',
    search_config_info_id            bigint               null comment '查询列ID',
    search_config_inner_condition_id bigint               null comment '内部条件配置表ID',
    search_plan_id                   bigint               not null comment '用户方案表主键',
    search_column                    text                 not null comment '搜索配置列名',
    search_condition                 varchar(50)          not null comment '搜索条件',
    search_group                     varchar(55)          not null comment '查询条件分组',
    search_group_relation            varchar(55)          not null comment '查询条件分组方式(and or)',
    parent_group                     varchar(55)          null comment '查询父条件分组',
    parent_group_relation            varchar(55)          null comment '查询父条件分组方式(and or)',
    search_value                     varchar(255)         null comment '搜索参数',
    are_inner                        int        default 0 null comment '是否是内部条件(0:不是 ,1:是)',
    create_time                      datetime             null comment '创建时间',
    update_time                      datetime             null comment '更新时间',
    deleted                          tinyint(1) default 0 null comment '是否删除'
)
    comment '用户搜索方案配置表';

create table search_plan_new_show
(
    id             bigint auto_increment comment '主键' primary key,
    user_account   varchar(50)          null comment '用户账号',
    page_tag       varchar(100)         not null comment '页面标签',
    search_plan_id bigint               not null comment '用户方案表主键',
    search_column  varchar(55)          not null comment '搜索配置列名',
    show_status    varchar(50)          not null comment '{on:显示列,close:不显示的列,hidden:需要却不显示的列}',
    create_time    datetime             null comment '创建时间',
    update_time    datetime             null comment '更新时间',
    deleted        tinyint(1) default 0 null comment '是否删除'
)
    comment '用户搜索方案配置表';

create table search_datasource
(
    id                  bigint auto_increment comment '主键' primary key,
    datasource_name     varchar(50)  not null comment '数据源名称',
    datasource_driver   varchar(255) not null comment '数据源驱动',
    datasource_url      varchar(255) not null comment '数据源url',
    datasource_username varchar(255) not null comment '用户名',
    datasource_password varchar(255) not null comment '密码',
    create_time         datetime     not null comment '创建时间',
    update_time         datetime     null comment '更新时间',
    deleted             tinyint(1)   not null comment '是否删除'
)
    comment '查询方案数据源表';

create table search_datasource_relation
(
    id                   bigint auto_increment comment '主键' primary key,
    search_datasource_id bigint     null comment '数据源ID',
    search_config_id  bigint     null comment '查询方案ID',
    create_time          datetime   not null comment '创建时间',
    update_time          datetime   null comment '更新时间',
    deleted              tinyint(1) not null comment '是否删除'
)
    comment '查询方案数据源关系表';

create table search_extend_table
(
    id                   bigint auto_increment comment '主键' primary key,
    search_table_main_id bigint               null comment '关联关系表头ID',
    extend_table_name    varchar(100)         null comment '扩展表名称',
    extend_sql           mediumtext           null comment '表sql',
    remark               varchar(255)         null comment '备注',
    create_by            varchar(255)         null comment '创建人',
    update_by            varchar(255)         null comment '更新人',
    create_time          datetime             null comment '创建时间',
    update_time          datetime             null comment '更新时间',
    deleted              tinyint(1) default 0 null comment '是否删除'
)
    comment '扩展表';

create table search_table_column
(
    id                       bigint auto_increment comment '主键' primary key,
    page_tag                 varchar(100)         not null comment '页面标签',
    search_table_column      varchar(255)         null comment '列名',
    search_table_column_info longtext             null comment '列名详情',
    remark                   varchar(255)         null comment '备注',
    create_by                varchar(255)         null comment '创建人',
    update_by                varchar(255)         null comment '更新人',
    create_time              datetime             null comment '创建时间',
    update_time              datetime             null comment '更新时间',
    deleted                  tinyint(1) default 0 null comment '是否删除'
)
    comment '列名表';

create table search_table_column_target
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_column_id        bigint               null comment '列ID',
    search_table_main_relation_id bigint               null comment '关系表主键',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
)
    comment '指向表';

create table search_table_group
(
    id          bigint auto_increment comment '主键' primary key,
    page_tag    varchar(1000)        not null comment '页面标签',
    table_group varchar(1000)        null comment '条件',
    remark      varchar(255)         null comment '备注',
    create_by   varchar(255)         null comment '创建人',
    update_by   varchar(255)         null comment '更新人',
    create_time datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '是否删除'
);

create table search_table_group_relation
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_group_id         bigint               null comment '条件主键',
    search_table_main_relation_id bigint               null comment '关系表主键',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
);

create table search_table_having
(
    id                 bigint auto_increment comment '主键' primary key,
    page_tag           varchar(100)         not null comment '页面标签',
    relation           varchar(55)          null comment '(AND,OR)',
    table_having_where varchar(100)         null comment '条件',
    remark             varchar(255)         null comment '备注',
    create_by          varchar(255)         null comment '创建人',
    update_by          varchar(255)         null comment '更新人',
    create_time        datetime             null comment '创建时间',
    update_time        datetime             null comment '更新时间',
    deleted            tinyint(1) default 0 null comment '是否删除'
);

create table search_table_having_relation
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_having_id        bigint               null comment '条件主键',
    search_table_main_relation_id bigint               null comment '关系表主键',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
);

create table search_table_main
(
    id                bigint auto_increment comment '主键' primary key,
    search_table_name varchar(1024)        null comment '主表名称',
    alias             varchar(100)         null comment '主表别名',
    is_extend         tinyint(1) default 0 null comment '是否为扩展表',
    page_tag          varchar(100)         not null comment '页面标签',
    remark            varchar(255)         null comment '备注',
    create_by         varchar(255)         null comment '创建人',
    update_by         varchar(255)         null comment '更新人',
    create_time       datetime             null comment '创建时间',
    update_time       datetime             null comment '更新时间',
    deleted           tinyint(1) default 0 null comment '是否删除'
)
    comment '关联关系表-表头';

create table search_table_main_relation
(
    id                       bigint auto_increment comment '主键' primary key,
    search_table_main_id     bigint               null comment '主表外键',
    source_table_name        varchar(100)         null comment '来源表名',
    source_table_alias       varchar(100)         null comment '来源表别名',
    target_table_name        varchar(100)         null comment '目标表名',
    target_table_alias       varchar(100)         null comment '目标表别名',
    source_table_column_name varchar(100)         null comment '来源表名关联列名',
    target_table_column_name varchar(100)         null comment '目标表名关联列名',
    is_extend                tinyint(1) default 0 null comment '目标表是否是扩展表',
    link_relation            varchar(55)          null comment '连接关系',
    remark                   varchar(255)         null comment '备注',
    create_by                varchar(255)         null comment '创建人',
    update_by                varchar(255)         null comment '更新人',
    create_time              datetime             null comment '创建时间',
    update_time              datetime             null comment '更新时间',
    deleted                  tinyint(1) default 0 null comment '是否删除'
)
    comment '关联关系表-表体';

create table search_table_main_relation_link
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_main_relation_id bigint               null comment '表头ID',
    table_column_name             varchar(100)         null comment '当前关系所用字段',
    target_table_name             varchar(100)         null comment '目标表名',
    target_table_alias            varchar(100)         null comment '目标表别名',
    target_table_column_name      varchar(100)         null comment '目标表名关联列名',
    parent_relation_id            bigint               null comment '父级关系',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
)
    comment '关联关系表-表体-连接关系表';

create table search_table_main_relation_where
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_main_relation_id bigint               null comment '表头ID',
    target_table_name             varchar(100)         null comment '目标表名',
    target_table_alias            varchar(100)         null comment '目标表别名',
    where_info                    varchar(255)         null comment '过滤条件',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
)
    comment '关联关系表-表体-过滤条件表';

create table search_table_where
(
    id          bigint auto_increment comment '主键' primary key,
    page_tag    varchar(100)         not null comment '页面标签',
    relation    varchar(55)          null comment '(AND,OR)',
    table_where varchar(255)         null comment '条件',
    remark      varchar(255)         null comment '备注',
    create_by   varchar(255)         null comment '创建人',
    update_by   varchar(255)         null comment '更新人',
    create_time datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '是否删除'
)
    comment '初始条件表';

create table search_table_where_relation
(
    id                            bigint auto_increment comment '主键' primary key,
    search_table_where_id         bigint               null comment '条件主键',
    search_table_main_relation_id bigint               null comment '关系表主键',
    remark                        varchar(255)         null comment '备注',
    create_by                     varchar(255)         null comment '创建人',
    update_by                     varchar(255)         null comment '更新人',
    create_time                   datetime             null comment '创建时间',
    update_time                   datetime             null comment '更新时间',
    deleted                       tinyint(1) default 0 null comment '是否删除'
)
    comment '初始条件表-关联表';

