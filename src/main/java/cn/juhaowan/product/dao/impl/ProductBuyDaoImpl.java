package cn.juhaowan.product.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.ProductBuyDao;
import cn.juhaowan.product.vo.ProductBuyFilterConfig;


/**
 * 
 * @author chenchong
 *
 */

@Repository("ProductBuyDao")
public class ProductBuyDaoImpl extends BaseDaoImplJdbc<ProductBuyFilterConfig> implements  ProductBuyDao{
    
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public List<ProductBuyFilterConfig> list() {
		JuRowCallbackHandler<ProductBuyFilterConfig> cb=new JuRowCallbackHandler<ProductBuyFilterConfig>(ProductBuyFilterConfig.class);
		String sql="select * from product_buy_filter_conf order by weight asc";
		jdbcOperations.query(sql, cb);
		List<ProductBuyFilterConfig> pbList=cb.getList();
		return pbList;
	}
	
	@Override
	public List<ProductBuyFilterConfig> listByStatus() {
		JuRowCallbackHandler<ProductBuyFilterConfig> cb=new JuRowCallbackHandler<ProductBuyFilterConfig>(ProductBuyFilterConfig.class);
		String sql="select * from product_buy_filter_conf where status = 0 order by weight asc";
		jdbcOperations.query(sql, cb);
		List<ProductBuyFilterConfig> pbList=cb.getList();
		return pbList;
	}

	@Override
	public int addFilterConfig(ProductBuyFilterConfig productBuyFilterConfig) {
		try {
			this.insert(productBuyFilterConfig);
			
			return 0;
		} catch (Exception e) {
//			LOG.error("",e);
			return 1;
		}
	}

	@Override
	public ProductBuyFilterConfig findById(int id) {
		
		return findById(ProductBuyFilterConfig.class, id);
	}
	
	@Override
	public int deleteFilterConfig(int id) {
		if(id<0) {
			return 1;
		}
		ProductBuyFilterConfig fc=findById(id);
		if(fc==null) {
			return 1;
		}
		try {
			
			this.delete(fc);
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@Override
	public int updateFilter(ProductBuyFilterConfig pro) {
		if (pro==null) {
			return 1;
		}
		ProductBuyFilterConfig fc=findById(pro.getId());
		if(fc==null) {
			return 1;
		}
		fc.setName(pro.getName());
		fc.setClassName(pro.getClassName());
		fc.setParamDesc(pro.getParamDesc());
		fc.setWeight(pro.getWeight());
		fc.setRemark(pro.getRemark());
		fc.setStatus(pro.getStatus());
		fc.setType(pro.getType());
		try {
			update(fc);
			
		} catch (Exception e) {
			return 1;
		}
		return 0;
		
	}

}
