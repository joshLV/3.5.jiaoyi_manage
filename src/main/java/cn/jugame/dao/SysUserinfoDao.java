package cn.jugame.dao;

import java.util.List;

import cn.jugame.admin.web.form.SysUserinfoForm;
import cn.jugame.vo.SysUserinfo;
/**
 * 系统用户DAO
 * @author houjt
 *
 */
public interface SysUserinfoDao extends BaseDao{
	
	/**
	 * 超级管理员  0
	 */
	public static int USER_TYPE_SUPER_ADMIN = 0;	
	
	/**
	 * 普通管理员 1
	 */
	public static int USER_TYPE_NORMAL_AMDIN = 1;	
	/**
	 * 根据ID查找
	 * @param userId
	 * @return
	 */
	public SysUserinfo findById(int userId);
	/**
	 * 根据登录名查找
	 * @param loginid
	 * @return
	 */
	public SysUserinfo findByLoginId(String loginid);
	/**
	 * 根据属性查找
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<SysUserinfo> findByProperty(String propertyName, Object value);
	
	
}
