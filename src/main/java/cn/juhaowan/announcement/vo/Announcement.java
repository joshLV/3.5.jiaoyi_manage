package cn.juhaowan.announcement.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 公告
 * 
 * @author APXer
 * 
 */
@Table(name = "announcement")
public class Announcement implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "announcement_id", type = DbType.Int)
	private int announcementId;
	@Column(name = "title", type = DbType.Varchar)
	private java.lang.String title;
	@Column(name = "template", type = DbType.Varchar)
	private java.lang.String template;
	@Column(name = "content", type = DbType.Varchar)
	private java.lang.String content;

	@Column(name = "createtime", type = DbType.DateTime)
	private java.util.Date createtime;

	@Column(name = "validdate", type = DbType.DateTime)
	private java.util.Date validdate;

	@Column(name = "sendedTime", type = DbType.DateTime)
	private java.util.Date sendedTime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "category_id", type = DbType.Int)
	private int categoryId;
	
	@Column(name = "release_platform", type = DbType.Int)
	private int releasePlatform;
	
	@Column(name = "url", type = DbType.Varchar)
	private java.lang.String url;
	
	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.util.Date getSendedTime() {
		return sendedTime;
	}

	public void setSendedTime(java.util.Date sendedTime) {
		this.sendedTime = sendedTime;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(int announcementId) {
		this.announcementId = announcementId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
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

	public java.util.Date getValiddate() {
		return validdate;
	}

	public void setValiddate(java.util.Date validdate) {
		this.validdate = validdate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getTemplate() {
		return template;
	}

	public void setTemplate(java.lang.String template) {
		this.template = template;
	}

	public int getReleasePlatform() {
		return releasePlatform;
	}

	public void setReleasePlatform(int releasePlatform) {
		this.releasePlatform = releasePlatform;
	}
	

}