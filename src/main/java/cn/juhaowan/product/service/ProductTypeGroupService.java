package cn.juhaowan.product.service;

import java.util.List;

import cn.juhaowan.product.vo.ProductTypeGroup;

public interface ProductTypeGroupService {
	
	/**
	 * 添加一条记录
	 * @param productTypeGroup
	 * @return
	 */
	int add(ProductTypeGroup productTypeGroup);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	int del(int id);
	
	/**
	 * 根据ID查出一条记录
	 * @param id
	 * @return
	 */
	ProductTypeGroup findById(int id);
	
	/**
	 * 更新一条记录
	 * @param productTypeGroup
	 * @return
	 */
	int update(ProductTypeGroup productTypeGroup);
	
	/**
	 * 查出所有的商品类型一级分类
	 * @return
	 */
	List<ProductTypeGroup> findAll();
}
