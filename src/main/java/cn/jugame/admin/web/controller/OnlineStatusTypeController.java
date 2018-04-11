package cn.jugame.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.PageInfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;
import cn.juhaowan.customer.vo.WorkStatus;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 在线状态类型管理
 * 
 * @author apxer
 * 
 */
@Controller
@RequestMapping(value = "/personnelManagement")
public class OnlineStatusTypeController {
	Logger logger = LoggerFactory.getLogger(OnlineStatusTypeController.class);

	@Autowired
	private OnlineCustomerMonitorService onlineCustomerMonitorService;
	@Autowired
	private BackUserLogService backUserLogService;
	@Autowired
	private ISysUserinfoService iSysUserinfoService;

	/**
	 * 在线状态类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/workStatusList")
	public String workStatusList(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10);
		String sort = RequestUtils.getParameter(request, "sort", "weight");
		String order = RequestUtils.getParameter(request, "order", "desc");
		if (!"weight".equals(sort)) {
			return "";
		}
		PageInfo<WorkStatus> workStatusList = onlineCustomerMonitorService.queryCategoryList(pageSize, pageNo, sort,
				order);
		model.addAttribute("categorySize", workStatusList.getRecordCount());
		model.addAttribute("workStatusList", workStatusList.getItems());
		return "personnelManagement/workStatusList";
	}

	/**
	 * 在线状态类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助分类列表JSON
	 */
	@RequestMapping(value = "/workStatusList_json")
	@ResponseBody
	public JSONObject workStatusListJson(HttpServletRequest request, Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		if (!"weight".equals(sort)) {
			return null;
		}
		PageInfo<WorkStatus> workStatusList = onlineCustomerMonitorService.queryCategoryList(pageSize, pageNo, sort,order);
		data.put("total", workStatusList.getRecordCount());

		for (WorkStatus ws : workStatusList.getItems()) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("id", ws.getId());
			categoryMap.put("name", ws.getName());
			categoryMap.put("weight", ws.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 获得所有在线状态类型
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 在线状态类型列表JSON
	 */
	@RequestMapping(value = "/allCategoryList_json")
	@ResponseBody
	public JSONObject allCategoryListJson(HttpServletRequest request, Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		if (!"weight".equals(sort)) {
			return null;
		}
		List<WorkStatus> workStatusList = onlineCustomerMonitorService.queryCategoryList(sort, order);
		data.put("total", workStatusList.size());
		
		cn.jugame.vo.SysUserinfo userinfo = (cn.jugame.vo.SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		cn.jugame.vo.SysUserinfo newu = iSysUserinfoService.findById(userinfo.getUserId());

		for (WorkStatus ws : workStatusList) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("id", ws.getId());
			categoryMap.put("name", ws.getName());
			categoryMap.put("weight", ws.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		data.put("u_info", newu);

		return JSONObject.fromObject(data);
	}

	/**
	 * 添加在线状态类型
	 * 
	 * @param ws
	 *            在线状态类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/workStatus_add")
	@ResponseBody
	public MessageBean workStatusAdd(WorkStatus ws, HttpServletRequest request) {
		if (StringUtils.isBlank(ws.getName())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(ws.getWeight() + "")) {
			return new MessageBean(false, "权重不能为空");
		}
		String categoryName = ws.getName();
		int weight = ws.getWeight();
		if (onlineCustomerMonitorService.queryCategoryNameIsExist(categoryName) == 0) {
			int isSuccess = onlineCustomerMonitorService.addWorkStatusCategory(categoryName, weight);
			BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, "",
					BackUserLogService.WORK_STATUS_ADD, request);
			// 操作成功
			return new MessageBean();
		} else {
			return new MessageBean(false, "您输入的在线状态类型名称已经存在请重新输入");
		}

	}

	/**
	 * 在线状态类型名称是否存在
	 * 
	 * @param ws
	 *            在线状态类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/workStatusIsExist")
	@ResponseBody
	public MessageBean workStatusIsExist(WorkStatus ws) {
		String categoryName = ws.getName();
		if (StringUtils.isBlank(categoryName)) {
			return new MessageBean(false, "请输入在线状态类型");
		}
		int isExist = onlineCustomerMonitorService.queryCategoryNameIsExist(categoryName);
		if (isExist == 0) {
			return new MessageBean(true, "该类型名称可以使用");
		} else {
			return new MessageBean(false, "您输入的类型名称已经存在,请重新输入");
		}
	}

	/**
	 * 删除在线状态类型
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/workStatus_delete")
	@ResponseBody
	public MessageBean workStatusDelete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		logger.info("del =" + ids);
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}

		String[] idArr = ids.split(",");
		if (idArr.length > 5) {
			return new MessageBean(false, "最多只能同时删除5条记录");
		}

		for (int i = 0; i < idArr.length; i++) {
			try {
				int effectRow = onlineCustomerMonitorService
						.deleteWorkStatusCategory(Integer.parseInt(idArr[i]), false);
				BackUserLogUtil.log(backUserLogService, (effectRow == 0) ? true : false, idArr[i],
						BackUserLogService.WORK_STATUS_DELETE, request);

				if (effectRow == 0) {
					return new MessageBean(false, "删除该在线状态类型成功");
				} else {
					return new MessageBean(true, "删除该在线状态类型失败，可能该在线状态类型下有在线状态内容");
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}

	/**
	 * 修改在线状态类型
	 * 
	 * @param ws
	 *            在线状态类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/workStatus_edit")
	@ResponseBody
	public MessageBean workStatusEdit(WorkStatus ws, HttpServletRequest request) {
		if (StringUtils.isBlank(ws.getName())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(ws.getWeight() + "")) {
			return new MessageBean(false, "内容不能为空");
		}
		int categoryID = ws.getId();
		String categoryName = ws.getName();
		int weight = ws.getWeight();
		int isSuccess = onlineCustomerMonitorService.modifyWorkStatusCategory(categoryID, categoryName, weight);
		BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, categoryID + "",
				BackUserLogService.WORK_STATUS_EDIT, request);
		return new MessageBean();
	}
}
