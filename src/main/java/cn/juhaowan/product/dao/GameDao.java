package cn.juhaowan.product.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.vo.Game;

/**
 * 
 * @author Administrator
 * 
 */
public interface GameDao extends BaseDao<Game> {
	/**
	 * 按id查找游戏
	 * 
	 * @param id
	 *            游戏id
	 * @return 游戏实体
	 */
	public Game findById(int id);

	/**
	 * 游戏状态 1 ：正常显示
	 */
	public static int GAME_STATUS_SHOW = 1;

	/**
	 * 游戏状态 2 ：不显示
	 */
	public static int GAME_STATUS_HIDDEN = 2;

	/**
	 * 游戏状态 3 ：游戏停服维护
	 */
	public static int GAME_STATUS_SERVER_PAUSE = 3;

	/**
	 * 按字段查找游戏列表
	 * 
	 * @param propertyName
	 *            字段名称
	 * @param value
	 *            字段内容
	 * @return 游戏列表
	 */
	public List<Game> findByProperty(String propertyName, Object value);

	/**
	 * 添加游戏信息
	 * 
	 * @return 返回值 0：成功 1：失败
	 * @param game
	 *            游戏信息实体
	 * @pdOid 4425b857-62ce-4d88-9750-1aa25fab7923
	 */
	int insertGame(Game game);

	/**
	 * 按游戏id修改游戏信息
	 * 
	 * @return 0：成功 1：失败
	 * @param game
	 *            游戏实体
	 * @pdOid 778cb694-a74b-4e86-ab78-8ef551205f4a
	 */
	int updateGame(Game game);

	/**
	 * 按游戏id删除游戏信息
	 * 
	 * @param gameId
	 *            游戏id
	 * @return 0 成功 1 失败
	 * @pdOid f4817d06-3381-4116-9dec-0374a83fd0b3
	 */
	int deleteGame(int gameId);

	/**
	 * 修改游戏信息权重
	 * 
	 * @param gameId
	 *            游戏id
	 * @param weight
	 *            权重
	 * @return 0 成功 1 失败
	 * @pdOid f582be92-1301-44a3-a17c-a3c4bf244a92
	 */
	int modifyGameWeight(int gameId, int weight);

	/**
	 * 按游戏ID查找游戏信息
	 * 
	 * @param gameId
	 *            游戏id
	 * @return 0 成功 1 失败
	 * @pdOid 3d107d6c-fbeb-43b8-9424-977644244fb7
	 */
	Game queryGameByGameId(int gameId);

	/**
	 * 修改游戏状态
	 * 
	 * @param gameId
	 *            游戏id
	 * @param status
	 *            游戏状态
	 * @return 0 成功 1 失败 0：显示 1：不显示
	 * @pdOid c16a6c61-6ed4-4030-8152-4a1d53245699
	 */
	int modifyGameStatus(int gameId, int status);

	/**
	 * 分页查询游戏列表
	 * 
	 * @param conditions
	 *            查询条件
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            分页大小
	 * @param sort
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @return 分页
	 * @pdOid 4ad8f84a-990e-4b00-a592-e259c484585c
	 */
	PageInfo<Game> queryGameForPage(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order);

	/**
	 * 按首字母查询列表
	 * 
	 * @param initial
	 *            首字母
	 * @return 首字母列表
	 * @pdOid 6946d913-b2fb-411b-b8a6-035af877aadc
	 */
	List<Game> queryByInitial(String initial);

	/**
	 * 按首字母查询显示列表
	 *
	 * @param initial
	 *            首字母
	 * @return 首字母列表
	 */
	List<Game> queryByInitalAndStatus(String initial);

	/**
	 * 按CPid查找游戏列表
	 * 
	 * @param gameCPId
	 *            游戏cpid
	 * @return 游戏列表
	 * @pdOid d601312d-bac3-4eab-b86c-6ee283a009a5
	 */
	List<Game> queryGameByCPId(int gameCPId);

	/**
	 * 列出正常显示的游戏列表 （1：正常 3：维护）
	 * 
	 * @return 游戏列表
	 * @pdOid 6946d913-b2fb-411b-b8a6-035af877aadc
	 */
	List<Game> queryGameByStatus();

	/**
	 * 列出已存在游戏首字母
	 * 
	 * @return 首字母列表
	 */
	List<String> findGameDistinctInitial();
	
	/**
	 * 列出所有游戏
	 * @return 所有游戏列表
	 */
	List<Game> listAllGame();

    /**
     * 列出指定权重值以下的所有游戏列表
     * @param weight
     * @return
     */
	List<Game> listGameByWeightLimit(int weight);
}
