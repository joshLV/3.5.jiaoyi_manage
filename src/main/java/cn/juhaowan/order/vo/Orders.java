package cn.juhaowan.order.vo;

import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.GenerationType;
/**
 * 订单实体
 * @author houjt
 *
 */
@Table(name = "orders")
public class Orders implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "order_id", type = DbType.Varchar)
	private java.lang.String orderId;

	@Column(name = "order_time", type = DbType.DateTime)
	private java.util.Date orderTime;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;

	@Column(name = "order_assign", type = DbType.Int)
	private int orderAssign;

	@Column(name = "order_status", type = DbType.Int)
	private int orderStatus;

	@Column(name = "order_amount", type = DbType.Double)
	private double orderAmount;

	@Column(name = "order_password", type = DbType.Varchar)
	private java.lang.String orderPassword;

	@Column(name = "order_ispay", type = DbType.Int)
	private int orderIspay;

	@Column(name = "order_model", type = DbType.Int)
	private int orderModel;

	@Column(name = "order_buy_uid", type = DbType.Int)
	private int orderBuyUid;

	@Column(name = "order_buy_qq", type = DbType.Varchar)
	private java.lang.String orderBuyQq;

	@Column(name = "order_buy_game_uid", type = DbType.Varchar)
	private java.lang.String orderBuyGameUid;

	@Column(name = "order_buy_game_role_id", type = DbType.Varchar)
	private java.lang.String orderBuyGameRoleId;

	@Column(name = "order_buy_game_role_name", type = DbType.Varchar)
	private java.lang.String orderBuyGameRoleName;

	@Column(name = "goods_name", type = DbType.Varchar)
	private java.lang.String goodsName;

	@Column(name = "goods_id", type = DbType.Varchar)
	private java.lang.String goodsId;

	@Column(name = "goods_type", type = DbType.Int)
	private int goodsType;

	@Column(name = "goods_price", type = DbType.Double)
	private double goodsPrice;

	@Column(name = "goods_count", type = DbType.Int)
	private int goodsCount;

	@Column(name = "goods_remark", type = DbType.Varchar)
	private java.lang.String goodsRemark;

	@Column(name = "order_sell_uid", type = DbType.Int)
	private int orderSellUid;

	@Column(name = "order_sell_game_uid", type = DbType.Varchar)
	private java.lang.String orderSellGameUid;

	@Column(name = "order_sell_game_role_id", type = DbType.Varchar)
	private java.lang.String orderSellGameRoleId;

	@Column(name = "order_sell_game_role_name", type = DbType.Varchar)
	private java.lang.String orderSellGameRoleName;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "game_area_id", type = DbType.Int)
	private int gameAreaId;

	@Column(name = "game_server_id", type = DbType.Int)
	private int gameServerId;

	@Column(name = "order_comefrom", type = DbType.Varchar)
	private java.lang.String orderComefrom;

	@Column(name = "send_goods_time", type = DbType.DateTime)
	private java.util.Date sendGoodsTime;

	@Column(name = "get_goods_time", type = DbType.DateTime)
	private java.util.Date getGoodsTime;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "goods_single_number", type = DbType.Long)
	private long goodsSingleNumber;

	@Column(name = "game_name", type = DbType.Varchar)
	private java.lang.String gameName;

	@Column(name = "game_area_name", type = DbType.Varchar)
	private java.lang.String gameAreaName;

	@Column(name = "game_server_name", type = DbType.Varchar)
	private java.lang.String gameServerName;

	@Column(name = "order_buy_phonenum", type = DbType.Varchar)
	private java.lang.String orderBuyPhonenum;

	@Column(name = "goods_sell_pid", type = DbType.Varchar)
	private java.lang.String goodsSellPid;

	@Column(name = "order_free", type = DbType.Double)
	private double orderFree;

	@Column(name = "order_pay_success_time", type = DbType.DateTime)
	private java.util.Date orderPaySuccessTime;

	@Column(name = "order_buy_game_role_level",type = DbType.Int)
	private int orderBuyGameRoleLevel;
	
	@Column(name = "order_sell_user_type",type = DbType.Int)
	private int orderSellUserType;
	
	@Column(name = "order_sell_game_role_level",type = DbType.Int)
	private int orderSellGameRoleLevel;
	
	@Column(name = "order_pay_platform_id",type = DbType.Int)
	private int orderPayPlatformId;
	
	@Column(name = "order_pay_way",type = DbType.Int)
	private int orderPayWay;
	
	@Column(name = "order_pay_qudao",type = DbType.Varchar)
	private java.lang.String orderPayQudao;
	
	@Column(name = "channel_id",type = DbType.Int)
	private int channelId;
	
	@Column(name = "quick_deliver_flag",type = DbType.SmallInt)
	private int quickDeliverFlag;
	
	@Column(name = "order_fr",type = DbType.Varchar)
	private java.lang.String orderFr;
	
	@Column(name = "order_terrace",type = DbType.Varchar)
	private java.lang.String orderTerrace;
	
	@Column(name = "goods_putaway_date",type = DbType.DateTime)
	private java.util.Date goodsPutawayDate;
	
	@Column(name = "refund_date",type = DbType.DateTime)
	private java.util.Date refundDate;
	
	@Column(name = "refund_sum",type = DbType.Double)
	private double refundSum;
	
	@Column(name = "order_revoke_cause",type = DbType.Varchar)
	private String orderRevokeCause;
	
	@Column(name = "order_revoke_date",type = DbType.DateTime)
	private java.util.Date orderRevokeDate;
	
	@Column(name = "order_entrance",type = DbType.Varchar)
	private String orderEntrance;
	
	@Column(name = "parent_order_id",type = DbType.Varchar)
	private String parentOrderId;
	
	@Column(name = "ty_amount",type = DbType.Int)
	private int tyAmount;
	
	@Column(name = "ty_amount_now", type = DbType.Int)
	private int tyAmountNow;
	
	@Column(name = "ty_dikou_amount",type = DbType.Int)
	private int tyDikouAmount;

    @Column(name = "ty_material_submit", type = DbType.Int)
    private int tyMaterialSubmit;
	
	@Column(name = "product_original_price", type = DbType.Double) 
	private double productOriginalPrice;
	
	@Column(name = "son_channel_id",type = DbType.Varchar)
	private String sonChannelId;
	
	@Column(name = "game_version", type = DbType.Varchar) 
	private String gameVersion;
	
	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public java.util.Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getOrderAssign() {
		return orderAssign;
	}

	public void setOrderAssign(int orderAssign) {
		this.orderAssign = orderAssign;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public java.lang.String getOrderPassword() {
		return orderPassword;
	}

	public void setOrderPassword(java.lang.String orderPassword) {
		this.orderPassword = orderPassword;
	}

	public int getOrderIspay() {
		return orderIspay;
	}

	public void setOrderIspay(int orderIspay) {
		this.orderIspay = orderIspay;
	}

	public int getOrderModel() {
		return orderModel;
	}

	public void setOrderModel(int orderModel) {
		this.orderModel = orderModel;
	}

	public int getOrderBuyUid() {
		return orderBuyUid;
	}

	public void setOrderBuyUid(int orderBuyUid) {
		this.orderBuyUid = orderBuyUid;
	}

	public java.lang.String getOrderBuyQq() {
		return orderBuyQq;
	}

	public void setOrderBuyQq(java.lang.String orderBuyQq) {
		this.orderBuyQq = orderBuyQq;
	}

	public java.lang.String getOrderBuyGameUid() {
		return orderBuyGameUid;
	}

	public void setOrderBuyGameUid(java.lang.String orderBuyGameUid) {
		this.orderBuyGameUid = orderBuyGameUid;
	}

	public java.lang.String getOrderBuyGameRoleId() {
		return orderBuyGameRoleId;
	}

	public void setOrderBuyGameRoleId(java.lang.String orderBuyGameRoleId) {
		this.orderBuyGameRoleId = orderBuyGameRoleId;
	}

	public java.lang.String getOrderBuyGameRoleName() {
		return orderBuyGameRoleName;
	}

	public void setOrderBuyGameRoleName(java.lang.String orderBuyGameRoleName) {
		this.orderBuyGameRoleName = orderBuyGameRoleName;
	}

	public java.lang.String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}

	public java.lang.String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public java.lang.String getGoodsRemark() {
		return goodsRemark;
	}

	public void setGoodsRemark(java.lang.String goodsRemark) {
		this.goodsRemark = goodsRemark;
	}

	public int getOrderSellUid() {
		return orderSellUid;
	}

	public void setOrderSellUid(int orderSellUid) {
		this.orderSellUid = orderSellUid;
	}

	public java.lang.String getOrderSellGameUid() {
		return orderSellGameUid;
	}

	public void setOrderSellGameUid(java.lang.String orderSellGameUid) {
		this.orderSellGameUid = orderSellGameUid;
	}

	public java.lang.String getOrderSellGameRoleId() {
		return orderSellGameRoleId;
	}

	public void setOrderSellGameRoleId(java.lang.String orderSellGameRoleId) {
		this.orderSellGameRoleId = orderSellGameRoleId;
	}

	public java.lang.String getOrderSellGameRoleName() {
		return orderSellGameRoleName;
	}

	public void setOrderSellGameRoleName(java.lang.String orderSellGameRoleName) {
		this.orderSellGameRoleName = orderSellGameRoleName;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getGameAreaId() {
		return gameAreaId;
	}

	public void setGameAreaId(int gameAreaId) {
		this.gameAreaId = gameAreaId;
	}

	public int getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(int gameServerId) {
		this.gameServerId = gameServerId;
	}

	public java.lang.String getOrderComefrom() {
		return orderComefrom;
	}

	public void setOrderComefrom(java.lang.String orderComefrom) {
		this.orderComefrom = orderComefrom;
	}

	public java.util.Date getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(java.util.Date sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public java.util.Date getGetGoodsTime() {
		return getGoodsTime;
	}

	public void setGetGoodsTime(java.util.Date getGoodsTime) {
		this.getGoodsTime = getGoodsTime;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	

	public java.lang.String getGameName() {
		return gameName;
	}

	public void setGameName(java.lang.String gameName) {
		this.gameName = gameName;
	}

	public java.lang.String getGameAreaName() {
		return gameAreaName;
	}

	public void setGameAreaName(java.lang.String gameAreaName) {
		this.gameAreaName = gameAreaName;
	}

	public java.lang.String getGameServerName() {
		return gameServerName;
	}

	public void setGameServerName(java.lang.String gameServerName) {
		this.gameServerName = gameServerName;
	}

	public java.lang.String getOrderBuyPhonenum() {
		return orderBuyPhonenum;
	}

	public void setOrderBuyPhonenum(java.lang.String orderBuyPhonenum) {
		this.orderBuyPhonenum = orderBuyPhonenum;
	}

	public java.lang.String getGoodsSellPid() {
		return goodsSellPid;
	}

	public void setGoodsSellPid(java.lang.String goodsSellPid) {
		this.goodsSellPid = goodsSellPid;
	}

	public double getOrderFree() {
		return orderFree;
	}

	public void setOrderFree(double orderFree) {
		this.orderFree = orderFree;
	}

	public java.util.Date getOrderPaySuccessTime() {
		return orderPaySuccessTime;
	}

	public void setOrderPaySuccessTime(java.util.Date orderPaySuccessTime) {
		this.orderPaySuccessTime = orderPaySuccessTime;
	}
			
	public int getOrderBuyGameRoleLevel() {
		return orderBuyGameRoleLevel;
	}

	public void setOrderBuyGameRoleLevel(int orderBuyGameRoleLevel) {
		this.orderBuyGameRoleLevel = orderBuyGameRoleLevel;
	}

	public int getOrderSellGameRoleLevel() {
		return orderSellGameRoleLevel;
	}

	public void setOrderSellGameRoleLevel(int orderSellGameRoleLevel) {
		this.orderSellGameRoleLevel = orderSellGameRoleLevel;
	}

	
	public long getGoodsSingleNumber() {
		return goodsSingleNumber;
	}

	public void setGoodsSingleNumber(long goodsSingleNumber) {
		this.goodsSingleNumber = goodsSingleNumber;
	}

	public int getOrderSellUserType() {
		return orderSellUserType;
	}

	public void setOrderSellUserType(int orderSellUserType) {
		this.orderSellUserType = orderSellUserType;
	}

	public int getOrderPayPlatformId() {
		return orderPayPlatformId;
	}

	public void setOrderPayPlatformId(int orderPayPlatformId) {
		this.orderPayPlatformId = orderPayPlatformId;
	}

	public int getOrderPayWay() {
		return orderPayWay;
	}

	public void setOrderPayWay(int orderPayWay) {
		this.orderPayWay = orderPayWay;
	}

	public java.lang.String getOrderPayQudao() {
		return orderPayQudao;
	}

	public void setOrderPayQudao(java.lang.String orderPayQudao) {
		this.orderPayQudao = orderPayQudao;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getQuickDeliverFlag() {
		return quickDeliverFlag;
	}

	public void setQuickDeliverFlag(int quickDeliverFlag) {
		this.quickDeliverFlag = quickDeliverFlag;
	}

	public java.lang.String getOrderFr() {
		return orderFr;
	}

	public void setOrderFr(java.lang.String orderFr) {
		this.orderFr = orderFr;
	}

	public java.lang.String getOrderTerrace() {
		return orderTerrace;
	}

	public void setOrderTerrace(java.lang.String orderTerrace) {
		this.orderTerrace = orderTerrace;
	}

	public java.util.Date getGoodsPutawayDate() {
		return goodsPutawayDate;
	}

	public void setGoodsPutawayDate(java.util.Date goodsPutawayDate) {
		this.goodsPutawayDate = goodsPutawayDate;
	}


	public java.util.Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(java.util.Date refundDate) {
		this.refundDate = refundDate;
	}

	public double getRefundSum() {
		return refundSum;
	}

	public void setRefundSum(double refundSum) {
		this.refundSum = refundSum;
	}

	public String getOrderRevokeCause() {
		return orderRevokeCause;
	}

	public void setOrderRevokeCause(String orderRevokeCause) {
		this.orderRevokeCause = orderRevokeCause;
	}

	public java.util.Date getOrderRevokeDate() {
		return orderRevokeDate;
	}

	public void setOrderRevokeDate(java.util.Date orderRevokeDate) {
		this.orderRevokeDate = orderRevokeDate;
	}

	public String getOrderEntrance() {
		return orderEntrance;
	}

	public void setOrderEntrance(String orderEntrance) {
		this.orderEntrance = orderEntrance;
	}

	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public int getTyAmount() {
		return tyAmount;
	}

	public void setTyAmount(int tyAmount) {
		this.tyAmount = tyAmount;
	}

	public int getTyAmountNow() {
		return tyAmountNow;
	}

	public void setTyAmountNow(int tyAmountNow) {
		this.tyAmountNow = tyAmountNow;
	}

	public int getTyDikouAmount() {
		return tyDikouAmount;
	}

	public void setTyDikouAmount(int tyDikouAmount) {
		this.tyDikouAmount = tyDikouAmount;
	}

	public int getTyMaterialSubmit() {
		return tyMaterialSubmit;
	}

	public void setTyMaterialSubmit(int tyMaterialSubmit) {
		this.tyMaterialSubmit = tyMaterialSubmit;
	}

	public double getProductOriginalPrice() {
		return productOriginalPrice;
	}

	public void setProductOriginalPrice(double productOriginalPrice) {
		this.productOriginalPrice = productOriginalPrice;
	}

	public String getSonChannelId() {
		return sonChannelId;
	}

	public void setSonChannelId(String sonChannelId) {
		this.sonChannelId = sonChannelId;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

}
