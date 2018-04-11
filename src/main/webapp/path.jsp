<%@page import="cn.jugame.util.Config"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%=request.getRealPath("/") %><br/>
<%=Config.getValue("mobile.white") %><br/>
<%=Config.getValue("user.white") %><br/>
<%=Config.getValue("bank.white") %><br/>
</body>
</html>