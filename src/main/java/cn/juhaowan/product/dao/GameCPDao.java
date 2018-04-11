package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.GameCP;

/**
 * 
 * @author Administrator
 * 
 */
public interface GameCPDao extends BaseDao<GameCP> {
	/**
	 * 按id 查找游戏cp
	 * 
	 * @param id
	 *            CPid
	 * @return 游戏cp
	 */
	public GameCP findById(int id);

	/**
	 * 按条件查询游戏cp列表
	 * 
	 * @param propertyName
	 *            查询条件
	 * @param value
	 *            查询内容
	 * @return cp列表
	 */
	public List<GameCP> findByProperty(String propertyName, Object value);

	/**
	 * 添加游戏CP信息
	 * 
	 * @param gameCP
	 *            游戏cp 对象
	 * @return 0 成功 1 失败
	 * @pdOid 8251a418-1d66-4c8f-8b47-06b4ef604179
	 */
	int insertGameCP(GameCP gameCP);

	/**
	 * 修改游戏CP信息
	 * 
	 * @param gameCP
	 *            游戏Cp 对象
	 * @return 0 成功 1 失败
	 * @pdOid c89b7d5c-a731-41b8-a685-75c1d85bfe82
	 */
	int updateGameCP(GameCP gameCP);

	/**
	 * 删除游戏CP信息
	 * 
	 * @param gameCPId
	 *            游戏Cp id
	 * @return 0 成功 1 失败
	 * @pdOid 508efa39-2893-414b-b027-0cd381c141c9
	 */
	int deleteGameCP(int gameCPId);

	/**
	 * 按游戏cp id 查找CP信息
	 * 
	 * @param gameCPId
	 *            游戏cpid
	 * @return 游戏cp
	 * @pdOid 7d43aa79-521e-4ae8-a0cc-c51119621251
	 */
	GameCP queryGameCPByCPId(int gameCPId);

	/**
	 * 按条件分页查询游戏CP列表
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页条数
	 * @param sort
	 *            排序字段
	 * @param order
	 *            desc/asc
	 * @return 分页
	 * @pdOid 4f0ade49-6c39-4c50-9dff-4874dd2991eb
	 */
	PageInfo<GameCP> queryGameCPForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);
}
