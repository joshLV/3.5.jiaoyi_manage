<%@page import="java.text.SimpleDateFormat"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="org.springframework.jdbc.core.JdbcOperations"%>
<%@page import="cn.juhaowan.member.dao.D" %>
<%@page import="cn.juhaowan.order.service.OrdersService" %>
<%@page import="cn.juhaowan.util.XProperty" %>
<%@page import="cn.juhaowan.util.XObject" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page trimDirectiveWhitespaces="true" %>

<%!
//@Author zhangzj@jugame.cn
Date prev_date(int day){
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, day);
	return cal.getTime();
}
JSONObject build_list(long total, List<XObject> rows){
	JSONObject rtn = new JSONObject();
	rtn.put("total", total);

	JSONArray arr = new JSONArray();
	if(rows != null){
		for(XObject row : rows){
			JSONObject obj = new JSONObject();
			for(XProperty prop: row){
				Object value = prop.getValue();
				if(value instanceof Date){
					value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((Date)value).getTime());
				}
				obj.put(prop.getKey(), value);
			}
			arr.add(obj);
		}
	}
	rtn.put("data", arr);
	return rtn;
}
%>

<%
//用于校验的vkey
String vkey = "asdf@T_T@nofun";

int start = RequestUtils.getParameterInt(request, "start", 0);
int limit = RequestUtils.getParameterInt(request, "limit", 100);
if(limit > 200){
	limit = 200;
}
int day = RequestUtils.getParameterInt(request, "day", 0);
if(day >= 0){
	//默认获取昨天的数据
	day = -1;
}

//校验
String vcode = RequestUtils.getParameter(request, "vcode", "");
if(StringUtils.isBlank(vcode)){
	System.out.println("vcode为空");
	return;
}
StringBuffer sb = new StringBuffer();
sb.append(start).append(limit).append(day).append(vkey);
String chk_code = MD5.encode(sb.toString());

if(!vcode.equalsIgnoreCase(chk_code)){
	System.out.println("vcode错误");
	return;
}


//时间
Date date = prev_date(day);
String btime = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date.getTime());
String etime = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date.getTime());

JdbcOperations jdbc = SpringFactory.getBean("jdbcTemplate");
List<XObject> rows = D.proxy("orders", jdbc).all(start, limit, new XProperty[]{
		new XProperty("order_status", 6), //只获取成功的
		new XProperty("order_time >=", btime),
		new XProperty("order_time <=", etime),
}, new XProperty[]{
		new XProperty("order_time", "ASC")
});
long total = D.proxy("orders", jdbc).getCount(new XProperty[]{
		new XProperty("order_status", 6), //只获取成功的
		new XProperty("order_time >=", btime),
		new XProperty("order_time <=", etime),
});

//组织成json返回
JSONObject rtn = build_list(total, rows);
out.println(rtn.toString());
%>
