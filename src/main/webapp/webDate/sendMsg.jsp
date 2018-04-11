<%@page import="cn.jugame.web.util.HttpRequestUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String url = "http://120.31.134.77:18043/validate_service/validate/smscode?appKey=web&code=124577&smsId=SMS_1445015&mobile=13229999501";
String str = HttpRequestUtil.doHttpRequestByGet(url);
out.println(str);

%>
