package cn.juhaowan.count.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.juhaowan.count.dao.StatisticsTOP100Dao;
import cn.juhaowan.count.service.StatisticsTop100Service;
import cn.juhaowan.count.vo.StatisticsTop100;

/**
 * 统计销售/消费TOP100榜
 * 
 * @author APXer
 * 
 */
@Service("StatisticsTop100Service")
public class DefaultStatisticsTOP100Service implements StatisticsTop100Service {
	@Autowired
	private StatisticsTOP100Dao statisticsTOP100Dao;

	@Override
	public List<StatisticsTop100> queryTOP100List(String startDate,
			String endDate, String excludeUsers, int topType) {
		return statisticsTOP100Dao.queryTOP100List(startDate, endDate,
				excludeUsers, topType);
	}

}
