<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.product.vo.GameProductType"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.juhaowan.order.vo.Orders"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.juhaowan.order.service.OrdersService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.juhaowan.product.service.GameProductTypeService"%>
<%@page import="cn.juhaowan.product.vo.GameProductTypeShow"%>
<%
    JSONObject json = new JSONObject();
    try{
	String  buyUid =  request.getParameter("order_buy_uid");
	//String  btime =  request.getParameter("btime");
	//String  etime =  request.getParameter("etime");
    //加密key
    String encodeKey = request.getParameter("encodeKey");
    String md5key = MD5.encode(buyUid+"sereblwerijwerie");
    //btime = URLDecoder.decode(btime);
    //etime = URLDecoder.decode(etime);
    
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
	
    OrdersService oService = SpringFactory.getBean("OrdersService");
	GameProductTypeService gpts = SpringFactory.getBean("GameProductTypeService");
    //if(null == buyUid || null == btime || null == etime){
    	if(null == buyUid){
    	json.put("result", "1");
    	json.put("msg", "参数为空");
		out.println(json);
	    System.out.println(json);
	    return;
    }
    
	if(!md5key.toLowerCase().equals(encodeKey.toLowerCase())){
		json.put("result", "1");
		json.put("msg", "加密信息不匹配");
		out.println(json);
	    System.out.println(json); 
	    return;
	}
    
	//List<Orders> o = oService.queryOrderBySellUid(75, "2013-06-05 17:18:01", "2013-6-06 17:18:01");
	List<Orders> o = oService.queryOrderByBuyUid(Integer.parseInt(buyUid)); 
    String s = "";
	if(null == o){
		json.put("result", "1");
		json.put("msg", "该买家用户不存在订单");
		out.println(json);
	    System.out.println(json);
	    return;
	}
	for(int i = 0; i < o.size();i++){
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("order_id", o.get(i).getOrderId());
		outMap.put("order_status", o.get(i).getOrderStatus());
		outMap.put("order_amount", o.get(i).getOrderAmount());
		outMap.put("goods_count", o.get(i).getGoodsCount());
		outMap.put("goods_price", o.get(i).getGoodsPrice());
		outMap.put("goods_single_number", o.get(i).getGoodsSingleNumber());
		outMap.put("goods_type", o.get(i).getGoodsType());
		outMap.put("game_id", o.get(i).getGameId());
		outMap.put("game_name", o.get(i).getGameName());
		outMap.put("game_area_id", o.get(i).getGameAreaId());
		outMap.put("game_area_name", o.get(i).getGameAreaName());
		if(null != o.get(i).getOrderTime()){
			outMap.put("order_time", sdf.format(o.get(i).getOrderTime()));
		}else{
			outMap.put("order_time", null);
		}
		if(null != o.get(i).getGetGoodsTime()){
			outMap.put("get_goods_time", sdf.format(o.get(i).getGetGoodsTime()));
		}else{
			outMap.put("get_goods_time", null);
		}
		if(null != o.get(i).getModifyTime()){
			outMap.put("modify_time", sdf.format(o.get(i).getModifyTime()));
		}else{
			outMap.put("modify_time", null);
		}
		GameProductTypeShow gpt = gpts.queryGameProductTypeShow(o.get(i).getGameId(), o.get(i).getGoodsType());
		if(null != gpt){
		outMap.put("goods_type_name", gpt.getUnit());
		}else{
			outMap.put("goods_type_name", "");	
		}
		
		showList.add(outMap);
		
	}
	s = JSONArray.fromObject(showList).toString();
    json.put("result", "0");
    json.put("msg", "查询成功");
    json.put("buyUid", buyUid);
    json.put("data", s);
    out.println(json.toString());
    System.out.println(json);
    }catch(Exception e){
    	  json.put("result", "1");
    	  json.put("msg", "出现异常");
    	  out.println(json.toString());
    	System.out.println("-----------orderinfo--");
    	e.printStackTrace(System.out);
    }
  	
%>