package cn.juhaowan.order.vo;

import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.GenerationType;

/**
 * 充值卡表实体
 * 
 */
@Table(name = "rechargeable_card")
public class RechargeableCard implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "recharge_time", type = DbType.DateTime)
	private java.util.Date rechargeTime;

	@Column(name = "customer_service_id", type = DbType.Int)
	private int customerServiceId;

	@Column(name = "number", type = DbType.Varchar)
	private java.lang.String number;

	@Column(name = "customer_service_name", type = DbType.Varchar)
	private java.lang.String customerServiceName;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "denomination", type = DbType.Double)
	private double denomination;

	@Column(name = "password", type = DbType.Varchar)
	private java.lang.String password;

	@Column(name = "order_id", type = DbType.Varchar)
	private java.lang.String orderId;
	
	@Column(name = "type", type = DbType.Int)
	private int type;
	
	@Column(name = "balance", type = DbType.Double)
	private double balance;
	
	@Column(name = "operation_type", type = DbType.Int)
	private int operationType;
	
	@Column(name = "original_price", type = DbType.Double)
	private double originalPrice;
	
	@Column(name = "game_account", type = DbType.Varchar)
	private java.lang.String gameAccount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(java.util.Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public int getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(int customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public java.lang.String getCustomerServiceName() {
		return customerServiceName;
	}

	public void setCustomerServiceName(java.lang.String customerServiceName) {
		this.customerServiceName = customerServiceName;
	}

	public java.lang.String getNumber() {
		return number;
	}

	public void setNumber(java.lang.String number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getDenomination() {
		return denomination;
	}

	public void setDenomination(double denomination) {
		this.denomination = denomination;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public java.lang.String getGameAccount() {
		return gameAccount;
	}

	public void setGameAccount(java.lang.String gameAccount) {
		this.gameAccount = gameAccount;
	}

}
