package cn.juhaowan.count.service;

import java.util.List;

import cn.juhaowan.count.vo.StatisticsTop100;

/**
 * @author Administrator
 * 
 */
public interface StatisticsTop100Service {
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
	public static int TOPTYPE_CONSUMPTION = 0;
	public static int TOPTYPE_SALES = 1;

	List<StatisticsTop100> queryTOP100List(String startDate, String endDate,
			String excludeUsers, int topType);
}
