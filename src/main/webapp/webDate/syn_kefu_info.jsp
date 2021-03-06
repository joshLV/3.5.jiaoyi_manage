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
<%!Logger log = LoggerFactory.getLogger("syn_kefu_info_jsp");%>
<%
	JSONObject json = new JSONObject();
	JSONArray jsonArray = null;
	int  kefuId = RequestUtils.getParameterInt(request, "kefu_id", 0);
	if(kefuId == 0){
		json.put("code",-1);
		json.put("msg", "参数为空");
		json.put("data", "");
		out.println(json);
		return;
	}
	
	try {
		ISysUserinfoService iSysUserinfoService = SpringFactory.getBean("SysUserinfoService");
		cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(kefuId);
		if(null == newu){
			json.put("code",-2);
			json.put("msg", "用户不存在");
			json.put("data", "");
			out.println(json);
			return;
		}
		JSONObject djson = JSONObject.fromObject(newu);
		json.put("code",0);
		json.put("msg", "查询成功");
		json.put("data", djson.toString());
		out.println(json);
		return;
	} catch (Exception e) {
		e.printStackTrace();
		json.put("code",-3);
		json.put("msg", "接口异常");
		out.println(json);
	}

	
%>
