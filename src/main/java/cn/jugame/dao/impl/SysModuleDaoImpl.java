package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysModuleDao;
import cn.jugame.vo.SysModule;
/**
 * 系统模块
 * @author houjt
 *
 */
@Repository("SysModuleDao")
public class SysModuleDaoImpl extends BaseDaoImpl<SysModule> implements SysModuleDao{
	@Override
	public SysModule findById(int modId) {
		return (SysModule)getSession().get(SysModule.class , modId);
	}
	@Override
	public List<SysModule> findAll() {
		try {
			String queryString = "from SysModule order by orderNo asc";
			List<SysModule> list = this.queryForPage(queryString, 0, 5000, null);
			return  list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	@Override
	public List<SysModule> findByProperty(String propertyName, Object value) {
		return findByProperty("SysModule", propertyName, value);
	}
 

}
