package cn.juhaowan.customer.vo;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * @author Administrator
 *
 */
public class DutyListForm  implements Serializable {


	// 客服岗位号
	private String customerServiceId;

	// 讯鸟后台账号
	private String loginName;
	
	// 姓名
	private String fullName;

	// 1业务类型 
	private String businessType;

	// 2交易模式 
	private String businessModel;
	
	// 3用户类型 
	private String userType;

	// 4商品类型 
	private String productTypeids;
	
	// 5游戏
	private String gameids;
	
	// 用户id
	private String uids;
	
	//是否在上班
	private int onDuty;
	
	//在线状态
	private int onlineDuty;
	
	//是否是客服
	private int isCustomer;
	
	//是否是物服
	private int isObejectCustomer;
	
	//工作状态 读表
	private String workStatus;
	
	//客服类型
	private int serverType;
	
	//已支付单数
	private int payCount;
	
	//未支付单数
	private int unPayCount;
	
	public String getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(String customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProductTypeids() {
		return productTypeids;
	}

	public void setProductTypeids(String productTypeids) {
		this.productTypeids = productTypeids;
	}

	public String getGameids() {
		return gameids;
	}

	public void setGameids(String gameids) {
		this.gameids = gameids;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public int getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(int onDuty) {
		this.onDuty = onDuty;
	}

	public int getOnlineDuty() {
		return onlineDuty;
	}

	public void setOnlineDuty(int onlineDuty) {
		this.onlineDuty = onlineDuty;
	}

	public int getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(int isCustomer) {
		this.isCustomer = isCustomer;
	}

	public int getIsObejectCustomer() {
		return isObejectCustomer;
	}

	public void setIsObejectCustomer(int isObejectCustomer) {
		this.isObejectCustomer = isObejectCustomer;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
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





	
	
}
