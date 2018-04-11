package cn.juhaowan.product.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.product.service.ProductTypeGroupService;
import cn.juhaowan.product.vo.ProductTypeGroup;

@Service("ProductTypeGroupService")
public class DefaultProductTypeGroup implements ProductTypeGroupService {
	
	private Logger logger = LoggerFactory.getLogger(DefaultProductTypeGroup.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public int add(ProductTypeGroup productTypeGroup) {
		String sql = "INSERT INTO `product_type_group`(`name`,`status`,`weight`) VALUES(?,?,?);";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql, productTypeGroup.getName(),
					productTypeGroup.getStatus(),
					productTypeGroup.getWeight());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}
	@Override
	public int del(int id) {
		String sql = "DELETE  FROM `product_type_group` WHERE id=?";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql, id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}
	@Override
	public ProductTypeGroup findById(int id) {
		String sql = "SELECT * FROM `product_type_group` WHERE id=?";
		ProductTypeGroup pGroup  = new ProductTypeGroup();
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
			pGroup.setId((int)map.get("id"));
			pGroup.setName((String)map.get("name"));
			pGroup.setStatus((int)map.get("status"));
			pGroup.setWeight((int)map.get("weight"));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return pGroup;
	}
	@Override
	public int update(ProductTypeGroup productTypeGroup) {
		String sql = "UPDATE `product_type_group` SET `name`=?,`status`=?,`weight`=? WHERE `id`=?";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql, productTypeGroup.getName(),
									 productTypeGroup.getStatus(),
									 productTypeGroup.getWeight(),
									 productTypeGroup.getId());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}
	@Override
	public List<ProductTypeGroup> findAll() {
		String sql = " SELECT * FROM `product_type_group` WHERE STATUS = 1";
		JuRowCallbackHandler<ProductTypeGroup> cb = new JuRowCallbackHandler<ProductTypeGroup>(ProductTypeGroup.class);
		try {
			jdbcTemplate.query(sql, cb);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		List<ProductTypeGroup> list = cb.getList();
		return list;
	}
	
	
	
}
