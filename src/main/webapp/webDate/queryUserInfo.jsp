<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.juhaowan.member.vo.Member"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.juhaowan.member.service.MemberService"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.util.MD5"%>
<%@page import="java.net.URLDecoder"%>
<%
	JSONObject json = new JSONObject();
	try {
		String uid = request.getParameter("uid");
		//加密key
		String encodeKey = request.getParameter("encodeKey");
		String md5key = MD5.encode(uid + "sereblwerijwerie");
		MemberService ms = SpringFactory.getBean("MemberService");
		if (null == uid || null == encodeKey) {
			json.put("result", "1");
			json.put("msg", "参数为空");
			out.println(json);
			System.out.println(json);
			return;
		}

		if (!md5key.toLowerCase().equals(encodeKey.toLowerCase())) {
			json.put("result", "1");
			json.put("msg", "加密验证不匹配");
			out.println(json);
			System.out.println(json);
			return;
		}

		//List<Orders> o = oService.queryOrderBySellUid(75, "2013-06-05 17:18:01", "2013-6-06 17:18:01");
		Member m = ms.findMemberByUid_back(Integer.parseInt(uid));
		if (null != m) {
			json.put("result", "0");
			json.put("msg", "用户存在");
			out.println(json);
			System.out.println(json);
			return;
		}
		json.put("result", "1");
		json.put("msg", "用户不存在");
		out.println(json.toString());
		System.out.println(json);

	} catch (Exception e) {
		json.put("result", "1");
		json.put("msg", "出现异常");
		out.println(json);
		System.out.println("-----------userinfo--");
		e.printStackTrace(System.out);

	}
%>