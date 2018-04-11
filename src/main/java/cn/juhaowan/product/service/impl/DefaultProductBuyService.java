package cn.juhaowan.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.ProductBuyDao;
import cn.juhaowan.product.service.ProductBuyService;
import cn.juhaowan.product.vo.ProductActivities;
import cn.juhaowan.product.vo.ProductActivitiesDownload;
import cn.juhaowan.product.vo.ProductActivitiesGoods;
import cn.juhaowan.product.vo.ProductActivitiesImages;
import cn.juhaowan.product.vo.ProductActivitiesNotes;
import cn.juhaowan.product.vo.ProductBuyFilter;
import cn.juhaowan.product.vo.ProductBuyFilterConfig;

/**
 * 
 * @author chenchong
 * 
 */
@Service("ProductBuyService")
public class DefaultProductBuyService implements ProductBuyService {

	@Autowired
	ProductBuyDao productBuyDao;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<ProductBuyFilterConfig> list() {
		return productBuyDao.list();

	}

	@Override
	public List<ProductBuyFilterConfig> listStatus() {
		return productBuyDao.listByStatus();
	}

	@Override
	public int addFilterConfig(ProductBuyFilterConfig productBuyFilterConfig) {
		return productBuyDao.addFilterConfig(productBuyFilterConfig);
	}

	@Override
	public ProductBuyFilterConfig findById(int id) {

		return productBuyDao.findById(id);
	}

	@Override
	public int updateFilterConfig(ProductBuyFilterConfig productBuyFilterConfig) {

		return productBuyDao.updateFilter(productBuyFilterConfig);
	}

	@Override
	public int deleteFilterConfig(int id) {
		return productBuyDao.deleteFilterConfig(id);
	}

	@Override
	public int addFilter(ProductBuyFilter pbf) {

		if (pbf == null) {
			return 1;
		}
		String sql = "INSERT INTO `product_buy_filter`(`name`,`product_id`,`product_name`,`filter_id`,`param`,`weight`,`err_message`,`remark`, `activitie_id`) VALUES (?,?,?,?,?,?,?,?,?);";
		int i = jdbcTemplate.update(sql, pbf.getName(), pbf.getProductId(),
				pbf.getProductName(), pbf.getFilterId(), pbf.getParam(),
				pbf.getWeight(), pbf.getErrMessage(), pbf.getRemark(),
				pbf.getActivitieId());
		return i ^ 1;
	}

	@Override
	public int delFilter(String pids, String filterId, String aId) {
		if (filterId == null || aId == null) {
			return 1;
		}
		String sql = "DELETE FROM `product_buy_filter` WHERE  filter_id= ? AND activitie_id = ? ";
		if (pids != null) {
			sql += " and product_id IN ('" + pids + "')";
		}
		int i = jdbcTemplate.update(sql, filterId, aId);
		return i ^ 1;
	}

	@Override
	public int delFilter(String pids, String aId) {

		if (pids == null || aId == null) {
			return 1;
		}
		String sql = "DELETE FROM `product_buy_filter` WHERE  activitie_id = ? ";
		if (pids != null) {
			sql += " and product_id IN ('" + pids + "')";
		}
		int i = jdbcTemplate.update(sql, aId);
		return i ^ 1;

	}

	@Override
	public List<ProductBuyFilter> productFiterList(String productId) {
		List<ProductBuyFilter> list = null;
		String sql = "SELECT * FROM `product_buy_filter` WHERE product_id= ?";
		JuRowCallbackHandler<ProductBuyFilter> rch = new JuRowCallbackHandler<ProductBuyFilter>(
				ProductBuyFilter.class);
		jdbcTemplate.query(sql, rch, productId);
		if (null != rch.getList()) {
			list = rch.getList();
		}
		return list;
	}

	@Override
	public int delFilterById(int id) {
		String sql = "DELETE FROM `product_buy_filter` WHERE id = ? limit 1";
		int i = jdbcTemplate.update(sql, id);
		return i ^ 1;
	}

	@Override
	public List<ProductBuyFilter> productFiterListByActivitiesId(
			String activitiesId) {
		List<ProductBuyFilter> list = null;
		String sql = "SELECT * FROM product_buy_filter WHERE `activitie_id`= ?";
		JuRowCallbackHandler<ProductBuyFilter> rch = new JuRowCallbackHandler<ProductBuyFilter>(
				ProductBuyFilter.class);
		jdbcTemplate.query(sql, rch, activitiesId);
		if (null != rch.getList()) {
			list = rch.getList();
		}
		return list;
	}

