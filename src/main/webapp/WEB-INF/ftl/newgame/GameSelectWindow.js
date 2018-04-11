define(function(require, exports, module){
	var utils = require("${static_file}=extjs.utils");
    var xsm = require("${static_file}=extjs.com.SelectionModelRectify");

	var GameSelectWindow = Ext.extend(Ext.Window, {
        __make_id: function(name){
            return "GameSelectWindow:" + name;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

		constructor: function(config, data){
            config = config || {};

			var me = this;

			this.fields = this.create_fields();
			this.sm = new xsm.XCheckboxSelectionModel({
                singleSelect: config.singleSelect
            });
            this.grid = this.create_grid(config, data);

			GameSelectWindow.superclass.constructor.call(this, {
                title: "选择游戏", 
                layout: 'fit',
                autoScroll: true,
                border: false,
                modal: true,
                frame : true,
                closable : true,
                autoDestroy : true,
                closeAction : "close",
                resizable : true,
                width: 1000,
                height: 800,
                items: [this.grid],
                tbar: [
                    {xtype: "button", icon: "${icon_path}/add.png", text: "确定", width: 100, height: 30, style: "border: solid 1px gray; background-color: #ccc;", handler: function(){
                        var games = me.get_data();
                        me.onAccept(games);
                    }},
                    '-',
                    "根据游戏名称筛选：",
                    {xtype: "textfield", enableKeyEvents: true, width: 200, listeners: {
                        keyup: function(tf, e){
                            var store = me.grid.store;
                            var val = tf.getValue();
                            if(val.length == 0){
                                store.clearFilter();
                                return;
                            }

                            store.filterBy(function(record, id){
                                if(record.get("gameName").indexOf(val) != -1)
                                    return true;
                                return false;
                            });
                        }
                    }},
                ],
                listeners: {
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

        create_grid: function(config, data){
            var me = this;
            var grid = new Ext.grid.GridPanel({
                loadMask : true,
                stripeRows : true,
                layout: "fit",
                autoHeight: true,
                border: false,
                sm: this.sm,
                view: new Ext.grid.GridView({
                    forceFit: true,
                    enableRowBody: true
                }),
                store: new Ext.data.JsonStore({
                    url: utils.build_url("game/gameList_json.do"),
                    root: "rows",
                    totalProperty: "total",
                    fields: this.fields,
                    listeners: {
                        beforeload: function(s){
                            s.baseParams.page = 1;
                            s.baseParams.rows = 1000;
                            s.baseParams.sort = "game_id,weight";
                            s.baseParams.order = "desc";
                        },
                        load: function(s){
                            if(data != undefined){
                                for(var i=0; i<data.length; ++i){
                                    var idx = me.grid.store.find("gameId", data[i].gameId);
                                    if(idx != -1){
                                        me.grid.getSelectionModel().selectRow(idx, true);
                                    }
                                }
                            }
                        }
                    }
                }),
                columns: [
                    this.sm,
                    {header:"游戏编号", dataIndex:"gameId", align:"center", width:50},
                    {header:"游戏名称", dataIndex:"gameName", align:"center", width:50, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        return "<b>" + value + "</b>";
                    }},
                    {header:"首字母", dataIndex:"gameInitial", align:"center", width:50},
                    {header:"是否支持C2C", dataIndex:"isSupportC2cView", align:"center", width:50},
                    {header:"是否支持API", dataIndex:"isSupportApiView", align:"center", width:50},
                    {header:"显示状态", dataIndex:"status", align:"center", width:50},
                    {header:"权重", dataIndex:"weight", align:"center", width:50},
                    {header:"游戏系数", dataIndex:"gameCoefficient", align:"center", width:50},
                    {header:"市场系数", dataIndex:"marketCoefficient", align:"center", width:50},
                    {header:"有无通用货币", dataIndex:"isComCurrency", align:"center", width:50},
                ],
                listeners: {
                    render: function(){
                        grid.store.load()
                    }
                }
            });
            return grid;
        },

		create_fields: function(){
			return Ext.data.Record.create([
            	{name: "gameId"},
            	{name: "gameName"},
            	{name: "gameInitial"},
            	{name: "isSupportC2cView"},
				{name: "isSupportApiView"},
            	{name: "status"},
            	{name: "weight"},
            	{name: "gameCoefficient"},
            	{name: "marketCoefficient"},
            	{name: "isComCurrency"}
			]);
		},

		get_data: function(){
			var data = [];
			var records = this.grid.getSelectionModel().getSelections();
			for(var i=0; i<records.length; ++i){
				data.push(records[i].data);
			}
			return data;
		},
        onAccept: function(games){
            //to be impelmented
        }
	});

	exports.GameSelectWindow = GameSelectWindow;
});