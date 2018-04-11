package cn.juhaowan.member.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberTakeMoney;
import cn.juhaowan.member.vo.MemberTakeMoneyLog;

/**
 * 提现服务接口
 * 
 * **/
public interface MemberTakeMoneyService {
	
	public final static int TAkEMONEY_ORDINARY = 1; // 普通提现
	public final static int TAkEMONEY_RAPID = 2; // 快速提现
	
	public final static String BANK = "bank";
	public final static String ALIPAY = "alipay";
	
	/**
	 * 支付宝提现:订单新建状态
	 */
	public final static int ALIPAY_WITHDRAW_NEW = 0;
	/**
	 * 支付宝提现:提交支付宝成功
	 */
	public final static int ALIPAY_WITHDRAW_REQ_SUCC = 1;
	/**
	 * 支付宝提现:提交支付宝失败
	 */
	public final static int ALIPAY_WITHDRAW_REQ_FAIL = 2;
	/**
	 * 支付宝提现:提现成功
	 */
	public final static int ALIPAY_WITHDRAW_TAKE_SUCC = 3;
	/**
	 * 支付宝提现:提现失败
	 */
	public final static int ALIPAY_WITHDRAW_TAKE_FAIL = 4;
	/**
	 * 支付宝提现:已退款
	 */
	public final static int ALIPAY_WITHDRAW_RETURN = 5;
	
	
   /** @param uid 
    * @param amount 
    * @param type 1 普通提现
    * 2 快速提现
    * @pdOid 14758e54-b2ee-49d3-b92d-0b91018a73fe */
   int addTakeMoney(int uid,double fee, double amount, int type,String bankID, String bankCardNum, String bankAddr, String provinces, String realName, String orderId);
  
   
   /** @param takeid 
    * @param verifyStatus 1 审核通过
    * 2 审核不通过（必须写备注）
    * @param remark
    * @pdOid 5389fc5f-042b-4a24-8056-e3e1b8681b8a */
   int verify(int takeid, int verifyStatus, String remark);
   /** 
    * (调用易宝接口进行转账?)
    * 修改状态为已转账
    * 0 操作成功
    * 1 操作单末审核成功
    * @param takeid
    * @pdOid 3aeba889-8d59-4547-bd29-85c0504bbfcd */
   int transfer(int takeid);
   
   /** 
    * 记录转账状态，成功则给用户发送站内信
    * 0 成功
    * 1 申请末转账
    * 
    * @param transactionId 
    * @param status 1 成功
    * 2 失败
    * @pdOid a9f87ff1-1f8b-466e-83ad-29293c3e332f */
   int transferStatusNotify(String transactionId, int status, int takeid);
   /** @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
    * @pdOid cad64e9f-cff3-4a4c-a82d-bb96d342063c */
   PageInfo<MemberTakeMoney> queryTakeMoneyrList(Map<String,Object> condition, int pageSize, int pageNo, String sort, String order);
   /**
    * 根据id查找
    */
   MemberTakeMoney findById(int id);
   
   /**
    * 根据transaction_id查找
    */
   MemberTakeMoney findByTId(String tid);
   
   
   
   /**
    * 根据id查找用户一天提现总金额
    * 
    * **/
   double findAmount(int uid);
   /**
    * 查询所有未提现成功的提现单
    * @param condition
    * @return
    */
   List<MemberTakeMoney> queryMemberTakeMoneyByNotSuccess(Map<String,Object> condition);
   /**
    * 根据打款ID查找提现单
    * @param dkOrderId 打款ID
    * @return
    */
   MemberTakeMoney findByDKOrderId(String dkOrderId);
   
   /**
    * 添加提现日志 0：成功  1：失败
    */
   int addLog(MemberTakeMoneyLog log);
   
   /** @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
   */
   PageInfo<MemberTakeMoneyLog> queryTakeMoneyrLogList(Map<String,Object> condition, int pageSize, int pageNo, String sort, String order);

   /**
    * 记录用户转账成功状态
    * 
    * **/
   int transferSuccessNotify(String tid);
   /**
    * 记录用户转账失败状态
    * 
    * **/
   int transferFailureNotify(String tid);
   
   /**
    * 根据用户uid查询用户24小时之内有没有申请提现
    * @param uid 用户uid
    * @return true 用户24小时内有申请提现
    * 		false 用户24小时内没有申请提现
    * 
    * **/
   boolean findTakeMoneyLog(int uid);
   
   
   
   /**
    * 根据提现单号修改提现状态
    * 
    * **/
   int updateTakeMoneyStatusByorderId(String orderId, int status, String ramark);
   
   
   /**
    * 支付宝提现
    * @param uid 用户ID
    * @param recv_uid 接收帐户的用户ID
    * @param fee 手续费
    * @param amount 提现金额
    * @param ali_account 支付宝收款方账号
    * @param ali_username 支付宝收款方用户名
    * @param payment_id 订单ID
    * @return 提现申请是否提交成功，并不代表已经打款到支付宝
    */
   boolean addTakeMoney4Alipay(int uid, int recv_uid, double fee, double amount, String ali_account, String ali_username, String payment_id);
   
   /**
    * 支付宝提现失败的情况下对用户退款
    * @param paymentId 支付宝提现的订单号
    * @return
    */
   boolean returnMoney4Alipay(String paymentId);
}