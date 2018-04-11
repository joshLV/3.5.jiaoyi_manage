package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysOperationLogDao;
import cn.jugame.service.ISysOperationLogService;
import cn.jugame.vo.SysOperationLog;
/**
 * 系统操作日志服务
 * @author houjt
 *
 */
@Service("SysOperationLogService")
public class SysOperationLogService implements ISysOperationLogService{
	@Autowired
	@Qualifier("SysOperationLogDao")
	private SysOperationLogDao sysOperationLogDao;
	@Override
	public List<SysOperationLog> list(){
		return sysOperationLogDao.queryForPage("from SysOperationLog", 0, 20);
	} 
	@Override
	public void save(SysOperationLog sysOperationLog){ 
		sysOperationLogDao.insert(sysOperationLog);
	}
	@Override
	public SysOperationLog findById(int id){
		return sysOperationLogDao.findById(id);		
	}
	@Override
	public void update(SysOperationLog sysOperationLog) {
		sysOperationLogDao.update(sysOperationLog);
	}
	@Override
	public void delete(SysOperationLog sysOperationLog) {
		sysOperationLogDao.delete(sysOperationLog);
	}
	 
	
}
