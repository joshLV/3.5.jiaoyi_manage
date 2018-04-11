package cn.juhaowan.log.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.dao.impl.FrontUserLogDaoImpl;
import cn.juhaowan.log.service.FrontUserLogService;
import cn.juhaowan.log.vo.FrontUserLog;

/**
 * 前台日志
 * **/
@Service("FrontUserLogService")
public class DefaultFrontUserLogService implements FrontUserLogService{

	@Autowired
	private FrontUserLogDaoImpl frontUserLogDaoImpl;
	
	@Override
	public int addLog(int uid, String ip, int type,String logRemark) {
		return this.frontUserLogDaoImpl.addLog(uid, ip, type, logRemark);
	}

	@Override
	public PageInfo<FrontUserLog> queryLogList(Map<String, Object> condition,
			int pageSize, int pageNo, String sort, String order) {
		return this.frontUserLogDaoImpl.queryLogList(condition, pageSize, pageNo, sort, order);
	}
}
