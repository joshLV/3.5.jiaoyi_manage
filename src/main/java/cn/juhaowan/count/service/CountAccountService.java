package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 账户统计接口方法
 * 
 */
public interface CountAccountService {

	

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
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayAccountForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	

	/**
	 * 3.1.1日账户统计【日提现金额 分时段】
	 * @param conditions 条件
	 * @return 日提现金额 分时段
	 */
	List<Map> queryTakeByTime(Map<String, Object> conditions);

	

}