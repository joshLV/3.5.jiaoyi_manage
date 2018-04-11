package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 日金额接口方法
 * 
 */
public interface CountMoneyIoService {
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
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayMoneyForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order);

	
	
	/**
	 * 4.1.1日金额支出统计【提现人数 分时段】
	 * @param conditions 条件
	 * @return 提现人数 分时段
	 */
	List<Map> queryTakeMemberNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.2日金额支出统计【提现笔数 分时段】
	 * @param conditions 条件
	 * @return 提现笔数 分时段
	 */
	List<Map> queryTakeNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.3日金额支出统计【提现成功人数 分时段】
	 * @param conditions 条件
	 * @return 提现成功人数 分时段
	 */
	List<Map> querytakeMemberSucNumByTime(Map conditions);

	/**
	 * 4.1.4日金额支出统计【提现成功笔数 分时段】
	 * @param conditions 条件
	 * @return 提现成功笔数 分时段
	 */
	List<Map> queryTakeSucNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.5日金额支出统计【提现金额 分时段】
	 * @param conditions 条件
	 * @return 提现金额 分时段
	 */
	List<Map> queryTakeMoneyDayCountByTime(Map<String, Object> conditions);

	/**
	 * 4.1.6日金额支出统计【充值人数 分时段】
	 * @param conditions 条件
	 * @return 充值人数 分时段
	 */
	List<Map> queryRechargeMemberNumByTime(Map conditions);

	/**
	 * 4.1.7日金额支出统计【充值笔数 分时段】
	 * @param conditions 条件
	 * @return 充值笔数 分时段
	 */
	List<Map> queryRechargeNumByTime(Map<String, Object> conditions);

	/**
	 * 4.1.8日金额支出统计【充值金额 分时段】
	 * @param conditions 条件
	 * @return 充值金额 分时段
	 */
	List<Map> queryRechargeMoneyCountByTime(Map<String, Object> conditions);

	

}