package cn.juhaowan.helpinfo.service;

import java.util.List;

import cn.jugame.util.PageInfo;
import cn.juhaowan.helpinfo.vo.HelpCategory;
import cn.juhaowan.helpinfo.vo.HelpInfo;

/**
 * 帮助信息服务接口
 * 
 */
public interface HelpInfoService {
	/**
	 * 添加一条帮助信息
	 * 
	 * @param categoryName
	 *            帮助分类名称
	 * @param weight
	 *            权重
	 * @pdOid 8d2fd2e7-af06-4cbd-a033-5088cfeb1c4f
	 * @return 0: 成功 , 1: 失败
	 */
	int addHelpCategory(String categoryName, int weight,int isShow);

	/**
	 * 修改帮助分类信息
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
	int modifyHelpCategory(int categoryID, String categoryName, int categoryWeight, int isShow);

	/**
	 * 删除帮助分类
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param isForse
	 *            是否强制删除，如果强制，则不判断是否有帮助信息属于该分类
	 * @pdOid 311ca714-ac70-45bd-bb86-480b0d94cbb4
	 * @return 0: 成功 , 1: 失败
	 */
	int deleteHelpCategory(int categoryID, boolean isForse);

	/**
	 * 添加一条帮助信息
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param helpTitle
	 *            帮助标题
	 * @param contentText
	 *            帮助信息内容
	 * @pdOid 81d0ffc7-5b14-4237-9697-6129ee180bf1
	 * 
	 * @return 0: 成功 , 1: 失败
	 */
	int addHelpContent(int categoryID, String helpTitle, String contentText);

	/**
	 * 添加一条帮助信息
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param helpTitle
	 *            帮助标题
	 * @param contentText
	 *            帮助信息内容
	 * @param weight
	 *            常见度
	 * 
	 * @return 0: 成功 , 1: 失败
	 */
	int addHelpContent(int categoryID, String helpTitle, String contentText, int weight);

	/**
	 * 添加一条帮助信息
	 * 
	 * @param categoryID
	 *            分类ID
	 * @param helpTitle
	 *            帮助标题
	 * @param contentText
	 *            帮助信息内容
	 * @param weight
	 *            常见度
	 * @param tag
	 *            标签
	 * @return 0: 成功 , 1: 失败
	 */
	int addHelpContent(int categoryID, String helpTitle, String contentText, int weight, String tag,int isShow);

	/**
	 * 删除一条帮助信息
	 * 
	 * @param helpInfoID
	 *            帮助ID
	 * @pdOid 0c43633d-b04d-45cd-bd66-a75c1810e1db
	 * @return 0: 成功 , 1: 失败
	 */
	int deleteHelpContent(int helpInfoID);

	/**
	 * 查询所有分类(类型)
	 * 
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回帮助分类列表
	 */
	List<HelpCategory> queryCategoryList(String sort, String order);

	/**
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回帮助分类列表
	 */
	List<HelpCategory> queryCategoryListFront(String sort, String order);

	/**
	 * 查询所有分类(类型)(带缓存)
	 * 
	 * @param sort
	 *            排序字段
	 * @param order
	 *            升序，降序
	 * @pdOid cade38ae-7267-46d7-947f-fafb255c8add
	 * @return 返回帮助分类列表
	 */
	List<HelpCategory> queryCategoryListCache(String sort, String order);

	/**
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @return 返回PageInfo类型的帮助分类
	 */
	PageInfo<HelpCategory> queryCategoryList(int pageSize, int pageNo, String sort, String order);

	/**
	 * 读取某一分类下的帮助信息列表
	 * 
	 * @param categoryID
	 *            帮助分类ID
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @pdOid fd3d77d4-edcb-4c20-87fb-94a2c6879770
	 * @return 返回PageInfo类型的帮助信息
	 */
	PageInfo<HelpInfo> queryHelpListByCategoryID(int categoryID, int pageSize, int pageNo, String sort, String order);

