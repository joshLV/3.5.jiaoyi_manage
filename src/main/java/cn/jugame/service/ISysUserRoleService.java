package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysUserRole;
/**
 * 用户角色服务
 * @author houjt
 *
 */
public interface ISysUserRoleService {
	/**
	 * 查询所有
	 * @return
	 */
	List<SysUserRole> list();
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	SysUserRole findById(int id);
	/**
	 * 保存
	 * @param sysUserRole
	 */
	void save(SysUserRole sysUserRole);
	/**
	 * 更新
	 * @param sysUserRole
	 */
	void update(SysUserRole sysUserRole);
	/**
	 * 删除
	 * @param sysUserRole
	 */
	void delete(SysUserRole sysUserRole);
	
	
}
