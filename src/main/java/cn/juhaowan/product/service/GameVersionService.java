package cn.juhaowan.product.service;

import java.util.List;

import cn.juhaowan.product.vo.GameVersion;

public interface GameVersionService {

	/**
	 * 添加
	 * 
	 * @param gameVersion
	 * @return
	 */
	int add(GameVersion gameVersion);

	/**
	 * 
	 * @param id
	 * @param gameId
	 * @return
	 */
	GameVersion findByIds(int id, int gameId);

	/**
	 * 更新
	 * 
	 * @param gameVersion
	 * @return
	 */
	int update(GameVersion gameVersion);

	/**
	 * 删除
	 * 
	 * @param id
	 * @param gameId
	 * @return
	 */
	int del(int id, int gameId);

	List<GameVersion> findAll(int gameId);

	/**
	 * 根据游戏列出
	 * 
	 * @param gameId
	 * @return
	 */
	List<GameVersion> findByGameId(int gameId);
}
