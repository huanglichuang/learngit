package com.zj.getlocation.GetLocation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HbaseUtil {
    private static Configuration configuration = HBaseConfiguration.create();
    private static Connection connection;
    static {
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            System.out.println("连接HBase失败");
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        if(connection == null){
            try {
                return ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                System.out.println("连接HBase失败");
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Table getTable(String tableName){
        try {
            return getConnection().getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            System.out.println("获取表失败");
            e.printStackTrace();
            return null;
        }
    }
}