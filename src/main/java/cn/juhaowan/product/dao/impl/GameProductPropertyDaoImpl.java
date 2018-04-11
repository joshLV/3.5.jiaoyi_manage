/**
 * 
 */
package cn.juhaowan.product.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.product.dao.GameProductPropertyDao;
import cn.juhaowan.product.vo.GameProductProperty;


/**
 * 自定义属性DAO实现
 * 
 * 
 */
@Repository("GameProductPropertyDao")
public class GameProductPropertyDaoImpl extends BaseDaoImplJdbc<GameProductProperty> implements GameProductPropertyDao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int addProperty(GameProductProperty property) {
		if(null == property){
			return 1;
		}
		String sql = "INSERT INTO `game_product_property`(`game_id`,`game_product_type_id`,`property_type`,`KEY`,`key_type`,`VALUE`,`value_type_limit`,`value_required`,`value_encode`,weight,key_desc)"
        +"values (?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcOperations.update(sql, property.getGameId(),property.getGameProductTypeId(),property.getPropertyType(),property.getKey(),property.getKeyType(),
				property.getValue(),property.getValueTypeLimit(),property.getValueRequired(),property.getValueEncode(),property.getWeight(),property.getKey_desc());
		
		
		
		return i ^ 1;
	}

	@Override
	public int modifyProperty(GameProductProperty p) {
        String sql = "UPDATE `game_product_property` SET `game_id` = ?,`game_product_type_id` = ?," 
        		+ "`property_type` = ?,`key` = ?,`key_type` = ?,`value` = ?," 
        		+ "`value_type_limit` = ?,`value_required` = ?,`value_encode` = ?,`weight`= ?,`key_desc` = ?"
                + " WHERE `id` = ?;";
        int i = jdbcOperations.update(sql, p.getGameId(),p.getGameProductTypeId(),p.getPropertyType(),p.getKey(),p.getKeyType(),
        		p.getValue(),p.getValueTypeLimit(),p.getValueRequired(),p.getValueEncode(),p.getWeight(),p.getKey_desc(),p.getId());
		return i ^ 1;
	}

	@Override
	public int delProperty(int propertyId) {
        String sql = "DELETE FROM `game_product_property` WHERE `id` = ?;";
        int i = jdbcOperations.update(sql, propertyId);
		return i ^ 1;
	}

	@Override
	public List<GameProductProperty> queryProperty(int gameId, int gameProductTypeId) {
		List<GameProductProperty> list = null;
		String sql = "SELECT * FROM game_product_property WHERE game_id = ? AND game_product_type_id = ?  ORDER BY weight ASC";
		JuRowCallbackHandler<GameProductProperty> rch = new JuRowCallbackHandler<GameProductProperty>(GameProductProperty.class);
		jdbcOperations.query(sql, rch,gameId,gameProductTypeId);
		if(null != rch.getList()){
			list = rch.getList();
		}
		return list;
	}

	@Override
	public List<GameProductProperty> queryProperty(int gameId, int gameProductTypeId, int propertyType) {
		List<GameProductProperty> list = null;
		String sql = "SELECT * FROM game_product_property WHERE game_id = ? AND game_product_type_id = ? AND property_type = ?  ORDER BY weight ASC";
		JuRowCallbackHandler<GameProductProperty> rch = new JuRowCallbackHandler<GameProductProperty>(GameProductProperty.class);
		jdbcOperations.query(sql, rch,gameId,gameProductTypeId,propertyType);
		if(null != rch.getList()){
			list = rch.getList();
		}
		return list;
	}

	@Override
	public GameProductProperty queryPropertyById(int id) {
		GameProductProperty gp = null;
		String sql = "SELECT * FROM game_product_property WHERE id = ?";
		JuRowCallbackHandler<GameProductProperty> rch = new JuRowCallbackHandler<GameProductProperty>(GameProductProperty.class);
		jdbcOperations.query(sql, rch,id);
		if(null != rch.getList()){
			gp = rch.getList().get(0);
		}
		return gp;
	}

	@Override
	public int delProperty(int gameId, int gameProductTypeId) {
		  String sql = "DELETE FROM `game_product_property` WHERE `game_id` = ? AND `game_product_type_id` = ?";
	      int i = jdbcOperations.update(sql, gameId,gameProductTypeId);
	      return i ^ 1;
	}

	

}
