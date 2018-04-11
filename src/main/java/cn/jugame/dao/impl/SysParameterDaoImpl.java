package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysParameterDao;
import cn.jugame.vo.SysParameter;
/**
 * 系统参数
 * @author Administrator
 *
 */
@Repository("SysParameterDao")
public class SysParameterDaoImpl extends BaseDaoImpl<SysParameter> implements SysParameterDao{
	@Override
	public SysParameter findById(int id) {
		return (SysParameter)getSession().get(SysParameter.class , id);
	}
	@Override
	public List<SysParameter> findByProperty(String propertyName, Object value) {
		return findByProperty("SysParameter", propertyName, value);
	}
 

}
