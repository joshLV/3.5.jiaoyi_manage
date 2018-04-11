package cn.juhaowan.count.dao;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.MonthWorkRateForm;

/**
 * 月工时率统计
 * 
 * @author APXer
 * 
 */
public interface MonthWorkRateStatisticsDao {
	/**
	 * 寄售客服
	 */
	public static final int IS_SERVICE = 1;
	/**
	 * 寄售物服
	 */
	public static final int IS_OBJECT_SERVICE = 2;
	/**
	 * 审核员
	 */
	public static final int IS_AUDITOR = 3;
	/**
	 * 投资客服
	 */
	public static final int IS_INVESTMENT_SERVICE = 4;

	PageInfo<MonthWorkRateForm> queryMonthWorkRatePageInfo(
			Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order);
}
