package cn.juhaowan.product.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.product.dao.GameCPDao;
import cn.juhaowan.product.vo.GameCP;
/**
 * 
 * @author Administrator
 *
 */
@Repository("GameCPDao")
public class GameCPDaoImpl extends BaseDaoImplJdbc<GameCP> implements GameCPDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public GameCP findById(int id) {
		return findById(GameCP.class, id);
	}

	@Override
	public List<GameCP> findByProperty(String propertyName, Object value) {
		return findByProperty(GameCP.class, propertyName, value);
	}

	@Override
	public int insertGameCP(GameCP gameCP) {
		if(null == gameCP) {
			return 1;
		}
		GameCP c = new GameCP();
		c =  findById(gameCP.getCpId());
		if(null != c) {
			return 1;
		}
		insert(gameCP);
		return 0;
	}

	@Override
	public int updateGameCP(GameCP gameCP) {
		if(null == gameCP) {
			return 1;
		}
		GameCP cp = new GameCP();
		cp = findById(gameCP.getCpId());
		if(null == cp) {
			return 1;
		}
		update(gameCP);
		return 0;
	}

	@Override
	public int deleteGameCP(int gameCPId) {
		if(gameCPId < 0) {
			return 1;
		}
		GameCP cp = new GameCP();
		cp = findById(gameCPId);
		if(null == cp) {
			return 1;
		}
		delete(cp);
		return 0;
	}

	@Override
	public GameCP queryGameCPByCPId(int gameCPId) {
		return findById(gameCPId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PageInfo<GameCP> queryGameCPForPage(Map<String, Object> conditions,int pageNo, int pageSize,
			String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>(); 
		String sqlCount = null; 
		int count = 0; 
		int firstResult = 1;
		if(null == sort) {
			sort = "cp_id";
		}
		if(null == order) {
			order = "asc";
		}
		sql.append("select * from game_cp where 1 = 1");
		
		if(null != conditions) {
			if(null != conditions.get("cp_name")) {
				sql.append(" and cp_name like ? ");
				conList.add("%" + conditions.get("cp_name") + "%");
			}
			
			if(null != conditions.get("cp_discretion")){
				sql.append("and cp_discretion like ? ");
				conList.add("%" + conditions.get("cp_discretion") + "%");
			}
		}
		sql.append(" order by " + sort + " " + order);
		
		sqlCount = sql.toString().replace("select * from ", "select count(*) from ");
		count = jdbcOperations.queryForInt(sqlCount,conList.toArray());
		PageInfo<GameCP> pageInfo = new PageInfo<GameCP>("",count,pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		
		if(count == 0) {
			return pageInfo;
		}
		
	
		if(pageNo <= 0) {
			pageNo = 1;
		}
		if(pageNo > pageInfo.getPageCount()) {
			pageNo = pageInfo.getPageCount(); 
		}
		
		firstResult = (pageNo - 1) * pageInfo.getPageSize();
		
		sql.append(" limit " + firstResult + "," + pageInfo.getPageSize());
		
		JuRowCallbackHandler<GameCP> cb = new JuRowCallbackHandler<GameCP>(GameCP.class);
		
		jdbcOperations.query(sql.toString(),cb,conList.toArray());
		pageInfo.setItems(cb.getList());
		return pageInfo;
		
		}
	

}
