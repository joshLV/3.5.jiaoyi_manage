<%@page import="cn.jugame.util.GamePasswdUtil"%>
<%@page import="cn.juhaowan.interfaces.game.vo.GameUserInfo"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.util.RequestUtils"%>
<%@page import="cn.juhaowan.order.vo.OrdersC2c"%>
<%@page import="cn.juhaowan.order.service.OrdersC2cService"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%!Logger log = LoggerFactory.getLogger("query_c2c_account_pwd_jsp");%>
<%

String orderId = RequestUtils.getParameter(request, "oid", "");
if(!StringUtils.isBlank(orderId)){

OrdersC2cService c2cS = SpringFactory.getBean("OrdersC2cService");
OrdersC2c c2c = c2cS.queryById(orderId);

String baccount = "";
String bpwd = "";
String bsafe = "";

String saccount ="";
String spwd ="";
String ssafe ="";

String sp = ""; 
String ss = "";
if(null != c2c ){
	saccount = c2c.getSelluserGameAccount()==null?"":c2c.getSelluserGameAccount();
	spwd = c2c.getSelluserGameAccountPwd()==null?"":c2c.getSelluserGameAccountPwd();
	ssafe = c2c.getSelluserGameSafekey()==null?"":c2c.getSelluserGameSafekey();
	if(!spwd.equals("")){
	    sp =  GamePasswdUtil.decode(GamePasswdUtil.decode(spwd));
	}
	if(!"".equals(ssafe)){
		ss =  GamePasswdUtil.decode(GamePasswdUtil.decode(ssafe));
	}
}
out.println("卖家帐号："+saccount +"<br>卖家密码："+sp +"<br>卖家安全锁："+ss);

}	
%>


<html>
<body>

<form action="" >
<table>
<tr>
<td>
订单id:
</td>
<td>
<input name="oid" />
</td>
</tr>

<tr>
<td><input type="submit" name="submit" value="提交"/></td>
<td></td>
</tr>
</table>

</form>

</body>


</html>