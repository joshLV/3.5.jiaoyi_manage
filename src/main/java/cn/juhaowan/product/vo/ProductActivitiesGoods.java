package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 商品购买活动
 */
@Table(name = "product_activities_goods")
public class ProductActivitiesGoods implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "product_id", type = DbType.Varchar)
	private java.lang.String productId;

	@Column(name = "activitie_id", type = DbType.Varchar)
	private java.lang.String activitieId;

	@Column(name = "inpage_show_flag", type = DbType.Int)
	private int inpageShowFlag;


	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}



	public java.lang.String getActivitieId() {
		return activitieId;
	}

	public void setActivitieId(java.lang.String activitieId) {
		this.activitieId = activitieId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInpageShowFlag() {
		return inpageShowFlag;
	}

	public void setInpageShowFlag(int inpageShowFlag) {
		this.inpageShowFlag = inpageShowFlag;
	}

	
	
}
