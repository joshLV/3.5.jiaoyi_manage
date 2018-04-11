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
@Table(name = "game_cp")
public class GameCP implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cp_id", type = DbType.Int)
	private int cpId;

	@Column(name = "cp_name", type = DbType.Varchar)
	private java.lang.String cpName;

	@Column(name = "cp_discretion", type = DbType.Varchar)
	private java.lang.String cpDiscretion;

	public int getCpId() {
		return cpId;
	}

	public void setCpId(int cpId) {
		this.cpId = cpId;
	}

	public java.lang.String getCpName() {
		return cpName;
	}

	public void setCpName(java.lang.String cpName) {
		this.cpName = cpName;
	}

	public java.lang.String getCpDiscretion() {
		return cpDiscretion;
	}

	public void setCpDiscretion(java.lang.String cpDiscretion) {
		this.cpDiscretion = cpDiscretion;
	}

}