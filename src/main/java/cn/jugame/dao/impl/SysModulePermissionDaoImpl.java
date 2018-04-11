package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysModulePermissionDao;
import cn.jugame.vo.SysModulePermission;
/**
 * 模块权限
 * @author houjt
 *
 */
@Repository("SysModulePermissionDao")
public class SysModulePermissionDaoImpl extends BaseDaoImpl<SysModulePermission> implements SysModulePermissionDao{
	@Override
	public SysModulePermission findById(int pid) {
		return (SysModulePermission)getSession().get(SysModulePermission.class , pid);
	}
	@Override
	public List<SysModulePermission> findByProperty(String propertyName, Object value) {
		return findByProperty("SysModulePermission", propertyName, value);
	}
 

}
