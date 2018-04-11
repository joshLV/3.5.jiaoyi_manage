package cn.juhaowan.count.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountDayAccountDao;
import cn.juhaowan.count.vo.CountDayAccount;
/**
 * 账户统计
 * @author Administrator
 *
 */
@Repository("CountDayAccountDao")
public class CountDayAccountDaoImpl extends BaseDaoImplJdbc<CountDayAccount>
		implements CountDayAccountDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public CountDayAccount findById(int id) {
		return findById(CountDayAccount.class, id);
	}

	@Override
	public PageInfo queryForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order) {

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
		sql.append("from count_day_account where 1 = 1 ");

		if (null != conditions) {

			if (null != conditions.get("beginTime")) {
				sql.append(" and count_day >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if (null != conditions.get("endTime")) {
				sql.append(" and count_day <= ? ");
				conList.add(conditions.get("endTime"));
			}

		}

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<CountDayAccount> pageinfo = new PageInfo<CountDayAccount>(count, pageSize);
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
		JuRowCallbackHandler<CountDayAccount> cb = new JuRowCallbackHandler<CountDayAccount>(CountDayAccount.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@Override
	public List queryTakeByTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select HOUR(m1.`verify_time`) AS hor, ");
		sql.append(" sum(m1.amount) as amount");
		sql.append(" from member_take_money m1  ");
		sql.append(" where m1.STATUS='1' ");
		if (map != null && map.get("time") != null) {
			sql.append(" and m1.verify_time like ");
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
			m.put("amount", rs.getFloat(2));
			mapList.add(m);
		}

		return mapList;

	}

}
