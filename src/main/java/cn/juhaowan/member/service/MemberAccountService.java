package cn.juhaowan.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberAccountFrozen;
import cn.juhaowan.member.vo.MemberAccountLog;
import cn.juhaowan.member.vo.MemberAccountMergeLog;
import cn.juhaowan.member.vo.MemberCashSetting;
import cn.juhaowan.member.vo.MemberDeposit;

/**
 * 用户资金服务
 *  
 */
public interface MemberAccountService {
	
	/**
	 * 支付渠道：聚好玩(平台转账)
	 */
	public static final int PLATFORM_JUGAME_TRANS=6;
	
	
	int  rechargeForUser(String uid, String amount, String accountType, String channel,
			String remark);
	
	/**
	 * 充值
	 * @param uid
	 * @param accountType 1 现金账户 2 非现金账户（不可提现）
	 * @param amount  操作金额（到账金额=操作金额-手续费）
	 * @param fee 手续费
	 * @param bankId
	 * @param bankCardNo
	 * @param cardNo  充值卡卡号
	 * @param orderId
	 * @param completeTime （易宝充值）订单完成时间
	 * @param externalId 8868平台流水号
	 * @param remark  0 成功 1 失败
	 * @return
	 */
	int recharge(int uid, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark);
	
	
	/**
	 * 充值
	 * @param uid
	 * @param payType 支付类型
	 * @param accountType 1 现金账户 2 非现金账户（不可提现）
	 * @param amount  操作金额（到账金额=操作金额-手续费）
	 * @param fee 手续费
	 * @param bankId
	 * @param bankCardNo
	 * @param cardNo  充值卡卡号
	 * @param orderId
	 * @param completeTime （易宝充值）订单完成时间
	 * @param externalId 8868平台流水号
	 * @param remark  0 成功 1 失败
	 * @return
	 */
	int recharge(int uid, int payType, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark);
	
	
	
	/**
	 * 支付订单，优先扣非现金账户
	 * 
	 * @param outUid
	 * @param inUid
	 * @param amount
	 * @param orderId
	 * @param payType 支付类型
	 * @param remark
	 * 
	 * @return 返回double数组: double[0] 表示支付结果(0成功，1余额不足），double[1]
	 *         非现金账户扣除金额，double[2] 现金账户扣除金额
	 * 
	 **/
	double[] pay(int outUid, double amount, String orderId, int payType, String remark);
 
	
	/**
	 * 收入(订单完成交易，将钱转入卖家账号）
	 * @param uid   转入用户id
	 * @param amount  金额
	 * @param orderId  订单id
	 * @param remark 	备注
	 * @return
	 */
	int revenue(int uid, double amount, String orderId, String remark);

	/**
	 * 查询用户现金账户余额
	 * 
	 * @param uid
	 * @pdOid b059d2aa-b2f2-46f3-b859-095a1b590ea0
	 */
	double queryCash(int uid);

	 
	/**
	 * 提现 从用户现金账号中扣除相应金额 0成功，1余额不足
	 * @param uid
	 * @param amount 	提现金额
	 * @param fee 	  	手续费（只记录到日志）
	 * @param bankId  	提现银行
	 * @param bankCardNo  提现银行卡号
	 * @param orderId 	   提现单id
	 * @param remark	备注
	 * @return
	 */
	int takeMoney(int uid, double amount,double fee, String bankId, String bankCardNo,
			String orderId, String remark);

	/**
	 * 查询余额 包括 现金和非现金账户
	 * 
	 * @param uid
	 * @pdOid 06c44556-1197-49fd-9355-49eb880e1d69
	 */
	double queryBalance(int uid);

	/**
	 * 查询账户余额
	 * 
	 * @param uid
	 * @param accountType 账户类型
	 * @return
	 */
	double queryBalance(int uid, int accountType);

	/**
	 * 查询48小时内现金充值的金额
	 * @param uid
	 * @return
	 */
	double queryCash48hRechargeAmount(int uid);
	
	/**
	 * 查询可提现金额
	 * @param uid
	 * @return
	 */
	double queryCanTakeMoneyAmount(int uid);
	
	/**
	 * 查询不提现金额
	 * @param uid
	 * @return
	 */
	double queryCanNotTakeMoneyAmount(int uid);
	
	/**
	 * 查询资金流水
	 * 
	 * @param uid
	 * @param type 
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param pageNo
	 * @pdOid 2e569966-1d9e-45e1-bc71-24ae063b0efe
	 */
	PageInfo<?> queryAccountLog(int uid, int type, String startDate,String endDate, int pageSize, int pageNo);
	
	
	/**
	 * 查询资金流水(多种状态
	 * @param uid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param pageSize
	 * @param pageNo
	 * @pdOid 2e569966-1d9e-45e1-bc71-24ae063b0efe
	 */
	PageInfo<?> queryAccountLog2(int uid, List<Integer> type, int pageSize, int pageNo);

	/**
	 * 设置提现限制 0 成功， 1失败
	 * @param uid
	 * @param dayLimit
	 * @param itemLimit
	 * @pdOid d5f30fb1-b335-4c8f-8d67-9788c82cba24
	 */
	int setTakeMoneyLimit(int uid, int dayLimit, int itemLimit);

	/**
	 * 查询用户提现设置
	 * 
	 * @param uid
	 * @return
	 */
	MemberCashSetting getTakeMoneyLimit(int uid);

