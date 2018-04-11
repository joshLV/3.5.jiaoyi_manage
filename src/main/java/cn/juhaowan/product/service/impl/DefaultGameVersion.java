package cn.juhaowan.product.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.product.service.GameVersionService;
import cn.juhaowan.product.vo.GameVersion;

@Service("GameVersionService")
public class DefaultGameVersion implements GameVersionService {
	
	private Logger logger = LoggerFactory.getLogger(DefaultGameVersion.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public int add(GameVersion gameVersion) {
		String sql = "INSERT INTO `game_version`(`game_id`,`name`,`status`,`weight`) VALUES(?,?,?,?);";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql, gameVersion.getGameId(),
					gameVersion.getName(),
					gameVersion.getStatus(),
					gameVersion.getWeight());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}

	

	@Override
	public GameVersion findByIds(int id, int gameId) {
			String sql = "SELECT * FROM `game_version` WHERE `id`=? and game_id=?";
			GameVersion gv = new GameVersion();
			try {
				Map<String, Object> map = jdbcTemplate.queryForMap(sql,id,gameId);
				if (map == null) {
					return null;
				}
				gv.setGameId((int)map.get("game_id"));
				gv.setName((String)map.get("name"));
				gv.setStatus((int)map.get("status"));
				gv.setWeight((int)map.get("weight"));
				gv.setId((int)map.get("id"));
			} catch (Exception e) {
				logger.error(e.getMessage());
				return null;
			}
			return gv;
	}



	@Override
	public int update(GameVersion gameVersion) {
		String sql = "UPDATE `game_version` SET `game_id`=?,`name`=?,`status`=?,`weight`=? WHERE `id`=?";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql, 
					gameVersion.getGameId(),
					gameVersion.getName(),
					gameVersion.getStatus(),
					gameVersion.getWeight(),
					gameVersion.getId());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}

	@Override
	public int del(int id,int gameId) {
		String sql = "DELETE  FROM `game_version` WHERE `id`=? and game_id=?";
		int i = -1;
		try {
			i = jdbcTemplate.update(sql,id,gameId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return i>0 ? 1 : 0;
	}



	@Override
	public List<GameVersion> findAll(int gameId) {
		String sql = "SELECT * FROM `game_version` where game_id=?";
		JuRowCallbackHandler<GameVersion> cb = new JuRowCallbackHandler<GameVersion>(GameVersion.class);
		try {
			jdbcTemplate.query(sql, cb, gameId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		List<GameVersion> list = cb.getList();
		if (list == null) {
			return null;
		}
		return list;
	}



	@Override
	public List<GameVersion> findByGameId(int gameId) {
		String sql = "SELECT * FROM `game_version` WHERE game_id = ?";
		JuRowCallbackHandler<GameVersion> cb = new JuRowCallbackHandler<GameVersion>(GameVersion.class);
		List<GameVersion> list = null;
		try {
			jdbcTemplate.query(sql, cb,gameId);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		list  = cb.getList();
		if (list == null) {
			return null;
		}
		return list;
	}
	
	

}
