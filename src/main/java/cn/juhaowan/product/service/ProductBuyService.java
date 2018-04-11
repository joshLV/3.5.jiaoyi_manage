package cn.juhaowan.product.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.ProductActivitiesDownload;
import cn.juhaowan.product.vo.ProductActivitiesGoods;
import cn.juhaowan.product.vo.ProductActivitiesImages;
import cn.juhaowan.product.vo.ProductActivitiesNotes;
import cn.juhaowan.product.vo.ProductBuyFilter;
import cn.juhaowan.product.vo.ProductBuyFilterConfig;
import cn.juhaowan.product.vo.ProductActivities;

/**
 * 
 * @author chenchong
 * 
 */
public interface ProductBuyService {

	/**
	 * 商品购买规则列表
	 * 
	 * @return
	 */
	List<ProductBuyFilterConfig> list();

	/**
	 * 商品购买规则列表(显示正常)
	 * 
	 * @return
	 */
	List<ProductBuyFilterConfig> listStatus();
	/**
	 * 添加
	 * 
	 * @param productBuyFilterConfig
	 * @return
	 */
	int addFilterConfig(ProductBuyFilterConfig productBuyFilterConfig);

	/**
	 * 通过id查找
	 * 
	 * @param id
	 * @return
	 */
	ProductBuyFilterConfig findById(int id);

	/**
	 * 更新
	 * 
	 * @return
	 */
	int updateFilterConfig(ProductBuyFilterConfig productBuyFilterConfig);

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteFilterConfig(int id);

	/**
	 * 添加
	 * 
	 * @param productBuyFilter
	 * @return
	 */
	int addFilter(ProductBuyFilter productBuyFilter);

	/**
	 * 根据商品id 删除购买规则(批量)
	 * 
	 * @param pids
	 * @param filterId
	 * @param aId
	 * @return
	 */
	int delFilter(String pids, String filterId, String aId);

	/**
	 * 删除活动下的指定商品
	 * 
	 * @param pids
	 * @param aId
	 * @return
	 */
	int delFilter(String pids, String aId);

	/**
	 * 根据商品id获取购买规则
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductBuyFilter> productFiterList(String productId);

	/**
	 * 根据id 删除购买规则
	 * 
	 * @param id
	 * @return
	 */
	int delFilterById(int id);

	/**
	 * 根据活动id获取购买规则
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductBuyFilter> productFiterListByActivitiesId(String activitiesId);

	/**
	 * 根据活动id获取购买规则GROUP BY 商品id
	 * 
	 * @param productId
	 * @param activitiesId
	 * @return
	 */
	List<ProductBuyFilter> distinctFiterListByAId(String productId, String activitiesId);
	
	/**
	 * 判断规则是否已经被应用
	 * @param fiterId
	 * @return
	 */
	List<ProductBuyFilter> findFilterList(String fiterId);

	/**
	 * 根据条件查找购买规则
	 * 
	 * @param productId
	 * @param fiterId
	 * @param acivitiesId
	 * @return
	 */
	ProductBuyFilter findFilter(String productId, String fiterId, String acivitiesId);

	/**
	 * 通过id查找商品规则
	 * 
	 * @param id
	 * @return
	 */
	ProductBuyFilter findProductBuyFilterById(int id);

	/**
	 * 更新特定商品规则
	 * 
	 * @param productBuyFilter
	 * @return
	 */
	int updateFilter(ProductBuyFilter productBuyFilter);

	/**
	 * 添加活动
	 * 
	 * @param productActivities
	 * @return
	 */
	int addActivities(ProductActivities productActivities);

	/**
	 * 编辑活动
	 * 
	 * @param productActivities
	 * @return
	 */
	int editActivities(ProductActivities productActivities);

	/**
	 * 编辑活动上下线
	 * 
	 * @param status 0：下线 1：上线
	 * @param id
	 * @return
	 */
	int modifyActivitiesStatus(int status, String id);

