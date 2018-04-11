package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductHotSaleDao;
import cn.juhaowan.product.vo.ProductHotSale;
import cn.juhaowan.product.vo.ProductOnsale;
/**
 * 
 * @author Administrator
 *
 */
@Repository("ProductHotSaleDao")
public class ProductHotSaleDaoImpl extends BaseDaoImplJdbc<ProductHotSale> implements ProductHotSaleDao {


	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public ProductOnsale findById(String productId) {
		ProductOnsale productonsale = null;
		JuRowCallbackHandler<ProductOnsale> cb = new JuRowCallbackHandler<ProductOnsale>(ProductOnsale.class);
		String sql = " SELECT b.* FROM `product_hot_sale` a LEFT JOIN  `product_onsale` b  ON a.`product_id` = b.`product_id` WHERE b.product_id= ?; ";
		jdbcOperations.query(sql, cb, productId);
		List<ProductOnsale> productList = cb.getList();
		if (productList.size() > 0) {
			productonsale = productList.get(0);
		}
		return productonsale;

	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<ProductHotSale> queryPage(Map<String, Object> conditions,int pageNo, int pageSize,String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		int count = 0;
		int firstResult = 1;
		String sqlCount = null;
		if(null == sort) {
			sort = "create_time";
		}
		if(null == order) {
			order = "desc";
		}
		sql.append(" from product_hot_sale where 1 = 1");
		if(null != conditions) {
			
			if(null != conditions.get("product_id")) {
				sql.append(" and product_id = ? ");
				conList.add(conditions.get("product_id"));
			}
		}
		sql.append(" order by " + sort + " " + order);
		System.out.println("sql ==" + sql.toString());
		sqlCount = "select count(product_id) " + sql.toString();
		count = jdbcOperations.queryForInt(sqlCount,conList.toArray());
		PageInfo<ProductHotSale> pageinfo = new PageInfo<ProductHotSale>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if(pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		String sqlPage = "select * " + sql.toString();
		
		JuRowCallbackHandler<ProductHotSale> cb = new JuRowCallbackHandler<ProductHotSale>(ProductHotSale.class);
		jdbcOperations.query(sqlPage, cb,conList.toArray());
		pageinfo.setItems(cb.getList());
		
		return pageinfo;
	}







	@Override
	public int addHotProduct(String productId, int weight) {
		//不允许重复添加
		if(isExist(productId)){
			return 1;
		}
		int i = 0;
		String sql ="insert into `product_hot_sale`(`product_id`, `weight`,`create_time`)values (?,?,now())";
		i = jdbcOperations.update(sql, productId,weight);
		return i ^ 1;
	}







	@Override
	public int delHotProduct(String productId) {
        int i = 0;
        String sql = "DELETE FROM `product_hot_sale` WHERE `product_id` = ?";
        i = jdbcOperations.update(sql, productId);
		return i ^ 1;
	}







	@Override
	public int updateHotProduct(String productId, int weight) {
		int i = 0;
		String sql ="UPDATE `product_hot_sale` SET `weight` = ? WHERE `product_id` = ?";
		i = jdbcOperations.update(sql, weight,productId);
		return i ^ 1;
	}







	@Override
	public List<ProductOnsale> listHotProduct() {
	
		List<ProductOnsale> list = null;
		JuRowCallbackHandler<ProductOnsale> cb = new JuRowCallbackHandler<ProductOnsale>(ProductOnsale.class);
		String sql ="SELECT b.*, c.`game_name`, d.`partition_name`  FROM `product_hot_sale` a ,`product_onsale` b, `game` c, `game_partition` d  WHERE  a.`product_id` = b.`product_id` AND b.`game_id`=c.`game_id` AND b.`game_id`=d.`game_id` AND  b.`game_area_id`=d.`game_partition_id` AND `product_status` = 7 AND `product_stock`>0 ORDER BY a.`weight` ASC;";
		jdbcOperations.query(sql, cb);
		list = cb.getList();
		if(list.size() > 0){
			return list;
		}
		return list;
	}

	@Override
	public boolean isExist(String productId) {
		boolean flag = false;
		String sql ="select product_id from product_hot_sale where product_id = ?";
		SqlRowSet r = jdbcOperations.queryForRowSet(sql,productId);
		while(r.next()){
			if(null != r.getString(1)){
				flag = true;
			}
		}
		return flag;
	}




	
}
