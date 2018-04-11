<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%> 
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.TreeMap" %>
<%
String URL;
Properties pro = new Properties();
try { 
	pro.load(new FileInputStream(SysUserinfo.class.getClassLoader().getResource("resources.properties").getPath()));
} catch (Exception e) {
	e.printStackTrace();
}
URL = pro.getProperty("basekefuurl");
String orderid = request.getParameter("orderid");
String link = request.getParameter("link");
String type = request.getParameter("type");
if(orderid == null){
	out.println("<p>订单号不存在<p>");
	return;
}
if(link == null){
	out.println("<p>链接不存在</p>");
	return;
}

response.sendRedirect(URL+"interface/exec_op_function.jsp?orderid="+orderid+"&link="+link+"&type="+type);
%>

<%-- <jsp:forward page="http://sdk.jugame.cn/sdkapi/account_trade.jsp?csid=<%=uid%>>&gid=133"/> --%>