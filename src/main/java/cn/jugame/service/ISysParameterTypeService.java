package cn.jugame.service;

import java.util.List;

import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysParameterType;
/**
 * 系统参数类型服务
 * @author houjt
 *
 */
public interface ISysParameterTypeService {
	/**
	 * 查询列表
	 * @return
	 */
	List<SysParameterType> list();
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	SysParameterType findById(int id);
	/**
	 * 保存
	 * @param sysParameterType
	 */
	void save(SysParameterType sysParameterType);
	/**
	 * 更新
	 * @param sysParameterType
	 */
	void update(SysParameterType sysParameterType);
	/**
	 * 删除
	 * @param sysParameterType
	 */
	void delete(SysParameterType sysParameterType);
	/**
	 * 分页查询
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<SysParameterType> queryParameterTypeList(SysParameterType condition,int pageSize,int pageNo,String sort,String order);
}
