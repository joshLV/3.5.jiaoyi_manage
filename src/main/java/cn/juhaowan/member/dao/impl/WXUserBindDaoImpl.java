package cn.juhaowan.member.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.juhaowan.member.dao.WXUserBindDao;
import cn.juhaowan.member.dao.WXUserSubcribeDao;

/**
 * 微信账户绑定
 * @author zhenby
 *
 */
@Repository("WXUserBindDao")
public class WXUserBindDaoImpl implements WXUserBindDao {

	/**
	 * 数据库表字段名名称：8868用户标识
	 */
	public static String COLUMN_NAME_UID = "uid";
	
	/**
	 * 数据库表字段名名称：订阅表(wx_user_subcribe)中的标识
	 */
	public static String COLUMN_NAME_SUB_ID = "sub_id";
	
	/**
	 * 数据库表字段名名称：绑定状态
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
	
	Logger logger = LoggerFactory.getLogger(WXUserBindDaoImpl.class);
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Autowired
	private WXUserSubcribeDao daoSubcribe;
	
	/**
	 * 读取存储的表名
	 * @return
	 */
	public String getTableName(){
		return "wx_user_bind";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isWXOpenIdBinded(String wxOpenId) {
		if (wxOpenId.length() > 0){
			int subId = daoSubcribe.subIdWithWXOpenId(wxOpenId);
			
			if (subId > 0){
				String sql = "select count(*) from " + getTableName() + " where " + COLUMN_NAME_SUB_ID + " = ? AND stat = 1";
				logger.info(sql);
				if (jdbcOperations.queryForInt(sql, subId) == 1){
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isUidBinded(int uid) {
		if (uid > 0){
			String sql = "select count(*) from " + getTableName() + " where " + COLUMN_NAME_UID + " = ? AND stat = 1";
			logger.info(sql);
			if (jdbcOperations.queryForInt(sql, uid) == 1){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int newWXBind(String wxOpenId, int uid) {
		int result = -1;
		if (wxOpenId.length() > 0 && uid > 0)
		{
			int subId = daoSubcribe.subIdWithWXOpenId(wxOpenId);

			if (subId > 0){
				String sqlHavedBind = "select count(*) from " + getTableName() + " where " + COLUMN_NAME_SUB_ID + " = ? and " + COLUMN_NAME_UID + " = ? AND stat = 1";
				logger.info("havedBind:" + sqlHavedBind);
				//判断是否已经存在绑定关系
				if(jdbcOperations.queryForInt(sqlHavedBind, subId, uid) == 0) {
					//创建绑定关系
					String sqlNewBind = String.format("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES(?, ?, %d, now(), now()) ON DUPLICATE KEY UPDATE stat = 1", 
													getTableName(), COLUMN_NAME_UID, COLUMN_NAME_SUB_ID, COLUMN_NAME_STAT, 
													COLUMN_NAME_CREATE_TIME, COLUMN_NAME_MODIFY_TIME, BIND_STAT_NORMAL);
					logger.info("sqlNewBind:" + sqlNewBind + ", uid:" + uid + ", subId:" +subId);
					if (jdbcOperations.update(sqlNewBind, uid, subId) > 0){
						logger.info("new succ");
						result = 0;
					}
				}
			}
		}else{
			result = -2;
		}
		
		return result;
	}
	

	@Override
	public int subIdWithUid(int uid) {
		int subId = -1;
		if (uid > 0){
			String sql = "select " + COLUMN_NAME_SUB_ID + " from " + getTableName() + " where " + COLUMN_NAME_UID + " = ?";
			logger.info(sql);
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
			if (rs.next()){
				subId = rs.getInt(1);
			}
		}else{
			subId = -2;
		}
		return subId;
	}

	@Override
	public int uidWithWXOpenId(String wxOpenId) {
		int uid = -1;
		if (wxOpenId.length() > 0){
			int subId = daoSubcribe.subIdWithWXOpenId(wxOpenId);
			
			if (subId > 0){
				String sql = "select " + COLUMN_NAME_UID + " from " + getTableName() + " where " + COLUMN_NAME_SUB_ID + " = ?";
				logger.info(sql);
				SqlRowSet rs = jdbcOperations.queryForRowSet(sql, subId);
				if (rs.next()){
					uid = rs.getInt(1);
				}
			}
		}else{
			uid = -2;
		}
		return uid;
	}
	
	@Override
	public String wxOpenIdWithUid(int uid) {
		String wxOpenId = null;
		if (uid > 0){
			int subId = subIdWithUid(uid);
			wxOpenId = daoSubcribe.wxOpenIdWithSubId(subId);
		}else{
			uid = -2;
		}
		
		return wxOpenId;
	}

}
