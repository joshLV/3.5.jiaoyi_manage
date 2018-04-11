define(function(require, exports){
    exports.build_url = function(controller){
        return String.format("${request.contextPath}/{0}", controller);
    }

    //------------------------------------------------
    function http_request(url, param_obj, succFun, failFun){
        Ext.Ajax.request({
            url: url, 
            params: param_obj,
            method : "POST",
            success: function(resp, opts){
                var obj = Ext.decode(resp.responseText);
                if(obj.success){
                    if(succFun) succFun(obj);
                }else{
                    if(failFun) failFun(obj);
                    else{
                        show_msg(obj.msg);
                    }
                }
            },
            failure: function(resp, otps){
                show_msg('server-side failure with status code ' + response.status);
            }
        });
    }

    function multi_http_request(callback/*, req1, req2, ...*/){
        var result_arr = [];
        var len = arguments.length - 1;
        for(var i=1; i<arguments.length; ++i){
            var req = arguments[i];
            http_request(req.url, req.param, function(result){
                result_arr.push(result);

                //当result_arr.length跟请求数一样的时候，认为全部请求完成
                //FIXME 仅考虑每个req必然返回的情况
                if(result_arr.length == len){
                    callback(result_arr);
                }
            })
        }
    }

    function multi_store_load(callback/*, store1, store2, ...*/){
        var result_arr = [];
        var len = arguments.length - 1;
        var onLoad = function(sto, recs, opts){
            //加载完卸载该监听器
            sto.un("load", onLoad);

            //FIXME 仅考虑每个store加载必然返回的情况
            result_arr.push(recs);
            if(result_arr.length == len){
                callback(result_arr);
            }
        };

        for(var i=1; i<arguments.length; ++i){
            var sto = arguments[i];
            sto.on("load", onLoad);
            sto.load();
        }
    }

    exports.http_request = http_request;
    exports.multi_http_request = multi_http_request;
    exports.multi_store_load = multi_store_load;
    //---------------------------------------------

    //--------------------------------------------
    //一些通用方法
    function show_msg(msg){
        Ext.Msg.alert("提示", msg);
    }

    function show_confirm(msg, ok_func, no_func, cancel_func){
        Ext.Msg.show({
            title:'提示',
            msg: msg,
            buttons: Ext.Msg.YESNOCANCEL,
            fn: function(rtn){
                if(rtn == "yes"){
                    if(ok_func) ok_func();
                }
                if(rtn == "no"){
                    if(no_func) no_func();
                }
                if(rtn == "cancel"){
                    if(cancel_func) cancel_func();
                }
            },
            icon: Ext.MessageBox.QUESTION
        });
    }

    function easy_confirm(msg, ok_func, no_func){
        Ext.Msg.confirm("提示", msg, function(rtn){
            console.log(rtn);
            if(rtn == "yes"){
                if(ok_func) ok_func();
            }
            if(rtn == "no"){
                if(no_func) no_func();
            }
        });
    }

    function mask(msg){
        Ext.getCmp("main-panel").getEl().mask(msg);
    }

    function unmask(){
        Ext.getCmp("main-panel").getEl().unmask();
    }

    exports.show_msg = show_msg;
    exports.show_confirm = show_confirm;
    exports.mask = mask;
    exports.unmask = unmask;

    //---------------------------------------------
    //对于seajs的一些封装
    function load_resource(url, callback){
        seajs.use(url, callback);
    }

    exports.load_resource = load_resource;

    //---------------------------------------------
    function htmlEncode(str) {
        var div = document.createElement("div");
        div.appendChild(document.createTextNode(str));
        return div.innerHTML;
    }
    function htmlDecode(str) {
        var div = document.createElement("div");
        div.innerHTML = str;
        return div.innerHTML;
    }

    exports.htmlEncode = htmlEncode;
    exports.htmlDecode = htmlDecode;


    //----------------------------------------------
    //cookie的操作
    function setCookie(key, val){
        Ext.util.Cookies.set(key, val);
    }

    function getCookie(key){
        return Ext.util.Cookies.get(key);
    }

    function clearCookie(key){
        Ext.util.Cookies.clear(key);
    }

    exports.setCookie = setCookie;
    exports.getCookie = getCookie;
    exports.clearCookie = clearCookie;


    //为gridpanel的cell创建button
    function createGridBtn(cfg){
        var btnId = Ext.id();
        var btn = (function(){
            return new Ext.Button(cfg).render(document.body, btnId);
        }).defer(1, this);

        return btnId;
    }   
    exports.createGridBtn = createGridBtn;

    //解析如 xx=xx&xx=xx的格式数据
    function parseStr(str){
        if(!str || str.length == 0)
            return [];

        var r = {};
        var e_arr = str.split("&");
        for(var i=0; i<e_arr.length; ++i){
            var e = e_arr[i].split("=");
            if(e.length == 0){
                continue;
            }else if(e.length == 1){
                r[e[0]] = "";
            }else{
                r[e[0]] = e[1];
            }
        }

        return r;
    }
    exports.parseStr = parseStr;

    //添加panel到main-panel中
    exports.show_panel = function(id, create_func){
        var panel = Ext.getCmp('main-panel');
        var tab = panel.getComponent(id);
        if (tab) {
            panel.setActiveTab(tab);
            return;
        }   
        var subPanel = create_func(panel, id);
        panel.setActiveTab(panel.add(subPanel));
    }

    exports.get_record_value = function(record, name, defaultVal){
        if(!defaultVal){
            defaultVal = "";
        }
        return record ? record.get(name) : defaultVal;
    }


    //-------------------------------------------------------------------------------------------
    //数字转汉字
    var _number_change = {
        ary0:["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
        ary1:["", "十", "百", "千"],
        ary2:["", "万", "亿", "兆"],
        init:function (name) {
            this.name = name;
        },
        strrev:function () {
            var ary = []
            for (var i = this.name.length; i >= 0; i--) {
                ary.push(this.name[i])
            }
            return ary.join("");
        }, //倒转字符串。
        pri_ary:function () {
            var $this = this
            var ary = this.strrev();
            var zero = ""
            var newary = ""
            var i4 = -1
            for (var i = 0; i < ary.length; i++) {
                if (i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
                    i4++;
                    newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
                    zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

                }
                //关于0的处理与判断。
                if (ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
                    switch (i % 4) {
                        case 0:
                            break;
                        //如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
                        case 1:
                        case 2:
                        case 3:
                            if (ary[i - 1] != '0') {
                                zero = "零"
                            }
                            ; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
                            break;

                    }

                    newary = zero + newary;
                    zero = '';
                }
                else { //如果不是“0”
                    newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
                }

            }
            if (newary.indexOf("零") == 0) {
                newary = newary.substr(1)
            }//处理前面的0
            return newary;
        }
    }

    //创建class类
    function number_change() {
        this.init.apply(this, arguments);
    }
    number_change.prototype = _number_change

    exports.number_change = number_change;

    //-------------------------------------------------------------------------------------------
    exports.scroll_bottom = function(panel){
        var d = panel.body.dom;
        d.scrollTop = d.scrollHeight - d.offsetHeight;
    }

    exports.getComboDisplay = function(combo) {
        var value = combo.getValue();
        var valueField = combo.valueField;
        var record;
        combo.getStore().each(function(r){
            if(r.data[valueField] == value){
                record = r;
                return false;
            }
        });
        return record ? record.get(combo.displayField) : null;
    }

    //--------------------------------------------------------------------------------------------
    var idx = 1;
    exports.increase_number = function(){
        return idx++;
    }

    exports.parseInt = function(str){
        var r = parseInt(str);
        if(r)
            return r;
        //NaN和0都返回0
        return 0;
    }

    //--------------------------------------------------------------------------------------------
    var cache = {}; 
    exports.set_data = function(key, data){
        cache[key] = data;
    }   
    exports.get_data = function(key, erase){
        if(cache[key] != undefined){
            var value = cache[key];
            if(erase){
                delete cache[key];
            }   
            return value;
        }   
        return null;
    }   
    exports.delete_data = function(key){
        if(cache[key] != undefined){
            delete cache[key];
        }   
    }
});
