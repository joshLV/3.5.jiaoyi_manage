package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.*;

/**
 * 商品类型 自定义提示信息
 * @author Administrator
 *
 */
@Table(name="game_product_type_reminder")
public class GameProductTypeReminder implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "game_id",type=DbType.Int)
	private int gameId;
	
	@Column(name = "game_product_type_id",type=DbType.Int)
	private int gameProductTypeId;
	
	@Column(name = "type",type=DbType.Int)
	private int type;
	
	@Column(name = "message",type=DbType.Varchar)
	private java.lang.String message;
	
	 
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}

}