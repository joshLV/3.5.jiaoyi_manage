package cn.juhaowan.order.dao;

import java.util.List;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.order.vo.SysUserinfo;


/**
 * 订单派单
 * 
 * @author Administrator
 * 
 */
public interface DutyDao extends BaseDao<Object> {

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
	 * =================================== 判断是否有职责权限
	 * 
	 * @param dutyId
	 *            职责类型
	 * @param subDutyId
	 *            职责值
	 * @param uid
	 *            客服id
	 * @param busType 业务分类
	 * @return 0 无 1 有
	 */
	int hasDuty(int dutyId, int subDutyId, int uid,int busType);

	/**
	 * 上班状态的所有客服/物服
	 * 
	 * @param isCustomer
	 *            是否是客服 0 不是 1 是
	 * @param isObjectCustomer
	 *            是否是物服 0 不是 1 是
	 * @return
	 */
	List<SysUserinfo> onDutyUserList(int isCustomer, int isObjectCustomer);

	/**
	 * 找出派到订单 最少的客服
	 * 
	 * @param uidStr
	 *            符合条件的uid 字符串 1212，1212
	 * @return uid
	 */
	int findMinCustomerUIDOrderC2C(String uidStr);

	/**
	 * 找出派到订单 最少的物服
	 * 
	 * @param uidStr
	 *            符合条件的uid 字符串 1212，1212
	 * @return uid
	 */
	int findMinObjectUIDOrderC2C(String uidStr);
	
	/**
	 * order c2c表最后派单客服id
	 * @return
	 */
	int orderLastAssignCS();
	
	/**
	 * order c2c表最后派单物服id
	 * @return
	 */
	int orderLastAssignOS();

}
