/**
 * 
 */
package cn.juhaowan.product.service;

import java.util.List;

import cn.juhaowan.product.vo.GameProductProperty;
import cn.juhaowan.product.vo.GameProductType;

/**
 * 自定义属性接口
 */
public interface GameProductPropertyService {
	/**
	 * 新增自定义属性
	 * 
	 * @param property
	 * @return
	 */
	int addProperty(GameProductProperty property);

	/**
	 * 修改自定义属性
	 * 
	 * @param property
	 * @return
	 */
	int modifyProperty(GameProductProperty property);

	/**
	 * 删除自定义属性
	 * 
	 * @param propertyId
	 * @return
	 */
	int delProperty(int propertyId);

	/**
	 * 根据属性id查询
	 * 
	 * @param id
	 * @return
	 */
	GameProductProperty queryPropertyById(int id);

	/**
	 * 根据条件查询属性列表
	 * 
	 * @param gameId 游戏id
	 * @param gameProductTypeId 游戏商品类型id
	 * @param propertyType 属性类型
	 * @return
	 */
	List<GameProductProperty> queryProperty(int gameId, int gameProductTypeId, int propertyType);

	/**
	 * 复制商品自定义属性
	 * 
	 * @param sourceGameId
	 * @param tagerGameId
	 * @param gameProductTypeId
	 * @return
	 */
	public int copyProperty(int sourceGameId, int tagerGameId, int gameProductTypeId);

	/**
	 * 根据属性游戏id和商品分类id查询
	 * 
	 * @param id
	 * @return
	 */
	GameProductType queryProductTypeByGameIdAndgameProductTypeId(int gameId, int gameProductTypeId);
}
