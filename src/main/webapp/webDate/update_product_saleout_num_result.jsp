<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%!Logger log = LoggerFactory.getLogger("update_product_saleout_num.jsp");%>
<%
	String result = request.getParameter("result");

	
%>


<html>
<body>

<p>共更新商品销量数：<%=result%></p>

<p><input type="button" name="Submit" onclick="javascript:history.back(-1);" value="返回上一页"></p>
</body>


</html>