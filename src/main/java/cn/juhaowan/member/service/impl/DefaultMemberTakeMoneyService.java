package cn.juhaowan.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.D;
import cn.juhaowan.member.dao.MemberAccountDao;
import cn.juhaowan.member.dao.MemberAccountLogDao;
import cn.juhaowan.member.dao.MemberTakeMoneyDao;
import cn.juhaowan.member.dao.MemberTakeMoneyLogDao;
import cn.juhaowan.member.dao.impl.MemberTakeMoneyDaoImpl;
import cn.juhaowan.member.service.MemberAccountService;
import cn.juhaowan.member.service.MemberTakeMoneyService;
import cn.juhaowan.member.vo.MemberTakeMoney;
import cn.juhaowan.member.vo.MemberTakeMoneyLog;
import cn.juhaowan.util.Helpers;
import cn.juhaowan.util.XObject;
import cn.juhaowan.util.XProperty;

/**
 * 提现服务接口实现
 * **/
@Service("MemberTakeMoneyService")
public class DefaultMemberTakeMoneyService implements MemberTakeMoneyService {

	@Autowired
	private MemberTakeMoneyDao memberTakeMoneyDao;

	@Autowired
	private MemberTakeMoneyDaoImpl memberTakeMoneyDaoImpl;
	@Autowired
	private JdbcOperations jdbcOperations;

	@Autowired
	private MemberTakeMoneyLogDao memberTakeMoneyLogDao;
	
	@Autowired
	private MemberAccountService memberAccountService;
	
	@Autowired
	private MemberAccountLogDao memberAccountLogDao;
	
	static Logger logger = LoggerFactory.getLogger(MemberAccountService.class);

	@Override
	public int addTakeMoney(int uid, double fee, double amount, int type, String bankID, String bankCardNum,
			String bankAddr, String provinces, String realName, String orderId) {
		return memberTakeMoneyDao.addTakeMoney(uid, fee, amount, type, bankID, bankCardNum, bankAddr, provinces,
				realName, orderId);
	}

	@Override
	public int verify(int takeid, int verifyStatus, String remark) {
		return memberTakeMoneyDao.updateVerifyStauts(takeid, verifyStatus, remark);
	}

	@Override
	public int transfer(int takeid) {
		// 判断审核是否通过(1==通过)
		if (memberTakeMoneyDaoImpl.findVerifyStatus(takeid) == 1) {
			return memberTakeMoneyDao.updateTransferStatus(takeid);
		}
		return 0;
	}

	@Override
	public int transferStatusNotify(String transactionId, int status, int takeid) {
		return memberTakeMoneyDao.updateNotifyStatus(transactionId, status, takeid);
	}

	@Override
	public PageInfo<MemberTakeMoney> queryTakeMoneyrList(Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order) {
		return memberTakeMoneyDao.queryTakeMoneyrPage(condition, pageSize, pageNo, sort, order);

	}

	@Override
	public MemberTakeMoney findById(int id) {

		return memberTakeMoneyDao.findByid(id);
	}
	@Override
	public MemberTakeMoney findByTId(String tid) {
		return memberTakeMoneyDao.findBytid(tid);
	}
	
	
	@Override
	public double findAmount(int uid) {
		//银行卡提现日总金额
		double a1 = memberTakeMoneyDao.findAmount(uid);
		
		//支付宝提现日总金额
		double a2 = 0;
		String todayBeg = Helpers.getTodayBeginString();
		String todayEnd = Helpers.getTodayEndString();
		List<Map<String, Object>> datas = jdbcOperations.queryForList("SELECT SUM(`amount`) AS _SUM FROM `alipay_withdraw` WHERE `create_time`>=? AND `create_time`<=? AND uid=?", todayBeg, todayEnd, uid);
		if(datas.size() > 0){
			a2 = Helpers.convert2Double(datas.get(0).get("_SUM"));
		}
		logger.info("a2=>" + a2);
		
		//加起来是日提现总金额
		return a1 + a2;
	}

