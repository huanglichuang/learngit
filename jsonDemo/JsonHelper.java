package YTOP.MLO.API.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 1:将JavaBean转换成Map、JSONObject
 * 2:将Map转换成Javabean
 * 3:将JSONObject转换成Map、Javabean
 * 
 * @author Alexia
 */

public class JsonHelper {

	private static Logger log = Logger.getLogger(JsonHelper.class);

	/**
	 * 将Javabean转换为Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	public static Map<String, String> toMap(Object javaBean) {

		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {

			try {

				if (method.getName().startsWith("get")) {

					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	/**
	 * 将Json对象转换成Map
	 * 
	 * @param jsonString
	 *            json对象
	 * @return Map对象
	 * @throws JSONException
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String> toMap(String jsonString) {

		// String js= "";
		// try {
		// js = new String (jsonString.getBytes(),"utf-8");
		// } catch (UnsupportedEncodingException e) {
		// log.error("JSON字符串转换出错！", e);
		// return null;
		// }
		// JSONObject jsonObject = new JSONObject(js);

		JSONObject jsonObject = new JSONObject(jsonString);

		Map<String, String> result = new HashMap<String, String>();
		Iterator<?> iterator = jsonObject.keys();
		String key = null;
		String value = null;

		while (iterator.hasNext()) {

			key = (String) iterator.next();
			value = jsonObject.get(key).toString();
			result.put(key, value);

		}
		return result;

	}

	/**
	 * 将JavaBean转换成JSONObject（通过Map中转）
	 * 
	 * @param bean
	 *            javaBean
	 * @return json对象
	 */
	public static JSONObject toJSON(Object bean) {

		return new JSONObject(toMap(bean));

	}

	/**
	 * 将Map转换成Javabean
	 * 
	 * @param javabean
	 *            javaBean
	 * @param data
	 *            Map数据
	 */
	public static Object toJavaBean(Object javabean, Map<?, ?> data) {

		Method[] methods = javabean.getClass().getDeclaredMethods();
		for (Method method : methods) {

			try {
				if (method.getName().startsWith("set")) {

					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] {

					data.get(field)

					});

				}
			} catch (Exception e) {
			}

		}

		return javabean;

	}

	/**
	 * JSONObject到JavaBean
	 * 
	 * @param javabean
	 *            javaBean
	 * @return json对象
	 * @throws Exception 
	 * @throws ParseException
	 *             json解析异常
	 * @throws JSONException
	 * @throws UnsupportedEncodingException 
	 */
	public static void toJavaBean(Object javabean, String jsonString)
			throws Exception {

		JSONObject jsonObject = new JSONObject(jsonString);

		Map<?, ?> map = toMap(jsonObject.toString());

		toJavaBean(javabean, map);

	}

	/**
	 * 
	 * 业 务: 将Json数据以Json格式写到指定路径 <br>
	 * 英文名: writeToJson 功能号: TODO 时 间: 2016年12月6日 下午7:32:11 应用项目:
	 * 中依睿晟新增订单APP后台管理系统 作者: 严昊 <br>
	 *
	 * 入参：TODO TODO TODO TODO TODO TODO TODO
	 * 
	 * 
	 * 出参：JSON
	 * @throws IOException 
	 */
	public static void writeToJson(String rootPath, String fileName,
			JSONObject json) throws IOException {
		File rootFile = new File(rootPath);
		rootFile.mkdirs();
		File file = new File(rootPath + fileName);
		String string = json.toString();
		Writer write = new FileWriterWithEncoding(file, "utf-8");
		write.write(string);
		write.flush();
		write.close();
	}

	public static void writeToJson(String rootPath, String fileName,
			JSONArray jsonarray) throws IOException {
		File rootFile = new File(rootPath);
		rootFile.mkdirs();
		File file = new File(rootPath + fileName);
		String string = jsonarray.toString();
		Writer write = new FileWriterWithEncoding(file, "utf-8");
		write.write(string);
		write.flush();
		write.close();
	}

}