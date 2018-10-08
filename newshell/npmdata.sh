#!/bin/bash
#创建时间
today=`date --date='1 days ago' +%Y%m%d`
#创建日增量数据文件夹
#mkdir /etldata/logscsv/${today}/
#mkdir /etldata/logs/${today}/

#mv /home/hlc/data/logs/* /etldata/logs/${today}/

awk -F "\"" '{
if($2~/npm=/){
if($6~/iPhone/){
split($1,array1,"[")
split($2,array2,"npm=")
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
split($2,array2,"npm=")
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
split($2,array2,"npm=")
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
}' /etldata/logs/pData/*>/etldata/logscsv/npmdata.csv

mysql --local-infile -h rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com -uroot -pBigdatadb@098 dmp -e "load data local infile '/etldata/logscsv/npmdata.csv' into table npm_data fields terminated by '\\t' lines terminated by '\n'"
