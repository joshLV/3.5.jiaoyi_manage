package cn.juhaowan.product.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.ProductSelfDefinedDao;
import cn.juhaowan.product.vo.ProductSelfDefined;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("ProductSelfDefinedDao")
public class ProductSelfDefinedDaoImpl extends BaseDaoImplJdbc<Object> implements ProductSelfDefinedDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int insertSelfDefined(ProductSelfDefined selfDefined) {
		if(null == selfDefined){
			return 1;
		}
		boolean flag = isExist(selfDefined.getProductId());
		if(flag){
			return 1;
		}
		String sql = "INSERT `product_self_defined`(`product_id`,`self_defined`)VALUES (?, ?)";
		int i = jdbcOperations.update(sql, selfDefined.getProductId(),selfDefined.getSelfDefined());
		return i ^ 1;
	}

	@Override
	public int updateSelfDefined(ProductSelfDefined selfDefined) {
		if(null == selfDefined){
			return 1;
		}
		boolean flag = isExist(selfDefined.getProductId());
		if(flag == false){
			insertSelfDefined(selfDefined);
		}
	    String sql = "UPDATE `product_self_defined` SET `self_defined` = ? WHERE `product_id` = ?";
        int i = jdbcOperations.update(sql,selfDefined.getSelfDefined(),selfDefined.getProductId());
		return i ^ 1;
	}

	@Override
	public int deleteSelfDefined(String productId) {
		String sql = "DELETE FROM `product_self_defined` WHERE `product_id` = ?";
		int i = jdbcOperations.update(sql, productId);
		return i ^ 1;
	}

	@Override
	public ProductSelfDefined queryByProductId(String productId) {
		ProductSelfDefined de = null;
		String sql = "SELECT * FROM `product_self_defined` WHERE `product_id` = ?";
		
		JuRowCallbackHandler<ProductSelfDefined> rch = new JuRowCallbackHandler<ProductSelfDefined>(ProductSelfDefined.class);
		jdbcOperations.query(sql, rch,productId);
		if(null != rch.getList() && rch.getList().size() > 0){
			de = rch.getList().get(0);
		}
		return de;
	}

	@Override
	public boolean isExist(String productId) {
		boolean flag = false;
		String sql = "SELECT `product_id` FROM `product_self_defined` WHERE `product_id` = ?";
	    SqlRowSet r = jdbcOperations.queryForRowSet(sql,productId);
	    while(r.next()){
	    	flag = true;
	    }
		return flag;
	}

	
}
