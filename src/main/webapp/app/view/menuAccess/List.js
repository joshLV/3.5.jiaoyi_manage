Ext.Loader.setPath('Ext.ux', resourcesPath+'/extjs4/ux/');

Ext.require([
    'Ext.ux.form.SearchField'
]);

Ext.require([
	'Ext.ux.grid.column.ActionButtonColumn'
]);
//var sm = Ext.create('Ext.selection.CheckboxModel',{
//    checkOnly:true
//});

Ext.define('app.view.menuAccess.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.menuAccessList',
    title : '菜单访问统计',
    store: 'MenuAccessStore',
    dockedItems: [{
        dock: 'top',
        xtype: 'toolbar',
        items: [{xtype: 'menuAccessToolbar'}]
    }],
//    selModel: sm,
//    tbar: [{ xtype: 'button', name: 'add', text: '添加', icon:resourcesPath+'/extjs/resources/icons/add.png'},
//        { xtype: 'button', name: 'delete', text: '删除', icon:resourcesPath+'/extjs/resources/icons/delete.png'},
//        { xtype: 'button', name: 'edit', text: '编辑', icon:resourcesPath+'/extjs/resources/icons/pencil.png'}],
//    bbar: { xtype: "pagingtoolbar", store: 'MenuAccessStore', displayInfo: true },
    initComponent: function() {
        this.columns = [
            {xtype: "rownumberer",header: '序号',  width:50},
            {header: '父模块名称', dataIndex: 'parent_mod_name', flex: 1, sortable: true},
            {header: '模块名称', dataIndex: 'mod_name', flex: 1, sortable: true},
            {header: '点击量', dataIndex: 'total', flex: 1, sortable: true}
        ];

        this.callParent(arguments);
    }
});