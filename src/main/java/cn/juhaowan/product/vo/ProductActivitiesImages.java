package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 活动图片
 */
@Table(name = "product_activities_images")
public class ProductActivitiesImages implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "activitie_id", type = DbType.Varchar)
	private java.lang.String activitieId;

	@Column(name = "image_url", type = DbType.Varchar)
	private java.lang.String imageUrl;

	@Column(name = "type", type = DbType.Int)
	private int type;

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

	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
