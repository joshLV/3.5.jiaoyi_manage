define(function(require, exports, module){
	var utils = require("${static_file}=extjs.utils");

	var GameBaseInfoPanel = Ext.extend(Ext.Panel, {
		__make_id : function(name){
            return "GameBaseInfoPanel:" + name;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

		constructor: function(){
			var me = this;

            //如果是修改数据的话，logo_url有值，此时后端通过logo文件和logo_url判断是否需要更新
            this.logo_url = "";
            this.file_size = "";

			this.form1 = new Ext.form.FormPanel({
                border: false,
                width: 400,
                height: 80,
                padding: "0px 10px 0px 0px",
                items: [
                    {id: me.__make_id("game_name"), xtype: "textfield", fieldLabel: "游戏名称", allowBlank: false, anchor: "95%", listeners: {
                        blur: function(p){
                            var game_desc = me.__get_cmp("game_desc").getValue();
                            if(game_desc == ""){
                                me.__get_cmp("game_desc").setValue(p.getValue());
                            }

                            // 访问拼音服务获取游戏名对应的拼音
                            if(p.getValue() != ""){
                                utils.http_request(utils.build_url("convertpy"), {word: p.getValue()}, function(json){
                                    var c = json.py[0];
                                    c = c.toUpperCase();
                                    me.__get_cmp("shortcut").setValue(c);
                                })
                            }
                        }
                    }},
                    {id: me.__make_id("weight"), xtype: "textfield", fieldLabel: "权重", allowBlank: false, anchor: "95%"},
                    {id: me.__make_id('logo'), hideLabel: false, fieldLabel: "选择Logo", anchor: "95%", xtype: 'textfield', inputType: "file", listeners: {
                        change: function(field, newValue, oldValue){
                            var el = me.__get_cmp('logo').getEl();
                            var url = 'file://' + el.dom.value;
                            Ext.get(me.__make_id('imageBrowser')).dom.src = window.URL.createObjectURL(el.dom.files.item(0));
                        }
                    }},
                ]
            });

            this.form2 = new Ext.form.FormPanel({
                border: false,
                width: 400,
                height: 80,
                padding: "0px 10px 0px 0px",
                items: [
                    {id: me.__make_id("game_star"), xtype: "textfield", fieldLabel: "游戏星级", allowBlank: false, anchor: "95%"},
                    {id: me.__make_id("shortcut"), xtype: "textfield", fieldLabel: "首字母", allowBlank: false, anchor: "95%"},
                    {id: me.__make_id("download_url"), xtype: "textfield", fieldLabel: "下载地址", anchor: "95%", listeners: {
                        change: function(p){
                            if(p.getValue() == "")
                                return;

                            utils.http_request(utils.build_url("game/check_download_url"), {download_url: p.getValue()}, function(json){
                                if(json.msg == "succ"){
                                    p.clearInvalid();
                                }else{
                                    p.markInvalid("此下载地址无法正常访问，请检查");
                                }
                            });
                        },
                    }},
                ]
            });

            this.form3 = new Ext.form.FormPanel({
                border: false,
                width: 400,
                items: [
                    {fieldLabel: 'Logo预览', xtype: 'box', autoEl: {src: Ext.BLANK_IMAGE_URL, tag: 'img', complete: 'off', id: me.__make_id('imageBrowser'), width:100, height: 100} },
                ]
            });

            this.form4 = new Ext.form.FormPanel({
                border: false,
                padding: 5, 
                colspan: 2,
                items: [
                    {id: me.__make_id("game_desc"), xtype: "textarea", fieldLabel: "游戏简介", anchor: "100%", height: 100}
                ]
            });

			GameBaseInfoPanel.superclass.constructor.call(this, {
				border: false, 
				layout: "table",
				layoutConfig: {columns:3}, 
				items: [this.form1, this.form2, this.form3, this.form4]
			});
		},

		get_game_name: function(){
			return this.__get_cmp("game_name").getValue();
		},

		is_valid : function(){
            if(!this.form1.getForm().isValid() || !this.form2.getForm().isValid() || !this.form3.getForm().isValid() || !this.form4.getForm().isValid()){
                return false;
            }

            if(!this.logo_url && this.__get_cmp('logo').getValue() == "")
                return false;

            return true;
        },

        get_data : function(){
            var base = {};
            base.game_name = this.__get_cmp("game_name").getValue();
            base.weight = this.__get_cmp("weight").getValue();
            base.logo = this.__get_cmp('logo').getEl().dom.files[0];
            base.logo_url = this.logo_url;
            base.file_size = this.file_size;
            base.game_star = this.__get_cmp("game_star").getValue();
            base.shortcut = this.__get_cmp("shortcut").getValue();
            base.download_url = this.__get_cmp("download_url").getValue();
            base.game_desc = this.__get_cmp("game_desc").getValue();
            return base;
        },

        reset : function(){
            this.form1.getForm().reset();
            this.form2.getForm().reset();
            this.form3.getForm().reset();
            this.form4.getForm().reset();

            this.logo_url = "";
            this.file_size = "";
            Ext.get(this.__make_id('imageBrowser')).dom.src = "";
        },

        set_data: function(base_info){
            this.logo_url = base_info.logo;
            this.file_size = base_info.file_size;

            this.__get_cmp("game_name").setValue(base_info.game_name);
            this.__get_cmp("weight").setValue(base_info.weight);
            this.__get_cmp("game_star").setValue(base_info.game_star);
            this.__get_cmp("shortcut").setValue(base_info.shortcut);
            this.__get_cmp("download_url").setValue(base_info.download_url);
            this.__get_cmp("game_desc").setValue(base_info.game_desc);

            Ext.get(this.__make_id('imageBrowser')).dom.src = this.logo_url;
        }
	});

	exports.GameBaseInfoPanel = GameBaseInfoPanel;
});