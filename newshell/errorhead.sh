#!/bin/bash
#创建时间
today=`date --date='1 days ago' +%Y%m%d`

#创建日增量数据文件夹
mkdir /etldata/errorhead/${today}/

cd /etldata/headImgData2/headImg/${today}/
echo `find -size 5093c | xargs du -sh`>>/etldata/errorhead/${today}/headerr.csv

sed -i "s/.png//g" /etldata/errorhead/${today}/headerr.csv
sed -i "s#./##g" /etldata/errorhead/${today}/headerr.csv
sed -i "s/8.0K/ /g" /etldata/errorhead/${today}/headerr.csv
