package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysParameterType;
/**
 * 参数类型接口
 * @author Administrator
 *
 */
public interface SysParameterTypeDao extends BaseDao<SysParameterType>{
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysParameterType findById(int id);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysParameterType> findByProperty(String propertyName, Object value);
	
}
