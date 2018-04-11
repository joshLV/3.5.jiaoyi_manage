package cn.juhaowan.log.dao;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.vo.BackUserLog;

/**
 * 后台日志
 * **/
public interface BackUserLogDao {

   /**
    * 添加后台日志
    * 
	* @param uid 用户id 
	* @param ip 用户ip
	* @param typ 备注
	*/
    int addLog(int uid, String ip, int typ,String logRemark);
   
   
   /**
    * 查询后台日志
    * 
    *  @param condition 查询条件
    *  @param pageSize 每页大小
    *  @param pageNO 查询哪一页
    *  @param sort 排序字段
    *  @param order 排序规则
    *  @return   PageInfo
    */
    PageInfo<BackUserLog> queryLogList(Map<String, Object> condition, int pageSize, int pageNO, String sort, String order);
}