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
	tyId =URLDecoder.decode(tyId);
	String keysourse = tyId   + "fksldjfsdjfsjdiufosdf";
	String encode = MD5.encode(keysourse);
	//System.out.println("encode == " + encode);
	//System.out.println("encodeString == " + encodeString);
	JSONObject json = new JSONObject();
	
	if (StringUtils.isBlank(encodeString)||StringUtils.isBlank(tyId)) {
		json.put("code","9999");
		json.put("msg", "参数为空");
		out.print(json);
		return;
	}
	if (!encode.toLowerCase().equals(encodeString.toLowerCase())) {
		json.put("code","9998");
		json.put("msg", "签名失败");
		out.print(json);
		return;
	}



	OrderTyGameAccountService oService = SpringFactory.getBean("OrderTyGameAccountService");
	int i = oService.updateVerifyFlag(tyId);

	json.put("code","0");
	json.put("msg", "操作成功");
	out.print(json);
%>