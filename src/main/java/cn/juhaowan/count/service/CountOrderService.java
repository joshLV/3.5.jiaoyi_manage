package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 交易统计接口方法
 * 
 */
public interface CountOrderService {

	

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
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayOrderForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order);

	

	/**
	 * 6.1.1订单统计【交易成功量 分时段】
	 * @param conditions 条件
	 * @return 交易成功量 分时段
	 */
	public List queryOrderSucNumByTime(Map conditions);

	/**
	 * 6.1.2订单统计【交易失败 分时段】
	 * @param conditions 条件
	 * @return 交易失败 分时段
	 */
	public List queryOrderFaileNumByTime(Map conditions);

	/**
	 * 6.1.3订单统计【交易金额 分时段】
	 * @param conditions 条件
	 * @return 交易金额 分时段
	 */
	public List queryOrderMoneyByTime(Map conditions);

}