package cn.juhaowan.order.vo;

import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.GenerationType;

/**
 * 充值卡日志表实体
 * 
 */
@Table(name = "rechargeable_card")
public class RechargeableCardLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "card_num", type = DbType.Varchar)
	private java.lang.String cardNum;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;
	
	@Column(name = "oprator", type = DbType.Int)
	private int oprator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCardNum() {
		return cardNum;
	}

	public void setCardNum(java.lang.String cardNum) {
		this.cardNum = cardNum;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public int getOprator() {
		return oprator;
	}

	public void setOprator(int oprator) {
		this.oprator = oprator;
	}
	
	

}
