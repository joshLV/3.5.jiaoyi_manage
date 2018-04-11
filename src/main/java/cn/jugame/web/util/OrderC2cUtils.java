package cn.jugame.web.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.util.Cache;


/**
 * c2c发货工具类
 * @author Administrator
 *
 */
public class OrderC2cUtils {
	public static Logger LOGGER = LoggerFactory.getLogger(OrderC2cUtils.class);
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
	 * @param orderId 订单号
	 * @param token 
	 * @return
	 */
	public static boolean validateToken(String orderId,String token){
		if(orderId == null || ("").equals(orderId)){
			LOGGER.error("查看订单详情的订单号为空");
			return false;
		}
		if(token == null || ("").equals(token)){
			LOGGER.error("查看订单详情的token为空");
			return false;				
		}
		//根据orderId去Cache里儿取token
		String cacheToken = Cache.get(orderId);
		if(cacheToken == null || ("").equals(token)){
			LOGGER.error("查看订单详情的token己过期，请联系管理员");
			return false;				
		}
		if(!token.equals(cacheToken)){
			LOGGER.error("token验证不通过");
			return false;				
		}
		return true;
	}
}
