package com.zj.baseinfo.Baseinfo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

public class HBaseUtils {
	private static Configuration configuration = HBaseConfiguration.create();
	private static Connection connection;
	private static Table table;
	static {
		try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			System.out.println("连接HBase失败");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = ConnectionFactory.createConnection(configuration);
			} catch (IOException e) {
				System.out.println("连接HBase失败");
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Table getTable(String tableName) {
		if (table == null) {
			try {
				table = getConnection().getTable(TableName.valueOf(tableName));
			} catch (IOException e) {
				System.out.println("获取Table失败");
				e.printStackTrace();
				return null;
			}
		}
		return table;
	}

	public static void init() {

		Configuration conf = HBaseConfiguration.create();
		// 如果跑在DMP服务器上需要修改这里!
		// conf.set("hbase.zookeeper.quorum",
		// "JDroid:2181,Slave1:2181,Slave2:2181,Slave3:2181");
		conf.set("hbase.zookeeper.quorum", "localhost:2181");
		try {
			// 构建一个连接对象,自动加载hbase-site.xml
			connection = ConnectionFactory.createConnection(conf);
			System.out.println("!!!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
