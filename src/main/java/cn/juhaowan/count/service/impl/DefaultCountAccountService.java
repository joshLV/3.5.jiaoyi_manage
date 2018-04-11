package cn.juhaowan.count.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayAccountDao;
import cn.juhaowan.count.service.CountAccountService;

/**
 * 用户账号统计
 * @author Administrator
 *
 */
@Service("CountAccountService")
public class DefaultCountAccountService implements CountAccountService {
	@Autowired
	private CountDayAccountDao countDayAccountDao;
	

	//3分页查询账户统计
	@Override
	public PageInfo queryDayAccountForPage(Map<String, Object> conditions,int pageNo, 
			int pageSize, String sort, String order) {
		
		return countDayAccountDao.queryForPage(conditions, pageNo, pageSize, sort, order);
	}

	
	//3.1.1日账户统计【日提现金额 分时段】
	@Override
	public List queryTakeByTime(Map<String, Object> conditions) {
		return countDayAccountDao.queryTakeByTime(conditions);
	}
	
	

	
	
	
	
}
