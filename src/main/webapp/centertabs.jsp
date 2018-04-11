<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="cn.jugame.vo.SysUserinfo" %>
<%@page import="cn.jugame.vo.SysViewRoleModule"%>
<%@page import="java.util.List"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
SysUserinfo u = (SysUserinfo) session.getAttribute("admin");
int hasChat = 0;
if(u.getUsertype() != 0){
List<SysViewRoleModule> modList = (List<SysViewRoleModule>)session.getAttribute(GlobalsKeys.ADMIN_MODULE);
for(int i =0;i <modList.size();i++){
	if("webDate/app_kefu_chat.jsp".equals(modList.get(i).getFirstPage())){
		hasChat = 1;
		break;
	}
}
}
%>


<script type="text/javascript" charset="utf-8">
	var centerTabs;
	$(function() {
		centerTabs = $('#centerTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
        if(<%=hasChat%> == 1){
        	appkefuLogin();
        }
		
	});

	function refreshTab(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}

	function closeTab(title) {
		if (centerTabs.tabs('exists', title)) {
			centerTabs.tabs('close', title)
		}

	}

	function addTab(node) {
		var closeflag = true;
		var freshitem = "node.iconCls";
		console.log("--"+node.text);
		if(node.text =="app聊天"){
			closeflag = false;
		}
		if (centerTabs.tabs('exists', node.text)) {
			//if(node.text!="app聊天"){
				refreshTab(node.text); 
				var closeflag = false;
				var freshitem = "";
			//}
			
			centerTabs.tabs('select', node.text);

		} else {
			if (node.attributes.url && node.attributes.url.length > 0) {
				centerTabs
						.tabs(
								'add',
								{
									title : node.text,
									closable : closeflag,
									iconCls : node.iconCls,
									content : '<iframe src="'
											+ node.attributes.url
											+ '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
									tools : [ {
										iconCls : 'icon-mini-refresh',
										handler : function() {
											refreshTab(node.text);
										}
									} ]
								/**/
								});
			} else {
				centerTabs
						.tabs(
								'add',
								{
									title : node.text,
									closable : closeflag,
									iconCls : node.iconCls,
									content : '<iframe src="error/err.jsp" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>'/*,
														tools : [ {
															iconCls : 'icon-mini-refresh',
															handler : function() {
																refreshTab(node.text);
															}
														} ]*/
								});
			}
		}
	}
	
	//APP聊天登录
	function appkefuLogin(){
	var link = "webDate/app_kefu_chat.jsp";
	var node1 = {
			"attributes":{ "url":link}, 
			"checked":false,
			"iconCls":"",
			"state":"closed",
			"text":"app聊天" 
			};
	addTab(node1);	
	}
</script>

<div id="centerTabs" class="easyui-tabs" data-options="border:false">
	<div title="首页" border="false" href="_content.html" style="overflow: hidden; padding: 5px;"></div>
</div>
