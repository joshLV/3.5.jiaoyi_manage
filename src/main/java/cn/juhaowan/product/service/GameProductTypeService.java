/**
 * 
 */
package cn.juhaowan.product.service;

import java.util.List;
import cn.juhaowan.product.vo.GameProductType;
import cn.juhaowan.product.vo.GameProductTypeShow;
import cn.juhaowan.product.vo.ProductType;

/**
 * 
 */
public interface GameProductTypeService {
	/**
	 * 分配游戏商品类型
	 * 
	 * @param gptIds 游戏商品类型id
	 * @param unit 单位
	 * @return
	 */
	int updateGameProductType(int gameId, int[] gptIds, String[] unit, String[] symbols, String[] typeName, String[] idDisplay, String[] roleDisplay, String[] autoAssign, String[] minPrice, String[] maxPrice, String[] isAudit, String[] isPublich,
			String[] gameChannel, String[] weight, String[] quota,String[] scWhiteListAddFlagStr,String[]dcWhiteListCheckFlagStr);

	/**
	 * 根据游戏Id查询该游戏下所有游戏商品类型 （所有类型列表）不带缓存
	 * 
	 * @param gameId 游戏ID
	 * @return 该游戏下所有游戏商品类型
	 */
	List<ProductType> findByGameId(int gameId);

	/**
	 * 根据游戏Id查询该游戏下所有游戏商品类型 (正常状态的类型列表)带缓存
	 * 
	 * @param gameId 游戏ID
	 * @return 该游戏下所有游戏商品类型
	 */
	List<GameProductTypeShow> findByGameIdFront(int gameId);

	/**
	 * 根据渠道ID查询游戏商品类型
	 * 
	 * @param id 游戏商品类型id
	 * @return 游戏商品类型
	 */
	GameProductType findById(int id);

	/**
	 * 根据渠道ID查询游戏商品类型(Cache)
	 * 
	 * @param id 游戏商品类型id
	 * @return 游戏商品类型
	 */
	GameProductType findByIdCache(int id);

	/**
	 * 查询所有游戏商品类型
	 * 
	 * @return
	 */

	List<GameProductType> queryAllGameProductType();

	/**
	 * 按游戏id查询游戏商品类型
	 * 
	 * @param gameId
	 * @return
	 */
	List<GameProductType> queryGameProductTypeByGameId(int gameId);

	/**
	 * 根据游戏id 和 商品类型id查询 （后台权限分配调用）
	 * 
	 * @param gameId
	 * @param productTypeId
	 * @return
	 */
	int queryByGameIdAndTypeId(int gameId, int productTypeId);

	/**
	 * 按游戏id查询游戏商品类型（后台 :商品类型id小于100）
	 */
	List<GameProductType> queryGameProductTypeLimited(int gameId);

	/**
	 * 根据游戏商品分类id 查询
	 * 
	 * @param id
	 * @return
	 */
	GameProductTypeShow queryGameProductTypeShow(int id);

	/**
	 * 根据游戏id 和 商品类型id查询
	 * 
	 * @param gameId
	 * @param productTypeId
	 * @return GameProductTypeShow
	 */
	GameProductTypeShow queryGameProductTypeShow(int gameId, int productTypeId);

	/**
	 * 根据游戏id 和 商品类型id查询
	 * 
	 * @param gameId
	 * @param productTypeId
	 * @return
	 */
	GameProductType findbyGameIdAndProductTypeId(int gameId, int productTypeId);

	/**
	 * 根据游戏id删除所有的游戏商品类型
	 * 
	 * @param gameId
	 * @return
	 */
	int delGameProTypeByGameId(int gameId);
	
	/**
	 * 更新单个游戏商品类型
	 * @param gpt
	 * @return
	 */
	int updateGameProType(GameProductType gpt);

}
