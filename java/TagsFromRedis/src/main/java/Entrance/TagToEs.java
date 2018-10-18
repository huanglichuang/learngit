package Entrance;

import EsMethod.EsIndexService;
import Model.UserInfo;
import base.EsConfig;
import base.EsFactory;
import lombok.Data;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;

@Data
public class TagToEs {
    private EsFactory esFactory = new EsFactory();
    private EsConfig esConfig = new EsConfig();
	public static void main(String[] args) {
	    TagToEs tagToEs = new TagToEs();
		tagToEs.TagsToEs("7018466");
	}
	public  void TagsToEs(String id){
//		UserInfo userInfo = RealTimeTagsServiceByRedis.getUserTags(id);
//        System.out.println(userInfo);
//		String unionID = userInfo.getUnionID();
//		Integer uid = userInfo.getUid();
//		Integer sex = userInfo.getSex();
//		Integer state = userInfo.getState();
//		Integer province = userInfo.getProvince();
//		Integer city = userInfo.getCity();
//		String areaCode = userInfo.getAreaCode();
//		String headImgurl = userInfo.getHeadImgurl();
//		String nickName = userInfo.getNickName();
//		String openId = userInfo.getOpenId();
//		Integer paperCount = userInfo.getPaperCount();
//		String paperCountRange = userInfo.getPaperCountRange();
//		String phoneName = userInfo.getPhoneName();
//		Integer phonePrice = userInfo.getPhonePrice();
//		Integer phonePriceRange = userInfo.getPhonePriceRange();
//		String osVersion = userInfo.getOsVersion();
//		Integer age = userInfo.getAge();
//		String ageRange = userInfo.getAgeRange();
//		Double beauty = userInfo.getBeauty();
//		Double glass = userInfo.getGlass();
//		Double health = userInfo.getHealth();
//		Double stain = userInfo.getStain();
//		Double acne = userInfo.getAcne();
//		Double darkCircle = userInfo.getDarkCircle();
//		String ethnicity = userInfo.getEthnicity();
//		Integer childNum = userInfo.getChildNum();
//		ArrayList<UserChildInfo> child = userInfo.getChild();
//		String childInfo = userInfo.getChildInfo();
//        UserTagsEsDataServiceImpl service = new UserTagsEsDataServiceImpl();
        UserInfo userTags = new UserInfo();
        userTags.setUid(999999999);
        userTags.setPhoneName("vvvvvvvvvvvv");
        System.out.println(userTags.getUid());
        System.out.println(userTags.getPhoneName());
        RestHighLevelClient rhlClient = esFactory.getRestHighLevelClient();
        String adIndexName = esConfig.getAdIndexName();
        String adTypeName = esConfig.getAdTypeName();
        EsIndexService service = new EsIndexService();
        IndexResponse index = service.index(rhlClient, adIndexName, adTypeName, userTags);
        System.out.println(index);
        System.exit(0);
    }
}
