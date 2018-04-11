/**
 * 
 */
package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * @author APXer
 * 
 */
@Table(name = "game_channel")
public class GameChannel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type = DbType.Int)
	private int id;
	/**
	 * 所属游戏
	 */
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	/**
	 * 所属渠道
	 */
	@Column(name = "channel_id", type = DbType.Int)
	private int channelId;


	@Column(name = "qudao_coefficient",type = DbType.Double)
	private double qudaoCoefficient;
	
	@Column(name = "is_ty",type=DbType.Int)
	private int isTy;
	
	@Column(name = "down_url",type=DbType.Varchar)
	private java.lang.String downUrl;
	
	@Column(name = "file_size",type=DbType.Varchar)
	private java.lang.String fileSize;
	
	@Column(name = "game_version_id",type=DbType.Int)
	private int gameVersionId;
	
	@Column(name = "channel_name",type=DbType.Varchar)
	private String channelName;
	
	
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

	
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public double getQudaoCoefficient() {
		return qudaoCoefficient;
	}

	public void setQudaoCoefficient(double qudaoCoefficient) {
		this.qudaoCoefficient = qudaoCoefficient;
	}

	public int getIsTy() {
		return isTy;
	}

	public void setIsTy(int isTy) {
		this.isTy = isTy;
	}

	public java.lang.String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(java.lang.String downUrl) {
		this.downUrl = downUrl;
	}

	public java.lang.String getFileSize() {
		return fileSize;
	}

	public void setFileSize(java.lang.String fileSize) {
		this.fileSize = fileSize;
	}

	public int getGameVersionId() {
		return gameVersionId;
	}

	public void setGameVersionId(int gameVersionId) {
		this.gameVersionId = gameVersionId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	
	
}
