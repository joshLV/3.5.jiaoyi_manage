/**
 * 
 */
package cn.juhaowan.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberStoreDao;
import cn.juhaowan.member.service.MemberStoreManagerService;
import cn.juhaowan.member.vo.MemberPayCostList;
import cn.juhaowan.member.vo.MemberStore;
import cn.juhaowan.member.vo.MemberStoreOrder;

/**
 * @author Administrator
 * 
 */
@Service("MemberStoreManagerService")
public class DefaultMemberStoreManagerService implements MemberStoreManagerService {

	@Autowired
	JdbcOperations jdbcOperations;
	@Autowired
	MemberStoreDao memberStoreDao;

	@Override
	public int addMemberStore(MemberStore memberStore) {
		if(null == memberStore){
			return 1;
		}
		if(memberStore.getUid() < 0){
			return 1;
		}
		if(memberStoreDao.userIsExist(memberStore.getUid())){
			return 1;
		}
        int i = memberStoreDao.addMemberStore(memberStore);
		return i;
	}
	
	@Override
	public int addMemberStoreOrder(MemberStoreOrder memberStoreOrder) {
		int i = memberStoreDao.addMemberStoreOrder(memberStoreOrder);
		return i;
	}

	@Override
	public MemberStore queryByUidFront(int uid) {
		MemberStore m = null;
        m = memberStoreDao.queryByUid(uid);
		return m;
	}

	@Override
	public MemberStore queryByUidBack(int uid) {
		MemberStore m = null;
        m = memberStoreDao.queryByUid(uid);
		return m;
	}

	@Override
	public PageInfo<MemberStore> queryPageFront(Map<String, Object> conditions,  int pageNo,int pageSize, String sort,
			String order) {
		
		return memberStoreDao.queryPage(conditions, pageNo,pageSize, sort, order);
	}

	@Override
	public PageInfo<MemberStore> queryPageBack(Map<String, Object> conditions,  int pageNo, int pageSize, String sort,
			String order) {
		return memberStoreDao.queryPage(conditions,  pageNo, pageSize,sort, order);
	}

	@Override
	public List<MemberStore> queryListByStoreStatus(int storeStatus) {
		List<MemberStore> list = null;
		list = memberStoreDao.queryListByStoreStatus(storeStatus);
		return list;
	}

	@Override
	public int updateMemberStore(MemberStore memberStore) {
		int i = memberStoreDao.updateMemberStore(memberStore);
		return i;
	}

	@Override
	public int updateStoreStatus(int uid, int storeStatus) {
		int i = memberStoreDao.updateStoreStatus(uid, storeStatus);
		return i;
	}

	@Override
	public int updateVerifyStatus(int uid, int verifyStatus) {

		// #审核通过
		if (verifyStatus == MemberStoreDao.VERIFYSTATUS_SUCCESS) {
			// 支付成功 类型是开通店铺的支付单
			MemberPayCostList p = memberStoreDao.qeuryByIdAndType(uid, MemberStoreDao.PAYTYPE_NEW);
			if (null == p) {
				return 1;
			}
			int i = memberStoreDao.updateVerifyStatus(uid, verifyStatus);
			if (i == 1) {
				return 1;
			}
			// 开通店铺 （更新店铺开始和结束时间 以及审核时间）
			int j = memberStoreDao.outDateRenew(uid, p.getOpeningtime());
			if (j == 1) {
				// 开通店铺失败时 将之前状态修改为未审核 时间清空
				memberStoreDao.rollBackUpdateVerifyStatus(uid);
			}
			return j;

		} else if (verifyStatus == MemberStoreDao.VERIFYSTATUS_FAIL) { // #审核不过
			int i = memberStoreDao.updateVerifyStatus(uid, verifyStatus);
			return i;
		} else {// #其他异常情况
			return 1;
		}
	}



	@Override
	public int updateDealInfo(int uid, int yearDealNumber, double yearDealTotal, Date dealFinalTime) {
		int i = memberStoreDao.updateDealInfo(uid, yearDealNumber, yearDealTotal, dealFinalTime);
		return i;
	}





