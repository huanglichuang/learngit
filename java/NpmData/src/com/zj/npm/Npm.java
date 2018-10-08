package com.zj.npm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

public class Npm {
	private static StringBuilder stringBuilder;
	public static void main(String[] args) {
		fromMysql();
//		String recode = "--DfqyJk-XkHj_VZuZrVLk6Qzd7URq5GE2QvhGr";
//		getImei(recode);
	}
	public static void fromMysql(){
		String sql = "select recode from npm_dwd group by recode";
		try {
			Statement statement = JDBCUtil.getStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String recode = resultSet.getString("recode");
				getImei(recode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getImei(String recode){
		stringBuilder = new StringBuilder();
		String urlStr = "http://testapi.chuzhijin.com/new_v1_0.device/decode?npm=" + recode;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
			reader.close();
			String imei = stringBuilder.toString().substring(0,15);
			System.out.println(imei + "," + recode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
