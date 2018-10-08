package com.zj.tagtoredis.TagToRedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import redis.clients.jedis.Jedis;

public class TagToRedis {
	private static Jedis jedis;
	static {
        //连接服务器
        jedis = new Jedis("127.0.0.1", 6379);
        //权限认证
        jedis.auth("123456");
    }
	public static void main (String[] args) {
        String line = null;
        File file_user_phone = new File("/home/hbase/jars_UserPortrait/FirstTrancheTags-2.2.txt");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file_user_phone)));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                int length = split.length;
                String tags_num = split[length - 1];
                jedis.del(tags_num);
                for (int i = 0; i < length - 1; i++) {
                    jedis.rpush(tags_num, split[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
