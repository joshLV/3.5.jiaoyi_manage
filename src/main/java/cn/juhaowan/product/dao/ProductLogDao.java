package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;

import cn.juhaowan.product.vo.ProductLog;
import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;

/**
 * 
 * @author Administrator
 * 
 */
public interface ProductLogDao extends BaseDao<ProductLog> {

	/**
	 * 按id查找日志
	 * 
	 * @param id
	 *            日志id
	 * @return 日志实体
	 */
	public ProductLog findById(int id);

	/**
	 * 按条件查询日志列表
	 * 
	 * @param propertyName
	 *            条件字段
	 * @param value
	 *            条件内容
	 * @return 日志列表
	 */
	public List<ProductLog> findByProperty(String propertyName, Object value);

	/**
	 * 添加日志
	 * 
	 * @param productId
	 *            商品id
	 * @param operateType
	 *            操作类型
	 * @param userid
	 *            用户uid
	 * @param discretion
	 *            描述
	 * @param remark
	 *            备注
	 * @return 0 成功 1 失败
	 * @pdOid 6c61b743-8111-4396-88f8-69550fc27e56
	 */
	int addProductLog(String productId, int operateType, int userid,
			String discretion, String remark);

	/**
	 * 按条件查询日志列表
	 * 
	 * @param conditions
	 *            条件
	 * @param pageNo
	 *            首页
	 * @param pageSize
	 *            每页条数
	 * @param sort
	 *            排序字段
	 * @param order
	 *            ASC/DESC
	 * @return 分页
	 * @pdOid 60547cf5-3f91-485c-bbd9-05651bfd656d
	 */
	PageInfo<ProductLog> queryProductLog(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 查询日志总数量
	 * 
	 * @param uid
	 *            用户id
	 * @param sqlCondition
	 *            sql语句
	 * @param sqlParam
	 *            条件
	 * @return 页数
	 */
	public int queryLogCount(int uid, String sqlCondition, Object... sqlParam);

	/**
	 * 查询日志列表
	 * 
	 * @param uid
	 *            用户uid
	 * @param sqlCondition
	 *            aql语句
	 * @param fistResult
	 *            开始页数
	 * @param maxResult
	 *            每页最大值
	 * @param sqlParam
	 *            条件
	 * @return 日志列表
	 */
	public List<ProductLog> queryLogList(int uid, String sqlCondition,
			int fistResult, int maxResult, Object... sqlParam);

}
