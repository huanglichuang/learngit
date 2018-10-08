#!/bin/bash

diraaa='20180914'

#将获取的头像文件MD5
function getdir(){
    for element in `ls $1`
    do
        dir_or_file=$1"/"$element
        if [ -d $dir_or_file ]
        then
            getdir $dir_or_file
        else
          echo `md5sum $dir_or_file`>>/etldata/headcsv/${diraaa}headmd5.csv
        fi
    done
}
root_dir="/etldata/headImgData2/headImg/${diraaa}"
getdir $root_dir
#拆分MD5的csv文件
awk -F " " '{
split($2,array1,"/")
split(array1[6],array2,".")
print(array2[1]"\t"$1"\t"array1[5])
}' /etldata/headcsv/${diraaa}headmd5.csv>/etldata/headcsv/${diraaa}headtable.csv

#导入到数据库
mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/headcsv/${diraaa}headtable.csv' into table zj_user_head fields terminated by '\\t' lines terminated by '\n'"
