package com.zj.getlocation.GetLocation;

import net.sf.json.JSONObject;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetBus {
	private static Table table;

	private static Statement statement;
	private static Put put;
	
	private static int random;

	private static final String[] KEYS = {"NIYBZ-N7KA2-IRBUA-CBH3Q-7V3LH-ZJFBZ","HOGBZ-XNSA2-TOKUV-CKM55-ZGTL6-SKF6G","XHFBZ-DBSRU-3HHVC-BXWWW-O2EOT-R7B2R","QRWBZ-LUQKW-IFARX-OLOL4-JDHUS-E5B7G"};

	public static void getLocation(String tableName, byte[] rowkey, String lng, String lat) {
		random = (int)(Math.random()*4);
		String urlString = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + KEYS[random]
				+ "&get_poi=1" + "&poi_options=radius=1700";
		String result = "";
		table = HbaseUtil.getTable(tableName);
		put = new Put(rowkey);
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			// 腾讯地图使用GET
			conn.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			// 获取地址解析结果
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
		} catch (Exception e) {
			e.getMessage();
		}
		// 转JSON格式
		if (JSONObject.fromObject(result).get("result") !=null ) {
			Object object = JSONObject.fromObject(result).get("result");
			if (JSONObject.fromObject(object)!=null) {
				JSONObject array = JSONObject.fromObject(object);
				if (array.get("address_reference") != null) {
					String address_reference = array.get("address_reference").toString();
					GetAddressReference(table, rowkey, put, address_reference);
				}
			}
		}
	}
	public static void GetAddressReference(Table table, byte[] rowkey, Put put, String address_reference) {
		if (JSONObject.fromObject(address_reference)!=null) {
			JSONObject array = JSONObject.fromObject(address_reference);
			if (array.get("business_area") != null) {
				String business_area = array.get("business_area").toString();
				String s = new String(rowkey);
				JSONObject jsonObject = JSONObject.fromObject(business_area);
				String title = jsonObject.get("title").toString();
				String object = jsonObject.get("location").toString();
				JSONObject lnglat = JSONObject.fromObject(object);
				String lat = lnglat.get("lat").toString();
				String lng = lnglat.get("lng").toString();
				System.out.println(s + "," + lng + "," + lat + "," + title);
				put.addColumn("i".getBytes(), "business_area".getBytes(), title.getBytes());
				try {
					table.put(put);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void Device() {
		String device_sql = "select imei,latitude,longitude from zj_device where length(latitude)>3";
		try {
			statement = JDBCUtil.getStatement();
			ResultSet device_set = statement.executeQuery(device_sql);
			while (device_set.next()) {
				String imei = device_set.getString("imei");
				String latitude = device_set.getString("latitude");
				String longitude = device_set.getString("longitude");
				byte[] rowkey = imei.getBytes();
				getLocation("t_device_info", rowkey, longitude, latitude);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Store() {
		String store_sql = "select id,latitude,longitude from zj_store where length(latitude)>3";
		try {
			statement = JDBCUtil.getStatement();
			ResultSet store_set = statement.executeQuery(store_sql);
			while (store_set.next()) {
				int id = store_set.getInt("id");
				String latitude = store_set.getString("latitude");
				String longitude = store_set.getString("longitude");
				byte[] rowkey = (id+"").getBytes();
				getLocation("t_store_info", rowkey, longitude, latitude);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Device();
		Store();
		HbaseUtil.closeConnection();
//		getLocation("s", "i".getBytes(), "116.307490", "39.984154");
	}
}