package cn.juhaowan.banner.service;

import java.util.List;
import java.util.Map;
import cn.juhaowan.banner.vo.PtBannerImages;
import cn.jugame.util.PageInfo;


/**
 * 横幅图片接口方法
 * 
 */
public interface BannerImagesService {
	/**
	 * 添加横幅图片
	 * @param bannerImg  横幅图片实体
	 * @return 0 成功 1 失败
	 */
	int insertPtBannerImg(PtBannerImages bannerImg);

	/**
	 * 按横幅id修改横幅图片
	 * 
	 * @param bannerImg 横幅图片实体
	 * @return 0 成功 1 失败
	 */
	int updatePtBannerImg(PtBannerImages bannerImg);

	/**
	 * 按横幅图片id删除横幅图片信息
	 * 
	 * @param imgId 横幅图片id
	 * @return 0 成功 1 失败
	 */
	int deletePtBannerImg(int imgId);

	/**
	 * 修改横幅图片权重
	 * 
	 * @param imgId 横幅图片id
	 * @param weight 权重
	 * @return 0 成功 1 失败
	 */
	int modifyPtBannerImgWeight(int imgId, int weight);

	/**
	 * 按横幅横幅ID查找横幅图片信息
	 * 
	 * @param imgId 横幅图片id
	 * @return 0 成功 1 失败
	 */
	PtBannerImages queryPtBannerImgById(int imgId);

	/**
	 * 修改横幅状态
	 * 
	 * @param imgId 横幅图片id
	 * @param status 状态 0 未审核  1上线 9下线
	 * @return 0 成功 1 失败
	 */
	int modifyPtBannerImgStatus(int imgId, int status);

	/**
	 * 分页查询横幅列表
	 * 
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 分页大小
	 * @param sort  排序字段
	 * @param order  asc/desc
	 * @return 分页
	 */
	PageInfo queryPtBannerImgForPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);


	/**
	 * 列出所有横幅
	 * @return 所有横幅列表
	 */
	List<PtBannerImages> listAllPtBannerImg();
	
	/**
	 * 根据横幅id 查询图片列表 (前台用 加3分钟缓存) 
	 * @param bannerId 横幅id
	 * @return 横幅图片列表
	 */
	List<PtBannerImages> queryListByBanberId(int bannerId);
	
	/**
	 * 按横幅id 删除横幅图片
	 * @param bannerId 横幅id
	 * @return 0 成功 1 失败
	 */
	int delByBannerId(int bannerId);
	
	/**
	 * 根据横幅id 查询图片列表 后台 
	 * @param bannerId 横幅id
	 * @return 横幅图片列表
	 */
	List<PtBannerImages> queryListByBanberIdBack(int bannerId,int statusType);
	
	/**
	 * 根据tag获取所有图片
	 * @param tag
	 * @return
	 */
	List<Map<String,Object>> queryListByTag(String tag);
	
}
