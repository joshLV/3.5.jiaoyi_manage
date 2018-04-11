package cn.jugame.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * JSON类型转换工具
 * @author apxer@qq.com
 * 
 */
public class JSONTookit {
	private static String CLASSNAME; // 类名
	private static List<String> FIELDNAMES; // 泛型字段List
	private static Map<String, Object> FIELDTYPES; // 反省字段类型List
	private static Class<?> CLAZZ = null;

	/**
	 * @param object
	 *            Object
	 * @return JSONObject
	 */
	public static JSONObject getJSONObject(Object object) {
		return init(object);
	}

	/**
	 * 设置泛型的确切类型
	 * 
	 * @param <T>
	 *            泛型
	 * @param x
	 *            泛型
	 */
	private static <T> void f(T x) {
		CLASSNAME = x.getClass().getName();
	}

	/**
	 * 获取泛型具体类型
	 * 
	 * @return 类名
	 */
	private static String getClassName() {
		return CLASSNAME;
	}

	/**
	 * 初始化参数,并判断泛型类型
	 * 
	 * @param object
	 *            泛型类型
	 * @return JSONObject
	 */
	private static JSONObject init(Object object) {
		FIELDNAMES = new ArrayList<String>();
		FIELDTYPES = new HashMap<String, Object>();
		boolean switchWhich = false;
		try {
			if (object == java.util.ArrayList.class) {
				f(((List<?>) object).get(0));
			} else {
				f(object);
			}
			CLAZZ = Class.forName(getClassName());
			switchWhich = (CLAZZ == java.util.ArrayList.class);
			Field[] fields = CLAZZ.getDeclaredFields();
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName())) {
					continue;
				}
				FIELDNAMES.add(field.getName());
				FIELDTYPES.put(field.getName(), field.getType());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (switchWhich) {
			return generateJSONObject((List<?>) object);
		} else {
			return generateJSONObject(object);
		}
	}

	/**
	 * @param object
	 *            Object
	 * @return JSONObject
	 */
	private static JSONObject generateJSONObject(Object object) {
		Map<String, String[]> stautsField = new HashMap<String, String[]>();
		JSONObject data = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (String field : FIELDNAMES) {
				Method getValue = CLAZZ.getMethod("get" + StringUtils.capitalize(field));
				if (FIELDTYPES.get(field) == java.util.Date.class) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put(field, sdf.format(getValue.invoke(object)));
				} else if (FIELDTYPES.get(field) == int.class) {
					if (stautsField.containsKey(field)) {// 如果需要转换状态码到字符串
						String[] statusMapping = stautsField.get(field);
						Map<String, String> mappingCode = setStatusCode2String(statusMapping[0], statusMapping[1]);
						map.put(field, mappingCode.get(getValue.invoke(object) + "")); // +""关键
					} else {
						map.put(field, getValue.invoke(object));
					}
				} else {
					map.put(field, getValue.invoke(object));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.putAll(map);
		return data;
	}

	/**
	 * 根据泛型类型生成JSONList
	 * 
	 * @param list
	 *            列表
	 * @return JSONObject
	 */
	private static JSONObject generateJSONObject(List<?> list) {
		Map<String, String[]> stautsField = new HashMap<String, String[]>();
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		try {
			for (Object o : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String field : FIELDNAMES) {
					Method getValue = CLAZZ.getMethod("get" + StringUtils.capitalize(field));
					if (FIELDTYPES.get(field) == java.util.Date.class) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put(field, sdf.format(getValue.invoke(o)));
					} else if (FIELDTYPES.get(field) == int.class) {
						if (stautsField.containsKey(field)) {// 如果需要转换状态码到字符串
							String[] statusMapping = stautsField.get(field);
							Map<String, String> mappingCode = setStatusCode2String(statusMapping[0], statusMapping[1]);
							map.put(field, mappingCode.get(getValue.invoke(o) + "")); // +""关键
						} else {
							map.put(field, getValue.invoke(o));
						}
					} else {
						map.put(field, getValue.invoke(o));
					}
				}
				showList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("total", list.size());
		data.put("rows", rows);
		return data;
	}

	/**
	 * 设置状态码到状态显示的字符串
	 * 
	 * @param statusCode
	 *            状态码
	 * @param mappingString
	 *            状态码对应字符串
	 * @return map
	 */
	private static Map<String, String> setStatusCode2String(String statusCode, String mappingString) {
		String[] statusCodes = statusCode.split(",");
		String[] mappingStrings = mappingString.split(",");
		int statusLength = statusCodes.length;
		int mappingLength = mappingString.length();
		Map<String, String> map = new HashMap<String, String>();
		if (statusLength != mappingLength) {
			for (int i = 0; i < statusLength; i++) {
				map.put(statusCodes[i], mappingStrings[i]);
			}
		}
		return map;
	}

	/**
	 * 兼容ie 增加和修改方法时 返回提示
	 * @param flag  
	 * @param mes 
	 * @return  map
	 */
	public static Map<String, Object> returnMes(boolean flag, String mes) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", flag);
		result.put("msg", mes);
		return result;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 *            CMD arguments
	 */
	public static void main(String[] args) {
		// ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml",
		// "spring-servlet.xml", "spring-config-Captcha.xml");
		// SuggestionService suggestionService = (SuggestionService) ctx.getBean("SuggestionService");
		// PageInfo<Suggestion> pageInfo = suggestionService.querySuggestionList(2, 1, 10, 1, "createtime", "desc");
		//
		// Map<String, String[]> stautsField = new HashMap<String, String[]>();
		// stautsField.put("categoryId", "1,2,3;建议,疑难杂症,其他".split(";"));
		// JSONTookit json = new JSONTookit();
		// json.setStautsField(stautsField); // 状态码转字符串配置
		// json.setDateFormatPattern("yyyy年MM月dd"); // 设置日期转换格式
		// System.out.println(stautsField.toString());
		// System.err.println(json.getJSONObject(pageInfo.getItems().get(0)));
		// System.out.println(json.generateJSONObject(pageInfo.getItems()));
		// System.out.println(json.setStatusCode2String("1,2,3", "过,是,的"));
	}
}
