package cn.juhaowan.member.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberAccountLog;

/**
 * 用户账户日志操作类
 * @author cai9911
 *
 */
public interface MemberAccountLogDao {
	
	/**
	 *充值
	 */
	public static int OPERATOR_TYPE_RECHARGE = 1;
	
	/**
	 * 支付
	 */
	public static int OPERATOR_TYPE_PAY = 5;
	
	/**
	 * 收入 
	 */
	public static int OPERATOR_TYPE_PAY_COLLECT = 10;
	
	/**
	 * 冻结押金	
	 */
	public static int OPERATOR_TYPE_DEPOSIT_ADD = 12;
	
	/**
	 * 解冻押金
	 */
	public static int OPERATOR_TYPE_DEPOSIT_CUT = 13;
	
	/**
	 * 提现(支出)
	 */
	public static int OPERATOR_TAKE_MONEY = 15;
	
	/**
	 * 退费 
	 */
	public static int OPERATOR_RETURN_MONEY = 20;
 
	/**
	 * 插入日志
	 * @param module
	 */
	void insert(MemberAccountLog module);
	
	/**
	 * 添加日志
	 * @param uid
	 * @param orderId
	 * @param amount
	 * @param fee
	 * @param accountType
	 * @param operatorType
	 * @param bankId
	 * @param bankCardNo
	 * @param cardNo
	 * @param depositType
	 * @param balance
	 * @param totalBalance
	 * @param remark
	 */
	void addLog(int uid,String orderId,double amount,double fee,int accountType,int operatorType,
			String bankId,String bankCardNo, String cardNo,int depositType,
			double balance,double totalBalance,String remark);
	
	/**
	 * 添加日志
	 * @param uid
	 * @param orderId
	 * @param amount
	 * @param accountType 账户类型
	 * @param operatorType 操作类型
	 * @param balance
	 * @param totalBalance
	 * @param remark
	 */
	void addLog(int uid,String orderId,double amount,int accountType,int operatorType,
			double balance,double totalBalance,String remark);
	
	/**
	 * 查询日志总数量
	 * @param uid
	 * @param sqlCondition
	 * @param fistResult
	 * @param maxResult
	 * @param sqlParam
	 * @return
	 */
	public int queryLogCount(int uid,String sqlCondition,Object... sqlParam);
	
	/**
	 * 查询日志列表 
	 * 
	 * @param uid
	 * @param sqlCondition
	 * @param fistResult
	 * @param maxResult
	 * @param sqlParam
	 * @return
	 */
	public List<MemberAccountLog> queryLogList(int uid ,String sqlCondition,int fistResult,int maxResult,Object... sqlParam);
	
	/** 按条件查询分页 
	* @param conditions 
	* @param pageNo 页数
	* @param pageSize 
	* @param sort 
	* @param order
	* @pdOid 4a7afc63-56a5-4c58-81fc-2d394d68effc 
	* */
	PageInfo<MemberAccountLog> queryPageInfo(Map<String,Object> conditions, int pageNo, int pageSize, String sort, String order);
	
	/**
	 * 按id查询资金流水详情
	 */
	MemberAccountLog queryById(int id);
}
