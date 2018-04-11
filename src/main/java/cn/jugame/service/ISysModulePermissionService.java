package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysModulePermission;
/**
 * 模块权限服务
 * @author houjt
 *
 */
public interface ISysModulePermissionService {
	/**
	 * 查询所有
	 * @return
	 */
	List<SysModulePermission> findAll();
	/**
	 * 根据ID查找
	 * @param pid
	 * @return
	 */
	SysModulePermission findById(int pid);
	/**
	 * 保存模块权限
	 * @param sysModulePermission
	 */
	void save(SysModulePermission sysModulePermission);
	/**
	 * 更新模块权限
	 * @param sysModulePermission
	 */
	void update(SysModulePermission sysModulePermission);
	/**
	 * 删除模块权限
	 * @param sysModulePermission
	 */
	void delete(SysModulePermission sysModulePermission);
	/**
	 * 根据模块ID，查找相关权限集合
	 * @param modId
	 * @return
	 */
	List<SysModulePermission> findByModuleId(int modId);
	/**
	 * 根据PID删除
	 * @param pid
	 * @return
	 */
	int delModulePermission(int pid);
}
