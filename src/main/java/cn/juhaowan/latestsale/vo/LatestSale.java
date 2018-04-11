/**
 * 
 */
package cn.juhaowan.latestsale.vo;

import java.io.Serializable;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * @author APXer
 *         最新出售
 */
@Table(name = "latest_sale")
public class LatestSale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	/**
	 * 最新出售标题
	 */
	@Column(name = "title", type = DbType.Varchar)
	private java.lang.String title;

	/**
	 * 信息类型
	 */
	@Column(name = "category", type = DbType.Int)
	private int category;
	
	/**
	 * 权重
	 */
	@Column(name = "weight", type = DbType.Int)
	private int weight;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	public int getId() {
		return id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public int getCategory() {
		return category;
	}

	public int getWeight() {
		return weight;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


}
