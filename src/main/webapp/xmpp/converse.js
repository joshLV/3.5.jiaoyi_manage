var WX_SERVER_BASE_URL = "http://120.31.134.11/weixin/interfaces/";
var WX_SERVER_URL_SWITCH_CUSTOMER = WX_SERVER_BASE_URL + "api_switch_customer.php";
var WX_SERVER_URL_GET_INFO_BY_USER_NAME = WX_SERVER_BASE_URL + "api_get_info_by_username.php";
var WX_SERVER_URL_QUIT_MODE = WX_SERVER_BASE_URL + "api_user_quit_mode.php";
var WX_SERVER_URL_SET_C2C_MODE = WX_SERVER_BASE_URL + "api_user_set_c2c_mode.php";
var WX_SERVER_URL_GET_INFO_BY_UID = WX_SERVER_BASE_URL + "api_get_info_by_uid.php";

var XMPP_API_BASE_URL = "http://120.31.134.11/xmpp/api/";
var XMPP_API_IS_LOGIN_URL = XMPP_API_BASE_URL + "islogin.php";
var XMPP_API_START_USER_CLIENT_URL = XMPP_API_BASE_URL + "start_user_client.php";
var XMPP_API_GET_CHAT_HISTORY_URL = XMPP_API_BASE_URL + "get_chat_history.php";

var JUGAME_BASE_URL = 'http://14.17.121.180:8281/JIAOYI_MANAGE/webDate/';
var JUGAME_URL_QUERY_ORDERS_BY_BUY_UID = JUGAME_BASE_URL + 'queryOrdersByBuyUid.jsp';
var JUGAME_URL_QUERY_ORDER_BY_ORDER_ID = JUGAME_BASE_URL + 'queryOrderByOid.jsp';
var JUGAME_ENCODE_KEY ='sereblwerijwerie';

var PROXY_BASE_URL = "http://120.31.134.11/tools/jsonp_proxy.php";

var DOMAIN = "120.31.134.11";
var XMPP_DEFALUT_PASS = "user8868";

var CODE_SUCC = 0; //成功状态码
var CODE_FAIL = 1;

var ORDER_STATUS = {
    '0': '未付款',
    '2': '未发货',
    '4': '已发货，待收货',
    '6': '已收货，订单成功',
    '7': '确认收货，待转钱给卖家',
    '8': '订单失败',
    '10': '已退费'
};

var GOODS_TYPE = {
    '1'  : '虚拟游戏币',
    '99' : '魔晶',
    '101': '装备',
    '102': '物品'
};

var JUGAME_ENCODE_KEY ='sereblwerijwerie';

var sharedChatBoxesView;

function xmppDesktopNotice(msg) {
    var m = window.webkitNotifications.createNotification(
        "http://ww4.sinaimg.cn/mw690/622a530dgw1ecke2bq3bsj204c04cq2w.jpg",
        '有新消息~',
        '少年，有人呼唤你' 
    );

    m.show();
}


// 格式化日期的方法
function dateFormat(date, format) {
    if(format === undefined){
        format = date;
        date = new Date();
    }

    var map = {
        "M": date.getMonth() + 1, //月份 
        "d": date.getDate(), //日 
        "h": date.getHours(), //小时 
        "m": date.getMinutes(), //分 
        "s": date.getSeconds(), //秒 
        "q": Math.floor((date.getMonth() + 3) / 3), //季度 
        "S": date.getMilliseconds() //毫秒 
    };
    format = format.replace(/([yMdhmsqS])+/g, function(all, t){
        var v = map[t];
        if(v !== undefined){
            if(all.length > 1){
                v = '0' + v;
                v = v.substr(v.length-2);
            }
            return v;
        }
        else if(t === 'y'){
            return (date.getFullYear() + '').substr(4 - all.length);
        }
        return all;
    });
    return format;
}

// 代理请求方法，解决跨域问题
function jsonpProxyGet(url, callback) {
    console.log("proxy get url:" + url);

    url = PROXY_BASE_URL + '?url=' + encodeURIComponent(url);
    jQuery.getJSON(url + "&callback=?", callback); 
}


// 通过 userName 获取 uid
function getUidWithUserName(userName, callback) {
    var url = WX_SERVER_URL_GET_INFO_BY_USER_NAME + "?username=" + encodeURIComponent(userName);
    jsonpProxyGet(url, function (data) {
        var code = data.code
        if (code == CODE_SUCC) {
            var uid = data.uid;
            if (uid > 0) {
                callback(uid);
                return;
            }
        }

        callback(0);
    });
}

// 通过 uid 获取包括 userName、name 等信息
function getInfoWithUid(uid, callback) {
    var url = WX_SERVER_URL_GET_INFO_BY_UID + "?uid=" + uid;
    jsonpProxyGet(url, function(data) {
        if (typeof data.code != undefined) {
            callback(data);
        }
    });
}

// 让用户退出客服模式，达到断线的效果
function quitMode(userName, callback) {
    var url = WX_SERVER_URL_QUIT_MODE + "?username=" + encodeURIComponent(userName) + "&is_send_quit_hint=1";
    jsonpProxyGet(url, function(data) {
        if (typeof data.code != undefined) {
            callback(data.code);
        }
    });
}

// 切换客服
function switchCustomer(userName, switchToCustiomer, callback) {
    var url = WX_SERVER_URL_SWITCH_CUSTOMER + "?username=" + encodeURIComponent(userName) + "&switch_to_customer=" + encodeURIComponent(switchToCustiomer);
    console.log("switchToCustiomer url:" + url);
    jsonpProxyGet(url, function(data) {
        if (typeof data.code != undefined) {
            callback(data.code);
        }
    });
}

// 让用户在 XMPP 上线的接口
function startUserClient(userName, callback) {
    var url = XMPP_API_START_USER_CLIENT_URL + "?username=" + encodeURIComponent(userName);
    jsonpProxyGet(url, function(data) {
        if(!callback) {
            return;
        }

        if (typeof data.code != undefined) {
            callback(data.code);
        } else {
            callback(CODE_FAIL);
        }
    });
}


//设置用户的C2C模式
//注意: 设置之后用户的菜单就被锁定了,  仅在主动联系用户的时候使用
function setUserC2CMode(userName) {
    var url = WX_SERVER_URL_SET_C2C_MODE + "?username=" + encodeURIComponent(userName);
    jsonpProxyGet(url, function(data){
        console.log("setUserC2CMode:" + data);
    });
}

// 检查用户是否登录xmpp，如果未登录，则帮用户登录，在打开对话框的时候使用
function userLoginXMPP(userName, callback) {
    var url = XMPP_API_IS_LOGIN_URL + "?username=" + encodeURIComponent(userName);
    jsonpProxyGet(url, function(data) {
        if(typeof data.is_login != undefined) {
            if(data.is_login == 1) {   //1是登陆状态
                callback(CODE_SUCC);
            } else {
                startUserClient(userName, function(code) {
                    if (code != undefined) {
                        callback(code);
                    } else {
                        callback(CODE_FAIL);
                    }
                });
            }
        } else {
            callback(CODE_FAIL);
        }
    });
}


function getOrderWithOrderId(orderId, callback) {
    var encodeStr = orderId + JUGAME_ENCODE_KEY;
    var encodeKey = $.md5(encodeStr);
    var url = JUGAME_URL_QUERY_ORDER_BY_ORDER_ID + "?order_id=" + encodeURIComponent(orderId) + "&encodeKey=" + encodeURIComponent(encodeKey);
    console.log('orderurl:' + url);
    jsonpProxyGet(url, function(data) {
        if (typeof data.data != undefined) {
            callback(data.data);
        }
    });
}

function getOrdersWithUserName(userName, callback) {
    console.log("getOrdersWithUserName:" + userName);

    var firstSuccOrder = null;
    var firstWatiGoodsOrder = null;

    getUidWithUserName(userName, function(uid) {
        if (uid > 0) {
            var encodeStr = uid + JUGAME_ENCODE_KEY;
            var encodeKey = $.md5(encodeStr);
            var proxyURL = JUGAME_URL_QUERY_ORDERS_BY_BUY_UID + '?order_buy_uid=' + uid
                    + '&encodeKey=' + encodeURIComponent(encodeKey);

            jsonpProxyGet(proxyURL, function(data) {
                console.log("data", data);
                var result = data.result;
                if (result == CODE_SUCC) {
                    var orderData = data.data;
                    if (orderData.length > 0) {
                        firstWatiGoodsOrder = orderData[0];
                        firstWatiGoodsOrder.uid = uid ;
                    }
                }

                if (firstWatiGoodsOrder != null || firstSuccOrder != null) {
                    var toShowOrder = (firstWatiGoodsOrder != null) ? firstWatiGoodsOrder : firstSuccOrder;
                    callback(toShowOrder);
                } else {
                    callback(undefined);
                }
            });
        }
    });

}

function contactBuyer(uid, orderId) {
    getInfoWithUid(uid, function(data) {

        if(data.code == undefined || data.code !== 0) {
            alert('查不到相关微信信息, 不能联系用户:' + uid);
            return;
        }

        var userName = data.username;

        //启动用户的虚拟客户端
        startUserClient(userName);


        if (userName != undefined) {
            var nickname = (data.name == undefined) ? data.name : userName;
            var jid = userName + '@' + DOMAIN;

            //臭傻逼openfire把这个id全弄成小写了，所以没办法，需要hardcode，id转小写
            sharedChatBoxesView.showChatBox({
                            'id': jid.toLowerCase(),
                            'jid': jid.toLowerCase(),
                            'fullname': nickname,
                            'image_type': '',
                            'image': '',
                            'url': '',
                            'status': '', 
                            'order_id': orderId
            });
        }
    });
}

function showOrderInfo($el, order) {
    console.log('order:' + JSON.stringify(order));
    var $orderDiv = $el.find('div.chatbox-left');
    if (order != undefined && order.order_id != undefined) {
        var orderId = order.order_id; 
        var orderStatus = order.order_status;
        orderStatus = ORDER_STATUS[orderStatus];
        var uid = order.uid || order.order_buy_uid; 
        var orderAmount = order.order_amount; 
        var goodsCount = order.goods_count; 
        var goodsPrice = order.goods_price; 
        var goodsType = order.goods_type; 
        goodsType = GOODS_TYPE[goodsType];
        var gameName = order.game_name; 
        var gameAreaName = order.game_area_name; 
        var orderTime = order.order_time; 
        var getGoodsTime = order.get_goods_time; 

        $orderDiv.append('<div class="order-info">' +
            '订单号:' + orderId 
            + '<br/><br/> 买家UID:'  + uid 
            + '<br/><br/> 订单状态:' + orderStatus
            + '<br/><br/> 订单金额:' + orderAmount
            + '<br/><br/> 商品数量:' + goodsCount 
            + '<br/><br/> 商品价格:' + goodsPrice
            + '<br/><br/> 商品类型:' + goodsType 
            + '<br/><br/> 游戏名字:' + gameName
            + '<br/><br/> 游戏分区:' + gameAreaName 
            + '<br/><br/> 下单时间:' + orderTime + '</div>');
    } else {
        $orderDiv.append('无订单');
    }
}

// 获取与某用户的聊天记录
function getChatHistory(userName, callback) {
    var proxyURL = XMPP_API_GET_CHAT_HISTORY_URL + '?username=' + encodeURIComponent(userName);

    jsonpProxyGet(proxyURL, function(data) {
        console.log("data:", data);
        var result = data.code;
        if (result == CODE_SUCC) {
            var historyData = data.data;
            if (historyData.length > 0) {
                callback(historyData);
            }
        } else {
            callback(undefined);
        }
    });
}


