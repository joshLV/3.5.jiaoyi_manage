package cn.juhaowan.suggestion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.suggestion.service.SuggestionService;
import cn.juhaowan.suggestion.vo.Suggestion;

@Service("SuggestionService")
public class DefaultSuggestionService implements SuggestionService {
	
	@Autowired
	private JdbcOperations jdbcTemplate;
	//private static String SUGGESTION_DETAIL_KEY = "SUGGESTION_detail_key_";

	@Override
	public int addSuggestion(int userID, String content, int categoryID, String userGameID, String userQQ, String userEmail, String userPhone, int sourceType) {
		String sql = "insert suggestion(user_id,content,user_game_id,qq,email,mobile,source_type,category_id,createtime) values(?,?,?,?,?,?,?,?,NOW())";
		int i = jdbcTemplate.update(sql, userID, content, userGameID, userQQ, userEmail, userPhone, sourceType, categoryID);
		return i;
	}

	@Override
	public int changeSuggestionStatus(int suggestionID, int status) {
		String sql = "update suggestion set status=? where suggestion_id=?";
		int i = jdbcTemplate.update(sql, status, suggestionID);
		return i;
	}

	@Override
	public int deleteSuggestion(int suggestionID) {
		String sql = "update suggestion set status=? where suggestion_id=?";
		int i = jdbcTemplate.update(sql, -1, suggestionID);
		return i;
	}

	

	@Override
	public int updateRemarkRelated(int isHandled, String remark,int hUID,
			int suggestionId,Date handledtime) {
		String sql="update suggestion set is_handled=?,remark=?,handled_user_id=?,handled_time=? where suggestion_id=? ";
		int i=jdbcTemplate.update(sql, isHandled,remark,hUID,handledtime,suggestionId);
		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<Suggestion> querySuggestionList(int categoryID, int status,int handledUserID,int isHandled, int pageSize, int pageNo, String sort, String order,int source){
		if (null == sort || ("").equals(sort)) {
			sort = "createtime";
		}
		if (null == order) {
			order = "DESC";
		}
		List<Object> mConditionList = new ArrayList<Object>();
		String sql;
		if (status == -2) {
			sql = " from `suggestion` ";
		} else {
			sql = " from `suggestion` where status=? ";
			mConditionList.add(status);
		}

		if (categoryID > 0) {
			if (sql.contains("where")) {
				sql += " and category_id=? ";
				mConditionList.add(categoryID);
			} else {
				sql += " where category_id=? ";
				mConditionList.add(categoryID);
			}

		}
		if (handledUserID>0) {
			if (sql.contains("where")) {
				sql += " and handled_user_id=? ";
				mConditionList.add(handledUserID);
			} else {
				sql += " where handled_user_id=? ";
				mConditionList.add(handledUserID);
			}
		}
		if (isHandled>=0) {
			if (sql.contains("where")) {
				sql += " and is_handled=? ";
				mConditionList.add(isHandled);
			} else {
				sql += " where is_handled=? ";
				mConditionList.add(isHandled);
			}
		}
		if (source != -1) {
			if (sql.contains("where")) {
				sql += " and source=? ";
				mConditionList.add(source);
			} else {
				sql += " where source=? ";
				mConditionList.add(source);
			}
		}
		String sqlCount = "select count(suggestion_id) " + sql;
		int rowcount = jdbcTemplate.queryForInt(sqlCount, mConditionList.toArray());
		PageInfo<Suggestion> mPageInfo = new PageInfo<Suggestion>(rowcount, pageSize);
		mPageInfo.setRecordCount(rowcount);
		mPageInfo.setPageno(pageNo);

		if (rowcount == 0)
			return mPageInfo;

		if (pageNo <= 0)
			pageNo = 1;

		if (pageNo > mPageInfo.getPageCount())
			pageNo = mPageInfo.getPageCount();

		int firstResult = (pageNo - 1) * mPageInfo.getPageSize();
		if (firstResult < 0)
			firstResult = 0;

		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order + " LIMIT ?,?";
		
		mConditionList.add(firstResult);
		mConditionList.add(mPageInfo.getPageSize());
		JuRowCallbackHandler<Suggestion> callback = new JuRowCallbackHandler<Suggestion>(Suggestion.class);
		jdbcTemplate.query(sqlQuery, callback,mConditionList.toArray());
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}


	@Override
	public Suggestion getSuggestionDetail(int suggestionID) {
		Suggestion mSuggestion = null;
		String sql = "select * from suggestion where suggestion_id = ?";
		JuRowCallbackHandler<Suggestion> callback = new JuRowCallbackHandler<Suggestion>(Suggestion.class);
		jdbcTemplate.query(sql, callback, suggestionID);
		if (callback.getList().size() < 1) {
			return null;
		}
		mSuggestion = callback.getList().get(0);
		return mSuggestion;
	}
	
	

}
