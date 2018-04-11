package cn.juhaowan.count.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayAccountDao;
import cn.juhaowan.count.dao.CountDayActiveDao;
import cn.juhaowan.count.dao.CountDayMoneyIoDao;
import cn.juhaowan.count.dao.CountDayOrderDao;
import cn.juhaowan.count.dao.CountDayProductDao;
import cn.juhaowan.count.dao.CountDayUserDao;
import cn.juhaowan.count.service.CountUserService;

/**
 * 用户统计
 * @author Administrator
 *
 */
@Service("CountUserService")
public class DefaultCountUserService implements CountUserService {

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
	private CountDayOrderDao countDayOrderDao;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	
	
    //1分页查询用户统计
	@Override
	public PageInfo queryDayUserForPage(Map<String, Object> conditions,int pageNo,
			int pageSize, String sort, String order) {
		return countDayUserDao.queryForPage(conditions, pageNo, pageSize, sort, order);
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
	
	
	
}
