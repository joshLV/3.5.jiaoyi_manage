package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 角色信息
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_role")
public class SysRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID", nullable = false)
	private int roleId;
	
	@Column(name = "ROLE_NAME")
	private java.lang.String roleName;
	
	@Column(name = "ROLE_DESC")
	private java.lang.String roleDesc;
	
	@Column(name = "REMARK")
	private java.lang.String remark;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "ROLE_CODE")
	private java.lang.String roleCode;
	
	@Column(name = "ALLOW_UPDATE")
	private int allowUpdate;
	
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;
	
	@Column(name = "IS_CUSTOMER_R")
	private int isCustomerR;
	
	
	 
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public java.lang.String getRoleName() {
		return roleName;
	}

	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}

	public java.lang.String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(java.lang.String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}

	public int getAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(int allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getIsCustomerR() {
		return isCustomerR;
	}

	public void setIsCustomerR(int isCustomerR) {
		this.isCustomerR = isCustomerR;
	}

}