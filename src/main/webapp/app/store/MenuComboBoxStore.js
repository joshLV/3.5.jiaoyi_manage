Ext.define('app.store.MenuComboBoxStore', {
    extend: 'Ext.data.Store',
    autoLoad: true,
    fields: ['MOD_ID', 'MODULE_NAME'],
	proxy: {
	    type: 'ajax',
	    api: {
	        read: ctx + '/system/menuAccess_menuData'
	    },
	    reader: {
	        type: 'json',
	        root: 'data',
	        successProperty: 'success'
	    }
	}
});