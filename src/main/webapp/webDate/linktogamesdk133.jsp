
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
SysUserinfo sys = (SysUserinfo)session.getAttribute(GlobalsKeys.ADMIN_KEY);
String uid ="";
if(sys !=null){
	uid = String.valueOf(sys.getUserId());
}
//¸ñ¶·½­ºþ
response.sendRedirect("http://sdk.jugame.cn/sdkapi/account_trade.jsp?csid="+uid+"&gid=133&orderId=ORD");
%>

<%-- <jsp:forward page="http://sdk.jugame.cn/sdkapi/account_trade.jsp?csid=<%=uid%>>&gid=133"/> --%>