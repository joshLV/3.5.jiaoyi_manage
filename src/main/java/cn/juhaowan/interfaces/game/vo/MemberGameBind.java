package cn.juhaowan.interfaces.game.vo;


import cn.jugame.dal.annotation.*;

@Table(name="member_game_bind")
public class MemberGameBind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	
	@Column(name = "uid", type = DbType.Int)
	private int uid;
	
	@Column(name = "game_uid", type = DbType.Varchar)
	private String gameUid;
	
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;
	
	@Column(name = "nickname", type = DbType.Varchar)
	private String nickname;
	
	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;
	

	private String operation;
	

	private String gameName;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getGameUid() {
		return gameUid;
	}
	
	public void setGameUid(String gameUid) {
		this.gameUid = gameUid;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}


}