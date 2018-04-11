/**
 * 
 * @author lithium<lijie@jugame.cn>
 * @date   2013-05-10
 * @desc   只是现金账户， 实现类
 */

package cn.juhaowan.member.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.juhaowan.member.dao.MemberCash48hRechargeDao;

/**
 * 
 * @author lithium
 *
 */

@Repository("MemberCash48hRechargeDao")
public class MemberCash48hRechargeDaoImpl implements MemberCash48hRechargeDao {
	Logger logger = LoggerFactory.getLogger(MemberAccountDaoImpl.class);
	@Autowired
	private JdbcOperations jdbcOperations;
	
	
	/**
	 * 读取存储的表名
	 * @param uid
	 * @return  表名
	 */
	public String getTableName() {
		return "member_cash_48h_recharge";
	}
	
	
	/**
	 * 查询充值现金数额
	 * @param uid 用户id
	 * @return 用户充值的数额
	 */
	@Override
	public double queryAmount(int uid) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sql = "SELECT SUM(amount) FROM " + this.getTableName() 
				+ " WHERE uid = ? AND create_time > '" 
				+ dateFormat.format(new Date(new Date().getTime() - MemberCash48hRechargeDao.DAYS_LIMIT * 24 * 3600 * 1000)) 
				+ "'";

		logger.info("sql:" + sql +  ", uid:" + uid);
		
		//计算当前时间2天内的金额
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}
	
	/**
	 * 查询充值现金数额
	 * @param uid 用户ID
	 * @param date 查询的日期
	 * @return  用户充值的数额
	 */
	@Override
	public double queryAmount(int uid, java.util.Date date) {	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		String sql = "SELECT SUM(amount) FROM " + this.getTableName() 
				+ " WHERE uid = ? AND create_time > '" 
				+ dateFormat.format(new Date(new Date().getTime() -  MemberCash48hRechargeDao.DAYS_LIMIT * 24 * 3600 * 1000)) 
				+ "'";

		
		logger.info("sql:" + sql + ", uid:" + uid);
			
		//使用指定的时间，计算当前时间2天内的金额
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			return rs.getDouble(1);
		}
		return 0;
	}
	
	/**
	 * 插入新的充值记录
	 * @param uid 用户ID
	 * @param amount 数量
	 * @param type 0为现金充值， 1为交易收入
	 * @return 0成功, 其他值为失败
	 */
	@Override
	public int recharge(int uid, double amount, int type) {
		String sql = "INSERT INTO " + this.getTableName() + "(uid, amount, type, create_time) VALUES(?, ?, ?, now())";
		int result =  jdbcOperations.update(sql, uid ,amount, type);
		
		return result > 0 ? 0 : 1;
	}

}
