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
String kefuid = request.getParameter("kefuid");
if(orderid == null){
	out.println("<p>订单号不存在<p>");
	return;
}
if(kefuid == null){
	out.println("<p>客服uid不存在</p>");
	return;
}

response.sendRedirect(URL+"interface/get_chat_record_succ.jsp?orderid="+orderid+"&kefuid="+kefuid+"&pageNo=20");
%>

<%-- <jsp:forward page="http://sdk.jugame.cn/sdkapi/account_trade.jsp?csid=<%=uid%>>&gid=133"/> --%>