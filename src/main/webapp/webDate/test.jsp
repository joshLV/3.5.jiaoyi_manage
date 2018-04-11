<%@page import="cn.jugame.web.util.HttpRequestUtil"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="javax.xml.ws.spi.http.HttpContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 

String jsoncall = request.getParameter("qudaoId");
//out.println(jsoncall + "([ {qudaoName:\"John\"}])");
String ss = "中国人名共"+jsoncall;

System.out.println("document.write(\""+ ss+"\")");
out.println("document.write(\""+ss+"\")");

String url = "http://120.31.134.77:18043/validate_service/validate/smscode?appKey=web&code=124577&smsId=SMS_1445015&mobile=13229999501";
String str = HttpRequestUtil.doHttpRequestByGet(url);
out.println(str);

// response.w
// HttpContext context = new HttpContext();
// context.Response.ContentType = "text/plain";
// String callbackFunName = context.Request["callbackparam"];
// context.Response.Write(callbackFunName + "([ { name:\"John\"}])");

%>
