package cn.juhaowan.member.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberAccountFrozenDao;
import cn.juhaowan.member.vo.MemberAccountFrozen;

/**
 * 资金账户操作类
 * @author cai9911
 *
 */
@Repository("MemberAccountFrozenDao")
public class MemberAccountFrozenDaoImpl implements MemberAccountFrozenDao {
	Logger logger = LoggerFactory.getLogger(MemberAccountFrozenDao.class);
	@Autowired
	private JdbcOperations jdbcOperations;
	
	/**
	 * 读取存储的表名
	 * @param uid
	 * @return
	 */
	public String getTableName(){
		return "member_account_frozen";
	}

	
	@Override
	public int frozen(int uid, int type, String remark) {
		
		//注意， uid在数据库中有唯一索引
		String sql = "INSERT INTO " + this.getTableName() + "(uid, type, remark, update_time, create_time) VALUES (?, ?, ?, now(), now()) " 
				+ " ON DUPLICATE KEY UPDATE remark= ?, update_time=now();";
		
		System.out.println(sql);
		
		try {
			int rtn = jdbcOperations.update(sql, uid, type, remark, remark);
			return rtn > 0 ? 0 : 1;
		} catch (Exception e) {
			return 1;
		}
	}
	

	@Override
	public int frozen(List<Integer> l, int type, String remark) {
		if(l.size() == 0) {
			return 0;
		}
		
		StringBuffer sql = new StringBuffer("INSERT INTO " + this.getTableName() + "(uid, type, remark, update_time, create_time) VALUES ");
		
		
		for(int i = 0, len = l.size(); i < len; i++) {
			if(i != 0) {
				sql.append(",");
			}
			sql.append("(" + l.get(i)  + ", " + type + ", '" + remark + "', now(), now())");
		}
		
		sql.append(" ON DUPLICATE KEY UPDATE update_time=now();");
		
		System.out.println(sql.toString());

		try {
			int rtn = jdbcOperations.update(sql.toString());
			return rtn > 0 ? 0 : 1;
		} catch (Exception e) {
			return 1;
		}
		
	}

	
	@Override
	public int unFrozen(int uid) {
		String sql = "DELETE FROM " + this.getTableName() + " WHERE uid= ?";
		
		try {
			int rtn = jdbcOperations.update(sql, uid);
			return rtn > 0 ? 0 : 1;
		} catch (Exception e) {
			return 1;
		}
	}

	
	@Override
	public int unFrozen(List<Integer> l) {
		
		if(l.size() == 0) {
			return 0;
		}
		
		StringBuffer sql = new StringBuffer("DELETE FROM " + this.getTableName() + " WHERE uid IN (");
		
		for(int i = 0, len = l.size(); i < len; i++) {
			if(i != 0) {
				sql.append(",");
			}
			sql.append(l.get(i));
		}
		
		sql.append(")");

		
		try {
			int rtn = jdbcOperations.update(sql.toString());
			return rtn > 0 ? 0 : 1;
		} catch (Exception e) {
			return 1;
		}
	}

	
	
	@Override
	public boolean isFrozen(int uid) {
		String sql = "SELECT * FROM " + this.getTableName() + " WHERE uid= ? limit 1";
		JuRowCallbackHandler<MemberAccountFrozen> cb = new JuRowCallbackHandler<MemberAccountFrozen>(MemberAccountFrozen.class);
		jdbcOperations.query(sql, cb, uid);
		
		if(cb.getList() != null && cb.getList().size() > 0) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isFrozenPayAndRecharge(int uid) {
		String sql = "SELECT * FROM " + this.getTableName() + " WHERE uid= ?  AND type=? limit 1";
		JuRowCallbackHandler<MemberAccountFrozen> cb = new JuRowCallbackHandler<MemberAccountFrozen>(MemberAccountFrozen.class);
		jdbcOperations.query(sql, cb, uid, MemberAccountFrozenDao.FROZEN_TYPE_PAY_AND_RECHARGE);
		
		if(cb.getList() != null && cb.getList().size() > 0) {
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberAccountFrozen> findWithPage(Map<String, Object> conditions, int pageSize, int pageNo, String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "id";
		}
		if (null == order) {
			order = "ASC";
		}
		sql.append(" FROM " + this.getTableName() + " WHERE 1=1 ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String) conditions.get("uid"))) {
				sql.append(" AND uid = ?");
				conList.add(conditions.get("uid"));
			}
			
			if (StringUtils.isNotBlank((String) conditions.get("beginTime"))) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			
			if (StringUtils.isNotBlank((String) conditions.get("endTime"))) {
				sql.append(" and create_time <= ? ");
				conList.add(conditions.get("endTime"));
			}
		}
		
		sql.append(" ORDER BY " + sort + " " + order);
		String sqlcount = "SELECT COUNT(*)  " + sql.toString();
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<MemberAccountFrozen> pageInfo = new PageInfo<MemberAccountFrozen>(count, pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		// pageInfo.setPageSize(pageSize);

		if (count == 0) {
			return pageInfo;
		}

		if (pageNo <= 0) {
			pageNo = 1;
		}

		if (pageNo > pageInfo.getPageCount()) {
			pageNo = pageInfo.getPageCount();
		}

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0) {
			firstResult = 0;
		}

		String sqlPage = "SELECT * " + sql.append(" LIMIT " + firstResult + "," + pageInfo.getPageSize()).toString();

		JuRowCallbackHandler<MemberAccountFrozen> rowCallbackHandler = new JuRowCallbackHandler<MemberAccountFrozen>(MemberAccountFrozen.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
	}



	
	

}
