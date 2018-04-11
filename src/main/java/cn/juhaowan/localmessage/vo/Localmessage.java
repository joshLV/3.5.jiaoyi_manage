package cn.juhaowan.localmessage.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name = "localmessage")
public class Localmessage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "message_id", type = DbType.Int)
	private int messageId;

	@Column(name = "message_content_id", type = DbType.Int)
	private int messageContentId;

	@Column(name = "message_title", type = DbType.Varchar)
	private java.lang.String messageTitle;

	@Column(name = "user_id", type = DbType.Int)
	private int userId;

	@Column(name = "receive_time", type = DbType.DateTime)
	private java.util.Date receiveTime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "mmc_id", type = DbType.Int)
	private int mmcId;

	public int getMmcId() {
		return mmcId;
	}

	public void setMmcId(int mmcId) {
		this.mmcId = mmcId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getMessageContentId() {
		return messageContentId;
	}

	public void setMessageContentId(int messageContentId) {
		this.messageContentId = messageContentId;
	}

	public java.lang.String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(java.lang.String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public java.util.Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(java.util.Date receive_time) {
		this.receiveTime = receive_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
