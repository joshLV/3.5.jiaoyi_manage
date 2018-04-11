package cn.jugame.admin.web.form;


/**
 * 
 * @author Administrator
 *
 */
public class DutyListshowForm   {


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
	
	
}
