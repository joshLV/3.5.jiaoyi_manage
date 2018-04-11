package cn.juhaowan.helpinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.helpinfo.service.HelpInfoService;
import cn.juhaowan.helpinfo.vo.HelpCategory;
import cn.juhaowan.helpinfo.vo.HelpInfo;

/**
 * 帮助管理实现
 * 
 */
@Service("HelpInfoService")
public class DefaultHelpInfoService implements HelpInfoService {
	@Autowired
	private JdbcOperations jdbcTemplate;

	private static final String HELPINFO_LIST_KEY = "helpinfo_all_list_key";
	private static final String HELPINFO_CATEGORY_LIST_KEY = "helpinfo_catgory_list_key";
	private static final String HELPINFO_LIST_BY_CATEGORY_KEY = "helpinfo_list_by_category_key_";
	private static final String HELPINFO_DETAIL_KEY = "helpinfo_detail_key_";
	private static final String HELP_CATEGORY_LIST_KEY = "help_category_list_key";

	private static final String HELPINFO_LIST_BY_CATEGORYID_KEY = "helpinfo_list_by_categoryid_key";

	private static final String HELPINFO_LIST_BY_WEIGHT_KEY = "helpinfo_list_by_weight_key";

	@Override
	public int addHelpCategory(String categoryName, int weight ,int isShow) {
		String sql = "insert help_category(category_name,weight,is_show) values(?,?,?)";
		int i = jdbcTemplate.update(sql, categoryName, weight,isShow);
		return i;
	}

	@Override
	public int addHelpContent(int categoryID, String helpTitle, String contentText) {
		String sql = "insert help_info(help_title,help_content,category_id,createtime) values(?,?,?,NOW())";
		int i = jdbcTemplate.update(sql, helpTitle, contentText, categoryID);
		return i;
	}

	@Override
	public int deleteHelpCategory(int categoryID, boolean isForce) {
		int effectRow = 1;
		String sqlcount = "select * from help_info where category_id=?";
		int count = jdbcTemplate.queryForList(sqlcount, categoryID).size();
		if (count > 0) {
			if (isForce) {
				String sql = "delete from help_info where category_id = ?";
				jdbcTemplate.update(sql, categoryID);
				sql = "delete from help_category where help_category_id = ?";
				effectRow = jdbcTemplate.update(sql, categoryID);
				if (effectRow > 0) {
					return 0;
				}
			} else {
				return 1;
			}
		} else {
			String sql = "delete from help_category where help_category_id = ?";
			effectRow = jdbcTemplate.update(sql, categoryID);
			if (effectRow > 0) {
				return 0;
			}
		}
		return effectRow;
	}

	@Override
	public int deleteHelpContent(int helpInfoID) {
		String sql = "delete from help_info where help_id=?";
		int i = jdbcTemplate.update(sql, helpInfoID);
		return i;
	}

	@Override
	public HelpInfo getHelpContentByID(int helpID) {
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		String sql = "select * from help_info where help_id=? and is_show = 1";
		jdbcTemplate.query(sql, callback, helpID);
		HelpInfo mHelpInfo = null;
		if (callback.getList() != null && callback.getList().size( ) > 0) {
			mHelpInfo = callback.getList().get(0);
		}
		return mHelpInfo;
	}
	
	

	@Override
	public HelpInfo getHelpContentById(int helpID) {
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		String sql = "select * from help_info where help_id=?";
		jdbcTemplate.query(sql, callback, helpID);
		HelpInfo mHelpInfo = null;
		if (callback.getList() != null && callback.getList().size( ) > 0) {
			mHelpInfo = callback.getList().get(0);
		}
		return mHelpInfo;
	}

	@Override
	public HelpInfo getHelpContentByIDFront(int helpID) {
		if (Cache.get(HELPINFO_DETAIL_KEY + helpID) != null) {
			return (HelpInfo) Cache.get(HELPINFO_DETAIL_KEY + helpID);
		}
		HelpInfo mHelpInfo = getHelpContentByID(helpID);
		Cache.set(HELPINFO_DETAIL_KEY + helpID, 600, mHelpInfo);
		return mHelpInfo;
	}

	@Override
	public HelpInfo getHelpContentByTagDb(String tag) {
		JuRowCallbackHandler<HelpInfo> cb = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		String sql = "SELECT * FROM help_info WHERE tag=? and is_show = 1";
		jdbcTemplate.query(sql, cb, tag);
		HelpInfo tagHelpInfo = null;
		if (cb.getList() != null && cb.getList().size() > 0) {
			tagHelpInfo = cb.getList().get(0);
		}
		return tagHelpInfo;
	}

	@Override
	public HelpInfo getHelpContentByTag(String tag) {
		String cacheKey = HELPINFO_DETAIL_KEY + "tag_" + tag;
		if (Cache.get(cacheKey) != null) {
			return (HelpInfo) Cache.get(cacheKey);
		}
		HelpInfo tagHelpInfo = getHelpContentByTagDb(tag);
		if (tagHelpInfo != null) {
			Cache.set(cacheKey, 600, tagHelpInfo);
		}
		return tagHelpInfo;
	}

