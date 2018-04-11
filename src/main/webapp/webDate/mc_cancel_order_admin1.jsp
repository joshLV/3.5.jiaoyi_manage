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
String url = "http://192.168.1.152:18101/order/cancel";
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
		
		
		
	    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	    long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
        long nh = 1000 * 60 * 60;// 一小时的毫秒数    
        long nm = 1000 * 60;// 一分钟的毫秒数    
        long ns = 1000;// 一秒钟的毫秒数    
        long diff;    
        long day = 0;    
        long hour = 0;    
        long min = 0;    
        long sec = 0; 
	    try {    
            diff = sd.parse(endTime).getTime() - new Date().getTime();  
            min = diff/(1000*60);
            day = diff / nd;// 计算差多少天    
            hour = diff % nd / nh + day * 24;// 计算差多少小时    
           // min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟    
            sec = diff % nd % nh % nm / ns;// 计算差多少秒    
            // 输出结果    
            if(sec >0){
            	min +=1;
            }
        } catch (Exception e) {    
           
        } 
		
	    if( -60 < min ){
// 	    	result="<font color=red>订单还没有超过一个小时，请稍候再试</font><br>";
	    	cancelResult = "<font color=red>暂时无法取消,订单还没有超过一个小时，请稍候再试</font>";
	    }else{
// 	    	result ="超时";
	    	cancelResult = "测试";// HttpRequestUtil.dohttpRequestByPost(url, params);
	    }
	    result += "订单号：" + orderId + "订单时间："+endTime+",失败原因：<font color=red>【" + list.get(0).get("fail_reason") + "】</font>,token:" + list.get(0).get("token") + "<br>取消订单结果：" + cancelResult;
	
	}else{
		result ="订单号不存在秒冲表";
	}
	
}


%>
<html>
<form action="">
秒冲订单撤单<font color="red">（慎重操作，用户确认撤单才可操作）</font>：<input type="text" name="orderId" value="<%=orderId%>" />
<input type="submit" value="撤单">
</form>
<div>

查询结果：<%=result%><br>
</div>
</html>