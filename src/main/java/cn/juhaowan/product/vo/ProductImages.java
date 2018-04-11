package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

@Table(name="product_images")
public class ProductImages implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "product_id",type=DbType.Varchar)
	private java.lang.String productId;
	
	@Column(name = "pic_url",type=DbType.Varchar)
	private java.lang.String picUrl;
	
	@Column(name = "create_time",type=DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "status",type=DbType.Int)
	private int status;
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	public java.lang.String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}