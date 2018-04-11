package cn.jugame.admin.web.controller;

import java.util.Date;
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
import cn.jugame.service.ISysParameterService;
import cn.jugame.service.ISysParameterTypeService;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysParameter;
import cn.jugame.vo.SysParameterType;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.log.service.BackUserLogService;
/**
 * 系统参数
 * @author Administrator
 *
 */
@Controller
public class ParameterController {
	Logger logger = LoggerFactory.getLogger(ParameterController.class);
	@Autowired
	private ISysParameterService parameterService;
	
	@Autowired
	private ISysParameterTypeService parameterTypeServiceService;
	
	@Autowired
	private BackUserLogService backUserLogService;
	
	
/**
 * 参数列表
 * @param model 
 * @return 跳转路径
 */
	@RequestMapping(value = "/system/parameterList")
	public String sysParameterList(Model model) {

		List<SysParameterType> typeList = parameterTypeServiceService.list();
		model.addAttribute("typeList", typeList);
		return "system/parameterList";
	}
/**
 * 参数列表数据
 * @param request 
 * @param model 
 * @return 跳转路径
 */
	@RequestMapping(value = "/system/parameterList_json")
	public String sysParameterListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "id"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
																		

		SysParameter obj = new SysParameter();
		obj.setParaCode(request.getParameter("paraCode"));
		obj.setParaValue(request.getParameter("paraValue"));
		obj.setDscr(request.getParameter("dscr"));

		PageInfo<SysParameter> pageInfo = parameterService.queryParameterList(obj, pageSize, pageNo, sort, order);
		model.addAttribute("pageInfo", pageInfo);
		if(null != pageInfo && null != pageInfo.getItems()){
			for(int i = 0;i < pageInfo.getItems().size();i++){
				if(null != pageInfo.getItems().get(i).getParaValue()){
				String parValue = "";
				parValue  = cn.jugame.web.util.StringUtils.stringFilterNormal(pageInfo.getItems().get(i).getParaValue());
				pageInfo.getItems().get(i).setParaValue(parValue);
				}
				if(null != pageInfo.getItems().get(i).getDscr()){
				String dscr = "";
				dscr = cn.jugame.web.util.StringUtils.stringFilterNormal(pageInfo.getItems().get(i).getDscr());
				pageInfo.getItems().get(i).setDscr(dscr);
				}
			}
		}

		return "system/parameterList_json";
	}

	/**
	 * 添加系统参数
	 * @param par 系统参数
	 * @return 提示
	 */
	@RequestMapping(value = "/system/parameter_add")
	@ResponseBody
	public MessageBean parameterAdd(SysParameter par,HttpServletRequest request) {
		if(par.getParaType() == null){
			return new MessageBean(false,"请填写参数类型");
		}
		if (null == par.getDscr()) {
			return new MessageBean(false, "请填写参数说明");
		}
		if (null == par.getParaCode()) {
			return new MessageBean(false, "请填写参数代码");
		}
		if (null == par.getParaValue()) {
			return new MessageBean(false, "请填写参数值");
		}

		//重复参数名
		List<SysParameter> list = parameterService.listByParaCode(par.getParaCode());
		if(null != list && list.size() > 0) {
			return new MessageBean(false,"参数代码已存在");
		}
		
		par.setCreateTime(new Date());
		//String dscr = par.getDscr();
		//parValue = cn.jugame.web.util.StringUtils.stringFilterNormal(parValue);
		//dscr = cn.jugame.web.util.StringUtils.stringFilterNormal(dscr);
		par.setAllowUpdate(1);
		//par.setParaType("SYS_PARAM");
		//par.setParaValue(par.getParaValue());
		//par.setDscr(par.getDscr());
		parameterService.save(par);

//		Map<String, String> map = BackUserLogUtil.findUserInfo(request);
//		backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
//				map.get(BackUserLogUtil.USER_IP), BackUserLogService.SYS_ARGS_ADD,
//				"【添加系统参数】管理员：" + map.get(BackUserLogUtil.USER_NAME) + "参数代码:" + par.getParaCode());
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
		backUserLogService.addLog(u.getUserId(),
				RequestUtils.getUserIp(request),
				backUserLogService.SYS_ARGS_ADD, u.getFullname()
						+ "系统参数更新  参数名：" + par.getParaCode());
		// 操作成功
		return new MessageBean();
	}
