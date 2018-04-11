package cn.jugame.dao;

import java.util.List;

import cn.jugame.vo.SysViewUserRole;
/**
 * 用户角色视图
 * @author Administrator
 *
 */
public interface SysViewUserRoleDao extends BaseDao<SysViewUserRole>{
	/**
	 * 根据登录ID查询
	 * @param loginid
	 * @return
	 */
	public List<SysViewUserRole> findByLoginId(String loginid);
	
}
