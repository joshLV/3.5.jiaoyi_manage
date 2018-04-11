package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.count.service.CountProgramService;
import cn.juhaowan.count.service.CountService;
import cn.juhaowan.count.vo.CountDayAccount;
import cn.juhaowan.count.vo.CountDayActive;
import cn.juhaowan.count.vo.CountDayMoneyIo;
import cn.juhaowan.count.vo.CountDayOrder;
import cn.juhaowan.count.vo.CountDayProduct;
import cn.juhaowan.count.vo.CountDayUser;
import cn.juhaowan.count.vo.CountProgram;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class DataCountController {
	Logger logger = LoggerFactory.getLogger(DataCountController.class);

	@Autowired
	private CountService countService;
	@Autowired
	private CountProgramService countProgramService;
	/**
	 * 1.用户列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayUserList")
	public String countDayUserList(HttpServletRequest request, Model model) {
		return "count/countDayUserList";
	}
    /**
     * 1.用户列表数据
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "/count/countDayUserList_json")
	public String countDayUserListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
	
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayUser> pageInfo = null;
		pageInfo = countService.queryDayUserForPage(m, pageNo, pageSize, sort,
				order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayUserList_json";
		}

		model.addAttribute("pageInfo", pageInfo);
		return "count/countDayUserList_json";

	}

	/**
	 *  2.日活统计列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayActiveList")
	public String countDayActiveList(HttpServletRequest request, Model model) {
		return "count/countDayActiveList";
	}
    /**
     * 2.日活统计列表数据
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "/count/countDayActiveList_json")
	public String countDayActiveListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
	
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayActive> pageInfo = null;
		pageInfo = countService.queryDayActiveForPage(m, pageNo, pageSize,
				sort, order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayActiveList_json";
		}

		model.addAttribute("pageInfo", pageInfo);
		return "count/countDayActiveList_json";

	}

	/**
	 *  3.账户统计列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayAccountList")
	public String countDayAccountList(HttpServletRequest request, Model model) {
		return "count/countDayAccountList";
	}
	/**
	 *  3.账户统计列表数据
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayAccountList_json")
	public String countDayAccountListJson(HttpServletRequest request,
			Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
	
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayAccount> pageInfo = null;
		pageInfo = countService.queryDayAccountForPage(m, pageNo, pageSize,sort, order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayAccountList_json";
		}

		model.addAttribute("pageInfo", pageInfo);
		return "count/countDayAccountList_json";

	}

	/**
	 *  4 日金额收支统计
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayMoneyIOList")
	public String countDayMoneyIOList(Model model) {

		return "count/countDayMoneyIOList";
	}
	/**
	 *  4 日金额收支统计数据
	 * @param model 
	 * @param request 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayMoneyIOList_json")
	public String countDayMoneyIOListJson(HttpServletRequest request,Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
	
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayMoneyIo> pageInfo = null;
		pageInfo = countService.queryDayMoneyForPage(m, pageNo, pageSize, sort,order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayMoneyIOList_json";
		}

		model.addAttribute("pageInfo", pageInfo);


		return "count/countDayMoneyIOList_json";
	}

	/**
	 *  5 商品统计
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayProductList")
	public String countDayProductList(Model model) {

		return "count/countDayProductList";
	}
	/**
	 *  5 商品统计数据
	 *  @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayProductList_json")
	public String countDayProductListJson(HttpServletRequest request,Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayProduct> pageInfo = null;
		pageInfo = countService.queryDayProductForPage(m, pageNo, pageSize,sort, order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayProductList_json";
		}

		model.addAttribute("pageInfo", pageInfo);

		return "count/countDayProductList_json";
	}

	/**
	 *  6 交易统计
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayOrderList")
	public String countDayOrderList(Model model) {

		return "count/countDayOrderList";
	}
	/**
	 *  6 交易统计数据
	 * @param model 
	 * @param request 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countDayOrderList_json")
	public String countDayOrderListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
	
		String etime = request.getParameter("etime");
		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
		}
		PageInfo<CountDayOrder> pageInfo = null;
		pageInfo = countService.queryDayOrderForPage(m, pageNo, pageSize, sort,order);
		if (null == pageInfo) {
			model.addAttribute("pageInfo", null);
			return "count/countDayOrderList_json";
		}

		model.addAttribute("pageInfo", pageInfo);

		return "count/countDayOrderList_json";
	}
	/**
	 * 用户URL访问统计
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/countProgramByurlList")
	public String countProgramByurlList(HttpServletRequest request,Model model){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String viewUrl = RequestUtils.getParameter(request, "viewUrl", "");
		model.addAttribute("viewUrl", viewUrl);
		List<CountProgram> parentProgram = this.countProgramService.getParentProgram();
		model.addAttribute("parentProgram", parentProgram);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -3);
		Date newdate = calendar.getTime();
		model.addAttribute("beginTime", sf.format(newdate));
		model.addAttribute("endTime", sf.format(new Date()));
		return "count/countProgramByurlList";
	}
	/**
	 * AJAX返回结果
	 * @param request 
	 * @param model 
	 * @return json数据
	 */
	@RequestMapping(value = "/count/countProgramByurlList_json")
	@ResponseBody
	public JSONObject countProgramByurlListJson(HttpServletRequest request,Model model){
		JSONObject data = new JSONObject();
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 30); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_time"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map map = new HashMap();
		String beginTime = request.getParameter("beginTime");
		if (beginTime != null && !("").equals(beginTime)) {
			map.put("beginTime", beginTime);
		}
		String endTime = request.getParameter("endTime");
		if (endTime != null && !("").equals(endTime)) {
			map.put("endTime", endTime);
		}
		String viewUrl = request.getParameter("viewUrl");
		if (viewUrl != null && !("").equals(viewUrl)) {
			map.put("viewUrl", "%" + viewUrl.trim() + "%");
		}
		// 进行查询
		data.put("total", 0);
		data.put("rows", "[]");
		PageInfo<Map<String, Object>> pageInfo = this.countProgramService.queryCountProgramByurlList(map,  
				pageNo, pageSize, sort, order);
		System.out.println("pageInfo========"+pageInfo);
		if (pageInfo != null) {
			List<Map<String, Object>> list = pageInfo.getItems();
			System.out.println("items========"+pageInfo.getItems());
			data.put("total", pageInfo.getRecordCount());
			if (list != null && list.size() > 0) {
				JSONArray rows = JSONArray.fromObject(list);
				data.put("rows", rows);
			}
		}
		System.out.println(data);
		return data;
	}
}
