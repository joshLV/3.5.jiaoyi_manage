package cn.jugame.admin.web.controller;

import org.springframework.ui.Model;

public abstract class ExtjsBaseController {
	protected String page(String title, String entry_js, Model model){
		model.addAttribute("title", title);
		model.addAttribute("entry_js", entry_js);
		model.addAttribute("param", model.asMap());
		return "extjs/index";
	}
}
