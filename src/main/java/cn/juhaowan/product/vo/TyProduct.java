/**
 * 
 */
package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Table;

/**
 * 退游商品表 
 * 
 * @author APXer
 * 
 */
@Table(name = "ty_product")
public class TyProduct implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "product_id", type = DbType.Varchar)
	private String productId;
	
	@Column(name = "market_value", type = DbType.Int)
	private int marketValue;
	
	@Column(name = "pic_url", type = DbType.Varchar)
	private String picUrl;
	
	@Column(name = "status", type = DbType.Int)
	private int status;
	
	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "is_recommend", type = DbType.Int)
	private int isRecommend;
	
	@Column(name = "show_info", type = DbType.Varchar)
	private String showInfo;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(int marketValue) {
		this.marketValue = marketValue;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getShowInfo() {
		return showInfo;
	}

	public void setShowInfo(String showInfo) {
		this.showInfo = showInfo;
	}
	
	
}
