package cn.juhaowan.count.vo;

import java.util.Date;
import cn.jugame.dal.annotation.*;

@Table(name="count_program_byurl")
public class CountProgramByurl implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "view_url",type=DbType.Varchar)
	private java.lang.String viewUrl;
	
	@Column(name = "login_pv",type=DbType.Int)
	private int loginPv;
	
	@Column(name = "login_uv",type=DbType.Int)
	private int loginUv;
	
	@Column(name = "noLogin_pv",type=DbType.Int)
	private int nologinPv;
	
	@Column(name = "noLogin_uv",type=DbType.Int)
	private int nologinUv;
	
	@Column(name = "count_time",type=DbType.Varchar)
	private java.lang.String countTime;
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(java.lang.String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public int getLoginPv() {
		return loginPv;
	}

	public void setLoginPv(int loginPv) {
		this.loginPv = loginPv;
	}

	public int getLoginUv() {
		return loginUv;
	}

	public void setLoginUv(int loginUv) {
		this.loginUv = loginUv;
	}

	public int getNologinPv() {
		return nologinPv;
	}

	public void setNologinPv(int nologinPv) {
		this.nologinPv = nologinPv;
	}

	public int getNologinUv() {
		return nologinUv;
	}

	public void setNologinUv(int nologinUv) {
		this.nologinUv = nologinUv;
	}

	public java.lang.String getCountTime() {
		return countTime;
	}

	public void setCountTime(java.lang.String countTime) {
		this.countTime = countTime;
	}

}