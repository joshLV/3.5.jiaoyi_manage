package cn.juhaowan.member.service.impl;

import net.sf.json.JSONObject;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import cn.jugame.pay.http.api.PayApiWrapper;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberAccountDao;
import cn.juhaowan.member.dao.MemberAccountFrozenDao;
import cn.juhaowan.member.dao.MemberAccountLogDao;
import cn.juhaowan.member.dao.MemberAccountMergeLogDao;
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

/**
 * 用户资金账户
 * 
 * @author cai9911
 *
 */
@Service("MemberAccountService")
public class HttpMemberAccountService implements MemberAccountService {
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
	private MemberAccountFrozenDao memberAccountFrozenDao;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	Logger logger = LoggerFactory.getLogger(HttpMemberAccountService.class);
	
	PayApiWrapper w = new PayApiWrapper("pay-api.properties");
	
	@Override
	public int  rechargeForUser(String uid, String amount, String accountType, String channel,
			String remark) {
		
		String jsonStr = w.rechargeForUser(uid, amount, accountType, channel, remark);

		logger.info("rechargeForUser jsonStr:" + jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		if(jsonObject.isNullObject()) {
			logger.error("rechargeForUsers, json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		
		if(jsonObject.getInt("code") == 0) {
			logger.info("rechargeForUser成功. uid:" + uid + ", amount:" + amount + ",accountType:" + accountType
					+ ",remark:" + remark);
			return 0;
		}
		
		logger.info("rechargeForUser失败. uid:" + uid + ", amount:" + amount + ",accountType:" + accountType
				+ ",remark:" + remark);
		
		return 1;
	}
	
	
	@Override
	public int recharge (int uid, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark) {
		logger.error("不提供recharge方法的实现。");
		return -1;
//		return this.recharge(uid, 1, accountType, amount, fee, bankId, bankCardNo, cardNo, orderId, completeTime, externalId, remark);
	}
	


	@Override
	public int recharge (int uid, int payType, int accountType, double amount, double fee, String bankId, String bankCardNo, String cardNo,
			String orderId, Date completeTime, String externalId, String remark) {
		logger.error("不提供recharge方法的实现。");
		return -1;
	}
	
	@Override
	public double[] pay(int outUid, double amount, String orderId, int payType, String remark) {
		double[] reslut = new double[3];
		reslut[0] = -1;
		reslut[1] = -1;
		reslut[2] = -1;
		
		logger.error("不提供pay方法实现。");
		return reslut;
	}
	
	
	@Override
	public int revenue(int uid ,double amount,String orderId,String remark){

		//如果已经转账 直接返回0
		String sql  = "SELECT zhifu_id,zhifu_status FROM `zhifu_order` WHERE order_id = ? AND  `order_seller_uid` = ? AND `zhifu_total_amount` = ?  ORDER BY order_create_time DESC LIMIT 1 ";
		SqlRowSet re = jdbcOperations.queryForRowSet(sql,orderId,uid,amount);
		while(re.next()){
			String status = re.getString(2);
			if(status.endsWith("30")){
				return 0;
			}
		}

		String jsonStr = w.revenue(orderId, String.valueOf(uid), String.valueOf(amount), remark);
		logger.info("revenue jsonStr:" + jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		if(jsonObject.isNullObject()) {
			logger.error("json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		
		if(jsonObject.getInt("code") == 0 && jsonObject.getInt("status") == 0) {
			logger.info("revenue成功. order_id:" + jsonObject.getString("order_id")
					+ ", amount:" + jsonObject.getString("amount"));
			return 0;
		}
		
		logger.error("revenue失败. order_id:" + orderId + ", code:" + jsonObject.getString("code"));
		
		return 1;
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
		
		logger.error("不提供takeMoney的实现");
		return -1;
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
//		if(uid <= 0) {
//			logger.error("DefaultMemberAccountService::query48hRecharegeAmount uid=" + uid + " error");
//			return 0;
//		}
//		
//		return this.memberCash48hRecharegeDao.queryAmount(uid);
		
		//不做提现限制了
		return 0;
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
		logger.error("不提供returnMoney方法的实现。请使用其他重载的方法，需要一次传入2个账户退费的金额");
		return -1;
	}


	@Override
	public int returnMoney(int uid, String orderId, double amountCash, double amountNoCash, String remark) {
		
		String jsonStr = w.returnMoney(orderId, String.valueOf(uid), String.valueOf(amountCash),
				 String.valueOf(amountNoCash), remark);
		

		logger.info("returnMoney jsonStr:" + jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		if(jsonObject.isNullObject()) {
			logger.error("returnMoney, json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		
		if(jsonObject.getInt("code") == 0) {
			logger.info("returnMoney成功. uid:" + uid + ", orderId:" + orderId
					+ "amountCash:" + amountCash + ",amountNoCash:" + amountNoCash + ",remark:" + remark);
			return 0;
		}
		
		logger.error("returnMoney失败. uid:" + uid + ", orderId:" + orderId
				+ "amountCash:" + amountCash + ",amountNoCash:" + amountNoCash + ",remark:" + remark);
		
		return 1;
		 
	}
		
	
	@Override
	public int returnTakeMoney(int uid, double amount, String requestId, String remark) {
		logger.error("不提供returnTakeMoney方法的实现。");
		return -1;
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
		logger.error("不提供alipayTransfer方法的实现。");
		return -1;
	}


	@Override
	public int transfer(String fromUid, String toUid, String accountType, String amount, String remark) {
		String jsonStr = w.transfer(fromUid, toUid, accountType, amount, remark);
		
		logger.info("transfer jsonStr:" + jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		if(jsonObject.isNullObject()) {
			logger.error("transfer, json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		
		if(jsonObject.getInt("code") == 0) {
			logger.info("transfer成功. fromUid:" + fromUid + ", toUid:" + toUid + ", amount:" + amount 
					+ ",accountType:" + accountType + ",remark:" + remark);
			return 0;
		}
		
		logger.error("transfer失败. fromUid:" + fromUid + ", toUid:" + toUid + ", amount:" + amount 
				+ ",accountType:" + accountType + ",remark:" + remark);
		
		return 1;
	}


	@Override
	public int adjustRevenue(String orderId, String sellerUid, String sellerAmount, String buyerUid,
			String buyerAmount, String remark) {
	
		String jsonStr = w.adjustRevenue(orderId, sellerUid, sellerAmount, buyerUid, buyerAmount, remark);
		logger.info("adjustRevenue jsonStr:" + jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		
		if(jsonObject.isNullObject()) {
			logger.error("transfer, json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		
		if(jsonObject.getInt("code") == 0 && jsonObject.getInt("status") == 0) {
			logger.info("adjustRevenue成功. order_id:" + jsonObject.getString("order_id"));
			return 0;
		}
		
		logger.error("adjustRevenue失败. sellerUid:" + sellerUid + ", sellerAmount:" + sellerAmount 
				+ ", buyerUid:" + buyerUid  + ",buyerAmount:" + buyerAmount + ",remark:" + remark);
		
		return 1;
	}
}
