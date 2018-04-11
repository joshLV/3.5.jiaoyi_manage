<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.jmx.snmp.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="cn.juhaowan.product.service.ProductService"%>
<%@page import="cn.juhaowan.product.vo.ProductC2c"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.web.util.ProductC2cUtils"%>
<%@page import="cn.jugame.util.GamePasswdUtil" %>
<%
	String encodeString =  request.getParameter("encodeString");
	JSONObject json = new JSONObject();
	ProductService pService = SpringFactory.getBean("ProductService");
	if(null !=encodeString && !"".equals(encodeString)){
		Map<String,String> paramMap = new HashMap<String,String>();
		Map<String,Object> sourceMap = null;
		int i  = pService.updateC2CSellerAccountStatus(encodeString);
		if(i == 0 ){
			json.put("code", "0");
			json.put("msg", "更新成功");

		}else if(i ==2){
			json.put("code", "2");
			json.put("msg", "解密异常");
		}else if(i == 3){
			json.put("code", "3");
			json.put("msg", "解密后的字符串格式不对");
		}
	}
	
	else{
		json.put("code", "4");
		json.put("msg", "参数不能为空");
	}
	out.print(json.toString());
%>