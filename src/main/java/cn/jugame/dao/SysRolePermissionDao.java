package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysRolePermission;
/**
 * 角色权限
 * @author Administrator
 *
 */
public interface SysRolePermissionDao extends BaseDao<SysRolePermission>{
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysRolePermission findById(int id);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysRolePermission> findByProperty(String propertyName, Object value);
	
}
