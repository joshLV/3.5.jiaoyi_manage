package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysRoleModule;
/**
 * 角色模块服务
 * @author Administrator
 *
 */
public interface ISysRoleModuleService {
	/**
	 * 查询列表
	 * @return
	 */
	List<SysRoleModule> list();
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	SysRoleModule findById(int id);
	/**
	 * 保存
	 * @param sysRoleModule
	 */
	void save(SysRoleModule sysRoleModule);
	/**
	 * 更新
	 * @param sysRoleModule
	 */
	void update(SysRoleModule sysRoleModule);
	/**
	 * 删除
	 * @param sysRoleModule
	 */
	void delete(SysRoleModule sysRoleModule);
	
	
}
