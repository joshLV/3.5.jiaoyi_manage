package cn.juhaowan.banner.service;

import java.util.List;
import java.util.Map;
import cn.juhaowan.banner.vo.PtBanner;
import cn.jugame.util.PageInfo;


/**
 * 横幅接口方法
 * 
 */
public interface BannerService {
	/**
	 * 添加横幅
	 * @param banner  横幅信息实体
	 * @return 0 失败 大于1 成功  返回的是刚刚添加的横幅id
	 */
	int insertPtBanner(PtBanner banner);

	/**
	 * 按横幅id修改横幅信息
	 * 
	 * @param banner 横幅实体
	 * @return 0 成功 1 失败
	 */
	int updatePtBanner(PtBanner banner);

	/**
	 * 按横幅id删除横幅信息
	 * 
	 * @param bannerId 横幅id
	 * @return 0 成功 1 失败
	 */
	int deletePtBanner(int bannerId);

	/**
	 * 修改横幅权重
	 * 
	 * @param bannerId 横幅id
	 * @param weight 权重
	 * @return 0 成功 1 失败
	 */
	int modifyPtBannerWeight(int bannerId, int weight);

	/**
	 * 按横幅ID查找横幅信息
	 * 
	 * @param bannerId bannerId
	 * @return 0 成功 1 失败
	 */
	PtBanner queryPtBannerById(int bannerId);

	/**
	 * 修改横幅状态
	 * 
	 * @param bannerId 横幅id
	 * @param status 状态  0未审核  1上线 9下线
	 * @return 0 成功 1 失败
	 */
	int modifyPtBannerStatus(int bannerId, int status);

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
	PageInfo queryPtBannerForPage(Map<String, Object> conditions, int pageNo, int pageSize, String sort, String order);


	/**
	 * 列出所有横幅
	 * @return 所有横幅列表
	 */
	List<PtBanner> listAllPtBanner();
	
	/**
	 * 根据tag 查询图片列表 (前台用 加3分钟的缓存)
	 * @param tag 标签
	 * @return 列表
	 */
	PtBanner queryListByTag(String tag);
	
	/**
	 * 查询tag是否重复
	 * @param tag 标签
	 * @return boolean
	 */
	boolean tagExist(String tag);
	
}
