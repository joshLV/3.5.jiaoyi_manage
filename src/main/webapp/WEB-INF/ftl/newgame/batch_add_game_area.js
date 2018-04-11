define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var gai = require("${static_file}=newgame.GameAreaInfoPanel");
    var gsw = require("${static_file}=newgame.GameSelectWindow");

    Ext.onReady(function(){

        Ext.QuickTips.init();

        var __make_id = function(name){
            return "batch_add_game_area:" + name;
        }

        var __get_cmp = function(name){
            return Ext.getCmp(__make_id(name));
        }

        //视图
        var game_area_panel = new gai.GameAreaInfoPanel({collapsible: false});
        var main_panel = new Ext.Panel({
            id:"main-panel", 
            layout : "fit",
            padding: 3, 
            border: false, 
            region:"center", 
            autoDestroy:true, 
            autoScroll: true, 
            tbar: [
                {xtype: "button", icon: "${icon_path}/accept.png", width: 100, height: 40, style: "border: solid 1px gray;", text: "保存", handler: function(){
                    var data = main_panel.get_data();
                    if(data.games.length == 0){
                        utils.show_msg("请先选择需要增加区服的游戏");
                        return;
                    }

                    utils.http_request(utils.build_url("game/batch_save_game"), {data: Ext.util.JSON.encode(data)}, function(json){
                        utils.show_msg("批量增加游戏区服成功");

                        //清场？
                        // __get_cmp("games").setValue("");
                        // game_area_panel.reset();
                    });
                }},
                '-',
                '-',

                {id: __make_id("games"), xtype: "textfield", width: 800, readOnly: true},
                '-',
                {xtype: "button", icon: "${icon_path}/zoom_in.png", width: 80, text: "选择游戏", handler: function(){
                    var cmp = __get_cmp("games");

                    var win = new gsw.GameSelectWindow({}, cmp.games);
                    win.onAccept = function(games){
                        //填入顶栏中
                        var str = "";
                        if(games.length != 0){
                            str = games[0].gameName;
                            for(var i=1; i<games.length; ++i){
                                str += "，" + games[i].gameName;
                            }
                        }
                        cmp.setValue(str);
                        cmp.games = games;

                        win.close();
                    }
                    win.show();
                }}

            ],
            items: [game_area_panel]
        });

        //#%^*(*&%^&$$#^$%#*(*)))^$#@^*&
        main_panel.get_data = function(){
            var data = {};

            var gs = [];
            var games = __get_cmp("games").games || [];
            for(var i=0; i<games.length; ++i){
                gs.push({game_id: games[i].gameId, game_name: games[i].gameName});
            }

            data.games = gs;
            data.area_info = game_area_panel.get_data();
            return data;      
        }

        //------------------------------------------------------
        new Ext.Viewport({
            layout : "border",
            items : [main_panel]
        });
    });
}); 
