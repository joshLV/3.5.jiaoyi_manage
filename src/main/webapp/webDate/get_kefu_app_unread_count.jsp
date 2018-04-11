<%@page import="java.net.URLEncoder"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="cn.jugame.web.util.URLConnectionHelper" %>
<% 
String result = URLConnectionHelper.getResult("http://192.168.0.222:8081/kefu_chat/interface/appmsg_unread_count.jsp","");
//String result = URLConnectionHelper.getResult("http://192.168.0.222:8081/interface/test.jsp", "qudaoId="+qudaoId);
//String s=new String(result.trim().getBytes("utf-8"),"ISO-8859-1") ;

out.println(result.trim());
%>

