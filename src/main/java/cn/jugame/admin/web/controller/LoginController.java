package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.dao.SysUserinfoDao;
import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.Cache;
import cn.jugame.util.MD5;
import cn.jugame.util.SysConfig;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.vo.SysViewRoleModule;
import cn.jugame.vo.SysViewRolePermission;
import cn.jugame.vo.SysViewUserRole;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.jugame.web.util.sendMsgUtil;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.sms.service.SMSService;

import com.octo.captcha.service.multitype.MultiTypeCaptchaService;

/**
 * 后台登陆
 * 
 * @author Administrator
 * 
 */
@Controller
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ISysUserinfoService sysUserinfoService;
	@Autowired
	private MultiTypeCaptchaService captchaService;

	@Autowired
	private BackUserLogService backUserLogService;

	@Autowired
	private SMSService smsService;

	public static String INPUT = "login_submit";

	public static String LOGIN = "redirect:/main.html";

	/**
	 * 
	 * @return 跳转
	 */
	@RequestMapping(value = "/index")
	public String tologin() {
		return "redirect:/login.html";
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param loginid
	 *            登录名
	 * @param password
	 *            登录密码
	 * @param vcode
	 *            验证码
	 * @param model
	 * @return 跳转
	 */
	@RequestMapping(value = "/login_submit")
	public String dologin(HttpServletRequest request, String loginid,String password, String vcode, String msgcode, Model model) {
		// logger.info("loginid=" + loginid + ",password=" + password +
		// ",vcode="+ vcode);
		String ip = RequestUtils.getUserIp(request);
//		String loginid = request.getParameter("loginid");
//		String password = request.getParameter("password");
//		String vcode = request.getParameter("vcode");

		if (null == vcode || "".equals(vcode)) {
			logger.info("请输入验证码 loginid=" + loginid + ",vcode=" + vcode + ",ip=" + ip);
			addActionError("请输入验证码", model);
			return INPUT;
		}
		if (StringUtils.isBlank(loginid) || StringUtils.isBlank(password)|| StringUtils.isBlank(vcode)) {
			logger.info("请输入正确的用户名和密码 loginid=" + loginid + ",vcode=" + vcode + ",ip=" + ip);
			addActionError("请输入正确的用户名和密码", model);
			return INPUT;
		}
		String sessionId = request.getSession().getId();

		// System.out.println(vcode);

		try {
			if (!captchaService.validateResponseForID(sessionId, vcode)) {
				logger.info("验证码不正确 loginid=" + loginid + ",vcode=" + vcode + ",ip=" + ip);
				addActionError("验证码不正确", model);
				return INPUT;
			}
		} catch (Exception e) {
			logger.info("验证码不正确e loginid=" + loginid + ",vcode=" + vcode + ",ip=" + ip);
			addActionError("验证码不正确", model);
			return INPUT;
		}
		SysUserinfo userinfo = sysUserinfoService.login(loginid, password);
		if (userinfo == null) {
			logger.info("用户名或者密码不正确 loginid=" + loginid + ",vcode=" + vcode + ",ip=" + ip);
			addActionError("用户名或者密码不正确", model);
			return INPUT;
		}
		if (userinfo.getStatus() != 1) {
			addActionError("账号已被锁定", model);
			return INPUT;
		}
		// 如果是客服
		//if (userinfo.getIsCustomer() > 0 || userinfo.getIsObjectCustomer() > 0|| userinfo.getIsInvestmentService() > 0) {
			// 验证ip地址是否正确
			if (userinfo.getIpLoginFlag() == 1) {
				String loginIp = userinfo.getIpLoginAddress();
				int ipresult = ipVerify(ip, loginIp);
				if (ipresult != 0) {
					logger.info("本系统只能在特定的网络登录 loginid=" + loginid
							+ ",password=" + password + ",vcode=" + vcode
							+ ",ip=" + ip);
					addActionError("本系统只能在特定的网络登录", model);
					return INPUT;
				}
			}
			// 验证短信是否正确
			if (userinfo.getMsgLoginFlag() == 1) {
				if (null == msgcode) {
					addActionError("请输入短信收到的验证码", model);
					return INPUT;
				}
				String result  = sendMsgUtil.verify(msgcode, userinfo.getTelephone());
				if (StringUtils.isBlank(result)) {
					addActionError("检校校验码失败", model);
					return INPUT;
				} else {
					
					logger.info(loginid + "verify sms result:" + result);
					JSONObject json = JSONObject.fromObject(result);
					if(!json.getBoolean("data")){
						addActionError(json.getString("message"), model);
						return INPUT;
					}
					
				}
				
			}
		//}

		// 超级管理员,不需要设置权限
		if (userinfo.getUsertype() != SysUserinfoDao.USER_TYPE_SUPER_ADMIN) {
			// 管理员的角色
			List<SysViewUserRole> roleList = sysUserinfoService.findUserRole(userinfo.getLoginid());
			if (roleList == null || roleList.size() == 0) {
				addActionError("未分配任何角色，请联系管理员", model);
				return INPUT;
			}

			// 管理员所拥有权限
			List<SysViewRolePermission> perList = sysUserinfoService.findRolePermission(roleList);
			if (perList == null || perList.size() == 0) {
				addActionError("未分配任何权限 ，请联系管理员", model);
				return INPUT;
			}
			// 管理员所拥有权限 -Map形式，踢除重复
			Map<String, String> perMap = new HashMap<String, String>();
			for (int i = 0; i < perList.size(); i++) {
				// 保证key是小写字母
				perMap.put(perList.get(i).getPerCode().toLowerCase(), perList.get(i).getPerName());
				// System.out.println("user_per:"+
				// perList.get(i).getPerCode().toLowerCase());
			}
			// 模块,用于控制显示菜单
			List<SysViewRoleModule> modList = sysUserinfoService.findRoleModule(roleList);
			if (modList == null || modList.size() == 0) {
				addActionError("未分配任何模块 ，请联系管理员", model);
				return INPUT;
			}

			request.getSession().setAttribute(GlobalsKeys.ADMIN_ROLE, roleList);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_PERMISSION,perList);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_PERMISSION_MAP,perMap);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_MODULE, modList);
		} else {
			List<SysModule> modList = sysUserinfoService.findAdminModule();
			List<SysViewRoleModule> mod2List = new ArrayList<SysViewRoleModule>();
			for (int i = 0; i < modList.size(); i++) {
				SysModule module = modList.get(i);
				SysViewRoleModule roleModule = new SysViewRoleModule();
				try {
					BeanUtils.copyProperties(roleModule, module);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mod2List.add(roleModule);
			}
			request.getSession().setAttribute(GlobalsKeys.ADMIN_MODULE,mod2List);

		}

		// 写日志
		// sysLogService.saveLog("LOGIN", "登录", "登录", userinfo.getFullname() +
		// "登录系统", userinfo.getLoginid(), RequestUtils.getUserIp(), "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		backUserLogService.addLog(
				userinfo.getUserId(),
				RequestUtils.getUserIp(request),
				backUserLogService.ADMIN_LOGIN,
				"管理员登录：ID为" + userinfo.getUserId() + ",登录帐号："
						+ userinfo.getLoginid() + "的管理员"
						+ userinfo.getFullname() + "在" + sdf.format(new Date())
						+ "登录后台管理系统");
		request.getSession().setAttribute(GlobalsKeys.ADMIN_KEY, userinfo);
		sysUserinfoService.updateLoginCount(userinfo.getUserId());
		model.addAttribute("success", "true");
		model.addAttribute("msg", "登录成功");

		return "login_submit";
	}

	/**
	 * 为了兼容从struts拷过来的代码
	 * 
	 * @param errMsg
	 *            错误信息
	 * @param model
	 */
	public void addActionError(String errMsg, Model model) {
		model.addAttribute("success", "false");
		model.addAttribute("msg", errMsg);
	}

	/**
	 * 单点登录 客服系统
	 * 
	 * @param request
	 * @param model
	 * @return 跳转
	 */
	@RequestMapping(value = "single/md5CustomerLogin")
	public String md5CustomerLogin(HttpServletRequest request, Model model) {

		String loginName = RequestUtils.getParameter(request, "loginName", null);
		String customerId = RequestUtils.getParameter(request, "customerId",null);
		String data = RequestUtils.getParameter(request, "data", null);
		String loginfrom = RequestUtils.getParameter(request, "loginfrom", null);
		if (null == loginName || null == customerId || null == data) {
			return "redirect:/login.html";
		}
		// 清理之前登录的session
		request.getSession().removeAttribute(GlobalsKeys.ADMIN_KEY);

		String md5Str = SysConfig.getSysParamValue("SYS_MD5_LOGIN_STR", "");
		if (md5Str == null || "".equals(md5Str)) {
			addActionError("数据库加密字符串未配置,请联系管理员", model);
			logger.info("数据库加密字符串未配置,请联系管理员");
			return LOGIN;
		}
		StringBuffer str = new StringBuffer();
		str.append(customerId).append(md5Str).append(loginName);
		String newMd5 = MD5.encode(str.toString());
		if (!newMd5.equals(data)) {
			return "redirect:/login.html";
		}

		SysUserinfo userinfo = sysUserinfoService.findByLoginId(loginName);
		// logger.info("==============" + userinfo.getFullname());
		if (userinfo == null) {
			addActionError("用户名不存在,请联系管理员", model);
			logger.info("用户名不存在,请联系管理员");
			return LOGIN;
		}
		if (userinfo.getUsertype() == 0) {
			addActionError("超级管理员不允许单点登录", model);
			logger.info("超级管理员不允许单点登录");
			return LOGIN;
		}
		if (userinfo.getStatus() != 1) {
			addActionError("账号已被锁定", model);
			logger.info("账号已被锁定");
			return LOGIN;
		}

		// 超级管理员,不需要设置权限
		if (userinfo.getUsertype() != SysUserinfoDao.USER_TYPE_SUPER_ADMIN) {
			// 管理员的角色
			List<SysViewUserRole> roleList = sysUserinfoService.findUserRole(userinfo.getLoginid());
			if (roleList == null || roleList.size() == 0) {
				addActionError("未分配任何角色，请联系管理员", model);
				logger.info("未分配任何角色，请联系管理员");
				return LOGIN;
			}

			// 管理员所拥有权限
			List<SysViewRolePermission> perList = sysUserinfoService.findRolePermission(roleList);
			if (perList == null || perList.size() == 0) {
				addActionError("未分配任何权限 ，请联系管理员", model);
				logger.info("未分配任何权限 ，请联系管理员");
				return LOGIN;
			}
			// 管理员所拥有权限 -Map形式，踢除重复
			Map<String, String> perMap = new HashMap<String, String>();
			for (int i = 0; i < perList.size(); i++) {
				// 保证key是小写字母
				perMap.put(perList.get(i).getPerCode().toLowerCase(), perList
						.get(i).getPerName());
				// System.out.println("user_per:"+
				// perList.get(i).getPerCode().toLowerCase());
			}
			if (loginfrom != null && !"".equals(loginfrom)) {
				String perStr = "cn.jugame.admin.web.controller.OrderC2cController.getOrderC2cNoSuccessListForQq";
				perMap.put(perStr.toLowerCase(), "客服qq登录");
				System.out.println("权限 -- " + perStr);
			}
			// 模块,用于控制显示菜单
			List<SysViewRoleModule> modList = sysUserinfoService.findRoleModule(roleList);
			if (modList == null || modList.size() == 0) {
				addActionError("未分配任何模块 ，请联系管理员", model);
				logger.info("未分配任何模块 ，请联系管理员");
				return LOGIN;
			}

			request.getSession().setAttribute(GlobalsKeys.ADMIN_ROLE, roleList);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_PERMISSION,perList);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_PERMISSION_MAP,perMap);
			request.getSession().setAttribute(GlobalsKeys.ADMIN_MODULE, modList);
		} else {
			List<SysModule> modList = sysUserinfoService.findAdminModule();
			List<SysViewRoleModule> mod2List = new ArrayList<SysViewRoleModule>();
			for (int i = 0; i < modList.size(); i++) {
				SysModule module = modList.get(i);
				SysViewRoleModule roleModule = new SysViewRoleModule();
				try {
					BeanUtils.copyProperties(roleModule, module);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mod2List.add(roleModule);
			}
			request.getSession().setAttribute(GlobalsKeys.ADMIN_MODULE,
					mod2List);

		}

		// 写日志
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		backUserLogService.addLog(
				userinfo.getUserId(),
				RequestUtils.getUserIp(request),
				backUserLogService.ADMIN_LOGIN,
				"客服系统单点登录：ID为" + userinfo.getUserId() + "的管理员"
						+ userinfo.getFullname() + "在" + sdf.format(new Date())
						+ "登录后台管理系统");

		// 单点登录 配置用户类型为：3 目前通过单点登录修改的列表页面：1 会员列表 2实名认证列表 3商品列表 4订单列表 5支付单列表
		userinfo.setUsertype(3);
		request.getSession().setAttribute(GlobalsKeys.ADMIN_KEY, userinfo);
		sysUserinfoService.updateLoginCount(userinfo.getUserId());
		model.addAttribute("success", "true");
		model.addAttribute("msg", "登录成功");
		if (!loginfrom.equals("") || null != loginfrom) {
			return "redirect:" + loginfrom;
		}
		return "redirect:/main.html";
	}

	private int ipVerify(String ip, String userIpAddress) {
		if (StringUtils.isBlank(ip)) {
			return -1;
		}
		String ipAddress = userIpAddress;//Config.getValue("kefu_login_ip");//192.168.1.22,454.554.12.25
		if (!ipAddress.contains(",")) {

			if (ip.equals(ipAddress)) {
				return 0;
			} else {
				return -2;
			}
		}
		String[] ips = ipAddress.split(",");
		for (int i = 0; i < ips.length; i++) {
			if (ip.equals(ips[i].trim())) {
				return 0;
			}
		}
		return -3;
	}

	/**
	 * 发送校验码
	 * 
	 * @param request
	 *            请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "/sendVerifyCode")
	@ResponseBody
	public MessageBean sendVerifyCode(HttpServletRequest request) {
		MessageBean mb = null;
		String loginid = request.getParameter("loginid");
		if (StringUtils.isBlank(loginid)) {
			return new MessageBean(false, "用户登录帐号不能为空");
		}
		SysUserinfo u = sysUserinfoService.findByLoginId(loginid);
		if (null == u) {
			return new MessageBean(false, "用户不存在");
		}
		String mobile = u.getTelephone();
		if (StringUtils.isBlank(mobile)) {
			return new MessageBean(false, "用户手机为空");
		}
		String resesult = sendMsgUtil.send(mobile);
		if (StringUtils.isBlank(resesult)) {
			mb = new MessageBean(false, "登录发送校验码失败");
		} else {
			logger.info(loginid + "send sms result:" + resesult);
			JSONObject json = JSONObject.fromObject(resesult);
			if(json.getInt("code") == 0){
				mb = new MessageBean(true, "登录发送校验码成功");
			}else{
				mb = new MessageBean(false, json.getString("message"));
			}
			
		}
		return mb;		
	}

	/**
	 * 查询账号是否打开短信验证
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isSMS")
	@ResponseBody
	public int isSMS(HttpServletRequest request) {
		String loginid = request.getParameter("loginid");
		SysUserinfo sysUserinfo = sysUserinfoService.findByLoginId(loginid);
		int msgLoginFlag = 0;
		if (sysUserinfo != null) {
		    msgLoginFlag = sysUserinfo.getMsgLoginFlag();
		}
		return msgLoginFlag;
	}
	
}
