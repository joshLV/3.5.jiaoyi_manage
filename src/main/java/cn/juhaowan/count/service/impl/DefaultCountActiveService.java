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
import cn.juhaowan.count.service.CountActiveService;

/**
 * 日活统计
 * @author Administrator
 *
 */
@Service("CountActiveService")
public class DefaultCountActiveService implements CountActiveService {

	@Autowired
	private CountDayUserDao countDayUserDao;
	
	@Autowired
	private CountDayActiveDao countDayActiveDao;
	
	
   
    //2分页查询日活统计
	@Override
	public PageInfo queryDayActiveForPage(Map<String, Object> conditions,int pageNo, 
			int pageSize, String sort, String order) {
		return countDayActiveDao.queryForPage(conditions, pageNo, pageSize, sort, order);
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
	
	
	
	
	
}