/*!
 * Converse.js (Web-based XMPP instant messaging client)
 * http://conversejs.org
 *
 * Copyright (c) 2012, Jan-Carel Brand <jc@opkode.com>
 * Dual licensed under the MIT and GPL Licenses
 */

// AMD/global registrations
(function (root, factory) {
    if (typeof console === "undefined" || typeof console.log === "undefined") {
        console = { log: function () {}, error: function () {} };
    }
    if (typeof define === 'function' && define.amd) {
        define("converse", [
            "backbone.localStorage",
            "jquery.tinysort",
            "strophe",
            "strophe.roster",
            "strophe.vcard",
            "strophe.disco"
            ], function() {
                // Use Mustache style syntax for variable interpolation
                _.templateSettings = {
                    evaluate : /\{\[([\s\S]+?)\]\}/g,
                    interpolate : /\{\{([\s\S]+?)\}\}/g
                };
                return factory(jQuery, _, console);
            }
        );
    } else {
        // Browser globals
        _.templateSettings = {
            evaluate : /\{\[([\s\S]+?)\]\}/g,
            interpolate : /\{\{([\s\S]+?)\}\}/g
        };
        root.converse = factory(jQuery, _, console || {log: function(){}});
    }
}(this, function ($, _, console) {
    var converse = {};
    converse.initialize = function (settings, callback) {
        var converse = this;

        // Constants
        // ---------
        var UNENCRYPTED = 0;
        var UNVERIFIED= 1;
        var VERIFIED= 2;
        var FINISHED = 3;
        var KEY = {
            ENTER: 13
        };

        // Default configuration values
        // ----------------------------
        this.allow_contact_requests = false;
        this.allow_muc = false;
        this.allow_otr = false;
        this.animate = true;
        this.auto_list_rooms = false;
        this.auto_subscribe = true;
        this.bosh_service_url = undefined; // The BOSH connection manager URL.
        this.debug = false;
        this.hide_muc_server = false;
        this.prebind = false;
        this.show_controlbox_by_default = false;
        this.show_only_online_users = false;
        this.show_emoticons = true;
        this.show_toolbar = true;
        this.xhr_custom_status = false;
        this.xhr_custom_status_url = '';
        this.xhr_user_search = false;
        this.xhr_user_search_url = '';

        // Allow only whitelisted configuration attributes to be overwritten
        _.extend(this, _.pick(settings, [
            'allow_contact_requests',
            'allow_muc',
            'allow_otr',
            'animate',
            'auto_list_rooms',
            'auto_subscribe',
            'bosh_service_url',
            'connection',
            'debug',
            'fullname',
            'hide_muc_server',
            'jid',
            'prebind',
            'rid',
            'show_controlbox_by_default',
            'show_emoticons',
            'show_only_online_users',
            'show_toolbar',
            'sid',
            'xhr_custom_status',
            'xhr_custom_status_url',
            'xhr_user_search',
            'xhr_user_search_url'
        ]));

       var STATUSES = {
            'dnd': 'This contact is busy',
            'online': 'This contact is online',
            'offline': 'This contact is offline',
            'unavailable': 'This contact is unavailable',
            'xa': 'This contact is away for an extended period',
            'away': 'This contact is away'
        };


        // Module-level variables
        // ----------------------
        this.callback = callback || function () {};
        this.initial_presence_sent = 0;
        this.msg_counter = 0;

        // Module-level functions
        // ----------------------
        this.autoLink = function (text) {
            // Convert URLs into hyperlinks
            var re = /((http|https|ftp):\/\/[\w?=&.\/\-;#~%\-]+(?![\w\s?&.\/;#~%"=\-]*>))/g;
            return text.replace(re, '<a target="_blank" href="$1">$1</a>');
        };

        this.giveFeedback = function (message, klass) {
            $('.conn-feedback').text(message);
            $('.conn-feedback').attr('class', 'conn-feedback');
            if (klass) {
                $('.conn-feedback').addClass(klass);
            }
        };

        this.log = function (txt, level) {
            if (this.debug) {
                if (level == 'error') {
                    console.log('ERROR: '+txt);
                } else {
                    console.log(txt);
                }
            }
        };

        this.getVCard = function (jid, callback, errback) {
            converse.connection.vcard.get(
                $.proxy(function (iq) {
                    // Successful callback
                    var $vcard = $(iq).find('vCard');
                    var fullname = decodeURIComponent($vcard.find('NICKNAME').text()),
                        img = $vcard.find('BINVAL').text(),
                        img_type = $vcard.find('TYPE').text(),
                        url = $vcard.find('URL').text();
                    if (jid) {
                        var rosteritem = converse.roster.get(jid);
                        console.log('rosteritem :', rosteritem);
                        if (rosteritem) {
                            fullname = _.isEmpty(fullname)? rosteritem.get('fullname') || jid: fullname;
                            rosteritem.save({
                                'fullname': fullname,
                                'image_type': img_type,
                                'image': img,
                                'url': url,
                                'vcard_updated': converse.toISOString(new Date())
                            });
                        }
                    }
                    if (callback) {
                        callback(jid, fullname, img, img_type, url);
                    }
                }, this),
                jid,
                function (iq) {
                    // Error callback
                    var rosteritem = converse.roster.get(jid);
                    if (rosteritem) {
                        rosteritem.save({
                            'vcard_updated': converse.toISOString(new Date())
                        });
                    }
                    if (errback) {
                        errback(iq);
                    }
                });
        };

        this.onConnect = function (status) {
            var $button, $form;
            if (status === Strophe.Status.CONNECTED) {
                converse.log('Connected');
                converse.onConnected();
            } else if (status === Strophe.Status.DISCONNECTED) {
                $form = $('#converse-login');
                $button = $form.find('input[type=submit]');
                if ($button) { $button.show().siblings('span').remove(); }
                converse.giveFeedback('连接断开', 'error');
                converse.connection.connect(
                    converse.connection.jid,
                    converse.connection.pass,
                    converse.onConnect
                );
            } else if (status === Strophe.Status.Error) {
                $form = $('#converse-login');
                $button = $form.find('input[type=submit]');
                if ($button) { $button.show().siblings('span').remove(); }
                converse.giveFeedback('错误', 'error');
            } else if (status === Strophe.Status.CONNECTING) {
                converse.giveFeedback('连接服务器...');
            } else if (status === Strophe.Status.CONNFAIL) {
                converse.chatboxesview.views.controlbox.trigger('connection-fail');
                converse.giveFeedback('连接失败', 'error');
            } else if (status === Strophe.Status.AUTHENTICATING) {
                converse.giveFeedback('验证账号...');
            } else if (status === Strophe.Status.AUTHFAIL) {
                converse.chatboxesview.views.controlbox.trigger('auth-fail');
                converse.giveFeedback('验证账号失败', 'error');
            } else if (status === Strophe.Status.DISCONNECTING) {
                converse.giveFeedback('断开连接...', 'error');
            } else if (status === Strophe.Status.ATTACHED) {
                converse.log('Attached');
                converse.onConnected();
            }
        };

        this.toISOString = function (date) {
            var pad;
            if (typeof date.toISOString !== 'undefined') {
                return date.toISOString();
            } else {
                // IE <= 8 Doesn't have toISOStringMethod
                pad = function (num) {
                    return (num < 10) ? '0' + num : '' + num;
                };
                return date.getUTCFullYear() + '-' +
                    pad(date.getUTCMonth() + 1) + '-' +
                    pad(date.getUTCDate()) + 'T' +
                    pad(date.getUTCHours()) + ':' +
                    pad(date.getUTCMinutes()) + ':' +
                    pad(date.getUTCSeconds()) + '.000Z';
            }
        };

        this.parseISO8601 = function (datestr) {
            /* Parses string formatted as 2013-02-14T11:27:08.268Z to a Date obj.
            */
            var numericKeys = [1, 4, 5, 6, 7, 10, 11],
                struct = /^\s*(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}\.?\d*)Z\s*$/.exec(datestr),
                minutesOffset = 0,
                i, k;

            for (i = 0; (k = numericKeys[i]); ++i) {
                struct[k] = +struct[k] || 0;
            }
            // allow undefined days and months
            struct[2] = (+struct[2] || 1) - 1;
            struct[3] = +struct[3] || 1;
            if (struct[8] !== 'Z' && struct[9] !== undefined) {
                minutesOffset = struct[10] * 60 + struct[11];

                if (struct[9] === '+') {
                    minutesOffset = 0 - minutesOffset;
                }
            }
            return new Date(Date.UTC(struct[1], struct[2], struct[3], struct[4], struct[5] + minutesOffset, struct[6], struct[7]));
        };

        this.updateMsgCounter = function () {
            if (this.msg_counter > 0) {
                if (document.title.search(/^Messages \(\d+\) /) == -1) {
                    document.title = "Messages (" + this.msg_counter + ") " + document.title;
                } else {
                    document.title = document.title.replace(/^Messages \(\d+\) /, "Messages (" + this.msg_counter + ") ");
                }
                window.blur();
                window.focus();
            } else if (document.title.search(/^Messages \(\d+\) /) != -1) {
                document.title = document.title.replace(/^Messages \(\d+\) /, "");
            }
        };

        this.incrementMsgCounter = function () {
            this.msg_counter += 1;
            this.updateMsgCounter();
        };

        this.clearMsgCounter = function () {
            this.msg_counter = 0;
            this.updateMsgCounter();
        };

        this.initStatus = function (callback) {
            this.xmppstatus = new this.XMPPStatus();
            var id = hex_sha1('converse.xmppstatus-'+this.bare_jid);
            this.xmppstatus.id = id; // This appears to be necessary for backbone.localStorage
            this.xmppstatus.localStorage = new Backbone.LocalStorage(id);
            this.xmppstatus.fetch({success: callback, error: callback});
        };

        this.initRoster = function () {
            // Set up the roster
            this.roster = new this.RosterItems();
            this.roster.localStorage = new Backbone.LocalStorage(
                hex_sha1('converse.rosteritems-'+converse.bare_jid));

            // Register callbacks that depend on the roster
            this.connection.roster.registerCallback(
                $.proxy(this.roster.rosterHandler, this.roster),
                null, 'presence', null);

            this.connection.addHandler(
                $.proxy(this.roster.subscribeToSuggestedItems, this.roster),
                'http://jabber.org/protocol/rosterx', 'message', null);

            this.connection.addHandler(
                $.proxy(function (presence) {
                    this.presenceHandler(presence);
                    return true;
                }, this.roster), null, 'presence', null);

            // No create the view which will fetch roster items from
            // localStorage
            this.rosterview = new this.RosterView({'model':this.roster});
        };

        this.onConnected = function () {
            if (this.debug) {
                this.connection.xmlInput = function (body) { console.log(body); };
                this.connection.xmlOutput = function (body) { console.log(body); };
                Strophe.log = function (level, msg) { console.log(level+' '+msg); };
                Strophe.error = function (msg) { console.log('ERROR: '+msg); };
            }
            this.bare_jid = Strophe.getBareJidFromJid(this.connection.jid);
            this.domain = Strophe.getDomainFromJid(this.connection.jid);
            this.features = new this.Features();
            this.initStatus($.proxy(function () {
                this.initRoster();
                this.chatboxes.onConnected();
                this.connection.roster.get(function () {});

                $(document).click(function() {
                    if ($('.toggle-otr ul').is(':visible')) {
                        $('.toggle-otr ul', this).slideUp();
                    }
                    if ($('.toggle-smiley ul').is(':visible')) {
                        $('.toggle-smiley ul', this).slideUp();
                    }
                });


                $(window).on("blur focus", $.proxy(function(e) {
                    if ((this.windowState != e.type) && (e.type == 'focus')) {
                        converse.clearMsgCounter();
                    }
                    this.windowState = e.type;
                },this));
                this.giveFeedback('在线联系人');

                if (this.callback) {
                    this.callback();
                }
            }, this));
        };

        // Backbone Models and Views
        // -------------------------
        this.Message = Backbone.Model.extend();

        this.Messages = Backbone.Collection.extend({
            model: converse.Message
        });

        this.ChatBox = Backbone.Model.extend({
            initialize: function () {
                if (this.get('box_id') !== 'controlbox') {
                    //if (_.contains([UNVERIFIED, VERIFIED], this.get('otr_status'))) {
                    //    this.initiateOTR();
                    //}
                    this.messages = new converse.Messages();
                    this.messages.localStorage = new Backbone.LocalStorage(
                        hex_sha1('converse.messages'+this.get('jid')+converse.bare_jid));
                    this.set({
                        'user_id' : Strophe.getNodeFromJid(this.get('jid')),
                        'box_id' : hex_sha1(this.get('jid')),
                        'otr_status': this.get('otr_status') || UNENCRYPTED
                    });
                }
            },

            createMessage: function (message) {
                var $message = $(message),
                    body = converse.autoLink($message.children('body').text()),
                    from = Strophe.getBareJidFromJid($message.attr('from')),
                    composing = $message.find('composing'),
                    delayed = $message.find('delay').length > 0,
                    fullname = this.get('fullname'),
                    stamp, time, sender;
                fullname = (_.isEmpty(fullname)? from: fullname).split(' ')[0];

                if (!body) {
                    if (composing.length) {
                        this.messages.add({
                            fullname: fullname,
                            sender: 'them',
                            delayed: delayed,
                            time: converse.toISOString(new Date()),
                            composing: composing.length
                        });
                    }
                } else {
                    if (delayed) {
                        stamp = $message.find('delay').attr('stamp');
                        time = stamp;
                    } else {
                        time = converse.toISOString(new Date());
                    }
                    if (from == converse.bare_jid) {
                        sender = 'me';
                    } else {
                        sender = 'them';
                    }
                    this.messages.create({
                        fullname: fullname,
                        sender: sender,
                        delayed: delayed,
                        time: time,
                        message: body
                    });
                }
            },

            messageReceived: function (message) {
                var $body = $(message).children('body');
                var text = ($body.length > 0 ? converse.autoLink($body.text()) : undefined);
                if ((!text) || (!converse.allow_otr)) {
                    return this.createMessage(message);
                }
                this.createMessage(message);
            }
        });

        this.ChatBoxView = Backbone.View.extend({
            length: 200,
            tagName: 'div',
            className: 'chatbox',
            //dx: 0, //x
            //dy: 0, //y

            events: {
                'click .close-chatbox-button': 'closeChat',
                'click .minimized-chatbox-button': 'minimizedChat',
                'click .minimized-head-button': 'minimizedChat',
                'click .user-offline': 'offlineChat',
                'click .forwardService': 'forwardService',
                'keypress textarea.chat-textarea': 'keyPressed',
                //'mousedown .chat-head':  'moveMouseDown',
                //'mousemove .chat-head':  'moveMouseMove',
                //'mouseup    .chat-head':  'moveMouseUp',
                //'click textarea.chat-textarea': 'changeZindex',
                //'click .chatbox-left': 'changeZindex',
                //'click .chatbox-center': 'changeZindex'
                //'click .toggle-smiley': 'toggleEmoticonMenu',
                //'click .toggle-smiley ul li': 'insertEmoticon',
                //'click .toggle-otr': 'toggleOTRMenu',
                //'click .start-otr': 'startOTRFromToolbar',
                //'click .end-otr': 'endOTR',
                //'click .auth-otr': 'authOTR'
            },

            changeZindex: function(e) {
                $('.chatbox').css('z-index', 20);
                this.$el.css('z-index', 30);
            },

            moveMouseDown: function(e) {
                this.$el.css('cursor', 'move');
                this.$el.fadeTo('fast', 0.7);

                //设置层叠关系
                $('.chatbox').css('z-index', 20);
                this.$el.css('z-index', 30);


                var left = 0, top = 0;
                if(!!this.$el.css("left") && this.$el.css("left") !== 'auto') {
                    left = parseInt(this.$el.css("left"));
                }

                if(!!this.$el.css("top") || this.$el.css("top") !== 'auto') {
                    top = parseInt(this.$el.css("top"));
                }

                this.$el.data('dx', e.pageX - left);
                this.$el.data('dy', e.pageY - top);
                this.$el.data('move', true);

            }, 

            moveMouseMove: function(e) {
                //console.log(this, eX, eY, this.$el.data('dx'), this.$el.data('dy'));
                if(this.$el.data('move')) {
                    var eX=event.pageX, eY=event.pageY;
                    this.$el.css('left', eX - this.$el.data('dx'));
                    this.$el.css('top', eY - this.$el.data('dy'));
                }
            }, 

            moveMouseUp: function(e) {
                this.$el.css('cursor', 'auto');
                this.$el.fadeTo('fast', 1);
                this.$el.data('move', false);
            }, 

            template: _.template(
            	'<div class="minimized-head"><a class="minimized-head-button">{{ fullname }}</a></div>' +
                '<div class="chat-head chat-head-chatbox">' +
                    '<a class="minimized-chatbox-button icon-minus"></a>' +
                    //'<a class="close-chatbox-button icon-close"></a>' +
                    '<div class="user">' +
                        '<div class="chat-title"> {{ fullname }}(<a href="javascript:void(0);" class="user-offline">点击断开连接</a>) &nbsp;&nbsp;&nbsp;<a target="_blank" href="xmpp/chatHistory.html?userName={{ jid.split("@")[0] }}">聊天记录</a></div>' +
                    '</div>' +
                    '<div class="forward">' +
                        '<input type="button" class="forwardService" value="转给另一个客服"/>' +
                    '</div>' +
                    '<p class="user-custom-message"><p/>' +
                '</div>' +
                '<div class="chatbox-left"></div> ' +
                '<div class="chatbox-center">' +
                '<div class="chat-content"></div>' +
                '<form class="sendXMPPMessage" action="" method="post">' +
                    '{[ if ('+converse.show_toolbar+') { ]}' +
                        '<ul class="chat-toolbar no-text-select"></ul>'+
                    '{[ } ]}' +
                '<textarea ' +
                    'type="text" ' +
                    'class="chat-textarea" ' +
                    'placeholder="'+'输入消息内容'+'"/>'+
                '</form>' + 
                '</div>'
            ),

            toolbar_template: _.template(
                '{[ if (show_emoticons)  { ]}' +
                    '<li class="toggle-smiley icon-happy" title="Insert a smilery">' +
                        '<ul>' +
                            '<li><a class="icon-smiley" href="#" data-emoticon=":)"></a></li>'+
                            '<li><a class="icon-wink" href="#" data-emoticon=";)"></a></li>'+
                            '<li><a class="icon-grin" href="#" data-emoticon=":D"></a></li>'+
                            '<li><a class="icon-tongue" href="#" data-emoticon=":P"></a></li>'+
                            '<li><a class="icon-cool" href="#" data-emoticon="8)"></a></li>'+
                            '<li><a class="icon-evil" href="#" data-emoticon=">:)"></a></li>'+
                            '<li><a class="icon-confused" href="#" data-emoticon=":S"></a></li>'+
                            '<li><a class="icon-wondering" href="#" data-emoticon=":\\"></a></li>'+
                            '<li><a class="icon-angry" href="#" data-emoticon=">:("></a></li>'+
                            '<li><a class="icon-sad" href="#" data-emoticon=":("></a></li>'+
                            '<li><a class="icon-shocked" href="#" data-emoticon=":O"></a></li>'+
                            '<li><a class="icon-thumbs-up" href="#" data-emoticon="(^.^)b"></a></li>'+
                            '<li><a class="icon-heart" href="#" data-emoticon="<3"></a></li>'+
                        '</ul>' +
                    '</li>' +
                '{[ } ]}' +
                '{[ if (allow_otr)  { ]}' +
                    '<li class="toggle-otr {{otr_status_class}}" title="{{otr_tooltip}}">'+
                        '<span class="chat-toolbar-text">{{otr_translated_status}}</span>'+
                        '{[ if (otr_status == "'+UNENCRYPTED+'") { ]}' +
                            '<span class="icon-unlocked"></span>'+
                        '{[ } ]}' +
                        '{[ if (otr_status == "'+UNVERIFIED+'") { ]}' +
                            '<span class="icon-lock"></span>'+
                        '{[ } ]}' +
                        '{[ if (otr_status == "'+VERIFIED+'") { ]}' +
                            '<span class="icon-lock"></span>'+
                        '{[ } ]}' +
                        '{[ if (otr_status == "'+FINISHED+'") { ]}' +
                            '<span class="icon-unlocked"></span>'+
                        '{[ } ]}' +
                        '<ul>'+
                            '{[ if (otr_status == "'+UNENCRYPTED+'") { ]}' +
                                '<li><a class="start-otr" href="#">'+'Start encrypted conversation'+'</a></li>'+
                            '{[ } ]}' +
                            '{[ if (otr_status != "'+UNENCRYPTED+'") { ]}' +
                                '<li><a class="start-otr" href="#">'+'Refresh encrypted conversation'+'</a></li>'+
                                '<li><a class="end-otr" href="#">'+'End encrypted conversation'+'</a></li>'+
                                '<li><a class="auth-otr" data-scheme="smp" href="#">'+'Verify with SMP'+'</a></li>'+
                            '{[ } ]}' +
                            '{[ if (otr_status == "'+UNVERIFIED+'") { ]}' +
                                '<li><a class="auth-otr" data-scheme="fingerprint" href="#">'+'Verify with fingerprints'+'</a></li>'+
                            '{[ } ]}' +
                            '<li><a href="http://www.cypherpunks.ca/otr/help/3.2.0/levels.php" target="_blank">'+"What\'s this?"+'</a></li>'+
                        '</ul>'+
                    '</li>'+
                '{[ } ]}'
            ),

            message_template: _.template(
                '<div class="chat-message {{extra_classes}}">' +
                    '<span class="chat-message-{{sender}}">{{time}} {{username}}:&nbsp;</span>' +
                    '<span class="chat-message-content">{{message}}</span>' +
                '</div>'),

            order_template: _.template(
                '<div class="order-info {{extra_classes}}">' +
                    '订单号:{{orderId}} <br/><br/> '
                    + '订单状态:{{orderStatus}} <br/><br/>'
                    + '订单金额:{{orderAmount}} <br/><br/>' 
                    + '商品数量:{{goodsCount}} <br/><br/>'
                    + '商品价格:{{goodsPrice}} <br/><br/>' 
                    + '商品类型:{{goodsType}} <br/><br/>' 
                    + '游戏名字:{{gameName}} <br/><br/>' 
                    + '游戏分区:{{gameAreaName}} <br/><br/>' 
                    + '下单时间:{{orderTime}} <br/><br/>' 
                    + '收货时间:{{getGoodsTime}}'
                    + '</div>'),

            action_template: _.template(
                '<div class="chat-message {{extra_classes}}">' +
                    '<span class="chat-message-{{sender}}">{{time}} **{{username}} </span>' +
                    '<span class="chat-message-content">{{message}}</span>' +
                '</div>'),

            new_day_template: _.template(
                '<time class="chat-date" datetime="{{isodate}}">{{datestring}}</time>'
                ),

            initialize: function (){
                this.model.messages.on('add', this.onMessageAdded, this);
                this.model.on('show', this.show, this);
                this.model.on('destroy', this.hide, this);
                this.model.on('change', this.onChange, this);
                //this.model.on('showOTRError', this.showOTRError, this);
                //this.model.on('buddyStartsOTR', this.buddyStartsOTR, this);
                //this.model.on('showHelpMessages', this.showHelpMessages, this);
                this.model.on('sendMessageStanza', this.sendMessageStanza, this);
                //this.model.on('showSentOTRMessage', function (text) {
                //    this.showOTRMessage(text, 'me');
                //}, this);
                //this.model.on('showReceivedOTRMessage', function (text) {
                //    this.showOTRMessage(text, 'them');
                //}, this);
                this.updateVCard();
                this.$el.appendTo(converse.chatboxesview.$el);
                this.render().show().model.messages.fetch({add: true});
                if (this.model.get('status')) {
                    this.showStatusMessage(this.model.get('status'));
                }

                // 显示该用户相关的订单
            },

            render: function () {
                this.$el.attr('id', this.model.get('box_id'))
                    .html(this.template(this.model.toJSON()));
                this.getAndShowOrderInfo(this.$el);
                return this;
            },

            showStatusNotification: function (message, replace) {
                var $chat_content = this.$el.find('.chat-content');
                $chat_content.find('div.chat-event').remove().end()
                    .append($('<div class="chat-event"></div>').text(message));
                this.scrollDown();
            },

            getAndShowOrderInfo: function ($el) {
                var orderId = this.model.get('order_id');
                if (orderId != undefined && orderId.length > 0){
                    getOrderWithOrderId(orderId, function(order) {
                        showOrderInfo($el, order);
                    });
                } else {
                    //var jid = this.model.get('jid');    // 用户账号
                    var username = this.model.get('user_id');    // 用户账号
                    console.log('getOrdersWithUserName:' + username);
                    getOrdersWithUserName(username, function(order) {
                        showOrderInfo($el, order); 
                    });
                }
            },

            showMessage: function ($el, msg_dict) {
                var this_date = converse.parseISO8601(msg_dict.time),
                    text = msg_dict.message,
                    match = text.match(/^\/(.*?)(?: (.*))?$/),
                    sender = msg_dict.sender,
                    template, username;

                if ((match) && (match[1] === 'me')) {
                    text = text.replace(/^\/me/, '');
                    template = this.action_template;
                    username = msg_dict.fullname;
                } else  {
                    template = this.message_template;
                    username = sender === 'me' && '我' || msg_dict.fullname;
                }
                $el.find('div.chat-event').remove();
                $el.append(
                    template({
                        'sender': sender,
                        'time': this_date.toTimeString().substring(0,5),
                        'message': text,
                        'username': username,
                        'extra_classes': msg_dict.delayed && 'delayed' || ''
                    }));
                return this.scrollDown();
            },

            onMessageAdded: function (message) {
                //console.log("Strophe", Strophe);
                //console.log('message', this);
                var time = message.get('time'),
                    times = this.model.messages.pluck('time'),
                    this_date = converse.parseISO8601(time),
                    $chat_content = this.$el.find('.chat-content'),
                    previous_message, idx, prev_date, isodate, text, match;

                // If this message is on a different day than the one received
                // prior, then indicate it on the chatbox.
                idx = _.indexOf(times, time)-1;
                if (idx >= 0) {
                    previous_message = this.model.messages.at(idx);
                    prev_date = converse.parseISO8601(previous_message.get('time'));
                    isodate = new Date(this_date.getTime());
                    isodate.setUTCHours(0,0,0,0);
                    isodate = converse.toISOString(isodate);
                    if (this.isDifferentDay(prev_date, this_date)) {
                        $chat_content.append(this.new_day_template({
                            isodate: isodate,
                            datestring: this_date.toString().substring(0,15)
                        }));
                    }
                }
                if (message.get('composing')) {
                    this.showStatusNotification(message.get('fullname')+' '+' 正在输入');
                    return;
                } else {
                    this.showMessage($chat_content, _.clone(message.attributes));
                    
                    // 如果在最小化，则颜色加红
                    var $minimizedHead = this.$el.find('div.minimized-head');
                    if ($minimizedHead.is(':visible')) {
                        this.$el.find('a.minimized-head-button').addClass('minimized-head-button-color');
                    }
                }
                if ((message.get('sender') != 'me') && (converse.windowState == 'blur')) {
                    converse.incrementMsgCounter();
                }
                return this.scrollDown();
            },

            isDifferentDay: function (prev_date, next_date) {
                return (
                    (next_date.getDate() != prev_date.getDate()) ||
                    (next_date.getFullYear() != prev_date.getFullYear()) ||
                    (next_date.getMonth() != prev_date.getMonth()));
            },

            sendMessageStanza: function (text) {
                /*
                 * Sends the actual XML stanza to the XMPP server.
                 */
                // TODO: Look in ChatPartners to see what resources we have for the recipient.
                // if we have one resource, we sent to only that resources, if we have multiple
                // we send to the bare jid.
                var timestamp = (new Date()).getTime();
                var bare_jid = this.model.get('jid');
                var message = $msg({from: converse.connection.jid, to: bare_jid, type: 'chat', id: timestamp})
                    .c('body').t(text).up()
                    .c('active', {'xmlns': 'http://jabber.org/protocol/chatstates'});
                // Forward the message, so that other connected resources are also aware of it.
                // TODO: Forward the message only to other connected resources (inside the browser)
                var forwarded = $msg({to:converse.bare_jid, type:'chat', id:timestamp})
                                .c('forwarded', {xmlns:'urn:xmpp:forward:0'})
                                .c('delay', {xmns:'urn:xmpp:delay',stamp:timestamp}).up()
                                .cnode(message.tree());

                converse.connection.send(message);
                converse.connection.send(forwarded);
            },

            sendMessage: function (text) {
                //设置用户的模式
                setUserC2CMode(this.model.get('user_id'));
                console.log("set user c2c mode" + this.model.get('user_id'));

                var fullname = converse.xmppstatus.get('fullname');
                    fullname = _.isEmpty(fullname)? converse.bare_jid: fullname;
                    this.model.messages.create({
                        fullname: fullname,
                        sender: 'me',
                        time: converse.toISOString(new Date()),
                        message: text
                    });
                    this.sendMessageStanza(text);
            },

            keyPressed: function (ev) {
                var $textarea = $(ev.target),
                    message, notify, composing;
                if(ev.keyCode == KEY.ENTER) {
                    ev.preventDefault();
                    message = $textarea.val();
                    $textarea.val('').focus();
                    if (message !== '') {
                        this.sendMessage(message);
                    }
                    this.$el.data('composing', false);
                } else if (!this.model.get('chatroom')) {
                    // composing data is only for single user chat
                    composing = this.$el.data('composing');
                    if (!composing) {
                        if (ev.keyCode != 47) {
                            // We don't send composing messages if the message
                            // starts with forward-slash.
                            notify = $msg({'to':this.model.get('jid'), 'type': 'chat'})
                                            .c('composing', {'xmlns':'http://jabber.org/protocol/chatstates'});
                            converse.connection.send(notify);
                        }
                        this.$el.data('composing', true);
                    }
                }
            },
            onChange: function (item, changed) {
                if (_.has(item.changed, 'chat_status')) {
                    var chat_status = item.get('chat_status'),
                        fullname = item.get('fullname');
                    fullname = _.isEmpty(fullname)? item.get('jid'): fullname;
                    if (this.$el.is(':visible')) {
                        if (chat_status === 'offline') {
                            this.showStatusNotification(fullname+' '+' 已经离线');
                        } else if (chat_status === 'away') {
                            this.showStatusNotification(fullname+' '+' 暂时离开');
                        } else if ((chat_status === 'dnd')) {
                            this.showStatusNotification(fullname+' '+' 繁忙');
                        } else if (chat_status === 'online') {
                            this.$el.find('div.chat-event').remove();
                        }
                    }
                }
                if (_.has(item.changed, 'status')) {
                    this.showStatusMessage(item.get('status'));
                }
                if (_.has(item.changed, 'image')) {
                    this.renderAvatar();
                }
            },

            showStatusMessage: function (msg) {
                this.$el.find('p.user-custom-message').text(msg).attr('title', msg);
            },

            closeChat: function () {
                this.model.trigger('hide');
                if (converse.connection) {
                    try {
                        this.model.destroy();
                    } catch(err) {}
                }  
            },
            
            minimizedChat:function () { 
                var $minimizedHead = this.$el.find('div.minimized-head');
                $minimizedHead.toggle();

                var $chatHead = this.$el.find('div.chat-head');
                $chatHead.toggle();

                var $content = this.$el.find('div.chatbox-center');
                $content.toggle();
                this.$el.find('div.chatbox-left').toggle();
                if (!$content.is(':visible')) {
                    this.$el.addClass('minimized');

                    // 如果是第一个元素，则加一个 minimized-first 的 class
                    var $controlbox = this.$el.parent().find('#controlbox');
                    var firstChatboxId = this.$el.parent().find('.chatbox').first().attr('id');
                    var controlboxId = $controlbox.attr('id');
                    var currElementId = this.$el.attr('id');
                    if (firstChatboxId != controlboxId && firstChatboxId == currElementId) {
                        this.$el.addClass('minimized-first');
                    } else if (firstChatboxId == controlboxId && !$controlbox.is(':visible')) {
                        var secondChatboxId = this.$el.parent().find('.chatbox').first().next().attr('id');
                        if (currElementId == secondChatboxId) {
                            this.$el.addClass('minimized-first');
                        }    
                    }    
                } else {
                    this.$el.find('a.minimized-head-button').removeClass('minimized-head-button-color');
                    this.$el.removeClass('minimized');
                    this.$el.removeClass('minimized-first');
                }    
            }, 

           offlineChat: function () {
                var userName = this.model.get('user_id');
                var that = this;
                var result = confirm("确定断开与 " + userName + " 的连接？");
                if (result === true) {
                    quitMode(userName, function (code) {
                        if (code == CODE_SUCC) {
                            that.closeChat();
                        } else {
                            alert ("断开失败，请重试");
                        }
                    });
                }
            },

            forwardService: function () {
                var toUsername = prompt('要转到的客服名称：');
                if(toUsername) {
                    var userName = this.model.get('user_id');
                    switchCustomer(userName, toUsername, function(code) {
                    	if (code == CODE_SUCC) {
                    		this.closeChat();
                        } else {
                            alert ("转客服失败，请重试");
                        }
                    });
                }
            },


            updateVCard: function () {
                console.log("updateVCard...");
                var jid = this.model.get('jid'),
                    rosteritem = converse.roster.get(jid);
                    converse.getVCard(
                        jid,
                        $.proxy(function (jid, fullname, image, image_type, url) {
                            this.model.save({
                                'fullname' : fullname || jid,
                                'url': url,
                                'image_type': image_type,
                                'image': image
                            });
                        }, this),
                        $.proxy(function (stanza) {
                            converse.log("ChatBoxView.initialize: An error occured while fetching vcard");
                        }, this)
                    );
            },
            renderAvatar: function () {
                if (!this.model.get('image')) {
                    return;
                }
                var img_src = 'data:'+this.model.get('image_type')+';base64,'+this.model.get('image'),
                    canvas = $('<canvas height="33px" width="33px" class="avatar"></canvas>'),
                    ctx = canvas.get(0).getContext('2d'),
                    img = new Image();   // Create new Image object
                img.onload = function() {
                    var ratio = img.width/img.height;
                    ctx.drawImage(img, 0,0, 35*ratio, 35);
                };
                img.src = img_src;
                this.$el.find('.chat-title').before(canvas);
                return this;
            },

            focus: function () {
                this.$el.find('.chat-textarea').focus();
                return this;
            },

            hide: function () {
                if (converse.animate) {
                    this.$el.hide('fast');
                } else {
                    this.$el.hide();
                }
            },

            show: function () {
                if (this.$el.is(':visible') && this.$el.css('opacity') == "1") {
                    return this.focus();
                }
                if (converse.animate) {
                    this.$el.css({'opacity': 0, 'display': 'block'}).animate({opacity: '1'}, 200);
                } else {
                    this.$el.css({'opacity': 1, 'display': 'block'});
                }
                if (converse.connection) {
                    // Without a connection, we haven't yet initialized
                    // localstorage
                    this.model.save();
                }
                return this;
            },

            scrollDown: function () {
                var $content = this.$el.find('.chat-content');
                $content.scrollTop($content[0].scrollHeight);
                return this;
            }
        });

        this.ContactsPanel = Backbone.View.extend({
            tagName: 'div',
            className: 'oc-chat-content',
            id: 'users',
            events: {
                'click a.toggle-xmpp-contact-form': 'toggleContactForm',
                'submit form.add-xmpp-contact': 'addContactFromForm',
                'submit form.search-xmpp-contact': 'searchContacts',
                'click a.subscribe-to-user': 'addContactFromList'
            },

            tab_template: _.template('<li><a class="s current" href="#users">'+'联系人'+'</a></li>'),
            template: _.template(
                '<form class="set-xmpp-status" action="" method="post">'+
                    '<span id="xmpp-status-holder">'+
                        '<select id="select-xmpp-status" style="display:none">'+
                            '<option value="online">'+'在线'+'</option>'+
                            '<option value="offline">'+'离线'+'</option>'+
                        '</select>'+
                    '</span>'+
                '</form>'
            ),

            add_contact_dropdown_template: _.template(
                '<dl class="add-converse-contact dropdown">' +
                    '<dt id="xmpp-contact-search" class="fancy-dropdown">' +
                        '<a class="toggle-xmpp-contact-form" href="#"'+
                            'title="'+'点击添加一个新的联系人'+'">'+
                        '<span class="icon-plus"></span>'+'添加联系人'+'</a>' +
                    '</dt>' +
                    '<dd class="search-xmpp" style="display:none"><ul></ul></dd>' +
                '</dl>'
            ),

            add_contact_form_template: _.template(
                '<li>'+
                    '<form class="add-xmpp-contact">' +
                        '<input type="text" name="identifier" class="username" placeholder="'+'联系人用户名'+'"/>' +
                        '<button type="submit">'+'添加'+'</button>' +
                    '</form>'+
                '<li>'
            ),

            search_contact_template: _.template(
                '<li>'+
                    '<form class="search-xmpp-contact">' +
                        '<input type="text" name="identifier" class="username" placeholder="'+'联系人名称'+'"/>' +
                        '<button type="submit">'+'搜索'+'</button>' +
                    '</form>'+
                '<li>'
            ),

            initialize: function (cfg) {
                cfg.$parent.append(this.$el);
                this.$tabs = cfg.$parent.parent().find('#controlbox-tabs');
            },

            render: function () {
                var markup;
                var widgets = this.template();

                this.$tabs.append(this.tab_template());
                if (converse.xhr_user_search) {
                    markup = this.search_contact_template();
                } else {
                    markup = this.add_contact_form_template();
                }

                if (converse.allow_contact_requests) {
                    widgets += this.add_contact_dropdown_template();
                }
                this.$el.html(widgets);

                this.$el.find('.search-xmpp ul').append(markup);
                this.$el.append(converse.rosterview.$el);
                return this;
            },

            toggleContactForm: function (ev) {
                ev.preventDefault();
                this.$el.find('.search-xmpp').toggle('fast', function () {
                    if ($(this).is(':visible')) {
                        $(this).find('input.username').focus();
                    }
                });
            },

            searchContacts: function (ev) {
                ev.preventDefault();
                $.getJSON(xhr_user_search_url+ "?q=" + $(ev.target).find('input.username').val(), function (data) {
                    var $ul= $('.search-xmpp ul');
                    $ul.find('li.found-user').remove();
                    $ul.find('li.chat-info').remove();
                    if (!data.length) {
                        $ul.append('<li class="chat-info">'+'没发现符合条件的用户'+'</li>');
                    }

                    $(data).each(function (idx, obj) {
                        $ul.append(
                            $('<li class="found-user"></li>')
                            .append(
                                $('<a class="subscribe-to-user" href="#" title="'+'点击添加为你的联系人'+'"></a>')
                                .attr('data-recipient', Strophe.escapeNode(obj.id)+'@'+converse.domain)
                                .text(obj.fullname)
                            )
                        );
                    });
                });
            },

            addContactFromForm: function (ev) {
                ev.preventDefault();
                var $input = $(ev.target).find('input');
                var jid = $input.val();
                if (! jid) {
                    // this is not a valid JID
                    $input.addClass('error');
                    return;
                }
                converse.getVCard(
                    jid,
                    $.proxy(function (jid, fullname, image, image_type, url) {
                        this.addContact(jid, fullname);
                    }, this),
                    $.proxy(function (stanza) {
                        converse.log("An error occured while fetching vcard");
                        var jid = $(stanza).attr('from');
                        this.addContact(jid, jid);
                    }, this));
                $('.search-xmpp').hide();
            },

            addContactFromList: function (ev) {
                ev.preventDefault();
                var $target = $(ev.target),
                    jid = $target.attr('data-recipient'),
                    name = $target.text();
                this.addContact(jid, name);
                $target.parent().remove();
                $('.search-xmpp').hide();
            },

            addContact: function (jid, name) {
                converse.connection.roster.add(jid, name, [], function (iq) {
                    converse.connection.roster.subscribe(jid, null, converse.xmppstatus.get('fullname'));
                });
            }
        });

        this.ControlBoxView = converse.ChatBoxView.extend({
            tagName: 'div',
            className: 'chatbox controlbox',
            id: 'controlbox',
            events: {
                'click a.close-chatbox-button': 'closeChat',
                'click ul#controlbox-tabs li a': 'switchTab',
                'click .chat-head': 'changeZindex'
            },

            changeZindex: function(e) {
                $('.chatbox').css('z-index', 20);
                this.$el.css('z-index', 30);
            },

            initialize: function () {
                this.$el.appendTo(converse.chatboxesview.$el);
                this.model.on('change', $.proxy(function (item, changed) {
                    var i;
                    if (_.has(item.changed, 'connected')) {
                        this.render();
                    }
                    if (_.has(item.changed, 'visible')) {
                        if (item.changed.visible === true) {
                            this.show();
                        }
                    }
                }, this));
                this.model.on('show', this.show, this);
                this.model.on('destroy', this.hide, this);
                this.model.on('hide', this.hide, this);
                if (this.model.get('visible')) {
                    this.show();
                }
            },

            template: _.template(
                '<div class="chat-head oc-chat-head">'+
                    '<ul id="controlbox-tabs"></ul>'+
                    '<a class="close-chatbox-button icon-close"></a>'+
                '</div>'+
                '<div class="controlbox-panes"></div>'
            ),

            switchTab: function (ev) {
                ev.preventDefault();
                var $tab = $(ev.target),
                    $sibling = $tab.parent().siblings('li').children('a'),
                    $tab_panel = $($tab.attr('href')),
                    $sibling_panel = $($sibling.attr('href'));

                $sibling_panel.fadeOut('fast', function () {
                    $sibling.removeClass('current');
                    $tab.addClass('current');
                    $tab_panel.fadeIn('fast', function () {
                    });
                });
            },

            //showHelpMessages: function (msgs) {
            //    // Override showHelpMessages in ChatBoxView, for now do nothing.
            //    return;
            //},

            render: function () {
                if ((!converse.prebind) && (!converse.connection)) {
                    // Add login panel if the user still has to authenticate
                    this.$el.html(this.template(this.model.toJSON()));
                    this.loginpanel = new converse.LoginPanel({'$parent': this.$el.find('.controlbox-panes'), 'model': this});
                    this.loginpanel.render();
                } else if (!this.contactspanel) {
                    this.$el.html(this.template(this.model.toJSON()));
                    this.contactspanel = new converse.ContactsPanel({'$parent': this.$el.find('.controlbox-panes')});
                    this.contactspanel.render();
                    converse.xmppstatusview = new converse.XMPPStatusView({'model': converse.xmppstatus});
                    converse.xmppstatusview.render();
                }
                return this;
            }
        });


        this.ChatBoxes = Backbone.Collection.extend({
            model: converse.ChatBox,

            onConnected: function () {
                this.localStorage = new Backbone.LocalStorage(
                    hex_sha1('converse.chatboxes-'+converse.bare_jid));
                if (!this.get('controlbox')) {
                    this.add({
                        id: 'controlbox',
                        box_id: 'controlbox'
                    });
                } else {
                    this.get('controlbox').save();
                }
                // This will make sure the Roster is set up
                this.get('controlbox').set({connected:true});

                // Register message handler
                converse.connection.addHandler(
                    $.proxy(function (message) {
                        this.messageReceived(message);
                        return true;
                    }, this), null, 'message', 'chat');

                // Get cached chatboxes from localstorage
                this.fetch({
                    add: true,
                    success: $.proxy(function (collection, resp) {
                        if (_.include(_.pluck(resp, 'id'), 'controlbox')) {
                            // If the controlbox was saved in localstorage, it must be visible
                            this.get('controlbox').set({visible:true}).save();
                        }
                    }, this)
                });
            },

            messageReceived: function (message) {
                var buddy_jid, $message = $(message),
                    message_from = $message.attr('from');
                if (message_from == converse.connection.jid) {
                    // FIXME: Forwarded messages should be sent to specific resources,
                    // not broadcasted
                    return true;
                }
                var $forwarded = $message.children('forwarded');
                if ($forwarded.length) {
                    $message = $forwarded.children('message');
                }
                var from = Strophe.getBareJidFromJid(message_from),
                    to = Strophe.getBareJidFromJid($message.attr('to')),
                    resource, chatbox, roster_item;
                if (from == converse.bare_jid) {
                    // I am the sender, so this must be a forwarded message...
                    buddy_jid = to;
                    resource = Strophe.getResourceFromJid($message.attr('to'));
                } else {
                    buddy_jid = from;
                    resource = Strophe.getResourceFromJid(message_from);
                }
                chatbox = this.get(buddy_jid);
                roster_item = converse.roster.get(buddy_jid);

                if (roster_item === undefined) {
                    // The buddy was likely removed
                    converse.log('Could not get roster item for JID '+buddy_jid, 'error');
                    return true;
                }

                if (!chatbox) {
                    var fullname = roster_item.get('fullname');
                    fullname = _.isEmpty(fullname)? buddy_jid: fullname;

                    console.log("buddy_jid:" + buddy_jid);
                    chatbox = this.create({
                        'id': buddy_jid,
                        'jid': buddy_jid,
                        'fullname': fullname,
                        'image_type': roster_item.get('image_type'),
                        'image': roster_item.get('image'),
                        'url': roster_item.get('url')
                    });

                    xmppDesktopNotice(message);
                }
                chatbox.messageReceived(message);
                converse.roster.addResource(buddy_jid, resource);
                return true;
            }
        });

        this.ChatBoxesView = Backbone.View.extend({
            el: '#conversejs',

            initialize: function () {
                // boxesviewinit
                this.views = {};
                this.model.on("add", function (item) {
                    var view = this.views[item.get('id')];
                    if (!view) {
                        if (item.get('box_id') === 'controlbox') {
                            view = new converse.ControlBoxView({model: item});
                            view.render();
                        } else {
                            view = new converse.ChatBoxView({model: item});
                        }
                        this.views[item.get('id')] = view;
                    } else {
                        delete view.model; // Remove ref to old model to help garbage collection
                        view.model = item;
                        view.initialize();
                        if (item.get('id') !== 'controlbox') {
                            // FIXME: Why is it necessary to again append chatboxes?
                            view.$el.appendTo(this.$el);
                        }
                    }
                }, this);
            },

            showChatBox: function (attrs) {
                console.log('jid:' + this.model.get(attrs.jid));
                var chatbox  = this.model.get(attrs.jid);
                if (chatbox) {
                    chatbox.trigger('show');
                } else {
                    chatbox = this.model.create(attrs, {
                        'error': function (model, response) {
                            converse.log(response.responseText);
                        }
                    });
                }
                chatbox.orderId = attrs.orderId;
                return chatbox;
            }
        });

        this.RosterItem = Backbone.Model.extend({
            initialize: function (attributes, options) {
                var jid = attributes.jid;
                if (!attributes.fullname) {
                    attributes.fullname = jid;
                }
                var attrs = _.extend({
                    'id': jid,
                    'user_id': Strophe.getNodeFromJid(jid),
                    'resources': [],
                    'status': ''
                }, attributes);
                attrs.sorted = false;
                attrs.chat_status = 'offline';
                this.set(attrs);
            }
        });

        this.RosterItemView = Backbone.View.extend({
            tagName: 'dd',

            events: {
                "click .accept-xmpp-request": "acceptRequest",
                "click .decline-xmpp-request": "declineRequest",
                "click .open-chat": "openChat",
                "click .remove-xmpp-contact": "removeContact"
            },


            openChat: function (ev) {
                console.log(this.model);
                var that = this;
                //使用user_id,  不是jid
                userLoginXMPP(this.model.get('user_id'), function (code) {
                    if (code == CODE_SUCC) {
                        ev.preventDefault();
                        console.log('id, jid:' + that.model.get('jid'));
                        converse.chatboxesview.showChatBox({
                            'id': that.model.get('jid'),
                            'jid': that.model.get('jid'),
                            'fullname': that.model.get('fullname'),
                            'image_type': that.model.get('image_type'),
                            'image': that.model.get('image'),
                            'url': that.model.get('url'),
                            'status': that.model.get('status')
                        });
                    } else {
                        alert ("当前用户无法在 XMPP 上登录，请重试。");
                    }
                });
            },

            removeContact: function (ev) {
                ev.preventDefault();
                var result = confirm("Are you sure you want to remove this contact?");
                if (result === true) {
                    var bare_jid = this.model.get('jid');
                    converse.connection.roster.remove(bare_jid, function (iq) {
                        converse.connection.roster.unauthorize(bare_jid);
                        converse.rosterview.model.remove(bare_jid);
                    });
                }
            },

            acceptRequest: function (ev) {
                var jid = this.model.get('jid');
                converse.connection.roster.authorize(jid);
                converse.connection.roster.add(jid, this.model.get('fullname'), [], function (iq) {
                    converse.connection.roster.subscribe(jid, null, converse.xmppstatus.get('fullname'));
                });
                ev.preventDefault();
            },

            declineRequest: function (ev) {
                ev.preventDefault();
                converse.connection.roster.unauthorize(this.model.get('jid'));
                try {
                    this.model.destroy();
                } catch(e) {}
            },

            template: _.template(
                '<a class="open-chat" title="'+'点击即可与该联系人对话'+'" href="#">'+
                    '<span class="icon-{{ chat_status }}" title="{{ status_desc }}"></span>{{ fullname }}'+
                '</a>' +
                '<a class="remove-xmpp-contact icon-remove" title="'+'点击移除该联系人'+'" href="#"></a>'),

            pending_template: _.template(
                '<span>{{ fullname }}</span>' +
                '<a class="remove-xmpp-contact icon-remove" title="'+'点击移除该联系人'+'" href="#"></a>'),

            request_template: _.template('<div>{{ fullname }}</div>' +
                '<button type="button" class="accept-xmpp-request">' +
                'Accept</button>' +
                '<button type="button" class="decline-xmpp-request">' +
                'Decline</button>' +
                ''),

            render: function () {
                var item = this.model,
                    ask = item.get('ask'),
                    requesting  = item.get('requesting'),
                    subscription = item.get('subscription');

                var classes_to_remove = [
                    'current-xmpp-contact',
                    'pending-xmpp-contact',
                    'requesting-xmpp-contact'
                    ].concat(_.keys(STATUSES));

                _.each(classes_to_remove,
                    function (cls) {
                        if (this.el.className.indexOf(cls) !== -1) {
                            this.$el.removeClass(cls);
                        }
                    }, this);

                this.$el.addClass(item.get('chat_status'));

                if (ask === 'subscribe') {
                    this.$el.addClass('pending-xmpp-contact');
                    this.$el.html(this.pending_template(item.toJSON()));
                } else if (requesting === true) {
                    this.$el.addClass('requesting-xmpp-contact');
                    this.$el.html(this.request_template(item.toJSON()));
                    converse.controlboxtoggle.showControlBox();
                } else if (subscription === 'both' || subscription === 'to') {
                    this.$el.addClass('current-xmpp-contact');
                    this.$el.html(this.template(
                        _.extend(item.toJSON(), {'status_desc': STATUSES[item.get('chat_status')||'offline']})
                    ));
                }
                return this;
            }
        });

        this.RosterItems = Backbone.Collection.extend({
            model: converse.RosterItem,
            comparator : function (rosteritem) {
                var chat_status = rosteritem.get('chat_status'),
                    rank = 4;
                switch(chat_status) {
                    case 'offline':
                        rank = 0;
                        break;
                    case 'unavailable':
                        rank = 1;
                        break;
                    case 'xa':
                        rank = 2;
                        break;
                    case 'away':
                        rank = 3;
                        break;
                    case 'dnd':
                        rank = 4;
                        break;
                    case 'online':
                        rank = 5;
                        break;
                }
                return rank;
            },

            subscribeToSuggestedItems: function (msg) {
                $(msg).find('item').each(function () {
                    var $this = $(this),
                        jid = $this.attr('jid'),
                        action = $this.attr('action'),
                        fullname = $this.attr('name');
                    if (action === 'add') {
                        converse.connection.roster.add(jid, fullname, [], function (iq) {
                            converse.connection.roster.subscribe(jid, null, converse.xmppstatus.get('fullname'));
                        });
                    }
                });
                return true;
            },

            isSelf: function (jid) {
                return (Strophe.getBareJidFromJid(jid) === Strophe.getBareJidFromJid(converse.connection.jid));
            },

            addResource: function (bare_jid, resource) {
                var item = this.get(bare_jid),
                    resources;
                if (item) {
                    resources = item.get('resources');
                    if (resources) {
                        if (_.indexOf(resources, resource) == -1) {
                            resources.push(resource);
                            item.set({'resources': resources});
                        }
                    } else  {
                        item.set({'resources': [resource]});
                    }
                }
            },

            removeResource: function (bare_jid, resource) {
                var item = this.get(bare_jid),
                    resources,
                    idx;
                if (item) {
                    resources = item.get('resources');
                    idx = _.indexOf(resources, resource);
                    if (idx !== -1) {
                        resources.splice(idx, 1);
                        item.set({'resources': resources});
                        return resources.length;
                    }
                }
                return 0;
            },

            subscribeBack: function (jid) {
                var bare_jid = Strophe.getBareJidFromJid(jid);
                if (converse.connection.roster.findItem(bare_jid)) {
                    converse.connection.roster.authorize(bare_jid);
                    converse.connection.roster.subscribe(jid, null, converse.xmppstatus.get('fullname'));
                } else {
                    converse.connection.roster.add(jid, '', [], function (iq) {
                        converse.connection.roster.authorize(bare_jid);
                        converse.connection.roster.subscribe(jid, null, converse.xmppstatus.get('fullname'));
                    });
                }
            },

            unsubscribe: function (jid) {
                /* Upon receiving the presence stanza of type "unsubscribed",
                * the user SHOULD acknowledge receipt of that subscription state
                * notification by sending a presence stanza of type "unsubscribe"
                * this step lets the user's server know that it MUST no longer
                * send notification of the subscription state change to the user.
                */
                converse.xmppstatus.sendPresence('unsubscribe');
                if (converse.connection.roster.findItem(jid)) {
                    converse.connection.roster.remove(jid, function (iq) {
                        converse.rosterview.model.remove(jid);
                    });
                }
            },

            getNumOnlineContacts: function () {
                var count = 0,
                    models = this.models,
                    models_length = models.length,
                    i;
                for (i=0; i<models_length; i++) {
                    if (_.indexOf(['offline', 'unavailable'], models[i].get('chat_status')) === -1) {
                        count++;
                    }
                }
                return count;
            },

            cleanCache: function (items) {
                /* The localstorage cache containing roster contacts might contain
                * some contacts that aren't actually in our roster anymore. We
                * therefore need to remove them now.
                */
                var id, i,
                    roster_ids = [];
                for (i=0; i < items.length; ++i) {
                    roster_ids.push(items[i].jid);
                }
                for (i=0; i < this.models.length; ++i) {
                    id = this.models[i].get('id');
                    if (_.indexOf(roster_ids, id) === -1) {
                        this.get(id).destroy();
                    }
                }
            },

            rosterHandler: function (items) {
                this.cleanCache(items);
                _.each(items, function (item, index, items) {
                    if (this.isSelf(item.jid)) { return; }
                    var model = this.get(item.jid);
                    if (!model) {
                        var is_last = (index === (items.length-1)) ? true : false;
                        if ((item.subscription === 'none') && (item.ask === null) && !is_last) {
                            // We're not interested in zombies
                            // (Hack: except if it's the last item, then we still
                            // add it so that the roster will be shown).
                            return;
                        }
                        this.create({
                            jid: item.jid,
                            subscription: item.subscription,
                            ask: item.ask,
                            fullname: item.name || item.jid,
                            is_last: is_last
                        });
                    } else {
                        if ((item.subscription === 'none') && (item.ask === null)) {
                            // This user is no longer in our roster
                            model.destroy();
                        } else if (model.get('subscription') !== item.subscription || model.get('ask') !== item.ask) {
                            // only modify model attributes if they are different from the
                            // ones that were already set when the rosterItem was added
                            model.set({'subscription': item.subscription, 'ask': item.ask, 'requesting': null});
                            model.save();
                        }
                    }
                }, this);

                if (!converse.initial_presence_sent) {
                    /* Once we've sent out our initial presence stanza, we'll
                     * start receiving presence stanzas from our contacts.
                     * We therefore only want to do this after our roster has
                     * been set up (otherwise we can't meaningfully process
                     * incoming presence stanzas).
                     */
                    converse.initial_presence_sent = 1;
                    converse.xmppstatus.sendPresence();
                }
            },

            handleIncomingSubscription: function (jid) {
                var bare_jid = Strophe.getBareJidFromJid(jid);
                var item = this.get(bare_jid);

                if (!converse.allow_contact_requests) {
                    converse.connection.roster.unauthorize(bare_jid);
                    return true;
                }
                if (converse.auto_subscribe) {
                    if ((!item) || (item.get('subscription') != 'to')) {
                        this.subscribeBack(jid);
                    } else {
                        converse.connection.roster.authorize(bare_jid);
                    }
                } else {
                    if ((item) && (item.get('subscription') != 'none'))  {
                        converse.connection.roster.authorize(bare_jid);
                    } else {
                        if (!this.get(bare_jid)) {
                            converse.getVCard(
                                bare_jid,
                                $.proxy(function (jid, fullname, img, img_type, url) {
                                    this.add({
                                        jid: bare_jid,
                                        subscription: 'none',
                                        ask: null,
                                        requesting: true,
                                        fullname: fullname,
                                        image: img,
                                        image_type: img_type,
                                        url: url,
                                        vcard_updated: converse.toISOString(new Date()),
                                        is_last: true
                                    });
                                }, this),
                                $.proxy(function (jid, fullname, img, img_type, url) {
                                    converse.log("Error while retrieving vcard");
                                    // XXX: Should vcard_updated be set here as
                                    // well?
                                    this.add({
                                        jid: bare_jid,
                                        subscription: 'none',
                                        ask: null,
                                        requesting: true,
                                        fullname: jid,
                                        is_last: true
                                    });
                                }, this)
                            );
                        } else {
                            return true;
                        }
                    }
                }
                return true;
            },

            presenceHandler: function (presence) {
                var $presence = $(presence),
                    presence_type = $presence.attr('type');
                if (presence_type === 'error') {
                    // TODO
                    // error presence stanzas don't necessarily have a 'from' attr.
                    return true;
                }
                var jid = $presence.attr('from'),
                    bare_jid = Strophe.getBareJidFromJid(jid),
                    resource = Strophe.getResourceFromJid(jid),
                    $show = $presence.find('show'),
                    chat_status = $show.text() || 'online',
                    status_message = $presence.find('status'),
                    item;

                if (this.isSelf(bare_jid)) {
                    if ((converse.connection.jid !== jid)&&(presence_type !== 'unavailable')) {
                        // Another resource has changed it's status, we'll update ours as well.
                        // FIXME: We should ideally differentiate between converse.js using
                        // resources and other resources (i.e Pidgin etc.)
                        converse.xmppstatus.save({'status': chat_status});
                    }
                    return true;
                } else if (($presence.find('x').attr('xmlns') || '').indexOf(Strophe.NS.MUC) === 0) {
                    return true; // Ignore MUC
                }
                item = this.get(bare_jid);
                if (item && (status_message.text() != item.get('status'))) {
                    item.save({'status': status_message.text()});
                }
                if ((presence_type === 'subscribed') || (presence_type === 'unsubscribe')) {
                    return true;
                } else if (presence_type === 'subscribe') {
                    return this.handleIncomingSubscription(jid);
                } else if (presence_type === 'unsubscribed') {
                    this.unsubscribe(bare_jid);
                } else if (presence_type === 'unavailable') {
                    if (this.removeResource(bare_jid, resource) === 0) {
                        if (item) {
                            item.set({'chat_status': 'offline'});
                        }
                    }
                } else if (item) {
                    // presence_type is undefined
                    this.addResource(bare_jid, resource);
                    item.set({'chat_status': chat_status});
                }
                return true;
            }
        });

        this.RosterView = Backbone.View.extend({
            tagName: 'dl',
            id: 'converse-roster',
            rosteritemviews: {},

            requesting_contacts_template: _.template(
                '<dt id="xmpp-contact-requests">'+'联系人请求'+'</dt>'),

            contacts_template: _.template(
                '<dt id="xmpp-contacts">'+'我的联系人'+'</dt>'),

            //pending_contacts_template: _.template(
            //    '<dt id="pending-xmpp-contacts">'+'处理中的联系人'+'</dt>'),

            initialize: function () {
                this.model.on("add", function (item) {
                    this.addRosterItemView(item).render(item);
                    if (!item.get('vcard_updated')) {
                        // This will update the vcard, which triggers a change
                        // request which will rerender the roster item.
                        converse.getVCard(item.get('jid'));
                    }
                }, this);

                this.model.on('change', function (item) {
                    if ((_.size(item.changed) === 1) && _.contains(_.keys(item.changed), 'sorted')) {
                        return;
                    }
                    this.updateChatBox(item).render(item);
                }, this);

                this.model.on("remove", function (item) { this.removeRosterItemView(item); }, this);
                this.model.on("destroy", function (item) { this.removeRosterItemView(item); }, this);

                var roster_markup = this.contacts_template();
                if (converse.allow_contact_requests) {
                    //roster_markup = this.requesting_contacts_template() + roster_markup + this.pending_contacts_template();
                    roster_markup = this.requesting_contacts_template() + roster_markup;
                }
                this.$el.hide().html(roster_markup);

                this.model.fetch({add: true}); // Get the cached roster items from localstorage
            },

            updateChatBox: function (item, changed) {
                var chatbox = converse.chatboxes.get(item.get('jid')),
                    changes = {};
                if (!chatbox) {
                    return this;
                }
                if (_.has(item.changed, 'chat_status')) {
                    changes.chat_status = item.get('chat_status');
                }
                if (_.has(item.changed, 'status')) {
                    changes.status = item.get('status');
                }
                chatbox.save(changes);
                return this;
            },

            addRosterItemView: function (item) {
                var view = new converse.RosterItemView({model: item});
                this.rosteritemviews[item.id] = view;
                return this;
            },

            removeRosterItemView: function (item) {
                var view = this.rosteritemviews[item.id];
                if (view) {
                    view.$el.remove();
                    delete this.rosteritemviews[item.id];
                    this.render();
                }
                return this;
            },

            renderRosterItem: function (item, view) {
                if ((converse.show_only_online_users) && (item.get('chat_status') !== 'online')) {
                    view.$el.remove();
                    view.delegateEvents();
                    return this;
                }
                if ($.contains(document.documentElement, view.el)) {
                    view.render();
                } else {
                    this.$el.find('#xmpp-contacts').after(view.render().el);
                }
            },

            render: function (item) {
                var $my_contacts = this.$el.find('#xmpp-contacts'),
                    $contact_requests = this.$el.find('#xmpp-contact-requests'),
                    $pending_contacts = this.$el.find('#pending-xmpp-contacts'),
                    sorted = false,
                    $count, changed_presence;
                if (item) {
                    var jid = item.id,
                        view = this.rosteritemviews[item.id],
                        ask = item.get('ask'),
                        subscription = item.get('subscription'),
                        requesting  = item.get('requesting'),
                        crit = {order:'asc'};

                    if ((ask === 'subscribe') && (subscription == 'none')) {
                        $pending_contacts.after(view.render().el);
                        $pending_contacts.after($pending_contacts.siblings('dd.pending-xmpp-contact').tsort(crit));
                    } else if ((ask === 'subscribe') && (subscription == 'from')) {
                        // TODO: We have accepted an incoming subscription
                        // request and (automatically) made our own subscription request back.
                        // It would be useful to update the roster here to show
                        // things are happening... (see docs/DEVELOPER.rst)
                        $pending_contacts.after(view.render().el);
                        $pending_contacts.after($pending_contacts.siblings('dd.pending-xmpp-contact').tsort(crit));
                    } else if (requesting === true) {
                        $contact_requests.after(view.render().el);
                        $contact_requests.after($contact_requests.siblings('dd.requesting-xmpp-contact').tsort(crit));
                    } else if (subscription === 'both' || subscription === 'to') {
                        this.renderRosterItem(item, view);
                    }
                    changed_presence = item.changed.chat_status;
                    if (changed_presence) {
                        this.sortRoster(changed_presence);
                        sorted = true;
                    }
                    if (item.get('is_last')) {
                        if (!sorted) {
                            this.sortRoster(item.get('chat_status'));
                        }
                        if (!this.$el.is(':visible')) {
                            // Once all initial roster items have been added, we
                            // can show the roster.
                            this.$el.show();
                        }
                    }
                }
                // Hide the headings if there are no contacts under them
                _.each([$my_contacts, $contact_requests, $pending_contacts], function (h) {
                    if (h.nextUntil('dt').length) {
                        if (!h.is(':visible')) {
                            h.show();
                        }
                    }
                    else if (h.is(':visible')) {
                        h.hide();
                    }
                });
                $count = $('#online-count');
                $count.text('('+this.model.getNumOnlineContacts()+')');
                if (!$count.is(':visible')) {
                    $count.show();
                }
                return this;
            },

            sortRoster: function (chat_status) {
                var $my_contacts = this.$el.find('#xmpp-contacts');
                $my_contacts.siblings('dd.current-xmpp-contact.'+chat_status).tsort('a', {order:'asc'});
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.offline'));
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.unavailable'));
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.xa'));
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.away'));
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.dnd'));
                $my_contacts.after($my_contacts.siblings('dd.current-xmpp-contact.online'));
            }
        });

        this.XMPPStatus = Backbone.Model.extend({
            initialize: function () {
                this.set({
                    'status' : this.get('status') || 'online'
                });
                this.on('change', $.proxy(function () {
                    if (this.get('fullname') === undefined) {
                        converse.getVCard(
                            null, // No 'to' attr when getting one's own vCard
                            $.proxy(function (jid, fullname, image, image_type, url) {
                                this.save({'fullname': fullname});
                            }, this)
                        );
                    }
                }, this));
            },

            sendPresence: function (type) {
                if (type === undefined) {
                    type = this.get('status') || 'online';
                }
                var status_message = this.get('status_message'),
                    presence;
                // Most of these presence types are actually not explicitly sent,
                // but I add all of them here fore reference and future proofing.
                if ((type === 'unavailable') ||
                        (type === 'probe') ||
                        (type === 'error') ||
                        (type === 'unsubscribe') ||
                        (type === 'unsubscribed') ||
                        (type === 'subscribe') ||
                        (type === 'subscribed')) {
                    presence = $pres({'type':type});
                } else {
                    if (type === 'online') {
                        presence = $pres();
                    } else {
                        presence = $pres().c('show').t(type).up();
                    }
                    if (status_message) {
                        presence.c('status').t(status_message);
                    }
                }
                converse.connection.send(presence);
            },

            setStatus: function (value) {
                this.sendPresence(value);
                this.save({'status': value});
            },

            setStatusMessage: function (status_message) {
                converse.connection.send($pres().c('show').t(this.get('status')).up().c('status').t(status_message));
                this.save({'status_message': status_message});
                if (this.xhr_custom_status) {
                    $.ajax({
                        url:  this.xhr_custom_status_url,
                        type: 'POST',
                        data: {'msg': status_message}
                    });
                }
            }
        });

        this.XMPPStatusView = Backbone.View.extend({
            el: "span#xmpp-status-holder",

            events: {
                "click a.choose-xmpp-status": "toggleOptions",
                "click #fancy-xmpp-status-select a.change-xmpp-status-message": "renderStatusChangeForm",
                "submit #set-custom-xmpp-status": "setStatusMessage",
                "click .dropdown dd ul li a": "setStatus"
            },

            toggleOptions: function (ev) {
                ev.preventDefault();
                $(ev.target).parent().parent().siblings('dd').find('ul').toggle('fast');
            },

            change_status_message_template: _.template(
                '<form id="set-custom-xmpp-status">' +
                    '<input type="text" class="custom-xmpp-status" {{ status_message }}"'+
                        'placeholder="'+'自定义状态'+'"/>' +
                    '<button type="submit">'+'保存'+'</button>' +
                '</form>'),

            status_template: _.template(
                '<div class="xmpp-status">' +
                    '<a class="choose-xmpp-status {{ chat_status }}" data-value="{{status_message}}" href="#" title="'+'点击修改你的状态'+'">' +
                        '<span class="icon-{{ chat_status }}"></span>'+
                        '{{ status_message }}' +
                    '</a>' +
                '</div>'),

            renderStatusChangeForm: function (ev) {
                ev.preventDefault();
                var status_message = this.model.get('status') || 'offline';
                var input = this.change_status_message_template({'status_message': status_message});
                this.$el.find('.xmpp-status').replaceWith(input);
                this.$el.find('.custom-xmpp-status').focus().focus();
            },

            setStatusMessage: function (ev) {
                ev.preventDefault();
                var status_message = $(ev.target).find('input').val();
                if (status_message === "") {
                }
                this.model.setStatusMessage(status_message);
            },

            setStatus: function (ev) {
                ev.preventDefault();
                var $el = $(ev.target),
                    value = $el.attr('data-value');
                this.model.setStatus(value);
                this.$el.find(".dropdown dd ul").hide();
            },

            getPrettyStatus: function (stat) {
                if (stat === 'chat' || stat === 'online') {
                    pretty_status = '在线';
                } else if (stat === 'dnd') {
                    pretty_status = '繁忙';
                } else if (stat === 'xa') {
                    pretty_status = '长时间离开';
                } else if (stat === 'away') {
                    pretty_status = '离开';
                } else if (stat === 'offline') {
                    pretty_status = '离线';
                } else {
                    pretty_status = stat || '在线'; // XXX: Is 'online' the right default choice here?
                }
                return pretty_status;
            },

            updateStatusUI: function (model) {
                if (!(_.has(model.changed, 'status')) && !(_.has(model.changed, 'status_message'))) {
                    return;
                }
                var stat = model.get('status');
                // # For translators: the %1$s part gets replaced with the status
                // # Example, I am online
                var status_message = model.get('status_message') || this.getPrettyStatus(stat);
                this.$el.find('#fancy-xmpp-status-select').html(
                    this.status_template({
                        'chat_status': stat,
                        'status_message': status_message
                    }));
            },

            choose_template: _.template(
                '<dl id="target" class="dropdown">' +
                    '<dt id="fancy-xmpp-status-select" class="fancy-dropdown"></dt>' +
                    '<dd><ul class="xmpp-status-menu"></ul></dd>' +
                '</dl>'),

            option_template: _.template(
                '<li>' +
                    '<a href="#" class="{{ value }}" data-value="{{ value }}">'+
                        '<span class="icon-{{ value }}"></span>'+
                        '{{ text }}'+
                    '</a>' +
                '</li>'),

            initialize: function () {
                this.model.on("change", this.updateStatusUI, this);
            },

            render: function () {
                // Replace the default dropdown with something nicer
                var $select = this.$el.find('select#select-xmpp-status'),
                    chat_status = this.model.get('status') || 'offline',
                    options = $('option', $select),
                    $options_target,
                    options_list = [],
                    that = this;
                this.$el.html(this.choose_template());
                this.$el.find('#fancy-xmpp-status-select')
                        .html(this.status_template({
                            'status_message': this.model.get('status_message') || this.getPrettyStatus(chat_status),
                            'chat_status': chat_status
                            }));
                // iterate through all the <option> elements and add option values
                options.each(function(){
                    options_list.push(that.option_template({'value': $(this).val(),
                                                            'text': this.text
                                                            }));
                });
                $options_target = this.$el.find("#target dd ul").hide();
                $options_target.append(options_list.join(''));
                $select.remove();
                return this;
            }
        });

        this.Feature = Backbone.Model.extend();
        this.Features = Backbone.Collection.extend({
            /* Service Discovery
            * -----------------
            * This collection stores Feature Models, representing features
            * provided by available XMPP entities (e.g. servers)
            * See XEP-0030 for more details: http://xmpp.org/extensions/xep-0030.html
            * All features are shown here: http://xmpp.org/registrar/disco-features.html
            */
            model: converse.Feature,
            initialize: function () {
                this.localStorage = new Backbone.LocalStorage(
                    hex_sha1('converse.features'+converse.bare_jid));
                if (this.localStorage.records.length === 0) {
                    // localStorage is empty, so we've likely never queried this
                    // domain for features yet
                    converse.connection.disco.info(converse.domain, null, $.proxy(this.onInfo, this));
                    converse.connection.disco.items(converse.domain, null, $.proxy(this.onItems, this));
                } else {
                    this.fetch({add:true});
                }
            },

            onItems: function (stanza) {
                $(stanza).find('query item').each($.proxy(function (idx, item) {
                    converse.connection.disco.info(
                        $(item).attr('jid'),
                        null,
                        $.proxy(this.onInfo, this));
                }, this));
            },

            onInfo: function (stanza) {
                var $stanza = $(stanza);
                if (($stanza.find('identity[category=server][type=im]').length === 0) &&
                    ($stanza.find('identity[category=conference][type=text]').length === 0)) {
                    // This isn't an IM server component
                    return;
                }
                $stanza.find('feature').each($.proxy(function (idx, feature) {
                    this.create({
                        'var': $(feature).attr('var'),
                        'from': $stanza.attr('from')
                    });
                }, this));
            }
        });

        this.LoginPanel = Backbone.View.extend({
            tagName: 'div',
            id: "login-dialog",
            events: {
                'submit form#converse-login': 'authenticate'
            },
            tab_template: _.template(
                '<li><a class="current" href="#login">'+'登录'+'</a></li>'
            ),
            template: _.template(
                '<form id="converse-login">' +
                '<label>'+'用户名:'+'</label>' +
                '<input type="username" name="username">' +
                '<label>'+'密码:'+'</label>' +
                '<input type="password" name="password">' +
                '<input class="login-submit" type="submit" value="'+'登录'+'">' +
                '</form">'
            ),
            bosh_url_input: _.template(
                '<label>'+'BOSH服务地址:'+'</label>' +
                '<input type="text" id="bosh_service_url">'
            ),

            connect: function ($form, jid, password) {
                if ($form) {
                    $form.find('input[type=submit]').hide().after('<span class="spinner login-submit"/>');
                }
                converse.connection = new Strophe.Connection(converse.bosh_service_url);
                converse.connection.connect(jid, password, converse.onConnect);
            },

            showConnectButton: function () {
                var $form = this.$el.find('#converse-login');
                var $button = $form.find('input[type=submit]');
                if ($button.length) {
                    $button.show().siblings('span').remove();
                }
            },

            initialize: function (cfg) {
                cfg.$parent.html(this.$el.html(this.template()));
                this.$tabs = cfg.$parent.parent().find('#controlbox-tabs');
                this.model.on('connection-fail', function () { this.showConnectButton(); }, this);
                this.model.on('auth-fail', function () { this.showConnectButton(); }, this);
            },

            render: function () {
                this.$tabs.append(this.tab_template());
                this.$el.find('input#jid').focus();
                return this;
            },

            authenticate: function (ev) {
                var $form = $(ev.target),
                    $username_input = $form.find('input[name=username]'),
                    jid = $username_input.val() + '@' + DOMAIN,
                    $pw_input = $form.find('input[name=password]'),
                    password = $pw_input.val(),
                    $bsu_input = null,
                    errors = false;

                if (!converse.bosh_service_url) {
                    $bsu_input = $form.find('input#bosh_service_url');
                    converse.bosh_service_url = $bsu_input.val();
                    if (! converse.bosh_service_url)  {
                        errors = true;
                        $bsu_input.addClass('error');
                    }
                }
                if (! jid) {
                    errors = true;
                    $jid_input.addClass('error');
                }
                if (! password)  {
                    errors = true;
                    $pw_input.addClass('error');
                }
                if (errors) { return; }
                this.connect($form, jid, password);
                return false;
            },

            remove: function () {
                this.$tabs.empty();
                this.$el.parent().empty();
            }
        });

        this.ControlBoxToggle = Backbone.View.extend({
            tagName: 'a',
            className: 'toggle-online-users',
            id: 'toggle-controlbox',
            events: {
                'click': 'onClick'
            },
            attributes: {
                'href': "#"
            },

            template: _.template(
                '<strong class="conn-feedback">微信客服</strong>'+
                '<strong style="display: none" id="online-count">(0)</strong>'
            ),

            initialize: function () {
                this.render();
            },

            render: function () {
                $('#conversejs').append(this.$el.html(this.template()));
                return this;
            },

            showControlBox: function () {
                var controlbox = converse.chatboxes.get('controlbox');
                if (!controlbox) {
                    converse.chatboxes.add({
                        id: 'controlbox',
                        box_id: 'controlbox',
                        visible: true
                    });
                    if (converse.connection) {
                        converse.chatboxes.get('controlbox').save();
                    }
                } else {
                    controlbox.trigger('show');
                }
            },

            onClick: function (e) {
                e.preventDefault();
                if ($("div#controlbox").is(':visible')) {
                    var controlbox = converse.chatboxes.get('controlbox');
                    controlbox.trigger('hide');
                    if (converse.connection) {
                        try { 
                            controlbox.destroy();
                        } catch(err) {}
                    } 
                } else {
                    this.showControlBox();
                }
            }
        });

        // Initialization
        // --------------
        // This is the end of the initialize method.
        this.chatboxes = new this.ChatBoxes();
        this.chatboxesview = new this.ChatBoxesView({model: this.chatboxes});
        sharedChatBoxesView = this.chatboxesview;
        this.controlboxtoggle = new this.ControlBoxToggle();

        if ((this.prebind) && (!this.connection)) {
            if ((!this.jid) || (!this.sid) || (!this.rid) || (!this.bosh_service_url)) {
                this.log('If you set prebind=true, you MUST supply JID, RID and SID values');
                return;
            }
            this.connection = new Strophe.Connection(this.bosh_service_url);
            this.connection.attach(this.jid, this.sid, this.rid, this.onConnect);
        } else if (this.connection) {
            this.onConnected();
        }
        if (this.show_controlbox_by_default) { this.controlboxtoggle.showControlBox(); }


        //如果有用户名就帮他登陆呗, 自动登陆
        console.log("document.login_8868_uid:" +  document.login_8868_uid + ", pass:" + XMPP_DEFALUT_PASS);
        if(document.login_8868_uid != undefined) {
            setTimeout(function() {
                $('#converse-login input[type=username]').val(document.login_8868_uid);
                //$('#converse-login input[type=username]').val('test1');
                $('#converse-login input[type=password]').val(XMPP_DEFALUT_PASS);
                $('#converse-login').submit();
            }, 300);
        }

    };
    return {
        'initialize': function (settings, callback) {
            converse.initialize(settings, callback);
        }
    };
}));
