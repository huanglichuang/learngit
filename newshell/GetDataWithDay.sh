#!/bin/bash

#创建时间
today=`date --date='1 days ago' +%Y%m%d`

#创建日增量数据文件夹
mkdir /etldata/tables/${today}/
mkdir /etldata/isp/${today}/
mkdir /etldata/areaFromPhone/${today}/

#获取日增表数据生成.csv文件
#事实表
mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_authorizer_subscribe where date_add(curdate(), interval -1 day) = DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_authorizer_subscribe.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_order where date_add(curdate(), interval -1 day) = DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_order.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_device_opaper_log where date_add(curdate(), interval -1 day) = DATE(FROM_UNIXTIME(add_time))">/etldata/tables/${today}/zj_device_opaper_log.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_duoble_subscribe where date_add(curdate(), interval -1 day) = DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_duoble_subscribe.csv
#维度表
mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_double_wechat where date_add(curdate(), interval -1 day) >= DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_double_wechat.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_user where date_add(curdate(), interval -1 day) >= DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_user.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_wechat_authorizer where date_add(curdate(), interval -1 day) >= DATE(FROM_UNIXTIME(create_time))">/etldata/tables/${today}/zj_wechat_authorizer.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_device">/etldata/tables/${today}/zj_device.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_store">/etldata/tables/${today}/zj_store.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_area_city">/etldata/tables/${today}/zj_area_city.csv

mysql -h rm-bp1in0w8t4d80pm5t.mysql.rds.aliyuncs.com -umaxcompute -pk598V44B zhijin_db -e "select * from zj_agent">/etldata/tables/${today}/zj_agent.csv

#将获取到的订单表数据优先加载到本地数据库

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_order.csv' into table zj_order fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_authorizer_subscribe.csv' into table zj_authorizer_subscribe fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_device_opaper_log.csv' into table zj_device_opaper_log fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_duoble_subscribe.csv' into table zj_duoble_subscribe fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"
#更新表数据
mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_user"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_user.csv' into table zj_user fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_wechat_authorizer"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_wechat_authorizer.csv' into table zj_wechat_authorizer fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_device"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_device.csv' into table zj_device fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_store"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_store.csv' into table zj_store fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_area_city"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_area_city.csv' into table zj_area_city fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table zj_agent"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_agent.csv' into table zj_agent fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/tables/${today}/zj_double_wechat.csv' into table zj_double_wechat fields terminated by '\\t' lines terminated by '\n' ignore 1 lines"

#根据手机号获取省市区运营商信息
java -jar /home/hlc/jar/GetDataFromPhone.jar > /etldata/areaFromPhone/${today}/GetDataFromPhone.csv

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "truncate table user_phone_area"

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/areaFromPhone/${today}/GetDataFromPhone.csv' into table user_phone_area fields terminated by ',' lines terminated by '\n'"

#加载dmp_user数据
mysql -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "\. /home/hlc/sql/zj_dmp_user.sql"

#头像md5操作
sh -x /home/hlc/shell/MD5HEAD.sh>/home/hlc/shell/shlog/d.log

#生成大表数据
sh -x /home/hlc/shell/logs.sh>/home/hlc/shell/shlog/error.log

#爬取运营商信息
java -jar /home/hlc/jar/GetDataFromIsp.jar>/etldata/isp/${today}/isp.csv

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/isp/${today}/isp.csv' into table zj_ipsdata fields terminated by ',' lines terminated by '\n'"

#生成带有运营商的大表数据

mysql -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "\. /home/hlc/sql/zj_alldata.sql"
