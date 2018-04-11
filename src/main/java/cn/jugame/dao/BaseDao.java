package cn.jugame.dao;

import java.util.List;
/**
 * 基础DAO
 * @author houjt
 *
 * @param <T>
 */
public interface BaseDao<T> {
	/**
	 * 插入实体
	 * @param modle
	 */
	public void insert(T modle); 
	/**
	 * 更新实体
	 * @param modle
	 */
	public void update(T modle); 
	/**
	 * 删除实体
	 * @param modle
	 */
	public void delete(T modle); 
	
	/**
	 * 根据属性查找表的数据
	 * @param modelName 	对象名
	 * @param propertyName  属性名
	 * @param value			属性值
	 * @return
	 */
	public List<T> findByProperty(String modelName,String propertyName, Object value);
	/**
	  * 分页查询
	  */
	public List<T> queryForPage(final String hql,final int firstResult, final int maxResult,final Object... paramlist);
	
	/**
	  *查询总记录数
	  */
	public int getRowCount(String hql, final Object... paramlist);
}
