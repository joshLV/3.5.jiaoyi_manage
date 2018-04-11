package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统参数实体
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_parameter")
public class SysParameter implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PARA_TYPE")
	private java.lang.String paraType;
	
	@Column(name = "PARA_CODE")
	private java.lang.String paraCode;
	
	@Column(name = "DSCR")
	private java.lang.String dscr;
	
	@Column(name = "PARA_VALUE")
	private java.lang.String paraValue;
	
	@Column(name = "ALLOW_UPDATE")
	private int allowUpdate;
	
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;
	
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getParaType() {
		return paraType;
	}

	public void setParaType(java.lang.String paraType) {
		this.paraType = paraType;
	}

	public java.lang.String getParaCode() {
		return paraCode;
	}

	public void setParaCode(java.lang.String paraCode) {
		this.paraCode = paraCode;
	}

	public java.lang.String getDscr() {
		return dscr;
	}

	public void setDscr(java.lang.String dscr) {
		this.dscr = dscr;
	}

	public java.lang.String getParaValue() {
		return paraValue;
	}

	public void setParaValue(java.lang.String paraValue) {
		this.paraValue = paraValue;
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

}