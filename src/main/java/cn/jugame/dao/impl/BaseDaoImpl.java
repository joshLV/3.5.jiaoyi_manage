package cn.jugame.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.jugame.dao.BaseDao;
/**
 * 基础DAO
 * @author Administrator
 *
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory; 

    public Session getSession() {
        //事务必须是开启的(Required)，否则获取不到
        return sessionFactory.getCurrentSession();
    }
	/**
	 * 删除实体
	 */
	public void delete(T modle) {
		try {
			getSession().delete(modle);
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		}
	}
	/**
	 * 插入实体
	 */
	public void insert(T modle) {
		try {
			getSession().saveOrUpdate(modle);
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		}
	}
	/**
	 * 更新
	 */
	public void update(T modle) {
		try {
			getSession().saveOrUpdate(modle);
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 根据属性查找表的数据
	 * 
	 * @param modelName  	对象名
	 * @param propertyName 属性名
	 * @param value 		属性值
	 * @return
	 */
	public List<T> findByProperty(String modelName, String propertyName,
			Object value) {
		String queryString = "from " + modelName + " as model where model."
				+ propertyName + "= ?";
		return this.queryForPage(queryString, 0, 5000, value);
		//return getSession().find(queryString, value);
	}
	
	
	/**
	 * 分页查询
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryForPage(final String hql,final int firstResult, final int maxResult,final Object... paramlist) {
		Query query = getSession().createQuery(hql);		
		this.setParameters(query, paramlist);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		List<T> list = query.list();
		return list; 
	}
	/**
	 * 获取多少行
	 */
	public int getRowCount(String hql, final Object... paramlist) {
		// 直接用.size()方法，会有性能问题(如果数据量大）
		// return getHibernateTemplate().find(hql,value).size();
		String hql2 = hql.toString();
		if (!hql2.toLowerCase().startsWith("select")) {
			hql2 = "select count(*) " + hql;
		}
		if (hql2.toLowerCase().contains("order by")) {
			hql2 = hql2.substring(0, hql2.toLowerCase().indexOf("order by"));
		}
		Query query = this.getSession().createQuery(hql2);
		this.setParameters(query, paramlist);

		return ((Long) query.iterate().next()).intValue();
	}
	
	/**
	 * 给查询设置参数
	 * @param query
	 * @param paramlist
	 */
	protected void setParameters(Query query, Object[] paramlist) {
        if (paramlist != null) {
            for (int i = 0; i < paramlist.length; i++) {
                if(paramlist[i] instanceof Date) {
                    query.setTimestamp(i, (Date)paramlist[i]);
                } else {
                    query.setParameter(i, paramlist[i]);
                }
            }
        }
    }

}
