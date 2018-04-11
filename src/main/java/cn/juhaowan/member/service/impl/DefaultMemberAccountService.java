package cn.juhaowan.member.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import cn.jugame.util.PageInfo;
import cn.jugame.util.Sundry;
import cn.juhaowan.member.dao.MemberAccountDao;
import cn.juhaowan.member.dao.MemberAccountFrozenDao;
import cn.juhaowan.member.dao.MemberAccountLogDao;
import cn.juhaowan.member.dao.MemberAccountMergeLogDao;
import cn.juhaowan.member.dao.MemberCash48hRechargeDao;
import cn.juhaowan.member.dao.MemberCashSettingDao;
import cn.juhaowan.member.dao.MemberDepositDao;
import cn.juhaowan.member.dao.MemberDepositLogDao;
import cn.juhaowan.member.service.MemberAccountService;
import cn.juhaowan.member.vo.MemberAccountFrozen;
import cn.juhaowan.member.vo.MemberAccountLog;
import cn.juhaowan.member.vo.MemberAccountMergeLog;
import cn.juhaowan.member.vo.MemberCashSetting;
import cn.juhaowan.member.vo.MemberDeposit;
import cn.juhaowan.member.vo.MemberDepositLog;
//import cn.juhaowan.member.vo.MemberDeposit;
//import cn.juhaowan.member.vo.MemberDepositLog;

/**
 * 用户资金账户
 * 
 * @author cai9911
 *
 */
//@Service("MemberAccountService")
public class DefaultMemberAccountService implements MemberAccountService {
	@Autowired
	private MemberAccountDao memberAccountDao;
	
	@Autowired
	private MemberAccountLogDao memberAccountLogDao;
	
	@Autowired
	private MemberAccountMergeLogDao memberAccountMergeLogDao;
		
	@Autowired
	private MemberCashSettingDao memberCashSettingDao;
	
	@Autowired
	private MemberDepositDao memberDepositDao;
	
	@Autowired
	private MemberDepositLogDao memberDepositLogDao;
	
	@Autowired
	private MemberCash48hRechargeDao memberCash48hRecharegeDao;
	
	@Autowired
	private MemberAccountFrozenDao memberAccountFrozenDao;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Autowired
	private javax.sql.DataSource dataSource;
	
	
	Logger logger = LoggerFactory.getLogger(DefaultMemberAccountService.class);
	
	
	
	@Override
	public int recharge (int uid, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark) {
		return this.recharge(uid, 1, accountType, amount, fee, bankId, bankCardNo, cardNo, orderId, completeTime, externalId, remark);
	}
	


	@Override
	public int recharge (int uid, int payType, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark) {
		
		if(uid <= 0 || amount <= 0){
			return 2;
		}
		
		if (accountType != MemberAccountDao.ACCOUNT_TYPE_CASH 
			&& accountType != MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) {
			return 3;
		}
		
		//判断是否有创建现金和非现金账户，如果没有则创建相应的账户
		//有异常也不管，继续跑
		try {
			memberAccountDao.createAccount(uid); 
		} catch(Exception e) {
			logger.error("" + e);
		}

		double amountCash = 0; //充值非现金金额
		double amountNoCash = 0; //充值现金金额
		double balance = 0; //指定账户余额
		double totalBalance = 0; //总余额
		
		//现金充值，冗余写入48小时现金充值表
		if(accountType == MemberAccountDao.ACCOUNT_TYPE_CASH) {
			if(this.memberCash48hRecharegeDao.recharge(uid, amount - fee, MemberCash48hRechargeDao.DEFAULT_RECHARGE) != 0) {
				logger.error("(账户=" + accountType + ",uid=" + uid + ", 写入48小时充值表错误");
			}
			amountCash = amount;
		} else {
			amountNoCash = amount;
		}
		
		//充值日志 
		logger.warn("充值,账户=" + accountType + ",uid=" + uid + ",orderID=" + orderId + ",金额=" + amount + "，手续费=" + fee);
		
		//数据库连接
		Connection conn = null;
		try{
			//----开始事务
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//先查出这个账户的主键ID，为了事务之后的锁记录更新用
			int rid = -1;
			PreparedStatement stmt0 = conn.prepareStatement("SELECT account_id FROM member_account WHERE uid = ? AND account_type = ? LIMIT 1");
			stmt0.setInt(1, uid);
			stmt0.setInt(2, accountType);
			ResultSet rs0 = stmt0.executeQuery();
			if(rs0.next()){
				rid = rs0.getInt(1);
			}
			stmt0.close();
			
			if(rid == -1) {
				logger.error("cannot find member_account.account_id by uid:" + uid + ", accountType:" + accountType);
				return 1;
			}
			
			//更新用户指定账户余额, 必须使用主键才不会出错，什么高并发死锁啊
			PreparedStatement stmt1 = conn.prepareStatement("UPDATE member_account SET balance = balance + ? , last_time=now()"
					+ " WHERE account_id = ? LIMIT 1");
			stmt1.setDouble(1, amount - fee);
			stmt1.setInt(2, rid);
			stmt1.executeUpdate();
			stmt1.close();
			
			//查询该账户的余额
			PreparedStatement stmt2 = conn.prepareStatement("SELECT balance FROM member_account WHERE account_id = ? FOR UPDATE");
			stmt2.setInt(1, rid);
			ResultSet rs2 = stmt2.executeQuery();
			if(rs2.next()){
				balance = rs2.getDouble(1);
			}
			stmt2.close();
			
			//查询该账户的总余额， 这个不使用主键，也不会报错。 我也不知道，应该是都阻塞在stmt1了，哈哈哈。
			PreparedStatement stmt3 = conn.prepareStatement("SELECT SUM(balance) FROM member_account WHERE uid = ? FOR UPDATE");
			stmt3.setInt(1, uid);
			ResultSet rs3 = stmt3.executeQuery();
			if(rs3.next()){
				totalBalance = rs3.getDouble(1);
			}
			stmt3.close();
			
			//提交事务
			conn.commit();
		} catch (Exception e) {
			logger.error("",e);
			if(conn != null) {
				try {
					conn.rollback();
				} catch(Exception e1) {
					logger.error("conn rollback err. ", e1);
					return 1;
				}
			}
			return 1;
		} finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch(Exception e) {
					logger.error("conn 释放失败.", e);
					return 1;
				}
			}
		}

		logger.warn("充值后余额 ,账户=" + accountType + ",uid=" + uid + ", 余额=" + balance);
			
		//记录日志
		memberAccountLogDao.addLog(uid, orderId, amount, fee, accountType, MemberAccountLogDao.OPERATOR_TYPE_RECHARGE, 
				bankId, bankCardNo, cardNo,0,balance, totalBalance, remark);
			
		//记录冗余日志，给易宝做同步
		memberAccountMergeLogDao.addRechargeLog(uid, 
				payType,
				cardNo,
				amountCash,
				amountNoCash,
				fee, 
				totalBalance,
				completeTime,
				externalId,
				orderId, //8868充值内部流水号， requestId
				remark);
		
		return 0;
	}
	
