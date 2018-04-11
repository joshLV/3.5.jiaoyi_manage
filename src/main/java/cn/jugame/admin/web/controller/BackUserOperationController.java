package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.log.vo.BackUserLog;

/**
 * 后台公告
 * 
 * @author APXer
 * 
 */
@Controller
public class BackUserOperationController {
	Logger logger = LoggerFactory.getLogger(BackUserOperationController.class);
	@Autowired
	@Qualifier("BackUserLogService")
	private BackUserLogService backUserLogService;

	/**
	 * 后台用户操作日志查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */

	@RequestMapping(value = "/backuserlog/backUserLogList")
	public String backUserLogList(HttpServletRequest request, Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		Map<String, Object> condition = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String uid = request.getParameter("uid");
		String logIp = request.getParameter("logIp");
		String logType = request.getParameter("logType");
		if (!StringUtils.isBlank(beginTime) && beginTime != null) {
			condition.put("beginTime", beginTime);
		}
		if (!StringUtils.isBlank(endTime) && endTime != null) {
			condition.put("endTime", endTime);
		}
		if (!StringUtils.isBlank(uid) && uid != null) {
			condition.put("uid", uid);
		}
		if (!StringUtils.isBlank(logIp) && logIp != null) {
			condition.put("log_ip", logIp);
		}
		if (!StringUtils.isBlank(logType) && logType != null) {
			condition.put("log_type", logType);
		}

		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		PageInfo<BackUserLog> pageInfo = backUserLogService.queryLogList(condition, pageSize, pageNo, sort, order);
		List<BackUserLog> backlogList = pageInfo.getItems();
		data.put("total", pageInfo.getRecordCount());
		Map<String, String> backlogTypeMap = BackUserLogUtil.BACKLOG_TYPE_MAP;
		for (BackUserLog backUserLog : backlogList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> backUserLogMap = new HashMap<String, Object>();
			backUserLogMap.put("id", backUserLog.getId());
			backUserLogMap.put("uid", backUserLog.getUid());
			backUserLogMap.put("logIp", backUserLog.getLogIp());
			backUserLogMap.put("logType", backUserLog.getLogType());
			backUserLogMap.put("logRemark", backUserLog.getLogRemark());
			String logTypeName = "";
			Set<Map.Entry<String, String>> set = backlogTypeMap.entrySet();
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				if ((backUserLog.getLogType() + "").equals(entry.getKey())) {
					logTypeName = entry.getValue();
				}
			}
			backUserLogMap.put("logTypeName", logTypeName);
			backUserLogMap.put("createTime", sdf.format(backUserLog.getCreateTime()));
			showList.add(backUserLogMap);
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		model.addAttribute(data);
		return "backuserlog/backUserLogList";
	}

	/**
	 * 用户日志列表JSON
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/backuserlog/backUserLogList_json")
	@ResponseBody
	public JSONObject backUserLogListJson(HttpServletRequest request, Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		// desc
		Map<String, Object> condition = new HashMap<String, Object>();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String uid = request.getParameter("uid");
		String logIp = request.getParameter("logIp");
		String logType = request.getParameter("logType");
		if (!StringUtils.isBlank(beginTime) && beginTime != null) {
			condition.put("beginTime", beginTime);
		}
		if (!StringUtils.isBlank(endTime) && endTime != null) {
			condition.put("endTime", endTime);
		}
		if (!StringUtils.isBlank(uid) && uid != null) {
			condition.put("uid", uid);
		}
		if (!StringUtils.isBlank(logIp) && logIp != null) {
			condition.put("log_ip", logIp);
		}
		if (!StringUtils.isBlank(logType) && logType != null) {
			condition.put("log_type", logType);
		}
		PageInfo<BackUserLog> pageInfo = backUserLogService.queryLogList(condition, pageSize, pageNo, sort, order);
		List<BackUserLog> backlogList = pageInfo.getItems();
		if (backlogList != null) {
			data.put("total", pageInfo.getRecordCount());
			Map<String, String> backlogTypeMap = BackUserLogUtil.BACKLOG_TYPE_MAP;
			for (BackUserLog backUserLog : backlogList) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map<String, Object> backUserLogMap = new HashMap<String, Object>();
				backUserLogMap.put("id", backUserLog.getId());
				backUserLogMap.put("uid", backUserLog.getUid());
				backUserLogMap.put("logIp", backUserLog.getLogIp());
				backUserLogMap.put("logType", backUserLog.getLogType());
				backUserLogMap.put("logRemark", backUserLog.getLogRemark());
				String logTypeName = "";
				Set<Map.Entry<String, String>> set = backlogTypeMap.entrySet();
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
					if ((backUserLog.getLogType() + "").equals(entry.getKey())) {
						logTypeName = entry.getValue();
					}
				}
				backUserLogMap.put("logTypeName", logTypeName);
				backUserLogMap.put("createTime", sdf.format(backUserLog.getCreateTime()));
				showList.add(backUserLogMap);
			}
		}else{
			data.put("total", 0);
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		return JSONObject.fromObject(data);
	}

	/**
	 * @return 所有日志类型
	 */
	@RequestMapping(value = "/backuserlog/allBacklogType_json")
	@ResponseBody
	public JSONObject allBacklogTypeJson() {
		return BackUserLogUtil.allLogTypeMapJson();
	}
}
