<%@page import="cn.juhaowan.product.vo.GameChannel"%>
<%@page import="cn.juhaowan.product.service.GameChannelService"%>
<%@page import="cn.juhaowan.product.service.ChannelService"%>
<%@page import="cn.juhaowan.product.vo.Channel"%>
<%@page import="org.apache.commons.lang.ObjectUtils"%>
<%@page import="cn.juhaowan.admin.service.OrderTyGameAccountService"%>
<%@page import="cn.jugame.util.Cache"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="cn.juhaowan.order.service.OrdersC2cService"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.web.util.OrderC2cUtils"%>
<%
	String orderId =  request.getParameter("orderId");
	String token =  request.getParameter("token");
	if (StringUtils.isBlank(token)){
		out.println("token is blank");
		return ;
	}
	JSONObject json = new JSONObject();
	
	String key = "ty/client/" + token ;
	String tyid = Cache.get(key);
	System.out.println(key + "," + tyid+",orderid="+orderId);
// 	if (StringUtils.isBlank(tyid)){
// 		json.put("code", "1");
// 		json.put("msg", "退游账号不存在");
// 		out.print(json.toString());
// 		return ;
// 	}
	
	OrderTyGameAccountService orderTyGameAccountService = SpringFactory.getBean("OrderTyGameAccountService");
	GameChannelService gameChannelService = SpringFactory.getBean("GameChannelService");
	Map<String,String> paramMap = new HashMap<String,String>();
	paramMap.put("orderId", orderId);
	Map<String,Object> gameAccount = orderTyGameAccountService.findTyGameAccountById(orderId);
	if(gameAccount == null){
		json.put("code", "1");
		json.put("msg", "退游账号不存在");
		out.print(json.toString());
		return ;
	}
	json.put("code", "0");
	//商品信息
	json.put("goodsName", ObjectUtils.toString(gameAccount.get("good_name"), "") ); //商品名称
	json.put("goodsPrice", ""); //商品单价
	json.put("goodsCount", 1); //商品数量
	json.put("goodsRemark", ""); //商品描述
	json.put("goodsId", "---"); //商品编号
	json.put("goodsType", "退游账号"); //商品类型
	json.put("goodsSingleNumber", "0"); //商品单件数量
	
	//游戏数据
	JSONObject valaInfo = JSONObject.fromObject(gameAccount.get("vala_info"));
	json.put("gameName", valaInfo.get("gamename")); //游戏名称
	json.put("gameAreaName", valaInfo.get("gamearea")); //游戏区
	json.put("gameServerName", ""); //服务器
	json.put("gameId", gameAccount.get("game_id")); //游戏ID
	
	//根据游戏渠道找到渠道id 
	int gameChannelId = 0;
	int channelId = 0;
	gameChannelId = Integer.parseInt(gameAccount.get("game_channel_id").toString());
	GameChannel gameChannel = gameChannelService.findItemById(gameChannelId);
	if(gameChannel != null){
		channelId = gameChannel.getChannelId();
	}
	json.put("gameChannelId", channelId); //渠道ID
	
	//买家信息
	json.put("orderBuyUid", 0); //买家平台账号ID
	json.put("orderBuyGameRoleId", 0); //买家角色ID
	json.put("orderBuyGameRoleName", "--"); //买家角色名
	json.put("orderBuyGameRoleLevel", 0); //买家角色等级
	
	//卖家信息
	json.put("orderSellUid", gameAccount.get("uid")); //卖家平台账号ID
	json.put("orderSellGameRoleId", 0); //卖家角色ID
	json.put("orderSellGameRoleName", "--"); //卖家角色名
	json.put("orderSellGameRoleLevel", 0); //卖家角色等级			
	
	//订单信息
	json.put("orderId", tyid); //订单号
	json.put("orderAmount", 0); //订单金额
	json.put("orderIspay", "未支付"); //是否支付
	
	
	//C2C订单信息
	json.put("customerServiceId", ObjectUtils.toString(gameAccount.get("customer_service_id"), "")); //接手客服ID
	json.put("selluserGameAccount", ObjectUtils.toString(gameAccount.get("game_account"), "")); //卖家游戏账号
	json.put("selluserGameAccountPwd", GamePasswdUtil.decode(ObjectUtils.toString(gameAccount.get("game_passwd"), ""))); //账号密码
	json.put("selluserGameSafekey", GamePasswdUtil.decode(ObjectUtils.toString(gameAccount.get("game_safe_lock"), ""))); //账号安全锁

	//Cache.delete(key);
	out.print(json.toString());
%>