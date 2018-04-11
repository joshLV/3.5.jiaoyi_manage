package cn.juhaowan.count.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayProduct;
/**
 * 商品统计
 * @author Administrator
 *
 */
public interface CountDayProductDao extends BaseDao<CountDayProduct>{
	/**
	 * 按id查询商品统计
	 * @param id 编号
	 * @return 商品统计详情
	 */
	public CountDayProduct findById(int id);
	
	
	
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
	    * 【商品统计】5.1.1当日有商品上架的用户数量
	    * @param con 条件
	    * @return 当日有商品上架的用户数量 分时段 
	    */
	   List<Map> queryOnSaleUserNumByTime(Map con);
	   
	   /**
	    * 【商品统计】5.1.2当日商品上架数量
	    * @param con 条件
	    * @return 当日商品上架数量 分时段
	    */
	   List<Map> queryOnSaleNumByTime(Map con);
	   
	   /**
	    * 【商品统计】5.1.3当日商品下架成功数量
	    * @param con 条件
	    * @return 当日商品下架成功数量 分时段
	    */
	   List<Map> queryOffSaleNumByTime(Map con);
	   

}
