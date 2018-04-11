<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.juhaowan.product.service.ProductService"%>
<%@page import="cn.juhaowan.product.vo.Product"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.net.URLDecoder"%>
<%
	try {
		String data = request.getParameter("data");
		//加密key
		String encodeKey = request.getParameter("encodeKey");
		data = URLDecoder.decode(data);
		String md5key = MD5.encode(data + "sereblwerijwerie");
		//StringBuffer sb = new StringBuffer() ; 
		//InputStream is = request.getInputStream(); 
		//InputStreamReader isr = new InputStreamReader(is);   
		//BufferedReader br = new BufferedReader(isr); 
		//String s = ""; 
		//while((s=br.readLine())!=null){ 
		//sb.append(s) ; 
		//} 
		//String data =sb.toString(); 
		//System.out.println("===offsale===" + data);
		//String encodeKey = data.split("`")[0];
		//String dataresult = data.split("`")[1];
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONArray list = jsonObject.getJSONArray("data");
		ProductService pService = SpringFactory.getBean("ProductService");

		List<Map<String, String>> offSucList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> offFailList = new ArrayList<Map<String, String>>();

		JSONObject json = new JSONObject();
		if (!md5key.toLowerCase().equals(encodeKey.toLowerCase())) {
			json.put("result", "1");
			out.println(json);
			//System.out.println(json);
		} else {
			//下架成功数量
			int countSuc = 0;
			//下架失败数量
			int countFail = 0;
			//获得需要下架的数据列表
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> offSucMap = new HashMap<String, String>();
				Map<String, String> offFailMap = new HashMap<String, String>();
				JSONObject o = JSONObject.fromObject(list.get(i));
				//System.out.println("pid ==" + o.getString("product_id"));
				String pId = o.getString("product_id");
				String oId = o.getString("product_remark");
				int m = pService.productOffShelves(69583, pId, 8);
				if (m == 0) {
					countSuc++;
					offSucMap.put(pId, oId);
					offSucList.add(offSucMap);
				} else {
					countFail++;
					offFailMap.put(pId, oId);
					offFailList.add(offFailMap);
				}
			}
			json.put("result", "0");
			json.put("countSuc", countSuc); //下架成功数量
			json.put("countFail", countFail);//下架失败数量
			json.put("offSucList", offSucList);//下架成功商品id列表
			json.put("offFailList", offFailList);//下架失败商品id列表
			out.println(json.toString());
			//System.out.println(json);
		}
	} catch (Exception e) {
		System.out.println("-----------offsale--");
		e.printStackTrace(System.out);
	}
%>