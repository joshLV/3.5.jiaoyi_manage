package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysRoleDao;
import cn.jugame.vo.SysRole;
/**
 * 角色
 * @author houjt
 *
 */
@Repository("SysRoleDao")
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao{
	@Override
	public SysRole findById(int roleId) {
		return (SysRole)getSession().get(SysRole.class , roleId);
	}
	@Override
	public List<SysRole> findByProperty(String propertyName, Object value) {
		return findByProperty("SysRole", propertyName, value);
	}
	@Override
	public List<SysRole> findCustomerByProperty(String propertyName,
			Object value) {
		String queryString = "from SysRole as model where model."
				+ propertyName + "= ? and model.isCustomerR=1";
		return this.queryForPage(queryString, 0, 5000, value);
	}
 

}
