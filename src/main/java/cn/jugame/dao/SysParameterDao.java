package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysParameter;
/**
 * 系统参数接口
 * @author houjt
 *
 */
public interface SysParameterDao extends BaseDao<SysParameter>{
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysParameter findById(int id);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysParameter> findByProperty(String propertyName, Object value);
	
}
