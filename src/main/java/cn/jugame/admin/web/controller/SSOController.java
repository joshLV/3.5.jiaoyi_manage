package cn.jugame.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jugame.service.ISysModuleService;
import cn.jugame.util.Cache;
import cn.jugame.util.MD5;
import cn.jugame.vo.SysModule;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.GlobalsKeys;



/**
 * 单点登录
 * @author Administrator
 *
 */
@Controller
public class SSOController {
	Logger logger = LoggerFactory.getLogger(SSOController.class);

	@Autowired
	private ISysModuleService iSysModuleService;


	/**
	 * 列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/SSOAuth/SSOAuth")
	public String sSOAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
        String modid = request.getParameter("mod_id");
        SysModule mo = iSysModuleService.findById(Integer.parseInt(modid));
        if(null == mo){
        	return "redirect:/nopermission.html";
        }
		SysUserinfo u = (SysUserinfo)request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		//mamcache key
		String str = String.valueOf(u.getUserId()) + "_" + "sso_uid" + System.currentTimeMillis();
		//生成token  保存到mamcache 120秒
		String ticket = MD5.encode(str);
		Cache.add("sso/" + ticket, 15000, u.getUserId());
		String link = mo.getFirstPage();
		if(link.contains("?")){
			link = link += "&ticket=" + ticket;
		}else{
			link = link += "?ticket=" + ticket;
		}
		return "redirect:" + link;
	}

	

	/**
	 * 列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/SSOAuth/SSOAuthTest")
	public String SSOAuthTest(HttpServletRequest request, HttpServletResponse response, Model model) {
		String key   = request.getParameter("key");
		String link = "http://192.168.0.55:5200/CommonAction.do?link=funcId=" + key + "&action=list&pageType=grid&showFlag=true&filterFlag=true&pageSize=50";
//        String modid = request.getParameter("mod_id");
//        SysModule mo = iSysModuleService.findById(Integer.parseInt(modid));
//        if(null == mo){
//        	return "redirect:/nopermission.html";
//        }
//		SysUserinfo u = (SysUserinfo)request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		//mamcache key
		String str = String.valueOf("1000_sso_uid" + System.currentTimeMillis());
		//生成token  保存到mamcache 120秒
		String ticket = MD5.encode(str);
		Cache.add("sso/" + ticket, 15000, 1000);
//		String link = mo.getFirstPage();
		if(link.contains("?")){
			link = link += "&ticket=" + ticket;
		}else{
			link = link += "?ticket=" + ticket;
		}
		return "redirect:" + link;
	}
	

}
