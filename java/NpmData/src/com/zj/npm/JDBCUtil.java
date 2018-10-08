package com.zj.npm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCUtil {
    private final static String URL = "jdbc:mysql://rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com:3306/dmp";
    private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Bigdatadb@098";

    private static Connection connection;

    public static Connection getConnection() throws Exception{
        if(connection==null){
            Class.forName(DRIVER_CLASS);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }
    public static Statement getStatement() throws Exception{
        if(connection == null){
            connection = getConnection();
        }
        return connection.createStatement();
    }
}