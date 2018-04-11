<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%!Logger log = LoggerFactory.getLogger("update_product_saleout_num.jsp");%>
<%
	SysUserinfo sys = (SysUserinfo) session.getAttribute(GlobalsKeys.ADMIN_KEY);
	String uid = "";
	if (sys == null) {
		response.sendRedirect("../nopermission.html");
	}
	JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
	String upproduct = "UPDATE `product` SET product_status = 3 WHERE product_status = 4 LIMIT 5 ";
	int j = jdbcTemplate.update(upproduct);
	out.println("更新商品表结果 + " + j);
%>


