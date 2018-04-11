package cn.juhaowan.product.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.GameServerDao;
import cn.juhaowan.product.vo.GameServer;
/**
 * 
 * @author Administrator
 *
 */
@Repository("GameServerDao")
public class GameServerDaoImpl extends BaseDaoImplJdbc<GameServer> implements GameServerDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public GameServer findById(int id) {
		return findById(GameServer.class, id);
	}

	@Override
	public List<GameServer> findByProperty(String propertyName, Object value) {
		return findByProperty(GameServer.class, propertyName, value);
	}

	@Override
	public int insertGameServer(final GameServer gameServer) {
		if (null == gameServer) {
			return 1;
		}
		final String sql = "insert into `game_server` (`game_server_id`, `game_id`, " 
				+ "`game_partition_id`, `game_server_name`, `weight`,`status`) values(?,?,?,?,?,?);";
		int flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, gameServer.getGameServerId());
				ps.setInt(2, gameServer.getGameId());
				ps.setInt(3, gameServer.getGamePartitionId());
				ps.setString(4, gameServer.getGameServerName());
				ps.setInt(5, gameServer.getWeight());
				ps.setInt(6, gameServer.getStatus());
			}

		});
		return flag == 1 ? 0 : 1;
	}

	@Override
	public int updateGameServer(final GameServer gameServer) {
		if (null == gameServer) {
			return 1;
		}
		final String sql = "UPDATE `game_server` SET `game_server_name`=?,`weight`=?,`status`=? " 
				+ "WHERE `game_server_id`=? AND `game_id`=? AND `game_partition_id` = ?;";
		int flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, gameServer.getGameServerName());
				ps.setInt(2, gameServer.getWeight());
				ps.setInt(3, gameServer.getStatus());
				ps.setInt(4, gameServer.getGameServerId());
				ps.setInt(5, gameServer.getGameId());
				ps.setInt(6, gameServer.getGamePartitionId());
			}

		});
		return flag == 1 ? 0 : 1;
	}
	@Override
	public int deleteGameServer(final GameServer gameServer) {
		if (gameServer == null) {
			return 1;
		}
		final String sql = "DELETE FROM `game_server` WHERE `game_server_id`=? " 
				+ "AND `game_id`=? AND `game_partition_id` = ?;";
		int flag = jdbcOperations.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, gameServer.getGameServerId());
				ps.setInt(2, gameServer.getGameId());
				ps.setInt(3, gameServer.getGamePartitionId());
			}

		});
		return flag == 1 ? 0 : 1;
	}

	@Override
	public int modifyGameServerWeight(int gameServerid, int weight) {
		if (gameServerid < 0) {
			return 1;
		}
		String sql = "update game_server set weight = ? where game_server_id = ?";
		int i = jdbcOperations.update(sql, new Object[] {weight, gameServerid});
		if (i == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<GameServer> queryGameServerByGameId(int gameId) {
		return findByProperty("game_id", gameId);
	}

	@Override
	public List<GameServer> queryGameServerByPartitionId(int gamePartitionId) {
		return findByProperty("game_partation_id", gamePartitionId);
	}

	@Override
	public GameServer queryGameServerByServerId(int gameServerId) {
		return findById(gameServerId);

	}

	@Override
	public GameServer findGameServerByMainKey(int gameId, int partitionId,
			int serverId) {
		if (gameId < 0) {
			return null;
		}
		String sql = "";
		GameServer gameServer = null;
		JuRowCallbackHandler<GameServer> cb = new JuRowCallbackHandler<GameServer>(
				GameServer.class);
		// 没有分区
		if (partitionId == 0) {
			sql = "select * from  game_server where  game_id = ? and  game_server_id = ?  ";
			jdbcOperations.query(sql, cb, new Object[] {gameId, serverId});
			List<GameServer> list = cb.getList();
			if(list.size() > 0){
				gameServer = list.get(0);
			}
			return gameServer;
		} else {
			sql = "select * from  game_server where  game_id = ? and  game_partition_id= ? and game_server_id =? ";
			jdbcOperations.query(sql.toString(), cb, new Object[] {gameId,partitionId, serverId});
			List<GameServer> list = cb.getList();
			if (list.size() > 0){
				gameServer = list.get(0);
			}
			return gameServer;
		}

	}

	@Override
	public List<GameServer> findGameServerListByMainKey(int gameId,
			int partitionId) {
		if (gameId < 0) {
			return null;
		}
		String sql = "";
		JuRowCallbackHandler<GameServer> cb = new JuRowCallbackHandler<GameServer>(
				GameServer.class);
		// 没有分区
		if (partitionId == 0) {
			sql = "select * from  game_server where  game_id = ?  ";
			jdbcOperations.query(sql, cb, new Object[] {gameId});
			List<GameServer> list = cb.getList();

			return list;
		} else {
			sql = "select * from  game_server where  game_id = ? and  game_partition_id= ?  ";
			jdbcOperations.query(sql.toString(), cb, new Object[] {gameId,partitionId });
			List<GameServer> list = cb.getList();
			return list;
		}
	}



}
