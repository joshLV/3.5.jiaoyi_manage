package cn.juhaowan.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GameCPDao;
import cn.juhaowan.product.dao.GameDao;
import cn.juhaowan.product.dao.GamePartitionDao;
import cn.juhaowan.product.dao.GameServerDao;
import cn.juhaowan.product.service.GameService;
import cn.juhaowan.product.vo.Game;
import cn.juhaowan.product.vo.GameCP;
import cn.juhaowan.product.vo.GamePartition;
import cn.juhaowan.product.vo.GameServer;

/**
 * 
 * @author Administrator
 * 
 */
@Service("GameService")
public class DefaultGameService implements GameService {

	@Autowired
	private GameDao gameDao;

	@Autowired
	private GamePartitionDao gamePartitionDao;

	@Autowired
	private GameServerDao gameServerDao;

	@Autowired
	private GameCPDao gameCPDao;

	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int insertGame(Game game) {
		int i = gameDao.insertGame(game);
		return i;
	}

	@Override
	public int updateGame(Game game) {
		int i = gameDao.updateGame(game);
		return i;
	}

	@Override
	public int deleteGame(int gameId) {
		int i = gameDao.deleteGame(gameId);
		return i;
	}

	@Override
	public Game queryGameByGameId(int gameId) {
		return gameDao.queryGameByGameId(gameId);
	}

