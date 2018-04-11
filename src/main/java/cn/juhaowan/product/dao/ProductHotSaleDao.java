/**
 * 
 */
package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.ProductHotSale;
import cn.juhaowan.product.vo.ProductOnsale;

/**
 * 
 */
public interface ProductHotSaleDao extends BaseDao<ProductHotSale> {
	

	
    /**
     * 增加热卖商品
     * @param productId
     * @param weight
     * @return 0 成功 1 失败
     */
	int addHotProduct(String productId,int weight);
	/**
	 * 撤销热卖商品
	 * @param productId
	 * @return 0 成功 1 失败
	 */
	int delHotProduct(String productId);
	/**
	 * 根据商品id查询
	 * @param productId
	 * @return
	 */
	ProductOnsale findById(String productId);
	/**
	 * 修改热卖商品权重
	 * @param productId
	 * @param weight
	 * @return
	 */
	int updateHotProduct(String productId,int weight);
	
	/**
	 * 按条件查询日志列表
	 * 
	 * @param conditions条件
	 * @param pageNo首页
	 * @param pageSize每页条数
	 * @param sort排序字段
	 * @param orderASC/DESC
	 * @return 分页
	 */
	PageInfo<ProductHotSale> queryPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order);
	
	
	/**
	 * 列出热卖商品
	 * @return 列表
	 */
	List<ProductOnsale> listHotProduct();
	/**
	 * 判断热卖商品是否存在
	 * @param productId
	 * @return
	 */
	boolean isExist(String productId);
	
	
}
