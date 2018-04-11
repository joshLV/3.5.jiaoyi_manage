package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysUserRole;
/**
 * 用户角色
 * @author houjt
 *
 */
public interface SysUserRoleDao extends BaseDao<SysUserRole>{
	/**
	 * 根据ID
	 * @param id
	 * @return
	 */
	public SysUserRole findById(int id);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysUserRole> findByProperty(String propertyName, Object value);
	
}
