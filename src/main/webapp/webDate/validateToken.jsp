<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="cn.jugame.util.Cache"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.service.ISysModuleService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="cn.jugame.vo.SysModule"%>
<%@page import="cn.jugame.vo.SysViewUserRole"%>
<%@page import="cn.jugame.vo.SysViewRoleModule"%>
<%@page import="cn.jugame.vo.SysViewRolePermission"%>
<%@page import="cn.jugame.service.ISysUserinfoService"%>
<%@page import="cn.jugame.vo.SysModule"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%
	String ticket = request.getParameter("ticket");
	String modId = request.getParameter("modId");
	String sign = request.getParameter("sign");
	JSONObject json = new JSONObject();
	
	if (StringUtils.isBlank(ticket) || !StringUtils.isNumeric(modId)|| StringUtils.isBlank(sign)) {
		json.put("code", "1");
		json.put("msg", "参数为空");
		out.print(json.toString());
		return;
	}
	//查询用户是否有对应的权限
	JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
	String sql = "SELECT * FROM `sys_userinfo` WHERE user_id IN(SELECT user_id FROM `sys_user_role` WHERE role_id IN(SELECT role_id FROM `sys_role_module` WHERE mod_id =  ? )) AND STATUS = 1 AND user_id = ? ";
	
	ISysModuleService moduleService = SpringFactory.getBean("SysModuleService");
	SysModule modulekey = moduleService.findById(Integer.parseInt(modId));
	String key = modulekey.getModuleCode();
	String mySign = MD5.encode(ticket + key + modId);

	System.out.println("jsp页面获取到得ticket --------》 " + ticket);
	System.out.println("jsp页面获取到得modId --------》 " + modId);
	System.out.println("jsp页面获取到得sign --------》 " + sign);
	System.out.println("jsp页面自己生产的mySign  --------》 " + mySign);
	System.out.println("jsp页面自己生产的key  --------》 " + key);
	if (!mySign.toLowerCase().equals(sign.toLowerCase())) {
		json.put("code", "1");
		json.put("msg", "签名失败");
		out.print(json.toString());
		return;
	}

	try{
		int uid =  Cache.get("sso/" + ticket);
		ISysUserinfoService uservice = SpringFactory.getBean("SysUserinfoService");

	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//用户角色列表
		List<Map<String, Object>> uroleList = new ArrayList<Map<String, Object>>();
		//角色模块列表
		List<Map<String, Object>> rmoduleList = new ArrayList<Map<String, Object>>();
		//角色权限列表
		List<Map<String, Object>> rperList = new ArrayList<Map<String, Object>>();

		Map<String, Object> userMap = new HashMap<String, Object>();

		SysUserinfo user = uservice.findById(uid);
		SysViewUserRole urole = null;
		SysViewRoleModule rolemodule = null;
		SysViewRolePermission roleper = null;

		//System.out.println("结果uid == " + user.getUserId());
		if (user == null) {
			json.put("code", "1");
			json.put("msg", "无此相关信息");
			out.print(json.toString());
			return;
		}

		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,modId,uid);
		if((null == list || list.size() == 0 ) && user.getUsertype() == 1){
			json.put("code", "1");
			json.put("msg", "权限没有配置，请联系管理员");
			out.print(json.toString());
			return;
		}
		
		
		//普通管理员用户信息
		userMap.put("user_id", user.getUserId());
		userMap.put("loginid", user.getLoginid());
		userMap.put("usertype", user.getUsertype());
		userMap.put("status", user.getStatus());
		userMap.put("create_time", sdf.format(user.getCreateTime()));
		userMap.put("customer_service_id",user.getCustomServiceId() == null ? "" : user.getCustomServiceId());
		userMap.put("online_status", user.getOnlineStatus());
		userMap.put("is_customer", user.getIsCustomer());
		userMap.put("is_object_customer", user.getIsObjectCustomer());
		userMap.put("is_on_duty", user.getIsOnDuty());
		userMap.put("customer_nickname", user.getCustomerNickname());
		userMap.put("customer_qq", user.getCustomerQQ());
		userMap.put("fullname", user.getFullname());
		if (user.getUsertype() == 1) {
			//普通管理员用户角色信息
			List<SysViewUserRole> list2 = uservice.findUserRole(user.getLoginid());
			if (null != list2) {
				for (int i = 0; i < list2.size(); i++) {
					Map<String, Object> uroleMap = new HashMap<String, Object>();
					uroleMap.put("user_id", list2.get(i).getUserId());//用户uid
					uroleMap.put("role_id", list2.get(i).getRoleId());//角色id
					uroleMap.put("role_name", list2.get(i).getRoleName());//角色名
					uroleMap.put("status", list2.get(i).getStatus());//角色状态
					uroleMap.put("role_code", list2.get(i).getRoleCode());//角色代号
					uroleList.add(uroleMap);
				}
			}
			//普通管理员角色模块信息
			List<SysViewRoleModule> list3 = uservice.findRoleModule(list2);
			if (null != list3) {
				for (int j = 0; j < list3.size(); j++) {
					Map<String, Object> rmoduleMap = new HashMap<String, Object>();
					rmoduleMap.put("role_id", list3.get(j).getRoleId());//角色id
					rmoduleMap.put("role_name", list3.get(j).getRoleName());//角色名字
					rmoduleMap.put("mod_id", list3.get(j).getModId());//模块id
					rmoduleMap.put("parent_id", list3.get(j).getParentId());//父模块id
					rmoduleMap.put("module_code", list3.get(j).getModuleCode());//模块代码
					rmoduleMap.put("module_name", list3.get(j).getModuleName());//模块名字
					rmoduleMap.put("level_seq", list3.get(j).getLevelSeq());//模块类型 父模块/子模块
					rmoduleMap.put("first_page", list3.get(j).getFirstPage());//跳转路径
					rmoduleMap.put("is_menu", list3.get(j).getIsMenu());//是否菜单
					rmoduleMap.put("status", list3.get(j).getStatus());//状态
					rmoduleMap.put("order_no", list3.get(j).getOrderNo());//排序号

					rmoduleList.add(rmoduleMap);
				}
			}

			//普通管理员角色权限信息
			List<SysViewRolePermission> list4 = uservice
					.findRolePermission(list2);
			if (null != list4) {
				for (int k = 0; k < list4.size(); k++) {
					Map<String, Object> rperMap = new HashMap<String, Object>();
					rperMap.put("role_id", list4.get(k).getRoleId());//角色id
					rperMap.put("role_name", list4.get(k).getRoleName());//角色名字
					rperMap.put("pid", list4.get(k).getPid());
					rperMap.put("mod_id", list4.get(k).getModId());
					rperMap.put("per_name", list4.get(k).getPerName());
					rperMap.put("per_code", list4.get(k).getPerCode());
					rperMap.put("remark", list4.get(k).getRemark());

					rperList.add(rperMap);

				}
			}
		} else {//超级管理员
			List<SysModule> modList = uservice.findAdminModule();
			List<SysViewRoleModule> mod2List = new ArrayList<SysViewRoleModule>();
			for (int i = 0; i < modList.size(); i++) {
				SysModule module = modList.get(i);
				SysViewRoleModule roleModule = new SysViewRoleModule();
				Map<String, Object> rmoduleMap = new HashMap<String, Object>();
				try {
					BeanUtils.copyProperties(roleModule, module);

					rmoduleMap.put("role_id", roleModule.getRoleId());//角色id
					rmoduleMap.put("role_name", roleModule.getRoleName());//角色名字
					rmoduleMap.put("mod_id", roleModule.getModId());//模块id
					rmoduleMap.put("parent_id", roleModule.getParentId());//父模块id
					rmoduleMap.put("module_code",roleModule.getModuleCode());//模块代码
					rmoduleMap.put("module_name",roleModule.getModuleName());//模块名字
					rmoduleMap.put("level_seq", roleModule.getLevelSeq());//模块类型 父模块/子模块
					rmoduleMap.put("first_page", roleModule.getFirstPage());//跳转路径
					rmoduleMap.put("is_menu", roleModule.getIsMenu());//是否菜单
					rmoduleMap.put("status", roleModule.getStatus());//状态
					rmoduleMap.put("order_no", roleModule.getOrderNo());//排序号

					rmoduleList.add(rmoduleMap);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		json.put("code", "0");
		json.put("msg", "查询成功");
		json.put("user", userMap);
		json.put("userrole", uroleList);
		json.put("rolemodule", rmoduleList);
		json.put("rolepermission", rperList);
		out.print(json.toString());
	}catch(Exception ee ){
		json.put("code", "1");
		json.put("msg", "ticket已过期!");
		out.print(json.toString());
		ee.printStackTrace();
	}
	
	//System.out.println(json.toString());
%>