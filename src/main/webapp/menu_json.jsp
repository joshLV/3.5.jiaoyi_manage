<%@page import="cn.jugame.web.util.RequestUtils"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.vo.SysViewRoleModule"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@page import="java.util.List"%>
<% 
SysUserinfo userinfo = (SysUserinfo)session.getAttribute(GlobalsKeys.ADMIN_KEY); 
if(userinfo==null) return ;
//用户类型 0.管理员，1普通管理员
int userType = userinfo.getUsertype();

List<SysViewRoleModule> modList = (List<SysViewRoleModule>)session.getAttribute(GlobalsKeys.ADMIN_MODULE);



int id = RequestUtils.getParameterInt(request, "id", 0);
%>
[
<% 
int idx = 0;
for(int i=0;i<modList.size();i++){  
	SysViewRoleModule module = modList.get(i); 
	//System.out.println(module.getModuleName());
	if(module.getParentId() == id){
		if (module.getIsMenu()==0) continue;
		if (idx > 0) out.println(",");
		idx ++ ;	
	
%>
{"attributes":{
<%if(module.getIsExternal() == 1){ %>
"url":"SSOAuth/SSOAuth?mod_id=<%= module.getModId()%>"},
<% }else{ %>
"url":"<%= module.getFirstPage()%>"},
<%} %>
"checked":false,"iconCls":"",
"id":"<%= module.getModId()%>",
"state":"<%=(module.getParentId()==0?"closed":"open") %>",
"text":"<%= module.getModuleName()%>"
}
<% 
	}
} %>
 ]