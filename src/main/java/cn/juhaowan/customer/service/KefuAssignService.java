//package cn.juhaowan.customer.service;
//
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//import cn.jugame.util.PageInfo;
//import cn.juhaowan.customer.vo.CustomerServiceDuty;
//import cn.juhaowan.customer.vo.DutyListForm;
//import cn.juhaowan.customer.vo.ProductVerifyFailReason;
//import cn.juhaowan.customer.vo.SysUserinfo;
//import cn.juhaowan.product.vo.Product;
//
///**
// * 商品接口信息
// */
//public interface KefuAssignService {
//
//	/**
//	 * 业务类型  1 上架 (商品)
//	 * 
//	 */
//	public static int MAIN_DUTY_VERIFY = 1;
//	
//	/**
//	 * 业务类型   2 售中（订单）
//	 * 
//	 */
//	public static int MAIN_DUTY_ONSALE = 2;
//	
//	/**
//	 * 职责类型 1 业务类型
//	 */
//	public static int DUTYTYPE_BUSINESSTYPE = 1;
//	/**
//	 * 职责类型 2 交易模式
//	 */
//	public static int DUTYTYPE_TRADEMODEL = 2;
//	/**
//	 * 职责类型 3 商品类型
//	 */
//	public static int DUTYTYPE_PRODUCTTYPE = 3;
//	/**
//	 * 职责类型 4 用户级别
//	 */
//	public static int DUTYTYPE_MEMBERTYPE = 4;
//	/**
//	 * 职责类型 5 游戏
//	 */
//	public static int DUTYTYPE_GAME = 5;
//
//
//	/**
//	 * 派单
//	 * 
//	 * @param productId
//	 *            商品id
//	 * @param customerServiceId
//	 *            客服id
//	 * @return 小于等于0 失败( 0:没有符合权限的客服 -1：商品不存在 -2：商品派单不是未审核状态 -3：未分配客服 -4:
//	 *         商品职责分类找到到匹配的客服 -5: 商品c2c表信息不存在该商品 大于0 （可以派单的uid）
//	 */
//	int assignProductToCS(String productId);
//
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
//
//
//	
//	/**===================================
//	 * 判断是否有职责权限
//	 * @param dutyId 职责类型
//	 * @param subDutyId 职责值
//	 * @param uid 客服id
//	 * @param busType 业务分类
//	 * @return 0 无 1 有
//	 */
//	int hasDuty(int dutyId,int subDutyId,int uid,int busType);
//	
//	/**
//	 * 上班状态的所有客服/物服
//	 * @param isCustomer 是否是客服 0 不是 1 是
//	 * @param isObjectCustomer 是否是物服 0 不是 1 是
//	 * @return 
//	 */
//	List<SysUserinfo> onDutyUserList(int isCustomer,int isObjectCustomer);
//	
//	/**
//	 * 找出接待审核商品的单 最少的客服uid
//	 * @param uidStr 符合条件的uid 字符串 1212，1212
//	 * @return uid 
//	 */
//	int findMinUIDProductC2C(String uidStr);
//	
//	/**
//	 * product c2c表最后派单客服id
//	 * @return
//	 */
//	int productLastAssign();
//	
//	/**
//	 * 待审核商品派单给客服
//	 * @param productId 商品id
//	 * @param customerId 客服id
//	 * @return 0 成功 1 失败
//	 */
//	int assignProductCustomer(String productId,int customerId);
//}
