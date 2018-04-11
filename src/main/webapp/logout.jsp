<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<% 
session.removeAttribute("admin");
session.invalidate();
response.sendRedirect("./");
%>