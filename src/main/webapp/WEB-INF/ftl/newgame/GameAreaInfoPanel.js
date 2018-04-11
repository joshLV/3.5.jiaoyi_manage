define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var gvw = require("${static_file}=newgame.GameVersionWindow");
    var gac = require("${static_file}=newgame.GameAreaGroupPanel");

	var GameAreaInfoPanel = Ext.extend(Ext.Panel, {
		constructor: function(collap_conf){
            collap_conf = collap_conf || {};
			this.subs = {};
            
			var me = this;
			GameAreaInfoPanel.superclass.constructor.call(this, {
				layout: "anchor",
                border: false,
                autoHeight: true,
                padding: 2,
                collapsible: collap_conf.collapsible==undefined?true:collap_conf.collapsible,
                tbar: [
                    {xtype: "button", text: "添加版本", icon: "${icon_path}/add.png", handler: function(){
                        var game_name = me.get_game_name();
                        if(game_name == ""){
                            utils.show_msg("请先填写游戏名称");
                            return;
                        }

                        //将其它版本配置中使用过的渠道取出来
                        var other_channels = [];
                        for(var vn in me.subs){
                            var grp_panel = me.subs[vn];
                            other_channels = other_channels.concat(grp_panel.channels);
                        }

                        var win = new gvw.GameVersionWindow(other_channels, game_name);
                        win.onAccept = function(version_name, is_show, weight, channels, area_grps){
                            //如果当前版本名称存在，提示错误
                            if(me.subs[version_name] != undefined){
                                utils.show_msg("相同版本名称的配置已经存在");
                                return;
                            }
                            if(version_name == ""){
                                utils.show_msg("必须配置版本名称");
                                return;
                            }
                            if(channels.length == 0){
                                utils.show_msg("必须为该游戏版本配置对应的渠道");
                                return;
                            }

                            var grp_panel = me.create_group_panel(version_name, is_show, weight, channels, area_grps);
                            me.add(grp_panel);
                            me.subs[version_name] = grp_panel;
                            me.doLayout();

                            win.close();

                            //更新游戏区服统计信息
                            me.refresh_info(me);

                            //滑动到最底端
                            utils.scroll_bottom(me);
                        }
                        win.show();
                    }}
                ],
			});
		},

        _convert_channels_to_string: function(channels){
            if(channels.length > 0){
                var str_channels = channels[0].channel_name.replace("客户端", "");
                for(var i=1; i<channels.length; ++i){
                    str_channels += "，" + channels[i].channel_name.replace("客户端", "");
                }
                return str_channels;
            }
            return "";
        },

        //创建游戏分区组Panel
        /** XXX 注意：所有参数（除了panel）都是容易过期的数据，只有在第一次使用时有效，在“修改该版本区服”后都会导致过期，所以当前有效数值总是要从控件中获取一次!!! */
        create_group_panel: function(version_name, is_show, weight, channels, area_grps){
            var me = this;

            //-------------------------
            var subpanel = new gac.GameAreaGroupPanel(Ext.id());
            subpanel.before_remove_grp = function(fieldset, grid){
                var _area_grps = subpanel.get_data();
                if(_area_grps.length == 1){
                    utils.show_msg("版本区服配置至少需要保留一组数据，请通过删除版本区服来进行删除.");
                    return false;
                }
                return true;
            }
            //XXX 注意这里调用了父容器的方法!!!
            subpanel.after_remove_area = function(){
                //更新游戏区服统计信息
                me.refresh_info(me);
            }
            subpanel.after_remove_grp = function(){
                //更新游戏区服统计信息
                me.refresh_info(me);
            }

            //区服列表界面
            for(var i=0; i<area_grps.length; ++i){
                var grp = area_grps[i].grp;
                var records = area_grps[i].records;
                subpanel.add_sub(grp, records);
            }

            //-------------------------
            //tbar中的组件
            var version_name_field = new Ext.form.TextField({width: 80, value: version_name});
            var is_show_combobox = new Ext.form.ComboBox({width: 60, triggerAction: 'all', typeAhead : true, editable: false, model:"local", store:[[1, "显示"], [0, "不显示"]], value: is_show});
            var weight_field = new Ext.form.TextField({xtype: "textfield", width: 80, value: weight});
            var channels_field = new Ext.form.TextField({xtype: "textfield", width: 500, value: me._convert_channels_to_string(channels), readOnly: true});

            var grp_panel = new Ext.Panel({
                padding: "10px 0px 0px 0px",
                border: false, 
                items: [{
                    xtype:"panel", 
                    tbar: new Ext.Toolbar({
                        style : 'background:grey',
                        items:[
                        "版本名称:", version_name_field,
                        '-',
                        is_show_combobox,
                        '-',
                        '权重:', weight_field,
                        '-',
                        "渠道:", channels_field,
                        '->',
                        {xtype: "button", icon: "${icon_path}/application_edit.png", text: "修改该版本区服", handler: function(){
                            //当前修改时可选择的渠道
                            var other_channels = [];
                            for(var vn in me.subs){
                                var gp = me.subs[vn];
                                if(gp.getId() == grp_panel.getId())
                                    continue;
                                other_channels = other_channels.concat(gp.channels);
                            }
                            
                            //当前的区服配置
                            var _area_grps = subpanel.get_data();

                            //弹出编辑窗口
                            var win = new gvw.GameVersionWindow(other_channels, version_name_field.getValue(), is_show_combobox.getValue(), weight_field.getValue(), grp_panel.channels, _area_grps);
                            win.onAccept = function(version_name2, is_show2, weight2, channels2, area_grps2){
                                var old_version_name = version_name_field.getValue();

                                //如果当前版本名称存在，提示错误
                                if(old_version_name != version_name2 && me.subs[version_name2] != undefined){
                                    utils.show_msg("相同版本名称的配置已经存在");
                                    return;
                                }
                                if(version_name == ""){
                                    utils.show_msg("必须配置版本名称");
                                    return;
                                }
                                if(channels2.length == 0){
                                    utils.show_msg("必须为该游戏版本配置对应的渠道");
                                    return;
                                }

                                //移除旧的数据绑定和界面数据
                                delete me.subs[old_version_name];  
                                subpanel.clearAll();

                                //重新填充界面和绑定数据
                                for(var i=0; i<area_grps2.length; ++i){
                                    var grp = area_grps2[i].grp;
                                    var records = area_grps2[i].records;
                                    subpanel.add_sub(grp, records);
                                }
                                me.subs[version_name2] = grp_panel;
                                grp_panel.channels = channels2;

                                //更新tbar中的数据
                                version_name_field.setValue(version_name2);
                                is_show_combobox.setValue(is_show2);
                                weight_field.setValue(weight2);
                                channels_field.setValue(me._convert_channels_to_string(channels2));

                                //更新游戏区服统计信息
                                me.refresh_info(me);

                                win.close();
                            };
                            win.show();
                        }},
                        {xtype: "button", icon: "${icon_path}/delete.png", text: "删除该版本区服", handler: function(){
                            subpanel.can_delete(function(){
                                var grp_name = version_name_field.getValue();
                                me.remove(grp_panel);
                                delete me.subs[grp_name];

                                me.doLayout();

                                //更新游戏区服统计信息
                                me.refresh_info(me);
                            });
                        }}
                    ]}),
                    items: [subpanel]
                }]
            });
            // 挂接上数据
            grp_panel.channels = channels;

            // 把subpanel也带上
            grp_panel.subpanel = subpanel;

            //获取数据
            grp_panel.get_data = function(){
                var data = {};
                data.version_name = version_name_field.getValue();
                data.is_show = is_show_combobox.getValue();
                data.weight = weight_field.getValue();
                data.channels = grp_panel.channels;
                data.area_grps = subpanel.get_data();
                return data;
            }

            return grp_panel;
        },

		//to be implemented
		get_game_name: function(){
        },
		//to be implemented
		refresh_info: function(panel){
        },

		is_valid : function(){
            return Object.keys(this.subs) != 0;
        },

        //获取数据
        get_data : function(){
            var data = [];
            for(var version_name in this.subs){
                var grp_panel = this.subs[version_name];
                data.push(grp_panel.get_data());
            }
            return data;
        },

        reset : function(){
            this.removeAll();
            this.subs = {};
            this.doLayout();
            
            this.refresh_info(this);
        },

        set_data: function(area_info){
            for(var i=0; i<area_info.length; ++i){
                var info = area_info[i];
                var grp_panel = this.create_group_panel(info.version_name, info.is_show, info.weight, info.channels, info.area_grps);
                this.add(grp_panel);
                this.subs[info.version_name] = grp_panel;
            }
            this.doLayout();
        	
            this.refresh_info(this);
        }
	});

	exports.GameAreaInfoPanel = GameAreaInfoPanel;
});