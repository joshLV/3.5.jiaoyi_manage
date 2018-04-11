package cn.jugame.web.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.util.MD5;

public class GetPropertiesUtil {

	private static final Logger log = LoggerFactory.getLogger(GetPropertiesUtil.class);

	String orderid = "ORD-140123-09244646951";
	String baseurl = "http://192.168.0.199:8080/3.5.jiaoyi_manage/api/api/";
	String appId = "1000";
	String appKey = "12345678900";
	String result = "";
	String data = "{\"order_id\":\"" + orderid + "\"}";
	String timeTemp = String.valueOf(System.currentTimeMillis());
	String sign = MD5.encode(data + appKey + timeTemp).substring(0, 8);
	String param = "cmd=getOrderDetail&data=" + data + "&sign=" + sign + "&appId=" + appId + "&requestTime=" + timeTemp;

	private static Properties pro = new Properties();

	public static String getPropertiesByKey(String key) {
		String result = "";
		try {
			pro.load(new FileInputStream(GetPropertiesUtil.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();

		}

		result = pro.getProperty(key);
		return result;
	}

	public static String getApiSign(String data, String appKey, String timeTemp) {
		String sign = "";
		try {
			sign = MD5.encode(data + appKey + timeTemp).substring(0, 8);
		} catch (Exception e) {
			log.error("md5签名异常" + e);
		}
		return sign;

	}

	public static void main(String[] args) {
		String r = getPropertiesByKey("web.js.path");

		System.out.println("===>>" + r);
	}

}
