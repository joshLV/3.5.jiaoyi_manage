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
@Table(name = "product_log")
public class ProductLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "product_id", type = DbType.Varchar)
	private java.lang.String productId;

	@Column(name = "operate_userid", type = DbType.Int)
	private int operateUserid;

	@Column(name = "operate_type", type = DbType.Int)
	private int operateType;

	@Column(name = "description", type = DbType.Varchar)
	private java.lang.String description;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

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

	public int getOperateUserid() {
		return operateUserid;
	}

	public void setOperateUserid(int operateUserid) {
		this.operateUserid = operateUserid;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}