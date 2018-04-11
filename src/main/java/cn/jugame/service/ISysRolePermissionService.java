package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysRolePermission;
/**
 * 角色权限服务
 * @author houjt
 *
 */
public interface ISysRolePermissionService {
	/**
	 * 查询所有
	 * @return
	 */
	List<SysRolePermission> findAll();
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	SysRolePermission findById(int id);
	/**
	 * 保存
	 * @param sysRolePermission
	 */
	void save(SysRolePermission sysRolePermission);
	/**
	 * 更新
	 * @param sysRolePermission
	 */
	void update(SysRolePermission sysRolePermission);
	/**
	 * 删除
	 * @param sysRolePermission
	 */
	void delete(SysRolePermission sysRolePermission);
	
	
}
