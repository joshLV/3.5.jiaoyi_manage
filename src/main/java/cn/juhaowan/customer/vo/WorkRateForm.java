package cn.juhaowan.customer.vo;

import java.util.Date;

/**
 * 工时率View
 * 
 * @author apxer
 * 
 */
public class WorkRateForm implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	/**
	 * 客服id
	 */
	private int csId;

	/**
	 * 工作日
	 */
	private Date days;
	/**
	 * 上班时间
	 */
	private Date onDuty;
	/**
	 * 下班时间
	 */
	private Date offDuty;
	/**
	 * 有郊时间
	 */
	private int effectiveTime;
	/**
	 * 客服类型
	 */
	private int csType;
	/**
	 * 工时率
	 */
	private Double workRate;

	/**
	 * 岗位号
	 */
	private String postNo;
	/**
	 * 讯鸟后台帐号
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String fullname;

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

	public Double getWorkRate() {
		return workRate;
	}

	public void setWorkRate(Double workRate) {
		this.workRate = workRate;
	}

	public int getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(int effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
