/**
 * 
 */
package cn.juhaowan.links.service.impl;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.juhaowan.links.service.LinksService;
import cn.juhaowan.links.vo.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *         友情链接实现
 */
@Service("LinkService")
public class DefaultLinksServcie implements LinksService {
	@Autowired
	private JdbcOperations jdbcOperations;
	private static String LINKS_KEY = "links:";

	@Override
	public int addLink(Link link) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int deleteLink(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Link findById(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int updateLink(Link link) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Link> queryLinkList(int type) {
		String condition = "";
		if (type == LINKS_PLATFORM_PC) {
			condition = " WHERE platform >=0";
		}else {
			if (type == LINKS_PLATFORM_WAP) {
				condition = " WHERE platform <= 0";
			}
		}

		String sqlQuery = "SELECT * FROM links" + condition + " ORDER BY weight DESC, create_time ASC";
		JuRowCallbackHandler<Link> callback = new JuRowCallbackHandler<Link>(Link.class);
		jdbcOperations.query(sqlQuery, callback);
		List<Link> returnList = callback.getList();
		return returnList;
	}



	/**
	 * 辅助上、下移操作
	 * 
	 * @param id
	 *            更新ID
	 * @param link
	 *            友情链接
	 * @return
	 */
	@SuppressWarnings("unused")
	private int updateLink(int id, Link link) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int moveUp(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int moveDown(int id) {
		throw new UnsupportedOperationException();
	}
}
