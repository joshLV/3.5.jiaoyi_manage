package cn.jugame.admin.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.service.ISysParameterService;
import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.Config;
import cn.jugame.web.util.HttpRequestUtil;
import cn.jugame.web.util.MessageBean;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;

/**
 * @author APXer 
 * 
 */
@Controller
@RequestMapping(value = "/personnelManagement")
public class OnlineCustomerMonitorController {
	@Autowired
	private OnlineCustomerMonitorService onlineCustomerMonitorService;
	@Autowired
	private ISysUserinfoService iSysUserinfoService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ISysParameterService sysParameterService;

	Logger logger = Logger.getLogger(OnlineCustomerMonitorController.class);
	
	
	/**
	 * 状态变更(新)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "workStatusUpdate2")
	@ResponseBody
	public MessageBean workStatusUpdate2(HttpServletRequest request) {
		String kefuId = request.getParameter("csId");
		String workStatus = request.getParameter("workStatus");
		//新客服系统
		String url = "";
		try {
			url = Config.getValue("kefu.base.url")+"interface/kefu_onduty_status2.jsp?kefuId=" + kefuId + "&workStatus=" + workStatus;
		} catch (BeansException e) {
			return new MessageBean(false, "kefu.base.url参数没配置，请联系管理员");
		}
		logger.info("更新状态url："+url);
		String str = HttpRequestUtil.doHttpRequestByGet(url);
		logger.info("下班url："+url + ",result:" + str);
		String code = "-1";
		String msg = "";
		
		try {
			JSONObject json = JSONObject.fromObject(str);
			code = json.getString("code");
			msg = json.getString("msg");
		} catch (Exception e) {
			msg = "下班请求返回的字符串异常，请重试或者联系管理员";
		}
		
		if("0".equals(code)){
			//后台系统
			cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(Integer.parseInt(kefuId));
			newu.setOnlineStatus(Integer.parseInt(workStatus));
			iSysUserinfoService.update(newu);
			return new MessageBean(true, "状态变更操作成功");
		}else{
			return new MessageBean(false, msg);
		}
	}
	
	/**
	 * 上班(新)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "onDuty2")
	@ResponseBody
	public MessageBean onDuty2(HttpServletRequest request) {
		String kefuId = request.getParameter("csId");
		String workStatus = request.getParameter("workStatus");
		if (StringUtils.isBlank(kefuId)) {
		return new MessageBean(false, "请提交参数: 客服Id");
		}
		if (StringUtils.isBlank(workStatus)) {
			return new MessageBean(false, "请提交参数：客服在线工作状态");
		}
		int workStatusI = Integer.parseInt(workStatus);
		// 更改客服上班状态,以及在线状态
		String url = "";
		try {
			url = Config.getValue("kefu.base.url")+"interface/kefu_onduty_onduty.jsp?kefuId=" + kefuId;
		} catch (BeansException e) {
			return new MessageBean(false, "kefu.base.url参数没配置，请联系管理员");
		}
		String num = HttpRequestUtil.doHttpRequestByGet(url);
		num = num.replaceAll("\r|\n", "");
		logger.info("上班url："+url);
		if (num.equals("1") || num == "1") {
//			//后台系统
			cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(Integer.parseInt(kefuId));
			newu.setOnlineStatus(workStatusI);
			newu.setIsOnDuty(1);
			iSysUserinfoService.update(newu);
			return new MessageBean(true, "上班签卡成功");
		}else {
			return new MessageBean(false, "上班签卡失败");
		}
	}
	/**
	 * 下班(新)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "offDuty2")
	@ResponseBody
	public MessageBean offDuty2(HttpServletRequest request) {
		String kefuId = request.getParameter("csId");
		String workStatus = request.getParameter("workStatus");
		if (StringUtils.isBlank(kefuId)) {
		return new MessageBean(false, "请提交参数: 客服Id");
		}
		if (StringUtils.isBlank(workStatus)) {
			return new MessageBean(false, "请提交参数：客服在线工作状态");
		}
		int workStatusI = Integer.parseInt(workStatus);
		// 更改客服上班状态,以及在线状态
		String url = "";
		try {
			url = Config.getValue("kefu.base.url")+"interface/kefu_onduty_offduty2.jsp?kefuId="+kefuId+"&workStatus="+workStatus;
		} catch (BeansException e) {
			return new MessageBean(false, "kefu.base.url参数没配置，请联系管理员");
		}
		String str = HttpRequestUtil.doHttpRequestByGet(url);
		str = str.replaceAll("\r|\n", "");
		logger.info("下班url："+url + ",result:" + str);
		String code = "-1";
		String msg = "";
		
		try {
			JSONObject json = JSONObject.fromObject(str);
			code = json.getString("code");
			msg = json.getString("msg");
		} catch (Exception e) {
			msg = "下班请求返回的字符串异常，请重试或者联系管理员";
		}
		
		if("0".equals(code)){
			//后台系统更新成功
			cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(Integer.parseInt(kefuId));
			newu.setOnlineStatus(workStatusI);
			newu.setIsOnDuty(0);
			iSysUserinfoService.update(newu);
			return new MessageBean(true, "下班签卡成功");
		}else{
			return new MessageBean(false, msg);
		}
	}
	
	/**
	 * @param endTime
	 *            结束时间
	 * @param beginTime
	 *            开始时间
	 * @return
	 */
	public static long timeDifferenceSec(Date endTime, Date beginTime) {
		SimpleDateFormat bsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat esdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long effectiveTime = 0;
		try {
			effectiveTime = (esdf.parse(esdf.format(endTime)).getTime() - bsdf.parse(bsdf.format(beginTime)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return effectiveTime;
	}
}
