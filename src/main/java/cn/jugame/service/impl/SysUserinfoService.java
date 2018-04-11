package cn.jugame.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.admin.web.form.SysUserinfoForm;
import cn.jugame.dao.SysModuleDao;
import cn.jugame.dao.SysUserinfoDao;
import cn.jugame.dao.SysViewRoleModuleDao;
import cn.jugame.dao.SysViewRolePermissionDao;
import cn.jugame.dao.SysViewUserRoleDao;
import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.MD5;
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
@Service("SysUserinfoService")
public class SysUserinfoService implements ISysUserinfoService {
	@Autowired
	@Qualifier("SysUserinfoDao")
	private SysUserinfoDao sysUserinfoDao;

	@Autowired
	@Qualifier("SysViewUserRoleDao")
	private SysViewUserRoleDao sysViewUserRoleDao;

	@Autowired
	@Qualifier("SysViewRolePermissionDao")
	private SysViewRolePermissionDao sysViewRolePermissionDao;

	@Autowired
	@Qualifier("SysViewRoleModuleDao")
	private SysViewRoleModuleDao sysViewRoleModuleDao;

	@Autowired
	@Qualifier("SysModuleDao")
	private SysModuleDao sysModuleDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory; 

	@Override
	public void delete(int userId) {
		SysUserinfo userinfo = sysUserinfoDao.findById(userId);
		if (userinfo != null) {
			sysUserinfoDao.delete(userinfo);
			try {
				// 删除角色信息
				jdbcTemplate.update(
						"delete from SYS_USER_ROLE where user_id = ?", userId);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 登录 ,会验证密码是否正确
	 * 
	 * @param loginid
	 * @param password
	 * @return
	 */
	public SysUserinfo login(String loginid, String password) {
		SysUserinfo userinfo = sysUserinfoDao.findByLoginId(loginid);
		if (userinfo == null)
			return null;

		String pwdMd5 = MD5.encode(password);
		if (userinfo.getUserPassword().equals(pwdMd5)) {
			// 保存登录时间，登录次数加1
			userinfo.setLastLoginTime(new Date());
			userinfo.setLoginTime(userinfo.getLoginTime() + 1);
			sysUserinfoDao.update(userinfo);

			return userinfo;
		}
		// 密码不正确
		return null;
	}

	/**
	 * 查询用户所属于角色
	 * 
	 * @param loginid
	 * @return
	 */
	public List<SysViewUserRole> findUserRole(String loginid) {
		return sysViewUserRoleDao.findByLoginId(loginid);
	}

	/**
	 * 查询角色的权限
	 * 
	 * @param roleList
	 * @return
	 */
	public List<SysViewRolePermission> findRolePermission(
			List<SysViewUserRole> roleList) {

		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < roleList.size(); i++) {
			SysViewUserRole sysViewUserRole = roleList.get(i);
			if (i > 0)
				ids.append(",");
			ids.append(sysViewUserRole.getRoleId());
		}
		String hql = "from SysViewRolePermission where roleId in ("
				+ ids.toString() + ")";

		List<SysViewRolePermission> list = sysViewRolePermissionDao
				.queryForPage(hql, 0, 10000);

		return list;
	}

	/**
	 * 查询角色的模块
	 * 
	 * @param roleList
	 * @return
	 */
	public List<SysViewRoleModule> findRoleModule(List<SysViewUserRole> roleList) {
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < roleList.size(); i++) {
			SysViewUserRole sysViewUserRole = roleList.get(i);
			if (i > 0)
				ids.append(",");
			ids.append(sysViewUserRole.getRoleId());
		}
		String hql = "from SysViewRoleModule where roleId in ("
				+ ids.toString() + ") and status = ? order by orderNo asc";

		List<SysViewRoleModule> list = sysViewRoleModuleDao.queryForPage(hql,
				0, 10000, 1);

		// 去 重
		List<SysViewRoleModule> list2 = new ArrayList<SysViewRoleModule>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			SysViewRoleModule roleModule = list.get(i);
			if (map.get(roleModule.getModId() + "") == null) {
				list2.add(roleModule);
				map.put("" + roleModule.getModId(), "" + roleModule.getModId());
			}
		}

		return list2;
	}

