package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 *  @author cxb
 * **/
@Table(name = "member_cash_setting")
public class MemberCashSetting implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "day_limit", type = DbType.Int)
	private int dayLimit;

	@Column(name = "item_limit", type = DbType.Int)
	private int itemLimit;

	@Column(name = "last_time", type = DbType.DateTime)
	private java.util.Date lastTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}

	public int getItemLimit() {
		return itemLimit;
	}

	public void setItemLimit(int itemLimit) {
		this.itemLimit = itemLimit;
	}

	public java.util.Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
	}

}
