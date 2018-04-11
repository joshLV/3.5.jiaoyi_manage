package cn.juhaowan.member.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberOpenService;
import cn.juhaowan.member.vo.MemberServiceType;

/**
 * 会员服务类型
 */
public interface MemberServiceService {

	/**
	 * 快速发货
	 */
	public final static String QUICK_DELIVER = "quick_deliver";

	/**
	 * 添加服务类型
	 * 
	 * @return 0：成功 1：失败
	 * 
	 * @param serviceType
	 * @pdOid 58380a24-afe5-48d1-af9b-69966b8f62b7
	 */
	int addServiceType(MemberServiceType serviceType);

	/**
	 * 更新服务类型
	 * 
	 * @return 0：成功 1：失败
	 * 
	 * @param serviceType
	 * @pdOid df7dee15-7280-4e6f-86f0-3df5d193e496
	 */
	int updateServiceType(MemberServiceType serviceType);

	/**
	 * 删除服务类型
	 * 
	 * @pdOid 6e102430-8e50-4a7a-83f0-98fcdddbdc0c
	 * @param id
	 *            服务ID
	 * @param isForce
	 *            是否删除会员关联服务
	 * @return 0：成功 1：失败
	 */
	int deleteServiceType(int id, boolean isForce);

	/**
	 * 更新服务类型状态
	 * 
	 * @return 0：成功 1：失败
	 * 
	 * @param id
	 * @param status
	 * @pdOid 67c0b580-25a2-44ae-b154-b3ab81d89c90
	 */
	int updateServiceTypeStatus(int id, int status);

	/**
	 * 更新服务类型权重
	 * 
	 * @return 0：成功 1：失败
	 * 
	 * @param id
	 * @param weight
	 * @pdOid e12ea143-d017-47a8-9be0-fd96f1ede661
	 */
	int updateWeight(int id, int weight);

	/**
	 * 按条件查询分页
	 * 
	 * @param conditions
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @pdOid 4e5e9aa1-58ec-4c7a-905a-26c320d90e66
	 */
	PageInfo<MemberServiceType> queryPageServiceType(Map<String, Object> conditions, int pageSize, int pageNo,
			String sort, String order);

	/**
	 * 根据id查询类型详情
	 * 
	 * @param id
	 * @pdOid ece61b74-85b4-4b54-890c-35af43f2857b
	 */
	MemberServiceType queryByid(int id);

	/**
	 * 开通某项会员服务
	 * 
	 * @param uid
	 * @param osId
	 * @pdOid 978fe988-763d-427c-95a4-5c2531693e4e
	 */
	int addMemberServer(int uid, int[] osId);

	/**
	 * 停止某项会员服务
	 * 
	 * @param uid
	 * @param osId
	 * @pdOid c51d52c4-a1d6-40eb-8d26-e84dda55fc38
	 */
	int closeServer(int uid, int osId);

	/**
	 * 查询会员服务PageInfo
	 * 
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @pdOid 98656475-b405-4bf7-804f-7fe56a61f6e8
	 */
	PageInfo<MemberOpenService> queryPageInfo(Map<String, Object> conditions, int pageNo, int pageSize, String sort,
			String order);

	/**
	 * 查询所有服务
	 * 
	 */
	List<MemberServiceType> queryAllServiceType();

	/**
	 * 根据UID查询用户所开通的服务
	 * 
	 * @param uid
	 * @pdOid 116e9958-fbdb-40fa-ad9b-2fd5897da270
	 */
	List<MemberServiceType> queryOpenServerByUID(int uid);

	/**
	 * 根据UID查询用户所开通的服务Front(cache)
	 * 
	 * @param uid
	 * @pdOid 116e9958-fbdb-40fa-ad9b-2fd5897da270
	 */
	List<MemberServiceType> queryOpenServerByUIDFront(int uid);

	/**
	 * 根据UID、Service_code查询用户是否开通的该服务
	 * 
	 * @param uid
	 *            UID
	 * @param serviceCode
	 *            服务代码
	 * @return
	 */
	boolean queryOpenServerByUIDAndServiceCode(int uid, String serviceCode);

	/**
	 * @param serviceName
	 * @return
	 */
	int queryServiceNameIsExist(String serviceName);
}
