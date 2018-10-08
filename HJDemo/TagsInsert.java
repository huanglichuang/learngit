package com.dmp.other;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TagsInsert {

    private static Connection connection = null;

    private static void init () {

        Configuration conf = HBaseConfiguration.create();
        //如果跑在DMP服务器上需要修改这里!
        //conf.set("hbase.zookeeper.quorum", "JDroid:2181,Slave1:2181,Slave2:2181,Slave3:2181");
        //conf.set("hbase.zookeeper.quorum", "localhost:2181");
        try {
            // 构建一个连接对象,自动加载hbase-site.xml
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------以上是基本的初始化操作---------------------------------
    public static void main (String[] args) {
        init();
        Table table = null;
        ArrayList<Put> puts = new ArrayList<Put>();
        try {
            if (connection != null) {
                table = connection.getTable(TableName.valueOf("t_tags_info"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tagsPath = "/home/hbase/一期tags.dat";
        HashMap tagsAndNum = readTagsFile(tagsPath);
        for (Object key : tagsAndNum.keySet()) {
            Object value = tagsAndNum.get(key);
            // 构造要插入的数据为一个Put类型(一个put对象只能对应一个rowkey)的对象
            Put put = new Put(Bytes.toBytes(value.toString()));//指定行键
            put.addColumn(Bytes.toBytes("interest_point_tags"), Bytes.toBytes(key.toString()), Bytes.toBytes(value.toString()));
            puts.add(put);
            // System.out.println(key);
        }

        try {
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("插入成功！");

        try {
            table.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 功能：Java读取txt文件的内容
     * 步骤：
     * 1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出 readline() 备注：需要考虑的是异常情况
     *
     * @param filePath
     * @return 将这个文件按照每一行切割成数组存放到list中
     */
    private static HashMap readTagsFile (String filePath) {
        HashMap map = new HashMap();
        //标签编号
        List<String> label_num = new ArrayList<String>();
        //标签实体
        List<String> label_content = new ArrayList<String>();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String tags_content = "";
                    String tags_num = lineTxt.split(" ")[lineTxt.split(" ").length - 1];
                    String fields[] = lineTxt.split(" ");
                    for (int i = 1; i < fields.length - 2; i++) {
                        tags_content += fields[i];
                        tags_content += ",";
                    }
                    tags_content += fields[fields.length - 2];
                    System.out.println(tags_content.toString() + " : " + tags_num);
                    map.put(tags_content.toString(), tags_num);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return map;
    }
}
