package cn.juhaowan.count.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.MonthWorkRateStatisticsDao;
import cn.juhaowan.count.service.MonthWorkRateStatisticsService;
import cn.juhaowan.count.vo.MonthWorkRateForm;

/**
 * @author APXer
 * 
 */
@Service("MonthWorkRateStatisticsService")
public class DefaultMonthWorkRateStatisticsService implements
		MonthWorkRateStatisticsService {
	@Autowired
	private MonthWorkRateStatisticsDao monthWorkRateStatisticsDao;

	@Override
	public PageInfo<MonthWorkRateForm> queryMonthWorkRatePageInfo(
			Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		return monthWorkRateStatisticsDao.queryMonthWorkRatePageInfo(
				conditions, pageSize, pageNo, sort, order);
	}

}
