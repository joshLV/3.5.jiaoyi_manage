package cn.juhaowan.customer.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name = "sys_userinfo")
public class SysUserinfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "USER_ID", type = DbType.Int)
	private int userId;

	@Column(name = "LOGINID", type = DbType.Varchar)
	private java.lang.String loginid;

	@Column(name = "FULLNAME", type = DbType.Varchar)
	private java.lang.String fullname;

	@Column(name = "USER_PASSWORD", type = DbType.Varchar)
	private java.lang.String userPassword;

	@Column(name = "USERTYPE", type = DbType.Int)
	private int usertype;

	@Column(name = "WORKPHONE", type = DbType.Varchar)
	private java.lang.String workphone;

	@Column(name = "TELEPHONE", type = DbType.Varchar)
	private java.lang.String telephone;

	@Column(name = "USER_EMAIL", type = DbType.Varchar)
	private java.lang.String userEmail;

	@Column(name = "USER_ADDRESS", type = DbType.Varchar)
	private java.lang.String userAddress;

	@Column(name = "USER_TITLE", type = DbType.Varchar)
	private java.lang.String userTitle;

	@Column(name = "STATUS", type = DbType.Int)
	private int status;

	@Column(name = "CREATE_TIME", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "LAST_LOGIN_TIME", type = DbType.DateTime)
	private java.util.Date lastLoginTime;

	@Id
	@Column(name = "LOGIN_TIME", type = DbType.Int)
	private int loginTime;

	@Column(name = "custom_service_id", type = DbType.Varchar)
	private java.lang.String customServiceId;

	@Column(name = "online_status", type = DbType.Int)
	private int onlineStatus;

	@Column(name = "is_customer", type = DbType.Int)
	private int isCustomer;

	@Column(name = "is_object_customer", type = DbType.Int)
	private int isObjectCustomer;

	@Column(name = "is_on_duty", type = DbType.Int)
	private int isOnDuty;

	/**
	 * 是否是投资客服
	 */
	@Column(name = "is_investment_service", type = DbType.Int)
	private int isInvestmentService;
	/**
	 * 是否显示微信窗口
	 */
	@Column(name = "is_visible_weixin", type = DbType.Int)
	private int isVisibleWeiXin;
	
	/**
	 * 客服昵称
	 */
	@Column(name = "customer_nickname", type = DbType.Varchar)
	private java.lang.String customerNickname;

	@Column(name = "head_img", type = DbType.Varchar)
	private java.lang.String headImg;
	
	
	private int payCount;
	private int unPayCount;
	
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public java.lang.String getLoginid() {
		return loginid;
	}

	public void setLoginid(java.lang.String loginid) {
		this.loginid = loginid;
	}

	public java.lang.String getFullname() {
		return fullname;
	}

	public void setFullname(java.lang.String fullname) {
		this.fullname = fullname;
	}

	public java.lang.String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(java.lang.String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public java.lang.String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(java.lang.String workphone) {
		this.workphone = workphone;
	}

	public java.lang.String getTelephone() {
		return telephone;
	}

	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}

	public java.lang.String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(java.lang.String userEmail) {
		this.userEmail = userEmail;
	}

	public java.lang.String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(java.lang.String userAddress) {
		this.userAddress = userAddress;
	}

	public java.lang.String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(java.lang.String userTitle) {
		this.userTitle = userTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}

	public java.lang.String getCustomServiceId() {
		return customServiceId;
	}

	public void setCustomServiceId(java.lang.String customServiceId) {
		this.customServiceId = customServiceId;
	}

	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public int getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(int isCustomer) {
		this.isCustomer = isCustomer;
	}

	public int getIsObjectCustomer() {
		return isObjectCustomer;
	}

	public void setIsObjectCustomer(int isObjectCustomer) {
		this.isObjectCustomer = isObjectCustomer;
	}

	public int getIsOnDuty() {
		return isOnDuty;
	}

	public void setIsOnDuty(int isOnDuty) {
		this.isOnDuty = isOnDuty;
	}

	public int getIsInvestmentService() {
		return isInvestmentService;
	}

	public void setIsInvestmentService(int isInvestmentService) {
		this.isInvestmentService = isInvestmentService;
	}

	public int getIsVisibleWeiXin() {
		return isVisibleWeiXin;
	}

	public void setIsVisibleWeiXin(int isVisibleWeiXin) {
		this.isVisibleWeiXin = isVisibleWeiXin;
	}

	public java.lang.String getCustomerNickname() {
		return customerNickname;
	}

	public void setCustomerNickname(java.lang.String customerNickname) {
		this.customerNickname = customerNickname;
	}

	public int getPayCount() {
		return payCount;
	}

	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}

	public int getUnPayCount() {
		return unPayCount;
	}

	public void setUnPayCount(int unPayCount) {
		this.unPayCount = unPayCount;
	}

	public java.lang.String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(java.lang.String headImg) {
		this.headImg = headImg;
	}
	
	

}