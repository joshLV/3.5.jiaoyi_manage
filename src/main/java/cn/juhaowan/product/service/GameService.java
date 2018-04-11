package cn.juhaowan.product.service;

import java.util.List;
import java.util.Map;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.Game;
import cn.juhaowan.product.vo.GamePartition;
import cn.juhaowan.product.vo.GameServer;
import cn.juhaowan.product.vo.GameCP;

/**
 * 游戏信息接口方法
 * 
 */
public interface GameService {
	/**
	 * 添加游戏信息
	 * 
	 * 
	 * @param game 游戏信息实体
	 * @return 0 成功 1 失败
	 * @pdOid 4425b857-62ce-4d88-9750-1aa25fab7923
	 */
	int insertGame(Game game);

	/**
	 * 按游戏id修改游戏信息
	 * 
	 * @param game 游戏实体
	 * @return 0 成功 1 失败
	 * @pdOid 778cb694-a74b-4e86-ab78-8ef551205f4a
	 */
	int updateGame(Game game);

	/**
	 * 按游戏id删除游戏信息
	 * 
	 * @param gameId 游戏id
	 * @return 0 成功 1 失败
	 * @pdOid f4817d06-3381-4116-9dec-0374a83fd0b3
	 */
	int deleteGame(int gameId);

	/**
	 * 按游戏ID查找游戏信息
	 * 
	 * @param gameId 游戏id
	 * @return 0 成功 1 失败
	 * @pdOid 3d107d6c-fbeb-43b8-9424-977644244fb7
	 */
	Game queryGameByGameId(int gameId);

	/**
	 * 分页查询游戏列表
	 * 
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 分页大小
	 * @param sort 排序字段
	 * @param order asc/desc
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo<Game> queryGameForPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * 添加游戏分区信息
	 * 
	 * @param gamePartition 分区实体
	 * @return 0 成功 1 失败
	 * @pdOid 8c34a882-0b3e-46c6-b973-03d728694502
	 */
	int insertGamePartition(GamePartition gamePartition);

	/**
	 * 修改游戏分区信息
	 * 
	 * @param gamePartition 游戏分区实体
	 * @return 0 成功 1 失败
	 * @pdOid a98a7fec-cb82-4920-81a9-fa478ff3c8f0
	 */
	int updateGamePartition(GamePartition gamePartition);

	/**
	 * 删除游戏分区信息
	 * 
	 * @param gamePartition 游戏分区
	 * @return 0 成功 1 失败
	 * 
	 * @pdOid 4ecd033b-1f2a-41c9-bfd4-264fbf2f8913
	 */
	int deleteGamePartition(GamePartition gamePartition);

	/**
	 * 按游戏ID查找游戏分区列表
	 * 
	 * @param gameId 游戏id
	 * @return 游戏分区列表
	 * @pdOid 54c04791-ee0d-4a01-b603-abf7f0e4120a
	 */
	List<GamePartition> queryGamePartitionByGameId(int gameId);

	/**
	 * 按游戏ID和版本id查找游戏分区列表
	 * 
	 * @param gameId 游戏id
	 * @return 游戏分区列表
	 */
	List<GamePartition> queryGamePartitionByGameIdAndVersionId(int gameId, int versionId);

	/**
	 * 添加游戏服务器信息
	 * 
	 * @param gameServer 游戏服务器实体
	 * @return 0 成功 1 失败
	 * @pdOid 9983c6d9-ed46-4988-a328-2883f4c4ddc0
	 */
	int insertGameServer(GameServer gameServer);

	/**
	 * 修改游戏服务器信息
	 * 
	 * @param gameServer 游戏服务器实体
	 * @return 0 成功 1 失败
	 * @pdOid f125f012-1c32-402a-afac-995cd6868c04
	 */
	int updateGameServer(GameServer gameServer);

	/**
	 * 删除游戏服务器信息
	 * 
	 * @param gameServer 游戏服务器id
	 * @return 0 成功 1 失败
	 * @pdOid 9218cf93-d54a-4bc7-99e1-3d13293333d8
	 */
	int deleteGameServer(GameServer gameServer);

	/**
	 * 按游戏id查询服务器信息列表
	 * 
	 * @param gameId 游戏id
	 * @return 游戏服务器列表
	 * @pdOid f914d0bf-3afe-4c85-abf2-e0ee59cb7be9
	 */
	List<GameServer> queryGameServerByGameId(int gameId);

	/**
	 * 添加游戏CP信息
	 * 
	 * @param gameCP 游戏cp 对象
	 * @return 0 成功 1 失败
	 * @pdOid 8251a418-1d66-4c8f-8b47-06b4ef604179
	 */
	int insertGameCP(GameCP gameCP);

	/**
	 * 修改游戏CP信息
	 * 
	 * @param gameCP 游戏Cp 实体
	 * @return 0 成功 1 失败
	 * @pdOid c89b7d5c-a731-41b8-a685-75c1d85bfe82
	 */
	int updateGameCP(GameCP gameCP);

	/**
	 * 删除游戏CP信息
	 * 
	 * @param gameCPId 游戏Cp id
	 * @return 0 成功 1 失败
	 * @pdOid 508efa39-2893-414b-b027-0cd381c141c9
	 */
	int deleteGameCP(int gameCPId);

