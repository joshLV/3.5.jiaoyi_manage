package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysRolePermissionDao;
import cn.jugame.service.ISysRolePermissionService;
import cn.jugame.vo.SysRolePermission;
/**
 * 角色权限服务
 * @author Administrator
 *
 */
@Service("SysRolePermissionService")
public class SysRolePermissionService implements ISysRolePermissionService{
	@Autowired
	@Qualifier("SysRolePermissionDao")
	private SysRolePermissionDao sysRolePermissionDao;
	@Override
	public List<SysRolePermission> findAll(){
		return sysRolePermissionDao.queryForPage("from SysRolePermission", 0, 20000);
	} 
	@Override
	public void save(SysRolePermission sysRolePermission){ 
		sysRolePermissionDao.insert(sysRolePermission);
	}
	@Override
	public SysRolePermission findById(int id){
		return sysRolePermissionDao.findById(id);		
	}
	@Override
	public void update(SysRolePermission sysRolePermission) {
		sysRolePermissionDao.update(sysRolePermission);
	}
	@Override
	public void delete(SysRolePermission sysRolePermission) {
		sysRolePermissionDao.delete(sysRolePermission);
	}
	 
	
}
