package cn.juhaowan.member.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.juhaowan.member.service.MemberQQBindService;

@Service("MemberQQBindService")
public class DefaultMemberQQBindService implements MemberQQBindService{
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	private Logger logger = LoggerFactory.getLogger(DefaultMemberQQBindService.class);
	
	
	@Override
	public String queryOpenID(int uid) {
		String sql = "select openid from member_qq_bind where uid = ? and status = 1";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			return rs.getString("openid");
		}
		return null;
	}

	@Override
	public int queryUid(String openID) {
		String sql = "select uid,status from member_qq_bind where openid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, openID);
		if (rs.next()){
			int status = rs.getInt("status");
			int uid = rs.getInt("uid");
			if (status == 1){
				return uid;
			}
			return -2;	//已解绑
		}
		return -1;
	}

	@Override
	public boolean bindQQUser(int uid, String openid,String nickName) {
		try{
			//查询是否存在，如果存在，则直接改状态
			String sql = "select id,status from member_qq_bind where uid = ? and openid = ?";
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql,uid,openid);
			if (rs.next()){
				int id = rs.getInt("id");
				int status = rs.getInt("status");
				if (status == 1 ){
					return true;//什么也没有做
				}
				sql = "update member_qq_bind set status = 1,modify_time = NOW() where id = ?";
				jdbcOperations.update(sql,id);
				return true;
			}
			//插入新的记录
			sql = "insert into member_qq_bind(openid,uid,nick_name,status,create_time,modify_time)";
			sql += " value(?,?,?,1,NOW(),NOW())";
			int x = jdbcOperations.update(sql, openid,uid,nickName);
			return x > 0 ? true : false;
		}catch (Exception e) {
			logger.error("绑定qq用户失败:" + e);
		}
		return false;
	}
	
	public 	boolean unBinQQUser(int uid,String openid){
		String sql = "update member_qq_bind set status = 0 where uid = ? and openid = ?";
		int x = jdbcOperations.update(sql, uid,openid);
		return x > 0 ? true : false;
	}

}
