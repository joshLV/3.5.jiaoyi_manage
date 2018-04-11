package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberTakeMoneyLogDao;
import cn.juhaowan.member.vo.MemberTakeMoneyLog;


@SuppressWarnings("rawtypes")
@Repository("MemberTakeMoneyLogDao")
public class MemberTakeMoneyLogDaoImpl extends BaseDaoImplJdbc implements MemberTakeMoneyLogDao {

	//private static final Logger log = LoggerFactory.getLogger(MemberTakeMoneyLogDaoImpl.class);
	
	@Autowired
	private JdbcOperations jdbcOperations;



	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberTakeMoneyLog> findLogWithPage(Map<String, Object> conditions,int pageSize, int pageNo, String sort, String order) {
		
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "uid";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from member_take_money_log where 1=1 ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String) conditions.get("order_id"))) {
				sql.append(" and order_id = ? ");
				conList.add(conditions.get("order_id"));
			}

			if (StringUtils.isNotBlank((String) conditions.get("operator_uid"))) {
				sql.append(" and operator_uid = ? ");
				conList.add(conditions.get("operator_uid"));
			}
			if (StringUtils.isNotBlank((String) conditions.get("take_money_id"))) {
				sql.append(" and take_money_id = ? ");
				conList.add(conditions.get("take_money_id"));
			}

			if (StringUtils.isNotBlank((String)conditions.get("beginTime"))) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
	        if (StringUtils.isNotBlank((String)conditions.get("endTime"))) {
	        	sql.append(" and create_time <= ? ");
				conList.add(conditions.get("endTime"));
			}

		
		}
		sql.append(" order by " + sort + " " + order);
		String sqlcount = "select count(*)  " + sql.toString();
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<MemberTakeMoneyLog> pageInfo = new PageInfo<MemberTakeMoneyLog>(count,pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		//pageInfo.setPageSize(pageSize);

		if(count == 0) {
            return pageInfo;
        }

		if(pageNo <= 0) {
            pageNo = 1;
        }

		if(pageNo > pageInfo.getPageCount()) {
            pageNo = pageInfo.getPageCount();
        }

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if(firstResult < 0) {
            firstResult = 0;
        }

		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();
 
		JuRowCallbackHandler<MemberTakeMoneyLog> rowCallbackHandler = new JuRowCallbackHandler<MemberTakeMoneyLog>(MemberTakeMoneyLog.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
	}
	
	

	
}
