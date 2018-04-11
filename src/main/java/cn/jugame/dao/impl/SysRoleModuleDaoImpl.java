package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysRoleModuleDao;
import cn.jugame.vo.SysRoleModule;
/**
 * 角色模块
 * @author houjt
 *
 */
@Repository("SysRoleModuleDao")
public class SysRoleModuleDaoImpl extends BaseDaoImpl<SysRoleModule> implements SysRoleModuleDao{
	@Override
	public SysRoleModule findById(int id) {
		return (SysRoleModule)getSession().get(SysRoleModule.class , id);
	}
	@Override
	public List<SysRoleModule> findByProperty(String propertyName, Object value) {
		return findByProperty("SysRoleModule", propertyName, value);
	}
 

}
