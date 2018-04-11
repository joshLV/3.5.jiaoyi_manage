package cn.jugame.admin.web.form;

/**
 * 短信接收人VO: 用于人工充值、人工转账、批量充值业务
 * 
 * @author APXer
 */
public class SMSRecipients {
	private double min;
	private double max;
	private String recipient;
	private String mobile;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
