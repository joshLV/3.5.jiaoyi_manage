<%@page import="cn.juhaowan.order.vo.OrdersSelfDefined"%>
<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.jmx.snmp.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="cn.juhaowan.order.service.OrdersC2cService"%>
<%@page import="cn.juhaowan.order.service.OrdersSelfDefinedService"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.web.util.OrderC2cUtils"%>
<%
	String orderId =  request.getParameter("orderId");
	String token =  request.getParameter("token");
	JSONObject json = new JSONObject();
	OrdersC2cService ordersC2cService = SpringFactory.getBean("OrdersC2cService");
	OrdersSelfDefinedService definedService = SpringFactory.getBean("OrdersSelfDefinedService");
	


	if(OrderC2cUtils.validateToken(orderId, token)){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("orderId", orderId);
		Map<String,Object> sourceMap = ordersC2cService.queryOrderC2cDetail(orderId);
		if(sourceMap != null){
			json.put("code", "0");
			//商品信息
			json.put("goodsName", sourceMap.get("goods_name")==null?"":sourceMap.get("goods_name")); //商品名称
			json.put("goodsPrice", sourceMap.get("goods_price")==null?"":sourceMap.get("goods_price")); //商品单价
			json.put("goodsCount", sourceMap.get("goods_count")==null?"":sourceMap.get("goods_count")); //商品数量
			json.put("goodsRemark", sourceMap.get("goods_remark")==null?"":sourceMap.get("goods_remark")); //商品描述
			json.put("goodsId", sourceMap.get("goods_id")==null?"":sourceMap.get("goods_id")); //商品编号
			
			if(sourceMap.get("goods_type").toString().equals("1")){
				json.put("goodsType", "游戏币"); //商品类型
			}else if(sourceMap.get("goods_type").toString().equals("2")){
				json.put("goodsType", "装备"); //商品类型
			}else if(sourceMap.get("goods_type").toString().equals("3")){
				json.put("goodsType", "道具"); //商品类型
			}
			json.put("goodsSingleNumber", sourceMap.get("goods_single_number")==null?"":sourceMap.get("goods_single_number")); //商品单件数量
			
			//游戏数据
			json.put("gameName", sourceMap.get("game_name")==null?"":sourceMap.get("game_name")); //游戏名称
			json.put("gameAreaName", sourceMap.get("game_area_name")==null?"":sourceMap.get("game_area_name")); //游戏区
			json.put("gameServerName", sourceMap.get("game_server_name")==null?"":sourceMap.get("game_server_name")); //服务器
			json.put("gameId", sourceMap.get("game_id")==null?"":sourceMap.get("game_id")); //游戏ID
			json.put("gameChannelId", sourceMap.get("channel_id")==null?"0":sourceMap.get("channel_id")); //游戏渠道ID
			
			//买家信息
			json.put("orderBuyUid", sourceMap.get("order_buy_uid")==null?"":sourceMap.get("order_buy_uid")); //买家平台账号ID
			json.put("orderBuyGameRoleId", sourceMap.get("order_buy_game_role_id")==null?"":sourceMap.get("order_buy_game_role_id")); //买家角色ID
			json.put("orderBuyGameRoleName", sourceMap.get("order_buy_game_role_name")==null?"":sourceMap.get("order_buy_game_role_name")); //买家角色名
			json.put("orderBuyGameRoleLevel", sourceMap.get("order_buy_game_role_level")==null?"":sourceMap.get("order_buy_game_role_level")); //买家角色等级
			
			//卖家信息
			json.put("orderSellUid", sourceMap.get("order_sell_uid")==null?"":sourceMap.get("order_sell_uid")); //卖家平台账号ID
			json.put("orderSellGameRoleId", sourceMap.get("order_sell_game_role_id")); //卖家角色ID
			json.put("orderSellGameRoleName", sourceMap.get("order_sell_game_role_name")==null?"":sourceMap.get("order_sell_game_role_name")); //卖家角色名
			json.put("orderSellGameRoleLevel", sourceMap.get("order_sell_game_role_level")==null?"":sourceMap.get("order_sell_game_role_level")); //卖家角色等级			
			
			//订单信息
			json.put("orderId", sourceMap.get("order_id")); //订单号
			json.put("orderAmount", sourceMap.get("order_amount")); //订单金额
			json.put("orderIspay", sourceMap.get("order_ispay").toString().equals("1")?"己支付":"未支付"); //是否支付
			
			
			//C2C订单信息
			json.put("customerServiceId", sourceMap.get("customer_service_id")==null?"":sourceMap.get("customer_service_id")); //接手客服ID
			json.put("selluserGameAccount", sourceMap.get("selluser_game_account")==null?"":sourceMap.get("selluser_game_account")); //卖家游戏账号


			String sellSafekey ="";
			String sellPwd ="";
			try{
				sellSafekey = GamePasswdUtil.decode(sourceMap.get("selluser_game_account_pwd").toString());
			}catch(Exception e){
				
			}
			try{
				sellPwd =  GamePasswdUtil.decode(sourceMap.get("selluser_game_safekey").toString());
				}catch(Exception e){
					
			}
			json.put("selluserGameAccountPwd", sellPwd); //卖家游戏账号密码
			json.put("selluserGameSafekey", sellSafekey); //卖家游戏账号安全锁
			
			OrdersSelfDefined d = definedService.findById(orderId);
			String buyremark = "";
			if(d != null){
				String self =  d.getOrderSelfDefined();
				try {
					// 买家自定义信息(买家信息)
					JSONObject de = JSONObject.fromObject(self);
					JSONArray buyarr = de.getJSONArray("buyProperty");
					buyremark = buyarr.toString();
				} catch (Exception e) {
				}
				
				if("".equals(buyremark)){
					buyremark = d.getOrderSelfDefined();
				}
				
			}
			json.put("buyProperty", buyremark); //买家自定义信息

		}else{
			json.put("code", "1");
			json.put("msg", "无此订单相关信息");
		}
	}else{
		json.put("code", "1");
		json.put("msg", "验证不通过");
	}
	out.print(json.toString());
%>