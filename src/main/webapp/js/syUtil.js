var sy = $.extend({}, sy);/* 定义全局对象，类似于命名空间或包的作用 */

/**
 * 获得URL参数 * 
 * @returns 对应名称的值
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/** 
 * 将form表单元素的值序列化成对象 
 * @returns object
 */
sy.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * @requires jQuery,jQuery cookie plugin * 
 * 更换EasySSH主题的方法
 * 
 * @param themeName
 *            主题名称
 */
sy.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			try{
				var ifr = $iframe[i];
				url = $(ifr).contents().find('#easyuiTheme').attr('href');
				href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}catch(e){}
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7,
		path: "/"
		
	});
};
//从cookie中读取上次的主题
$(function(){ 
	var easyuiThemeName =$.cookie('easyuiThemeName');
	//alert(easyuiThemeName);
	if (easyuiThemeName){
		sy.changeTheme(easyuiThemeName);
	}
});
	
/**
 * 扩展validatebox，验证两次密码是否一致功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	equals : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

/**
 * 扩展validatebox，验证最小输入字符
 */
$.extend($.fn.validatebox.defaults.rules, {  
    minLength: {  
        validator: function(value, param){  
            return value.length >= param[0];  
        },  
        message: '请最少输入 {0} 个字符.'  
    }  
}); 

/**
 * 扩展validatebox，验证最多输入字符
 */
$.extend($.fn.validatebox.defaults.rules, {  
    maxLength: {  
        validator: function(value, param){  
            return !(value.length >= param[0]);  
        },  
        message: '最多只能输入 {0} 个字符.'  
    }  
}); 










function dateFormatYMD(date){
	if(date!=null&&date.length>0){
		date = date.substring(0,10);
	}
	return date;
}