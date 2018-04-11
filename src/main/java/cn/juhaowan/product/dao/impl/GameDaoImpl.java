package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GameDao;
import cn.juhaowan.product.vo.Game;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("GameDao")
public class GameDaoImpl extends BaseDaoImplJdbc<Game> implements GameDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public Game findById(int id) {
		return (Game) findById(Game.class, id);
	}

	@Override
	public List<Game> findByProperty(String propertyName, Object value) {
		return findByProperty(Game.class, propertyName, value);
	}

	@Override
	public int insertGame(Game game) {
		if (game == null) {
			return 1;
		}

		game.setGameInitial(game.getGameInitial().toUpperCase());
		game.setCreateTime(new Date());
		game.setIsForChat(1);
		insert(game);
		return 0;
	}

	@Override
	public int updateGame(Game game) {
		if (game == null) {
			return 1;
		}
		Game g = null;
		g = findById(game.getGameId());
		if (null == g) {
			return 1;
		}
		update(game);
		return 0;
	}

	@Override
	public int deleteGame(int gameId) {
		if (gameId < 0) {
			return 1;
		}
		Game g = null;
		g = findById(gameId);
		if (null == g) {
			return 1;
		}
		delete(g);
		return 0;
	}

	@Override
	public int modifyGameWeight(int gameId, int weight) {
		if (gameId < 0) {
			return 1;
		}
		String sql = "update game set weight = ? where game_id=?";
		int i = jdbcOperations.update(sql, weight, gameId);

		if (i == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Game queryGameByGameId(int gameId) {
		return findById(gameId);
	}

	@Override
	public int modifyGameStatus(int gameId, int status) {
		if (gameId < 0) {
			return 1;
		}
		String sql = "update game set status = ? where game_id=?";
		int i = jdbcOperations.update(sql, status, gameId);
		if (i == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public PageInfo<Game> queryGameForPage(Map<String, Object> conditions,
			int pageNo, int pageSize, String sort, String order) {

		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "game_id";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from game where 1=1 ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String) conditions.get("game_name"))) {
				sql.append(" and game_name like ?");
				conList.add("%" + conditions.get("game_name") + "%");
			}
			if (null != conditions.get("cp_id")) {
				sql.append(" and cp_id = ?");
				conList.add(conditions.get("cp_id"));
			}
			if (StringUtils.isNotBlank((String) conditions.get("game_initial"))) {
				sql.append(" and game_initial = ?");
				conList.add(conditions.get("game_initial"));
			}

		}
		sql.append(" order by " + sort + " " + order);

		String sqlcount = "select count(*)  " + sql.toString();

		@SuppressWarnings("deprecation")
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<Game> pageInfo = new PageInfo<Game>(count, pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);

		if (count == 0) {
			return pageInfo;
		}

		if (pageNo <= 0) {
			pageNo = 1;
		}

		if (pageNo > pageInfo.getPageCount()) {
			pageNo = pageInfo.getPageCount();
		}

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0) {
			firstResult = 0;
		}

		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();

		JuRowCallbackHandler<Game> rowCallbackHandler = new JuRowCallbackHandler<Game>(Game.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;

	}

	@Override
	public List<Game> queryByInitial(String initial) {
		return findByProperty("game_initial", initial);
	}

	@Override
	public List<Game> queryByInitalAndStatus(String initial) {
		String queryString = "game_initial = ? AND (status = ? or status = ?)";
		return this.queryForPage(Game.class, queryString, 0, 5000, initial, GameDao.GAME_STATUS_SHOW, GameDao.GAME_STATUS_SERVER_PAUSE);
	}

	@Override
	public List<Game> queryGameByCPId(int gameCPId) {
		return findByProperty("cp_id", gameCPId);
	}

	@Override
	public List<Game> queryGameByStatus() {

		JuRowCallbackHandler<Game> cb = new JuRowCallbackHandler<Game>(
				Game.class);
		String sql = " select * from game  where status = ? or status = ? ORDER BY weight ASC "; //2014-04-09 caixb修改  增加根据权重排序
		jdbcOperations.query(sql, cb, GameDao.GAME_STATUS_SHOW,GameDao.GAME_STATUS_SERVER_PAUSE);
		List<Game> gtList = cb.getList();

		return gtList;
	}

	@Override
	public List<String> findGameDistinctInitial() {
		List<String> list = new ArrayList<>();
		String sql = "select DISTINCT game_initial  from  game where status = ? or status = ?  ";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql,GameDao.GAME_STATUS_SHOW, GameDao.GAME_STATUS_SERVER_PAUSE);
		while (rs.next()) {
			list.add(rs.getString(1).toUpperCase());
		}

		Collections.sort(list);
		return list;
	}

	@Override
	public List<Game> listAllGame() {
	
		JuRowCallbackHandler<Game> cb = new JuRowCallbackHandler<Game>(Game.class);
		String sql = " SELECT * FROM game ORDER BY game_initial ASC";
		jdbcOperations.query(sql, cb);
		List<Game> gtList = cb.getList();

		return gtList;
	}

    @Override
    public List<Game> listGameByWeightLimit(int weight) {
        JuRowCallbackHandler<Game> cb = new JuRowCallbackHandler<Game>(Game.class);
        String sql = "SELECT * FROM game WHERE weight<? AND (status = ? OR status = ?) ORDER BY weight ASC ";

        jdbcOperations.query(sql, cb, weight, GameDao.GAME_STATUS_SHOW, GameDao.GAME_STATUS_SERVER_PAUSE);
        List<Game> gtList = cb.getList();
        return gtList;
    }
}
