package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

@Table(name = "game_product_type")
public class GameProductType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "product_type_id", type = DbType.Int)
	private int productTypeId;

	@Column(name = "unit", type = DbType.Varchar)
	private java.lang.String unit;

	private String productTypeName;

	@Column(name = "buy_info_symbol", type = DbType.Int)
	private int buyInfoSymbol;

	@Column(name = "type_name", type = DbType.Varchar)
	private java.lang.String typeName;

	@Column(name = "id_display", type = DbType.Varchar)
	private java.lang.String idDisplay;

	@Column(name = "role_display", type = DbType.Varchar)
	private java.lang.String roleDisplay;

	@Column(name = "auto_assign", type = DbType.Int)
	private int autoAssign;

	@Column(name = "min_price", type = DbType.Double)
	private double minPrice;

	@Column(name = "max_price", type = DbType.Double)
	private double maxPrice;

	@Column(name = "is_audit", type = DbType.Int)
	private int isAudit;

	@Column(name = "game_channel_id", type = DbType.Int)
	private int gameChannelId;

	@Column(name = "is_publish", type = DbType.Int)
	private int isPublish;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	@Column(name = "dc_white_list_check_flag", type = DbType.Int)
	private int dcWhiteListCheckFlag;

	@Column(name = "sc_white_list_add_flag", type = DbType.Int)
	private int scWhiteListAddFlag;

	@Column(name = "account_display", type = DbType.Varchar)
	private java.lang.String accountDisplay;

	@Column(name = "password_display", type = DbType.Varchar)
	private java.lang.String passwordDisplay;

	@Column(name = "safekey_display", type = DbType.Varchar)
	private java.lang.String safekeyDisplay;

	@Column(name = "area_display", type = DbType.Varchar)
	private java.lang.String areaDisplay;

	@Column(name = "image_url", type = DbType.Varchar)
	private java.lang.String imageUrl;
	
	@Column(name = "quota", type = DbType.Int)
	private int quota;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public java.lang.String getUnit() {
		return unit;
	}

	public void setUnit(java.lang.String unit) {
		this.unit = unit;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public int getBuyInfoSymbol() {
		return buyInfoSymbol;
	}

	public void setBuyInfoSymbol(int buyInfoSymbol) {
		this.buyInfoSymbol = buyInfoSymbol;
	}

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.String getIdDisplay() {
		return idDisplay;
	}

	public void setIdDisplay(java.lang.String idDisplay) {
		this.idDisplay = idDisplay;
	}

	public java.lang.String getRoleDisplay() {
		return roleDisplay;
	}

	public void setRoleDisplay(java.lang.String roleDisplay) {
		this.roleDisplay = roleDisplay;
	}

	public int getAutoAssign() {
		return autoAssign;
	}

	public void setAutoAssign(int autoAssign) {
		this.autoAssign = autoAssign;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}

	public int getGameChannelId() {
		return gameChannelId;
	}

	public void setGameChannelId(int gameChannelId) {
		this.gameChannelId = gameChannelId;
	}

	public int getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(int isPublish) {
		this.isPublish = isPublish;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDcWhiteListCheckFlag() {
		return dcWhiteListCheckFlag;
	}

	public void setDcWhiteListCheckFlag(int dcWhiteListCheckFlag) {
		this.dcWhiteListCheckFlag = dcWhiteListCheckFlag;
	}

	public int getScWhiteListAddFlag() {
		return scWhiteListAddFlag;
	}

	public void setScWhiteListAddFlag(int scWhiteListAddFlag) {
		this.scWhiteListAddFlag = scWhiteListAddFlag;
	}

	public java.lang.String getAccountDisplay() {
		return accountDisplay;
	}

	public void setAccountDisplay(java.lang.String accountDisplay) {
		this.accountDisplay = accountDisplay;
	}

	public java.lang.String getPasswordDisplay() {
		return passwordDisplay;
	}

	public void setPasswordDisplay(java.lang.String passwordDisplay) {
		this.passwordDisplay = passwordDisplay;
	}

	public java.lang.String getSafekeyDisplay() {
		return safekeyDisplay;
	}

	public void setSafekeyDisplay(java.lang.String safekeyDisplay) {
		this.safekeyDisplay = safekeyDisplay;
	}

	public java.lang.String getAreaDisplay() {
		return areaDisplay;
	}

	public void setAreaDisplay(java.lang.String areaDisplay) {
		this.areaDisplay = areaDisplay;
	}

	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}
	
	

}
