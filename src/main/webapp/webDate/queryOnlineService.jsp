<%@page import="cn.juhaowan.customer.service.CustomerServiceDutyService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.HashMap"%>
<%
	CustomerServiceDutyService customerServiceDutyService = SpringFactory
			.getBean("CustomerServiceDutyService");
	JSONObject json = customerServiceDutyService
			.queryAllOnlineServiceList();
	out.println(json.toString());
%>