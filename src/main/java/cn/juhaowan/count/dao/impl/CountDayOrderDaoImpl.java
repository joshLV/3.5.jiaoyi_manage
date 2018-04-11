package cn.juhaowan.count.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayOrderDao;
import cn.juhaowan.count.vo.CountDayOrder;
/**
 * 交易统计
 * @author Administrator
 *
 */
@Repository("CountDayOrderDao")
public class CountDayOrderDaoImpl extends BaseDaoImplJdbc<CountDayOrder> implements CountDayOrderDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public CountDayOrder findById(int id) {
		return findById(CountDayOrder.class, id);
	}

	


	@Override
	public PageInfo queryForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order) {
	
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "id";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from count_day_order where 1 = 1 ");

		if (null != conditions) {

			if (null != conditions.get("beginTime")) {
				sql.append(" and count_day >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if (null != conditions.get("endTime")) {
				sql.append(" and count_day <=  ? ");
				conList.add(conditions.get("endTime"));
			}

		}

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		//count = jdbcTemplate.queryForInt(sqlCount,conList.toArray());
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<CountDayOrder> pageinfo = new PageInfo<CountDayOrder>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		
		pageinfo.setPageno(pageNo);
		
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if(pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<CountDayOrder> cb = new JuRowCallbackHandler<CountDayOrder>(CountDayOrder.class);
		
		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());  
		
		return pageinfo;
	}



    //【订单统计】6.1.1 交易成功量
	@Override
	public List<Map> queryOrderSucNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(order_pay_success_time) AS hor,  COUNT(DISTINCT(order_id)) AS oidcount FROM " 
				+ "orders WHERE order_status='6'   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and order_pay_success_time like ");
			sql.append("'%" + map.get("time") + "%'");
		}

		sql.append(" GROUP BY hor ");
		//System.out.println(sql.toString());
		List<Map> mapList = new ArrayList<Map>();


		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());

		while (rs.next()) {
			Map m = new HashMap<String, Object>();
			m.put("hor", rs.getInt(1));
			m.put("oidcount", rs.getInt(2));
			mapList.add(m);
		}

		return mapList;
	}



    //【订单统计】6.1.2 交易失败量
	@Override
	public List<Map> queryOrderFaileNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(modify_time) AS hor,  COUNT(DISTINCT(order_id)) AS oidcount FROM" 
				+ " orders WHERE order_status='8'   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and modify_time like ");
			sql.append("'%" + map.get("time") + "%'");
		}

		sql.append(" GROUP BY hor ");
		//System.out.println(sql.toString());
		List<Map> mapList = new ArrayList<Map>();


		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());

		while (rs.next()) {
			Map m = new HashMap<String, Object>();
			m.put("hor", rs.getInt(1));
			m.put("oidcount", rs.getInt(2));
			mapList.add(m);
		}

		return mapList;
	}



	//【订单统计】6.1.3 交易金额
	@Override
	public List<Map> queryOrderMSumByTime(Map map) {

				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT HOUR(order_pay_success_time) AS hor,  SUM(order_amount) AS order_amount FROM " 
						+ "orders WHERE order_status='6'   ");

				if (map != null && map.get("time") != null) {
					sql.append(" and order_pay_success_time like ");
					sql.append("'%" + map.get("time") + "%'");
				}

				sql.append(" GROUP BY hor ");
				//System.out.println(sql.toString());
				List<Map> mapList = new ArrayList<Map>();


				// 查询的结果集
				SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());

				while (rs.next()) {
					Map m = new HashMap<String, Object>();
					m.put("hor", rs.getInt(1));
					m.put("order_amount", rs.getFloat(2));
					mapList.add(m);
				}

				return mapList;
			}

}
