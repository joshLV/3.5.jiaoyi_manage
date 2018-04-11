package cn.juhaowan.localmessage.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name="message_content")
public class MessageContent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "message_content_id",type=DbType.Int)
	private int messageContentId;
	
	@Column(name = "message_text",type=DbType.Varchar)
	private java.lang.String messageText;
	
	 
	public int getMessageContentId() {
		return messageContentId;
	}

	public void setMessageContentId(int messageContentId) {
		this.messageContentId = messageContentId;
	}

	public java.lang.String getMessageText() {
		return messageText;
	}

	public void setMessageText(java.lang.String messageText) {
		this.messageText = messageText;
	}

}