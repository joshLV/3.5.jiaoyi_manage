package cn.juhaowan.customer.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.customer.vo.CustomerStatusLog;
import cn.juhaowan.customer.vo.WorkRate;
import cn.juhaowan.customer.vo.WorkRateForm;
import cn.juhaowan.customer.vo.WorkStatus;

public interface OnlineCustomerMonitorDao {

	/**
	 * 寄售客服
	 */
	public static final int IS_CUSTOMER = 0;
	/**
	 * 寄售物服
	 */
	public static final int IS_OBJECT_CUSTOMER = 1;
	/**
	 * 审核员
	 */
	public static final int IS_AUDITOR = 2;

	/**
	 * 投资客服
	 */
	public static final int IS_INVESTMENT_SERVICE = 3;

	// ###############################sys_userinfo
	// 管理######################################
	/**
	 * 查询客服类型
	 * 
	 * @param customerId
	 *            客服ID
	 * @return
	 */
	int queryCustomerType(int customerId);

	// ###############################工时率管理######################################
	/**
	 * @param conditions
	 *            查询条件（客服类型，岗位号，姓名，统计时间，姓名，统计时间）
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @return 返回PageInfo类型的工作状态类型
	 */
	PageInfo<WorkRateForm> queryWorkRatePageInfo(
			Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order);

	/**
	 * 更新下班时间
	 * 
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	int onDuty(int customerId, int customerType);

	/**
	 * 更新下班时间
	 * 
	 * @param id
	 * @return
	 */
	int offDuty(int id);

	/**
	 * 查找最新上班记录
	 * 
	 * @param customerId
	 *            客服ID
	 * @return
	 */
	WorkRate findNewOnDutyLogByCustomerId(int customerId);

	/**
	 * 根据Id查询某条工时率记录
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
	WorkRate findById(int id);

	/**
	 * 添加工时率
	 * 
	 * @param customerId
	 *            客服ID
	 * @param workrate
	 *            工时率
	 * @return
	 */
	int statisticsWorkRate(int customerId, int effectiveTime, double workrate);

	// ###############################状态变更日志管理######################################
	/**
	 * 更新当前在线工作状态:sys_userinfo
	 * 
	 * @param customerId
	 *            客服ID
	 * @param workStatus
	 *            工作状态
	 * @return
	 */
	int updateCurrentWorkStatus(int customerId, int workStatus);

	/**
	 * 添加客服状态变更日志记录
	 * 
	 * @param customerId
	 *            客服ID
	 * @param workStatus
	 *            工作状态
	 * @return
	 */
	int addWorkStatusUpdateLog(int customerId, int workStatus);

	/**
	 * 根据客服Id和某天日期（2013-08-20）查询客服状态变更日志
	 * 
	 * @param customerId
	 *            客服ID
	 * @param updateDate
	 *            变更时间
	 * @return
	 */
	List<CustomerStatusLog> findById(int customerId, String updateDate);

	/**
	 * 查询某天上班在线状态变更情况
	 * 
	 * @param customerId
	 * @param onDutyTime
	 * @param offDutyTime
	 * @return
	 */
	List<CustomerStatusLog> queryTodayStatusLog(int customerId,
			Date onDutyTime, Date offDutyTime);

	// ###############################工作状态类型管理######################################

	/**
	 * 添加工作状态类型
	 * 
	 * @param categoryName
	 *            工作状态类型名称
	 * @param weight
	 *            权重
	 * @pdOid 8d2fd2e7-af06-4cbd-a033-5088cfeb1c4f
	 * @return 0: 成功 , 1: 失败
	 */
	int addWorkStatusCategory(String categoryName, int weight);

	/**
	 * 修改工作状态类型
	 * 
	 * @param categoryID
	 *            工作状态ID
	 * @param categoryName
	 *            工作状态名称
	 * @param categoryWeight
	 *            工作状态权重
	 * @pdOid bf5b4427-2548-4261-82b4-9a3793b0b9aa
	 * @return 影响记录数
	 */
	int modifyWorkStatusCategory(int categoryID, String categoryName,
			int categoryWeight);

	/**
	 * 删除工作状态类型
	 * 
	 * @param categoryID
	 *            工作状态ID
	 * @param isForse
	 *            是否强制删除，如果强制，则不判断是否有帮助信息属于该工作状态
	 * @pdOid 311ca714-ac70-45bd-bb86-480b0d94cbb4
	 * @return 0: 成功 , 1: 失败
	 */
	int deleteWorkStatusCategory(int categoryID, boolean isForse);

	/**
	 * 查询所有工作类型
	 * 
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回工作状态类型列表
	 */
	List<WorkStatus> queryCategoryList(String sort, String order);

	/**
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @return 返回PageInfo类型的工作状态类型
	 */
	PageInfo<WorkStatus> queryCategoryList(int pageSize, int pageNo,
			String sort, String order);

	/**
	 * 查看工作状态类型是否存在[0:不存在 1:存在]
	 * 
	 * @param categoryName
	 * @return
	 */
	int queryCategoryNameIsExist(String categoryName);

	WorkStatus findWorkStatusById(int id);
}
