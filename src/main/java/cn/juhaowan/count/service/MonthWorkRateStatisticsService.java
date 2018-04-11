package cn.juhaowan.count.service;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.MonthWorkRateForm;

/**
 * 月工时率统计
 * 
 * @author APXer
 * 
 */
public interface MonthWorkRateStatisticsService {
	PageInfo<MonthWorkRateForm> queryMonthWorkRatePageInfo(Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order);
}
