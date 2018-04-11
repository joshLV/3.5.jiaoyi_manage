/**
 * 
 */
package cn.juhaowan.product.dao;

import java.util.List;
import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.GamePartitionCategory;
import cn.juhaowan.product.vo.GamePartitionCategoryRelationship;

/**
 * @author APXer
 * 
 */
public interface GamePartitionCategoryDao extends BaseDao<GamePartitionCategory> {
	/**
	 * 游戏区分类
	 * 
	 * @param ggc 游戏分类
	 * @return
	 */
	int setCategoryPartition(int gameId, int categoryId, int[] gpIds);

	/**
	 * 根据游戏Id查询该游戏下所有分类
	 * 
	 * @param gameId 游戏ID
	 * @return 该游戏下所有分类
	 */
	List<GamePartitionCategory> findByGameId(int gameId);

	/**
	 * 根据分类ID查询分类信息
	 * 
	 * @param id 分类ID
	 * @return 分类信息
	 */
	GamePartitionCategory findById(int id);

	/**
	 * 添加游戏分类
	 * 
	 * @param GamePartitionCategory
	 * @return
	 */
	int insertGameCategory(GamePartitionCategory gameCategory);

	/**
	 * 删除游戏分类
	 * 
	 * @param GameCategoryId
	 * @return
	 */
	int deleteGameCategory(int gameCategoryId);

	/**
	 * 修改游戏分类
	 * 
	 * @param GamePartitionCategory
	 * @return
	 */
	int updateGameCategory(GamePartitionCategory gameCategory);

	/**
	 * 分页查询游戏分类
	 * 
	 * @param gameId 查询条件
	 * @param pageNo 页数
	 * @param pageSize 分页大小
	 * @param sort 排序字段(Default Sort Item: weight)
	 * @param order asc/desc
	 * @return 游戏分类的分页
	 */
	PageInfo<GamePartitionCategory> queryGameCategoryForPage(int gameId, int versionId, int pageNo, int pageSize, String sort, String order);

	/**
	 * 查询游戏分类名称是否重复
	 * 
	 * @param GameCategoryName 游戏分类名称
	 * @param gameId 游戏Id
	 * @return 0:不重复 1：重复
	 */
	int queryGameCategoryNameIsExist(String gameCategoryName, int gameId, int gcId, int versionId);

	/**
	 * 根据游戏Id查询该游戏下该游戏下所有区
	 * 
	 * @param gameId 游戏ID
	 * @param categoryId 种类ID
	 * @return
	 */
	List<GamePartitionCategoryRelationship> queryCategoryAllPartition(int gameId, int categoryId);

}