	@Override
	public PageInfo<Game> queryGameForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		PageInfo<Game> pageinfo = gameDao.queryGameForPage(conditions, pageNo,pageSize, sort, order);
		return pageinfo;
	}

	@Override
	public int insertGamePartition(GamePartition gamePartition) {
		// 如果没有此游戏
		int gId = gamePartition.getGameId();
		Game game = null;
		game = gameDao.findById(gId);
		if (null == game) {
			return 1;
		}
		int i = gamePartitionDao.insertGamePartition(gamePartition);
		// 添加分区之后，更新游戏表partition_flag字段
		if (i == 0 && game.getPartitionFlag() == 0) {
			game.setPartitionFlag(1);
			gameDao.update(game);
		}
		return i;
	}

	@Override
	public int updateGamePartition(GamePartition gamePartition) {
		int i = gamePartitionDao.updateGamePartition(gamePartition);

		return i;
	}

	@Override
	public int deleteGamePartition(GamePartition gamePartition) {
		int i = gamePartitionDao.deleteGamePartition(gamePartition);
		List<GamePartition> pList = gamePartitionDao.queryGamePartitionByGameId(gamePartition.getGameId());
		if (pList.size() <= 0 && i == 0) {

			// 更新game游戏表partition_flag字段
			int gId = gamePartition.getGameId();
			Game game = null;
			game = gameDao.findById(gId);
			// 如果有游戏
			if (null != game) {
				game.setPartitionFlag(0);
				gameDao.update(game);
			}
		}
		return i;
	}

	@Override
	public List<GamePartition> queryGamePartitionByGameId(int gameId) {

		List<GamePartition> list = null;
		list = gamePartitionDao.queryGamePartitionByGameId(gameId);
		return list;

	}

	@Override
	public List<GamePartition> queryGamePartitionByGameIdAndVersionId(int gameId, int versionId) {

		List<GamePartition> list = null;
		String sql = "select * from  game_partition where  game_id = ? and  game_version_id= ?  ORDER BY game_partition_id ASC";
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb, new Object[] { gameId, versionId });
		list = cb.getList();
		return list;

	}

	@Override
	public int insertGameServer(GameServer gameServer) {
		// 如果没有此游戏
		int gId = gameServer.getGameId();
		Game game = null;
		game = gameDao.findById(gId);
		if (null == game) {
			return 1;
		}
		int i = gameServerDao.insertGameServer(gameServer);
		// 添加服务器之后，更新游戏表server_flag字段
		if (i == 0 && game.getServerFlag() == 0) {
			game.setServerFlag(1);
			gameDao.update(game);
		}

		return i;
	}

	@Override
	public int updateGameServer(GameServer gameServer) {
		int i = gameServerDao.updateGameServer(gameServer);
		return i;
	}

	@Override
	public int deleteGameServer(GameServer gameServer) {
		int i = gameServerDao.deleteGameServer(gameServer);
		List<GameServer> sList = gameServerDao.findGameServerListByMainKey(
				gameServer.getGameId(), gameServer.getGamePartitionId());
		if (sList.size() <= 0 && i == 0) {

			// 更新game游戏表server_flag字段
			int gId = gameServer.getGameId();
			Game game = null;
			game = gameDao.findById(gId);
			// 如果有游戏
			if (null != game) {
				game.setServerFlag(0);
				gameDao.update(game);
			}
		}
		return i;
	}

	@Override
	public List<GameServer> queryGameServerByGameId(int gameId) {
		List<GameServer> list = null;
		list = gameServerDao.queryGameServerByGameId(gameId);
		return list;
	}

	@Override
	public int insertGameCP(GameCP gameCP) {
		int i = gameCPDao.insertGameCP(gameCP);
		return i;
	}

	@Override
	public int updateGameCP(GameCP gameCP) {
		int i = gameCPDao.updateGameCP(gameCP);
		return i;
	}

	@Override
	public int deleteGameCP(int gameCPId) {
		int i = gameCPDao.deleteGameCP(gameCPId);
		return i;
	}

	@Override
	public GameCP queryGameCPByCPId(int gameCPId) {
		return gameCPDao.queryGameCPByCPId(gameCPId);
	}

	@Override
	public PageInfo<GameCP> queryGameCPForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {

		return gameCPDao.queryGameCPForPage(conditions, pageNo, pageSize, sort,order);
	}

	@Override
	public Game queryGameByGameIdFront(int gameId) {
		Game game = null;
		game = gameDao.queryGameByGameId(gameId);
		return game;

	}

	@Override
	public GamePartition findGamePartitionByMainKey(int gameId, int partitionId) {

		GamePartition gamePartition = null;
		gamePartition = gamePartitionDao.findGamePartitionByMainKey(gameId,partitionId);
		return gamePartition;
	}

	@Override
	public PageInfo<GamePartition> queryPage(Map<String, Object> conditions, int pageNo,int pageSize, String sort, String order) {
		return gamePartitionDao.queryPage(conditions, pageNo, pageSize, sort,order);
	}

	@Override
	public GameServer findGameServerByMainKey(int gameId, int partitionId,int serverId) {
		GameServer gameServer = null;
		gameServer = gameServerDao.findGameServerByMainKey(gameId, partitionId,serverId);
		return gameServer;
	}

	@Override
	public List<GamePartition> queryGamePartitionListByMainKey(int gameId) {
		List<GamePartition> list = null;
		list = gamePartitionDao.queryGamePartitionByGameId(gameId);
		return list;
	}

	@Override
	public List<GameServer> queryGameServerListByMainKey(int gameId,int partitionId) {
		List<GameServer> list = new ArrayList<GameServer>();
		list = gameServerDao.findGameServerListByMainKey(gameId, partitionId);
		return list;
	}

	@Override
	public GamePartition queryGamePartitionByMainKey(int gameId, int partitionId) {
		GamePartition gamePartition = null;
		gamePartition = gamePartitionDao.findGamePartitionByMainKey(gameId,partitionId);
		return gamePartition;
	}

	@Override
	public GameServer queryGameServerByMainKey(int gameId, int partitionId,int serverId) {
		GameServer gameServer = null;
		gameServer = gameServerDao.findGameServerByMainKey(gameId, partitionId,serverId);
		return gameServer;
	}

	@Override
	public int queryGameNameIsExist(String gameName) {

		String sql = "select * from game where game_name = ? ";
		JuRowCallbackHandler<Game> jrb = new JuRowCallbackHandler<Game>(Game.class);
		jdbcOperations.query(sql, jrb, gameName);
		if (jrb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}

	}

	@Override
	public List<Game> listAllGame() {
		return gameDao.listAllGame();
	}

	@Override
	public Game queryGameByGameIdCache(int gameId) {
		Game gameinfo = null;
		gameinfo = gameDao.queryGameByGameId(gameId);
		return gameinfo;
	}

	@Override
	public GamePartition queryGamePartitionByMainKeyCache(int gameId,int partitionId) {
		GamePartition gamePartition = null;
		if (null == gamePartition) {
			gamePartition = gamePartitionDao.findGamePartitionByMainKey(gameId,partitionId);
		}
		return gamePartition;
	}

	@Override
	public GameServer queryGameServerByMainKeyCache(int gameId,int partitionId, int serverId) {
		GameServer gameServer = null;
		gameServer = gameServerDao.findGameServerByMainKey(gameId,partitionId, serverId);
		return gameServer;
	}

	@Override
	public List<GamePartition> queryTyGamePartitionByGameId(int gameId) {
		List<GamePartition> list = null;
		String sql = "select * from game_partition where game_id = ? and is_ty = 1";
		JuRowCallbackHandler<GamePartition> jrh = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, jrh, gameId);
		if (jrh.getList().size() > 0) {
			list = jrh.getList();
		}
		return list;
	}


	@Override
	public List<GamePartition> queryTyGamePartitionByGameIdAndVersionId(int gameId,int versionId) {
		List<GamePartition> list = null;
		String sql = "select * from game_partition where game_id = ? and is_ty = 1 and game_version_id = ?";
		JuRowCallbackHandler<GamePartition> jrh = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, jrh, gameId,versionId);
		if (jrh.getList().size() > 0) {
			list = jrh.getList();
		}
		return list;
	}
	
	@Override
	public GamePartition findGamePartition(int gameId, int partitionId,int versionId) {
		if (gameId < 0) {
			return null;
		}
		String sql = "select * from  game_partition where  game_id = ? and  game_partition_id= ? and game_version_id = ?";
		GamePartition gamePartition = null;
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb, new Object[] { gameId, partitionId,versionId });
		List<GamePartition> productList = cb.getList();
		if (productList.size() > 0) {
			gamePartition = productList.get(0);
		}
		return gamePartition;
	}

	@Override
	public List<GamePartition> isGameAreaNameExist(String gameAreaName,int gameId, int versionId) {
		String sql = "SELECT * FROM game_partition WHERE game_id = ? AND partition_name = ? AND game_version_id=? ";
		JuRowCallbackHandler<GamePartition> cb = new JuRowCallbackHandler<GamePartition>(GamePartition.class);
		jdbcOperations.query(sql, cb, new Object[] { gameId, gameAreaName,versionId });
		List<GamePartition> list = cb.getList();
		return list;
	}

}
