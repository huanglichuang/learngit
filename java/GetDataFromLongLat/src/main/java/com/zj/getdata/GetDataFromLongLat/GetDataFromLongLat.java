package com.zj.getdata.GetDataFromLongLat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetDataFromLongLat {
	private static final String KEY = "QJVBZ-TVNAQ-MHZ5M-GLUJG-VBZDK-5UFF7";
	public static String getLocation(String lng, String lat) {
		String urlString = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key=" + KEY
				+ "&get_poi=1";
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
		Object object = JSONObject.fromObject(result).get("result");
		JSONArray array = JSONArray.fromObject(object);
		System.out.println(array);
		return array.toString();
	}

	public static void main(String[] args) {
		String lng = "110.345886";// 经度
		String lat = "20.066392";// 维度
		String location = getLocation(lng, lat);
//		System.out.println(location);
	}
}
