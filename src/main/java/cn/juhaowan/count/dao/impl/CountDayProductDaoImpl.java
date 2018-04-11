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
import cn.juhaowan.count.dao.CountDayProductDao;
import cn.juhaowan.count.vo.CountDayProduct;
/**
 * 商品统计
 * @author Administrator
 *
 */
@Repository("CountDayProductDao")
public class CountDayProductDaoImpl extends BaseDaoImplJdbc<CountDayProduct> implements CountDayProductDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public CountDayProduct findById(int id) {
		return findById(CountDayProduct.class, id);
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
		sql.append("from count_day_product where 1 = 1 ");

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
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<CountDayProduct> pageinfo = new PageInfo<CountDayProduct>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		
		pageinfo.setPageno(pageNo);
		
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()){
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<CountDayProduct> cb = new JuRowCallbackHandler<CountDayProduct>(CountDayProduct.class);
		
		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());  
		
		return pageinfo;
	}



	//【商品统计】5.1.1当日有商品上架的用户数量
	@Override
	public List<Map> queryOnSaleUserNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(on_sale_time) AS hor,  COUNT(DISTINCT(user_id)) AS uidcount FROM " 
				+ "product   WHERE 1=1   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and on_sale_time like ");
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
			m.put("idcount", rs.getInt(2));
			mapList.add(m);
		}

		return mapList;
	}



    //【商品统计】5.1.2当日商品上架数量
	@Override
	public List<Map> queryOnSaleNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(on_sale_time) AS hor,  SUM(product_stock) AS stockNum FROM product   WHERE 1=1   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and on_sale_time like ");
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
			m.put("stockNum", rs.getInt(2));
			mapList.add(m);
		}

		return mapList;
	}



    //【商品统计】5.1.3当日商品下架成功数量
	@Override
	public List<Map> queryOffSaleNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(off_shelves_time) AS hor,  SUM(product_stock) AS stockNum  FROM " 
				+ "product WHERE (product_status = 8 OR product_status=9 OR product_status=10 OR" 
				+ "product_status=11 OR product_status=12) ");

		if (map != null && map.get("time") != null) {
			sql.append(" and off_shelves_time like ");
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
			m.put("stockNum", rs.getInt(2));
			mapList.add(m);
		}

		return mapList;
	}




	

	
	
}
