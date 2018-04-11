<%@page import="org.springframework.web.multipart.MaxUploadSizeExceededException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    String message = (String) request.getAttribute("javax.servlet.error.message");
    String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
    String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
    Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
    Exception ex = (Exception)request.getAttribute("exception"); 
     
    String queryString = request.getQueryString();
    String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
    url = url.replaceAll("&amp;", "&").replaceAll("&", "&amp;");

    if(t != null) {
        LOGGER.error("error", t);
    }
%>
<html>
    <head>
        <title>页面错误</title>
    </head>
    <body>
    <%	 
	    if (ex != null && ex instanceof MaxUploadSizeExceededException){
	    	out.print("对不起，超过了最大上传限制，请联系管理员<br/>");
	    }else {
            out.println("对不起,您访问的页面出了一点内部小问题,请<a href=\"" + url + "\">刷新一下</a>重新访问,或者先去别的页面转转,过会再来吧~<br/><br/>");
        }
    %>
    <a href="javascript:void(0);" onclick="history.go(-1)">返回刚才页面</a><br/><br/>
    </body>
</html>
<%!
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("DAILY_ALL");
%>