package cn.juhaowan.member.dao;

import cn.juhaowan.member.vo.MemberCashSetting;

public interface MemberCashSettingDao {
	/**
	 * 更新提现设置
	 * @param uid
	 * @param dayLimit
	 * @param itemLimit
	 * @return
	 */
	int setTakeMoneyLimit(int uid, int dayLimit, int itemLimit) ;
	
	/**
	 * 查询用户提现设置
	 * @param uid
	 * @return
	 */
	MemberCashSetting findByUid(int uid);
}