	@Override
	public int modifyHelpCategory(int categoryID, String categoryName, int categoryWeight, int isShow) {
		String sql = "update help_category set category_name=?,weight=?,is_show=? where help_category_id=?";
		int i = jdbcTemplate.update(sql, categoryName, categoryWeight, isShow, categoryID);
		return i;
	}

	@Override
	public List<HelpCategory> queryCategoryList(String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from help_category order by " + sort + " " + order;
		JuRowCallbackHandler<HelpCategory> callback = new JuRowCallbackHandler<HelpCategory>(HelpCategory.class);
		jdbcTemplate.query(sqlQuery, callback);
		List<HelpCategory> returnList = callback.getList();
		return returnList;
	}

	@Override
	public List<HelpCategory> queryCategoryListFront(String sort, String order) {
		if (Cache.get(HELPINFO_CATEGORY_LIST_KEY) != null) {
			return (List<HelpCategory>) Cache.get(HELPINFO_CATEGORY_LIST_KEY);
		}
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from help_category where is_show = 1 order by " + sort + " " + order;
		JuRowCallbackHandler<HelpCategory> callback = new JuRowCallbackHandler<HelpCategory>(HelpCategory.class);
		jdbcTemplate.query(sqlQuery, callback);
		List<HelpCategory> returnList = callback.getList();
		Cache.set(HELPINFO_CATEGORY_LIST_KEY, 600, returnList);
		return returnList;
	}

