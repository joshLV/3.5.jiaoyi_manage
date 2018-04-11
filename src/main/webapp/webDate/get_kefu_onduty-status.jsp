<%@page import="cn.jugame.util.Config"%>
<%@page import="java.util.Map"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="cn.jugame.service.ISysParameterService"%>
<%@page import="cn.jugame.vo.SysParameter"%>
<%@page import="java.util.List"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.jugame.service.impl.SysModuleService"%>
<%@page import="java.net.URLEncoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="cn.jugame.web.util.URLConnectionHelper" %>
<% 
SysUserinfo userinfo = (SysUserinfo) session.getAttribute(GlobalsKeys.ADMIN_KEY);
if (null != userinfo) { 
	String url = "";
	try {
		url = Config.getValue("kefu.base.url");
	} catch (Exception e) {
		e.printStackTrace();
	}
	int kefuId = userinfo.getUserId();
	String result = URLConnectionHelper.getResult(url+"interface/get_kefu_work_status.jsp?kefuId="+kefuId,"");
	out.println(result.trim());
}
%>

