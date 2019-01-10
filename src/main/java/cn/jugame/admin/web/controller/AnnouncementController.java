package cn.jugame.admin.web.controller;

import java.text.DateFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.DateEditor;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.announcement.service.AnnouncementService;
import cn.juhaowan.announcement.vo.Announcement;
import cn.juhaowan.announcement.vo.AnnouncementCategory;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 公告Controller
 * 
 * @author APXer
 * 
 */
@Controller
public class AnnouncementController {
	Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
	@Autowired
	@Qualifier("AnnouncementService")
	private AnnouncementService announcementService;
	@Autowired
	@Qualifier("BackUserLogService")
	private BackUserLogService backUserLogService;

	/**
	 * 日期转换
	 * 
	 * @param request
	 *            请求
	 * @param binder
	 *            绑定
	 * @throws Exception
	 *             所有异常
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 公告信息查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/announcement/announcementList")
	public String announcementList(HttpServletRequest request, Model model) {
		int status = RequestUtils.getParameterInt(request, "status", 0);
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 100); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		//增加前端url模糊查询
		String url = RequestUtils.getParameter(request, "url", "");
		//开始时间
		String startTime = RequestUtils.getParameter(request, "startTime", "");
		//结束时间
		String endTime = RequestUtils.getParameter(request, "endTime", "");
																				
		if (!"createtime".equals(sort)) {
			return "";
		}
		
		String title = RequestUtils.getParameter(request, "title", "");
		//发布平台
		int platform = RequestUtils.getParameterInt(request, "platform", 3);
		PageInfo<Announcement> pageInfo = announcementService.queryAnnouncementList(title,status, pageSize, pageNo,sort, order,url,startTime,endTime,platform);
		model.addAttribute("pageInfo", pageInfo);
		return "announcement/announcementList";
	}

	/**
	 * 公告信息列表
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 公告信息列表JSON
	 */
	@RequestMapping(value = "/announcement/announcementList_json")
	@ResponseBody
	public JSONObject announcementListJson(HttpServletRequest request,Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();

		int status = RequestUtils.getParameterInt(request, "status", 0);
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		if (!"createtime".equals(sort)) {
			return null;
		}
		//增加标题模糊查询
		String title = RequestUtils.getParameter(request, "title", "");
		//增加前端url模糊查询
		String url = RequestUtils.getParameter(request, "url", "");
		//开始时间
		String startTime = RequestUtils.getParameter(request, "startTime", "");
		//结束时间
		String endTime = RequestUtils.getParameter(request, "endTime", "");
		//发布平台
		int platform = RequestUtils.getParameterInt(request, "platform", 3);
		
		PageInfo<Announcement> pageInfo = announcementService.queryAnnouncementList(title,status, pageSize, pageNo,sort, order,url,startTime,endTime,platform);
		List<Announcement> annoList = pageInfo.getItems();
		data.put("total", pageInfo.getRecordCount());
		List<AnnouncementCategory> announcementCategoryList = announcementService.queryCategoryList("weight", "desc");
		for (Announcement announcement : annoList) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> annoMap = new HashMap<String, Object>();
			annoMap.put("announcementId", announcement.getAnnouncementId());
			annoMap.put("title", announcement.getTitle());
			annoMap.put("template", announcement.getTemplate());
			annoMap.put("content", announcement.getContent());
			annoMap.put("categoryId", announcement.getCategoryId());
			if(StringUtils.isNotBlank(announcement.getUrl())){
				annoMap.put("url", announcement.getUrl());
			}
			String categoryName = "";
			for (AnnouncementCategory announcementCategory : announcementCategoryList) {
				if (announcementCategory.getAnnouncementCategoryId() == announcement.getCategoryId()) {
					categoryName = announcementCategory.getCategoryName();
				}
			}
			annoMap.put("categoryName", categoryName);
			String createTime = sdf.format(announcement.getCreatetime());
			if (null != announcement.getSendedTime()) {
				String sendedTime = sdf.format(announcement.getSendedTime());
				annoMap.put("sendedTime", sendedTime);
			}
			String validTime = sdf.format(announcement.getValiddate());
			annoMap.put("createTime", createTime);
			annoMap.put("validdate", validTime);
			annoMap.put("expDate", "["+createTime + "] - [" + validTime + "]");
			annoMap.put("status", announcement.getStatus() == 0 ? "已发布" : (announcement.getStatus() == 1 ? "已删除" : "待发布"));
			annoMap.put("opreation","<a href='javascript:void(0)' onclick='showDetail("+ announcement.getAnnouncementId() + ");'>详细信息</a>");
			annoMap.put("releasePlatform", announcement.getReleasePlatform());
			showList.add(annoMap);
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		return JSONObject.fromObject(data);
	}

