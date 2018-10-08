package com.zj.getdatafromphonenum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetDataFromPhoneNum {
	private final static String URL = "jdbc:mysql://rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com:3306/dmp";
	private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "Bigdatadb@098";
	private Connection connection;
	private Statement statement;

	public static void main(String[] args) {
		GetDataFromPhoneNum getDataFromPhoneNum = new GetDataFromPhoneNum();
		try {
			getDataFromPhoneNum.getphone();
			// String location = getDataFromPhoneNum.getLocation("11111111111");
			// System.out.println(location);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getConnection() throws Exception {
		if (connection == null) {
			Class.forName(DRIVER_CLASS);
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
	}

	private void getStatement() throws Exception {
		if (connection == null) {
			getConnection();
		}
		this.statement = connection.createStatement();
	}

	private void getphone() throws Exception {
		String sql = "select uid,phone from zj_user where length(phone) = 11";
		getStatement();
		ResultSet rs = statement.executeQuery(sql);
		String resource = "";
		while (rs.next()) {
			int uid = rs.getInt("uid");
			String phone = rs.getString("phone");
			String json = getLocation(phone);
			String[] infos = json.replaceAll("\"", "").split(":");
			if (!(infos[2].split("}"))[0].equals("null")) {
				String province = (infos[6].split(","))[0];
				String city = (infos[5].split("}"))[0];
				String isp = (infos[8].split("}"))[0];
				resource = uid + "," + phone + "," + province + "," + city + "," + isp;
				System.out.println(resource);
			}
		}
	}

	private String getLocation(String tel) {
		String url = "http://mobsec-dianhua.baidu.com/dianhua_api/open/location?tel=" + tel;
		String json = loadJSON(url);
		return json;
	}

	private String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "utf-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}
}
