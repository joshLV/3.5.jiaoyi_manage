package cn.juhaowan.suggestion.vo;

import cn.jugame.dal.annotation.*;
@Table(name="suggestion")
public class Suggestion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "suggestion_id",type=DbType.Int)
	private int suggestionId;
	
	@Column(name = "user_id",type=DbType.Int)
	private int userId;
	
	@Column(name = "content",type=DbType.Varchar)
	private java.lang.String content;
	
	@Column(name = "createtime",type=DbType.DateTime)
	private java.util.Date createtime;
	
	@Column(name = "status",type=DbType.Int)
	private int status;
	
	@Column(name = "qq",type=DbType.Varchar)
	private java.lang.String qq;
	
	@Column(name = "email",type=DbType.Varchar)
	private java.lang.String email;
	
	@Column(name = "mobile",type=DbType.Varchar)
	private java.lang.String mobile;
	
	@Column(name = "source_type",type=DbType.Int)
	private int sourceType;
	
	@Column(name = "category_id",type=DbType.Int)
	private int categoryId;
	
	@Column(name = "user_game_id",type=DbType.Varchar)
	private java.lang.String userGameId;
	
	@Column(name = "remark",type=DbType.Varchar)
	private java.lang.String remark;
	
	@Column(name = "is_handled",type=DbType.Int)
	private int isHandled;
	
	@Column(name = "handled_user_id",type=DbType.Int)
	private int handledUserId;
	
	@Column(name = "handled_time",type=DbType.DateTime)
	private java.util.Date handledTime;
	 
	@Column(name = "source",type=DbType.Int)
	private int source;
	
	@Column(name = "si",type=DbType.Varchar)
	private java.lang.String si;
	
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getIsHandled() {
		return isHandled;
	}

	public void setIsHandled(int isHandled) {
		this.isHandled = isHandled;
	}

	public int getHandledUserId() {
		return handledUserId;
	}

	public void setHandledUserId(int handledUserId) {
		this.handledUserId = handledUserId;
	}

	public java.util.Date getHandledTime() {
		return handledTime;
	}

	public void setHandledTime(java.util.Date handledTime) {
		this.handledTime = handledTime;
	}

	public int getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(int suggestionId) {
		this.suggestionId = suggestionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getQq() {
		return qq;
	}

	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public java.lang.String getUserGameId() {
		return userGameId;
	}

	public void setUserGameId(java.lang.String userGameId) {
		this.userGameId = userGameId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public java.lang.String getSi() {
		return si;
	}

	public void setSi(java.lang.String si) {
		this.si = si;
	}
	
	

}