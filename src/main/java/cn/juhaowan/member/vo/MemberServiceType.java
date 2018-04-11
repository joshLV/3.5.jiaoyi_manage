package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 
 * @author Administrator
 * 
 */
@Table(name = "member_servicetype")
public class MemberServiceType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;
	@Column(name = "service_code", type = DbType.Varchar)
	private String serviceCode;
	@Column(name = "weight", type = DbType.Int)
	private int weight;

	@Column(name = "imgUrl", type = DbType.Varchar)
	private java.lang.String imgurl;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	public int getId() {
		return id;
	}

	public java.lang.String getName() {
		return name;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public int getWeight() {
		return weight;
	}

	public java.lang.String getImgurl() {
		return imgurl;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public int getStatus() {
		return status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setImgurl(java.lang.String imgurl) {
		this.imgurl = imgurl;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}
