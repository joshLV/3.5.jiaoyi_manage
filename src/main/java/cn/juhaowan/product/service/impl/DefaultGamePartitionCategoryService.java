/**
 * 
 */
package cn.juhaowan.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GamePartitionCategoryDao;
import cn.juhaowan.product.service.GamePartitionCategoryService;
import cn.juhaowan.product.vo.GamePartitionCategory;
import cn.juhaowan.product.vo.GamePartitionCategoryRelationship;

/**
 * @author APXer
 * 
 */
@Service("GamePartitionCategoryService")
public class DefaultGamePartitionCategoryService implements GamePartitionCategoryService {
	@Autowired
	private GamePartitionCategoryDao gamePartitionCategoryDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int setCategoryPartition(int gameId, int categoryId, int[] gpIds) {
		return gamePartitionCategoryDao.setCategoryPartition(gameId, categoryId, gpIds);
	}


	@Override
	public GamePartitionCategory findById(int id) {
		return gamePartitionCategoryDao.findById(id);
	}

	@Override
	public int insertGameCategory(GamePartitionCategory gameCategory) {
		return gamePartitionCategoryDao.insertGameCategory(gameCategory);
	}

	@Override
	public int deleteGameCategory(int gameCategoryId) {
		return gamePartitionCategoryDao.deleteGameCategory(gameCategoryId);
	}

	@Override
	public int updateGameCategory(GamePartitionCategory gameCategory) {
		return gamePartitionCategoryDao.updateGameCategory(gameCategory);
	}

	@Override
	public PageInfo<GamePartitionCategory> queryGameCategoryForPage(int gameId, int versionId, int pageNo, int pageSize, String sort, String order) {
		return gamePartitionCategoryDao.queryGameCategoryForPage(gameId, versionId, pageNo, pageSize, sort, order);
	}

	@Override
	public int queryGameCategoryNameIsExist(String gameCategoryName, int gameId, int gcId, int versionId) {
		return gamePartitionCategoryDao.queryGameCategoryNameIsExist(gameCategoryName, gameId, gcId, versionId);
	}


	@Override
	public List<GamePartitionCategoryRelationship> queryCategoryAllPartition(int gameId, int categoryId) {
		return gamePartitionCategoryDao.queryCategoryAllPartition(gameId, categoryId);
	}




}
