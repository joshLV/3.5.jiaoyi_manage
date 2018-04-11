package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 * 
 * @author Administrator
 *
 */
@Table(name = "game_partition_category")
public class GamePartitionCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "categoryName", type = DbType.Varchar)
	private java.lang.String categoryName;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "game_version_id", type = DbType.Int)
	private int gameVersionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public java.lang.Integer getGameId() {
		return gameId;
	}

	public void setGameId(java.lang.Integer gameId) {
		this.gameId = gameId;
	}

	public java.lang.Integer getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}
	
	

	public int getGameVersionId() {
		return gameVersionId;
	}

	public void setGameVersionId(int gameVersionId) {
		this.gameVersionId = gameVersionId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}