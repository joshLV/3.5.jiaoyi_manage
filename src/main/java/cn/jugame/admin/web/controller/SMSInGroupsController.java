package cn.jugame.admin.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.web.util.MessageBean;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.sms.service.SMSService;

/**
 * 短信群发
 * 
 * @author APXer
 * 
 */
@Controller
public class SMSInGroupsController {
	Logger logger = LoggerFactory.getLogger(SMSInGroupsController.class);
	@Autowired
	private SMSService smsService;
	@Autowired
	private BackUserLogService backUserLogService;

	/**
	 * 短信群发页面
	 * 
	 * @param request
	 *            请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "sms/showSMSInGroupsView")
	public String showSMSInGroupsView(HttpServletRequest request) {
		return "sms/smsInGroups";
	}

	/**
	 * 短信群发功能
	 * 
	 * @param request
	 *            请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "sms/sMSInGroups")
	@ResponseBody
	public MessageBean sMSInGroups(HttpServletRequest request) {
		String phoneNumbers = request.getParameter("phoneNumbers");
		String smsContent = request.getParameter("smsContent");
		if (StringUtils.isBlank(phoneNumbers)) {
			return new MessageBean(false, "请填写正确的手机号码");
		}
		if (StringUtils.isBlank(smsContent)) {
			return new MessageBean(false, "请填写短信内容");
		}
		if (smsContent.length() > 70) {
			return new MessageBean(false, "请填写短信内容长度不超过70个字");
		}
		String[] phoneNumberArr = phoneNumbers.split("\n");
		if (phoneNumberArr.length > 100) {
			return new MessageBean(false, "最多只能群发100个手机号码");
		}
		int successAmount = 0;
		int failAmount = 0;
		StringBuffer failurePhoneNumber = new StringBuffer();
		for (int i = 0; i < phoneNumberArr.length; i++) {
			if (phoneNumberArr[i].length() == 11 && StringUtils.isNumeric(phoneNumberArr[i])) {
				int result = smsService.sendSms(phoneNumberArr[i].trim(), smsContent);
				if (result == 0) {
					successAmount++;
				} else {
					failAmount++;
					failurePhoneNumber.append(phoneNumberArr[i]).append(";");
				}
			}
		}
		logger.info("短信群发号码：" + phoneNumbers.replaceAll("\n", ",") + "\n短信内容：" + smsContent + "\n发送失败号码："
				+ failurePhoneNumber.toString());
		backUserLogService.addLog(0, request.getRemoteAddr(), BackUserLogService.SMS_IN_GROUPS, "发送短息数量："
				+ phoneNumberArr.length);
		return new MessageBean(true, "短信发送成功条数：" + successAmount + "\n短信发送失败条数：" + failAmount + "\n失败手机号: "
				+ failurePhoneNumber.toString());

	}

}
