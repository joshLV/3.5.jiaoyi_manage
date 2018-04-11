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
import cn.juhaowan.count.dao.CountDayMoneyIoDao;
import cn.juhaowan.count.vo.CountDayMoneyIo;
/**
 * 日金额统计
 * @author Administrator
 *
 */
@Repository("CountDayMoneyIoDao")
public class CountDayMoneyIoDaoImpl extends BaseDaoImplJdbc<CountDayMoneyIo> implements CountDayMoneyIoDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public CountDayMoneyIo findById(int id) {
		return findById(CountDayMoneyIo.class, id);
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
		sql.append("from count_day_money_io where 1 = 1 ");

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
		PageInfo<CountDayMoneyIo> pageinfo = new PageInfo<CountDayMoneyIo>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		
		pageinfo.setPageno(pageNo);
		
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()){
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<CountDayMoneyIo> cb = new JuRowCallbackHandler<CountDayMoneyIo>(CountDayMoneyIo.class);
		
		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());  
		
		return pageinfo;
	}



    //1提现人数
	@Override
	public List<Map> queryTakeMemberNumByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT HOUR(create_time) AS hor,COUNT(DISTINCT(uid)) AS uidcount FROM " 
				+ "member_take_money WHERE 1 = 1");

		if (map != null && map.get("time") != null) {
			sql.append(" and create_time like ");
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
			m.put("uidcount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}




    //2提现笔数
	@Override
	public List<Map> queryTakeNumByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(create_time) AS hor,COUNT(id) AS idcount FROM member_take_money WHERE 1 = 1  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and create_time like ");
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



    //3提现成功人数
	@Override
	public List<Map> querytakeMemberSucNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(transfer_time) AS hor,COUNT(DISTINCT(uid)) AS uidcount " 
				+ "FROM member_take_money WHERE STATUS = 3  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and transfer_time like ");
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
			m.put("uidcount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}



    //4提现成功笔数
	@Override
	public List<Map> queryTakeSucNumByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(transfer_time) AS hor,COUNT(id) AS idcount FROM member_take_money WHERE STATUS = 3 ");

		if (map != null && map.get("time") != null) {
			sql.append(" and transfer_time like ");
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



   //5提现金额
	@Override
	public List<Map> queryTakeMoneyDayCountByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(transfer_time) AS hor,SUM(amount) AS amount FROM " 
				+ "member_take_money WHERE STATUS = 3  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and transfer_time like ");
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
			m.put("amount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}



    //6充值人数
	@Override
	public List<Map> queryRechargeMemberNumByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(create_time) AS hor,COUNT(DISTINCT(uid)) AS uidcount FROM " 
				+ "member_account_log WHERE operator_type=1	  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and create_time like ");
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
			m.put("uidcount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}



    //7充值笔数
	@Override
	public List<Map> queryRechargeNumByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(create_time) AS hor,COUNT(DISTINCT(uid)) AS uidcount FROM " 
				+ "member_account_log WHERE operator_type=1	  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and create_time like ");
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
			m.put("uidcount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}



    //8充值金额
	@Override
	public List<Map> queryRechargeMoneyCountByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(create_time) AS hor, SUM(amount) AS amount FROM " 
				+ "member_account_log WHERE operator_type=1  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and create_time like ");
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
			m.put("amount", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}



    //9账户余额
	@Override
	public List<Map> queryAccountAmountByTime(Map conditions) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
