<%@page import="cn.juhaowan.member.vo.Member"%>
<%@page import="cn.juhaowan.member.service.MemberService"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.juhaowan.order.service.OrdersService"%>
<%@page import="cn.juhaowan.order.vo.Orders"%>
<%@page import="cn.jugame.util.PageInfo"%>
<%@page import="cn.juhaowan.order.dao.OrdersDao"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>
<%@page import="cn.jugame.util.GlobalsKeys"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
	// 格式化数据并输出
	out.clear();
	out.write(this.huoDongFanli(request).toString());
%>
<%!
	/**
	 * 返利，人均上限100
	 */
	private static double MAX_RETURN_MONEY = 100d;
	/**
	 * 返利最大金额
	 */
	private static double TOTAL_RETURN_MONEY = 2000D;
	/**
	* 活动须剔除的用户集合
	*/
	private static List<Integer> CANCELUSERLIST= new ArrayList<Integer>(); 
	
	public JSONObject huoDongFanli(HttpServletRequest request){
		JSONObject json = new JSONObject();
		String total = request.getParameter("total");
		String beginTime = request.getParameter("beginTime");
		String endTime =  request.getParameter("endTime");
		String percent = request.getParameter("percent");
		String maxReturn = request.getParameter("maxReturn");
		String delUserStr = request.getParameter("delUserStr");
		//首先根据时间查询自然日内所有成功支付的订单
		Calendar calendar = Calendar.getInstance();
		Date begin = null;
		try {
			begin = DateUtils.parseDate(beginTime, GlobalsKeys.dateParsePatterns);
		} catch (ParseException e) {
			json.put("code", 1);
			json.put("msg", "时间格式转换出错");
			return json;
		}
		//获取剔除返利用户名单
		if(delUserStr != null && !("").equals(delUserStr)){
			String[] userArray = delUserStr.split(",");
			if(userArray != null && userArray.length >0){
				for(int i = 0;i < userArray.length;i++){
					if(userArray[i] != null && !("").equals(userArray[i])){
						CANCELUSERLIST.add(Integer.parseInt(userArray[i]));
					}
				}
			}
		}else{
			CANCELUSERLIST.clear();
		}
		if(maxReturn != null && !("").equals(maxReturn)){
			MAX_RETURN_MONEY = Double.parseDouble(maxReturn);
		}
		if(total != null){
			TOTAL_RETURN_MONEY = Double.parseDouble(total);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map map = new HashMap();
		if(endTime != null && !("").equals(endTime)){
			map.put("endTime", endTime);
		}else{
			calendar.setTime(begin);
			calendar.add(Calendar.DATE, 1);
			Date end = calendar.getTime();
			map.put("endTime", sf.format(end));
		}
		map.put("orderStatus", OrdersDao.ORDER_STATUS_SUCCESS);
		map.put("beginTime", sf.format(begin));
		PageInfo<Orders> pageInfo = getOrdersService().queryOrderList(map, 1, 100000, "order_time", "ASC");
		if(pageInfo.getItems() == null ){
			json.put("code", 1);
			json.put("msg", "无相关活动数据");
			return json;
		}
		List<Orders> list = pageInfo.getItems();
		List<JSONObject> showList = new  ArrayList<JSONObject>();
		JSONObject tempJson = null;
		DecimalFormat df = new DecimalFormat("0.00");
		json.put("data", "[]");
		for(int i = 0;i < list.size();i++){
			Orders order = list.get(i);
			int uid = order.getOrderBuyUid();
			if(validate(uid)){
				Member member = getMemberService().findMemberByUid(uid);
				double orderAmount = order.getOrderAmount();
				double rightNow = orderAmount * Double.parseDouble(percent) / 100;
				if(TOTAL_RETURN_MONEY > 0){
					tempJson = getJson(showList,String.valueOf(order.getOrderBuyUid()));
					if(tempJson == null){
						tempJson = new JSONObject();
						double returnMoney = this.getReturnMoney(TOTAL_RETURN_MONEY, 0d, rightNow);
						tempJson.put("uid",uid);
						tempJson.put("count",1);
						tempJson.put("returnMoney",df.format(returnMoney));
						tempJson.put("totalMoney", df.format(orderAmount));
						if(member != null){
							tempJson.put("mobile", member.getMobile());
						}
						showList.add(tempJson);
					}else{
						double amount = tempJson.getDouble("totalMoney") + orderAmount;
						double returnMoney = this.getReturnMoney(TOTAL_RETURN_MONEY,tempJson.getDouble("returnMoney"), rightNow);
						int count = tempJson.getInt("count") + 1;
						tempJson.put("count",count);
						tempJson.put("returnMoney",df.format(tempJson.getDouble("returnMoney") + returnMoney));
						tempJson.put("totalMoney", df.format(amount));
					}
				}
			}
		}
		JSONArray rows = JSONArray.fromObject(showList);
		json.put("code", "0");
		json.put("data", rows);
		json.put("left", df.format(TOTAL_RETURN_MONEY));
		json.put("total", total);
		return json;
	}
	/**
	 * 判断是否存在
	 * @param showList
	 * @param key
	 * @return
	 */
	protected JSONObject getJson(List<JSONObject> showList,String key){
		JSONObject outJson = null;
		if(showList == null){
			return null;
		}
		for(int i=0 ;i < showList.size();i++){
			JSONObject jsonObject = showList.get(i);
		       String uid = jsonObject.getString("uid");
		       if(uid.equals(key)){
		    	   outJson = jsonObject;
		    	   break;
		       }
		}
	   return outJson;
	}
	/**
	 * 获取返利的方法
	 * @param total 当前总金额
	 * @param userhave 用户当前获取
	 * @param rightNow 当前订单获取
	 * @return
	 */
	protected double getReturnMoney(double total,double userhave,double rightNow){
		double returnMoney = 0d;
		if(total <= 0){
			return returnMoney;
		}else{
			if(total >= rightNow){
				//总额还充足
				if(userhave >= MAX_RETURN_MONEY){
					return returnMoney;
				}else{
					double temp = userhave + rightNow;
					if(temp >= MAX_RETURN_MONEY){
						returnMoney = MAX_RETURN_MONEY - userhave;
						TOTAL_RETURN_MONEY -= returnMoney;
					}else{
						returnMoney = rightNow;
						TOTAL_RETURN_MONEY -= rightNow;
					}
				}
			}else{
				if(userhave >= MAX_RETURN_MONEY){
					return returnMoney;
				}else{
					double temp = userhave + total;
					if(temp >= MAX_RETURN_MONEY){
						returnMoney = MAX_RETURN_MONEY - userhave;
						TOTAL_RETURN_MONEY -= returnMoney;
					}else{
						returnMoney = total;
						TOTAL_RETURN_MONEY -= total;
					}
				}				
			}
		}
		return returnMoney;
	}
	/**
	 * 验证用户是否有返利的权利
	 * @param uid 验证的用户ID
	 * @return
	 */
	protected boolean validate(int uid){
		if(CANCELUSERLIST.size() == 0){
			return true;
		}
		if(CANCELUSERLIST.contains(uid)){
			return false;
		}
		return true;
	}
	
	protected OrdersService getOrdersService(){
		return SpringFactory.getBean("OrdersService");	
	}
	
	protected MemberService getMemberService(){
		return SpringFactory.getBean("MemberService");	
	}
%>