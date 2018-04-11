package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;


public class GameProductTypeShow {
	

	//游戏商品类型表id
	@Column(name = "gptId",type=DbType.Int)
	private int gptId;
	
	//商品类型表id
	@Column(name = "typeId",type=DbType.Int)
	private int typeId;
	
	//游戏id
	@Column(name = "gameId",type=DbType.Int)
	private int gameId;
	
    //商品类型编码
	@Column(name = "productType",type=DbType.Varchar)
	private String productType;
	
	//商品类型名称
	@Column(name = "name",type=DbType.Varchar)
	private String name;
	
	//商品类型状态
	@Column(name = "status",type=DbType.Int)
	private int status;
	
	//商品类型单位
	@Column(name = "unit",type=DbType.Varchar)
	private String unit;
	
	
	
	//商品类型买卖信息标志
	@Column(name = "buyInfoSymbol",type=DbType.Int)
	private int buyInfoSymbol;
	
	//商品类型名称
	@Column(name = "type_name",type=DbType.Varchar)
	private String typeName;
	
	//ID前台展现名称
	@Column(name = "id_display",type=DbType.Varchar)
	private String idDisplay;
	
	//角色前台展现名称
	@Column(name = "role_display",type=DbType.Varchar)
	private String roleDisplay;
	
	

	public int getGptId() {
		return gptId;
	}
	public void setGptId(int gptId) {
		this.gptId = gptId;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getBuyInfoSymbol() {
		return buyInfoSymbol;
	}
	public void setBuyInfoSymbol(int buyInfoSymbol) {
		this.buyInfoSymbol = buyInfoSymbol;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRoleDisplay() {
		return roleDisplay;
	}
	public void setRoleDisplay(String roleDisplay) {
		this.roleDisplay = roleDisplay;
	}
	public String getIdDisplay() {
		return idDisplay;
	}
	public void setIdDisplay(String idDisplay) {
		this.idDisplay = idDisplay;
	}

	
	

}