package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 *  @author cxb
 * **/
@Table(name = "member_deposit")
public class MemberDeposit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "deposit_type", type = DbType.Int)
	private int depositType;

	@Column(name = "deposit_amount", type = DbType.Float)
	private double depositAmount;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "last_time", type = DbType.DateTime)
	private java.util.Date lastTime;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getDepositType() {
		return depositType;
	}

	public void setDepositType(int depositType) {
		this.depositType = depositType;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}
