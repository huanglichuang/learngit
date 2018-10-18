package Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

/**
 * 数组和List相互转换的工具类
 *
 * @param <T>
 * @author David Yin
 * @date Jun 9, 2017
 */
public class CollectionsUtils<T> {

    /**
     * 将数组数据转换为List数据
     *
     * @param objectArray
     * @return
     */
    public static <T> List<T> convertArrayToList(T[] objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return null;
        }

        List<T> result = Lists.newArrayList();
        for (T objetct : objectArray) {
            result.add(objetct);
        }
        return result;
    }

    /**
     * 将List数据转换为数组数据
     *
     * @param objectList
     * @return
     */
    public static <T> T[] convertListToArray(Class<T> clazz, List<T> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return null;
        }
        // https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/how-to-create-a-generic-array-in-java.md
        T[] result = (T[]) Array.newInstance(clazz, objectList.size());
        objectList.toArray();
        for (int i = 0; i < objectList.size(); i++) {
            result[i] = objectList.get(i);
        }
        return result;
    }

    /**
     * 将数组数据转换为Set数据
     *
     * @param objectArray
     * @return
     */
    public static <T> Set<T> convertArrayToSet(T[] objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return null;
        }

        Set<T> result = Sets.newHashSet();
        for (T objetct : objectArray) {
            result.add(objetct);
        }
        return result;
    }

}
