define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var gac = require("${static_file}=newgame.GameAreaGroupPanel");
    var gci = require("${static_file}=newgame.GameChannelPanel");

    var GameVersionWindow = Ext.extend(Ext.Window, {

        __make_id : function(name){
            return "GameVersionWindow:" + name;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

        /**
        Params:
            other_channels: 已经被其它游戏版本配置选择过的渠道，不允许在这里被选择
            version_name: 当前版本名，若新建则为null
            is_show: 当前是否支持展示，若新建则为null
            weight: 当前权重，若新建则为null
            channels: 当前已有渠道，若新建则为null
            area_grps: 当前配置的区服，若新建则为null
        */        
        constructor: function(other_channels, version_name, is_show, weight, channels, area_grps){
            this.version_name = version_name;
            this.is_show = is_show;
            this.weight = weight;
            this.channels = channels;
            this.area_grps = area_grps;

            this.version_panel = this.create_version_panel();
            this.channel_pannel = this.create_channel_panel(other_channels);
            this.area_panel = this.create_area_panel();

            var me = this;
            this.inner_pannel = new Ext.Panel({xtype: "panel", border:false, autoScroll: true, layout: "anchor", items: [this.version_panel, this.channel_pannel, this.area_panel]});
            this.closing = false;
            GameVersionWindow.superclass.constructor.call(this, {
                id: "GameVersionWindow",
                title: "游戏版本编辑",
                layout: "fit",
                autoDestroy: true,
                closable: true,
                closeAction: "close",
                modal: true,
                resizable: true,
                frame : true,
                width: 1000,
                height: 800,
                autoScroll: true,
                tbar: [
                    {id: me.__make_id("ok_btn"), xtype: "button", text: "确认选择", width: 100, height: 30, style: "border: solid 1px gray; background-color: #ccc;", icon: "${icon_path}/accept.png", handler: function(){
                        me.closing = true;
                        me.on_save();
                    }}
                ],
                items: [this.inner_pannel],
                listeners: {
                    beforeclose: function(){
                        if(!me.closing){
                            utils.show_confirm("是否保存当前编辑的内容？", function(){
                                me.closing = true;
                                me.on_save();
                            }, function(){
                                me.closing = true;
                                me.close();
                            });
                        }
                        return me.closing;
                    },
                    show: function(p){
                        var coord = p.getPosition(true);
                        var x = coord[0];
                        var y = coord[1];
                        if(x < 0)
                            x = 10;
                        if(y < 0)
                            y = 10;
                        p.setPosition(x, y);
                    }
                }
            });
        },

        on_save: function(){
            var me = this;
            //将数据都收集
            var version_name = me.__get_cmp("version_name").getValue();
            var is_show = me.__get_cmp("is_show").getValue();
            var weight = me.__get_cmp("weight").getValue();

            var channels = me.channel_pannel.get_data();

            var subpanel = Ext.getCmp("GameAreaGroupPanel:GameVersionWindow");
            var _area_grps = subpanel.get_data();

            me.onAccept(version_name, is_show, weight, channels, _area_grps);
        },

        create_version_panel: function(){
            var me = this;

            return new Ext.form.FormPanel({
                height: 100,
                anchor: "95%",
                border: false,
                items: [
                    {id: me.__make_id("version_name"), xtype: "textfield", fieldLabel: "版本名称", anchor: "50%", value: this.version_name, allowBlank: false},
                    {id: me.__make_id("is_show"), xtype: "combo", fieldLabel: "是否显示", anchor: "50%", triggerAction: 'all', typeAhead : true, editable: false, model:"local",
                        value: me.is_show?me.is_show:1, store: [[1, "显示"], [0, "不显示"]]},
                    {id: me.__make_id("weight"), xtype: "textfield", fieldLabel: "权重", anchor: "50%", value: me.weight?me.weight:100, allowBlank: false},
                ]
            });
        },

        create_channel_panel: function(other_channels){
            var panel = new gci.GameChannelPanel(other_channels);
            if(this.channels){
                panel.set_data(this.channels);
            }
            return panel;
        },

        create_area_panel: function(){
            var me = this;
            var subpanel = new gac.GameAreaGroupPanel("GameVersionWindow");
            subpanel.gen_id = 1;

            if(me.area_grps){
                subpanel.add_subs(me.area_grps);
            }

            var panel = new Ext.Panel({
                autoHeight: true,
                padding: 8,
                border: false,
                anchor: "95%",
                tbar: [
                    "区服分组名称:",
                    {id: me.__make_id("grp"), xtype: "textfield", width: 150}
                ],
                items:[subpanel],
                listeners: {
                    render: function(p){
                        var tbar2 = new Ext.Toolbar([
                            "区名前缀：",
                            {id: me.__make_id("prefix"), xtype: "textfield", width: 60},
                            '-',
                            {id: me.__make_id("start_type"), xtype: "combo", width: 80, triggerAction: 'all', typeAhead : true, editable: false, model:"local", value: 1, 
                                store: [[1, "起始数字"], [2, "起始文字"]]},
                            {id: me.__make_id("start_num"), xtype: "numberfield", width: 40, value: 1},
                            '-',
                            "区服单位:",
                            {id: me.__make_id("unit"), xtype: "textfield", width: 40, value: "区"},
                            '-',
                            "起始权重:",
                            {id: me.__make_id("start_weight"), xtype: "numberfield", width: 40, value: 100},
                            '-',
                            '权重排序:',
                            {id: me.__make_id("weight_sort"), xtype: "combo", width: 80, triggerAction: 'all', typeAhead : true, editable: false, model:"local", value: "asc", 
                                store: [["asc", "升序"], ["desc", "降序"]]},
                            '-',
                            "创建数量:",
                            {id: me.__make_id("create_num"), xtype: "numberfield", width: 40, value: 10},
                            '->',
                            {xtype: "button", text: "创建分组", widht: 80, icon: "${icon_path}/add.png", handler: function(){
                                var grp = me.__get_cmp("grp").getValue();
                                if(grp == ""){
                                    grp = "default_" + (subpanel.gen_id++);
                                }
                                var records = me._generate_records(grp);
                                if(records.length != 0){
                                    subpanel.add_sub(grp, records);
                                    panel.doLayout();

                                    utils.scroll_bottom(me.inner_pannel);
                                }
                            }}
                        ]);
                        tbar2.render(p.tbar);
                    }
                }
            });
            
            return panel;
        },

        _generate_records: function(grp){
            var records = [];
            var me = this;
            var start_num = utils.parseInt(me.__get_cmp("start_num").getValue());
            if(start_num <= 0){
                utils.show_msg("请设置起始数字/起始文字为一个大于0的数字");
                return records;
            }
            
            var create_num = utils.parseInt(me.__get_cmp("create_num").getValue());
            if(create_num <= 0){
                utils.show_msg("请设置创建数量为一个大于0的数字");
                return records;
            }
            var start_type = me.__get_cmp("start_type").getValue();

            var start_weight = utils.parseInt(me.__get_cmp("start_weight").getValue());
            if(start_weight <= 0){
                utils.show_msg("请设置起始权重为一个大于0的数字");
                return records;
            }
            
            var weight_sort = me.__get_cmp("weight_sort").getValue();
            var unit = me.__get_cmp("unit").getValue();

            var prefix = me.__get_cmp("prefix").getValue();

            for(var i=0; i<create_num; ++i){
                var area = start_num+i;
                var weight = start_weight;
                if(weight_sort == "asc"){
                    weight += i;
                }
                if(weight_sort == "desc"){
                    weight -= i;
                    if(weight < 0)
                        weight = 0;
                }

                //起始数字
                if(start_type == 1){
                    records.push({grp: grp, area_id: 0, area: prefix+area+unit, weight: weight});
                }
                //起始文字
                if(start_type == 2){
                    var nc = new utils.number_change(area+"");
                    var zh_area = nc.pri_ary();
                    records.push({grp: grp, area_id: 0, area: prefix+zh_area+unit, weight: weight});
                }
            }
            return records;
        },

        onAccept: function(version_name, is_show, weight, channels, area_grps){
            //to be implemented
        }
    });

    exports.GameVersionWindow = GameVersionWindow;
});