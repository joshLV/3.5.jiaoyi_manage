package cn.jugame.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.SysUserinfoDao;
import cn.jugame.vo.SysUserinfo;
/**
 * 用户信息
 * @author houjt
 *
 */
@Repository("SysUserinfoDao")
public class SysUserinfoDaoImpl extends BaseDaoImpl implements SysUserinfoDao{
	@Override
	public SysUserinfo findById(int userId) {
		return (SysUserinfo)getSession().get(SysUserinfo.class , userId);
	}
	@Override
	public List<SysUserinfo> findByProperty(String propertyName, Object value) {
		return findByProperty("SysUserinfo", propertyName, value);
	}
	@Override
	public SysUserinfo findByLoginId(String loginid) {
		List<SysUserinfo> list = findByProperty("SysUserinfo", "loginid", loginid);
		if (list.size() > 0){
			return list.get(0);
		} 
		return null;
	}
	

}
