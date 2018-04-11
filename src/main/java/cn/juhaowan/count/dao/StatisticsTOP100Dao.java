package cn.juhaowan.count.dao;

import java.util.List;

import cn.juhaowan.count.vo.StatisticsTop100;

/**
 * 消费|销售Top100_DAO
 * 
 * @author APXer
 * 
 */
public interface StatisticsTOP100Dao {
	/**
	 * 某时段某TOP100榜
	 * 
	 * @param startDate
	 *            开始用户
	 * @param endDate
	 *            结束用户
	 * @param excludeUsers
	 *            排除用户
	 * @param topType
	 *            0:消费TOP100榜,1:销售TOP100榜
	 * @return
	 */

	List<StatisticsTop100> queryTOP100List(String startDate, String endDate,
			String excludeUsers, int topType);
}
