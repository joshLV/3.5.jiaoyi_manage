package cn.jugame.admin.web.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.service.ISysUserinfoService;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.suggestion.service.SuggestionService;
import cn.juhaowan.suggestion.vo.Suggestion;

/**
 * 意见反馈
 * 
 */
@Controller
public class FeedbackController {
	Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	@Autowired
	@Qualifier("SuggestionService")
	private SuggestionService suggestionService;
	@Autowired
	private BackUserLogService backUserLogService;
	@Autowired
	private ISysUserinfoService sysUserService;
	/**
	 * 意见反馈列表FreeMaker
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/feedback/feedbackList")
	public String feedbackList(HttpServletRequest request, Model model) {

		return "feedback/feedbackList";
	}

	/**
	 * 意见反馈列表JSON
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/feedback/feedbackList_json")
	@ResponseBody
	public JSONObject feedbackListJson(HttpServletRequest request, Model model) {
		JSONObject data = new JSONObject();
		
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();

		int categoryId = RequestUtils.getParameterInt(request, "categoryId", -1);
		int status = RequestUtils.getParameterInt(request, "status", -2);
		
		int huid=RequestUtils.getParameterInt(request, "handledUserID",0);
		int isHandled=RequestUtils.getParameterInt(request, "isHandled", -3);
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 10); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "createtime"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "DESC"); // asc
		int source = RequestUtils.getParameterInt(request, "source", -1); // asc
		if (!"createtime".equals(sort)) {
			return null;
		}
		PageInfo<Suggestion> pageInfo = suggestionService.querySuggestionList(categoryId, status, huid, isHandled,
				pageSize, pageNo, sort, order, source);
		if (pageInfo.getItems()!=null&&pageInfo!=null) {
			List<Suggestion> feedbackList = pageInfo.getItems();
			data.put("total", pageInfo.getRecordCount());
			for (Suggestion suggestion : feedbackList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("suggestionId", suggestion.getSuggestionId());
				String categoryName = "";
				switch (suggestion.getCategoryId()) {
				case 1:
					categoryName = "优化建议";
					break;
				case 2:
					categoryName = "问题错误";
					break;
				case 3:
					categoryName = "其他反馈";
					break;
				case 4:
					categoryName = "VIP礼包申请";
					break;
				case 5:
					categoryName = "游戏反馈";
					break;
				}
				map.put("categoryName", categoryName);
				
				map.put("userId", suggestion.getUserId());
				String content = suggestion.getContent()==null?"":suggestion.getContent();
				if(null != suggestion.getContent()){
					if(content.contains("script")){
						content = "";
					}
					map.put("content", content);
				}else{
					map.put("content", content);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("createtime", sdf.format(suggestion.getCreatetime()));
				if (suggestion.getHandledTime()!=null) {
					
					map.put("handledTime", sdf.format(suggestion.getHandledTime()));
				}else {
					map.put("handledTime", "");
				}
				String statusStr = "";
				switch (suggestion.getStatus()) {
				case 0:
					statusStr = "未读";
					break;
				case 1:
					statusStr = "已读";
					break;
				default:
					statusStr = "删除";
					break;
				}
				map.put("status", statusStr);
				map.put("source", suggestion.getSource());
				SysUserinfo sysUserinfo = sysUserService.findById(suggestion.getHandledUserId());
				String handledUser = "--";
				if (sysUserinfo != null) {
					handledUser = sysUserService.findById(suggestion.getHandledUserId()).getFullname();
				}
				map.put("handledUser", handledUser);
				String isHandledStr="";
				switch (suggestion.getIsHandled()) {
				case 0:
					isHandledStr="已处理";
					break;
				default:
					isHandledStr="未处理";
					break;
				}
				map.put("isHandled", isHandledStr);
				if (suggestion.getRemark()==null) {
					map.put("remark", "");
				}else if (suggestion.getRemark()!=null&&suggestion.getRemark().length()>10) {
					map.put("remark",suggestion.getRemark().substring(1,10)+"...");
				}else {
					map.put("remark", suggestion.getRemark());
				}
				map.put("qq", suggestion.getQq() == null ? "--" : suggestion.getQq());
				map.put("si", suggestion.getSi()==null?"":suggestion.getSi());
				//map.put("remark", suggestion.getRemark() != null ? suggestion.getRemark() : "");
				map.put("opreation", "<a href='javascript:void(0)' onclick='showDetail(" + suggestion.getSuggestionId()
						+ ");'>详细信息</a>");
				showList.add(map);
			}
		}
		
		JSONArray rows = JSONArray.fromObject(showList);
		data.put("rows", rows);
		return data;
	}

	/**
	 * 添加反馈信息
	 * 
	 * @param suggestion
	 *            意见反馈
	 * @return 提示信息
	 */
	@RequestMapping(value = "/feedback/feedback_add")
	@ResponseBody
	public MessageBean feedbackAdd(Suggestion suggestion, HttpServletRequest request) {
		if (StringUtils.isBlank(suggestion.getUserId() + "")) {
			return new MessageBean(false, "用户ID不能为空");
		}

		if (StringUtils.isBlank(suggestion.getContent())) {
			return new MessageBean(false, "内容不能为空");
		}
		if (StringUtils.isBlank(suggestion.getUserGameId())) {
			return new MessageBean(false, "用户帐号不能为空");
		}
		if (StringUtils.isBlank(suggestion.getMobile())) {
			return new MessageBean(false, "电话不能为空");
		}
		if (StringUtils.isBlank(suggestion.getQq())) {
			return new MessageBean(false, "QQ不能为空");
		}
		if (StringUtils.isBlank(suggestion.getEmail())) {
			return new MessageBean(false, "邮箱不能为空");
		}
		int userId = suggestion.getUserId();
		String usergameId = suggestion.getUserGameId();
		int categoryId = suggestion.getCategoryId();
		String content = suggestion.getContent();
		String mobile = suggestion.getMobile();
		String qq = suggestion.getQq();
		String email = suggestion.getEmail();

		int isSuccess = suggestionService.addSuggestion(userId, content, categoryId, usergameId, qq, email, mobile, 1); // 默认1内部插入
		BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, "", BackUserLogService.FEEDBACK_ADD,request);
		return new MessageBean();
	}

	/**
	 * 删除反馈信息
	 * 
	 * @param request
	 *            请求
	 * @return 提示信息
	 */
	@RequestMapping(value = "/feedback/feedback_delete")
	@ResponseBody
	public MessageBean feedbackDelete(HttpServletRequest request) {
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
				int isSuccess = suggestionService.deleteSuggestion(Integer.parseInt(idArr[i]));
				BackUserLogUtil.log(backUserLogService, (isSuccess > 0) ? true : false, idArr[i],BackUserLogService.FEEDBACK_DELETE, request);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return new MessageBean();
	}


	/**
	 * 反馈状态变更
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 提示信息
	 */
	@RequestMapping(value = "/feedback/feedbackChangeStatus")
	@ResponseBody
	public MessageBean feedbackChangeStatus(HttpServletRequest request, Model model) {
		int feedbackId = RequestUtils.getParameterInt(request, "feedbackId", 0);
		int flag = suggestionService.changeSuggestionStatus(feedbackId, 1);
		StringBuffer sb = new StringBuffer();
		if (flag == 1) {
			return new MessageBean(false, "操作成功");
		}
		return new MessageBean(true, sb.toString());
	}
	/**
	 * 根据suggestionId查出对应反馈信息
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "/feedback/feedback_json")
	@ResponseBody
	public Suggestion getSuggestion(HttpServletRequest request) {
		int feedbackId=RequestUtils.getParameterInt(request, "feedbackId",0 );
		Suggestion s=suggestionService.getSuggestionDetail(feedbackId);
		if (s.getRemark()==null) {
			s.setRemark(" ");
		}
		String content = s.getContent()==null?"":s.getContent();
		if(null != s.getContent()){
			if(content.contains("script")){
				content = "";
			}
			s.setContent(content);
		}else{
			s.setContent(content);
		}
		return s;
	}
	
	/**
	 * 更新备注信息
	 * @param request
	 * @return提示信息
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/feedback/remark_update")
	@ResponseBody
	public MessageBean updateRemark(HttpServletRequest request) throws UnsupportedEncodingException {
		String remark=request.getParameter("remark");
		int isHandled=RequestUtils.getParameterInt(request, "isHandled", 1);
		int sId=RequestUtils.getParameterInt(request, "suggestionId", 0);
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
		int suId = u.getUserId();
		Date handDate=new Date();
		int i = suggestionService.updateRemarkRelated(isHandled, remark, suId,sId,handDate);
		if (i > 0) {
			return new MessageBean(true, "操作成功");
		} else {
			return new MessageBean(false, "操作失败");

		}	
	}
}
