package cn.juhaowan.announcement.service;

/***********************************************************************
 * Module:  AnnouncementService.java
 * Author:  Administrator
 * Purpose: Defines the Interface AnnouncementService
 ***********************************************************************/

import java.util.List;

import cn.jugame.util.PageInfo;
import cn.juhaowan.announcement.vo.Announcement;
import cn.juhaowan.announcement.vo.AnnouncementCategory;

/**
 * 公告服务
 * 
 */
public interface AnnouncementService {
	/**
	 * 添加公告
	 * 
	 * @param title
	 * @param content
	 * @param validTime
	 * @pdOid 56f78525-12e7-4548-8be7-4c5cb2790e97
	 */
	int addAnnouncement(String title, String template,String content, String validTime,int categoryId,String sendedTime, int releasePlatform );

	/**
	 * 删除公告
	 * 
	 * @param announcementID
	 * @pdOid fcbc8de1-980f-49b2-a3e6-3cb867e026fc
	 */
	int deleteAnnouncement(int announcementID);

	/**
	 * @param status
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @pdOid b375cc21-8074-430e-b485-bf23cbfe0868
	 */
	PageInfo<Announcement> queryAnnouncementList(String title,int status, int pageSize, int pageNo, String sort, String order);
	
	/**
	 * 显示公告列表 
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PageInfo<Announcement> queryAnnouncementListFont(int pageSize, int pageNo);

	List<Announcement> queryRecentAnnouncementList(int count);

	/** @pdOid ec7893f1-b4e4-4bc5-af1d-15f6a97b9569 */
	Announcement getAnnouncementDetail(int announcementID);

	Announcement getAnnouncementDetailForManage(int announcementID);

	/**
	 * 读取某一分类下的公告信息列表
	 * 
	 * @param categoryID
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 */
	PageInfo<Announcement> queryAnnouncemntListByCategoryID(int categoryID, int pageSize, int pageNo, String sort, String order);

	/**
	 * 添加一条公告分类信息
	 * 
	 * @param categoryName
	 */
	int addAnnoCategory(String categoryName, int weight);

	/**
	 * 修改公告分类信息
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param categoryName
	 *            分类名称
	 * @param categoryWeight
	 *            分类权重
	 * @pdOid bf5b4427-2548-4261-82b4-9a3793b0b9aa
	 */
	int modifyAnnoCategory(int categoryID, String categoryName, int categoryWeight);

	/**
	 * 删除公告分类
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param isForse
	 *            是否强制删除，如果强制，则不判断是否有帮助信息属于该分类
	 * @pdOid 311ca714-ac70-45bd-bb86-480b0d94cbb4
	 */
	int deleteAnnoCategory(int categoryID, boolean isForse);

	/**
	 * 查询公告分类列表
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 **/
	PageInfo<AnnouncementCategory> queryCategoryList(int pageSize, int pageNo, String sort, String order);

	/**
	 * 获得所有公告分类
	 * @param sort
	 * @param order
	 * @return
	 */
	List<AnnouncementCategory> queryCategoryList(String sort, String order);

	/**
	 * 公告分类是否存在
	 * @param categoryName
	 * @return
	 */
	int queryCategoryNameIsExist(String categoryName);

	/**
	 * 修改公告信息
	 * @param announcementID
	 * @param title
	 * @param content
	 * @param sendedTime
	 * @param validdate
	 * @param categoryId
	 * @param status
	 * @return
	 */
	int modifyAnnouncement(Integer announcementID, String title,String template, String content, String sendedTime, String validdate, int categoryId, Integer status, int releasePlatform );

}