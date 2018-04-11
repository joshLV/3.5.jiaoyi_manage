package cn.juhaowan.localmessage.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.Cache;
import cn.jugame.util.GlobalsKeys;
import cn.jugame.util.PageInfo;
import cn.juhaowan.localmessage.service.LocalMessageService;
import cn.juhaowan.localmessage.vo.Localmessage;
import cn.juhaowan.localmessage.vo.MessageContent;
import cn.juhaowan.localmessage.vo.MessageMission;
import cn.juhaowan.localmessage.vo.MessageMissionCategory;
import cn.juhaowan.member.service.MemberService;

@Service("LocalMessageService")
public class DefaultLocalMessageService implements LocalMessageService {
	@Autowired
	private JdbcOperations jdbcTemplate;

	@Autowired
	private MemberService memberService;

	private static final String MESSAGE_CONTENT_KEY = "message_content_";
	private static final String NEW_MESSAGE_COUNT_KEY = "new_user_localmessage_count_key_";

	private static final String MessageMissionCategory_LIST_KEY = "message_mission_category_list_key";

	@Deprecated
	@Override
	public int doSendMessage() {
		JuRowCallbackHandler<MessageMission> mCallBack = new JuRowCallbackHandler<MessageMission>(
				MessageMission.class);
		String sql = "select * from message_mission where flag=0 and status=0";
		// log.info("do send message:" + sql);
		jdbcTemplate.query(sql, mCallBack);
		List<MessageMission> mMissionList = mCallBack.getList();

		// log.info("mMissionList:" + mMissionList.size());
		if (mMissionList.size() < 1) {
			return 0;
		}
		int flag = 0;
		for (MessageMission mMission : mMissionList) {
			int contentid = insertMessageContent(mMission.getMessageContent());
			if (mMission.getReceiver() != null
					&& mMission.getReceiver().equals("0")) {
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.EXCUTESEND);
				List<Integer> member_id_list = memberService.queryAllMemberID();
				for (int mReceiver : member_id_list) {
					flag = insertMessage(mMission.getMessageTitle(), contentid,
							mReceiver);
				}
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.DONESEND);
			} else {
				String[] mReceiverArray = mMission.getReceiver().split(",");
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.EXCUTESEND);
				for (String mReceiver : mReceiverArray) {
					int mReceiverInt = Integer.parseInt(mReceiver);
					flag = insertMessage(mMission.getMessageTitle(), contentid,
							mReceiverInt);
				}
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.DONESEND);
			}
		}
		return flag;
	}

	/**
	 * update_version doSendMessage()
	 */
	@Override
	public int sendMessageMission() {
		String nowDate = getNowString();
		JuRowCallbackHandler<MessageMission> mCallBack = new JuRowCallbackHandler<MessageMission>(
				MessageMission.class);
		String sql = "select * from message_mission where status=0 and sendtime<=? and flag=0 order by createtime desc";
		jdbcTemplate.query(sql, mCallBack, nowDate);
		List<MessageMission> mMissionList = mCallBack.getList();

		if (mMissionList.size() < 1) {
			return 0;
		}
		int flag = 0;
		for (MessageMission mMission : mMissionList) {
			int contentid = insertMessageContent(mMission.getMessageContent());
			if (mMission.getReceiver() != null
					&& mMission.getReceiver().equals("0")) {
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.EXCUTESEND);
				List<Integer> member_id_list = memberService.queryAllMemberID();
				for (int mReceiver : member_id_list) {
					flag = insertMessage(mMission.getMessageTitle(), contentid,
							mReceiver, mMission.getMmcId());
				}
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.DONESEND);
			} else {
				String[] mReceiverArray = mMission.getReceiver().split(",");
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.EXCUTESEND);
				for (String mReceiver : mReceiverArray) {
					int mReceiverInt = Integer.parseInt(mReceiver);
					flag = insertMessage(mMission.getMessageTitle(), contentid,
							mReceiverInt, mMission.getMmcId());
				}
				updateMissionStatus(mMission.getMessageMissionId(),
						LocalMessageService.DONESEND);
			}
		}
		return flag;
	}

	private int updateMissionStatus(int missionID, int status) {
		String sql = "update message_mission set status=?,sendedtime=NOW() where message_mission_id=?";
		int i = jdbcTemplate.update(sql, status, missionID);
		return i;
	}

	private int insertMessageContent(String content) {
		MessageContent mMessageContent = new MessageContent();
		mMessageContent.setMessageText(content);
		BaseDaoImplJdbc<MessageContent> mBaseDao = new BaseDaoImplJdbc<MessageContent>();
		mBaseDao.setJdbcTemplate(jdbcTemplate);
		mBaseDao.insert(mMessageContent);
		return mMessageContent.getMessageContentId();
	}

	@Override
	public String getMessageContentByID(int contentID) {
		if (contentID < 0) {
			return null;
		}
		String messageContent = "";
		String key = MESSAGE_CONTENT_KEY + String.valueOf(contentID);
		if ((messageContent = Cache.get(key)) != null) {
			return messageContent;
		} else {
			JuRowCallbackHandler<MessageContent> callback = new JuRowCallbackHandler<MessageContent>(
					MessageContent.class);
			String sql = "select * from message_content where message_content_id=?";
			jdbcTemplate.query(sql, callback, contentID);
			if (callback.getList().size() > 0) {
				messageContent = callback.getList().get(0).getMessageText();
			} else {
				messageContent = null;
			}
			return messageContent;
		}

	}

	@Deprecated
	public int insertMessage(String title, int contentid, int receiverid) {
		String sql = "insert localmessage(message_content_id,message_title,user_id,receive_time,status) values (?,?,?,NOW(),?)";
		int i = jdbcTemplate.update(sql, contentid, title, receiverid, 0);
		return i;
	}

	/**
	 * update_version
	 */
	public int insertMessage(String title, int contentid, int receiverid,
			int mmcId) {
		String sql = "insert localmessage(message_content_id,message_title,user_id,receive_time,status,mmc_id) values (?,?,?,NOW(),?,?)";
		int i = jdbcTemplate
				.update(sql, contentid, title, receiverid, 0, mmcId);
		return i;
	}

	private String getNowString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	@Override
	public int changeUserMessageStatus(int[] messageID, int status) {
		int i = 0;
		for (int msgid : messageID) {
			String sql = "update localmessage set status=? where message_id=?";
			i += jdbcTemplate.update(sql, status, msgid);
		}
		return i;
	}

	@Override
	public int deleteSendMission(int missionID) {
		String sql = "update message_mission set flag=1 where message_mission_id=?";
		int i = jdbcTemplate.update(sql, missionID);
		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<MessageMission> queryMissionList(int status, int pageSize,
			int pageNo) {
		String sqlCount = "";
		int rowCount = 0;
		if (-1 == status) {
			sqlCount = "select count(message_mission_id) from message_mission where flag=0";
			rowCount = jdbcTemplate.queryForInt(sqlCount);
		} else {
			sqlCount = "select count(message_mission_id) from message_mission where status=? and flag=0";
			rowCount = jdbcTemplate.queryForInt(sqlCount, status);
		}
		PageInfo<MessageMission> mPageInfo = new PageInfo<MessageMission>("",
				rowCount, pageSize);
		if (rowCount < 1) {
			return mPageInfo;
		}
		if (pageNo <= 0)
			pageNo = 1;
		if (pageNo > mPageInfo.getPageCount())
			pageNo = mPageInfo.getPageCount();
		int mStartNum = mPageInfo.getStartNum(pageNo);
		JuRowCallbackHandler<MessageMission> callback = new JuRowCallbackHandler<MessageMission>(
				MessageMission.class);
		String sqlQuery = "";
		if (-1 == status) {
			sqlQuery = "select * from message_mission where flag=0 order by createtime desc limit ?,? ";
			jdbcTemplate.query(sqlQuery, callback, mStartNum, pageSize);
		} else {
			sqlQuery = "select * from message_mission where status=? and flag=0 order by createtime desc limit ?,?";
			jdbcTemplate.query(sqlQuery, callback, status, mStartNum, pageSize);
		}
		mPageInfo.setItems(callback.getList());
		return mPageInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<Localmessage> queryMessageListByUserID(int userID,
			Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> mConditionList = new ArrayList<Object>();
		String sqlCount = "";
		int rowCount = 0;
		if (null == sort || ("").equals(sort)) {
			sort = "receive_time";
		}
		if (null == order) {
			order = "DESC";
		}
		sql.append("from localmessage where 1=1");

		if (userID > 0) {
			sql.append(" and user_id=?");
			mConditionList.add(userID);
		}

		if (condition != null) {
			if (condition.get("status") != null) {
				sql.append(" and status=?");
				mConditionList.add(condition.get("status"));
			}
			if (condition.get("starttime") != null) {
				sql.append(" and receive_time>=?");
				mConditionList.add(condition.get("starttime"));
			}
			if (condition.get("endtime") != null) {
				sql.append(" and receive_time<=?");
				mConditionList.add(condition.get("endtime"));
			}
		}
		sqlCount = "select count(message_id) " + sql.toString();
		rowCount = jdbcTemplate.queryForInt(sqlCount, mConditionList.toArray());
		PageInfo<Localmessage> mPageInfo = new PageInfo<Localmessage>("",
				rowCount, pageSize);
		if (rowCount < 1) {
			Cache.set(NEW_MESSAGE_COUNT_KEY + userID, 60 * 5, 0);
			return mPageInfo;
		}
		if (pageNo <= 0)
			pageNo = 1;
		if (pageNo > mPageInfo.getPageCount())
			pageNo = mPageInfo.getPageCount();

		int mStartNum = mPageInfo.getStartNum(pageNo);
		// int mEndNum = mPageInfo.getPageSize();
		sql.append(" order by " + sort + " " + order + ",status asc ");
		mConditionList.add(mStartNum);
		mConditionList.add(pageSize);
		sql.append(" limit ?,?");
		String sqlQuery = "select * " + sql.toString();
		JuRowCallbackHandler<Localmessage> callback = new JuRowCallbackHandler<Localmessage>(
				Localmessage.class);
		jdbcTemplate.query(sqlQuery, callback, mConditionList.toArray());
		mPageInfo.setItems(callback.getList());
		refreshNewMessageCount(userID);
		return mPageInfo;
	}

	@Override
	public int queryMessageCountByUserID(int userID,
			Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		List<Object> mConditionList = new ArrayList<Object>();
		String sqlCount = "";
		sql.append("from localmessage where 1=1");

		if (userID > 0) {
			sql.append(" and user_id=?");
			mConditionList.add(userID);
		}

		if (condition != null) {
			if (condition.get("status") != null) {
				sql.append(" and status=?");
				mConditionList.add(condition.get("status"));
			}
		}
		sqlCount = "select count(message_id) " + sql.toString();
		@SuppressWarnings("deprecation")
		int rowCount = jdbcTemplate.queryForInt(sqlCount,mConditionList.toArray());
		return rowCount;
	}

	@Override
	public Localmessage getMessageByID(int messageId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from localmessage where message_id=?");
		String sqlQuery = "select * " + sql.toString();
		JuRowCallbackHandler<Localmessage> callback = new JuRowCallbackHandler<Localmessage>(
				Localmessage.class);
		jdbcTemplate.query(sqlQuery, callback, messageId);
		if (callback.getList().size() > 0) {
			return callback.getList().get(0);
		} else {
			return null;
		}
	}

	@Override
	public int sendMessageToUser(int userID, String msgText, String msgTitle,
			String sendtime) {
		int i = doSendMessage(msgTitle, msgText, userID + "", 0);
		return i;
	}

	/**
	 * update_version
	 */
	@Override
	public int sendMessageToUser(int userID, String msgText, String msgTitle,
			String sendtime, int mmcId) {
		int i = doSendMessage(msgTitle, msgText, userID + "", mmcId);
		return i;
	}

	@Override
	public int deleteUserMessage(int[] messageID) {
		int count = 0;
		if (messageID.length > 0) {
			String sql = "delete from localmessage wehre messageid=?";
			for (int msgID : messageID) {
				int i = jdbcTemplate.update(sql, msgID);
				if (i == 1) {
					count++;
				}
			}
		}
		return count;
	}

	@Deprecated
	@Override
	public int sendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime) {
		StringBuffer userID = new StringBuffer();
		if (userIDArray.length > 0) {
			for (int mUserID : userIDArray) {
				if (userID.length() > 0) {
					userID.append("," + mUserID);
				} else {
					userID.append(mUserID);
				}
			}
		}
		int i = doSendMessage(msgTitle, msgContent, userID.toString(), 0);
		return i;
	}

	@Deprecated
	public int timingSendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime) {
		StringBuffer userID = new StringBuffer();
		if (userIDArray.length > 0) {
			for (int mUserID : userIDArray) {
				if (userID.length() > 0) {
					userID.append("," + mUserID);
				} else {
					userID.append(mUserID);
				}
			}
		}
		String sql = "insert message_mission(receiver,message_content,message_title,createtime,sendtime) values(?,?,?,NOW(),?)";
		int i = jdbcTemplate
				.update(sql, userID, msgContent, msgTitle, sendtime);

		if (i == 1 && compareDateTime(sendtime, getNowString()) <= 0) {
			doSendMessage();
		}
		return i;
	}

	public int timingSendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime, int mmcId) {
		StringBuffer userID = new StringBuffer();
		if (userIDArray.length > 0) {
			for (int mUserID : userIDArray) {
				if (userID.length() > 0) {
					userID.append("," + mUserID);
				} else {
					userID.append(mUserID);
				}
			}
		}
		String sql = "insert message_mission(receiver,message_content,message_title,createtime,sendtime,mmc_id) values(?,?,?,NOW(),?,?)";
		int i = jdbcTemplate.update(sql, userID, msgContent, msgTitle,
				sendtime, mmcId);

		if (i == 1 && compareDateTime(sendtime, getNowString()) <= 0) {
			sendMessageMission();
		}
		return i;
	}

	public int timingSendMessageToUser(int userID, String msgText,
			String msgTitle, String sendtime, int mmcId) {
		String sql = "insert message_mission(receiver,message_content,message_title,createtime,sendtime,mmc_id) values(?,?,?,NOW(),?,?)";
		int i = jdbcTemplate.update(sql, userID, msgText, msgTitle, sendtime,
				mmcId);

		if (i == 1 && compareDateTime(sendtime, getNowString()) <= 0) {
			sendMessageMission();
		}
		return i;
	}

	@Deprecated
	public int timingSendMessageToUser(int userID, String msgText,
			String msgTitle, String sendtime) {
		String sql = "insert message_mission(receiver,message_content,message_title,createtime,sendtime) values(?,?,?,NOW(),?)";
		int i = jdbcTemplate.update(sql, userID, msgText, msgTitle, sendtime);

		if (i == 1 && compareDateTime(sendtime, getNowString()) <= 0) {
			doSendMessage();
		}
		return i;
	}

	/**
	 * update_version
	 */
	@Override
	public int sendMessageToUsers(int[] userIDArray, String msgContent,
			String msgTitle, String sendtime, int mmcId) {
		StringBuffer userID = new StringBuffer();
		if (userIDArray.length > 0) {
			for (int mUserID : userIDArray) {
				if (userID.length() > 0) {
					userID.append("," + mUserID);
				} else {
					userID.append(mUserID);
				}
			}
		}
		int i = doSendMessage(msgTitle, msgContent, userID.toString(), 0);
		return i;
	}

	private int compareDateTime(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date mDate1 = DateUtils.parseDate(date1,
					GlobalsKeys.dateParsePatterns);
			Date mDate2 = formatter.parse(date2);

			int number1 = mDate1.compareTo(mDate2);
			return number1;

		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Deprecated
	@Override
	public int getNewMessageCount(int uid) {
		if (Cache.get(NEW_MESSAGE_COUNT_KEY + uid) != null) {
			return Cache.get(NEW_MESSAGE_COUNT_KEY + uid);
		}
		String sql = "select count(message_id) from localmessage where user_id=? and status=0";
		int i = jdbcTemplate.queryForInt(sql, uid);
		Cache.set(NEW_MESSAGE_COUNT_KEY + uid, 60 * 5, i);
		return i;
	}

	/**
	 * update_version
	 */
	@Override
	public int getNewMessageCount(int uid, int mmcId) {
		if (Cache.get(NEW_MESSAGE_COUNT_KEY + uid + "_" + mmcId) != null) {
			return Cache.get(NEW_MESSAGE_COUNT_KEY + uid + "_" + mmcId);
		}
		String sql = "select count(message_id) from localmessage where user_id=? and mmc_id=? and status=0";
		@SuppressWarnings("deprecation")
		int i = jdbcTemplate.queryForInt(sql, uid, mmcId);
		Cache.set(NEW_MESSAGE_COUNT_KEY + uid + "_" + mmcId, 60 * 5, i);
		return i;
	}

	@Override
	public void updateNewMessageCount(int uid, int count) {
		Integer cache_count = new Integer(0);
		cache_count = Cache.get(NEW_MESSAGE_COUNT_KEY + uid);
		if (cache_count != null && cache_count > 0) {
			Cache.set(NEW_MESSAGE_COUNT_KEY + uid, 60 * 5, cache_count + count);
		}

	}

	public void refreshNewMessageCount(int uid) {
		String sql = "select count(message_id) from localmessage where user_id=? and status=0";
		@SuppressWarnings("deprecation")
		int i = jdbcTemplate.queryForInt(sql, uid);
		Cache.set(NEW_MESSAGE_COUNT_KEY + uid, 60 * 5, i);
	}

	/**
	 * *******************************************站内信类型管理***********************
	 * *******************************
	 */
	@Override
	public int addMessageMissionCategory(int mmcId, String categoryName,
			int weight) {
		String sql = "insert message_mission_category(mmc_id,category_name,weight) values(?,?,?)";
		int i = jdbcTemplate.update(sql, mmcId, categoryName, weight);
		return i;
	}

	@Override
	public int modifyMessageMission(int categoryID, String categoryName,
			int categoryWeight) {
		String sql = "update message_mission_category set category_name=?,weight=? where mmc_id=?";
		int i = jdbcTemplate.update(sql, categoryName, categoryWeight,
				categoryID);
		return i;
	}

	@Override
	public int deleteMessageMissionCategory(int categoryID, boolean isForce) {
		int effectRow = 1;
		String sqlcount = "select * from message_mission where mmc_id=?";
		int count = jdbcTemplate.queryForList(sqlcount, categoryID).size();
		if (count > 0) {
			if (isForce) {
				String messageMissionSQL = "delete from message_mission where mmc_id = ?";
				jdbcTemplate.update(messageMissionSQL, categoryID);
				String localMessageSQL = "delete from localmessage where mmc_id = ?";
				jdbcTemplate.update(localMessageSQL, categoryID);
				String messageMissionCategory = "delete from message_mission_category where mmc_id = ?";
				effectRow = jdbcTemplate.update(messageMissionCategory,
						categoryID);
				if (effectRow > 0) {
					return 0;
				}
			}
		} else {
			String sql = "delete from message_mission_category where mmc_id = ?";
			effectRow = jdbcTemplate.update(sql, categoryID);
			if (effectRow > 0) {
				return 0;
			}
		}
		return effectRow;
	}

	@Override
	public List<MessageMissionCategory> queryCategoryList(String sort,
			String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from message_mission_category order by "
				+ sort + " " + order;
		JuRowCallbackHandler<MessageMissionCategory> callback = new JuRowCallbackHandler<MessageMissionCategory>(
				MessageMissionCategory.class);
		jdbcTemplate.query(sqlQuery, callback);
		List<MessageMissionCategory> returnList = callback.getList();
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageMissionCategory> queryCategoryListFront(String sort,
			String order) {
		if (Cache.get(MessageMissionCategory_LIST_KEY) != null) {
			return (List<MessageMissionCategory>) Cache
					.get(MessageMissionCategory_LIST_KEY);
		}
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sqlQuery = "select * from message_mission_category order by "
				+ sort + " " + order;
		JuRowCallbackHandler<MessageMissionCategory> callback = new JuRowCallbackHandler<MessageMissionCategory>(
				MessageMissionCategory.class);
		jdbcTemplate.query(sqlQuery, callback);
		List<MessageMissionCategory> returnList = callback.getList();
		Cache.set(MessageMissionCategory_LIST_KEY, 3600 * 5, returnList);
		return returnList;
	}

	@Override
	public List<MessageMissionCategory> queryCategoryListCache(String sort,
			String order) {
		if (Cache.get(MessageMissionCategory_LIST_KEY) != null) {
			return Cache.get(MessageMissionCategory_LIST_KEY);
		}
		List<MessageMissionCategory> mmcList = queryCategoryList(sort, order);
		if (!mmcList.isEmpty()) {
			Cache.set(MessageMissionCategory_LIST_KEY, 3600, mmcList);
			return mmcList;
		}
		return null;
	}

	@Override
	public PageInfo<MessageMissionCategory> queryCategoryList(int pageSize,
			int pageNo, String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "weight";
		}
		if (null == order) {
			order = "DESC";
		}
		String sql = "from message_mission_category";
		String sqlcount = "select count(message_mission_id) " + sql;
		@SuppressWarnings("deprecation")
		int rowcount = jdbcTemplate.queryForInt(sqlcount);
		PageInfo<MessageMissionCategory> mPageInfo = new PageInfo<MessageMissionCategory>(
				"", rowcount, pageSize);
		String sqlQuery = "select * " + sql + " ORDER BY " + sort + " " + order
				+ " LIMIT ?,?";
		JuRowCallbackHandler<MessageMissionCategory> callback = new JuRowCallbackHandler<MessageMissionCategory>(
				MessageMissionCategory.class);
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
	public int queryCategoryNameIsExist(String categoryName) {
		String sql = "SELECT * FROM message_mission_category WHERE category_name = ?";
		JuRowCallbackHandler<MessageMissionCategory> cb = new JuRowCallbackHandler<MessageMissionCategory>(
				MessageMissionCategory.class);
		jdbcTemplate.query(sql, cb, categoryName);
		if (cb.getList().size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isExistMmcId(int mmcId) {
		String sql = "SELECT * FROM message_mission_category WHERE mmc_id = ?";
		JuRowCallbackHandler<MessageMissionCategory> cb = new JuRowCallbackHandler<MessageMissionCategory>(
				MessageMissionCategory.class);
		jdbcTemplate.query(sql, cb, mmcId);
		if (cb.getList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * APXer
	 * 
	 * 立即发送站内信
	 * 
	 * @param title
	 *            站内信标题
	 * @param content
	 *            站内信内容
	 * @param receiver
	 *            接受者
	 * @param mmcId
	 *            站内信类型ID
	 * 
	 * @return 1:成功 ，0：失败
	 */
	public int doSendMessage(String title, String content, String receiver,
			int mmcId) {
		int flag = 0;
		int contentid = insertMessageContent(content);
		if ((null != receiver) && "0".equals(receiver)) {
			List<Integer> member_id_list = memberService.queryAllMemberID();
			for (int mReceiver : member_id_list) {
				flag = insertMessage(title, contentid, mReceiver, mmcId);
			}
		} else {
			String[] mReceiverArray = receiver.split(",");
			for (String mReceiver : mReceiverArray) {
				int mReceiverInt = Integer.parseInt(mReceiver);
				flag = insertMessage(title, contentid, mReceiverInt, mmcId);
			}
		}
		return flag;
	}
}
