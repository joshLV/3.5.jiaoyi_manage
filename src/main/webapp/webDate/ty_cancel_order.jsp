<%@page import="cn.jugame.web.util.MessageBean"%>
<%@page import="cn.juhaowan.admin.service.OrderTyService"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%
//定时任务部署在180，jiaoyi用户crontab
//防止误打开
if(request.getParameter("x") == null){
	return ;
}
//取消退游的订单
//退游，180分钟没有完善资料的，取消订单
try{
	JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
	OrderTyService orderTyService = SpringFactory.getBean("OrderTyService");
	Date d = DateUtils.addMinutes(new Date(), -180);
	
	//退游\换游 \ 未完善资料
	String sql = "select * from orders where order_model > 10 and order_model != 119  and order_status = 2 and order_amount = 0 and ty_material_submit = 0 and order_time < ? limit 5";
	
	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,d);
	if (list == null){
		out.println(new Date() + "list is null.");
		return ;
	}
	for(int i=0;i<list.size();i++){
		Map<String,Object> item = list.get(i);
		String orderId = (String)item.get("order_id");
		//取消订单
		MessageBean mb = orderTyService.orderCancel(orderId, "[系统]超时完善资料取消", "", 1000);
		out.println(orderId + "==>" + mb.isSuccess() + "," + mb.getMsg() +"<br/>");
	}
	out.println("finish..");
}catch(Exception ex){
	out.println(ex.getMessage());
	ex.printStackTrace();
}





%>