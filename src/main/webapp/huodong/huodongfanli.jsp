<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动返利</title>
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.2/themes/default/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.2/themes/icon.css">  
    <script type="text/javascript" src="../jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>  
    <script type="text/javascript" src="../jquery-easyui-1.3.2/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script> 
    <script type="text/javascript">
		$(document).ready(function(){
			$('#beginTime').datetimebox({
				required : true,
				validType : 'md[\'\']'
			});
			$('#endTime').datetimebox({
				validType : 'md[\'\']'
			});
		});
		function getTableInfo(){
			var total = $("#total").val();
			var beginTime = $('#beginTime').datetimebox('getValue');
			var endTime = $('#endTime').datetimebox('getValue');
			var percent = $("#percent").val();
			var delUserStr = $("#delUserStr").val();
			var maxReturn = $("#maxReturn").val();
			if(total==null||total==''){
				alert("请输入活动金额");
			}else if(beginTime==null||beginTime==''){
				alert("请选择开始时间");
			}else if(percent==null||percent==''){
				alert("请输入返利比例");
			}else{
				$.ajax({
					url : 'huodongfanli_json.jsp',
					data : {
						total : total,
						beginTime : beginTime,
						endTime : endTime,
						percent :percent,
						delUserStr : delUserStr,
						maxReturn :maxReturn
					},
					dataType : 'json',
					cache : false,
					success : function(response) {
						if(response.code == 0){
							//填充表格
							fillSource(response);
						}else{
							alert(response.msg);
							$("#sourceDiv").html("");
						}
					}
				});
			}
		}
		function fillSource(response){
			$("#sourceDiv").html("");
			var html ='<font color="red">此次活动总金额:'+response.total+"，活动剩余额:"+response.left+"</font><br />"
			html += '<table style="border: 1px;width: 40%" >';
			html += '<tr><td>用户ID</td><td>金额总数</td><td>返利订单数</td><td>返利总金额</td><td>用户手机</td></tr>';
	  		$.each(response.data,function(i,item)
	  		{
	  			html += '<tr><td>'+item.uid+'</td><td>'+item.totalMoney+'</td><td>'+item.count+'</td><td>'+item.returnMoney+'</td><td>'+item.mobile+'</td></tr>';
	  		});
	  		html += '</table>';
	  		$("#sourceDiv").html(html);
		}
    </script>
</head>
<body>
<div id="conditionDiv">
	<form id="condition" style="padding: 5px 10px">
		活动金额:<input type="text" name="total" value="" id="total" />
		开始时间:<input id="beginTime" type="text" name="beginTime" style="width: 150px">结束时间:<input id="endTime" type="text" name="endTime" style="width: 150px">
		返利比例:<input type="text" name="percent" value="" id="percent" /><font color="red">(请输入数字，如百分之三，则输入3)</font><br />
		返利上限:<input type="text" name="maxReturn" value="" id="maxReturn" /> 剔除用户:<input type="text" name="delUserStr" value="" style="width: 400px" id="delUserStr" /><font color="red">填写规则：用户ID之间用,号隔开如123,345</font>
		<input type="button" value="提交" onclick="getTableInfo();"/>
	</form>
</div>
<div id="sourceDiv">
</div>
</body>
</html>