package cn.juhaowan.count.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayActive;
/**
 * 日活统计
 * @author Administrator
 *
 */
public interface CountDayActiveDao extends BaseDao<CountDayActive>{
	/**
	 * 按id查找日活统计信息
	 * @param id 编号
	 * @return 日活统计信息
	 */
	public CountDayActive findById(int id);
	
	
	
	   /** 按条件分页查询列表
	    * @param conditions 查询条件
	    * @param pageNo 页数
	    * @param pageSize 每页条数
	    * @param sort 排序字段
	    * @param order desc/asc
	    * @return 分页
	    * @pdOid 4f0ade49-6c39-4c50-9dff-4874dd2991eb */
	   PageInfo queryForPage(Map<String,Object> conditions, int pageNo, int pageSize, String sort, String order);
	   
	   /**
	    * 未注册用户PV 分时段
	    * @param conditions 条件
	    * @return 未注册用户pv 分时段 列表数据
	    */
	   List<Map> queryNoRegPVByTime(Map conditions);
}