	/**
	 * 按游戏cp id 查找CP信息
	 * 
	 * @param gameCPId 游戏cpid
	 * @return 游戏cp实体
	 * @pdOid 7d43aa79-521e-4ae8-a0cc-c51119621251
	 */
	GameCP queryGameCPByCPId(int gameCPId);

	/**
	 * 按条件分页查询游戏CP列表
	 * 
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 每页条数
	 * @param sort 排序字段
	 * @param order desc/asc
	 * @return 分页
	 * @pdOid 4f0ade49-6c39-4c50-9dff-4874dd2991eb
	 */
	PageInfo<GameCP> queryGameCPForPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * 按游戏ID查找游戏信息(前台 带缓存)
	 * 
	 * @param gameId 游戏id
	 * @return 游戏实体
	 * @pdOid 3d107d6c-fbeb-43b8-9424-977644244fb7
	 */
	Game queryGameByGameIdFront(int gameId);

	/**
	 * 根据游戏id和分区id查询 分区实体(前台 带缓存)
	 * 
	 * @param partitionId 分区id
	 * @param gameId 游戏id
	 * @return 游戏分区实体
	 */
	GamePartition findGamePartitionByMainKey(int gameId, int partitionId);

	/**
	 * 根据游戏id，分区id，服务器id，查询服务器对象 （没分区时partitionId 传入 0）(前台 带缓存)
	 * 
	 * @param gameId 游戏id
	 * @param partitionId 分区id
	 * @param serverId 服务器id
	 * @return 服务器实体
	 */

	GameServer findGameServerByMainKey(int gameId, int partitionId, int serverId);

	/**
	 * 按游戏ID查找游戏分区列表 （后台）
	 * 
	 * @param gameId 游戏id
	 * @return 游戏分区列表
	 * @pdOid 54c04791-ee0d-4a01-b603-abf7f0e4120a
	 */
	List<GamePartition> queryGamePartitionListByMainKey(int gameId);

	/**
	 * 按游戏id和查询服务器信息列表 （后台）
	 * 
	 * @param gameId 游戏id
	 * @param partitionId （没分区时partitionId 传入 0）
	 * @return 游戏服务器列表
	 * @pdOid f914d0bf-3afe-4c85-abf2-e0ee59cb7be9
	 */
	List<GameServer> queryGameServerListByMainKey(int gameId, int partitionId);

	/**
	 * 根据游戏id和分区id查询 分区实体(后台)
	 * 
	 * @param partitionId 分区id
	 * @param gameId 游戏id
	 * @return 游戏分区实体
	 * 
	 */
	GamePartition queryGamePartitionByMainKey(int gameId, int partitionId);

	/**
	 * 根据游戏id，分区id，服务器id，查询服务器对象 （没分区时partitionId 传入 0）(后台)
	 * 
	 * @param gameId 游戏id
	 * @param partitionId 分区id
	 * @param serverId 服务器id
	 * @return 服务器实体
	 */

	GameServer queryGameServerByMainKey(int gameId, int partitionId, int serverId);

	/**
	 * 查询游戏名是否重复
	 * 
	 * @param gameName 游戏名字
	 * @return 0:不重复 1：重复
	 */
	int queryGameNameIsExist(String gameName);

	/**
	 * 列出所有游戏
	 * 
	 * @return 所有游戏列表
	 */
	List<Game> listAllGame();

	/**
	 * 按游戏ID查找游戏信息(带缓存)
	 * 
	 * @param gameId 游戏id
	 * @return 0 成功 1 失败
	 * @pdOid 3d107d6c-fbeb-43b8-9424-977644244fb7
	 */
	Game queryGameByGameIdCache(int gameId);

	/**
	 * 根据游戏id和分区id查询 分区实体(后台带缓存)
	 * 
	 * @param partitionId 分区id
	 * @param gameId 游戏id
	 * @return 游戏分区实体
	 * 
	 */
	GamePartition queryGamePartitionByMainKeyCache(int gameId, int partitionId);

	/**
	 * 根据游戏id，分区id，服务器id，查询服务器对象 （没分区时partitionId 传入 0）(后台带缓存)
	 * 
	 * @param gameId 游戏id
	 * @param partitionId 分区id
	 * @param serverId 服务器id
	 * @return 服务器实体
	 */

	GameServer queryGameServerByMainKeyCache(int gameId, int partitionId, int serverId);

	/**
	 * 按游戏ID查找游戏分区列表 （只查询支持退游的分区）
	 * 
	 * @param gameId 游戏id
	 * @return 游戏分区列表
	 */
	List<GamePartition> queryTyGamePartitionByGameId(int gameId);

	/**
	 * 区服管理（后台） 区服列表分页
	 * 
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<GamePartition> queryPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);

	/**
	 * （后台 根据游戏id,版本id和游戏分区查找 实体）
	 * 
	 * @param gameId
	 * @param partitionId
	 * @return
	 */
	GamePartition findGamePartition(int gameId, int partitionId, int versionId);

	/**
	 * 查询该区版本下的区名称是否存在
	 * 
	 * @param gameAreaName
	 * @param gameId
	 * @param versionId
	 * @return
	 */
	List<GamePartition> isGameAreaNameExist(String gameAreaName, int gameId, int versionId);
	
	/**
	 * 后台 根据游戏id,版本id 查找列表
	 * @param gameId
	 * @param versionId
	 * @return
	 */
	List<GamePartition> queryTyGamePartitionByGameIdAndVersionId(int gameId,int versionId);

}
