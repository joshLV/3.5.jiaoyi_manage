package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberAccountLogDao;
import cn.juhaowan.member.vo.MemberAccountLog;


/**
 * 账户日志操作
 * @author cai9911
 *
 */
@Repository("MemberAccountLogDao")
public class MemberAccountLogDaoImpl extends BaseDaoImplJdbc<MemberAccountLog> implements MemberAccountLogDao  {

	@Autowired
	private JdbcOperations jdbcOperations;
	/**
	 * 获取表名
	 * @param uid
	 * @return
	 */
	public String getTableName(int uid){
		return "member_account_log";
		//不分表了，mysql带有分区功能		
//		String tmp = uid + "";
//		String tableExt = tmp.substring(tmp.length()-1);
//		return "member_account_log_" + tableExt;
	}
	
	/**
	 * 写日志
	 * @param uid
	 * @param order_id
	 * @param amount 		操作金额
	 * @param accountType 	操作类型
	 * @param bankId		充值渠道号/提现银行编号 
	 * @param bankCardNo	提现银行卡号
	 * @param cardNo		神州行卡号
	 * @param depositType
	 * @param operatorType
	 * @param balance 		账户余额
	 * @param totalBalance  总余额
	 * @param remark   备注
	 */
	@Override
	public void addLog(int uid,String orderId,double amount,double fee,int accountType,int operatorType,
			String bankId,String bankCardNo, String cardNo,int depositType,
			double balance,double totalBalance,String remark){
		MemberAccountLog log = new MemberAccountLog();
		log.setUid(uid); 
		log.setOrderId(orderId);
		log.setCreateTime(new Date());
		log.setAmount(amount);
		log.setFee(fee);
		log.setAccountType(accountType);
		log.setOperatorType(operatorType); 
		log.setBalance(balance); //当前操作账户余额
		log.setTotalBalance(totalBalance);//总余额 
		log.setRemark(remark);
		log.setBankId(bankId);
		log.setBankCardNo(bankCardNo);
		log.setCardNo(cardNo);
		log.setDepositType(depositType);
		
		//记录日志
		this.insert(log);
	}
	
	/**
	 * 写日志
	 * @param uid
	 * @param orderId
	 * @param amount
	 * @param accountType
	 * @param operatorType
	 * @param balance
	 * @param totalBalance
	 * @param remark
	 */
	@Override
	public void addLog(int uid,String orderId,double amount,int accountType,int operatorType,
			double balance,double totalBalance,String remark){
		this.addLog(uid, orderId, amount,0, accountType, operatorType, "", "", "", 0, balance, totalBalance, remark);
	}
	
	
	@Override
	public int queryLogCount(int uid,String sqlCondition,Object... sqlParam){
		String sql = "select count(*) from " + getTableName(uid) + " where " + sqlCondition;
		@SuppressWarnings("deprecation")
		int x = jdbcOperations.queryForInt(sql, sqlParam);
		return x;
	}
	
	@Override
	public List<MemberAccountLog> queryLogList(int uid, String sqlCondition,int fistResult,int maxResult,Object... sqlParam){
		String sql = "select * from " + getTableName(uid) + " where " + sqlCondition + " order by id desc limit " + fistResult + "," + maxResult;
		JuRowCallbackHandler<MemberAccountLog> callback = new JuRowCallbackHandler<>(MemberAccountLog.class);
		jdbcOperations.query(sql, callback, sqlParam);
		return callback.getList();
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberAccountLog> queryPageInfo(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order) {
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;
 
		if(null == sort){
			sort = "uid";
		}
		if(null == order){
			order = "asc";
		}
		sql.append("from member_account_log where 1 = 1 ");
		
		if(null != conditions){
			if(null != conditions.get("uid")){
				sql.append(" and uid = ? ");
				conList.add(conditions.get("uid"));
			}
			
			if(null != conditions.get("operator_type")){
				sql.append(" and operator_type = ? ");
				conList.add(conditions.get("operator_type"));
			}
	
			if(null != conditions.get("beginTime") && null == conditions.get("endTime")){
				sql.append(" and create_time > date_format( ? ,'%Y-%m-%d %H:%i:%s') ");
				conList.add(conditions.get("beginTime"));
			}
			if(null != conditions.get("endTime") && null == conditions.get("beginTime")){
				sql.append(" and create_time <  date_format( ? ,'%Y-%m-%d %H:%i:%s') ");
				conList.add(conditions.get("endTime"));
			}
			if(null != conditions.get("beginTime") && null != conditions.get("endTime")){
				sql.append(" and create_time between  ?  and  ?");
				conList.add(conditions.get("beginTime"));
				conList.add(conditions.get("endTime"));
			}
		}
		
		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
	     count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<MemberAccountLog> pageinfo = new PageInfo<MemberAccountLog>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		
		pageinfo.setPageno(pageNo);
		
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if(pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		
		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<MemberAccountLog> cb = new JuRowCallbackHandler<MemberAccountLog>(MemberAccountLog.class);
		
		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());  
		
		return pageinfo;
	}

	@Override
	public MemberAccountLog queryById(int id) {

		return findById(MemberAccountLog.class, id);
	}
}
