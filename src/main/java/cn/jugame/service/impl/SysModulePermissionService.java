package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysModulePermissionDao;
import cn.jugame.service.ISysModulePermissionService;
import cn.jugame.vo.SysModulePermission;
/**
 * 模块权限服务
 * @author houjt
 *
 */
@Service("SysModulePermissionService")
public class SysModulePermissionService implements ISysModulePermissionService{
	@Autowired
	@Qualifier("SysModulePermissionDao")
	private SysModulePermissionDao sysModulePermissionDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<SysModulePermission> findAll(){
		return sysModulePermissionDao.queryForPage("from SysModulePermission", 0, 10000);
	} 
	@Override
	public void save(SysModulePermission sysModulePermission){ 
		sysModulePermissionDao.insert(sysModulePermission);
	}
	@Override
	public SysModulePermission findById(int pid){
		return sysModulePermissionDao.findById(pid);		
	}
	@Override
	public void update(SysModulePermission sysModulePermission) {
		sysModulePermissionDao.update(sysModulePermission);
	}
	@Override
	public void delete(SysModulePermission sysModulePermission) {
		sysModulePermissionDao.delete(sysModulePermission);
	}
	@Override
	public List<SysModulePermission> findByModuleId(int modId){
		return this.sysModulePermissionDao.findByProperty("modId", modId);
	}
	@Override
	public int delModulePermission(int pid){
		String sql = "delete from sys_module_permission where pid=?";
		int result = this.jdbcTemplate.update(sql, pid);
		return result;
	}
}
