package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统日志对象
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_operation_log")
public class SysOperationLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "MODULE_CODE")
	private java.lang.String moduleCode;
	
	@Column(name = "MODULE_NAME")
	private java.lang.String moduleName;
	
	@Column(name = "OPERATE_TYPE")
	private java.lang.String operateType;
	
	@Column(name = "OPERATE_DSCR")
	private java.lang.String operateDscr;
	
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;
	
	@Column(name = "REMARK")
	private java.lang.String remark;
	
	@Column(name = "PART_MONTH")
	private java.lang.String partMonth;
	
	@Column(name = "CLIENT_IP")
	private java.lang.String clientIp;
	
	@Column(name = "LOGINID")
	private java.lang.String loginid;
	
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(java.lang.String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public java.lang.String getModuleName() {
		return moduleName;
	}

	public void setModuleName(java.lang.String moduleName) {
		this.moduleName = moduleName;
	}

	public java.lang.String getOperateType() {
		return operateType;
	}

	public void setOperateType(java.lang.String operateType) {
		this.operateType = operateType;
	}

	public java.lang.String getOperateDscr() {
		return operateDscr;
	}

	public void setOperateDscr(java.lang.String operateDscr) {
		this.operateDscr = operateDscr;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getPartMonth() {
		return partMonth;
	}

	public void setPartMonth(java.lang.String partMonth) {
		this.partMonth = partMonth;
	}

	public java.lang.String getClientIp() {
		return clientIp;
	}

	public void setClientIp(java.lang.String clientIp) {
		this.clientIp = clientIp;
	}

	public java.lang.String getLoginid() {
		return loginid;
	}

	public void setLoginid(java.lang.String loginid) {
		this.loginid = loginid;
	}

}