package cn.juhaowan.count.dao;

import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountProgramByurl;
/**
 * 
 * @author Administrator
 *
 */
public interface CountProgramByurlDao extends BaseDao<CountProgramByurl> {
	/**
	 * 统计每日URL的访问次数
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 每页多少条记录
	 * @param sort 排序字段
	 * @param order 排序方式
	 * @return 分页
	 */
	public PageInfo<Map<String,Object>> queryCountProgramByurlList(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);
}
