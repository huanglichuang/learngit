package com.dmp.other;

import com.dmp.utils.CTUtils;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HeadImgCrawl {
    public static void main (String[] args) {

        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String mysqlUrl = "jdbc:mysql://rm-bp1u81ft5yi82i842.mysql.rds.aliyuncs.com:3306/dmp";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "Bigdatadb@098";
        String lastCT = null;

        //读取最后一个UID
        CTUtils CTUtils = new CTUtils();
        lastCT = CTUtils.get();

        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(mysqlUrl, user, password);
            if (! con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //SQL语句
            String sql_getMaxTime = "select MAX(create_time) as max_time from zj_user";
            String max_time = null;
            //ResultSet类，用来存放获取的结果集
            ResultSet maxCreateTime = statement.executeQuery(sql_getMaxTime);
            while (maxCreateTime.next()) {
                max_time = maxCreateTime.getString("max_time");
            }

            String sql_info = "select uid,create_time,headimgurl from zj_user where create_time > " + lastCT + " and create_time <= " + max_time;
            ResultSet rs_info = statement.executeQuery(sql_info);
            String uid = null;
            String headimgurl = null;
            String newHeadimgurl = null;
            int headimgurl_len = 0;
            while (rs_info.next()) {
                //获取stuname这列数据
                uid = rs_info.getString("uid");
                //获取stuid这列数据
                headimgurl = rs_info.getString("headimgurl");
                headimgurl_len = headimgurl.length();
                if (headimgurl_len != 0) {
                    newHeadimgurl = headimgurl.substring(0, headimgurl_len - 3);
                    //输出结果
                    System.out.println(uid + "\t" + newHeadimgurl);
                    //下载头像
                    downloadPicture(uid, newHeadimgurl + "0");
                }
            }
            rs_info.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //链接url下载图片
    private static void downloadPicture (String uid, String newHeadimgurl) {
        URL url = null;

        try {
            url = new URL(newHeadimgurl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName = "/etldata/headImgData2/headImg/" + uid + ".png";

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            System.out.println("头像爬取成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
