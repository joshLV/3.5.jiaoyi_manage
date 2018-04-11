package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 活动游戏下载
 */
@Table(name = "product_activities_download")
public class ProductActivitiesDownload implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "activitie_id", type = DbType.Varchar)
	private java.lang.String activitieId;

	@Column(name = "button_name", type = DbType.Varchar)
	private java.lang.String buttonName;
	
	@Column(name = "download_url", type = DbType.Varchar)
	private java.lang.String downloadUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getActivitieId() {
		return activitieId;
	}

	public void setActivitieId(java.lang.String activitieId) {
		this.activitieId = activitieId;
	}

	public java.lang.String getButtonName() {
		return buttonName;
	}

	public void setButtonName(java.lang.String buttonName) {
		this.buttonName = buttonName;
	}

	public java.lang.String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(java.lang.String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	

	
}
