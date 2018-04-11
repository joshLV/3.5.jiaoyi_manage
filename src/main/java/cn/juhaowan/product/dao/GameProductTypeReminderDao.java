/**
 * 
 */
package cn.juhaowan.product.dao;

import java.util.List;
import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.product.vo.GameProductTypeReminder;

/**
   自定义提醒信息
  11： (发布页)商品信息提示 
  12：（发布页）账户信息信息提示 
  13：（发布页）发布提示信息
  21：（商品详情页）提示信息
  31：  支付成功页信息提示
 * 
 */
public interface GameProductTypeReminderDao extends BaseDao<GameProductTypeReminder> {
	/**
	 *  提示信息类型【11: (发布页)商品信息提示 】
	 */
	public static int REMINDER_RELEASE_PRODUCT = 11;
	/**
	 *  提示信息类型【12:（发布页）账户信息信息提示 】
	 */
	public static int REMINDER_RELEASE_SELLER = 12;
	/**
	 *  提示信息类型【13:（发布页）发布提示信息 】
	 */
	public static int REMINDER_RELEASE = 13;
	/**
	 *  提示信息类型【21:(商品详情页)提示信息】
	 */
	public static int REMINDER_PRODUCT_DETAIL = 21;
	/**
	 *  提示信息类型【31：支付成功页信息提示】
	 */
	public static int REMINDER_PAY_SUC = 31;

	
	
	/**
	 * 新增
	 * @param reminder
	 * @return 
	 */
	int addReminder(GameProductTypeReminder reminder);
	/**
	 * 修改
	 * @param reminder
	 * @return
	 */
	int modifyReminder(GameProductTypeReminder reminder);
	/**
	 * 删除
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
	
	/**
	 * 删除
	 * @param reminderId
	 * @return
	 */
	int delReminder(int gameId,int gameProductTypeId);
	
}
