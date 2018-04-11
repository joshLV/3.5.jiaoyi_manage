package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;


/**
 * 账户统计
 * @author Administrator
 *
 */
@Table(name = "count_day_account")
public class CountDayAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "take_money_sum", type = DbType.Double)
	private double takeMoneySum;

	@Column(name = "recharge_sum", type = DbType.Double)
	private double rechargeSum;

	@Column(name = "balance1_sum", type = DbType.Double)
	private double balance1Sum;

	@Column(name = "deposit_amount_sum", type = DbType.Double)
	private double depositAmountSum;

	@Column(name = "balance2_sum", type = DbType.Double)
	private double balance2Sum;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTakeMoneySum() {
		return takeMoneySum;
	}

	public void setTakeMoneySum(double takeMoneySum) {
		this.takeMoneySum = takeMoneySum;
	}

	public double getRechargeSum() {
		return rechargeSum;
	}

	public void setRechargeSum(double rechargeSum) {
		this.rechargeSum = rechargeSum;
	}

	public double getBalance1Sum() {
		return balance1Sum;
	}

	public void setBalance1Sum(double balance1Sum) {
		this.balance1Sum = balance1Sum;
	}

	public double getDepositAmountSum() {
		return depositAmountSum;
	}

	public void setDepositAmountSum(double depositAmountSum) {
		this.depositAmountSum = depositAmountSum;
	}

	public double getBalance2Sum() {
		return balance2Sum;
	}

	public void setBalance2Sum(double balance2Sum) {
		this.balance2Sum = balance2Sum;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

}