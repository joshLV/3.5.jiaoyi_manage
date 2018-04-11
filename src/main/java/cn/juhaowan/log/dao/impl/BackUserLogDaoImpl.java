package cn.juhaowan.log.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.log.dao.BackUserLogDao;
import cn.juhaowan.log.vo.BackUserLog;

/**
 * 后台日志
 * **/
@Repository("BackUserLogDao")
public class BackUserLogDaoImpl implements BackUserLogDao {

	@Autowired
	private JdbcOperations jdbcTemplate;

	/**
	 * 写入日志
	 * 
	 * @param uid
	 * @param ip
	 * @param type
	 */
	public int addLog(int uid, String ip, int type, String logRemark) {
		int i = 0;
		String sql = " insert into back_user_log(uid,log_ip,log_type,log_remark,create_time) values (?,?,?,?,?)";
		i = jdbcTemplate.update(sql, uid, ip, type, logRemark, new Date());
		return i;
	}

	/**
	 * 查询日志列表
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageSize
	 *            每页显示数
	 * @param pageNo
	 *            显示页
	 * @param sort
	 *            排序字段
	 * @param order
	 *            排序规则
	 */
	public PageInfo<BackUserLog> queryLogList(Map<String, Object> condition, int pageSize,
			int pageNo, String sort, String order) {
		// 默认值
		if (StringUtils.isBlank(sort)) {
			sort = "uid";
		}
		if (StringUtils.isBlank(order)) {
			order = "asc";
		}

		List<Object> conList = new ArrayList<Object>();
		PageInfo<BackUserLog> pageInfo = new PageInfo<BackUserLog>();
		StringBuffer sqlBody = new StringBuffer(" from back_user_log where 1=1");
		if (null != condition) {
			if (null != condition.get("uid")) {
				sqlBody.append(" and uid=?");
				conList.add(condition.get("uid"));
			}
			if (null != condition.get("log_ip")) {
				sqlBody.append(" and log_ip=?");
				conList.add(condition.get("log_ip"));
			}
			if (null != condition.get("log_type")) {
				sqlBody.append(" and log_type=?");
				conList.add(condition.get("log_type"));
			}
			if (null != condition.get("beginTime")) {
				sqlBody.append(" and create_time >= ? ");
				conList.add(condition.get("beginTime"));
			}
			if (null != condition.get("endTime")) {
				sqlBody.append(" and create_time <= ? ");
				conList.add(condition.get("endTime") + " 23:59:59");
			}
		}
		System.out.println(sqlBody.toString());
		String sqlCount = "select count(*) " + sqlBody.toString();

		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sqlCount, conList.toArray());
		if (count == 0){
			return pageInfo;
		}

		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		pageInfo.setPageSize(pageSize);
		int index = pageInfo.getStartNum(pageNo);

		String sql = "select * " + sqlBody.toString() + " order by " + sort
				+ " " + order + " limit " + index + "," + pageSize;

		JuRowCallbackHandler<BackUserLog> juRowCallbackHandler = new JuRowCallbackHandler<BackUserLog>(
				BackUserLog.class);

		jdbcTemplate.query(sql, juRowCallbackHandler, conList.toArray());
		pageInfo.setItems(juRowCallbackHandler.getList());
		return pageInfo;
	}
}
