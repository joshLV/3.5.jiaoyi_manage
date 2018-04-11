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
import cn.juhaowan.count.service.CountOrderService;

/**
 * 交易统计
 * @author Administrator
 *
 */
@Service("CountOrderService")
public class DefaultCountOrderService implements CountOrderService {

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
	
	
	
   
	//6分页查询日交易统计
	@Override
	public PageInfo queryDayOrderForPage(Map<String, Object> conditions,int pageNo, 
			int pageSize, String sort, String order) {
		return countDayOrderDao.queryForPage(conditions, pageNo, pageSize, sort, order);
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
	

	
}
