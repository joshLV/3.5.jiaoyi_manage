/**
 * 
 */
package cn.juhaowan.product.dao;

import java.util.List;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.GameProductType;
import cn.juhaowan.product.vo.GameProductTypeShow;
import cn.juhaowan.product.vo.ProductType;

/**
 * 
 */
public interface GameProductTypeDao extends BaseDao<GameProductType> {
	
	/**
	 *  购买填写信息 [填写角色ID]
	 */
	public static int INPUT_ID = 10;
	
	/**
	 *  购买填写信息 [填写角色名]
	 */
	public static int INPUT_NAME = 20;

	/**
	 *  购买填写信息 [角色ID/角色名  二选一]
	 */
	public static int INPUT_ID_OR_NAME = 30;
	
	/**
	 *  购买填写信息 [角色ID/角色名 两者必填]
	 */
	public static int INPUT_ID_AND_NAME = 40;
	
	/**
	 *  购买填写信息 [角色ID/角色名  两者都不填]
	 */
	public static int INPUT_NULL = 50;

	
	/**
	 * 订单 自动派单
	 */
	public static int ASSIGN_AUTO = 0;
	
	/**
	 * 订单 手动派单
	 */
	public static int ASSIGN_SELECT = 1;
	
    /**
     *  更新游戏商品类型
     * @param gameId
     * @param gptIds
     * @param unit
     * @param symbols
     * @param typeName
     * @param idDisplay 
     * @param roleDisplay
     * @return
     */
	int updateGameProductType(int gameId, int[] gptIds,String[] unit,String[] symbols,String[] typeName,
			String[] idDisplay,String[] roleDisplay,String[] autoAssign,String[] minPrice,String[] maxPrice,String[] isAudit, String[] isPublich, String[] gameChannel,
			String[] weight);
	/**
	 * 根据游戏Id查询该游戏下所有商品类型
	 * 
	 * @param gameId
	 *            游戏ID
	 * @return 该游戏下所有商品类型
	 */
	List<ProductType> findByGameId(int gameId);
	/**
	 * 根据游戏Id查询该游戏下所有商品类型(有效类型) status 1:显示 2：隐藏
	 * 
	 * @param gameId
	 *            游戏ID
	 * @return 该游戏下所有商品类型
	 */
    List<GameProductTypeShow> findByGameIdFront(int gameId) ;
//	/**
//	 * 根据游戏Id查询该游戏下所有商品类型(Cache)
//	 * 
//	 * @param gameId
//	 *            游戏ID
//	 * @return 该游戏下所有商品类型
//	 */
//	List<GameProductTypeShow> findByGameIdCache(int gameId);

	/**
	 * 根据游戏商品类型ID查询商品类型
	 * 
	 * @param id
	 *            游戏商品类型ID
	 * @return 商品类型信息
	 */
	GameProductType findById(int id);
	/**
	 * 根据渠道ID查询游戏商品类型(Cache)
	 * 
	 * @param id
	 *            游戏商品类型ID
	 * @return 游戏商品类型信息
	 */
	GameProductType findByIdCache(int id);

	/**
	 * 删除游戏商品类型
	 * 
	 * @param GameProductTypeId
	 * @return
	 */
	int deleteGameProductType(int GameProductTypeId);
    /**
     * 查询所有游戏商品类型
     * @return
     */

	List<GameProductType> queryAllGameProductType();
    /**
     * 按游戏id查询游戏商品类型
     * @param gameId
     * @return
     */
	List<GameProductType> queryGameProductTypeByGameId(int gameId);
	
	
	/**
	 * 根据游戏id 和 商品类型id查询 （后台权限分配调用）
	 * @param gameId
	 * @param productTypeId
	 * @return
	 */
	int  queryByGameIdAndTypeId(int gameId,int productTypeId);
	
	/**
	 * 根据游戏商品分类id 查询
	 * @param id
	 * @return
	 */
	GameProductTypeShow queryGameProductTypeShow(int id);
	
	/**
	 * 根据游戏id 和 商品类型id查询 （后台权限分配调用）
	 * @param gameId
	 * @param productTypeId
	 * @return GameProductTypeShow
	 */
	GameProductTypeShow  queryGameProductTypeShow(int gameId,int productTypeId);
	
	/**
	 * 根据游戏id 和 商品类型id查询 （后台复制属性内容）
	 * @param gameId
	 * @param productTypeId
	 * @return
	 */
	GameProductType  queryTypeByGameIdAndTypeId(int gameId,int productTypeId);
	
	/**
	 * 按游戏id查询游戏商品类型（商品类型id小于100）
	 * @param gameId
	 * @return
	 */
	List<GameProductType> queryGameProductTypeLimited(int gameId);
	
}
