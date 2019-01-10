package cn.juhaowan.announcement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.announcement.service.AnnouncementService;
import cn.juhaowan.announcement.vo.Announcement;
import cn.juhaowan.announcement.vo.AnnouncementCategory;

/**
 *
 */
@Service("AnnouncementService")
public class DefaultAnnouncementService implements AnnouncementService {
	@Autowired
	private JdbcOperations jdbcTemplate;

	private String recentAnnouncementKey = "recentAnnouncementKey";
	private String announcementDetailKey = "announcementDetailKey_";

	@Override
	public int addAnnouncement(String title, String template, String content,
			String validTime, int categoryId, String sendedTime,int releasePlatform,String url) {
		int i;
		if (sendedTime != null) {
			String sql = "insert announcement(title,content,createtime,validdate,category_id,sendedTime,status,template,release_platform,url) values(?,?,NOW(),DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'),?,?,2,?,?,?)";
			i = jdbcTemplate.update(sql, title, content, validTime, categoryId,
					sendedTime, template,releasePlatform,url);
		} else {
			String sql = "insert announcement(title,content,createtime,validdate,category_id,sendedTime,template,release_platform,url) values(?,?,NOW(),DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'),?,NOW(),?,?,?)";
			i = jdbcTemplate.update(sql, title, content, validTime, categoryId,
					template,releasePlatform,url);
		}
		return i;
	}

	@Override
	public int deleteAnnouncement(int announcementID) {
		String sql = "update announcement set status = 1 where announcement_id = ?";
		int i = jdbcTemplate.update(sql, announcementID);
		return i;
	}

