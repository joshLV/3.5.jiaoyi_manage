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
@Table(name = "game_partition")
public class GamePartition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "game_partition_id", type = DbType.Int)
	private int gamePartitionId;

	@Id
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;

	@Column(name = "partition_name", type = DbType.Varchar)
	private java.lang.String partitionName;

	@Column(name = "weight", type = DbType.Int)
	private int weight;
	
	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "is_ty", type = DbType.Int)
	private int isTy;
	
	@Column(name = "area_coefficient", type = DbType.Double)
	private double areaCoefficient;
	
	@Column(name = "currency_ratio", type = DbType.BigInt)
	private long currencyRatio;
	
	@Column(name = "game_version_id", type = DbType.Int)
	private int gameVersionId;
	
	//游戏分区分类id
	private int partitionTypeId;
	
	
	public int getPartitionTypeId() {
		return partitionTypeId;
	}

	public void setPartitionTypeId(int partitionTypeId) {
		this.partitionTypeId = partitionTypeId;
	}

	public int getGamePartitionId() {
		return gamePartitionId;
	}

	public void setGamePartitionId(int gamePartitionId) {
		this.gamePartitionId = gamePartitionId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public java.lang.String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(java.lang.String partitionName) {
		this.partitionName = partitionName;
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

	public int getIsTy() {
		return isTy;
	}

	public void setIsTy(int isTy) {
		this.isTy = isTy;
	}

	public double getAreaCoefficient() {
		return areaCoefficient;
	}

	public void setAreaCoefficient(double areaCoefficient) {
		this.areaCoefficient = areaCoefficient;
	}

	public long getCurrencyRatio() {
		return currencyRatio;
	}

	public void setCurrencyRatio(long currencyRatio) {
		this.currencyRatio = currencyRatio;
	}

	public int getGameVersionId() {
		return gameVersionId;
	}

	public void setGameVersionId(int gameVersionId) {
		this.gameVersionId = gameVersionId;
	}
	
	
}