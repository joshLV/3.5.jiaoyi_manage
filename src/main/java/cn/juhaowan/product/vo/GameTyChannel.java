/**
 * 
 */
package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;

/**
 * 游戏渠道
 * 
 * @author APXer
 * 
 */
@Table(name = "game_channel")
public class GameTyChannel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	/**
	 * 渠道名称
	 */
	@Column(name = "name", type = DbType.Varchar)
	private java.lang.String name;

	/**
	 * 状态 0：开启,1:关闭
	 */
	@Column(name = "status", type = DbType.Int)
	private int status;
	/**
	 * 权重
	 */
	@Column(name = "weight", type = DbType.Int)
	private int weight;

	
	
	
	public int getId() {
		return id;
	}

	public java.lang.String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public int getWeight() {
		return weight;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}


}
