/**
 * 
 */
package cn.juhaowan.product.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GamePartitionCategoryDao;
import cn.juhaowan.product.vo.GamePartitionCategory;
import cn.juhaowan.product.vo.GamePartitionCategoryRelationship;

/**
 * 游戏分类DAO实现
 * 
 * @author APXer
 * 
 */
@Repository("GamePartitionCategoryDao")
public class GamePartitionCategoryDaoImpl extends BaseDaoImplJdbc<GamePartitionCategory> implements GamePartitionCategoryDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int setCategoryPartition(int gameId, int categoryId, int[] gpIds) {
		int k = 1;
		try {

			jdbcOperations.update("delete from game_partition_category_relationship where game_id = " + gameId + " AND category_id=" + categoryId);
			if (gpIds != null) {
				for (int i = 0; i < gpIds.length; i++) {
					String sql = "insert into game_partition_category_relationship(game_id,category_id,game_partition_id) values(" + gameId + "," + categoryId + "," + gpIds[i] + ")";
					k = jdbcOperations.update(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k ^ 1;
	}

	@Override
	public List<GamePartitionCategory> findByGameId(int gameId) {
		JuRowCallbackHandler<GamePartitionCategory> cb = new JuRowCallbackHandler<GamePartitionCategory>(GamePartitionCategory.class);
		String sql = "select * from game_partition_category where game_id=? ORDER BY weight ASC";
		jdbcOperations.query(sql, cb, gameId);
		List<GamePartitionCategory> gcList = cb.getList();
		return gcList;
	}

	@Override
	public GamePartitionCategory findById(int id) {
		return findById(GamePartitionCategory.class, id);
	}

	@Override
	public int insertGameCategory(GamePartitionCategory gc) {
		if (null == gc) {
			return 1;
		}
		String sql = "insert game_partition_category(categoryName,game_id,weight,game_version_id) values(?,?,?,?)";
		int i = jdbcTemplate.update(sql//
				, gc.getCategoryName()//
				, gc.getGameId()//
				, gc.getWeight(), gc.getGameVersionId());
		return i ^ 1;
	}

	@Override
	public int deleteGameCategory(int gcId) {
		if (gcId < 0) {
			return 1;
		}
		GamePartitionCategory gc = findById(gcId);
		if (null == gc) {
			return 1;
		}
		delete(gc);
		jdbcOperations.update("delete from game_partition_category where id = " + gcId);
		return 0;
	}

	@Override
	public int updateGameCategory(GamePartitionCategory gc) {
		if (gc == null) {
			return 1;
		}
		GamePartitionCategory currentGC = findById(gc.getId());
		if (null == currentGC) {
			return 1;
		}
		update(gc);
		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<GamePartitionCategory> queryGameCategoryForPage(int gameId, int versionId, int pageNo, int pageSize, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from game_partition_category where game_id= ? and game_version_id = ?";
		String sqlcount = "select count(*) " + sql;
		int rowcount = jdbcOperations.queryForInt(sqlcount, gameId, versionId);
		PageInfo<GamePartitionCategory> mPageInfo = new PageInfo<GamePartitionCategory>("", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		JuRowCallbackHandler<GamePartitionCategory> callback = new JuRowCallbackHandler<GamePartitionCategory>(GamePartitionCategory.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}

		jdbcOperations.query(sqlQuery, callback, gameId, versionId, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public int queryGameCategoryNameIsExist(String GameCategoryName, int gameId, int gcId, int versionId) {
		String sql;
		if (0 == gcId) {
			sql = "select * from game_partition_category where categoryName = ? and game_id=?  and game_version_id = ?";
		} else {
			sql = "select * from game_partition_category where categoryName = ? and game_id=? and id!=?  and game_version_id = ?";
		}

		JuRowCallbackHandler<GamePartitionCategory> jrb = new JuRowCallbackHandler<GamePartitionCategory>(GamePartitionCategory.class);
		if (0 == gcId) {
			jdbcOperations.query(sql, jrb, GameCategoryName, gameId, versionId);
		} else {
			jdbcOperations.query(sql, jrb, GameCategoryName, gameId, gcId, versionId);
		}
		if (jrb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<GamePartitionCategoryRelationship> queryCategoryAllPartition(int gameId, int categoryId) {
		JuRowCallbackHandler<GamePartitionCategoryRelationship> cb = new JuRowCallbackHandler<GamePartitionCategoryRelationship>(GamePartitionCategoryRelationship.class);
		String sql = "select * from game_partition_category_relationship where game_id=? and category_id=? ORDER BY game_partition_id ASC";
		jdbcOperations.query(sql, cb, gameId, categoryId);
		List<GamePartitionCategoryRelationship> gcpList = cb.getList();
		return gcpList;
	}

}
