//package cn.jugame.admin.web.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.util.PageInfo;
//import cn.jugame.web.util.BackUserLogUtil;
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.latestsale.service.LatestSaleService;
//import cn.juhaowan.latestsale.vo.LatestSale;
//import cn.juhaowan.log.service.BackUserLogService;
//
///**
// * 最新出售管理
// * 
// * @author APXer
// * 
// */
//@Controller
//public class NewSalesController {
//	Logger logger = LoggerFactory.getLogger(NewSalesController.class);
//	@Autowired
//	private BackUserLogService backUserLogService;
//	@Autowired
//	private LatestSaleService latestSaleService;
//
//	/**
//	 * 最新出售列表
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 转向地址
//	 */
//	@RequestMapping(value = "/newsales/newsalesList")
//	public String newsalesList(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc"); // asc desc
//		PageInfo<LatestSale> pageInfo = latestSaleService.queryLatestSalePageInfo(pageSize, pageNo, sort, order);
//		model.addAttribute("pageInfo", pageInfo);
//		return "newsales/newsalesList";
//	}
//
//	/**
//	 * 最新出售列表JSON
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 转向地址
//	 */
//	@RequestMapping(value = "/newsales/newsalesList_json")
//	@ResponseBody
//	public JSONObject newsalesListJson(HttpServletRequest request, Model model) {
//		JSONObject data = new JSONObject();
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc"); // asc desc
//		List<LatestSale> lsList = latestSaleService.queryLatestSaleList();
//		int size = lsList.size();
//
//		PageInfo<LatestSale> pageInfo = latestSaleService.queryLatestSalePageInfo(pageSize, pageNo, sort, order);
//		List<LatestSale> latestSaleList = pageInfo.getItems();
//
//		data.put("total", pageInfo.getRecordCount());
//
//		for (LatestSale ls : latestSaleList) {
//			Map<String, Object> latestSaleMap = new HashMap<String, Object>();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			latestSaleMap.put("id", ls.getId());
//			latestSaleMap.put("title", ls.getTitle());
//			String category = "";
//			if (ls.getCategory() == LatestSaleService.LATEST_SALE) {
//				category = "最新出售";
//			}
//			if (ls.getCategory() == LatestSaleService.LATEST_PRODUCT) {
//				category = "最新商品";
//			}
//			if (ls.getCategory() == LatestSaleService.LATEST_TY) {
//				category = "最新退游";
//			}
//			latestSaleMap.put("category",category);
//			latestSaleMap.put("weight", ls.getWeight());
//			latestSaleMap.put("createTime", sdf.format(ls.getCreateTime()));
//			StringBuffer opreationStr = new StringBuffer();
//			if (ls.getId() != lsList.get(0).getId()) {
//				opreationStr.append("<a href='#' class='easyui-linkbutton'  onclick='javascript:moveUp("//
//						+ ls.getId() + ")'>上移</a>");
//			} else {
//				opreationStr.append("　　");
//			}
//			opreationStr.append("　");
//			if (ls.getId() != lsList.get(size - 1).getId()) {
//				opreationStr.append("<a href='#' class='easyui-linkbutton'  onclick='javascript:moveDown(" + ls.getId()
//						+ ")'>下移</a>");
//			} else {
//				opreationStr.append("　　");
//			}
//			latestSaleMap.put("opreation", opreationStr.toString());
//			showList.add(latestSaleMap);
//		}
//
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//
//		return JSONObject.fromObject(data);
//	}
//
//	/**
//	 * 添加最新出售
//	 * 
//	 * @param request
//	 *            请求
//	 * @throws UnsupportedEncodingException
//	 *             不支持字符类型
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/newsales/newsales_add")
//	@ResponseBody
//	public MessageBean newsalesAdd(LatestSale latestSale, HttpServletRequest request)
//			throws UnsupportedEncodingException {
//		String title = request.getParameter("title");
//		if (StringUtils.isBlank(title)) {
//			return new MessageBean(false, "最新出售商品标题不能为空");
//		}
//		int isSuccess = latestSaleService.addLatestSale(latestSale);
//		BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, "", BackUserLogService.NEWSALES_ADD,
//				request);
//		if (isSuccess != 0) {
//			return new MessageBean(false, "最新出售添加失败");
//		}
//		return new MessageBean();
//	}
//
//	/**
//	 * 删除最新出售
//	 * 
//	 * @param request
//	 *            请求
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/newsales/newsales_delete")
//	@ResponseBody
//	public MessageBean newsalesDelete(HttpServletRequest request) {
//		String ids = request.getParameter("ids");
//		logger.info("del =" + ids);
//		if (StringUtils.isBlank(ids)) {
//			return new MessageBean(false, "请选择要删除的内容");
//		}
//		String[] idArr = ids.split(",");
//		if (idArr.length > 5) {
//			return new MessageBean(false, "最多只能同时删除5条记录");
//		}
//		for (int i = 0; i < idArr.length; i++) {
//			try {
//				int effectRow = latestSaleService.deleteLatestSale(Integer.parseInt(idArr[i]));
//				BackUserLogUtil.log(backUserLogService, (effectRow == 0) ? true : false, idArr[i],
//						BackUserLogService.NEWSALES_DELETE, request);
//
//				if (effectRow != 0) {
//					return new MessageBean(false, "ID为" + idArr[i] + "的记录删除失败");
//				}
//			} catch (Exception e) {
//				logger.error("", e);
//			}
//		}
//		return new MessageBean();
//	}
//
//	/**
//	 * 修改最新出售
//	 * 
//	 * @param latestSale
//	 *            最新出售对象
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/newsales/newsales_edit")
//	@ResponseBody
//	public MessageBean newsalesEdit(LatestSale latestSale, HttpServletRequest request) {
//		if (StringUtils.isBlank(latestSale.getTitle())) {
//			return new MessageBean(false, "标题不能为空");
//		}
//		int isSuccess = latestSaleService.updateLatestSale(latestSale);
//		BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, latestSale.getId() + "",
//				BackUserLogService.NEWSALES_UPDATE, request);
//		if (isSuccess != 0) {
//			return new MessageBean(false, "修改最新出售信息失败");
//		}
//		return new MessageBean();
//	}
//	
//	/**
//	 * 获取要编辑的记录
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/newsales/getNewsSale")
//	@ResponseBody
//	public LatestSale getNewsSale( HttpServletRequest request) {
//		String id = request.getParameter("id");
//		int temp = 0;
//		try {
//			temp = Integer.parseInt(id);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//		LatestSale latestSale = latestSaleService.findById(temp);
//		return latestSale;
//	}
//	
//	
//
//	/**
//	 * 上移
//	 * 
//	 * @param request
//	 *            请求
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/newsales/newsales_up")
//	@ResponseBody
//	public MessageBean moveUP(HttpServletRequest request) {
//		String currentId = request.getParameter("id");
//		if (StringUtils.isBlank(currentId)) {
//			return new MessageBean(false, "ID不能为空");
//		}
//		int flag = latestSaleService.moveUp(Integer.parseInt(currentId));
//		if (flag != 0) {
//			return new MessageBean(true, "操作失败");
//		}
//		return new MessageBean();
//	}
//
//	/**
//	 * 下移
//	 * 
//	 * @param request
//	 *            请求
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/newsales/newsales_down")
//	@ResponseBody
//	public MessageBean moveDown(HttpServletRequest request) {
//		String currentId = request.getParameter("id");
//		if (StringUtils.isBlank(currentId)) {
//			return new MessageBean(false, "ID不能为空");
//		}
//		int flag = latestSaleService.moveDown(Integer.parseInt(currentId));
//		if (flag != 0) {
//			return new MessageBean(true, "操作失败");
//		}
//		return new MessageBean();
//	}
//
//}
