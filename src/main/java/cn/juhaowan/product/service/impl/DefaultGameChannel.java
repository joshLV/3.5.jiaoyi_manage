package cn.juhaowan.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.juhaowan.product.dao.GameChannelDao;
import cn.juhaowan.product.service.GameChannelService;
import cn.juhaowan.product.vo.GameChannel;

@Service("GameChannelService")
public class DefaultGameChannel implements GameChannelService{
	
	@Autowired
	private GameChannelDao gameChannelDao;
	
	@Override
	public List<GameChannel> queryChannelByGameId(int gameId) {
		return gameChannelDao.queryChannelByGameId(gameId);
	}

	@Override
	public int addGameChannel(GameChannel g) {
		return gameChannelDao.addGameChannel(g);
	}

	@Override
	public int deleteGameChannel(int gameId, int gameChannelId) {
		return gameChannelDao.deleteGameChannel(gameId, gameChannelId);
	}

	@Override
	public GameChannel findItemByGameIdAndChannelId(int gameId,
			int gameChannelId) {
		return gameChannelDao.findItemByGameIdAndChannelId(gameId, gameChannelId);
	}

	@Override
	public GameChannel findItemById(int id) {
		return gameChannelDao.findItemById(id);
	}

	@Override
	public int updateItem(GameChannel g) {
		return gameChannelDao.updateItem(g);
	}
	
}