	/**
	 * 按条件查询分页
	 * 
	 * @param conditions 条件
	 * @param pageNo 首页
	 * @param pageSize 每页条数
	 * @param sort 排序字段
	 * @param order ASC/DESC
	 * @return 分页
	 * @pdOid e5db0a45-34d3-474d-a7b6-b40fe660b46b
	 */
	PageInfo<ProductActivities> queryActivitiesPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order, String tag);

	/**
	 * 根据id查询活动信息
	 * 
	 * @param aid
	 * @return
	 */
	ProductActivities findActivitiesById(String aid);

	/**
	 * 判断活动时间重叠
	 * @param aid
	 * @param productId
	 * @return
	 */
	String overlapActivities(String aid, String productId);
	
	/**
	 * 判断活动时间重叠(传入时间)
	 * @param aid
	 * @param productId
	 * @param modifybtime
	 * @param modifyetime
	 * @return
	 */
    String overlapActivities(String aid, String productId,String modifybtime,String modifyetime);
	
	/**
	 * 列出活动下的所有商品
	 * @param aid
	 * @return
	 */
	List<ProductActivitiesGoods> listActivitiesGoods(String productid,String aid);
	
	/**
	 * 添加
	 * @return
	 */
	int  addActivitiesGoods(ProductActivitiesGoods productActivitiesGoods);
	
	
	/**
	 * 根据活动id和商品id查询活动商品
	 * @param aid
	 * @param productId
	 * @return
	 */
	ProductActivitiesGoods findActivitiesGoodsById(String aid,String productId);
	
	/**
	 * 设置活动商品内页显示
	 * @param aid
	 * @param productId
	 * @return
	 */
	int updateActivitiesGoodsInpageShow(String aid,String productId);
	
	/**
	 * 设置活动下所有商品都不在内页显示
	 * @param aid
	 * @return
	 */
	int resetActivitiesGoodsInpageShow(String aid);
	/**
	 * 删除活动商品
	 * @return
	 */
	int delProductActivitiesGoods(String aid,String productId);
	
	/**
	 * 活动图片
	 * @param aid
	 * @param type
	 * @return
	 */
	ProductActivitiesImages listActivitiesImages(String aid,int type);
	
	
	/**
	 * 添加活动图片
	 * @param img
	 * @return
	 */
	int addActivitiesImage(ProductActivitiesImages img);
	
	/**
	 * 更新活动图片
	 * @param img
	 * @return
	 */
	int updateActivitiesImage(ProductActivitiesImages img);
	/**
	 * 删除活动图片
	 * @param id
	 * @return
	 */
	int delActivitiesImage(int id);
	
	/**
	 * 首页图片列表
	 * @param aid
	 * @return
	 */
	List<ProductActivitiesImages> indeximgList(String aid);
	/**
	 * 内页图片列表
	 * @param aid
	 * @return
	 */
	List<ProductActivitiesImages> innerimgList(String aid);
	
	/**
	 * 活动须知列表
	 * @param aid
	 * @return
	 */
	List<ProductActivitiesNotes> listNotes(String aid);
	
	/**
	 * 添加活动须知
	 * @param notes
	 * @return
	 */
	int addActivitiesNotes(ProductActivitiesNotes notes);
	
	/**
	 * 更新活动须知
	 * @param notes
	 * @return
	 */
	int updateActivitiesNotes(ProductActivitiesNotes notes);
	
	/**
	 * 根据id查找活动须知
	 * @param id
	 * @return
	 */
	ProductActivitiesNotes  findActivitiesNotes(int id);
	
	/**
	 * 活动游戏下载列表
	 * @param aid
	 * @return
	 */
	List<ProductActivitiesDownload> listActivitiesDownload(String aid);
	
	/**
	 * 增加活动下载游戏链接
	 * @param dl
	 * @return
	 */
	int addActivitiesDownload(ProductActivitiesDownload dl);
	
	/**
	 * 更新游戏下载游戏链接信息
	 * @param dl
	 * @return
	 */
	int updateActivitiesDownload(ProductActivitiesDownload dl);
	
	/**
	 * 删除游戏下载游戏信息链接
	 * @param id
	 * @return
	 */
	int delActivitiesDownload(int id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	ProductActivitiesDownload findActivitiesDownload(int id);
	/**
	 * 更新活动权重
	 * @param weight
	 * @param id
	 * @return
	 */
	int modifyActivitiesWeight(int weight, String id);
}
