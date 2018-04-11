package cn.juhaowan.member.service;

public interface WXUserBindService {
	
	/**
	 * 查询微信用户是否已绑定接口
	 * @param wxOpenId	微信用户标识
	 * @return
	 */
	boolean isWXOpenIdBinded(String wxOpenId);
	
	/**
	 * 查询8868用户是否已绑定接口
	 * @param uid		8868用户标识
	 * @return
	 */
	boolean isUidBinded(int uid);
	
	/**
	 * 微信用户绑定接口
	 * @param wxOpenId	微信用户标识
	 * @param uid		8868用户标识
	 * @return 0成功，-1失败 -2参数错误
	 */
	int bind(String wxOpenId, int uid);
	
	
	/**
	 * 查询微信用户绑定的8868 uid
	 * @param wxOpenId	微信用户标识
	 * @return -1 失败，-2 参数错误，成功直接返回 uid
	 */
	int uidWithWXOpenId(String wxOpenId);
	
	/**
	 * 查询8868账户绑定的微信标识
	 * @param uid		8868用户标识
	 * @return 如果失败，则返回null
	 */
	String wxOpenIdWithUid(int uid);
}
