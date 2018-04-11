package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayAccount;

/**
 * 接口方法
 * 
 */
public interface CountUserService {

	/**
	 * 1分页查询用户统计
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
	PageInfo queryDayUserForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order);

	

	

	/**
	 * 1.1.1 用户统计【注册用户数量 分时段】
	 * 
	 * @param conditions 条件
	 * @return 注册用户数量 分时段
	 */
	public List queryRegNumByTime(Map<String, Object> conditions);

	/**
	 * 1.1.2 用户统计【实名用户数量 分时段】
	 * @param condition 条件
	 * @return 实名用户数量 分时段
	 */
	public List queryRealNumByTime(Map condition);

	/**
	 * 2.1.3 日活统计 1.1.4 用户统计【注册用户PV 分时段】
	 * @param condition 条件
	 * @return 注册用户PV 分时段
	 */
	public List queryRegPVByTime(Map condition);

	
}