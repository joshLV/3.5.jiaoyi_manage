//package cn.jugame.admin.web.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.util.RequestUtils;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.service.ProductService;
//import cn.juhaowan.product.vo.Game;
//import cn.juhaowan.product.vo.GamePartition;
//import cn.juhaowan.product.vo.ProductType;
//
///**
// * 共用JSON
// * 
// * @author APXer
// * 
// */
//@Controller
//@RequestMapping(value = "/publicJson")
//public class PublicJson {
//	@Autowired
//	private GameService gameService;
//	@Autowired
//	private ProductService productService;
//
//	/**
//	 * 所有商品类型
//	 * 
//	 * @param request
//	 * @param model
//	 */
//	@RequestMapping(value = "/queryAllProductType_json")
//	@ResponseBody
//	public JSONArray queryAllProductTypeJson(HttpServletRequest request,
//			Model model) {
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		List<ProductType> ptList = productService.queryProductTypeListNoCache();
//		for (ProductType pt : ptList) {
//			Map<String, Object> ptMap = new HashMap<String, Object>();
//			ptMap.put("productId", pt.getId());
//			ptMap.put("productName", pt.getName());
//			showList.add(ptMap);
//		}
//		return JSONArray.fromObject(showList);
//	}
//	/**
//	 * 所有游戏
//	 * 
//	 * @param request
//	 * @param model
//	 */
//	@RequestMapping(value = "/queryAllGame_json")
//	@ResponseBody
//	public JSONArray queryAllGameJson(HttpServletRequest request, Model model) {
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		@SuppressWarnings("unchecked")
//		List<Game> gameList = gameService.listAllGame();
//		for (Game game : gameList) {
//			Map<String, Object> gameMap = new HashMap<String, Object>();
//			gameMap.put("gameId", game.getGameId());
//			gameMap.put("gameName", game.getGameName());
//			showList.add(gameMap);
//		}
//		return JSONArray.fromObject(showList);
//	}
//	/**
//	 * 根据游戏ID查询该游戏下所有区
//	 * 
//	 * @param request
//	 * @param model
//	 */
//	@RequestMapping(value = "/queryPartitionListByGameId")
//	@ResponseBody
//	public JSONObject queryPartitionListByGameId(HttpServletRequest request,
//			Model model) {
//		int gameId = RequestUtils.getParameterInt(request, "gameId", 0);
//		JSONObject data = new JSONObject();
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//
//		List<GamePartition> gamePartitionList = gameService
//				.queryGamePartitionByGameId(gameId);
//		data.put("total", gamePartitionList.size());
//
//		for (GamePartition gp : gamePartitionList) {
//			Map<String, Object> gpMap = new HashMap<String, Object>();
//			gpMap.put("gamePartitionId", gp.getGamePartitionId());
//			gpMap.put("partitionName", gp.getPartitionName());
//			showList.add(gpMap);
//		}
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//
//		return JSONObject.fromObject(data);
//	}
//	/**
//	 * 根据游戏ID查询该游戏下所有区Array
//	 * 
//	 * @param request
//	 * @param model
//	 */
//	@RequestMapping(value = "/queryAllPartition_json")
//	@ResponseBody
//	public JSONArray queryAllPartitionJson(HttpServletRequest request,
//			Model model) {
//		String gameId = request.getParameter("gameId");
//		if (StringUtils.isBlank(gameId)) {
//			return null;
//		}
//		if (gameId.contains(",")) {
//			return null;
//		}
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		List<GamePartition> gamePartitionList = gameService
//				.queryGamePartitionByGameId(Integer.parseInt(gameId));
//		for (GamePartition gp : gamePartitionList) {
//			Map<String, Object> gpMap = new HashMap<String, Object>();
//			gpMap.put("gamePartitionId", gp.getGamePartitionId());
//			gpMap.put("partitionName", gp.getPartitionName());
//			showList.add(gpMap);
//		}
//		return JSONArray.fromObject(showList);
//	}
//	/**
//	 * 所有商品种类
//	 * 
//	 * @param request
//	 * @param model
//	 */
//	@RequestMapping(value = "/queryAllProductCategory_json")
//	@ResponseBody
//	public JSONArray queryAllProductCategoryJson(HttpServletRequest request,
//			Model model) {
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> ptMap = new HashMap<String, Object>();
//		ptMap.put("id", 1);
//		ptMap.put("name", "虚拟币");
//		Map<String, Object> ptMap2 = new HashMap<String, Object>();
//		ptMap2.put("id", 2);
//		ptMap2.put("name", "非虚拟币");
//		showList.add(ptMap);
//		showList.add(ptMap2);
//		return JSONArray.fromObject(showList);
//	}
//
//}
