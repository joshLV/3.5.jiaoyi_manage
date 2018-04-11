package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysViewUserRoleDao;
import cn.jugame.vo.SysViewUserRole;

/**
 * 用户角色视图
 * @author Administrator
 *
 */
@Repository("SysViewUserRoleDao")
public class SysViewUserRoleDaoImpl extends BaseDaoImpl<SysViewUserRole> implements SysViewUserRoleDao{

	@Override
	public void delete(SysViewUserRole modle) {
		return;
	}

	@Override
	public void insert(SysViewUserRole modle) {
		return;
	}

	@Override
	public void update(SysViewUserRole modle) {
		return;
	}
	@Override
	public List<SysViewUserRole> findByLoginId(String loginid) {
		return findByProperty("SysViewUserRole", "loginid", loginid);
	}
	
	
}
