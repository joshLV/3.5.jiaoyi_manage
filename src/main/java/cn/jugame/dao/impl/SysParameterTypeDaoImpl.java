package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysParameterTypeDao;
import cn.jugame.vo.SysParameterType;
/**
 * 参数类型
 * @author houjt
 *
 */
@Repository("SysParameterTypeDao")
public class SysParameterTypeDaoImpl extends BaseDaoImpl<SysParameterType> implements SysParameterTypeDao{
	@Override
	public SysParameterType findById(int id) {
		return (SysParameterType)getSession().get(SysParameterType.class , id);
	}
	@Override
	public List<SysParameterType> findByProperty(String propertyName, Object value) {
		return findByProperty("SysParameterType", propertyName, value);
	}
 

}
