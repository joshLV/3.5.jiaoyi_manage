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
import cn.juhaowan.member.dao.MemberTakeMoneyDao;
import cn.juhaowan.member.vo.MemberTakeMoney;

/**
 * 提现dao接口实现
 * **/
@Repository("MemberTakeMoneydao")
public class MemberTakeMoneyDaoImpl extends BaseDaoImplJdbc<MemberTakeMoney> implements MemberTakeMoneyDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	/**
	 * 添加提现申请 1 成功 0 失败
	 * */
	@Override
	public int addTakeMoney(int uid, double fee, double amount, int type, String bankID, String bankCardNum,
			String bankAddr, String provinces, String realName, String orderId) {

		int i = 0;
		String sql = " insert into member_take_money(uid,fee,amount,take_money_type,create_time,status,bank_id,bank_card_num,bank_addr,provinces_code,real_name,transaction_id,transfer_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		i = jdbcOperations.update(sql, uid, fee, amount, type, new Date(), 3, bankID, bankCardNum, bankAddr, provinces,
				realName, orderId,new Date()); // 0表示未审核 
		return i;
	}

	/**
	 * 审核申请 0:未审核 1:审核通过 2:审核不通过 3:转账成功 4:提现失败
	 */
	@Override
	public int updateVerifyStauts(int takeid, int verifyStatus, String remark) {
		int i = 0;
		if (StringUtils.isBlank(remark)){
			return i;
		}
		String sql = " update member_take_money set status=?, remark=?, verify_time=now() where id = ? ";
		i = jdbcOperations.update(sql, verifyStatus, remark, takeid);
		return i;
	}

	/**
	 * 审核状态查询
	 * 
	 * */
	@SuppressWarnings("deprecation")
	public int findVerifyStatus(int takeid) {
		int i = 0;
		String sql = "select status from member_take_money where id=?";
		i = jdbcOperations.queryForInt(sql, takeid);
		return i;
	}

	/**
	 * 转账 1 成功 0 失败
	 */
	@Override
	public int updateTransferStatus(int takeid) {
		int i = 0;
		String sql = " update member_take_money set status=? where id = ? ";
		i = jdbcOperations.update(sql, 3, takeid);// 3表示转账成功
		return i;
	}

	/**
	 * 转账状态通知 1 成功 0 失败
	 * */
	@Override
	public int updateNotifyStatus(String transactionId, int status, int takeid) {
		int i = 0;
		StringBuffer sql = new StringBuffer("update member_take_money set transaction_id=?,status=?");
		if (status == 3) {
			sql.append(",transfer_time=now() where id=?");
			i = jdbcOperations.update(sql.toString(), transactionId, status, takeid);
		} else {
			sql.append(" where id=?");
			i = jdbcOperations.update(sql.toString(), transactionId, status, takeid);
		}
		return i;
	}

	/**
	 * 查询列表
	 * */
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberTakeMoney> queryTakeMoneyrList(Map<String, Object> condition, int pageSize, int pageNo, String sort,
			String order) {

		PageInfo<MemberTakeMoney> pageInfo = new PageInfo<MemberTakeMoney>();
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sqlBody = new StringBuffer("from member_take_money where 1=1");

		if (StringUtils.isBlank(sort)){
			sort = "id";
		}
		if (StringUtils.isBlank(order)){
			order = "asc";
		}

		if (condition != null) {
			if (null != condition.get("id")) {
				sqlBody.append(" and id=?");
				conList.add(condition.get("id"));
			}
			if (null != condition.get("uid")) {
				sqlBody.append(" and uid=?");
				conList.add(condition.get("uid"));
			}
			if (null != condition.get("status")) {
				sqlBody.append(" and status=?");
				conList.add(condition.get("status"));
			}

			if (null != condition.get("take_money_type")) {
				sqlBody.append(" and take_money_type=?");
				conList.add(condition.get("take_money_type"));
			}

			if (null != condition.get("beginTime")) {
				sqlBody.append(" and create_time >= ? ");
				conList.add(condition.get("beginTime"));
			}

			if (null != condition.get("endTime")) {
				sqlBody.append(" and create_time <= ? ");
				conList.add(condition.get("endTime"));
			}
		}

		String sqlCount = "select count(*) " + sqlBody.toString();

		int count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		if (count == 0){
			return pageInfo;
		}

		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		pageInfo.setPageSize(pageSize);
		int index = pageInfo.getStartNum(pageNo);

		String sql = "select * " + sqlBody.toString() + " order by " + sort + " " + order + " limit " + index + "," + pageSize;
		JuRowCallbackHandler<MemberTakeMoney> juRowCallbackHandler = new JuRowCallbackHandler<MemberTakeMoney>(
				MemberTakeMoney.class);

		jdbcOperations.query(sql, juRowCallbackHandler, conList.toArray());
		pageInfo.setItems(juRowCallbackHandler.getList());
		return pageInfo;
	}

	/**
	 * 查询列表(后台)
	 * */
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberTakeMoney> queryTakeMoneyrPage(Map<String, Object> conditions, int pageSize, int pageNo, String sort,
			String order) {

		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "uid";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from member_take_money where 1 = 1 ");

		if (null != conditions) {
			if (null != conditions.get("transactionId")) {
				sql.append(" and transaction_id = ? ");
				conList.add(conditions.get("transactionId"));
			}
			if (null != conditions.get("uid")) {
				sql.append(" and uid = ? ");
				conList.add(conditions.get("uid"));
			}
			if (null != conditions.get("status")) {
				sql.append(" and status = ? ");
				conList.add(conditions.get("status"));
			}

			if (null != conditions.get("beginTime") && null == conditions.get("endTime")) {
				sql.append(" and create_time > date_format( ? ,'%Y-%m-%d %H:%i:%s') ");
				conList.add(conditions.get("beginTime"));
			}
			if (null != conditions.get("endTime") && null == conditions.get("beginTime")) {
				sql.append(" and create_time <  date_format( ? ,'%Y-%m-%d %H:%i:%s') ");
				conList.add(conditions.get("endTime"));
			}
			if (null != conditions.get("beginTime") && null != conditions.get("endTime")) {
				sql.append(" and create_time between  ?  and  ?");
				conList.add(conditions.get("beginTime"));
				conList.add(conditions.get("endTime"));
			}

		}

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<MemberTakeMoney> pageinfo = new PageInfo<MemberTakeMoney>(count, pageSize);
		if (count == 0){
			return pageinfo;
		}

		pageinfo.setPageno(pageNo);

		if (pageNo <= 0){
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()){
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<MemberTakeMoney> cb = new JuRowCallbackHandler<MemberTakeMoney>(MemberTakeMoney.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@Override
	public MemberTakeMoney findByid(int id) {
		return findById(MemberTakeMoney.class, id);
	}

	@Override
	public MemberTakeMoney findBytid(String tid) {
		MemberTakeMoney memberTakeMoney = null;
		String sql = " select * from member_take_money where transaction_id = ? ";
		JuRowCallbackHandler<MemberTakeMoney> mrch = new JuRowCallbackHandler<MemberTakeMoney>(MemberTakeMoney.class);
		jdbcOperations.query(sql, mrch, tid);
		List<MemberTakeMoney> mtmoney = mrch.getList();
		if (mtmoney.size() > 0){
			memberTakeMoney = mtmoney.get(0);
		}
		return memberTakeMoney;
	}
	
	
	@Override
	public double findAmount(int uid) {
		String sql = "select SUM(amount) as am from member_take_money where DATEDIFF(create_time,NOW())=0 and uid=?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, uid);
		Object obj = map.get("am");
		if (null == obj){
			return 0;
		}
		return Double.parseDouble(obj.toString());
	}

	/**
	 * 查询所有己审核过的的提现单
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public List<MemberTakeMoney> queryMemberTakeMoneyByNotSuccess(Map<String, Object> condition) {
		List<MemberTakeMoney> list = null;
		String sql = "SELECT * FROM member_take_money WHERE STATUS=?";
		JuRowCallbackHandler<MemberTakeMoney> cb = new JuRowCallbackHandler<MemberTakeMoney>(MemberTakeMoney.class);
		this.jdbcTemplate.query(sql, cb, "1");
		if (cb != null) {
			list = new ArrayList<MemberTakeMoney>();
			list = cb.getList();
		}
		return list;
	}

	/**
	 * 修改提现状态
	 * **/
	@Override
	public int updateTransferStatus(String tid, int status) {
		String sql = "update member_take_money set status = ? where transaction_id = ?";
		return jdbcOperations.update(sql.toString(), status, tid);
	}

	/**
	    * 根据用户uid查询用户24小时之内有没有申请提现
	    * @param uid 用户uid
	    * @return true 用户24小时内有申请提现
	    * 		false 用户24小时内没有申请提现
	    * 
	    * **/
	@Override
	public boolean findTakeMoneyLog(int uid) {
		String sql = "SELECT uid FROM member_take_money WHERE UNIX_TIMESTAMP(create_time) > (UNIX_TIMESTAMP(NOW()) - 3600 * 24) AND uid = ?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid);
		if(list.size() < 1){
			return false;
		}
		return true;
	}
}
