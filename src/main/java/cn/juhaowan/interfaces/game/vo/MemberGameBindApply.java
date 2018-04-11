package cn.juhaowan.interfaces.game.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;


/**
 * 绑定申请表 
 * @author lithium
 *
 */

@Table(name = "member_game_bind_apply")
public class MemberGameBindApply implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;
	
	@Column(name = "uid", type = DbType.Int)
	private int uid;
	
	@Column(name = "game_role_id", type = DbType.Varchar)
	private String  gameRoleId;
	
	
	@Column(name = "game_role_name", type = DbType.Varchar)
	private String  gameRoleName;


	@Column(name = "game_id", type = DbType.Int)
	private int gameId;
	
	@Column(name = "area_id", type = DbType.Int)
	private int areaId;
	
	@Column(name = "server_id", type = DbType.Int)
	private int serverId;
	
	@Column(name = "vcode", type = DbType.Varchar)
	private String  vcode;


	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;
	
	@Column(name = "update_time", type = DbType.DateTime)
	private java.util.Date updateTime;
	
	private String gameName;
	
	private String areaName;
	
	private String serverName;
	

	public int getId() {
		return id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getGameRoleId() {
		return gameRoleId;
	}

	public void setGameRoleId(String gameRoleId) {
		this.gameRoleId = gameRoleId;
	}
	
	public String getGameRoleName() {
		return gameRoleName;
	}

	public void setGameRoleName(String gameRoleName) {
		this.gameRoleName = gameRoleName;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
