package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.*;

@Table(name="count_program")
public class CountProgram implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "parent_program",type=DbType.Int)
	private int parentProgram;
	
	@Column(name = "program_name",type=DbType.Varchar)
	private java.lang.String programName;
	
	@Column(name = "program_url",type=DbType.Varchar)
	private java.lang.String programUrl;
	
	@Column(name = "create_uuid",type=DbType.Int)
	private int createUuid;
	
	@Column(name = "create_time",type=DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "status",type=DbType.Int)
	private int status;
	
	@Column(name = "remark",type=DbType.Varchar)
	private java.lang.String remark;
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentProgram() {
		return parentProgram;
	}

	public void setParentProgram(int parentProgram) {
		this.parentProgram = parentProgram;
	}

	public java.lang.String getProgramName() {
		return programName;
	}

	public void setProgramName(java.lang.String programName) {
		this.programName = programName;
	}

	public java.lang.String getProgramUrl() {
		return programUrl;
	}

	public void setProgramUrl(java.lang.String programUrl) {
		this.programUrl = programUrl;
	}

	public int getCreateUuid() {
		return createUuid;
	}

	public void setCreateUuid(int createUuid) {
		this.createUuid = createUuid;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}