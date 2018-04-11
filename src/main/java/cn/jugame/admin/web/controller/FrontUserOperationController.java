package cn.jugame.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.FrontUserLogService;
import cn.juhaowan.log.vo.FrontUserLog;

/**
 * 前台日志管理
 * 
 * @author APXer
 * 
 */
@Controller
public class FrontUserOperationController {
	Logger logger = LoggerFactory.getLogger(FrontUserOperationController.class);
	@Autowired
	@Qualifier("FrontUserLogService")
	private FrontUserLogService frontUserLogService;

	/**
	 * 前端用户操作日志查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/frontuserlog/frontUserLogList")
	public String frontUserLogList(HttpServletRequest request, Model model) {
		int uid = RequestUtils.getParameterInt(request, "uid", 0);
		if (uid != 0) {
			int pageNo = RequestUtils.getParameterInt(request, "page", 1);
			int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
			String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
			String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																				// desc
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("uid", uid);
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			logger.info("搜索时间List:" + beginTime + "=====" + endTime);
			if (!StringUtils.isBlank(beginTime)) {
				condition.put("beginTime", beginTime);
			}
			if (!StringUtils.isBlank(endTime)) {
				condition.put("endTime", endTime);
			}

			PageInfo<FrontUserLog> pageInfo = frontUserLogService.queryLogList(//
					condition, pageSize, pageNo, sort, order);
			model.addAttribute("pageInfo", pageInfo);
		} else {
			model.addAttribute("pageInfo", new PageInfo<FrontUserLog>());
		}
		return "frontuserlog/frontUserLogList";
	}

	/**
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/frontuserlog/frontUserLogList_json")
	public String frontUserLogListJson(HttpServletRequest request, Model model) {
		int uid = RequestUtils.getParameterInt(request, "uid", 0);
		if (uid != 0) {
			int pageNo = RequestUtils.getParameterInt(request, "page", 1);
			int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
			String sort = RequestUtils.getParameter(request, "sort", "create_time"); // 排序字段
			String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																				// desc

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("uid", uid);
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			logger.info("搜索时间json:" + beginTime + "=====" + endTime);
			if (!StringUtils.isBlank(beginTime)) {
				condition.put("beginTime", beginTime);
			}
			if (!StringUtils.isBlank(endTime)) {
				condition.put("endTime", endTime);
			}

			PageInfo<FrontUserLog> pageInfo = frontUserLogService.queryLogList(//
					condition, pageSize, pageNo, sort, order);

			model.addAttribute("pageInfo", pageInfo);
		} else {
			model.addAttribute("pageInfo", new PageInfo<FrontUserLog>());
		}
		return "frontuserlog/frontUserLogList_json";
	}

}