	/**
	 * 超级管理员的模块
	 * 
	 * @return
	 */
	public List<SysModule> findAdminModule() {
		String queryString = "from SysModule where status = ? order by orderNo asc";
		return sysModuleDao.queryForPage(queryString, 0, 5000, 1);
	}

	/**
	 * 读取用户列表
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 */
	public PageInfo<SysUserinfo> queryUserinfoList(SysUserinfo condition,
			int pageSize, int pageNo, String sort, String order,boolean isCustomer) {

		StringBuffer hql = new StringBuffer();
		hql.append("from SysUserinfo where 1=1 ");

		List<String> conList = new ArrayList<String>();

		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getLoginid())) {
				hql.append(" and loginid like ?");
				conList.add("%" + condition.getLoginid() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getFullname())) {
				hql.append(" and fullname like ?");
				conList.add("%" + condition.getFullname() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getUserEmail())) {
				hql.append(" and userEmail like ?");
				conList.add("%" + condition.getUserEmail() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getTelephone())) {
				hql.append(" and telephone like ?");
				conList.add("%" + condition.getTelephone() + "%");
			}
			if(condition.getIsCustomer() > 0){
				hql.append(" and is_customer = 1");
				
			}
			if(condition.getIsObjectCustomer() > 0){
				hql.append(" and is_object_customer = 1");
				
			}
			if(condition.getIsInvestmentService() > 0){
				hql.append(" and is_investment_service = 1");
				
			}
			if(condition.getUserId() > 0){
				hql.append(" and USER_ID = ?");
				conList.add(condition.getUserId()+"");
			}
			//是否是客服角色
			if(isCustomer){
				hql.append(" and isCustomerR = 1");
			}

			// TODO:这里可以加更多的搜索条件

		}

		hql.append(" order by " + sort + " " + order);
		// 总记录数
		int count = sysUserinfoDao.getRowCount(hql.toString(),
				conList.toArray());
		PageInfo<SysUserinfo> pageInfo = new PageInfo<SysUserinfo>("", count,
				pageSize);

		if (count == 0)
			return pageInfo;

		if (pageNo < 1)
			pageNo = 1;
		pageInfo.setPageno(pageNo);
		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		List<SysUserinfo> list = sysUserinfoDao.queryForPage(hql.toString(),
				firstResult, pageInfo.getPageSize(), conList.toArray());
		pageInfo.setItems(list);

		return pageInfo;
	}

	/**
	 * 修改密码
	 * 
	 * @param loingid
	 * @param oldpwd
	 * @param newpwd
	 * @return 0修改成功，1旧密码不正确 ，2账号不存在
	 */
	public int updatePwd(int userid, String oldpwd, String newpwd) {

		SysUserinfo userinfo = sysUserinfoDao.findById(userid);
		if (userinfo == null)
			return 2;

		String oldPwdMd5 = MD5.encode(oldpwd);

		if (userinfo.getUserPassword().equals(oldPwdMd5)) {
			userinfo.setUserPassword(MD5.encode(newpwd));
			sysUserinfoDao.update(userinfo);
			return 0;
		}

		return 1;
	}

	/**
	 * 添加新用户
	 * 
	 * @param userinfo
	 * @return 1 用户已存在，0成功
	 */
	public int insert(SysUserinfo sysUserinfo) {
		int c = sysUserinfoDao.findByProperty("SysUserinfo", "loginid",
				sysUserinfo.getLoginid()).size();
		if (c > 0)
			return 1;

		sysUserinfo.setCreateTime(new Date());
		sysUserinfo.setLastLoginTime(new Date());
		sysUserinfoDao.insert(sysUserinfo);
		return 0;
	}

	/**
	 * 更新用户所属于的角色
	 * 
	 * @param userid
	 * @param roleids
	 */
	public void updateUserRole(int userid, int[] roleids) {
		try {
			jdbcTemplate.update("delete from sys_user_role where user_id = "
					+ userid);
			if (roleids != null) {
				for (int i = 0; i < roleids.length; i++) {
					String sql = "insert into sys_user_role(user_id,role_id) values('"
							+ userid + "','" + roleids[i] + "')";
					jdbcTemplate.update(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SysUserinfo findById(int userId) {
		return sysUserinfoDao.findById(userId);
	}

	public void update(SysUserinfo sysUserinfo) {
		sysUserinfoDao.update(sysUserinfo);
	}

	@Override
	public int updateLoginCount(int userid) {
		String sql = "update sys_userinfo set LOGIN_TIME = LOGIN_TIME+1 where USER_ID = ?";
		int i = jdbcTemplate.update(sql, userid);
		return i ^ 1;
	}

	// 新增手机号码登录
	@Override
	public SysUserinfo phonelogin(String phone, String password) {
		SysUserinfo u = null;
		String sql = "select LOGINID from sys_userinfo where TELEPHONE = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, phone);
		if (result.next()) {
			u = this.login(result.getString(1), password);
		}

		return u;
	}

	@Override
	public boolean telExist(String phone) {
		String sql = "select LOGINID from sys_userinfo where TELEPHONE = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, phone);
		if (result.next()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据登录名查找登录用户
	 * 
	 * @param loginid
	 * @return
	 */
	@Override
	public SysUserinfo findByLoginId(String loginid) {
		return this.sysUserinfoDao.findByLoginId(loginid);
	}

	// 生成岗位号（客服岗位号＝部门编号+岗位编号+3位序号）KF-ZX-001
	@Override
	public String createCustomerServiceId() {
		int b = (int) (Math.random() * 1000);
		String a = String.valueOf(b);
		StringBuffer s = new StringBuffer();
		if (a.length() < 2 && a.length() > 0) {
			int len = 2 - a.length();
			for (int j = 0; j < len; j++) {
				s = s.append("0");
			}
		}
		String str = "KF-ZX-" + s.toString() + a;
		return str;
	}

//	@Override
//	public PageInfo<SysUserinfoForm> queryCustomerPageInfo(
//			Map<String, Object> condition, int pageSize, int pageNo,
//			String sort, String order) {
//		if (null == sort || ("").equals(sort)) {
//			sort = "createTime";
//		}
//		if (null == order) {
//			order = "DESC";
//		}
//		String sql = "from SysUserinfo";
//		StringBuffer sqlBody = new StringBuffer();
//		List<Object> conList = new ArrayList<Object>();
//		if (null != condition) {
//			if (null != condition.get("customerType")) {// 客服/物服/审核员
//				Integer customerType = (Integer) condition.get("customerType");
//				if (customerType == 0) {
//					sqlBody.append(" WHERE is_customer !=0 ");
//				} else if (customerType == 1) {
//					sqlBody.append(" WHERE is_object_customer !=0 ");
//				} else if (customerType == 2) {
//					sqlBody.append(" WHERE (is_customer !=0 AND is_object_customer !=0) ");
//				}
//			} else {
//				sqlBody.append(" WHERE (is_customer !=0 OR is_object_customer !=0) ");
//			}
//			if (null != condition.get("fullname")) {// 姓名
//				sqlBody.append(" and fullname like '");
//				sqlBody.append("%" + condition.get("fullname") + "%'");
//			}
//			if (null != condition.get("postNo")) {// 岗位号
//				sqlBody.append(" and custom_service_id like '");
//				sqlBody.append("%" + condition.get("postNo") + "%'");
//			}
//			if (null != condition.get("isOnDuty")) {// 是否上班
//				sqlBody.append(" and is_on_duty =? ");
//				conList.add(condition.get("isOnDuty"));
//			}
//			if (null != condition.get("onlineStatus")) {// 在线状态
//				sqlBody.append(" and online_status =? ");
//				conList.add(condition.get("onlineStatus"));
//			}
//		} else {
//			sqlBody.append(" WHERE (is_customer !=0 OR is_object_customer !=0) ");
//		}
//
//		String sqlcount = "from SysUserinfo " + sqlBody.toString();
//		System.err.println("9.4" + sqlBody.toString());
//		int count = 0;
//		if (conList.size() > 0) {
//			count = sysUserinfoDao.getRowCount(sqlcount.toString(),conList.toArray());
//
//		} else {
//			count = sysUserinfoDao.getRowCount(sqlcount.toString());
//		}
//		String sqlQuery = sql + " " + sqlBody.toString() + " ORDER BY " + sort
//				+ " " + order;
//		PageInfo<SysUserinfoForm> pageInfo = new PageInfo<SysUserinfoForm>(count,pageSize);
//
//		if (count == 0) {
//			return pageInfo;
//		}
//		if (pageNo < 1) {
//			pageNo = 1;
//		}
//		pageInfo.setPageno(pageNo);
//		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
//		List<SysUserinfoForm> list = new ArrayList<SysUserinfoForm>();
//		if (conList.size() > 0) {
//			list = this.queryForPage(sqlQuery, firstResult,
//					pageInfo.getPageSize(), conList.toArray());
//		} else {
//			list = this.queryForPage(sqlQuery, firstResult,
//					pageInfo.getPageSize());
//		}
//		pageInfo.setItems(list);
//		return pageInfo;
//	}

	@Override
	public SysUserinfo findByqq(String qq) {
        List<SysUserinfo> ulist = sysUserinfoDao.findByProperty("customerQQ", qq);
        SysUserinfo u = null;
        if(ulist != null){
        	if(ulist.size() > 0){
        		u = ulist.get(0);
        	}
        	
        }
		return u;
	}
	
	
    public Session getSession() {
        //事务必须是开启的(Required)，否则获取不到
        return sessionFactory.getCurrentSession();
    }
    
    
    
	@Override
	public int updateHeadImg(String uid,String img) {
		String sql = "update sys_userinfo set head_img = ? where USER_ID = ?";
		int i = jdbcTemplate.update(sql, img,uid);
		return i;
	}

	/**
	 * 给查询设置参数
	 * @param query
	 * @param paramlist
	 */
	protected void setParameters(Query query, Object[] paramlist) {
        if (paramlist != null) {
            for (int i = 0; i < paramlist.length; i++) {
                if(paramlist[i] instanceof Date) {
                    query.setTimestamp(i, (Date)paramlist[i]);
                } else {
                    query.setParameter(i, paramlist[i]);
                }
            }
        }
    }
	
	public List<SysUserinfoForm> queryForPage(final String hql,final int firstResult, final int maxResult,final Object... paramlist) {
		Query query = getSession().createQuery(hql);		
		this.setParameters(query, paramlist);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		List<SysUserinfoForm> list = query.list();
		return list; 
	}
	
	/**
	 * 导入用户
	 * @param list
	 * @param kefuitem
	 * @return
	 */
	public int saveUser(List<String[]> list,String kefuitem,int roleid){
		int count=0;
		int is_customer=0;
		int is_object_customer=0;
		if(kefuitem.equals("交互")){
			is_customer=1;
		}
		if(kefuitem.equals("充值") || kefuitem.equals("资料")){
			is_object_customer=1;
		}
		
		for (int i = 0;i < list.size(); i++) {
			String[] x = list.get(i);
			final String logid =  x[2];
			final String fullname=x[1];
			final String userpassword="96E79218965EB72C92A549DD5A330112";
			final String worktel=x[3];
			final String tel=x[3];
			final String usertitle=x[0];
			final int iscustomer=is_customer;
			final int isobjectcusomer=is_object_customer;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int j = jdbcOperations.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					int i = 0;
				
					String addSql = "INSERT INTO sys_userinfo(LOGINID,FULLNAME,USER_PASSWORD,WORKPHONE,TELEPHONE,USER_TITLE,CREATE_TIME,is_customer,is_object_customer,LOGIN_TIME,status,USERTYPE,USER_EMAIL,LAST_LOGIN_TIME)"
							+ " VALUES(?,?,?,?,?,?,NOW(),?,?,0,0,0,'',NOW())";
					
					
					PreparedStatement ps = con.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
					 ps.setString(1, logid);
					 ps.setString(2, fullname);
					 ps.setString(3, userpassword);
					 ps.setString(4, worktel);
					 ps.setString(5, tel);
					 ps.setString(6, usertitle);
					 ps.setInt(7, iscustomer);
					 ps.setInt(8, isobjectcusomer);
					return ps;
				}
			}, keyHolder);
			int rid = keyHolder.getKey().intValue();//获取ID
			String hql="insert into sys_user_role(user_id,role_id) values("+rid+","+roleid+")";
			jdbcTemplate.update(hql);
			count++;
		}
		return count;
	}

}
