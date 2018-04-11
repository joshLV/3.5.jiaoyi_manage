package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

@Table(name="game_product_property")
public class GameProductProperty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "game_id",type=DbType.Int)
	private int gameId;
	
	@Column(name = "game_product_type_id",type=DbType.Int)
	private int gameProductTypeId;
	
	@Column(name = "property_type",type=DbType.Int)
	private int propertyType;
	
	@Column(name = "key",type=DbType.Varchar)
	private java.lang.String key;
	
	@Column(name = "key_type",type=DbType.Int)
	private int keyType;
	
	@Column(name = "value",type=DbType.Varchar)
	private java.lang.String value;
	
	@Column(name = "value_type_limit",type=DbType.Int)
	private int valueTypeLimit;
	
	@Column(name = "value_required",type=DbType.Int)
	private int valueRequired;
	
	@Column(name = "value_encode",type=DbType.Int)
	private int valueEncode;
	
	@Column(name = "weight",type=DbType.Int)
	private int weight;
	
	@Column(name = "key_desc",type=DbType.Varchar)
	private java.lang.String key_desc;
	
	 
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

	public int getGameProductTypeId() {
		return gameProductTypeId;
	}

	public void setGameProductTypeId(int gameProductTypeId) {
		this.gameProductTypeId = gameProductTypeId;
	}

	public int getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(int propertyType) {
		this.propertyType = propertyType;
	}

	public java.lang.String getKey() {
		return key;
	}

	public void setKey(java.lang.String key) {
		this.key = key;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public int getValueTypeLimit() {
		return valueTypeLimit;
	}

	public void setValueTypeLimit(int valueTypeLimit) {
		this.valueTypeLimit = valueTypeLimit;
	}

	public int getValueRequired() {
		return valueRequired;
	}

	public void setValueRequired(int valueRequired) {
		this.valueRequired = valueRequired;
	}

	public int getValueEncode() {
		return valueEncode;
	}

	public void setValueEncode(int valueEncode) {
		this.valueEncode = valueEncode;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public java.lang.String getKey_desc() {
		return key_desc;
	}

	public void setKey_desc(java.lang.String key_desc) {
		this.key_desc = key_desc;
	}
	
	

}