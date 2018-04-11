<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.product.vo.GameProductType"%>
<%@page import="cn.juhaowan.product.vo.GamePartition"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.juhaowan.product.vo.Game"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.juhaowan.product.service.GameService"%>
<%@page import="cn.juhaowan.product.service.GameProductTypeService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.HashMap"%>
<%
    JSONObject json = new JSONObject();
    try{
	String  random =  request.getParameter("random");
    //加密key
    String encodeKey = request.getParameter("encodeKey");
    String md5key = MD5.encode(random+"sereblwerijwerie");
    
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> gpshowList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> gptshowList = new ArrayList<Map<String, Object>>();
	
	GameService gs = SpringFactory.getBean("GameService");
	GameProductTypeService gpts = SpringFactory.getBean("GameProductTypeService");
    if(null == random ){
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
	List<Game> gList = gs.listAllGame();
    String game = "";
    String game_partition = "";
    String game_product_type = "";
	if(null == gList){
		json.put("result", "1");
		json.put("msg", "不存在游戏"); 
		out.println(json);
	    System.out.println(json);
	    return;
	}
	for(int i = 0; i < gList.size();i++){
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("game_id", gList.get(i).getGameId());
		outMap.put("game_name", gList.get(i).getGameName());
		showList.add(outMap);
        //		
		List<GamePartition> gpList = gs.queryGamePartitionByGameId(gList.get(i).getGameId());
				if(null != gpList){
					for(int j = 0; j < gpList.size();j++){
					Map<String, Object> gpoutMap = new HashMap<String, Object>();
					gpoutMap.put("game_id", gpList.get(j).getGameId());
					gpoutMap.put("game_partition_id", gpList.get(j).getGamePartitionId());
					gpoutMap.put("partition_name", gpList.get(j).getPartitionName());
					gpshowList.add(gpoutMap);
					}
				}
				
				List<GameProductType> gptList = gpts.queryGameProductTypeByGameId(gList.get(i).getGameId());
				if(null != gptList){
					for(int k = 0; k < gptList.size();k++){
					Map<String, Object> gptoutMap = new HashMap<String, Object>();
					gptoutMap.put("game_id", gptList.get(k).getGameId());
					gptoutMap.put("product_type_id", gptList.get(k).getProductTypeId());
					gptoutMap.put("unit", gptList.get(k).getUnit());
					 //gpoutMap.put("type_name", gptList.get(k).getTypeName());
					gptshowList.add(gptoutMap);
					}
				}
	}

	game = JSONArray.fromObject(showList).toString();
	game_partition = JSONArray.fromObject(gpshowList).toString();
	game_product_type = JSONArray.fromObject(gptshowList).toString();
    json.put("result", "0");
    json.put("msg", "查询成功");
    json.put("game", game);
    json.put("game_partition",game_partition);
    json.put("game_product_type",game_product_type);
    out.println(json.toString());
    //System.out.println(json);
  	
    }catch(Exception e){
    	  json.put("result", "1");
    	  json.put("msg", "出现异常");
    	  out.println(json.toString());
    	//System.out.println("-----------gameinfo--");
    	e.printStackTrace(System.out);
    }
  	
%>