/**
 * 系统参数详细信息
 * @param request 
 * @param model 
 * @return 系统参数
 */
	@RequestMapping(value = "/system/parameter_json")
	@ResponseBody
	public SysParameter parameterJson(HttpServletRequest request,Model model) {
		int id = RequestUtils.getParameterInt(request, "id", 0);

		SysParameter par = parameterService.findById(id);
		if (par == null){
			return null;
		}
        model.addAttribute("type_code", par.getParaType());
        
		return par;
	}

	/**
	 * 修改参数
	 * @param par 
	 * @return 提示
	 */
	@RequestMapping(value = "/system/parameter_edit")
	@ResponseBody
	public MessageBean parameterEdit(SysParameter par,HttpServletRequest request) {

		if (par.getId() < 0) {
			return new MessageBean(false, "无此参数");
		}
		SysParameter parameter = parameterService.findById(par.getId());
		if (null == parameter) {
			return new MessageBean(false, "无此参数");
		}
		
		//重复参数名
		List<SysParameter> list = parameterService.listByParaCode(par.getParaCode());
		if(null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++){
				if(list.get(i).getId() != par.getId()){
					return new MessageBean(false,"参数代码已存在");
				}
			}
			
		}
			

		parameter.setParaCode(par.getParaCode());
		//String dscr = par.getDscr();
		//dscr  = cn.jugame.web.util.StringUtils.stringFilterNormal(dscr);
		//parameter.setAllowUpdate(par.getAllowUpdate());
		parameter.setDscr(par.getDscr());
		parameter.setParaValue(par.getParaValue());
		parameter.setParaType(par.getParaType());
		parameterService.update(parameter);
//		
//		Map<String, String> map = BackUserLogUtil.findUserInfo(request);
//		backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
//				map.get(BackUserLogUtil.USER_IP), BackUserLogService.SYS_ARGS_ADD,
//				"【修改系统参数】管理员：" + map.get(BackUserLogUtil.USER_NAME) + "参数代码:" + par.getParaCode());
		
		SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
		backUserLogService.addLog(u.getUserId(),
				RequestUtils.getUserIp(request),
				backUserLogService.SYS_ARGS_UPDATE, u.getFullname()
						+ "系统参数更新  ID为：" + parameter.getId() + "参数名：" + parameter.getParaCode());
		return new MessageBean();
	}

	/**
	 * 删除
	 * 
	 * @param request 
	 * @return 提示
	 */
	@RequestMapping(value = "/system/parameter_delete")
	@ResponseBody
	public MessageBean parameterDelete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}

		String[] idArr = ids.split(",");
		if (idArr.length > 5) {
			return new MessageBean(false, "最多只能同时删除5条记录");
		}

		for (int i = 0; i < idArr.length; i++) {

			try {
				SysParameter sysParameter = parameterService.findById(Integer.parseInt(idArr[i]));
				parameterService.delete(sysParameter);
//				Map<String, String> map = BackUserLogUtil.findUserInfo(request);
//				backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
//						map.get(BackUserLogUtil.USER_IP), BackUserLogService.SYS_ARGS_ADD,
//						"【删除系统参数】管理员：" + map.get(BackUserLogUtil.USER_NAME) + "参数代码:" + sysParameter.getParaCode());
				SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
				backUserLogService.addLog(u.getUserId(),
						RequestUtils.getUserIp(request),
						backUserLogService.SYS_ARGS_DELETE, u.getFullname()
								+ "删除了系统参数ID为：" + idArr[i] + "参数名：" + sysParameter.getParaCode());
			} catch (Exception e) {
				continue;
			}
		}

		return new MessageBean(true, "删除成功");
	}
}
