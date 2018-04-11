package cn.juhaowan.customer.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.customer.dao.OnlineCustomerMonitorDao;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;
import cn.juhaowan.customer.vo.CustomerStatusLog;
import cn.juhaowan.customer.vo.SysUserinfo;
import cn.juhaowan.customer.vo.WorkRate;
import cn.juhaowan.customer.vo.WorkRateForm;
import cn.juhaowan.customer.vo.WorkStatus;

@Service("OnlineCustomerMonitorService")
public class DefaultSOnlineCustomerMonitorService implements OnlineCustomerMonitorService {
	@Autowired
	private OnlineCustomerMonitorDao onlineCustomerMonitorDao;
	@Autowired
	private JdbcOperations jdbcOperations;

	// ###############################sys_userinfo 管理######################################
	/**
	 * 查询客服类型
	 * 
	 * @param customerId
	 *            客服ID
	 * @return
	 */
	public int queryCustomerType(int customerId) {
		return onlineCustomerMonitorDao.queryCustomerType(customerId);
	}

	// ###############################工时率管理######################################
	@Override
	public PageInfo<WorkRateForm> queryWorkRatePageInfo(Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		return onlineCustomerMonitorDao.queryWorkRatePageInfo(conditions, pageSize, pageNo, sort, order);
	}

	@Override
	public int onDuty(int customerId, int customerType) {
		return onlineCustomerMonitorDao.onDuty(customerId, customerType);
	}

	public int offDuty(int id) {
		return onlineCustomerMonitorDao.offDuty(id);
	}

	public WorkRate findNewOnDutyLogByCustomerId(int customerId) {
		return onlineCustomerMonitorDao.findNewOnDutyLogByCustomerId(customerId);
	}

	public WorkRate findById(int id) {
		return onlineCustomerMonitorDao.findById(id);
	}

	public int statisticsWorkRate(int customerId, int effectiveTime, double workrate) {
		return onlineCustomerMonitorDao.statisticsWorkRate(customerId, effectiveTime, workrate);
	}

	// ###############################状态变更日志管理######################################
	@Override
	public int updateCurrentWorkStatus(int customerId, int workStatus) {
		return onlineCustomerMonitorDao.updateCurrentWorkStatus(customerId, workStatus);
	}

	@Override
	public int addWorkStatusUpdateLog(int customerId, int workStatus) {
		return onlineCustomerMonitorDao.addWorkStatusUpdateLog(customerId, workStatus);
	}

	@Override
	public List<CustomerStatusLog> findById(int customerId, String updateDate) {
		return onlineCustomerMonitorDao.findById(customerId, updateDate);
	}

	@Override
	public List<CustomerStatusLog> queryTodayStatusLog(int customerId, Date onDutyTime, Date offDutyTime) {
		return onlineCustomerMonitorDao.queryTodayStatusLog(customerId, onDutyTime, offDutyTime);
	}

	// **************************************工作状态类型管理*********************************************
	@Override
	public int addWorkStatusCategory(String categoryName, int weight) {
		return onlineCustomerMonitorDao.addWorkStatusCategory(categoryName, weight);
	}

	@Override
	public int modifyWorkStatusCategory(int categoryID, String categoryName, int categoryWeight) {
		return onlineCustomerMonitorDao.modifyWorkStatusCategory(categoryID, categoryName, categoryWeight);
	}

	@Override
	public int deleteWorkStatusCategory(int categoryID, boolean isForce) {
		return onlineCustomerMonitorDao.deleteWorkStatusCategory(categoryID, isForce);
	}

	@Override
	public List<WorkStatus> queryCategoryList(String sort, String order) {
		return onlineCustomerMonitorDao.queryCategoryList(sort, order);
	}

	@Override
	public PageInfo<WorkStatus> queryCategoryList(int pageSize, int pageNo, String sort, String order) {
		return onlineCustomerMonitorDao.queryCategoryList(pageSize, pageNo, sort, order);
	}

	@Override
	public int queryCategoryNameIsExist(String categoryName) {
		return onlineCustomerMonitorDao.queryCategoryNameIsExist(categoryName);
	}

	@Override
	public WorkStatus findWorkStatusById(int id) {
		return onlineCustomerMonitorDao.findWorkStatusById(id);
	}

	@Override
	public List<SysUserinfo> onDutyCustomer(int type) {
        StringBuffer sb  = new StringBuffer("SELECT * FROM `sys_userinfo` WHERE `USERTYPE` = 1 AND `online_status`=2 AND  `is_on_duty`=1 ");
        if(type == 1){
        	sb.append("AND `is_customer` =1 ");
        }else if(type == 2){
        	sb.append("AND `is_object_customer` =1  ");
        }else if(type == 3){
        	sb.append("AND (`is_investment_service` =1  OR  `is_investment_service` =10 ) ");
        }else{
        	sb.append("AND (`is_customer` =1 OR `is_object_customer` =1 OR `is_investment_service` =1 OR  `is_investment_service` =10)");
        }
    	JuRowCallbackHandler<SysUserinfo> callback = new JuRowCallbackHandler<SysUserinfo>(SysUserinfo.class);
		jdbcOperations.query(sb.toString(), callback);
		List<SysUserinfo> returnList = callback.getList();
		return returnList;
	}

	@Override
	public boolean onDutyCustomerDuty(List<SysUserinfo> list,int ouid) {
		for(int i=0;i <list.size();i++){
			int uid = list.get(i).getUserId();
			String sql = "SELECT GROUP_CONCAT(duty_subid) duty_str FROM `customer_service_duty` WHERE busness_type=2 AND duty_id=2 AND customer_service_id=?";
			Map<String,Object> re = jdbcOperations.queryForMap(sql,uid);
			String dutyStr = re.get("duty_str").toString();
			 
			System.out.println(ouid+"____-"+uid+"---str --"+ dutyStr);
			
		}
		
		return false;
	}
	
	
	
}
