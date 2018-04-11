/**
 * 
 */
package cn.juhaowan.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.util.Cache;
import cn.juhaowan.product.dao.GameProductTypeDao;
import cn.juhaowan.product.service.GameProductTypeService;
import cn.juhaowan.product.vo.GameProductType;
import cn.juhaowan.product.vo.GameProductTypeShow;
import cn.juhaowan.product.vo.ProductType;

/**
 * 
 */
@Service("GameProductTypeService")
public class DefaultGameProductTypeService implements GameProductTypeService {
	@Autowired
	private GameProductTypeDao gameProductTypeDao;
	@Autowired
	private JdbcOperations jdbcOperations;

	private static final String GAME_PRODUCT_TYPE_KEY = "game_product_type_key";
	private static final String GAME_PRODUCT_TYPE_ID_KEY = "game_product_type_id_key";
	private static final String GAME_PRODUCT_TYPE_SHOW ="game_product_type_show_key";

	
	
	@Override
	public int updateGameProductType(int gameId, int[] gptIds,String[] unit,String[] symbols,String[] typeName,String[] idDisplay,String[] roleDisplay,String[] autoAssign,String[] minPrice,String[] maxPrice,String[] isAudit,String[] isPublich,String[] gameChannel,String[] weight,String[] quota,String[] scWhiteListAddFlagStr,String[] dcWhiteListCheckFlagStr) {
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
				String updateSql = "UPDATE `game_product_type` SET unit ='" + unit[i] + "',type_name= '" + typeName[i] + "',auto_assign ='"
						+ autoAssign[i] + "',min_price ='" + minPrice[i] + "',max_price ='" + maxPrice[i] + "',is_audit ='" + isAudit[i] + "' ,game_channel_id ='" + gameChannel[i] + "',is_publish ='" + isPublich[i] + "',weight ='" + weight[i] + "',quota ='" + quota[i]+"',sc_white_list_add_flag ='" + scWhiteListAddFlagStr[i] + "',dc_white_list_check_flag ='" + dcWhiteListCheckFlagStr[i] + "' WHERE game_id= " + gameId + " AND product_type_id=" + gptIds[i];
				String addSql = "insert into game_product_type(game_id,product_type_id,unit,type_name,auto_assign,min_price,max_price,is_audit,game_channel_id,is_publish,weight,quota,sc_white_list_add_flag,dc_white_list_check_flag) values('" + gameId + "'," + "'" + gptIds[i] + "','" + unit[i]
						+ "','" + typeName[i] + "','" + autoAssign[i] + "','" + minPrice[i] + "','" + maxPrice[i] + "','" + isAudit[i] + "','" + gameChannel[i] + "','" + isPublich[i] + "','" + weight[i] +  "','" + quota[i]+ "','" + scWhiteListAddFlagStr[i] + "','" + dcWhiteListCheckFlagStr[i] + "'  )";
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
			e.printStackTrace();
			return 1;
		}
		return k ^ 1;
	}

	@Override
	public List<ProductType> findByGameId(int gameId) {
		return gameProductTypeDao.findByGameId(gameId);
	}

	@Override
	public List<GameProductTypeShow> findByGameIdFront(int gameId) {
		if (Cache.get(GAME_PRODUCT_TYPE_KEY + gameId) != null) {
			return Cache.get(GAME_PRODUCT_TYPE_KEY + gameId);
		}
		List<GameProductTypeShow> gcList = gameProductTypeDao.findByGameIdFront(gameId);
		if(null != gcList){
		Cache.set(GAME_PRODUCT_TYPE_KEY + gameId, 180, gcList);
		return gcList;
		}
		return null;
	}

	
//	@Override
//	public List<GameProductTypeShow> findByGameIdCache(int gameId) {
//		return gameProductTypeDao.findByGameIdCache(gameId);
//	}

	@Override
	public GameProductType findById(int id) {
		return gameProductTypeDao.findById(id);
	}

	@Override
	public GameProductType findByIdCache(int id) {
		if (Cache.get(GAME_PRODUCT_TYPE_ID_KEY + id) != null) {
			return Cache.get(GAME_PRODUCT_TYPE_ID_KEY + id);
		}
		GameProductType  gameProductType = gameProductTypeDao.findByIdCache(id);
		if (null != gameProductType) {
			Cache.set(GAME_PRODUCT_TYPE_ID_KEY + id, 180, gameProductType);
			return gameProductType;
		}
		return null;
	}

	@Override
	public List<GameProductType> queryAllGameProductType() {
		return gameProductTypeDao.queryAllGameProductType();
	}

	@Override
	public List<GameProductType> queryGameProductTypeByGameId(int gameId) {
		return gameProductTypeDao.queryGameProductTypeByGameId(gameId);
	}

	
	@Override
	public List<GameProductType> queryGameProductTypeLimited(int gameId) {
		return gameProductTypeDao.queryGameProductTypeLimited(gameId);
	}

	@Override
	public int queryByGameIdAndTypeId(int gameId, int productTypeId) {
		return gameProductTypeDao.queryByGameIdAndTypeId(gameId, productTypeId);
	}

	@Override
	public GameProductTypeShow queryGameProductTypeShow(int id) {
		return gameProductTypeDao.queryGameProductTypeShow(id);
	}

	@Override
	public GameProductTypeShow queryGameProductTypeShow(int gameId, int productTypeId) {
		if (Cache.get(GAME_PRODUCT_TYPE_SHOW + gameId  + productTypeId) != null) {
			return Cache.get(GAME_PRODUCT_TYPE_SHOW + gameId + productTypeId);
		}
		
		GameProductTypeShow show = gameProductTypeDao.queryGameProductTypeShow(gameId, productTypeId);
		if (null != show) {
			Cache.set(GAME_PRODUCT_TYPE_SHOW + gameId  + productTypeId, 180, show);
			return show;
		}
		return null;
	}

	@Override
	public GameProductType findbyGameIdAndProductTypeId(int gameId, int productTypeId) {
		// TODO Auto-generated method stub
		return gameProductTypeDao.queryTypeByGameIdAndTypeId(gameId, productTypeId);
	}

	@Override
	public int delGameProTypeByGameId(int gameId) {
        String sql ="DELETE FROM game_product_type WHERE game_id = ?";
        int i = jdbcOperations.update(sql,gameId);
		return i ^ 1;
	}

	@Override
	public int updateGameProType(GameProductType gpt) {
		String sql ="UPDATE `game_product_type` SET `buy_info_symbol` = ?, `id_display` = ?, `role_display` = ?, `account_display` = ?,`password_display` = ?, `safekey_display` = ?, `area_display` = ? ,`image_url` = ? WHERE `id` = ?;";
		int i = jdbcOperations.update(sql, gpt.getBuyInfoSymbol(),gpt.getIdDisplay(),gpt.getRoleDisplay(),gpt.getAccountDisplay(),gpt.getPasswordDisplay(),gpt.getSafekeyDisplay(),gpt.getAreaDisplay(),gpt.getImageUrl(),gpt.getId());
		return i ^ 1;
	}


   



}
