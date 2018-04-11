package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 商品购买规则详情
 */
@Table(name = "product_buy_filter")
public class ProductBuyFilter implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;

	@Column(name = "product_id", type = DbType.Varchar)
	private java.lang.String productId;

	@Column(name = "product_name", type = DbType.Varchar)
	private java.lang.String productName;

	@Column(name = "filter_id", type = DbType.Int)
	private int filterId;

	@Column(name = "param", type = DbType.Varchar)
	private java.lang.String param;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "err_message", type = DbType.Varchar)
	private java.lang.String errMessage;

	@Column(name = "activitie_id", type = DbType.Varchar)
	private java.lang.String activitieId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	public int getFilterId() {
		return filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}

	public java.lang.String getParam() {
		return param;
	}

	public void setParam(java.lang.String param) {
		this.param = param;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(java.lang.String errMessage) {
		this.errMessage = errMessage;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getActivitieId() {
		return activitieId;
	}

	public void setActivitieId(java.lang.String activitieId) {
		this.activitieId = activitieId;
	}



}
