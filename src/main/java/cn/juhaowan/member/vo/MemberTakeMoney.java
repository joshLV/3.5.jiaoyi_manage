package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 *  @author cxb
 * **/
@Table(name = "member_take_money")
public class MemberTakeMoney implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "amount", type = DbType.Float)
	private double amount;

	@Column(name = "fee", type = DbType.Float)
	private double fee;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "adminid", type = DbType.Varchar)
	private java.lang.String adminid;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "verify_time", type = DbType.DateTime)
	private java.util.Date verifyTime;

	@Column(name = "transfer_time", type = DbType.DateTime)
	private java.util.Date transferTime;

	@Column(name = "transaction_id", type = DbType.Varchar)
	private java.lang.String transactionId;

	@Column(name = "take_money_type", type = DbType.Int)
	private int takeMoneyType;

	@Column(name = "bank_id", type = DbType.Varchar)
	private java.lang.String bankId;

	@Column(name = "bank_addr", type = DbType.Varchar)
	private java.lang.String bankAddr;

	@Column(name = "provinces_code", type = DbType.Varchar)
	private java.lang.String provincesCode;

	@Column(name = "bank_card_num", type = DbType.Varchar)
	private java.lang.String bankCardNum;

	@Column(name = "real_name", type = DbType.Varchar)
	private java.lang.String realName;
	
    //账户同功能开通标志
	private String zhgFlag;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getAdminid() {
		return adminid;
	}

	public void setAdminid(java.lang.String adminid) {
		this.adminid = adminid;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.util.Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(java.util.Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public java.util.Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(java.util.Date transferTime) {
		this.transferTime = transferTime;
	}

	public java.lang.String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(java.lang.String transactionId) {
		this.transactionId = transactionId;
	}

	public int getTakeMoneyType() {
		return takeMoneyType;
	}

	public void setTakeMoneyType(int takeMoneyType) {
		this.takeMoneyType = takeMoneyType;
	}

	public java.lang.String getBankId() {
		return bankId;
	}

	public void setBankId(java.lang.String bankId) {
		this.bankId = bankId;
	}

	public java.lang.String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(java.lang.String bankAddr) {
		this.bankAddr = bankAddr;
	}

	public java.lang.String getProvincesCode() {
		return provincesCode;
	}

	public void setProvincesCode(java.lang.String provincesCode) {
		this.provincesCode = provincesCode;
	}

	public java.lang.String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(java.lang.String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	public String getZhgFlag() {
		return zhgFlag;
	}

	public void setZhgFlag(String zhgFlag) {
		this.zhgFlag = zhgFlag;
	}
	
	

}
