package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysModulePermission;
/**
 * 系统模块权限接口
 * @author houjt
 *
 */
public interface SysModulePermissionDao extends BaseDao<SysModulePermission>{
	/**
	 * 根据ID查找
	 * @param pid
	 * @return
	 */
	public SysModulePermission findById(int pid);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysModulePermission> findByProperty(String propertyName, Object value);
	
}
