define(function(require, exports, module){
	var utils = require("${static_file}=extjs.utils");
    var xsm = require("${static_file}=extjs.com.SelectionModelRectify");

	var GameProductTypeEditWindow = Ext.extend(Ext.Window, {
		__make_id : function(name){
            return "GameProductTypeEditWindow:" + name;
        },

        __get_cmp: function(name){
            return Ext.getCmp(this.__make_id(name));
        },

		constructor: function(){
			var me = this;

			this.base_info_panel = this.create_base_info_panel();
			this.ext_info_panel = this.create_ext_info_panel();

			this.prod_info_panel = this.create_property_panel("商品属性", "#D2E0F1");
			this.saler_info_panel = this.create_property_panel("卖家信息", "#BFEFFF");
			this.buy_info_panel = this.create_property_panel("购买信息", "#D2E0F1");
			this.ty_info_panel = this.create_property_panel("退游信息", "#BFEFFF");

			this.tip_info_panel = this.create_tip_panel();

			//用来判断当前窗口是否需要关闭
			this.closing = false;
			GameProductTypeEditWindow.superclass.constructor.call(this, {
				title: "配置商品大类",
                layout: "fit",
                autoDestroy: true,
                closable: true,
                closeAction: "close",
                resizable: true,
                modal: true,
                frame : true,
                width: 1400,
                height: 800,
                tbar: [
                	{id: me.__make_id("ok_btn"), xtype: "button", text: "确定", icon: "${icon_path}/accept.png", width: 100, height: 30, style: "border: solid 1px gray; background-color: #ccc;", handler: function(){
                		me.closing = true;
                        me.on_save();
                	}}
                ],
				items: [{xtype: "panel", border: false, layout: "anchor", autoScroll: true, padding: 8, items: [
					this.base_info_panel,
					this.ext_info_panel,
					this.prod_info_panel,
					this.saler_info_panel,
					this.buy_info_panel,
					this.ty_info_panel,
					this.tip_info_panel
				]}],
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
    		var data = me.get_data();

    		if(!me.is_valid(data)){
    			return;
    		}

    		me.onAccept(data);
		},

		_show_sc_dc: function(groupName){
			var me = this;
			var setDisabled = function(cmp, flag){
				if(cmp.rendered){
					cmp.setDisabled(flag);
				}else{
					cmp.on("render", function(p){
						cmp.setDisabled(flag);
					})
				}
			}

			if(groupName == "首充号"){
				setDisabled(me.__get_cmp("sc_whitelist"), false);
				setDisabled(me.__get_cmp("dc_whitelist"), true);
			}
			else if(groupName == "代充"){
				setDisabled(me.__get_cmp("sc_whitelist"), true);
				setDisabled(me.__get_cmp("dc_whitelist"), false);
			}
			else if(groupName == "首充号代充"){
				setDisabled(me.__get_cmp("sc_whitelist"), false);
				setDisabled(me.__get_cmp("dc_whitelist"), false);
			}
			else{
				setDisabled(me.__get_cmp("sc_whitelist"), true);
				setDisabled(me.__get_cmp("dc_whitelist"), true);
			}
		},

		create_base_info_panel: function(){
			var me = this;

			var form1 = new Ext.form.FormPanel({
				border: false,
				width: 400,
				padding: 8,
				items:[
					//商品大类选项
					{id: me.__make_id("product_type_group"), xtype: "combo", fieldLabel: "商品大类", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model:"remote", 
					allowBlank: false, store: new Ext.data.JsonStore({
						url: utils.build_url("product/proTypeGroupList_json"),
						totalProperty: "total",
						root: "rows",
						fields: [
							{name: "id"}, {name: "weight"}, {name: "status"}, {name: "description"}, {name: "name"}
						],
						listeners: {
							beforeload: function(s){
								s.baseParams.page = 1;
								s.baseParams.rows = 1000;
								s.baseParams.sort = "weight";
								s.baseParams.order = "desc";
							},
							load: function(s){
								//加载完后重新设置以显示中文名称
								var combo = me.__get_cmp("product_type_group");
								combo.setValue(combo.getValue());

								//首充代充的展示
								var groupName = utils.getComboDisplay(me.__get_cmp("product_type_group"));
								me._show_sc_dc(groupName);


								var idx = s.find("id", combo.getValue());
								if(idx != -1){
									var record = s.getAt(idx);
									var cmp = me.__get_cmp("product_type");
									if(cmp.store.baseParams.groupName == record.get("name"))
										return;

									cmp.store.baseParams.groupName = record.get("name");
									cmp.store.load();
								}
							}
						}
					}), displayField: "name", valueField: "id", listeners: {
						beforequery: function(qe){
	                        delete qe.combo.lastQuery;
	                    },
	                    render: function(qe){
							qe.store.load();
						},
						select: function(qe, record, index){
							var groupName = record.get("name");
							var cmp = me.__get_cmp("product_type");
							if(cmp.store.baseParams.groupName == groupName)
								return;

							cmp.setValue("");
							cmp.store.baseParams.groupName = groupName;

							//首充代充的展示
							me._show_sc_dc(groupName);
						}
					}},

					//首充白名单
					{id: me.__make_id("sc_whitelist"), xtype: "combo", fieldLabel: "首充白名单", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, store: [[0, "不添加"], [1, "添加"]], value: 1},

					//商品渠道
					{id: me.__make_id("product_channel"), xtype: "combo", fieldLabel: "商品渠道", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, value: 0, store: new Ext.data.JsonStore({
						url: utils.build_url("game/channels.do"),
						root: "rows",
						fields: [
							{name: 'id'},
						    {name: 'name'}
						],
						listeners: {
							beforeload: function(s){
								s.baseParams.page = 1;
								s.baseParams.rows = 1000;
								s.baseParams.sort = "weight";
								s.baseParams.order = "desc";
							},
							load: function(s){
								var channels = me.get_product_channels();
								s.filterBy(function(record, id){
		                        	for(var i=0; i<channels.length; ++i){
		                        		if(channels[i].channel_id == record.get("id")){
		                        			return true;
		                        		}
		                        	}
		                        	return false;
		                        });

		                        s.insert(0, new s.recordType({id:0, name:"无"}))


								//加载完后重新设置以显示中文名称
								var combo = me.__get_cmp("product_channel");
								combo.setValue(combo.getValue());
							}
						}
					}), displayField: "name", valueField: "id", listeners: {
						beforequery: function(qe){
	                        delete qe.combo.lastQuery;
	                    },
	                    render: function(qe){
							qe.store.load();
						},
					}},

					//商品类型前台显示
					{id: me.__make_id("type_name"), xtype: "textfield", fieldLabel: "前台显示名称", anchor: "95%", allowBlank: false},

					//最高商品价
					{id: me.__make_id("max_price"), xtype: "textfield", fieldLabel: "商品最高价格", anchor: "95%", allowBlank: false, value: "100"},

					//是否人工审核
					{id: me.__make_id("need_audit"), xtype: "combo", fieldLabel: "是否人工审核", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, store: [[0, "是"], [1, "否"]], value: 0},

					//是否分派订单
					{id: me.__make_id("auto_assign"), xtype: "combo", fieldLabel: "分派订单", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, store: [[0, "自动"], [1, "手动"]], value: 1},
				]
			});
			var form2 = new Ext.form.FormPanel({
				border: false,
				width: 400,
				padding: 8,
				items:[
					//商品类型
					{id: me.__make_id("product_type"), xtype: "combo", fieldLabel: "商品类型", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model:"remote",
					allowBlank: false, store: new Ext.data.JsonStore({
						url: utils.build_url("product/productTypeList_json"),
						totalProperty: "total",
						root: "rows",
						fields: [
							{name: "id"}, {name: "name"}, {name: "productTypeGroupName"}, {name: "product_type"}, {name: "status"}
						],
						listeners: {
							beforeload: function(s){
								if(s.baseParams.groupName == undefined || s.baseParams.groupName == ""){
									return false;
								}
								return true;
							},
							load: function(s){
								//加载完后重新设置以显示中文名称
								var combo = me.__get_cmp("product_type");
								combo.setValue(combo.getValue());
							}
						}
					}), displayField: "name", valueField: "id", listeners: {
						beforequery: function(qe){
	                        delete qe.combo.lastQuery;
	                    },
	                    select: function(qe){
	                    	me.__get_cmp("type_name").setValue(utils.getComboDisplay(qe));
	                    }
					}},

					//代充白名单
					{id: me.__make_id("dc_whitelist"), xtype: "combo", fieldLabel: "代充白名单", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, store: [[0, "不查验"], [1, "查验"]], value: 1},

					//商品类型权重
					{id: me.__make_id("weight"), xtype: "textfield", fieldLabel: "商品类型权重", anchor: "95%", value: "0", allowBlank: false},

					//单位
					{id: me.__make_id("unit"), xtype: "textfield", fieldLabel: "单位", anchor: "95%", allowBlank: false, value: "件"},

					//商品最低价
					{id: me.__make_id("min_price"), xtype: "textfield", fieldLabel: "商品最低价格", anchor: "95%", allowBlank: false, value: "1"},

					//是否支持发布
					{id: me.__make_id("need_publish"), xtype: "combo", fieldLabel: "是否支持发布", anchor: "95%", triggerAction: 'all', typeAhead : true, editable: false, model: "local",
					allowBlank: false, store: [[0, "支持"], [1, "不支持"]], value: 0},

					//限购件数
					{id: me.__make_id("quota"), xtype: "textfield", fieldLabel: "限购件数", anchor: "95%", allowBlank: false, value: "0"},
				]
			});

			var fieldset = new Ext.form.FieldSet({
				title: "基础信息",
				layout: "table",
				layoutConfig: {columns:2},
				anchor: "98%",
				items: [form1, form2],
			});

			fieldset.get_data = function(){
				var base_info = {};

				base_info.product_type_group = me.__get_cmp("product_type_group").getValue();
				base_info.product_type_group_display = utils.getComboDisplay(me.__get_cmp("product_type_group"));

				base_info.product_type = me.__get_cmp("product_type").getValue();
				base_info.product_type_display = utils.getComboDisplay(me.__get_cmp("product_type"));

				base_info.sc_whitelist = me.__get_cmp("sc_whitelist").getValue();
				base_info.dc_whitelist = me.__get_cmp("dc_whitelist").getValue();

				base_info.product_channel = me.__get_cmp("product_channel").getValue();
				base_info.product_channel_display = utils.getComboDisplay(me.__get_cmp("product_channel"));

				base_info.type_name = me.__get_cmp("type_name").getValue();
				base_info.max_price = me.__get_cmp("max_price").getValue();
				base_info.need_audit = me.__get_cmp("need_audit").getValue();
				base_info.weight = me.__get_cmp("weight").getValue();
				base_info.unit = me.__get_cmp("unit").getValue();
				base_info.min_price = me.__get_cmp("min_price").getValue();
				base_info.need_publish = me.__get_cmp("need_publish").getValue();
				base_info.auto_assign = me.__get_cmp("auto_assign").getValue();
				base_info.quota = me.__get_cmp("quota").getValue();
				return base_info;
			}
			fieldset.set_data = function(base_info){
				me.__get_cmp("product_type_group").setValue(base_info.product_type_group);
				me.__get_cmp("product_type").setValue(base_info.product_type);
				me.__get_cmp("sc_whitelist").setValue(base_info.sc_whitelist);
				me.__get_cmp("dc_whitelist").setValue(base_info.dc_whitelist);
				me.__get_cmp("product_channel").setValue(base_info.product_channel);
				me.__get_cmp("type_name").setValue(base_info.type_name);
				me.__get_cmp("max_price").setValue(base_info.max_price);
				me.__get_cmp("need_audit").setValue(base_info.need_audit);
				me.__get_cmp("weight").setValue(base_info.weight);
				me.__get_cmp("unit").setValue(base_info.unit);
				me.__get_cmp("min_price").setValue(base_info.min_price);
				me.__get_cmp("need_publish").setValue(base_info.need_publish);
				me.__get_cmp("auto_assign").setValue(base_info.auto_assign);
				me.__get_cmp("quota").setValue(base_info.quota);
			}

			return fieldset;
		},

		create_ext_info_panel: function(){
			var me = this;
			var form = new Ext.form.FormPanel({
				border: false,
				labelWidth: 130,
				items: [
					{id: me.__make_id("image_url"), xtype: "textfield", fieldLabel: "图片地址(商品详情页显示)", anchor: "95%"},
					{id: me.__make_id("id_display"), xtype: "textfield", fieldLabel: "ID前台展现名称", anchor: "95%"},
					{id: me.__make_id("role_display"), xtype: "textfield", fieldLabel: "角色前台展现名称", anchor: "95%"},
					{id: me.__make_id("account_display"), xtype: "textfield", fieldLabel: "账号前台展现名称", anchor: "95%"},
					{id: me.__make_id("password_display"), xtype: "textfield", fieldLabel: "密码前台展现名称", anchor: "95%"},
					{id: me.__make_id("safekey_display"), xtype: "textfield", fieldLabel: "安全锁前台展现名称", anchor: "95%"},
					{id: me.__make_id("area_display"), xtype: "textfield", fieldLabel: "分区前台展现名称", anchor: "95%"},
				]
			});

			var fieldset = new Ext.form.FieldSet({
				title: "扩展信息",
				collapsible: true,
				anchor: "98%",
				items: [form]
			});

			fieldset.get_data = function(){
				var ext_info = {
					image_url: me.__get_cmp("image_url").getValue(),
					id_display: me.__get_cmp("id_display").getValue(),
					role_display: me.__get_cmp("role_display").getValue(),
					account_display: me.__get_cmp("account_display").getValue(),
					password_display: me.__get_cmp("password_display").getValue(),
					safekey_display: me.__get_cmp("safekey_display").getValue(),
					area_display: me.__get_cmp("area_display").getValue(),
				};
				return ext_info
			}
			fieldset.set_data = function(ext_info){
				me.__get_cmp("image_url").setValue(ext_info.image_url);
				me.__get_cmp("id_display").setValue(ext_info.id_display);
				me.__get_cmp("role_display").setValue(ext_info.role_display);
				me.__get_cmp("account_display").setValue(ext_info.account_display);
				me.__get_cmp("password_display").setValue(ext_info.password_display);
				me.__get_cmp("safekey_display").setValue(ext_info.safekey_display);
				me.__get_cmp("area_display").setValue(ext_info.area_display);
			}

			return fieldset;
		},

		//商品自定义属性信息
		create_property_panel: function(title, color){
			var fields = Ext.data.Record.create([
				{name: "key"},
				{name: "key_type"},
				{name: "value"},
				{name: "value_type_limit"},
				{name: "value_required"},
				{name: "value_encode"},
				{name: "weight"},
				{name: "key_desc"},
        	]);
        	var sm = new xsm.XCheckboxSelectionModel();
			var grid = new Ext.grid.EditorGridPanel({
				loadMask : true,
                stripeRows : true,
                layout: "fit",
                autoHeight: true,
                clicksToEdit: 1,
                anchor: "98%",
                border: false,
                columnLines: true,
                view: new Ext.grid.GridView({
                    forceFit: true,
                    enableRowBody: true
                }),
                store: new Ext.data.JsonStore({
                	root: "data",
                	fields: fields
                }),
                sm: sm,
                columns: [
                	sm,
                    {header:"前台展现名称", dataIndex:"key", align:"center", width:40, editor: new Ext.form.TextField()},
                    {header:"字段类型", dataIndex:"key_type", align:"center", width:30, editor: new Ext.form.ComboBox({
                    	triggerAction: 'all', typeAhead : true, editable: false, model:"local", store: [[0, "文本"], [1, "数值"], [2, "单选"], [3, "多选"]],
                    	listeners: {
                    		select: function(combo, record, index){
                    			if(combo.getValue() == 0 || combo.getValue() == 1){
	                    			grid.getColumnModel().setColumnHeader(3, "限制(字数)");	
                    			}else{
	                    			grid.getColumnModel().setColumnHeader(3, "限制");
                    			}
                    		}
                    	}
                    }), renderer : function(value, metaData, record, rowIndex, colIndex, store){
                    	if(value == 0) return "文本";
                    	if(value == 1) return "数值";
                    	if(value == 2) return "单选";
                    	if(value == 3) return "多选";
                    }},
                    {header:"限制(字数)", dataIndex:"value_type_limit", align:"center", width:30, editor: new Ext.form.NumberField()},
                    {header:"候选值", dataIndex:"value", align:"center", width:30, editor: new Ext.form.TextField()},
                    {header:"是否必选", dataIndex:"value_required", align:"center", width:30, editor: new Ext.form.ComboBox({
                    	triggerAction: 'all', typeAhead : true, editable: false, model:"local", store: [[0, "否"], [1, "是"]]
                    }), renderer : function(value, metaData, record, rowIndex, colIndex, store){
                    	if(value == 0) return "否";
                    	if(value == 1) return "是";
                    }},
                    {header:"是否加密", dataIndex:"value_encode", align:"center", width:30, editor: new Ext.form.ComboBox({
                    	triggerAction: 'all', typeAhead : true, editable: false, model:"local", store: [[0, "否"], [1, "是"]]
                    }), renderer : function(value, metaData, record, rowIndex, colIndex, store){
                    	if(value == 0) return "否";
                    	if(value == 1) return "是";
                    }},
                    {header:"权重", dataIndex:"weight", align:"center", width:30, editor: new Ext.form.TextField()},
                    {header:"字段说明", dataIndex:"key_desc", align:"center", width:50, editor: new Ext.form.TextField()},
                ],
                tbar: new Ext.Toolbar({
                	style: "background:" + color,
                	items: [
	                	{xtype: "button", icon: "${icon_path}/add.png", text: "添加", handler: function(){
	                		var record = new fields({
	                			key: "",
	                			key_type: 0,
	                			value: "",
	                			value_type_limit: "",
	                			value_required: 0,
	                			value_encode: 0,
	                			weight: 100,
	                			key_desc: ""
	                		});
	                		grid.store.add([record]);
	                		grid.view.refresh();
	                	}},
	                	{xtype: "button", icon: "${icon_path}/delete.png", text: "删除", handler: function(){
	                		var records = grid.getSelectionModel().getSelections();
	                		if(records.length == 0){
	                			utils.show_msg("请先选择需要删除的属性信息");
	                			return;
	                		}
	                		for(var i=0; i<records.length; ++i){
		                		grid.store.remove(records[i]);
	                		}
	                	}}
	                ]
                })
			});

			var fieldset = new Ext.form.FieldSet({
				title: title,
				collapsible: true,
				anchor: "98%",
				items: [grid]
			});
			fieldset.get_data = function(){
				var records = grid.store.getRange();
				var data = [];
				for(var i=0; i<records.length; ++i){
					var el = records[i].data;
					if(el.key == "")
						continue;
					if(el.value_type_limit == "")
						el.value_type_limit = 0;

					data.push(el);
				}
				return data;
			}
			fieldset.set_data = function(data){
				for(var i=0; i<data.length; ++i){
					var record = new fields();
					record.set("key", data[i].key);
					record.set("key_type", data[i].key_type);
					record.set("value", data[i].value);
					record.set("value_type_limit", data[i].value_type_limit);
					record.set("value_required", data[i].value_required);
					record.set("value_encode", data[i].value_encode);
					record.set("weight", data[i].weight);
					record.set("key_desc", data[i].key_desc);

					grid.store.add([record]);
				}
			}

			return fieldset;
		},

		create_tip_panel: function(){
			var fields = Ext.data.Record.create([
				{name: "type"},
				{name: "message"},
        	]);
        	var sm = new xsm.XCheckboxSelectionModel();
			var grid = new Ext.grid.EditorGridPanel({
				loadMask : true,
                stripeRows : true,
                layout: "fit",
                autoHeight: true,
                clicksToEdit: 1,
                anchor: "98%",
                border: false,
                columnLines: true,
                sm: sm,
                view: new Ext.grid.GridView({
                    forceFit: true,
                    enableRowBody: true
                }),
                store: new Ext.data.JsonStore({
                	root: "data",
                	fields: fields
                }),
                columns: [
                	sm,
                    {header:"提示信息位置", dataIndex:"type", align:"center", width:20, editor: new Ext.form.ComboBox({
                    	triggerAction: 'all', typeAhead : true, editable: false, model:"local", 
                    	store: [[11, "(发布页)商品信息提示"], [12, "(发布页)账户信息提示"], [13, "(发布页)发布提示信息"], [21, "(详情页)商品提示信息"], [31, "支付成功页信息提示"]],
                    }), renderer: function(value, metaData, record, rowIndex, colIndex, store){
                    	if(value == 11) return "(发布页)商品信息提示";
                    	if(value == 12) return "(发布页)账户信息提示";
                    	if(value == 13) return "(发布页)发布提示信息";
                    	if(value == 21) return "(详情页)商品提示信息";
                    	if(value == 31) return "支付成功页信息提示";
                    }},
                    {header:"提示内容", dataIndex:"message", align:"center", width:50, editor: new Ext.form.TextField(), renderer : function(value, metaData, record, rowIndex, colIndex, store){
                    	return utils.htmlEncode(value);
                    }},
                ],
                tbar: [
                	{xtype: "button", icon: "${icon_path}/add.png", text: "添加", handler: function(){
                		var record = new fields({
                			type: 11,
                			message: ""
                		});
                		grid.store.add([record]);
                		grid.view.refresh();
                	}},
                	{xtype: "button", icon: "${icon_path}/delete.png", text: "删除", handler: function(){
                		var records = grid.getSelectionModel().getSelections();
                		if(records.length == 0){
                			utils.show_msg("请先选择需要删除的提示信息");
                			return;
                		}
                		for(var i=0; i<records.length; ++i){
	                		grid.store.remove(records[i]);
                		}
                	}}
                ]
			});

			var fieldset = new Ext.form.FieldSet({
				title: "自定义提示信息",
				collapsible: true,
				anchor: "98%",
				items: [grid]
			});

			fieldset.get_data = function(){
				var records = grid.store.getRange();
				var data = [];
				for(var i=0; i<records.length; ++i){
					var el = records[i].data;
					if(el.message == "")
						continue;

					data.push(el);
				}
				return data;
			}

			fieldset.set_data = function(data){
				for(var i=0; i<data.length; ++i){
					var record = new fields();
					record.set("type", data[i].type);
					record.set("message", data[i].message);
					grid.store.add([record]);
				}
			}

			return fieldset;
		},

		//获取数据
		get_data: function(){
			var data = {};
			data.base_info = this.base_info_panel.get_data();
			data.ext_info = this.ext_info_panel.get_data();
			data.prod_info = this.prod_info_panel.get_data();
			data.saler_info = this.saler_info_panel.get_data();
			data.buy_info = this.buy_info_panel.get_data();
			data.ty_info = this.ty_info_panel.get_data();
			data.tip_info = this.tip_info_panel.get_data();

			return data;
		},

		is_valid: function(data){
			if(data.base_info.product_type == ""){
				utils.show_msg("没有选择商品类型");
				return false;
			}

			var infos = [data.prod_info, data.saler_info, data.buy_info, data.ty_info];
			for(var k=0; k<infos.length; ++k){
				for(var i=0; i<infos[k].length; ++i){
					var info = infos[k][i];

					//没配置key的数据就不需要检验了，反正会忽略掉的
					if(info.key == "")
						continue;

					//文本和数值，要求必填“限制”字段
					if(info.key_type == 0 || info.key_type == 1){
						if(info.value_type_limit == ""){
							utils.show_msg("属性配置中，文本和数值类型必须填写限制字段。")
							return false;
						}
					}
				}
			}

			return true;
		},

		//设置数据及界面
		set_data: function(data){
			this.base_info_panel.set_data(data.base_info);
			this.ext_info_panel.set_data(data.ext_info);
			this.prod_info_panel.set_data(data.prod_info);
			this.saler_info_panel.set_data(data.saler_info);
			this.buy_info_panel.set_data(data.buy_info);
			this.ty_info_panel.set_data(data.ty_info);
			this.tip_info_panel.set_data(data.tip_info);
		},

		//to be implemented
		get_product_channels: function(){
		},

		//to be implemented
		onAccept: function(data){
		}
	});

	exports.GameProductTypeEditWindow = GameProductTypeEditWindow;
});