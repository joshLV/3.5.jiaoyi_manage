package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysOperationLogDao;
import cn.jugame.vo.SysOperationLog;
/**
 * 系统日志
 * @author houjt
 *
 */
@Repository("SysOperationLogDao")
public class SysOperationLogDaoImpl extends BaseDaoImpl<SysOperationLog> implements SysOperationLogDao{
	@Override
	public SysOperationLog findById(int id) {
		return (SysOperationLog)getSession().get(SysOperationLog.class , id);
	}
	@Override
	public List<SysOperationLog> findByProperty(String propertyName, Object value) {
		return findByProperty("SysOperationLog", propertyName, value);
	}
 

}
