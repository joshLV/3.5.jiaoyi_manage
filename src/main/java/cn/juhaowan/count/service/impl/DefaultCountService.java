package cn.juhaowan.count.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayAccountDao;
import cn.juhaowan.count.dao.CountDayMoneyIoDao;
import cn.juhaowan.count.dao.CountDayProductDao;
import cn.juhaowan.count.dao.CountDayOrderDao;
import cn.juhaowan.count.dao.CountDayUserDao;
import cn.juhaowan.count.dao.CountDayActiveDao;
import cn.juhaowan.count.dao.CountProgramByurlDao;

import cn.juhaowan.count.service.CountService;
import cn.juhaowan.count.vo.CountProgramByurl;


@Service("CountService")
public class DefaultCountService implements CountService {

	@Autowired
	private CountDayUserDao countDayUserDao;
	
	@Autowired
	private CountDayActiveDao countDayActiveDao;
	
	@Autowired
	private CountDayAccountDao countDayAccountDao;
	
	@Autowired
	private CountDayMoneyIoDao countDayMoneyIoDao;
	
	@Autowired 
	private CountDayProductDao countDayProductDao;
	
	@Autowired
	private CountDayOrderDao countDayOrderDao ;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	@Autowired
	private CountProgramByurlDao countProgramByurlDao;
	
    //1分页查询用户统计
	@Override
	public PageInfo queryDayUserForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		return countDayUserDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}
    //2分页查询日活统计
	@Override
	public PageInfo queryDayActiveForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		return countDayActiveDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}

	//3分页查询账户统计
	@Override
	public PageInfo queryDayAccountForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		
		return countDayAccountDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}

	//4分页查询日金额支出统计
	@Override
	public PageInfo queryDayMoneyForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		return countDayMoneyIoDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}
	//5分页查询商品统计
	@Override
	public PageInfo queryDayProductForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		return countDayProductDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}
	//6分页查询日交易统计
	@Override
	public PageInfo queryDayOrderForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		return countDayOrderDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}
	//3.1.1日账户统计【日提现金额 分时段】
	@Override
	public List queryTakeByTime(Map<String, Object> conditions) {
		return countDayAccountDao.queryTakeByTime(conditions);
	}
	
	//1.1.1 用户统计【注册用户数量 分时段】
	@Override
	public List queryRegNumByTime(Map<String, Object> conditions) {
		return countDayUserDao.queryRegNumByTime(conditions);
	}
	
	//1.1.2 用户统计【实名用户数量 分时段】
	@Override
	public List queryRealNumByTime(Map conditions) {
		return countDayUserDao.queryRealNumByTime(conditions);
	}

	//1.1.4 用户统计【注册用户PV 分时段】
	//通用2.1.3日活统计【未注册用户PV 分时段】
	@Override
	public List queryRegPVByTime(Map condition) {
		return countDayUserDao.queryRegPVByTime(condition);
	}
	
	//2.1.4日活统计【未注册用户PV 分时段】
	@Override
	public List queryNoRegPVByTime(Map conditions) {
		return countDayActiveDao.queryNoRegPVByTime(conditions);
	}
	//5.1.1商品统计【当日上架商品的用户数量  分时段】
	@Override
	public List queryOnSaleUserNumByTime(Map conditions) {
		return countDayProductDao.queryOnSaleUserNumByTime(conditions);
	}
	//5.1.2商品统计【当日上架商品数量 分时段】
	@Override
	public List queryOnSaleNumByTime(Map conditions) {
		return countDayProductDao.queryOnSaleNumByTime(conditions);
	}
	//5.1.3商品统计【当日下架商品数量 分时段】
	@Override
	public List queryOffSaleByTime(Map conditions) {
		return countDayProductDao.queryOffSaleNumByTime(conditions);
	}
	
	//6.1.1订单统计【交易成功量 分时段】
	@Override
	public List queryOrderSucNumByTime(Map conditions) {
		return countDayOrderDao.queryOrderSucNumByTime(conditions);
	}
	//6.1.2订单统计【交易失败 分时段】
	@Override
	public List queryOrderFaileNumByTime(Map conditions) {
		return countDayOrderDao.queryOrderFaileNumByTime(conditions);
	}
	//6.1.3订单统计【交易金额 分时段】
	@Override
	public List queryOrderMoneyByTime(Map conditions) {
		return countDayOrderDao.queryOrderMSumByTime(conditions);
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
