package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;




/**
 * 日金额统计
 * 
 * @author Administrator
 * 
 */
@Table(name = "count_day_money_io")
public class CountDayMoneyIo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "take_member_num", type = DbType.Int)
	private int takeMemberNum;

	@Column(name = "take_num", type = DbType.Int)
	private int takeNum;

	@Column(name = "take_member_suc_num", type = DbType.Int)
	private int takeMemberSucNum;

	@Column(name = "take_suc_num", type = DbType.Int)
	private int takeSucNum;

	@Column(name = "take_money_day_count", type = DbType.Double)
	private double takeMoneyDayCount;

	@Column(name = "recharge_member_num", type = DbType.Int)
	private int rechargeMemberNum;

	@Column(name = "recharge_num", type = DbType.Int)
	private int rechargeNum;

	@Column(name = "recharge_money_count", type = DbType.Double)
	private double rechargeMoneyCount;

	@Column(name = "account_amount", type = DbType.Double)
	private double accountAmount;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTakeMemberNum() {
		return takeMemberNum;
	}

	public void setTakeMemberNum(int takeMemberNum) {
		this.takeMemberNum = takeMemberNum;
	}

	public int getTakeNum() {
		return takeNum;
	}

	public void setTakeNum(int takeNum) {
		this.takeNum = takeNum;
	}

	public int getTakeMemberSucNum() {
		return takeMemberSucNum;
	}

	public void setTakeMemberSucNum(int takeMemberSucNum) {
		this.takeMemberSucNum = takeMemberSucNum;
	}

	public int getTakeSucNum() {
		return takeSucNum;
	}

	public void setTakeSucNum(int takeSucNum) {
		this.takeSucNum = takeSucNum;
	}

	public int getRechargeMemberNum() {
		return rechargeMemberNum;
	}

	public void setRechargeMemberNum(int rechargeMemberNum) {
		this.rechargeMemberNum = rechargeMemberNum;
	}

	public int getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(int rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	public double getTakeMoneyDayCount() {
		return takeMoneyDayCount;
	}

	public void setTakeMoneyDayCount(double takeMoneyDayCount) {
		this.takeMoneyDayCount = takeMoneyDayCount;
	}

	public double getRechargeMoneyCount() {
		return rechargeMoneyCount;
	}

	public void setRechargeMoneyCount(double rechargeMoneyCount) {
		this.rechargeMoneyCount = rechargeMoneyCount;
	}

	public void setAccountAmount(double accountAmount) {
		this.accountAmount = accountAmount;
	}

	public java.lang.Double getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(java.lang.Double accountAmount) {
		this.accountAmount = accountAmount;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

}