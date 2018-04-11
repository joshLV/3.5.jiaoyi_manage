package cn.juhaowan.customer.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.customer.dao.OnlineCustomerMonitorDao;
import cn.juhaowan.customer.vo.CustomerStatusLog;
import cn.juhaowan.customer.vo.WorkRate;
import cn.juhaowan.customer.vo.WorkRateForm;
import cn.juhaowan.customer.vo.WorkStatus;

@Service("OnlineCustomerMonitorDao")
public class DefaultSOnlineCustomerMonitorDaoImpl implements
		OnlineCustomerMonitorDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	// ###############################sys_userinfo
	// 管理######################################
	/**
	 * 查询客服类型
	 * 
	 * @param customerId
	 *            客服ID
	 * @return
	 */
	public int queryCustomerType(int customerId) {
		String sql = "select is_customer,is_object_customer,is_investment_service from sys_userinfo where user_id=?";
		SqlRowSet rs = jdbcOperations
				.queryForRowSet(sql.toString(), customerId);
		int customerType = -1;
		while (rs.next()) {
			int isCustomer = rs.getInt("is_customer");
			int isObjectCustomer = rs.getInt("is_object_customer");
			int isInvestmentService = rs.getInt("is_investment_service");
			customerType = judgeCustomerType(isCustomer, isObjectCustomer,isInvestmentService);
		}
		return customerType;
	}

	public int judgeCustomerType(int isCustomer, int isObjectCustomer,int isInvestmentService) {
		int customerType = -1;
		if (isCustomer == 1 && isObjectCustomer == 1) {// 既是客服也是物服(审核员)
			customerType = IS_AUDITOR;
		} else if (isCustomer == 1) {// 是客服
			customerType = IS_CUSTOMER;
		} else if (isObjectCustomer == 1) {// 是物服
			customerType = IS_OBJECT_CUSTOMER;
		} else if (isInvestmentService == 1) {
			customerType = IS_INVESTMENT_SERVICE;
		} else if (isInvestmentService == 2){
			customerType = 4;
		}
		return customerType;
	}

	/**
	 * 判断客服类型
	 * 
	 * @param isCustomer
	 *            是否是客服(0：不是，1：是）
	 * @param isObjectCustomer
	 *            是否是物服(0：不是，1：是）
	 * @return -1:既不是客服也不是物服 ，IS_AUDITOR 既是客服也是物服(审核员)，IS_CUSTOMER
	 *         是客服，IS_OBJECT_CUSTOMER 是物服
	 */
	public int judgeCustomerType(int isCustomer, int isObjectCustomer) {
		int customerType = -1;
		if (isCustomer != 0 && isObjectCustomer != 0) {// 既是客服也是物服(审核员)
			customerType = IS_AUDITOR;
		} else if (isCustomer != 0) {// 是客服
			customerType = IS_CUSTOMER;
		} else if (isObjectCustomer != 0) {// 是物服
			customerType = IS_OBJECT_CUSTOMER;
		}
		return customerType;
	}

	// ###############################工时率管理######################################
	@Override
	public PageInfo<WorkRateForm> queryWorkRatePageInfo(
			Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		PageInfo<WorkRateForm> workRateFormPageInfo = null;
		// TODO 统计显示
		if (null == sort || ("").equals(sort)) {
			sort = "on_duty";
		}
		if (null == order) {
			order = "ASC";
		}
		String sql = "select user_id,loginid,fullname,custom_service_id,is_customer,is_object_customer from sys_userinfo";
		StringBuffer sqlBody = new StringBuffer();
		if (null != conditions) {
			if (null != conditions.get("customerType")) {// 客服/物服/审核员
				Integer customerType = (Integer) conditions.get("customerType");
				if (customerType == IS_CUSTOMER) {
					sqlBody.append(" WHERE is_customer !=0 ");
				} else if (customerType == IS_OBJECT_CUSTOMER) {
					sqlBody.append(" WHERE is_object_customer !=0 ");
				} else if (customerType == IS_AUDITOR) {
					sqlBody.append(" WHERE is_customer !=0 AND is_object_customer !=0 ");
				} 
			} else {
				sqlBody.append(" WHERE (is_customer !=0 OR is_object_customer !=0) ");
			}
			if (null != conditions.get("fullname")) {// 姓名
				sqlBody.append(" and fullname like '");
				sqlBody.append("%" + conditions.get("fullname") + "%'");
			}
			if (null != conditions.get("postNo")) {// 岗位号
				sqlBody.append(" and custom_service_id like '");
				sqlBody.append("%" + conditions.get("postNo") + "%'");
			}
		}
		// 所有符合条件的工时率
		StringBuffer sqlBodyWrokRate = new StringBuffer();
		List<Object> conditionWorkRate = new ArrayList<Object>();
		sqlBodyWrokRate
				.append("SELECT * FROM work_rate AS wr,("
						+ sql
						+ sqlBody.toString()
						+ ") AS su WHERE su.user_id=wr.cs_id AND off_duty IS NOT NULL ");
		if (null != conditions) {// 是否有时间段条件
			if (null != conditions.get("fromDays")) {// 从...
				sqlBodyWrokRate.append("AND days BETWEEN ?");
				conditionWorkRate.add(conditions.get("fromDays"));
				if (null == conditions.get("toDays")) {// 到...为止
					sqlBodyWrokRate
							.append(" AND date_format(now(),'%Y-%m-%d') ");
				}
			}
			if (null != conditions.get("toDays")) {// 到...为止
				sqlBodyWrokRate.append(" AND ?");
				conditionWorkRate.add(conditions.get("toDays"));
			}
		}

		sqlBodyWrokRate.append(" order by " + sort + " " + order);
		String countSQL = sqlBodyWrokRate.toString().replace("*", "count(*)");
		int total = 0;
		if (conditionWorkRate.size() > 0) {
			total = jdbcOperations.queryForInt(countSQL,
					conditionWorkRate.toArray());
		} else {
			total = jdbcOperations.queryForInt(countSQL);
		}
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		sqlBodyWrokRate.append(" limit " + startNum + "," + pageSize);
		SqlRowSet wrfRS = //
		jdbcOperations.queryForRowSet(sqlBodyWrokRate.toString(),
				conditionWorkRate.toArray());
		List<WorkRateForm> workRateForms = new ArrayList<WorkRateForm>();
		if (wrfRS != null) {
			while (wrfRS.next()) {
				WorkRateForm wrf = new WorkRateForm();
				int userId = wrfRS.getInt("user_id");
				String loginId = wrfRS.getString("loginid");
				String fullname = wrfRS.getString("fullname");
				int isCustomer = wrfRS.getInt("is_customer");
				int isObjectCustomer = wrfRS.getInt("is_object_customer");
				int customerType = judgeCustomerType(isCustomer,
						isObjectCustomer);
				Date days = wrfRS.getDate("days");
				Double workRate = wrfRS.getDouble("workrate");
				Date onDuty = wrfRS.getDate("on_duty");
				Date offDuty = wrfRS.getDate("off_duty");
				int effectiveTime = wrfRS.getInt("effective_time");
				String postNo = wrfRS.getString("custom_service_id");
				wrf.setId(wrfRS.getInt("id"));
				wrf.setCsId(userId);
				wrf.setLoginName(loginId);
				wrf.setPostNo(postNo);
				wrf.setFullname(fullname);
				wrf.setCsType(customerType);
				wrf.setDays(days);
				wrf.setOnDuty(onDuty);
				wrf.setOffDuty(offDuty);
				wrf.setEffectiveTime(effectiveTime);
				wrf.setWorkRate(workRate);
				workRateForms.add(wrf);
			}
		}
		workRateFormPageInfo = new PageInfo<WorkRateForm>(total, pageSize);
		workRateFormPageInfo.setItems(workRateForms);
		return workRateFormPageInfo;
	}

	@Override
	public int onDuty(int customerId, int customerType) {
		int idWR = findIsOnDutyDay(customerId, customerType);
		if (idWR > 0) {
			String sql = "update work_rate set off_duty=null where cs_id=? and cs_type=? and days=DATE_FORMAT(now(),'%Y-%m-%d')";
			return jdbcOperations.update(sql, customerId, customerType);
		} else {
			String sql = "insert work_rate(cs_id,days,on_duty,cs_type) values(?,now(),now(),?)";
			int i = jdbcOperations.update(sql, customerId, customerType);
			return i;
		}

	}

	/**
	 * 今天是否上过班
	 * 
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	public int findIsOnDutyDay(int customerId, int customerType) {
		String sql = "update work_rate set cs_id=? where cs_id=? and cs_type=? and days=DATE_FORMAT(now(),'%Y-%m-%d')";
		int i = jdbcOperations
				.update(sql, customerId, customerId, customerType);
		return i;
	}

	public int offDuty(int id) {
		String sql = "update work_rate set off_duty=now() where id=?";
		return jdbcOperations.update(sql, id);
	}

	public WorkRate findNewOnDutyLogByCustomerId(int customerId) {
		JuRowCallbackHandler<WorkRate> callback = new JuRowCallbackHandler<WorkRate>(
				WorkRate.class);
		String sql = "select * from work_rate where cs_id=? and off_duty is null and DATEDIFF(now(),on_duty)<=1 order by id desc LIMIT 0,1";
		jdbcOperations.query(sql, callback, customerId);
		WorkRate wr = null;
		if (callback.getList() != null && callback.getList().size() > 0) {
			wr = callback.getList().get(0);
		}
		return wr;
	}

	public WorkRate findById(int id) {
		JuRowCallbackHandler<WorkRate> callback = new JuRowCallbackHandler<WorkRate>(
				WorkRate.class);
		String sql = "select * from work_rate where id=?";
		jdbcOperations.query(sql, callback, id);
		WorkRate wr = null;
		if (callback.getList() != null && callback.getList().size() > 0) {
			wr = callback.getList().get(0);
		}
		return wr;
	}

	public int statisticsWorkRate(int customerId, int effectiveTime,
			double workrate) {
		String sql = "update work_rate set effective_time =?,workrate=? where id=?";
		return jdbcOperations.update(sql, effectiveTime, workrate, customerId);
	}

	// ###############################状态变更日志管理######################################
	@Override
	public int updateCurrentWorkStatus(int customerId, int workStatus) {
		String sql = "update sys_userinfo set online_status=? where user_id=?";
		int i = jdbcOperations.update(sql, workStatus, customerId);
		return i;
	}

	@Override
	public int addWorkStatusUpdateLog(int customerId, int workStatus) {
		String sql = "insert customer_status_log(cs_id,work_status,update_time) values(?,?,now())";
		int i = jdbcOperations.update(sql, customerId, workStatus);
		return i;
	}

	@Override
	public List<CustomerStatusLog> findById(int customerId, String updateDate) {
		if (updateDate == null) {
			return Collections.emptyList();
		}
		String sqlQuery = "select * from customer_status_log where cs_id=? and DATE_FORMAT('?','%Y-%m-%d') order by update_time ASC;";
		JuRowCallbackHandler<CustomerStatusLog> callback = new JuRowCallbackHandler<CustomerStatusLog>(
				CustomerStatusLog.class);
		jdbcOperations.query(sqlQuery, new Object[] { customerId, updateDate },
				callback);
		List<CustomerStatusLog> returnList = callback.getList();
		return returnList;
	}

	@Override
	public List<CustomerStatusLog> queryTodayStatusLog(int customerId,
			Date onDutyTime, Date offDutyTime) {
		String sqlQuery = "select * from customer_status_log where cs_id=? and update_time between ? and ? order by update_time ASC;";
		JuRowCallbackHandler<CustomerStatusLog> callback = new JuRowCallbackHandler<CustomerStatusLog>(
				CustomerStatusLog.class);
		jdbcOperations.query(sqlQuery, new Object[] { customerId, onDutyTime,
				offDutyTime }, callback);
		List<CustomerStatusLog> returnList = callback.getList();
		return returnList;
	}

	// **************************************工作状态类型管理*********************************************
	@Override
	public int addWorkStatusCategory(String categoryName, int weight) {
		String sql = "insert work_status(name,weight) values(?,?)";
		int i = jdbcOperations.update(sql, categoryName, weight);
		return i;
	}

	@Override
	public int modifyWorkStatusCategory(int categoryID, String categoryName,
			int categoryWeight) {
		String sql = "update work_status set name=?,weight=? where id=?";
		int effectRow = jdbcOperations.update(sql, categoryName,
				categoryWeight, categoryID);
		if (effectRow > 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteWorkStatusCategory(int categoryID, boolean isForce) {
		String sql = "delete from work_status where id = ?";
		int effectRow = jdbcOperations.update(sql, categoryID);
		if (effectRow > 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<WorkStatus> queryCategoryList(String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from work_status order by " + sort + " "
				+ order;
		JuRowCallbackHandler<WorkStatus> callback = new JuRowCallbackHandler<WorkStatus>(
				WorkStatus.class);
		jdbcOperations.query(sqlQuery, callback);
		List<WorkStatus> returnList = callback.getList();
		return returnList;
	}

	@Override
	public PageInfo<WorkStatus> queryCategoryList(int pageSize, int pageNo,
			String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from work_status";
		String sqlcount = "select count(*) " + sql;
		int rowcount = jdbcOperations.queryForInt(sqlcount);
		PageInfo<WorkStatus> mPageInfo = new PageInfo<WorkStatus>("", rowcount,
				pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order
				+ " LIMIT ?,?";
		JuRowCallbackHandler<WorkStatus> callback = new JuRowCallbackHandler<WorkStatus>(
				WorkStatus.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcOperations.query(sqlQuery, callback, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public int queryCategoryNameIsExist(String categoryName) {
		String sql = "SELECT * FROM work_status WHERE name = ?";
		JuRowCallbackHandler<WorkStatus> cb = new JuRowCallbackHandler<WorkStatus>(
				WorkStatus.class);
		jdbcOperations.query(sql, cb, categoryName);
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public WorkStatus findWorkStatusById(int id) {
		String sql = "SELECT * FROM work_status WHERE id = ?";
		JuRowCallbackHandler<WorkStatus> cb = new JuRowCallbackHandler<WorkStatus>(
				WorkStatus.class);
		jdbcOperations.query(sql, cb, id);
		if (cb.getList().size() > 0) {
			return cb.getList().get(0);
		} else {
			return null;
		}
	}

}
