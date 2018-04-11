package cn.juhaowan.product.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.GameChannelDao;
import cn.juhaowan.product.vo.GameChannel;

@Repository("GameChannelDao")
public class GameChannelImpl extends BaseDaoImplJdbc<GameChannel> implements GameChannelDao {
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public List<GameChannel> queryChannelByGameId(int gameId) {
		String sql = "select * from game_channel where game_id=?";
		JuRowCallbackHandler<GameChannel> cb = new JuRowCallbackHandler<GameChannel>(GameChannel.class);
		jdbcOperations.query(sql, cb, new Object[] {gameId});
		return cb.getList();
	}

	@Override
	public int addGameChannel(GameChannel g) {
		String sql = "insert into game_channel(game_id,channel_id,qudao_coefficient,is_ty,down_url,file_size,game_version_id,channel_name) values(?,?,?,?,?,?,?,?)";
		int i = jdbcOperations.update(sql, g.getGameId(),g.getChannelId(),g.getQudaoCoefficient(),g.getIsTy(),g.getDownUrl(),g.getFileSize(),g.getGameVersionId(),g.getChannelName());
		return i>0 ? 1 : 0;
	}

	@Override
	public int deleteGameChannel(int gameId, int gameChannelId) {
		String sql = "delete  from game_channel where game_id = ? and channel_id = ?";
		int i = jdbcOperations.update(sql, new Object[] {gameId,gameChannelId});
		
		return i>0 ? 1 : 0;
	}

	@Override
	public GameChannel findItemByGameIdAndChannelId(int gameId, int gameChannelId) {
		String sql = "select * from game_channel where game_id = ? and channel_id = ?";
		JuRowCallbackHandler<GameChannel> cb = new JuRowCallbackHandler<GameChannel>(GameChannel.class);
		jdbcOperations.query(sql, cb, new Object[] {gameId,gameChannelId});
		 List<GameChannel> list = cb.getList();
		 if (list.size() > 0 && list != null) {
			 GameChannel gameAndGameChannel=list.get(0);
			 return gameAndGameChannel;
		}else {
			return null;
		}
	}

	@Override
	public GameChannel findItemById(int id) {
		return this.findById(GameChannel.class, id);
	}

	@Override
	public int updateItem(GameChannel g) {
		String sql = "UPDATE game_channel SET game_id=?,channel_id=?,qudao_coefficient=?,is_ty=?, down_url = ?,file_size = ?,game_version_id=?,channel_name=? WHERE id=?";
		int i = jdbcOperations.update(sql, g.getGameId(),g.getChannelId(),g.getQudaoCoefficient(),g.getIsTy(),g.getDownUrl(),g.getFileSize(),g.getGameVersionId(),g.getChannelName(),g.getId());
		return i>0 ? 1:0;
	}
	
	
	
	
	
	
}
