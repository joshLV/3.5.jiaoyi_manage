package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.GenerationType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 
 * @author Administrator
 * 
 */
@Table(name = "product_onsale")
public class ProductOnsale implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "product_id", type = DbType.Varchar)
	private java.lang.String productId;

	@Column(name = "product_name", type = DbType.Varchar)
	private java.lang.String productName;

	@Column(name = "product_subtitle", type = DbType.Varchar)
	private java.lang.String productSubtitle;

	@Column(name = "product_type", type = DbType.Int)
	private int productType;

	@Column(name = "product_single_number", type = DbType.Long)
	private long productSingleNumber;

	@Column(name = "product_soldout_number", type = DbType.Int)
	private int productSoldoutNumber;

	@Column(name = "product_single_price", type = DbType.Double)
	private double productSinglePrice;

	@Column(name = "product_stock", type = DbType.Int)
	private int productStock;

	@Column(name = "product_info", type = DbType.Varchar)
	private java.lang.String productInfo;

	@Column(name = "product_onsale_model", type = DbType.Int)
	private int productOnsaleModel;

	@Column(name = "product_status", type = DbType.Int)
	private int productStatus;

	@Column(name = "product_remark", type = DbType.Varchar)
	private java.lang.String productRemark;

	@Column(name = "product_weight", type = DbType.Int)
	private int productWeight;

	@Column(name = "no_product_flag", type = DbType.Int)
	private int noProductFlag;

	@Column(name = "product_validity", type = DbType.DateTime)
	private java.util.Date productValidity;

	@Column(name = "user_id", type = DbType.Int)
	private int userId;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "game_name", type = DbType.Varchar)
	private java.lang.String gameName;

	@Column(name = "game_area_id", type = DbType.Int)
	private int gameAreaId;

	@Column(name = "partition_name", type = DbType.Varchar)
	private java.lang.String gameAreaName;

	@Column(name = "game_server_id", type = DbType.Int)
	private int gameServerId;

	@Column(name = "game_uid", type = DbType.Varchar)
	private java.lang.String gameUid;

	@Column(name = "game_role_id", type = DbType.Varchar)
	private java.lang.String gameRoleId;

	@Column(name = "game_role_name", type = DbType.Varchar)
	private java.lang.String gameRoleName;

	@Column(name = "game_user_lever", type = DbType.Int)
	private int gameUserLever;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;

	@Column(name = "on_sale_time", type = DbType.DateTime)
	private java.util.Date onSaleTime;

	@Column(name = "off_shelves_time", type = DbType.DateTime)
	private java.util.Date offShelvesTime;

	@Column(name = "final_transaction_time", type = DbType.DateTime)
	private java.util.Date finalTransactionTime;

	@Column(name = "verify_time", type = DbType.DateTime)
	private java.util.Date verifyTime;

	@Column(name = "sell_pid", type = DbType.Varchar)
	private java.lang.String sellPid;

	@Column(name = "unit", type = DbType.Varchar)
	private java.lang.String unit;

	@Column(name = "soldout_time", type = DbType.DateTime)
	private java.util.Date soldoutTime;

	@Column(name = "user_nickName", type = DbType.Varchar)
	private java.lang.String userNickname;

	@Column(name = "channel_id", type = DbType.Int)
	private int channelId;

	@Column(name = "quick_deliver_flag", type = DbType.Int)
	private int quickDeliverFlag;

	@Column(name = "product_original_price", type = DbType.Double)
	private double productOriginalPrice;

	@Column(name = "son_channel_id", type = DbType.Varchar)
	private java.lang.String sonChannelId;

	@Column(name = "is_show", type = DbType.Int)
	private int isShow;

	// 商品所属游戏图标
	private java.lang.String gameImg;

	public java.lang.String getGameImg() {
		return gameImg;
	}

	public void setGameImg(java.lang.String gameImg) {
		this.gameImg = gameImg;
	}

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public long getProductSingleNumber() {
		return productSingleNumber;
	}

	public void setProductSingleNumber(long productSingleNumber) {
		this.productSingleNumber = productSingleNumber;
	}

	public int getProductSoldoutNumber() {
		return productSoldoutNumber;
	}

	public void setProductSoldoutNumber(int productSoldoutNumber) {
		this.productSoldoutNumber = productSoldoutNumber;
	}

	public double getProductSinglePrice() {
		return productSinglePrice;
	}

	public void setProductSinglePrice(double productSinglePrice) {
		this.productSinglePrice = productSinglePrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public java.lang.String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(java.lang.String productInfo) {
		this.productInfo = productInfo;
	}

	public int getProductOnsaleModel() {
		return productOnsaleModel;
	}

	public void setProductOnsaleModel(int productOnsaleModel) {
		this.productOnsaleModel = productOnsaleModel;
	}

	public int getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public java.lang.String getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(java.lang.String productRemark) {
		this.productRemark = productRemark;
	}

	public int getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(int productWeight) {
		this.productWeight = productWeight;
	}

	public int getNoProductFlag() {
		return noProductFlag;
	}

	public void setNoProductFlag(int noProductFlag) {
		this.noProductFlag = noProductFlag;
	}

	public java.util.Date getProductValidity() {
		return productValidity;
	}

	public void setProductValidity(java.util.Date productValidity) {
		this.productValidity = productValidity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public java.lang.String getGameName() {
		return gameName;
	}

	public void setGameId(java.lang.String gameName) {
		this.gameName = gameName;
	}

	public int getGameAreaId() {
		return gameAreaId;
	}

	public void setGameAreaId(int gameAreaId) {
		this.gameAreaId = gameAreaId;
	}

	public java.lang.String getGameAreaName() {
		return gameAreaName;
	}

	public void setGameAreaId(java.lang.String gameAreaName) {
		this.gameAreaName = gameAreaName;
	}

	public int getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(int gameServerId) {
		this.gameServerId = gameServerId;
	}

	public java.lang.String getGameUid() {
		return gameUid;
	}

	public void setGameUid(java.lang.String gameUid) {
		this.gameUid = gameUid;
	}

	public java.lang.String getGameRoleId() {
		return gameRoleId;
	}

	public void setGameRoleId(java.lang.String gameRoleId) {
		this.gameRoleId = gameRoleId;
	}

	public java.lang.String getGameRoleName() {
		return gameRoleName;
	}

	public void setGameRoleName(java.lang.String gameRoleName) {
		this.gameRoleName = gameRoleName;
	}

	public int getGameUserLever() {
		return gameUserLever;
	}

	public void setGameUserLever(int gameUserLever) {
		this.gameUserLever = gameUserLever;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public java.util.Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(java.util.Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public java.util.Date getOffShelvesTime() {
		return offShelvesTime;
	}

	public void setOffShelvesTime(java.util.Date offShelvesTime) {
		this.offShelvesTime = offShelvesTime;
	}

	public java.util.Date getFinalTransactionTime() {
		return finalTransactionTime;
	}

	public void setFinalTransactionTime(java.util.Date finalTransactionTime) {
		this.finalTransactionTime = finalTransactionTime;
	}

	public java.util.Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(java.util.Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public java.lang.String getProductSubtitle() {
		return productSubtitle;
	}

	public void setProductSubtitle(java.lang.String productSubtitle) {
		this.productSubtitle = productSubtitle;
	}

	public java.lang.String getSellPid() {
		return sellPid;
	}

	public void setSellPid(java.lang.String sellPid) {
		this.sellPid = sellPid;
	}

	public java.lang.String getUnit() {
		return unit;
	}

	public void setUnit(java.lang.String unit) {
		this.unit = unit;
	}

	public java.util.Date getSoldoutTime() {
		return soldoutTime;
	}

	public void setSoldoutTime(java.util.Date soldoutTime) {
		this.soldoutTime = soldoutTime;
	}

	public java.lang.String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(java.lang.String userNickname) {
		this.userNickname = userNickname;
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

	public void setGameName(java.lang.String gameName) {
		this.gameName = gameName;
	}

	public void setGameAreaName(java.lang.String gameAreaName) {
		this.gameAreaName = gameAreaName;
	}

	public double getProductOriginalPrice() {
		return productOriginalPrice;
	}

	public void setProductOriginalPrice(double productOriginalPrice) {
		this.productOriginalPrice = productOriginalPrice;
	}

	public java.lang.String getSonChannelId() {
		return sonChannelId;
	}

	public void setSonChannelId(java.lang.String sonChannelId) {
		this.sonChannelId = sonChannelId;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	

}