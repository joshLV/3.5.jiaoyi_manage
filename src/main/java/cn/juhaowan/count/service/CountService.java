package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 接口方法
 * 
 * @pdOid 512c96f6-7862-413d-989c-89f76d2060f2
 */
public interface CountService {

	/**
	 * 1分页查询用户统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayUserForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 2分页查询日活统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayActiveForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 3分页查询账户统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayAccountForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 4分页查询日金额支出统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayMoneyForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 5分页查询商品统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayProductForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 6分页查询交易统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayOrderForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 3.1.1日账户统计【日提现金额 分时段】
	 */
	List<Map> queryTakeByTime(Map<String, Object> conditions);

	/**
	 * 1.1.1 用户统计【注册用户数量 分时段】
	 * 
	 * @param conditions
	 * @return
	 */
	public List queryRegNumByTime(Map<String, Object> conditions);

	/**
	 * 1.1.2 用户统计【实名用户数量 分时段】
	 */
	public List queryRealNumByTime(Map condition);

	/**
	 * 2.1.3 日活统计 1.1.4 用户统计【注册用户PV 分时段】
	 */
	public List queryRegPVByTime(Map condition);

	/**
	 * 2.1.4日活统计【非注册用户PV 分时段】
	 */
	public List queryNoRegPVByTime(Map conditions);
	
	/**
	 * 4.1.1日金额支出统计【提现人数 分时段】
	 */
	List<Map> queryTakeMemberNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.2日金额支出统计【提现笔数 分时段】
	 */
	List<Map> queryTakeNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.3日金额支出统计【提现成功人数 分时段】
	 */
	List<Map> querytakeMemberSucNumByTime(Map conditions);

	/**
	 * 4.1.4日金额支出统计【提现成功笔数 分时段】
	 */
	List<Map> queryTakeSucNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.5日金额支出统计【提现金额 分时段】
	 */
	List<Map> queryTakeMoneyDayCountByTime(Map<String, Object> conditions);

	/**
	 * 4.1.6日金额支出统计【充值人数 分时段】
	 */
	List<Map> queryRechargeMemberNumByTime(Map conditions);

	/**
	 * 4.1.7日金额支出统计【充值笔数 分时段】
	 */
	List<Map> queryRechargeNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.8日金额支出统计【充值金额 分时段】
	 */
	List<Map> queryRechargeMoneyCountByTime(Map<String, Object> conditions);

	/**
	 * 5.1.1商品统计【上架商品的用户数量 分时段】
	 */
	public List queryOnSaleUserNumByTime(Map conditions);

	/**
	 * 5.1.2商品统计【上架成功商品数量 分时段】
	 */
	public List queryOnSaleNumByTime(Map conditions);

	/**
	 * 5.1.3商品统计【日下架商品数量 分时段】
	 */
	public List queryOffSaleByTime(Map conditions);

	/**
	 * 6.1.1订单统计【交易成功量 分时段】
	 */
	public List queryOrderSucNumByTime(Map conditions);

	/**
	 * 6.1.2订单统计【交易失败 分时段】
	 */
	public List queryOrderFaileNumByTime(Map conditions);

	/**
	 * 6.1.3订单统计【交易金额 分时段】
	 */
	public List queryOrderMoneyByTime(Map conditions);

}