	/**
	 * 读取某一分类下的帮助信息列表
	 * 
	 * @param categoryID
	 *            帮助分类ID
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @pdOid fd3d77d4-edcb-4c20-87fb-94a2c6879770
	 * @return 返回PageInfo类型的帮助信息
	 */
	PageInfo<HelpInfo> queryHelpListByCategoryIDFront(int categoryID, int pageSize, int pageNo, String sort,
			String order);

	/**
	 * 读取某一分类下的帮助信息列表(带缓存)
	 * 
	 * @param categoryID
	 *            帮助分类ID
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @pdOid fd3d77d4-edcb-4c20-87fb-94a2c6879770
	 * @return 返回PageInfo类型的帮助信息
	 */
	PageInfo<HelpInfo> queryHelpListByCategoryIDCache(int categoryID, int pageSize, int pageNo, String sort,
			String order);

	/**
	 * 读取帮助信息列表
	 * 
	 * @param pageSize
	 *            每页条数
	 * @param pageNo
	 *            页码
	 * @param sort
	 *            排序字段
	 * @param order
	 *            降序或升序
	 * @pdOid 684b6964-5c44-4b98-b3ee-386b9387faa9
	 * @return 返回PageInfo类型的帮助信息
	 */
	PageInfo<HelpInfo> queryHelpList(int pageSize, int pageNo, String sort, String order);

	/**
	 * 读取某一ID的帮户信息详情(前台用）
	 * 
	 * @param helpID
	 *            帮助信息ID
	 * @return 返回对应ID帮助信息
	 */
	HelpInfo getHelpContentByIDFront(int helpID);

	/**
	 * 读取某一 Tag 对应的帮助信息详情(前台用)
	 * @param tag 帮助信息对应 tag
	 * @return 指定 tag 对应帮助内容
	 */
	HelpInfo getHelpContentByTag(String tag);

	/**
	 * 读取某一 Tag 对应的帮助信息详情(无缓存)
	 * @param tag 帮助信息对应 tag
	 * @return 指定 tag 对应帮助内容
	 */
	HelpInfo getHelpContentByTagDb(String tag);

	/**
	 * 读取某一ID的帮户信息详情(显示)
	 * 
	 * @param helpID
	 *            帮助信息ID
	 * @pdOid 66e472aa-047a-4498-b034-1efb11eee39c
	 * @return 返回对应ID帮助信息
	 */
	HelpInfo getHelpContentByID(int helpID);
	
	/**
	 * 读取某一ID的帮户信息详情
	 * @param helpID
	 * @return
	 */
	HelpInfo getHelpContentById(int helpID);

	/**
	 * 查询所有帮助信息列表(前台用)
	 * 
	 * @return 帮助信息列表
	 */
	List<Object> QueryAllHelpListFront();

	/**
	 * 查看帮助分类是否存在[0:不存在 1:存在]
	 * 
	 * @param categoryName
	 * @return
	 */
	int queryCategoryNameIsExist(String categoryName);

	/**
	 * 修改帮助信息
	 * 
	 * @param helpId
	 * @param helpTitle
	 * @param helpContent
	 * @param categoryId
	 * @return
	 */
	int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId);

	/**
	 * 修改帮助信息
	 * 
	 * @param helpId
	 * @param helpTitle
	 * @param helpContent
	 * @param categoryId
	 * @param helpWeight
	 * @return
	 */
	int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId, int helpWeight,int isShow);

	/**
	 * 修改帮助信息
	 * 
	 * @param helpId
	 *            帮助Id
	 * @param helpTitle
	 *            帮助标题
	 * @param helpContent
	 *            帮助内容
	 * @param categoryId
	 *            帮助类型
	 * @param helpWeight
	 *            帮助常见度
	 * @param tag
	 *            标签
	 * @return
	 */
	int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId, int helpWeight,
			String tag,int isShow);

	/**
	 * 按常见度排序查询
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	public PageInfo<HelpInfo> findHelpInfoByWeightListCache(int pageSize, int pageNo, String sort, String order);

	/**
	 * 查看帮助标签是否存在[0:不存在 1:存在]
	 * @param helpId 排除自己
	 * @param tag
	 *            标签
	 * @return
	 */
	int queryTagIsExist(int helpId,String tag);
}
