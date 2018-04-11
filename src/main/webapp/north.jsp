<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="cn.jugame.service.ISysUserinfoService"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
if(session.getAttribute(GlobalsKeys.ADMIN_KEY)== null){
	return ;
}

ISysUserinfoService isus = SpringFactory.getBean("SysUserinfoService");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
SysUserinfo userinfo = (SysUserinfo) session.getAttribute(GlobalsKeys.ADMIN_KEY);
SysUserinfo userinfo2 = isus.findById(userinfo.getUserId());
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
if (null != userinfo) { 
%>

<script type="text/javascript" charset="utf-8">
document.login_8868_uid = <%=userinfo.getUserId()%>;
document.login_8868_ufullname = "<%=userinfo.getFullname()%>";
document.login_8868_is_visible_weixin = "<%=userinfo.getIsVisibleWeiXin()%>";
</script>
<div>
	广州水煮科技 <font color="red">【登录用户:<%=userinfo.getFullname()%>】昵称：【<%=userinfo.getCustomerNickname()%>】</font>&nbsp;&nbsp;
	
	<%
	if(userinfo.getIsCustomer()==1 || userinfo.getIsObjectCustomer()==1 || userinfo.getIsInvestmentService()==1|| userinfo.getIsInvestmentService()==2|| userinfo.getIsInvestmentService()==10) {
	%> 
	<font id ="kefuStatus"></font>
	<script type="text/javascript" charset="utf-8">
      //window.setInterval("waitforverifycount()",7000);
      window.setInterval("selectOrderCount()",14000);
      window.setInterval("kefuStatus()",24000);
    </script>
	<%
	}else{
		%> 
		<script type="text/javascript" charset="utf-8">
	    //window.setInterval("selectOrderCount()",7000);
	    </script>
	 <%   
	}
		} else {
	%>
	广州水煮科技
	<%
		}
	%>
	
	<script type="text/javascript" charset="utf-8">
		function setDesktopNotice() {
		    window.webkitNotifications.requestPermission();
		}
		
		function updateDesktopNoticeShow() {
		    if(window.webkitNotifications && window.webkitNotifications.checkPermission() == 0) {
		        $('#desktop-notice').text('已开启桌面通知');
		    } else {
		        $('#desktop-notice').text('还未开启桌面通知');
		    }
		}
		
		$(function(){
		    updateDesktopNoticeShow();
		});
	
		function logout(b) {
	
		}
		var changePwdWindow;
		function showChangePwdWindow() {
			changePwdWindow = $('<div class="easyui-layout"/>').window({
				modal : true,
				title : '修改密码',
				width : 350,
				//height : 220,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				resizable : false,
				href : 'changePwd.html',
				onClose : function() {
					$(this).window('destroy');
				}
			});
		}
		//客服待审核商品数量