	/**
	 * 退费
	 * 
	 * @param uid
	 * @param accountType
	 * @param orderId
	 * @param amount
	 * @param remark
	 * @return
	 */
	int returnMoney(int uid, int accountType, String orderId, double amount,
			String remark);
	

	
	/**
	 * 退费
	 * 
	 * @param uid
	 * @param orderId
	 * @param amountCash 现金
	 * @param amountNoCash 非现金
	 * @param remark 
	 * @return 0成功，1失败
	 */
	int returnMoney(int uid, String orderId, double amountCash, double amountNoCash, String remark);
	
	
	/**
	 * 退回提现的金额
	 * @param uid 用户ID
	 * @param amount 退回的金额数量
	 * @param requestId 请求ID
	 * @param remark 备注
	 * @return 0 为OK, 其他为失败
	 */
	int returnTakeMoney(int uid, double amount, String requestId, String remark);
	
	
	/**
	 * 后台分页查询流水列表方法
	 * 
	 * @param uid   用户id
	 * @param type  操作类型
	 * @param beginTime 开始时间
	 * @param endTime   结束时间
	 * @param pageNo    第几页
	 * @param pageSize  每页条数
	 * @param sort      排序条件
	 * @param order     DESC/ASC
	 * @return
	 */
	public PageInfo<MemberAccountLog> queryMemberAccountLogPageBack(int uid, int type,
			String beginTime, String endTime, int pageNo, int pageSize,
			String sort, String order);
	
	
	/**
	 * 按id查询资金详情(后台)
	 */
	MemberAccountLog queryLogById(int id);
	
	

	/**
	 * 
	 * @param uid 用户ID
	 * @param authStatus  验证状态。 如果传入为"1"，则userName, idCardNo不能为空
	 * @param userName 姓名
	 * @param idCardNo 身份证号
	 * @return
	 */
	int createAccount(int uid, String authStatus, String userName,  String idCardNo);
	

	
	
	/**
	 * 锁定现金（押金） 0 成功 1 余额不足
	 * 
	 * @param uid
	 * @param depositType
	 * @param amount
	 * @param remark
	 * @pdOid 620d263e-a125-4c94-9e74-4192596e6cbd
	 */
	int lockDeposit(int uid, int depositType, double amount, String remark);

	/**
	 * 解锁现金（押金） 0 成功 1 余额不足
	 * 
	 * @param uid
	 * @param amount
	 * @param remark
	 * @pdOid d1c5b227-18a6-4bfd-844f-cb35959b02f1
	 */
	int unlockDeposit(int uid, int depositType, double amount, String remark);

	/**
	 * 扣除押金
	 * 
	 * @param uid
	 * @param deposit_type
	 * @param amount
	 * @param remark
	 * @return
	 */
	int subDeposit(int uid, int depositType, double amount, String remark);
	
	
	/**
	 * 添加押金
	 * 
	 * @param uid
	 * @param depositType
	 * @param amount
	 * @param remark
	 * @return
	 */
	int addDeposit(int uid, int depositType, double amount, String remark);

	/**
	 * 查询用户押金情况
	 * 
	 * @param uid
	 * @return
	 */
	List<MemberDeposit> queryDepositList(int uid);


	
	/**
	 * 添加冻结的UID
	 * @param uid 冻结的uid
	 * @return 0:成功， 其他: 失败
	 */
	int frozen(int uid, int type, String remark);
	
	
	/**
	 * 添加冻结的UID
	 * @param l 冻结的uid列表
	 * @return
	 */
	int frozen(List<Integer> l, int type, String remark);
	
	
	/**
	 * 是否为冻结用户
	 * @param uid
	 * @return
	 */
	boolean isFrozen(int uid);
	
	
	/**
	 * 是否为冻结支付和充值的用户
	 * @param uid
	 * @return
	 */
	boolean isFrozenPayAndRecharge(int uid);
	
	
	/**
	 * 解冻uid
	 * @param uid 解冻的UID
	 * @return 0:成功，其他：失败
	 */
	int unFrozen(int uid);
	
	
	
	/**
	 * 解冻uid
	 * @param l 解冻的UID列表
	 * @return 0:成功， 其他: 失败
	 */
	int unFrozen(List<Integer> l);
	
	
	/**
	 * 冻结列表
	 * @param conditions
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<MemberAccountFrozen> queryMemberFrozenPage(Map<String,Object> conditions, int pageSize, int pageNo, String sort, String order);
	
	
	/**
	 * 分页查询merge log
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<MemberAccountMergeLog> queryMemberAccountMergeLog(Map<String, Object> condition, int pageSize, 
				int pageNo,String sort, String order);
	
	/**
	 * 转账接口
	 * @param fromUid
	 * @param toUid
	 * @param accountType
	 * @param amount
	 * @return 0代表成功，1代表失败
	 */
	public int alipayTransfer(int fromUid, int toUid, int accountType, double amount, String orderId, double fee, String aliAccount, String desc);
	
	
	/**
	 * 转账接口 
	 * @param fromUid
	 * @param toUid
	 * @param accountType
	 * @param amount
	 * @param remark
	 * @return
	 */
	public int transfer(String fromUid, String toUid, String accountType, String amount,
			String remark);
	
	
	/**
	 * 仲裁接口
	 * @param orderId
	 * @param sellerUid
	 * @param sellerAmount
	 * @param buyerUid
	 * @param buyerAmount
	 * @param remark
	 * @return
	 */
	public int adjustRevenue(String orderId, String sellerUid, String sellerAmount, String buyerUid, String buyerAmount, String remark);
}