	@Override
	public int renew(int uid, int openTime) {
		//查询用户店铺信息
		MemberStore store = null;
		store = memberStoreDao.queryByUid(uid);
		if(null == store){
			return 1;
		}
		//正常续费
		if(store.getStoreStatus() == memberStoreDao.STORESTATUS_NORMAL){
			int i = memberStoreDao.renew(uid, openTime);
			return i;
		}
		//过期续费 或 新开店铺
		if(store.getStoreStatus() == memberStoreDao.STORESTATUS_OUTDATE || store.getStoreStatus() == memberStoreDao.STORESTATUS_TEMP){
			int j = memberStoreDao.outDateRenew(uid, openTime);
			//续费成功 修改店铺状态
			if(j == 0){
				int k = memberStoreDao.updateStoreStatus(uid, memberStoreDao.STORESTATUS_NORMAL);
				return k;
			}
			return j;
		}
		
		return 1;
	}

	// ------------------
	@Override
	public int addPayCost(int payType, int uid, Double amount, Integer openingTime) {
		return memberStoreDao.addPayCost(payType, uid, amount, openingTime);
	}

	@Override
	public PageInfo<MemberPayCostList> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize,
			String sort, String order) {
		return memberStoreDao.queryPageInfo(conditions, pageNo, pageSize, sort, order);
	}

	@Override
	public List<MemberPayCostList> queryListByUid(int uid) {
		return memberStoreDao.queryListByUid(uid);
	}

	@Override
	public int orderPaySuc(int tempOrderId,int uid) {
		int i = 1;
		//查询用户店铺信息 
		MemberStore store = memberStoreDao.queryByUid(uid);
		//查询开通店铺支付单信息
		MemberStoreOrder order = memberStoreDao.queryStoreOrderById(tempOrderId);
		if(null == order){
			return 1;
		}
        //判断支付单状态  
		if(order.getStatus() != MemberStoreDao.PAYSTATUS_SUCCESS){
			return 1;
		}
		//开通店铺 添加到店铺 等待审核
		if(null == store){
			MemberStore addStore = new MemberStore();
			addStore.setUid(uid);
			addStore.setStoreStatus(MemberStoreDao.STORESTATUS_TEMP);
			i = memberStoreDao.addMemberStore(addStore);
			if(i == 0){
				//添加成功支付单到member_store_paycostlist
				int j = memberStoreDao.addPayCost(MemberStoreDao.PAYTYPE_NEW, uid, Double.parseDouble(String.valueOf(order.getAmount())), order.getOpeningtime());
//			    if(j == 0){
//			    	//更新临时订单表状态 修改为支付成功
//					int k = memberStoreDao.updateStoreOrder(tempOrderId, MemberStoreDao.PAYSTATUS_SUCCESS);
//					return k;
//					
//			    }
				return j;
				
			}

			return i;
		}
		
		//正常续费 
		if(store.getStoreStatus() == MemberStoreDao.STORESTATUS_NORMAL){
			i = memberStoreDao.renew(uid, order.getOpeningtime());
			if(i == 0){
				//添加成功支付单到member_store_paycostlist
				int j = memberStoreDao.addPayCost(MemberStoreDao.PAYTYPE_NORMAL, uid, Double.parseDouble(String.valueOf(order.getAmount())), order.getOpeningtime());
//				if(j == 0){
//					int k = memberStoreDao.updateStoreOrder(tempOrderId, MemberStoreDao.PAYSTATUS_SUCCESS);
//					return k;
//				}
				return j;
				
			}
			return i;
		}
		
		//过期续费
		if(store.getStoreStatus() == MemberStoreDao.STORESTATUS_OUTDATE){
			i = memberStoreDao.outDateRenew(uid,  order.getOpeningtime());
			//续费成功 修改店铺状态
			if(i == 0){
			     i = memberStoreDao.updateStoreStatus(uid, memberStoreDao.STORESTATUS_NORMAL);
				if(i == 0 ){
					int j = memberStoreDao.addPayCost(MemberStoreDao.PAYTYPE_OUTDATE, uid, Double.parseDouble(String.valueOf(order.getAmount())), order.getOpeningtime());
//					if(j == 0){
//						int k = memberStoreDao.updateStoreOrder(tempOrderId, MemberStoreDao.PAYSTATUS_SUCCESS);
//						return k;
//					}
					return j;
					
				}
				
			}
			return i;
		}
		
		return i;
	}

	@Override
	public void rollBackUpdateVerifyStatus(int uid) {
		memberStoreDao.rollBackUpdateVerifyStatus(uid);
	}
	
	
}
