package cn.jugame.service;

import java.util.List;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.vo.SysViewRoleModule;
import cn.jugame.vo.SysViewRolePermission;
import cn.jugame.vo.SysViewUserRole;

/**
 * 用户信息服务
 * 
 * @author houjt
 * 
 */
public interface ISysUserinfoService {
	/**
	 * 根据ID查找
	 * 
	 * @param userId
	 * @return
	 */
	SysUserinfo findById(int userId);

	/**
	 * 添加新用户
	 * 
	 * @param userinfo
	 * @return 1 用户已存在，0成功
	 */
	int insert(SysUserinfo sysUserinfo);

	/**
	 * 更新
	 * 
	 * @param sysUserinfo
	 */
	void update(SysUserinfo sysUserinfo);

	/**
	 * 删除
	 * 
	 * @param userId
	 */
	void delete(int userId);

	/**
	 * 更新用户所属于的角色
	 * 
	 * @param userid
	 * @param roleids
	 */
	public void updateUserRole(int userid, int[] roleids);

	/**
	 * 登录 ,会验证密码是否正确
	 * 
	 * @param loginid
	 * @param password
	 * @return
	 */
	public SysUserinfo login(String loginid, String password);

	/**
	 * 查询用户所属于角色
	 * 
	 * @param loginid
	 * @return
	 */
	public List<SysViewUserRole> findUserRole(String loginid);

	/**
	 * 查询角色的权限
	 * 
	 * @param roleList
	 * @return
	 */
	public List<SysViewRolePermission> findRolePermission(List<SysViewUserRole> roleList);

	/**
	 * 查询角色的模块
	 * 
	 * @param roleList
	 * @return
	 */
	public List<SysViewRoleModule> findRoleModule(List<SysViewUserRole> roleList);

	/**
	 * 超级管理员的模块
	 * 
	 * @return
	 */
	public List<SysModule> findAdminModule();

	/**
	 * 分页查询
	 * 
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	public PageInfo<SysUserinfo> queryUserinfoList(SysUserinfo condition, int pageSize, int pageNo, String sort,
			String order,boolean isCustomer);

	/**
	 * 修改密码
	 * 
	 * @param userid
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public int updatePwd(int userid, String oldpwd, String newpwd);

	/**
	 * 用户登录 修改登录次数
	 */
	public int updateLoginCount(int userid);

	/**
	 * 登录 ,增加手机号码登录
	 * 
	 * @param loginid
	 * @param password
	 * @return
	 */
	public SysUserinfo phonelogin(String phone, String password);

	/**
	 * 验证手机号码是否已经存在
	 */
	public boolean telExist(String phone);

	/**
	 * 根据登录名查找登录用户
	 * 
	 * @param loginid
	 * @return
	 */
	public SysUserinfo findByLoginId(String loginid);

	/**
	 * 生成客服岗位号＝部门编号+岗位编号+3位序号
	 */
	public String createCustomerServiceId();
//
//	/**
//	 * 客服人员信息PageInfo查询
//	 * @param condition MayKey: customerType(客服类型：0: 客服,1: 物服,2: 审核员), isOnDuty(是否上班）,like fullname(姓名),like postNo(岗位号)
//	 * @param pageSize
//	 * @param pageNo
//	 * @param sort
//	 * @param order
//	 * @return
//	 */
//	PageInfo<SysUserinfoForm> queryCustomerPageInfo(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order);
//	
	/**
	 * 根据qq查找登录用户
	 * 
	 * @param loginid
	 * @return
	 */
	public SysUserinfo findByqq(String qq);
	
	/**
	 * 更新用户头像
	 * @param uid
	 * @param img
	 * @return
	 */
	int updateHeadImg(String uid,String img);

	/**
	 * 导入用户
	 * @param list
	 * @param kefuitem
	 * @return
	 */
	public int saveUser(List<String[]> list,String kefuitem,int roleid);
}
