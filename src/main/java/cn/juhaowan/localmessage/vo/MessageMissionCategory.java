package cn.juhaowan.localmessage.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 站内信类型
 * 
 */
@Table(name = "message_mission_category")
public class MessageMissionCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mmc_id", type = DbType.Int)
	private int mmcId;

	@Column(name = "category_name", type = DbType.Varchar)
	private java.lang.String categoryName;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	public int getMmcId() {
		return mmcId;
	}

	public void setMmcId(int mmcId) {
		this.mmcId = mmcId;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
