package cn.juhaowan.order.service;

import java.util.List;

import cn.juhaowan.order.vo.SysUserinfo;



/**
 * 商品接口信息
 */
public interface DutyService {


//	
//    /**
//     * 分派已支付未发货订单到客服
//     * @param orderId 订单id
//     * @return  小于等于0 失败(
//     * 0:没有符合权限的客服 
//     * -1：订单不存在 
//     * -2：订单不是已支付未发货状态
//     * -3：订单c2c数据不存在
//     * -4: 未分配客服人员
//     * -5：订单种类和客服/物服职责权限匹配不上
//	 * 大于0 （可以派单的uid）
//     */
//	int assignOrderToCs(String orderId);
//	
//	/**
//	 * 分派已支付未发货订单给物服
//	 * @param orderId 订单id
//	 * @return 小于等于0 失败(
//     * 0:没有符合权限的客服 
//     * -1：订单不存在 
//     * -2：订单不是已支付未发货状态
//     * -3：订单c2c数据不存在
//     * -4: 未分配客服人员
//     * -5：订单种类和客服/物服职责权限匹配不上
//	 * 大于0 （可以派单的uid）
//	 */
//	int assignOrderToOs(String orderId);
//
//    /**
//     * 分派已支付未发货订单到客服
//     * @param orderId 订单id
//     * @return 0 成功 1 失败
//     */
//	int assignOrderToCs(Orders o);
//	
//	/**
//	 * 分派已支付未发货订单给物服
//	 * @param orderId 订单id
//
//	 * @return 0 成功 1 失败
//	 */
//	int assignOrderToOs(Orders o);
//	
//	
//	/**
//	 * 符合客服转单的客服list
//	 * @param orderId 订单id
//	 * @return 可以为该订单转单的客服list
//	 */
//	public List<SysUserinfo> transferCS(String orderId);
//	
//	/**
//	 * 符合物服转单的物服list
//	 * @param orderId 订单id
//	 * @return 可以为该订单转单的物服list
//	 */
//	public List<SysUserinfo> transferOS(String orderId);
	
	/**
	 * 上班状态的物服列表
	 * @return
	 */
	public List<SysUserinfo> onDutyOSList();
	/**
	 * 根据ID查找
	 * @param userId
	 * @return
	 */
	SysUserinfo findById(int userId);
}
