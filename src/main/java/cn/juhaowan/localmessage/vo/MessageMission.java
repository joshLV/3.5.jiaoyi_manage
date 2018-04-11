package cn.juhaowan.localmessage.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name = "message_mission")
public class MessageMission implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "message_mission_id", type = DbType.Int)
	private int messageMissionId;

	@Column(name = "receiver", type = DbType.Varchar)
	private java.lang.String receiver;

	@Column(name = "message_content", type = DbType.Varchar)
	private java.lang.String messageContent;

	@Column(name = "message_title", type = DbType.Varchar)
	private java.lang.String messageTitle;

	@Column(name = "createtime", type = DbType.DateTime)
	private java.util.Date createtime;

	@Column(name = "sendtime", type = DbType.DateTime)
	private java.util.Date sendtime;

	@Column(name = "sendedtime", type = DbType.DateTime)
	private java.util.Date sendedtime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "flag", type = DbType.Int)
	private int flag;

	@Column(name = "mmc_id", type = DbType.Int)
	private int mmcId;

	public int getMessageMissionId() {
		return messageMissionId;
	}

	public void setMessageMissionId(int messageMissionId) {
		this.messageMissionId = messageMissionId;
	}

	public java.lang.String getReceiver() {
		return receiver;
	}

	public void setReceiver(java.lang.String receiver) {
		this.receiver = receiver;
	}

	public java.lang.String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(java.lang.String messageContent) {
		this.messageContent = messageContent;
	}

	public java.lang.String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(java.lang.String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public java.util.Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(java.util.Date sendtime) {
		this.sendtime = sendtime;
	}

	public java.util.Date getSendedtime() {
		return sendedtime;
	}

	public void setSendedtime(java.util.Date sendedtime) {
		this.sendedtime = sendedtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getMmcId() {
		return mmcId;
	}

	public void setMmcId(int mmcId) {
		this.mmcId = mmcId;
	}

}
