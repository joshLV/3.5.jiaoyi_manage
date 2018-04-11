package cn.juhaowan.product.service;

import java.util.List;

import cn.juhaowan.product.vo.GameChannel;

public interface GameChannelService {
	
	/**
	 * 根据游戏id列出某游戏下的渠道
	 * @param gameId
	 * @return
	 */
	List<GameChannel> queryChannelByGameId(int gameId);
	
	/**
	 * 为某游戏添加渠道
	 * @param g
	 * @return
	 */
	int addGameChannel(GameChannel g);
	
	/**
	 * 删除渠道
	 * @param gameId
	 * @param gameChannelId
	 * @return
	 */
	int deleteGameChannel(int gameId, int gameChannelId);
	
	/**
	 * 根据游戏id和渠道id查出实体
	 * @param gameId
	 * @param gameChannelId
	 * @return
	 */
	GameChannel findItemByGameIdAndChannelId(int gameId, int gameChannelId);
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	GameChannel findItemById(int id);
	
	/**
	 * 更新实体
	 * @param g
	 * @return
	 */
	int updateItem(GameChannel g);
}
