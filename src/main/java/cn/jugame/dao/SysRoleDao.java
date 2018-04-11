package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysRole;
/**
 * 角色DAO
 * @author houjt
 *
 */
public interface SysRoleDao extends BaseDao<SysRole>{
	/**
	 * 根据ID查找角色对象
	 * @param roleId
	 * @return
	 */
	public SysRole findById(int roleId);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysRole> findByProperty(String propertyName, Object value);
	
	
	/**
	 * 根据属性查找客服角色
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysRole> findCustomerByProperty(String propertyName, Object value);
}
