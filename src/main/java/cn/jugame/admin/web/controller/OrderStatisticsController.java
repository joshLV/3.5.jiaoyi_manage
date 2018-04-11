//package cn.jugame.admin.web.controller;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.admin.web.form.DefaultOrderStatistics;
//import cn.jugame.dal.callback.JuRowCallbackHandler;
//import cn.jugame.util.RequestUtils;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.service.ProductService;
//import cn.juhaowan.product.vo.Game;
//import cn.juhaowan.product.vo.GamePartition;
//import cn.juhaowan.product.vo.ProductType;
//
///**
// * 
// * @author APXer
// * 
// */
//@Controller
//@RequestMapping(value = "/statistics")
//public class OrderStatisticsController {
//	@Autowired
//	private JdbcOperations jdbcOperations;
//	@Autowired
//	private GameService gameService;
//	@Autowired
//	private ProductService productService;
//
//	/**
//	 * 订单统计列表
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 转向地址
//	 */
//	@RequestMapping(value = "/orderStatisticsList")
//	public String orderStatisticsList(HttpServletRequest request, Model model) {
//		// 游戏列表
//		@SuppressWarnings("unchecked")
//		List<Game> gameList = gameService.listAllGame();
//		model.addAttribute("gameList", gameList);
//
//		List<GamePartition> gamePartitionList = gameService
//				.queryGamePartitionByGameId(gameList.get(0).getGameId());
//		model.addAttribute("gamePartitionList", gamePartitionList);
//		// 商品类型 带3分钟缓存
//		List<ProductType> productTypeList = productService
//				.queryProductTypeList();
//		model.addAttribute("productTypeList", productTypeList);
//		// 游戏列表
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT");
//		sql.append(" os.order_date,os.game_id,os.game_name,os.game_area_name,os.game_area_id,os.product_type,os.product_category,");
//		sql.append(" SUM(os.order_total) order_total,");
//		sql.append(" SUM(os.order_amount) order_amount,");
//		sql.append(" SUM(os.pay_order_count) pay_order_count,");
//		sql.append(" SUM(os.pay_order_amount) pay_order_amount,");
//		sql.append(" (SUM(os.pay_order_count)/sum(os.order_total)) order_payment_rate,");
//		sql.append(" SUM(os.fail_order_count) fail_order_count,");
//		sql.append(" SUM(os.successful_transaction_orders) successful_transaction_orders,");
//		sql.append(" SUM(os.successful_transcation_amount) successful_transcation_amount,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.pay_order_count)) successful_transcation_rate,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.order_total)) order_conversion_rate ");
//		sql.append("FROM");
//		sql.append(" order_statistics AS os ");
//		String gameId = RequestUtils.getParameter(request, "gameName", "-1");
//		String gameArea = RequestUtils.getParameter(request, "gameArea", "-1");
//		String productType = RequestUtils.getParameter(request, "productType",
//				"-1");
//		String productCategory = RequestUtils.getParameter(request,
//				"productCategory", "-1");
//		StringBuffer whereSB = new StringBuffer();
//		StringBuffer groupBySB = new StringBuffer(" GROUP BY ");
//		if (!"-1".equals(gameId)) {
//			groupBySB.append(" game_id,");
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND game_id in(" + gameId + ") ");
//			} else {
//				whereSB.append(" WHERE game_id in(" + gameId + ") ");
//			}
//		}
//		if (!"-1".equals(gameArea)) {
//			groupBySB.append(" game_area_id,");
//		}
//		if (!"-1".equals(productType)) {
//			groupBySB.append(" product_type,");
//		}
//		if (!"-1".equals(productCategory)) {
//			groupBySB.append(" product_category,");
//		}
//		groupBySB.append(" order_date ");
//		sql.append(whereSB.toString());
//		sql.append(groupBySB.toString());
//		sql.append(" WITH ROLLUP");
//		JuRowCallbackHandler<DefaultOrderStatistics> callback = new JuRowCallbackHandler<DefaultOrderStatistics>(
//				DefaultOrderStatistics.class);
//		jdbcOperations.query(sql.toString(), callback);
//		if (callback.getList() != null && callback.getList().size() > 0) {
//			model.addAttribute("totalListDOS", callback.getList());
//		}
//		return "statistics/orderStatisticsList";
//	}
//
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
//
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
//
//	@RequestMapping(value = "/gamePartitionList_select")
//	public String gamePartitionListSelect(HttpServletRequest request,
//			Model model) {
//		String gameId = request.getParameter("gameId");
//		model.addAttribute("gameId", gameId);
//		return "statistics/gamePartitionList_select";
//	}
//
//	/**
//	 * 根据游戏ID查询该游戏下所有区
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
//
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
//	/**
//	 * 订单统计列表JSON
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 查询工时率统计列表JSON
//	 */
//	@RequestMapping(value = "/orderStatisticsList_json")
//	@ResponseBody
//	public JSONObject orderStatisticsListJSON(HttpServletRequest request,
//			Model model) {
//
//		JSONObject data = new JSONObject();
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		// 游戏列表
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT");
//		sql.append(" os.order_date,os.game_id,os.game_area_id,os.game_name,os.game_area_name,os.product_type,os.product_category,");
//		sql.append(" SUM(os.order_total) order_total,");
//		sql.append(" SUM(os.order_amount) order_amount,");
//		sql.append(" SUM(os.pay_order_count) pay_order_count,");
//		sql.append(" SUM(os.pay_order_amount) pay_order_amount,");
//		sql.append(" (SUM(os.pay_order_count)/sum(os.order_total)) order_payment_rate,");
//		sql.append(" SUM(os.fail_order_count) fail_order_count,");
//		sql.append(" SUM(os.successful_transaction_orders) successful_transaction_orders,");
//		sql.append(" SUM(os.successful_transcation_amount) successful_transcation_amount,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.pay_order_count)) successful_transcation_rate,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.order_total)) order_conversion_rate ");
//		sql.append("FROM");
//		sql.append(" order_statistics AS os ");
//		int showDetail = RequestUtils.getParameterInt(request, "showDetail", 0);
//		String gameId = RequestUtils.getParameter(request, "gameName", "-1");
//		String gameArea = RequestUtils.getParameter(request, "gameArea", "-1");
//		String productType = RequestUtils.getParameter(request, "productType",
//				"-1");
//		String productCategory = RequestUtils.getParameter(request,
//				"productCategory", "-1");
//
//		String fromDays = request.getParameter("fromDays");
//		String toDays = request.getParameter("toDays");
//
//		StringBuffer whereSB = new StringBuffer();
//		StringBuffer groupBySB = new StringBuffer(" GROUP BY ");
//		// 条件个数
//		int conditionTotal = 0;
//		if (!"-1".equals(gameArea) && !"".equals(gameArea)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" game_area_id ");
//			} else {
//				groupBySB.append(" ,game_area_id ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND game_area_id in(" + gameArea + ") ");
//			} else {
//				whereSB.append(" WHERE game_area_id in(" + gameArea + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(productType) && !"".equals(productType)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" product_type ");
//			} else {
//				groupBySB.append(" ,product_type ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND product_type in(" + productType + ") ");
//			} else {
//				whereSB.append(" WHERE product_type in(" + productType + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(productCategory) && !"".equals(productCategory)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" product_category ");
//			} else {
//				groupBySB.append(" ,product_category ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND product_category in(" + productCategory
//						+ ") ");
//			} else {
//				whereSB.append(" WHERE product_category in(" + productCategory
//						+ ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(gameId) && !"".equals(gameId)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" game_id ");
//			} else {
//				groupBySB.append(" ,game_id ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND game_id in(" + gameId + ") ");
//			} else {
//				whereSB.append(" WHERE game_id in(" + gameId + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!StringUtils.isBlank(fromDays)) {// 从...
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND order_date BETWEEN ");
//				whereSB.append("'" + fromDays + "'");
//				if ("".equals(toDays)) {// 到...为止
//					whereSB.append(" AND date_format(now(),'%Y-%m-%d') ");
//				}
//			} else {
//				whereSB.append(" WHERE order_date BETWEEN ");
//				whereSB.append("'" + fromDays + "'");
//				if ("".equals(toDays)) {// 到...为止
//					whereSB.append(" AND date_format(now(),'%Y-%m-%d') ");
//				}
//			}
//		}
//		if (!StringUtils.isBlank(toDays)) {// 到...为止
//			whereSB.append(" AND ");
//			whereSB.append(" '" + toDays + "' ");
//		}
//
//		if (groupBySB.toString().length() > " GROUP BY ".length()) {
//			groupBySB.append(" ,order_date ");
//		} else {
//			groupBySB.append(" order_date ");
//		}
//
//		sql.append(whereSB.toString());
//		sql.append(groupBySB.toString());
//		sql.append(" WITH ROLLUP");
//		JuRowCallbackHandler<DefaultOrderStatistics> callback = new JuRowCallbackHandler<DefaultOrderStatistics>(
//				DefaultOrderStatistics.class);
//		jdbcOperations.query(sql.toString(), callback);
//		List<DefaultOrderStatistics> totalDOS = null;
//		if (callback.getList() != null && callback.getList().size() > 0) {
//			totalDOS = callback.getList();
//
//			List<ProductType> productTypeList = productService
//					.queryProductTypeList();
//			for (int j = 0; j < totalDOS.size(); j++) {
//				Map<String, Object> dosMap = new HashMap<String, Object>();
//				SimpleDateFormat orderDateSDF = new SimpleDateFormat(
//						"yyyy-MM-dd");
//				dosMap.put(
//						"orderDate",
//						null != totalDOS.get(j).getOrderDate() ? orderDateSDF
//								.format(totalDOS.get(j).getOrderDate()) : "");
//				dosMap.put("gameName", totalDOS.get(j).getGameName());
//				dosMap.put("gameAreaName", totalDOS.get(j).getGameAreaName());
//				dosMap.put("gameId", totalDOS.get(j).getGameId());
//				dosMap.put("gameAreaId", totalDOS.get(j).getGameAreaId());
//				dosMap.put("productType", totalDOS.get(j).getProductType());
//				for (ProductType pt : productTypeList) {
//					if (pt.getId() == totalDOS.get(j).getProductType()) {
//						dosMap.put("productTypeName", pt.getName());
//					}
//				}
//				dosMap.put("productCategory", (totalDOS.get(j)
//						.getProductCategory() == 1) ? "虚拟币" : (totalDOS.get(j)
//						.getProductCategory() == 2) ? "非虚拟币" : "");
//				dosMap.put("orderTotal", totalDOS.get(j).getOrderTotal());
//				dosMap.put("orderAmount", NumberFormat.getCurrencyInstance()
//						.format(totalDOS.get(j).getOrderAmount()));
//				dosMap.put("payOrderCount", totalDOS.get(j).getPayOrderCount());
//				dosMap.put("payOrderAmount", NumberFormat.getCurrencyInstance()
//						.format(totalDOS.get(j).getPayOrderAmount()));
//				DecimalFormat dfopr = new DecimalFormat("0.##%");
//				dosMap.put("orderPaymentRate",
//						dfopr.format(totalDOS.get(j).getOrderPaymentRate()));
//				dosMap.put("failOrderCount", totalDOS.get(j)
//						.getFailOrderCount());
//				dosMap.put("successfulTransactionOrders", totalDOS.get(j)
//						.getSuccessfulTransactionOrders());
//				dosMap.put(
//						"successfulTranscationAmount",
//						NumberFormat.getCurrencyInstance().format(
//								totalDOS.get(j)
//										.getSuccessfulTranscationAmount()));
//				DecimalFormat dfstr = new DecimalFormat("0.##%");
//				dosMap.put("successfulTranscationRate", dfstr.format(totalDOS
//						.get(j).getSuccessfulTranscationRate()));
//				DecimalFormat dfocr = new DecimalFormat("0.##%");
//				dosMap.put("orderConversionRate",
//						dfocr.format(totalDOS.get(j).getOrderConversionRate()));
//				if (showDetail == 1) {
//					if (null == totalDOS.get(j).getOrderDate()) {
//						if (conditionTotal > 1 && (j != totalDOS.size() - 1)) {
//							if (showDetail == 1) {
//								if (null == totalDOS.get(j - 1).getOrderDate()) {
//									continue;
//								}
//							}
//						}
//					}
//					showList.add(dosMap);
//				} else {
//					if (totalDOS.get(j).getOrderDate() == null) {
//						showList.add(dosMap);
//					}
//				}
//			}
//		} else {
//			data.put("total", 0);
//		}
//		data.put("total", showList.size());
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//		return JSONObject.fromObject(data);
//	}
//
//	/**
//	 * 导出订单统计文件
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/exportOrderStatisticsExcel")
//	@ResponseBody
//	public void exportOrderStatisticsExcel(HttpServletRequest request,
//			HttpServletResponse response) {
//		String outputData = request.getParameter("outputData");
//		if (!StringUtils.isBlank(outputData)) {
//			return;
//		}
//		// 游戏列表
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT");
//		sql.append(" os.order_date,os.game_id,os.game_area_id,os.game_name,os.game_area_name,os.product_type,os.product_category,");
//		sql.append(" SUM(os.order_total) order_total,");
//		sql.append(" SUM(os.order_amount) order_amount,");
//		sql.append(" SUM(os.pay_order_count) pay_order_count,");
//		sql.append(" SUM(os.pay_order_amount) pay_order_amount,");
//		sql.append(" (SUM(os.pay_order_count)/sum(os.order_total)) order_payment_rate,");
//		sql.append(" SUM(os.fail_order_count) fail_order_count,");
//		sql.append(" SUM(os.successful_transaction_orders) successful_transaction_orders,");
//		sql.append(" SUM(os.successful_transcation_amount) successful_transcation_amount,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.pay_order_count)) successful_transcation_rate,");
//		sql.append(" (SUM(os.successful_transaction_orders)/SUM(os.order_total)) order_conversion_rate ");
//		sql.append("FROM");
//		sql.append(" order_statistics AS os ");
//		int showDetail = RequestUtils.getParameterInt(request, "showDetail", 0);
//		String gameId = RequestUtils.getParameter(request, "gameName", "-1");
//		String gameArea = RequestUtils.getParameter(request, "gameArea", "-1");
//		String productType = RequestUtils.getParameter(request, "productType",
//				"-1");
//		String productCategory = RequestUtils.getParameter(request,
//				"productCategory", "-1");
//
//		String fromDays = request.getParameter("fromDays");
//		String toDays = request.getParameter("toDays");
//
//		StringBuffer whereSB = new StringBuffer();
//		StringBuffer groupBySB = new StringBuffer(" GROUP BY ");
//		// 条件个数
//		int conditionTotal = 0;
//		if (!"-1".equals(gameArea) && !"".equals(gameArea)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" game_area_id ");
//			} else {
//				groupBySB.append(" ,game_area_id ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND game_area_id in(" + gameArea + ") ");
//			} else {
//				whereSB.append(" WHERE game_area_id in(" + gameArea + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(productType) && !"".equals(productType)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" product_type ");
//			} else {
//				groupBySB.append(" ,product_type ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND product_type in(" + productType + ") ");
//			} else {
//				whereSB.append(" WHERE product_type in(" + productType + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(productCategory) && !"".equals(productCategory)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" product_category ");
//			} else {
//				groupBySB.append(" ,product_category ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND product_category in(" + productCategory
//						+ ") ");
//			} else {
//				whereSB.append(" WHERE product_category in(" + productCategory
//						+ ") ");
//			}
//			conditionTotal++;
//		}
//		if (!"-1".equals(gameId) && !"".equals(gameId)) {
//			if (" GROUP BY ".equals(groupBySB.toString())) {
//				groupBySB.append(" game_id ");
//			} else {
//				groupBySB.append(" ,game_id ");
//			}
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND game_id in(" + gameId + ") ");
//			} else {
//				whereSB.append(" WHERE game_id in(" + gameId + ") ");
//			}
//			conditionTotal++;
//		}
//		if (!StringUtils.isBlank(fromDays)) {// 从...
//			if (whereSB.toString().length() > 0) {
//				whereSB.append(" AND order_date BETWEEN ");
//				whereSB.append("'" + fromDays + "'");
//				if ("".equals(toDays)) {// 到...为止
//					whereSB.append(" AND date_format(now(),'%Y-%m-%d') ");
//				}
//			} else {
//				whereSB.append(" WHERE order_date BETWEEN ");
//				whereSB.append("'" + fromDays + "'");
//				if ("".equals(toDays)) {// 到...为止
//					whereSB.append(" AND date_format(now(),'%Y-%m-%d') ");
//				}
//			}
//		}
//		if (!StringUtils.isBlank(toDays)) {// 到...为止
//			whereSB.append(" AND ");
//			whereSB.append(" '" + toDays + "' ");
//		}
//
//		if (groupBySB.toString().length() > " GROUP BY ".length()) {
//			groupBySB.append(" ,order_date ");
//		} else {
//			groupBySB.append(" order_date ");
//		}
//
//		sql.append(whereSB.toString());
//		sql.append(groupBySB.toString());
//		sql.append(" WITH ROLLUP");
//		JuRowCallbackHandler<DefaultOrderStatistics> callback = new JuRowCallbackHandler<DefaultOrderStatistics>(
//				DefaultOrderStatistics.class);
//		jdbcOperations.query(sql.toString(), callback);
//		List<DefaultOrderStatistics> totalDOS = null;
//
//		response.setCharacterEncoding("UTF-8");
//		// response.setContentType("application/vnd.ms-excel");
//		response.setContentType("application/x-download");
//		try {
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ new String("订单统计.xls".getBytes("utf-8"), "iso-8859-1"));
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		ServletOutputStream sos = null;
//		try {
//			sos = response.getOutputStream();
//			HSSFWorkbook wb = new HSSFWorkbook();
//			HSSFSheet sheet = wb.createSheet("订单统计");
//			generateTableHeader(gameId, gameArea, productType, productCategory,
//					conditionTotal, sheet, wb);
//			if (callback.getList() != null && callback.getList().size() > 0) {
//				totalDOS = callback.getList();
//				List<ProductType> productTypeList = productService
//						.queryProductTypeList();
//				for (int j = 0; j < totalDOS.size(); j++) {
//					if (showDetail == 1) {
//						generateRow(gameId, gameArea, productType,
//								productCategory, totalDOS, productTypeList,
//								sheet, j, conditionTotal, wb, showDetail);
//					} else {
//						if (totalDOS.get(j).getOrderDate() == null) {
//							generateRow(gameId, gameArea, productType,
//									productCategory, totalDOS, productTypeList,
//									sheet, j, conditionTotal, wb, showDetail);
//
//						}
//					}
//				}
//			}
//			wb.write(sos);
//			sos.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				sos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	/**
//	 * 生成导出数据表头
//	 * 
//	 * @param gameId
//	 * @param conditionTotal
//	 * @param sheet
//	 */
//	public void generateTableHeader(String gameId, String gameArea,
//			String productType, String productCategory, int conditionTotal,
//			HSSFSheet sheet, HSSFWorkbook wb) {
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setFillForegroundColor(HSSFColor.TURQUOISE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		HSSFRow field = sheet.createRow(0);
//		HSSFCell dateTH = field.createCell((short) 0);
//		dateTH.setCellValue(new HSSFRichTextString("日期"));
//		dateTH.setCellStyle(style);
//		int ct = 1;
//		if (!"-1".equals(gameId) && !"".equals(gameId)) {
//			HSSFCell gameNameTH = field.createCell((short) ct);
//			gameNameTH.setCellValue(new HSSFRichTextString("游戏名称"));
//			gameNameTH.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(gameArea) && !"".equals(gameArea)) {
//			HSSFCell gameAreaTH = field.createCell((short) ct);
//			gameAreaTH.setCellValue(new HSSFRichTextString("游戏区名称"));
//			gameAreaTH.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(productType) && !"".equals(productType)) {
//			HSSFCell productTypeTH = field.createCell((short) ct);
//			productTypeTH.setCellValue(new HSSFRichTextString("商品类型"));
//			productTypeTH.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(productCategory) && !"".equals(productCategory)) {
//			HSSFCell productCategoryTH = field.createCell((short) ct);
//			productCategoryTH.setCellValue(new HSSFRichTextString("商品种类"));
//			productCategoryTH.setCellStyle(style);
//			ct++;
//		}
//		HSSFCell orderTotalTH = field.createCell((short) (1 + conditionTotal));
//		orderTotalTH.setCellValue(new HSSFRichTextString("订单总数"));
//		orderTotalTH.setCellStyle(style);
//		HSSFCell orderAmountTH = field.createCell((short) (2 + conditionTotal));
//		orderAmountTH.setCellValue(new HSSFRichTextString("订单总金额"));
//		orderAmountTH.setCellStyle(style);
//		HSSFCell payOrderCountTH = field
//				.createCell((short) (3 + conditionTotal));
//		payOrderCountTH.setCellValue(new HSSFRichTextString("支付成功订单数"));
//		payOrderCountTH.setCellStyle(style);
//		HSSFCell payOrderAmountTH = field
//				.createCell((short) (4 + conditionTotal));
//		payOrderAmountTH.setCellValue(new HSSFRichTextString("支付成功订单总金额"));
//		payOrderAmountTH.setCellStyle(style);
//		HSSFCell orderPaymentRateTH = field
//				.createCell((short) (5 + conditionTotal));
//		orderPaymentRateTH.setCellValue(new HSSFRichTextString("订单支付成功率"));
//		orderPaymentRateTH.setCellStyle(style);
//		HSSFCell failOrderCountTH = field
//				.createCell((short) (6 + conditionTotal));
//		failOrderCountTH.setCellValue(new HSSFRichTextString("退费订单数"));
//		failOrderCountTH.setCellStyle(style);
//		HSSFCell successfulTransactionOrdersTH = field
//				.createCell((short) (7 + conditionTotal));
//		successfulTransactionOrdersTH.setCellValue(new HSSFRichTextString(
//				"交易成功订单数"));
//		successfulTransactionOrdersTH.setCellStyle(style);
//		HSSFCell successfulTranscationAmountTH = field
//				.createCell((short) (8 + conditionTotal));
//		successfulTranscationAmountTH.setCellValue(new HSSFRichTextString(
//				"交易成功总金额"));
//		successfulTranscationAmountTH.setCellStyle(style);
//		HSSFCell successfulTranscationRateTH = field
//				.createCell((short) (9 + conditionTotal));
//		successfulTranscationRateTH
//				.setCellValue(new HSSFRichTextString("交易成功率"));
//		successfulTranscationRateTH.setCellStyle(style);
//		HSSFCell orderConversionRateTH = field
//				.createCell((short) (10 + conditionTotal));
//		orderConversionRateTH.setCellValue(new HSSFRichTextString("订单转化"));
//		orderConversionRateTH.setCellStyle(style);
//	}
//
//	/**
//	 * 生成导出数据内容行
//	 * 
//	 * @param gameId
//	 * @param totalDOS
//	 * @param sheet
//	 * @param j
//	 * @param conditionTotal
//	 */
//	public void generateRow(String gameId, String gameArea, String productType,
//			String productCategory, List<DefaultOrderStatistics> totalDOS,
//			List<ProductType> productTypeList, HSSFSheet sheet, int j,
//			int conditionTotal, HSSFWorkbook wb, int showDetail) {
//		HSSFCellStyle style = wb.createCellStyle();
//		if (null == totalDOS.get(j).getOrderDate()) {
//			if (conditionTotal > 1 && (j != totalDOS.size() - 1)) {
//				if (showDetail == 1) {
//					if (null == totalDOS.get(j - 1).getOrderDate()) {
//						return;
//					}
//				}
//			}
//		}
//		SimpleDateFormat orderDateSDF = new SimpleDateFormat("yyyy-MM-dd");
//		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
//		HSSFCell dateDR = dataRow.createCell((short) 0);
//		if (null != totalDOS.get(j).getOrderDate()) {
//			if (j % 2 == 0) {
//				style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//			}
//			dateDR.setCellValue(new HSSFRichTextString(orderDateSDF
//					.format(totalDOS.get(j).getOrderDate())));
//		} else {
//
//			if (j == totalDOS.size() - 1) {
//				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				dateDR.setCellValue(new HSSFRichTextString("合计"));
//			} else {
//				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				dateDR.setCellValue(new HSSFRichTextString("小计"));
//			}
//		}
//
//		int ct = 1;
//		if (!"-1".equals(gameId) && !"".equals(gameId)) {
//			HSSFCell gameNameDR = dataRow.createCell((short) ct);
//			gameNameDR.setCellValue(new HSSFRichTextString((j == totalDOS
//					.size() - 1) ? "" : totalDOS.get(j).getGameName()));
//			gameNameDR.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(gameArea) && !"".equals(gameArea)) {
//			HSSFCell gameAreaDR = dataRow.createCell((short) ct);
//			gameAreaDR.setCellValue(new HSSFRichTextString((j == totalDOS
//					.size() - 1) ? "" : totalDOS.get(j).getGameAreaName()));
//			gameAreaDR.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(productType) && !"".equals(productType)) {
//			String productTypeName = "";
//
//			for (ProductType pt : productTypeList) {
//				if (pt.getId() == totalDOS.get(j).getProductType()) {
//					productTypeName = pt.getName();
//				}
//			}
//			HSSFCell productTypeDR = dataRow.createCell((short) ct);
//			productTypeDR.setCellValue(new HSSFRichTextString((j == totalDOS
//					.size() - 1) ? "" : productTypeName));
//			productTypeDR.setCellStyle(style);
//			ct++;
//		}
//		if (!"-1".equals(productCategory) && !"".equals(productCategory)) {
//			HSSFCell productCategoryDR = dataRow.createCell((short) ct);
//			productCategoryDR.setCellValue(new HSSFRichTextString(
//					(j == totalDOS.size() - 1) ? "" : (totalDOS.get(j)
//							.getProductCategory() == 1) ? "虚拟币" : (totalDOS
//							.get(j).getProductCategory() == 2) ? "非虚拟币" : ""));
//			productCategoryDR.setCellStyle(style);
//			ct++;
//		}
//		HSSFCell orderTotalDR = dataRow
//				.createCell((short) (1 + conditionTotal));
//		orderTotalDR.setCellValue(totalDOS.get(j).getOrderTotal());
//		HSSFCell orderAmountDR = dataRow
//				.createCell((short) (2 + conditionTotal));
//		orderAmountDR
//				.setCellValue(new HSSFRichTextString(NumberFormat
//						.getCurrencyInstance().format(
//								totalDOS.get(j).getOrderAmount())));
//		HSSFCell payOrderCountDR = dataRow
//				.createCell((short) (3 + conditionTotal));
//		payOrderCountDR.setCellValue(totalDOS.get(j).getPayOrderCount());
//		HSSFCell payOrderAmountDR = dataRow
//				.createCell((short) (4 + conditionTotal));
//		payOrderAmountDR.setCellValue(new HSSFRichTextString(NumberFormat
//				.getCurrencyInstance().format(
//						totalDOS.get(j).getPayOrderAmount())));
//		HSSFCell orderPaymentRateDR = dataRow
//				.createCell((short) (5 + conditionTotal));
//		DecimalFormat dfopr = new DecimalFormat("0.##%");
//		orderPaymentRateDR.setCellValue(new HSSFRichTextString(dfopr
//				.format(totalDOS.get(j).getOrderPaymentRate())));
//		HSSFCell failOrderCountDR = dataRow
//				.createCell((short) (6 + conditionTotal));
//		failOrderCountDR.setCellValue(totalDOS.get(j).getFailOrderCount());
//		HSSFCell successfulTransactionOrdersDR = dataRow
//				.createCell((short) (7 + conditionTotal));
//		successfulTransactionOrdersDR.setCellValue(totalDOS.get(j)
//				.getSuccessfulTransactionOrders());
//		HSSFCell successfulTranscationAmountDR = dataRow
//				.createCell((short) (8 + conditionTotal));
//		successfulTranscationAmountDR.setCellValue(new HSSFRichTextString(
//				NumberFormat.getCurrencyInstance().format(
//						totalDOS.get(j).getSuccessfulTranscationAmount())));
//		DecimalFormat dfstr = new DecimalFormat("0.##%");
//		HSSFCell successfulTranscationRateDR = dataRow
//				.createCell((short) (9 + conditionTotal));
//		successfulTranscationRateDR.setCellValue(new HSSFRichTextString(dfstr
//				.format(totalDOS.get(j).getSuccessfulTranscationRate())));
//		DecimalFormat dfocr = new DecimalFormat("0.##%");
//		HSSFCell orderConversionRateDR = dataRow
//				.createCell((short) (10 + conditionTotal));
//		orderConversionRateDR.setCellValue(new HSSFRichTextString(dfocr
//				.format(totalDOS.get(j).getOrderConversionRate())));
//		dateDR.setCellStyle(style);
//		orderTotalDR.setCellStyle(style);
//		orderAmountDR.setCellStyle(style);
//		payOrderCountDR.setCellStyle(style);
//		payOrderAmountDR.setCellStyle(style);
//		orderPaymentRateDR.setCellStyle(style);
//		failOrderCountDR.setCellStyle(style);
//		successfulTransactionOrdersDR.setCellStyle(style);
//		successfulTranscationAmountDR.setCellStyle(style);
//		successfulTranscationRateDR.setCellStyle(style);
//		orderConversionRateDR.setCellStyle(style);
//
//	}
//}
