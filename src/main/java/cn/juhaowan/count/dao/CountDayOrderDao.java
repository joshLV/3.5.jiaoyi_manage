package cn.juhaowan.count.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayOrder;
/**
 * 交易统计
 * @author Administrator
 *
 */
public interface CountDayOrderDao extends BaseDao<CountDayOrder>{
	/**
	 * 按id查找交易统计信息
	 * @param id 编号
	 * @return 交易统计信息
	 */
	public CountDayOrder findById(int id);
	
	
	
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
	    * 【订单统计】6.1.1 交易成功量
	    * @param con 条件
	    * @return 交易成功量 分时段 列表
	    */
	   List<Map> queryOrderSucNumByTime(Map con);
	   
	   /**
	    * 【订单统计】6.1.2 交易失败
	    * @param con 条件
	    * @return 交易失败 分时段 列表
	    */
	   List<Map> queryOrderFaileNumByTime(Map con);
	   
	   /**
	    * 【订单统计】6.1.3 交易金额
	    * @param con 条件
	    * @return 交易金额 分时段 列表
	    */
	   List<Map> queryOrderMSumByTime(Map con);
}
