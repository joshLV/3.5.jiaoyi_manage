package cn.juhaowan.interfaces.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.interfaces.dao.MemberGameBindDao;
import cn.juhaowan.interfaces.game.vo.MemberGameBind;


@Repository("MemberGameBindDao")
public class MemberGameBindDaoImpl implements MemberGameBindDao {
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public int bind(int gameid,int uid, String gameUid) {
		String sql = "select count(id) from member_game_bind where game_id = ? and uid = ? and game_uid = ?";
		int x = jdbcOperations.queryForInt(sql, gameid, uid, gameUid);
		if (x > 0) {
			return 0; //已存在, 默认为成功
		}
		
		sql = "insert into member_game_bind(uid,game_uid,game_id,create_time) value(?,?,?,now())";
		x = jdbcOperations.update(sql, uid, gameUid, gameid);
		
		return x > 0 ? 0 : 1;
	}
	
	
	@Override
	public int unbind(int uid, int bindId) {
		String sql = "DELETE FROM member_game_bind WHERE id = ? AND uid = ? LIMIT 1";
		
		int rtn = jdbcOperations.update(sql, bindId, uid);
		
		return rtn > 0 ? 0 : 1;
	}
	
	@Override
	public int unbind(int id){
       String sql = "DELETE FROM member_game_bind WHERE id = ?";
		
		int rtn = jdbcOperations.update(sql, id);
		
		return rtn > 0 ? 0 : 1;
	}
	
	@Override
	public int setNickName(int gameid, int uid, String gameUid, String nickName) {
		String sql = "UPDATE member_game_bind SET nickname=?  WHERE game_id=? AND uid=? AND game_uid=? LIMIT 1";
		int x = jdbcOperations.update(sql, nickName, gameid, uid, gameUid);
		System.out.println("UPDATE member_game_bind SET nickname='" + nickName 
				+ "' WHERE game_id=" + gameid 
				+ " AND uid=" + uid 
				+ " AND game_uid='" + gameUid + "'");
		return x > 0 ? 0 : 1;
	}
	
	
	
	@Override
	public int setNickName(int bindId, String nickName) {
		String sql = "UPDATE member_game_bind SET nickname=?  WHERE id=?  LIMIT 1";
		int x = jdbcOperations.update(sql, nickName, bindId);
		return x > 0 ? 0 : 1;
	}
	
	
	
	@Override
	public String getNickName(int gameid, int uid, String gameUid) {
		String sql = "SELECT nickname FROM member_game_bind WHERE  game_id=? AND uid=? AND game_uid=?";
		Map<String, Object> map = jdbcOperations.queryForMap(sql, gameid, uid, gameUid);
		Object obj = map.get("nickname");
		if(null == obj) {
			return "";
		}
		return obj.toString();
	}
	
	@Override
	public HashMap<String, String> getNickNameMap(int gameid, int uid) {
		HashMap<String, String> rtn = new HashMap<String, String>();
		String sql = "SELECT * FROM member_game_bind WHERE  game_id=? AND uid=?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, gameid, uid);
		while(rs.next()) {
			rtn.put(rs.getString("game_uid"), rs.getString("nickname"));
		}
		return rtn;
	}

	@Override
	public int findUid(int gameid, String gameUid) {
		String sql = "select * from member_game_bind where game_id = ? and game_uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, gameid, gameUid);
		if (rs.next()){
			return rs.getInt("uid");
		}

		return 0;
	}

	@Override
	public List<String> findGameUid(int uid, int gameid) {
		String sql = "SELECT * FROM member_game_bind WHERE uid = ? AND game_id = ? ";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid, gameid);
		
		ArrayList<String> rtn = new ArrayList<String>();
		
		while (rs.next()){
			rtn.add(rs.getString("game_uid"));
		}
		
		return rtn;
	}

	@Override
	public List<MemberGameBind> findByUid(int uid){
		String sql = "select * from member_game_bind where uid = ? ";
		JuRowCallbackHandler<MemberGameBind> callback = new JuRowCallbackHandler<>(MemberGameBind.class);
		
		jdbcOperations.query(sql, callback, uid);
		
		return callback.getList();
	}
	
	@Override
	public List<MemberGameBind> findByGameUid(String gameUid) {
		String sql = "select * from member_game_bind where game_uid = ? ";
		JuRowCallbackHandler<MemberGameBind> callback = new JuRowCallbackHandler<>(MemberGameBind.class);
		
		jdbcOperations.query(sql, callback, gameUid);
		
		return callback.getList();
	}

	@Override
	public MemberGameBind findById(int id) {
		String sql = "select * from member_game_bind where id = ? ";
		JuRowCallbackHandler<MemberGameBind> callback = new JuRowCallbackHandler<>(MemberGameBind.class);
		
		jdbcOperations.query(sql, callback, id);
		
		if(callback.getList() != null && callback.getList().size() == 1) {
			return callback.getList().get(0);
		}
		
		return null;
	}

}
