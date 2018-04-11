package cn.juhaowan.count.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;

import cn.jugame.util.PageInfo;

import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountProgramByurlDao;

import cn.juhaowan.count.dao.CountProgramDao;
import cn.juhaowan.count.service.CountProgramService;
import cn.juhaowan.count.vo.CountDayMoneyIo;
import cn.juhaowan.count.vo.CountProgram;
@Service("CountProgramService")
public class DefaultCountProgramService implements CountProgramService{
	private static Logger log = LoggerFactory.getLogger(DefaultCountProgramService.class);
	@Autowired
	private CountProgramDao countProgramDao;
	@Autowired
	private JdbcOperations jdbcOperations;
	@Autowired
	private CountProgramByurlDao countProgramByurlDao;
	@Override
	public List<CountProgram> getParentProgram() {
		String sql="select * from count_program where status=1 and parent_program=0";
		JuRowCallbackHandler<CountProgram> cb = new JuRowCallbackHandler<CountProgram>(CountProgram.class);
		this.jdbcOperations.query(sql, cb);
		return cb.getList();
	}

	@Override
	public List<CountProgram> getAllProgram() {
		String sql="select * from count_program where 1=1";
		JuRowCallbackHandler<CountProgram> cb = new JuRowCallbackHandler<CountProgram>(CountProgram.class);
		this.jdbcOperations.query(sql, cb);
		return cb.getList();
	}

	@Override
	public int addCountProgram(CountProgram countProgram) {
		this.countProgramDao.insert(countProgram);
		return 0;
	}

	@Override
	public int updateCountProgram(CountProgram countProgram) {
		this.countProgramDao.update(countProgram);
		return 0;
	}

	@Override
	public int deleteCountProgram(int programId, int parentId) {
		StringBuffer sb=new StringBuffer();
		sb.append("delete from count_program where id=? ");
		if(parentId==0){
			sb.append(" or  parent_program=?");
			return this.jdbcOperations.update(sb.toString(), programId,programId);
		}else{
			sb.append(" and parent_program=?");
			return this.jdbcOperations.update(sb.toString(), programId,parentId);
		}
	}
	/**
	 * 根据栏目ID查找相关栏目
	 * @param id
	 * @return
	 */
	@Override
	public CountProgram findById(int id){
		return this.countProgramDao.findById(CountProgram.class, id);
	}


	//栏目url 统计
	@Override
	public List<Map> queryProgramByUrl(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUM(login_pv) login_pv,SUM(login_uv) login_uv,SUM(noLogin_pv)noLogin_pv,SUM(noLogin_uv)noLogin_uv FROM count_program_byurl " +
				"WHERE 1 = 1  ");

		if (map != null && map.get("view_url") != null) {
			sql.append(" and view_url like ");
			sql.append("'%"+map.get("view_url")+"%'");
		}
		
		if (map != null && map.get("btime") != null) {
			sql.append(" and count_time >= ");
			sql.append("'"+map.get("btime")+"'");
		}
		
		if (map != null && map.get("etime") != null) {
			sql.append(" and count_time <= ");
			sql.append("'"+map.get("etime")+"'");
		}
//		sql.append(" order BY id asc ");
		System.out.println(sql.toString());
		List<Map> mapList = new ArrayList<Map>();


		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());

		while (rs.next()) {
			Map m = new HashMap<String, Object>();
			m.put("login_pv", rs.getInt(1));
			m.put("login_uv", rs.getInt(2));
			m.put("noLogin_pv", rs.getInt(3));
			m.put("noLogin_uv", rs.getInt(4));
			mapList.add(m);
		}

		return mapList;
	}

	@Override
	public Map ListProgramByTime(Map conditions) {
		Map m = new HashMap();
        //查询所有栏目
		List<CountProgram> list = getAllProgram();
		//按栏目url查询统计pv uv
		for(int i =0;i<list.size();i++){
			List newList = new ArrayList<>();
			conditions.put("view_url", list.get(i).getProgramUrl());
			newList = queryProgramByUrl(conditions);
			m.put(list.get(i).getProgramName(), newList);
		}
		
		return m;
	}


	
	
	

	/**
	 * 统计每日URL的访问次数
	 * @param conditions 查询条件
	 * @param pageNo 页数
	 * @param pageSize 每页多少条记录
	 * @param sort 排序字段
	 * @param order 排序方式
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryCountProgramByurlList(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order){
		return this.countProgramByurlDao.queryCountProgramByurlList(conditions, pageNo, pageSize, sort, order);
	}

}
