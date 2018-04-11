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
	String encodeString = RequestUtils.getParameter(request, "vcode", "");
	int gameId = RequestUtils.getParameterInt(request, "gameId", 0);
	int channelId = RequestUtils.getParameterInt(request, "channelId", 0);
	int num = RequestUtils.getParameterInt(request, "num", 0);
	StringBuffer sb = new StringBuffer();
	sb.append(gameId);
	if (channelId != 0) {
		sb.append(channelId);
	}
	if (num != 0) {
		sb.append(num);
	}
	//System.out.println(sb.toString());
	String encode = MD5.encode(sb.toString() + "fksldjfsdjfsjdiufosdf");
	//System.out.println("encode == " + encode);
	JSONObject json = new JSONObject();
	OrderTyGameAccountService oService = SpringFactory.getBean("OrderTyGameAccountService");
	if (StringUtils.isBlank(encodeString)) {
		json.put("msg", "参数不能为空");
		json.put("data", "[]");
		out.print(json);
		return;
	}
	if (!encode.equals(encodeString)) {
		json.put("msg", "签名失败");
		json.put("data", "[]");
		out.print(json);
		return;
	}
	List<Map<String, Object>> list = oService.findTyGameAccountList(gameId, channelId, num);
	json.put("msg", "查询成功");
	json.put("data", list);
	out.print(json);
%>