<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="java.util.List"%>
<%
String orderId = RequestUtils.getParameter(request, "orderId", "");
JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
String sql = "SELECT order_id,account,token,create_time,fail_reason FROM  `deliver_order` WHERE order_id = ? ";
String result = "";
if(StringUtils.isBlank(orderId)){
	result = "请填写订单号";

}else{
	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,orderId);
	if(null != list && list.size() > 0){
		result = "订单号：" + orderId + ",失败原因：<font color=red>【" + list.get(0).get("fail_reason") + "】</font>,token:" + list.get(0).get("token");
	}else{
		result ="订单号不存在秒冲表";
	}
	
}


%>
<html>
<form action="">
秒冲订单：<input type="text" name="orderId" value="<%=orderId%>" />
<input type="submit">
</form>
<div>

查询结果：<%=result%><br>
</div>
</html>