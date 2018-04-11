package cn.juhaowan.log.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.dao.impl.BackUserLogDaoImpl;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.log.vo.BackUserLog;

/**
 * 后台日志
 * **/
@Service("BackUserLogService")
public class DefaultBackUserLogService implements BackUserLogService{

	@Autowired
	private BackUserLogDaoImpl backUserLogDaoImpl;
	
	@Override
	public int addLog(int uid, String ip, int type,String logRemark) {
		return this.backUserLogDaoImpl.addLog(uid, ip, type, logRemark);
	}

	@Override
	public PageInfo<BackUserLog> queryLogList(Map<String, Object> condition,
			int pageSize, int pageNo, String sort, String order) {
		return this.backUserLogDaoImpl.queryLogList(condition, pageSize, pageNo, sort, order);
	}

}
