package cn.jugame.admin.web.form;

import java.io.Serializable;

import cn.jugame.vo.SysUserinfo;

/**
 * 
 * @author Administrator
 * 
 */
public class SysUserinfoForm extends SysUserinfo implements Serializable {

	private static final long serialVersionUID = 5552503598505246567L;

	/**
	 * 角色id
	 */
	private int[] roleids;
	private Integer[] serviceTypeIds;
	

	public int[] getRoleids() {
		return roleids;
	}

	public void setRoleids(int[] roleids) {
		this.roleids = roleids;
	}

	public Integer[] getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(Integer[] serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}


	
	

}
