<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="cn.jugame.vo.SysUserinfo" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>



<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserinfo u = (SysUserinfo) session.getAttribute("admin");



if (null ==u){
	
	//response.sendRedirect("login.html");
%>
<script language="javascript">
parent.location.href="login.html";
</script>
<% 
	
}else{
%>
<script language="javascript">
parent.location.href="main.html";
</script>
<%
}
%>