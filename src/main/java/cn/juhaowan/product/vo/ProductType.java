package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;



/**
 * 
 * @author Administrator
 * 
 */
@Table(name = "product_type")
public class ProductType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "product_type", type = DbType.Varchar)
	private java.lang.String productType;

	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;

	@Column(name = "status", type = DbType.Int)
	private int status;
	
	@Column(name = "product_type_group_id", type = DbType.Int)
	private int proTypeGroupId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getProductType() {
		return productType;
	}

	public void setProductType(java.lang.String productType) {
		this.productType = productType;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProTypeGroupId() {
		return proTypeGroupId;
	}

	public void setProTypeGroupId(int proTypeGroupId) {
		this.proTypeGroupId = proTypeGroupId;
	}

}