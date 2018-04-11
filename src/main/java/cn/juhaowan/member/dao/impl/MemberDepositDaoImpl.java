package cn.juhaowan.member.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.member.dao.MemberDepositDao;
import cn.juhaowan.member.vo.MemberDeposit;

@Repository("MemberDepositDao")
public class MemberDepositDaoImpl implements MemberDepositDao{
	@Autowired
    protected JdbcOperations jdbcOperations; 
	
	Logger logger = LoggerFactory.getLogger(MemberDepositDaoImpl.class);
	
	public String getTableName(int uid ){ 
		//暂时不分表
		return "member_deposit";
	} 
	
	@Override
	public double queryBalance(int uid ,int deposit_type){
		String sql = "select deposit_amount from "+getTableName(uid)+" where uid = ? and deposit_type = ? ";
		logger.info(sql);
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid ,deposit_type);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int add(int uid, int deposit_type, double amount) {
		//判断是否存在账户
		String sql = "select count(*) from "+getTableName(uid)+" where uid = ? and deposit_type =? ";
		int result = 0 ;
		if (jdbcOperations.queryForInt(sql,uid,deposit_type)==0){
			//创建账户
			sql = "insert into "+getTableName(uid)+"(uid,deposit_type,deposit_amount,create_time,last_time)";
			sql += " values(?,?,?,now(),now())";
			result = jdbcOperations.update(sql, uid,deposit_type,amount);
		}else{
			sql = "update " + getTableName(uid) + " set deposit_amount = deposit_amount + ?  where uid = ? and deposit_type =? ";
			result = jdbcOperations.update(sql, amount, uid,deposit_type);
		}
		return result>0 ? 0 : 1;
	}

	@Override
	public int sub(int uid, int deposit_type, double amount) {
		String sql = "update "+getTableName(uid)+" set deposit_amount = deposit_amount - ? where uid = ? and deposit_type =? ";
		int x = jdbcOperations.update(sql,amount,uid,deposit_type);
		return x > 0 ? 0 : 1;
	}
	
	@Override
	public List<MemberDeposit> queryDepositList(int uid){
		String sql = "select * from " +getTableName(uid)+" where uid = ?";
		JuRowCallbackHandler<MemberDeposit> callbackHandler = new JuRowCallbackHandler<>(MemberDeposit.class);
		jdbcOperations.query(sql, callbackHandler,uid);
		return callbackHandler.getList();
		
	}
}
