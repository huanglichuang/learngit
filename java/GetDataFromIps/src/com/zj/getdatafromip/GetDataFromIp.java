package com.zj.getdatafromip;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.URL;

public class GetDataFromIp {

	private final static String URL = "jdbc:mysql://rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com:3306/dmp";
	private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "Bigdatadb@098";
	private Connection connection;
	private Statement statement;

	public static void main(String[] args) throws Exception {
		GetDataFromIp dataFromIp = new GetDataFromIp();
		dataFromIp.getDataFromIp();
//		 String ip = "171.127.150.228";
//		 dataFromIp.insertIp(ip, "中国", "山西", "吕梁", "联通");
		// String address = "";
		// address = dataFromIp.getAddresses("ip="+ip, "utf-8");
		// System.out.println(address);
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

	private String getDataFromIpStore(String ip) throws Exception {
		String[] infos = ip.split("\\.");
		String ipString = infos[0] + "." + infos[1] + "." + infos[2];
		String sql = "select country,province,city,isp from zj_ipstore where ip = '" + ipString + "'";
		getStatement();
		ResultSet rs = statement.executeQuery(sql);
		String resource = "";
		while (rs.next()) {
			String country = rs.getString("country");
			String province = rs.getString("province");
			String city = rs.getString("city");
			String isp = rs.getString("isp");
			resource = ip + "," + country + "," + province + "," + city + "," + isp;
		}
		return resource;
	}

	private void getDataFromIp() throws Exception {
		// String sql = "SELECT ip from logs_dwd where date_add(curdate(),
		// interval -1 day) = DATE(logdate)";
		String sql = "select ip,id from zj_alldata WHERE date_add(curdate(), interval -1 day)=DATE(all_day)";
		getStatement();
		ResultSet rs = statement.executeQuery(sql);
		GetDataFromIp dataFromIp = new GetDataFromIp();
		String address = "";
		while (rs.next()) {
			String ip = rs.getString("ip");
			int id = rs.getInt("id");
			String resources = getDataFromIpStore(ip);
			if (resources.length()>10) {
				System.out.println(id + "," + resources);
			} else {
				while (true) {
					address = dataFromIp.getAddresses("ip=" + ip, "utf-8");
					if (address != null) {
						String datawords = ip + address;
						String[] infos = datawords.replaceAll("\"", "").split(":");
						if (infos.length > 4) {
							String country = (infos[4].split(","))[0];
							String province = (infos[6].split(","))[0];
							String city = (infos[7].split(","))[0];
							String isp = (infos[9].split(","))[0];
							insertIp(ip, country, province, city, isp);
							System.out.println(id + "," + ip + "," + country + "," + province + "," + city + "," + isp);
							break;
						}
					}
				}
			}
		}
	}

	private String getAddresses(String content, String encodingString) throws UnsupportedEncodingException {
		// 这里调用pconline的接口
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		String returnStr = this.getResult(urlStr, content, encodingString);
		return returnStr;
	}

	private String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000); // 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000); // 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true); // 是否打开输出流 true|false
			connection.setDoInput(true); // 是否打开输入流true|false
			connection.setRequestMethod("POST"); // 提交方法POST|GET
			connection.setUseCaches(false); // 是否缓存true|false
			connection.connect(); // 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content); // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush(); // 刷新
			out.close(); // 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
																														// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	private void insertIp(String ip, String country, String province, String city, String isp) throws Exception {
		String[] infos = ip.split("\\.");
		String ipString = infos[0] + "." + infos[1] + "." + infos[2];
		String sql = "replace into zj_ipstore values('" + ipString + "','" + country + "','" + province + "','" + city
				+ "','" + isp + "'," + "year(NOW())*10000 + month(NOW())*100 + (day(NOW()) - 1))";
//		System.out.println(sql);
		getStatement();
		statement.execute(sql);
	}
}
