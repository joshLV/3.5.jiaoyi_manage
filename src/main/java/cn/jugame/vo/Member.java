
package cn.jugame.vo;




import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 用户信息
 * 
 * @author houjt
 * 
 */
@Table(name = "sys_userinfo")
public class Member implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
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

	@Column(name = "LOGIN_TIME", type = DbType.Int)
	private int loginTime;

	@Column(name = "head_img", type = DbType.Varchar)
	private java.lang.String headImg;
	
	@Column(name = "msg_login_flag", type = DbType.Int)
	private int msgLoginFlag;
	
	@Column(name = "ip_login_flag", type = DbType.Int)
	private int ipLoginFlag;
	
	@Column(name = "ip_login_address", type = DbType.Varchar)
	private String ipLoginAddress;
	
	/**
	 * 客服id
	 */
	@Column(name = "custom_service_id", type = DbType.Varchar)
	private java.lang.String customServiceId;
	/**
	 * 在线状态
	 */
	@Column(name = "online_status", type = DbType.Int)
	private int onlineStatus;
	/**
	 * 是否是客服
	 */
	@Column(name = "is_customer", type = DbType.Int)
	private int isCustomer;
	/**
	 * 是否是物服
	 */
	@Column(name = "is_object_customer", type = DbType.Int)
	private int isObjectCustomer;
	/**
	 * 是否是投资客服
	 */
	@Column(name = "is_investment_service", type = DbType.Int)
	private int isInvestmentService;
	/**
	 * 是否是上班
	 */
	@Column(name = "is_on_duty", type = DbType.Int)
	private int isOnDuty;
	/**
	 * 客服昵称
	 */
	@Column(name = "customer_nickname", type = DbType.Varchar)
	private java.lang.String customerNickname;
	/**
	 * 客服QQ
	 */
	@Column(name = "customer_qq", type = DbType.Varchar)
	private java.lang.String customerQQ;
	/**
	 * 是否显示微信窗口
	 */
	@Column(name = "is_visible_weixin", type = DbType.Int)
	private int isVisibleWeiXin;

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
	
}

