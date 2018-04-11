/**
 * 
 */
package cn.juhaowan.latestsale.service;

import java.util.List;

import cn.jugame.util.PageInfo;
import cn.juhaowan.latestsale.vo.LatestSale;

/**
 * @author APXer
 *         最新出售服务
 */
public interface LatestSaleService {
	/**
	 * 最新出售
	 */
	public final static int LATEST_SALE = 0;
	/**
	 * 最新商品
	 */
	public final static int LATEST_PRODUCT = 1;
	/**
	 * 最新退游
	 */
	public final static int LATEST_TY = 2;

	/**
	 * 添加最新出售
	 * 
	 * @param latestSale
	 * @return
	 */
	int addLatestSale(LatestSale latestSale);

	/**
	 * 删除最新出售
	 * 
	 * @param id
	 * @return
	 */
	int deleteLatestSale(int id);

	/**
	 * 根据Id查询最新出售
	 * 
	 * @param id
	 * @return
	 */
	LatestSale findById(int id);

	/**
	 * 更新最新出售
	 * 
	 * @param latestSale
	 * @return
	 */
	int updateLatestSale(LatestSale latestSale);

	/**
	 * 分页列表
	 * 
	 * @param status
	 * @param pageSize
	 * @param pageNo
	 * @param sort
	 * @param order
	 * @return
	 */
	PageInfo<LatestSale> queryLatestSalePageInfo(int pageSize, int pageNo, String sort, String order);

	/**
	 * 获得最新出售List
	 * 
	 * @return
	 */
	List<LatestSale> queryLatestSaleList();

	/**
	 * 获得最新出售List
	 * 
	 * @return
	 */
	List<LatestSale> queryLatestSaleList(int category, String sort, String order);

	/**
	 * 获得最新出售List前端(cache)
	 * 
	 * @return
	 */
	List<LatestSale> queryLatestSaleListFront(int category, String sort, String order);

	/**
	 * 上移
	 * 
	 * @param id
	 * @return
	 */
	int moveUp(int id);

	/**
	 * 下移
	 * 
	 * @param id
	 * @return
	 */
	int moveDown(int id);
}
