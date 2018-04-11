package cn.juhaowan.product.dao;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.ProductBuyFilterConfig;
import java.util.*;

public interface ProductBuyDao extends  BaseDao<ProductBuyFilterConfig> {
	/**
	 * 
	 * @return 商品购买规则列表
	 */
	List<ProductBuyFilterConfig>  list();
	/**
	 * 商品购买规则列表(正常)
	 * @return
	 */
	List<ProductBuyFilterConfig> listByStatus();
	/**
	 * 	添加
	 * @param productBuyFilterConfig
	 */
	int addFilterConfig(ProductBuyFilterConfig productBuyFilterConfig);
	
	/**
	 * 根据id号删除
	 */
	int deleteFilterConfig(int id);
	
	/**
	 * 更新
	 * @param productBuyFilterConfig
	 * @return
	 */
	int updateFilter(ProductBuyFilterConfig productBuyFilterConfig);
	
	/**
	 * 通过id查找
	 * @param id
	 * @return
	 */
	ProductBuyFilterConfig findById(int id);

}
