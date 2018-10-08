package com.zj.base64.Base64;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {
	public static void main(String[] args) {
		String key = "123456";
		byte[] bt = key.getBytes();
		String newkey = (new BASE64Encoder()).encodeBuffer(bt);
		System.out.println(newkey);
		try {
			byte[] decodeBuffer = (new BASE64Decoder()).decodeBuffer(newkey);
			String string = new String(decodeBuffer);
			System.out.println(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
