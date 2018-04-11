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
import cn.juhaowan.product.dao.GameProductTypeDao;
import cn.juhaowan.product.vo.GameProductType;
import cn.juhaowan.product.vo.GameProductTypeShow;
import cn.juhaowan.product.vo.ProductType;

/**
 * 游戏商品类型DAO实现
 * 
 * 
 */
@Repository("GameProductTypeDao")
public class GameProductTypeDaoImpl extends BaseDaoImplJdbc<GameProductType> implements GameProductTypeDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int updateGameProductType(int gameId, int[] gptIds, String[] unit, String[] symbols, String[] typeName, String[] idDisplay, String[] roleDisplay, String[] autoAssign, String[] minPrice, String[] maxPrice, String[] isAudit, String[] isPublich, String[] gameChannel,String[] weight) {
		int k = 1;
		try {
			if (gptIds == null)
				return k;
			// 保存不能删除的ids
			StringBuffer updateids = new StringBuffer();

			// 重新添加商品分类表
			for (int i = 0; i < gptIds.length; i++) {
				// 存在的更新,isPublicStr,gameChannelStr
				String existSql = "SELECT id FROM `game_product_type` WHERE game_id =" + gameId + " AND product_type_id=" + gptIds[i];
				String updateSql = "UPDATE `game_product_type` SET unit ='" + unit[i] + "',buy_info_symbol= " + symbols[i] + ",type_name= '" + typeName[i] + "',id_display= '" + idDisplay[i] + "',role_display= '" + roleDisplay[i] + "',auto_assign ='"
						+ autoAssign[i] + "',min_price ='" + minPrice[i] + "',max_price ='" + maxPrice[i] + "',is_audit ='" + isAudit[i] + "' ,game_channel_id ='" + gameChannel[i] + "',is_publish ='" + isPublich[i] + "',weight ='" + weight[i] + "' WHERE game_id= " + gameId + " AND product_type_id=" + gptIds[i];
				String addSql = "insert into game_product_type(game_id,product_type_id,unit,buy_info_symbol,type_name,id_display,role_display,auto_assign,min_price,max_price,is_audit,game_channel_id,is_publish,weight) values('" + gameId + "'," + "'" + gptIds[i] + "','" + unit[i]
						+ "','" + symbols[i] + "','" + typeName[i] + "','" + idDisplay[i] + "','" + roleDisplay[i] + "','" + autoAssign[i] + "','" + minPrice[i] + "','" + maxPrice[i] + "','" + isAudit[i] + "','" + gameChannel[i] + "','" + isPublich[i] + "','" + weight[i] + "'  )";
				SqlRowSet exist = jdbcOperations.queryForRowSet(existSql);
				if (exist.next()) {
					jdbcOperations.update(updateSql);
					// 保存更改的id
					updateids.append(exist.getString(1));
					updateids.append(",");

				} else {
					jdbcOperations.update(addSql);
					SqlRowSet r = jdbcOperations.queryForRowSet("SELECT MAX(id) FROM `game_product_type`");
					while (r.next()) {
						updateids.append(r.getString(1));
						updateids.append(",");
					}
				}
			}
			// 删除多余的项
			// System.out.println("不能删除的==" + updateids);
			String realupdateids = updateids.toString();
			if (!"".equals(realupdateids) && null != realupdateids) {
				if (realupdateids.contains(",")) {
					realupdateids = String.valueOf((realupdateids.substring(0, updateids.length() - 1)));
					String delSql = "DELETE FROM game_product_type WHERE game_id = " + gameId + " and id NOT IN (" + realupdateids + ");";
					jdbcOperations.update(delSql);
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return 1;
		}
		return k ^ 1;
	}

	@Override
	public List<ProductType> findByGameId(int gameId) {
		JuRowCallbackHandler<ProductType> cb = new JuRowCallbackHandler<ProductType>(ProductType.class);
		String sql = "SELECT id,product_type,NAME,STATUS FROM `product_type` pt,(SELECT gpt.`product_type_id` from `game_product_type` gpt where gpt.`game_id`=?) gpt where  pt.id = gpt.`product_type_id`";
		jdbcOperations.query(sql, cb, gameId);
		List<ProductType> gcList = cb.getList();
		return gcList;
	}


	@Override
	public List<GameProductTypeShow> findByGameIdFront(int gameId) {

		JuRowCallbackHandler<GameProductTypeShow> cb = new JuRowCallbackHandler<GameProductTypeShow>(GameProductTypeShow.class);
		String sql = "SELECT a.id gptId,b.id typeId,b.`product_type` productType,a.type_name,a.id_display,a.role_display,b.name,b.status,a.game_id gameId,a.`unit`,a.`buy_info_symbol` buyInfoSymbol FROM `game_product_type` a LEFT JOIN `product_type` b ON a.`product_type_id` = b.id WHERE b.status = 1 AND a.game_id= ?";
		jdbcOperations.query(sql, cb, gameId);
		List<GameProductTypeShow> gcList = cb.getList();

		if (!gcList.isEmpty()) {
			return gcList;
		}
		return null;
	}

	@Override
	public GameProductType findById(int id) {
		return findById(GameProductType.class, id);
	}

	@Override
	public GameProductType findByIdCache(int id) {

		GameProductType gameProductType = findById(id);
		if (null != gameProductType) {
			return gameProductType;
		}
		return null;
	}

	@Override
	public int deleteGameProductType(int GameProductTypeId) {
		if (GameProductTypeId < 0) {
			return 1;
		}
		GameProductType gc = findById(GameProductTypeId);
		if (null == gc) {
			return 1;
		}
		delete(gc);
		jdbcOperations.update("delete from game_product_type where product_type_id = " + GameProductTypeId);
		return 0;
	}

	@Override
	public List<GameProductType> queryAllGameProductType() {
		JuRowCallbackHandler<GameProductType> cb = new JuRowCallbackHandler<GameProductType>(GameProductType.class);
		String sql = "SELECT * FROM `game_product_type`";
		jdbcOperations.query(sql, cb);
		List<GameProductType> gcList = cb.getList();
		return gcList;
	}

	@Override
	public List<GameProductType> queryGameProductTypeByGameId(int gameId) {
		JuRowCallbackHandler<GameProductType> cb = new JuRowCallbackHandler<GameProductType>(GameProductType.class);
		String sql = "SELECT * FROM `game_product_type` WHERE game_id=?";
		jdbcOperations.query(sql, cb, gameId);
		List<GameProductType> gcList = cb.getList();
		return gcList;
	}

	@Override
	public List<GameProductType> queryGameProductTypeLimited(int gameId) {
		String sql = "SELECT * FROM game_product_type WHERE product_type_id<100 AND game_id=?";
		JuRowCallbackHandler<GameProductType> cb = new JuRowCallbackHandler<GameProductType>(GameProductType.class);
		jdbcOperations.query(sql, cb, gameId);
		List<GameProductType> gcList = cb.getList();
		return gcList;
	}

	@Override
	public int queryByGameIdAndTypeId(int gameId, int productTypeId) {
		int i = 0;
		String sql = "SELECT id FROM `game_product_type` WHERE `game_id`= ? AND `product_type_id`= ?";
		SqlRowSet result = jdbcOperations.queryForRowSet(sql, gameId, productTypeId);
		while (result.next()) {
			i = result.getInt(1);
		}
		return i;
	}

	@Override
	public GameProductTypeShow queryGameProductTypeShow(int id) {
		JuRowCallbackHandler<GameProductTypeShow> cb = new JuRowCallbackHandler<GameProductTypeShow>(GameProductTypeShow.class);
		String sql = "SELECT a.id gptId,b.id typeId,b.`product_type` productType,a.type_name,a.id_display,a.role_display,b.name,b.status,a.game_id gameId,a.`unit`,a.`buy_info_symbol` buyInfoSymbol,a.type_name ,a.`id_display`,a.`role_display`  FROM `game_product_type` a LEFT JOIN `product_type` b ON a.`product_type_id` = b.id WHERE b.status = 1 AND a.id= ?";
		jdbcOperations.query(sql, cb, id);
		List<GameProductTypeShow> list = cb.getList();
		GameProductTypeShow result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	@Override
	public GameProductTypeShow queryGameProductTypeShow(int gameId, int productTypeId) {

		JuRowCallbackHandler<GameProductTypeShow> cb = new JuRowCallbackHandler<GameProductTypeShow>(GameProductTypeShow.class);
		String sql = "SELECT a.id gptId,b.id typeId,b.`product_type` productType,a.type_name,a.id_display,a.role_display,b.name,b.status,a.game_id gameId,a.`unit`,a.`buy_info_symbol` buyInfoSymbol ,a.type_name ,a.`id_display`,a.`role_display` FROM `game_product_type` a LEFT JOIN `product_type` b ON a.`product_type_id` = b.id WHERE b.status = 1 AND a.game_id= ? AND b.id= ?";
		jdbcOperations.query(sql, cb, gameId, productTypeId);
		List<GameProductTypeShow> list = cb.getList();
		GameProductTypeShow show = null;
		if (!list.isEmpty()) {
			show = list.get(0);
			return show;
		}
		return show;
	}

	@Override
	public GameProductType queryTypeByGameIdAndTypeId(int gameId, int productTypeId) {
		GameProductType t = null;
		String sql = "SELECT *  FROM `game_product_type` WHERE `game_id`= ? AND `product_type_id`= ?";
		JuRowCallbackHandler<GameProductType> cb = new JuRowCallbackHandler<GameProductType>(GameProductType.class);
		jdbcOperations.query(sql, cb, gameId, productTypeId);
		List<GameProductType> list = cb.getList();
		if (!list.isEmpty()) {
			t = list.get(0);
		}
		return t;
	}

}
