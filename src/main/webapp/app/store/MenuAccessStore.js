Ext.define('app.store.MenuAccessStore', {
    extend: 'Ext.data.Store',
    model: 'app.model.MenuAccessModel',
    autoLoad: true,
    remoteSort: false,
    proxy: {
        type: 'ajax',
        api: {
            read: ctx + '/system/menuAccess_listDataExt'
        },
        reader: {
            type: 'json',
            root: 'items',
            successProperty: 'success'
        }
    }
});