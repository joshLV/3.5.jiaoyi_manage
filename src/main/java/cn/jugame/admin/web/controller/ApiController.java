//package cn.jugame.admin.web.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Method;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import cn.jugame.service.ISysParameterService;
//import cn.jugame.service.ISysUserinfoService;
//import cn.jugame.util.Config;
//import cn.jugame.util.GamePasswdUtil;
//import cn.jugame.util.MD5;
//import cn.jugame.util.PageInfo;
//import cn.jugame.vo.SysParameter;
//import cn.jugame.web.util.PayHttpApiUtil;
//import cn.juhaowan.customer.dao.OnlineCustomerMonitorDao;
//import cn.juhaowan.customer.service.OnlineCustomerMonitorService;
//import cn.juhaowan.localmessage.service.LocalMessageService;
//import cn.juhaowan.member.dao.MemberAccountDao;
//import cn.juhaowan.member.service.MemberAccountService;
//import cn.juhaowan.member.service.MemberService;
//import cn.juhaowan.member.vo.Member;
//import cn.juhaowan.member.vo.MemberRealinfo;
//import cn.juhaowan.order.dao.OrderOperateLogDao;
//import cn.juhaowan.order.dao.OrderPayInfoDao;
//import cn.juhaowan.order.dao.OrdersC2cCancelReasonDao;
//import cn.juhaowan.order.dao.OrdersC2cDao;
//import cn.juhaowan.order.dao.OrdersDao;
//import cn.juhaowan.order.dao.SendgoodsTimeoutReasonDao;
//import cn.juhaowan.order.service.DutyService;
//import cn.juhaowan.order.service.OrderChangeConfigService;
//import cn.juhaowan.order.service.OrderChangeUserService;
//import cn.juhaowan.order.service.OrdersC2cCancelReasonService;
//import cn.juhaowan.order.service.OrdersC2cService;
//import cn.juhaowan.order.service.OrdersSelfDefinedService;
//import cn.juhaowan.order.service.OrdersService;
//import cn.juhaowan.order.service.SendgoodsTimeoutConfigService;
//import cn.juhaowan.order.service.SendgoodsTimeoutReasonService;
//import cn.juhaowan.order.service.SendgoodsTimeoutRecordService;
//import cn.juhaowan.order.service.impl.DefaultOrdersService;
//import cn.juhaowan.order.util.OrderUtil;
//import cn.juhaowan.order.vo.OrderChangeUser;
//import cn.juhaowan.order.vo.OrderOperateLog;
//import cn.juhaowan.order.vo.Orders;
//import cn.juhaowan.order.vo.OrdersC2c;
//import cn.juhaowan.order.vo.OrdersC2cCancelReason;
//import cn.juhaowan.order.vo.OrdersSelfDefined;
//import cn.juhaowan.order.vo.SendgoodsTimeoutConfig;
//import cn.juhaowan.order.vo.SendgoodsTimeoutReason;
//import cn.juhaowan.order.vo.SendgoodsTimeoutRecord;
//import cn.juhaowan.product.dao.GameProductTypeDao;
//import cn.juhaowan.product.dao.ProductDao;
//import cn.juhaowan.product.service.GameProductTypeService;
//import cn.juhaowan.product.service.ProductService;
//import cn.juhaowan.product.vo.GameProductType;
//import cn.juhaowan.product.vo.ProductC2c;
//import cn.juhaowan.order.vo.SysUserinfo;
//
///**
// * 后台对外开放api
// * 
// * @author houjt
// * 
// */
//@Controller
//public class ApiController {
//	Logger logger = LoggerFactory.getLogger(ApiController.class);
//	// 接口请求超时时间（30分）
//	private int validatetime = 30;
//	@Autowired
//	private OrdersC2cService ordersC2cService;
//	@Autowired
//	private OrdersC2cCancelReasonService ordersC2cCancelReasonService;
//	@Autowired
//	private SendgoodsTimeoutReasonService sendgoodsTimeoutReasonService;
//	@Autowired
//	private OrderChangeUserService orderChangeUserService;
//	@Autowired
//	private OrdersService ordersService;
//	@Autowired
//	private OnlineCustomerMonitorService onlineCustomerMonitorService;
//	@Autowired
//	private SendgoodsTimeoutRecordService sendgoodsTimeoutRecordService;
//	@Autowired
//	private SendgoodsTimeoutConfigService sendgoodsTimeoutConfigService;
//	@Autowired
//	private MemberAccountService memberAccountService;
//	@Autowired
//	private LocalMessageService localMessageService;
//	@Autowired
//	private ProductService productService;
//	@Autowired
//	private DutyService dutyService;
//	@Autowired
//	private GameProductTypeService gameProductTypeService;
//	@Autowired
//	private ISysUserinfoService sysUserinfoService;
//	@Autowired
//	private OrderChangeConfigService orderChangeConfigService;
//	@Autowired
//	private OrdersSelfDefinedService ordersSelfDefinedService;
//	@Autowired
//	private MemberService memberService;
//	@Autowired
//	private ISysParameterService parameterService;
//
//	@Autowired
//	private DefaultOrdersService defaultOrdersService;
//
//	@Autowired
//	private OrderOperateLogDao orderOperateLogDao;
//
//	@Autowired
//	private ISysUserinfoService iSysUserinfoService;
//
//	/**
//	 * 接口入口
//	 * 
//	 * @param cmd 接口名称
//	 * @param data 请求参数
//	 * @param sign 签名
//	 * @param appId 应用的id
//	 * @return
//	 */
//	@RequestMapping(value = "/api/api")
//	@ResponseBody
//	public JSONObject api(String cmd, String data, String sign, String appId, String requestTime) {
//		JSONObject json = new JSONObject();
//		logger.info("cmd = " + cmd + ",data=" + data + ",sign=" + sign + ",appId=" + appId + ",requestTime=" + requestTime);
//		if (StringUtils.isBlank(cmd) || StringUtils.isBlank(data) || StringUtils.isBlank(sign) || StringUtils.isBlank(appId) || StringUtils.isBlank(requestTime)) {
//			json.put("code", 9000);
//			json.put("msg", "必填参数为空");
//			return json;
//		}
//		String appKey = Config.getValue("api.app.appkey." + appId);
//		if (StringUtils.isBlank(appKey)) {
//			json.put("code", 9999);
//			json.put("msg", "没有找到应用信息");
//			return json;
//		}
//		// 验证接口请求时间
//		long nowDate = System.currentTimeMillis();
//		if (!validateRequestTime(Long.parseLong(requestTime), nowDate)) {
//			json.put("code", 9777);
//			json.put("msg", "请求接口时间超时失效");
//			return json;
//		}
//		// 验证加密信息
//		String mySign = MD5.encode(data + appKey + requestTime).substring(0, 8);
//		// cmd=sendOrder&data={"order_id":"ORD-131219-11541395408","opid":1054,"send_order_user_id":1054,"send_order_type":2,"remark":"是打两份快速"}&sign=FF6A2A80&appId=1000&requestTime=1387531444786
//		if (!mySign.toUpperCase().equals(sign.toUpperCase())) {
//			json.put("code", 9888);
//			json.put("msg", "信息被非法篡改");
//			return json;
//		}
//
//		try {
//			data = URLDecoder.decode(data, "utf-8");
//			Class<?> object = Class.forName(this.getClass().getName());
//			Method[] methods = object.getDeclaredMethods();
//			for (int i = 0; i < methods.length; i++) {
//				Method method = methods[i];
//				if (method.getName().equals(cmd)) {
//					return (JSONObject) method.invoke(this, data, appId);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//
//		return null;
//	}
//
//	/**
//	 * 验证请求时间是否过期
//	 * 
//	 * @param requestTime 请求时间
//	 * @param nowTime 当前时间
//	 * @return
//	 */
//	private boolean validateRequestTime(long requestTime, long nowTime) {
//		long tmp = nowTime - requestTime;
//		if ((tmp / (1000 * 60) - validatetime) > 0) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 接口的例子
//	 * 
//	 * @param data
//	 * @return 返回json对象
//	 */
//	public JSONObject test(String data, String appId) {
//		JSONObject json = new JSONObject();
//		json.put("code", 0);
//		json.put("msg", "操作成功");
//		json.put("body", "{}");
//		return json;
//	}
//
//	public JSONObject ssoLogin(String data, String appid) {
//		// data ={"login":"xxx"}
//		JSONObject obj = JSONObject.fromObject(data);
//		String login = "";
//		String password = "";
//		JSONObject json = new JSONObject();
//		try {
//			login = obj.getString("login");
//			password = obj.getString("password");
//		} catch (Exception e) {
//			json.put("code", "1");
//			json.put("msg", "data参数格式不对");
//			json.put("body", "{}");
//			return json;
//		}
//
//		// 用户角色列表
//		//List<Map<String, Object>> uroleList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> userMap = new HashMap<String, Object>();
//		cn.jugame.vo.SysUserinfo user = sysUserinfoService.login(login, password);
//
//		// System.out.println("结果uid == " + user.getUserId());
//		if (user == null) {
//			json.put("code", "1");
//			json.put("msg", "无此相关信息");
//			json.put("body", "{}");
//			return json;
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		// 普通管理员用户信息
//		userMap.put("user_id", user.getUserId());
//		userMap.put("loginid", user.getLoginid());
//		userMap.put("usertype", user.getUsertype());
//		userMap.put("status", user.getStatus());
//		userMap.put("create_time", sdf.format(user.getCreateTime()));
//		userMap.put("customer_service_id", user.getCustomServiceId() == null ? "" : user.getCustomServiceId());
//		userMap.put("online_status", user.getOnlineStatus());
//		userMap.put("is_customer", user.getIsCustomer());
//		userMap.put("is_object_customer", user.getIsObjectCustomer());
//		userMap.put("is_on_duty", user.getIsOnDuty());
//		userMap.put("customer_nickname", user.getCustomerNickname());
//		userMap.put("customer_qq", user.getCustomerQQ());
//		userMap.put("full_name", user.getFullname());
//
//		json.put("code", "0");
//		json.put("msg", "查询成功");
//		json.put("body", userMap);
//
//		return json;
//	}
//
//	public JSONObject ssoQQLogin(String data, String appid) {
//		// data ={"login":"xxx"}
//		JSONObject obj = JSONObject.fromObject(data);
//		String qq = "";
//		JSONObject json = new JSONObject();
//		try {
//			qq = obj.getString("qq");
//		} catch (Exception e) {
//			json.put("code", "1");
//			json.put("msg", "data参数格式不对");
//			json.put("body", "{}");
//			return json;
//		}
//
//		// 用户角色列表
//		//List<Map<String, Object>> uroleList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> userMap = new HashMap<String, Object>();
//		cn.jugame.vo.SysUserinfo user = sysUserinfoService.findByqq(qq);
//
//		if (user == null) {
//			json.put("code", "1");
//			json.put("msg", "无此相关信息");
//			json.put("body", "{}");
//			return json;
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		// 普通管理员用户信息
//		userMap.put("user_id", user.getUserId());
//		userMap.put("loginid", user.getLoginid());
//		userMap.put("usertype", user.getUsertype());
//		userMap.put("status", user.getStatus());
//		userMap.put("create_time", sdf.format(user.getCreateTime()));
//		userMap.put("customer_service_id", user.getCustomServiceId() == null ? "" : user.getCustomServiceId());
//		userMap.put("online_status", user.getOnlineStatus());
//		userMap.put("is_customer", user.getIsCustomer());
//		userMap.put("is_object_customer", user.getIsObjectCustomer());
//		userMap.put("is_on_duty", user.getIsOnDuty());
//		userMap.put("customer_nickname", user.getCustomerNickname());
//		userMap.put("customer_qq", user.getCustomerQQ());
//		userMap.put("full_name", user.getFullname());
//
//		json.put("code", "0");
//		json.put("msg", "查询成功");
//		json.put("body", userMap);
//
//		return json;
//	}
//
//	/**
//	 * 订单列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getOrderList(String data, String appId) {
//		JSONObject json = new JSONObject();
//		JSONObject obj = JSONObject.fromObject(data);
//		if (obj == null) {
//			json.put("code", "9999");
//			json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//			logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//			return json;
//		}
//		String status = "";
//		String goodsType = "";// 商品类型
//		String uid = "";
//		try {
//			status = obj.getString("status");
//			goodsType = obj.getString("goodsType");
//			uid = obj.getString("uid");
//		} catch (Exception e) {
//			json.put("code", "1");
//			json.put("msg", "data参数格式不对");
//			json.put("body", "{}");
//			return json;
//		}
//		// 构造查询map
//		Map<String, Object> map = new HashMap<String, Object>();
//		// 客服岗位ID
//		if (!("").equals(uid) && !("0").equals(uid)) {
//			map.put("uid", uid);
//		}
//		if (!("").equals(status)) {
//			map.put("status", status);
//		}
//		map.put("goodsType", goodsType);
//
//		// 查询数据
//		map.put("goodsType", 200);
//		PageInfo<Map<String, Object>> pageInfo = this.ordersC2cService.queryOrderC2cMapList(map, 1, 100, "", "");
//		json.put("code", 0);
//		json.put("msg", "查询列表成功");
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		if (pageInfo != null && pageInfo.getItems() != null) {
//			json.put("total", pageInfo.getRecordCount());
//			List<Map<String, Object>> list = pageInfo.getItems();
//			Map<String, Object> newMap = null;
//			for (int i = 0; i < list.size(); i++) {
//				newMap = list.get(i);
//				Map<String, Object> outMap = new HashMap<String, Object>();
//				outMap.put("orderId", newMap.get("order_id"));
//				outMap.put("chatCount", "0");
//				outMap.put("sessionId", "0");
//				outMap.put("userId", "0");
//				outMap.put("", "");
//
//				showList.add(outMap);
//			}
//			JSONArray rows = JSONArray.fromObject(showList);
//			json.put("body", rows);
//		} else {
//			json.put("body", "{}");
//		}
//		return json;
//	}
//
//	/**
//	 * 获取订单的详细信息
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getOrderDetail(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			if (order == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，根据订单号" + orderId + "查找订单对象为空!");
//				logger.info("调用appId:" + appId + "接口出错，根据订单号" + orderId + "查找订单对象为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Map<String, Object> map = new HashMap<String, Object>();
//			// 订单信息
//			map.put("order_id", order.getOrderId());
//			map.put("order_time", sf.format(order.getOrderTime()));
//			map.put("modify_time", sf.format(order.getModifyTime()));
//			map.put("order_amount", order.getOrderAmount());
//			map.put("goods_count", order.getGoodsCount());
//			map.put("order_status", order.getOrderStatus());
//			if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_DELETE) {
//				map.put("order_status_remark", "己删除");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_CANCEL) {
//				map.put("order_status_remark", "交易失败");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_CONFIRM_GOODS) {
//				map.put("order_status_remark", "待收货");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_NOT_PAY) {
//				map.put("order_status_remark", "未付款");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_RANSFER_SELL_ACCOUNT) {
//				map.put("order_status_remark", "待转账");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_REFUND) {
//				map.put("order_status_remark", "己退费");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_SUCCESS) {
//				map.put("order_status_remark", "交易成功");
//			} else if (order.getOrderStatus() == OrdersDao.ORDER_STATUS_WAIT_GOODS) {
//				map.put("order_status_remark", "未发货");
//			} else {
//				map.put("order_status_remark", "未知状态");
//			}
//			map.put("order_assign", order.getOrderAssign());
//			map.put("remark", order.getRemark() == null ? "" : order.getRemark());
//			map.put("order_ispay", order.getOrderIspay() == OrdersDao.ORDER_IS_PAY ? "己支付" : "未支付");
//			if (order.getOrderModel() == 1) {
//				map.put("order_model", "c2c");
//			} else if (order.getOrderModel() == 2) {
//				map.put("order_model", "api");
//			} else if (order.getOrderModel() == 3) {
//				map.put("order_model", "其他模式");
//			}
//			map.put("order_comefrom", order.getOrderComefrom() == null ? "" : order.getOrderComefrom());
//			if (order.getSendGoodsTime() != null) {
//				map.put("send_goods_time", sf.format(order.getSendGoodsTime()));
//			} else {
//				map.put("send_goods_time", "");
//			}
//			if (order.getGetGoodsTime() != null) {
//				map.put("get_goods_time", sf.format(order.getGetGoodsTime()));
//			} else {
//				map.put("get_goods_time", "");
//			}
//			map.put("order_free", order.getOrderFree());
//			if (order.getOrderPaySuccessTime() != null) {
//				map.put("order_pay_success_time", sf.format(order.getOrderPaySuccessTime()));
//			} else {
//				map.put("order_pay_success_time", "");
//			}
//			if (order.getOrderPayPlatformId() == OrderPayInfoDao.PAY_PLATFORM_YB) {
//				map.put("order_pay_platform_id", "易宝");
//			} else if (order.getOrderPayPlatformId() == OrderPayInfoDao.PAY_PLATFORM_UNIONPAY) {
//				map.put("order_pay_platform_id", "银联");
//			} else if (order.getOrderPayPlatformId() == OrderPayInfoDao.PAY_PLATFORM_JUGAME) {
//				map.put("order_pay_platform_id", "聚好玩");
//			} else if (order.getOrderPayPlatformId() == OrderPayInfoDao.PAY_PLATFORM_YB_CREDIT) {
//				map.put("order_pay_platform_id", "易宝/信用卡");
//			} else if (order.getOrderPayPlatformId() == OrderPayInfoDao.PAY_PLATFORM_ZFB) {
//				map.put("order_pay_platform_id", "支付宝");
//			}
//			if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_ACCOUNT) {
//				map.put("order_pay_way", "账户余额");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_ALIPAY) {
//				map.put("order_pay_way", "支付宝");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_BANKCARD) {
//				map.put("order_pay_way", "银行卡支付");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_CREDIT) {
//				map.put("order_pay_way", "信用卡支付");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_PCBANK) {
//				map.put("order_pay_way", "PC网银支付");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_PHONE) {
//				map.put("order_pay_way", "手机充值卡");
//			} else if (order.getOrderPayWay() == OrderPayInfoDao.PAY_WAY_UNION) {
//				map.put("order_pay_way", "银联支付");
//			}
//			map.put("order_pay_qudao", order.getOrderPayQudao() == null ? "" : order.getOrderPayQudao());
//			map.put("order_fr", order.getOrderFr() == null ? "" : order.getOrderFr());
//			MemberRealinfo orderBuyRealinfo = this.memberService.findRealInfoByUid(order.getOrderBuyUid());
//			String orderBuyRealName = "";
//			if (orderBuyRealinfo != null) {
//				if (orderBuyRealinfo.getRealName() != null) {
//					orderBuyRealName = orderBuyRealinfo.getRealName();
//				}
//			}
//			map.put("order_buy_realname", orderBuyRealName);
//			// 买家信息
//			map.put("order_buy_uid", order.getOrderBuyUid());
//			map.put("order_buy_qq", order.getOrderBuyQq() == null ? "" : order.getOrderBuyQq());
//			map.put("order_buy_game_uid", order.getOrderBuyGameUid() == null ? "" : order.getOrderBuyGameUid());
//			map.put("order_buy_game_role_id", order.getOrderBuyGameRoleId() == null ? "" : order.getOrderBuyGameRoleId());
//			map.put("order_buy_game_role_name", order.getOrderBuyGameRoleName() == null ? "" : order.getOrderBuyGameRoleName());
//			map.put("order_buy_phonenum", order.getOrderBuyPhonenum() == null ? "" : order.getOrderBuyPhonenum());
//			map.put("order_buy_game_role_level", order.getOrderBuyGameRoleLevel());
//			// 商品信息
//			map.put("goods_name", order.getGoodsName());
//			map.put("goods_id", order.getGoodsId());
//			String goodtypename = "未知";
//			try {
//				GameProductType gpt = gameProductTypeService.findbyGameIdAndProductTypeId(order.getGameId(), order.getGoodsType());
//				goodtypename = gpt.getTypeName();
//			} catch (Exception e) {
//
//			}
//
//			map.put("goods_type", goodtypename);
//			map.put("goods_price", order.getGoodsPrice());
//			map.put("goods_count", order.getGoodsCount());
//			map.put("goods_remark", order.getGoodsRemark() == null ? "" : order.getGoodsRemark());
//			map.put("goods_single_number", order.getGoodsSingleNumber());
//			map.put("goods_sell_pid", order.getGoodsSellPid());
//			map.put("game_channel_id", order.getChannelId());
//			map.put("quick_deliver_flag", order.getQuickDeliverFlag());
//			// 卖家信息
//			map.put("order_sell_uid", order.getOrderSellUid());
//			map.put("order_sell_game_uid", order.getOrderSellGameUid() == null ? "" : order.getOrderSellGameUid());
//			map.put("order_sell_game_role_id", order.getOrderSellGameRoleId() == null ? "" : order.getOrderSellGameRoleId());
//			map.put("order_sell_game_role_name", order.getOrderSellGameRoleName() == null ? "" : order.getOrderSellGameRoleName());
//			map.put("order_sell_user_type", order.getOrderSellUserType());
//			map.put("order_sell_game_role_level", order.getOrderSellGameRoleLevel());
//			//会员信息
//			Member orderSellMember = this.memberService.findMemberByUid(order.getOrderSellUid());
//			ProductC2c pc2c = productService.findProductC2cByProductId(order.getGoodsId());
//			if (orderSellMember != null) {
//				map.put("order_sell_phone", orderSellMember.getMobile() == null ? "" : orderSellMember.getMobile());
//				if(pc2c != null){
//					if(StringUtils.isNotBlank(pc2c.getContactQq())){
//						map.put("order_sell_qq", pc2c.getContactQq());
//					}else{
//						map.put("order_sell_qq", orderSellMember.getQq() == null ? "--" : orderSellMember.getQq());
//					}
//				}
//				
//
//			}
//
//			MemberRealinfo orderSellRealinfo = this.memberService.findRealInfoByUid(order.getOrderSellUid());
//			String orderSellRealName = "";
//			if (orderSellRealinfo != null) {
//				if (orderSellRealinfo.getRealName() != null) {
//					orderSellRealName = orderSellRealinfo.getRealName() == null ? "" : orderSellRealinfo.getRealName();
//				}
//			}
//			map.put("order_sell_realname", orderSellRealName);
//
//			// 游戏信息
//			map.put("game_id", order.getGameId());
//			map.put("game_name", order.getGameName());
//			map.put("game_server_name", order.getGameServerName()==null?"":order.getGameServerName());
//			map.put("game_area_name", order.getGameAreaName());
//			map.put("game_area_id", order.getGameAreaId());
//			map.put("game_server_id", order.getGameServerId());
//			OrdersSelfDefined ordersSelfDefined = this.ordersSelfDefinedService.findById(orderId);
//			if (ordersSelfDefined == null) {
//				map.put("order_self_defined", "");
//			} else {
//				map.put("order_self_defined", ordersSelfDefined.getOrderSelfDefined());
//			}
//			OrdersC2c ordersC2c = this.ordersC2cService.queryById(orderId);
//			// c2c订单信息
//			if (ordersC2c != null) {
//				// 客服名字
//				String customerService = "未分配";
//
//				// 物服名字
//				String objService = "未分配";
//
//				cn.jugame.vo.SysUserinfo sys = sysUserinfoService.findById(ordersC2c.getCustomerServiceId());
//				if (sys != null) {
//					customerService = sys.getFullname();
//				}
//				map.put("customer_service_id", ordersC2c.getCustomerServiceId());
//
//				map.put("customer_service_name", customerService);
//
//				if (ordersC2c.getCustomerServiceTime() != null) {
//					map.put("customer_service_time", sf.format(ordersC2c.getCustomerServiceTime()));
//				} else {
//					map.put("customer_service_time", "");
//				}
//				map.put("physic_service_id", ordersC2c.getPhysicServiceId());
//
//				if (ordersC2c.getPhysicServiceId() > 0) {
//					cn.jugame.vo.SysUserinfo sys1 = sysUserinfoService.findById(ordersC2c.getPhysicServiceId());
//					if (sys1 != null) {
//						objService = sys1.getFullname();
//					}
//				}
//				// System.out.println("物服名称 === " + objService);
//				// System.out.println("客服名称 === " + customerService);
//				map.put("physic_service_name", objService);
//				if (ordersC2c.getPhysicServiceTime() != null) {
//					map.put("physic_service_time", sf.format(ordersC2c.getPhysicServiceTime()));
//				} else {
//					map.put("physic_service_time", "");
//				}
//				map.put("order_c2c_status", ordersC2c.getOrderC2cStatus());
//				if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_CANCEL_ORDER) {
//					map.put("order_c2c_status_remark", "己撤销");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_CHANGE_SELL) {
//					map.put("order_c2c_status_remark", "转单卖家");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_CUSTOMER_CHECK) {
//					map.put("order_c2c_status_remark", "客服审撤");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_CUSTOMER_SEND) {
//					map.put("order_c2c_status_remark", "客服派单");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_SEND_GOODS) {
//					map.put("order_c2c_status_remark", "物服发货");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_SEND_GOODS_AGAIN) {
//					map.put("order_c2c_status_remark", "物服重新发货");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_SUCCESS) {
//					map.put("order_c2c_status_remark", "发货完成");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_SYSTEM_SEND) {
//					map.put("order_c2c_status_remark", "系统/主管分单");
//				} else if (ordersC2c.getOrderC2cStatus() == OrdersC2cDao.ORDERSC2C_STATUS_TRANSFER_SELL_ACCOUNT) {
//					map.put("order_c2c_status_remark", "转账卖家");
//				}
//				if (ordersC2c.getGoodsPosition() == 1) {
//					map.put("goods_position", "交易所");
//				} else if (ordersC2c.getGoodsPosition() == 2) {
//					map.put("goods_position", "仓库");
//				} else if (ordersC2c.getGoodsPosition() == 3) {
//					map.put("goods_position", "邮箱");
//				} else if (ordersC2c.getGoodsPosition() == 4) {
//					map.put("goods_position", "包裹");
//				} else {
//					map.put("goods_position", "未知");
//				}
//				if (ordersC2c.getSelluserGameAccount() != null) {
//					map.put("selluser_game_account", ordersC2c.getSelluserGameAccount());
//				} else {
//					map.put("selluser_game_account", "");
//				}
//				if (ordersC2c.getSelluserGameAccountPwd() != null && !"".equals(ordersC2c.getSelluserGameAccountPwd())) {
//					map.put("selluser_game_account_pwd", GamePasswdUtil.decode(ordersC2c.getSelluserGameAccountPwd()));
//				} else {
//					map.put("selluser_game_account_pwd", "");
//				}
//				if (ordersC2c.getSelluserGameSafekey() != null && !"".equals(ordersC2c.getSelluserGameSafekey())) {
//					map.put("selluser_game_safekey", GamePasswdUtil.decode(ordersC2c.getSelluserGameSafekey()));
//				} else {
//					map.put("selluser_game_safekey", "");
//				}
//				if (ordersC2c.getSendOrderType() == 1) {
//					map.put("send_order_type", "系统指定");
//				} else if (ordersC2c.getSendOrderType() == 2) {
//					map.put("send_order_type", "卖家指定");
//				}
//				map.put("is_customer_accept", ordersC2c.getIsCustomerAccept());
//				map.put("is_physic_accept", ordersC2c.getIsPhysicAccept());
//				map.put("son_channel_id", order.getSonChannelId()==null?"":order.getSonChannelId());
//				
//				map.put("buy_game_account", ordersC2c.getBuyuserGameAccount()==null?"":ordersC2c.getBuyuserGameAccount());
//				map.put("buy_game_account_pwd", ordersC2c.getBuyuserGameAccountPwd()==null?"":ordersC2c.getBuyuserGameAccountPwd());
//				map.put("buy_game_account_safekey", ordersC2c.getBuyuserGameSafekey()==null?"":ordersC2c.getBuyuserGameSafekey());
//
//				OrderOperateLog orderOperateLog = this.orderOperateLogDao.queryOrderOperateLogInfo(orderId, OrderOperateLogDao.ORDER_LOG_TYPE_PHYSIC_CANCEL);
//				if (orderOperateLog != null) {
//					map.put("cancalReason", orderOperateLog.getOperateReason());
//					cn.jugame.vo.SysUserinfo sysUserinfo = this.iSysUserinfoService.findById(orderOperateLog.getOperateUserId());
//					String remark = "操作员(" + sysUserinfo.getFullname() + "),相关撤单原因:" + orderOperateLog.getRemark();
//					map.put("cancalRemark", remark);
//				} else {
//					map.put("cancalReason", "");
//					map.put("cancalRemark", "");
//				}
//			}
//			// 标志是否可以直接转单
//			String isSpeSeller = "0";
//			List<SysParameter> parameterList = parameterService.listByParaCode("ORDER_C2C_SPECIFY_SELLER");
//			if (null != parameterList) {
//				SysParameter parameter = parameterList.get(0);
//				if (parameter.getParaValue().contains(String.valueOf(order.getOrderSellUid()))) {
//					isSpeSeller = "1";
//				}
//			}
//			map.put("isSpeSeller", isSpeSeller);
//
//			JSONObject body = JSONObject.fromObject(map);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 发货
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject sendGoodsSuccess(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "传入参数参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "订单编号参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			int result = this.ordersC2cService.sendGoods(orderId, (int) operateId, "");
//			if (result > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，发货失败!");
//				logger.info("调用appId:" + appId + "接口出错，发货失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 客服审撤
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject checkCancelOrder(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "订单id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			Object type = paramJson.get("type");
//			if (type == null) {
//				json.put("code", "9000");
//				json.put("msg", "type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，type参数为空!");
//				return json;
//			}
//			Object needAddStock = paramJson.get("need_add_stock");
//			if (needAddStock == null) {
//				json.put("code", "9000");
//				json.put("msg", "need_add_stock参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，need_add_stock参数为空!");
//				return json;
//			}
//			String reason = paramJson.getString("reason") == null ? "" : paramJson.getString("reason");
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			int result = this.ordersC2cService.customerCheckOrder(orderId, reason, remark, (int) operateId, (int) type, String.valueOf(needAddStock));
//			if (result > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，审撤失败!");
//				logger.info("调用appId:" + appId + "接口出错，审撤失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//
//	}
//
//	/**
//	 * 撤单原因列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject ordersCancelReasonList(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			Object type = paramJson.get("type");
//			if (type == null) {
//				json.put("code", "9000");
//				json.put("msg", "type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，type参数为空!");
//				return json;
//			}
//			// List<OrdersC2cCancelReason> list = this.ordersC2cCancelReasonService.queryAllReason((int)type,OrdersC2cCancelReasonDao.OPERATE_TYPE_CANCEL_ORDER);
//			List<OrdersC2cCancelReason> list = this.ordersC2cCancelReasonService.queryReasonsByParams(OrdersC2cCancelReasonDao.CANCEL_REASON_TYPE_CUSTOMER, OrdersC2cCancelReasonDao.OPERATE_TYPE_CANCEL_ORDER,
//					OrdersC2cCancelReasonDao.ORDER_BELONGING_JSH);
//
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "撤单返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list.size(); i++) {
//				OrdersC2cCancelReason ordersC2cCancelReason = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("id", ordersC2cCancelReason.getId());
//				tmp.put("cancel_reason", ordersC2cCancelReason.getCancelReason());
//				tmp.put("status", ordersC2cCancelReason.getStatus());
//				tmp.put("contact_flag", ordersC2cCancelReason.getContactFlag());
//				tmp.put("reason_type", ordersC2cCancelReason.getReasonType());
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//
//		}
//
//	}
//
//	/**
//	 * 超时赔付原因列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject sendgoodsTimeoutReasonList(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			Object type = paramJson.get("type");
//			if (type == null) {
//				json.put("code", "9000");
//				json.put("msg", "type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，type参数为空!");
//				return json;
//			}
//			List<SendgoodsTimeoutReason> list = this.sendgoodsTimeoutReasonService.querySendgoodsTimeoutReasonListByFlag(1, (int) type);
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "超时赔付返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list.size(); i++) {
//				String typeName = "";
//				if (list.get(i).getReasonType() == 0) {
//					typeName = "[赔付]";
//				} else if (list.get(i).getReasonType() == 1) {
//					typeName = "[不赔付]";
//				} else {
//					typeName = "[未知]";
//				}
//				SendgoodsTimeoutReason sendgoodsTimeoutReason = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("reason_id", sendgoodsTimeoutReason.getReasonId());
//				tmp.put("reason_remark", sendgoodsTimeoutReason.getReasonRemark() + typeName);
//				tmp.put("reason_type", sendgoodsTimeoutReason.getReasonType());
//				tmp.put("reason_flag", sendgoodsTimeoutReason.getReasonFlag());
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 转单卖家列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getChangeSellUserList(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			if (order == null) {
//				json.put("code", "9999");
//				json.put("msg", "接口出错，根据订单ID查找订单对象为空!");
//				logger.info("调用appId:" + appId + "接口出错，根据订单ID查找订单对象为空!");
//				return json;
//			}
//			// 计算比列
//			double toSingleRate = (order.getGoodsCount() * order.getGoodsSingleNumber()) / order.getOrderAmount();
//			List<OrderChangeUser> list = this.orderChangeUserService.getOrderChangeUserList(order.getGameId(), order.getGameServerId(), order.getGameAreaId(), order.getChannelId(), order.getGoodsType(), order.getOrderAmount(), toSingleRate);
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "获取转单卖家列表，返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list.size(); i++) {
//				OrderChangeUser orderChangeUser = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("id", orderChangeUser.getId());
//				tmp.put("uid", orderChangeUser.getUid());
//				tmp.put("game_id", orderChangeUser.getGameId());
//				tmp.put("game_area_id", orderChangeUser.getGameAreaId());
//				tmp.put("game_server_id", orderChangeUser.getGameServerId());
//				tmp.put("game_uid", orderChangeUser.getGameUid());
//				tmp.put("game_role_id", orderChangeUser.getGameRoleId());
//				tmp.put("game_role_name", orderChangeUser.getGameRoleName());
//				tmp.put("game_account", orderChangeUser.getGameAccount());
//				tmp.put("game_pwd", orderChangeUser.getGamePwd());
//				tmp.put("game_safekey", orderChangeUser.getGameSafekey());
//				tmp.put("goods_position", orderChangeUser.getGoodsPosition());
//				tmp.put("create_time", sf.format(orderChangeUser.getCreateTime()));
//				tmp.put("channel_id", orderChangeUser.getChannelId());
//				tmp.put("allowTosingleHighestAmount", orderChangeUser.getAllowToSingleHighestAmount());
//				tmp.put("allowToSingleHighestRate", orderChangeUser.getAllowToSingleHighestRate());
//				tmp.put("allowToSingleStandardRate", orderChangeUser.getAllowToSingleStandardRate());
//				tmp.put("allowTosingle", orderChangeUser.getAllowToSingle());
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 申请撤单
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject cancelOrderRequest(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			Object reasonId = paramJson.get("reason_id");
//			if (reasonId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用接口出错，reasonId参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，reasonId参数为空!");
//				return json;
//			}
//			String reasonRemark = paramJson.getString("reason_remark") == null ? "" : paramJson.getString("reason_remark");
//			OrdersC2cCancelReason ordersC2cCancelReason = this.ordersC2cCancelReasonService.queryByReasonId((int) reasonId);
//			OrdersC2c ordersC2c = this.ordersC2cService.queryById(orderId);
//			if (onlineCustomerMonitorService.queryCustomerType((int) operateId) == OnlineCustomerMonitorDao.IS_OBJECT_CUSTOMER) {
//				if (ordersC2c.getPhysicServiceId() != (int) operateId) {
//					json.put("code", "9000");
//					json.put("msg", "调用接口出错，当前操作用户ID" + (int) operateId + "无权限操作");
//					logger.info("调用appId:" + appId + "接口出错，当前操作用户ID" + (int) operateId + "无权限操作");
//					return json;
//				}
//			}
//			// 执行撤单申请
//			int result = this.ordersC2cService.cancelOrderRequest(orderId, ordersC2cCancelReason, (int) operateId, reasonRemark);
//			if (result > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，申请撤单失败!");
//				logger.info("调用appId:" + appId + "接口出错，申请撤单失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 转账给卖家
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject transferAccount(String data, String appId) {
//
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			String outId = paramJson.getString("out_id") == null ? "" : paramJson.getString("out_id");
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			Orders orders = this.ordersService.queryOrderById(orderId);
//			// 判断当前订单是否己进行过赔付
//			SendgoodsTimeoutRecord tempRecord = this.sendgoodsTimeoutRecordService.findSendgoodsTimeoutRecordByOrderId(orderId);
//			if (tempRecord != null) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，订单号" + orderId + "己执行过超时赔付，不能再进行赔付");
//				logger.info("调用appId:" + appId + "接口出错，订单号" + orderId + "己执行过超时赔付，不能再进行赔付");
//				return json;
//			}
//			if (outId != null && !("").equals(outId)) {
//				// 转账订单号为快速发货且超时发货的订单，须记录超时原因,同时判断是否赔付
//				SendgoodsTimeoutReason sendgoodsTimeoutReason = this.sendgoodsTimeoutReasonService.findByReasonId(Integer.parseInt(outId));
//				if (sendgoodsTimeoutReason == null) {
//					json.put("code", "9999");
//					json.put("msg", "调用接口出错，根据超时赔付ID" + outId + "查找超时赔付对象为空");
//					logger.info("调用appId:" + appId + "接口出错，转账成功，根据超时赔付ID" + outId + "查找超时赔付对象为空");
//					return json;
//				}
//				long diff = (orders.getGetGoodsTime().getTime() - orders.getOrderPaySuccessTime().getTime()) / 1000;
//				int temp = (int) (diff / 60);
//				// 记录赔付
//				SendgoodsTimeoutRecord sendgoodsTimeoutRecord = new SendgoodsTimeoutRecord();
//				sendgoodsTimeoutRecord.setRecordUid((int) operateId);
//				sendgoodsTimeoutRecord.setRecordOrderid(orderId);
//				sendgoodsTimeoutRecord.setRecordTime(new Date());
//				sendgoodsTimeoutRecord.setRecordTimeoutTime(temp);
//				sendgoodsTimeoutRecord.setRecordReason(sendgoodsTimeoutReason.getReasonRemark());
//				sendgoodsTimeoutRecord.setRecordIspay(sendgoodsTimeoutReason.getReasonType());
//				SendgoodsTimeoutConfig sendgoodsTimeoutConfig = this.sendgoodsTimeoutConfigService.getSendgoodsTimeoutConfigByTime(temp, 1);
//				// 获取比例
//				int radio = sendgoodsTimeoutConfig.getConfigRadio();
//				sendgoodsTimeoutRecord.setRecordRadio(radio);
//				logger.info("读取超时赔付比例为:" + radio);
//				if (sendgoodsTimeoutReason.getReasonType() == SendgoodsTimeoutReasonDao.TIME_OUT_TYPE_PAY) {
//					logger.info("当前订单" + orderId + "须要进行超时赔付!");
//					double money = (orders.getOrderAmount() * radio) / 100;
//					logger.info("计算订单" + orderId + "超时赔付金额为:" + money);
//					sendgoodsTimeoutRecord.setRecordMoney(money);
//
//					// 调用接口给配置好的账户扣钱，再给买家加钱
//					try {
//						logger.info("给用户充值赔付账户资金");
//						// 获取当前时间戳
//						logger.info("给用户充值赔付账户资金");
//						int num = this.memberAccountService.rechargeForUser(String.valueOf(orders.getOrderBuyUid()), String.valueOf(money), String.valueOf(MemberAccountDao.ACCOUNT_TYPE_NOT_CASH), "6", "超时赔付");
//						if (num > 0) {
//							json.put("code", "9999");
//							json.put("msg", "调用接口出错，转账成功，给买家充值失败");
//							logger.info("调用appId:" + appId + "接口出错，转账成功，给买家充值失败");
//							return json;
//						}
//						// 记录日志
//						this.ordersService.writeOrderLog((int) operateId, orderId, 1, OrderOperateLogDao.ORDER_LOG_TYPE_PAY_TIMEOUNT, "", sendgoodsTimeoutReason.getReasonRemark());
//						logger.info("充值成功!");
//						// 充值成功，发站内信通知用户
//						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//						String msg = "您购买的" + orders.getGoodsName() + "已交易成功，由于超出规定时间，现已将物品金额的" + radio + "%赔付到您的账户内，请查收。感谢您对8868交易平台的支持！";
//						this.localMessageService.sendMessageToUser(orders.getOrderBuyUid(), msg, "超时赔付通知", sf.format(new Date()));
//					} catch (Exception e) {
//						logger.error("", e);
//					}
//				}
//				int recordResult = this.sendgoodsTimeoutRecordService.saveSendgoodsTimeoutRecord(sendgoodsTimeoutRecord);
//				if (recordResult == 1) {
//					json.put("code", "9999");
//					json.put("msg", "调用appId:" + appId + "接口出错，转账成功，给买家充值成功，保存赔付结果失败");
//					logger.info("调用appId:" + appId + "接口出错，转账成功，给买家充值成功，保存赔付结果失败");
//					return json;
//				}
//			}
//			try {
//				// 发货成功，给卖家转钱，同时发站内信
//				int a = OrderUtil.sendMessageAndSellevenue(orders);
//				if (a > 0) {
//					json.put("code", "9999");
//					json.put("msg", "调用接口出错，转账失败");
//					logger.info("调用appId:" + appId + "接口出错，转账失败");
//					return json;
//				}
//				// 更新商品的成交数
//				productService.modifyProductSoldoutNumber(orders.getGoodsId(), //
//						orders.getGoodsCount(), orders.getOrderId(), orders.getOrderBuyUid()); // goodsCount成交笔数
//				int transFerResult = this.ordersC2cService.transferAccount(orderId, (int) operateId, remark);
//				if (transFerResult > 0) {
//					json.put("code", "9999");
//					json.put("msg", "调用接口出错，转账失败");
//					logger.info("调用appId:" + appId + "接口出错，转账失败");
//					return json;
//				}
//				json.put("code", "0");
//				json.put("msg", "转账成功");
//				return json;
//			} catch (Exception e) {
//				json.put("code", "9999");
//				json.put("msg", "调用接口出错，请联系管理员");
//				logger.error("", e);
//				return json;
//			}
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 符合转单条件的(客服/物服)列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject changeUserList(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object type = paramJson.get("type");
//			if (type == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，type参数为空!");
//				return json;
//			}
//			List<SysUserinfo> list = null;
//			if ((int) type == 0) {
//				list = this.dutyService.transferCS(orderId);
//			} else if ((int) type == 1) {
//				list = this.dutyService.transferOS(orderId);
//			}
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口成功，返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list.size(); i++) {
//				SysUserinfo sysUserinfo = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("user_id", sysUserinfo.getUserId());
//				tmp.put("loginid", sysUserinfo.getLoginid());
//				tmp.put("usertype", sysUserinfo.getUsertype());
//				tmp.put("status", sysUserinfo.getStatus());
//				tmp.put("create_time", sf.format(sysUserinfo.getCreateTime()));
//				tmp.put("customer_service_id", sysUserinfo.getCustomServiceId());
//				tmp.put("online_status", sysUserinfo.getOnlineStatus());
//				tmp.put("is_customer", sysUserinfo.getIsCustomer());
//				tmp.put("is_object_customer", sysUserinfo.getIsObjectCustomer());
//				tmp.put("is_on_duty", sysUserinfo.getIsOnDuty());
//				tmp.put("customer_nickname", sysUserinfo.getCustomerNickname());
//				tmp.put("customer_qq", sysUserinfo.getCustomerQq());
//				tmp.put("is_investment_service", sysUserinfo.getIsInvestmentService());
//				tmp.put("is_visible_weixin", sysUserinfo.getIsVisibleWeiXin());
//				tmp.put("full_name", sysUserinfo.getFullname());
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//
//	}
//
//	/**
//	 * 转单给(客服/物服)
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject changeUser(String data, String appId) {
//		try {
//
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			Object changeUserId = paramJson.get("change_user_id");
//			if (changeUserId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，change_user_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，change_user_id参数为空!");
//				return json;
//			}
//			Object changeUserType = paramJson.get("change_user_type");
//			if (changeUserType == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，change_user_type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，change_user_type参数为空!");
//				return json;
//			}
//			OrdersC2c ordersC2c = this.ordersC2cService.queryById(orderId);
//			// 执行操作
//			int result = 0;
//			if ((int) changeUserType == OrdersC2cDao.ORDERC2C_CHANGE_USER_CUSTOMER) {
//				result = this.ordersC2cService.changeOrderC2cUser(orderId, ordersC2c.getCustomerServiceId(), (int) changeUserId, (int) changeUserType, (int) operateId, remark);
//			} else if ((int) changeUserType == OrdersC2cDao.ORDERC2C_CHANGE_USER_PHYSIC) {
//				result = this.ordersC2cService.changeOrderC2cUser(orderId, ordersC2c.getPhysicServiceId(), (int) changeUserId, (int) changeUserType, (int) operateId, remark);
//			}
//			if (result > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，订单号" + orderId + "转单失败!");
//				logger.info("调用appId:" + appId + "接口出错，订单号" + orderId + "转单失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 符合派单的(物服/客服)列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject sendOrderUserList(String data, String appId) {
//		JSONObject json = new JSONObject();
//		try {
//
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object type = paramJson.get("type");
//			if (type == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，type参数为空!");
//				return json;
//			}
//			List<SysUserinfo> list = null;
//			int uid = 0;
//			if ((int) type == 0) {
//				uid = this.dutyService.assignOrderToCs(orderId);
//				SysUserinfo sysUserinfo = this.dutyService.findById(uid);
//				list = new ArrayList<SysUserinfo>();
//				list.add(sysUserinfo);
//			} else if ((int) type == 1) {
//				Orders order = this.ordersService.queryOrderById(orderId);
//				GameProductType gameProductType = this.gameProductTypeService.findbyGameIdAndProductTypeId(order.getGameId(), order.getGoodsType());
//				if (gameProductType == null) {
//					json.put("code", "9999");
//					json.put("msg", "调用appId:" + appId + "接口失败，根据游戏ID" + order.getGameId() + "和商品类型" + order.getGoodsType() + "查找游戏商品类型为空!");
//					logger.info("调用appId:" + appId + "接口失败，根据游戏ID" + order.getGameId() + "和商品类型" + order.getGoodsType() + "查找游戏商品类型为空!");
//					return json;
//				}
//				if (gameProductType.getAutoAssign() == GameProductTypeDao.ASSIGN_SELECT) {
//					list = this.dutyService.onDutyOSList();
//				} else {
//					// 自动派单
//					uid = this.dutyService.assignOrderToOs(orderId);
//					SysUserinfo sysUserinfo = this.dutyService.findById(uid);
//					list = new ArrayList<SysUserinfo>();
//					list.add(sysUserinfo);
//				}
//			}
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口成功，返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list.size(); i++) {
//				SysUserinfo sysUserinfo = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("user_id", sysUserinfo.getUserId());
//				tmp.put("loginid", sysUserinfo.getLoginid());
//				tmp.put("usertype", sysUserinfo.getUsertype());
//				tmp.put("status", sysUserinfo.getStatus());
//				tmp.put("create_time", sf.format(sysUserinfo.getCreateTime()));
//				tmp.put("customer_service_id", sysUserinfo.getCustomServiceId());
//				tmp.put("online_status", sysUserinfo.getOnlineStatus());
//				tmp.put("is_customer", sysUserinfo.getIsCustomer());
//				tmp.put("is_object_customer", sysUserinfo.getIsObjectCustomer());
//				tmp.put("is_on_duty", sysUserinfo.getIsOnDuty());
//				tmp.put("customer_nickname", sysUserinfo.getCustomerNickname());
//				tmp.put("customer_qq", sysUserinfo.getCustomerQq());
//				tmp.put("is_investment_service", sysUserinfo.getIsInvestmentService());
//				tmp.put("is_visible_weixin", sysUserinfo.getIsVisibleWeiXin());
//				tmp.put("full_name", sysUserinfo.getFullname());
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			json.put("code", "9998");
//			json.put("msg", "无可派单物服或订单状态不可派单");
//			return json;
//		}
//	}
//
//	/**
//	 * 派单
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject sendOrder(String data, String appId) {
//		try {
//
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			Object sendOrderUserId = paramJson.get("send_order_user_id");
//			if (sendOrderUserId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，send_order_user_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，send_order_user_id参数为空!");
//				return json;
//			}
//			Object sendOrderType = paramJson.get("send_order_type");
//			if (sendOrderType == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，send_order_type参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，send_order_type参数为空!");
//				return json;
//			}
//			// 派单前先受理
//			int result = this.ordersC2cService.updateOrderIsAccept(orderId, (int) sendOrderType, (int) operateId);
//			if (result > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，订单" + orderId + "受理失败!");
//				logger.info("调用appId:" + appId + "接口出错，订单" + orderId + "受理失败!!");
//				return json;
//			}
//			int num = 0;
//			if ((int) sendOrderType == OrdersC2cDao.ORDERSC2C_USER_TYPE_CUSTOMER) {
//				num = this.ordersC2cService.systemSendOrder(orderId, (int) sendOrderUserId, remark, (int) operateId);
//			} else if ((int) sendOrderType == OrdersC2cDao.ORDERSC2C_USER_TYPE_PHYSIC) {
//				num = this.ordersC2cService.customerSendOrder(orderId, (int) sendOrderUserId, remark);
//			}
//			if (num > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，订单号" + orderId + "受理成功，派单失败!");
//				logger.info("调用appId:" + appId + "接口出错，订单号" + orderId + "受理成功，派单失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 验证C2C订单是否需要超时赔付
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public JSONObject checkSendGoodsTimeOut(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			if (order.getQuickDeliverFlag() == 1) {
//				logger.info("订单号：" + order.getOrderId() + "支付成功时间：" + order.getOrderPaySuccessTime());
//				logger.info("订单号：" + order.getOrderId() + "成功发货时间：" + order.getGetGoodsTime());
//				// 计算时间秒
//				long diff = (order.getGetGoodsTime().getTime() - order.getOrderPaySuccessTime().getTime()) / 1000;
//				logger.info("订单号：" + order.getOrderId() + "发货时长为:" + diff);
//				// 获取发货超时时间配置
//				List<SendgoodsTimeoutConfig> list = this.sendgoodsTimeoutConfigService.querySendgoodsTimeoutConfigList(1, "ASC");
//				int time = list.get(0).getConfigTime();
//				logger.info("发货超时最底时间配置为:" + time);
//				if ((diff / 60) > time) {
//					json.put("isOut", 1);
//					return json;
//				}
//			}
//
//			json.put("isOut", 0);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 查询当前买家QQ的订单列表数据
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getNoSuccessOrderC2cList(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String qq = paramJson.getString("qq");
//			if (qq == null || qq.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，qq参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，qq参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("buyerQq", qq);
//			param.put("uid", operateId);
//			param.put("status", 1);
//			PageInfo<Map<String, Object>> pageInfo = this.ordersC2cService.queryOrderC2cMapList(param, 1, 50000, "", "");
//			if (pageInfo == null || pageInfo.getItems() == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口成功，返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			List<Map<String, Object>> list = pageInfo.getItems();
//			for (int i = 0; i < list.size(); i++) {
//				Map<String, Object> item = list.get(i);
//				Map<String, Object> tmp = new HashMap<String, Object>();
//				tmp.put("orderId", item.get("order_id"));
//				showList.add(tmp);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * to转单卖家
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject toChangeSell(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			// 计算比列
//			double toSingleRate = (order.getGoodsCount() * order.getGoodsSingleNumber()) / order.getOrderAmount();
//			// 根据订单匹配相关的转单用户列表
//			List<OrderChangeUser> changeUserList = orderChangeUserService.getOrderChangeUserList(order.getGameId(), order.getGameServerId(), order.getGameAreaId(), order.getChannelId(), order.getGoodsType(), order.getOrderAmount(), toSingleRate);
//			if (changeUserList == null || changeUserList.size() == 0) {
//				json.put("code", "1");
//				json.put("msg", "无转单用户，操作失败！");
//				return json;
//			}
//			// 进行转单
//			String reason = "客服审撤:转单卖家，将C2C订单状态由物服审核改为转单卖家";
//			int num = this.ordersC2cService.toChangeSell(orderId, reason, (int) operateId, "");
//			if (num != 0) {
//				json.put("code", "1");
//				json.put("msg", "修改C2C订单为转单卖家出错！");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * do转单卖家
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject doChangeSell(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			String changeId = paramJson.getString("change_id");
//			if (changeId == null || changeId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，change_Id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，change_Id参数为空!");
//				return json;
//			}
//			String update = paramJson.getString("update");
//			if (update == null || update.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，update参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，update参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			OrderChangeUser orderChangeUser = this.orderChangeUserService.findbyId(Integer.parseInt(changeId));
//			if (orderChangeUser == null) {
//				json.put("code", "9999");
//				json.put("msg", "根据转单用户对象ID" + changeId + "查找转单用户对象为空!");
//				logger.info("调用appId:" + appId + "接口出错，根据转单用户对象ID" + changeId + "查找转单用户对象为空!");
//				return json;
//			}
//			int sellId = orderChangeUser.getUid();
//			Member member = this.memberService.findMemberByUid(sellId);
//			if (member == null) {
//				json.put("code", "9999");
//				json.put("msg", "根据用户ID" + sellId + "查找用户对象为空!");
//				logger.info("调用appId:" + appId + "接口出错，根据用户ID" + sellId + "查找用户对象为空!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			if (order.getOrderSellUid() == sellId) {
//				json.put("code", "9999");
//				json.put("msg", "转单失败，转单卖家的ID重复!");
//				logger.info("调用appId:" + appId + "接口出错，转单失败，转单卖家的ID重复!");
//				return json;
//			}
//			boolean needUpdate = false;
//			if (update.equals("1")) {
//				needUpdate = true;
//			}
//			// 调接口通知支付修改卖家信息
//			int payResult = PayHttpApiUtil.updateZhifuOrderInfo(orderId, "", "", "", "", "", "", "", "", String.valueOf(sellId), "转单卖家，修改卖家信息");
//			if (payResult > 0) {
//				json.put("code", "9999");
//				json.put("msg", "转单失败，通知支付修改订单" + orderId + "卖家ID失败!");
//				logger.info("调用appId:" + appId + "接口出错，转单失败，通知支付修改订单" + orderId + "卖家ID失败!");
//				return json;
//			}
//			String reason = "转单卖家，转单前卖家ID为:" + order.getOrderSellUid() + "，转单后卖家ID为:" + sellId;
//			int num1 = this.ordersC2cService.doChangeSell(orderId, reason, (int) operateId, remark, orderChangeUser.getGameAccount(), orderChangeUser.getGamePwd(), orderChangeUser.getGameSafekey(), orderChangeUser.getGoodsPosition(), needUpdate);
//			if (num1 == 1) {
//				json.put("code", "9999");
//				json.put("msg", "修改订单" + orderId + " C2C属性失败!");
//				logger.info("调用appId:" + appId + "接口出错，转单失败，修改订单" + orderId + " C2C属性失败!");
//				return json;
//			}
//			int num = this.ordersService.changeSell(orderId, "PRO-00000000-00000000000", sellId, (int) operateId, orderChangeUser.getGameUid(), orderChangeUser.getGameRoleId(), orderChangeUser.getGameRoleName(), orderChangeUser.getChannelId());
//			if (num == 0) {
//				json.put("code", "9999");
//				json.put("msg", "转单失败，修改订单" + orderId + "卖家属性失败!");
//				logger.info("调用appId:" + appId + "接口出错，转单失败，修改订单" + orderId + "卖家属性失败!");
//				return json;
//			}
//			// 转单完毕，判断当前商品是否还有订单未结束，如果没有，则直接下架
//			int count = this.ordersService.queryNotPaySuccessOrderByProductId(order.getGoodsId(), orderId);
//			if (count == 0) {
//				logger.info("当前商品编号:" + order.getGoodsId() + "无有未完成的订单，可执行下架操作!");
//				int result = this.productService.productAdminOffShelves(order.getGoodsId(), ProductDao.PRODUCT_STATUC_MANAGEOFF, (int) operateId, "订单转单卖家，原订单商品下架");
//				if (result == 0) {
//					json.put("code", "0");
//					json.put("msg", "转单成功，下架商品" + order.getGoodsId() + "成功!");
//					logger.info("调用appId:" + appId + "接口结果:转单成功，下架商品" + order.getGoodsId() + "成功!");
//					return json;
//				} else {
//					json.put("code", "0");
//					json.put("msg", "转单成功，下架商品" + order.getGoodsId() + "失败!");
//					logger.info("调用appId:" + appId + "接口结果:转单成功，下架商品" + order.getGoodsId() + "失败!");
//					return json;
//				}
//			} else if (count > 0) {
//				json.put("code", "0");
//				json.put("msg", "转单成功，下架商品失败，原因: 当前商品编号:" + order.getGoodsId() + "还有未完成的订单，不执行下架操作!");
//				logger.info("调用appId:" + appId + "接口结果:转单成功，下架商品失败，原因: 当前商品编号:" + order.getGoodsId() + "还有未完成的订单，不执行下架操作!");
//				return json;
//			} else if (count == -1) {
//				json.put("code", "0");
//				json.put("msg", "转单成功，下架商品失败，原因: 查询" + order.getGoodsId() + "是否还有订单未结束出错，下架商品失败!");
//				logger.info("调用appId:" + appId + "接口结果:转单成功，下架商品失败，原因: 查询" + order.getGoodsId() + "是否还有订单未结束出错，下架商品失败!");
//				return json;
//			}
//			return null;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 客服撤单
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject cancel(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("order_id");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，order_id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，order_id参数为空!");
//				return json;
//			}
//			String reasonId = paramJson.getString("reason_id");
//			if (reasonId == null || reasonId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，reason_Id参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，reason_Id参数为空!");
//				return json;
//			}
//			Object operateId = paramJson.get("opid");
//			if (operateId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，opid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，opid参数为空!");
//				return json;
//			}
//			String remark = paramJson.getString("remark") == null ? "" : paramJson.getString("remark");
//			OrdersC2c ordersC2c = this.ordersC2cService.queryById(orderId);
//			if (ordersC2c.getOrderC2cStatus() != OrdersC2cDao.ORDERSC2C_STATUS_CUSTOMER_SEND) {
//				json.put("code", "9999");
//				json.put("msg", "订单状态有误，不能进行撤单！");
//				logger.info("订单状态有误，不能进行撤单!");
//				return json;
//			}
//			Orders order = this.ordersService.queryOrderById(orderId);
//			OrdersC2cCancelReason ordersC2cCancelReason = this.ordersC2cCancelReasonService.queryByReasonId(Integer.parseInt(reasonId));
//			if (onlineCustomerMonitorService.queryCustomerType((int) operateId) == OnlineCustomerMonitorDao.IS_OBJECT_CUSTOMER) {
//				json.put("code", "9999");
//				json.put("msg", "操作人权限不够！");
//				logger.info("操作人权限不够!");
//				return json;
//			}
//			if (onlineCustomerMonitorService.queryCustomerType((int) operateId) == OnlineCustomerMonitorDao.IS_CUSTOMER) {
//				if (ordersC2c.getCustomerServiceId() != (int) operateId) {
//					json.put("code", "9999");
//					json.put("msg", "您不能撤销他人订单！");
//					logger.info("您不能撤销他人订单!");
//					return json;
//				}
//			}
//			OrderUtil.cancelOrder(order, (int) operateId, remark, "1", ordersC2c, ordersC2cCancelReason.getCancelReason(), OrderOperateLogDao.ORDER_LOG_TYPE_CANCEL);
//			json.put("code", "0");
//			json.put("msg", "操作成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 获取配置的特殊转单用户列表
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getSpecifySeller(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			List<SysParameter> parameterList = parameterService.listByParaCode("ORDER_C2C_SPECIFY_SELLER");
//			if (parameterList.isEmpty()) {
//				json.put("code", "9999");
//				json.put("msg", "返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SysParameter parameter = parameterList.get(0);
//			String[] specifySeller = parameter.getParaValue().split(",");
//			JSONArray body = JSONArray.fromObject(specifySeller);
//			json.put("body", body);
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 查询后台用户数据
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject getSysUserInfo(String data, String appId) {
//		JSONObject json = new JSONObject();
//		try {
//
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String uid = paramJson.getString("uid");
//			if (uid == null || uid.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，uid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，uid参数为空!");
//				return json;
//			}
//
//			Map<String, Object> userMap = new HashMap<String, Object>();
//			cn.jugame.vo.SysUserinfo user = sysUserinfoService.findById(Integer.parseInt(uid));
//
//			if (user == null) {
//				json.put("code", "1");
//				json.put("msg", "无此相关信息");
//				json.put("body", "{}");
//				return json;
//			}
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			// 普通管理员用户信息
//			userMap.put("user_id", user.getUserId());
//			userMap.put("loginid", user.getLoginid());
//			userMap.put("usertype", user.getUsertype());
//			userMap.put("status", user.getStatus());
//			userMap.put("create_time", sdf.format(user.getCreateTime()));
//			userMap.put("customer_service_id", user.getCustomServiceId() == null ? "" : user.getCustomServiceId());
//			userMap.put("online_status", user.getOnlineStatus());
//			userMap.put("is_customer", user.getIsCustomer());
//			userMap.put("is_object_customer", user.getIsObjectCustomer());
//			userMap.put("is_on_duty", user.getIsOnDuty());
//			userMap.put("customer_nickname", user.getCustomerNickname());
//			userMap.put("customer_qq", user.getCustomerQQ());
//			userMap.put("full_name", user.getFullname());
//
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			json.put("body", userMap);
//
//			return json;
//		} catch (Exception e) {
//			json.put("code", "1");
//			json.put("msg", "无此相关信息");
//			json.put("body", "{}");
//			return json;
//		}
//	}
//
//	// 订单历史记录查看
//	public JSONObject followOrder(String data, String appId) {
//		try {
//			JSONObject json = new JSONObject();
//			JSONObject paramJson = JSONObject.fromObject(data);
//			String orderId = paramJson.getString("order_id");
//
//			if (orderId == null) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，orderId参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，orderId参数为空!");
//				return json;
//			}
//			// List<OrderOperateLog> list = defaultOrdersService.queryOrderLogByOrderId(orderId);
//			// List<Map> showList = null;
//			// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			// json.put("code", "0");
//			// json.put("msg", "查询成功");
//			// System.out.println("--------" + list.size());
//			// if (list == null) {
//			// json.put("code", "1");
//			// json.put("msg", "该订单暂无备注信息");
//			// return json;
//			// }
//			// showList = new ArrayList<Map>();
//			//
//			// for (int i = 0; i < list.size(); i++) {
//			// Map<String, Object> map = new HashMap<String, Object>();
//			// OrderOperateLog orderOperateLog = list.get(i);
//			// map.put("operateTime",sf.format(orderOperateLog.getOperateTime()));
//			// map.put("person", orderOperateLog.getOperateUserId());
//			// map.put("reason", orderOperateLog.getOperateReason());
//			// map.put("operateType", orderOperateLog.getOperateType());
//			// map.put("orderType", orderOperateLog.getOrderType());
//			// map.put("remark", orderOperateLog.getRemark());
//			// showList.add(map);
//			// }
//			//
//			// JSONArray body = JSONArray.fromObject(showList);
//			// json.put("body", body);
//
//			List<OrderOperateLog> list = defaultOrdersService.queryOrderLogByOrderId(orderId);
//			if (list == null || list.size() == 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口成功，返回结果为空!");
//				logger.info("调用appId:" + appId + "接口成功，返回结果为空!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "查询成功");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//			int orderType = 0;
//			String orderTypeStr = "";
//			int person = 0;
//			String personStr = "";
//			int opType = 0;
//			String opTypeStr = "";
//			for (int i = 0; i < list.size(); i++) {
//				OrderOperateLog orderOperateLog = list.get(i);
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("operateTime", sf.format(orderOperateLog.getOperateTime()));
//				map.put("reason", orderOperateLog.getOperateReason() == null ? "" : orderOperateLog.getOperateReason());
//				map.put("remark", orderOperateLog.getRemark() == null ? "" : orderOperateLog.getRemark());
//				orderType = orderOperateLog.getOrderType();// 订单类型
//				person = orderOperateLog.getOperateUserId();// 操作uid
//				opType = orderOperateLog.getOperateType();// 操作类型
//				if (orderType == 1) {
//					orderTypeStr = "普通订单";
//				} else {
//					orderTypeStr = "支付单";
//				}
//				map.put("orderType", orderTypeStr);
//
//				// 操作人
//				if (person == 0) {
//					personStr = "系统";
//				} else {
//					personStr = String.valueOf(person);
//				}
//				map.put("person", personStr);
//				// 操作类型
//				if (opType == 1) {
//					opTypeStr = "生成订单";
//				} else if (opType == 2) {
//					opTypeStr = "订单支付";
//				} else if (opType == 3) {
//					opTypeStr = "生成支付单";
//				} else if (opType == 4) {
//					opTypeStr = "订单取消";
//				} else if (opType == 5) {
//					opTypeStr = "订单退款";
//				} else if (opType == 6) {
//					opTypeStr = "API自动发货";
//				} else if (opType == 10) {
//					opTypeStr = "订单完成";
//				} else if (opType == 1001) {
//					opTypeStr = "派单给客服";
//				} else if (opType == 1002) {
//					opTypeStr = "派单给物服";
//				} else if (opType == 1003) {
//					opTypeStr = "物服审请撤单";
//				} else if (opType == 1004) {
//					opTypeStr = "客服审核";
//				} else if (opType == 1005) {
//					opTypeStr = "物服发货";
//				} else if (opType == 1006) {
//					opTypeStr = "客服受理";
//				} else if (opType == 1007) {
//					opTypeStr = "物服受理";
//				} else if (opType == 1008) {
//					opTypeStr = "管理员转单";
//				} else if (opType == 1009) {
//					opTypeStr = "转账给卖家";
//				} else if (opType == 1010) {
//					opTypeStr = "超时赔付";
//				} else if (opType == 1011) {
//					opTypeStr = "转单卖家";
//				} else if (opType == 1012) {
//					opTypeStr = "人工撤单";
//				} else if (opType == -1) {
//					opTypeStr = "逻辑删除订单";
//				}
//				map.put("operateType", opTypeStr);
//
//				showList.add(map);
//			}
//			JSONArray body = JSONArray.fromObject(showList);
//			json.put("body", body);
//			return json;
//
//		} catch (Exception e) {
//			logger.error("", e);
//			return null;
//
//		}
//
//	}
//
//	/**
//	 * 更新订单卖家账号密码信息
//	 * 
//	 * @param data
//	 * @param appId
//	 * @return
//	 */
//	public JSONObject updateC2cSellAccount(String data, String appId) {
//		JSONObject json = new JSONObject();
//		try {
//
//			JSONObject paramJson = JSONObject.fromObject(data);
//			if (paramJson == null) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，data参数格式有误!");
//				logger.info("调用appId:" + appId + "接口出错，data参数格式有误!");
//				return json;
//			}
//			String orderId = paramJson.getString("orderid");
//			if (orderId == null || orderId.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，orderid参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，orderid参数为空!");
//				return json;
//			}
//
//			String account = paramJson.getString("account");
//			if (account == null || account.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，account参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，account参数为空!");
//				return json;
//			}
//
//			String password = paramJson.getString("password");
//			if (password == null || password.equals("")) {
//				json.put("code", "9000");
//				json.put("msg", "调用appId:" + appId + "接口出错，password参数为空!");
//				logger.info("调用appId:" + appId + "接口出错，password参数为空!");
//				return json;
//			}
//
//			int i = ordersC2cService.updateC2CSellAccount(orderId, account, password);
//			if (i > 0) {
//				json.put("code", "9999");
//				json.put("msg", "调用appId:" + appId + "接口出错，订单号" + orderId + "更新卖家账号信息失败!");
//				logger.info("调用appId:" + appId + "接口出错，订单号" + orderId + "更新卖家账号信息失败!");
//				return json;
//			}
//			json.put("code", "0");
//			json.put("msg", "更新卖家账号信息成功");
//			return json;
//		} catch (Exception e) {
//			logger.error("", e);
//			json.put("code", "1");
//			json.put("msg", "接口异常11");
//			return json;
//		}
//	}
//
//	/**
//	 * 
//	 * 测试
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// ApiController api = new ApiController();
//		// String sign = MD5.encode("{}" + "12345678900").substring(0,8);
//		// JSONObject j = api.api("test", "{}", sign, "1000");
//		// System.out.println(j);
//		// if (j.getInt("code") == 0){
//		// System.out.println("操作成功");
//		// }
//		System.out.println(System.currentTimeMillis());
//
//		String str = "中国大中的";
//		//byte[] b;
//		try {
//			String tt = URLEncoder.encode(str, "UTF-8");
//			System.out.println(tt);
//
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// String st =
//
//	}
//}
