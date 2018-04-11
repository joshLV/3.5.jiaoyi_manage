package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysRoleModule;
/**
 * 角色模块DAO
 * @author Administrator
 *
 */
public interface SysRoleModuleDao extends BaseDao<SysRoleModule>{
	/**
	 * 根据ID查找对象
	 * @param id
	 * @return
	 */
	public SysRoleModule findById(int id);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysRoleModule> findByProperty(String propertyName, Object value);
	
}
