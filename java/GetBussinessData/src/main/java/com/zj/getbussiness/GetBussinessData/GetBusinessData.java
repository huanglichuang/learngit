package com.zj.getbussiness.GetBussinessData;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetBusinessData {

	private static Statement statement;

	private static final String KEY = "QJVBZ-TVNAQ-MHZ5M-GLUJG-VBZDK-5UFF7";

	public static void getLocation(String lng, String lat,String date) {
		String urlString = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + KEY
				+ "&get_poi=1" + "&poi_options=radius=1700";
		String result = "";
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
					GetAddressReference(address_reference,date);
				}
			}
		}
	}
	public static void GetAddressReference(String address_reference,String date) {
		if (JSONObject.fromObject(address_reference)!=null) {
			JSONObject array = JSONObject.fromObject(address_reference);
			if (array.get("business_area") != null) {
				String business_area = array.get("business_area").toString();
				JSONObject jsonObject = JSONObject.fromObject(business_area);
				String title = jsonObject.get("title").toString();
				String object = jsonObject.get("location").toString();
				JSONObject lnglat = JSONObject.fromObject(object);
				String lat = lnglat.get("lat").toString();
				String lng = lnglat.get("lng").toString();
				System.out.println(lng + "," + lat + "," + title + "," + date);
			}
		}
	}

	public static void Device() {
		String device_sql = "select latitude,longitude,year(NOW())*10000 + month(NOW())*100 + day(NOW()) from zj_device where length(latitude)>3";
		try {
			statement = JDBCUtil.getStatement();
			ResultSet device_set = statement.executeQuery(device_sql);
			while (device_set.next()) {
				String latitude = device_set.getString("latitude");
				String longitude = device_set.getString("longitude");
				String date = device_set.getString(2);
				getLocation(longitude, latitude,date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Store() {
		String store_sql = "select latitude,longitude,year(NOW())*10000 + month(NOW())*100 + day(NOW()) from zj_store where length(latitude)>3";
		try {
			statement = JDBCUtil.getStatement();
			ResultSet store_set = statement.executeQuery(store_sql);
			while (store_set.next()) {
				String latitude = store_set.getString("latitude");
				String longitude = store_set.getString("longitude");
				String date = store_set.getString(2);
				getLocation(longitude, latitude,date);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Device();
		Store();
//		getLocation("116.307490", "39.984154");
	}
}