//
//	@Override
//	public int recharge (int uid, int payType, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
//			String orderId, Date completeTime, String externalId, String remark) {
//		
//		if (uid <= 0 || amount <= 0){
//			return 2;
//		}
//		if (accountType != MemberAccountDao.ACCOUNT_TYPE_CASH 
//			&& accountType != MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) {
//			return 3;
//		}
//		try{
//			double amountCash = 0;
//			double amountNoCash = 0;
//			
//			//充值 
//			logger.warn("充值,账户=" + accountType + ",uid=" + uid + ",orderID=" + orderId + ",金额=" + amount + "，手续费=" + fee);
//		
//			memberAccountDao.createAccount(uid); //判断是否有创建现金和非现金账户，如果没有则创建相应的庄户。
//			memberAccountDao.add(uid, accountType, amount - fee); 
//			double balance = memberAccountDao.queryBalance(uid, accountType);
//			double totalBalance = memberAccountDao.queryBalance(uid);
//			logger.warn("充值后余额 ,账户=" + accountType + ",uid=" + uid + ", 余额=" + balance);
//
//			//现金充值，冗余写入48小时现金充值表
//			if(accountType == MemberAccountDao.ACCOUNT_TYPE_CASH) {
//				if(this.memberCash48hRecharegeDao.recharge(uid, amount - fee, MemberCash48hRechargeDao.DEFAULT_RECHARGE) != 0) {
//					logger.error("(账户=" + accountType + ",uid=" + uid + ", 写入48小时充值表错误");
//				}
//				amountCash = amount;
//				logger.warn("充值后余额 ,账户=" + accountType + ",uid=" + uid + ", 48小时内充值金额=" + this.memberCash48hRecharegeDao.queryAmount(uid));
//			} else {
//				amountNoCash = amount;
//			}
//			
//			//记录日志
//			memberAccountLogDao.addLog(uid, orderId, amount, fee, accountType, MemberAccountLogDao.OPERATOR_TYPE_RECHARGE, 
//					bankId, bankCardNo, cardNo,0,balance, totalBalance, remark);
//			
//			//记录冗余日志，给易宝做同步
//			memberAccountMergeLogDao.addRechargeLog(uid, 
//					payType,
//					cardNo,
//					amountCash,
//					amountNoCash,
//					fee, 
//					totalBalance,
//					completeTime,
//					externalId,
//					orderId, //8868充值内部流水号， requestId
//					remark);
//			
//			return 0;
//		} catch (Exception e) {
//			logger.error("",e);
//			return 1;
//		} 
//	}
	
	@Override
	public double[] pay(int outUid, double amount, String orderId, int payType, String remark) {
		double[] reslut = new double[3];
		
		//现金和非现金的数量
		double cashAmount = 0;
		double noCashAmount = 0;
		double costNoCashAmout = 0;  //花费非现金账户的数量
		double costCashAmount = 0;  //话费现金账户的数量
		
		
		//数据库连接
		Connection conn = null;
		try{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			//查询现金账户和非现金账户主键ID
			int cashAccountId = -1;
			int noCashAccountId = -1;
			
			PreparedStatement stmt0 = conn.prepareStatement("SELECT account_id, account_type FROM member_account WHERE uid = ? LIMIT 2");
			stmt0.setInt(1, outUid);
			
			ResultSet rs0 = stmt0.executeQuery();
			while(rs0.next()) {
				if(rs0.getInt(2) == MemberAccountDao.ACCOUNT_TYPE_CASH) {
					cashAccountId = rs0.getInt(1);
				}
				
				if(rs0.getInt(2) == MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) {
					noCashAccountId = rs0.getInt(1);
				}
			}
			stmt0.close();
			
			//查询现金余额
			if(cashAccountId > 0) {
				PreparedStatement stmt1 = conn.prepareStatement("SELECT balance FROM member_account WHERE account_id = ? FOR UPDATE");
				stmt1.setInt(1, cashAccountId);
				ResultSet rs1 = stmt1.executeQuery();
				if(rs1.next()) {
					cashAmount = rs1.getDouble(1);
				}
				stmt1.close();
			}
			
			//查询非现金余额
			if(noCashAccountId > 0) {
				PreparedStatement stmt2 = conn.prepareStatement("SELECT balance FROM member_account WHERE account_id = ? FOR UPDATE");
				stmt2.setInt(1, noCashAccountId);
				ResultSet rs2 = stmt2.executeQuery();
				if(rs2.next()) {
					noCashAmount = rs2.getDouble(1);
				}
				stmt2.close();
			}
			
			//总余额
			double totalBalance = cashAmount + noCashAmount;
			if(totalBalance < amount) {  //余额不足
				reslut[0] = 1;
			} else {
				if(noCashAmount >= amount) { //非现金足够支付
					costNoCashAmout = amount;
					costCashAmount = 0;
					
					logger.warn("支付,扣除用户【非现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + amount);
					PreparedStatement stmt3 = conn.prepareStatement("UPDATE member_account SET balance = balance - ? WHERE account_id = ?");
					stmt3.setDouble(1, amount);
					stmt3.setInt(2, noCashAccountId);
					stmt3.executeUpdate();
					stmt3.close();
					
					reslut[1] = amount;
					reslut[2] = 0;
				} else { //需要动用2个账户的金额
					costNoCashAmout = noCashAmount;  //花费非现金账户的数量
					costCashAmount = amount - noCashAmount;  //花费现金账户的数量
					reslut[1] = costNoCashAmout;
					reslut[2] = costCashAmount;
					
					//更新非现金账户
					if(costNoCashAmout > 0) { //判断非现金账户是否有钱， 没钱就要完全花现金账户的
						logger.warn("支付,扣除用户【非现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + costNoCashAmout);
						PreparedStatement stmt4 = conn.prepareStatement("UPDATE member_account SET balance = balance - ? WHERE account_id = ?");
						stmt4.setDouble(1, costNoCashAmout);
						stmt4.setInt(2, noCashAccountId);
						stmt4.executeUpdate();
						stmt4.close();
					}
					
					//更新现金账户
					logger.warn("支付,扣除用户【现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + costCashAmount);
					PreparedStatement stmt5 = conn.prepareStatement("UPDATE member_account SET balance = balance - ? WHERE account_id = ?");
					stmt5.setDouble(1, costCashAmount);
					stmt5.setInt(2, cashAccountId);
					stmt5.executeUpdate();
					stmt5.close();
				}
				
				//操作成功
				reslut[0] = 0;
			}
			
			conn.commit();

		} catch (Exception e) {
			logger.error("",e);
			reslut[0] = 99;
			
			if(conn != null) {
				try {
					conn.rollback();
				} catch(Exception e1) {
					logger.error("conn rollback err.", e1);
					return reslut;
				}
			}
			
			return reslut;
		} finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch(Exception e) {
					logger.error("conn close err.", e);
					return reslut;
				}
			
			}
		}
		
		//写日志
		if(reslut[0] == 0) { //支付正常写日志吧
			memberAccountMergeLogDao.addPayLog(outUid, payType, costCashAmount, costNoCashAmout, 
					cashAmount + noCashAmount - costCashAmount - costNoCashAmout, orderId, remark);
			
			
			if(costNoCashAmout > 0) {
				memberAccountLogDao.addLog(outUid, orderId, costNoCashAmout, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, 
						MemberAccountLogDao.OPERATOR_TYPE_PAY, noCashAmount - costNoCashAmout, 
						noCashAmount + cashAmount - costNoCashAmout,  //计算余额
						remark);
			}
			
			if(costCashAmount > 0) {
				memberAccountLogDao.addLog(outUid, orderId, costCashAmount, MemberAccountDao.ACCOUNT_TYPE_CASH, 
						MemberAccountLogDao.OPERATOR_TYPE_PAY, cashAmount - costCashAmount,
						noCashAmount + cashAmount - costNoCashAmout - costCashAmount,  //此处需要减去非现金的花费，计算总余额（优先消费非现金账户）
						remark);
			}
		}
		
		return reslut;
	}
	
	
//	@Override
//	public double[] pay(int outUid, double amount, String orderId, int payType, String remark) {
//		double[] reslut = new double[3];
//		try{
//			//查询余额
//			double totalBalance = memberAccountDao.queryBalance(outUid);
//			logger.info("账户总余额为:" + totalBalance + ",支付金额为:" + amount);
//			if (totalBalance < amount){
//				reslut[0] = 1;
//				return reslut;//余额不足
//			}
//			//非现金账户余额
//			double balance1 = memberAccountDao.queryBalance(outUid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
//			
//			double amountCash = 0;
//			double amountNoCash = 0;
//			
//			if (balance1 >= amount){
//				reslut[1] = amount;
//				//操作非现金账户
//				logger.warn("支付,扣除用户【非现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + amount);
//				memberAccountDao.cut(outUid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, amount);
//				double bb = balance1 - amount;//扣除余额
//				
//				//记录日志
//				memberAccountLogDao.addLog(outUid, orderId, amount, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, 
//						MemberAccountLogDao.OPERATOR_TYPE_PAY,bb, totalBalance - amount, remark);
//				
//				amountNoCash = amount;
//				
//			} else {
//				double bb = amount - balance1; 
//				reslut[2] = bb;
//				if (balance1 > 0){
//					reslut[1] = balance1;
//					logger.warn("支付,扣除用户【非现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + balance1);
//					memberAccountDao.cut(outUid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, balance1);
//					
//					//记录日志
//					memberAccountLogDao.addLog(outUid, orderId, balance1, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, MemberAccountLogDao.OPERATOR_TYPE_PAY, 0, totalBalance - balance1, remark);
//				}
//				//现金账户
//				double balance2 = memberAccountDao.queryBalance(outUid, MemberAccountDao.ACCOUNT_TYPE_CASH);
//				logger.warn("支付,扣除用户【现金账户】uid = " + outUid + ",orderID = " + orderId + ", 金额=" + bb);
//				memberAccountDao.cut(outUid, MemberAccountDao.ACCOUNT_TYPE_CASH, bb);
//				
//				//记录日志
//				memberAccountLogDao.addLog(outUid, orderId, bb, MemberAccountDao.ACCOUNT_TYPE_CASH, MemberAccountLogDao.OPERATOR_TYPE_PAY, balance2 - bb, totalBalance - amount , remark);
//				
//				amountNoCash = balance1;
//				amountCash = bb;	
//			}
//			
//			memberAccountMergeLogDao.addPayLog(outUid, payType, amountCash, amountNoCash, totalBalance - amountCash - amountNoCash, orderId, remark);
//		} catch (Exception e) {
//			logger.error("",e);
//			reslut[0] = 99;
//		}
//		return reslut;
//	}
	
	
	@Override
	public int revenue(int uid ,double amount,String orderId,String remark){
		if (uid <= 0 || amount <= 0) {
			return 2; 
		}
		try{
			//充值 
			logger.warn("收入,账户uid=" + uid + ",orderID=" + orderId + ",金额=" + amount);
			memberAccountDao.add(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount); 
			double balance = memberAccountDao.queryBalance(uid,MemberAccountDao.ACCOUNT_TYPE_CASH);
			double totalBalance = memberAccountDao.queryBalance(uid);
			logger.warn("收入余额 ,uid=" + uid + ", 余额=" + balance);
			 
			if(this.memberCash48hRecharegeDao.recharge(uid, amount, MemberCash48hRechargeDao.REVENUE_RECHARGE) != 0) {
				logger.error("uid=" + uid + ", 写入48小时充值表错误");
			}
			
			logger.warn("收入 ,uid=" + uid + ", 48小时内充值金额=" + this.memberCash48hRecharegeDao.queryAmount(uid));
			
			//记录日志
			memberAccountLogDao.addLog(uid, orderId, amount, MemberAccountDao.ACCOUNT_TYPE_CASH, 
					MemberAccountLogDao.OPERATOR_TYPE_PAY_COLLECT,	balance, totalBalance, remark);
			
			//记录冗余日志，给易宝做同步
			memberAccountMergeLogDao.addRevenueLog(uid, amount, totalBalance, orderId, remark);
			
			return 0;
		}catch (Exception e) {
			logger.error("",e);
			return 1;
		}
	}
	
	
	@Override
	public double queryCash(int uid) {
		if (uid <= 0){
			return 0;
		}
		return memberAccountDao.queryCash(uid);
	}

	
	@Override
	public int takeMoney(int uid, double amount,double fee,String bankId,String bankCardNo,String orderId, String remark) {
		double balance = memberAccountDao.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
		if (balance < amount) {
			return 1;//余额不足
		}
		try {
			logger.warn("提现(支出)，扣除用户【现金账户】uid=" + uid + ", orderId=" + orderId + ",金额=" + amount + ",余额=" + (balance - amount) + ",remark=" + remark);
			memberAccountDao.cut(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount);
			
			double totalBalance = memberAccountDao.queryBalance(uid);
			
			balance =  balance - amount;
			//记录日志
			memberAccountLogDao.addLog(uid, orderId, amount,fee, MemberAccountDao.ACCOUNT_TYPE_CASH, MemberAccountLogDao.OPERATOR_TAKE_MONEY,
					bankId,bankCardNo, "" , 0, balance , totalBalance, remark);
			 
			//记录冗余日志，给易宝做同步
			memberAccountMergeLogDao.addTakeMoneyLog(uid, orderId, amount, fee, totalBalance, bankId, bankCardNo, remark);
			
			return 0;
		} catch (Exception e) {
			logger.error("",e);
			return 1;
		}
	}

	@Override
	public double queryBalance(int uid) {
		if (uid <= 0){
			return 0;
		}
		return memberAccountDao.queryBalance(uid); 
	}
	
	
	@Override
	public double queryBalance(int uid, int accountType) {
		if (uid <= 0){
			return 0;
		}
		return memberAccountDao.queryBalance(uid,accountType);
	}
	
	
	@Override
	public double queryCash48hRechargeAmount(int uid) {
		if(uid <= 0) {
			logger.error("DefaultMemberAccountService::query48hRecharegeAmount uid=" + uid + " error");
			return 0;
		}
		
		return this.memberCash48hRecharegeDao.queryAmount(uid);
	}

	
	@Override
	public double queryCanTakeMoneyAmount(int uid) {
		if(uid <= 0)  {
			logger.error("DefaultMemberAccountService::queryCanTakeMoneyAmount uid=" + uid + " error");
			return 0;
		}
		
		//余额
		double cashBalance = this.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
		
		//48小时内充值
		double recharge48hAmount  = this.queryCash48hRechargeAmount(uid);
		
		logger.info("uid:" + uid + ", cashBalance:" + cashBalance + ", recharge48hAmount:" + recharge48hAmount);
		
		//如果余额大于48小时内充值金额， 可提现金额 = 余额 - 48小时内充值金额
		if(cashBalance > recharge48hAmount) {
			return cashBalance - recharge48hAmount;
		} 
		
		//否则 可提现金额为0
		return 0;
	}
	
	
	@Override
	public double queryCanNotTakeMoneyAmount(int uid) {
		if(uid <= 0)  {
			logger.error("DefaultMemberAccountService::queryCanNotTakeMoneyAmount uid=" + uid + " error");
			return 0;
		}
		
		//现金余额
		double cashBalance = this.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
		double notCashBalance = this.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
		
		//48小时内充值
		double recharge48hAmount  = this.queryCash48hRechargeAmount(uid);
		
		logger.info("uid:" + uid + ", cashBalance:" + cashBalance +  ", notCashBalance:" + notCashBalance + ", recharge48hAmount:" + recharge48hAmount);
		
		
		//如果余额大于48小时内充值金额， 不可提现金额 = 48小时内充值金额 + 非现金账户余额
		if(cashBalance > recharge48hAmount) {
			return recharge48hAmount + notCashBalance;
		} 
		
		//否则 不可提现金额为余额 + 非现金账户余额
		return cashBalance + notCashBalance;
	}
	
	
	@Override
	public PageInfo<?> queryAccountLog(int uid, int type, String startDate,String endDate, int pageSize, int pageNo) {
		StringBuffer sqlCondition = new StringBuffer(" uid = ? and 1 = 1");
		Object[] objects = new Object[]{uid};
		if(type != -1){
			sqlCondition.append(" and operator_type = ?");
			objects = Arrays.copyOf(objects, objects.length + 1);
			objects[objects.length - 1] = type;
		}
		if(startDate != null){
			sqlCondition.append(" and create_time > ?");
			objects = Arrays.copyOf(objects, objects.length + 1);
			objects[objects.length - 1 ] = startDate;
		}
		if(endDate != null){
			sqlCondition.append(" and create_time < ?");
			objects = Arrays.copyOf(objects, objects.length + 1);
			objects[objects.length - 1] = endDate;
		}
		//String sqlCondition = " uid = ? and operator_type = ?  and create_time > ? and create_time < ? ";
		System.out.println(sqlCondition.toString());
		int count = memberAccountLogDao.queryLogCount(uid, sqlCondition.toString(), objects);
		System.out.println(count);
		PageInfo<MemberAccountLog> pageInfo = new PageInfo<>(count, pageSize);
	
		int fistResult = pageInfo.getStartNum(pageNo); 
		List<MemberAccountLog> list = memberAccountLogDao.queryLogList(uid, sqlCondition.toString(), fistResult, pageSize, objects);
		pageInfo.setItems(list);
		
		return pageInfo;
	}
	
	
	@Override
	public PageInfo<?> queryAccountLog2(int uid, List<Integer> type, int pageSize, int pageNo) {
		StringBuffer sqlCondition = new StringBuffer(" uid = ? and (operator_type = ?");
		if(type.size() == 2){
			sqlCondition.append(" or operator_type = ?)");
			int count = memberAccountLogDao.queryLogCount(uid, sqlCondition.toString(), uid, type.get(0),type.get(1));
			PageInfo<MemberAccountLog> pageInfo = new PageInfo<>(count, pageSize);
			int fistResult = pageInfo.getStartNum(pageNo); 
			List<MemberAccountLog> list = memberAccountLogDao.queryLogList(uid, sqlCondition.toString(), fistResult, pageSize, uid, type.get(0),type.get(1));
			pageInfo.setItems(list);
			return pageInfo;
		}
		
		if(type.size() == 1){
			sqlCondition.append(")");
			int count = memberAccountLogDao.queryLogCount(uid, sqlCondition.toString(), uid, type.get(0));
			PageInfo<MemberAccountLog> pageInfo = new PageInfo<>(count, pageSize);
			int fistResult = pageInfo.getStartNum(pageNo); 
			List<MemberAccountLog> list = memberAccountLogDao.queryLogList(uid, sqlCondition.toString(), fistResult, pageSize, uid, type.get(0));
			pageInfo.setItems(list);
			return pageInfo;
		}
		return null;
	}
	
	
	@Override
	public int setTakeMoneyLimit(int uid, int dayLimit, int itemLimit) {
		if (uid <= 0){
			return 0;
		}
		
		try{
			memberCashSettingDao.setTakeMoneyLimit(uid, dayLimit, itemLimit);
			return 0;
		} catch(Exception ex) {
			logger.error("", ex);
			return 1;
		}
	}
	
	
	@Override
	public MemberCashSetting getTakeMoneyLimit(int uid) {
		if (uid <= 0) {
			return null;
		}
		return memberCashSettingDao.findByUid(uid);
	}

	
	@Override
	public int returnMoney(int uid,int accountType, String orderId, double amount, String remark) {
		if (uid <= 0 || amount <= 0){
			return 2;
		}
		if (accountType != MemberAccountDao.ACCOUNT_TYPE_CASH 
			&& accountType != MemberAccountDao.ACCOUNT_TYPE_NOT_CASH){
			return 3;
		}
		try{
			//退费 
			logger.warn("退款,账户=" + accountType + ",uid=" + uid + ",orderID=" + orderId + ",金额=" + amount);
			memberAccountDao.add(uid, accountType, amount); 
			double balance = memberAccountDao.queryBalance(uid,accountType);
			double totalBalance = memberAccountDao.queryBalance(uid);
			logger.warn("退款后余额 ,账户=" + accountType + ",uid=" + uid + ", 余额=" + balance);
			
			
			//记录日志
			memberAccountLogDao.addLog(uid, orderId, amount, accountType, MemberAccountLogDao.OPERATOR_RETURN_MONEY, 
					balance, totalBalance, remark);
			
			//写冗余日志
			double amountCash = 0;
			double amountNoCash = 0;
			if(accountType == MemberAccountDao.ACCOUNT_TYPE_CASH) {
				amountCash = amount;
			} else {
				amountNoCash = amount;
			}
			
			memberAccountMergeLogDao.addReturnMoneyLog(uid, amountCash, amountNoCash, totalBalance, orderId, remark);
			return 0;
		} catch (Exception e) {
			logger.error("",e);
			return 1;
		} 
	}


	@Override
	public int returnMoney(int uid, String orderId, double amountCash, double amountNoCash, String remark) {
		if (uid <= 0 || amountCash + amountNoCash <= 0 || amountNoCash < 0 || amountCash < 0){
			return 2;
		}
		
		try{			
			//现金
			if(amountCash > 0) {
				memberAccountDao.add(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amountCash);
				
				double cashBalance = memberAccountDao.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
				double tmpTotalBalance = memberAccountDao.queryBalance(uid);
				
				//记录日志
				memberAccountLogDao.addLog(uid, orderId, amountCash, MemberAccountDao.ACCOUNT_TYPE_CASH,
						MemberAccountLogDao.OPERATOR_RETURN_MONEY, cashBalance, tmpTotalBalance, remark);
			} 
			
			//非现金
			if(amountNoCash > 0) {
				memberAccountDao.add(uid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH, amountNoCash);
				
				double notCashBalance = memberAccountDao.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
				double tmpTotalBalance = memberAccountDao.queryBalance(uid);
				
				//记录日志
				memberAccountLogDao.addLog(uid, orderId, amountCash, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH,
						MemberAccountLogDao.OPERATOR_RETURN_MONEY, notCashBalance, tmpTotalBalance, remark);
			}
			
			//写冗余日志
			double totalBalance = memberAccountDao.queryBalance(uid);
			memberAccountMergeLogDao.addReturnMoneyLog(uid, amountCash, amountNoCash, totalBalance, orderId, remark);
			return 0;
			
		} catch (Exception e) {
			logger.error("",e);
			return 1;
		} 
	}
		
	
	@Override
	public int returnTakeMoney(int uid, double amount, String requestId, String remark) {
		if(uid <= 0 || amount <= 0) {
			return 2;
		}
		try {
			//提现都是现金
			memberAccountDao.add(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount); 
			double cashBalance = memberAccountDao.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
			double totalBalance = memberAccountDao.queryBalance(uid);
			
			memberAccountLogDao.addLog(uid, "", amount, MemberAccountDao.ACCOUNT_TYPE_CASH,
					MemberAccountLogDao.OPERATOR_RETURN_MONEY, cashBalance, totalBalance, remark);
			memberAccountMergeLogDao.addReturnTakeMoneyLog(uid, amount, totalBalance, requestId, remark);
			return 0;
		} catch (Exception e) {
			logger.error("",e);
			return 1;
		} 
	}
	
	
	
	@Override
	public PageInfo<MemberAccountLog> queryMemberAccountLogPageBack(int uid, int type, String beginTime,String endTime, int pageNo, int pageSize, String sort, String order){
		Map<String, Object> conditions = new HashMap<String, Object>();

		if (uid > 0) {
			conditions.put("uid", uid);
		}
		if (type > 0) {
			conditions.put("operator_type", type);
		}

		if(!("".equals(beginTime))){
			conditions.put("beginTime", beginTime);
		}
		if(!("".equals(endTime))){
			conditions.put("endTime", endTime);
		}
		return memberAccountLogDao.queryPageInfo(conditions, pageNo, pageSize, sort, order);
	}

	
	@Override
	public MemberAccountLog queryLogById(int id) {
		return memberAccountLogDao.queryById(id);
	}


	
	@Override
	public int createAccount(int uid, String authStatus, String userName, String idCardNo) {
		if(memberAccountDao.createAccount(uid) != 0) {
			logger.error("createAccount cashAccount and notCashAccount error");
			return 1;
		}
		
		if(memberAccountDao.addZhtAsyncReg(uid, authStatus, userName, idCardNo) != 0) {
			logger.error("memberZhtAsyncService.regAdd error. uid:" + uid);
			return 2;
		}

		return 0;
		
		
//易宝变更为异步逻辑		
//		String uidStr = String.valueOf(uid);
//		String requestId = Util.getUUID();  //生成一个请求ID(UUID)
//		
//		try {
//			
//			IRespMsg resp = null;
//			//写死默认是未验证
//			if(authStatus.equals("1")) {
//				resp = YeePayApi.initializeAccount(uidStr, requestId, userName, "", "ID", idCardNo, authStatus, "", "");
//			} else {
//				resp = YeePayApi.initializeAccount(uidStr, requestId, "", "", "", "", "", "", "");
//			}
//			
//			if(resp != null && resp instanceof InitializeAccountRespMsg) {
//				InitializeAccountRespMsg respMsg = (InitializeAccountRespMsg)resp;
//				//创建成功或者用户已经存在，则认为成功
//				if(respMsg.getCode().equals("1") || respMsg.getCode().equals("2004")) {
//					return 0;
//				} 
//			}
//			
//			logger.error("createAccount resp error");
//			return 2;
//		} catch (IOException e) {
//			logger.error("createAccount exception:" + e);
//			return 3;
//		}
	}


	
	
	
	@Override
	public int lockDeposit(int uid, int depositType,double amount, String remark) {
		//扣除账户金额
		try{
			double balance1 = memberAccountDao.queryBalance(uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
			if (balance1 < amount){
				return 1; //余额不足
			}
			
			double balance2 = balance1 - amount; 
			memberAccountDao.cut(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount);
			
			double totalBalance = memberAccountDao.queryBalance(uid);
			memberAccountLogDao.addLog(uid, "", amount, MemberAccountDao.ACCOUNT_TYPE_CASH,
					MemberAccountLogDao.OPERATOR_TYPE_DEPOSIT_ADD, balance2, totalBalance, remark);
			
			logger.warn("锁定押金: deposit_type=" + depositType + ",uid=" + uid + ",金额=" + amount + ",remark=" + remark);
			
			//增加押金金额
			memberDepositDao.add(uid, depositType, amount);
			double balance = memberDepositDao.queryBalance(uid, depositType);
			logger.warn("锁定后押金余额: deposit_type=" + depositType + ",uid=" + uid + ",余额=" + balance);
			   			
			MemberDepositLog log = new MemberDepositLog();
			log.setOperatroType(MemberDepositLogDao.OPERATOR_TYPE_ADD);
			log.setDepositType(depositType);
			log.setCreateTime(new Date());
			log.setRemark(remark);
			log.setUid(uid);
			log.setAmount(amount);
			log.setBalance(balance);
			memberDepositLogDao.insert(log);
			return 0;
		}catch (Exception e) {
			logger.error("",e);
			return 1;
		}
	}

	
	@Override
	public int unlockDeposit(int uid, int depositType, double amount, String remark) {
		try{
			double balance = memberDepositDao.queryBalance(uid, depositType);
			if (balance < amount){
				return 1;
			}
			
			logger.warn("【解锁押金】uid=" + uid + ",金额=" + amount + ",remark=" + remark);
			this.subDeposit(uid, depositType, amount, "解锁押金," + depositType + "," + remark);
			
			//现金账户增加
			logger.warn("【解锁押金】增加现金金额,uid=" + uid + ",金额=" + amount + ",remark=" + remark);
			memberAccountDao.add(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount);
			
			double balance1 = memberAccountDao.queryBalance(MemberAccountDao.ACCOUNT_TYPE_CASH);
			double totalBalance = memberAccountDao.queryBalance(uid);
			memberAccountLogDao.addLog(uid, "", amount, MemberAccountDao.ACCOUNT_TYPE_CASH,
					MemberAccountLogDao.OPERATOR_TYPE_DEPOSIT_CUT, balance1, totalBalance, remark);
			 
			return 0;
		}catch (Exception e) {
			logger.error("",e);
			return 99;
		}
	}
	
	
	@Override
	public int subDeposit(int uid, int depositType, double amount, String remark) {
		try{ 
			double balance = memberDepositDao.queryBalance(uid, depositType);
			logger.warn("扣除押金,uid=" + uid + ",金额=" + amount + ",余额=" + balance);
			if (balance < amount){
				return 1;
			}

			//扣除押金金额
			int x = memberDepositDao.sub(uid, depositType, amount);
			logger.warn("扣除押金,uid=" + uid + ",金额=" + amount + ",余额=" + (balance - amount) + ", 返回:" + x);
			
			if(x != 0) {
				logger.error("DefaultMemberAccountService::subDeposit memberDepositDao.add return:" + x);
				return 2;
			}
			memberDepositLogDao.addLog(uid, depositType, MemberDepositLogDao.OPERATOR_TYPE_SUB, amount, balance - amount,remark);
		
			return 0;
		}catch (Exception e) {
			logger.error("",e);
			return 99;
		}
	}
	
	
	@Override
	public int addDeposit(int uid, int depositType, double amount, String remark) {
		try{
			logger.warn("添加押金, uid=" + uid + ", 金额=" + amount + ", 备注:" + remark);
			int x = memberDepositDao.add(uid, depositType, amount);
			
			if(x != 0) {
				logger.error("DefaultMemberAccountService::addDeposit memberDepositDao.add return:" + x);
				return 2;
			}
			double balance = memberDepositDao.queryBalance(uid, depositType);
			memberDepositLogDao.addLog(uid,depositType,MemberDepositLogDao.OPERATOR_TYPE_ADD,amount,balance,remark);
			
			return 0;
		}catch (Exception e) {
			logger.error("",e);
			return 99;
		}
	}
	
	
	@Override
	public List<MemberDeposit> queryDepositList(int uid) {
		return memberDepositDao.queryDepositList(uid);
	}


	@Override
	public int frozen(int uid, int type, String remark) {
		return memberAccountFrozenDao.frozen(uid, type, remark);
	}


	@Override
	public int frozen(List<Integer> l, int type, String remark) {
		return memberAccountFrozenDao.frozen(l, type,  remark);
	}

	
	@Override
	public boolean isFrozen(int uid) {
		return memberAccountFrozenDao.isFrozen(uid);
	}
	
	
	
	@Override
	public boolean isFrozenPayAndRecharge(int uid) {
		return memberAccountFrozenDao.isFrozenPayAndRecharge(uid);
	}
	
	@Override
	public int unFrozen(int uid) {
		return memberAccountFrozenDao.unFrozen(uid);
	}


	@Override
	public int unFrozen(List<Integer> l) {
		return memberAccountFrozenDao.unFrozen(l);
	}


	@Override
	public PageInfo<MemberAccountFrozen> queryMemberFrozenPage(Map<String, Object> conditions, int pageSize,
			int pageNo, String sort, String order) {
		return memberAccountFrozenDao.findWithPage(conditions, pageSize, pageNo, sort, order);
	}

	
	@Override
	public PageInfo<MemberAccountMergeLog> queryMemberAccountMergeLog(Map<String, Object> condition, int pageSize, 
			int pageNo,String sort, String order) {
		return memberAccountMergeLogDao.findWithPage(condition, pageSize, pageNo, sort, order);
	}



	@Override
	public int alipayTransfer(int fromUid, int toUid, int accountType, double amount, String orderId, double fee, String aliAccount, String desc) {
		double fromUserBalance = memberAccountDao.queryBalance(fromUid, accountType);
		if(fromUserBalance < amount) {
			logger.error("transfer " + fromUid + " totalBalance:" + fromUserBalance + " < " + amount);
			return 1;
		}
		
		//扣钱
		int r = memberAccountDao.cut(fromUid, accountType, amount);
		//扣钱失败了
		if(r != 0){
			logger.error("cut money fail! uid: " + fromUid);
			return 1;
		}
		
		//加钱
		memberAccountDao.add(toUid, accountType, amount);
		
		//2个账户的余额
		double fromUserTotalBalance = memberAccountDao.queryBalance(fromUid);
		double toUserTotalBalance = memberAccountDao.queryBalance(toUid);
		
		//写日志
		logger.info("addLog : OPERATOR_TYPE_PAY for uid:" + fromUid);
		memberAccountLogDao.addLog(fromUid, orderId, amount, fee, MemberAccountDao.ACCOUNT_TYPE_CASH, MemberAccountLogDao.OPERATOR_TYPE_PAY,
				aliAccount, Sundry.bankAccountEncrypt(aliAccount), aliAccount , 0, 0, 0, desc);
		
		//支付宝提现，展现为提现，实际为支付
		memberAccountMergeLogDao.addPayLog(fromUid, MemberAccountService.PLATFORM_JUGAME_TRANS, 
				amount, 0, fromUserTotalBalance, "", desc);

		logger.info("addLog : OPERATOR_TYPE_PAY_COLLECT for uid:" + toUid);
		memberAccountLogDao.addLog(toUid, orderId, amount, fee, accountType, MemberAccountLogDao.OPERATOR_TYPE_PAY_COLLECT, aliAccount, 
				Sundry.bankAccountEncrypt(aliAccount), aliAccount, 0, 0, 0, desc);
		memberAccountMergeLogDao.addRevenueLog(toUid, amount, toUserTotalBalance, "", desc);
		return 0;
	}



	@Override
	public int rechargeForUser(String uid, String amount, String accountType, String channel, String remark) {
		logger.error("rechargeForUser方法不能调用啊~~~");
		return -1;
	}



	@Override
	public int transfer(String fromUid, String toUid, String accountType, String amount, String remark) {
		logger.error("transfer方法不能调用啊~~~");
		return -1;
	}



	@Override
	public int adjustRevenue(String orderId, String sellerUid, String sellerAmount, String buyerUid,
			String buyerAmount, String remark) {
		logger.error("transfer方法不能调用啊~~~");
		return -1;
	}
}
