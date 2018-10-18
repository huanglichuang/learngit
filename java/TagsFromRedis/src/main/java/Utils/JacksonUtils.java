package Utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.google.common.collect.Lists;
import dto.JacksonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jaskson功能的工具类封装
 *
 * @author DAVID YIN
 * @date 2018/09/15
 */
public class JacksonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return fromJsonWithException(json, clazz);
        } catch (Exception e) {
//            log.error("json is: " + json, e);
            return null;
        }
    }

    public static <T> T fromJsonWithException(String json, Class<T> clazz) throws JsonParseException,
            JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T fromJson(String json, Class<T> c, Class... t) {
        try {
            return fromJsonWithException(json, c, t);
        } catch (IOException e) {
            throw new JacksonException(e);
        }
    }

    public static <T> T fromJsonWithException(String json, Class<T> c, Class... t) throws JsonParseException,
            JsonMappingException, IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(c, t);
        return objectMapper.readValue(json, javaType);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> c) {
        try {
            return fromJsonListWithException(json, c);
        } catch (IOException e) {
            throw new JacksonException(e);
        }
    }

    /**
     * 反序列化出错时忽略错误，返回空list
     * 注意：可能导致潜在的错误被隐藏掉
     *
     * @param json
     * @param c
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonListIgnoreException(String json, Class<T> c) {
        try {
            return fromJsonListWithException(json, c);
        } catch (Exception e) {
            // ignore all exception
//            log.warn(e.getMessage() + ":json is[" + json + "]");
            return Lists.newArrayList();
        }
    }

    public static <T> List<T> fromJsonListWithException(String json, Class<T> c) throws IOException {
        JavaType type = getCollectionType(ArrayList.class, c);
        return (List<T>) objectMapper.readValue(json, type);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static String toJsonWithException(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static String toJson(Object o) {
        try {
            return toJsonWithException(o);
        } catch (Exception e) {
            throw new JacksonException(e);
        }
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // map = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
            map = new ObjectMapper().readValue(json, HashMap.class);
        } catch (Exception e) {
//            log.error("json to map failed", e);
        }
        return map;
    }

    public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException {
        // String json = "{\"list\":[{\"c\":10}]}";
        // String json = "{}";
        // B<C> b = (B<C>)JacksonUtils.fromJson(json, B.class, C.class);
        // System.out.println(b);

        C c = new C();
        c.setB("a\\a\\n");
        System.out.println(c.getB());
        String json = JacksonUtils.toJson(c);
        System.out.println(json);
    }
}

class B<T> {
    protected List<T> list = new ArrayList<T>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class C {
    private int a = 10;
    private String b = "100";

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
