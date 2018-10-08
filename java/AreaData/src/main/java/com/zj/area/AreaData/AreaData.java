package com.zj.area.AreaData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.zj.area.AreaData.PinYinTool.Type;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class AreaData {
	// key
	private static final String KEY = "QJVBZ-TVNAQ-MHZ5M-GLUJG-VBZDK-5UFF7";

	public static String getLocation(Integer id) {
		String urlString = "";
		if (id != null) {
			urlString = "https://apis.map.qq.com/ws/district/v1/getchildren?" + "&key=" + KEY + "&id=" + id;
		} else {
			urlString = "https://apis.map.qq.com/ws/district/v1/getchildren?" + "&key=" + KEY;
		}
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
		return result;
	}

	public static String getRegion(String keyword) {
		String urlString = "";
		urlString = "https://apis.map.qq.com/ws/district/v1/search?" + "&key=" + KEY + "&keyword=" + keyword;
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
		return result;
	}

	public static void first() {
		String result = getLocation(null);
		JSONArray jsonArray = JSONObject.fromObject(result).getJSONArray("result");
		Object object = jsonArray.get(0);
		String string = object.toString();
		Object[] array = new JSONArray().fromObject(string).toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject().fromObject(array[i]);
			Integer codeNum = jsonObject.getInt("id");
			String province = jsonObject.getString("fullname");
			String pinyins = jsonObject.getString("pinyin");
			String[] infos = pinyins.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "")
					.split(",");
			String pinyin = "";
			for (int j = 0; j < infos.length; j++) {
				pinyin += infos[j];
			}
			pinyin = pinyin.substring(0, 1).toUpperCase() + pinyin.substring(1);
			String firstWord = pinyin.substring(0, 1).toUpperCase();
			System.out.println(0 + "," + codeNum + "," + province + "," + firstWord + "," + pinyin);
			second(codeNum);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void second(Integer code) {
		String result = getLocation(code);
		JSONArray jsonArray = JSONObject.fromObject(result).getJSONArray("result");
		Object object = jsonArray.get(0);
		String string = object.toString();
		Object[] array = new JSONArray().fromObject(string).toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject().fromObject(array[i]);
			Integer codeNum = jsonObject.getInt("id");
			String province = jsonObject.getString("fullname");
			String pinyins = jsonObject.getString("pinyin");
			String[] infos = pinyins.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "")
					.split(",");
			String pinyin = "";
			for (int j = 0; j < infos.length; j++) {
				pinyin += infos[j];
			}
			pinyin = pinyin.substring(0, 1).toUpperCase() + pinyin.substring(1);
			String firstWord = pinyin.substring(0, 1).toUpperCase();
			System.out.println(code + "," + codeNum + "," + province + "," + firstWord + "," + pinyin);
			try {
				thrid(codeNum);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void thrid(Integer code) throws BadHanyuPinyinOutputFormatCombination {
		PinYinTool tool = new PinYinTool();
		String result = getLocation(code);
		JSONArray jsonArray = JSONObject.fromObject(result).getJSONArray("result");
		Object object = jsonArray.get(0);
		String string = object.toString();
		Object[] array = new JSONArray().fromObject(string).toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject().fromObject(array[i]);
			Integer codeNum = jsonObject.getInt("id");
			String region = jsonObject.getString("fullname");
			String pinyin = tool.toPinYin(region,"",Type.FIRSTUPPER);
			String firstWord = pinyin.substring(0, 1);
			System.out.println(code + "," + codeNum + "," + region + "," + firstWord + "," + pinyin);
		}
	}

	public static void main(String[] args) {
//		 first();
		String location = getLocation(null);
		System.out.println(location);
	}
}