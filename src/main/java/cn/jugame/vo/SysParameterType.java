package cn.jugame.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 参数类型
 * @author houjt
 *
 */
@Entity
@Table(name = "sys_parameter_type")
public class SysParameterType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PARA_TYPE")
	private java.lang.String paraType;
	
	@Column(name = "DSCR")
	private java.lang.String dscr;
	
	@Column(name = "CREATE_DATE")
	private java.util.Date createDate;
	
	
	 
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

	public java.lang.String getDscr() {
		return dscr;
	}

	public void setDscr(java.lang.String dscr) {
		this.dscr = dscr;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

}