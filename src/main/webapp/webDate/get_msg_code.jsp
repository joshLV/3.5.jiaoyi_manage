<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="cn.jugame.service.ISysUserinfoService"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="cn.jugame.util.Cache"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.dal.callback.JuRowMapper"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="cn.juhaowan.order.vo.RechargeableCard"%>
<%@page import="java.util.List"%>
<%
String phoneorlogid = RequestUtils.getParameter(request, "data", "");
String code = "--";
String tel = "";
if(!StringUtils.isBlank(phoneorlogid)){
if(phoneorlogid.matches("^[0-9]*$")){
	tel = phoneorlogid;
	try{
	code = Cache.get("LOGIN_MSG_" + tel);
	}catch (Exception e){
		e.printStackTrace();
	}
}else{
	ISysUserinfoService us = SpringFactory.getBean("SysUserinfoService");
	JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
	//Cache.set("LOGIN_MSG_" + mobile, 15 * 60, verifyCode);
	SysUserinfo u = us.findByLoginId(phoneorlogid);

	if(null != u){
		tel = u.getTelephone();
		if(!StringUtils.isBlank(tel)){
			try{
				code = Cache.get("LOGIN_MSG_" + tel);
				}catch (Exception e){
					e.printStackTrace();
				}
		}
	}
}


}


%>
<html>
<form action="">
手机号码或登录帐号：<input type="text" name="data" value="<%=phoneorlogid%>" />
<input type="submit">
</form>
<div>

验证码：<%=code%><br>
手机号码：<%=tel%>
</div>
</html>