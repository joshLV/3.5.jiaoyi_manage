/**
 * 
 */
package cn.juhaowan.product.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.GameProductTypeReminderDao;
import cn.juhaowan.product.vo.GameProductTypeReminder;


/**
 * 自定义提示信息DAO实现
 * 
 * 
 */
@Repository("GameProductTypeReminderDao")
public class GameProductTypeReminderDaoImpl extends BaseDaoImplJdbc<GameProductTypeReminder> implements GameProductTypeReminderDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int addReminder(GameProductTypeReminder r) {
		if(null == r){
			return 1;
		}
		String sql = "INSERT INTO `game_product_type_reminder`(`game_id`,`game_product_type_id`,`type`,`message`)"
                   + "VALUES (?,?,?,?);";
		int i = jdbcOperations.update(sql, r.getGameId(),r.getGameProductTypeId(),r.getType(),r.getMessage());
		return i ^ 1;
	}

	@Override
	public int modifyReminder(GameProductTypeReminder r) {
        String sql = "UPDATE `game_product_type_reminder`"
                      + " SET `game_id` = ?,`game_product_type_id` = ?,`type` = ?,"
        		      + "`message` = ? WHERE `id` = ?;";
        int i = jdbcOperations.update(sql, r.getGameId(),r.getGameProductTypeId(),r.getType(),r.getMessage(),r.getId());
		return i ^ 1;
	}

	@Override
	public int delReminder(int Id) {
        String sql = "DELETE FROM `game_product_type_reminder` WHERE `id` = ?;";
        int i = jdbcOperations.update(sql, Id);
		return i ^ 1;
	}

	@Override
	public List<GameProductTypeReminder> queryReminder(int gameId, int gameProductTypeId) {
		List<GameProductTypeReminder> list = null;
		String sql = "SELECT * FROM game_product_type_reminder WHERE game_id = ? AND game_product_type_id = ? ";
		JuRowCallbackHandler<GameProductTypeReminder> rch = new JuRowCallbackHandler<GameProductTypeReminder>(GameProductTypeReminder.class);
		jdbcOperations.query(sql, rch,gameId,gameProductTypeId);
		if(null != rch.getList()){
			list = rch.getList();
		}
		return list;
	}

	@Override
	public List<GameProductTypeReminder> queryReminder(int gameId, int gameProductTypeId, int reminderType) {
		List<GameProductTypeReminder> list = null;
		String sql = "SELECT * FROM game_product_type_reminder WHERE game_id = ? AND game_product_type_id = ? AND type = ? ";
		JuRowCallbackHandler<GameProductTypeReminder> rch = new JuRowCallbackHandler<GameProductTypeReminder>(GameProductTypeReminder.class);
		jdbcOperations.query(sql, rch,gameId,gameProductTypeId,reminderType);
		if(null != rch.getList()){
			list = rch.getList();
		}
		return list;
	}

	@Override
	public GameProductTypeReminder queryReminderById(int id) {
		GameProductTypeReminder gp = null;
		String sql = "SELECT * FROM game_product_type_reminder WHERE id = ?";
		JuRowCallbackHandler<GameProductTypeReminder> rch = new JuRowCallbackHandler<GameProductTypeReminder>(GameProductTypeReminder.class);
		jdbcOperations.query(sql, rch,id);
		if(null != rch.getList()){
			gp = rch.getList().get(0);
		}
		return gp;
	}

	@Override
	public boolean isExist(int gameId, int gameProductTypeId, String reminderType) {
        boolean flag = false;
        String result = "";
        String sql = "SELECT GROUP_CONCAT(TYPE) FROM `game_product_type_reminder` WHERE game_id =? AND `game_product_type_id`= ?";
        SqlRowSet s = jdbcOperations.queryForRowSet(sql,gameId,gameProductTypeId);
        while(s.next()){
        	result = s.getString(1);
        	System.out.println("reslut === " + result);
        	if(null != result){
        	if(result.contains(reminderType)){
        		flag =  true;
        	}
        	}
        }
        return flag;
	}

	@Override
	public int delReminder(int gameId, int gameProductTypeId) {
		 String sql = "DELETE FROM `game_product_type_reminder` WHERE `game_id` = ? AND `game_product_type_id` = ? ";
	     int i = jdbcOperations.update(sql, gameId,gameProductTypeId);
	     return i ^ 1;
	}

	

}