	@Override
	public List<ProductBuyFilter> distinctFiterListByAId(String productId,
			String activitiesId) {
		List<ProductBuyFilter> list = null;
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM `product_buy_filter` WHERE activitie_id =? ");
		if (productId != null && !"".equals(productId)) {
			sql.append("and product_id = '" + productId + "'");
		}
		sql.append(" GROUP BY product_id");
		JuRowCallbackHandler<ProductBuyFilter> rch = new JuRowCallbackHandler<ProductBuyFilter>(
				ProductBuyFilter.class);
		jdbcTemplate.query(sql.toString(), rch, activitiesId);
		if (null != rch.getList()) {
			list = rch.getList();
		}
		return list;
	}

	@Override
	public List<ProductBuyFilter> findFilterList(String fiterId) {
		List<ProductBuyFilter> list = null;
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM `product_buy_filter` WHERE filter_id =? ");
		JuRowCallbackHandler<ProductBuyFilter> rch = new JuRowCallbackHandler<ProductBuyFilter>(
				ProductBuyFilter.class);
		jdbcTemplate.query(sql.toString(), rch, fiterId);
		if (null != rch.getList()) {
			list = rch.getList();
		}
		return list;
	}

	@Override
	public ProductBuyFilter findFilter(String productId, String fiterId,
			String acivitiesId) {
		StringBuffer sql = new StringBuffer(
				"SELECT id,product_id,param,product_name FROM `product_buy_filter` WHERE  product_id= ?  AND activitie_id =?");
		if (fiterId != null) {
			sql.append(" AND filter_id=  " + fiterId);
		}
		SqlRowSet set = jdbcTemplate.queryForRowSet(sql.toString(), productId,
				acivitiesId);
		ProductBuyFilter f = null;
		while (set.next()) {
			f = new ProductBuyFilter();
			f.setId(set.getInt(1));
			f.setProductId(set.getString(2));
			f.setParam(set.getString(3));
			f.setProductName(set.getString(4));
		}
		return f;
	}

	@Override
	public ProductBuyFilter findProductBuyFilterById(int id) {
		ProductBuyFilter f = null;
		String sql = "SELECT * FROM `product_buy_filter` WHERE id = ?";
		JuRowCallbackHandler<ProductBuyFilter> rch = new JuRowCallbackHandler<ProductBuyFilter>(
				ProductBuyFilter.class);
		jdbcTemplate.query(sql, rch, id);
		if (null != rch.getList() && rch.getList().size() > 0) {
			f = rch.getList().get(0);
		}
		return f;
	}

	@Override
	public int updateFilter(ProductBuyFilter f) {
		String sql = "UPDATE `product_buy_filter` SET `id` = ?,`name` = ?,`product_id` = ?,`product_name` = ?,`filter_id` = ?,`param` = ?,`weight` = ?,`err_message` = ?,`remark` = ?,`activitie_id` = ? WHERE `id` = ?;";
		int i = jdbcTemplate.update(sql, f.getId(), f.getName(),
				f.getProductId(), f.getProductName(), f.getFilterId(),
				f.getParam(), f.getWeight(), f.getErrMessage(), f.getRemark(),
				f.getActivitieId(), f.getId());
		return i ^ 1;
	}