// 		function waitforverifycount(){
// 			//var product_id_for_pic = $('#id_for_update').val();
// 			$.ajax({
// 				url : 'productverify/productWaitForVerifyShow.do',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(data) {
// 				 $('li .tree-node[node-id="1070"] > .tree-title').html("1070上架审核(客服)<font color=red>("+ data.countnum +")</font>");
// 				}
// 			});
// 		}
		//查询当前用户有多少个未处理的单(AJAX刷新)
		function selectOrderCount(){
// 			$.ajax({
// 				url : 'orderC2c/queryNoSuccessOrderCount',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(response) {
// 					$('li .tree-node[node-id="1069"] > .tree-title').html("1069待处理<font color='red'>("+response.count+")</font>");
// 				}
// 			});
// 			$.ajax({
// 				url : 'orderC2cAccount/queryNoSuccessOrderCount',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(response) {
// 					$('li .tree-node[node-id="1142"] > .tree-title').html("1142待处理订单<font color='red'>("+response.count+")</font>");
// 				}
// 			});
// 			$.ajax({
// 				url : 'orderC2cAccount/queryArbitrateOrderCount',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(response) {
// 					$('li .tree-node[node-id="1146"] > .tree-title').html("1146待仲裁订单<font color='red'>("+response.count+")</font>");
// 				}
// 			});
			
// 			$.ajax({
// 				url : 'orderC2cAccount/showNoSuccessOrderCount?type=1',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(response) {
// 					$('li .tree-node[node-id="1149"] > .tree-title').html("1149客服聊天<font color='red'>("+response.count+")</font>");
// 				}
// 			});
			
// 			$.ajax({
// 				url : 'orderC2cAccount/showNoSuccessOrderCount?type=2',
// 				dataType : 'json',
// 				cache : false,
// 				success : function(response) {
// 					$('li .tree-node[node-id="1158"] > .tree-title').html("1158账号客服聊天<font color='red'>("+response.count+")</font>");
// 				}
// 			});
			$.ajax({
				url : 'api/notify',
				dataType : 'json',
				cache : false,
				success : function(response) {
			        var obj = eval(response);
			      
		            $.each(obj.notify,function(index,element){  
		            	 var modId =element.modId;
		            	 var count  = element.count;
		            	 var title= $('li .tree-node[node-id='+modId+'] > .tree-title').html()||'';
		            	 if(title.indexOf("<") >= 0 )  {
		            		 title = title.substr(0, title.indexOf('<'));
		            	 }
		            	
		                //console.log(modId +"=====" + count +" ==title ===" + title + "====" + title.indexOf('<'));
		                $('li .tree-node[node-id='+modId+'] > .tree-title').html(title+"<font color='red'>("+count+")</font>");
		            })  
				}
			});
			
		}
		
		
		function kefuStatus(){
			$.ajax({
				url : 'webDate/get_kefu_onduty-status.jsp',
				dataType : 'json',
				cache : false,
				success : function(d) {
					//alert(response);
					console.dirxml(d);
					console.log(d.isOnduty);
					var ws = d.workStatus;
					$('#kefuStatus').html(d.isOnduty);
					$("#workStatus option[value='"+ws+"']").attr("selected", 'selected');
				}
			});
		}
	</script>
</div>


<div style="position: absolute; right: 10px; bottom: 0px;">
	<%if(userinfo.getIsCustomer()==1 || userinfo.getIsObjectCustomer()==1 || userinfo.getIsInvestmentService()==1|| userinfo.getIsInvestmentService()==2|| userinfo.getIsInvestmentService()==10) {%>
		工作状态:<select
			id="workStatus"
			name="workStatus"
			onchange="javascript:updateWorkStatus();"
			style="width: 66px">
		</select> 
		<img id="onDuty" onclick="javascript:onDutyF();" alt="上班" src="images/onDuty.png" class="onDuty" > 
		<img id="offDuty" onclick="javascript:offDutyF();" alt="下班" src="images/offDuty.png" style="vertical-align:-7px" > 
	<%}%>
    <span id='desktop-notice' onclick="javascript:setDesktopNotice();" style="text-decoration:underline;cursor:pointer;color:blue;" > 设置桌面通知</span>
		<a
		href="javascript:void(0);"
		class="easyui-menubutton"
		menu="#layout_north_pfMenu"
		iconCls="icon-ok">更换皮肤</a> <a
		href="javascript:showChangePwdWindow();"
		class="easyui-linkbutton"
		data-options="plain:true"
		iconCls="icon-edit">修改密码</a> <a
		href="logout.jsp"
		class="easyui-linkbutton"
		data-options="plain:true"
		iconCls="icon-back">退出</a>
</div>
<div
	id="layout_north_pfMenu"
	style="width: 120px; display: none;">
	<div onclick="sy.changeTheme('default');">default</div>
	<div onclick="sy.changeTheme('gray');">gray</div>
	<div onclick="sy.changeTheme('bootstrap');">bootstrap</div>
	<div onclick="sy.changeTheme('black');">black</div>
	<div onclick="sy.changeTheme('metro');">metro</div>
</div>
<div
	id="layout_north_kzmbMenu"
	style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
</div>
<div
	id="layout_north_zxMenu"
	style="width: 100px; display: none;">
	<div onclick="logout(true);">退出系统</div>
