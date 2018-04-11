package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 *  @author cxb
 * **/
@Table(name = "member_take_money_log")
public class MemberTakeMoneyLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "operator_uid", type = DbType.Varchar)
	private java.lang.String operatorUid;

	@Column(name = "order_id", type = DbType.Varchar)
	private java.lang.String orderId;

	@Column(name = "ret_code", type = DbType.Varchar)
	private java.lang.String retCode;

	@Column(name = "r1_code", type = DbType.Varchar)
	private java.lang.String r1Code;

	@Column(name = "bank_status", type = DbType.Varchar)
	private java.lang.String bankStatus;

	@Column(name = "error_msg", type = DbType.Varchar)
	private java.lang.String errorMsg;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "take_money_id", type = DbType.Int)
	private int takeMoneyId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getOperatorUid() {
		return operatorUid;
	}

	public void setOperatorUid(java.lang.String operatorUid) {
		this.operatorUid = operatorUid;
	}

	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getRetCode() {
		return retCode;
	}

	public void setRetCode(java.lang.String retCode) {
		this.retCode = retCode;
	}

	public java.lang.String getR1Code() {
		return r1Code;
	}

	public void setR1Code(java.lang.String r1Code) {
		this.r1Code = r1Code;
	}

	public java.lang.String getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(java.lang.String bankStatus) {
		this.bankStatus = bankStatus;
	}

	public java.lang.String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(java.lang.String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getTakeMoneyId() {
		return takeMoneyId;
	}

	public void setTakeMoneyId(int takeMoneyId) {
		this.takeMoneyId = takeMoneyId;
	}

}
