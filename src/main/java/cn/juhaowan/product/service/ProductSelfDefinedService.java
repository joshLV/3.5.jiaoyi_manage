package cn.juhaowan.product.service;

import cn.juhaowan.product.vo.ProductSelfDefined;

/**
 * 商品接口信息
 */
public interface ProductSelfDefinedService {

	/**
	 * 商品c2c自定义属性添加
	 * 
	 * @param ProductSelfDefined
	 * @return 0：成功 1：失败
	 */
	int insertSelfDefined(ProductSelfDefined selfDefined);

	/**
	 * 修改商品c2c自定义属性
	 * 
	 * @param ProductSelfDefined
	 * @return 0：成功 1：失败
	 */
	int updateSelfDefined(ProductSelfDefined selfDefined);

	/**
	 * 删除
	 * 
	 * @param productId  商品id
	 * @return 0：成功 1：失败
	 */
	int deleteSelfDefined(String productId);

	/**
	 * 按商品id查询 
	 * 
	 * @param productId  商品id
	 * @return 
	 */
	ProductSelfDefined queryByProductId(String productId);
	/**
	 * 判断是否已经存在
	 * @param productId
	 * @return 
	 */
	boolean isExist(String productId);
}
