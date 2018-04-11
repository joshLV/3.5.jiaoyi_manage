package cn.juhaowan.member.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.juhaowan.member.dao.WXUserSubcribeDao;

/**
 * 微信订阅
 * @author zhenby
 *
 */
@Repository("WXUserSubcribeDao")
public class WXUserSubcribeDaoImpl implements WXUserSubcribeDao {
	
	/**
	 * 数据库表字段名名称：订阅关系标识
	 */
	public static String COLUMN_NAME_ID = "id";
	
	/**
	 * 数据库表字段名名称：微信用户标识
	 */
	public static String COLUMN_NAME_WX_OPEN_ID = "wx_openid";
	
	/**
	 * 数据库表字段名名称：微信用户昵称
	 */
	public static String COLUMN_NAME_WX_NICKNAME = "wx_nickname";
	
	/**
	 * 数据库表字段名名称：微信头像地址
	 */
	public static String COLUMN_NAME_WX_HEAD_IMG_URL = "wx_headimgurl";
	
	/**
	 * 数据库表字段名名称：订阅状态
	 */
	public static String COLUMN_NAME_STAT = "stat";
	
	/**
	 * 数据库表字段名名称：创建时间
	 */
	public static String COLUMN_NAME_CREATE_TIME = "create_time";
	
	/**
	 * 数据库表字段名名称：修改时间
	 */
	public static String COLUMN_NAME_MODIFY_TIME = "modify_time";
	
	Logger logger = LoggerFactory.getLogger(WXUserSubcribeDaoImpl.class);
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	/**
	 * 读取存储的表名
	 * @return
	 */
	public String getTableName(){
		return "wx_user_subcribe";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isSubcribed(String wxOpenId) {
		if (wxOpenId.length() > 0){
			String sql = "select count(*) from " + getTableName() + " where " + COLUMN_NAME_WX_OPEN_ID + " = ?";
			logger.info(sql);
			if (jdbcOperations.queryForInt(sql, wxOpenId) == 1){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int newWXSubcribe(String wxOpenId, String wxNickname,
			String wxHeadImgUrl) {
		int result = -1;
		
		if (wxOpenId.length() > 0){
			if (!this.isSubcribed(wxOpenId)){
				//插入新订阅
				String sqlNewBind = String.format("INSERT INTO %s（%s, %s, %s, %s, %s, %s) VALUES(?, ?, ?, %d, now(), now())", 
												getTableName(), COLUMN_NAME_WX_OPEN_ID, COLUMN_NAME_WX_NICKNAME, 
												COLUMN_NAME_WX_HEAD_IMG_URL, COLUMN_NAME_STAT, COLUMN_NAME_CREATE_TIME, 
												COLUMN_NAME_MODIFY_TIME, SUBCRIBE_STAT_NORMAL);
				if (jdbcOperations.update(sqlNewBind, wxOpenId, wxNickname, wxHeadImgUrl) == 1){
					result = 0;
				}
			}
		}else{
			result = -2;
		}
		
		return result;
	}

	@Override
	public int subIdWithWXOpenId(String wxOpenId) {
		int subId = -1;
		if (wxOpenId.length() > 0){
			String sql = "select " + COLUMN_NAME_ID + " from " + getTableName() + " where " + COLUMN_NAME_WX_OPEN_ID + " = ?";
			logger.info(sql);
			
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, wxOpenId);
			
			if (rs.next()){
				subId = rs.getInt(1);
			}
		}else{
			subId = -2;
		}
		
		return subId;
	}
	
	@Override
	public String wxOpenIdWithSubId(int subId) {
		String wxOpenId = null;
		if (subId > 0){
			String sql = "select " + COLUMN_NAME_WX_OPEN_ID + " from " + getTableName() + " where " + COLUMN_NAME_ID + " = ?";
			logger.info(sql);
			
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, subId);
			
			if (rs.next()){
				wxOpenId = rs.getString(1);
			}
		}
		
		return wxOpenId;
	}

	@Override
	public String wxNickname(String wxOpenId) {
		String nickname = null;
		if (wxOpenId.length() > 0){
			String sql = "select " + COLUMN_NAME_WX_NICKNAME + " from " + getTableName() + " where " + COLUMN_NAME_WX_OPEN_ID + " = ?";
			logger.info(sql);
			
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, wxOpenId);
			if (rs.next()){
				nickname = rs.getString(1);
			}
		}
		
		return nickname;
	}

	@Override
	public String wxHeadImgUrl(String wxOpenId) {
		String headImgUrl = null;
		if (wxOpenId.length() > 0){
			String sql = "select " + COLUMN_NAME_WX_HEAD_IMG_URL + " from " + getTableName() + " where " + COLUMN_NAME_WX_OPEN_ID + " = ?";
			logger.info(sql);
			
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, wxOpenId);
			if (rs.next()){
				headImgUrl = rs.getString(1);
			}
		}
		
		return headImgUrl;
	}
}
