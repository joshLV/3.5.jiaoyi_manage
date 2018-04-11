package cn.juhaowan.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberPayCostList;
import cn.juhaowan.member.vo.MemberStore;
import cn.juhaowan.member.vo.MemberStoreOrder;




/**
 * 卖家店铺
 *
 */
public interface MemberStoreManagerService {

   
   /**
    * 添加店铺 
    * @param memberStore 店铺实体
    * @return 0 成功 1 失败
    * */
   int addMemberStore(MemberStore memberStore);
   /**
    * 添加 用户申请店铺临时表
    * @param memberStoreOrder 申请店铺临时表
    * @return 0 成功 1 失败
    */
    int addMemberStoreOrder(MemberStoreOrder memberStoreOrder);
   /** 
    * 根据uid 查询店铺详情 （前台 带缓存）
    * @param uid 用户uid
    * @return 店铺实体
    * */
   MemberStore queryByUidFront(int uid);
   /** 
    * 根据uid 查询店铺详情 （后台 ）
    * @param uid 用户uid
    * @return 店铺实体
    * */
   MemberStore queryByUidBack(int uid);
   /** 
    * 根据条件查询店铺列表（前台 代缓存）
    * @param conditions 
    * @param pageSize 
    * @param pageNo 
    * @param sort 
    * @param order
    * @return 分页列表
    * */
   PageInfo<MemberStore> queryPageFront(Map<String,Object> conditions, int pageNo,int pageSize ,String sort, String order);
   /**
    * 根据条件查询店铺列表（后台 ）
    * @param conditions 
    * @param pageSize 
    * @param pageNo 
    * @param sort 
    * @param order
    * @return 分页列表
    * */
   PageInfo<MemberStore> queryPageBack(Map<String,Object> conditions, int pageNo, int pageSize, String sort, String order);
   /**
    * 根据店铺状态查询店铺列表
    * @param storeStatus 店铺状态
    * @return 店铺列表
    * */
   List<MemberStore> queryListByStoreStatus(int storeStatus);
   /** 
    * 更新会员店铺信息
    * @param memberStore 会员店铺
    * @return 0 成功 1 失败
    * */
   int updateMemberStore(MemberStore memberStore);
   /** 
    * 店铺状态修改  更新修改时间  
    * @param uid  会员uid
    * @param storeStatus 店铺状态
    * @return 0 成功 1 失败
    * */
   int updateStoreStatus(int uid, int storeStatus);
   /** 
    * 审核状态修改
    * @param uid 会员uid
    * @param verifyStatus 审核状态
    * @return 0 成功 1失败
    * */
   int updateVerifyStatus(int uid, int verifyStatus);

   /** 
    * 根据用户uid 更新月成交笔数 月成交量 最后成交时间
    * @param uid 用户uid
    * @param yearDealNumber 月成交笔数 
    * @param yearDealTotal  月成交量
    * @param dealFinalTime  最后成交时间
    * @return 0 成功 1失败
    * */
   int updateDealInfo(int uid, int yearDealNumber, double yearDealTotal, Date dealFinalTime);
   /**
    * 正常续费
    * @param uid 用户uid 
    * @param openTime 开通时长
    * @return 0成功 1失败
    * */
   int renew(int uid, int openTime);


   //------------------------------------------------------------------
   /**
	 * 开通店铺
	 * 
	 * @pdOid 14f2a151-e5cc-487d-8494-775e5aaa3d15
	 */
	static final int PAYTYPE_OPENSTORE = 1;
	/**
	 * 正常续费
	 * 
	 * @pdOid bb7dfa35-d5b1-4a85-b8e7-3b31f85dffcf
	 */
	static final int PAYTYPE_NORMALRENEWAL = 5;
	/**
	 * 过期续费
	 * 
	 * @pdOid 2a5d49dd-0ca4-456a-a973-83129152d2a1
	 */
	static final int PAYTYPE_OVERDUERENEWAL = 7;

	/**
	 * 添加缴费信息
	 * 
	 * @param payType
	 *            缴费类型
	 * @param uid
	 *            用户ID
	 * @param amount
	 *            金额
	 * @param openingTime
	 *            开通时长
	 * 
	 * @pdOid 72a0f1f2-35d8-43a7-9013-4b697c5bc7ee
	 */
	int addPayCost(int payType, int uid, Double amount, java.lang.Integer openingTime);

	/**
	 * 查询缴费信息PageInfo
	 * 
	 * @param conditions
	 *            条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            排序方式
	 * @pdOid 11b2dfbd-c753-4482-9a7c-799f9a337461
	 */
	PageInfo<MemberPayCostList> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize,
			String sort, String order);

	/**
	 * 根据用户Id查询所属缴费信息List
	 * 
	 * @param uid
	 *            用户ID
	 * @pdOid aaa3f5da-e299-4c90-afa6-e48be2cb64b0
	 */
	List<MemberPayCostList> queryListByUid(int uid);
	/**
	 * 用户支付成功回调操作 
	 * @param tempOrderId 临时表订单id
	 * @param uid 用户uid
	 * @return 0 成功 1 失败
	 */
	int orderPaySuc(int tempOrderId,int uid);
	/**
	 * 回滚后台店铺审核
	 * 后台审核出错时调用
	 * @param uid 用户uid 
	 */
	void rollBackUpdateVerifyStatus(int uid);
}