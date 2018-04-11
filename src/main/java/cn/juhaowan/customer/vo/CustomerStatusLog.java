/**
 * 
 */
package cn.juhaowan.customer.vo;

import java.util.Date;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 客服状态变更日志POJO
 * 
 * @author Apxer
 * 
 */
@Table(name = "customer_status_log")
public class CustomerStatusLog {
	public CustomerStatusLog() {
	}

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	/**
	 * 客服Id
	 */
	@Column(name = "cs_id", type = DbType.Int)
	private int csId;
	/**
	 * 工作状态
	 */
	@Column(name = "work_status", type = DbType.Int)
	private int workStatus;
	/**
	 * 变更时间
	 */
	@Column(name = "update_time", type = DbType.DateTime)
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCsId() {
		return csId;
	}

	public void setCsId(int csId) {
		this.csId = csId;
	}

	public int getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
