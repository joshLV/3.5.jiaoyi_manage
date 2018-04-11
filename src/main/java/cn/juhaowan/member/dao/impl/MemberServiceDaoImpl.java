/**
 * 
 */
package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberServiceDao;
import cn.juhaowan.member.vo.MemberOpenService;
import cn.juhaowan.member.vo.MemberServiceType;

/**
 * 会员服务管理
 * 
 */
@Repository("MemberServiceDao")
public class MemberServiceDaoImpl implements MemberServiceDao {
	@Autowired
	JdbcOperations jdbcOperations;

	@Override
	public int addServiceType(MemberServiceType serviceType) {
		String sql = "insert member_servicetype(name,weight,imgUrl,remark,status,create_time,service_code) values(?,?,?,?,?,now(),?)";
		int i = jdbcOperations.update(sql, serviceType.getName(), serviceType.getWeight(), serviceType.getImgurl(),
				serviceType.getRemark(), serviceType.getStatus(), serviceType.getServiceCode().toUpperCase());
		return i ^ 1;
	}

	@Override
	public int updateServiceType(MemberServiceType serviceType) {
		String sql = "update member_servicetype set name=?,weight=? ,imgUrl=? ,remark=? ,status=?,service_code=? where id=?";
		int i = jdbcOperations.update(sql, serviceType.getName(), serviceType.getWeight(), serviceType.getImgurl(),
				serviceType.getRemark(), serviceType.getStatus(), serviceType.getServiceCode(), serviceType.getId());
		return i ^ 1;
	}

	@Override
	public int deleteServiceType(int id, boolean isForce) {
		int effectRow = 0;
		String sqlcount = "select * from member_openservice where service_id=?";
		int count = jdbcOperations.queryForList(sqlcount, id).size();
		if (count > 0) {
			if (isForce) {
				String sql = "delete from member_openservice where service_id = ?";
				jdbcOperations.update(sql, id);
				sql = "delete from member_servicetype where id = ?";
				effectRow = jdbcOperations.update(sql, id);
			} else {
				return 0;
			}
		} else {
			String sql = "delete from member_servicetype where id = ?";
			effectRow = jdbcOperations.update(sql, id);
		}
		return effectRow;
	}

	@Override
	public int updateServiceTypeStatus(int id, int status) {
		String sql = "update member_servicetype set status=? where id=?";
		int i = jdbcOperations.update(sql, status, id);
		return i ^ 1;
	}

	@Override
	public int updateWeight(int id, int weight) {
		String sql = "update member_servicetype set weight=? where id=?";
		int i = jdbcOperations.update(sql, weight, id);
		return i ^ 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberServiceType> queryPageServiceType(Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		PageInfo<MemberServiceType> pageInfo = new PageInfo<MemberServiceType>();
		List<Object> conList = new ArrayList<Object>();
		// 默认值
		if (StringUtils.isBlank(sort)) {
			sort = "weight";
		}
		if (StringUtils.isBlank(order)) {
			order = "asc";
		}

		StringBuffer sqlBody = new StringBuffer(" from member_servicetype where 1=1");
		if (null != conditions) {
			if (null != conditions.get("name")) {
				sqlBody.append(" and name=?");
				conList.add(conditions.get("name"));
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
		JuRowCallbackHandler<MemberServiceType> juRowCallbackHandler = new JuRowCallbackHandler<MemberServiceType>(
				MemberServiceType.class);
		try {
			jdbcOperations.query(sql, juRowCallbackHandler, conList.toArray());
			pageInfo.setItems(juRowCallbackHandler.getList());
		} catch (Exception e) {
			System.err.println(e);
		}
		return pageInfo;
	}

	@Override
	public MemberServiceType queryByid(int id) {
		JuRowCallbackHandler<MemberServiceType> callback = new JuRowCallbackHandler<MemberServiceType>(
				MemberServiceType.class);
		String sql = "select * from member_servicetype where id=?";
		jdbcOperations.query(sql, callback, id);
		MemberServiceType mHelpInfo = null;
		if (callback.getList() != null && callback.getList().size() > 0) {
			mHelpInfo = callback.getList().get(0);
		}
		return mHelpInfo;
	}

	@Override
	public int addMemberServer(int uid, int[] osId) {
		int k = 1;
		try {
			jdbcOperations.update("delete from member_openservice where uid = " + uid);
			if (osId == null) {
				return 0;
			}
			
			for (int i = 0; i < osId.length; i++) {
				String sql = "insert member_openservice(uid,service_id,create_time) values(?,?,now())";
				k = jdbcOperations.update(sql, uid, osId[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k ^ 1;
	}

	@Override
	public int closeServer(int uid, int osId) {
		String sql = "delete from member_openservice where uid=? and service_id=?";
		int i = jdbcOperations.update(sql, uid, osId);
		return i ^ 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MemberOpenService> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize,
			String sort, String order) {
		PageInfo<MemberOpenService> pageInfo = new PageInfo<MemberOpenService>();
		List<Object> conList = new ArrayList<Object>();
		// 默认值
		if (StringUtils.isBlank(sort)) {
			sort = "uid";
		}
		if (StringUtils.isBlank(order)) {
			order = "asc";
		}

		StringBuffer sqlBody = new StringBuffer(" from member_openservice where 1=1");
		if (null != conditions) {
			if (null != conditions.get("uid")) {
				sqlBody.append(" and uid=?");
				conList.add(conditions.get("uid"));
			}
			if (null != conditions.get("osId")) {
				sqlBody.append(" and service_id=?");
				conList.add(conditions.get("osId"));
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
		JuRowCallbackHandler<MemberOpenService> juRowCallbackHandler = new JuRowCallbackHandler<MemberOpenService>(
				MemberOpenService.class);
		try {
			jdbcOperations.query(sql, juRowCallbackHandler, conList.toArray());
			pageInfo.setItems(juRowCallbackHandler.getList());
		} catch (Exception e) {
			System.err.println(e);
		}
		return pageInfo;
	}

	@Override
	public List<MemberServiceType> queryOpenServerByUID(int uid) {
		String sqlQuery = "select * from member_openservice where uid=?";
		JuRowCallbackHandler<MemberOpenService> callback = new JuRowCallbackHandler<MemberOpenService>(
				MemberOpenService.class);
		jdbcOperations.query(sqlQuery, callback, uid);
		List<MemberOpenService> mosList = callback.getList();
		List<MemberServiceType> mst = new ArrayList<MemberServiceType>();
		for (MemberOpenService memberOpenService : mosList) {
			mst.add(queryByid(memberOpenService.getServiceId()));
		}
		return mst;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean queryOpenServerByUIDAndServiceCode(int uid, String serviceCode) {
		String sql = "select count(mos.uid) from member_openservice mos,member_servicetype mst where mos.service_id=mst.id and uid=? and service_code=?";
		int count = jdbcOperations.queryForInt(sql, uid, serviceCode);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int queryServiceNameIsExist(String serviceName) {
		String sql = "SELECT * FROM member_servicetype WHERE name = ?";
		JuRowCallbackHandler<MemberServiceType> cb = new JuRowCallbackHandler<MemberServiceType>(
				MemberServiceType.class);
		jdbcOperations.query(sql, cb, serviceName);
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<MemberServiceType> queryAllServiceType() {
		String sqlQuery = "select * from member_servicetype";
		JuRowCallbackHandler<MemberServiceType> callback = new JuRowCallbackHandler<MemberServiceType>(
				MemberServiceType.class);
		jdbcOperations.query(sqlQuery, callback);
		List<MemberServiceType> mst = callback.getList();
		return mst;
	}
}
