package com.zj.baseinfo.Baseinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class MysqlToHbase {
	private final static String URL = "jdbc:mysql://rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com:3306/dmp";
	private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "Bigdatadb@098";
	
	private Table userTable;
	
	private Connection connection;
	private Statement statement;
	public void getConnection() throws Exception{
		if(connection==null){
			Class.forName(DRIVER_CLASS);
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
	}
	
	public void getStatement() throws Exception{
		if(connection == null){
			getConnection();
		}
		this.statement = connection.createStatement();
	}
	public void getDataFromUser() throws Exception{
		if(userTable==null){
			userTable = HBaseUtils.getTable("t_user_info");
		}
		String rowkey = "base_info";
		Put put = new Put(Bytes.toBytes(rowkey));
		String sql = "SELECT openid from zj_user";
		getStatement();
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			String openid = rs.getString("openid");
			put.addColumn("base_info".getBytes(), "openid".getBytes(), openid.getBytes());
		}
		userTable.put(put);
	}
	public static void main(String[] args) throws Exception{
		HBaseUtils.init();
		MysqlToHbase mysqlToHBase = new MysqlToHbase();
		mysqlToHBase.getDataFromUser();
		HBaseUtils.closeConnection();
	}
}
