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
import cn.juhaowan.count.service.CountProductService;

/**
 * 商品统计
 * @author Administrator
 *
 */
@Service("CountProductService")
public class DefaultCountProductService implements CountProductService {

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
	
	
	
   
	//5分页查询商品统计
	@Override
	public PageInfo queryDayProductForPage(Map<String, Object> conditions,int pageNo, 
			int pageSize, String sort, String order) {
		return countDayProductDao.queryForPage(conditions, pageNo, pageSize, sort, order);
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
	
	
	
}
