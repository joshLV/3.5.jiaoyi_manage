package cn.juhaowan.member.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberAccountFrozen;

public interface MemberAccountFrozenDao {

	//默认的冻结类型
	public static int FROZEN_TYPE_DEFAULT = 0;
	
	//易宝冻结
	public static int FROZEN_TYPE_YEEPAY = 1;
	
	//我们冻结
	public static int FROZEN_TYPE_JUGAME = 2;
	
	//冻结充值和支付
	public static int FROZEN_TYPE_PAY_AND_RECHARGE = 3;
	
	
	/**
	 * 添加冻结的UID
	 * @param uid 冻结的uid
	 * @param type 冻结的类型
	 * @param remark 备注
	 * @return 0:成功， 其他: 失败
	 */
	int frozen(int uid, int type, String remark);
	
	
	/**
	 * 添加冻结的UID
	 * @param l 冻结的uid列表
	 * @param type 冻结的类型
	 * @return
	 */
	int frozen(List<Integer> l, int type, String remark);
	
	
	
	/**
	 * 解冻uid
	 * @param uid 解冻的UID
	 * @return 0:成功，其他：失败
	 */
	int unFrozen(int uid);
	
	
	
	/**
	 * 解冻uid
	 * @param l 解冻的UID列表
	 * @return 0:成功， 其他: 失败
	 */
	int unFrozen(List<Integer> l);
	
	
	/**
	 * 判断是否是冻结用户
	 * @param uid 用户uid
	 * @return
	 */
	boolean isFrozen(int uid);
	
	
	/**
	 * 判断是否是冻结用户
	 * @param uid 用户uid
	 * @return
	 */
	boolean isFrozenPayAndRecharge(int uid);
	
	/**
	 * 分页查询黑名单信息
	 * @author Administrator
	 * @param conditions 查询条件
	 * @param pageSize 页记录数
	 * @param pageNo 当前页
	 * @param sort 排序字段
	 * @param order 升序/降序 
	 * @return 分页会员信息
	 * 
	 * */
	public PageInfo<MemberAccountFrozen> findWithPage(Map<String, Object> conditions, int pageSize, int pageNo, String sort, String order);

}
