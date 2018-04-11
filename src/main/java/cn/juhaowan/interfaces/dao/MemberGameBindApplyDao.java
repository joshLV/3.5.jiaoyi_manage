
package cn.juhaowan.interfaces.dao;

import java.util.HashMap;
import java.util.List;

import cn.juhaowan.interfaces.game.vo.MemberGameBindApply;


/**
 * 
 * @author lithium
 *  
 */


public interface MemberGameBindApplyDao {
	
	/**
	 * 绑定游戏账号
	 * @param uid
	 * @param gameRoleId
	 * @param gameRoleName
	 * @param gameId
	 * @param areaId
	 * @param vcode
	 * @return 0是成功，其他为失败
	 */
	int applyBind(int uid, String gameRoleId, String gameRoleName, int gameId, int areaId, String vcode);

	
	/**
	 * 删除申请
	 * @param uid
	 * @param gameRoleId
	 * @param gameId
	 * @param areaId
	 * @return  0是成功，其他为失败
	 */
	int delApply(int uid, String gameRoleId, int gameId, int areaId);
	
	/**
	 * 删除申请
	 * @return  0是成功，其他为失败
	 */
	int delApply(int id);
	
	/**
	 * 根据uid查找绑定申请
	 * @param uid
	 * @return
	 */
	List<MemberGameBindApply> findByUid(int uid);
	
	/**
	 * 根据ID查找申请对象
	 * @param id
	 * @return
	 */
	MemberGameBindApply findById(int id);
	
	/**
	 * 更新唯一索引查找
	 * @param uid
	 * @param gameRoleId
	 * @param gameId
	 * @param areaId
	 * @return
	 */
	MemberGameBindApply findByInfo(int uid, String gameRoleId, int gameId, int areaId);
}
