<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="cn.juhaowan.product.service.GameService"%>
<%@page import="cn.juhaowan.product.service.GameVersionService"%>
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
<%@page import="cn.juhaowan.product.service.ProductService"%>
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
<%!Logger log = LoggerFactory.getLogger("productOnsale_jsp");%>
<%
    //返回成功数据
	JSONObject json = new JSONObject();
    JSONObject jsonObject = null;
    JSONArray list = null;
    try{
    	
	String data = request.getParameter("data");
	String encodeKey = request.getParameter("encodeKey");
	data = URLDecoder.decode(data);
	String md5key = MD5.encode(data.trim() + "sereblwerijwerie");
	System.out.println("data== " + data);
	System.out.println("md5key== " + md5key);
	System.out.println("encodeKey== " + encodeKey);
	jsonObject = JSONObject.fromObject(data);
	list = jsonObject.getJSONArray("data");
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
		//上架成功计数
		int countOnsaleSuc = 0;
		//上架失败计数
		int countOnsaleFail = 0;
		//更新成功计数
		int countUpdateSuc = 0;
		//更新失败计数
		int countUpdateFail = 0;
		ProductService pService = SpringFactory.getBean("ProductService");

		List<Map<String, String>> onsaleFailList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> updateFailList = new ArrayList<Map<String, String>>();

		List<Map<String, String>> onsaleSucList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> updateSucList = new ArrayList<Map<String, String>>();
		//获得需要上架的数据列表
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> onsalefailMap = new HashMap<String, String>();
			Map<String, String> updatefailMap = new HashMap<String, String>();

			Map<String, String> onsaleSucMap = new HashMap<String, String>();
			Map<String, String> updateSucMap = new HashMap<String, String>();
			
			JSONObject o = JSONObject.fromObject(list.get(i));
			
			//判断重复
			String areaName = o.getString("game_area_name").trim();
			String  gamevesion = o.getString("game_vesion_id").trim();
			int gid = Integer.parseInt(o.getString("game_id").trim());
			String account = o.getString("account");
			String password = o.getString("password");
			String qq = "22432137";
			
			JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
			String esql = "SELECT COUNT(1) FROM `product_c2c` WHERE game_login_id = ? AND game_login_password = ? AND contact_qq = ?";
			int cc = jdbcTemplate.queryForInt(esql,account,GamePasswdUtil.endcode(password),qq);
			
			if(cc > 0){
				continue;
			}
			
			String sql ="SELECT * FROM `game_partition` WHERE game_id=? AND game_version_id=? AND partition_name LIKE  '%"+areaName+"%'";
			List<Map<String,Object>> par = jdbcTemplate.queryForList(sql,gid,gamevesion);
			if(par.size() == 0){
				json.put("result", "1");
				json.put("msg", "分区编号不存在");
				out.println(json);
				return;
			}
			System.out.println("===" + par.size());
			
			String key = "0";
			//商品信息设置--开始--
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			Product p = new Product();
			p.setGameAreaId(Integer.parseInt(par.get(0).get("game_partition_id").toString()));
			String pId = pService.generationProductId("PRO");
			p.setProductName(o.getString("product_name") + "=" + o.getString("product_single_price") + "元");
			//p.setProductSubtitle(o.getString("product_subtitle"));
			p.setProductSubtitle("");
			p.setProductType(Integer.parseInt(o.getString("product_type")));
			p.setProductSingleNumber(Long.parseLong(o.getString("product_single_number")));
			//p.setProductSoldoutNumber(Integer.parseInt(o.getString("product_soldout_number")));
			p.setProductSinglePrice(Double.parseDouble(o.getString("product_single_price")));
			p.setProductStock(Integer.parseInt(o.getString("product_stock")));
			p.setProductInfo(o.getString("product_info"));
			p.setProductOnsaleModel(1);
			p.setProductStatus(7);
			p.setProductRemark("接口批量导入__");
			p.setProductWeight(100);
			//p.setProductWeight(Integer.parseInt(o.getString("product_weight")));
			p.setNoProductFlag(0); //
			p.setProductValidity(sdf.parse("2023-12-23 14:05:05"));
			p.setUserId(Integer.parseInt(o.getString("user_id")));
			p.setGameId(gid);
// 			p.setGameUid(o.getString("game_uid"));
// 			p.setGameRoleId(o.getString("game_role_id"));
// 			p.setGameRoleName(o.getString("game_role_name"));
			p.setGameUid("0");
			p.setGameRoleId("0");
			p.setGameRoleName("0");
			p.setUnit("");
			//p.setChannelId(Integer.parseInt(o.getString("channel_id"))); //游戏渠道
			p.setChannelId(100);
			p.setProductOriginalPrice(Double.parseDouble(o.getString("product_original_price")));
			p.setSonChannelId("");
			p.setIsShow(Integer.parseInt(o.getString("is_show")));
			
			//商品c2c表
			ProductC2c c2c = new ProductC2c();
			c2c.setProductId(pId);
			c2c.setGameLoginId(account);
			c2c.setGameLoginPassword(password);
			c2c.setContactQq(qq);
			c2c.setGoodsPosition(4);
			c2c.setIs_verify(1);
			c2c.setCustomerServiceId(0);
			c2c.setAccountVerify(1);
			//商品信息设置  --结束--
			//查询商品表，是否存在  【更新表】
			Product flagProduct = pService.queryByProductId(key);
			//如果存在 更新
			if (null != flagProduct) {
				try {
					p.setProductId(key);
					p.setModifyTime(new Date());
					//下架
					if (flagProduct.getProductStatus() == 7) {
						pService.productOffShelves(Integer.parseInt(o.getString("user_id")), key, 8);
					}
					//update
					int updateflag = pService.updateProduct(p);

					//更新失败
					if (updateflag == 1) {
						countUpdateFail++;
						updatefailMap.put(key,"");
						updateFailList.add(updatefailMap);
					} else {
						//上架 
						pService.productOnsale(Integer.parseInt(o.getString("user_id")), key, "");
						//}
						//更新c2c表
						//pService.updateC2C(c2c);
						updateSucMap.put(key,"");
						countUpdateSuc++;
						updateSucList.add(updateSucMap);
					}
				} catch (Exception ee) {
					System.out.println("更新----");
					ee.printStackTrace();
					log.error("", ee);
				}
			} else { //不存在  【添加并且上架】 
				try {
					p.setProductId(pId);
					p.setCreateTime(new Date());
					p.setOnSaleTime(new Date());
					int j = pService.insertProductFront(p);
					//上架成功
					if (j == 0) {
						//System.out.println("上架成功==" + pId);
						int n = pService.insertProductC2c(pId,account,password, "",qq, 1);
						onsaleSucMap.put(pId,"");
						countOnsaleSuc++;
						onsaleSucList.add(onsaleSucMap);
					} else {//上架失败
						System.out.println("上架失败==" + pId);
						onsalefailMap.put(key,"");
						countOnsaleFail++;
						onsaleFailList.add(onsalefailMap);
					}
				} catch (Exception ee) {
					System.out.println("添加----");
					ee.printStackTrace();
					log.error("", ee);
				}

			}
		}
		JSONArray onFailList = JSONArray.fromObject(onsaleFailList);
		JSONArray upFailList = JSONArray.fromObject(updateFailList);

		JSONArray onSucList = JSONArray.fromObject(onsaleSucList);
		JSONArray upSucList = JSONArray.fromObject(updateSucList);
		json.put("result", "0");
		//json.put("countOnsaleSuc", countOnsaleSuc); //上架成功数量
		//json.put("countOnsaleFail", countOnsaleFail);//上架失败数量
		//json.put("countUpdateSuc", countUpdateSuc); //更新成功数量
		//json.put("countUpdateFail", countUpdateFail);//更新失败数量
		//json.put("onsaleFailList", onFailList); //上架失败商品id列表
		//json.put("updateFailList", upFailList); //更新失败商品id列表
		json.put("onsaleSucList", onSucList); //上架失败商品id列表
		//json.put("updateSucList", upSucList); //更新失败商品id列表
		out.println(json.toString());
		System.out.println(json);
	} catch (Exception e) {
		System.out.println("-----------onsale--");
		System.out.println(e.getLocalizedMessage());
		for (StackTraceElement el : e.getStackTrace()) {
			System.out.println("methodName : " + el.getMethodName());
			System.out.println("LineNumber : " + el.getLineNumber());
		}
		log.error("", e);
		e.printStackTrace();

	}
%>