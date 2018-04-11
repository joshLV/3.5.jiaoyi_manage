package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;



/**
 * 用户统计
 * 
 * @author Administrator
 * 
 */
@Table(name = "count_day_user")
public class CountDayUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "reg_num", type = DbType.Int)
	private int regNum;

	@Column(name = "real_num", type = DbType.Int)
	private int realNum;
	
	@Column(name = "total_num", type = DbType.Int)
	private int totalNum;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;
	
	@Column(name = "real_total", type = DbType.Int)
	private int realTotal;

	// 注册用户日UV
	private int regUv;
	// 注册用户日PV
	private int regPv;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRegNum() {
		return regNum;
	}

	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}

	public int getRealNum() {
		return realNum;
	}

	public void setRealNum(int realNum) {
		this.realNum = realNum;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

	public int getRegUv() {
		return regUv;
	}

	public void setRegUv(int regUv) {
		this.regUv = regUv;
	}

	public int getRegPv() {
		return regPv;
	}

	public void setRegPv(int regPv) {
		this.regPv = regPv;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRealTotal() {
		return realTotal;
	}

	public void setRealTotal(int realTotal) {
		this.realTotal = realTotal;
	}
	
	

}