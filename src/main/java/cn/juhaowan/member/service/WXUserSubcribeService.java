package cn.juhaowan.member.service;

public interface WXUserSubcribeService {
	
	/**
	 * 微信用户订阅接口
	 * @param wxOpenId  	微信用户标识
	 * @param wxNickname   	微信昵称
	 * @param wxHeadImgUrl 	微信头像地址
	 * @return 0成功，-1失败 -2参数错误
	 */
	int subcribe(String wxOpenId, String wxNickname, String wxHeadImgUrl);
	
	/**
	 * 通过微信标识获取微信昵称
	 * @param wxOpenId		微信用户标识
	 * @return 
	 */
	String wxNickname(String wxOpenId);
}
