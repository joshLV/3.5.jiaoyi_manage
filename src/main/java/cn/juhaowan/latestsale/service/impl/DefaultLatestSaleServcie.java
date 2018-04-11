/**
 * 
 */
package cn.juhaowan.latestsale.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.latestsale.service.LatestSaleService;
import cn.juhaowan.latestsale.vo.LatestSale;

/**
 * @author APXer
 *         最新出售实现
 */
@Service("LatestSaleService")
public class DefaultLatestSaleServcie implements LatestSaleService {
	@Autowired
	private JdbcOperations jdbcOperations;
	private static String LATESTSALEKEY = "latest_sale_key/";

	@Override
	public int addLatestSale(LatestSale latestSale) {
		int i = 1;
		String sql = "insert into latest_sale(title,create_time,weight,category) values(?,now(),?,?)";
		i = jdbcOperations.update(sql, latestSale.getTitle(), latestSale.getWeight(), latestSale.getCategory());
		return i ^ 1;
	}

	@Override
	public int deleteLatestSale(int id) {
		int i = 1;
		String sql = "delete from latest_sale where id=?";
		i = jdbcOperations.update(sql, id);
		return i ^ 1;
	}

	@Override
	public LatestSale findById(int id) {
		LatestSale mLatestSale = null;
		String sql = "select * from latest_sale where id = ?";
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		jdbcOperations.query(sql, callback, id);
		if (callback.getList().size() < 1) {
			return null;
		}
		mLatestSale = callback.getList().get(0);
		return mLatestSale;
	}

	@Override
	public int updateLatestSale(LatestSale latestSale) {
		String sql = "update latest_sale set title=? ,weight=?,category=? where id=?";
		int i = jdbcOperations.update(sql, latestSale.getTitle(), latestSale.getWeight(), latestSale.getCategory(),
				latestSale.getId());
		return (i > 0) ? 0 : 1;
	}

	@Override
	public PageInfo<LatestSale> queryLatestSalePageInfo(int pageSize, int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from latest_sale";
		String sqlcount = "select count(id) " + sql;
		@SuppressWarnings("deprecation")
		int rowcount = jdbcOperations.queryForInt(sqlcount);
		PageInfo<LatestSale> mPageInfo = new PageInfo<LatestSale>("", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcOperations.query(sqlQuery, callback, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public List<LatestSale> queryLatestSaleList() {
		String sqlQuery = "select * from latest_sale";
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		jdbcOperations.query(sqlQuery, callback);
		List<LatestSale> returnList = callback.getList();
		return returnList;
	}

	@Override
	public List<LatestSale> queryLatestSaleList(int category, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from latest_sale where category=? order by " + sort + ",id " + order;
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		jdbcOperations.query(sqlQuery, callback, category);
		List<LatestSale> returnList = callback.getList();
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LatestSale> queryLatestSaleListFront(int category, String sort, String order) {
		String key = LATESTSALEKEY + category;
		if (Cache.get(key) != null) {
			return (List<LatestSale>) Cache.get(key);
		}
		List<LatestSale> returnList = queryLatestSaleList(category, sort, order);
		Cache.set(key, 180, returnList);
		return returnList;
	}

	/**
	 * 辅助上、下移操作
	 * 
	 * @param id
	 *            更新ID
	 * @param latestSale
	 *            最新出售
	 * @return
	 */
	private int updateLatestSale(int id, LatestSale latestSale) {
		String sql = "update latest_sale set title=? ,weight=?, create_time=?,category=? where id=?";
		int i = jdbcOperations.update(sql, latestSale.getTitle(), latestSale.getWeight(), latestSale.getCreateTime(),
				latestSale.getCategory(), id);
		return (i > 0) ? 0 : 1;
	}

	@Override
	public int moveUp(int id) {
		LatestSale currentLS = findById(id);

		LatestSale moveUpLS = null;
		String sql = "select * from latest_sale where id < ? order by id DESC limit 0,1";
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		jdbcOperations.query(sql, callback, id);
		if (callback.getList().size() < 1) {
			moveUpLS = null;
		}
		moveUpLS = callback.getList().get(0);
		int upId = moveUpLS.getId();
		int currentLSFlag = updateLatestSale(id, moveUpLS);
		int upLSFlag = updateLatestSale(upId, currentLS);
		if (currentLSFlag == 0 && upLSFlag == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int moveDown(int id) {
		LatestSale currentLS = findById(id);

		LatestSale moveUpLS = null;
		String sql = "select * from latest_sale where id > ? order by id ASC limit 0,1";
		JuRowCallbackHandler<LatestSale> callback = new JuRowCallbackHandler<LatestSale>(LatestSale.class);
		jdbcOperations.query(sql, callback, id);
		if (callback.getList().size() < 1) {
			moveUpLS = null;
		}
		moveUpLS = callback.getList().get(0);
		int upId = moveUpLS.getId();
		int currentLSFlag = updateLatestSale(id, moveUpLS);
		int upLSFlag = updateLatestSale(upId, currentLS);
		if (currentLSFlag == 0 && upLSFlag == 0) {
			return 0;
		} else {
			return 1;
		}
	}

}
