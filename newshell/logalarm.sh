#!/bin/bash
tail -10 /home/flume/apache-flume-1.8.0-bin/logs/flume.log | grep "Avro source s1: Received avro event batch of">/home/hlc/data/dev/null

if [ $? -eq 1 ]
then
java -jar /home/hlc/jar/Msg.jar ":日志数据异常"
mv /home/hlc/jar/Msg.jar /home/hlc/jar/Msg/
fi
