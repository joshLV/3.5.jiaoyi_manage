package cn.juhaowan.count.vo;

import java.util.Date;
import cn.jugame.dal.annotation.*;

@Table(name="count_program_bytime")
public class CountProgramBytime implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "detail_id",type=DbType.Int)
	private int detailId;
	
	@Column(name = "view_time",type=DbType.Int)
	private int viewTime;
	
	@Column(name = "login_pv",type=DbType.Int)
	private int loginPv;
	
	@Column(name = "noLogin_pv",type=DbType.Int)
	private int nologinPv;
	
	@Column(name = "count_time",type=DbType.Varchar)
	private java.lang.String countTime;
	
	 
	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public int getViewTime() {
		return viewTime;
	}

	public void setViewTime(int viewTime) {
		this.viewTime = viewTime;
	}

	public int getLoginPv() {
		return loginPv;
	}

	public void setLoginPv(int loginPv) {
		this.loginPv = loginPv;
	}

	public int getNologinPv() {
		return nologinPv;
	}

	public void setNologinPv(int nologinPv) {
		this.nologinPv = nologinPv;
	}

	public java.lang.String getCountTime() {
		return countTime;
	}

	public void setCountTime(java.lang.String countTime) {
		this.countTime = countTime;
	}

}