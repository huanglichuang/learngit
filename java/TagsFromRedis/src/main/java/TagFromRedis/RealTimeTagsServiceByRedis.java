package TagFromRedis;

import Model.UserInfo;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class RealTimeTagsServiceByRedis {

    private static Jedis jedis = null;


    /***
     * @Description: 返回用户的实时tags接口
     * @Param: [searchInput]
     * @return: com.dmp.bean.UserInfo
     * @Author: HouJun
     * @Date: 2018/10/6
     */
    public static UserInfo getUserTags (String id) {
        UserInfo userInfo = new UserInfo();

        //获取Redis连接
        jedis = RedisConnection.getJedis();

        //根据uid从Redis里获取数据，封装对象，并且返回

        Map<String, String> info = jedis.hgetAll(id);

        //封装userInfo对象（考虑是否为空）

        if (info.containsKey("uid")) userInfo.setUid(Integer.valueOf(info.get("uid")));
        if (info.containsKey("paper_count")) userInfo.setPaperCount(Integer.valueOf(info.get("paper_count")));
        if (info.containsKey("paper_count_range")) userInfo.setPaperCountRange(String.valueOf(Integer.valueOf(info.get("paper_count_range"))));
        if (info.containsKey("phone_type")) userInfo.setPhoneName(String.valueOf(Integer.valueOf(info.get("phone_type"))));
        if (info.containsKey("phone_system")) userInfo.setOsVersion(String.valueOf(Integer.valueOf(info.get("phone_system"))));
        if (info.containsKey("phone_price")) userInfo.setPhonePrice(Integer.valueOf(info.get("phone_price")));
        if (info.containsKey("phone_price_range")) userInfo.setPhonePriceRange(Integer.valueOf(info.get("phone_price_range")));
        if (info.containsKey("province_code")) userInfo.setProvince(Integer.valueOf(info.get("province_code")));
        if (info.containsKey("city_code")) userInfo.setCity(Integer.valueOf(info.get("city_code")));
        if (info.containsKey("sex")) userInfo.setSex(Integer.valueOf(info.get("sex")));
        if (info.containsKey("state")) userInfo.setState(Integer.valueOf(info.get("state")));
        if (info.containsKey("UNIONID")) userInfo.setUnionID(info.get("UNIONID"));
        if (info.containsKey("age")) userInfo.setAge(Integer.valueOf(info.get("age")));
        if (info.containsKey("age_range")) userInfo.setAgeRange(info.get("age_range"));
        if (info.containsKey("beauty")) userInfo.setBeauty(Double.valueOf(info.get("beauty")));
        if (info.containsKey("glass")) userInfo.setGlass(Double.valueOf(info.get("glass")));
        if (info.containsKey("health")) userInfo.setHealth(Double.valueOf(info.get("health")));
        if (info.containsKey("stain")) userInfo.setStain(Double.valueOf(info.get("stain")));
        if (info.containsKey("acne")) userInfo.setAcne(Double.valueOf(info.get("acne")));
        if (info.containsKey("dark_circle")) userInfo.setDarkCircle(Double.valueOf(info.get("dark_circle")));
        if (info.containsKey("ethnicity")) userInfo.setEthnicity(String.valueOf(info.get("ethnicity")));
        if (info.containsKey("child_num")) userInfo.setChildNum(Integer.valueOf(info.get("child_num")));
        if (info.containsKey("child")) userInfo.setChildInfo(String.valueOf(info.get("child")));
        if (info.containsKey("update_time")) userInfo.setUpdateTime(Integer.valueOf(info.get("update_time")));

        return userInfo;
    }
}
