<%@page import="cn.juhaowan.order.vo.DcWhiteList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="cn.juhaowan.product.vo.ProductOnsale"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.jmx.snmp.Timestamp"%> 
<%@page import="java.util.Iterator"%>
<%@page import="cn.juhaowan.order.service.DcWhiteListService"%>
<%@page import="cn.juhaowan.product.vo.ProductC2c"%>
<%@page import="cn.juhaowan.product.vo.Product"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="javax.servlet.ServletInputStream"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.web.util.ProductC2cUtils"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="cn.jugame.util.MD5"%>
<%!Logger log = LoggerFactory.getLogger("dcWhiteAdd_jsp");%>
<%

	//返回成功数据
	JSONObject json = new JSONObject();
	JSONObject jsonObject = null;
	JSONArray list = null;
	try{
	String data = request.getParameter("data");
	String encodeKey = request.getParameter("encodeKey");
	//data = URLDecoder.decode(data);
	String md5key = MD5.encode(data + "sereblwerijwerie");
	//---------------------------
	jsonObject = JSONObject.fromObject(data);
	list = jsonObject.getJSONArray("data");
	//返回成功数据
	
	System.out.println("data === " +data);
	System.out.println("encodeKey === " +encodeKey);
	System.out.println("md5key === " +md5key);
	if (!md5key.toLowerCase().equals(encodeKey.toLowerCase())) {
		json.put("result", "1");
		json.put("msg", "签名失败");
		out.println(json);
		return;
	}
	}catch(Exception ee){
		json.put("result", "1");
		json.put("msg", "参数格式不对");
		out.println(json);
		return;
	}
	try {
		DcWhiteListService ds = SpringFactory.getBean("DcWhiteListService");
		DcWhiteList white = null;
		for(int i =0;i <list.size();i++){
			JSONObject o = JSONObject.fromObject(list.get(i));
			int gid = Integer.parseInt(o.getString("game_id"));
			int cid = Integer.parseInt(o.getString("channel_id")); 
			String account = o.getString("account");
			int e = ds.isexist(gid, cid, account);
			if(e == 0){
				white = new DcWhiteList();
				white.setGameId(gid);
				white.setChannelId(cid);
				white.setAccount(account);
				white.setRemark("1批量导入");
				ds.add(white);
			}
		
		}
		json.put("result", "0");
		out.println(json);
		//System.out.println(json);
	} catch (Exception e) {
		System.out.println("-----------dcwhitelist--");
		e.printStackTrace();

	}
%>