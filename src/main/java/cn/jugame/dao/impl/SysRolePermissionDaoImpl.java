package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysRolePermissionDao;
import cn.jugame.vo.SysRolePermission;
/**
 * 角色权限
 * @author Administrator
 *
 */
@Repository("SysRolePermissionDao")
public class SysRolePermissionDaoImpl extends BaseDaoImpl<SysRolePermission> implements SysRolePermissionDao{
	@Override
	public SysRolePermission findById(int id) {
		return (SysRolePermission)getSession().get(SysRolePermission.class , id);
	}
	@Override
	public List<SysRolePermission> findByProperty(String propertyName, Object value) {
		return findByProperty("SysRolePermission", propertyName, value);
	}
 

}
