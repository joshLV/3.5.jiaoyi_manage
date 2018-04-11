package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

@Table(name="product_self_defined")
public class ProductSelfDefined implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "product_id",type=DbType.Varchar)
	private java.lang.String productId;
	
	@Column(name = "self_defined",type=DbType.Varchar)
	private java.lang.String selfDefined;
	
	 
	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	public java.lang.String getSelfDefined() {
		return selfDefined;
	}

	public void setSelfDefined(java.lang.String selfDefined) {
		this.selfDefined = selfDefined;
	}

}