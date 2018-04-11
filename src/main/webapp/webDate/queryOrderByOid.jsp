<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.collections.map.HashedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.order.vo.Orders"%>
<%@page import="cn.juhaowan.order.vo.OrdersC2c"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.juhaowan.order.service.OrdersService"%>
<%@page import="cn.juhaowan.order.service.OrdersC2cService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%
	JSONObject json = new JSONObject();
	try {
		String orderId = request.getParameter("order_id");
		//加密key
		String encodeKey = request.getParameter("encodeKey");
		String md5key = MD5.encode(orderId + "sereblwerijwerie");
		
		if (null == orderId || null == encodeKey) {
			json.put("result", "1");
			json.put("msg", "参数为空");
			out.println(json);
			System.out.println(json);
			return;
		}

		if (!md5key.toLowerCase().equals(encodeKey.toLowerCase())) {
			json.put("result", "1");
			json.put("msg", "加密验证不匹配");
			out.println(json);
			System.out.println(json);
			return;
		}

		OrdersService os = SpringFactory.getBean("OrdersService");
		OrdersC2cService c2cs = SpringFactory.getBean("OrdersC2cService");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Orders o = os.queryOrderById(orderId);
		OrdersC2c c2c = c2cs.queryById(orderId);
		if (null != o) {
			json.put("result", "0");
			json.put("msg", "订单存在");
			Map<String,Object> pMap = new HashMap<String,Object>();
			//订单信息
			pMap.put("order_id", o.getOrderId());//订单id
			pMap.put("order_time", o.getOrderTime()!=null?sdf.format(o.getOrderTime()):"");//下单时间
			pMap.put("modify_time", o.getModifyTime()!=null?sdf.format(o.getModifyTime()):"");//修改时间
			pMap.put("order_assign", o.getOrderAssign());//订单是否分派
			pMap.put("order_status", o.getOrderStatus());//订单状态
			pMap.put("order_amount", o.getOrderAmount());//订单金额
			pMap.put("order_password", o.getOrderPassword());//订单交易密码
			pMap.put("order_ispay", o.getOrderIspay());//订单支付状态
			pMap.put("order_model", o.getOrderModel());//订单售卖模式
			pMap.put("order_buy_uid", o.getOrderBuyUid());//卖家uid
			pMap.put("order_buy_qq", o.getOrderBuyQq());//卖家qq
			pMap.put("order_buy_game_uid", o.getOrderBuyGameUid());//卖家游戏uid
			pMap.put("order_buy_game_role_id", o.getOrderBuyGameRoleId());//卖家游戏角色id
			pMap.put("order_buy_game_role_name", o.getOrderBuyGameRoleName());//卖家游戏角色名字
			pMap.put("goods_name", o.getGoodsName());//商品名称
			pMap.put("goods_type", o.getGoodsType());////商品类型
			pMap.put("goods_price", o.getGoodsPrice());//商品价格
			pMap.put("goods_count", o.getGoodsCount());//商品数量
			pMap.put("goods_remark", o.getGoodsRemark());//商品简介
			pMap.put("order_sell_uid", o.getOrderSellUid());//买家uid
			pMap.put("order_sell_game_uid``", o.getOrderSellGameUid());//买家游戏uid
			pMap.put("order_sell_game_role_id", o.getOrderSellGameRoleId());//买家游戏角色id
			pMap.put("order_sell_game_role_name", o.getOrderSellGameRoleName());//买家角色名
			pMap.put("game_id", o.getGameId());//游戏id
			pMap.put("game_area_id", o.getGameAreaId());//游戏分区id
			pMap.put("game_server_id", o.getGameServerId());//游戏服务器id
			pMap.put("order_comefrom", o.getOrderComefrom());//订单来源,是否来由其他游戏的SDK产生
			pMap.put("send_goods_time", o.getSendGoodsTime()!=null?sdf.format(o.getSendGoodsTime()):"");//发货时间
			pMap.put("get_goods_time", o.getGetGoodsTime()!=null?sdf.format(o.getGetGoodsTime()):"");//收货时间
			pMap.put("remark", o.getRemark());//订单备注
			pMap.put("goods_single_number", o.getGoodsSingleNumber());//商品单件数量
			pMap.put("game_name", o.getGameName());//游戏名字
			pMap.put("game_area_name", o.getGameAreaName());//游戏分区名字
			pMap.put("game_server_name", o.getGameServerName());//游戏服务器名字
			pMap.put("order_buy_phonenum", o.getOrderBuyPhonenum());//买家手机
			pMap.put("goods_sell_pid", o.getGoodsSellPid());//商品上架号
			pMap.put("order_free", o.getOrderFree());//手续费
			pMap.put("order_pay_success_time", o.getOrderPaySuccessTime()!=null?sdf.format(o.getOrderPaySuccessTime()):"");//支付成功实践
			//pMap.put("valid_flag", o.getorder);
			pMap.put("order_sell_user_type", o.getOrderSellUserType());//卖家类型 1 普通用户 2 vip
			pMap.put("order_buy_game_role_level", o.getOrderBuyGameRoleLevel());//买家游戏叫色等级
			pMap.put("order_sell_game_role_level", o.getOrderSellGameRoleLevel());//卖家游戏角色等级
			//pMap.put("shipped_long", o.get);
			pMap.put("order_pay_platform_id", o.getOrderPayPlatformId());//支付平台id
			pMap.put("order_pay_way", o.getOrderPayWay());//支付方式
			pMap.put("order_pay_qudao", o.getOrderPayQudao());//支付渠道
			pMap.put("game_channel_id", o.getChannelId());//游戏渠道id
			pMap.put("quick_deliver_flag", o.getQuickDeliverFlag());//是否快速发货 1 是 0 否
			//pMap.put("is_lock", o.geti);
			//c2c 信息
			//pMap.put("customer_service_id", c2c.getCustomerServiceId());
			//pMap.put("customer_service_time", c2c.getCustomerServiceTime()!=null?sdf.format(c2c.getCustomerServiceTime()):"");
			
			//pMap.put("physic_service_id", c2c.getPhysicServiceId());
			//pMap.put("physic_service_time", c2c.getPhysicServiceTime()!=null?sdf.format(c2c.getPhysicServiceTime()):"");
			//pMap.put("order_c2c_status", c2c.getOrderC2cStatus());
			//pMap.put("selluser_game_account", c2c.getSelluserGameAccount());
			//pMap.put("selluser_game_account_pwd", c2c.gets);
			//pMap.put("selluser_game_safekey", );
			//pMap.put("send_order_type", );
			//pMap.put("is_customer_accept", );
			//pMap.put("is_physic_accept", );
			//pMap.put("goods_position", );
			//pMap.put("contact_flag", );

		
		
			json.put("data", pMap);
			out.println(json);
			System.out.println(json);
			return;
		}
		json.put("result", "1");
		json.put("msg", "商品不存在");
		out.println(json.toString());
		System.out.println(json);

	} catch (Exception e) {
		json.put("result", "1");
		json.put("msg", "出现异常");
		out.println(json);
		System.out.println("-----------queryProductByUid--");
		e.printStackTrace(System.out);

	}
%>