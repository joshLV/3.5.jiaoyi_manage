package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息
 * 
 * @author houjt
 * 
 */
@Entity
@Table(name = "sys_userinfo")
public class SysUserinfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", nullable = false)
	private int userId;

	@Column(name = "LOGINID")
	private java.lang.String loginid;

	@Column(name = "FULLNAME")
	private java.lang.String fullname;

	@Column(name = "USER_PASSWORD")
	private java.lang.String userPassword;

	@Column(name = "USERTYPE")
	private int usertype;

	@Column(name = "WORKPHONE")
	private java.lang.String workphone;

	@Column(name = "TELEPHONE")
	private java.lang.String telephone;

	@Column(name = "USER_EMAIL")
	private java.lang.String userEmail;

	@Column(name = "USER_ADDRESS")
	private java.lang.String userAddress;

	@Column(name = "USER_TITLE")
	private java.lang.String userTitle;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;

	@Column(name = "LAST_LOGIN_TIME")
	private java.util.Date lastLoginTime;

	@Column(name = "LOGIN_TIME")
	private int loginTime;

	@Column(name = "head_img")
	private java.lang.String headImg;
	
	@Column(name = "msg_login_flag")
	private int msgLoginFlag;
	
	@Column(name = "ip_login_flag")
	private int ipLoginFlag;
	
	@Column(name = "ip_login_address")
	private String ipLoginAddress;
	
	/**
	 * 客服id
	 */
	@Column(name = "custom_service_id")
	private java.lang.String customServiceId;
	/**
	 * 在线状态
	 */
	@Column(name = "online_status")
	private int onlineStatus;
	/**
	 * 是否是客服
	 */
	@Column(name = "is_customer")
	private int isCustomer;
	/**
	 * 是否是物服
	 */
	@Column(name = "is_object_customer")
	private int isObjectCustomer;
	/**
	 * 是否是投资客服
	 */
	@Column(name = "is_investment_service")
	private int isInvestmentService;
	/**
	 * 是否是上班
	 */
	@Column(name = "is_on_duty")
	private int isOnDuty;
	/**
	 * 客服昵称
	 */
	@Column(name = "customer_nickname")
	private java.lang.String customerNickname;
	/**
	 * 客服QQ
	 */
	@Column(name = "customer_qq")
	private java.lang.String customerQQ;
	/**
	 * 是否显示微信窗口
	 */
	@Column(name = "is_visible_weixin")
	private int isVisibleWeiXin;
	/**
	 * 是否客服
	 */
	@Column(name = "IS_CUSTOMER_R")
	private int isCustomerR;

	public String getIpLoginAddress() {
		return ipLoginAddress;
	}

	public void setIpLoginAddress(String ipLoginAddress) {
		this.ipLoginAddress = ipLoginAddress;
	}

	public int getUserId() {
		return userId;
	}

	public java.lang.String getLoginid() {
		return loginid;
	}

	public java.lang.String getFullname() {
		return fullname;
	}

	public java.lang.String getUserPassword() {
		return userPassword;
	}

	public int getUsertype() {
		return usertype;
	}

	public java.lang.String getWorkphone() {
		return workphone;
	}

	public java.lang.String getTelephone() {
		return telephone;
	}

	public java.lang.String getUserEmail() {
		return userEmail;
	}

	public java.lang.String getUserAddress() {
		return userAddress;
	}

	public java.lang.String getUserTitle() {
		return userTitle;
	}

	public int getStatus() {
		return status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public int getLoginTime() {
		return loginTime;
	}

	public java.lang.String getCustomServiceId() {
		return customServiceId;
	}

	public int getOnlineStatus() {
		return onlineStatus;
	}

	public int getIsCustomer() {
		return isCustomer;
	}

	public int getIsObjectCustomer() {
		return isObjectCustomer;
	}

	public int getIsOnDuty() {
		return isOnDuty;
	}

	public java.lang.String getCustomerNickname() {
		return customerNickname;
	}

	public java.lang.String getCustomerQQ() {
		return customerQQ;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setLoginid(java.lang.String loginid) {
		this.loginid = loginid;
	}

	public void setFullname(java.lang.String fullname) {
		this.fullname = fullname;
	}

	public void setUserPassword(java.lang.String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public void setWorkphone(java.lang.String workphone) {
		this.workphone = workphone;
	}

	public void setTelephone(java.lang.String telephone) {
		this.telephone = telephone;
	}

	public void setUserEmail(java.lang.String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserAddress(java.lang.String userAddress) {
		this.userAddress = userAddress;
	}

	public void setUserTitle(java.lang.String userTitle) {
		this.userTitle = userTitle;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}

	public void setCustomServiceId(java.lang.String customServiceId) {
		this.customServiceId = customServiceId;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public void setIsCustomer(int isCustomer) {
		this.isCustomer = isCustomer;
	}

	public void setIsObjectCustomer(int isObjectCustomer) {
		this.isObjectCustomer = isObjectCustomer;
	}

	public void setIsOnDuty(int isOnDuty) {
		this.isOnDuty = isOnDuty;
	}

	public void setCustomerNickname(java.lang.String customerNickname) {
		this.customerNickname = customerNickname;
	}

	public void setCustomerQQ(java.lang.String customerQQ) {
		this.customerQQ = customerQQ;
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

	public java.lang.String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(java.lang.String headImg) {
		this.headImg = headImg;
	}

	public int getMsgLoginFlag() {
		return msgLoginFlag;
	}

	public void setMsgLoginFlag(int msgLoginFlag) {
		this.msgLoginFlag = msgLoginFlag;
	}

	public int getIpLoginFlag() {
		return ipLoginFlag;
	}

	public void setIpLoginFlag(int ipLoginFlag) {
		this.ipLoginFlag = ipLoginFlag;
	}

	public int getIsCustomerR() {
		return isCustomerR;
	}

	public void setIsCustomerR(int isCustomerR) {
		this.isCustomerR = isCustomerR;
	}
	
}
