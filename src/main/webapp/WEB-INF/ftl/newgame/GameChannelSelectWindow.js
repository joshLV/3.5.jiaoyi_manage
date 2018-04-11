define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var xsm = require("${static_file}=extjs.com.SelectionModelRectify");

    var GameChannelSelectWindow = Ext.extend(Ext.Window, {
        constructor: function(exclu_channels){
            this.exclu_channels = exclu_channels;

            var me = this;
            var grid = this.create_channel_grid();
            GameChannelSelectWindow.superclass.constructor.call(this, {
                id: "GameChannelSelectWindow",
                title: "游戏渠道列表",
                layout: "fit",
                autoDestroy: true,
                closable: true,
                closeAction: "close",
                resizable: true,
                modal: true,
                frame : true,
                width: 1000,
                height: 600,
                tbar: [
                    {xtype: "button", text: "确认选择", icon: "${icon_path}/accept.png", style: "border: solid 1px gray; background-color: #ccc;", width: 100, height: 30, handler: function(){
                        var selections = grid.getSelectionModel().getSelections();
                        if(selections.length == 0){
                            utils.show_msg("请选择需要添加的渠道");
                            return;
                        }
                        me.onAccept(selections);
                    }}
                ],
                items: [grid],
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

        create_channel_grid: function(){
            var me = this;
            var sm = new xsm.XCheckboxSelectionModel();
            
            var grid = new Ext.grid.GridPanel({
                autoDestroy: true,
                layout: "fit",
                loadMask : true,
                stripeRows : true,
                autoScroll : true,
                view: new Ext.grid.GridView({
                    forceFit: true,
                    enableRowBody: true
                }),
                sm: sm,
                store: new Ext.data.JsonStore({
                    url: utils.build_url("game/channels.do"),
                    totalProperty: "total",
                    root: "rows",
                    fields: [
                        {name: "id"},
                        {name: "rechargeType"},
                        {name: "weight"},
                        {name: "channelHomeUrl"},
                        {name: "status"},
                        {name: "name"},
                    ],
                    listeners: {
                        beforeload: function(s){
                            var qudaos = "";
                            if(me.exclu_channels && me.exclu_channels.length > 0){
                                qudaos += me.exclu_channels[0].channel_id;
                                for(var i=1; i<me.exclu_channels.length; ++i){
                                    qudaos += "," + me.exclu_channels[i].channel_id;
                                }
                            }

                            s.baseParams.page = 1;
                            s.baseParams.rows = 1000;
                            s.baseParams.sort = "weight";
                            s.baseParams.order = "desc";
                            s.baseParams.qudaos = qudaos;
                            return true;
                        },
                        load: function(s){
                            grid.getSelectionModel().selectAll();
                        }
                    }
                }),
                columns: [
                    new Ext.grid.RowNumberer(),
                    sm,
                    {header:"编号", dataIndex:"id", align:"center", width:25},
                    {header:"渠道名称", dataIndex:"name", align:"center", width:50},
                    {header:"状态", dataIndex:"status", align:"center", width:30},
                    {header:"权重", dataIndex:"weight", align:"center", width:30},
                    {header:"充值方式", dataIndex:"rechargeType", align:"center", width:50, renderer : function(value, metaData, record, rowIndex, colIndex, store){
                        var types = value;
                        var tname ="";
                        if(types.indexOf(",") >=0){
                            var str = types.split(",");
                            for(var i=0;i<str.length;i++){
                                if(str[i] ==1){
                                    tname +="骏网卡," 
                                }else  if(str[i] ==12){
                                    tname +="天下卡," 
                                }else  if(str[i] ==13){
                                    tname +="QQ卡," 
                                }else  if(str[i] ==2){
                                    tname +="支付宝自充," 
                                }else  if(str[i] ==3){
                                    tname +="支付宝白名单," 
                                }
                            }
                            return tname;
                        }else{
                            if (types==0){
                                return "<font color='red'>暂无</font>";
                            }else if(types==1){
                                return "骏网卡";
                            }else if(types==2){
                                return "支付宝自充";
                            }else if(types==3){
                                return "支付宝白名单";
                            }else if(types==12){
                                return "天下卡";
                            }else if(types==13){
                                return "QQ卡";
                            }else{
                                return "--"
                            }
                        }
                    }},
                    {header:"渠道官方地址", dataIndex:"channelHomeUrl", align:"center", width:80}
                ],
                listeners: {
                    render: function(g){
                        g.store.load();
                    }
                }
            });
            return grid;
        },

        //to be implemented
        onAccept: function(selections){
            alert("fuck...");
        }
    });

    exports.GameChannelSelectWindow = GameChannelSelectWindow;
});