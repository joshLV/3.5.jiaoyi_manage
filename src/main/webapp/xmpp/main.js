//require.config({
//    paths: {
//        "jquery": "components/jquery/jquery",
//        "jquery.md5": "components/jquery.md5/jquery.md5",
//        "jquery.tinysort": "components/tinysort/src/jquery.tinysort",
//        "underscore": "components/underscore/underscore",
//        "backbone": "components/backbone/backbone",
//        "backbone.localStorage": "components/backbone.localStorage/backbone.localStorage",
//        "strophe": "components/strophe/strophe",
//        "strophe.roster": "components/strophe.roster/index",
//        "strophe.vcard": "components/strophe.vcard/index",
//        "strophe.disco": "components/strophe.disco/index"
//    },
//
//    // define module dependencies for modules not using define
//    shim: {
//        'backbone': {
//            //These script dependencies should be loaded before loading
//            //backbone.js
//            deps: [
//                'underscore'
//                ,'jquery'
//                ],
//            //Once loaded, use the global 'Backbone' as the
//            //module value.
//            exports: 'Backbone'
//        },
//        'jquery.md5': { deps: ['jquery'] },
//        'jquery.tinysort': { deps: ['jquery'] },
//        'strophe': { deps: ['jquery'] },
//        'underscore':   { exports: '_' },
//        'strophe.roster':   { deps: ['strophe'] },
//        'strophe.vcard':    { deps: ['strophe'] },
//        'strophe.disco':    { deps: ['strophe'] }
//    }
//});
//
//require(["jquery", "jquery.md5", "converse"], function(require, $, converse) {
//    window.converse = converse;
//});

//不需要jquery的依赖，在主文档中已经引用
require.config({
    paths: {
        "jquery.md5": "components/jquery.md5/jquery.md5",
        "jquery.tinysort": "components/tinysort/src/jquery.tinysort",
        "underscore": "components/underscore/underscore",
        "backbone": "components/backbone/backbone",
        "backbone.localStorage": "components/backbone.localStorage/backbone.localStorage",
        "strophe": "components/strophe/strophe",
        "strophe.roster": "components/strophe.roster/index",
        "strophe.vcard": "components/strophe.vcard/index",
        "strophe.disco": "components/strophe.disco/index"
    },

    // define module dependencies for modules not using define
    shim: {
        'backbone': {
            //These script dependencies should be loaded before loading
            //backbone.js
            deps: [
                'underscore'
                //,'jquery'
                ],
            //Once loaded, use the global 'Backbone' as the
            //module value.
            exports: 'Backbone'
        },
        'jquery.md5': { },
        'jquery.tinysort': { },
        'strophe': { },
        'underscore':   { exports: '_' },
        'strophe.roster':   { deps: ['strophe'] },
        'strophe.vcard':    { deps: ['strophe'] },
        'strophe.disco':    { deps: ['strophe'] }
    }
});

require([ "jquery.md5", "converse"], function(require, $, converse) {
    window.converse = converse;
});
