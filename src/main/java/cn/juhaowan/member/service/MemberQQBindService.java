package cn.juhaowan.member.service;

/**
 * qq用户绑定
 * @author caizr
 *
 */
public interface MemberQQBindService {
	
	/**
	 * 查询用户绑定的qqOPenID (只返回状态正常的)
	 * @param uid
	 * @return 返回null表示没有绑定
	 */
	String queryOpenID(int uid);
	
	/**
	 * 查询qq用户绑定8868平台的用户id(只返回状态正常的)
	 * 	
	 * @param openID
	 * @return  -1没有绑定，-2状态已解绑，大于0为用户id
	 */
	int queryUid(String openID);
	
	
	/**
	 * 绑定用qq用户
	 * @param uid
	 * @param openID qq的openid
	 * @param nickName 昵称
	 * @return true绑定成功，false绑定失败
	 */
	boolean bindQQUser(int uid,String openid,String nickName);
	
	/**
	 * 角色解绑
	 * @param uid
	 * @param openid
	 * @return
	 */
	boolean unBinQQUser(int uid,String openid);
}
