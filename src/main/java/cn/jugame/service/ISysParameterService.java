package cn.jugame.service;

import java.util.List;

import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysParameter;
/**
 * 系统参数服务
 * @author houjt
 *
 */
public interface ISysParameterService {
	/**
	 * 查询列表
	 * @return
	 */
	List<SysParameter> list();
	/**
	 * 分页查询
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<SysParameter> queryParameterList(SysParameter condition,int pageSize,int pageNo,String sort,String order);
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	SysParameter findById(int id);
	/**
	 * 保存
	 * @param sysParameter
	 */
	void save(SysParameter sysParameter);
	/**
	 * 更新的
	 * @param sysParameter
	 */
	void update(SysParameter sysParameter);
	/**
	 * 删除
	 * @param sysParameter
	 */
	void delete(SysParameter sysParameter);
	/**
	 * 根据paraCode查询
	 * @param paraCode
	 * @return
	 */
	List<SysParameter> listByParaCode(String paraCode);
	
}
