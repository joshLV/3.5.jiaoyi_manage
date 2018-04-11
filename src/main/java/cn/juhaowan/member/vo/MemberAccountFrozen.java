package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;


/**
 * 资金冻结表。 被冻结的用户不能完成支付动作
 * @author lithium
 *
 */

@Table(name = "member_account_frozen")
public class MemberAccountFrozen  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "id",type = DbType.Int)
	private int id;
	
	
	@Column(name = "uid",type = DbType.Int)
	private int uid;
	
	@Column(name = "type",type = DbType.Int)
	private int type;


	@Column(name = "remark",type = DbType.Varchar)
	private String remark;
	
	@Column(name = "update_time",type = DbType.DateTime)
	private java.util.Date updateTime;


	@Column(name = "create_time",type = DbType.DateTime)
	private java.util.Date createTime;
	
	public int getId() {
		return id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
}
