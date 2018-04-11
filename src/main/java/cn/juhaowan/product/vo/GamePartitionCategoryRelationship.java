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
@Table(name = "game_partition_category_relationship")
public class GamePartitionCategoryRelationship implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "category_id", type = DbType.Int)
	private int categoryId;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "game_partition_id", type = DbType.Int)
	private int gamePartitionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getGamePartitionId() {
		return gamePartitionId;
	}

	public void setGamePartitionId(int gamePartitionId) {
		this.gamePartitionId = gamePartitionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}