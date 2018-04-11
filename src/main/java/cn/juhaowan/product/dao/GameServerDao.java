package cn.juhaowan.product.dao;

import java.util.List;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.GameServer;

/**
 * 
 * @author Administrator
 * 
 */
public interface GameServerDao extends BaseDao<GameServer> {
	/**
	 * 按id查找游戏服务器信息
	 * 
	 * @param id
	 *            服务器id
	 * @return 游戏服务器
	 */
	public GameServer findById(int id);

	/**
	 * 按条件查询
	 * 
	 * @param propertyName
	 *            字段
	 * @param value
	 *            字段内容
	 * @return 服务器列表
	 */
	public List<GameServer> findByProperty(String propertyName, Object value);

	/**
	 * 添加游戏服务器信息
	 * 
	 * @param gameServer
	 *            游戏服务器对象
	 * @return 0 成功 1 失败
	 * @pdOid 9983c6d9-ed46-4988-a328-2883f4c4ddc0
	 */
	int insertGameServer(GameServer gameServer);

	/**
	 * 修改游戏服务器信息
	 * 
	 * @param gameServer
	 *            游戏服务器实体对象
	 * @return 0 成功 1 失败
	 * @pdOid f125f012-1c32-402a-afac-995cd6868c04
	 */
	int updateGameServer(GameServer gameServer);

	/**
	 * 删除游戏服务器信息
	 * 
	 * @param gameServer
	 *            游戏服务器
	 * @return 0 成功 1 失败
	 * @pdOid 9218cf93-d54a-4bc7-99e1-3d13293333d8
	 */
	int deleteGameServer(GameServer gameServer);

	/**
	 * 修改服务器游戏权重
	 * 
	 * @param gameServerid
	 *            游戏服务器id
	 * @param weight
	 *            权重
	 * @return 0 成功 1 失败
	 * @pdOid 69f30e49-3a60-460d-bca3-188fa2bfdfeb
	 */
	int modifyGameServerWeight(int gameServerid, int weight);

	/**
	 * 按游戏id查询服务器信息列表
	 * 
	 * @param gameId
	 *            游戏id
	 * @return 服务器列表
	 * @pdOid f914d0bf-3afe-4c85-abf2-e0ee59cb7be9
	 */
	List<GameServer> queryGameServerByGameId(int gameId);

	/**
	 * 按分区id查询服务器信息列表
	 * 
	 * @param gamePartitionId
	 *            游戏分区id
	 * @return 服务器列表
	 * @pdOid be061fe7-9c41-48ce-8150-aac1ccfaa8ce
	 */
	List<GameServer> queryGameServerByPartitionId(int gamePartitionId);

	/**
	 * 按服务器id查询服务器信息
	 * 
	 * @param gameServerId
	 *            游戏服务器id
	 * @return 服务器实体
	 * @pdOid ae005fe0-353c-4ce7-89ef-ea9227556cd5
	 */
	GameServer queryGameServerByServerId(int gameServerId);

	/**
	 * 根据游戏id，分区id，服务器id，查询服务器对象
	 * 
	 * @param gameId
	 *            游戏id
	 * @param partitionId
	 *            分区id
	 * @param serverId
	 *            服务器id
	 * @return 服务器
	 */
	GameServer findGameServerByMainKey(int gameId, int partitionId, int serverId);

	/**
	 * 根据分区游戏id，分区id，查询服务器列表 （没分区时partitionId 传入 0）
	 * 
	 * @param gameId
	 *            游戏id
	 * @param partitionId
	 *            分区id
	 * @return 游戏列表
	 */

	List<GameServer> findGameServerListByMainKey(int gameId, int partitionId);
}
