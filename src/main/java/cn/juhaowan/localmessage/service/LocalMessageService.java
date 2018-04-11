package cn.juhaowan.localmessage.service;

import java.util.List;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.localmessage.vo.Localmessage;
import cn.juhaowan.localmessage.vo.MessageMission;
import cn.juhaowan.localmessage.vo.MessageMissionCategory;

/**
 * 站内信服务
 * 
 * @pdOid 9444a3bf-9517-4163-b8b9-9b550c25a456
 */
/**
 * @author Administrator
 * 
 */
/**
 * @author Administrator
 * 
 */
public interface LocalMessageService {
	/**
	 * 待发送
	 */
	public int WAITSEND = 0;
	/**
	 * 已发送
	 */
	public int DONESEND = 1;
	/**
	 * 发送中
	 */
	public int EXCUTESEND = 2;

	/**
	 * 站内信服务
	 * 
	 * @param userID
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @pdOid 7f9778a0-cb92-48d7-b669-38f6a8e71f23
	 */
	PageInfo<Localmessage> queryMessageListByUserID(int userID,
			Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order);

	/**
	 * 读取一个用户所有的消息列表
	 * 
	 * @param messageID
	 * @param status
	 * @pdOid 5c15c3ca-6a94-4aef-a61a-8fa929751d5f
	 */

	int queryMessageCountByUserID(int userID, Map<String, Object> condition);

	/**
	 * 改变站内信状态（已读，未读）
	 * 
	 * @param messageID
	 * @pdOid 484fa0a2-e26b-4f10-8579-0ecd9ed0f67f
	 */
	int changeUserMessageStatus(int[] messageID, int status);

	Localmessage getMessageByID(int messageId);

	/**
	 * 删除用户站内信
	 * 
	 * @param userID
	 * @param msgText
	 * @param msgTitle
	 * @param sendtime
	 *            发送时间
	 * @pdOid 3b1c8707-fd72-4d8c-bfa0-79ca2fc0e7fc
	 */
	int deleteUserMessage(int[] messageID);

	/**
	 * 给一个用户发送站内信
	 * 
	 * @param userIDArray
	 * @param msgContent
	 * @param msgTitle
	 * @param sendtime
	 *            发送时间
	 * @pdOid 4dd15586-8cfb-4b9c-8e07-b662684e6898
	 */
	int sendMessageToUser(int userID, String msgText, String msgTitle,
			String sendtime);

	/**
	 * 定时任务：给一个用户发送站内信
	 * 
	 * @param userIDArray
	 * @param msgContent
	 * @param msgTitle
	 * @param sendtime
	 *            发送时间
	 * @pdOid 4dd15586-8cfb-4b9c-8e07-b662684e6898
	 */
	@Deprecated
	int timingSendMessageToUser(int userID, String msgText, String msgTitle,
			String sendtime);

