<%@page import="java.net.URLDecoder"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.web.util.RequestUtils"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.admin.service.OrderTyGameAccountService"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%
	String encodeString = RequestUtils.getParameter(request,"vcode","");
	String tyId = RequestUtils.getParameter(request,"ty_id","0");
	String msg = RequestUtils.getParameter(request,"msg","0");
	msg = URLDecoder.decode(msg);
	tyId =URLDecoder.decode(tyId);
	String keysourse = tyId + msg  + "fksldjfsdjfsjdiufosdf";
	String encode = MD5.encode(keysourse);
	JSONObject json = new JSONObject();
	
	if (StringUtils.isBlank(encodeString) ||StringUtils.isBlank(tyId) ||StringUtils.isBlank(msg)) {
		json.put("msg", "9999");
		out.print(json);
		return;
	}
	if (!encode.equals(encodeString)) {
		json.put("msg", "9998");
		out.print(json);
		return;
	}


	OrderTyGameAccountService oService = SpringFactory.getBean("OrderTyGameAccountService");
	int i = oService.updateVerifyStr(tyId, msg);

	json.put("msg", i);
	out.print(json);
%>