package cn.juhaowan.helpinfo.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 帮助分类
 * 
 */
@Table(name = "help_category")
public class HelpCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "help_category_id", type = DbType.Int)
	private int helpCategoryId;

	@Column(name = "category_name", type = DbType.Varchar)
	private java.lang.String categoryName;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "is_show",type = DbType.Int)
	private int isShow;

	public int getHelpCategoryId() {
		return helpCategoryId;
	}

	public void setHelpCategoryId(int helpCategoryId) {
		this.helpCategoryId = helpCategoryId;
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

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	
}
