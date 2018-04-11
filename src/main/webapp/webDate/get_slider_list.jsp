<%@page import="cn.juhaowan.banner.vo.PtBannerImages"%>
<%@page import="cn.juhaowan.banner.vo.PtBanner"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.jdbc.support.rowset.SqlRowSet"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.juhaowan.banner.service.BannerService"%>
<%@page import="cn.juhaowan.banner.service.BannerImagesService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%!Logger log = LoggerFactory.getLogger("get_slider_list_jsp");%>
<%
	JSONObject json = new JSONObject();
	JSONArray jsonArray = null;
	String tag = RequestUtils.getParameter(request, "tag", "");
	if(StringUtils.isBlank(tag)){
		json.put("code",-1);
		json.put("msg", "参数为空");
		json.put("data", "");
		out.println(json);
		return;
	}
	
	
	try {
		BannerService bs = SpringFactory.getBean("BannerService");
		BannerImagesService bimg = SpringFactory.getBean("BannerImagesService");
		
		PtBanner b = bs.queryListByTag(tag);
		List<Map<String, Object>> list = bimg.queryListByTag(tag);
		jsonArray = JSONArray.fromObject(list);
		json.put("code",0);
		json.put("msg", "查询成功");
		json.put("data", jsonArray);
		out.println(json);
	} catch (Exception e) {
		e.printStackTrace();
		json.put("code",-2);
		json.put("msg", "接口异常");
		out.println(json);
	}

	
%>
