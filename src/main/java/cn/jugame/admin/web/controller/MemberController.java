//package cn.jugame.admin.web.controller;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import cn.jugame.admin.web.form.MemberAccountLogForm;
//import cn.jugame.admin.web.form.MemberForm;
//import cn.jugame.admin.web.form.MemberRealinfoForm;
//import cn.jugame.util.DES;
//import cn.jugame.util.ExtBeanUtils;
//import cn.jugame.util.PageInfo;
//import cn.jugame.util.Sundry;
//import cn.jugame.vo.SysUserinfo;
//import cn.jugame.web.util.DateEditor;
//import cn.jugame.web.util.GlobalsKeys;
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.RequestUtils;
//import cn.jugame.yeepay.api.YeePayApi;
//import cn.jugame.yeepay.api.message.IRespMsg;
//import cn.juhaowan.localmessage.service.LocalMessageService;
//import cn.juhaowan.log.service.BackUserLogService;
//import cn.juhaowan.member.dao.MemberDepositDao;
//import cn.juhaowan.member.service.MemberAccountService;
//import cn.juhaowan.member.service.MemberBlackListService;
//import cn.juhaowan.member.service.MemberService;
//import cn.juhaowan.member.service.MemberServiceService;
//import cn.juhaowan.member.service.MemberTakeMoneyService;
//import cn.juhaowan.member.vo.Member;
//import cn.juhaowan.member.vo.MemberAccountLog;
//import cn.juhaowan.member.vo.MemberDeposit;
//import cn.juhaowan.member.vo.MemberRealinfo;
//import cn.juhaowan.member.vo.MemberTakeMoneyLog;
//
///**
// * 用户管理
// * 
// * @author Administrator
// * 
// */
//@Controller
//public class MemberController {
//	Logger logger = LoggerFactory.getLogger(MemberController.class);
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private MemberAccountService memberAccountService;
//
//	@Autowired
//	private MemberTakeMoneyService memberTakeMoneyService;
//
//	@Autowired
//	private BackUserLogServWice backUserLogService;
//
//	@Autowired
//	private MemberBlackListService memberBlackListService;
//
//	@Autowired
//	private LocalMessageService localMessageService;
//
//	@Autowired
//	private MemberServiceService memberServiceService;
//
//	/**
//	 * 从资源文件获取图片上传地址
//	 */
//	// 身份证图片保存地址
//	private static String IDCARDIMGURL;
//	static {
//		Properties pro = new Properties();
//		try {
//			pro.load(new FileInputStream(MemberController.class.getClassLoader().getResource("resources.properties").getPath()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		IDCARDIMGURL = pro.getProperty("idimagebaseurl");
//	}
//
//	/**
//	 * 会员列表
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/memberList")
//	public String memberList(HttpServletRequest request, Model model) {
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		model.addAttribute("uid", uid);
//		// 客服单点登录 设置页面列表条数为25
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		if (null != u && u.getUsertype() == 3) {
//			model.addAttribute("pageList", 25);
//		}
//		return "member/memberList";
//	}
//
//	/**
//	 * 会员列表数据
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/memberList_json")
//	public String memberListJson(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "uid"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc");
//		// 客服单点登录 设置页面列表条数为25
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		if (null != u && u.getUsertype() == 3) {
//			pageSize = 25;
//		}
//		// 搜索条件
//		Map<String, Object> condition = new HashMap<String, Object>();
//		String btime = RequestUtils.getParameter(request, "btime", "");
//		String etime = RequestUtils.getParameter(request, "etime", "");
//		String sn = RequestUtils.getParameter(request, "sn", "");
//		String userType = RequestUtils.getParameter(request, "user_type", "");
//
//		String loginbtime = RequestUtils.getParameter(request, "loginbtime", "");
//		String loginetime = RequestUtils.getParameter(request, "loginetime", "");
//		String loginqq = RequestUtils.getParameter(request, "qqLogin", "");
//		String userTerrace = RequestUtils.getParameter(request, "userTerrace", "");
//
//		String mobile = request.getParameter("telephone");
//		String loginname = request.getParameter("loginname");
//
//		String loginSearch = "";
//
//		if (!StringUtils.isBlank(loginqq)) {
//			loginSearch = "&$@_QQ_" + loginqq.trim();
//		} else {
//			loginSearch = loginname;
//		}
//
//		String tempuid = "0";
//		tempuid = request.getParameter("uid");
//
//		int uid = 0;
//		try {
//			uid = Integer.parseInt(tempuid);
//		} catch (NumberFormatException e) {
//			uid = 2;
//		}
//
//		condition.put("login_name", loginSearch);
//
//		condition.put("mobile", mobile);
//		if (null != btime && !"".equals(btime)) {
//			condition.put("beginTime", btime);
//			model.addAttribute("btime", btime);
//		}
//		if (null != etime && !"".equals(etime)) {
//			condition.put("endTime", etime + " 23:59:59");
//			model.addAttribute("etime", etime);
//		}
//
//		if (null != loginbtime && !"".equals(loginbtime)) {
//			condition.put("loginbtime", loginbtime);
//			model.addAttribute("loginbtime", loginbtime);
//		}
//
//		if (null != loginetime && !"".equals(loginetime)) {
//			condition.put("loginetime", loginetime + " 23:59:59");
//			model.addAttribute("loginetime", loginetime);
//		}
//
//		if (uid > 0) {
//			condition.put("uid", tempuid);
//		}
//		if (null != sn && !"".equals(sn)) {
//			condition.put("sn", sn);
//		}
//		if (null != userType && !"".equals(userType)) {
//			condition.put("user_type", userType);
//		}
//		if(null!= userTerrace && !"".equals(userTerrace)){
//			condition.put("userTerrace", userTerrace.trim());
//		}
//		
//
//		PageInfo<Member> pageInfo = memberService.queryMemberList(condition, pageSize, pageNo, sort, order);
//		if (null == pageInfo) {
//			return null;
//		}
//		if (null != pageInfo.getItems()) {
//			for (int i = 0; i < pageInfo.getItems().size(); i++) {
//				if (memberBlackListService.isInBlackList(pageInfo.getItems().get(i).getUid())) {
//					pageInfo.getItems().get(i).setIsBlackList(0);
//				} else {
//					pageInfo.getItems().get(i).setIsBlackList(1);
//				}
//			}
//
//		}
//
//		model.addAttribute("pageInfo", pageInfo);
//
//		return "member/memberList_json";
//	}
//
//	/**
//	 * 查询条件 选择用户信息
//	 * 
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/memberList_select")
//	public String memberListSelect(Model model) {
//		return "member/memberList_select";
//	}
//
//	/**
//	 * 用户详情
//	 * 
//	 * @param re
//	 * @param model
//	 * @return 会员信息
//	 */
//	@RequestMapping(value = "/member/member_json")
//	@ResponseBody
//	public MemberForm memberJson(HttpServletRequest re, Model model) {
//		int uid = RequestUtils.getParameterInt(re, "uid", 0);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		// 格式化doble类型的数据
//		DecimalFormat df = new DecimalFormat("##.00");
//		Member member = new Member();
//		MemberForm mform = new MemberForm();
//		member = memberService.findMemberByUid_back(uid);
//		if (null == member) {
//			return null;
//		}
//
//		ExtBeanUtils.copyProperties(mform, member);
//		MemberRealinfo memberRealinfo = new MemberRealinfo();
//		memberRealinfo = memberService.findRealInfoByUid(uid);
//
//		// 实名
//		String realname = "暂无";
//		if (null != memberRealinfo && null != memberRealinfo.getRealName()) {
//			realname = memberRealinfo.getRealName();
//			mform.setRealname(realname);
//		}
//
//		// 实名状态
//		if (null != memberRealinfo) {
//			mform.setStatusMse(memberRealinfo.getStatus());
//		}
//
//		// 查询余额
//		// 包括 现金和非现金账户
//		double balance = memberAccountService.queryBalance(uid);
//		mform.setBalance(balance);
//		// 现金余额 balance1
//		double balance1 = memberAccountService.queryCanTakeMoneyAmount(uid);
//		mform.setBalance1(balance1);
//		// 非现金余额 balance2
//		double balance2 = memberAccountService.queryCanNotTakeMoneyAmount(uid);
//		mform.setBalance2(balance2);
//		// 押金总额 deposit_amount
//		double depositAmount = 0;
//		List<MemberDeposit> depositList = new ArrayList<MemberDeposit>();
//		depositList = memberAccountService.queryDepositList(uid);
//		if (depositList.size() > 0) {
//			for (int i = 0; i < depositList.size(); i++) {
//				depositAmount += depositList.get(i).getDepositAmount();
//			}
//			// 格式化押金
//			String depositTemp = df.format(depositAmount);
//			mform.setDepositAmount(Double.parseDouble(depositTemp));
//		}
//		// 生日
//		String birthday = null;
//		if (null != member.getBirthday()) {
//			birthday = sdf.format(member.getBirthday());
//			mform.setInfoBirthday(birthday);
//		}
//		// 创建时间
//		if (null != member.getCreateTime()) {
//			mform.setInfoCreateTime(sdf.format(member.getCreateTime()));
//		}
//		// 最后登录时间
//		if (null != member.getLastTime()) {
//			mform.setInfoLastTime(sdf.format(member.getLastTime()));
//		}
//		// 真正剩余时间
//		if (null != member.getUnlockTime() && member.getUnlockTime().getTime() > new Date().getTime()) {
//
//			if (member.getStatus() == 2) {
//				mform.setInfoUnlockTime(sdf.format(member.getUnlockTime()));
//				long realLockDay = (member.getUnlockTime().getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000);
//				mform.setRealLockDay(realLockDay + 1);
//			}
//		}
//
//		// 登录密码情况
//		if (memberService.isLoginLock(uid)) {
//			mform.setLoginStatus("登录密码被锁定");
//		} else {
//			mform.setLoginStatus("登录密码正常");
//		}
//
//		// 支付密码情况
//		if (null == member.getPayPasswd()) {
//			mform.setPayStatus("未设置支付密码");
//		} else {
//			if (memberService.isPayLock(uid)) {
//				mform.setPayStatus("支付密码被锁定");
//			} else {
//				mform.setPayStatus("支付密码正常");
//			}
//		}
//
//		return mform;
//	}
//
//	/**
//	 * 修改用户详情
//	 * 
//	 * @param m
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/member/member_edit")
//	@ResponseBody
//	public MessageBean memberEdit(Member m) {
//		if (m.getUid() < 0) {
//			return new MessageBean(false, "没有相干数据");
//		}
//		Member member = memberService.findMemberByUid(m.getUid());
//		if (null == member) {
//			return new MessageBean(false, "没有相关数据");
//		}
//		try {
//			int i = memberService.updateMemberInfo(member);
//			if (i > 0) {
//				return new MessageBean(false, "修改操作失败");
//			}
//		} catch (Exception e) {
//			return new MessageBean(false, "修改操作失败");
//		}
//
//		return new MessageBean();
//	}
//
//	/**
//	 * 用户账号解锁/锁定
//	 * 
//	 * @param re
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/member/updateMemStatus")
//	@ResponseBody
//	public MessageBean memberStatusUp(HttpServletRequest re) {
//
//		int uid = RequestUtils.getParameterInt(re, "uid", 0);
//		int lockday = RequestUtils.getParameterInt(re, "lockday", 0);
//		String remark = RequestUtils.getParameter(re, "remark", "");
//		int status2 = RequestUtils.getParameterInt(re, "lock_type", 0);
//
//		Member m = new Member();
//		m = memberService.findMemberByUid_back(uid);
//
//		if (null == m) {
//			return null;
//		}
//		SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		if (status2 == m.getStatus()) {
//			return new MessageBean(false, "状态不用修改");
//		} else if (status2 == 1) {
//			int i = memberService.updateMemberStatus(uid, status2, remark);
//			if (i == 0) {
//				backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), backUserLogService.MEMBER_LIST_UNLOCK, "【会员账号解锁定】：ID为" + u.getUserId() + "的管理员" + u.getFullname() + "对账号为:" + uid + "的用户账号进行了解锁定[用户备注：" + remark + "]");
//
//				return new MessageBean(true, m.getLoginName() + "的账号状态修改为正常");
//			} else {
//				return new MessageBean(false, "修改失败");
//			}
//		} else {
//
//			int i = memberService.lockMember(uid, status2, lockday, remark);
//
//			if (i == 0) {
//				backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), backUserLogService.MEMBER_LIST_LOCK, "【会员账号锁定】：ID为" + u.getUserId() + "的管理员" + u.getFullname() + "对账号为:" + uid + "的用户账号进行了更改[用户备注：" + remark + "]");
//				return new MessageBean(true, "修改成功");
//
//			} else {
//
//				return new MessageBean(false, "修改失败");
//			}
//
//		}
//
//	}
//
//	/**
//	 * 用户实名信息列表
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/memberrealinfoList")
//	public String memberRealInfoList(HttpServletRequest request, Model model) {
//
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//
//		model.addAttribute("uuuuid", uid);
//		// 客服单点登录 设置页面列表条数为25
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		if (null != u && u.getUsertype() == 3) {
//			model.addAttribute("pageList", 25);
//		}
//		return "member/memberrealinfoList";
//	}
//
//	/**
//	 * 用户实名信息
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/member_realinfoList_json")
//	public String memberRealInfoJson(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "uid"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc");
//		// 客服单点登录 设置页面列表条数为25
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		if (null != u && u.getUsertype() == 3) {
//			pageSize = 25;
//		}
//		// 搜索条件
//		Map<String, Object> condition = new HashMap<String, Object>();
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		String idNum = request.getParameter("id_num");
//		String realname = request.getParameter("realname");
//		int status = RequestUtils.getParameterInt(request, "status_search", -1);
//
//		if (uid > 0) {
//			String uidTemp = String.valueOf(uid);
//			condition.put("uid", uidTemp);
//		}
//		if ("".equals(idNum) || null != idNum) {
//			condition.put("id_num", idNum);
//		}
//		if ("".equals(realname) || null != realname) {
//			condition.put("real_name", realname);
//		}
//		if (status >= 0) {
//			String statusTemp = String.valueOf(status);
//			condition.put("status", statusTemp);
//		}
//
//		PageInfo<MemberRealinfo> pageInfo = memberService.queryMemberRealInfoList(condition, pageSize, pageNo, sort, order);
//		if (null == pageInfo) {
//			return null;
//		}
//		if (null != pageInfo.getItems()) {
//			for (int i = 0; i < pageInfo.getItems().size(); i++) {
//				String bankName = "";
//				try {
//					bankName = cn.jugame.util.Sundry.bankIDTobankName(pageInfo.getItems().get(i).getBankId());
//				} catch (Exception e) {
//				}
//				pageInfo.getItems().get(i).setBankId(bankName);
//
//			}
//		}
//
//		model.addAttribute("pageInfo", pageInfo);
//
//		return "member/member_realinfoList_json";
//
//	}
//
//	/**
//	 * 实名认证用户信息
//	 * 
//	 * @param re
//	 * @param model
//	 * @return 实名信息
//	 */
//	@RequestMapping(value = "/member/memberreal_json")
//	@ResponseBody
//	public MemberRealinfoForm memberRealJson(HttpServletRequest re, HttpServletResponse response, Model model) {
//		int uid = RequestUtils.getParameterInt(re, "uid", 0);
//
//		MemberRealinfo m = new MemberRealinfo();
//		MemberRealinfoForm mForm = new MemberRealinfoForm();
//		m = memberService.findRealInfoByUid(uid);
//		if (null == m) {
//			return null;
//		}
//		SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		ExtBeanUtils.copyProperties(mForm, m);
//
//		// 打款时间
//		String payTime = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (null != m.getPayMoneyTime()) {
//			payTime = sdf.format(m.getPayMoneyTime());
//			mForm.setInfoPayMoneyTime(payTime);
//		}
//		// 用户登录账号
//		String loginid = "";
//		Member mer = new Member();
//		mer = memberService.findMemberByUid(uid);
//		if (null != mer && null != mer.getLoginName()) {
//			loginid = mer.getLoginName();
//			mForm.setInfoLoginid(loginid);
//		}
//
//		// 用户id加密
//		// http://192.168.0.136:8080/9.1.JIAOYI_WEB/aljksdhflkasdhflk1?alskdhf=VN1VJ2Um1XyIWPGKH9JjTE8ylIN9wXBehRYxAM9FYE4=
//		String userIDCode = "";
//		try {
//			userIDCode = DES.encode("557sdfsdfsdfsdfsdfsdfs83" + String.valueOf(uid), Sundry.READ_IDCARD_KEY);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 身份证复印件图片url正面
//		String imgUrl = IDCARDIMGURL + "aljksdhflkasdhflk1?alskdhf=" + userIDCode;
//		if (null != m.getIdImgUrl() && !"".equals(m.getIdImgUrl())) {
//			mForm.setIdImgUrl(imgUrl);
//
//		}
//		// 身份证复印件图片反面
//		String imgUrlBack = IDCARDIMGURL + "aljksdhflkasdhflk2?alskdhf=" + userIDCode;
//		if (null != m.getIdImgUrl2() && !"".equals(m.getIdImgUrl2())) {
//			mForm.setIdImgUrl2(imgUrlBack);
//		}
//
//		// 银行代码->银行名称
//		String bankName = "";
//		if (null != m.getBankId()) {
//			bankName = cn.jugame.util.Sundry.bankIDTobankName(m.getBankId());
//			mForm.setBankName(bankName);
//
//			// 是否快速认证通过 1 可以快速 0 不可以快速
//			int auth = 0;
//			// 实名申请时间离现在的长度
//			int stime = 0;
//			auth = memberService.qeuryBankInfoAuth(m.getBankId());
//			if (null != m.getCreateTime()) {
//				stime = Integer.parseInt(DateEditor.getMinuters(new Date(), m.getCreateTime()));
//			}
//			if (auth == 1 && stime < 30) {
//				mForm.setAuthBankinfo(true);
//			} else {
//				mForm.setAuthBankinfo(false);
//			}
//		}
//
//		// 创建时间
//		String ctime = "";
//		if (null != m.getCreateTime()) {
//			ctime = sdf.format(m.getCreateTime());
//			mForm.setCreateTimeShow(ctime);
//		}
//
//		if (null != m.getBankCardNum() && !("".equals(m.getBankCardNum()))) {
//			// 解密银行号码
//			String bankCard = Sundry.bankAccountDecode(m.getBankCardNum());
//			try {
//				bankCard = "***********" + bankCard.substring(bankCard.length() - 4, bankCard.length());
//			} catch (Exception e) {
//				logger.info("身份证异常");
//			}
//
//			if (u.getUsertype() != 0) {
//				// 非管理员权限只能看到银行卡后四位
//				mForm.setBankCardNum(bankCard);
//			} else {
//				// 管理员权限才能看到完整的银行卡
//				mForm.setBankCardNum(Sundry.bankAccountDecode(m.getBankCardNum()));
//			}
//		}
//
//		// 分拆省市
//		String provid = "0";
//		String city = "0";
//		try {
//			if (null != m.getProvincesCode()) {
//				if (m.getProvincesCode().contains(",")) {
//					provid = m.getProvincesCode().split(",")[0];
//					city = m.getProvincesCode().split(",")[1];
//				}
//			}
//		} catch (Exception e) {
//			provid = "0";
//			city = "0";
//		}
//		mForm.setProvid(provid);
//		mForm.setCityid(city);
//
//		// 解密银行号码
//		String bankCard = Sundry.bankAccountDecode(m.getBankCardNum());
//		String codebankCard = "";
//		if (bankCard != null) {
//			codebankCard = "***********" + bankCard.substring(bankCard.length() - 4, bankCard.length());
//		}
//		// 修改权限开关
//		if (u.getUsertype() != 0) {
//			// 非管理员权限只能看到银行卡后四位
//			boolean updateflag = false;
//			boolean banknumflag = false;
//			String banknumperCode = "cn.jugame.admin.web.controller.membercontroller.showbankcard";
//			String perCode = "cn.jugame.admin.web.controller.membercontroller.updaterealinfo";
//			try {
//				updateflag = RequestUtils.isAuthority(re, response, perCode);
//				banknumflag = RequestUtils.isAuthority(re, response, banknumperCode);
//			} catch (Exception e) {
//				updateflag = false;
//				banknumflag = false;
//			}
//			mForm.setModifyFlag(updateflag);
//			mForm.setAuthorityShowBK(banknumflag);
//			mForm.setBankCardNum(codebankCard);
//
//		} else {
//			// 管理员权限可以修改
//			mForm.setModifyFlag(true);
//			mForm.setAuthorityShowBK(true);
//			mForm.setBankCardNum(bankCard);
//		}
//
//		return mForm;
//
//	}
//
//	/**
//	 * 实名验证
//	 * 
//	 * @param re
//	 * @return 提示
//	 */
//	@RequestMapping(value = "/member/updateStatus")
//	@ResponseBody
//	public MessageBean memberRealInfoStatusUp(HttpServletRequest re) {
//
//		int uid = RequestUtils.getParameterInt(re, "uid", 0);
//		int status2 = RequestUtils.getParameterInt(re, "status2", -1);
//		SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		String resultRespose = "";
//		String resultRemark = "";
//		String remark = "";
//		try {
//			remark = re.getParameter("remark");
//		} catch (Exception e) {
//			remark = "";
//		}
//		MemberRealinfo m = new MemberRealinfo();
//		m = memberService.findRealInfoByUid(uid);
//		if (null == m) {
//			return null;
//		}
//
//		int updateFlag = 1;
//
//		if (status2 == 5) {
//			if (null == m.getBankCardNum()) {
//				return new MessageBean(false, "该用户身份证为空");
//			}
//			if (null == m.getRealName()) {
//				return new MessageBean(false, "该用户真实姓名为空");
//			}
//			if (null == m.getBankCardNum()) {
//				return new MessageBean(false, "该用户身份证为空");
//			}
//
//			// 易宝同步信息 SetAuthStatusRespMsg
//			// uid
//			String uuid = String.valueOf(m.getUid());
//			// 时间戳
//			String requestId = String.valueOf(System.currentTimeMillis());
//			// 银行卡
//			String bankCard = Sundry.bankAccountDecode(m.getBankCardNum());
//			// 银行名称
//			String branchName = cn.jugame.util.Sundry.bankIDTobankName(m.getBankId());
//			// 真实姓名
//			String realName = m.getRealName();
//			// 身份证
//			String idCard = m.getIdNum();
//			// 电话号码
//			String mobile = "";
//			// 支行信息
//			String branchinfo = m.getBranchInfo() == null ? "" : m.getBranchInfo();
//			String proviceAndCity = m.getProvincesCode();
//			// 省【选填】
//			String province = "";
//			// 城市【选填】
//			String city = "";
//			if (proviceAndCity.contains(",")) {
//				province = proviceAndCity.split(",")[0];
//				city = proviceAndCity.split(",")[1];
//			}
//			logger.info("=uuid===" + uuid);
//			logger.info("银行名称===" + branchName);
//			logger.info("银行卡号===" + bankCard);
//			logger.info("银行开户省===" + province);
//			logger.info("银行开户市===" + city);
//			logger.info("真实姓名===" + realName);
//			logger.info("身份证===" + idCard);
//			logger.info("电话号码===" + mobile);
//			logger.info("时间戳===" + requestId);
//			logger.info("支行信息==" + branchinfo);
//			IRespMsg result = null;
//			IRespMsg resultBank = null;
//			try {
//				result = YeePayApi.setAuthStatus(uuid, requestId, realName, "ID", idCard, "realinfo");
//				if (result.isSuccess()) {
//					logger.info(result + ", 调用易宝接口实名身份证信息成功");
//					resultBank = YeePayApi.setUpBankCard(uuid, requestId, bankCard, branchName, "", province, city, "", branchinfo);
//
//					if (resultBank.isSuccess()) {
//						logger.info(resultBank + ", 调用易宝接口实名身份证信息成功,设置银行卡信息成功");
//						resultRespose = "【会员实名认证】：管理员:" + u.getFullname() + " 会员UID:" + uid + " 结果：实名身份证和银行卡信息成功 [管理员备注：" + remark + "]";
//						resultRemark = "实名身份证和银行卡信息成功";
//					} else {
//						logger.info(resultBank + ", 实名身份证信息成功,设置银行卡失败");
//						resultRespose = "【会员实名认证】：管理员:" + u.getFullname() + " 会员UID:" + uid + " 结果：实名身份证成功和设置银行卡信息失败 [管理员备注：" + remark + "]";
//						resultRemark = "实名身份证成功，设置银行卡信息失败";
//					}
//					updateFlag = 0;
//				} else {
//					logger.error(uid + "后台实名认证调用易宝接口实名认证失败。");
//				}
//			} catch (IOException e) {
//				logger.error(uid + "设置用户实名信息失败,出错原因" + e);
//			}
//		} else if (status2 == 7) {
//			resultRespose = "【会员实名认证】：管理员:" + u.getFullname() + " 会员UID:" + uid + " 结果：实名认证不通过 [管理员备注：" + remark + "]";
//			resultRemark = "实名认证不通过";
//			updateFlag = 0;
//		} else {
//			return new MessageBean(false, "无此参数,请刷新重新操作.");
//		}
//		if (updateFlag == 0) {
//			int i = memberService.updateMemberRealInfoStatus(uid, status2, "操作备注：" + remark + " 结果：" + resultRemark);
//			if (i == 0) {
//				// 记录日志
//				backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), backUserLogService.REALNAME_AUTHENTICATION, resultRespose);
//				return new MessageBean(true, resultRemark);
//			} else {
//				return new MessageBean(false, "无此参数,请刷新重新操作.");
//			}
//		} else {
//			return new MessageBean(false, "后台实名认证调用易宝接口实名认证信息失败");
//		}
//
//	}
//
//	/**
//	 * 用户资金流水
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/accountLogList")
//	public String accountList(HttpServletRequest request, Model model) {
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		model.addAttribute("uuuuid", uid);
//		return "member/accountLogList";
//	}
//
//	/**
//	 * 资金流水分页查询
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//
//	@RequestMapping(value = "/member/accountLogList_json")
//	public String accountListJson(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc");
//		// 搜索条件
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		int type = RequestUtils.getParameterInt(request, "p_type", 0);
//		String btime = request.getParameter("btime");
//		String etime = request.getParameter("etime");
//		if (null != etime && !"".equals(etime)) {
//			etime = etime + " 23:59:59";
//		}
//		if (null != btime && !"".equals(btime)) {
//			btime = btime + " 00:00:00";
//		}
//		PageInfo<MemberAccountLog> pageInfo = memberAccountService.queryMemberAccountLogPageBack(uid, type, btime, etime, pageNo, pageSize, sort, order);
//		if (null == pageInfo) {
//			return null;
//		}
//		model.addAttribute("pageInfo", pageInfo);
//		return "member/accountLogList_json";
//	}
//
//	/**
//	 * 资金流水详细信息
//	 * 
//	 * @param re
//	 * @param model
//	 * @return 资金流水信息
//	 */
//	@RequestMapping(value = "/member/memberAccountLog_json")
//	@ResponseBody
//	public MemberAccountLogForm memberAccountLogJson(HttpServletRequest re, Model model) {
//		int id = RequestUtils.getParameterInt(re, "id", 0);
//		// 格式化doble类型的数据
//		DecimalFormat df = new DecimalFormat("##.00");
//		MemberAccountLogForm mForm = new MemberAccountLogForm();
//		MemberAccountLog m = memberAccountService.queryLogById(id);
//		if (null == m) {
//			return null;
//		}
//
//		ExtBeanUtils.copyProperties(mForm, m);
//
//		// 日志添加时间
//		String cTime = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (null != m.getCreateTime()) {
//			cTime = sdf.format(m.getCreateTime());
//			mForm.setInfoCreateTime(cTime);
//		}
//		// 用户登录账号
//		String loginid = "";
//		Member mer = new Member();
//		mer = memberService.findMemberByUid(m.getUid());
//		if (null != mer && null != mer.getLoginName()) {
//			loginid = mer.getLoginName();
//			mForm.setInfoUserName(loginid);
//		}
//		// 银行卡号md5 解密
//		if (null != m.getBankCardNo() && !("".equals(m.getBankCardNo()))) {
//			// 解密银行号码
//			String bankCard = Sundry.bankAccountDecode(m.getBankCardNo());
//			bankCard = "***********" + bankCard.substring(bankCard.length() - 4, bankCard.length());
//			SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//
//			if (u.getUsertype() != 0) {
//				// 非管理员权限只能看到银行卡后四位
//				mForm.setBankCardNo(bankCard);
//			} else {
//				// 管理员权限才能看到完整的银行卡
//				mForm.setBankCardNo(Sundry.bankAccountDecode(m.getBankCardNo()));
//			}
//		}
//
//		// 操作金额
//		if (m.getAmount() > 0) {
//			mForm.setAmount(Double.parseDouble(df.format(m.getAmount())));
//		}
//		// 手续费
//		if (m.getFee() > 0) {
//			mForm.setFee(Double.parseDouble(df.format(m.getFee())));
//		}
//		// 账户余额
//		if (m.getBalance() > 0) {
//			mForm.setBalance(Double.parseDouble(df.format(m.getBalance())));
//		}
//		// 总余额
//		if (m.getTotalBalance() > 0) {
//			mForm.setTotalBalance(Double.parseDouble(df.format(m.getTotalBalance())));
//		}
//
//		return mForm;
//
//	}
//
//	/**
//	 * 用户个人信息跳转
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转地址
//	 */
//	@RequestMapping(value = "/member/memberlink_json")
//	public String memberlinkJson(HttpServletRequest request, Model model) {
//		return "member/memberlink_json";
//	}
//
//	/**
//	 * 更改用户类型
//	 * 
//	 * @param request
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/member/memberTypeUpdate")
//	@ResponseBody
//	public MessageBean memberTypeUpdate(HttpServletRequest request) {
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		//int type = RequestUtils.getParameterInt(request, "type", 0);
//		int utype = RequestUtils.getParameterInt(request, "u_type_update", 1);
//		String remark = RequestUtils.getParameter(request, "usertyperemark", "");
//
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		Member m = memberService.findMemberByUid_back(uid);
//		if (null == m) {
//			return new MessageBean(false, "用户不存在");
//		}
//		if (utype == m.getUserType()) {
//			return new MessageBean(false, "用户状态不用更改");
//		}
//		if (null == remark || "".equals(remark)) {
//			return new MessageBean(false, "请填写操作备注");
//		}
//		if(utype == 0){
//			return new MessageBean(false,"请选择用户类型");
//		}
//		int i = memberService.updateUserType(uid, utype);
//		String typeName = "";
//		if (utype == 1) {
//			typeName = "普通用户";
//		} else if (utype == 2) {
//			typeName = "VIP用户";
//		}else if (utype == 3) {
//			typeName = "高级VIP用户";
//		} else {
//			typeName = "";
//		}
//		if (i == 1) {
//			backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(request), backUserLogService.MEMBER_TYPE_UPDATE, "ID为" + u.getUserId() + "的管理员:" + u.getFullname() + "更改了:" + uid + "的用户类型为：【" + typeName + "】 操作备注：" + remark);
//			return new MessageBean(true, "修改成功");
//		} else {
//
//			return new MessageBean(false, "修改失败");
//		}
//
//	}
//
//	/**
//	 * 更改用户押金
//	 * 
//	 * @param request
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/member/depositUpdate")
//	@ResponseBody
//	public MessageBean depositUpdate(HttpServletRequest request) {
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		double deposit;
//		String depositStr = RequestUtils.getParameter(request, "deposit_add", "0.0");
//		try {
//			deposit = Double.parseDouble(depositStr);
//		} catch (NumberFormatException e) {
//
//			deposit = 0.0;
//		}
//		String depositRemark = RequestUtils.getParameter(request, "deposit_remark", "");
//
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		int i = memberAccountService.addDeposit(uid, MemberDepositDao.DEPOSIT_TYPE_NORMAL, deposit, depositRemark);
//		if (i == 0) {
//			backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(request), backUserLogService.DEPOSIT_ADD, "增加会员押金：ID为" + u.getUserId() + "的管理员:" + u.getFullname() + "对账号为:" + uid + "的用户账号进行了增加押金操作,增加了押金：" + deposit);
//			double depositAmount = 0;
//			String depositTemp = "0";
//			// 格式化doble类型的数据
//			DecimalFormat df = new DecimalFormat("##.00");
//			List<MemberDeposit> depositList = new ArrayList<MemberDeposit>();
//			depositList = memberAccountService.queryDepositList(uid);
//			if (depositList.size() > 0) {
//				for (int j = 0; j < depositList.size(); j++) {
//					depositAmount += depositList.get(j).getDepositAmount();
//				}
//				// 格式化押金
//				depositTemp = df.format(depositAmount);
//			}
//			return new MessageBean(true, "修改成功:" + depositTemp);
//		} else {
//
//			return new MessageBean(false, "修改失败:0");
//		}
//
//	}
//
//	/**
//	 * 用户提现日志
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/takeMoneyLogList")
//	public String takeMoneyLogList(HttpServletRequest request, Model model) {
//		String takeMoneyId = RequestUtils.getParameter(request, "take_money_id", "");
//		model.addAttribute("take_money_id", takeMoneyId);
//		return "member/takeMoneyLogList";
//	}
//
//	/**
//	 * 用户提现日志分页查询
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//
//	@RequestMapping(value = "/member/takeMoneyLogList_json")
//	public String takeMoneyLogListJson(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc");
//		// 搜索条件
//		String orderId = RequestUtils.getParameter(request, "order_id", "");
//		String operatorUid = RequestUtils.getParameter(request, "operator_uid", "");
//		String takeMoneyId = RequestUtils.getParameter(request, "take_money_id", "");
//		String btime = request.getParameter("btime");
//		String etime = request.getParameter("etime");
//		Map<String, Object> condition = new HashMap<String, Object>();
//		if (null != orderId && !"".equals(orderId)) {
//			condition.put("order_id", orderId);
//			model.addAttribute("order_id", orderId);
//		}
//		if (null != operatorUid && !"".equals(operatorUid)) {
//			condition.put("operator_uid", operatorUid);
//			model.addAttribute("operator_uid", operatorUid);
//		}
//		if (null != btime && !"".equals(btime)) {
//			condition.put("beginTime", btime);
//			model.addAttribute("btime", btime);
//		}
//		if (null != etime && !"".equals(etime)) {
//			condition.put("endTime", etime + " 23:59:59");
//			model.addAttribute("etime", etime);
//		}
//		if (null != takeMoneyId && !"".equals(takeMoneyId)) {
//			condition.put("take_money_id", takeMoneyId);
//			model.addAttribute("take_money_id", takeMoneyId);
//		}
//
//		PageInfo<MemberTakeMoneyLog> pageInfo = memberTakeMoneyService.queryTakeMoneyrLogList(condition, pageSize, pageNo, sort, order);
//
//		if (null == pageInfo) {
//			return null;
//		}
//		model.addAttribute("pageInfo", pageInfo);
//		return "member/takeMoneyLogList_json";
//	}
//
//	/**
//	 * 实名信息更新
//	 * 
//	 * @param re
//	 * @return 提示
//	 */
//	@RequestMapping(value = "/member/updateRealInfo")
//	@ResponseBody
//	public MessageBean updateRealInfo(MemberRealinfoForm m, HttpServletRequest re) {
//		if (StringUtils.isBlank(m.getRealName())) {
//			return new MessageBean(false, "请填写用户真实姓名");
//		}
//		if (StringUtils.isBlank(m.getBankId())) {
//			return new MessageBean(false, "请填写开户行");
//		}
//		if (StringUtils.isBlank(m.getProvid())) {
//			return new MessageBean(false, "请填写开户地省份");
//		}
//		if (StringUtils.isBlank(m.getBankCardNum())) {
//			return new MessageBean(false, "请填写银行卡号");
//		}
//		if (StringUtils.isBlank(m.getIdNum())) {
//			return new MessageBean(false, "请填写身份证号");
//		}
//		MemberRealinfo real = memberService.findRealInfoByUid(m.getUid());
//		if (real.getStatus() != 5) {
//			return new MessageBean(false, "只有审核通过的状态才能修改用户实名信息");
//		}
//		int bankFlag = 0;
//		int idCardFlag = 0;
//		int changeFlag = real.getChangeFlag();
//		int setChangFlag = 0;
//		String newprovincesCode = m.getProvid() + "," + m.getCityid();
//		String newBankcardnum = "";
//		if (!m.getBankCardNum().contains("***")) {
//			newBankcardnum = Sundry.bankAccountEncrypt(m.getBankCardNum());
//		}
//
//		// 银行卡信息
//		if (!m.getBankId().equals(real.getBankId()) || !newprovincesCode.equals(real.getProvincesCode()) || !newBankcardnum.equals(real.getBankCardNum())) {
//			bankFlag = 1;
//		}
//		// 身份证信息
//		if (!m.getRealName().equals(real.getRealName()) || !m.getIdNum().equals(real.getIdNum())) {
//			idCardFlag = 1;
//		}
//		if (bankFlag + idCardFlag <= 0) {
//			return new MessageBean(false, "请填写更改后提交");
//		}
//
//		if (changeFlag == 0) {
//			if (idCardFlag == 1 && bankFlag == 1) {
//				setChangFlag = 3;
//			}
//			if (idCardFlag == 1 && bankFlag == 0) {
//				setChangFlag = 2;
//			}
//			if (idCardFlag == 0 && bankFlag == 1) {
//				setChangFlag = 1;
//			}
//		}
//		// 银行信息
//		if (changeFlag == 1) {
//			if (idCardFlag == 1 && bankFlag == 1) {
//				setChangFlag = 3;
//			}
//			if (idCardFlag == 1 && bankFlag == 0) {
//				setChangFlag = 3;
//			}
//			if (idCardFlag == 0 && bankFlag == 1) {
//				setChangFlag = 1;
//			}
//		}
//		// 实名信息
//		if (changeFlag == 2) {
//			if (idCardFlag == 1 && bankFlag == 1) {
//				setChangFlag = 3;
//			}
//			if (idCardFlag == 0 && bankFlag == 1) {
//				setChangFlag = 3;
//			}
//			if (idCardFlag == 1 && bankFlag == 0) {
//				setChangFlag = 2;
//			}
//		}
//		if (changeFlag == 3) {
//			setChangFlag = 3;
//		}
//
//		SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		real.setRealName(m.getRealName());
//		real.setBankId(m.getBankId());
//		real.setProvincesCode(newprovincesCode);
//		real.setIdNum(m.getIdNum());
//		if (!m.getBankCardNum().contains("***")) {
//			real.setBankCardNum(Sundry.bankAccountEncrypt(m.getBankCardNum()));
//		}
//		real.setRemark(m.getRemark());
//		real.setBankAddr(m.getBankAddr());
//
//		int i = memberService.inputRealNameInfo(real.getUid(), real);
//		if (i == 0) {
//			// 后台更新市民认证 修改状态
//			if (setChangFlag > 0) {
//				memberService.updateChangeflagByUid(m.getUid(), setChangFlag);
//			}
//			backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), backUserLogService.MEMBER_MODIFY_REALINFO, "实名信息修改：ID为" + u.getUserId() + "的管理员:" + u.getFullname() + "对账号为:" + m.getUid() + "的用户账号进行了修改实名信息操作");
//			return new MessageBean(true, "实名信息修改成功");
//		} else {
//			return new MessageBean(false, "实名信息修改失败");
//		}
//
//	}
//
//	/**
//	 * 查看用户银行卡信息
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 银行卡号
//	 */
//	@RequestMapping(value = "/member/showbankcard")
//	@ResponseBody
//	public Map<String, Object> showBankCard(HttpServletRequest request, Model model) {
//		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//		int uid = RequestUtils.getParameterInt(request, "uid", 0);
//		MemberRealinfo m = memberService.findRealInfoByUid(uid);
//		String result = "";
//		if (null != m) {
//			if (null != m.getBankCardNum()) {
//				result = Sundry.bankAccountDecode(m.getBankCardNum());
//			}
//		}
//		Map<String, Object> newMap = new HashMap<String, Object>();
//		newMap.put("showbanknum", result);
//		if (u.getUsertype() != 0) {
//			backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(request), backUserLogService.MEMBER_SHOW_BANKCARDNUM, "实名信息列表：ID为" + u.getUserId() + " 管理员:" + u.getFullname() + "查看了uid号为：" + m.getUid() + "的银行卡号");
//		}
//		return newMap;
//	}
//
//}
