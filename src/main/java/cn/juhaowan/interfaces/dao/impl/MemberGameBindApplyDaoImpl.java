package cn.juhaowan.interfaces.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.interfaces.dao.MemberGameBindApplyDao;
import cn.juhaowan.interfaces.game.vo.MemberGameBindApply;


/**
 * 
 * @author lithium
 *
 */
@Repository("MemberGameBindApplyDao")
public class MemberGameBindApplyDaoImpl implements MemberGameBindApplyDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	
	@Override
	public int applyBind(int uid, String gameRoleId, String gameRoleName, int gameId, int areaId, String vcode) {
		
		//UNIQUE KEY `unique_1` (`uid`,`game_id`,`game_role_id`)
		String sql = " INSERT INTO member_game_bind_apply(uid, game_role_id, game_role_name,  game_id, area_id, vcode, create_time, update_time) " 
					+ " VALUES(?, ?, ?, ?, ?, ?, now(), now())  ON DUPLICATE KEY UPDATE vcode= ?, update_time = now() ";
		
		int x = jdbcOperations.update(sql, uid, gameRoleId, gameRoleName, gameId, areaId, vcode, vcode);
		return x > 0 ? 0 : 1;
	}
	

	@Override
	public int delApply(int uid, String gameRoleId, int gameId, int areaId) {
		String sql = " DELETE FROM member_game_bind_apply WHERE uid=? AND game_role_id=? AND game_id=? AND area_id=?  LIMIT 1";
		int x = jdbcOperations.update(sql, uid, gameRoleId, gameId, areaId);
		return x > 0 ? 0 : 1;
	}

	@Override
	public int delApply(int id) {
		String sql = " DELETE FROM member_game_bind_apply WHERE id = ? LIMIT 1";
		int x = jdbcOperations.update(sql, id);
		return x > 0 ? 0 : 1;
	}

	@Override
	public List<MemberGameBindApply> findByUid(int uid) {
		String sql = "select * from member_game_bind_apply where uid = ? ";
		JuRowCallbackHandler<MemberGameBindApply> callback = new JuRowCallbackHandler<MemberGameBindApply>(MemberGameBindApply.class);
		jdbcOperations.query(sql, callback, uid);
		
		return callback.getList();
	}

	@Override
	public MemberGameBindApply findById(int id) {
		String sql = "select * from member_game_bind_apply where id = ? limit 1";
		JuRowCallbackHandler<MemberGameBindApply> callback = new JuRowCallbackHandler<MemberGameBindApply>(MemberGameBindApply.class);
		jdbcOperations.query(sql, callback, id);
		
		if(callback.getList() != null && callback.getList().size() == 1) {
			return callback.getList().get(0);
		}
		
		return null;
	}

	@Override
	public MemberGameBindApply findByInfo(int uid, String gameRoleId, int gameId, int areaId) {
		String sql = "SELECT * FROM member_game_bind_apply WHERE uid = ? AND game_role_id = ? AND game_id = ? AND area_id = ? limit 1";
		JuRowCallbackHandler<MemberGameBindApply> callback = new JuRowCallbackHandler<MemberGameBindApply>(MemberGameBindApply.class);
		jdbcOperations.query(sql, callback, uid, gameRoleId, gameId, areaId);
		
		if(callback.getList() != null && callback.getList().size() == 1) {
			return callback.getList().get(0);
		}
		
		return null;
	}

}

