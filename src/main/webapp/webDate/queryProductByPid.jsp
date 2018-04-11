<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.collections.map.HashedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.product.vo.Product"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.juhaowan.product.service.ProductService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%
	JSONObject json = new JSONObject();
	try {
		String productId = request.getParameter("product_id");
		//加密key
		String encodeKey = request.getParameter("encodeKey");
		String md5key = MD5.encode(productId + "sereblwerijwerie");
		ProductService ps = SpringFactory.getBean("ProductService");
		if (null == productId || null == encodeKey) {
			json.put("result", "1");
			json.put("msg", "参数为空");
			out.println(json);
			System.out.println(json);
			return;
		}

		if (md5key.toLowerCase().equals(encodeKey.toLowerCase())) {
			json.put("result", "1");
			json.put("msg", "加密验证不匹配");
			out.println(json);
			System.out.println(json);
			return;
		}

		Product p = ps.queryByProductId(productId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != p) {
			json.put("result", "0");
			json.put("msg", "商品存在");
			Map<String,Object> pMap = new HashMap<String,Object>();
			pMap.put("product_id", p.getProductId());//商品id
			pMap.put("product_name", p.getProductName());//商品名称
			pMap.put("product_subtitle", p.getProductSubtitle());//商品小标题
			pMap.put("product_type", p.getProductType());//商品类型
			pMap.put("product_single_number", p.getProductSingleNumber());//商品单件数量
			pMap.put("product_soldout_number", p.getProductSoldoutNumber());//商品售出件数 
			pMap.put("product_single_price", p.getProductSinglePrice());//商品单价
			pMap.put("product_stock", p.getProductStock());//商品库存
			pMap.put("product_info", p.getProductInfo());//商品简介
			pMap.put("product_onsale_model", p.getProductOnsaleModel());//售卖模式 api 寄售
			pMap.put("product_status", p.getProductStatus());//商品状态
			pMap.put("product_remark", p.getProductRemark());//商品备注
			pMap.put("product_weight", p.getProductWeight());//商品权重
			pMap.put("no_product_flag", p.getNoProductFlag());//是否无货赔付
			pMap.put("product_validity", p.getProductValidity()!=null?sdf.format(p.getProductValidity()):"");//商品有效时间
			pMap.put("user_id", p.getUserId());//商品卖家uid
			pMap.put("game_id", p.getGameId());//游戏id
			pMap.put("game_area_id", p.getGameAreaId());//游戏分区id
			pMap.put("game_server_id", p.getGameServerId());//游戏服务器id 
			pMap.put("game_uid", p.getGameUid());//游戏uid
			pMap.put("game_role_id", p.getGameRoleId());//游戏角色id
			pMap.put("game_role_name", p.getGameRoleName());//游戏角色名称
			pMap.put("game_user_lever", p.getGameUserLever());//游戏角色等级
			pMap.put("create_time", p.getCreateTime()!=null?sdf.format(p.getCreateTime()):"");//商品创建时间
			pMap.put("modify_time", p.getModifyTime()!=null?sdf.format(p.getModifyTime()):"");//商品更新时间
			pMap.put("on_sale_time", p.getOnSaleTime()!=null?sdf.format(p.getOnSaleTime()):"");//上架时间
			pMap.put("off_shelves_time", p.getOffShelvesTime()!=null?sdf.format(p.getOffShelvesTime()):"");//下架时间
			pMap.put("final_transaction_time", p.getFinalTransactionTime()!=null?sdf.format(p.getFinalTransactionTime()):"");//最后成交时间
			pMap.put("verify_time", p.getVerifyTime()!=null?sdf.format(p.getVerifyTime()):"");//商品审核时间
			pMap.put("sell_pid", p.getSellPid());//上架号
			pMap.put("unit", p.getUnit());//商品单位
			pMap.put("soldout_time", p.getSoldoutTime()!=null?sdf.format(p.getSoldoutTime()):"");//售完时间
			pMap.put("user_nickName", p.getUserNickname());//卖家昵称
			pMap.put("game_channel_id", p.getChannelId());//游戏渠道id
			pMap.put("quick_deliver_flag", p.getQuickDeliverFlag());//是否快速发货
		
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