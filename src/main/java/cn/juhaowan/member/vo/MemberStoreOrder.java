package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;


/**
 * 会员店铺开通临时表
 * @author Administrator
 *
 */
@Table(name = "member_store_order")
public class MemberStoreOrder implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "pay_type", type = DbType.Int)
	private int paytype;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "amount", type = DbType.Float)
	private java.lang.Float amount;

	@Column(name = "opening_time", type = DbType.Int)
	private int openingtime;

	@Column(name = "payCost_time", type = DbType.DateTime)
	private java.util.Date paycosttime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public java.lang.Float getAmount() {
		return amount;
	}

	public void setAmount(java.lang.Float amount) {
		this.amount = amount;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public int getOpeningtime() {
		return openingtime;
	}

	public void setOpeningtime(int openingtime) {
		this.openingtime = openingtime;
	}

	public java.util.Date getPaycosttime() {
		return paycosttime;
	}

	public void setPaycosttime(java.util.Date paycosttime) {
		this.paycosttime = paycosttime;
	}



}
