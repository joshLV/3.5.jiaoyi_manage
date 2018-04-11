package cn.juhaowan.member.dao;

import java.util.List;

import cn.juhaowan.member.vo.MemberDepositLog;

/**
 * 押金日志操作类
 * @author cai9911
 *
 */
public interface MemberDepositLogDao{
	/**
	 *增加
	 * 
	 */
	public static int OPERATOR_TYPE_ADD = 1;
	
	/**
	 * 减少（扣除）
	 */
	public static int OPERATOR_TYPE_SUB = 2;
	
	
	/**
	 * 添加日志
	 * @param module
	 */
	void insert(MemberDepositLog module);
	
	
	/**
	 * 添加日志
	 * @param uid
	 * @param depositType 押金类型
	 * @param operatroType 操作类型
	 * @param amount 操作金额
	 * @param balance  余额
	 */
	void addLog(int uid ,int depositType ,int operatroType,double amount,double balance,String remark);
	
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
	public List<MemberDepositLog> queryLogList(int uid ,String sqlCondition,int fistResult,int maxResult,Object... sqlParam);
	
	
}
