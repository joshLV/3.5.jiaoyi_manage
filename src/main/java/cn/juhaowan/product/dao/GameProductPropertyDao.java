/**
 * 
 */
package cn.juhaowan.product.dao;

import java.util.List;
import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.GameProductProperty;

/**
   自定义属性
 * 
 */
public interface GameProductPropertyDao extends BaseDao<GameProductProperty> {
	/**
	 *  属性类型【1:自定义商品信息 】
	 */
	public static int TYPE_PRODUCT = 1;
	
	/**
	 *  属性类型【2：自定义卖家信息 】
	 */
	public static int TYPE_SELLER = 2;
	
	/**
	 *  属性类型【3：自定义购买信息 】
	 */
	public static int TYPE_BUY = 3;
	
	/**
	 *  属性类型【4：自定义退游信息 】
	 */
	public static int TYPE_DEFINE = 4;

	/**
	 *  购买填写信息 [0:文本]
	 */
	public static int VALUE_TEXT = 0;
	
	/**
	 *  属性值类型[1:数值]
	 */
	public static int VALUE_NUM = 1;
	
	/**
	 *  属性值类型 [2:单选]
	 */
	public static int VALUE_RADIO = 2;
	/**
	 *  属性值类型 [3:多选]
	 */
	public static int VALUE_CHECKBOX = 3;
	/**
	 *  属性值类型 [4:图片]
	 */
	public static int VALUE_PIC = 4;
	
	/**
	 * 新增自定义属性
	 * @param property
	 * @return 
	 */
	int addProperty(GameProductProperty property);
	/**
	 * 修改自定义属性
	 * @param property
	 * @return
	 */
	int modifyProperty(GameProductProperty property);
	/**
	 * 删除自定义属性
	 * @param propertyId
	 * @return
	 */
	int delProperty(int propertyId);
	/**
	 * 根据属性id查询
	 * @param id
	 * @return
	 */
	GameProductProperty queryPropertyById(int id);
	/**
	 * 根据条件查询属性列表
	 * @param gameId 游戏id
	 * @param gameProductTypeId 游戏商品类型id
	 * @return
	 */
	List<GameProductProperty> queryProperty(int gameId,int gameProductTypeId);
	
	/**
	 * 根据条件查询属性列表
	 * @param gameId 游戏id
	 * @param gameProductTypeId 游戏商品类型id
	 * @param propertyType  属性类型
	 * @return
	 */
	List<GameProductProperty> queryProperty(int gameId,int gameProductTypeId,int propertyType);
	
	/**
	 * 删除自定义属性
	 * @param gameId
	 * @param gameProductTypeId
	 * @return
	 */
	int delProperty(int gameId,int gameProductTypeId);
	
}
