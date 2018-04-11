package cn.juhaowan.product.dao;


import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.ProductSelfDefined;

/**
 * 商品c2c自定义属性
 * @author Administrator
 * 
 */
public interface ProductSelfDefinedDao extends BaseDao<Object> {

	

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
