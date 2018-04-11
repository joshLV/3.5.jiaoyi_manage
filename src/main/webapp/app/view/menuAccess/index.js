Ext.application({
    requires: 'Ext.container.Viewport',
    name: 'app',
    appFolder: ctx+'/app',
    controllers: [
        'MenuAccessController'
    ],
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: {
                xtype: 'menuAccessList'
            }
        });
    }
});