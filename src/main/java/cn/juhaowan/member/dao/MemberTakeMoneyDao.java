package cn.juhaowan.member.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberTakeMoney;

/**
 * 提现dao接口
 * **/
public interface MemberTakeMoneyDao {
	
	
	public static double AMOUNT_DAY = 10000;
	
	
   /**
    * 添加提现申请
    * @param uid 申请人uid
    * @param amount 提现金额
    * @param type  提现类型(1 普通提现  2 快速提现)
    * */
   int addTakeMoney(int uid,double fee, double amount, int type,String bankID, String bankCardNum, String bankAddr,  String provinces, String realName, String orderId);
   /** 
    * 审核申请
    * @param takeid  提现单ID
    * @param verifyStatus 审核状态(1 审核通过2 审核不通过（必须写备注）)
    * @param remark 备注ע
    */
   int updateVerifyStauts(int takeid, int verifyStatus, String remark);
   /** 
    * 转账
    * @param takeid  提现单ID
    */
   int updateTransferStatus(int takeid);
   		
   /** 
    * 记录转账状态，成功则给用户发送站内信
    * @param transactionId 
    * @param status 1 成功
    * 2 失败
    */
   int updateNotifyStatus(String transactionId, int status, int takeid);
   
   /** 
    * 查询列表
    * @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
    * */
   PageInfo<MemberTakeMoney> queryTakeMoneyrList(Map<String,Object> condition, 
		   int pageSize, int pageNo, String sort, String order);
   
   /** 
    * 查询列表(后台)
    * @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
    * */
   PageInfo<MemberTakeMoney> queryTakeMoneyrPage(Map<String,Object> condition, 
		   int pageSize, int pageNo, String sort, String order);

   /**
    * 按照id查找
    */
   MemberTakeMoney findByid(int id);
   
   /**
    * 按照transaction_id查找
    */
   MemberTakeMoney findBytid(String tid);
   
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
    * 修改转账状态
    * 
    * **/
   int updateTransferStatus(String tid, int status);
   
   
   /**
    * 根据用户uid查询用户24小时之内有没有申请提现
    * @param uid 用户uid
    * @return true 用户24小时内有申请提现
    * 		false 用户24小时内没有申请提现
    * 
    * **/
   boolean findTakeMoneyLog(int uid);
}