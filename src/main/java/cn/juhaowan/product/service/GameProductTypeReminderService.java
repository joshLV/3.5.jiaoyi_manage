/**
 * 
 */
package cn.juhaowan.product.service;

import java.util.List;
import cn.juhaowan.product.vo.GameProductTypeReminder;

/**
 *  自定义信息接口
 */
public interface GameProductTypeReminderService {

	/**
	 * 新增 自定义信息
	 * @param reminder
	 * @return 
	 */
	int addReminder(GameProductTypeReminder reminder);
	/**
	 * 修改 自定义信息
	 * @param reminder
	 * @return
	 */
	int modifyReminder(GameProductTypeReminder reminder);
	/**
	 * 删除 自定义信息
	 * @param reminder
	 * @return
	 */
	int delReminder(int reminderId);
	/**
	 * 根据属性id查询
	 * @param id
	 * @return
	 */
	GameProductTypeReminder queryReminderById(int id);
	/**
	 * 根据条件查询属性列表
	 * @param gameId 游戏id
	 * @param gameProductTypeId 游戏商品类型id
	 * @return
	 */
	List<GameProductTypeReminder> queryReminder(int gameId,int gameProductTypeId);
	
	/**
	 * 根据条件查询属性列表
	 * @param gameId 游戏id
	 * @param gameProductTypeId 游戏商品类型id
	 * @param reminderType  类型
	 * @return
	 */
	List<GameProductTypeReminder> queryReminder(int gameId,int gameProductTypeId,int reminderType);
	/**
	 * 是否已经存在同类型的自定义信息
	 * @param gameId
	 * @param gameProductTypeId
	 * @param reminderType
	 * @return
	 */
	boolean isExist(int gameId,int gameProductTypeId,String reminderType);
	
}
