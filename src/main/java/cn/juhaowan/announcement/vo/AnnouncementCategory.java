package cn.juhaowan.announcement.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 公告分类
 * @author APXer
 *
 */
@Table(name = "announcement_category")
public class AnnouncementCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "announcement_category_id", type = DbType.Int)
	private int announcementCategoryId;

	@Column(name = "category_name", type = DbType.Varchar)
	private java.lang.String categoryName;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	public int getAnnouncementCategoryId() {
		return announcementCategoryId;
	}

	public void setAnnouncementCategoryId(int announcementCategoryId) {
		this.announcementCategoryId = announcementCategoryId;
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