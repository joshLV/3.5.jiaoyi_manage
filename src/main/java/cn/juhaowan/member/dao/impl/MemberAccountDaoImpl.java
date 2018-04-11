package cn.juhaowan.member.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.juhaowan.member.dao.MemberAccountDao;

/**
 * 资金账户操作类
 * @author cai9911
 *
 */
@Repository("MemberAccountDao")
public class MemberAccountDaoImpl implements MemberAccountDao {
	Logger logger = LoggerFactory.getLogger(MemberAccountDaoImpl.class);
	@Autowired
	private JdbcOperations jdbcOperations;
	
	/**
	 * 读取存储的表名
	 * @param uid
	 * @return
	 */
	public String getTableName(int uid){
		return "member_account";
	}
	
	@Override
	public double queryCash(int uid) {
		String sql = "select balance from " + getTableName(uid) + " where uid = ? and account_type = ?";
		//logger.info(sql);
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid ,MemberAccountDao.ACCOUNT_TYPE_CASH);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}

	@Override
	public double queryBalance(int uid) {
		String sql = "select sum(balance) from " + getTableName(uid) + " where uid = ? and (account_type = ? or account_type = ?)";
		//logger.info(sql);
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid ,MemberAccountDao.ACCOUNT_TYPE_CASH,MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}
	
	@Override
	public double queryBalance(int uid, int accountType){
		String sql = "select balance from " + getTableName(uid) + " where uid = ? and account_type = ?";
		//logger.info(sql);
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid ,accountType);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int add(int uid, int accountType, double amount){
		//判断是否存在账户
		String sql = "select count(*) from " + getTableName(uid) + " where uid = ? and account_type =? ";
		int result = 0;
		if (jdbcOperations.queryForInt(sql,uid,accountType) == 0){
			//创建账户
			sql = "insert into " + getTableName(uid) + "(uid,account_type,balance,create_time,last_time)";
			sql += " values(?,?,?,now(),now())";
			result = jdbcOperations.update(sql, uid,accountType,amount);
		} else {
			sql = "update " + getTableName(uid) + " set balance = balance + ? , last_time=now() where uid = ? and account_type =? ";
			result = jdbcOperations.update(sql, amount, uid,accountType);
		}
		return result > 0 ? 0 : 1;
	}
	
	
	@Override
	public int cut(int uid, int accountType, double amount) {
//		String sql = "update " + getTableName(uid) + " set balance = balance - ? , last_time=now() where uid = ? and account_type =?";
		String sql = "update " + getTableName(uid) + " set balance = balance - ? , last_time=now() where uid = ? and account_type =? and balance >= ?";
		int x = jdbcOperations.update(sql, amount, uid, accountType, amount);
		return x > 0 ? 0 : 1;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public int createAccount(int uid) {
		int rtn1 = 1;
		int rtn2 = 1;

		String sqlTemplate = "select count(*) from " + getTableName(uid) + " where uid = ? and account_type = ? ";
		
		//判断是否存在账户
		if(jdbcOperations.queryForInt(sqlTemplate, uid, MemberAccountDao.ACCOUNT_TYPE_CASH) == 0) {
			//创建现金账户
			String sql1 = "INSERT INTO " + getTableName(uid) + "(uid,account_type,balance,create_time,last_time)  VALUES(?, ?, 0, now(), now()) "
					+ " ON DUPLICATE KEY UPDATE last_time = now()";
			rtn1 = jdbcOperations.update(sql1, uid, MemberAccountDao.ACCOUNT_TYPE_CASH);
		}
		
		if(jdbcOperations.queryForInt(sqlTemplate, uid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) == 0) {
			//创建非现金账户
			String sql2 = "INSERT INTO " + getTableName(uid) + "(uid,account_type,balance,create_time,last_time)  VALUES(?, ?, 0, now(), now())"
					+ " ON DUPLICATE KEY UPDATE last_time = now()";
			rtn2 = jdbcOperations.update(sql2, uid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
		}
		
		return (rtn1 & rtn2) > 0 ? 0 : 1;
	}

	@Override
	public int addZhtAsyncReg(int uid, String authStatus, String userName, String idCardNo) {
		String sql = "INSERT INTO member_zht_reg(uid, auth_status, user_name, id_card_no, status, create_time, update_time) VALUES(?, ?, ?, ?, ?, now(), now())"; 
		int rtn = jdbcOperations.update(sql, uid, authStatus, userName, idCardNo, 0); //0是状态没处理
		return rtn > 0 ? 0 : 1;
	}

}