</div>
<%if(userinfo.getIsCustomer()==1 || userinfo.getIsObjectCustomer()==1 || userinfo.getIsInvestmentService()==1|| userinfo.getIsInvestmentService()==2|| userinfo.getIsInvestmentService()==10) {%>
<style>
	.onDuty{
		vertical-align:-7px;
		margin-right:-7px;
		padding-right:0px;
	}
	.offDuty{
		vertical-align:-7px;
	}
</style>
<script type="text/javascript" charset="utf-8">
	function onDutyF() {
		$.messager.confirm("请确认", "现在是您新的一天上班的开始吗？", function(flag) {
			if (flag) {
				$.ajax({
					url : 'personnelManagement/onDuty2.do',
					data : {
						csId: <%=userinfo.getUserId()%>,
						workStatus : <%=userinfo.getOnlineStatus()%>
					},
					dataType : 'json',
					cache : false,
					success : function(response) {
						if(response.success){
							changeOnDuty();
							//window.location.reload();//重新载入变量
							window.setTimeout("window.location.reload();", 1000);
						}
						$.messager.show({
							title : '提示',
							msg : response.msg
						});
					}
				});
			}
		});
	}

	function offDutyF() {
		$.messager.confirm("请确认", "现在是您下班时间吗？", function(flag) {
			if (flag) {
				$.ajax({
					url : 'personnelManagement/offDuty2.do',
					data : {
						csId: <%=userinfo.getUserId()%>,
						workStatus : <%=userinfo.getOnlineStatus()%>
					},
					dataType : 'json',
					cache : false,
					success : function(response) {
						console.log(response)
						if(response.success){
							changeOffDuty();
							$.messager.show({
								title : '提示',
								msg : response.msg
							});
							//window.location.reload();//重新载入变量
							window.setTimeout("window.location.reload();", 1000);
						}else{
							$.messager.alert("错误提示",response.msg);
						}
					}
				});
			}
		});
	}
	
	function updateWorkStatus() {
		var isOnDuty = <%=userinfo2.getIsOnDuty()%>;
		if(isOnDuty > 0){ //上班才可更改
			var selectedStatus =  $('#workStatus option:selected').val();
			$.ajax({
				url : 'personnelManagement/workStatusUpdate2.do',
				data : {
					csId: <%=userinfo.getUserId()%>,
					workStatus : selectedStatus
				},
				dataType : 'json',
				cache : false,
				success : function(response) {
					if(response.success){
						$.messager.show({
							title : '提示',
							msg : response.msg
						});
						console.log(<%=userinfo.getUserId()%>);
						$.ajax({
							url : 'findByCustomerId.do',
							data : {
								customerId: <%=userinfo.getUserId()%>,
							},
						});
					}else{
						$.messager.alert("错误提示",response.msg);
						$("#workStatus option[value='2']").attr("selected", 'selected');
						//window.setTimeout("window.location.reload();", 5000);
						return;
					}
				}
			});
		}
	}
	$(function() {
		var url = "personnelManagement/allCategoryList_json";
		$.getJSON(url, function(result) {
			console.log(result.u_info.onlineStatus);
			var categoryCount = result.total;
			$("select[name='workStatus'] option").remove();
			for ( var i = 0; i < categoryCount; i++) {
				var options = "<option value='"+result.rows[i].id+"'>"+ result.rows[i].name + "</option>";
				$("select[name='workStatus']").append(options);
			}
			var currentStatus =result.u_info.onlineStatus;
			if (currentStatus > 0) {
				$("#workStatus option[value='"+ currentStatus + "']").attr("selected",'selected');
			//updateWorkStatus();
			} else {//1：休息
				$("#workStatus option[value='1']").attr("selected", 'selected');
				updateWorkStatus();
			}
		});
		
		var isOnDuty = <%=userinfo2.getIsOnDuty()%>;
		if(isOnDuty >0){
			changeOnDuty();
		}else{
			changeOffDuty();
		}
	});
	function changeOnDuty(){
		$("#onDuty").attr("src","images/disabledOnDuty.png");
		$("#onDuty").removeAttr("onclick");
		$("#offDuty").attr("src","images/offDuty.png");
		$("#offDuty").attr("onclick","javascript:offDutyF();");
	}
	function changeOffDuty(){
		$("#offDuty").attr("src","images/disabledOffDuty.png");
		$("#offDuty").removeAttr("onclick");
		$("#onDuty").attr("src","images/onDuty.png");
		$("#onDuty").attr("onclick","javascript:onDutyF();");
	}
	</script>
<%}%>

