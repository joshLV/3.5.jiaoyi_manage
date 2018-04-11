package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 模块实体
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_module")
public class SysModule implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MOD_ID", nullable = false)
	private int modId;
	
	@Column(name = "PARENT_ID")
	private int parentId;
	
	@Column(name = "MODULE_CODE")
	private java.lang.String moduleCode;
	
	@Column(name = "MODULE_NAME")
	private java.lang.String moduleName;
	
	@Column(name = "LEVEL_SEQ")
	private int levelSeq;
	
	@Column(name = "FIRST_PAGE")
	private java.lang.String firstPage;
	
	@Column(name = "TARGET")
	private java.lang.String target;
	
	@Column(name = "CSS_NAME")
	private java.lang.String cssName;
	
	@Column(name = "IS_MENU")
	private int isMenu;
	
	@Column(name = "REMARK")
	private java.lang.String remark;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "ORDER_No")
	private int orderNo;
	
	@Column(name = "is_external")
	private int isExternal;
	
	 
	public int getModId() {
		return modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public int getLevelSeq() {
		return levelSeq;
	}

	public void setLevelSeq(int levelSeq) {
		this.levelSeq = levelSeq;
	}

	public java.lang.String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(java.lang.String firstPage) {
		this.firstPage = firstPage;
	}

	public java.lang.String getTarget() {
		return target;
	}

	public void setTarget(java.lang.String target) {
		this.target = target;
	}

	public java.lang.String getCssName() {
		return cssName;
	}

	public void setCssName(java.lang.String cssName) {
		this.cssName = cssName;
	}

	public int getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
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

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(int isExternal) {
		this.isExternal = isExternal;
	}


	

}