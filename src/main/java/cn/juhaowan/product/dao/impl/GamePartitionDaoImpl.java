package cn.juhaowan.product.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GamePartitionDao;
import cn.juhaowan.product.vo.GamePartition;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("GamePartitionDao")
public class GamePartitionDaoImpl extends BaseDaoImplJdbc<GamePartition>
		implements GamePartitionDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public GamePartition findById(int id) {
		return findById(GamePartition.class, id);
	}

	@Override
	public List<GamePartition> findByProperty(String propertyName, Object value) {
		return findByProperty(GamePartition.class, propertyName, value);
	}

	@Override
	public int insertGamePartition(final GamePartition gamePartition) {
		if (gamePartition == null) {
			return 1;
		}

		if(gamePartition.getGamePartitionId() == 0 ){
			final String s = "INSERT  INTO `game_partition`(`game_id`,"
					+ "`partition_name`,`weight`,`status`,is_ty,area_coefficient,currency_ratio,game_version_id) VALUES (?,?,?,?,?,?,?,?);";
			int flag = jdbcOperations.update(s, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					//ps.setInt(1, gamePartition.getGamePartitionId());
					ps.setInt(1, gamePartition.getGameId());
					ps.setString(2, gamePartition.getPartitionName());
					ps.setInt(3, gamePartition.getWeight());
					ps.setInt(4, gamePartition.getStatus());
					ps.setInt(5, gamePartition.getIsTy());
					ps.setDouble(6, gamePartition.getAreaCoefficient());
					ps.setLong(7, gamePartition.getCurrencyRatio());
					ps.setLong(8, gamePartition.getGameVersionId());
				}

			});
			return flag == 1 ? 0 : 1;
		}else{
			final String sql = "INSERT  INTO `game_partition`(game_partition_id,`game_id`,"
					+ "`partition_name`,`weight`,`status`,is_ty,area_coefficient,currency_ratio,game_version_id) VALUES (?,?,?,?,?,?,?,?,?);";
			int flag=0;
			try {
				flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, gamePartition.getGamePartitionId());
						ps.setInt(2, gamePartition.getGameId());
						ps.setString(3, gamePartition.getPartitionName());
						ps.setInt(4, gamePartition.getWeight());
						ps.setInt(5, gamePartition.getStatus());
						ps.setInt(6, gamePartition.getIsTy());
						ps.setDouble(7, gamePartition.getAreaCoefficient());
						ps.setLong(8, gamePartition.getCurrencyRatio());
						ps.setLong(9, gamePartition.getGameVersionId());
					}

				});
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
			return flag == 1 ? 0 : 1;
		}

		
	}

	@Override
	public int updateGamePartition(final GamePartition gamePartition) {
		if (gamePartition == null) {
			return 1;
		}
		final String sql = "UPDATE `game_partition` SET game_partition_id=?,`partition_name`=?,`weight`=?,`status`=?,is_ty=?,area_coefficient=?,currency_ratio=? WHERE"
				+ " `game_partition_id`=? AND `game_id`=? AND `game_version_id`= ? ;";
		int flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, gamePartition.getGamePartitionId());
				ps.setString(2, gamePartition.getPartitionName());
				ps.setInt(3, gamePartition.getWeight());
				ps.setInt(4, gamePartition.getStatus());
				ps.setInt(5, gamePartition.getIsTy());
				ps.setDouble(6, gamePartition.getAreaCoefficient());
				ps.setLong(7, gamePartition.getCurrencyRatio());
				ps.setInt(8, gamePartition.getGamePartitionId());
				ps.setInt(9, gamePartition.getGameId());
				ps.setInt(10, gamePartition.getGameVersionId());
			}

		});
		return flag == 1 ? 0 : 1;
	}

	@Override
	public int deleteGamePartition(final GamePartition gamePartition) {
		if (gamePartition == null) {
			return 1;
		}
		final String sql = "DELETE FROM `game_partition` WHERE `game_partition_id`=? AND `game_id`=? AND `game_version_id`= ? ;";
		int flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, gamePartition.getGamePartitionId());
				ps.setInt(2, gamePartition.getGameId());
				ps.setInt(3, gamePartition.getGameVersionId());
			}

		});
		return flag == 1 ? 0 : 1;
	}

	@Override
	public int modifyGamePartitionWeight(int gamePartitionId, int weight) {
		if (gamePartitionId < 0) {
			return 1;
		}
		String sql = "update game_partition set weight = ? where game_partition_id=?";
		int i = jdbcOperations.update(sql, new Object[] {weight,
				gamePartitionId });
		if (i == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<GamePartition> queryGamePartitionByGameId(int gameId) {
		return findByProperty("game_id", gameId);
	}

	@Override
	public GamePartition queryGamePartitionByPartitionId(int gamePartitionId) {
		return findById(gamePartitionId);
	}

	@Override
	public GamePartition findGamePartitionByMainKey(int gameId, int partitionId) {
		if (gameId < 0) {
			return null;
		}
		String sql = "select * from  game_partition where  game_id = ? and  game_partition_id= ?";
		GamePartition gamePartition = null;
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb, new Object[] {gameId, partitionId });
		List<GamePartition> productList = cb.getList();
		if (productList.size() > 0) {
			gamePartition = productList.get(0);
		}
		return gamePartition;
	}
	
	@Override
	public  boolean isGameAreaNameExist(String gameAreaName,int gameId,int versionId,int partitionId) {
		String sql = "SELECT * FROM game_partition WHERE game_id = ? AND partition_name = ? AND game_version_id=? AND game_partition_id=?  ";
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb,gameId,gameAreaName,versionId,partitionId);
		List<GamePartition> list = cb.getList();
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
	@Override
	public boolean isGameAreaNameExist(String gameAreaName, int gameId,int versionId) {
		String sql = "SELECT * FROM game_partition WHERE game_id = ? AND partition_name = ? AND game_version_id=?  ";
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb,gameId,gameAreaName,versionId);
		List<GamePartition> list = cb.getList();
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

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
	@SuppressWarnings("deprecation")
	public PageInfo<GamePartition> queryPage(Map<String, Object> conditions,int pageNo, int pageSize,String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		int count = 0;
		int firstResult = 1;
		String sqlCount = null;
		if(null == sort) {
			sort = "weight";
		}
		if(null == order) {
			order = "desc";
		}
		sql.append(" from game_partition where 1 = 1");
		if(null != conditions) {
			
			if(null != conditions.get("game_id")) {
				sql.append(" and game_id = ? ");
				conList.add(conditions.get("game_id"));
			}
			if(null != conditions.get("version_id")) {
				sql.append(" and game_version_id = ? ");
				conList.add(conditions.get("version_id"));
			}
			if(null != conditions.get("partition_name")) {
				sql.append(" and partition_name = ? ");
				conList.add(conditions.get("partition_name"));
			}
		}
		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString();
		//System.out.println("sql ==" + sqlCount.toString());
		count = jdbcOperations.queryForInt(sqlCount,conList.toArray());
		PageInfo<GamePartition> pageinfo = new PageInfo<GamePartition>(count,pageSize);
		if(count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if(pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		String sqlPage = "select * " + sql.toString();
		//System.out.println(sqlPage);
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sqlPage, cb,conList.toArray());
		
		pageinfo.setItems(cb.getList());
		
		return pageinfo;
	}
}
