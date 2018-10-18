package TagFromRedis;

import lombok.Data;

/**
 * @program: dmp
 * @description: 用户的孩子信息
 * @author: HouJun
 * @create: 2018-10-09 20:19
 **/

@Data
public class UserChildInfo {

    //孩子的年龄
    private Integer childAge;
    //孩子的性别
    private Integer childSex;
    //孩子是否佩戴眼镜的置信度
    private Double childGlass;

}
