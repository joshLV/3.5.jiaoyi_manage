package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysOperationLog;
/**
 * 系统操作日志服务
 * @author houjt
 *
 */
public interface ISysOperationLogService {
	/**
	 * 获取所有操作日志
	 * @return
	 */
	List<SysOperationLog> list();
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	SysOperationLog findById(int id);
	/**
	 * 保存
	 * @param sysOperationLog
	 */
	void save(SysOperationLog sysOperationLog);
	/**
	 * 更新
	 * @param sysOperationLog
	 */
	void update(SysOperationLog sysOperationLog);
	/**
	 * 删除
	 * @param sysOperationLog
	 */
	void delete(SysOperationLog sysOperationLog);
	
	
}
