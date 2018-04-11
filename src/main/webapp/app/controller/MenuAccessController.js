Ext.define('app.controller.MenuAccessController', {
    extend: 'Ext.app.Controller',
    stores: [
        'MenuAccessStore',
        'MenuComboBoxStore'
    ],
    models: [
        'MenuAccessModel'
    ],
    views: [
        'menuAccess.Toolbar',
        'menuAccess.List',
        'menuAccess.MenuComboBox'
    ],
    init: function() {
        this.control({
            'menuAccessToolbar button[action=filter]': {
                click: this.updateFilter
            },'menuAccessToolbar button[action=reset]': {
                click: this.reset
            }
        });
    },
    updateFilter: function(button) {
        var grid = button.up('grid'),
            form = button.up('toolbar'),
            store = grid.getStore();
        store.proxy.extraParams = {
            startDate: form.down('[name=startDate]').getValue(),
            endDate: form.down('[name=endDate]').getValue(),
            menuId: form.down('[name=menuId]').getValue()
        };
        store.reload();
    },
    reset: function(button) {
        var form = button.up('toolbar');
        form.down('[name=menuId]').reset();
        form.down('[name=startDate]').reset();
        form.down('[name=endDate]').reset();
        this.updateFilter(button);
    }
});