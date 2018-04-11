package cn.juhaowan.product.dao;

import java.util.List;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.GameChannel;


public interface GameChannelDao extends BaseDao<GameChannel> {
	
	/**
	 * 根据游戏ID查出渠道列表
	 * @param gameId
	 * @return
	 */
	List<GameChannel> queryChannelByGameId(int gameId);
	
	/**
	 * 添加渠道列表
	 * @param g
	 */
	int addGameChannel(GameChannel g);
	
	
	/**
	 * 删除渠道
	 */
	int deleteGameChannel(int gameId,int gameChannelId);
	
	/**
	 * 根据游戏Id和渠道ID查找实体
	 * @param gameId
	 * @param gameChannelId
	 * @return
	 */
	GameChannel findItemByGameIdAndChannelId(int gameId,int gameChannelId);
	
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
