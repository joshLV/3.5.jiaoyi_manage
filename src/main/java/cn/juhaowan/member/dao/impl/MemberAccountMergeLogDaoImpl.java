package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberAccountMergeLogDao;
import cn.juhaowan.member.vo.MemberAccountMergeLog;



@Repository("MemberAccountMergeLogDao")
public class MemberAccountMergeLogDaoImpl extends BaseDaoImplJdbc<MemberAccountMergeLog> implements MemberAccountMergeLogDao  {

	@Autowired
	private JdbcOperations jdbcOperations;
	/**
	 * 获取表名
	 * @param uid
	 * @return
	 */
	public String getTableName(int uid){
		return "member_account_merge_log";
	}
	

	public void addLog(MemberAccountMergeLog log) {
		if(log.getCompleteTime() != null) {
			String sql = "INSERT INTO "  + this.getTableName(log.getUid())
					+ "(uid, operator_type, pay_type, bank_id, bank_card_no, card_no, amount_cash,"
					+ "amount_no_cash, amount_total, fee, total_balance, complete_time, order_id, external_id," 
					+ " request_id, remark, sync_status, create_time)" 
					+ " VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			jdbcOperations.update(sql, log.getUid(), log.getOperatorType(), log.getPayType(), log.getBankId(), log.getBankCardNo(),
				log.getCardNo(), log.getAmountCash(), log.getAmountNoCash(), log.getAmountTotal(), log.getFee(), log.getTotalBalance(),
				log.getCompleteTime(), log.getOrderId(), log.getExternalId(), log.getRequestId(), log.getRemark(), log.getSyncStatus(),
				log.getCompleteTime());
		} else {	
			String sql = "INSERT INTO "  + this.getTableName(log.getUid())
					+ "(uid, operator_type, pay_type, bank_id, bank_card_no, card_no, amount_cash,"
					+ "amount_no_cash, amount_total, fee, total_balance, complete_time, order_id, external_id," 
					+ " request_id, remark, sync_status, create_time)" 
					+ " VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			
			jdbcOperations.update(sql, log.getUid(), log.getOperatorType(), log.getPayType(), log.getBankId(), log.getBankCardNo(),
					log.getCardNo(), log.getAmountCash(), log.getAmountNoCash(), log.getAmountTotal(), log.getFee(), log.getTotalBalance(),
					log.getCompleteTime(), log.getOrderId(), log.getExternalId(), log.getRequestId(), log.getRemark(), log.getSyncStatus());
		}
	}
	
	
	@Override
	public void addRechargeLog(int uid, int payType, String cardNo, double amountCash, double amountNoCash, double fee,
			double totalBalance, Date completeTime, String externalId, String requestId, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_RECHARGE);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_RECHARGE);
		log.setPayType(payType);
		log.setBankId(""); 
		log.setBankCardNo("");
		log.setCardNo(cardNo);
		log.setAmountCash(amountCash);
		log.setAmountNoCash(amountNoCash);
		log.setAmountTotal(amountCash + amountNoCash - fee);
		log.setFee(fee);
		log.setTotalBalance(totalBalance);
		if(completeTime == null) {
			log.setCompleteTime(new Date());
		} else {
			log.setCompleteTime(completeTime);
		}
		log.setOrderId("");
		log.setExternalId(externalId);
		log.setRequestId(requestId);
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
		log.setRemark(remark);

		this.addLog(log);
		//this.insert(log);
		
	}
	
	
