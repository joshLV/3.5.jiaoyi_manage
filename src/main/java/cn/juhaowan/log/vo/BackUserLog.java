package cn.juhaowan.log.vo;

import cn.jugame.dal.annotation.*;

@Table(name="back_user_log")
public class BackUserLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "uid",type=DbType.Int)
	private int uid;
	
	@Column(name = "log_ip",type=DbType.Varchar)
	private java.lang.String logIp;
	
	@Column(name = "log_type",type=DbType.Int)
	private int logType;
	
	@Column(name = "log_remark",type=DbType.Varchar)
	private java.lang.String logRemark;
	
	@Column(name = "create_time",type=DbType.DateTime)
	private java.util.Date createTime;
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public java.lang.String getLogIp() {
		return logIp;
	}

	public void setLogIp(java.lang.String logIp) {
		this.logIp = logIp;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public java.lang.String getLogRemark() {
		return logRemark;
	}

	public void setLogRemark(java.lang.String logRemark) {
		this.logRemark = logRemark;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}