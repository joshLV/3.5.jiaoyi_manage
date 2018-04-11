package cn.juhaowan.product.vo;


import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 
 * @author cxb
 * @date 2014-7-2 上午11:41:11
 *
 */

@Table(name = "game_version")
public class GameVersion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	
	@Column(name = "name", type = DbType.Varchar)
	private String name;
	
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;
	
	@Column(name = "status", type = DbType.Int)
	private int status;
	
	@Column(name = "weight", type = DbType.Int)
	private int weight;

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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}