package cn.juhaowan.order.vo;


import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name="ofcard_order")
public class OfCardOrder {
	//private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name="order_number",type=DbType.Varchar)
	private java.lang.String orderNum;
	
	@Column(name="product_type",type=DbType.TinyInt)
	private int productType;
	
	@Column(name="product_value",type=DbType.Int)
	private int productValue;
	
	@Column(name="product_isp",type=DbType.TinyInt)
	private int productIsp;
	
	@Column(name="product_price",type=DbType.Double)
	private double productPrice;
	
	@Column(name="uid",type=DbType.Int)
	private int uid;
	
	@Column(name="mobile_number",type=DbType.Varchar)
	private java.lang.String mobileNum;
	
	@Column(name="cardname",type=DbType.Varchar)
	private java.lang.String cardName;
	
	@Column(name="cardid",type=DbType.Varchar)
	private java.lang.String cardId;
	
	@Column(name="cardno",type=DbType.Varchar)
	private java.lang.String cardNo;
	
	@Column(name="cardpws",type=DbType.Varchar)
	private java.lang.String cardPws;
	
	@Column(name="card_expired_time",type=DbType.DateTime)
	private java.util.Date cardExpiredTime;
	
	@Column(name="inprice",type=DbType.Double)
	private double inprice;
	
	@Column(name="cp_order",type=DbType.Varchar)
	private java.lang.String cpOrder;
	
	@Column(name="order_status",type=DbType.Int)
	private int orderStatus;
	
	@Column(name="c_time",type=DbType.DateTime)
	private java.util.Date cTime;
	
	@Column(name="u_time",type=DbType.DateTime)
	private java.util.Date uTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(java.lang.String orderNum) {
		this.orderNum = orderNum;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	public int getProductIsp() {
		return productIsp;
	}

	public void setProductIsp(int productIsp) {
		this.productIsp = productIsp;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public java.lang.String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(java.lang.String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public java.lang.String getCardName() {
		return cardName;
	}

	public void setCardName(java.lang.String cardName) {
		this.cardName = cardName;
	}

	public java.lang.String getCardId() {
		return cardId;
	}

	public void setCardId(java.lang.String cardId) {
		this.cardId = cardId;
	}

	public java.lang.String getCardNo() {
		return cardNo;
	}

	public void setCardNo(java.lang.String cardNo) {
		this.cardNo = cardNo;
	}

	public java.lang.String getCardPws() {
		return cardPws;
	}

	public void setCardPws(java.lang.String cardPws) {
		this.cardPws = cardPws;
	}

	public java.util.Date getCardExpiredTime() {
		return cardExpiredTime;
	}

	public void setCardExpiredTime(java.util.Date cardExpiredTime) {
		this.cardExpiredTime = cardExpiredTime;
	}

	public double getInprice() {
		return inprice;
	}

	public void setInprice(double inprice) {
		this.inprice = inprice;
	}

	public java.lang.String getCpOrder() {
		return cpOrder;
	}

	public void setCpOrder(java.lang.String cpOrder) {
		this.cpOrder = cpOrder;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public java.util.Date getcTime() {
		return cTime;
	}

	public void setcTime(java.util.Date cTime) {
		this.cTime = cTime;
	}

	public java.util.Date getuTime() {
		return uTime;
	}

	public void setuTime(java.util.Date uTime) {
		this.uTime = uTime;
	}

	
	
}
