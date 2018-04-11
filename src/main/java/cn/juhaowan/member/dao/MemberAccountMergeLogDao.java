package cn.juhaowan.member.dao;

import java.util.Date;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberAccountMergeLog;


public interface MemberAccountMergeLogDao {
	
	/**
	 *充值
	 */
	public static int OPERATOR_TYPE_RECHARGE = 1;
	
	/**
	 * 支付
	 */
	public static int OPERATOR_TYPE_PAY = 5;
	
	/**
	 * 收入 
	 */
	public static int OPERATOR_TYPE_PAY_COLLECT = 10;
	
	/**
	 * 冻结押金	
	 */
	public static int OPERATOR_TYPE_DEPOSIT_ADD = 12;
	
	/**
	 * 解冻押金
	 */
	public static int OPERATOR_TYPE_DEPOSIT_CUT = 13;
	
	/**
	 * 提现(支出)
	 */
	public static int OPERATOR_TAKE_MONEY = 15;
	
	/**
	 * 退费 
	 */
	public static int OPERATOR_RETURN_MONEY = 20;


	/**
	 * 调账： 提现失败退费
	 */
	public static int OPERATOR_RETURN_TAKE_MONEY = 21;
	
	
	/**
	 * 带同步
	 */
	public static int SYNC_STAT_NEED_SYNC = 0;
	
	/**
	 * 已经不同
	 */
	public static int SYNC_STAT_ALREADY_SYNC = 1;
	

	/**
	 * 不需要同步
	 */
	public static int SYNC_STAT_NO_SYNC = 10;
	
	
	
	/**
	 * 添加
	 * @param uid 用户ID
	 * @param payType 支付渠道(支付渠道，0 无  1 易宝  2 银联)
	 * @param cardNo 卡类充值的卡号
	 * @param amountCash  网银充值的数量，可提现
	 * @param amountNoCash 卡类充值的数量，不可提现
	 * @param fee 手续费
	 * @param totalBalance 总余额
	 * @param completeTime  （易宝充值）订单完成时间
	 * @param externalId 账户通充值订单流水号
	 * @param requestId 账户通充值订单流水号
	 * @param remark 备注
	 */
	void addRechargeLog(int uid, int payType, String cardNo, double amountCash, double amountNoCash,
			double fee, double totalBalance, Date completeTime, String externalId, String requestId, String remark);
	
//	
//	/**
//	 * 指定展现的操作类型的，支付日志
//	 * @param uid
//	 * @param showOperatorType
//	 * @param payType
//	 * @param amountCash
//	 * @param amountNoCash
//	 * @param totalBalance
//	 * @param orderId
//	 * @param remark
//	 */
//	void addPayLog(int uid, int showOperatorType, int payType, double amountCash, double amountNoCash, double totalBalance, 
//			String orderId, String remark);
//	
	/**
	 * 
	 * @param uid 用户id
	 * @param payType 支付渠道(支付渠道，0 无  1 易宝  2 银联)
	 * @param amountCash 网银支付的数量，可提现
	 * @param amountNoCash 卡类支付的数量，不可提现
	 * @param totalBalance 总余额
	 * @param orderId 订单ID
	 * @param remark 备注
	 */
	void addPayLog(int uid, int payType, double amountCash, double amountNoCash,
			double totalBalance, String orderId, String remark);
	
	
	/**
	 * 
	 * @param uid 用户ID
	 * @param amount 数量
	 * @param totalBalance 总余额 
	 * @param orderId 订单号
	 * @param remark 备注
	 */
	void addRevenueLog(int uid, double amount, double totalBalance, String orderId, String remark);
	
	
	/**
	 * 
	 * @param uid  用户uid
	 * @param amount 取现的数量
	 * @param fee 手续费
	 * @param totalBalance 总余额
	 * @param bankId
	 * @param bankCardNo
	 * @param remark
	 */
	void addTakeMoneyLog(int uid, String orderId, double amount, double fee, double totalBalance, String bankId, String bankCardNo, 
			String remark);
	

	/**
	 * 
	 * @param uid 用户uid
	 * @param amountCash 退款到现金账户的金额
	 * @param amountNoCash 退款到非现金账户的金额
	 * @param totalBalance 总余额
	 * @param orderId 订单号
	 * @param remark 备注
	 */
	void addReturnMoneyLog(int uid, double amountCash, double amountNoCash, double totalBalance,
			String orderId, String remark);
	

	
	/**
	 * 
	 * @param uid
	 * @param amount
	 * @param totalBalance
	 * @param requestId 账户通充值订单流水号
	 * @param remark 备注
	 */
	void addReturnTakeMoneyLog(int uid, double amount, double totalBalance, String requestId, String remark);
	
	
	/**
	 * 分页查找 
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<MemberAccountMergeLog> findWithPage(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order);
}
