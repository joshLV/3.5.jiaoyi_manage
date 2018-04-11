<%@page import="java.net.URLDecoder" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="javax.servlet.http.HttpServletRequest" %>
<%@page import="org.springframework.jdbc.core.JdbcOperations" %>
<%@page import="cn.jugame.util.MD5" %>
<%@page import="cn.jugame.util.SpringFactory" %>
<%@page import="cn.juhaowan.member.dao.D" %>
<%@page import="cn.juhaowan.product.service.ProductService" %>
<%@page import="cn.juhaowan.product.vo.Product" %>
<%@page import="cn.juhaowan.product.vo.ProductC2c" %>
<%@page import="cn.juhaowan.util.XObject" %>
<%@page import="cn.juhaowan.util.XProperty" %>
<%@page import="net.sf.json.JSONArray" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%!
JSONObject build_msg(boolean succ, String msg){
	JSONObject obj = new JSONObject();
	obj.put("success", succ);
	obj.put("msg", msg);
	return obj;
}

JSONObject build_msg(boolean succ, String msg, JSONObject data){
	JSONObject obj = new JSONObject();
	obj.put("success", succ);
	obj.put("msg", msg);
	obj.put("data", data);
	return obj;
}

JSONObject process(HttpServletRequest request){
	JdbcOperations jdbc = SpringFactory.getBean("jdbcTemplate");
	ProductService pService = SpringFactory.getBean("ProductService");
	
	String msg = null;
	String vcode = null;
	try{
		msg = URLDecoder.decode(request.getParameter("data"), "UTF-8");
		vcode = request.getParameter("vcode");
	}catch(Exception e){
		return build_msg(false, "参数错误");
	}

	try{
		String chk_code = MD5.encode(msg.trim() + "sereblwerijwerie");
		if(!vcode.equalsIgnoreCase(chk_code)){
			return build_msg(false, "签名失败");
		}
	}catch(Exception e){
		return build_msg(false, "签名失败");
	}
	
	JSONArray fails = new JSONArray();
	
	JSONObject json = JSONObject.fromObject(msg);
	JSONArray data = json.getJSONArray("data");
	for(int i=0; i<data.size(); ++i){
		JSONObject obj = data.getJSONObject(i);
		
		//这次是商品上架的标识ID
		String uniq_id = obj.getString("id");
		uniq_id = uniq_id.substring(0, 20);
		
		//这三部分确定游戏分区id
		String game_area_name = obj.getString("game_area_name");
		int game_vesion_id = obj.getInt("game_version_id");
		int game_id = obj.getInt("game_id");
		
		String sql = "SELECT * FROM `game_partition` WHERE `partition_name` LIKE '%" + game_area_name + "%' AND `game_version_id` = " + game_vesion_id + " AND `game_id` = " + game_id + " LIMIT 1";
		XObject row = D.proxy("game_partition", jdbc).query_row(sql);
		if(row == null || row.getAsInt("game_partition_id") == 0){
			JSONObject o = new JSONObject();
			o.put("id", uniq_id);
			o.put("msg", "no game_area");
			fails.add(o);
			continue;
		}
		
		try{
			String product_id = "APRO-" + uniq_id;
			
			//商品表
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			Product p = new Product();
			p.setProductId(product_id);
			p.setGameAreaId(row.getAsInt("game_partition_id"));
			p.setProductName(obj.getString("product_name") + "=" + obj.getString("product_single_price") + "元");
			p.setProductSubtitle("");
			p.setProductType(obj.getInt("product_type"));
			p.setProductSingleNumber(obj.getLong("product_single_number"));
			p.setProductSinglePrice(obj.getDouble("product_single_price"));
			p.setProductStock(obj.getInt("product_stock"));
			p.setProductInfo(obj.getString("product_info"));
			p.setProductOnsaleModel(1);
			p.setProductStatus(7);
			p.setProductRemark("自动上架");
			p.setProductWeight(100);
			p.setNoProductFlag(0); //
			p.setProductValidity(sdf.parse("2099-12-23 14:05:05"));
			p.setUserId(obj.getInt("user_id"));
			p.setGameId(game_id);
			p.setGameUid("0");
			p.setGameRoleId("0");
			p.setGameRoleName("0");
			p.setUnit("");
			p.setChannelId(obj.getInt("channel_id"));
			p.setProductOriginalPrice(obj.getDouble("product_original_price"));
			p.setSonChannelId("");
			p.setIsShow(obj.getInt("is_show"));
			
			//商品c2c表
			ProductC2c c2c = new ProductC2c();
			c2c.setProductId(product_id);
			c2c.setGameLoginId(obj.getString("account"));
			c2c.setGameLoginPassword(obj.getString("password"));
			c2c.setContactQq("");
			c2c.setGoodsPosition(4);
			c2c.setIs_verify(1);
			c2c.setCustomerServiceId(0);
			c2c.setAccountVerify(1);
			
			Product flagProduct = pService.queryByProductId(product_id);
			//更新商品
			if(flagProduct != null){
				p.setModifyTime(new Date());
				//先下架
				pService.productOffShelves(obj.getInt("user_id"), product_id, 8);
				int updateflag = pService.updateProduct(p);
				//更新失败
				if(updateflag != 0){
					JSONObject o = new JSONObject();
					o.put("id", uniq_id);
					o.put("msg", "update product fail");
					fails.add(o);
				}else{
					//再上架
					pService.productOnsale(obj.getInt("user_id"), product_id, "");
				}
			}
			//上架商品
			else{
				p.setProductId(product_id);
				p.setCreateTime(new Date());
				p.setOnSaleTime(new Date());
				int j = pService.insertProductFront(p);
				//上架失败
				if(j != 0){
					JSONObject o = new JSONObject();
					o.put("id", uniq_id);
					o.put("msg", "onsale product fail");
					fails.add(o);
				}else{
					//C2C上架
					pService.insertProductC2c(product_id, obj.getString("account"), obj.getString("password"), "", "", 1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("id", uniq_id);
			o.put("msg", "fail");
			fails.add(o);
		}
	}
	
	JSONObject o = new JSONObject();
	o.put("fails", fails);
	return build_msg(true, "ok", o);
}
%>
<%
JSONObject rtn = process(request);
out.println(rtn.toString());
%>