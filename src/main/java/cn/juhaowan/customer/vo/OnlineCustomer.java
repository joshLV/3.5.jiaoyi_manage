package cn.juhaowan.customer.vo;

/**
 * 在线客服/物服监控
 * 
 */
public class OnlineCustomer {
	/**
	 * 客服岗位号
	 */
	private String postNO;
	/**
	 * 讯鸟帐号
	 */
	private String loginName;
	/**
	 * 姓名
	 */
	private String fullName;
	/**
	 * 工作状态
	 */
	private String workStatus;
	/**
	 * 客服类型
	 */
	private String customerType;
	/**
	 * 交易模式
	 */
	private String tradingPatterns;
	/**
	 * 业务过程
	 */
	private String businessProcess;
	/**
	 * 商品类型
	 */
	private String productType;
	/**
	 * 游戏
	 */
	private String games;

	public String getPostNO() {
		return postNO;
	}

	public void setPostNO(String postNO) {
		this.postNO = postNO;
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

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getTradingPatterns() {
		return tradingPatterns;
	}

	public void setTradingPatterns(String tradingPatterns) {
		this.tradingPatterns = tradingPatterns;
	}

	public String getBusinessProcess() {
		return businessProcess;
	}

	public void setBusinessProcess(String businessProcess) {
		this.businessProcess = businessProcess;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}

}
