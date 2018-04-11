package cn.juhaowan.member.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.member.dao.MemberDepositLogDao;
import cn.juhaowan.member.vo.MemberDepositLog;

/**
 * 押金操作类
 * @author cai9911
 *
 */
@Repository("MemberDepositLogDao")
public class MemberDepositLogDaoImpl extends BaseDaoImplJdbc<MemberDepositLog> implements MemberDepositLogDao{
	
	public String getTableName(){ 
		//实现分表逻辑,或者分两个表
		return "member_deposit_log";
	}
	
	@Override
	public void addLog(int uid ,int depositType ,int operatroType,double amount,double balance,String remark){
		MemberDepositLog log = new MemberDepositLog();
		log.setOperatroType(MemberDepositLogDao.OPERATOR_TYPE_ADD);
		log.setDepositType(depositType);
		log.setCreateTime(new Date());
		log.setRemark(remark);
		log.setUid(uid);
		log.setAmount(amount);
		this.insert(log);
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public int queryLogCount(int uid,String sqlCondition,Object... sqlParam){
		String sql = "select count(*) from " + getTableName() + " where " + sqlCondition;
		
		int x = jdbcTemplate.queryForInt(sql);
		
		return x;
	}

	@Override
	public List<MemberDepositLog> queryLogList(int uid, String sqlCondition,
			int fistResult, int maxResult, Object... sqlParam) {
		String sql = "select * from " + getTableName() + " where " + sqlCondition + " order by id desc limit " + fistResult + "," + maxResult;
		JuRowCallbackHandler<MemberDepositLog> callback = new JuRowCallbackHandler<>(MemberDepositLog.class);
		jdbcTemplate.query(sql, callback,sqlParam);
		
		return callback.getList();
	}
	 
 

}
