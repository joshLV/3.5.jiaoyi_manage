package cn.juhaowan.order.vo;

import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.GenerationType;

/**
 * 支付单实体
 * 
 * @author houjt
 * 
 */
@Table(name = "order_pay_info")
public class OrderPayInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "pay_id", type = DbType.Varchar)
	private java.lang.String payId;

	@Column(name = "pay_type", type = DbType.Int)
	private int payType;

	@Column(name = "pay_way", type = DbType.Int)
	private int payWay;

	@Column(name = "money_type", type = DbType.Int)
	private int moneyType;

	@Column(name = "order_pay_amount", type = DbType.Double)
	private double orderPayAmount;

	@Column(name = "order_pay_time", type = DbType.DateTime)
	private java.util.Date orderPayTime;

	@Column(name = "order_is_pay", type = DbType.Int)
	private int orderIsPay;

	@Column(name = "pay_request_url", type = DbType.Varchar)
	private java.lang.String payRequestUrl;

	@Column(name = "pay_platform_id", type = DbType.Int)
	private int payPlatformId;

	@Column(name = "pay_result_id", type = DbType.Varchar)
	private java.lang.String payResultId;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "pay_user_uid", type = DbType.Int)
	private int payUserUid;

	@Column(name = "pay_remark", type = DbType.Varchar)
	private java.lang.String payRemark;
	
	@Column(name = "pay_qudao", type = DbType.Varchar)
	private java.lang.String payQudao;
	
	@Column(name = "pay_client_type", type = DbType.Varchar)
	private java.lang.String payClientType;
	
	public java.lang.String getPayId() {
		return payId;
	}

	public void setPayId(java.lang.String payId) {
		this.payId = payId;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	public int getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}

	public double getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(double orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public java.util.Date getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(java.util.Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public int getOrderIsPay() {
		return orderIsPay;
	}

	public void setOrderIsPay(int orderIsPay) {
		this.orderIsPay = orderIsPay;
	}

	public java.lang.String getPayRequestUrl() {
		return payRequestUrl;
	}

	public void setPayRequestUrl(java.lang.String payRequestUrl) {
		this.payRequestUrl = payRequestUrl;
	}

	public int getPayPlatformId() {
		return payPlatformId;
	}

	public void setPayPlatformId(int payPlatformId) {
		this.payPlatformId = payPlatformId;
	}

	public java.lang.String getPayResultId() {
		return payResultId;
	}

	public void setPayResultId(java.lang.String payResultId) {
		this.payResultId = payResultId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getPayUserUid() {
		return payUserUid;
	}

	public void setPayUserUid(int payUserUid) {
		this.payUserUid = payUserUid;
	}

	public java.lang.String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(java.lang.String payRemark) {
		this.payRemark = payRemark;
	}

	public java.lang.String getPayQudao() {
		return payQudao;
	}

	public void setPayQudao(java.lang.String payQudao) {
		this.payQudao = payQudao;
	}

	public java.lang.String getPayClientType() {
		return payClientType;
	}

	public void setPayClientType(java.lang.String payClientType) {
		this.payClientType = payClientType;
	}
	
}
