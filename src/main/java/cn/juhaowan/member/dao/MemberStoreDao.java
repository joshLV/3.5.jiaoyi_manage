package cn.juhaowan.member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberPayCostList;
import cn.juhaowan.member.vo.MemberStore;
import cn.juhaowan.member.vo.MemberStoreOrder;

/**
 * 卖家店铺审核缴费管理
 */
public interface MemberStoreDao {

	   /** 
	    * 店铺状态 待审核
	    */
	   static final int STORESTATUS_TEMP = 1;
	   /** 
	    * 店铺状态 正常
	    */
	   static final int STORESTATUS_NORMAL = 5;
	   /** 
	    * 店铺状态 过期
	    */
	   static final int STORESTATUS_OUTDATE = 7;
	   /** 
	    * 审核状态 待审核
	    */
	   static final int VERIFYSTATUS_PENDING = 1;
	   /** 
	    * 审核状态 审核成功
	    */
	   static final int VERIFYSTATUS_SUCCESS = 5;
	   /** 
	    * 审核状态 审核失败
	    */
	   static final int VERIFYSTATUS_FAIL = 7;
	   /** 
	    * 支付状态 待支付
	    */
	   static final int PAYSTATUS_PENDING = 1;
	   /** 
	    * 支付状态 支付成功
	    */
	   static final int PAYSTATUS_SUCCESS = 2;
	   /** 
	    * 支付状态 支付失败
	    */
	   static final int PAYSTATUS_FAIL = 7;
	   
	   /** 
	    * 缴费类型 开通店铺
	    */
	   static final int PAYTYPE_NEW = 1;
	   /** 
	    * 缴费类型 正常续费
	    */
	   static final int PAYTYPE_NORMAL = 5;
	   /** 
	    * 缴费类型 过期续费
	    */
	   static final int PAYTYPE_OUTDATE = 7;
	
	
	/**
	 * 添加店铺
	 * 
	 * @param memberStore
	 *            店铺实体
	 * @return 0 成功 1 失败
	 * */
	int addMemberStore(MemberStore memberStore);

	/**
	 * 根据uid 查询店铺详情 
	 * 
	 * @param uid
	 *            用户uid
	 * @return 店铺实体
	 * */
	MemberStore queryByUid(int uid);

	/**
	 * 根据条件查询店铺列表
	 * 
	 * @param conditions
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return 分页列表
	 * */
	PageInfo<MemberStore> queryPage(Map<String, Object> conditions, int pageSize, int pageNo, String sort,
			String order);


	/**
	 * 根据店铺状态查询店铺列表
	 * 
	 * @param storeStatus
	 *            店铺状态
	 * @return 店铺列表
	 * */
	List<MemberStore> queryListByStoreStatus(int storeStatus);

	/**
	 * 更新会员店铺信息
	 * 
	 * @param memberStore
	 *            会员店铺
	 * @return 0 成功 1 失败
	 * */
	int updateMemberStore(MemberStore memberStore);

	/**
	 * 店铺状态修改 更新修改时间
	 * 
	 * @param uid
	 *            会员uid
	 * @param storeStatus
	 *            店铺状态
	 * @return 0 成功 1 失败
	 * */
	int updateStoreStatus(int uid, int storeStatus);

	/**
	 * 审核状态修改
	 * 
	 * @param uid
	 *            会员uid
	 * @param verifyStatus
	 *            审核状态
	 * @return 0 成功 1失败
	 * */
	int updateVerifyStatus(int uid, int verifyStatus);



	/**
	 * 根据用户uid 更新月成交笔数 月成交量 最后成交时间
	 * 
	 * @param uid
	 *            用户uid
	 * @param yearDealNumber
	 *            月成交笔数
	 * @param yearDealTotal
	 *            月成交量
	 * @param dealFinalTime
	 *            最后成交时间
	 * @return 0 成功 1失败
	 * */
	int updateDealInfo(int uid, int yearDealNumber, double yearDealTotal, Date dealFinalTime);

	/**
	 * 正常续费 更新结束时间=（结束时间+时长）
	 * 
	 * @param uid
	 *            用户uid
	 * @param openTime
	 *            开通时长
	 * @return 0成功 1失败
	 * */
	int renew(int uid, int openTime);


	/**
	 * 过期续费 更新开始时间=now() 结束时间=now()+时长
	 * 
	 * @param uid
	 *            用户uid
	 * @param openTime
	 *            开通时长
	 * @return 0成功 1失败
	 * */
	int outDateRenew(int uid, int openTime);
	
	/**
	 * 用户店铺uid是否存在
	 * @param uid 用户uid
	 * @return ture 存在 false 不存在
	 */
	boolean userIsExist(int uid);
	/**
	 * 添加店铺缴费信息到临时表
	 * 
	 * @param memberStoreOrder
	 * @return 0 成功 1失败
	 */

	int addMemberStoreOrder(MemberStoreOrder memberStoreOrder);
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
	 * 根据缴费订单临时表查询支付单信息
	 * @param id 店铺支付单id
	 * @return 实体
	 */
	MemberStoreOrder queryStoreOrderById(int id);
	/**
	 * 缴费支付成功 更新店铺订单临时表信息
	 * @param id
	 * @param status
	 * @param paySucTime
	 * @return
	 */
	int updateStoreOrder(int id,int status);
	/**
	 * 根据订单id 和 状态 查询缴费信息表
	 * @param uid  用户uid
	 * @param type 类型
	 * @return 缴费信息表
	 */
	MemberPayCostList qeuryByIdAndType(int uid,int type);
	/**
	 * 回滚后台店铺审核
	 * 后台审核出错时调用
	 * @param uid 用户uid 
	 */
	void rollBackUpdateVerifyStatus(int uid);
}
