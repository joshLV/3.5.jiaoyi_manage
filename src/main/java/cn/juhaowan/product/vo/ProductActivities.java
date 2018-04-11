package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 商品购买活动
 */
@Table(name = "product_activities")
public class ProductActivities implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Varchar)
	private java.lang.String id;

	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;

	@Column(name = "info", type = DbType.Varchar)
	private java.lang.String info;

	@Column(name = "begin_time", type = DbType.DateTime)
	private java.util.Date beginTime;
	
	@Column(name = "end_time", type = DbType.DateTime)
	private java.util.Date endTime;

	@Column(name = "status", type = DbType.Int)
	private int status;
	
	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "sub_title", type = DbType.Varchar)
	private java.lang.String subTitle;
	
	@Column(name = "saleout_show_flag", type = DbType.Int)
	private int saleoutShowFlag;
	
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;
	
	@Column(name = "game_name", type = DbType.Varchar)
	private java.lang.String gameName;
	
	@Column(name = "game_download_logo_url", type = DbType.Varchar)
	private java.lang.String gameDownloadLogoUrl;
	
//	@Column(name = "image_link_flag", type = DbType.Int)
//	private int imageLinkFlag;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "tag", type = DbType.Varchar)
	private String tag;
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getInfo() {
		return info;
	}

	public void setInfo(java.lang.String info) {
		this.info = info;
	}

	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(java.lang.String subTitle) {
		this.subTitle = subTitle;
	}

	public int getSaleoutShowFlag() {
		return saleoutShowFlag;
	}

	public void setSaleoutShowFlag(int saleoutShowFlag) {
		this.saleoutShowFlag = saleoutShowFlag;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public java.lang.String getGameName() {
		return gameName;
	}

	public void setGameName(java.lang.String gameName) {
		this.gameName = gameName;
	}

	public java.lang.String getGameDownloadLogoUrl() {
		return gameDownloadLogoUrl;
	}

	public void setGameDownloadLogoUrl(java.lang.String gameDownloadLogoUrl) {
		this.gameDownloadLogoUrl = gameDownloadLogoUrl;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	

}
