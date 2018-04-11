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
import cn.juhaowan.count.dao.CountDayActiveDao;
import cn.juhaowan.count.vo.CountDayActive;
/**
 * 日活统计
 * @author Administrator
 *
 */
@Repository("CountDayActiveDao")
public class CountDayActiveDaoImpl extends BaseDaoImplJdbc<CountDayActive> implements CountDayActiveDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public CountDayActive findById(int id) {
		return findById(CountDayActive.class, id);
	}

	


	@Override
	public PageInfo queryForPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order) {
	
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;
		
		if(null == sort){
			sort = "id";
		}
		if(null == order){
			order = "asc";
		}
		sql.append("from count_day_active where 1 = 1 ");
		
		if(null != conditions){
	
	
			if(null != conditions.get("beginTime")){
				sql.append(" and count_day >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if(null != conditions.get("endTime")){
				sql.append(" and count_day <=  ? ");
				conList.add(conditions.get("endTime"));
			}

	
		}
		
		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<CountDayActive> pageinfo = new PageInfo<CountDayActive>(count,pageSize);
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
		JuRowCallbackHandler<CountDayActive> cb = new JuRowCallbackHandler<CountDayActive>(CountDayActive.class);
		
		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());  
		
		return pageinfo;
	}




	//当日未注册用户PV
	@Override
	public List<Map> queryNoRegPVByTime(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT view_time AS hor,noLogin_pv AS noLoginPv FROM count_program_bytime WHERE 1 = 1   ");

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
			m.put("noLoginPv", rs.getInt(2));
		
			mapList.add(m);
		}

		return mapList;
	}
	
	
	

}
