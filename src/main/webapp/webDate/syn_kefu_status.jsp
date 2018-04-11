<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.service.ISysUserinfoService"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="java.util.List"%>
<%!Logger log = LoggerFactory.getLogger("syn_kefu_status_jsp");%>
<%
	JSONObject json = new JSONObject();
	JSONArray jsonArray = null;
	int  kefuId = RequestUtils.getParameterInt(request, "kefu_id", 0);
	int  isOnDuty = RequestUtils.getParameterInt(request, "is_onduty", -1);
	int onlineStatus = RequestUtils.getParameterInt(request, "online_status", -1);
	if(kefuId == 0|| isOnDuty == -1|| onlineStatus == -1){
		json.put("code",-1);
		json.put("msg", "参数为空");
		out.println(json);
		return;
	}
	
	try {
		ISysUserinfoService sysu = SpringFactory.getBean("SysUserinfoService");
		
		cn.jugame.vo.SysUserinfo newu = sysu.findById(kefuId);
		if(null == newu){
			json.put("code",-2);
			json.put("msg", "用户不存在");
			out.println(json);
			return;
		}
		newu.setOnlineStatus(onlineStatus);
		newu.setIsOnDuty(isOnDuty);
		
		sysu.update(newu);
		json.put("code",0);
		json.put("msg", "修改成功");
		out.println(json);
		return;
	} catch (Exception e) {
		e.printStackTrace();
		json.put("code",-3);
		json.put("msg", "接口异常");
		out.println(json);
	}

	
%>
