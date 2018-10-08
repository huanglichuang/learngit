#!/bin/bash
#创建时间
today=`date --date='1 days ago' +%Y%m%d`
#创建日增量数据文件夹
mkdir /etldata/logscsv/${today}/
mkdir /etldata/logs/${today}/

mv /home/hlc/data/logs/* /etldata/logs/${today}/

awk -F "\"" '{
if($2~/excavate/ && $6~/NetType/){
if($6~/iPhone/){
split($1,array1,"[")
split($2,array2,"uid=")
split($6,array3,"(")
split($6,array4,"NetType/")
split(array1[1],arr1," ")
split(array1[2],arr2," ")
split(array2[2],arr3," ")
split(array3[2],arr4," ")
split(array4[2],arr5," ")
split($6,array5,"MicroMessenger/")
split(array5[1],arr6,"AppleWebKit/")
split(array5[2],arr7," ")
print(arr1[1]"\t"arr2[1]"\t"arr3[1]"\t"arr4[4]arr4[5]"\t"arr4[3]"\t"arr5[1]"\t"arr6[2]"\t"arr7[1])
}
else if($6~/iPad/){
split($1,array1,"[")
split($2,array2,"uid=")
split($6,array3,"(")
split($6,array4,"NetType/")
split(array1[1],arr1," ")
split(array1[2],arr2," ")
split(array2[2],arr3," ")
split(array3[2],arr4," ")
split(array4[2],arr5," ")
split($6,array5,"MicroMessenger/")
split(array5[1],arr6,"AppleWebKit/")
split(array5[2],arr7," ")
split(array3[2],arr8,";")
print(arr1[1]"\t"arr2[1]"\t"arr3[1]"\t"arr4[3]arr4[4]"\t"arr8[1]"\t"arr5[1]"\t"arr6[2]"\t"arr7[1])
}
else{
split($1,array1,"[")
split($2,array2,"uid=")
split($6,array3,"(")
split($6,array4,"NetType/")
split(array1[1],arr1," ")
split(array1[2],arr2," ")
split(array2[2],arr3," ")
split(array3[2],arr4,";")
split(array4[2],arr5," ")
split($6,array5,"MicroMessenger/")
split(array5[1],arr6,"AppleWebKit/")
split(array5[2],arr7," ")
if(arr4[2]~/u/ || arr4[2]~/U/){
print(arr1[1]"\t"arr2[1]"\t"arr3[1]"\t"arr4[3]"\t"arr4[5]"\t"arr5[1]"\t"arr6[2]"\t"arr7[1])
}else{
print(arr1[1]"\t"arr2[1]"\t"arr3[1]"\t"arr4[2]"\t"arr4[3]"\t"arr5[1]"\t"arr6[2]"\t"arr7[1])}
}
}
}' /etldata/logs/${today}/*>/etldata/logscsv/${today}/logstable.csv

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/logscsv/${today}/logstable.csv' into table logs_data fields terminated by '\\t' lines terminated by '\n'"

mysql -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "\. /home/hlc/sql/logs_dwd.sql"

mysql -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "\. /home/hlc/sql/zj_alldata.sql"

#mysql -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "delete from logs_data where date_add(curdate(), interval -7 day) = DATE((CASE WHEN date LIKE '%Jan%' THEN CONCAT(SUBSTR(REPLACE(date,'Jan','01'),7,4),
#       SUBSTR(REPLACE(date,'Jan','01'),4,2),SUBSTR(REPLACE(date,'Jan','01'),1,2))
#WHEN date LIKE '%Feb%' THEN CONCAT(SUBSTR(REPLACE(date,'Feb','02'),7,4),
#       SUBSTR(REPLACE(date,'Feb','02'),4,2),SUBSTR(REPLACE(date,'Feb','02'),1,2))
#WHEN date LIKE '%Mar%' THEN CONCAT(SUBSTR(REPLACE(date,'Mar','03'),7,4),
#       SUBSTR(REPLACE(date,'Mar','03'),4,2),SUBSTR(REPLACE(date,'Mar','03'),1,2))
#WHEN date LIKE '%Apr%' THEN CONCAT(SUBSTR(REPLACE(date,'Apr','04'),7,4),
#       SUBSTR(REPLACE(date,'Apr','04'),4,2),SUBSTR(REPLACE(date,'Apr','04'),1,2))
#WHEN date LIKE '%May%' THEN CONCAT(SUBSTR(REPLACE(date,'May','05'),7,4),
#       SUBSTR(REPLACE(date,'May','05'),4,2),SUBSTR(REPLACE(date,'May','05'),1,2))
#WHEN date LIKE '%Jun%' THEN CONCAT(SUBSTR(REPLACE(date,'Jun','06'),7,4),
#       SUBSTR(REPLACE(date,'Jun','06'),4,2),SUBSTR(REPLACE(date,'Jun','06'),1,2))
#WHEN date LIKE '%Jul%' THEN CONCAT(SUBSTR(REPLACE(date,'Jul','07'),7,4),
#       SUBSTR(REPLACE(date,'Jul','07'),4,2),SUBSTR(REPLACE(date,'Jul','07'),1,2))
#WHEN date LIKE '%Aug%' THEN CONCAT(SUBSTR(REPLACE(date,'Aug','08'),7,4),
#       SUBSTR(REPLACE(date,'Aug','08'),4,2),SUBSTR(REPLACE(date,'Aug','08'),1,2))
#WHEN date LIKE '%Sep%' THEN CONCAT(SUBSTR(REPLACE(date,'Sep','09'),7,4),
#       SUBSTR(REPLACE(date,'Sep','09'),4,2),SUBSTR(REPLACE(date,'Sep','09'),1,2))
#WHEN date LIKE '%Oct%' THEN CONCAT(SUBSTR(REPLACE(date,'Oct','10'),7,4),
#       SUBSTR(REPLACE(date,'Oct','10'),4,2),SUBSTR(REPLACE(date,'Oct','10'),1,2))
#WHEN date LIKE '%Nov%' THEN CONCAT(SUBSTR(REPLACE(date,'Nov','11'),7,4),
#       SUBSTR(REPLACE(date,'Nov','11'),4,2),SUBSTR(REPLACE(date,'Nov','11'),1,2))
#WHEN date LIKE '%Dec%' THEN CONCAT(SUBSTR(REPLACE(date,'Dec','12'),7,4),
#       SUBSTR(REPLACE(date,'Dec','12'),4,2),SUBSTR(REPLACE(date,'Dec','12'),1,2))
# END))"

