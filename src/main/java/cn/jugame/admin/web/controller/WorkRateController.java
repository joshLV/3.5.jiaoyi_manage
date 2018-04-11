package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.util.RequestUtils;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;
import cn.juhaowan.customer.vo.WorkRateForm;

/**
 * 工时率统计
 * 
 * @author APXer
 * 
 */
@Controller
@RequestMapping(value = "/statistics")
public class WorkRateController {
	@Autowired
	private OnlineCustomerMonitorService onlineCustomerMonitorService;

	/**
	 * 查询工时率统计List
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/workRateList")
	public String workRateList(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 25); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "on_duty"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc

		int customerType = RequestUtils.getParameterInt(request,
				"customerType", -1);
		String postNo = request.getParameter("postNo");
		String fullname = request.getParameter("fullname");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fiveDaysAgo = new Date().getTime() - 253800000;
		model.addAttribute("fromDays", sdf.format(new Date(fiveDaysAgo)));
		String fromDays = RequestUtils.getParameter(request, "fromDays",
				sdf.format(new Date(fiveDaysAgo)));
		String toDays = request.getParameter("toDays");

		Map<String, Object> conditions = new HashMap<String, Object>();
		if (-1 != customerType) {
			conditions.put("customerType", customerType);
		}
		if (!StringUtils.isBlank(postNo)) {
			conditions.put("postNo", postNo);
		}
		if (!StringUtils.isBlank(fullname)) {
			conditions.put("fullname", fullname);
		}
		if (!StringUtils.isBlank(fromDays)) {
			conditions.put("fromDays", fromDays);
		}
		if (!StringUtils.isBlank(toDays)) {
			conditions.put("toDays", toDays);
		}
		PageInfo<WorkRateForm> pageInfo = //
		onlineCustomerMonitorService.queryWorkRatePageInfo(conditions,pageSize, pageNo, sort, order);
		model.addAttribute("pageInfo", pageInfo);
		return "statistics/workRateList";
	}

	/**
	 * 查询工时率统计列表
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 查询工时率统计列表JSON
	 */
	@RequestMapping(value = "/workRateList_json")
	@ResponseBody
	public JSONObject workRateListJson(HttpServletRequest request, Model model) {

		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 25); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "on_duty"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc

		int customerType = RequestUtils.getParameterInt(request,"customerType", -1);
		String postNo = request.getParameter("postNo");
		String fullname = request.getParameter("fullname");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fiveDaysAgo = new Date().getTime() - 253800000;
		String fromDays = RequestUtils.getParameter(request, "fromDays",
				sdf.format(new Date(fiveDaysAgo)));
		String toDays = request.getParameter("toDays");

		Map<String, Object> conditions = new HashMap<String, Object>();
		if (-1 != customerType) {
			conditions.put("customerType", customerType);
		}
		if (!StringUtils.isBlank(postNo)) {
			conditions.put("postNo", postNo);
		}
		if (!StringUtils.isBlank(fullname)) {
			if (fullname.trim().length() > 0) {
				conditions.put("fullname", fullname);
			}
		}
		if (!StringUtils.isBlank(fromDays)) {
			conditions.put("fromDays", fromDays);
		}
		if (!StringUtils.isBlank(toDays)) {
			conditions.put("toDays", toDays);
		}
		PageInfo<WorkRateForm> pageInfo = //
		onlineCustomerMonitorService.queryWorkRatePageInfo(conditions,pageSize, pageNo, sort, order);
		data.put("total", pageInfo.getRecordCount());
		for (WorkRateForm wrf : pageInfo.getItems()) {
			Map<String, Object> wrMap = new HashMap<String, Object>();
			SimpleDateFormat daysSDF = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat onDutySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat offDutySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			wrMap.put("id", wrf.getId());
			wrMap.put("postNo", wrf.getPostNo()==null?"":wrf.getPostNo());
			wrMap.put("loginName", wrf.getLoginName()==null?"":wrf.getLoginName());
			wrMap.put("fullname", wrf.getFullname());
			wrMap.put("days", daysSDF.format(wrf.getDays()));
			int csType = wrf.getCsType();
			wrMap.put("csType",csType == OnlineCustomerMonitorService.IS_CUSTOMER ? "寄售客服": //
							csType == OnlineCustomerMonitorService.IS_OBJECT_CUSTOMER ? "寄售物服": "审核员");
			wrMap.put("onDuty", onDutySDF.format(wrf.getOnDuty()));
			wrMap.put("offDuty", offDutySDF.format(wrf.getOffDuty()));
//			wrMap.put(
//					"realWorkTime",
//					DateUtil.timeDifferenceSec(wrf.getOffDuty(),
//							wrf.getOnDuty())
//							/ 60000 + "分钟");
			wrMap.put("realWorkTime", 540 + "分钟");
			wrMap.put("effectiveTime", wrf.getEffectiveTime() / 60);
			wrMap.put("workRate", wrf.getWorkRate()==null?"":wrf.getWorkRate());
			showList.add(wrMap);
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		return JSONObject.fromObject(data);
	}

}
