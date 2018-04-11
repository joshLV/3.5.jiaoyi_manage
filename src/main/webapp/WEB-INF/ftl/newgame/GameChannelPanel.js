define(function(require, exports, module){
    var gcw = require("${static_file}=newgame.GameChannelSelectWindow");
    var utils = require("${static_file}=extjs.utils");

	var GameChannelPanel = Ext.extend(Ext.grid.EditorGridPanel, {
		constructor: function(other_channels){
			var me = this;
            this.other_channels = other_channels;
            
            this.record_fields = Ext.data.Record.create([
                {name: "channel_id"},
                {name: "channel_name"},
                {name: "package_download_url"},
                {name: "package_size"}
            ]);
            this.store = new Ext.data.JsonStore({
                root: "data",
                fields: this.record_fields
            });

			GameChannelPanel.superclass.constructor.call(this, {
				loadMask : true,
                stripeRows : true,
                layout: "fit",
                autoHeight: true,
                clicksToEdit: 1,
                anchor: "95%",
                border: false,
                columnLines: true,
                view: new Ext.grid.GridView({
                    forceFit: true,
                    enableRowBody: true
                }),
                store: this.store,
                columns: [
                    {header:"渠道名称", dataIndex:"channel_name", align:"center", width:50, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        return "<b>" + value + "</b>";
                    }},
                    {header:"渠道包下载地址", dataIndex:"package_download_url", align:"center", width:50, editor: new Ext.form.TextField()},
                    {header:"渠道包大小(KB)", dataIndex:"package_size", align:"center", width:50, editor: new Ext.form.TextField()},
                    {header:"操作", dataIndex:"opt", align:"center", width:30, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        return "<a href='javascript:void(0)'>删除</a>";
                    }},
                ],
                tbar: [
                    '->',
                    {xtype: "button", text: "添加渠道", icon: "${icon_path}/add.png", handler: function(){
                        var records = me.store.getRange();
                        var exclu_channels = [];
                        for(var i=0; i<records.length; ++i){
                            exclu_channels.push({channel_id: records[i].get("channel_id")});
                        }
                        if(other_channels){
                            exclu_channels = exclu_channels.concat(other_channels);
                        }

                        var channel_win = new gcw.GameChannelSelectWindow(exclu_channels);
                        channel_win.onAccept = function(selections){
                            for(var i=0; i<selections.length; ++i){
                                var v = selections[i];
                                var record = new me.record_fields({channel_id: v.get("id"), channel_name: v.get("name"), package_download_url: "", package_size: ""});
                                me.store.add(record);
                            }

                            channel_win.close();
                            utils.scroll_bottom(me);

                            //刷新标题中的统计数据
                            me.refresh_info(me);
                        }
                        channel_win.show();
                    }}
                ],
                listeners: {
                    cellclick: function(p, rowIndex, columnIndex, e){
                        var fieldName = me.getColumnModel().getDataIndex(columnIndex);
                        if(fieldName == "opt"){
                            me.store.removeAt(rowIndex);

                            //刷新标题中的统计数据
                            me.refresh_info(me);
                        }
                    }
                }
			});
		},

		get_records: function(){
			return this.store.getRange();
		},

        refresh_info: function(panel){
            //to be implemented
        },

		is_valid : function(){
            return this.store.getCount() != 0;
        },

        //获取数据
        get_data : function(){
            var data = [];
            var records = this.store.getRange();
            for(var i=0; i<records.length; ++i){
                data.push({
                    channel_id: records[i].get("channel_id"),
                    channel_name: records[i].get("channel_name"),
                    package_download_url: records[i].get("package_download_url"),
                    package_size: records[i].get("package_size")
                });
            }
            return data;
        },

        reset : function(){
            this.store.removeAll();
        },

        set_data: function(channel_info){
            for(var i=0; i<channel_info.length; ++i){
                var record = new this.record_fields({
                    channel_id: channel_info[i].channel_id,
                    channel_name: channel_info[i].channel_name,
                    package_download_url: channel_info[i].package_download_url,
                    package_size: channel_info[i].package_size,
                });
                this.store.add([record]);
            }

            this.refresh_info(this);
        }
	});

	exports.GameChannelPanel = GameChannelPanel;
});