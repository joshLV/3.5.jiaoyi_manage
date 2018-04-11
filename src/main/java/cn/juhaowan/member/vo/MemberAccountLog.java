package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;


/**
 * 
 * @author cai9911
 *
 */
@Table(name = "member_account_log")
public class MemberAccountLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type = DbType.Int)
	private int id;
	
	@Column(name = "uid",type = DbType.Int)
	private int uid;
	
	@Column(name = "account_type",type = DbType.Int)
	private int accountType;
	
	@Column(name = "operator_type",type = DbType.Int)
	private int operatorType;
	
	@Column(name = "pay_type",type = DbType.Int)
	private int payType;
	
	@Column(name = "bank_id",type = DbType.Varchar)
	private String bankId;
	
	@Column(name = "card_no",type = DbType.Varchar)
	private String cardNo;
	
	@Column(name = "bank_card_no",type = DbType.Varchar)
	private String bankCardNo;

	@Column(name = "deposit_type",type = DbType.Int)
	private int depositType;
	
	@Column(name = "amount",type = DbType.Double)
	private double amount;
	
	@Column(name = "fee",type = DbType.Double)
	private double fee;
	
	@Column(name = "balance",type = DbType.Double)
	private double balance;
	
	@Column(name = "total_balance",type = DbType.Double)
	private double totalBalance;
	
	@Column(name = "create_time",type = DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "order_id",type = DbType.Varchar)
	private java.lang.String orderId;

	@Column(name = "remark",type = DbType.Varchar)
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

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public int getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public int getDepositType() {
		return depositType;
	}

	public void setDepositType(int depositType) {
		this.depositType = depositType;
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	} 

}