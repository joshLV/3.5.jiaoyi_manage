//package cn.jugame.admin.web.controller;
//
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import cn.jugame.util.Cache;
//import cn.jugame.util.DateUtils;
//import cn.jugame.vo.SysUserinfo;
//import cn.jugame.web.util.GlobalsKeys;
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.localmessage.service.LocalMessageService;
//import cn.juhaowan.log.service.BackUserLogService;
//import cn.juhaowan.member.service.MemberService;
//import cn.juhaowan.member.vo.Member;
//import cn.juhaowan.member.vo.MemberRealinfo;
//
///**
// * 解除验证码锁定
// * @author chch
// *
// */
//@Controller
//public class DynamicPassUnlockController {
//	
//	Logger logger = LoggerFactory.getLogger(DynamicPassUnlockController.class);
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private LocalMessageService localMessageService;
//
//	@Autowired
//	private BackUserLogService backUserLogService;
//	
//	/**
//	 * 通过手机号获取用户信息
//	 * @param request
//	 * @param model
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/dynamicPassUnlock")
//	public String unlockInfo(HttpServletRequest request, Model model) {
//		String telphone= request.getParameter("telphone");
//			if (!StringUtils.isBlank(telphone)) {
//				model.addAttribute("telphone", telphone);
//				Member m = new Member();
//				MemberRealinfo mr = new MemberRealinfo();
//				m=memberService.findMemberByMobile(telphone);
//
//				if (null != m) {
//					mr = memberService.findRealInfoByUid(m.getUid());
//					model.addAttribute("platname", m.getLoginName());
//					model.addAttribute("uId", m.getUid());
//					//登录密码情况
//					if(memberService.isLoginLock(m.getUid())){
//						model.addAttribute("loginStatus", "登录密码被锁定");
//					}else {
//						model.addAttribute("loginStatus", "登录密码正常");
//					}
//					//支付密码情况
//					if(null == m.getPayPasswd()){
//						model.addAttribute("payStatus", "未设置支付密码");
//					}else {
//						if(memberService.isPayLock(m.getUid())) {
//							model.addAttribute("payStatus", "支付密码被锁定");
//						}else {
//							model.addAttribute("payStatus", "支付密码正常");
//						}
//					}
//				}
//	
//				if (null != mr && null != mr.getRealName()) {
//					model.addAttribute("realname", mr.getRealName());
//			}
//		} else {
//					model.addAttribute("telphone", "");
//		}
//     
//			return "/member/dynamicPassUnlock";
//	}
//	
//	@RequestMapping(value="/member/unlockDynamicPass")
//	@ResponseBody
//	public MessageBean unlockHandler(HttpServletRequest request, Model model) {
//	String telphone = RequestUtils.getParameter(request, "telphone", "");
//	if (telphone == "") {
//		return new MessageBean(false, "请输入用户手机号");
//	}
//	Member m = memberService.findMemberByMobile(telphone);
//	int uId=0;
//	if(m!=null) {
//		uId=m.getUid();	
//	}
//	else {
//		return new MessageBean(false, "该用户不存在");
//	}
//	String cacheKey = "sms/vcode/time/" + telphone + "/" + DateUtils.getDateString();
//	logger.info("解除手机号锁定cachekey ==" + cacheKey);
////	if(memberService.isPayLock(uId) == false){
////		return new MessageBean(false, "该用户的支付密码没有被锁定");
////	}
//	Cache.delete(cacheKey);
//
//	SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
//	// 发站内信
//	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	 String sendTime = sdf.format(new Date());
//	 
//	 localMessageService.sendMessageToUser(uId, "尊敬的用户,您好,您的验证码已经解锁。"
//	 + "如有疑问请立即联系我们【8868】", "8868的验证码解锁成功", sendTime);
//
//	// 记录日志
//	backUserLogService.addLog(u.getUserId(),
//			RequestUtils.getUserIp(request),
//			backUserLogService.MEMBER_DYNAMIC_PASS_UNLOCK,
//			"会员验证码解锁：操作ID:" + u.getUserId() + "管理员:" + u.getFullname() + "用户ID:" + uId + "]");
//	return new MessageBean(true, "用户验证码解锁成功");
//    }
//}
