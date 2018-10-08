#!/bin/bash
#创建时间
today=`date --date='1 days ago' +%Y%m%d`

#创建日增量数据文件夹
mkdir /etldata/headcsv/${today}/
#爬取头像数据
java -jar /etldata/headImgData2/jars/Crawl.jar
java -jar /etldata/headImgData2/jars/Mv.jar

#获取有问题的头像用户id
sh -x /home/hlc/shell/errorhead.sh>/home/hlc/shell/shlog/errorhead.log

#second download
java -jar /etldata/headImgData2/jars/SecondDownload.jar

#将获取的头像文件MD5
function getdir(){
    for element in `ls $1`
    do
        dir_or_file=$1"/"$element
        if [ -d $dir_or_file ]
        then
            getdir $dir_or_file
        else
          echo `md5sum $dir_or_file`>>/etldata/headcsv/${today}/headmd5.csv
        fi
    done
}
root_dir="/etldata/headImgData2/headImg/${today}"
getdir $root_dir
#拆分MD5的csv文件
awk -F " " '{
split($2,array1,"/")
split(array1[6],array2,".")
print(array2[1]"\t"$1"\t"array1[5])
}' /etldata/headcsv/${today}/headmd5.csv>/etldata/headcsv/${today}/headtable.csv

#导入到数据库
mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/headcsv/${today}/headtable.csv' into table zj_user_head fields terminated by '\\t' lines terminated by '\n'"
