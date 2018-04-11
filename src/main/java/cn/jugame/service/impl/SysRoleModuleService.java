package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysRoleModuleDao;
import cn.jugame.service.ISysRoleModuleService;
import cn.jugame.vo.SysRoleModule;
/**
 * 角色模块服务
 * @author Administrator
 *
 */
@Service("SysRoleModuleService")
public class SysRoleModuleService implements ISysRoleModuleService{
	@Autowired
	@Qualifier("SysRoleModuleDao")
	private SysRoleModuleDao sysRoleModuleDao;
	@Override
	public List<SysRoleModule> list(){
		return sysRoleModuleDao.queryForPage("from SysRoleModule", 0, 20);
	} 
	@Override
	public void save(SysRoleModule sysRoleModule){ 
		sysRoleModuleDao.insert(sysRoleModule);
	}
	@Override
	public SysRoleModule findById(int id){
		return sysRoleModuleDao.findById(id);		
	}
	@Override
	public void update(SysRoleModule sysRoleModule) {
		sysRoleModuleDao.update(sysRoleModule);
	}
	@Override
	public void delete(SysRoleModule sysRoleModule) {
		sysRoleModuleDao.delete(sysRoleModule);
	}
	 
	
}
