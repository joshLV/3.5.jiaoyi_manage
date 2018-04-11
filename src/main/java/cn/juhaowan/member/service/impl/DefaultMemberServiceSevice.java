/**
 * 
 */
package cn.juhaowan.member.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberServiceDao;
import cn.juhaowan.member.service.MemberServiceService;
import cn.juhaowan.member.vo.MemberOpenService;
import cn.juhaowan.member.vo.MemberServiceType;

/**
 * 
 */
@Service("MemberServiceService")
public class DefaultMemberServiceSevice implements MemberServiceService {
	@Autowired
	MemberServiceDao memberServiceDao;
	private static final String OPEN_SERVICE_LIST_KEY = "open_service_list_key";

	@Override
	public int addServiceType(MemberServiceType serviceType) {
		return memberServiceDao.addServiceType(serviceType);
	}

	@Override
	public int updateServiceType(MemberServiceType serviceType) {
		return memberServiceDao.updateServiceType(serviceType);
	}

	@Override
	public int deleteServiceType(int id, boolean isForce) {
		return memberServiceDao.deleteServiceType(id, isForce);
	}

	@Override
	public int updateServiceTypeStatus(int id, int status) {
		return memberServiceDao.updateServiceTypeStatus(id, status);
	}

	@Override
	public int updateWeight(int id, int weight) {
		return memberServiceDao.updateWeight(id, weight);
	}

	@Override
	public PageInfo<MemberServiceType> queryPageServiceType(Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order) {
		return memberServiceDao.queryPageServiceType(conditions, pageSize, pageNo, sort, order);
	}

	@Override
	public MemberServiceType queryByid(int id) {
		return memberServiceDao.queryByid(id);
	}

	@Override
	public int addMemberServer(int uid, int[] osId) {
		return memberServiceDao.addMemberServer(uid, osId);
	}

	@Override
	public int closeServer(int uid, int osId) {
		return memberServiceDao.closeServer(uid, osId);
	}

	@Override
	public PageInfo<MemberOpenService> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize,
			String sort, String order) {
		return memberServiceDao.queryPageInfo(conditions, pageNo, pageSize, sort, order);
	}

	@Override
	public List<MemberServiceType> queryOpenServerByUID(int uid) {
		return memberServiceDao.queryOpenServerByUID(uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberServiceType> queryOpenServerByUIDFront(int uid) {
		if (Cache.get(OPEN_SERVICE_LIST_KEY + uid) != null) {
			return (List<MemberServiceType>) Cache.get(OPEN_SERVICE_LIST_KEY + uid);
		}
		List<MemberServiceType> mstList = queryOpenServerByUID(uid);
		Cache.set(OPEN_SERVICE_LIST_KEY + uid, 180, mstList);
		return mstList;
	}

	@Override
	public boolean queryOpenServerByUIDAndServiceCode(int uid, String serviceCode) {
		return memberServiceDao.queryOpenServerByUIDAndServiceCode(uid, serviceCode);
	}

	@Override
	public int queryServiceNameIsExist(String serviceName) {
		return memberServiceDao.queryServiceNameIsExist(serviceName);
	}

	@Override
	public List<MemberServiceType> queryAllServiceType() {
		return memberServiceDao.queryAllServiceType();
	}

}
