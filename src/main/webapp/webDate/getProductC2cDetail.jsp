<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.product.vo.Product"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.jmx.snmp.Timestamp"%>
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

	String productId =  request.getParameter("productId");
	String token =  request.getParameter("token");
	JSONObject json = new JSONObject();
	ProductService productC2cService = SpringFactory.getBean("ProductService");
	
	//if(ProductC2cUtils.validateToken(productId, token)){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("productId", productId);
		Map<String,Object> sourceMap = null;
		ProductC2c c2c = productC2cService.findProductC2cByProductId(productId);
		Product p = productC2cService.queryByProductId(productId);
		if(c2c != null){
			json.put("code", "0");
			//c2c商品信息
			String login = "";
			String pdShow = "";
			String lock ="";
			
			//游戏登陆用户名
			if(null != c2c.getGameLoginId()){
				login = c2c.getGameLoginId();
				json.put("selluserGameAccount",login);
			}
			//游戏登陆密码
			if(null != c2c.getGameLoginPassword()){
				//解码 
				pdShow = GamePasswdUtil.decode(c2c.getGameLoginPassword());
				json.put("selluserGameAccountPwd", pdShow);
			}
			//游戏安全锁
			if(null != c2c.getSecurityLock()){
				lock = GamePasswdUtil.decode(c2c.getSecurityLock());
				json.put("selluserGameSafekey", lock);
			}
			//游戏id
			json.put("gameId", p.getGameId());
			//游戏渠道id
			json.put("gameChannelId", p.getChannelId());

		}else{
			json.put("code", "1");
			json.put("msg", "无此商品相关信息");
		}
	//}
	
	//else{
	//	json.put("code", "1");
	//	json.put("msg", "验证不通过");
	//}
	out.print(json.toString());
	System.out.println(json.toString());
%>