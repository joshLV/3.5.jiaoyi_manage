package cn.juhaowan.customer.vo;

public class ServiceJSON {
	private int userId;
	private String loginId;
	private String fullname;
	private String servcieNickname;
	private int[] serviceType;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getServcieNickname() {
		return servcieNickname;
	}

	public void setServcieNickname(String servcieNickname) {
		this.servcieNickname = servcieNickname;
	}

	public int[] getServiceType() {
		return serviceType;
	}

	public void setServiceType(int[] serviceType) {
		this.serviceType = serviceType;
	}
}
