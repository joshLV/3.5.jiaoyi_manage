define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var gvw = require("${static_file}=newgame.GameVersionWindow");
    var gac = require("${static_file}=newgame.GameAreaGroupPanel");
    var xsm = require("${static_file}=extjs.com.SelectionModelRectify");
    var gbi = require("${static_file}=newgame.GameBaseInfoPanel");
    var gai = require("${static_file}=newgame.GameAreaInfoPanel");
    var gpt = require("${static_file}=newgame.GameProductTypePanel");
    var tw = require("${static_file}=newgame.TemplateWindow");

    var GameEditPanel = Ext.extend(Ext.Panel, {
        __make_id: function(name){
            return "GameEditPanel:" + name;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

        constructor : function(game_id){
            var me = this;

            this.game_id = game_id==undefined?0:game_id;
            //写入全局变量，方便调用
            if(this.game_id){
                utils.set_data("game_id", this.game_id);
            }

            this.base_panel = new gbi.GameBaseInfoPanel();

            this.area_panel = new gai.GameAreaInfoPanel({collapsed: this.game_id!=0});
            this.area_panel.get_game_name = function(){
                return me.base_panel.get_game_name();
            }
            this.area_panel.refresh_info = function(panel){
                var data = me.area_panel.get_data();

                //游戏版本数量
                var ver_num = data.length;

                //区服分组数量
                var grp_num = 0;
                var channel_num = 0;
                var area_num = 0;
                for(var i=0; i<data.length; ++i){
                    grp_num += data[i].area_grps.length;
                    channel_num += data[i].channels.length;

                    for(var j=0; j<data[i].area_grps.length; ++j){
                        area_num += data[i].area_grps[j].records.length;
                    }
                }

                var s = "";
                if(ver_num > 0){
                    s += String.format("{0}个版本", ver_num);
                }
                if(grp_num > 0){
                    s += String.format("{0}个分组", grp_num);
                }
                if(channel_num > 0){
                    s += String.format("{0}个渠道", channel_num);
                }
                if(area_num != 0){
                    s += String.format("{0}个区服", area_num);
                }
                if(s != ""){
                    me.__get_cmp("fieldset:area_panel").setTitle(String.format("游戏区服信息【已添加{0}】", s));
                }else{
                    me.__get_cmp("fieldset:area_panel").setTitle("游戏区服信息");
                }
            }

            this.product_panel = new gpt.GameProductTypePanel({collapsed: this.game_id!=0}, this.game_id);
            this.product_panel.get_product_channels = function(){
                var area_info = me.area_panel.get_data();
                var channels = [];
                for(var i=0; i<area_info.length; ++i){
                    channels = channels.concat(area_info[i].channels);
                }
                return channels;
            }
            this.product_panel.refresh_info = function(){
                var data = me.product_panel.get_data();
                if(data.length != 0){
                    me.__get_cmp("fieldset:product_panel").setTitle(String.format("商品类型信息【已添加{0}个类型】", data.length));
                }else{
                    me.__get_cmp("fieldset:product_panel").setTitle("商品类型信息");
                }
            }

            var tbar = [
                {xtype: "button", text: "提交", icon: "${icon_path}/accept.png", style: "border: solid 1px gray; background-color: #ccc;", height: 40, width: 100, handler: function(){
                    if(me.is_valid()){
                        var data = me.get_data();
                        var str = Ext.util.JSON.encode(data);
                        //logo需要单独一个字段
                        var logo_file = data.base_info.logo;

                        //XXX 使用html5的功能
                        var form = new FormData();
                        form.append("data", str);
                        form.append("logo", logo_file);

                        //先蒙起来
                        var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"保存中，这可能需要一点时间..."});
                        myMask.show();

                        var xmlhttp = new XMLHttpRequest();
                        xmlhttp.open("POST", utils.build_url("game/save_game"), true);
                        xmlhttp.onload = function(e){
                            //完成请求后重新去除蒙板
                            myMask.hide();

                            var resp = Ext.util.JSON.decode(e.currentTarget.response);
                            //失败了
                            if(!resp.success){
                                utils.show_msg(resp.msg);
                                return;
                            }

                            utils.show_msg("保存游戏成功");

                            //将所有界面置空，允许继续添加游戏，如果是修改游戏就保持界面不动
                            if(!me.game_id || me.game_id <= 0){
                                me.base_panel.reset();
                                me.area_panel.reset();
                                me.product_panel.reset();
                            }
                        };
                        xmlhttp.send(form);
                    }
                }}
            ];
            //如果是编辑，则允许模板管理
            if(me.game_id){
                tbar = tbar.concat([
                    '-',
                    {xtype: "button", text: "模板管理", icon: "${icon_path}/application_xp.png", style: "border: solid 1px gray;", width: 100, height: 40, handler: function(){
                        var win = new tw.TemplateWindow(me.game_id);
                        win.onApply = function(tmpl_id){
                            me.product_panel.sync_tmpl(tmpl_id);
                        }
                        win.show();
                    }},
                    '-',
                    "选择模板：",
                    {id: me.__make_id("sel_tmpl"), xtype: "combo", width: 150, triggerAction: 'all', height: 40, editable: false, typeAhead : true, editable: false, model:"remote", displayField: "name", valueField: "id",
                    store: new Ext.data.JsonStore({
                        url: utils.build_url("tmpl/get_tmpls"),
                        root: "data",
                        fields: [
                            {name: "id"}, {name: "name"}
                        ],
                        listeners: {
                            beforeload: function(s){
                                s.baseParams.game_id = me.game_id;
                            }
                        }
                    }), listeners: {
                        beforequery: function(qe){
                            delete qe.combo.lastQuery;
                        },
                        render: function(qe){
                            qe.store.load();
                        },
                    }},
                    {xtype: "button", text: "应用到商品分类", width: 100, height: 40, style: "border: solid 1px gray;", icon: "${icon_path}/application_form_edit.png", handler: function(){
                        var tmpl_id = me.__get_cmp("sel_tmpl").getValue();
                        if(tmpl_id == ""){
                            utils.show_msg("请先选择模板再应用到商品分类");
                            return;
                        }

                        var sels = me.product_panel.getSelectionModel().getSelections();
                        if(sels.length == 0){
                            utils.show_msg("请先选择商品分类后再应用模板");
                            return;
                        }
                        
                        utils.show_confirm("确定将模板应用到当前选中的商品分类?", function(){
                            me.product_panel.apply_tmpl(tmpl_id);
                        });
                    }}
                ]);
            }
            GameEditPanel.superclass.constructor.call(this, {
                id : this.__make_id(game_id),
                border: false,
                layout: "fit",
                autoScroll: true,
                items: [
                    {xtype: "panel", border: false, layout: "anchor", autoScroll: true, items: [
                        {id: me.__make_id("fieldset:base_panel"), xtype: "fieldset", anchor: "95%", autoHeight: true, title: "游戏基础信息", items: [this.base_panel]},
                        {id: me.__make_id("fieldset:area_panel"), xtype: "fieldset", anchor: "95%", title: "游戏区服信息", autoHeight: true, items: [this.area_panel]},
                        {id: me.__make_id("fieldset:product_panel"), xtype: "fieldset", anchor: "95%", title: "商品类型信息", items: [this.product_panel]},
                    ]}
                ],
                tbar: tbar,
                listeners: {
                    render: function(p){
                        if(game_id > 0){
                            utils.http_request(utils.build_url("game/get_game"), {game_id: game_id}, function(json){
                                var base_info = json.data.base_info;
                                var area_info = json.data.area_info;
                                var product_info = json.data.product_info;

                                me.base_panel.set_data(base_info);
                                me.area_panel.set_data(area_info);
                                me.product_panel.set_data(product_info);

                                //在这里将area_panel和product_panel折叠起来，避免渲染出错
                                me.area_panel.collapse();
                                me.product_panel.collapse();
                            });
                        }
                    }
                }
            });
        },

        //=====================================================================================================================
        //校验所有数据
        is_valid: function(){
            if(!this.base_panel.is_valid()){
                utils.show_msg("游戏基础信息不完整，请先填写完整后再提交");
                return false;
            }
            if(!this.area_panel.is_valid()){
                utils.show_msg("游戏区服信息不完整，请先填写完整后再提交");
                return false;
            }
            return true;
        },
        //取得所有数据
        get_data: function(){
            var data = {};
            data.base_info = this.base_panel.get_data();
            data.area_info = this.area_panel.get_data();
            data.product_info = this.product_panel.get_data();
            data.game_id = parseInt(this.game_id);

            console.log(data.game_id);

            return data;
        }

    });

    exports.GameEditPanel = GameEditPanel;
});