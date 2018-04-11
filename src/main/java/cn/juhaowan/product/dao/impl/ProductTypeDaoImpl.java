package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductTypeDao;
import cn.juhaowan.product.vo.ProductType;
/**
 * 
 * @author Administrator
 *
 */
@Repository("ProductTypeDao")
public class ProductTypeDaoImpl extends BaseDaoImplJdbc<ProductType> implements ProductTypeDao {


	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public ProductType findById(int id) {
		return findById(ProductType.class, id);
	}

	@Override
	public List<ProductType> findByProperty(String propertyName, Object value) {
		return findByProperty(ProductType.class, propertyName, value);
	}

	@Override
	public int insertProductType(ProductType productType) {
		int i = 0;
		String sql = "INSERT INTO `product_type`(`id`,`product_type`,`name`,`status`,product_type_group_id)VALUES (?,?,?,?,?);";
		i = jdbcOperations.update(sql, productType.getId(),productType.getProductType(),productType.getName(),
				productType.getStatus(),productType.getProTypeGroupId());
	    return i ^ 1;
	}

	@Override
	public int updateProductType(ProductType productType) {
		if(null == productType) {
			return 1;
		}
		
		try {
			update(productType);
		} catch (Exception e) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public int deleteProductType(int id) {
		if(id < 0) {
			return 1;
		}
		ProductType pt = new ProductType();
		pt = findById(id);
		if(null == pt) {
			return 1;
		}
		try {
			delete(pt);
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@Override
	public String findProductTypeByTypeid(int id) {
		ProductType pt = new ProductType();
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		String sqlType = " select * from product_type  where id = ? ";
		jdbcOperations.query(sqlType, cb, id);
		List<ProductType> typeList = cb.getList();
		if(typeList.size() > 0) {
			pt = typeList.get(0);
		}
		if(null == pt) {
			return null;
		}
		String productType = pt.getProductType();
		return productType;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<ProductType> queryProductType(Map<String, Object> conditions,int pageNo, int pageSize, 
			String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		int count = 0;
		int firstResult = 1;
		String sqlCount = null;
		if(null == sort) {
			sort = "id";
		}
		if(null == order) {
			order = "desc";
		}
		sql.append(" FROM `product_type` AS a LEFT JOIN `producr_type_group` AS b ON a.`product_type_group_id` = b.`id` where 1 = 1");
		if(null != conditions) {
			
			if(null != conditions.get("name")) {
				sql.append(" and name like ? ");
				conList.add("%" + conditions.get("name") + "%");
			}
		

		}
		
		sql.append(" order by " + sort + " " + order);
		
		sqlCount = "select count(*) " + sql.toString();
		count = jdbcOperations.queryForInt(sqlCount,conList.toArray());
		PageInfo<ProductType> pageinfo = new PageInfo<ProductType>(count,pageSize);
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
		String sqlPage = "SELECT  a.`product_type`,a.`name`,a.`status`,b.`name` " + sql.toString();
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		
		jdbcOperations.query(sqlPage, cb,conList.toArray());
		pageinfo.setItems(cb.getList());
		
		return pageinfo;
	}

	@Override
	public ProductType findbyId(int id) {
		return findById(ProductType.class, id);
	}

	@Override
	public List<ProductType> queryProductTypeList() {
		String sql = "select * from  product_type where status = 1";
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		jdbcOperations.query(sql, cb);
		List<ProductType> productTypeList = cb.getList();
		return productTypeList;
	}

	@Override
	public List<ProductType> queryProductTypes() {
		String sql = "select * from product_type where id<100";
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		jdbcOperations.query(sql, cb);
		List<ProductType> productTypeList = cb.getList();
		return productTypeList;
	}
	
	
}
