package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysModule;
/**
 * 系统模块DAO
 * @author houjt
 *
 */
public interface SysModuleDao extends BaseDao<SysModule>{
	/**
	 * 根据模块ID查找
	 * @param modId 模块ID
	 * @return 返回系统模块
	 */
	public SysModule findById(int modId);
	/**
	 * 查询所有模块
	 * @return 模块列表
	 */
	public List<SysModule> findAll();
	/**
	 * 根据属性查找模块
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysModule> findByProperty(String propertyName, Object value);
	
}
