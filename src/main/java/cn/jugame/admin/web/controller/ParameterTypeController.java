package cn.jugame.admin.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
/**
 * 系统参数分类
 * @author Administrator
 *
 */
@Controller
public class ParameterTypeController {
	@Autowired
	private ISysParameterService parameterService;
	
	@Autowired
	private ISysParameterTypeService parameterTypeService;
/**
 * 系统参数类别
 * @param model 
 * @return 跳转路径
 */
	@RequestMapping(value = "/system/parameterTypeList")
	public String sysParameterTypeList(Model model) {

		return "system/parameterTypeList";
	}
/**
 * 系统参数类别列表数据
 * @param request 
 * @param model 
 * @return 跳转路径
 */
	@RequestMapping(value = "/system/parameterTypeList_json")
	public String sysParameterTypeListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 20); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "id"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
																		

		SysParameter obj = new SysParameter();
		obj.setParaCode(request.getParameter("paraCode"));
		obj.setParaValue(request.getParameter("paraValue"));
		obj.setDscr(request.getParameter("dscr"));

		PageInfo<SysParameterType> pageInfo = parameterTypeService.queryParameterTypeList(null, 
				pageSize, pageNo, sort, order);
		model.addAttribute("pageInfo", pageInfo);

		return "system/parameterTypeList_json";
	}

	/**
	 * 添加系统参数类别
	 * @param par 参数类型
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/parameterType_add")
	@ResponseBody
	public MessageBean parameterTypeAdd(SysParameterType par) {

		if (null == par) {
			return new MessageBean(false, "请填写参数类型");
		}
		if (null == par.getParaType()) {
			return new MessageBean(false, "请填写参数类型名称");
		}
		if (null == par.getDscr()) {
			return new MessageBean(false, "请填写参数类型描述");
		}
		par.setCreateDate(new Date());
		parameterTypeService.save(par);
		// 操作成功
		return new MessageBean();
	}
/**
 * 系统参数类型信息
 * @param request 
 * @param model 
 * @return 系统参数类型
 */
	@RequestMapping(value = "/system/parameterType_json")
	@ResponseBody
	public SysParameterType parameterTypeJson(HttpServletRequest request,Model model) {
		int id = RequestUtils.getParameterInt(request, "id", 0);

		SysParameterType par = parameterTypeService.findById(id);
		if (par == null){
			return null;
		}
		return par;
	}

	/**
	 * 修改参数
	 * @param par 系统参数
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/parameterType_edit")
	@ResponseBody
	public MessageBean parameterTypeEdit(SysParameterType par) {

		if (par.getId() < 0) {
			return new MessageBean(false, "无此参数");
		}
		SysParameterType parameter = parameterTypeService.findById(par.getId());
		if (null == parameter) {
			return new MessageBean(false, "无此参数");
		}

		parameter.setParaType(par.getParaType());
		parameter.setDscr(par.getDscr());
		parameter.setParaType(par.getParaType());
		parameterTypeService.update(parameter);
		return new MessageBean();
	}

	/**
	 * 删除
	 * @param request 
	 * @return 提示信息
	 */
	@RequestMapping(value = "/system/parameterType_delete")
	@ResponseBody
	public MessageBean parameterTypeDelete(HttpServletRequest request) {
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
				SysParameterType sysParameter = parameterTypeService.findById(Integer.parseInt(idArr[i]));
				parameterTypeService.delete(sysParameter);
			} catch (Exception e) {
				continue;
			}
		}

		return new MessageBean(true, "删除成功");
	}
}
