#!/bin/bash

day=`date -d -8days '+%Y%m%d'`
7za a -r /etldata/tables/$day.7z /etldata/tables/$day/*
7za a -r /etldata/logs/$day.7z /etldata/logs/$day/*
7za a -r /etldata/logscsv/$day.7z /etldata/logscsv/$day/*
7za a -r /etldata/headcsv/$day.7z /etldata/headcsv/$day/*
7za a -r /etldata/errorhead/$day.7z /etldata/errorhead/$day/*
7za a -r /etldata/isp/$day.7z /etldata/isp/$day/*
7za a -r /etldata/areaFromPhone/$day.7z /etldata/areaFromPhone/$day/*
if [ $? -eq 0 ];then
rm -rf /etldata/tables/$day/
rm -rf /etldata/logs/$day/
rm -rf /etldata/logscsv/$day/
rm -rf /etldata/headcsv/$day/
rm -rf /etldata/errorhead/$day/
rm -rf /etldata/isp/$day/
rm -rf /etldata/areaFromPhone/$day/
else
echo "failed"
fi
