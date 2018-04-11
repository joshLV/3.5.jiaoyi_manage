package cn.juhaowan.customer.dao;

import java.util.List;

import net.sf.json.JSONObject;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.customer.vo.CustomerServiceDuty;
import cn.juhaowan.customer.vo.DutyListForm;
import cn.juhaowan.customer.vo.SysUserinfo;


/**
 * 
 * @author Administrator
 * 
 */
public interface CustomerServiceDutyDao extends BaseDao<Object> {

	/**
	 * 职责类型 1 业务类型
	 */
	public static int DUTYTYPE_BUSINESSTYPE = 1;
	/**
	 * 职责类型 2 交易模式
	 */
	public static int DUTYTYPE_TRADEMODEL = 2;
	/**
	 * 职责类型 3 商品类型
	 */
	public static int DUTYTYPE_PRODUCTTYPE = 3;
	/**
	 * 职责类型 4 用户级别
	 */
	public static int DUTYTYPE_MEMBERTYPE = 4;
	/**
	 * 职责类型 5 游戏
	 */
	public static int DUTYTYPE_GAME = 5;
	
	/**
	 * 业务类型  1 上架  
	 * 
	 */
	public static int MAIN_DUTY_VERIFY = 1;
	
	/**
	 * 业务类型   2 售中
	 * 
	 */
	public static int MAIN_DUTY_ONSALE = 2;
	
	/**
	 * 是客服
	 */
	public static int IS_SERVICE=0;
	/**
	 * 是物服
	 */
	public static int IS_OBJECT_SERVICE=1;
	/**
	 * 是审核员
	 */
	public static int IS_AUDITOR=2;
	/**
	 * 是投资客服
	 */
	public static int IS_INVESTMENT_SERVICE=3;
	/**
	 * 添加职责权限
	 * 
	 * @param duty 
	 * @return 0：成功 1：失败
	 */
	int insert(CustomerServiceDuty duty);

	/**
	 * 删除职责权限
	 * 
	 * @param uid 
	 * @return 0：成功 1：失败
	 */
	int delete(int uid);

	/**
	 * 按uid查询 职责权限列表
	 * 
	 * @param SCId  客服uid
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(String SCId);
	
	/**
	 * 按uid 和 职责分类 查询 职责权限列表
	 * 
	 * @param SCId  客服uid
	 * @param dutyId 职责id
	 * @param busType 业务分类
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(int dutyId,String SCId,int busType);
	
	/**
	 * 按uid 和 职责分类 查询 职责权限列表
	 * 
	 * @param SCId  客服uid
	 * @param dutyId 职责id
	 * @param subdutyId 职责值
	 * @return 用户职责权限列表
	 */
	List<CustomerServiceDuty> queryByCSId(int dutyId,int subdutyId,String SCId);
	
	/**
	 * 按uid查询 职责权限列表
	 * 
	 * @param SCId  客服uid
	 * @return 用户职责权限form
	 */
	List<DutyListForm> queryByuId(String SCId);

	

	
	
	/**===================================
	 * 判断是否有职责权限
	 * @param dutyId 职责类型
	 * @param subDutyId 职责值
	 * @param uid 客服id
	 * @param busType 业务分类
	 * @return 0 无 1 有
	 */
	int hasDuty(int dutyId,int subDutyId,int uid,int busType);
  
	
	/**
	 * 上班状态的所有客服/物服
	 * @param isCustomer 是否是客服 0 不是 1 是
	 * @param isObjectCustomer 是否是物服 0 不是 1 是
	 * @return 
	 */
	List<SysUserinfo> onDutyUserList(int isCustomer,int isObjectCustomer);
	/**
	 * 找出接待审核商品的单 最少的客服uid
	 * @param uidStr 符合条件的uid 字符串 1212，1212
	 * @return uid 
	 */
	int findMinUIDProductC2C(String uidStr);
	
	/**
	 * 找出派到订单 最少的客服
	 * @param uidStr 符合条件的uid 字符串 1212，1212
	 * @return uid 
	 */
	int findMinCustomerUIDOrderC2C(String uidStr);
	
	/**
	 * 找出派到订单 最少的物服
	 * @param uidStr 符合条件的uid 字符串 1212，1212
	 * @return uid 
	 */
	int findMinObjectUIDOrderC2C(String uidStr);
	
	/**
	 * 待审核商品派单给客服
	 * @param productId 商品id
	 * @param customerId 客服id
	 * @return 0 成功 1 失败
	 */
	int assignProductCustomer(String productId,int customerId);
	/**
	 * product c2c表最后派单客服id
	 * @return
	 */
	int productLastAssign();
	
	/**
	 * 所有客服/物服
	 * @param isCustomer 是否是客服 0 不是 1 是
	 * @param isObjectCustomer 是否是物服 0 不是 1 是
	 * @return 
	 */
	List<SysUserinfo> dutyUserList(int isCustomer,int isObjectCustomer);

//	JSONObject queryAllOnlineServiceList();

}
