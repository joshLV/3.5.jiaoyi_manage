package cn.jugame.admin.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jugame.util.RequestUtils;

/**
 * 用户获取静态资源的Controller，并在spring-servlet中配置了对JS文件的freemarker解析
 * @author zimT_T
 *
 */
@Controller
public class StaticResourceController{

	@RequestMapping(value = "/static")
	public String getStaticFile(HttpServletRequest request, Model model){
		String file = RequestUtils.getParameter(request, "file", "").trim();
		file = file.replace('.', '/');
		
		Enumeration<String> enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String key = enu.nextElement();
			model.addAttribute(key, request.getParameter(key));
		}
		
		//就是本controller在freemarker中表现出来的地址
		model.addAttribute("static_file", request.getContextPath() + "/static?file");
		model.addAttribute("icon_path", request.getContextPath() + "/extjs/icons");
		return file;
	}
}
