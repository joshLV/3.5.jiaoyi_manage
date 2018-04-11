package cn.jugame.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cn.jugame.service.ISysRoleService;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysModulePermission;
import cn.jugame.vo.SysRole;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.vo.SysViewRolePermission;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 后台角色管理
 * @author Administrator
 * 
 */
@Controller
public class RoleController {

	Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysModulePermissionService modulePermissionService;

	@Autowired
	private ISysModuleService moduleService;
	
	@Autowired
	private BackUserLogService backUserLogService;

	/**
	 * 角色列表
	 * 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/roleList")
	public String userInfoList(Model model) {

		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);

		return "system/roleList";
	}
    /**
     * 角色信息
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "/system/role_json")
	public String role(HttpServletRequest request, Model model) {
		int roleid = RequestUtils.getParameterInt(request, "roleId", 0);

		SysRole role = sysRoleService.findById(roleid);
		if (role == null){
			return null;
		}

		// 查询角色的权限、模块
		List<SysViewRolePermission> rolePerList = sysRoleService.findRolePermissions(roleid);

		model.addAttribute("role", role);
		model.addAttribute("rolePerList", rolePerList);

		return "system/role_json";
	}

	/**
	 * 角色列表
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/system/roleList_json")
	public String roleList(HttpServletRequest request, Model model) {
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		return "system/roleList_json";
	}

    /**
     * 系统模块、权限列表
     * @param id 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "/system/permission_json")
	public String permission(String id, Model model) {
		if (!StringUtils.isBlank(id)) {
			return "";
		}
		List<SysModule> parentModuleList = moduleService.findParentModule();
		List<SysModule> moduleList = moduleService.findAll();
		List<SysModulePermission> modulePermissions = modulePermissionService.findAll();

		model.addAttribute("parentModuleList", parentModuleList);
		model.addAttribute("moduleList", moduleList);
		model.addAttribute("modulePermissions", modulePermissions);

		return "system/permission_json";
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色信息
	 * @param pers
	 *            角色权限
	 * @param re 
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/role_add")
	@ResponseBody
	public MessageBean roleAdd(SysRole role, String pers,HttpServletRequest re) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return new MessageBean(false, "角色名称不能为空");
		}
		if (StringUtils.isBlank(role.getRoleCode())) {
			return new MessageBean(false, "角色代码不能为空");
		}

		int result = sysRoleService.insert(role);
		if (result == 2) {
			return new MessageBean(false, "角色名称已存在");
		}
		if (result == 1) {
			return new MessageBean(false, "角色代码已存在");
		}

		int i = sysRoleService.updateRolePermission(role.getRoleId(), pers);
		//记录日志
		if(i == 0){
			SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
			backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), 
					BackUserLogService.ROLE_ADD, "管理员:" + u.getFullname() + "添加新角色:" + role.getRoleName());

			
		}

		return new MessageBean();
	}
    /**
     * 角色信息编辑 分配角色权限
     * @param role 
     * @param pers 
     * @param re 
     * @return 提示信息
     */
	@RequestMapping(value = "/system/role_edit")
	@ResponseBody
	public MessageBean roleEdit(SysRole role, String pers,HttpServletRequest re) {
		if (StringUtils.isBlank(role.getRoleName())) {
			return new MessageBean(false, "角色名称不能为空");
		}
		if (StringUtils.isBlank(role.getRoleCode())) {
			return new MessageBean(false, "角色代码不能为空");
		}

		SysRole role2 = sysRoleService.findById(role.getRoleId());
		if (role2 == null) {
			return new MessageBean(false, "找不到相关角色信息");
		}
		int oldStatus = role2.getStatus();
		int newStatus = role.getStatus();
	
		String statusName = "";
		if (newStatus == 1) {
			statusName = "有效";
		} else {
			statusName = "无效";
		}
		boolean flag = false;
		if(oldStatus != newStatus){
			flag = true;
		}

		role2.setStatus(role.getStatus());
		role2.setAllowUpdate(role.getAllowUpdate());
		role2.setRoleDesc(role.getRoleDesc());
		role2.setIsCustomerR(role.getIsCustomerR());

		sysRoleService.update(role2);
		SysUserinfo u = (SysUserinfo) re.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(re), 
				BackUserLogService.ROLE_UPDATE, "管理员:" + u.getFullname() + "更新了角色信息 角色名:" + role.getRoleName());
		int i = sysRoleService.updateRolePermission(role.getRoleId(), pers);

		//记录日志
		if(i == 0){
			if (flag) {
				backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(re),
						BackUserLogService.ROLE_UPDATE_STATUS,
						"管理员:" + u.getFullname() + "修改：" + role.getRoleName() + " 的状态为 :" + statusName);
			}
				backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(re),
						BackUserLogService.ROLE_SET_PERMISSION,
						"管理员:" + u.getFullname() + "更新了角色权限 角色名：" + role.getRoleName());
			
		}
		return new MessageBean();
	}
    /**
     * 角色信息删除
     * @param request 
     * @return 提示信息
     */
	@RequestMapping(value = "/system/role_delete")
	@ResponseBody
	public MessageBean roleDelete(HttpServletRequest request) {
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
				sysRoleService.delete(Integer.parseInt(idArr[i]));
				SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
				backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(request), 
						BackUserLogService.ROLE_DELETE, "管理员:" + u.getFullname() + "删除了角色信息 角色ID:" + idArr[i]);
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		return new MessageBean();
	}

}
