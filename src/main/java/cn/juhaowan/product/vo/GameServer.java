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
@Table(name = "game_server")
public class GameServer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "game_server_id", type = DbType.Int)
	private int gameServerId;

	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Id
	@Column(name = "game_partition_id", type = DbType.Int)
	private int gamePartitionId;

	@Column(name = "game_server_name", type = DbType.Varchar)
	private java.lang.String gameServerName;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "status", type = DbType.Int)
	private int status;

	public int getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(int gameServerId) {
		this.gameServerId = gameServerId;
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

	public java.lang.String getGameServerName() {
		return gameServerName;
	}

	public void setGameServerName(java.lang.String gameServerName) {
		this.gameServerName = gameServerName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}