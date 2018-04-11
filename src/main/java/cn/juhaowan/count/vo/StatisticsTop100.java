package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;

/**
 * 消费|销售Top100
 * 
 * @author APXer
 * 
 */
public class StatisticsTop100 implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "amount", type = DbType.Double)
	private double amount;

	@Column(name = "items", type = DbType.Int)
	private int items;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

}