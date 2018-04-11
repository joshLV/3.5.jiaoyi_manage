//游戏修改页
define(function(require, exports, module){
    var utils = require("${static_file}=extjs.utils");
    var gep = require("${static_file}=newgame.GameEditPanel");


    Ext.onReady(function(){

        Ext.QuickTips.init();

        //操蛋的seajs，通过后缀名来识别加载文件类型，而不是通过content-type
        seajs.use("${static_file}=newgame.css_newgame&suffix=file.css", function(){
            //视图
            new Ext.Viewport({
                layout : "border",
                items : [
                    {id:"main-panel", xtype:"panel", layout : "fit", padding: 3, border: false, region:"center", autoDestroy:true, autoScroll: true, items: [new gep.GameEditPanel(${game_id})]},
                ]
            });
        });
        
    });
}); 
