package cn.juhaowan.member.dao;

public interface WXUserBindDao {
	
	/**
	 * 订阅状态：正常
	 */
	public static int BIND_STAT_NORMAL = 1;
	
	/**
	 * 查询微信用户是否绑定了8868账户
	 * @param wxOpenid 	微信用户标识
	 * @return 
	 */
	boolean isWXOpenIdBinded(String wxOpenId);
	
	/**
	 * 查询8868用户是否绑定了微信账户
	 * @param uid		8868用户标识
	 * @return 
	 */
	boolean isUidBinded(int uid);
	
	/**
	 * 新增一个绑定关系
	 * @param wxOpenid 	微信用户标识
	 * @param uid		8868用户标识
	 * @return 0成功，-1失败 -2参数错误
	 */
	int newWXBind(String wxOpenId, int uid);
	
	/**
	 * 查询8868用户的绑定标识subId
	 * @param uid		8868用户标识
	 * @return -1无该微信用户绑定，-2参数错误，成功则直接返回该 subId
	 */
	int subIdWithUid(int uid);
	
	/**
	 * 查询微信用户绑定的8868账户标识
	 * @param wxOpenid 	微信用户标识
	 * @return -1无该微信用户绑定，-2参数错误，成功则直接返回该用户 uid
	 */
	int uidWithWXOpenId(String wxOpenId);
	
	/**
	 * 查询8868账户绑定的微信用户标识
	 * @param uid		8868用户标识
	 * @return 无该标识则返回null
	 */
	String wxOpenIdWithUid(int uid);
}
