

# 数据库版本说明

此文件是对数据库设计版本的重要内容的说明，具体数据库调整内容，可以参见数据库设计文件内：表变更记录

## V1.3 更新至2021-02-19

系统版本V1.2.1适用的数据库，完全转为Mysql模式。

1. 所有表的字段去掉F_， 然后命名模式改为Mysql规范样式。
2. 所有预留字段取消掉。
3. 去掉数据库函数，临时用不到，且改为mysql后，不再适用。
4. 表名修改为mysql规范的模式。



##  V1.2 更新至2020-10-28

系统版本1.2.0的数据库，在1.1.0相关的版本上进行整改，字段简化以及优化。

1. 为了可以快速适用多种数据库类型，调整字段命名格式：字段名全部改为 camnel 命名格式，表命名未变。
2. 缩短字段长度，修改人： f_lastModifyUserId->f_modifyBy, f_lastModifyTime->f_modifyTime, f_createUserId->f_createBy
3. 数据库调整为Mysql类型。
4. 去掉原设计中部分表的预留字段信息。
5. 修改部份表的逻辑删除为物理删除，同时去除大部分的是否有效字段。