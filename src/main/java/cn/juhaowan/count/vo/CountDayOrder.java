package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;



/**
 * 交易统计
 * 
 * @author Administrator
 * 
 */
@Table(name = "count_day_order")
public class CountDayOrder implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "order_suc_num", type = DbType.Int)
	private int orderSucNum;

	@Column(name = "order_faile_num", type = DbType.Int)
	private int orderFaileNum;
	
	@Column(name = "order_fail_api_num", type = DbType.Int)
	private int orderFailApiNum;
	
	@Column(name = "order_fail_c2c_num", type = DbType.Int)
	private int orderFailC2cNum;

	@Column(name = "order_suc_api_num", type = DbType.Int)
	private int orderSucApiNum;
	
	@Column(name = "order_suc_c2c_num", type = DbType.Int)
	private int orderSucC2cNum;
	
	@Column(name = "order_suc_api_money", type = DbType.Float)
	private java.lang.Float orderSucApiMoney;
	
	@Column(name = "order_suc_c2c_money", type = DbType.Float)
	private java.lang.Float orderSucC2cMoney;
	
	@Column(name = "order_day_money_num", type = DbType.Float)
	private java.lang.Float orderDayMoneyNum;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderSucNum() {
		return orderSucNum;
	}

	public void setOrderSucNum(int orderSucNum) {
		this.orderSucNum = orderSucNum;
	}

	public int getOrderFaileNum() {
		return orderFaileNum;
	}

	public void setOrderFaileNum(int orderFaileNum) {
		this.orderFaileNum = orderFaileNum;
	}

	public java.lang.Float getOrderDayMoneyNum() {
		return orderDayMoneyNum;
	}

	public void setOrderDayMoneyNum(java.lang.Float orderDayMoneyNum) {
		this.orderDayMoneyNum = orderDayMoneyNum;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

	public int getOrderFailApiNum() {
		return orderFailApiNum;
	}

	public void setOrderFailApiNum(int orderFailApiNum) {
		this.orderFailApiNum = orderFailApiNum;
	}

	public int getOrderFailC2cNum() {
		return orderFailC2cNum;
	}

	public void setOrderFailC2cNum(int orderFailC2cNum) {
		this.orderFailC2cNum = orderFailC2cNum;
	}

	public int getOrderSucApiNum() {
		return orderSucApiNum;
	}

	public void setOrderSucApiNum(int orderSucApiNum) {
		this.orderSucApiNum = orderSucApiNum;
	}

	public int getOrderSucC2cNum() {
		return orderSucC2cNum;
	}

	public void setOrderSucC2cNum(int orderSucC2cNum) {
		this.orderSucC2cNum = orderSucC2cNum;
	}

	public java.lang.Float getOrderSucApiMoney() {
		return orderSucApiMoney;
	}

	public void setOrderSucApiMoney(java.lang.Float orderSucApiMoney) {
		this.orderSucApiMoney = orderSucApiMoney;
	}

	public java.lang.Float getOrderSucC2cMoney() {
		return orderSucC2cMoney;
	}

	public void setOrderSucC2cMoney(java.lang.Float orderSucC2cMoney) {
		this.orderSucC2cMoney = orderSucC2cMoney;
	}

}