	@Deprecated
	int sendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime);

	/**
	 * 定时任务：给用户发送站内信
	 * 
	 * @param userIDArray
	 * @param msgContent
	 * @param msgTitle
	 * @param sendtime
	 *            发送时间
	 */
	@Deprecated
	int timingSendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime);

	/**
	 * 给用户群发站内信
	 * 
	 * @pdOid 4e8ed6b0-b258-4a2f-9f03-915ca1f5cfd4
	 */
	@Deprecated
	int doSendMessage();

	int sendMessageMission();

	/**
	 * 扫描定时发送站内信，进行发送
	 * 
	 * @param missionID
	 * @pdOid 592a4c8e-66d5-4060-97d7-f5df75b3dcf4
	 */
	int deleteSendMission(int missionID);

	/**
	 * 删除定时发送任务（只能删除还未发送的任务）
	 * 
	 * @param status
	 *            发送状态（0为已发送完成，1为未发送，-1为所有任务）
	 * @param pageSize
	 * @param pageNo
	 * @pdOid 2b379b21-7e8f-42ac-8f39-aad90698cad5
	 */
	PageInfo<MessageMission> queryMissionList(int status, int pageSize,
			int pageNo);

	/**
	 * 根据ID获取站内信文字内容，先从缓存拿
	 * 
	 * @param contentID
	 * @return
	 */
	String getMessageContentByID(int contentID);

	/**
	 * 直接发送站内信給某用户
	 * 
	 * @param title
	 *            标题
	 * @param contentid
	 *            内容
	 * @param receiverid
	 *            接受者
	 * @return >0成功
	 */
	int insertMessage(String title, int contentid, int receiverid);

	/**
	 * 直接发送站内信給某用户
	 * 
	 * @param title
	 *            标题
	 * @param contentid
	 *            内容
	 * @param receiverid
	 *            接受者
	 * @param mmcId
	 *            消息类型ID
	 * @return >0成功
	 */
	int insertMessage(String title, int contentid, int receiverid, int mmcId);

	@Deprecated
	int getNewMessageCount(int uid);

	int getNewMessageCount(int uid, int mmcId);

	void updateNewMessageCount(int uid, int count);

	/**
	 * 添加站内信类型
	 * 
	 * @param mmcId
	 * 
	 * @param categoryName
	 *            帮助分类名称
	 * @param weight
	 *            权重
	 * @pdOid 8d2fd2e7-af06-4cbd-a033-5088cfeb1c4f
	 * @return 0: 成功 , 1: 失败
	 */
	int addMessageMissionCategory(int mmcId, String categoryName, int weight);

	/**
	 * 修改站内信类型
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param categoryName
	 *            分类名称
	 * @param categoryWeight
	 *            分类权重
	 * @pdOid bf5b4427-2548-4261-82b4-9a3793b0b9aa
	 * @return 影响记录数
	 */
	int modifyMessageMission(int categoryID, String categoryName,
			int categoryWeight);

	/**
	 * 删除站内信类型
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param isForse
	 *            是否强制删除，如果强制，则不判断是否有帮助信息属于该分类
	 * @pdOid 311ca714-ac70-45bd-bb86-480b0d94cbb4
	 * @return 0: 成功 , 1: 失败
	 */
	int deleteMessageMissionCategory(int categoryID, boolean isForce);

	/**
	 * 查询所有分类(类型)
	 * 
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回站内信类型列表
	 */
	List<MessageMissionCategory> queryCategoryList(String sort, String order);

	/**
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回站内信类型列表
	 */
	List<MessageMissionCategory> queryCategoryListFront(String sort,
			String order);

	/**
	 * 查询所有分类(类型)(带缓存)
	 * 
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回站内信类型列表
	 */
	List<MessageMissionCategory> queryCategoryListCache(String sort,
			String order);

	/**
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @return 返回PageInfo类型的站内信类型
	 */
	PageInfo<MessageMissionCategory> queryCategoryList(int pageSize,
			int pageNo, String sort, String order);

	/**
	 * 查看帮助分类是否存在[0:不存在 1:存在]
	 * 
	 * @param categoryName
	 * @return
	 */
	int queryCategoryNameIsExist(String categoryName);

	/**
	 * 添加群发站内信到定时站内信表
	 * 
	 * @param userIDArray
	 * @param messageContent
	 * @param messageTitle
	 * @param sendtime
	 * @param mmcId
	 */
	int sendMessageToUsers(int[] userIDArray, String messageContent,
			String messageTitle, String sendtime, int mmcId);

	/**
	 * 定时任务：添加群发站内信到定时站内信表
	 * 
	 * @param userIDArray
	 * @param messageContent
	 * @param messageTitle
	 * @param sendtime
	 * @param mmcId
	 */
	int timingSendMessageToUsers(int[] userIDArray, String messageContent,
			String messageTitle, String sendtime, int mmcId);

	/**
	 * 給某个用户发送站内信到定时站内信表
	 * 
	 * @param parseInt
	 * @param messageContent
	 * @param messageTitle
	 * @param sendtime
	 * @param mmcId
	 */
	int sendMessageToUser(int parseInt, String messageContent,
			String messageTitle, String sendtime, int mmcId);

	/**
	 * 定时任务：給某个用户发送站内信到定时站内信表
	 * 
	 * @param parseInt
	 * @param messageContent
	 * @param messageTitle
	 * @param sendtime
	 * @param mmcId
	 */
	int timingSendMessageToUser(int parseInt, String messageContent,
			String messageTitle, String sendtime, int mmcId);

	boolean isExistMmcId(int mmcId);
}
