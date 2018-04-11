package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;




/**
 * 日活统计
 * 
 * @author Administrator
 * 
 */
@Table(name = "count_day_active")
public class CountDayActive implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "reg_uv", type = DbType.Int)
	private int regUv;

	@Column(name = "no_reg_uv", type = DbType.Int)
	private int noRegUv;

	@Column(name = "reg_pv", type = DbType.Int)
	private int regPv;

	@Column(name = "no_reg_pv", type = DbType.Int)
	private int noRegPv;

	@Column(name = "pv_uv_rate", type = DbType.Float)
	private java.lang.Float pvUvRate;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegUv() {
		return regUv;
	}

	public void setRegUv(int regUv) {
		this.regUv = regUv;
	}

	public int getNoRegUv() {
		return noRegUv;
	}

	public void setNoRegUv(int noRegUv) {
		this.noRegUv = noRegUv;
	}

	public int getRegPv() {
		return regPv;
	}

	public void setRegPv(int regPv) {
		this.regPv = regPv;
	}

	public int getNoRegPv() {
		return noRegPv;
	}

	public void setNoRegPv(int noRegPv) {
		this.noRegPv = noRegPv;
	}

	public java.lang.Float getPvUvRate() {
		return pvUvRate;
	}

	public void setPvUvRate(java.lang.Float pvUvRate) {
		this.pvUvRate = pvUvRate;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

}