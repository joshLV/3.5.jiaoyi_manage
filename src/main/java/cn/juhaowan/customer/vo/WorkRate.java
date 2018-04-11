package cn.juhaowan.customer.vo;

import java.util.Date;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 工时率POJO
 * 
 * @author apxer
 * 
 */
@Table(name = "work_rate")
public class WorkRate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	/**
	 * 客服id
	 */
	@Column(name = "cs_id", type = DbType.Int)
	private int csId;

	/**
	 * 工作日
	 */
	@Column(name = "days", type = DbType.DateTime)
	private Date days;
	/**
	 * 上班时间
	 */
	@Column(name = "on_duty", type = DbType.DateTime)
	private Date onDuty;
	/**
	 * 下班时间
	 */
	@Column(name = "off_duty", type = DbType.DateTime)
	private Date offDuty;
	/**
	 * 
	 */
	@Column(name = "effective_time", type = DbType.Int)
	private int effectiveTime;
	/**
	 * 客服类型
	 */
	@Column(name = "cs_type", type = DbType.Int)
	private int csType;
	/**
	 * 工时率
	 */
	@Column(name = "workrate", type = DbType.Double)
	private double workRate;

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

	public Date getDays() {
		return days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public Date getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(Date onDuty) {
		this.onDuty = onDuty;
	}

	public Date getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(Date offDuty) {
		this.offDuty = offDuty;
	}

	public int getCsType() {
		return csType;
	}

	public void setCsType(int csType) {
		this.csType = csType;
	}

	public double getWorkRate() {
		return workRate;
	}

	public void setWorkRate(double workRate) {
		this.workRate = workRate;
	}

	public int getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(int effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

}
