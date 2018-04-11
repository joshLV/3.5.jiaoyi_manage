package cn.juhaowan.count.vo;

import java.util.Date;

/**
 * 日工时率
 * 
 * @author APXer
 * 
 */
public class DayWorkRate {
	private Date day;
	private double workRate;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public double getWorkRate() {
		return workRate;
	}

	public void setWorkRate(double workRate) {
		this.workRate = workRate;
	}

}
