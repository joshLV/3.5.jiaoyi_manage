Ext.define('app.view.menuAccess.Toolbar', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.menuAccessToolbar',
    //layout: 'box',
    width: '100%',
    //margin: '0',

    items: [{
        	xtype: 'menuAccessComboBox', 
        },{
            xtype    : 'datefield',
            fieldLabel: '时间',
            name     : 'startDate',
            format: 'Y-m-d',
            editable:false,
            maxValue: new Date(),
            emptyText: '开始时间'
        },{
        	xtype: 'tbtext', 
        	text: '&nbsp;-&nbsp;&nbsp;&nbsp;'
        },{
            xtype    : 'datefield',
            name     : 'endDate',
            format: 'Y-m-d',
            editable:false,
            maxValue: new Date(),
            emptyText: '结束时间'
        },{
        	xtype: 'tbtext', 
        	text: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        },{
            // xtype: 'button', // default for Toolbars
            text: '查询',
            action: 'filter'
        },{
            // xtype: 'button', // default for Toolbars
            text: '重置',
            action: 'reset'
        }
    ],
    initComponent: function() {
        this.callParent(arguments);
    }
});