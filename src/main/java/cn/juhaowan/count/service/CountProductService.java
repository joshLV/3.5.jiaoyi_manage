package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 商品统计接口方法
 * 
 */
public interface CountProductService {

	

	/**
	 * 5分页查询商品统计
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo queryDayProductForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order);

	

	/**
	 * 5.1.1商品统计【上架商品的用户数量 分时段】
	 * @param conditions 条件
	 * @return 上架商品的用户数量 分时段
	 */
	public List queryOnSaleUserNumByTime(Map conditions);

	/**
	 * 5.1.2商品统计【上架成功商品数量 分时段】
	 * @param conditions 条件
	 * @return 上架成功商品数量 分时段
	 */
	public List queryOnSaleNumByTime(Map conditions);

	/**
	 * 5.1.3商品统计【日下架商品数量 分时段】
	 * @param conditions 条件
	 * @return 日下架商品数量 分时段
	 */
	public List queryOffSaleByTime(Map conditions);

	

}