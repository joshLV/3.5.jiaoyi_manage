<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="cn.jugame.web.util.URLConnectionHelper" %>
<%
String qudaoId = request.getParameter("qudaoId");
String result = URLConnectionHelper.getResult("http://192.168.0.104/channel/getQudaoName.do", "qudaoId="+qudaoId);
//String result = URLConnectionHelper.getResult("http://192.168.0.222:8081/interface/test.jsp", "qudaoId="+qudaoId);
//System.out.println("=="+result.trim());
out.println(result.trim());
%>

