package cn.juhaowan.member.dao;

import java.util.List;

import cn.juhaowan.member.vo.MemberDeposit;


public interface MemberDepositDao{
	 
	/**
	 * 押金类型：普通 (目前只有一种类型）
	 */
	public static int DEPOSIT_TYPE_NORMAL = 1 ;
	
	
	/**
	 * 查询押金余额
	 * @param uid
	 * @param deposit_type
	 * @return
	 */
	double queryBalance(int uid ,int deposit_type);
	
	/**
	 * 查询押金列表 
	 * @param uid
	 * @return
	 */
	List<MemberDeposit> queryDepositList(int uid);
	
	/**
	 * 增加
	 * @param uid
	 * @param account_type
	 * @param amount
	 * @return
	 */
	int add(int uid, int deposit_type, double amount);
	
	
	/**
	 * 扣除押金
	 * @param uid
	 * @param deposit_type
	 * @param amount
	 * @return
	 */
	int sub(int uid, int deposit_type, double amount) ;
	
}
