package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 *  @author cxb
 * **/
@Table(name = "member")
public class Member implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "login_name", type = DbType.Varchar)
	private java.lang.String loginName;

	@Column(name = "login_passwd", type = DbType.Varchar)
	private java.lang.String loginPasswd;

	@Column(name = "pay_passwd", type = DbType.Varchar)
	private java.lang.String payPasswd;

	@Column(name = "nick_name", type = DbType.Varchar)
	private java.lang.String nickName;

	@Column(name = "email", type = DbType.Varchar)
	private java.lang.String email;

	@Column(name = "email_validate", type = DbType.Int)
	private int emailValidate;

	@Column(name = "mobile", type = DbType.Varchar)
	private java.lang.String mobile;

	@Column(name = "mobile_validate", type = DbType.Int)
	private int mobileValidate;

	@Column(name = "real_validate", type = DbType.Int)
	private int realValidate;

	@Column(name = "qq", type = DbType.Varchar)
	private java.lang.String qq;

	@Column(name = "birthday", type = DbType.DateTime)
	private java.util.Date birthday;

	@Column(name = "question_id", type = DbType.Int)
	private int questionId;

	@Column(name = "answer", type = DbType.Varchar)
	private java.lang.String answer;

	@Column(name = "menber_type", type = DbType.Int)
	private int menberType;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "unlock_time", type = DbType.DateTime)
	private java.util.Date unlockTime;

	@Column(name = "last_time", type = DbType.DateTime)
	private java.util.Date lastTime;

	@Column(name = "ip", type = DbType.Varchar)
	private java.lang.String ip;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "user_type", type = DbType.Int)
	private int userType;
	
	@Column(name = "user_terrace", type = DbType.Varchar)
	private java.lang.String userTerrace;
	
	// 是否已被打入黑名单 0 是 1否
	private int isBlackList;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public java.lang.String getLoginName() {
		return loginName;
	}

	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public java.lang.String getLoginPasswd() {
		return loginPasswd;
	}

	public void setLoginPasswd(java.lang.String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

	public java.lang.String getPayPasswd() {
		return payPasswd;
	}

	public void setPayPasswd(java.lang.String payPasswd) {
		this.payPasswd = payPasswd;
	}

	public java.lang.String getNickName() {
		return nickName;
	}

	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public int getEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(int emailValidate) {
		this.emailValidate = emailValidate;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public int getMobileValidate() {
		return mobileValidate;
	}

	public void setMobileValidate(int mobileValidate) {
		this.mobileValidate = mobileValidate;
	}

	public int getRealValidate() {
		return realValidate;
	}

	public void setRealValidate(int realValidate) {
		this.realValidate = realValidate;
	}

	public java.lang.String getQq() {
		return qq;
	}

	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public java.lang.String getAnswer() {
		return answer;
	}

	public void setAnswer(java.lang.String answer) {
		this.answer = answer;
	}

	public int getMenberType() {
		return menberType;
	}

	public void setMenberType(int menberType) {
		this.menberType = menberType;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(java.util.Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public java.util.Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(int isBlackList) {
		this.isBlackList = isBlackList;
	}

	public java.lang.String getUserTerrace() {
		return userTerrace;
	}

	public void setUserTerrace(java.lang.String userTerrace) {
		this.userTerrace = userTerrace;
	}
	
	
	
}
