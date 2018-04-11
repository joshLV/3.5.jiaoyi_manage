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
/**
 * @author lithium
 *
 */
@Table(name = "member_account_merge_log")
public class MemberAccountMergeLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type = DbType.Int)
	private int id;
	
	@Column(name = "uid",type = DbType.Int)
	private int uid;
	
	@Column(name = "operator_type",type = DbType.Int)
	private int operatorType;
	
	@Column(name = "show_operator_type",type = DbType.Int)
	private int showOperatorType;


	@Column(name = "pay_type",type = DbType.Int)
	private int payType;
	
	@Column(name = "bank_id",type = DbType.Varchar)
	private String bankId;

	@Column(name = "bank_card_no",type = DbType.Varchar)
	private String bankCardNo;
	
	
	@Column(name = "card_no",type = DbType.Varchar)
	private String cardNo;

	@Column(name = "amount_cash",type = DbType.Double)
	private double amountCash;	
	
	@Column(name = "amount_no_cash",type = DbType.Double)
	private double amountNoCash;
	
	@Column(name = "amount_total",type = DbType.Double)
	private double amountTotal;
		
	@Column(name = "fee",type = DbType.Double)
	private double fee;
	
	@Column(name = "total_balance",type = DbType.Double)
	private double totalBalance;
	
	@Column(name = "complete_time",type = DbType.DateTime)
	private java.util.Date completeTime;
	
	@Column(name = "create_time",type = DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "order_id",type = DbType.Varchar)
	private java.lang.String orderId;
	
	@Column(name = "external_id",type = DbType.Varchar)
	private java.lang.String externalId;

	@Column(name = "request_id",type = DbType.Varchar)
	private java.lang.String requestId;


	@Column(name = "remark",type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "sync_status",type = DbType.Int)
	private int syncStatus;

	public int getId() {
		return id;
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}
	
	public int getShowOperatorType() {
		return showOperatorType;
	}

	public void setShowOperatorType(int showOperatorType) {
		this.showOperatorType = showOperatorType;
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

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public double getAmountCash() {
		return amountCash;
	}

	public void setAmountCash(double amountCash) {
		this.amountCash = amountCash;
	}

	public double getAmountNoCash() {
		return amountNoCash;
	}

	public void setAmountNoCash(double amountNoCash) {
		this.amountNoCash = amountNoCash;
	}

	public double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public java.util.Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
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

	public java.lang.String getExternalId() {
		return externalId;
	}

	public void setExternalId(java.lang.String externalId) {
		this.externalId = externalId;
	}
	
	
	public java.lang.String getRequestId() {
		return requestId;
	}
	
	public void setRequestId(java.lang.String requestId) {
		this.requestId = requestId;
	}
	
	
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

}