package cn.juhaowan.count.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountProgram;

public interface CountProgramService {
	/**
	 * 查询顶级栏目
	 * @return
	 */
	List<CountProgram> getParentProgram();
	/**
	 * 查询所有栏目
	 * @return
	 */
	List<CountProgram> getAllProgram();
	/**
	 * 添加栏目
	 * @param countProgram
	 * @return
	 */
	int addCountProgram(CountProgram countProgram);
	/**
	 * 修改栏目信息
	 * @param countProgram
	 * @return
	 */
	int updateCountProgram(CountProgram countProgram);
	/**
	 * 删除栏目
	 * @param programId 栏目ID
	 * @param parentId 父栏目ID
	 * @return
	 */
	int deleteCountProgram(int programId,int parentId);
	/**
	 * 根据栏目ID查找相关栏目
	 * @param id
	 * @return
	 */
	CountProgram findById(int id);

	
	/**
	 * 栏目浏览量统计
	 */
	List<Map> queryProgramByUrl(Map<String, Object> conditions);
	
	/**
	 * 按日期查询栏目pv uv 
	 */
	Map ListProgramByTime(Map conditions);

	/**
	 * 统计每日URL的访问次数
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 每页多少条记录
	 * @param sort 排序字段
	 * @param order 排序方式
	 * @return
	 */
	PageInfo<Map<String,Object>> queryCountProgramByurlList(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

}
