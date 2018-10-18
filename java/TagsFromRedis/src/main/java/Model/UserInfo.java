package Model;


import TagFromRedis.UserChildInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class UserInfo implements Serializable {
    //用户的unionID
    private String unionID;
    //用户的uid
    private Integer uid;
    //用户的性别
    private Integer sex;
    //用户的状态（是否被封禁）
    private Integer state;
    //用户所在的省份
    private Integer province;
    //用户所在的城市
    private Integer city;
    //用户所在地的邮政编码
    private String areaCode;
    //用户微信头像地址
    private String headImgurl;
    //用户的微信昵称
    private String nickName;
    //用户的openId
    private String openId;
    //用户的领纸数
    private Integer paperCount;
    //用户的领纸数区间
    private String paperCountRange;
    //用户的手机型号
    private String phoneName;
    //用户的手机价格
    private Integer phonePrice;
    //用户的手机价格区间
    private Integer phonePriceRange;
    //用户的手机操作系统版本
    private String osVersion;
    //用户的年龄
    private Integer age;
    //用户年龄区间
    private String ageRange;
    //用户的颜值
    private Double beauty;
    //用户配戴眼镜的置信度
    private Double glass;
    //用户皮肤健康的置信度
    private Double health;
    //用户皮肤色斑的置信度
    private Double stain;
    //用户皮肤青春痘的置信度
    private Double acne;
    //用户有青春痘的置信度
    private Double darkCircle;
    //用户所属种族
    private String ethnicity;
    //用户孩子的个数
    private Integer childNum;
    //用户孩子的年龄，性别，眼镜置信度（字符串，空格分隔）
    private ArrayList<UserChildInfo> child;

    //用户孩子信息(JSON)
    private String childInfo;
    //用户兴趣点提取
    private Map<String, List<Long>> labelNameIdMap;

    //用户信息的更新时间
    private Integer updateTime;

}
