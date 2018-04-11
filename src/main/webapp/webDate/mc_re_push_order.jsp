<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.util.StringUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.jugame.web.util.HttpRequestUtil"%>
<%@page import="java.util.Map"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="java.util.List"%>
<%
String orderId = RequestUtils.getParameter(request, "orderId", "");
JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
String url = "http://192.168.1.152:18101/order/retry";
String sql = "SELECT order_id,account,token,create_time,fail_reason FROM  `deliver_order` WHERE order_id = ? ";
String result = "";
String cancelResult = "";
if(StringUtils.isEmpty(orderId)){
	result = "请填写订单号";

}else{
	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,orderId);
	if(null != list && list.size() > 0){
		Map<String,String> params =  new HashMap<String,String>();
		params.put("orderId", orderId);
		params.put("token", String.valueOf(list.get(0).get("token")));
		String endTime = String.valueOf(list.get(0).get("create_time"));
		
	    cancelResult =  HttpRequestUtil.dohttpRequestByPost(url, params);
	    result += "订单号：" + orderId + "订单时间："+endTime+",失败原因：<font color=red>【" + list.get(0).get("fail_reason") + "】</font>,token:" + list.get(0).get("token") + "<br>取消订单结果：" + cancelResult;
	
	}else{
		result ="订单号不存在秒冲表";
	}
	
}


%>
<html>
<form action="">
推送秒冲订单重新发货<font color="red">（慎重操作，推送之后会重新发货）</font>：<input type="text" name="orderId" value="<%=orderId%>" />
<input type="submit" value="推送发货">
</form>
<div>

查询结果：<%=result%><br>
</div>
</html>