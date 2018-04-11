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
import cn.jugame.service.ISysModulePermissionService;
import cn.jugame.service.ISysModuleService;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysModulePermission;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 权限模块
 * 
 * @author Administrator
 * 
 */
@Controller
public class ModuleController {
	Logger logger = LoggerFactory.getLogger(ModuleController.class);
	@Autowired
	private ISysModuleService sysModuleService;
	@Autowired
	private ISysModulePermissionService sysModulePermissionService;
	@Autowired
	private BackUserLogService backUserLogService;

	/**
	 * 模块列表
	 * 
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/moduleList")
	public String moduleList(Model model) {
		List<SysModule> parentModule = this.sysModuleService.findParentModule();
		model.addAttribute("parentModule", parentModule);
		return "system/moduleList";
	}

	/**
	 * 模块列表数据
	 * 
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/moduleList_json")
	public String moduleListJson(Model model) {
		List<SysModule> moduleList = sysModuleService.findAll();
		model.addAttribute("moduleList", moduleList);
		return "system/moduleList_json";
	}

	/**
	 * 删除模块
	 * 
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/moduleDelte")
	@ResponseBody
	public MessageBean moduleDelete(HttpServletRequest request) {
		String moduleId = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		if (moduleId == null || ("").equals(moduleId)) {
			return new MessageBean(false, "模块ID不存在或为空");
		}
		// 进行操作
		int result = this.sysModuleService.delete(Integer.parseInt(moduleId),Integer.parseInt(parentId));
		// 记录日志
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (result > 0) {
			this.backUserLogService.addLog(userInfo.getUserId(),RequestUtils.getUserIp(request),
					BackUserLogService.MODULE_DELETE, "模块删除：删除模块ID为" + moduleId + "所属父模块" + parentId);
			return new MessageBean(true, "删除成功");
		} else {
			return new MessageBean(false, "删除失败");
		}
	}

	/**
	 * 添加模块
	 * 
	 * @param request
	 * @param sysModule
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/moudelAdd")
	@ResponseBody
	public MessageBean moudelAdd(HttpServletRequest request, SysModule sysModule) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (sysModule != null) {
			// 进行添加
			this.sysModuleService.save(sysModule);
			this.backUserLogService.addLog(userInfo.getUserId(),RequestUtils.getUserIp(request),
					BackUserLogService.MODULE_ADD,"模块添加：添加模块名为:" + sysModule.getModuleName());
			return new MessageBean(true, "添加成功");
		} else {
			return new MessageBean(false, "添加失败，保存对像为空");
		}

	}

	/**
	 * 编辑信息，读取具体模块信息
	 * 
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/system/moduleInfo")
	@ResponseBody
	public JSONObject moduleInfo(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		List<SysModule> parentModule = this.sysModuleService.findParentModule();
		data.put("parentModule", parentModule);
		String modId = request.getParameter("modId");
		if (modId != null && !("").equals(modId)) {
			SysModule sysModule = this.sysModuleService.findById(Integer.parseInt(modId));
			if (sysModule != null) {
				data.put("cssName", sysModule.getCssName());
				data.put("firstPage", sysModule.getFirstPage());
				data.put("isMenu", sysModule.getIsMenu());
				data.put("levelSeq", sysModule.getLevelSeq());
				data.put("modId", sysModule.getModId());
				data.put("moduleCode", sysModule.getModuleCode());
				data.put("moduleName", sysModule.getModuleName());
				data.put("orderNo", sysModule.getOrderNo());
				data.put("parentId", sysModule.getParentId());
				data.put("remark", sysModule.getRemark());
				data.put("status", sysModule.getStatus());
				data.put("target", sysModule.getTarget());
				data.put("isExternal", sysModule.getIsExternal());
				//data.put("linkTo", sysModule.getLinkTo());
			}
		}
		return data;
	}

	/**
	 * 修改模块信息
	 * 
	 * @param sysModule
	 * @param request
	 * @return 提示
	 */
	@RequestMapping(value = "/system/moduleEdit")
	@ResponseBody
	public MessageBean moduleEdit(HttpServletRequest request,SysModule sysModule) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (sysModule != null) {
			this.sysModuleService.update(sysModule);
			// 进行添加
			this.backUserLogService.addLog(userInfo.getUserId(),RequestUtils.getUserIp(request),
					BackUserLogService.MODULE_UPDATE,"模块更新：,模块id" + sysModule.getOrderNo());
			return new MessageBean(true, "更新成功");
		} else {
			return new MessageBean(false, "更新失败");
		}

	}

	/**
	 * 根据模块ID查询该模块下的权限
	 * 
	 * @param request
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/modulePermissionList")
	public String modulePermissionList(HttpServletRequest request, Model model) {
		String modId = request.getParameter("modId");
		if (modId != null && !("").equals(modId)) {
			model.addAttribute("modId", modId);
		}
		return "system/modulePermissionList";
	}

	/**
	 * 获取JSON串的值
	 * 
	 * @param request
	 * @param model
	 * @return json
	 */
	@RequestMapping(value = "/system/modulePermissionList_json")
	@ResponseBody
	public JSONObject modulePermissionListJson(HttpServletRequest request,Model model) {
		String modId = request.getParameter("modId");
		JSONObject data = new JSONObject();
		if (modId != null && !("").equals(modId)) {
			// 根据模块ID查找当前模块下的所有权限
			List showList = new ArrayList();
			List<SysModulePermission> list = this.sysModulePermissionService.findByModuleId(Integer.parseInt(modId));
			if (list != null && list.size() > 0) {
				int total = list.size();
				data.put("total", total);
				for (int i = 0; i < total; i++) {
					SysModulePermission sysModulePermission = list.get(i);
					Map map = new HashMap();
					map.put("pId", sysModulePermission.getPid());
					map.put("modId", sysModulePermission.getModId());
					map.put("perCode", sysModulePermission.getPerCode());
					map.put("perName", sysModulePermission.getPerName());
					map.put("pid", sysModulePermission.getPid());
					map.put("remark", sysModulePermission.getRemark());
					showList.add(map);
				}
			}
			JSONArray rows = JSONArray.fromObject(showList);
			data.put("rows", rows);
			data.put("modId", modId);
		}
		return data;
	}

	/**
	 * 删除模块权限
	 * 
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/modulePermissionDel")
	@ResponseBody
	public MessageBean modulePermissionDel(HttpServletRequest request) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		String ids = request.getParameter("ids");
		logger.info("del =" + ids);
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}
		String[] idArr = ids.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("pid为：");
		for (int i = 0; i < idArr.length; i++) {
			try {
				this.sysModulePermissionService.delModulePermission(Integer.parseInt(idArr[i]));
				// 进行添加日志
				this.backUserLogService.addLog(userInfo.getUserId(),RequestUtils.getUserIp(request),
						BackUserLogService.MODULE_DELETE_PERMISSION,"模块权限删除：,模块id:" + idArr[i]);
				sb.append(idArr[i] + ",");
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		sb.append("被删除!");
		return new MessageBean(true, sb.toString());
	}

	/**
	 * 给相关模块添加权限
	 * 
	 * @param sysModulePermission
	 * @param request
	 * @return 提示
	 */
	@RequestMapping(value = "/system/modulePermissionAdd")
	@ResponseBody
	public MessageBean modulePermissionAdd(HttpServletRequest request,SysModulePermission sysModulePermission) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (sysModulePermission != null) {
			if (StringUtils.isBlank(sysModulePermission.getPerCode())) {
				return new MessageBean(false, "权限代码不能为空");
			}
			if (StringUtils.isBlank(sysModulePermission.getPerName())) {
				return new MessageBean(false, "权限名不能为空");
			}
			// 进行保存
			this.sysModulePermissionService.save(sysModulePermission);
			this.backUserLogService.addLog(userInfo.getUserId(),	RequestUtils.getUserIp(request),
					BackUserLogService.MODULE_ADD_PERMISSION, "模块权限添加：模块ID为" + sysModulePermission.getModId() + "权限添加" + "权限名:"
					+ sysModulePermission.getPerName() + ",权限代码:" + sysModulePermission.getPerCode() + ",结果: 操作成功");
		} else {
			return new MessageBean(false, "保存的实体为空或不存在");
		}
		return new MessageBean();
	}

	/**
	 * 更新权限模块信息
	 * 
	 * @param sysModule
	 * @param request
	 * @return 提示
	 */
	@RequestMapping(value = "/system/modulePermissionEdit")
	@ResponseBody
	public MessageBean modulePermissionEdit(HttpServletRequest request,SysModule sysModule) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		String perCode = RequestUtils.getParameter(request, "perCode", "");
		int pid = RequestUtils.getParameterInt(request, "pid", 0);

		SysModulePermission sp = sysModulePermissionService.findById(pid);
		if (sp != null) {
			sp.setPerCode(perCode);
			sysModulePermissionService.save(sp);
			// 进行修改
			this.backUserLogService.addLog(userInfo.getUserId(),RequestUtils.getUserIp(request),
					BackUserLogService.MODULE_UPDATE_PERMISSION,"权限模块更新：,结果:更新成功");
			return new MessageBean(true, "更新成功");
		} else {
			return new MessageBean(false, "更新失败");
		}

	}
}
