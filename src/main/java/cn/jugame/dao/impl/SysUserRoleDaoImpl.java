package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysUserRoleDao;
import cn.jugame.vo.SysUserRole;
/**
 * 用户角色
 * @author houjt
 *
 */
@Repository("SysUserRoleDao")
public class SysUserRoleDaoImpl extends BaseDaoImpl<SysUserRole> implements SysUserRoleDao{
	@Override
	public SysUserRole findById(int id) {
		return (SysUserRole)getSession().get(SysUserRole.class , id);
	}
	@Override
	public List<SysUserRole> findByProperty(String propertyName, Object value) {
		return findByProperty("SysUserRole", propertyName, value);
	}
 

}
