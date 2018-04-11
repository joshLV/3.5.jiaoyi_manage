package cn.jugame.web.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.util.Cache;


/**
 * 单点登录工具类
 * @author Administrator
 *
 */
public class SSOUtils {
	public static Logger LOGGER = LoggerFactory.getLogger(SSOUtils.class);
	/**
	 * 获取token
	 * @return
	 */
	public static String getToken(int length){
		Random random = new Random();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
	/**
	 * 验证token的方法
	 * @param key
	 * @param token 
	 * @return
	 */
	public static boolean validateToken(String key,String token){
		if(key == null || ("").equals(key)){
			LOGGER.info("sso验证token方法,key为空");
			return false;
		}
		if(token == null || ("").equals(token)){
			LOGGER.info("sso验证token方法,token为空");
			return false;				
		}
		//根据key去Cache里儿取token
		String cacheToken = Cache.get(key);
		if(cacheToken == null || ("").equals(token)){
			LOGGER.info("sso验证token方法,token己过期" + key + "===" + token);
			return false;				
		}
		if(!token.equals(cacheToken)){
			LOGGER.info("sso验证token方法,token验证不通过" + key + "===" + token);
			return false;				
		}
		return true;
	}
}
