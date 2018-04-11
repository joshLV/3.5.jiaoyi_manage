package cn.juhaowan.product.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductHotSaleDao;
import cn.juhaowan.product.service.ProductHotSaleService;
import cn.juhaowan.product.vo.ProductHotSale;
import cn.juhaowan.product.vo.ProductOnsale;

/**
 * 
 * @author Administrator
 * 
 */
@Service("ProductHotSaleService")
public class DefaultProductHotSaleService implements ProductHotSaleService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultProductHotSaleService.class);

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Autowired
	private ProductHotSaleDao productHotSaleDao;

	@Override
	public int addHotProduct(String productId, int weight) {
		return productHotSaleDao.addHotProduct(productId, weight);
	}

	@Override
	public int delHotProduct(String productId) {
		return productHotSaleDao.delHotProduct(productId);
	}

	@Override
	public ProductOnsale findById(String productId) {
		return productHotSaleDao.findById(productId);
	}

	@Override
	public int updateHotProduct(String productId, int weight) {
		return productHotSaleDao.updateHotProduct(productId, weight);
	}

	@Override
	public PageInfo<ProductHotSale> queryPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order) {
		return productHotSaleDao.queryPage(conditions, pageNo, pageSize, sort, order);
	}

	@Override
	public List<ProductOnsale> listHotProduct() {
		if (Cache.get("listHotProduct") != null) {
			return Cache.get("listHotProduct");
		}
		List<ProductOnsale> list =  productHotSaleDao.listHotProduct();
		if(list.size() > 0){
			Cache.set("listHotProduct", 180, list);
			return list;
		}
		return null;
	}

	
	

}
