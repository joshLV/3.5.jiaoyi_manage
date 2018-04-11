package cn.juhaowan.member.dao;

public interface WXUserSubcribeDao {
	/**
	 * 订阅状态：正常
	 */
	public static int SUBCRIBE_STAT_NORMAL = 1;
	
	/**
	 * 查询此微信用户是否已经订阅
	 * @param wxOpenid 	微信用户标识
	 * @return
	 */
	boolean isSubcribed(String wxOpenId);
	
	/**
	 * 查询微信用户的订阅ID
	 * @param wxOpenId
	 * @param wxNickname
	 * @param wxHeadImgUrl
	 * @return 0成功，-1失败 -2参数错误
	 */
	int newWXSubcribe(String wxOpenId, String wxNickname, String wxHeadImgUrl);
	
	/**
	 * 根据微信标识查询微信用户的订阅ID
	 * @param wxOpenid 	微信用户标识
	 * @return -1失败 -2参数错误，成功则直接返回该subId
	 */
	int subIdWithWXOpenId(String wxOpenId);
	
	/**
	 * 根据subId查询微信用户标识
	 * @param subId		微信用户订阅标识(即wx_user_subcribe表中的id字段)
	 * @return 失败返回null
	 */
	String wxOpenIdWithSubId(int subId);
	
	/**
	 * 获取微信用户的昵称
	 * @param wxOpenid 	微信用户标识
	 * @return
	 */
	String wxNickname(String wxOpenId);
	
	/**
	 * 获取微信用户的头像地址
	 * @param wxOpenid 	微信用户标识
	 * @return
	 */
	String wxHeadImgUrl(String wxOpenId);
}
