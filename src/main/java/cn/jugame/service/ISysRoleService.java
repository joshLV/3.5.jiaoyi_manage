package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysRole;
import cn.jugame.vo.SysViewRolePermission;
/**
 * 角色服务
 * @author houjt
 *
 */
public interface ISysRoleService {
	/**
	 * 查询所有
	 * @return
	 */
	List<SysRole> findAll();
	/**
	 * 查询所有可效角色
	 * @return
	 */
    List<SysRole> findAllValid();
    /**
     * 根据ID查询
     * @param roleId
     * @return
     */
	SysRole findById(int roleId);
	/**
	 * 保存
	 * @param sysRole
	 * @return
	 */
	public int insert(SysRole sysRole);
	
	/**
	 * 修改角色的权限(会删除旧有权限） 
	 * @param roleid
	 * @param pers  模块(如mod_1000)，权限id(如：per_1000)，以逗号分开
	 * @return
	 */
	public int updateRolePermission(int roleid,String pers);
	/**
	 * 更新
	 * @param sysRole
	 */
	void update(SysRole sysRole);
	/**
	 * 删除实体
	 * @param sysRole
	 */
	void delete(SysRole sysRole);
	/**
	 * 根据ID删除
	 * @param roleid
	 */
	void delete(int roleid);
	
	/**
	 * 查询角色权限
	 * @param roleid
	 * @return
	 */
	List<SysViewRolePermission> findRolePermissions(int roleid);
	
	
	/**
	 * 查询有效客服角色
	 * @return
	 */
    List<SysRole> findValidCustomer();
	
}
