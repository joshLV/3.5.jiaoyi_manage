//package cn.jugame.admin.web.form;
//
//import java.io.Serializable;
//import java.util.List;
//
//import cn.juhaowan.member.vo.Member;
//import cn.juhaowan.member.vo.MemberServiceType;
///**
// * 
// * @author Administrator
// *
// */
//public class MemberForm extends Member implements Serializable {
//
//	private static final long serialVersionUID = 5552503598505246567L;
//
//	// 实名
//	private String realname = "暂无";
//
//	// 查询余额
//	// 包括 现金和非现金账户
//	private double balance;
//
//	// 现金余额 balance1(可提现)
//	private double balance1;
//	
//	// 非现金余额 balance2(不可提现)
//	private double balance2;
//
//	// 押金 deposit_amount
//	private double depositAmount;
//
//	// 生日
//	private String infoBirthday;
//
//	// 创建时间
//	private String infoCreateTime;
//
//	// 最后登录时间
//	private String infoLastTime;
//
//	// 是否实名认证
//	private int statusMse;
//
//	// 临时锁定账号的天数
//	private int lockDay;
//	
//	//临时锁定剩余解锁天数
//	private long realLockDay;
//	
//	//解锁时间
//	private String infoUnlockTime;
//	
//	//登录密码情况 锁定/正常
//	private String loginStatus;
//	
//	//支付密码情况 锁定/正常/还没设置
//	private String payStatus;
//	
//	//会员所开通服务list
//	private List<MemberServiceType> serviceList;
//
//	public String getRealname() {
//		return realname;
//	}
//
//	public void setRealname(String realname) {
//		this.realname = realname;
//	}
//
//	public double getBalance() {
//		return balance;
//	}
//
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}
//
//	public double getBalance1() {
//		return balance1;
//	}
//
//	public void setBalance1(double balance1) {
//		this.balance1 = balance1;
//	}
//
//	public double getBalance2() {
//		return balance2;
//	}
//
//	public void setBalance2(double balance2) {
//		this.balance2 = balance2;
//	}
//
//	public double getDepositAmount() {
//		return depositAmount;
//	}
//
//	public void setDepositAmount(double depositAmount) {
//		this.depositAmount = depositAmount;
//	}
//
//	public String getInfoBirthday() {
//		return infoBirthday;
//	}
//
//	public void setInfoBirthday(String infoBirthday) {
//		this.infoBirthday = infoBirthday;
//	}
//
//	public String getInfoCreateTime() {
//		return infoCreateTime;
//	}
//
//	public void setInfoCreateTime(String infoCreateTime) {
//		this.infoCreateTime = infoCreateTime;
//	}
//
//	public String getInfoLastTime() {
//		return infoLastTime;
//	}
//
//	public void setInfoLastTime(String infoLastTime) {
//		this.infoLastTime = infoLastTime;
//	}
//
//	public int getStatusMse() {
//		return statusMse;
//	}
//
//	public void setStatusMse(int statusMse) {
//		this.statusMse = statusMse;
//	}
//
//	public int getLockDay() {
//		return lockDay;
//	}
//
//	public void setLockDay(int lockDay) {
//		this.lockDay = lockDay;
//	}
//
//	public long getRealLockDay() {
//		return realLockDay;
//	}
//
//	public void setRealLockDay(long realLockDay) {
//		this.realLockDay = realLockDay;
//	}
//
//	public String getInfoUnlockTime() {
//		return infoUnlockTime;
//	}
//
//	public void setInfoUnlockTime(String infoUnlockTime) {
//		this.infoUnlockTime = infoUnlockTime;
//	}
//
//	public String getLoginStatus() {
//		return loginStatus;
//	}
//
//	public void setLoginStatus(String loginStatus) {
//		this.loginStatus = loginStatus;
//	}
//
//	public String getPayStatus() {
//		return payStatus;
//	}
//
//	public void setPayStatus(String payStatus) {
//		this.payStatus = payStatus;
//	}
//
//	public List<MemberServiceType> getServiceList() {
//		return serviceList;
//	}
//
//	public void setServiceList(List<MemberServiceType> serviceList) {
//		this.serviceList = serviceList;
//	}
//
//	
//}
