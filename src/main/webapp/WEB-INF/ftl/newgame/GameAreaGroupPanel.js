define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");

    var GameAreaGroupPanel = Ext.extend(Ext.form.FormPanel, {
        __make_id : function(name){
            if(name)
                return "GameAreaGroupPanel:" + this.id + ":" + name;
            return "GameAreaGroupPanel:" + this.id;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

        constructor: function(id){
            this.group = {};
            this.id = id;

            this.fields = this.create_fields();
            GameAreaGroupPanel.superclass.constructor.call(this, {
                id: this.__make_id(),
                border: false,
                padding: 8,
                autoHeight: true,
                layout: "fit",
                autoDestroy: true
            });
        },

        create_fields: function(){
            return Ext.data.Record.create([
                {name: "grp"},
                {name: "area_id"},
                {name: "area"},
                {name: "weight"}
            ]);
        },

        add_subs: function(area_grps){
            for(var i=0; i<area_grps.length; ++i){
                var grp = area_grps[i].grp;
                var records = area_grps[i].records;
                this.add_sub(grp, records);
            }
        },

        add_sub: function(grp_name, records){
            var grid = this.group[grp_name];
            if(grid != undefined || grid != null){
                utils.show_msg("已经添加同名分组");
                return;
            }

            grid = this.create_area_grid(grp_name, records);
            var fieldset = new Ext.form.FieldSet({
                autoDestroy: true, autoHeight: true, title: grp_name, collapsible: true, items: [grid]
            });
            this.add(fieldset);
            this.group[grp_name] = grid;

            this.doLayout();
        },

        create_area_grid: function(grp_name, records){
            var me = this;

            var grp_name_field = new Ext.form.TextField({xtype: "textfield", width: 100});

            var grid = new Ext.grid.EditorGridPanel({
                autoDestroy: true,
                layout: "fit",
                loadMask : true,
                stripeRows : true,
                autoHeight: true,
                cls: 'rowspan-grid',
                view: new Ext.ux.grid.RowspanView({
                    forceFit: true,
                    enableRowBody: true
                }),
                store: new Ext.data.JsonStore({
                    root: "data",
                    fields: me.fields,
                }),
                columns: [
                    {header:"区服分组名称", dataIndex:"grp", align:"center", width:50, rowspan: records.length, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        return "<b>" + value + "</b>";
                    }},
                    {header:"区服名称", dataIndex:"area", align:"center", width:50, editor: new Ext.form.TextField()},
                    {header:"权重", dataIndex:"weight", align:"center", width:50, editor: new Ext.form.TextField()},
                    {header:"操作", dataIndex:"opt", align:"center", width:20, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        return "<a href='javascript:void(0)'>删除</a>";
                    }},
                ],
                tbar: new Ext.Toolbar({
                    style: "background:#BFEFFF",
                    items:[
                        grp_name_field,
                        {xtype: "button", text: "修改分组名", icon: "${icon_path}/page_gear.png", width: 80, handler: function(){
                            var new_name = grp_name_field.getValue();
                            //如果已经存在了同名的，不允许修改
                            if(me.group[new_name] != undefined){
                                utils.show_msg("已经添加同名分组");
                                return;
                            }

                            //先删除旧的绑定
                            var old = grid.grp_name;
                            delete me.group[old];

                            //再绑定新的分组名
                            grid.grp_name = new_name;
                            me.group[new_name] = grid;

                            //将store中所有record的grp字段改成新值
                            grid.store.each(function(record){
                                record.set("grp", new_name);
                            });
                            grid.view.refresh();

                            //在更新fieldset中的title
                            var cmp = grid.findParentByType("fieldset");
                            cmp.setTitle(new_name);
                        }},
                        '-',
                        {xtype: "button", text: "新增区服", icon: "${icon_path}/add.png", width: 80, handler: function(){
                            //先得到当前grid中的最大weight值，+1就好了
                            var max_weight = 0;
                            grid.store.each(function(re){
                                if(re.get("weight") > max_weight)
                                    max_weight = re.get("weight");
                            });

                            var n = utils.increase_number();
                            var record = new me.fields({
                                grp: grid.grp_name, area_id: 0, area: "新建区服-" + n , weight: max_weight+1
                            });

                            grid.store.add([record]);
                            grid.view.refresh();
                        }},
                        '->',
                        {xtype: "button", text: "删除该分组", icon: "${icon_path}/delete.png", width: 80, handler: function(){
                            // 判断是否能删除
                            var records = grid.store.getRange();
                            me.__can_delete(records, function(){
                                var cmp = grid.findParentByType("fieldset");
                                if(!me.before_remove_grp(cmp, me.__get_cmp("grid:" + grp_name))){
                                    return;
                                }

                                me.remove(cmp);
                                me.doLayout();
                                utils.scroll_bottom(me);

                                //从group中去除
                                delete me.group[grp_name];

                                me.after_remove_grp();
                            });
                        }}
                    ]
                }),
                listeners: {
                    cellclick: function(p, rowIndex, columnIndex, e){
                        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
                        if(fieldName == "opt"){
                            // 判断是否能删除
                            var record = grid.store.getAt(rowIndex);
                            me.__can_delete([record], function(){
                                if(!me.before_remove_area(grid.store, rowIndex)){
                                    return;
                                }

                                grid.store.removeAt(rowIndex);

                                //刷新视图
                                grid.getView().refresh();

                                me.after_remove_area();
                            });
                        }
                    },
                    afteredit: function(e){
                        e.grid.getView().refresh();
                    }
                }
            });
            grid.grp_name = grp_name;
            
            //把数据写入
            for(var i=0; i<records.length; ++i){
                var rec = new me.fields(records[i]);
                grid.store.add([rec]);
            }

            return grid;
        },

        get_data: function(){
            var area_grps = [];
            for(var grp_name in this.group){
                var grid = this.group[grp_name];
                var records = grid.store.getRange();
                var data = [];
                for(var i=0; i<records.length; ++i){
                    data.push({grp: records[i].get("grp"), area_id: records[i].get("area_id"), area: records[i].get("area"), weight: records[i].get("weight")});
                }
                area_grps.push({grp: grp_name, records: data});
            }
            return area_grps;
        },

        clearAll: function(){
            this.removeAll();
            this.group = {};
        },

        //删除区服分组前的事件，如果返回false则忽略处理
        //to be implemented
        before_remove_grp: function(fieldset, grid){
            return true;
        },

        //删除区服前的事件，如果返回false则忽略处理
        //to be implemented
        before_remove_area: function(store, rowIndex){
            return true;
        },

        //to be implemented
        after_remove_area: function(){
        },

        //to be implemented
        after_remove_grp: function(){
        },


        //是否能删除该版本panel
        can_delete: function(callback){
            var rs = [];
            for(var k in this.group){
                var grid = this.group[k];
                var records = grid.store.getRange();
                rs = rs.concat(records);
            }
            this.__can_delete(rs, callback);
        },

        __can_delete: function(records, callback){
            var game_id = utils.get_data("game_id");

            //如果没有选中的区服，或者没有游戏ID（则该游戏是新增的），直接回调就好
            if(records.length == 0 || !game_id){
                if(callback) callback();
                return;
            }

            //修改区服的情况
            var str = records[0].get("area_id");
            for(var i=1; i<records.length; ++i){
                str += "`" + records[i].get("area_id");
            }
            utils.http_request(utils.build_url("game/can_delete_partition"), {area_id: str, game_id: game_id}, function(){
                if(callback) callback();
            });
        }
    });

    exports.GameAreaGroupPanel = GameAreaGroupPanel;
});