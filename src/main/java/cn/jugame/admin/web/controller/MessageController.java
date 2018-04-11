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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.localmessage.service.LocalMessageService;
import cn.juhaowan.localmessage.vo.MessageMission;
import cn.juhaowan.localmessage.vo.MessageMissionCategory;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 站内信管理
 * 
 * @author houjt
 * 
 */
@Controller
@RequestMapping(value = "/message")
public class MessageController {
	Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private LocalMessageService localMessageService;
	@Autowired
	private BackUserLogService backUserLogService;

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/messageMissionList")
	public String messageList() {
		return "message/messageMissionList";
	}

	/**
	 * 列表JSON数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/messageMissionList_json")
	@ResponseBody
	public JSONObject messageListJson(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		data.put("total", 0);
		data.put("rows", "[]");
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 20);// 每页多少条记录
		String status = RequestUtils.getParameter(request, "status", "-1");// 查询状态
		PageInfo<MessageMission> pageInfo = this.localMessageService.queryMissionList(Integer.parseInt(status),
				pageSize, pageNo);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		if (pageInfo != null && pageInfo.getItems() != null) {
			data.put("total", pageInfo.getRecordCount());
			List<MessageMission> list = pageInfo.getItems();
			Map<String, Object> newMap = null;

			List<MessageMissionCategory> mmcList = localMessageService.queryCategoryList("weight", "desc");

			for (int i = 0; i < list.size(); i++) {
				newMap = new HashMap<String, Object>();
				MessageMission messageMission = list.get(i);
				// 站内信列表接收人字段 截取前三个接收人的uid显示
				StringBuffer sb = new StringBuffer();
				if (null != messageMission.getReceiver() && messageMission.getReceiver().length() > 20) {
					for (int j = 0; j < 3; j++) {

						sb.append(messageMission.getReceiver().split(",")[j]);
						if (j != 2) {
							sb.append(",");
						}
					}
					newMap.put("receiver", sb.toString());
				} else {
					newMap.put("receiver", messageMission.getReceiver());
				}
				newMap.put("receiverAll", messageMission.getReceiver());
				newMap.put("messageMissionId", messageMission.getMessageMissionId());
				newMap.put("messageContent", messageMission.getMessageContent());

				newMap.put("messageTitle", messageMission.getMessageTitle());

				newMap.put("categoryId", messageMission.getMmcId());
				String categoryName = "";
				for (MessageMissionCategory mmc : mmcList) {
					if (messageMission.getMmcId() == mmc.getMmcId()) {
						categoryName = mmc.getCategoryName();
					}
				}
				newMap.put("mmcName", categoryName);

				newMap.put("status", messageMission.getStatus());
				newMap.put("createTime", sf.format(messageMission.getCreatetime()));
				if (messageMission.getSendtime() != null) {
					newMap.put("sendTime", sf.format(messageMission.getSendtime()));
				}
				if (messageMission.getSendedtime() != null) {
					newMap.put("sendedTime", sf.format(messageMission.getSendedtime()));
				}
				showList.add(newMap);
			}
			JSONArray rows = JSONArray.fromObject(showList);
			data.put("rows", rows);
		}
		return data;
	}

	/**
	 * 群发
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/messageMissionListAdd")
	@ResponseBody
	public MessageBean messageMissionListAdd(HttpServletRequest request) {
		String messageTitle = RequestUtils.getParameter(request, "messageTitle", "");
		String receiver = RequestUtils.getParameter(request, "receiver", "");
		String messageContent = RequestUtils.getParameter(request, "messageContent", "");
		String sendtime = RequestUtils.getParameter(request, "sendtime", "");
		int mmcId = RequestUtils.getParameterInt(request, "categoryId", 0);
		if (StringUtils.isBlank(messageTitle)) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(receiver)) {
			return new MessageBean(false, "收信人不能为空");
		}
		if (StringUtils.isBlank(messageContent)) {
			return new MessageBean(false, "信息内容不能为空");
		}
		if (StringUtils.isBlank(sendtime)) {
			return new MessageBean(false, "预订发送时间不能为空");
		}
		String[] array = receiver.split(",");
		// 判断选中人数
		if (array != null && array.length > 0) {
			if (array.length > 1) {
				int[] userIDArray = new int[array.length];
				for (int i = 0; i < array.length; i++) {
					if (array[i] != null) {
						userIDArray[i] = Integer.parseInt(array[i]);
					}
				}
				this.localMessageService.timingSendMessageToUsers(userIDArray, messageContent, messageTitle, sendtime, mmcId);
			} else {
				this.localMessageService.timingSendMessageToUser(Integer.parseInt(array[0]), messageContent, messageTitle,
						sendtime, mmcId);
			}
		}
		backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.STAND_INSIDE_LETTER_ADD,
				"admin添加站内信");
		// 操作成功
		return new MessageBean();
	}

	/**
	 * 删除用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/messageMissionDelete")
	@ResponseBody
	public MessageBean messageMissionDelete(HttpServletRequest request) {
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
		sb.append("成功删除任务ID为");
		for (int i = 0; i < idArr.length; i++) {
			try {
				this.localMessageService.deleteSendMission(Integer.parseInt(idArr[i]));
				sb.append(idArr[i] + ",");
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.STAND_INSIDE_LETTER_DELETE,
				"admin" + sb.toString());
		sb.append("的订时站内信任务");
		return new MessageBean(true, sb.toString());
	}

	/**
	 * 站内信类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/messageMissionCategoryList")
	public String messageMissionCategoryList(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10);
		String sort = RequestUtils.getParameter(request, "sort", "weight");
		String order = RequestUtils.getParameter(request, "order", "desc");
		if (!"weight".equals(sort)) {
			return "";
		}
		PageInfo<MessageMissionCategory> mmcCategoryList = localMessageService.queryCategoryList(pageSize, pageNo,
				sort, order);
		model.addAttribute("categorySize", mmcCategoryList.getRecordCount());
		model.addAttribute("messageMissionCategoryList", mmcCategoryList.getItems());
		return "message/messageMissionCategoryList";
	}

	/**
	 * 站内信类型查询
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 帮助分类列表JSON
	 */
	@RequestMapping(value = "/messageMissionCategoryList_json")
	@ResponseBody
	public JSONObject messageMissionCategoryListJson(HttpServletRequest request, Model model) {
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
		PageInfo<MessageMissionCategory> mmcCategoryList = localMessageService.queryCategoryList(pageSize, pageNo,
				sort, order);
		data.put("total", mmcCategoryList.getRecordCount());

		for (MessageMissionCategory mmcCategory : mmcCategoryList.getItems()) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("mmcId", mmcCategory.getMmcId());
			categoryMap.put("categoryName", mmcCategory.getCategoryName());
			categoryMap.put("weight", mmcCategory.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 获得所有站内信类型
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 站内信类型列表JSON
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
		List<MessageMissionCategory> mccCategoryList = localMessageService.queryCategoryList(sort, order);
		data.put("total", mccCategoryList.size());

		for (MessageMissionCategory mccCategory : mccCategoryList) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("mmcId", mccCategory.getMmcId());
			categoryMap.put("categoryName", mccCategory.getCategoryName());
			categoryMap.put("weight", mccCategory.getWeight());
			showList.add(categoryMap);
		}

		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);

