/**
 * 
 * @author lithium<lijie@jugame.cn>
 * @date   2013-05-10
 * @desc   只是现金账户
 */

package cn.juhaowan.member.dao;

/**
 * 48小时充值表
 * @author lithium
 *
 */
public interface MemberCash48hRechargeDao {
	
	public static final int DAYS_LIMIT = 1;
	
	public static int DEFAULT_RECHARGE = 0;
	
	public static int REVENUE_RECHARGE = 1;
	
	/**
	 * 查询充值现金数额
	 * @param uid 用户id
	 * @return 用户充值的数额
	 */
	double queryAmount(int uid);
	
	
	/**
	 * 查询充值现金数额
	 * @param uid 用户ID
	 * @param date 查询的日期
	 * @return  用户充值的数额
	 */
	double queryAmount(int uid, java.util.Date date);

	
	/**
	 * 插入新的充值记录
	 * @param uid 用户ID 
	 * @param amount 金额
	 * @param type 0为现金充值， 1为交易收入
	 * @return 0成功, 其他值为失败
	 */
	int recharge(int uid, double amount, int type); 
}
