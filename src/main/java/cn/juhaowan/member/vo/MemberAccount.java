package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 用户资金账号实体
 * @author cai9911
 *
 */
@Table(name = "member_account")
public class MemberAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "account_id",type = DbType.Int)
	private int accountId;
	
	@Column(name = "uid",type = DbType.Int)
	private int uid;
	
	@Column(name = "account_type",type = DbType.Int)
	private int accountType;
	
	@Column(name = "balance",type = DbType.Double)
	private double balance;
	
	@Column(name = "create_time",type = DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "last_time",type = DbType.DateTime)
	private java.util.Date lastTime;
	
	 
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
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

}