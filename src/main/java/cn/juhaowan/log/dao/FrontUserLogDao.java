package cn.juhaowan.log.dao;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.vo.FrontUserLog;
/**
 * 前台日志
 * 
 * **/
public interface FrontUserLogDao {

	/**
	 * 写入日志
	 * @param uid 
     * @param ip 
     * @param type
     */
	int addLog(int uid, String ip, int type,String logRemark);

   /**
    * 查询日志列表
    *  @param condition 查询条件
    *  @param pageSize 每页显示数
    *  @param pageNo 显示页
    *  @param sort 排序字段
    *  @param order 排序规则
    */
	PageInfo<FrontUserLog> queryLogList(Map<String, Object> condition,
			int pageSize, int pageNo, String sort, String order);
}
