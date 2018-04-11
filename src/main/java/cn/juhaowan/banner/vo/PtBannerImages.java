package cn.juhaowan.banner.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 * 幻灯片图片
 * @author Administrator
 *
 */
@Table(name = "pt_banner_images")
public class PtBannerImages implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "banner_id", type = DbType.Int)
	private int bannerId;

	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;

	@Column(name = "image_url", type = DbType.Varchar)
	private java.lang.String imageUrl;

	@Column(name = "alink", type = DbType.Varchar)
	private java.lang.String alink;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "up_time", type = DbType.DateTime)
	private java.util.Date upTime;

	@Column(name = "down_time", type = DbType.DateTime)
	private java.util.Date downTime;
	
	//图片显示图片链接
	private java.lang.String imageUrlShow;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBannerId() {
		return bannerId;
	}

	public void setBannerId(int bannerId) {
		this.bannerId = bannerId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public java.lang.String getAlink() {
		return alink;
	}

	public void setAlink(java.lang.String alink) {
		this.alink = alink;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getUpTime() {
		return upTime;
	}

	public void setUpTime(java.util.Date upTime) {
		this.upTime = upTime;
	}

	public java.util.Date getDownTime() {
		return downTime;
	}

	public void setDownTime(java.util.Date downTime) {
		this.downTime = downTime;
	}

	public java.lang.String getImageUrlShow() {
		return imageUrlShow;
	}

	public void setImageUrlShow(java.lang.String imageUrlShow) {
		this.imageUrlShow = imageUrlShow;
	}
	
	

}