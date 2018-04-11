package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.GamePartition;

/**
 * 
 * @author Administrator
 * 
 */
public interface GamePartitionDao extends BaseDao<GamePartition> {
	/**
	 * 按id查找分区内容
	 * 
	 * @param id
	 *            分区id
	 * @return 分区实体
	 */
	public GamePartition findById(int id);

	/**
	 * 按字段内容查找分区列表
	 * 
	 * @param propertyName
	 *            字段
	 * @param value
	 *            字段内容
	 * @return 分区列表
	 */
	public List<GamePartition> findByProperty(String propertyName, Object value);

	/**
	 * 添加游戏分区信息
	 * 
	 * @param gamePartition
	 *            分区实体对象
	 * @return 0 成功 1 失败
	 * @pdOid 8c34a882-0b3e-46c6-b973-03d728694502
	 */
	int insertGamePartition(GamePartition gamePartition);

	/**
	 * 修改游戏分区信息
	 * 
	 * @param gamePartition 分区id
	 * @return 0 成功 1 失败
	 * @pdOid a98a7fec-cb82-4920-81a9-fa478ff3c8f0
	 */
	int updateGamePartition(GamePartition gamePartition);

	/**
	 * 删除游戏分区信息
	 * 
	 * @param gamePartition
	 *            游戏分区id
	 * @return 0 成功 1 失败
	 * @pdOid 4ecd033b-1f2a-41c9-bfd4-264fbf2f8913
	 */
	int deleteGamePartition(GamePartition gamePartition);

	/**
	 * 修改游戏权重
	 * 
	 * @param gamePartitionId
	 *            游戏分区id
	 * @param weight
	 *            权重
	 * @return 0 成功 1 失败
	 * @pdOid 6caa08d7-212c-4380-8b9a-4b893cdec0f6
	 */
	int modifyGamePartitionWeight(int gamePartitionId, int weight);

	/**
	 * 按游戏ID查找游戏分区列表
	 * 
	 * @param gameId
	 *            游戏id
	 * @return 分区列表
	 * @pdOid 54c04791-ee0d-4a01-b603-abf7f0e4120a
	 */
	List<GamePartition> queryGamePartitionByGameId(int gameId);

	/**
	 * 按分区id查找分区信息
	 * 
	 * @param gamePartitionId
	 *            游戏分区id
	 * @return 游戏分区实体
	 * @pdOid 9750cb03-cad6-4105-bfc0-64ae7bd3b24e
	 */
	GamePartition queryGamePartitionByPartitionId(int gamePartitionId);

	/**
	 * 根据游戏id和分区id查询 分区实体
	 * 
	 * @param partitionId
	 *            分区id
	 * @param gameId
	 *            游戏id
	 * @return 游戏分区实体
	 */
	GamePartition findGamePartitionByMainKey(int gameId, int partitionId);
	
	/**
	 * 修改时，判断区名称是否存在
	 * true:存在
	 * false:不存在
	 * @param gameName
	 * @return
	 */
    boolean isGameAreaNameExist(String gameAreaName,int gameId,int versionId,int partitionId) ;
    
    /**
     * 添加时，判断区名称是否存在
     * @param gameAreaName
     * @param gameId
     * @param versionId
     * 
     * @return
     */
    boolean isGameAreaNameExist(String gameAreaName,int gameId,int versionId) ;
	
	/**
	 * 区服管理（后台）
	 * 区服列表分页
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<GamePartition> queryPage(Map<String, Object> conditions,int pageNo, int pageSize,String sort, String order);
}
