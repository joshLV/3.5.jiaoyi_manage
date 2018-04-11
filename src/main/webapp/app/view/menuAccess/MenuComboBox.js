Ext.define('app.view.menuAccess.MenuComboBox', {
    extend: 'Ext.form.ComboBox',
    alias: 'widget.menuAccessComboBox',
    name:'menuId',
    editable:false,
	fieldLabel: '模块',
    store:'MenuComboBoxStore',
    displayField: 'MODULE_NAME',
    valueField: 'MOD_ID'
});