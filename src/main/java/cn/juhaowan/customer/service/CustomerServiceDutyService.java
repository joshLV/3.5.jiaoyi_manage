package cn.juhaowan.customer.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.jugame.admin.web.form.SysUserinfoForm;
import cn.jugame.util.PageInfo;
import cn.juhaowan.customer.vo.CustomerServiceDuty;
import cn.juhaowan.customer.vo.DutyListForm;
import cn.juhaowan.customer.vo.SysUserinfo;

/**
 * 商品接口信息
 */
public interface CustomerServiceDutyService {

	/**
	 * 添加职责权限
	 * 
	 * @param duty
	 * @return 0：成功 1：失败
	 */
	int insertDuty(CustomerServiceDuty duty);

	/**
	 * 删除职责权限
	 * 
	 * @param uid
	 * @return 0：成功 1：失败
	 */
	int deleteDuty(int uid);

	/**
	 * 按uid查询 职责权限列表
	 * 
	 * @param SCId
	 *            客服uid
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(String SCId);

	/**
	 * 按uid 和 职责分类 查询 职责权限列表
	 * 
	 * @param SCId
	 *            客服uid
	 * @param dutyId
	 *            职责id
	 * @param busType
	 *            业务分类
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(int dutyId, String SCId, int busType);

	/**
	 * 按uid 和 职责分类 查询 职责权限列表
	 * 
	 * @param SCId
	 *            客服uid
	 * @param dutyId
	 *            职责id
	 * @param subdutyId
	 *            职责值
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(int dutyId, int subdutyId, String SCId);

//	/**
//	 * =================================商品审核原因方法接口============ 按id 查找商品审核原因
//	 * 
//	 * @param id
//	 * @return 商品审核原因
//	 */
//	public ProductVerifyFailReason findById(int id);
//
//	/**
//	 * 查询全部商品审核原因列表
//	 * 
//	 * @return 列表
//	 */
//	public List<ProductVerifyFailReason> listAll();
//
//	/**
//	 * 添加
//	 * 
//	 * @param reson
//	 *            实体
//	 * @return 0 成功 1 失败
//	 */
//	int insertReson(ProductVerifyFailReason reson);
//
//	/**
//	 * 修改商品审核失败原因信息
//	 * 
//	 * @param reson
//	 *            实体类
//	 * @return 0 成功 1 失败
//	 */
//	int updateReson(ProductVerifyFailReason reson);

	/**
	 * 删除商品审核失败原因信息
	 * 
	 * @param resonId
	 * @return 0 成功 1 失败
	 */
//	int deleteReson(int resonId);

	/**
	 * 按条件分页查询列表
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页条数
	 * @param sort
	 *            排序字段
	 * @param order
	 *            desc/asc
	 * @return 分页
	 */
//	PageInfo queryResonForPage(Map<String, Object> conditions, int pageNo,
//			int pageSize, String sort, String order);

	// =================================商品审核原因方法接口= 结束===========

	/**
	 * 派单======================================================== 分派待审核商品到客服
	 * 
	 * @param productId
	 *            商品id
	 * @param customerServiceId
	 *            客服id
	 * @return 小于等于0 失败( 0:没有符合权限的客服 -1：商品不存在 -2：商品派单不是未审核状态 -3：未分配客服 -4:
	 *         商品职责分类找到到匹配的客服 -5: 商品c2c表信息不存在该商品 大于0 （可以派单的uid）
	 */
//	int assignProductToCS(String productId);

	// /**
	// * 分派已支付未发货订单到客服(已经移到订单4.0)
	// * @param orderId 订单id
	// * @return 小于等于0 失败(
	// * 0:没有符合权限的客服
	// * -1：订单不存在
	// * -2：订单不是已支付未发货状态
	// * -3：订单c2c数据不存在
	// * -4: 未分配客服人员
	// * -5：订单种类和客服/物服职责权限匹配不上
	// * 大于0 （可以派单的uid）
	// */
	// int assignOrderToCs(String orderId);
	//
	// /**
	// * 分派已支付未发货订单给物服(已经移到订单4.0)
	// * @param orderId 订单id
	// * @return 小于等于0 失败(
	// * 0:没有符合权限的客服
	// * -1：订单不存在
	// * -2：订单不是已支付未发货状态
	// * -3：订单c2c数据不存在
	// * -4: 未分配客服人员
	// * -5：订单种类和客服/物服职责权限匹配不上
	// * 大于0 （可以派单的uid）
	// */
	// int assignOrderToOs(String orderId);
	//
//	/**
//	 * 派单=====传入对象 分派待审核商品到客服
//	 * 
//	 * @param productId
//	 *            商品id
//	 * @param customerServiceId
//	 *            客服id
//	 * @return 0 成功 1失败
//	 */
//	int assignProductToCS(Product p);

	//
	// /**
	// * 分派已支付未发货订单到客服(已经移到订单4.0)
	// * @param orderId 订单id
	// * @return 0 成功 1 失败
	// */
	// int assignOrderToCs(Orders o);
	//
	// /**
	// * 分派已支付未发货订单给物服(已经移到订单4.0)
	// * @param orderId 订单id
	//
	// * @return 0 成功 1 失败
	// */
	// int assignOrderToOs(Orders o);

	/**
	 * 查询该用户权限信息
	 * 
	 * @param uid
	 *            用户id
	 * @return 返回单条记录
	 */
	public List<DutyListForm> queryUserDuty(String uid);

	/**
	 * 用户权限列表(递归)
	 * 
	 * @param list
	 * @return 返回多条
	 */
	public List<DutyListForm> queryUserListDuty(List<DutyListForm> list);

	// /**
	// * 符合客服转单的客服list
	// * @param orderId 订单id
	// * @return 可以为该订单转单的客服list
	// */
	// public List<SysUserinfo> transferCS(String orderId);
	//
	// /**
	// * 符合物服转单的物服list
	// * @param orderId 订单id
	// * @return 可以为该订单转单的物服list
	// */
	// public List<SysUserinfo> transferOS(String orderId);

	public int findMinUIDProductC2C(String uidStr);

	/**
	 * 所有客服/物服
	 * 
	 * @param isCustomer
	 *            是否是客服 0 不是 1 是
	 * @param isObjectCustomer
	 *            是否是物服 0 不是 1 是
	 * @return
	 */
	List<SysUserinfo> dutyUserList(int isCustomer, int isObjectCustomer);

	/**
	 * 在线客服
	 * 
	 * @param serviceType
	 *            [1:客服,2:物服,3:审核员,4:投资管理]
	 * @return
	 */
//	JSONObject queryAllOnlineServiceList();
	
	PageInfo<SysUserinfo> queryCustomerPageInfo(Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order);
}