	/**
	 * 查询所有未提现成功的提现单
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public List<MemberTakeMoney> queryMemberTakeMoneyByNotSuccess(Map<String, Object> condition) {
		return this.memberTakeMoneyDao.queryMemberTakeMoneyByNotSuccess(condition);
	}

	/**
	 * 根据打款ID查找提现单
	 * 
	 * @param dkOrderId
	 *            打款ID
	 * @return
	 */
	@Override
	public MemberTakeMoney findByDKOrderId(String dkOrderId) {
		List<MemberTakeMoney> list = this.memberTakeMoneyDaoImpl.findByProperty(MemberTakeMoney.class, "transaction_id", dkOrderId);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public int addLog(MemberTakeMoneyLog log) {
		if (null == log){
			return 1;
		}
		String sql = " insert into `member_take_money_log` (`operator_uid`, `order_id`, `ret_code`, `r1_code`, `bank_status`, `error_msg`, `create_time`, `remark`,`take_money_id`) values(?,?,?,?,?,?,?,?,?)";
		int i = jdbcOperations.update(sql, log.getOperatorUid(), log.getOrderId(), log.getRetCode(), log.getR1Code(),
				log.getBankStatus(), log.getErrorMsg(), log.getCreateTime(), log.getRemark(), log.getTakeMoneyId());
		i = i ^ 1;
		return i;
	}

	@Override
	public PageInfo<MemberTakeMoneyLog> queryTakeMoneyrLogList(Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order) {
		return memberTakeMoneyLogDao.findLogWithPage(condition, pageSize, pageNo, sort, order);
	}

	/**
	 * 转账成功
	 * 
	 * **/
	@Override
	public int transferSuccessNotify(String tid) {
		return memberTakeMoneyDao.updateTransferStatus(tid, 5);
	}

	/**
	 * 转账失败
	 * 
	 * **/
	@Override
	public int transferFailureNotify(String tid) {
		MemberTakeMoney memberTakeMoney = memberTakeMoneyDao.findBytid(tid);
		try{
			int i = memberTakeMoneyDao.updateTransferStatus(tid, 6);
			if(i == 1){
				MemberTakeMoneyLog mtlog = new MemberTakeMoneyLog();
				mtlog.setCreateTime(new Date());
				mtlog.setOperatorUid("--");
				mtlog.setRemark("用户[" + memberTakeMoney.getUid() + "]提现失败,提现单号:" + memberTakeMoney.getId() + ",转账单号:" + memberTakeMoney.getTransactionId() + ",提款金额:" + memberTakeMoney.getAmount());
				mtlog.setTakeMoneyId(memberTakeMoney.getId());
				addLog(mtlog);
			}
			int j = memberAccountService.returnTakeMoney(memberTakeMoney.getUid(), memberTakeMoney.getAmount() - memberTakeMoney.getFee(), memberTakeMoney.getTransactionId(), "用户[" + memberTakeMoney.getUid() + "]请求退款,提现单号:" + memberTakeMoney.getId() + ",转账单号:" + memberTakeMoney.getTransactionId() + ",退款金额:" + (memberTakeMoney.getAmount() - memberTakeMoney.getFee()));
			if(j == 0){
				memberTakeMoneyDao.updateTransferStatus(tid, 7);
				MemberTakeMoneyLog mtlog = new MemberTakeMoneyLog();
				mtlog.setCreateTime(new Date());
				mtlog.setOperatorUid("--");
				mtlog.setRemark("用户[" + memberTakeMoney.getUid() + "]退款成功,提现单号:" + memberTakeMoney.getId() + ",转账单号:" + memberTakeMoney.getTransactionId() + ",退款款金额:" + (memberTakeMoney.getAmount() - memberTakeMoney.getFee()));
				mtlog.setTakeMoneyId(memberTakeMoney.getId());
				addLog(mtlog);
			}
		}catch (Exception e) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean findTakeMoneyLog(int uid) {
		return this.memberTakeMoneyDao.findTakeMoneyLog(uid);
	}

	@Override
	public int updateTakeMoneyStatusByorderId(String orderId, int status, String ramark) {
		String sql = "update member_take_money set status = ?,remark = ? where transaction_id = ?";
		return jdbcOperations.update(sql, status, ramark,orderId);
	}

	@Override
	public boolean addTakeMoney4Alipay(int uid, int recv_uid, double fee, double amount, String ali_account, String ali_username, String payment_id) {
		D db = new D("alipay_withdraw", jdbcOperations);
		long r = db.add(new XProperty[]{
				new XProperty("payment_id", payment_id),
				new XProperty("uid", uid),
				new XProperty("recv_uid", recv_uid),
				new XProperty("alipay_account", ali_account),
				new XProperty("alipay_username", ali_username),
				new XProperty("total_amount", amount), //扣掉手续费
				new XProperty("amount", amount-fee), //扣掉手续费
				new XProperty("create_time", Helpers.now()),
				new XProperty("status", 0),
				new XProperty("remark", ""),
		});
		logger.info(String.format("addTakeMoney4Alipay.sql: %s", db.last_query()));
		
		return r != -1;
	}

	@Override
	public boolean returnMoney4Alipay(String paymentId) {
		D db = new D("alipay_withdraw", jdbcOperations);
		XObject obj = db.get(new XProperty("payment_id", paymentId));
		if(obj == null){
			logger.error("不存在的支付宝提现订单: " + paymentId);
			return false;
		}
		
		//不是订单失败的提现
		if(obj.getAsInt("status") != ALIPAY_WITHDRAW_TAKE_FAIL && obj.getAsInt("status") != ALIPAY_WITHDRAW_REQ_FAIL){
			logger.error("支付宝提现订单不是提现失败状态，status: " + obj.getAsInt("status"));
			return false;
		}
		
		double amount = obj.getAsDouble("total_amount");
		int uid = obj.getAsInt("uid");
		
		int recv_uid = obj.getAsInt("recv_uid");
		if(recv_uid <= 0){
			logger.error("recv_id<=0 where payment_id=" + paymentId);
			return false;
		}
		
		int r = memberAccountService.transfer(String.valueOf(recv_uid), String.valueOf(uid), String.valueOf(MemberAccountDao.ACCOUNT_TYPE_CASH), String.valueOf(amount), "支付宝提现失败，订单号为：" + paymentId + "，退款" + amount + "给用户:" + uid);
//		int r = memberAccountService.alipayTransfer(recv_uid, uid, MemberAccountDao.ACCOUNT_TYPE_CASH, amount, paymentId, 0, "none", "支付宝提现失败，退款给用户");
//		int r = memberAccountService.returnMoney(uid, MemberAccountDao.ACCOUNT_TYPE_CASH, paymentId, amount, "支付宝提现失败，退款给用户");
		if(r != 0){
			logger.error(String.format("退款给用户失败，uid:%d, amount:%.2f, paymentId:%s", uid, amount, paymentId));
			return false;
		}
		
		db.update(new XProperty[]{
				new XProperty("status", ALIPAY_WITHDRAW_RETURN),
				new XProperty("remark", obj.get("remark") + " | 退款时间:" + Helpers.now())
		}, new XProperty[]{
				new XProperty("payment_id", paymentId)
		});
		
		return true;
	}
	
	
}
