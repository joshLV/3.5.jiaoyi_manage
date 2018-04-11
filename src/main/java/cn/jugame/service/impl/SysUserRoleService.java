package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysUserRoleDao;
import cn.jugame.service.ISysUserRoleService;
import cn.jugame.vo.SysUserRole;
/**
 * 用户角色服务
 * @author houjt
 *
 */
@Service("SysUserRoleService")
public class SysUserRoleService implements ISysUserRoleService{
	@Autowired
	@Qualifier("SysUserRoleDao")
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public List<SysUserRole> list(){
		return sysUserRoleDao.queryForPage("from SysUserRole", 0, 20);
	} 
	@Override
	public void save(SysUserRole sysUserRole){ 
		sysUserRoleDao.insert(sysUserRole);
	}
	@Override
	public SysUserRole findById(int id){
		return sysUserRoleDao.findById(id);		
	}
	@Override
	public void update(SysUserRole sysUserRole) {
		sysUserRoleDao.update(sysUserRole);
	}
	@Override
	public void delete(SysUserRole sysUserRole) {
		sysUserRoleDao.delete(sysUserRole);
	}
	 
	
}
