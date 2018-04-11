package cn.juhaowan.product.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.juhaowan.product.dao.ProductSelfDefinedDao;
import cn.juhaowan.product.service.ProductSelfDefinedService;
import cn.juhaowan.product.vo.ProductSelfDefined;

/**
 * 
 * @author Administrator
 * 
 */
@Service("ProductSelfDefinedService")
public class DefaultProductSelfDefinedService implements ProductSelfDefinedService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultProductSelfDefinedService.class);
	@Autowired
	private ProductSelfDefinedDao productSelfDefinedDao;

	@Override
	public int insertSelfDefined(ProductSelfDefined selfDefined) {
		return productSelfDefinedDao.insertSelfDefined(selfDefined);
	}

	@Override
	public int updateSelfDefined(ProductSelfDefined selfDefined) {
		return productSelfDefinedDao.updateSelfDefined(selfDefined);
	}

	@Override
	public int deleteSelfDefined(String productId) {
		return productSelfDefinedDao.deleteSelfDefined(productId);
	}

	@Override
	public ProductSelfDefined queryByProductId(String productId) {
		return productSelfDefinedDao.queryByProductId(productId);
	}

	@Override
	public boolean isExist(String productId) {
		return productSelfDefinedDao.isExist(productId);
	}

}
