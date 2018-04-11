package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductLogDao;
import cn.juhaowan.product.vo.ProductLog;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("ProductLog")
public class ProductLogDaoImpl extends BaseDaoImplJdbc<ProductLog> implements
		ProductLogDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	/**
	 * 分表 失效
	 * @return 表名称
	 */
	public String getTableName() {
		// 分表
		// String tmp = id+"";
		// String tableExt = tmp.substring(tmp.length() - 1 );
		// return "product_log" + tableExt;
		return "product_log";
	}

	/**
	 * 按id查找商品日志
	 * 
	 * @param id
	 *            编号
	 * @return 商品日志
	 */
	public ProductLog findById(int id) {
		return (ProductLog) findById(ProductLog.class, id);
	}

	/**
	 * 根据条件查询商品日志列表
	 * 
	 * @param propertyName
	 *            字段
	 * @param value
	 *            字段内容
	 * @return 商品日志列表
	 */
	public List<ProductLog> findByProperty(String propertyName, Object value) {
		return findByProperty(ProductLog.class, propertyName, value);
	}

	@Override
	public int addProductLog(String productId, int operateType, int userid,
			String discretion, String remark) {
		if (null == productId) {
			return 1;
		}
		try {
			ProductLog plog = new ProductLog();
			plog.setCreateTime(new Date());
			plog.setOperateType(operateType);
			plog.setOperateUserid(userid);
			plog.setProductId(productId);
			plog.setDescription(discretion);
			plog.setRemark(remark);
			insert(plog);
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<ProductLog> queryProductLog(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		int count = 0;
		int firstResult = 1;
		String sqlCount = null;
		if (null == sort) {
			sort = "id";
		}
		if (null == order) {
			order = "desc";
		}
		sql.append(" from product_log where 1 = 1");
		if (null != conditions) {

			if (null != conditions.get("product_id")) {
				sql.append(" and product_id = ? ");
				conList.add(conditions.get("product_id"));
			}
			if (null != conditions.get("operate_userid")) {
				sql.append(" and operate_userid = ? ");
				conList.add(conditions.get("operate_userid"));
			}
			if (null != conditions.get("operate_type")) {
				sql.append(" and operate_type = ? ");
				conList.add(conditions.get("operate_type"));
			}

		}

		sql.append(" order by " + sort + " " + order);

		sqlCount = "select count(*) " + sql.toString();

		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<ProductLog> pageinfo = new PageInfo<ProductLog>(count,pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}

		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<ProductLog> cb = new JuRowCallbackHandler<ProductLog>(ProductLog.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int queryLogCount(int uid, String sqlCondition, Object... sqlParam) {
		String sql = "select count(*) from " + getTableName() + " where " + sqlCondition;

		int i = jdbcOperations.queryForInt(sql);
		return i;
	}

	@Override
	public List<ProductLog> queryLogList(int uid, String sqlCondition,
			int fistResult, int maxResult, Object... sqlParam) {
		String sql = "select * from " + getTableName() + " where "
				+ sqlCondition + " order by id desc limit " + fistResult + "," + maxResult;
		JuRowCallbackHandler<ProductLog> callback = new JuRowCallbackHandler<ProductLog>(ProductLog.class);
		jdbcOperations.query(sql, callback, sqlParam);
		return callback.getList();
	}

}
