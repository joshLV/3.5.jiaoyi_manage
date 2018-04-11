 /**
 * 根据jPages改写的分页插件
 * 
 * 添加服务器端分页
 */

;(function($, window, document) {
	function updateInstances(){//定时更新实例化的对象列表，不存在的则删除
		for(var key in window[globalAttrName].instances){
			if($("#"+key).length == 0){
				delete window[globalAttrName].instances[key];
			}
		}
	}
	function generRandomCharacters(characterLength){//用于生成id
		characterLength = characterLength || 10;
		var chars = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		             'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
		var randomCharacters = "";
	    for(var i = 0; i < characterLength ; i ++) {
	        var index = Math.floor(Math.random()*(chars.length));
	        randomCharacters += chars[index];
	    }
	    return randomCharacters;
	}
	var globalAttrName = "";
	while(true){
		globalAttrName = generRandomCharacters();
		if(window[globalAttrName] == undefined){
			break;
		}
	}
	window[globalAttrName] = {instances:{}};//所有的实例化对象(该全局对象中保存当前已实例化的分页对象)
	setInterval(updateInstances,5000);//1000为1秒钟
	
	var defaults = {
			containerID: "",
			pageNumber: 1,//起始页码
	        pageSize: 10,//每页展示的记录数
	        realPagination:true,//是否启用服务器端分页，如果是服务器端分页，则每次页码发生变化时都会到服务器请求数据
	        showPage:{
	        	first:'&nbsp;',//首页文字显示，如果为空字符串则隐藏
				previous:'&nbsp;',//前一页文字显示，如果为空字符串则隐藏
				next:'&nbsp;',//下一页文字显示，如果为空字符串则隐藏
				last:'&nbsp;'//最后一页文字显示，如果为空字符串则隐藏
	        },
	        showRange:{
	        	start: 2,//起始页码显示的个数
	        	mid: 3,//中间页码显示的个数
		        end: 2//最后页码显示的个数
	        }
//	        ,serverParams:{//该参数作为ajax的参数来请求服务器数据，如果配置了该参数则会向服务器请求数据
//	        	type: "POST"
//	        }
		};
	function Plugin(element, options){
		this.options = $.extend({}, defaults, options);
		this._container = $("#" + this.options.containerID);
		if (!this._container.length) return;//不存在该html对象
		this._holder = $(element);
		
		//分页对象在创建后会发生改变的变量全部定义为对象的属性
		this._pageSize = this.options.pageSize;//每页显示的记录条数
		this._totalPage = Math.ceil(this._container.children().length / this._pageSize);//总页数
		
		if(this.options.serverParams){
			this._reload = true;
			this._pageNumber = this.options.pageNumber;//当前页码
			this._ajaxData = $.extend({},this.options.serverParams.data);//数据请求的参数
		}else{
			this._pageNumber = this.options.pageNumber<=this._totalPage?this.options.pageNumber:this._totalPage;//当前页码
		}
		
		this._showRowStart = (this._pageNumber-1)*this._pageSize;
		this._showRowEnd = this._pageNumber*this._pageSize-1;
		this.loadData();
	}
	Plugin.prototype = {
		constructor : Plugin,
		loadData:function(){//加载数据（配置了数据请求源）
			var thisObj = this;
			var options = thisObj.options;
			if(options.serverParams != undefined && (options.realPagination || this._reload)){//配置了请求服务器数据的ajax参数
				var ajaxParamsOption = options.serverParams;
				var ajaxData = $.extend({},thisObj._ajaxData);
				if(this.options.realPagination){//如果是服务器端分页，在请求参数中添加分页信息
					$.extend(ajaxData, {pageSize:thisObj._pageSize,pageNum:thisObj._pageNumber});
				}
				var ajaxParams =  $.extend({},ajaxParamsOption);//复制配置参数作为ajax请求的参数，避免对配置参数中的数据产生影响
				delete ajaxParams.data;//下面对参数做处理，避免传递数组数据时出现问题
				$.extend(ajaxParams,{type: "POST",dataType : 'json',cache : false},ajaxParams,{data:$.param(ajaxData,true)},{
					success: function(result) {
//						result = $.parseJSON(result);
						//数据格式为服务器端分页格式或者页面分页格式（这里的返回值有可能是其他数据，如登录超时返回的登录页面或者其他权限拦截后的数据）
						if((result.list != undefined && result.totalPage != undefined) || $.isArray(result)){
							var thisData = $.extend({"startRowNum":((thisObj._pageNumber-1)*(thisObj._pageSize) || 0)+1},ajaxData,result);
							var dataList = options.realPagination?result.list:result;
							ajaxParamsOption.success(dataList,thisData);
							if(options.realPagination){
								thisObj._totalPage = result.totalPage;
								thisObj._showRowStart = 0;
								thisObj._showRowEnd = thisObj._pageSize-1;
								//如果请求的页码大于总页码，则再去请求最后一页的数据，使用场景：用户在删除最后一页的全部数据后，刷新列表会自动回到前一页
								if(thisObj._totalPage > 0 && thisObj._pageNumber > thisObj._totalPage){
									thisObj.paginate(thisObj._totalPage);
									return;
								}
							}else{
								thisObj._totalPage = Math.ceil(thisObj._container.children().length / thisObj._pageSize);//总页数
								thisObj._showRowStart = (thisObj._pageNumber-1)*thisObj._pageSize;
								thisObj._showRowEnd = thisObj._showRowStart+thisObj._pageSize-1;
								thisObj._reload = false;
							}
							thisObj.init();
						}
			        }
				});
				$.ajax(ajaxParams);
			}else{
				this._showRowStart = (this._pageNumber-1)*this._pageSize;
				this._showRowEnd = this._pageNumber*this._pageSize-1;
				this.init();
			}
		},
		init:function(){
			this.setNav();
			this.setData();
		},
		setNav:function(){
			this.writeNav();
			this._holder.find("a").not(".jp-current,.jp-disabled").click(this.bind(function(evt) {
				var gotoPageNumber = this._pageNumber;
				if($(evt.target).hasClass("jp-first")){
					gotoPageNumber = 1;
				}else if($(evt.target).hasClass("jp-previous")){
					gotoPageNumber = gotoPageNumber-1;
				}else if($(evt.target).hasClass("jp-next")){
					gotoPageNumber = gotoPageNumber+1;
				}else if($(evt.target).hasClass("jp-last")){
					gotoPageNumber = this._totalPage;
				}else{
					if($.isNumeric($(evt.target).data("pageNum"))){
						gotoPageNumber = parseInt($(evt.target).data("pageNum"));
					}
				}
				this.paginate(gotoPageNumber);
		        evt.preventDefault();
		      }, this));
		},
		bind : function(fn, me) {
			return function() {
				return fn.apply(me, arguments);
			};
		},
		writeNav:function(){//仅生成可见的页码html，原jPages会生成所有的页码，隐藏不需要显示的，这种做法当页数很多时会出问题
			var navhtml = this.writeBtn("first") + this.writeBtn("previous");
			var showPageNumber = [];
			for(var i=1;i<=this.options.showRange.start;i++){//查询起始位置显示的页码
				if(i<=this._totalPage){
					showPageNumber.push(i);
				}
			}
			if(this.options.showRange.mid > 0){//查询中间位置显示的页码
				var midStartNumber = this._pageNumber - (this.options.showRange.mid%2==0?this.options.showRange.mid-2:this.options.showRange.mid-1)/2;
				var midEndNumber = midStartNumber+this.options.showRange.mid-1;
				midStartNumber = midStartNumber<1?1:midStartNumber;
				midEndNumber = midEndNumber<1?1:midEndNumber;
				for(var i=midStartNumber;i<=midEndNumber;i++){
					if(i<=this._totalPage && $.inArray(i,showPageNumber)==-1){
						showPageNumber.push(i);
					}
				}
			}
			for(var i=this._totalPage-this.options.showRange.end+1;i<=this._totalPage;i++){//查询最后位置显示的页码
				if(i>0 && $.inArray(i,showPageNumber)==-1){
					showPageNumber.push(i);
				}
			}
			showPageNumber.sort(function(a,b){return a>b?1:-1});
			var priviousPageNum = 0;
			for(var i=0;i<showPageNumber.length;i++){
				if(showPageNumber[i] > priviousPageNum+1){//不连续的页码前加省略号
					navhtml += "<span>...</span>";
				}
				priviousPageNum = showPageNumber[i];
				var classStr = "";
				if(showPageNumber[i] == this._pageNumber){
					classStr = 'class="jp-current"';
				}
				navhtml += "<a "+classStr+" data-page-num='"+showPageNumber[i]+"'>"+showPageNumber[i]+"</a>";
			}
			if(priviousPageNum < this._totalPage){
				navhtml += "<span>...</span>";
			}
			navhtml += this.writeBtn("next") + this.writeBtn("last");
			this._holder.html(navhtml);
		},
		writeBtn:function(which){//按照配置生成首页、前一页、后一页、尾页的html，如果未配置则返回空
			var thisClass = "";
			if(which == "first"){
				thisClass = " fa fa-angle-double-left";
				if(this._pageNumber == 1){
					thisClass += " jp-disabled";
				}
			}else if(which == "previous"){
				thisClass = " fa fa-angle-left";
				if(this._pageNumber == 1){
					thisClass += " jp-disabled";
				}
			}else if(which == "next"){
				thisClass = " fa fa-angle-right";
				if(this._pageNumber == this._totalPage){
					thisClass += " jp-disabled";
				}
			}else if(which == "last"){
				thisClass = " fa fa-angle-double-right";
				if(this._pageNumber == this._totalPage){
					thisClass += " jp-disabled";
				}
			}
			return this.options.showPage[which]!=''?"<a class='jp-" + which + " " +thisClass+ "'>" + this.options.showPage[which] + "</a>" : "";
		},
		setData:function(){
			var dataList = this._container.children();
			dataList.hide();
			for(var i=this._showRowStart;i<=this._showRowEnd;i++){
				dataList.eq(i).show();
			}
		},
		paginate:function(pageNum){
			if(this.validPageNumber(pageNum)){
				this._pageNumber = pageNum;
				this.loadData();
			}
		},
		validPageNumber:function(pageNumber){
			if(this.options.realPagination){//真分页查询是此处不能设置页码最大的限制，因为有可能页码会发生变化
				return $.isNumeric(pageNumber) && pageNumber > 0;
			}else{
				return $.isNumeric(pageNumber) && pageNumber > 0 && pageNumber <= this._totalPage;
			}
		},
		destroy:function(){
			this._container.children().show();
			this._holder.empty();
			delete window[globalAttrName].instances[this._holder.attr("id")];
		}
	}
	$.fn.Pager = function(arg,ajaxData){
		if(this.length){
			var $this = $(this).eq(0);
			var type = $.type(arg);
			var thisId = $this.attr("id");
			if(type === "object"){//创建新的分页对象
				if(thisId == undefined){
					while(true){
						thisId = generRandomCharacters();
						if($("#"+thisId).length == 0){
							break;
						}
					}
					$this.attr("id",thisId);
				}
				var thisInstance = new Plugin($this[0], arg);
				window[globalAttrName].instances[thisId] = thisInstance;
			}else{//调用之前对象中的方法
				var thisInstance = window[globalAttrName].instances[thisId];
				if(type === "string" && arg === "exist"){
					if(thisInstance == undefined){
						return false;
					}else{
						return true;
					}
				}else if(thisInstance != undefined){//当前对象已初始化为分页对象
					if(type === "string"){
				    	if(arg === "destroy"){
				    		thisInstance.destroy();
				    	}else if(arg === "lastPage"){
				    		thisInstance.paginate(thisInstance._totalPage);
				    	}else if(arg === "refresh"){
				    		thisInstance._reload = true;
				    		thisInstance.paginate(thisInstance._pageNumber);
				    	}else if(arg === "search"){//重置搜索条件，查询新数据，用于带条件的分页查询，条件动态改变
				    		thisInstance._ajaxData = $.extend({},ajaxData);//数据请求的参数
				    		thisInstance._reload = true;
				    		thisInstance.paginate(1);
				    	}
				    	return $this[0];
					}else if(type === 'number' && arg % 1 === 0){
				    	thisInstance.paginate(arg);
				    	return $this[0];
				    }else{
				    	alert("分页参数不存在！");
				    }
				}else{
					alert("该对象未执行分页初始化！");
				}
			}
		}else{
			alert("分页对象不存在！");
		}
	}
})(jQuery, window, document);
