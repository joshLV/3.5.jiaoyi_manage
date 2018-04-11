/**
 * 
 */
package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberStoreDao;
import cn.juhaowan.member.vo.MemberPayCostList;
import cn.juhaowan.member.vo.MemberStore;
import cn.juhaowan.member.vo.MemberStoreOrder;

/**
 * 店铺管理
 */
@Repository("MemberStoreDao")
public class MemberStoreDaoImpl implements MemberStoreDao {

	@Autowired
	JdbcOperations jdbcOperations;

	/*
	 * 添加店铺信息
	 */
	@Override
	public int addMemberStore(MemberStore memberStore) {
		String sql = "INSERT INTO `member_store`(uid,`verify_status`,`store_status`,`begin_time`,`end_time`,"
				+ "`create_time`,`verify_time`,`deal_year_number`,`deal_year_total`,`modify_time`,`deal_final_time`,`remark`)"
				+ "VALUES (?,?,?,?,?,now(),?,?,?,now(),?,?)";
		int i = jdbcOperations.update(sql, memberStore.getUid(),MemberStoreDao.VERIFYSTATUS_PENDING,MemberStoreDao.STORESTATUS_TEMP,
				memberStore.getBeginTime(),memberStore.getEndTime(), memberStore.getVerifyTime(), memberStore.getDealYearNumber(),
				memberStore.getDealYearTotal(), memberStore.getDealFinalTime(), memberStore.getRemark());

		return i ^ 1;
	}

	/*
	 * 根据uid查询 店铺信息
	 */
	@Override
	public MemberStore queryByUid(int uid) {
		MemberStore m = null;
		String sql = "select * from member_store where uid = ? ";
		JuRowCallbackHandler<MemberStore> rb = new JuRowCallbackHandler<MemberStore>(MemberStore.class);
		jdbcOperations.query(sql, rb, uid);
		if (rb.getList().size() > 0) {
			m = rb.getList().get(0);
		}
		return m;
	}

	/*
	 * 按条件查询店铺列表
	 */
	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberStore> queryPage(Map<String, Object> conditions,  int pageNo,int pageSize, String sort,
			String order) {
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "create_time";
		}
		if (null == order) {
			order = "asc";
		}

