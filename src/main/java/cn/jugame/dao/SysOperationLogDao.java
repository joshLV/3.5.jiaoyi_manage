package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysOperationLog;
/**
 * 系统日志接口
 * @author houjt
 *
 */
public interface SysOperationLogDao extends BaseDao<SysOperationLog>{
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysOperationLog findById(int id);
	/**
	 * 根据属性查找列表
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysOperationLog> findByProperty(String propertyName, Object value);
	
}