		return JSONObject.fromObject(data);
	}

	/**
	 * 添加站内信类型
	 * 
	 * @param messageMissionCategory
	 *            站内信类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/messageMissionCategory_add")
	@ResponseBody
	public MessageBean messageMissionCategoryAdd(MessageMissionCategory messageMissionCategory,
			HttpServletRequest request) {
		if (StringUtils.isBlank(messageMissionCategory.getMmcId() + "")) {
			return new MessageBean(false, "站内信ID不能为空");
		}
		if (StringUtils.isBlank(messageMissionCategory.getCategoryName())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(messageMissionCategory.getWeight() + "")) {
			return new MessageBean(false, "权重不能为空");
		}
		int mmcId = messageMissionCategory.getMmcId();
		boolean isExist = localMessageService.isExistMmcId(mmcId);
		if (isExist) {
			return new MessageBean(false, "您输入的站内信ID号已被占用，请填写其他数值");
		}
		String categoryName = messageMissionCategory.getCategoryName();
		int weight = messageMissionCategory.getWeight();
		if (localMessageService.queryCategoryNameIsExist(categoryName) == 0) {
			int isSuccess = localMessageService.addMessageMissionCategory(mmcId, categoryName, weight);
			BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, "",
					BackUserLogService.MESSAGE_MISSION_CATEGORY_ADD, request);
			// 操作成功
			return new MessageBean();
		} else {
			return new MessageBean(false, "您输入的站内信类型名称已经存在请重新输入");
		}

	}

	/**
	 * 站内信类型名称是否存在
	 * 
	 * @param category
	 *            站内信类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/messageMissionCategoryIsExist")
	@ResponseBody
	public MessageBean messageMissionCategoryIsExist(MessageMissionCategory category) {
		String categoryName = category.getCategoryName();
		if (StringUtils.isBlank(categoryName)) {
			return new MessageBean(false, "请输入站内信类型");
		}
		int isExist = localMessageService.queryCategoryNameIsExist(categoryName);
		if (isExist == 0) {
			return new MessageBean(true, "该类型名称可以使用");
		} else {
			return new MessageBean(false, "您输入的类型名称已经存在,请重新输入");
		}
	}

	/**
	 * 站内信类型ID是否存在
	 * 
	 * @param category
	 *            站内信类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/mmcIsExistId")
	@ResponseBody
	public MessageBean mmcIsExistId(MessageMissionCategory category) {
		String mccId = category.getMmcId() + "";
		if (StringUtils.isBlank(mccId)) {
			return new MessageBean(false, "请输入站内信ID");
		}
		boolean isExist = localMessageService.isExistMmcId(category.getMmcId());
		if (isExist) {
			return new MessageBean(false, "您输入的类型ID已经存在,请重新输入");
		} else {
			return new MessageBean(true, "该类型ID可以使用");
		}
	}

	/**
	 * 删除站内信类型
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/messageMissionCategory_delete")
	@ResponseBody
	public MessageBean messageMissionCategoryDelete(HttpServletRequest request) {
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
				int effectRow = localMessageService.deleteMessageMissionCategory(Integer.parseInt(idArr[i]), false);
				BackUserLogUtil.log(backUserLogService, (effectRow == 0) ? true : false, idArr[i],
						BackUserLogService.MESSAGE_MISSION_CATEGORY_DELETE, request);

				if (effectRow == 0) {
					return new MessageBean(false, "删除该站内信类型成功");
				} else {
					return new MessageBean(true, "删除该站内信类型失败，可能该站内信类型下有站内信内容");
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}

	/**
	 * 修改站内信类型
	 * 
	 * @param messageMissionCategory
	 *            站内信类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/messageMissionCategory_edit")
	@ResponseBody
	public MessageBean messageMissionCategoryEdit(MessageMissionCategory messageMissionCategory,
			HttpServletRequest request) {
		if (StringUtils.isBlank(messageMissionCategory.getCategoryName())) {
			return new MessageBean(false, "标题不能为空");
		}
		if (StringUtils.isBlank(messageMissionCategory.getWeight() + "")) {
			return new MessageBean(false, "内容不能为空");
		}
		int categoryID = messageMissionCategory.getMmcId();
		String categoryName = messageMissionCategory.getCategoryName();
		int weight = messageMissionCategory.getWeight();
		int isSuccess = localMessageService.modifyMessageMission(categoryID, categoryName, weight);
		BackUserLogUtil.log(backUserLogService, (isSuccess == 0) ? true : false, categoryID + "",
				BackUserLogService.MESSAGE_MISSION_CATEGORY_EDIT, request);
		return new MessageBean();
	}
}