		sql.append("from member_store where 1 = 1 ");
		//查询条件
		if(null != conditions){
			if(null != conditions.get("storeStatus")){
				sql.append(" and store_status = ?");
				conList.add(conditions.get("storeStatus"));
			}
			if(null != conditions.get("verifyStatus")){
				sql.append(" and verify_status = ?");
				conList.add(conditions.get("verifyStatus"));
			}
			if(null != conditions.get("uid")){
				sql.append(" and uid = ? ");
				conList.add(conditions.get("uid"));
			}
			
		}
		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<MemberStore> pageinfo = new PageInfo<MemberStore>(count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<MemberStore> cb = new JuRowCallbackHandler<MemberStore>(MemberStore.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	/*
	 * 根据店铺状态 查询店铺列表
	 */
	@Override
	public List<MemberStore> queryListByStoreStatus(int storeStatus) {
		List<MemberStore> list = new ArrayList<MemberStore>();
		String sql = "select * from member_store where store_status = ? ";
		JuRowCallbackHandler<MemberStore> rb = new JuRowCallbackHandler<MemberStore>(MemberStore.class);
		jdbcOperations.query(sql,rb,storeStatus);
		if(rb.getList().size() > 0){
			list = rb.getList();
		}
		return list;
	}

	/*
	 * 更新店铺信息
	 */
	@Override
	public int updateMemberStore(MemberStore memberStore) {
	   String sql = "UPDATE `member_store` SET `verify_status` = ?,`store_status` = ?,`begin_time` = ?,`end_time` = ?,"
                  + " `deal_year_number` = ?, `deal_year_total` = ?,`modify_time` = now(),"
                  + " `deal_final_time` = ? ,`remark` = ? WHERE `uid` = ? ;";
	    int i = jdbcOperations.update(sql,memberStore.getVerifyStatus(),memberStore.getStoreStatus(),memberStore.getBeginTime(),
	    		memberStore.getEndTime(),memberStore.getDealYearNumber(),memberStore.getDealYearTotal(),
	    		memberStore.getDealFinalTime(),memberStore.getRemark(),memberStore.getUid());
	    
		return i ^ 1;
	}

	/*
	 * 更新店铺状态
	 */
	@Override
	public int updateStoreStatus(int uid, int storeStatus) {
		String sql = "update member_store set store_status = ? where uid = ? ";
		int i = jdbcOperations.update(sql,storeStatus,uid);
		return i ^ 1;
	}

	/*
	 * 更新店铺审核状态
	 */
	@Override
	public int updateVerifyStatus(int uid, int verifyStatus) {
		String sql = "update member_store set verify_status = ? ,verify_time = now() where uid = ? ";
		int i = jdbcOperations.update(sql, verifyStatus,uid);
		return i ^ 1;
	}

	/*
	 * 更新月成交信息
	 */
	@Override
	public int updateDealInfo(int uid, int yearDealNumber, double yearDealTotal, Date dealFinalTime) {
		String sql = "update member_store set deal_year_number = ? ,deal_year_total = ? , deal_final_time = ? where uid = ? ";
		int i = jdbcOperations.update(sql,yearDealNumber,yearDealTotal,dealFinalTime,uid);
		return i ^ 1;
	}

	/*
	 * 正常续费
	 */
	@Override
	public int renew(int uid, int openTime) {
		String sql = "UPDATE member_store SET end_time = ( DATE_ADD(end_time,INTERVAL ? MONTH)) WHERE uid = ?";
		int i = jdbcOperations.update(sql,openTime,uid);
		
		return i ^ 1;

	}


	/*
	 * 过期续费/开通店铺
	 */
	@Override
	public int outDateRenew(int uid, int openTime) {
		String sql = "UPDATE member_store SET begin_time = now(), end_time = ( DATE_ADD(now(),INTERVAL ? MONTH)) WHERE uid = ?";
		int i = jdbcOperations.update(sql,openTime,uid);
		return i ^ 1;
	}
	
	
	/*
	 * 用户店铺是否已经存在
	 */
	@Override
	public boolean userIsExist(int uid) {
		String sql = "select * from member_store where uid = ? ";
		JuRowCallbackHandler<MemberStore> rb = new JuRowCallbackHandler<MemberStore>(MemberStore.class);
		jdbcOperations.query(sql,rb,uid);
		if(rb.getList().size() > 0){
			return true;
		}
		return false;
	}
	
	
	/*
	 * 用户申请店铺临时表
	 */


	@Override
	public int addMemberStoreOrder(MemberStoreOrder memberStoreOrder) {
		String sql = "INSERT INTO `member_store_order`(`pay_type`, `uid`, `amount`,`opening_time`,status,`payCost_time`)"
				+ "VALUES (?,?,?,?,?,now())";
		int i = jdbcOperations.update(sql, memberStoreOrder.getPaytype(), memberStoreOrder.getUid(),
				memberStoreOrder.getAmount(), memberStoreOrder.getOpeningtime(), memberStoreOrder.getStatus());
		return i ^ 1;
	}

	@Override
	public int addPayCost(int payType, int uid, Double amount, Integer openingTime) {
		String sql = "INSERT INTO `member_store_paycostlist`(`pay_type`, `uid`, `amount`,`opening_time`,`payCost_time`)"
				+ "VALUES (?,?,?,?,now())";
		int i = jdbcOperations.update(sql, payType, uid, amount, openingTime);
		return i ^ 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberPayCostList> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize,
			String sort, String order) {
		PageInfo<MemberPayCostList> pageInfo = new PageInfo<MemberPayCostList>();
		List<Object> conList = new ArrayList<Object>();
		// 默认值
		if (null == sort || ("").equals(sort)) {
			sort = "paycost_time";
		}
		if (null == order) {
			order = "DESC";
		}

		StringBuffer sqlBody = new StringBuffer(" from member_store_paycostlist where 1=1");
		if (null != conditions) {
			if (null != conditions.get("uid")) {
				sqlBody.append(" and uid=?");
				conList.add(conditions.get("uid"));
			}
			if (null != conditions.get("payType")) {
				sqlBody.append(" and pay_type=?");
				conList.add(conditions.get("payType"));
			}
		}
		String sqlCount = "select count(*) " + sqlBody.toString();
		int count = jdbcOperations.queryForInt(sqlCount, conList.toArray());

		if (count == 0) {
			return pageInfo;
		}

		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		pageInfo.setPageSize(pageSize);
		int index = pageInfo.getStartNum(pageNo);

		String sql = "select * " + sqlBody.toString() + " order by " + sort + " " + order + " limit " + index + ","
				+ pageSize;
		JuRowCallbackHandler<MemberPayCostList> juRowCallbackHandler = new JuRowCallbackHandler<MemberPayCostList>(
				MemberPayCostList.class);
		try {
			jdbcOperations.query(sql, juRowCallbackHandler, conList.toArray());
			pageInfo.setItems(juRowCallbackHandler.getList());
		} catch (Exception e) {
			System.err.println(e);
		}
		return pageInfo;
		// String sql = "from member_store_paycostlist";
		// String sqlcount = "select count(*) " + sql;
		// int rowcount = jdbcOperations.queryForInt(sqlcount);
		// PageInfo<MemberPayCostList> mPageInfo = new PageInfo<MemberPayCostList>("", rowcount, pageSize);
		// String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		// JuRowCallbackHandler<MemberPayCostList> callback = new JuRowCallbackHandler<MemberPayCostList>(
		// MemberPayCostList.class);
		// int startNum = 0;
		// if (pageNo <= 0) {
		// startNum = pageNo * pageSize;
		// } else {
		// startNum = (pageNo - 1) * pageSize;
		// }
		// jdbcOperations.query(sqlQuery, callback, startNum, pageSize);
		// mPageInfo.setItems(callback.getList());
		// return mPageInfo;
	}

	@Override
	public List<MemberPayCostList> queryListByUid(int uid) {
		String sqlQuery = "select * from member_store_paycostlist where uid=?";
		JuRowCallbackHandler<MemberPayCostList> callback = new JuRowCallbackHandler<MemberPayCostList>(
				MemberPayCostList.class);
		jdbcOperations.query(sqlQuery, callback, uid);
		List<MemberPayCostList> returnList = callback.getList();
		return returnList;
	}

	@Override
	public MemberStoreOrder queryStoreOrderById(int id) {
		MemberStoreOrder o = null;
		String sql = "select * from member_store_order where id = ? ";
		JuRowCallbackHandler<MemberStoreOrder> rb = new JuRowCallbackHandler<MemberStoreOrder>(MemberStoreOrder.class);
		jdbcOperations.query(sql, rb, id);
		if (rb.getList().size() > 0) {
			o = rb.getList().get(0);
		}
		return o;
	}

	@Override
	public int updateStoreOrder(int id, int status) {
		String sql = "UPDATE member_store_order SET paySuc_time = now(), status = ? WHERE id = ?";
		int i = jdbcOperations.update(sql,status,id);
		return i ^ 1;
	}

	@Override
	public MemberPayCostList qeuryByIdAndType(int uid, int type) {
		MemberPayCostList p = null;
		String sql = "select * from member_store_paycostlist where uid = ? and pay_type = ? order by payCost_time desc";
		JuRowCallbackHandler<MemberPayCostList> rb = new JuRowCallbackHandler<MemberPayCostList>(MemberPayCostList.class);
		jdbcOperations.query(sql, rb, uid,type);
		if (rb.getList().size() > 0) {
			p = rb.getList().get(0);
		}
		return p;
	}

	@Override
	public void rollBackUpdateVerifyStatus(int uid) {
        String sql = "UPDATE member_store SET begin_time = null, end_time = null ,verify_status = ? WHERE uid = ?";
        jdbcOperations.update(sql, MemberStoreDao.VERIFYSTATUS_PENDING,uid);
	}
	
	

}
