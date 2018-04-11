package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;
/**
 * 
 * @author chenchong
 * 商品购买规则
 */
@Table(name="product_buy_filter_conf") 
public class ProductBuyFilterConfig implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", type=DbType.Int)
	private int id;
	
	//规则名称
	@Column(name="name",type=DbType.Varchar)
	private java.lang.String name;
	
	//处理的类(如com.jugame.AAFilter)
	@Column(name="class_name",type=DbType.Varchar)
	private java.lang.String className;
	
	//参数说明
	@Column(name="param_desc",type=DbType.Varchar)
	private java.lang.String paramDesc;
	
	//权重
	@Column(name="weight",type=DbType.Int)
	private int weight;
	
	//备注
	@Column(name="remark",type=DbType.Varchar)
	private java.lang.String remark;
	
	@Column(name="type",type=DbType.Int)
	private int type;
	
	@Column(name="status",type=DbType.Int)
	private int status;
	
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

	public java.lang.String getClassName() {
		return className;
	}

	public void setClassName(java.lang.String className) {
		this.className = className;
	}

	public java.lang.String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(java.lang.String paramDesc) {
		this.paramDesc = paramDesc;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