	@Override
	public PageInfo<HelpCategory> queryCategoryList(int pageSize, int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from help_category";
		String sqlcount = "select count(help_category_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount);
		PageInfo<HelpCategory> mPageInfo = new PageInfo<HelpCategory>("", rowcount, pageSize);
		// String sqlQuery = "select * " + sql + " order by ? ? limit ?,?";
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		JuRowCallbackHandler<HelpCategory> callback = new JuRowCallbackHandler<HelpCategory>(HelpCategory.class);
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
	public PageInfo<HelpInfo> queryHelpList(int pageSize, int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from help_info";
		String sqlcount = "select count(help_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount);
		PageInfo<HelpInfo> mPageInfo = new PageInfo<HelpInfo>("", rowcount, pageSize);
		// String sqlQuery = "select * " + sql + " order by ? ? limit ?,?";
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
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
	public PageInfo<HelpInfo> queryHelpListByCategoryID(int categoryID, int pageSize, int pageNo, String sort,
			String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from help_info where category_id = ?";
		String sqlcount = "select count(help_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount, categoryID);
		PageInfo<HelpInfo> mPageInfo = new PageInfo<HelpInfo>("", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY ? ? limit ?,?";
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, categoryID, sort, order, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@Override
	public PageInfo<HelpInfo> queryHelpListByCategoryIDFront(int categoryID, int pageSize, int pageNo, String sort,
			String order) {
		if (Cache.get(HELPINFO_LIST_BY_CATEGORY_KEY + categoryID) != null) {
			return (PageInfo<HelpInfo>) Cache.get(HELPINFO_LIST_BY_CATEGORY_KEY + categoryID);
		}
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from help_info where category_id = ? and is_show = 1";
		String sqlcount = "select count(help_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount, categoryID);
		PageInfo<HelpInfo> mPageInfo = new PageInfo<HelpInfo>("", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY ? ? limit ?,?";
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, categoryID, sort, order, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		Cache.set(HELPINFO_LIST_BY_CATEGORY_KEY + categoryID, 600, mPageInfo);
		return mPageInfo;
	}

	@Override
	public List<Object> QueryAllHelpListFront() {
		if (Cache.get(HELPINFO_LIST_KEY) != null) {
			return (List<Object>) Cache.get(HELPINFO_LIST_KEY);
		}
		List<Object> returnList = new ArrayList<Object>();
		List<HelpCategory> category_list = queryCategoryList(null, null);
		if (category_list.size() > 0) {
			for (HelpCategory category : category_list) {
				PageInfo<HelpInfo> help_list = queryHelpListByCategoryID(category.getHelpCategoryId(), 500, 1,
						"createtime", "desc");
				if (help_list.getItems().size() > 0) {
					returnList.add(category);
					for (HelpInfo helpinfo : help_list.getItems()) {
						returnList.add(helpinfo);
					}
				}
			}
			Cache.set(HELPINFO_LIST_KEY, 600, returnList);
			return returnList;
		}
		return null;
	}

	@Override
	public int queryCategoryNameIsExist(String categoryName) {
		String sql = "SELECT * FROM help_category WHERE category_name = ?";
		JuRowCallbackHandler<HelpCategory> cb = new JuRowCallbackHandler<HelpCategory>(HelpCategory.class);
		jdbcTemplate.query(sql, cb, categoryName);
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId) {
		String sql = "update help_info set help_title=?,help_content=?,category_id=? where help_id=?";
		int i = jdbcTemplate.update(sql, helpTitle, helpContent, categoryId, helpId);
		return i;
	}

	/*
	 */
	@Override
	public List<HelpCategory> queryCategoryListCache(String sort, String order) {
		if (Cache.get(HELP_CATEGORY_LIST_KEY) != null) {
			return Cache.get(HELP_CATEGORY_LIST_KEY);
		}
		List<HelpCategory> helpCategoryList = queryCategoryList(sort, order);
		if (!helpCategoryList.isEmpty()) {
			Cache.set(HELP_CATEGORY_LIST_KEY, 600, helpCategoryList);
			return helpCategoryList;
		}
		return null;
	}

	/*
	 */
	@Override
	public PageInfo<HelpInfo> queryHelpListByCategoryIDCache(int categoryID, int pageSize, int pageNo, String sort,
			String order) {
		if (Cache.get(HELPINFO_LIST_BY_CATEGORYID_KEY) != null) {
			return Cache.get(HELPINFO_LIST_BY_CATEGORYID_KEY);
		}
		PageInfo<HelpInfo> helpInfoList = queryHelpListByCategoryIDCache(categoryID, pageSize, pageNo, sort, order);
		if (!helpInfoList.getItems().isEmpty()) {
			Cache.set(HELPINFO_LIST_BY_CATEGORYID_KEY, 600, helpInfoList);
			return helpInfoList;
		}
		return null;
	}

	/**
	 * 按常见度排序查询
	 */
	public PageInfo<HelpInfo> findHelpInfoByWeightListCache(int pageSize, int pageNo, String sort, String order) {
		if (Cache.get(HELPINFO_LIST_BY_WEIGHT_KEY) != null) {
			return Cache.get(HELPINFO_LIST_BY_WEIGHT_KEY);
		}
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from help_info";
		String sqlcount = "select count(help_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlcount);
		PageInfo<HelpInfo> mPageInfo = new PageInfo<HelpInfo>("", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + ",weight " + order + " LIMIT ?,?";
		JuRowCallbackHandler<HelpInfo> callback = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		int startNum = 0;
		if (pageNo <= 0) {
			startNum = pageNo * pageSize;
		} else {
			startNum = (pageNo - 1) * pageSize;
		}
		jdbcTemplate.query(sqlQuery, callback, startNum, pageSize);
		mPageInfo.setItems(callback.getList());
		if (!mPageInfo.getItems().isEmpty()) {
			Cache.set(HELPINFO_LIST_BY_WEIGHT_KEY, 600, mPageInfo);
			return mPageInfo;
		}
		return null;
	}

	@Override
	public int addHelpContent(int categoryID, String helpTitle, String contentText, int weight) {
		String sql = "insert help_info(help_title, help_content, category_id, createtime, weight) values(?,?,?,NOW(),?)";
		int i = jdbcTemplate.update(sql, helpTitle, contentText, categoryID, weight);
		return i;
	}

	@Override
	public int addHelpContent(int categoryID, String helpTitle, String contentText, int weight, String tag,int isShow) {
		String sql = "insert help_info(help_title, help_content, category_id, createtime, weight,tag,is_show) values(?,?,?,NOW(),?,?,?)";
		int i = jdbcTemplate.update(sql, helpTitle, contentText, categoryID, weight, tag,isShow);
		return i;
	}

	@Override
	public int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId, int helpWeight,int isShow) {
		String sql = "update help_info set help_title=?,help_content=?,category_id=?,weight=?,is_show=? where help_id=?";
		int i = jdbcTemplate.update(sql, helpTitle, helpContent, categoryId, helpWeight,isShow, helpId);
		return i;
	}

	@Override
	public int modifyHelpInfo(Integer helpId, String helpTitle, String helpContent, Integer categoryId, int helpWeight,
			String tag,int isShow) {
		String sql = "update help_info set help_title=?,help_content=?,category_id=?,weight=?,tag=?,is_show=? where help_id=?";
		int i = jdbcTemplate.update(sql, helpTitle, helpContent, categoryId, helpWeight, tag, isShow, helpId);
		return i;
	}

	@Override
	public int queryTagIsExist(int helpId,String tag) {
		String sql;
		if(helpId==0){
			sql = "SELECT * FROM help_info WHERE tag = ?";
		}else{
			sql = "SELECT * FROM help_info WHERE tag = ? and help_id != ?";
		}
		JuRowCallbackHandler<HelpInfo> cb = new JuRowCallbackHandler<HelpInfo>(HelpInfo.class);
		if(helpId==0){
			jdbcTemplate.query(sql, cb, tag);
		}else{
			jdbcTemplate.query(sql, cb, tag,helpId);
		}
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