//	@Override
//	public void addPayLog(int uid, int showOperatorType, int payType, double amountCash, double amountNoCash, double totalBalance, 
//			String orderId, String remark) {
//		MemberAccountMergeLog log = new MemberAccountMergeLog();
//		log.setUid(uid);
//		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_PAY);
//		log.setShowOperatorType(showOperatorType);
//		log.setPayType(payType);
//		log.setBankId(""); 
//		log.setBankCardNo("");
//		log.setCardNo("");
//		log.setAmountCash(amountCash);
//		log.setAmountNoCash(amountNoCash);
//		log.setAmountTotal(amountCash + amountNoCash);
//		log.setFee(0);
//		log.setTotalBalance(totalBalance);
//		log.setCompleteTime(null);
//		log.setOrderId(orderId);
//		log.setExternalId("");
//		log.setRequestId("");
//		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
//		log.setRemark(remark);
//		
//		this.addLog(log);
//	}
//	
	
	@Override
	public void addPayLog(int uid, int payType, double amountCash, double amountNoCash, double totalBalance, 
			String orderId, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_PAY);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_PAY);
		log.setPayType(payType);
		log.setBankId(""); 
		log.setBankCardNo("");
		log.setCardNo("");
		log.setAmountCash(amountCash);
		log.setAmountNoCash(amountNoCash);
		log.setAmountTotal(amountCash + amountNoCash);
		log.setFee(0);
		log.setTotalBalance(totalBalance);
		log.setCompleteTime(null);
		log.setOrderId(orderId);
		log.setExternalId("");
		log.setRequestId("");
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
		log.setRemark(remark);
		
		this.addLog(log);
	}
	
	
	@Override
	public void addRevenueLog(int uid, double amount, double totalBalance, String orderId, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_PAY_COLLECT);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_TYPE_PAY_COLLECT);
		log.setPayType(1);
		log.setBankId(""); 
		log.setBankCardNo("");
		log.setCardNo("");
		log.setAmountCash(amount);
		log.setAmountNoCash(0);
		log.setAmountTotal(amount);
		log.setFee(0);
		log.setTotalBalance(totalBalance);
		log.setCompleteTime(null);
		log.setOrderId(orderId);
		log.setExternalId("");
		log.setRequestId("");
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
		log.setRemark(remark);
		
		this.addLog(log);
		//this.insert(log);
	}
	
	
	@Override
	public void addTakeMoneyLog(int uid, String orderId, double amount, double fee, double totalBalance, String bankId,
			String bankCardNo, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_TAKE_MONEY);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_TAKE_MONEY);
		log.setPayType(1);
		log.setBankId(bankId); 
		log.setBankCardNo(bankCardNo);
		log.setCardNo("");
		log.setAmountCash(amount);
		log.setAmountNoCash(0);
		log.setAmountTotal(amount - fee);
		log.setFee(fee);
		log.setTotalBalance(totalBalance);
		log.setCompleteTime(null);
		log.setOrderId(orderId);
		log.setExternalId("");
		log.setRequestId("");
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
		log.setRemark(remark);
		
		this.addLog(log);
		//this.insert(log);
	}
	
	
	@Override
	public void addReturnMoneyLog(int uid, double amountCash, double amountNoCash, double totalBalance,
			String orderId, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_RETURN_MONEY);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_RETURN_MONEY);
		log.setPayType(1);
		log.setBankId(""); 
		log.setBankCardNo("");
		log.setCardNo("");
		log.setAmountCash(amountCash);
		log.setAmountNoCash(amountNoCash);
		log.setAmountTotal(amountCash + amountNoCash);
		log.setFee(0);
		log.setTotalBalance(totalBalance);
		log.setCompleteTime(null);
		log.setOrderId(orderId);
		log.setExternalId("");
		log.setRequestId("");
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NEED_SYNC);
		log.setRemark(remark);
		
		this.addLog(log);
		//this.insert(log);
	}


	@Override
	public void addReturnTakeMoneyLog(int uid, double amount, double totalBalance, String requestId, String remark) {
		MemberAccountMergeLog log = new MemberAccountMergeLog();
		log.setUid(uid);
		log.setOperatorType(MemberAccountMergeLogDao.OPERATOR_RETURN_TAKE_MONEY);
//		log.setShowOperatorType(MemberAccountMergeLogDao.OPERATOR_RETURN_TAKE_MONEY);
		log.setPayType(1);
		log.setBankId(""); 
		log.setBankCardNo("");
		log.setCardNo("");
		log.setAmountCash(amount);
		log.setAmountNoCash(0);
		log.setAmountTotal(amount);
		log.setFee(0);
		log.setTotalBalance(totalBalance);
		log.setCompleteTime(null);
		log.setOrderId("");
		log.setExternalId("");
		log.setRequestId(requestId);
		log.setRemark(remark);
		log.setSyncStatus(MemberAccountMergeLogDao.SYNC_STAT_NO_SYNC); //设置调账的状态为10. 此条不用同步。
		this.addLog(log);
		//this.insert(log);
	}


	@Override
	public PageInfo<MemberAccountMergeLog> findWithPage(Map<String, Object> conditions, int pageSize, int pageNo, String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "uid";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from member_account_merge_log where 1=1 ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String) conditions.get("uid"))) {
				sql.append(" and uid = ?");
				conList.add(conditions.get("uid"));
			}

			if (StringUtils.isNotBlank((String) conditions.get("beginTime"))) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
			if (StringUtils.isNotBlank((String) conditions.get("endTime"))) {
				sql.append(" and create_time <= ? ");
				conList.add(conditions.get("endTime"));
			}
			if (StringUtils.isNotBlank((String)conditions.get("operatorType"))) {
				sql.append("and operator_type=?");
				conList.add((String)conditions.get("operatorType"));
			}
			if(StringUtils.isNotBlank((String)conditions.get("externalId"))) {
				sql.append("and external_id=?");
				conList.add(conditions.get("externalId"));
			}
		}   
		sql.append(" order by " + sort + " " + order);
		String sqlcount = "select count(*)  " + sql.toString();
		System.out.println("sqlcount:" + sqlcount);
		@SuppressWarnings("deprecation")
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<MemberAccountMergeLog> pageInfo = new PageInfo<MemberAccountMergeLog>(count, pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		// pageInfo.setPageSize(pageSize);

		if (count == 0) {
			return pageInfo;
		}

		if (pageNo <= 0) {
			pageNo = 1;
		}

		if (pageNo > pageInfo.getPageCount()) {
			pageNo = pageInfo.getPageCount();
		}

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0) {
			firstResult = 0;
		}

		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();

		System.out.println("sqlPage:" + sqlPage);
		
		JuRowCallbackHandler<MemberAccountMergeLog> rowCallbackHandler = new JuRowCallbackHandler<MemberAccountMergeLog>(MemberAccountMergeLog.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
	}
}
