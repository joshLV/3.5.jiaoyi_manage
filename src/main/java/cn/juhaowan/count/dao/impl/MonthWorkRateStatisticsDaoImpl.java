package cn.juhaowan.count.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.MonthWorkRateStatisticsDao;
import cn.juhaowan.count.vo.DayWorkRate;
import cn.juhaowan.count.vo.MonthWorkRateForm;

/**
 * 月工时率统计
 * 
 * @author APXer
 * 
 */
@Repository("MonthWorkRateStatisticsDao")
public class MonthWorkRateStatisticsDaoImpl implements
		MonthWorkRateStatisticsDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public PageInfo<MonthWorkRateForm> queryMonthWorkRatePageInfo(
			Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		PageInfo<MonthWorkRateForm> mwrfPageInfo = null;
		// TODO 统计显示
		if (null == sort || ("").equals(sort)) {
			sort = "cs_id";
		}
		if (null == order) {
			order = "ASC";
		}

		// 1.查询本月所有客服
		String serviceSQL = "SELECT DISTINCT cs_id FROM work_rate WHERE off_duty>0 AND DATE_FORMAT(days,'%Y-%m')=? ";
		RowMapper<MonthWorkRateForm> rowMapper = new RowMapper<MonthWorkRateForm>() {
			@Override
			public MonthWorkRateForm mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MonthWorkRateForm mwrf = new MonthWorkRateForm();
				mwrf.setCsId(rs.getInt(1));
				return mwrf;
			}
		};
		List<MonthWorkRateForm> mwrfList = jdbcOperations.query(serviceSQL,
				rowMapper, conditions.get("ym"));

		List<MonthWorkRateForm> showList = new ArrayList<MonthWorkRateForm>();
		for (MonthWorkRateForm monthWorkRateForm : mwrfList) {
			// System.err.println(monthWorkRateForm.getCsId());
			// 2.查询客服信息
			// String serviceInfoSQL = "";
			// System.err.println("yyyy-MM==" + conditions.get("ym"));
			StringBuffer serviceInfoSQL = new StringBuffer(
					"select user_id,fullname,custom_service_id,is_customer,is_object_customer,is_investment_service from sys_userinfo where user_id=? ");
			if (null != conditions) {
				if (null != conditions.get("customerType")) {// 客服/物服/审核员
					Integer customerType = (Integer) conditions
							.get("customerType");
					serviceInfoSQL.append(" AND ");
					if (customerType == IS_SERVICE) {
						serviceInfoSQL.append("is_customer >0 ");
					} else if (customerType == IS_OBJECT_SERVICE) {
						serviceInfoSQL.append("is_object_customer >0 ");
					} else if (customerType == IS_AUDITOR) {
						serviceInfoSQL
								.append("is_customer >0 AND is_object_customer >0 ");
					} else if (customerType == IS_INVESTMENT_SERVICE) {
						serviceInfoSQL.append("is_investment_service >0 ");
					}
				} else {
					serviceInfoSQL
							.append(" AND (is_customer >0 OR is_object_customer >0 OR is_investment_service>0) ");
				}
				if (null != conditions.get("fullname")) {// 姓名
					serviceInfoSQL.append(" and fullname like '");
					serviceInfoSQL.append("%" + conditions.get("fullname")
							+ "%'");
				}
				if (null != conditions.get("postNo")) {// 岗位号
					serviceInfoSQL.append(" and custom_service_id like '");
					serviceInfoSQL
							.append("%" + conditions.get("postNo") + "%'");
				}
			}
			RowMapper<MonthWorkRateForm> serviceInfoRM = new RowMapper<MonthWorkRateForm>() {
				@Override
				public MonthWorkRateForm mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					MonthWorkRateForm mwrf = new MonthWorkRateForm();
					mwrf.setCsId(rs.getInt(1));
					mwrf.setFullname(rs.getString(2));
					mwrf.setServicePostNo(rs.getString(3));
					mwrf.setIsServcie(rs.getInt(4));
					mwrf.setIsObjectService(rs.getInt(5));
					mwrf.setIsInvestmentServcie(rs.getInt(6));
					return mwrf;
				}
			};
			List<MonthWorkRateForm> serviceDetailList = jdbcOperations.query(
					serviceInfoSQL.toString(), serviceInfoRM,
					monthWorkRateForm.getCsId());
			for (MonthWorkRateForm monthWorkRateForm2 : serviceDetailList) {
				// 月人数
				String monthOnDutyDaysSQL = "select count(DISTINCT days) from work_rate where off_duty>0 AND DATE_FORMAT(days,'%Y-%m')=? AND cs_id=?";
				int monthOnDutyDays = jdbcOperations.queryForInt(
						monthOnDutyDaysSQL, conditions.get("ym"),
						monthWorkRateForm2.getCsId());
				// 月工时率
				String monthWorkRateSQL = "select sum(effective_time)/sum(TIME_TO_SEC(TIMEDIFF(off_duty,on_duty))) from work_rate  where off_duty>0 AND DATE_FORMAT(days,'%Y-%m')=? AND cs_id=?";
				Double monthWorkRate = jdbcOperations.queryForObject(
						monthWorkRateSQL, Double.class, conditions.get("ym"),
						monthWorkRateForm2.getCsId());

				monthWorkRateForm2.setMonthOnDutyDays(monthOnDutyDays);
				if (null == monthWorkRate) {
					monthWorkRateForm2.setMonthWorkRate(0);
				} else {
					monthWorkRateForm2.setMonthWorkRate(monthWorkRate);
				}

				// 某月客服信息
				String inEarlyToEndSQL = "select days,workrate from work_rate where off_duty>0 AND DATE_FORMAT(days,'%Y-%m')=? AND cs_id=?";
				RowMapper<DayWorkRate> workRateRM = new RowMapper<DayWorkRate>() {
					@Override
					public DayWorkRate mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DayWorkRate dwr = new DayWorkRate();

						dwr.setDay(rs.getDate(1));
						dwr.setWorkRate(rs.getDouble(2));
						return dwr;
					}
				};
				List<DayWorkRate> dwrList = jdbcOperations.query(
						inEarlyToEndSQL, workRateRM, conditions.get("ym"),
						monthWorkRateForm2.getCsId());
				for (DayWorkRate dwr : dwrList) {
					// System.err.println(dwr.getDay() + "==" +
					// dwr.getWorkRate());
					SimpleDateFormat sdf = new SimpleDateFormat("d");
					String day = sdf.format(dwr.getDay());
					// System.err.println("号" + day);
					Method[] methods = monthWorkRateForm2.getClass()
							.getMethods();
					for (Method method : methods) {
						if (method.getName().equals("setWr" + day)) {
							// System.err.println("setWr" + day + "=="
							// + dwr.getWorkRate());
							try {
								method.invoke(monthWorkRateForm2,
										dwr.getWorkRate());
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}

						}
					}
				}
				showList.add(monthWorkRateForm2);
				// System.err.println("客服：" + monthWorkRateForm2);
			}
		}
		// System.err.println("列表：" + showList.size());
		mwrfPageInfo = new PageInfo<MonthWorkRateForm>(showList.size(),
				pageSize);
		mwrfPageInfo.setItems(showList);
		return mwrfPageInfo;
	}
}
