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
@Table(name = "game")
public class Game implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "cp_id", type = DbType.Int)
	private int cpId;

	@Column(name = "game_name", type = DbType.Varchar)
	private java.lang.String gameName;

	@Column(name = "game_initial", type = DbType.Varchar)
	private java.lang.String gameInitial;

	@Column(name = "game_pic", type = DbType.Varchar)
	private java.lang.String gamePic;

	@Column(name = "game_info", type = DbType.Varchar)
	private java.lang.String gameInfo;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	@Column(name = "interface_url", type = DbType.Varchar)
	private java.lang.String interfaceUrl;

	@Column(name = "interface_key", type = DbType.Varchar)
	private java.lang.String interfaceKey;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "partition_flag", type = DbType.Int)
	private int partitionFlag;

	@Column(name = "server_flag", type = DbType.Int)
	private int serverFlag;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;
	
	@Column(name = "pause_remark",type = DbType.Varchar)
	private java.lang.String pauseRemark;
	
	@Column(name = "pause_begin_time",type = DbType.DateTime)
	private java.util.Date pauseBeginTime;
	
	@Column(name = "pause_end_time",type = DbType.DateTime)
	private java.util.Date pauseEndTime;

	@Column(name = "is_support_c2c", type = DbType.Int)
	private int isSupportC2c;
	
	@Column(name = "is_support_api", type = DbType.Int)
	private int isSupportApi;
	
	@Column(name = "find_user_way", type = DbType.Int)
	private int findUserWay;
	
	@Column(name = "api_version", type = DbType.Int)
	private int apiVersion;
	
	@Column(name = "is_show_pwd", type = DbType.Int)
	private int isShowPwd;
	
	@Column(name = "is_down", type = DbType.Int)
	private int isDown;
	
	@Column(name = "down_url", type = DbType.Varchar)
	private String downUrl;
	
	@Column(name = "is_ty", type = DbType.Int)
	private int isTy;
	
	@Column(name = "game_coefficient", type = DbType.Double)
	private double gameCoefficient;
	
	@Column(name = "market_coefficient", type = DbType.Double)
	private double marketCoefficient;
	
	@Column(name = "is_common_currency", type = DbType.Int)
	private int isCommonCurrency;
	
	@Column(name = "common_currency_type_id", type = DbType.Int)
	private int commonCurrencyTypeId;
	
	@Column(name = "start",type = DbType.TinyInt)
	private int start;
	
	@Column(name = "file_size",type = DbType.Varchar)
	private java.lang.String fileSize;
	
	@Column(name = "is_for_chat", type = DbType.Int)
	private int isForChat;
	
	@Column(name = "is_ty_product", type = DbType.Int)
	private int isTyProduct;
	
	@Column(name = "game_home_url",type = DbType.Varchar)
	private java.lang.String gameHomeUrl;

	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getCpId() {
		return cpId;
	}

	public void setCpId(int cpId) {
		this.cpId = cpId;
	}

	public java.lang.String getGameName() {
		return gameName;
	}

	public void setGameName(java.lang.String gameName) {
		this.gameName = gameName;
	}

	public java.lang.String getGameInitial() {
		return gameInitial;
	}

	public void setGameInitial(java.lang.String gameInitial) {
		this.gameInitial = gameInitial;
	}

	public java.lang.String getGamePic() {
		return gamePic;
	}

	public void setGamePic(java.lang.String gamePic) {
		this.gamePic = gamePic;
	}

	public java.lang.String getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(java.lang.String gameInfo) {
		this.gameInfo = gameInfo;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public java.lang.String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(java.lang.String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public java.lang.String getInterfaceKey() {
		return interfaceKey;
	}

	public void setInterfaceKey(java.lang.String interfaceKey) {
		this.interfaceKey = interfaceKey;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPartitionFlag() {
		return partitionFlag;
	}

	public void setPartitionFlag(int partitionFlag) {
		this.partitionFlag = partitionFlag;
	}

	public int getServerFlag() {
		return serverFlag;
	}

	public void setServerFlag(int serverFlag) {
		this.serverFlag = serverFlag;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public java.lang.String getPauseRemark() {
		return pauseRemark;
	}

	public void setPauseRemark(java.lang.String pauseRemark) {
		this.pauseRemark = pauseRemark;
	}

	public java.util.Date getPauseBeginTime() {
		return pauseBeginTime;
	}

	public void setPauseBeginTime(java.util.Date pauseBeginTime) {
		this.pauseBeginTime = pauseBeginTime;
	}

	public java.util.Date getPauseEndTime() {
		return pauseEndTime;
	}

	public void setPauseEndTime(java.util.Date pauseEndTime) {
		this.pauseEndTime = pauseEndTime;
	}

	public int getIsSupportC2c() {
		return isSupportC2c;
	}

	public void setIsSupportC2c(int isSupportC2c) {
		this.isSupportC2c = isSupportC2c;
	}

	public int getIsSupportApi() {
		return isSupportApi;
	}

	public void setIsSupportApi(int isSupportApi) {
		this.isSupportApi = isSupportApi;
	}

	public int getFindUserWay() {
		return findUserWay;
	}

	public void setFindUserWay(int findUserWay) {
		this.findUserWay = findUserWay;
	}

	public int getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(int apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public int getIsShowPwd() {
		return isShowPwd;
	}

	public void setIsShowPwd(int isShowPwd) {
		this.isShowPwd = isShowPwd;
	}

	public int getIsDown() {
		return isDown;
	}

	public void setIsDown(int isDown) {
		this.isDown = isDown;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public int getIsTy() {
		return isTy;
	}

	public void setIsTy(int isTy) {
		this.isTy = isTy;
	}

	public double getGameCoefficient() {
		return gameCoefficient;
	}

	public void setGameCoefficient(double gameCoefficient) {
		this.gameCoefficient = gameCoefficient;
	}

	public double getMarketCoefficient() {
		return marketCoefficient;
	}

	public void setMarketCoefficient(double marketCoefficient) {
		this.marketCoefficient = marketCoefficient;
	}

	public int getIsCommonCurrency() {
		return isCommonCurrency;
	}

	public void setIsCommonCurrency(int isCommonCurrency) {
		this.isCommonCurrency = isCommonCurrency;
	}

	public int getCommonCurrencyTypeId() {
		return commonCurrencyTypeId;
	}

	public void setCommonCurrencyTypeId(int commonCurrencyTypeId) {
		this.commonCurrencyTypeId = commonCurrencyTypeId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public java.lang.String getFileSize() {
		return fileSize;
	}

	public void setFileSize(java.lang.String fileSize) {
		this.fileSize = fileSize;
	}

	public int getIsForChat() {
		return isForChat;
	}

	public void setIsForChat(int isForChat) {
		this.isForChat = isForChat;
	}

	public int getIsTyProduct() {
		return isTyProduct;
	}

	public void setIsTyProduct(int isTyProduct) {
		this.isTyProduct = isTyProduct;
	}

	public java.lang.String getGameHomeUrl() {
		return gameHomeUrl;
	}

	public void setGameHomeUrl(java.lang.String gameHomeUrl) {
		this.gameHomeUrl = gameHomeUrl;
	}
	
	
	
	
}