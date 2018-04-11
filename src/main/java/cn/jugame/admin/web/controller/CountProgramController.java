//package cn.jugame.admin.web.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.vo.SysUserinfo;
//import cn.jugame.web.util.GlobalsKeys;
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.count.service.CountProgramService;
//import cn.juhaowan.count.vo.CountProgram;
//import cn.juhaowan.log.service.BackUserLogService;
//
///**
// * 
// * @author Administrator
// * 栏目统计
// */
//@Controller
//@RequestMapping(value = "/program")
//public class CountProgramController {
//	@Autowired
//	private CountProgramService countProgramService;
//	@Autowired
//	private BackUserLogService backUserLogService;
//	/**
//	 * 查询所有配置的栏目
//	 * 
//	 * @param model 
//	 * @return 跳转
//	 */
//	@RequestMapping(value = "/countProgramList")
//	public String countProgramList(Model model) {
//		List<CountProgram> parentProgram = this.countProgramService.getParentProgram();
//		model.addAttribute("parentProgram", parentProgram);
//		return "count/countProgramList";
//	}
//
//	/**
//	 * 栏目详细列表
//	 * 
//	 * @param model
//	 * @return JSON对象
//	 */
//	@RequestMapping(value = "/countProgramList_json")
//	@ResponseBody
//	public JSONObject countProgramListJson(Model model) {
//		JSONObject data = new JSONObject();
//		data.put("total", 0);
//		data.put("rows", "[]");
//		List<CountProgram> totalProgram = this.countProgramService.getAllProgram();
//		List<Map<String,Object>> showList = new ArrayList<Map<String,Object>>();
//		if (totalProgram != null && totalProgram.size() > 0) {
//			int total = totalProgram.size();
//			data.put("total", total);
//			for (int i = 0; i < total; i++) {
//				CountProgram countProgram = totalProgram.get(i);
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("id", countProgram.getId());
//				map.put("_parentId", countProgram.getParentProgram());
//				map.put("programName", countProgram.getProgramName());
//				map.put("programUrl", countProgram.getProgramUrl());
//				map.put("status", countProgram.getStatus());
//				map.put("remark", countProgram.getRemark());
//				showList.add(map);
//			}
//		}
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//		return data;
//	}
//
//	/**
//	 * 添加栏目
//	 * 
//	 * @param countProgram
//	 *            栏目对象
//	 * @param request 
//	 * @return 提示
//	 */
//	@RequestMapping(value = "/addCountProgram")
//	@ResponseBody
//	public MessageBean addCountProgram(CountProgram countProgram,
//			HttpServletRequest request) {
//		if (StringUtils.isBlank(countProgram.getProgramName())) {
//			return new MessageBean(false, "栏目名称不能为空");
//		}
//		if (StringUtils.isBlank(countProgram.getProgramUrl())) {
//			return new MessageBean(false, "栏目url不能为空");
//		}
//		// 获取操作用户
//		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(
//				GlobalsKeys.ADMIN_KEY);
//		countProgram.setCreateTime(new Date());
//		countProgram.setCreateUuid(userInfo.getUserId());
//		// 保存
//		this.countProgramService.addCountProgram(countProgram);
//		backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.COLUMN_ADD, "admin添加了栏目，名称为:" + countProgram.getProgramName());
//		return new MessageBean();
//	}
//
//	/**
//	 * 查看栏目信息
//	 * 
//	 * @param request 
//	 * @return json
//	 */
//	@RequestMapping(value = "/countProgramInfo")
//	@ResponseBody
//	public JSONObject countProgramInfo(HttpServletRequest request) {
//		JSONObject data = new JSONObject();
//		String id = request.getParameter("id");
//		if (id == null || ("").equals(id)) {
//			return data;
//		}
//		// 能过ID查找相关栏目
//		CountProgram countProgram = this.countProgramService.findById(Integer.parseInt(id));
//		if (countProgram != null) {
//			data.put("id", countProgram.getId());
//			data.put("parentProgram", countProgram.getParentProgram());
//			data.put("programName", countProgram.getProgramName());
//			data.put("programUrl", countProgram.getProgramUrl());
//			data.put("remark", countProgram.getRemark());
//			data.put("status", countProgram.getStatus());
//		}
//		return data;
//	}
//
//	/**
//	 * 修改栏目信息
//	 * 
//	 * @param request 
//	 * @param countProgram 
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/editCountProgram")
//	@ResponseBody
//	public MessageBean editCountProgram(HttpServletRequest request,
//			CountProgram countProgram) {
//		if (countProgram != null) {
//			this.countProgramService.updateCountProgram(countProgram);
//			backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.COLUMN_ADD, "admin对栏目名称为:" + countProgram.getProgramName() + "进行修改");
//			return new MessageBean();
//		}
//		return new MessageBean(false, "更新失败");
//	}
//
//	/**
//	 * 删除栏目
//	 * 
//	 * @param request 
//	 * @return 提示信息
//	 */
//	@RequestMapping(value = "/deleteCountProgram")
//	@ResponseBody
//	public MessageBean deleteCountProgram(HttpServletRequest request) {
//		String programId = request.getParameter("id");
//		String parentId = request.getParameter("parentId");
//		if (programId == null || ("").equals(programId)) {
//			return new MessageBean(false, "栏目ID不存在或为空");
//		}
//		// 调用方法进行删除
//		int result = this.countProgramService.deleteCountProgram(
//				Integer.parseInt(programId), Integer.parseInt(parentId));
//		if (result > 0) {
//			backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.COLUMN_ADD, "admin对栏目ID为:" + programId + "进行删除");
//			return new MessageBean(true, "删除栏目成功");
//		} else {
//			backUserLogService.addLog(0, RequestUtils.getUserIp(request), BackUserLogService.COLUMN_ADD, "admin对栏目ID为:" + programId + "进行删除");
//			return new MessageBean(true, "删除失败");
//		}
//	}
//
//}
