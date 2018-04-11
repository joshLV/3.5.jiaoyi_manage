package cn.jugame.admin.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.jugame.admin.web.form.SysUserinfoForm;
import cn.jugame.service.ISysParameterService;
import cn.jugame.service.ISysRoleService;
import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.Config;
import cn.jugame.util.MD5;
import cn.jugame.util.PageInfo;
import cn.jugame.util.SpringFactory;
import cn.jugame.util.StringValidation;
import cn.jugame.vo.SysRole;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.vo.SysViewUserRole;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.HttpRequestUtil;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;
;
/**
 * 后台用户管理
 * 
 * @author Administrator
 * 
 */
@Controller
public class UserinfoController {
	Logger logger = LoggerFactory.getLogger(UserinfoController.class);
	@Autowired
	private ISysUserinfoService sysUserService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private BackUserLogService backUserLogService;
	
	@Autowired
	private ISysParameterService sysParameterService;

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/userinfoList")
	public String userInfoList(Model model) {

		List<SysRole> roleList = sysRoleService.findAllValid();
		model.addAttribute("roleList", roleList);

		return "system/userinfoList";
	}
	
	/**
	 * 客服人员管理列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/system/customerList")
	public String customerList(Model model) {

		List<SysRole> roleList = sysRoleService.findValidCustomer();
		model.addAttribute("roleList", roleList);

		return "system/customerList";
	}

	/**
	 * 用户信息
	 * 
	 * @param request
	 * @return 用户信息
	 */
	@RequestMapping(value = "/system/userinfo_json")
	@ResponseBody
	public SysUserinfoForm userinfoJson(HttpServletRequest request) {
		int userId = RequestUtils.getParameterInt(request, "userId", 0);

		SysUserinfo sysUserinfo = sysUserService.findById(userId);
		if (sysUserinfo == null) {
			return null;
		}

		// 查询角色id
		List<SysViewUserRole> roleList = sysUserService.findUserRole(sysUserinfo.getLoginid());
		int[] roleids = new int[roleList.size()]; // 角色id
		for (int i = 0; i < roleList.size(); i++) {
			roleids[i] = roleList.get(i).getRoleId();
		}
		// TODO 客服类型
		int isService = sysUserinfo.getIsCustomer();
		int isObjectServcie = sysUserinfo.getIsObjectCustomer();
		int isInvestment = sysUserinfo.getIsInvestmentService();

		List<Integer> serviceTypeList = new ArrayList<Integer>();
		if (isService > 0) {
			serviceTypeList.add(1);
		}
		if (isObjectServcie > 0) {
			serviceTypeList.add(2);
		}
		if (isService > 0 && isObjectServcie > 0) {
			serviceTypeList.add(3);
		}
		if (isInvestment == 1) {
			serviceTypeList.add(4);
		}
		if(isInvestment == 2){
			serviceTypeList.add(5);
		}
		if(isInvestment == 10){
			serviceTypeList.add(6);
		}
		
		Integer[] serviceType = new Integer[serviceTypeList.size()];
		for (int i = 0; i < serviceTypeList.size(); i++) {
			serviceType[i] = serviceTypeList.get(i);
		}
		SysUserinfoForm sysUserinfoForm = new SysUserinfoForm();
		sysUserinfoForm.setRoleids(roleids);
		sysUserinfoForm.setServiceTypeIds(serviceType);
		try {
			BeanUtils.copyProperties(sysUserinfoForm, sysUserinfo);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		sysUserinfoForm.setUserPassword(""); // 清空pass字段

		return sysUserinfoForm;
	}

	/**
	 * 用户信息查询
	 * 
	 * @param request
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/userinfoList_json")
	public String userinfoListJson(HttpServletRequest request, Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "id"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		int utype = RequestUtils.getParameterInt(request, "sysusertype", 0);
		int uid = RequestUtils.getParameterInt(request, "uid", 0);

		// 搜索条件
		SysUserinfo obj = new SysUserinfo();
		obj.setLoginid(request.getParameter("loginid"));
		obj.setFullname(request.getParameter("fullname"));
		obj.setUserId(uid);
		if(utype == 1){
			obj.setIsCustomer(1);
		}else if(utype == 2){
			obj.setIsObjectCustomer(1);
		}else if(utype == 3){
			obj.setIsInvestmentService(1);
		}else if(utype == 4){
			obj.setIsInvestmentService(2);
		}

		PageInfo<SysUserinfo> pageInfo = sysUserService.queryUserinfoList(obj,pageSize, pageNo, sort, order,false);

		model.addAttribute("pageInfo", pageInfo);

		return "system/userinfoList_json";
	}
	
	/**
	 * 用户信息查询(客服人员)
	 * 
	 * @param request
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/customerList_json")
	public String customerListJson(HttpServletRequest request, Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "id"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		int utype = RequestUtils.getParameterInt(request, "sysusertype", 0);

		// 搜索条件
		SysUserinfo obj = new SysUserinfo();
		obj.setLoginid(request.getParameter("loginid"));
		obj.setFullname(request.getParameter("fullname"));
		if(utype == 1){
			obj.setIsCustomer(1);
		}else if(utype == 2){
			obj.setIsObjectCustomer(1);
		}else if(utype == 3){
			obj.setIsInvestmentService(1);
		}else if(utype == 4){
			obj.setIsInvestmentService(2);
		}

		PageInfo<SysUserinfo> pageInfo = sysUserService.queryUserinfoList(obj,
				pageSize, pageNo, sort, order,true);

		model.addAttribute("pageInfo", pageInfo);

		return "system/userinfoList_json";
	}

	/**
	 * 删除用户信息
	 * 
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/userinfo_delete")
	@ResponseBody
	public MessageBean userInfoDelete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		logger.info("del =" + ids);
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}

		String[] idArr = ids.split(",");
		if (idArr.length > 5) {
			return new MessageBean(false, "最多只能同时删除5条记录");
		}
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < idArr.length; i++) {
			if ("1000".equals(idArr[i])) {
				sb.append("不允许删除[默认管理员：admin]");
				continue;
			}
			try {
				sysUserService.delete(Integer.parseInt(idArr[i]));
				SysUserinfo u = (SysUserinfo) request.getSession()
						.getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
				backUserLogService.addLog(u.getUserId(),
						RequestUtils.getUserIp(request),
						BackUserLogService.ADMIN_DELETE, u.getFullname()
								+ "删除了管理员账号ID为：" + idArr[i]);
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		return new MessageBean(true, "删除成功");
	}

	/**
	 * 添加用户
	 * 
	 * @param userinfo
	 * @param roleids
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/userinfo_add")
	@ResponseBody
	public MessageBean userinfoAdd(SysUserinfo userinfo, int[] roleids,
			Integer[] serviceTypeIds, HttpServletRequest request) {
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(
				cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
		String customerPostNo = request.getParameter("customerPostNo");
		String customerNickname = request.getParameter("customerNickname");
		String customerQQ = request.getParameter("customerQQ");
		int msgFlag = Integer.parseInt(request.getParameter("msgFlag"));
		int ipFlag = Integer.parseInt(request.getParameter("ipFlag"));
		String ip = request.getParameter("ip");
		String nIp = ip.replaceAll("，", ",");
		int isCustomerR = Integer.parseInt(request.getParameter("isCustomerR"));
		
		if (StringUtils.isBlank(userinfo.getLoginid())) {
			return new MessageBean(false, "账号不能为空");
		}
		if (!StringValidation.Match("^[a-zA-Z0-9]{3,15}$",
				userinfo.getLoginid())) {
			return new MessageBean(false, "账号只能由字母和数字组成");
		}

		if (!StringValidation.Match("^[a-zA-Z0-9!@#]{4,15}$",
				userinfo.getUserPassword())) {
			return new MessageBean(false, "密码可以由数字、字母和！@#组成，长度为4-15位");
		}
		String pwdMd5 = MD5.encode(userinfo.getUserPassword());
		userinfo.setUserPassword(pwdMd5);
		userinfo.setCreateTime(new Date());
		userinfo.setLoginTime(0);
		userinfo.setMsgLoginFlag(msgFlag);
		userinfo.setIpLoginFlag(ipFlag);
		userinfo.setIpLoginAddress(nIp);

		if (null != serviceTypeIds) {
			List<Integer> serviceTypeList = Arrays.asList(serviceTypeIds);
			if (serviceTypeList.contains(1)) {
				userinfo.setIsCustomer(1);
				if (StringUtils.isBlank(customerNickname)) {
					return new MessageBean(false, "是客服/审核员,客服昵称不能为空");
				}
				if (StringUtils.isBlank(customerQQ)) {
					return new MessageBean(false, "是客服/审核员,客服QQ不能为空");
				}
			}
			if (serviceTypeList.contains(2)) {
				userinfo.setIsObjectCustomer(1);
			}
			if ((serviceTypeList.contains(1) && serviceTypeList.contains(2))|| serviceTypeList.contains(3)) {
				if (StringUtils.isBlank(customerNickname)) {
					return new MessageBean(false, "是客服/审核员,客服昵称不能为空");
				}
				if (StringUtils.isBlank(customerQQ)) {
					return new MessageBean(false, "是客服/审核员,客服QQ不能为空");
				}
				userinfo.setIsCustomer(1);
				userinfo.setIsObjectCustomer(1);
			}
			if (serviceTypeList.contains(4)) {
				if (StringUtils.isBlank(customerNickname)) {
					return new MessageBean(false, "客服昵称不能为空");
				}
				userinfo.setIsInvestmentService(1);
			}
			if (serviceTypeList.contains(5)) {
				if (StringUtils.isBlank(customerNickname)) {
					return new MessageBean(false, "客服昵称不能为空");
				}
				userinfo.setIsInvestmentService(2);
			}
			if (serviceTypeList.contains(5)&& serviceTypeList.contains(4)) {
				if (StringUtils.isBlank(customerNickname)) {
					return new MessageBean(false, "客服昵称不能为空");
				}
				userinfo.setIsInvestmentService(10);
			}
			if (!StringUtils.isBlank(customerNickname)) {
				userinfo.setCustomerNickname(customerNickname);
			}
			if (!StringUtils.isBlank(customerQQ)) {
				userinfo.setCustomerQQ(customerQQ);
			}
			if (serviceTypeList.contains(1) || serviceTypeList.contains(2)|| serviceTypeList.contains(3)) {
				if (!StringUtils.isBlank(customerPostNo)) {
					userinfo.setCustomServiceId(customerPostNo);
				}
			}
		}

		userinfo.setIsCustomerR(isCustomerR);
		
		int x = sysUserService.insert(userinfo);
		if (x == 1) {
			return new MessageBean(false, "对不起，用户已存在");
		}

		if (userinfo.getUsertype() > 0) {
			sysUserService.updateUserRole(userinfo.getUserId(), roleids);

			backUserLogService.addLog(u.getUserId(),
					RequestUtils.getUserIp(request),
					BackUserLogService.ADMIN_ADD, u.getFullname() + "增加了管理员账号："
							+ userinfo.getFullname());
		}

		// 操作成功
		return new MessageBean();
	}

	/**
	 * 编辑
	 * 
	 * @param userinfo
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/userinfo_edit")
	@ResponseBody
	public MessageBean userinfoEdit(SysUserinfoForm userinfo,
			HttpServletRequest request) {
		// String customerType = request.getParameter("customerType");
		String customerPostNo = request.getParameter("customerPostNo");
		String customerNickname = request.getParameter("customerNickname");
		String customerQQ = request.getParameter("customerQQ");
		int msgFlag = Integer.parseInt(request.getParameter("msgFlag")); 
		int ipFlag = Integer.parseInt(request.getParameter("ipFlag")); 
		String ip = request.getParameter("ip");
		String nIp = ip.replaceAll("，", ",");
		SysUserinfo sysUserinfo = sysUserService.findById(userinfo.getUserId());
		int isCustomerR = Integer.parseInt(request.getParameter("isCustomerR"));
		
		if (sysUserinfo == null) {
			return new MessageBean(false, "找不到相关内容");
		}
		StringBuffer msg = new StringBuffer();
		sysUserinfo.setFullname(userinfo.getFullname());
		if (!StringUtils.isBlank(userinfo.getUserPassword())) {
			if (!StringValidation.Match("^[a-zA-Z0-9!@#]{4,15}$",
					userinfo.getUserPassword())) {
				return new MessageBean(false, "密码由数字、字母和！@#组成，长度为4-15位");
			}
			String pwdMd5 = MD5.encode(userinfo.getUserPassword());
			sysUserinfo.setUserPassword(pwdMd5);
			msg.append("\n新的密码为：" + userinfo.getUserPassword());
		}
		sysUserinfo.setIsVisibleWeiXin(userinfo.getIsVisibleWeiXin());
		sysUserinfo.setUsertype(userinfo.getUsertype());
		sysUserinfo.setWorkphone(userinfo.getWorkphone());
		sysUserinfo.setTelephone(userinfo.getTelephone());
		sysUserinfo.setUserEmail(userinfo.getUserEmail());
		sysUserinfo.setUserAddress(userinfo.getUserAddress());
		sysUserinfo.setUserTitle(userinfo.getUserTitle());
		sysUserinfo.setStatus(userinfo.getStatus());
//		sysUserinfo.setMsgLoginFlag(userinfo.getMsgLoginFlag());

		if (null != userinfo.getServiceTypeIds()) {
			List<Integer> serviceTypeList = Arrays.asList(userinfo.getServiceTypeIds());
			if (serviceTypeList.isEmpty()) {
				sysUserinfo.setIsCustomer(0);
				sysUserinfo.setIsObjectCustomer(0);
				sysUserinfo.setIsInvestmentService(0);
			} else {
				if ((serviceTypeList.contains(1) && serviceTypeList.contains(2))&& serviceTypeList.contains(4)&& serviceTypeList.contains(5)) {
					return new MessageBean(false, "客服不能分配这么多角色");
				}
				if (serviceTypeList.contains(1)) {
					sysUserinfo.setIsCustomer(1);
					if (StringUtils.isBlank(customerNickname)) {
						return new MessageBean(false, "是客服/审核员,客服昵称不能为空");
					}
					if (StringUtils.isBlank(customerQQ)) {
						return new MessageBean(false, "是客服/审核员,客服QQ不能为空");
					}
					sysUserinfo.setIsInvestmentService(0);
				}
				if (serviceTypeList.contains(2)) {
					sysUserinfo.setIsObjectCustomer(1);
					sysUserinfo.setIsCustomer(0);
					sysUserinfo.setIsInvestmentService(0);
				}
				if ((serviceTypeList.contains(1) && serviceTypeList.contains(2))|| serviceTypeList.contains(3)) {
					if (StringUtils.isBlank(customerNickname)) {
						return new MessageBean(false, "是客服/审核员,客服昵称不能为空");
					}
					if (StringUtils.isBlank(customerQQ)) {
						return new MessageBean(false, "是客服/审核员,客服QQ不能为空");
					}
					sysUserinfo.setIsCustomer(1);
					sysUserinfo.setIsObjectCustomer(1);
					sysUserinfo.setIsInvestmentService(0);
				}
				if (serviceTypeList.contains(4)) {
					if (StringUtils.isBlank(customerNickname)) {
						return new MessageBean(false, "客服昵称不能为空");
					}
					sysUserinfo.setIsInvestmentService(1);
					sysUserinfo.setIsCustomer(0);
					sysUserinfo.setIsObjectCustomer(0);
				}
				if (serviceTypeList.contains(5)) {
					if (StringUtils.isBlank(customerNickname)) {
						return new MessageBean(false, "客服昵称不能为空");
					}
					sysUserinfo.setIsInvestmentService(2);
					sysUserinfo.setIsCustomer(0);
					sysUserinfo.setIsObjectCustomer(0);
				}
				if((serviceTypeList.contains(4) && serviceTypeList.contains(5))|| serviceTypeList.contains(6)){
					if (StringUtils.isBlank(customerNickname)) {
						return new MessageBean(false, "客服昵称不能为空");
					}
					sysUserinfo.setIsInvestmentService(10);
					sysUserinfo.setIsCustomer(0);
					sysUserinfo.setIsObjectCustomer(0);
				}
				
			}
			if (!StringUtils.isBlank(customerNickname)) {
				sysUserinfo.setCustomerNickname(customerNickname);
			}
			if (!StringUtils.isBlank(customerQQ)) {
				sysUserinfo.setCustomerQQ(customerQQ);
			}
			if (serviceTypeList.contains(1) || serviceTypeList.contains(2)|| serviceTypeList.contains(3)) {
				if (!StringUtils.isBlank(customerPostNo)) {
					sysUserinfo.setCustomServiceId(customerPostNo);
				}
			}
		} else {
			sysUserinfo.setIsCustomer(0);
			sysUserinfo.setIsObjectCustomer(0);
			sysUserinfo.setIsInvestmentService(0);
		}
		sysUserinfo.setIsCustomerR(isCustomerR);
		sysUserinfo.setMsgLoginFlag(msgFlag);
		sysUserinfo.setIpLoginFlag(ipFlag);
		sysUserinfo.setIpLoginAddress(nIp);
		sysUserService.update(sysUserinfo);
		if (sysUserinfo.getUsertype() > 0) {
			try {
				sysUserService.updateUserRole(sysUserinfo.getUserId(),userinfo.getRoleids());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 操作成功
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(
				cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
		backUserLogService.addLog(u.getUserId(),
				RequestUtils.getUserIp(request),
				BackUserLogService.ADMIN_UPDATE_INFO, u.getFullname()
						+ "更改了管理员账号为" + sysUserinfo.getFullname() + "的账号信息");
		return new MessageBean(true, "操作成功" + msg.toString());
	}

	/**
	 * 用户修改密码
	 * 
	 * @param request
	 * @return 提示
	 */
	@RequestMapping(value = "/userinfo_changePwd")
	@ResponseBody
	public MessageBean userinfoChangePwd(HttpServletRequest request) {
		String oldPwd = RequestUtils.getParameter(request, "oldPwd", "");
		String newpwd = RequestUtils.getParameter(request, "cpwd", "");
		String rePassword = RequestUtils
				.getParameter(request, "rePassword", "");

		if (!(newpwd.equals(rePassword))) {
			return new MessageBean(false, "两次输入的新密码不一致");
		}
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(
				cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);

		if (u == null) {
			return new MessageBean(false, "请先登录再操作");
		}
		int i = sysUserService.updatePwd(u.getUserId(), oldPwd, newpwd);

		if (i == 0) {
			// 操作成功
			backUserLogService.addLog(u.getUserId(),
					RequestUtils.getUserIp(request),
					BackUserLogService.ADMIN_UPDATE_PASSWORD, u.getFullname()
							+ "更改了账号登陆密码");
			// Map<String, String> map = BackUserLogUtil.findUserInfo(request);
			// backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
			// map.get(BackUserLogUtil.USER_IP),
			// BackUserLogService.ADMIN_UPDATE_PASSWORD,
			// "【修改管理员密码】 管理员账号ID：" + u.getUserId() + "管理员账号:" +
			// u.getFullname());
			return new MessageBean(true, "密码修改成功");
		} else {
			// 操作失败
			return new MessageBean(false, "原密码不正确");
		}

	}

	/**
	 * 根据ID查询客服详细信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findByCustomerId")
	@ResponseBody
	public SysUserinfo findByCustomerId(HttpServletRequest request) {
		int customerId = RequestUtils.getParameterInt(request, "customerId", 0);
		SysUserinfo customer = sysUserService.findById(customerId);
		request.getSession().setAttribute(GlobalsKeys.ADMIN_KEY, customer);
		return customer;
	}
	
	@RequestMapping(value = "/system/userInfoImport")
	@ResponseBody
	public String kefuOrderDisposeRuleSub2(HttpServletRequest request, MultipartFile excel,Model model){
		int count=0;
		if (excel == null) {
			model.addAttribute("error", "上传文件为空");
			return "上传文件为空";
		}
		
		String kefuitem= RequestUtils.getParameter(request, "kefuItemName", "");
		int roleid=RequestUtils.getParameterInt(request, "roleid",0);
		long filesize=excel.getSize();
		if(filesize==0){
			return "上传文件为空";
		}
		try {
			List<String[]> datasList = new ArrayList<String[]>();
			HSSFWorkbook workBook = new HSSFWorkbook(excel.getInputStream());
			// 获取Excel的sheet数量
			// Integer sheetNum = workBook.getNumberOfSheets();
			HSSFSheet sheet = workBook.getSheetAt(0);
			if (sheet == null) {
				return "";
			}
			// 获取Sheet里面的Row数量
			Integer rowNum =sheet.getLastRowNum()+1;
			for (int j = 1; j < rowNum; j++) {
				HSSFRow row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				Integer cellNum =row.getLastCellNum()+1;
				String[] datas = new String[cellNum];
				for (int k = 0; k < cellNum; k++) {
					HSSFCell cell = row.getCell((short) k);
					if (cell == null) {
						continue;
					}
					if (cell != null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String cellValue = "";
						int cellValueType = cell.getCellType();
						if (cellValueType == cell.CELL_TYPE_STRING) {
							cellValue = cell.getStringCellValue();
						}
						if (cellValueType == cell.CELL_TYPE_NUMERIC) {
							Double number = cell.getNumericCellValue();

							cellValue = cell.getNumericCellValue() + "";
						}
						datas[k] = cellValue;
					}
				}
				datasList.add(datas);
			}
			 count=sysUserService.saveUser(datasList, kefuitem, roleid);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "成功导入"+count+"条数据";
	}
	
	/**
	 * 新建客服同步到新客服系统
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/userinfo_sync")
	@ResponseBody
	public MessageBean userinfoSync(HttpServletRequest request) {
		try {
			int kefuId = RequestUtils.getParameterInt(request, "kefuId", 0);
			JSONObject json = new JSONObject();
			ISysUserinfoService iSysUserinfoService = SpringFactory.getBean("SysUserinfoService");
			cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(kefuId);
			if(null == newu){
				json.put("code",-2);
				json.put("msg", "用户不存在");
				json.put("data", "");
				return new MessageBean(false, json.get("msg").toString());
			}
			JSONObject djson = JSONObject.fromObject(newu);
			String kefuUid = djson.getString("userId");//客服ID
			String kefuNickName = djson.getString("customerNickname");//客服昵称
			String customServiceId = djson.getString("customServiceId");//岗位号
			String onlineStatus = djson.getString("onlineStatus");
			String isOnDuty = djson.getString("isOnDuty");
			String loginName = djson.getString("loginid");//登录账号
			String isCustomer = djson.getString("isCustomer");//是否客服
			String isObjectCustomer = djson.getString("isObjectCustomer");//是否投咨
			String isInvestmentService = djson.getString("isInvestmentService");//是否物服
			String realName = djson.getString("fullname");//真实姓名
			String headImg = djson.getString("headImg");
			String qq = djson.getString("customerQQ")==null?"":djson.getString("customerQQ");
//			String date = "{\"kefuUid\":\""+kefuUid+"\",\"kefuNickName\":\""+kefuNickName+"\",\"customServiceId\":\""+customServiceId+"\",\"isOnDuty\":0,\"loginName\":"+loginName+"\"isCustomer\":"+isCustomer+"\"isObjectCustomer\":"+isObjectCustomer+"\"isInvestmentService\":"+isInvestmentService+"\"realName\":"+realName+"\"kefu_group\":2}";
//			String http = "http://localhost:8080/kefu_work_sys/crontabjob/syn_kefu_info.jsp";
			//List<SysParameter> val = sysParameterService.listByParaCode("SYS_USERINFO_SYNC");
			String http = Config.getValue("kefu.base.url")+"crontabjob/syn_kefu_info.jsp";
			Map<String, String> m = new HashMap<String, String>();
			m.put("kefuUid", kefuUid);
			m.put("kefuNickName", kefuNickName);
			m.put("customServiceId", customServiceId);
			m.put("loginName", loginName);
			m.put("isCustomer", isCustomer);
			m.put("isObjectCustomer", isObjectCustomer);
			m.put("isInvestmentService", isInvestmentService);
			m.put("realName", realName);
			m.put("isOnDuty", isOnDuty);
			m.put("onlineStatus", onlineStatus);
			m.put("headImg", headImg);
			m.put("qq", qq);
			String msg = HttpRequestUtil.dohttpRequestByPost(http, m);
			return new MessageBean(true, msg);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageBean(false, "同步出现异常!");
		}
	}
	
	/**
	 * 用户列表（查询，不能修改）
	 * 
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/userinfoShowList")
	public String userInfoShowList(Model model) {
		List<SysRole> roleList = sysRoleService.findAllValid();
		model.addAttribute("roleList", roleList);
		return "system/userinfoShowList";
	}
}
