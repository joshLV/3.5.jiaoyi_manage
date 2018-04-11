package cn.jugame.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysRoleDao;
import cn.jugame.dao.SysViewRolePermissionDao;
import cn.jugame.service.ISysRoleService;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysRole;
import cn.jugame.vo.SysViewRolePermission;
import cn.jugame.dao.SysModuleDao;
import cn.jugame.dao.SysRoleModuleDao;
/**
 * 角色服务
 * @author houjt
 *
 */
@Service("SysRoleService")
public class SysRoleService implements ISysRoleService{
	@Autowired
	@Qualifier("SysRoleDao")
	private SysRoleDao sysRoleDao;
	
	
	@Autowired
	@Qualifier("SysModuleDao")
	private SysModuleDao sysModuleDao;
	

	
	@Autowired
	private SysViewRolePermissionDao sysViewRolePermissionDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	Logger logger = LoggerFactory.getLogger(SysRoleService.class);
	@Override
	public List<SysRole> findAll(){
		return sysRoleDao.queryForPage("from SysRole", 0, 5000);
		
	} 
	@Override
	public int insert(SysRole sysRole){
		int c = sysRoleDao.findByProperty("SysRole", "roleCode", sysRole.getRoleCode()).size();
		if (c > 0){
			return 1;
		}
		int n = sysRoleDao.findByProperty("SysRole", "roleName", sysRole.getRoleName()).size();
		if (n > 0){
			return 2;
		}
		sysRole.setCreateTime(new Date());
		sysRoleDao.insert(sysRole);
		return 0;
	}
	@Override
	public int updateRolePermission(int roleid,String pers){
		try{
			jdbcTemplate.execute("delete from sys_role_module where role_id = " + roleid);
			jdbcTemplate.execute("delete from sys_role_permission where role_id = " + roleid);
			String[] perArr = pers.split(",");
			//
		    int result = 0;
			for (int i = 0; i < perArr.length; i++) {
				String id = perArr[i].substring(4);
				String sql = null;
				if (perArr[i].startsWith("mod_")){
					//父节点id
					SysModule module = sysModuleDao.findById(Integer.parseInt(id));
					//角色权限表 添加不重复的父节点id
					String rolesql = "SELECT count(ID) FROM `sys_role_module` WHERE ROLE_ID= ? AND MOD_ID= ?";
					SqlRowSet r = jdbcTemplate.queryForRowSet(rolesql, roleid,module.getParentId());
					while(r.next()){
						result = r.getInt(1);
					}
					if(result == 0){
						String persql = "insert into sys_role_module(role_id,mod_id) values(?,?)";
						jdbcTemplate.update(persql,roleid,module.getParentId());
					}
					sql = "insert into sys_role_module(role_id,mod_id) values('" + roleid + "','" + id + "')";
				}else{
					sql = "insert into sys_role_permission(role_id,pid) values('" + roleid + "','" + id + "')";
				}
				jdbcTemplate.execute(sql);
			}
		}catch(Exception e){
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	@Override
	public SysRole findById(int roleId){
		return sysRoleDao.findById(roleId);		
	}
	@Override
	public void update(SysRole sysRole) {
		sysRoleDao.update(sysRole);
	}
	@Override
	public void delete(SysRole sysRole) {
		//删除模块 ，权限信息
		jdbcTemplate.execute("delete from sys_role_module where role_id = " + sysRole.getRoleId());
		jdbcTemplate.execute("delete from sys_role_permission where role_id = " + sysRole.getRoleId());
		sysRoleDao.delete(sysRole);
	}
	@Override
	public void delete(int roleid){
		//删除模块 ，权限信息
		jdbcTemplate.execute("delete from sys_role_module where role_id = " + roleid);
		jdbcTemplate.execute("delete from sys_role_permission where role_id = " + roleid);
		jdbcTemplate.execute("delete from sys_role where role_id = " + roleid);
	}
	@Override
	public List<SysViewRolePermission> findRolePermissions(int roleid){
		return sysViewRolePermissionDao.findByProperty("SysViewRolePermission", "roleId", roleid);
				
	}
	//查询有效角色列表 过滤无效角色（新添加）
	@Override
	public List<SysRole> findAllValid(){
		return sysRoleDao.findByProperty("status", 1);
	}
	@Override
	public List<SysRole> findValidCustomer() {
		return sysRoleDao.findCustomerByProperty("status", 1);
	} 
	
}
