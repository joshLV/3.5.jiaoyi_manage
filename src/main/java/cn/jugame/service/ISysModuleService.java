package cn.jugame.service;

import java.util.List;

import cn.jugame.vo.SysModule;
/**
 * 模块服务
 * @author Administrator
 *
 */
public interface ISysModuleService {
	/**
	 * 读取父模块 
	 * @return
	 */
	List<SysModule> findParentModule();
	/**
	 * 查询所有模块
	 * @return
	 */
	List<SysModule> findAll();
	/**
	 * 根据ID查找
	 * @param modId
	 * @return
	 */
	SysModule findById(int modId);
	/**
	 * 保存
	 * @param sysModule
	 */
	void save(SysModule sysModule);
	/**
	 * 更新
	 * @param sysModule
	 */
	void update(SysModule sysModule);
	/**
	 * 删除
	 * @param sysModule
	 */
	void delete(SysModule sysModule);
	/**
	 * 根据ID删除
	 * @param modId
	 */
	int delete(int modId,int parentId);
	/**
	 * 查找模块表最大自增id
	 * @return
	 */
	int queryMaxId();
}
