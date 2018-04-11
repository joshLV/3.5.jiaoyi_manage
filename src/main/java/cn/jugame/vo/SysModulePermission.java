package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 模块权限实体
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_module_permission")
public class SysModulePermission implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PID", nullable = false)
	private int pid;
	
	@Column(name = "MOD_ID")
	private int modId;
	
	@Column(name = "PER_NAME")
	private java.lang.String perName;
	
	@Column(name = "PER_CODE")
	private java.lang.String perCode;
	
	@Column(name = "REMARK")
	private java.lang.String remark;
	
	
	 
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getModId() {
		return modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
	}

	public java.lang.String getPerName() {
		return perName;
	}

	public void setPerName(java.lang.String perName) {
		this.perName = perName;
	}

	public java.lang.String getPerCode() {
		return perCode;
	}

	public void setPerCode(java.lang.String perCode) {
		this.perCode = perCode;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}