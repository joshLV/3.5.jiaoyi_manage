package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

@Table(name="product_hot_sale")
public class ProductHotSale implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "product_id",type=DbType.Varchar)
	private java.lang.String productId;
	
	@Column(name = "weight",type=DbType.Int)
	private int weight;
	
	@Column(name = "create_time",type=DbType.DateTime)
	private java.util.Date createTime;
	
	private String productName;
	
	private int productStatus;
	
	 
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
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

}