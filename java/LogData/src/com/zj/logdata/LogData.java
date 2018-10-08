package com.zj.logdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LogData {
	public static Map<Integer, String> map = new HashMap<>();
	public static int live;
	public static int dead;

	public static void main(String[] args) {
		getTestData();
		int sum = live + dead;
		double rate = live/dead;
		System.out.println("文档总条数：" + sum + "		匹配数据：" + live + "	不匹配数据：" + dead + "	匹配数据占比：" + rate);
	}
	public static void getNpmData(String time,String province) {
		String sql = "SELECT c.imei,count(1)"
				+ " FROM npm_dwd a"
				+ " LEFT JOIN zj_ipstore b"
				+ " ON SUBSTR(a.ip,1,11) = b.ip"
				+ " OR SUBSTR(a.ip,1,10) = b.ip"
				+ " OR SUBSTR(a.ip,1,9) = b.ip"
				+ " OR SUBSTR(a.ip,1,8) = b.ip"
				+ " LEFT JOIN imei_recode c"
				+ " ON a.recode = c.recode"
				+ " WHERE b.isp = '电信'"
				+ " AND b.province = '"+province+"'"
				+ " AND UNIX_TIMESTAMP(a.logtime) - UNIX_TIMESTAMP('"+time+"') BETWEEN -1 AND 1";
		try {
			Statement statement = JDBCUtil.getStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				live += 1;
				while (resultSet.next()) {
					String imei = resultSet.getString(1);
					int count = resultSet.getInt(2);
					if (count == 1) {
						System.out.println(imei);
					}
				}
			}else {
				dead += 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getTestData() {
		File file = new File("/home/hlc/data/日志匹配对比.txt");
		String line = null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while ((line = reader.readLine()) != null) {
				String[] infos = line.split(",");
				String time = infos[0];
				String province = infos[1];
				getNpmData(time,province);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
