//订单的公用js
(function() {
	var global = this;

	var OrderHelper = {};

	//数据初始化--交易模式
	var orderModel = [];
	orderModel["1"] = "C2C寄售";
	orderModel["2"] = "API";
	orderModel["3"] = "其他";
	orderModel["10"] = "首充";
	orderModel["20"] = "退游";
	orderModel["30"] = "换游";
	orderModel["99"] = "差价";
	orderModel["101"] = "淘宝兑换码";
	orderModel["111"] = "IOS(7881)";
	orderModel["112"] = "IOS(8868)";
	orderModel["119"] = "第三方寄售";
	OrderHelper.orderModel = orderModel;

	//初始化-end
	var requestHost = "/"; // 网站根目录
	if (global.requestHost){
		requestHost = global.requestHost;
	};
	OrderHelper.requestHost = requestHost;
	/**
	 * 订单销售模式
 	 *
	 */	
	OrderHelper.getOrderModelName = function(orderModel){
		var modelName = OrderHelper.orderModel[orderModel];
		var color = "#000";
		if (orderModel == 10){
			color = "#ff0000";
		}
		if (orderModel == 20){
			color = "#006400";
		}
		if (orderModel == 30){
			color = "#000080";
		}
		if (modelName){
			return "<font color='"+color+"'>" +modelName + "</font>";
		}
		return "--";
	}
	OrderHelper.getOrderModelOption = function(){
		var str = "";
		var orderModel = this.orderModel;
		for (var i = 0; i < orderModel.length; i++) {
			if (orderModel[i]){
				str += "<option value='" +i+ "'>" + orderModel[i]+ "</option>"
			}
		};
		return str;
	}
	/**
	 * 显示订单操作日志
	 */
	OrderHelper.showOrderOperatorLog = function(orderId){
		var link = this.requestHost + "order/followOrder/"+orderId;
		try{
			var node = {
					"attributes":{ "url":link}, 
					"checked":false,
					"iconCls":"",
					"state":"closed",
					"text":"订单"+orderId+"操作详情"
					};
			top.addTab(node);
		}catch(e){
			window.open(link);
		}
	}

	/**
	 * 订单状态
	 */
	OrderHelper.getOrderStatusName = function(orderStatus) {
		if (orderStatus == 0) {
			return "<font color='#ff0000'>等待付款</font>";
		} else if (orderStatus == 2) {
			return "等待发货";
		} else if (orderStatus == 4) {
			return "待确认收货";
		} else if (orderStatus == 6) {
			return "交易成功";
		} else if (orderStatus == 7) {
			return "待转账";
		} else if (orderStatus == 8) {
			return "<font color='#666'>交易取消</font>";
		} else if (orderStatus == 10) {
			return "<font color='#666'>己退费</font>";
		} else if (orderStatus == -1) {
			return "<font color='#ff0000'>己删除</font>";
		}
	};
	OrderHelper.randomString = function(len) {
		　　len = len || 8;
		　　var chars = 'abcdefhijkmnprstwxyz2345678';    
		   /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
		　　var maxPos = chars.length;
		　　var pwd = '';
		　　for (i = 0; i < len; i++) {
		　　　　pwd += chars.charAt(Math.floor(Math.random() * maxPos));
		　　}
		　　return pwd;
	};
	global.OrderHelper = OrderHelper;
})();