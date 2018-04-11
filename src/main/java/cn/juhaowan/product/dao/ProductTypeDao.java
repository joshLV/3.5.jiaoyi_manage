package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;
import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.ProductType;

/**
 * 
 * @author Administrator
 * 
 */
public interface ProductTypeDao extends BaseDao<ProductType> {
	/**
	 * 按id查询商品分类
	 * 
	 * @param id
	 *            编号
	 * @return 商品分类
	 */
	public ProductType findById(int id);

	/**
	 * 按条件查询商品分类列表
	 * 
	 * @param propertyName
	 *            条件字段
	 * @param value
	 *            条件内容
	 * @return 分类列表
	 */
	public List<ProductType> findByProperty(String propertyName, Object value);

	/**
	 * 添加
	 * 
	 * @param productType
	 *            商品分类实体
	 * @return 0 成功 1 失败
	 */
	int insertProductType(ProductType productType);

	/**
	 * 修改
	 * 
	 * @param productType
	 *            商品类型实体
	 * @return 0 成功 1 失败
	 */
	int updateProductType(ProductType productType);

	/**
	 * 删除
	 * 
	 * @param id
	 *            编号
	 * @return 0 成功 1 失败
	 */
	int deleteProductType(int id);

	/**
	 * 根据id 查询商品类型
	 * 
	 * @param id
	 *            编号
	 * @return 商品类型名称
	 */
	public String findProductTypeByTypeid(int id);

	/**
	 * 按条件查询日志列表
	 * 
	 * @param conditions
	 *            条件
	 * @param pageNo
	 *            首页
	 * @param pageSize
	 *            条数
	 * @param sort
	 *            排序字段
	 * @param order
	 *            ASC/DESC
	 * @return 分页
	 * @pdOid 60547cf5-3f91-485c-bbd9-05651bfd656d
	 */
	PageInfo<ProductType> queryProductType(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 按id查询商品类型实体
	 * 
	 * @param id
	 *            编号
	 * @return 商品分类实体
	 */
	public ProductType findbyId(int id);

	/**
	 * 商品分类列表(带缓存)
	 * 
	 * @return 商品分类列表
	 */
	List<ProductType> queryProductTypeList();
	
	/**
	 * 查看id小于100的商品类型
	 * @return
	 */
	List<ProductType> queryProductTypes();
	
}
