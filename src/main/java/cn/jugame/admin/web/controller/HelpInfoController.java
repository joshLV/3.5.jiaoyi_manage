package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.helpinfo.service.HelpInfoService;
import cn.juhaowan.helpinfo.vo.HelpCategory;
import cn.juhaowan.helpinfo.vo.HelpInfo;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 帮助管理
 * 
 * @author APXer
 * 
 */
@Controller
public class HelpInfoController {
	Logger logger = LoggerFactory.getLogger(HelpInfoController.class);
	@Autowired
	@Qualifier("HelpInfoService")
	private HelpInfoService helpInfoService;
	@Autowired
	private BackUserLogService backUserLogService;

	/**
	 * FAQ信息查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/helpinfo/helpInfoList")
	public String helpInfoList(HttpServletRequest request, Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		// desc
		PageInfo<HelpInfo> pageInfo = helpInfoService.queryHelpList(pageSize, pageNo, sort, order);

		model.addAttribute("pageInfo", pageInfo);
		return "helpinfo/helpInfoList";
	}

	/**
	 * 帮助列表
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助信息JSON
	 */
	@RequestMapping(value = "/helpinfo/helpInfoList_json")
	@ResponseBody
	public JSONObject helpInfoListJson(HttpServletRequest request, Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		// desc

		List<HelpCategory> helpCategoryList = helpInfoService.queryCategoryList("weight", "desc");
		model.addAttribute("helpCategoryList", helpCategoryList);

		PageInfo<HelpInfo> pageInfo = helpInfoService.queryHelpList(pageSize, pageNo, sort, order);
		List<HelpInfo> helpInfoList = pageInfo.getItems();
		data.put("total", pageInfo.getRecordCount());

		for (HelpInfo helpInfo : helpInfoList) {
			Map<String, Object> helpInfoMap = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			helpInfoMap.put("helpId", helpInfo.getHelpId());
			helpInfoMap.put("helpTitle", helpInfo.getHelpTitle() == null ? "" : helpInfo.getHelpTitle());
			helpInfoMap.put("categoryId", helpInfo.getCategoryId());
			String categoryName = "";
			for (HelpCategory helpCategory : helpCategoryList) {
				if (helpCategory.getHelpCategoryId() == helpInfo.getCategoryId()) {
					categoryName = helpCategory.getCategoryName();
				}
			}
			helpInfoMap.put("categoryName", categoryName == null ? "" : categoryName);
			helpInfoMap.put("createtime", sdf.format(helpInfo.getCreatetime()));
			helpInfoMap.put("helpContent", helpInfo.getHelpContent() == null ? "" : helpInfo.getHelpContent());
			helpInfoMap.put("weight", helpInfo.getWeight());
			helpInfoMap.put("isShow", helpInfo.getIsShow());
			helpInfoMap.put("tag", helpInfo.getTag().trim().length() == 0 ? "无标签" : helpInfo.getTag());
			helpInfoMap.put("opreation", "<a href='#' class='easyui-linkbutton'  onclick='javascript:showDetail("
					+ helpInfo.getHelpId() + ")'>详细信息</a>");
			showList.add(helpInfoMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 根据categoryId查询FAQ信息
	 * 
	 * @param categoryId
	 *            帮助分类ID
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/helpinfo/helpInfoListById")
	public String helpInfoListByCategoryIdson(//
			@RequestParam int categoryId, HttpServletRequest request, Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		if (!"createtime".equals(sort)) {
			return "";
		}
		PageInfo<HelpInfo> pageInfo = helpInfoService.queryHelpListByCategoryID(categoryId, pageSize, pageNo, sort,
				order);
		model.addAttribute("pageInfo", pageInfo);
		return "helpinfo/helpInfoList_json";
	}

	/**
	 * FAQ信息类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/helpinfo/helpCategoryList")
	public String helpCategoryList(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10);
		String sort = RequestUtils.getParameter(request, "sort", "weight");
		String order = RequestUtils.getParameter(request, "order", "desc");
		if (!"weight".equals(sort)) {
			return "";
		}
		PageInfo<HelpCategory> helpCategoryList = helpInfoService.queryCategoryList(pageSize, pageNo, sort, order);
		model.addAttribute("categorySize", helpCategoryList.getRecordCount());
		model.addAttribute("helpCategoryList", helpCategoryList.getItems());
		return "helpinfo/helpCategoryList";
	}

	/**
	 * FAQ信息类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助分类列表JSON
	 */
	@RequestMapping(value = "/helpinfo/helpCategoryList_json")
	@ResponseBody
	public JSONObject helpCategoryListJson(HttpServletRequest request, Model model) {
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
		PageInfo<HelpCategory> helpCategoryList = helpInfoService.queryCategoryList(pageSize, pageNo, sort, order);
		data.put("total", helpCategoryList.getRecordCount());

		for (HelpCategory helpCategory : helpCategoryList.getItems()) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("helpCategoryId", helpCategory.getHelpCategoryId());
			categoryMap.put("categoryName", helpCategory.getCategoryName());
			categoryMap.put("weight", helpCategory.getWeight());
			categoryMap.put("isShow", helpCategory.getIsShow());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 获得所有帮助分类
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助分类列表JSON
	 */
	@RequestMapping(value = "/helpinfo/allCategoryList_json")
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
		List<HelpCategory> helpCategoryList = helpInfoService.queryCategoryList(sort, order);
		data.put("total", helpCategoryList.size());

		for (HelpCategory helpCategory : helpCategoryList) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("helpCategoryId", helpCategory.getHelpCategoryId());
			categoryMap.put("categoryName", helpCategory.getCategoryName());
			categoryMap.put("weight", helpCategory.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 添加FAQ信息类型
	 * 
	 * @param helpcategory
	 *            帮助分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpCategory_add")
	@ResponseBody
	public MessageBean helpcategoryAdd(HelpCategory helpcategory, HttpServletRequest request) {
		if (StringUtils.isBlank(helpcategory.getCategoryName())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(helpcategory.getWeight() + "")) {
			return new MessageBean(false, "权重不能为空");
		}
		String categoryName = helpcategory.getCategoryName();
		int weight = helpcategory.getWeight();
		int isShow = helpcategory.getIsShow();
		if (helpInfoService.queryCategoryNameIsExist(categoryName) == 0) {
			int isSuccess = helpInfoService.addHelpCategory(categoryName, weight,isShow);
			BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "",
					BackUserLogService.HELP_CATEGORY_ADD, request);
			// 操作成功
			return new MessageBean();
		} else {
			return new MessageBean(false, "您输入的分类名称已经存在请重新输入");
		}

	}

	/**
	 * 帮助分类名称是否存在
	 * 
	 * @param category
	 *            帮助分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpCategoryIsExist")
	@ResponseBody
	public MessageBean helpCategoryIsExist(HelpCategory category) {
		String categoryName = category.getCategoryName();
		if (StringUtils.isBlank(categoryName)) {
			return new MessageBean(false, "请输入帮助名称");
		}
		int isExist = helpInfoService.queryCategoryNameIsExist(categoryName);
		if (isExist != 0) {
			return new MessageBean(false, "您输入的分类名称已经存在,请重新输入");
		} else {
			return new MessageBean(true, "该分类名称可以使用");
		}
	}

	/**
	 * 帮助标签是否存在
	 * 
	 * @param helpInfo
	 *            帮助分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpinfoTagIsExist")
	@ResponseBody
	public MessageBean helpinfoTagIsExist(HelpInfo helpInfo) {
		String tag = helpInfo.getTag();
		if (StringUtils.isBlank(tag)) {
			return new MessageBean(false, "请输入标签");
		}
		int isExist = helpInfoService.queryTagIsExist(0,tag);
		if (isExist != 0) {
			return new MessageBean(false, "您输入的标签已经存在,请重新输入");
		} else {
			return new MessageBean(true, "该标签名称可以使用");
		}
	}

	/**
	 * 添加FAQ信息
	 * 
	 * @param helpinfo
	 *            帮助信息
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpInfo_add")
	@ResponseBody
	public MessageBean helpinfoAdd(HelpInfo helpinfo, HttpServletRequest request) {
		if (StringUtils.isBlank(helpinfo.getHelpTitle())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(helpinfo.getHelpContent())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(helpinfo.getWeight() + "")) {
			return new MessageBean(false, "常见度不能为空");
		}

		String title = helpinfo.getHelpTitle();
		String content = helpinfo.getHelpContent();
		String tag = helpinfo.getTag();
		int categoryID = helpinfo.getCategoryId();
		int weight = helpinfo.getWeight();
		int isShow = helpinfo.getIsShow();
		if (StringUtils.isBlank(helpinfo.getTag())) {
			return new MessageBean(false, "标签不能为空");
		}
		if (!StringUtils.isBlank(tag)) {
			if (helpInfoService.queryTagIsExist(0,tag) == 0) {
				int isSuccess = helpInfoService.addHelpContent(categoryID, title, content, weight, tag,isShow);
				BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "",
						BackUserLogService.HELPINFO_ADD, request);
			} else {
				return new MessageBean(false, "您输入的标签名称已经存在,请重新输入");
			}
		} else {
			int isSuccess = helpInfoService.addHelpContent(categoryID, title, content, weight);
			BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "",
					BackUserLogService.HELPINFO_ADD, request);
		}
		return new MessageBean();
	}

	/**
	 * 删除FAQ信息类型
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpCategory_delete")
	@ResponseBody
	public MessageBean helpcategoryDelete(HttpServletRequest request) {
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
				int effectRow = helpInfoService.deleteHelpCategory(Integer.parseInt(idArr[i]), false);
				BackUserLogUtil.log(backUserLogService, (effectRow == 0) ? true : false, idArr[i],
						BackUserLogService.HELP_CATEGORY_DELETE, request);

				if (effectRow != 0) {
					return new MessageBean(false, "删除帮助分类,先强制级联删除该分类下帮助信息");
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}

	/**
	 * 删除FAQ信息
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpInfo_delete")
	@ResponseBody
	public MessageBean helpinfoDelete(HttpServletRequest request) {
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
				int isSuccess = helpInfoService.deleteHelpContent(Integer.parseInt(idArr[i]));
				BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, idArr[i],
						BackUserLogService.HELPINFO_DELETE, request);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}

	/**
	 * 修改帮助信息
	 * 
	 * @param helpInfo
	 *            帮助信息
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpinfo_edit")
	@ResponseBody
	public MessageBean helpinfoEdit(HelpInfo helpInfo, HttpServletRequest request) {
		Integer helpId = helpInfo.getHelpId();
		String helpTitle = helpInfo.getHelpTitle();
		Integer categoryId = helpInfo.getCategoryId();
		String helpContent = helpInfo.getHelpContent();
		int helpWeight = helpInfo.getWeight();
		String tag = helpInfo.getTag();
		int isShow = helpInfo.getIsShow();
		if (StringUtils.isBlank(helpId + "")) {
			return new MessageBean(false, "帮助信息ID不能为空");
		}
		if (StringUtils.isBlank(helpTitle)) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(helpContent + "")) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(categoryId + "")) {
			return new MessageBean(false, "帮助分类不能为空");
		}
		if (StringUtils.isBlank(helpWeight + "")) {
			return new MessageBean(false, "常见度不能为空");
		}
		HelpInfo hi = helpInfoService.getHelpContentById(helpId);
		if (hi != null) {
			if (!StringUtils.isBlank(tag)) {
				if (helpInfoService.queryTagIsExist(helpId,tag) == 0) {
					int isSuccess = helpInfoService.modifyHelpInfo(helpId, helpTitle, helpContent, categoryId,
							helpWeight, tag, isShow);
					BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "",
							BackUserLogService.HELPINFO_UPDATE, request);
				} else {
					return new MessageBean(false, "帮助标签已存在");
				}
			} else {
				int isSuccess = helpInfoService.modifyHelpInfo(helpId, helpTitle, helpContent, categoryId, helpWeight,isShow);
				BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "",
						BackUserLogService.HELPINFO_UPDATE, request);
			}
		} else {
			return new MessageBean(false, "该[" + helpId + "]帮助信息不存在");
		}
		return new MessageBean();
	}

	/**
	 * 修改帮助类型
	 * 
	 * @param helpcategory
	 *            帮助分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/helpinfo/helpCategory_edit")
	@ResponseBody
	public MessageBean helpCategoryEdit(HelpCategory helpcategory, HttpServletRequest request) {
		if (StringUtils.isBlank(helpcategory.getCategoryName())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(helpcategory.getWeight() + "")) {
			return new MessageBean(false, "内容不能为空");
		}
		int helpCategoryId = helpcategory.getHelpCategoryId();
		String categoryName = helpcategory.getCategoryName();
		int weight = helpcategory.getWeight();
		int isShow = helpcategory.getIsShow();
		int isSuccess = helpInfoService.modifyHelpCategory(helpCategoryId, categoryName, weight, isShow);
		BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, helpCategoryId + "",
				BackUserLogService.HELP_CATEGORY_UPDATE, request);
		return new MessageBean();
	}

	/**
	 * 根据ID查询帮助详细信息
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助信息JSON
	 */
	@RequestMapping(value = "/helpinfo/helpInfo_json")
	@ResponseBody
	public JSONObject helpinfoJson(HttpServletRequest request, Model model) {
		JSONObject hi = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int helpId = RequestUtils.getParameterInt(request, "helpId", 0);
		HelpInfo helpInfo = helpInfoService.getHelpContentById(helpId);
		if (helpInfo == null) {
			return null;
		} else {
			List<HelpCategory> helpCategoryList = helpInfoService.queryCategoryList("weight", "desc");
			hi.put("helpId", helpInfo.getHelpId());
			String categoryName = "";
			for (HelpCategory helpCategory : helpCategoryList) {
				if (helpCategory.getHelpCategoryId() == helpInfo.getCategoryId()) {
					categoryName = helpCategory.getCategoryName();
				}
			}
			hi.put("categoryName", categoryName);
			hi.put("helpTitle", helpInfo.getHelpTitle());
			hi.put("helpContent", helpInfo.getHelpContent());
			hi.put("categoryId", helpInfo.getCategoryId());
			hi.put("createtime", sdf.format(helpInfo.getCreatetime()));
			hi.put("weight", helpInfo.getWeight());
			hi.put("tag", helpInfo.getTag().trim().length() == 0 ? "无标签" : helpInfo.getTag());
			hi.put("isShow", helpInfo.getIsShow() == 1 ? "显示" : "不显示");
		}
		return hi;
	}
}
