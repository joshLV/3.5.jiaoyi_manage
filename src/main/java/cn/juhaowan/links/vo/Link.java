/**
 * 
 */
package cn.juhaowan.links.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 *  友情链接
 */
@Table(name = "links")
public class Link implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	/**
	 * 友情链接名称
	 */
	@Column(name = "name", type = DbType.Varchar)
	private String name;

	/**
	 * 友情链接 url
	 */
	@Column(name = "url", type = DbType.Varchar)
	private String url;

	/**
	 * 友情链接图片 url
	 */
	@Column(name = "img_url", type = DbType.Varchar)
	private String imgUrl;

	/**
	 * 权重
	 */
	@Column(name = "weight", type = DbType.Int)
	private int weight;

	/**
	 * 权重
	 */
	@Column(name = "platform", type = DbType.Int)
	private int platform;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
