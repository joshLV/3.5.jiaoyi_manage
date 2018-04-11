package cn.juhaowan.order.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.order.dao.DutyDao;
import cn.juhaowan.order.service.DutyService;
import cn.juhaowan.order.vo.SysUserinfo;

@Service("DutyService")
public class DutyServiceImpl implements DutyService {
	private static Logger LOG = LoggerFactory.getLogger(DutyService.class);
	@Autowired
	private DutyDao customerServiceDutyDao;

	@Autowired
	private JdbcOperations jdbcOperations;

//	@Autowired
//	private OrdersService ordersService;
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private OrdersC2cService ordersC2cService;
	
//	@Autowired
//	private GameProductTypeService gameProductTypeService;

	// 派单===================================

//	@Override
//	public int assignOrderToCs(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//		return assignOrderToCs(o);
//	}
//
//	@Override
//	public int assignOrderToOs(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//		return assignOrderToOs(o);
//	}
//   //派单给客服
//	@Override
//	public int assignOrderToCs(Orders o) {
//		int result = -1;
//		if (null == o) {
//			LOG.error("订单派单失败-1,订单不存在");
//			return -1;
//		}
//
//		// 订单不是已支付 未发货状态
//		if (o.getOrderStatus() != OrdersDao.ORDER_STATUS_WAIT_GOODS) {
//			LOG.error("订单派单失败-2,订单不是已支付未发货状态");
//			return -2;
//		}
//		OrdersC2c c2c = ordersC2cService.queryById(o.getOrderId());
//		if (null == c2c) {
//			LOG.error("订单派单失败-3,订单c2c数据不存在");
//			return -3;
//
//		}
//
//		// 第一步：找出所有上班 并可以接单的客服
//		List<SysUserinfo> list = customerServiceDutyDao.onDutyUserList(1, 0);
//		if (null == list) {
//			LOG.error("订单派单失败-4,未分配客服人员");
//			return -4;
//		}
//
//		StringBuffer uidStr = new StringBuffer("");
//		// 第二步：根据商品信息 筛选有权限的客服
//		for (int c = 0; c < list.size(); c++) {
//			// 1 业务过程 上架
//			// int i =
//			// customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_BUSINESSTYPE,1,list.get(c).getUserId());
//
//			// 2 交易模式
//			int j = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_TRADEMODEL, o.getOrderModel(), list
//					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 3 商品类型
////			int typeSubduty = gameProductTypeService.queryByGameIdAndTypeId(o.getGameId(), o.getGoodsType());
////			int k = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_PRODUCTTYPE, typeSubduty, list
////					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
//			// 4 用户类型
//			int l = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_MEMBERTYPE,
//					memberService.isVIP(o.getOrderSellUid()), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 5 游戏
////			int m = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_GAME, o.getGameId(), list.get(c)
////					.getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//			// 第三步：有权限的客服 再筛选接单最少的uid
//			if (j *  l  == 1) {
//				uidStr.append(list.get(c).getUserId() + ",");
//			}
//		}
//		String str = uidStr.toString();
//		LOG.info("====派订单给客服=符合条件的客服uid==" + str);
//		if (!"".equals(uidStr) && null != uidStr && str.contains(",")) {
//			str = str.substring(0, str.length() - 1);
//		} else {
//			LOG.error("订单派单失败-5,订单种类和客服/物服职责权限匹配不上");
//			return -5;
//		}
//	
//		result = customerServiceDutyDao.findMinCustomerUIDOrderC2C(str);
//		LOG.info("最少订单客服==result==" + result);
//		return result;
//	}
//    //派单给物服
//	@Override
//	public int assignOrderToOs(Orders o) {
//		int result = -1;
//		if (null == o) {
//			LOG.error("订单派单失败-1,订单不存在");
//			return -1;
//		}
//		// 订单是 已支付 未发货状态
//		if (o.getOrderStatus() != OrdersDao.ORDER_STATUS_WAIT_GOODS) {
//			LOG.error("订单派单失败-2,订单不是已支付待发货状态");
//			return -2;
//		}
//		OrdersC2c c2c = ordersC2cService.queryById(o.getOrderId());
//		if (null == c2c) {
//			LOG.error("订单派单失败-3,订单c2c数据不存在,订单id：" + o.getOrderId());
//			return -3;
//
//		}
//
//		// 第一步：找出所有上班 并可以接单的物服
//		List<SysUserinfo> list = customerServiceDutyDao.onDutyUserList(0, 1);
//		if (null == list) {
//			LOG.error("订单派单失败-4,未分配物服人员");
//			return -4;
//		}
//
//		StringBuffer uidStr = new StringBuffer("");
//		// 第二步：根据订单信息 筛选有权限的物服
//		for (int c = 0; c < list.size(); c++) {
//			// 1 业务过程 上架
//			// int i =
//			// customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_BUSINESSTYPE,1,list.get(c).getUserId());
//
//			// 2 交易模式
//			int userId = list.get(c).getUserId();
//			int mainDutyOnsale = DutyDao.MAIN_DUTY_ONSALE;
//			int j = customerServiceDutyDao.hasDuty(DutyDao.DUTYTYPE_TRADEMODEL, o.getOrderModel(), userId,mainDutyOnsale);
//
////			// 3 商品类型--?这里为什么还要查一下呢？？？
////			int typeSubduty = gameProductTypeService.queryByGameIdAndTypeId(o.getGameId(), o.getGoodsType());
////			int k = 0;
////			if (typeSubduty == 0){
////				//caizr
////				k = 1;
////			}else{
////				k = customerServiceDutyDao.hasDuty(DutyDao.DUTYTYPE_PRODUCTTYPE, typeSubduty, userId,mainDutyOnsale);
////			}
//			// 4 用户类型
//			int vip = memberService.isVIP(o.getOrderSellUid());
//			int l = customerServiceDutyDao.hasDuty(DutyDao.DUTYTYPE_MEMBERTYPE,vip, userId,mainDutyOnsale);
//
//			// 5 游戏
////			int m = 0;
////			if (o.getGameId()==0){
////				m = 1;
////			}else{
////				m = customerServiceDutyDao.hasDuty(DutyDao.DUTYTYPE_GAME, o.getGameId(), userId,mainDutyOnsale);
////			}
//			
//			// 第三步：有权限的物服 再筛选接单最少的uid
//			if (j * l  == 1) {
//				uidStr.append(userId + ",");
//
//			}
//		}
//		String str = uidStr.toString();
//		LOG.info(o.getOrderId() + "====派订单给物服=符合条件的物服uid==" + str);
//		if (!"".equals(uidStr) && null != uidStr && str.contains(",")) {
//			str = str.substring(0, str.length() - 1);
//		} else {
//			LOG.error(o.getOrderId() +  "订单派单失败-5,订单种类找不到职责权限匹配的物服");
//			return -5;
//		}
//		result = customerServiceDutyDao.findMinObjectUIDOrderC2C(str);
//		LOG.info(o.getOrderId() + "最少订单物服==result==" + result);
//		return result;
//	}
//    //转单给客服
//	@Override
//	public List<SysUserinfo> transferCS(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//
//		// 第一步：找出所有上班 并可以接单的客服
//		List<SysUserinfo> list = customerServiceDutyDao.onDutyUserList(1, 0);
//		if (null == list) {
//			LOG.error(o.getOrderId() + "未配置客服人员");
//			return list;
//		}
//		
//		OrdersC2c c2c = ordersC2cService.queryById(o.getOrderId());
//		if(null == c2c){
//			LOG.error(o.getOrderId() + "请检查订单c2c表是否有对应的订单数据");
//			return null;
//		}
//		List<SysUserinfo> resultlist = new ArrayList<SysUserinfo>();
//		//StringBuffer uidStr = new StringBuffer("");
//		// 第二步：根据商品信息 筛选有权限的客服
//		for (int c = 0; c < list.size(); c++) {
//			// 1 业务过程 上架
//			// int i =
//			// customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_BUSINESSTYPE,1,list.get(c).getUserId());
//
//			// 2 交易模式
//			int j = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_TRADEMODEL, o.getOrderModel(), list
//					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 3 商品类型
////			int typeSubduty = gameProductTypeService.queryByGameIdAndTypeId(o.getGameId(), o.getGoodsType());
////			int k = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_PRODUCTTYPE, typeSubduty, list
////					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
//			// 4 用户类型
//			int l = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_MEMBERTYPE,
//					memberService.isVIP(o.getOrderSellUid()), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 5 游戏
////			int m = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_GAME, o.getGameId(), list.get(c)
////					.getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//			// 第三步：有权限的客服 再筛选接单最少的uid
//			if (j * l  == 1) {
//				if(c2c.getCustomerServiceId() != list.get(c).getUserId()){
//				resultlist.add(list.get(c));
//				}
//			}
//		}
//
//		return resultlist;
//	}
//    //转单给物服
//	@Override
//	public List<SysUserinfo> transferOS(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//
//		// 第一步：找出所有上班 并可以接单的物服
//		List<SysUserinfo> list = customerServiceDutyDao.onDutyUserList(0, 1);
//		if (null == list) {
//			LOG.error("未配置物服人员");
//			return list;
//		}
//		OrdersC2c c2c = ordersC2cService.queryById(o.getOrderId());
//		if(null == c2c){
//			LOG.error("请检查订单c2c表是否有对应的订单数据");
//			return null;
//		}
//		List<SysUserinfo> resultlist = new ArrayList<SysUserinfo>();
//		// 第二步：根据商品信息 筛选有权限的物服
//		for (int c = 0; c < list.size(); c++) {
//			// 1 业务过程 上架
//			// int i =
//			// customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_BUSINESSTYPE,1,list.get(c).getUserId());
//
//			// 2 交易模式
//			int j = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_TRADEMODEL, o.getOrderModel(), list
//					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 3 商品类型
////			int typeSubduty = gameProductTypeService.queryByGameIdAndTypeId(o.getGameId(), o.getGoodsType());
////			int k = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_PRODUCTTYPE, typeSubduty, list
////					.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
//			// 4 用户类型
//			int l = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_MEMBERTYPE,
//					memberService.isVIP(o.getOrderSellUid()), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//
////			// 5 游戏
////			int m = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_GAME, o.getGameId(), list.get(c)
////					.getUserId(),customerServiceDutyDao.MAIN_DUTY_ONSALE);
//			// 第三步：有权限的物服 再筛选接单最少的uid
//
//			if (j * l == 1) {
//				if(c2c.getPhysicServiceId() != list.get(c).getUserId()){
//				resultlist.add(list.get(c));
//				}
//			}
//		}
//
//		return resultlist;
//	}

	@Override
	public List<SysUserinfo> onDutyOSList() {
		return customerServiceDutyDao.onDutyUserList(0, 1);
	}
	
	@Override
	public SysUserinfo findById(int userId) {
		SysUserinfo sysUserinfo = null;
		String sql = "SELECT * FROM sys_userinfo WHERE USER_ID = ?";
		JuRowCallbackHandler<SysUserinfo> rch = new JuRowCallbackHandler<SysUserinfo>(SysUserinfo.class);
		jdbcOperations.query(sql, rch,userId);
		if(null != rch.getList() && rch.getList().size() > 0){
			sysUserinfo = rch.getList().get(0);
		}
		return sysUserinfo;
	}
}