	/**
	 * 添加公告信息
	 * 
	 * @param announcement
	 *            公告
	 * @param request
	 *            请求
	 * @return 消息提示
	 */
	@RequestMapping(value = "/announcement/announcement_add")
	@ResponseBody
	public MessageBean announcementAdd(Announcement announcement,
			HttpServletRequest request) {
		if (StringUtils.isBlank(announcement.getTitle())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(announcement.getContent())) {
			return new MessageBean(false, "内容不能为空");
		}
		String title = announcement.getTitle();
		String template = announcement.getTemplate();
		int categoryId = announcement.getCategoryId();
		String content = announcement.getContent();
		String url = announcement.getUrl();
		String validdate = request.getParameter("validdate");
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(currentTime);
		int releasePlatform = announcement.getReleasePlatform();
		if (compareDate(nowTime, validdate) == -1) {
			String typename = request.getParameter("typename");
			if (StringUtils.isBlank(typename)) {
				return new MessageBean(false, "发送类型不能为空");
			}
			int iTypename = Integer.parseInt(typename);
			if (iTypename == 1) {
				String sendedTime = request.getParameter("sendedTime");
				if (StringUtils.isBlank(sendedTime)) {
					return new MessageBean(false, "定时发送时间不能为空");
				}
				if (compareDate(sendedTime, validdate) == -1
						|| compareDate(sendedTime, validdate) == 0) {
					int isSuccess = announcementService.addAnnouncement(title,template, content, validdate, categoryId,sendedTime,releasePlatform,url);
					BackUserLogUtil.log(backUserLogService,
							(isSuccess > 0) ? true : false, "",
							BackUserLogService.ANNOUNCEMENT_ADD, request);
				} else {
					return new MessageBean(false, "定时发送时间不能大于有效期时间");
				}
			} else {
				int isSuccess = announcementService.addAnnouncement(title,template, content, validdate, categoryId, null,releasePlatform,url);
				BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true
						: false, "", BackUserLogService.ANNOUNCEMENT_ADD,
						request);
			}
			return new MessageBean();
		} else {
			return new MessageBean(false, "有效期时间不能小于当前时间");
		}
	}

	/**
	 * 比较两个日期前后
	 * 
	 * @param dateF
	 *            日期1
	 * @param dateS
	 *            日期2
	 * @return 1: 日期1>日期2, -1:日期1<日期2, 0:日期1=日期2
	 */
	public int compareDate(String dateF, String dateS) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式控制
		try {
			Date dt1 = df.parse(dateF);
			Date dt2 = df.parse(dateS);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 删除公告信息
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcement_delete")
	@ResponseBody
	public MessageBean announcementDelete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		logger.info("del =" + ids);
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}

		String[] idArr = ids.split(",");
		if (idArr.length > 5) {
			return new MessageBean(false, "最多只能同时删除5条记录");
		}
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < idArr.length; i++) {
			try {
				int flag = announcementService.deleteAnnouncement(Integer
						.parseInt(idArr[i]));
				BackUserLogUtil.log(backUserLogService, (flag != 0) ? true
						: false, idArr[i],
						BackUserLogService.ANNOUNCEMENT_DELETE, request);
				if (flag != 0) {
					sb.append("ID[" + idArr[i] + "]删除成功 \n<br>");
				}
			} catch (Exception e) {
				logger.error("", e);
			}

		}
		return new MessageBean(true, sb.toString());
	}

	/**
	 * 根据ID查询帮助详细信息
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 返回公告
	 */
	@RequestMapping(value = "/announcement/announcement_json")
	@ResponseBody
	public JSONObject announcementJson(HttpServletRequest request, Model model) {
		JSONObject anno = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int announcementId = RequestUtils.getParameterInt(request,"announcementId", 0);
		Announcement announcement = announcementService.getAnnouncementDetailForManage(announcementId);
		List<AnnouncementCategory> announcementCategoryList = announcementService.queryCategoryList("weight", "desc");
		if (announcement == null) {
			return null;
		} else {
			anno.put("title", announcement.getTitle());
			anno.put("template", announcement.getTemplate());
			anno.put("announcementId", announcement.getAnnouncementId());
			anno.put("categoryId", announcement.getCategoryId());
			String categoryName = "";
			for (AnnouncementCategory announcementCategory : announcementCategoryList) {
				if (announcementCategory.getAnnouncementCategoryId() == announcement.getCategoryId()) {
					categoryName = announcementCategory.getCategoryName();
				}
			}
			anno.put("categoryName", categoryName);
			anno.put("content", announcement.getContent());
			anno.put("status", announcement.getStatus());
			anno.put("createtime", sdf.format(announcement.getCreatetime()));
			anno.put("validdate", sdf.format(announcement.getValiddate()));
			anno.put("sendedTime", sdf.format(announcement.getSendedTime()));
		}
		return anno;
	}

	/**
	 * 根据categoryId查询公告信息
	 * 
	 * @param categoryId
	 *            公告分类ID
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 返回转向地址
	 */
	@RequestMapping(value = "/announcement/announcementListById")
	public String announcementListByCategoryIdJson(
			//
			@RequestParam int categoryId, HttpServletRequest request,
			Model model) {

		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		PageInfo<Announcement> pageInfo = announcementService
				.queryAnnouncemntListByCategoryID(categoryId, pageSize, pageNo,
						sort, order);
		model.addAttribute("pageInfo", pageInfo);
		return "announcement/announcementList_json";
	}

	/**
	 * 公告信息类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/announcement/announcementCategoryList")
	public String announcementCategoryList(HttpServletRequest request,
			Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		if (!"weight".equals(sort)) {
			return "";
		}
		PageInfo<AnnouncementCategory> helpCategoryList = announcementService
				.queryCategoryList(pageSize, pageNo, sort, order);
		model.addAttribute("categorySize", helpCategoryList.getRecordCount());
		model.addAttribute("announcementCategoryList",
				helpCategoryList.getItems());
		return "announcement/announcementCategoryList";
	}

	/**
	 * 公告信息类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/announcement/announcementCategoryList_json")
	@ResponseBody
	public JSONObject announcementCategoryListJson(HttpServletRequest request,
			Model model) {
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
		PageInfo<AnnouncementCategory> annoCategoryList = announcementService
				.queryCategoryList(pageSize, pageNo, sort, order);
		data.put("total", annoCategoryList.getRecordCount());
		for (AnnouncementCategory announcementCategory : annoCategoryList
				.getItems()) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("categoryId",
					announcementCategory.getAnnouncementCategoryId());
			categoryMap.put("categoryName",
					announcementCategory.getCategoryName());
			categoryMap.put("weight", announcementCategory.getWeight());
			showList.add(categoryMap);
		}
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 添加公告信息类型
	 * 
	 * @param announcementCategory
	 *            公告分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcementCategory_add")
	@ResponseBody
	public MessageBean announcementCategoryAdd(
			AnnouncementCategory announcementCategory,
			HttpServletRequest request) {
		if (StringUtils.isBlank(announcementCategory.getCategoryName())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(announcementCategory.getWeight() + "")) {
			return new MessageBean(false, "权重不能为空");
		}
		String categoryName = announcementCategory.getCategoryName();
		int weight = announcementCategory.getWeight();
		if (announcementService.queryCategoryNameIsExist(categoryName) == 0) {
			int isSuccess = announcementService.addAnnoCategory(categoryName,
					weight);
			BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true
					: false, "", BackUserLogService.ANNOUNCEMENT_CATEGORY_ADD,
					request);
			return new MessageBean();
		} else {
			return new MessageBean(false, "您输入的公告分类名称已经存在请重新输入");
		}
	}

	/**
	 * 公告是否存在
	 * 
	 * @param category
	 *            公告分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcementCategoryIsExist")
	@ResponseBody
	public MessageBean announcementCategoryIsExist(AnnouncementCategory category) {
		String categoryName = category.getCategoryName();
		if (StringUtils.isBlank(categoryName)) {
			return new MessageBean(false, "请输入游戏名称");
		}
		int isExist = announcementService
				.queryCategoryNameIsExist(categoryName);
		if (isExist != 0) {
			return new MessageBean(false, "您输入的分类名称已经存在请重新输入,请重新输入");
		} else {
			return new MessageBean(true, "该分类名称可以使用");
		}
	}

	/**
	 * 删除公告信息类型
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcementCategory_delete")
	@ResponseBody
	public MessageBean announcementCategoryDelete(HttpServletRequest request) {
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
				int effectRow = announcementService.deleteAnnoCategory(
						Integer.parseInt(idArr[i]), false);
				BackUserLogUtil.log(backUserLogService, (effectRow == 0) ? true
						: false, idArr[i],
						BackUserLogService.ANNOUNCEMENT_CATEGORY_DELETE,
						request);
				if (effectRow != 0) {
					return new MessageBean(false, "删除公告分类,先强制级联删除该分类下公告信息");
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}

	/**
	 * 修改公告
	 * 
	 * @param announcement
	 *            公告
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcement_edit")
	@ResponseBody
	public MessageBean announcementEdit(Announcement announcement,
			HttpServletRequest request) {
		Integer announcementID = announcement.getAnnouncementId();
		int categoryId = announcement.getCategoryId();
		Integer status = announcement.getStatus();
		String content = announcement.getContent();
		String title = announcement.getTitle();
		String url = announcement.getUrl();
		String template = announcement.getTemplate();
		String validdate = request.getParameter("validdate");
		int releasePlatform = announcement.getReleasePlatform();
		if (StringUtils.isBlank(announcement.getTitle())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(announcement.getContent())) {
			return new MessageBean(false, "内容不能为空");
		}
		Announcement anno = announcementService
				.getAnnouncementDetailForManage(announcementID);
		if (anno != null) {
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = sdf.format(currentTime);
			if (compareDate(nowTime, validdate) == -1) {
				String typename = request.getParameter("typename");
				if (StringUtils.isBlank(typename)) {
					return new MessageBean(false, "发送类型不能为空");
				}
				int iTypename = Integer.parseInt(typename);
				if (iTypename == 1) {
					String sendedTime = request.getParameter("sendedTime");
					if (StringUtils.isBlank(sendedTime)) {
						return new MessageBean(false, "定时发送时间不能为空");
					}
					if (compareDate(sendedTime, validdate) == -1
							|| compareDate(sendedTime, validdate) == 0) {
						int isSuccess = announcementService.modifyAnnouncement(announcementID, title, template, content,sendedTime, validdate, categoryId, status,releasePlatform,url);
						BackUserLogUtil
								.log(backUserLogService, (isSuccess > 0) ? true
										: false, announcementID + "",
										BackUserLogService.ANNOUNCEMENT_UPDATE,
										request);
					} else {
						return new MessageBean(false, "定时发送时间不能大于有效期时间");
					}
				} else {
					int isSuccess = announcementService.modifyAnnouncement(
							announcementID, title, template, content, null,
							validdate, categoryId, status,releasePlatform,url);
					BackUserLogUtil.log(backUserLogService,
							(isSuccess > 0) ? true : false,
							announcementID + "",
							BackUserLogService.ANNOUNCEMENT_UPDATE, request);
				}
				return new MessageBean();
			} else {
				return new MessageBean(false, "有效期时间不能小于当前时间");
			}
		} else {
			return new MessageBean(false, "该[" + announcementID + "]公告不存在");
		}
	}

	/**
	 * 修改公告类型
	 * 
	 * @param announcementCategory
	 *            公告分类
	 * @return 提示信息
	 */
	@RequestMapping(value = "/announcement/announcementCategory_edit")
	@ResponseBody
	public MessageBean announcementCategoryEdit(
			AnnouncementCategory announcementCategory,
			HttpServletRequest request) {
		if (StringUtils.isBlank(announcementCategory.getCategoryName())) {
			return new MessageBean(false, "公告分类名称不能为空");
		}
		if (StringUtils.isBlank(announcementCategory.getWeight() + "")) {
			return new MessageBean(false, "权重不能为空");
		}
		int categoryID = announcementCategory.getAnnouncementCategoryId();
		String categoryName = announcementCategory.getCategoryName();
		int weight = announcementCategory.getWeight();
		int isSuccess = announcementService.modifyAnnoCategory(categoryID,
				categoryName, weight);
		BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false,
				categoryID + "",
				BackUserLogService.ANNOUNCEMENT_CATEGORY_UPDATE, request);
		return new MessageBean();
	}

	/**
	 * 获得所有公告分类
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/announcement/allAnnoCategoryList_json")
	@ResponseBody
	public JSONObject allAnnoCategoryListJson(HttpServletRequest request,
			Model model) {
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		String sort = RequestUtils.getParameter(request, "sort", "weight"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
																			// desc
		if (!"weight".equals(sort)) {
			return null;
		}
		List<AnnouncementCategory> announcementCategoryList = announcementService
				.queryCategoryList(sort, order);
		data.put("total", announcementCategoryList.size());

		for (AnnouncementCategory announcementCategory : announcementCategoryList) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("categoryId",
					announcementCategory.getAnnouncementCategoryId());
			categoryMap.put("categoryName",
					announcementCategory.getCategoryName());
			categoryMap.put("weight", announcementCategory.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}
}
