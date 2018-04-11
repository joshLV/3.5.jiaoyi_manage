package cn.juhaowan.count.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;




/**
 * 商品统计
 * 
 * @author Administrator
 * 
 */
@Table(name = "count_day_product")
public class CountDayProduct implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "on_member_num", type = DbType.Int)
	private int onMemberNum;

	@Column(name = "on_product_num", type = DbType.Int)
	private int onProductNum;

	@Column(name = "off_product_num", type = DbType.Int)
	private int offProductNum;

	@Column(name = "store_total", type = DbType.Int)
	private int storeTotal;

	@Column(name = "count_day", type = DbType.DateTime)
	private java.util.Date countDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOnMemberNum() {
		return onMemberNum;
	}

	public void setOnMemberNum(int onMemberNum) {
		this.onMemberNum = onMemberNum;
	}

	public int getOnProductNum() {
		return onProductNum;
	}

	public void setOnProductNum(int onProductNum) {
		this.onProductNum = onProductNum;
	}

	public int getOffProductNum() {
		return offProductNum;
	}

	public void setOffProductNum(int offProductNum) {
		this.offProductNum = offProductNum;
	}

	public int getStoreTotal() {
		return storeTotal;
	}

	public void setStoreTotal(int storeTotal) {
		this.storeTotal = storeTotal;
	}

	public java.util.Date getCountDay() {
		return countDay;
	}

	public void setCountDay(java.util.Date countDay) {
		this.countDay = countDay;
	}

}