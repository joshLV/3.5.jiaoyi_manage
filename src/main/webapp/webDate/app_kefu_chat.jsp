<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
SysUserinfo sys = (SysUserinfo)session.getAttribute(GlobalsKeys.ADMIN_KEY);
String uid ="";
if(sys !=null){
	uid = String.valueOf(sys.getUserId());
	//System.out.println("3.5 uid ==== " + uid);
}else{
	return; 
}

String URL;
Properties pro = new Properties();
try {
	pro.load(new FileInputStream(SysUserinfo.class.getClassLoader().getResource("resources.properties").getPath()));
} catch (Exception e) {
	e.printStackTrace();
}
URL = pro.getProperty("basekefuurl");

response.sendRedirect(URL+"appview/appchat.jsp?kefu_id="+uid);
%>