	@Override
	public Announcement getAnnouncementDetail(int announcementID) {
		Announcement mAnnouncement = null;
		if ((mAnnouncement = Cache.get(announcementDetailKey
				+ String.valueOf(announcementID))) != null) {
			return mAnnouncement;
		}
		String sql = "select * from announcement where announcement_id = ?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(
				Announcement.class);
		jdbcTemplate.query(sql, callback, announcementID);
		if (callback.getList().size() < 1) {
			return null;
		}
		mAnnouncement = callback.getList().get(0);
		Cache.add(announcementDetailKey + String.valueOf(announcementID), 300,
				mAnnouncement);
		return mAnnouncement;
	}

	@Override
	public Announcement getAnnouncementDetailForManage(int announcementID) {
		Announcement mAnnouncement = null;
		String sql = "select * from announcement where announcement_id = ?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(
				Announcement.class);
		jdbcTemplate.query(sql, callback, announcementID);
		if (callback.getList().size() < 1) {
			return null;
		}
		mAnnouncement = callback.getList().get(0);
		return mAnnouncement;
	}

	@Override
	public PageInfo<Announcement> queryAnnouncementList(String title,int status,int pageSize, int pageNo, String sort, String order,String url,String startTime,String endTime,int platform) {
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from announcement where status=? ";
		if(!StringUtils.isEmpty(title)){
			sql += " AND title like '%"+ title +"%' ";
		}
		if(platform != 3 ){
			sql += " AND release_platform = " + platform + " ";
		}
		if(!StringUtils.isEmpty(url)){
			sql += " AND url like '%"+ url +"%' ";
		}
		if(!StringUtils.isEmpty(startTime)){
			sql += " AND validdate > '"+ startTime +" 00:00:00' ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " AND validdate < '"+ endTime +" 23:59:59' ";
		}
		String sqlCount = "select count(announcement_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlCount, status);
		PageInfo<Announcement> mPageInfo = new PageInfo<Announcement>("",rowcount, pageSize);
		String sqlQuery = "select * " + sql + " order by " + sort + " " + order + " limit ?,?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(Announcement.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, status, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public PageInfo<Announcement> queryAnnouncementListFont(int pageSize,
			int pageNo) {
		String sql = "from announcement where status=0 and validdate >NOW()";
		String sqlCount = "select count(announcement_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlCount);
		PageInfo<Announcement> mPageInfo = new PageInfo<Announcement>("",
				rowcount, pageSize);
		String sqlQuery = "select * " + sql
				+ " order by announcement_id desc limit ?,?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(
				Announcement.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public List<Announcement> queryRecentAnnouncementList(int count) {
		List<Announcement> rtnList = null;
		// 取缓存数据
		if ((rtnList = Cache.get(recentAnnouncementKey)) != null) {
			return rtnList;
		}
		String sql = "from announcement where validdate >NOW() AND status=0";
		String sqlQuery = "select * " + sql
				+ " order by createtime desc limit 0,?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(
				Announcement.class);
		jdbcTemplate.query(sqlQuery, callback, count);
		if (callback.getList().size() > 0) {
			Cache.add(recentAnnouncementKey, 300, callback.getList());
			return callback.getList();
		}
		return null;
	}

	@Override
	public PageInfo<Announcement> queryAnnouncemntListByCategoryID(
			int categoryID, int pageSize, int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from announcement where category_id = ?";
		String sqlcount = "select count(announcement_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount, categoryID);
		PageInfo<Announcement> mPageInfo = new PageInfo<Announcement>("",
				rowcount, pageSize);
		String sqlQuery = "select * " + sql + " order by " + sort + " " + order
				+ " limit ?,?";
		JuRowCallbackHandler<Announcement> callback = new JuRowCallbackHandler<Announcement>(
				Announcement.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, categoryID, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public int addAnnoCategory(String categoryName, int weight) {
		String sql = "insert announcement_category(category_name,weight) values(?,?)";
		int i = jdbcTemplate.update(sql, categoryName, weight);
		return i;
	}

	@Override
	public int deleteAnnoCategory(int categoryID, boolean isForce) {
		int effectRow = 1;
		String sqlcount = "select * from announcement where category_id=?";
		int count = jdbcTemplate.queryForList(sqlcount, categoryID).size();
		if (count > 0) {
			if (isForce) {
				String sql = "delete from announcement where category_id = ?";
				jdbcTemplate.update(sql, categoryID);
				sql = "delete from announcement_category where announcement_category_id = ?";
				effectRow = jdbcTemplate.update(sql, categoryID);
				if (effectRow > 0) {
					return 0;
				}
			} else {
				return 1;
			}
		} else {
			String sql = "delete from announcement_category where announcement_category_id = ?";
			effectRow = jdbcTemplate.update(sql, categoryID);
			if (effectRow > 0) {
				return 0;
			}
		}
		return effectRow;
	}

	@Override
	public int modifyAnnoCategory(int categoryID, String categoryName,
			int categoryWeight) {
		String sql = "update announcement_category set category_name=?,weight=? where announcement_category_id=?";
		int i = jdbcTemplate.update(sql, categoryName, categoryWeight,
				categoryID);
		return i;
	}

	@Override
	public PageInfo<AnnouncementCategory> queryCategoryList(int pageSize,
			int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from announcement_category";
		String sqlcount = "select count(announcement_category_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount);
		PageInfo<AnnouncementCategory> mPageInfo = new PageInfo<AnnouncementCategory>(
				"", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " order by " + sort + " " + order
				+ " limit ?,?";
		JuRowCallbackHandler<AnnouncementCategory> callback = new JuRowCallbackHandler<AnnouncementCategory>(
				AnnouncementCategory.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public List<AnnouncementCategory> queryCategoryList(String sort,
			String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "select * from announcement_category order by " + sort
				+ " " + order;
		JuRowCallbackHandler<AnnouncementCategory> callback = new JuRowCallbackHandler<AnnouncementCategory>(
				AnnouncementCategory.class);
		jdbcTemplate.query(sql, callback);
		List<AnnouncementCategory> returnList = callback.getList();
		return returnList;
	}

	@Override
	public int queryCategoryNameIsExist(String categoryName) {
		String sql = "SELECT * FROM announcement_category WHERE category_name = ?";
		JuRowCallbackHandler<AnnouncementCategory> cb = new JuRowCallbackHandler<AnnouncementCategory>(
				AnnouncementCategory.class);
		jdbcTemplate.query(sql, cb, categoryName);
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifyAnnouncement(Integer announcementID, String title,
			String template, String content, String sendedTime,
			String validdate, int categoryId, Integer status, int releasePlatform,String url ) {
		int i;
		if (sendedTime != null) {
			String sql = "update announcement set title=?,template=?,content=?,sendedTime=?,validdate=?,status=?,category_id=?,release_platform=?,url=? where announcement_id=?";
			i = jdbcTemplate.update(sql, title, template, content, sendedTime,
					validdate, status, categoryId,releasePlatform ,url,announcementID);
		} else {
			String sql = "update announcement set title=?,template=?,content=?,validdate=?,status=?,category_id=? ,release_platform=?,url=? where announcement_id=?";
			i = jdbcTemplate.update(sql, title, template, content, validdate,
					status, categoryId, releasePlatform,url,announcementID);
		}
		return i;
	}
}
