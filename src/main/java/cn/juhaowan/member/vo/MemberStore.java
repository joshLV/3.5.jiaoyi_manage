package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 
 * @author Administrator
 * 
 */
@Table(name = "member_store")
public class MemberStore implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "verify_status", type = DbType.Int)
	private int verifyStatus;

	@Column(name = "store_status", type = DbType.Int)
	private int storeStatus;

	@Column(name = "begin_time", type = DbType.DateTime)
	private java.util.Date beginTime;

	@Column(name = "end_time", type = DbType.DateTime)
	private java.util.Date endTime;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "verify_time", type = DbType.DateTime)
	private java.util.Date verifyTime;

	@Column(name = "deal_year_number", type = DbType.Int)
	private int dealYearNumber;

	@Column(name = "deal_year_total", type = DbType.Double)
	private double dealYearTotal;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;

	@Column(name = "deal_final_time", type = DbType.DateTime)
	private java.util.Date dealFinalTime;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	private String realName;
	
	
	private String userLogin;
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(int storeStatus) {
		this.storeStatus = storeStatus;
	}

	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public java.util.Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(java.util.Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public int getDealYearNumber() {
		return dealYearNumber;
	}

	public void setDealYearNumber(int dealYearNumber) {
		this.dealYearNumber = dealYearNumber;
	}

	public double getDealYearTotal() {
		return dealYearTotal;
	}

	public void setDealYearTotal(double dealYearTotal) {
		this.dealYearTotal = dealYearTotal;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public java.util.Date getDealFinalTime() {
		return dealFinalTime;
	}

	public void setDealFinalTime(java.util.Date dealFinalTime) {
		this.dealFinalTime = dealFinalTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	

}
