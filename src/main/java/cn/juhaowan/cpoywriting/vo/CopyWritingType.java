package cn.juhaowan.cpoywriting.vo;

import java.util.Date;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

// Generated 2018-8-23 10:13:29 by Hibernate Tools 3.4.0.CR1

/**
 * CopyWritingType generated by hbm2java
 */
@Table(name = "copy_writing_type")
public class CopyWritingType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id",type = DbType.Int)
	private int id;
	@Column(name = "name", type = DbType.Varchar)
	private String name;
	@Column(name = "remark", type = DbType.Varchar)
	private String remark;
	@Column(name = "create_time", type = DbType.DateTime)
	private Date createTime;
	@Column(name = "modify_time", type = DbType.DateTime)
	private Date modifyTime;
	@Column(name = "status", type = DbType.Varchar)
	private int status;
	@Column(name = "weight", type = DbType.Varchar)
	private int weight;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

}