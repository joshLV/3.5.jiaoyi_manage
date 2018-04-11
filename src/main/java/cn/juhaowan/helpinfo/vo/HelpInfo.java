package cn.juhaowan.helpinfo.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 帮助信息
 * 
 */
@Table(name = "help_info")
public class HelpInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "help_id", type = DbType.Int)
	private int helpId;

	/**
	 * 帮助标题
	 */
	@Column(name = "help_title", type = DbType.Varchar)
	private java.lang.String helpTitle;

	/**
	 * 帮助内容
	 */
	@Column(name = "help_content", type = DbType.Varchar)
	private java.lang.String helpContent;

	/**
	 * 帮助类型
	 */
	@Column(name = "category_id", type = DbType.Int)
	private int categoryId;

	/**
	 * 创建时间
	 */
	@Column(name = "createtime", type = DbType.DateTime)
	private java.util.Date createtime;

	/**
	 * 常见度
	 */
	@Column(name = "weight", type = DbType.Int)
	private int weight;

	/**
	 * 标签
	 */
	@Column(name = "tag", type = DbType.Varchar)
	private java.lang.String tag;
	
	/**
	 * 是否显示，默认显示值：1，不显示：0
	 */
	@Column(name = "is_show",type = DbType.Int)
	private int isShow;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHelpId() {
		return helpId;
	}

	public void setHelpId(int helpId) {
		this.helpId = helpId;
	}

	public java.lang.String getHelpTitle() {
		return helpTitle;
	}

	public void setHelpTitle(java.lang.String helpTitle) {
		this.helpTitle = helpTitle;
	}

	public java.lang.String getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(java.lang.String helpContent) {
		this.helpContent = helpContent;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public java.lang.String getTag() {
		return tag;
	}

	public void setTag(java.lang.String tag) {
		this.tag = tag;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	
	

}