	@Override
	public int addActivities(ProductActivities a) {
		String sql = "INSERT INTO `product_activities`(`id`,`name`,`info`,`begin_time`,`end_time`,`status`,create_time ,`sub_title`,"
				+ "`saleout_show_flag`,  `game_id`, `game_download_logo_url`,`game_name`,weight, `tag`)"
				+ "VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int i = jdbcTemplate.update(sql, a.getId(), a.getName(), a.getInfo(),
				a.getBeginTime(), a.getEndTime(), a.getStatus(),
				a.getCreateTime(), a.getSubTitle(), a.getSaleoutShowFlag(),
				a.getGameId(), a.getGameDownloadLogoUrl(), a.getGameName(),a.getWeight(), a.getTag());
		return i ^ 1;
	}

	@Override
	public int editActivities(ProductActivities a) {
		String sql = "UPDATE `product_activities` SET `name` = ?,`info` = ?,`begin_time` = ?,`end_time` = ?,`status` = ?,  `sub_title` = ?,"
				+ "`saleout_show_flag` = ?,`game_id` = ?,`game_download_logo_url` = ?,`game_name` = ?, `tag` = ? WHERE `id` = ? ";
		int i = jdbcTemplate.update(sql, a.getName(), a.getInfo(),
				a.getBeginTime(), a.getEndTime(), a.getStatus(),
				a.getSubTitle(), a.getSaleoutShowFlag(), a.getGameId(),
				a.getGameDownloadLogoUrl(), a.getGameName(), a.getTag(), a.getId());
		return i ^ 1;
	}

	@Override
	public int modifyActivitiesStatus(int status, String id) {
		String sql = "UPDATE `product_activities` SET `status` = ? WHERE `id` = ? ";
		int i = jdbcTemplate.update(sql, status, id);
		return i ^ 1;
	}
	
	@Override
	public int modifyActivitiesWeight(int weight, String id) {
		String sql = "UPDATE `product_activities` SET `weight` = ? WHERE `id` = ? ";
		int i = jdbcTemplate.update(sql, weight, id);
		return i ^ 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<ProductActivities> queryActivitiesPageInfo(Map<String, Object> conditions,
			int pageNo, int pageSize, String sort, String order, String tag) {
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "create_time";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from product_activities where 1 = 1 ");
		if (null != conditions) {
			// 新加的参数
			if (null != conditions.get("id")) {
				sql.append(" and id = ? ");
				conList.add(conditions.get("id"));
			}
			if (null != conditions.get("name")) {
				sql.append(" and name = ? ");
				conList.add(conditions.get("name"));
			}
			if (null != conditions.get("b_time")) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("b_time"));
			}
			if (null != conditions.get("e_time")) {
				sql.append(" and create_time <= ? ");
				conList.add(conditions.get("e_time"));
			}
			if (null != conditions.get("status")) {
				int s = -1;
				try {
					s = Integer.parseInt(conditions.get("status").toString());
				} catch (Exception e) {
				}
				if (s >= 0) {
					sql.append(" and status = ? ");
					conList.add(conditions.get("status"));
				}
			}
			if (null != conditions.get("tag")) {
				sql.append(" and tag = ?");
				conList.add(conditions.get("tag"));
			}
		}
		sql.append(" order by STATUS DESC," + sort + " " + order);

		sqlCount = "select count(1) " + sql.toString().toString();
		count = jdbcTemplate.queryForInt(sqlCount, conList.toArray());
		PageInfo<ProductActivities> pageinfo = new PageInfo<ProductActivities>(
				count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();
		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());
		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<ProductActivities> cb = new JuRowCallbackHandler<ProductActivities>(
				ProductActivities.class);
		jdbcTemplate.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@Override
	public ProductActivities findActivitiesById(String aid) {
		ProductActivities a = null;
		String sql = "SELECT * FROM `product_activities` WHERE id = ?";
		JuRowCallbackHandler<ProductActivities> rch = new JuRowCallbackHandler<ProductActivities>(
				ProductActivities.class);
		jdbcTemplate.query(sql, rch, aid);
		if (rch.getList() != null && rch.getList().size() > 0) {
			a = rch.getList().get(0);
		}
		return a;
	}

	@Override
	public String overlapActivities(String aid, String productId) {
		String result = "";
		ProductActivities a = null;
		a = findActivitiesById(aid);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String btime = "";
		String etime = "";

		String now = sdf.format(new Date());

		try {
			btime = sdf.format(a.getBeginTime());
			etime = sdf.format(a.getEndTime());
		} catch (Exception e) {
		}
		if ("".equals(btime) && "".equals(etime)) {
			return result;
		}
		String sql = "SELECT * FROM `product_activities`";
		List<ProductActivities> alist = null;
		JuRowCallbackHandler<ProductActivities> rch = new JuRowCallbackHandler<ProductActivities>(
				ProductActivities.class);
		jdbcTemplate.query(sql, rch);
		if (rch.getList() != null) {

			alist = rch.getList();
			String tempbtime = "";
			String tempetime = "";
			for (int i = 0; i < alist.size(); i++) {
				try {
					// 排除自己
					if (alist.get(i).getId().equals(aid)) {
						continue;
					}

					tempbtime = sdf.format(alist.get(i).getBeginTime());
					tempetime = sdf.format(alist.get(i).getEndTime());
					// 如果活动已经过期 排除
					if (now.compareTo(tempetime) > 0) {
						continue;
					}

				} catch (Exception e) {
					continue;
				}
				boolean b = !(tempbtime.compareTo(etime) > 0 || btime
						.compareTo(tempetime) > 0);
				// 时间重叠的活动
				if (b) {
					String sql2 = "SELECT * FROM `product_activities_goods` WHERE activitie_id=? AND product_id= ?";
					JuRowCallbackHandler<ProductActivitiesGoods> rch2 = new JuRowCallbackHandler<ProductActivitiesGoods>(
							ProductActivitiesGoods.class);
					jdbcTemplate.query(sql2, rch2, alist.get(i).getId(),
							productId);
					if (rch2.getList() != null && rch2.getList().size() > 0) {
						result = "商品(" + productId + "),已关联到另一个活动("
								+ rch2.getList().get(0).getActivitieId()
								+ ")中,请删除该商品再保存";

					}
				}
			}

		}
		return result;
	}

	@Override
	public String overlapActivities(String aid, String productId,
			String modifybtime, String modifyetime) {
		String result = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String now = sdf.format(new Date());

		if ("".equals(modifybtime) && "".equals(modifyetime)) {
			return result;
		}
		String btime = "";
		String etime = "";
		btime = modifybtime;
		etime = modifyetime;
		// System.out.println("原始活动id：" + aid + "时间：" + btime + "===" + etime);
		String sql = "SELECT * FROM `product_activities`";
		List<ProductActivities> alist = null;
		JuRowCallbackHandler<ProductActivities> rch = new JuRowCallbackHandler<ProductActivities>(
				ProductActivities.class);
		jdbcTemplate.query(sql, rch);
		if (rch.getList() != null) {

			alist = rch.getList();
			String tempbtime = "";
			String tempetime = "";
			for (int i = 0; i < alist.size(); i++) {
				try {
					// 排除自己
					if (alist.get(i).getId().equals(aid)) {
						//System.out.println("排除自己 " + alist.get(i).getId());
						continue;
					}

					tempbtime = sdf.format(alist.get(i).getBeginTime());
					tempetime = sdf.format(alist.get(i).getEndTime());
					// 如果活动已经过期 排除
					if (now.compareTo(tempetime) > 0) {
						//System.out.println("过期排除 == " + alist.get(i).getId() + "now = " + now + "tempetime" + tempetime);
						continue;
					}

				} catch (Exception e) {
					continue;
				}
				boolean b = !(tempbtime.compareTo(etime) > 0 || btime.compareTo(tempetime) > 0);
				// 时间重叠的活动
				if (b) {
					String sql2 = "SELECT * FROM `product_activities_goods` WHERE activitie_id=? AND product_id= ?";
					JuRowCallbackHandler<ProductActivitiesGoods> rch2 = new JuRowCallbackHandler<ProductActivitiesGoods>(
							ProductActivitiesGoods.class);
					jdbcTemplate.query(sql2, rch2, alist.get(i).getId(),productId);
					if (rch2.getList() != null && rch2.getList().size() > 0) {
						result = "商品(" + productId + "),已关联到另一个活动("+ rch2.getList().get(0).getActivitieId()+ ")中,两个活动时间重叠";

					}
				}
			}

		}
		return result;
	}

	@Override
	public List<ProductActivitiesGoods> listActivitiesGoods(String productId,
			String aid) {
		List<ProductActivitiesGoods> list = null;
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM  product_activities_goods WHERE activitie_id = ?");
		if (null != productId && !"".equals(productId)) {
			sql.append("and product_id = '" + productId + "'");
		}
		JuRowCallbackHandler<ProductActivitiesGoods> rch = new JuRowCallbackHandler<ProductActivitiesGoods>(
				ProductActivitiesGoods.class);
		jdbcTemplate.query(sql.toString(), rch, aid);
		if (null != rch.getList() && rch.getList().size() > 0) {
			list = rch.getList();
		}
		return list;
	}

	@Override
	public int addActivitiesGoods(ProductActivitiesGoods g) {
		String sql = "INSERT INTO `product_activities_goods`(`product_id`,`activitie_id`,`inpage_show_flag`)VALUES (?,?,?);";
		int i = jdbcTemplate.update(sql, g.getProductId(), g.getActivitieId(),
				g.getInpageShowFlag());
		return i ^ 1;
	}

	@Override
	public ProductActivitiesGoods findActivitiesGoodsById(String aid,
			String productId) {
		ProductActivitiesGoods p = null;
		String sql = "SELECT * FROM  product_activities_goods WHERE activitie_id = ? and product_id = ?";
		JuRowCallbackHandler<ProductActivitiesGoods> rch = new JuRowCallbackHandler<ProductActivitiesGoods>(
				ProductActivitiesGoods.class);
		jdbcTemplate.query(sql, rch, aid, productId);
		if (null != rch.getList() && rch.getList().size() > 0) {
			p = rch.getList().get(0);
		}
		return p;
	}

	@Override
	public int delProductActivitiesGoods(String aid, String productId) {
		String sql = "DELETE FROM  product_activities_goods WHERE activitie_id = ? and product_id = ?";
		// System.out.println("--- " + sql);
		int i = jdbcTemplate.update(sql, aid, productId);
		return i ^ 1;
	}

	@Override
	public ProductActivitiesImages listActivitiesImages(String aid, int type) {
		String sql = "SELECT * FROM `product_activities_images` WHERE activitie_id = ? AND type= ? ";
		ProductActivitiesImages img = null;
		JuRowCallbackHandler<ProductActivitiesImages> rch = new JuRowCallbackHandler<ProductActivitiesImages>(
				ProductActivitiesImages.class);
		jdbcTemplate.query(sql, rch, aid, type);
		if (rch.getList() != null && rch.getList().size() > 0) {
			img = rch.getList().get(0);
		}
		return img;
	}

	@Override
	public int addActivitiesImage(ProductActivitiesImages img) {
		String sql = "INSERT INTO `product_activities_images`(`activitie_id`,`image_url`,`type`)VALUES (?,?,?);";
		int i = jdbcTemplate.update(sql, img.getActivitieId(),
				img.getImageUrl(), img.getType());

		return i ^ 1;
	}

	@Override
	public int updateActivitiesImage(ProductActivitiesImages img) {
		String sql = "UPDATE `product_activities_images` SET `activitie_id` = ?,`image_url` = ?,`type` = ? WHERE `id` =  ?";
		int i = jdbcTemplate.update(sql, img.getActivitieId(),
				img.getImageUrl(), img.getType(), img.getId());
		return i ^ 1;
	}

	@Override
	public int delActivitiesImage(int id) {
		String sql = "DELETE FROM `product_activities_images` WHERE `id` =  ?";
		int i = jdbcTemplate.update(sql, id);
		return i ^ 1;
	}

	@Override
	public List<ProductActivitiesImages> indeximgList(String aid) {
		String sql = "SELECT * FROM `product_activities_images` WHERE activitie_id = ? AND (type= 1 or type = 2) ";
		List<ProductActivitiesImages> list = null;
		JuRowCallbackHandler<ProductActivitiesImages> rch = new JuRowCallbackHandler<ProductActivitiesImages>(
				ProductActivitiesImages.class);
		jdbcTemplate.query(sql, rch, aid);
		list = rch.getList();
		return list;

	}

	@Override
	public List<ProductActivitiesImages> innerimgList(String aid) {
		String sql = "SELECT * FROM `product_activities_images` WHERE activitie_id = ? AND (type= 3 or type = 4) ";
		List<ProductActivitiesImages> list = null;
		JuRowCallbackHandler<ProductActivitiesImages> rch = new JuRowCallbackHandler<ProductActivitiesImages>(
				ProductActivitiesImages.class);
		jdbcTemplate.query(sql, rch, aid);
		list = rch.getList();
		return list;
	}

	@Override
	public List<ProductActivitiesNotes> listNotes(String aid) {
		String sql = "SELECT * FROM `product_activities_notes` WHERE activitie_id = ? order by weight desc ";
		List<ProductActivitiesNotes> list = null;
		JuRowCallbackHandler<ProductActivitiesNotes> rch = new JuRowCallbackHandler<ProductActivitiesNotes>(
				ProductActivitiesNotes.class);
		jdbcTemplate.query(sql, rch, aid);
		list = rch.getList();
		return list;
	}

	@Override
	public int addActivitiesNotes(ProductActivitiesNotes notes) {
		String sql = "INSERT INTO `product_activities_notes`(`activitie_id`,`question`,`answer`,`weight`)VALUES (?,?,?,?);";

		int i = jdbcTemplate.update(sql, notes.getActivitieId(),
				notes.getQuestion(), notes.getAnswer(), notes.getWeight());
		return i ^ 1;
	}

	@Override
	public int updateActivitiesNotes(ProductActivitiesNotes notes) {
		String sql = "UPDATE `product_activities_notes` SET `activitie_id` = ?,`question` = ?,`answer` = ?,`weight` = ? WHERE `id` = ?";
		int i = jdbcTemplate.update(sql, notes.getActivitieId(),
				notes.getQuestion(), notes.getAnswer(), notes.getWeight(),
				notes.getId());
		return i ^ 1;
	}

	@Override
	public ProductActivitiesNotes findActivitiesNotes(int id) {
		ProductActivitiesNotes n = null;
		String sql = "SELECT * FROM `product_activities_notes` WHERE id = ?";
		JuRowCallbackHandler<ProductActivitiesNotes> rch = new JuRowCallbackHandler<ProductActivitiesNotes>(
				ProductActivitiesNotes.class);
		jdbcTemplate.query(sql, rch, id);
		if (rch.getList() != null && rch.getList().size() > 0) {
			n = rch.getList().get(0);
		}
		return n;
	}

	@Override
	public List<ProductActivitiesDownload> listActivitiesDownload(String aid) {
		List<ProductActivitiesDownload> list = null;
		String sql = "SELECT * FROM `product_activities_download` WHERE activitie_id = ?";
		JuRowCallbackHandler<ProductActivitiesDownload> rch = new JuRowCallbackHandler<ProductActivitiesDownload>(
				ProductActivitiesDownload.class);
		jdbcTemplate.query(sql, rch, aid);
		list = rch.getList();
		return list;
	}

	@Override
	public int addActivitiesDownload(ProductActivitiesDownload dl) {
		String sql = "INSERT INTO `product_activities_download`(`activitie_id`,`button_name`,`download_url`) VALUES (?,?,?);";
		int i = jdbcTemplate.update(sql, dl.getActivitieId(),
				dl.getButtonName(), dl.getDownloadUrl());
		return i ^ 1;
	}

	@Override
	public int updateActivitiesDownload(ProductActivitiesDownload dl) {
		String sql = "UPDATE `product_activities_download` SET `activitie_id` = ?,`button_name` = ?,`download_url` = ? WHERE `id` = ? ";
		int i = jdbcTemplate.update(sql, dl.getActivitieId(),
				dl.getButtonName(), dl.getDownloadUrl(), dl.getId());
		return i ^ 1;
	}

	@Override
	public int delActivitiesDownload(int id) {
		String sql = "DELETE FROM `product_activities_download` WHERE `id` = ?";
		int i = jdbcTemplate.update(sql, id);
		return i ^ 1;
	}

	@Override
	public ProductActivitiesDownload findActivitiesDownload(int id) {
		ProductActivitiesDownload download = null;
		String sql = "SELECT * FROM `product_activities_download` WHERE id = ?";
		JuRowCallbackHandler<ProductActivitiesDownload> rch = new JuRowCallbackHandler<ProductActivitiesDownload>(
				ProductActivitiesDownload.class);
		jdbcTemplate.query(sql, rch, id);
		if (rch.getList() != null && rch.getList().size() > 0) {
			download = rch.getList().get(0);
		}
		return download;
	}

	@Override
	public int updateActivitiesGoodsInpageShow(String aid, String productId) {

		String sql = "UPDATE `product_activities_goods` set  `inpage_show_flag` = 1 WHERE `activitie_id` = ? and product_id = ? ";
		int i = jdbcTemplate.update(sql, aid, productId);
		return i ^ 1;
	}

	@Override
	public int resetActivitiesGoodsInpageShow(String aid) {
		String resetSql = "UPDATE `product_activities_goods` set `inpage_show_flag` = 0 WHERE `activitie_id` = ? ";
		int i = jdbcTemplate.update(resetSql, aid);
		return i ^ 1;
	}

}
