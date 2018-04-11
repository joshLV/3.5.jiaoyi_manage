package cn.juhaowan.count.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import org.springframework.stereotype.Service;


import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayAccountDao;
import cn.juhaowan.count.dao.CountDayMoneyIoDao;
import cn.juhaowan.count.dao.CountDayProductDao;
import cn.juhaowan.count.dao.CountDayOrderDao;
import cn.juhaowan.count.dao.CountDayUserDao;
import cn.juhaowan.count.dao.CountDayActiveDao;

import cn.juhaowan.count.service.CountMoneyIoService;


/**
 * 日金额统计
 * @author Administrator
 *
 */
@Service("CountMoneyIoService")
public class DefaultCountMoneyIoService implements CountMoneyIoService {

	@Autowired
	private CountDayMoneyIoDao countDayMoneyIoDao;
 

	//4分页查询日金额支出统计
	@Override
	public PageInfo queryDayMoneyForPage(Map<String, Object> conditions,int pageNo, 
			int pageSize, String sort, String order) {
		return countDayMoneyIoDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}
	
	
	
	//4.1.1日金额支出统计【提现人数 分时段】
	@Override
	public List<Map> queryTakeMemberNumByTime(Map<String, Object> conditions) {
		return countDayMoneyIoDao.queryTakeMemberNumByTime(conditions);
	}
	//4.1.2日金额支出统计【提现笔数 分时段】
	@Override
	public List<Map> queryTakeNumByTime(Map<String, Object> conditions) {
		return countDayMoneyIoDao.queryTakeNumByTime(conditions);
	}
	// 4.1.3日金额支出统计【提现成功人数 分时段】
	@Override
	public List<Map> querytakeMemberSucNumByTime(Map conditions) {
		return countDayMoneyIoDao.querytakeMemberSucNumByTime(conditions);
	}
	//4.1.4日金额支出统计【提现成功笔数 分时段】
	@Override
	public List<Map> queryTakeSucNumByTime(Map<String, Object> conditions) {
		return countDayMoneyIoDao.queryTakeSucNumByTime(conditions);
	}
	//4.1.5日金额支出统计【提现金额 分时段】
	@Override
	public List<Map> queryTakeMoneyDayCountByTime(Map<String, Object> conditions) {
		return countDayMoneyIoDao.queryTakeMoneyDayCountByTime(conditions);
	}
	//4.1.6日金额支出统计【充值人数 分时段】
	@Override
	public List<Map> queryRechargeMemberNumByTime(Map conditions) {
		return countDayMoneyIoDao.queryRechargeMemberNumByTime(conditions);
	}
	//4.1.7日金额支出统计【充值笔数 分时段】
	@Override
	public List<Map> queryRechargeNumByTime(Map<String, Object> conditions) {
		return countDayMoneyIoDao.queryRechargeNumByTime(conditions);
	}
	//4.1.8日金额支出统计【充值金额 分时段】
	@Override
	public List<Map> queryRechargeMoneyCountByTime(Map<String, Object> conditions) {
		return null;
	}

	
	
	
	
}
