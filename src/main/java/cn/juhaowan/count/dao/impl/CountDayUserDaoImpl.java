package cn.juhaowan.count.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayUserDao;
import cn.juhaowan.count.vo.CountDayUser;
/**
 * 用户统计
 * @author Administrator
 *
 */
@Repository("CountDayUserDao")
public class CountDayUserDaoImpl extends BaseDaoImplJdbc<CountDayUser> implements CountDayUserDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public CountDayUser findById(int id) {
		return findById(CountDayUser.class, id);
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
		// sql.append("from (SELECT u.*,a.reg_uv regUV,a.reg_pv regPV FROM count_day_user u ,
		//count_day_active a WHERE   DATE_FORMAT(u.count_day,'%Y-%m-%d') =  
		//DATE_FORMAT(a.count_day,'%Y-%m-%d'))bb where 1 = 1 ");
		sql.append(" FROM count_day_user where 1=1 ");
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
		sqlCount = "select count(id) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<CountDayUser> pageinfo = new PageInfo<CountDayUser>(count,
				pageSize);
		if (count == 0){
			return pageinfo;
		}

		pageinfo.setPageno(pageNo);

		if (pageNo <= 0){
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()){
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();

		SqlRowSet rs = jdbcOperations.queryForRowSet(sqlPage,
				conList.toArray());
		List<CountDayUser> list = new ArrayList<CountDayUser>();

		while (rs.next()) {

			CountDayUser cdu = new CountDayUser();
			cdu.setId(rs.getInt(1));
			cdu.setRegNum(rs.getInt(2));
			cdu.setRealNum(rs.getInt(3));
			cdu.setCountDay(rs.getDate(4));
			cdu.setTotalNum(rs.getInt(5));
			cdu.setRealTotal(rs.getInt(6));
			//cdu.setRegUv(rs.getInt(5));
			//cdu.setRegPv(rs.getInt(6));
			list.add(cdu);
		}
		pageinfo.setItems(list);  
		
		return pageinfo;
	}



    //日注册用户 分时段
	@Override
	public List queryRegNumByTime(Map<String, Object> map) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(m1.create_time) AS hor,  COUNT(m1.uid) AS idcount FROM member m1  WHERE  1 = 1  ");

		if (map != null && map.get("time") != null) {
			sql.append(" and m1.create_time like ");
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



   //日用户实名 分时段(5 状态 为通过实名认证)
	@Override
	public List<Map> queryRealNumByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT HOUR(m1.approve_time) AS hor,  COUNT(m1.uid) AS idcount FROM " 
				+ "member_realinfo m1  WHERE  m1.status = 5   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and m1.approve_time like ");
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



    //注册用户PV 分时段
	@Override
	public List<Map> queryRegPVByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT view_time AS hor,login_pv AS loginPv FROM count_program_bytime WHERE 1 = 1   ");

		if (map != null && map.get("time") != null) {
			sql.append(" and count_time like ");
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
			m.put("loginPv", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}
	
	
	

}
