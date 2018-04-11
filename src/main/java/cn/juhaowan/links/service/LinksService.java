/**
 * 
 */
package cn.juhaowan.links.service;

import cn.juhaowan.links.vo.Link;

import java.util.List;

/**
 *  友情链接服务
 */
public interface LinksService {
	/**
	 * Wap
	 */
	public final static int LINKS_PLATFORM_WAP = -1;
	/**
	 * PC
	 */
	public final static int LINKS_PLATFORM_PC = 1;

	/**
	 * Wap
	 */
	public final static int LINKS_PLATFORM_ALL = 0;


	/**
	 * 添加友情链接
	 * 
	 * @param link
	 * @return
	 */
	int addLink(Link link);

	/**
	 * 删除友情链接
	 * 
	 * @param id
	 * @return
	 */
	int deleteLink(int id);

	/**
	 * 根据Id查询友情链接
	 *
	 * @param id
	 * @return
	 */
	Link findById(int id);

	/**
	 * 更新友情链接
	 * 
	 * @param latestSale
	 * @return
	 */
	int updateLink(Link latestSale);

	/**
	 * 获得友情链接List
	 * 
	 * @return
	 */
	List<Link> queryLinkList(int type);



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
