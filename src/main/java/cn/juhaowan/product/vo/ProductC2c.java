package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

@Table(name="product_c2c")
public class ProductC2c implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id(generationType = GenerationType.assign)
	@Column(name = "product_id",type=DbType.Varchar)
	private java.lang.String productId;
	
	@Column(name = "game_login_id",type=DbType.Varchar)
	private java.lang.String gameLoginId;
	
	@Column(name = "game_login_password",type=DbType.Varchar)
	private java.lang.String gameLoginPassword;
	
	@Column(name = "security_lock",type=DbType.Varchar)
	private java.lang.String securityLock;
	
	@Column(name = "contact_qq",type=DbType.Varchar)
	private java.lang.String contactQq;
	
	@Column(name = "customer_service_id",type=DbType.Int)
	private int customerServiceId;
	
	@Column(name = "assign_time",type=DbType.DateTime)
	private java.util.Date assignTime;
	
	@Column(name = "is_verify",type=DbType.Int)
	private int is_verify;
	
	@Column(name = "goods_position",type=DbType.Int)
	private int goodsPosition;
	
	@Column(name = "account_verify",type=DbType.Int)
	private int accountVerify;
	

	 
	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	public java.lang.String getGameLoginId() {
		return gameLoginId;
	}

	public void setGameLoginId(java.lang.String gameLoginId) {
		this.gameLoginId = gameLoginId;
	}

	public java.lang.String getGameLoginPassword() {
		return gameLoginPassword;
	}

	public void setGameLoginPassword(java.lang.String gameLoginPassword) {
		this.gameLoginPassword = gameLoginPassword;
	}

	public java.lang.String getSecurityLock() {
		return securityLock;
	}

	public void setSecurityLock(java.lang.String securityLock) {
		this.securityLock = securityLock;
	}

	public java.lang.String getContactQq() {
		return contactQq;
	}

	public void setContactQq(java.lang.String contactQq) {
		this.contactQq = contactQq;
	}



	public int getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(int customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public java.util.Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(java.util.Date assignTime) {
		this.assignTime = assignTime;
	}

	public int getIs_verify() {
		return is_verify;
	}

	public void setIs_verify(int is_verify) {
		this.is_verify = is_verify;
	}

	public int getGoodsPosition() {
		return goodsPosition;
	}

	public void setGoodsPosition(int goodsPosition) {
		this.goodsPosition = goodsPosition;
	}

	public int getAccountVerify() {
		return accountVerify;
	}

	public void setAccountVerify(int accountVerify) {
		this.accountVerify = accountVerify;
	}


	

}