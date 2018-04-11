package cn.juhaowan.member.dao;

public interface MemberAccountDao {
	/**
	 * 账户类型：现金账户
	 */
	public static int ACCOUNT_TYPE_CASH = 1;
	
	/**
	 * 账户类型：非现金账户
	 */
	public static int ACCOUNT_TYPE_NOT_CASH = 2;
	
	
	/**
	 * 查询现金账户余额
	 * @param uid
	 * @return
	 */
	double queryCash(int uid);
	
	/**
	 * 查询余额(包括 现金和非现金账户)
	 * @param uid
	 * @return
	 */
	double queryBalance(int uid);
	
	/**
	 * 查询账户余额
	 * @param uid
	 * @param accountType 账户类型
	 * @return
	 */
	double queryBalance(int uid,int accountType);
	
	
	/**
	 * 加钱
	 * @param uid 用户id
	 * @param accountType 账户类型（现金/非现金账户）
	 * @param amount 充值金额
	 * @return 0成功，1失败 2参数错误 
	 */
	int add(int uid, int accountType, double amount);
	

	
	/**
	 * 扣款(不会检查余额）
	 * @param uid 用户id
	 * @param accountType 扣款账户
	 * @param amount 扣款金额
	 * @return
	 */
	int cut(int uid, int accountType, double amount);

	
	/**
	 * 创建账户：现金账户和非现金账户
	 * @param uid
	 * @return
	 */
	int createAccount(int uid); 
	
	
	/**
	 * 添加一个需要开通易宝账户的申请
	 * @param uid 
	 * @param authStatus 是否需要直接实名验证
	 * @param userName 姓名
	 * @param idCardNo 身份证
	 * @return  0为成功， 1为失败
	 */
	int addZhtAsyncReg(int uid, String authStatus, String userName, String idCardNo);
}
