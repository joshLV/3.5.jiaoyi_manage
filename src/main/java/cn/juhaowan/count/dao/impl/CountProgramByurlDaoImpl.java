package cn.juhaowan.count.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.dao.CountProgramByurlDao;
import cn.juhaowan.count.vo.CountProgramByurl;
@Repository("CountProgramByurlDao")
public class CountProgramByurlDaoImpl extends BaseDaoImplJdbc<CountProgramByurl> implements
		CountProgramByurlDao {
	@Autowired
	private JdbcOperations jdbcTemplate;
	/**
	 * 缁熻姣忔棩URL鐨勮闂鏁�
	 * @param conditions 鏌ヨ鏉′欢
	 * @param pageNo 椤垫暟
	 * @param pageSize 姣忛〉澶氬皯鏉¤褰�
	 * @param sort 鎺掑簭瀛楁
	 * @param order 鎺掑簭鏂瑰紡
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryCountProgramByurlList(Map<String, Object> conditions, int pageNo,
			int pageSize, String sort, String order){
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sql_count = null;
		int count = 0 ;
		int firstResult = 1;
		
		if(null==sort || ("").equals(sort)){
			sort = "a.count_time";
		}
		if(null==order){
			order = "DESC";
		}
		sql.append(" FROM (SELECT a.*,c.`program_name`, ");
		sql.append(" c.`id` as programId ");
		sql.append(" from (SELECT CASE ");
		sql.append(" WHEN LOCATE('?', t.view_url) = 0 ");
		sql.append(" THEN t.view_url ");
		sql.append(" ELSE LEFT(t.`view_url`,LOCATE('?', t.view_url) - 1) ");
		sql.append(" END AS url, ");
		sql.append(" t.`id`, ");
		sql.append(" t.`count_time`, ");
		sql.append(" t.`view_url`, ");
		sql.append(" t.`login_pv`, ");
		sql.append(" t.`login_uv`, ");
		sql.append(" t.`noLogin_pv`, ");
		sql.append(" t.`noLogin_uv` ");
		sql.append(" FROM count_program_byurl t where 1=1 ");
		if(conditions!=null){
			if(conditions.get("beginTime")!=null){
				sql.append(" and t.count_time >= ?");
				conList.add(conditions.get("beginTime"));
			}
			if(conditions.get("endTime")!=null){
				sql.append(" and t.count_time <= ? ");
				conList.add(conditions.get("endTime"));
			}
			if(conditions.get("viewUrl")!=null){
				sql.append(" and t.view_url like ? ");
				conList.add(conditions.get("viewUrl"));			
			}
		}
		sql.append(" ) a ");
		sql.append(" LEFT JOIN count_program c ");
		sql.append(" ON c.`program_url` = a.url ");
		sql.append(" ORDER BY a."+sort+" "+order+",a.login_uv DESC ,a.login_pv DESC) b");
		System.out.println("sql========="+sql);
		sql_count = "select count(*) "+sql.toString();
		count = jdbcTemplate.queryForInt(sql_count,conList.toArray());
	
		PageInfo<Map<String,Object>> pageinfo = new PageInfo<Map<String,Object>>("",count,pageSize);
		if(count == 0) return pageinfo;
		
		pageinfo.setPageno(pageNo);
		
		if(pageNo<=0) pageNo = 1;
		if(pageNo>pageinfo.getPageCount()) pageNo = pageinfo.getPageCount();
		firstResult = (pageNo -1)* pageinfo.getPageSize();
		
		sql.append(" limit "+ firstResult+","+pageinfo.getPageSize());
		
		String sql_page = "SELECT b.id,b.url,b.count_time,b.login_pv,b.login_uv,b.noLogin_pv,b.noLogin_uv ,b.view_url, b.programId, ";
		sql_page+=" CASE WHEN b.programId IS NULL THEN '' ELSE b.programId END as programId,";
		sql_page+=" CASE WHEN b.program_name IS NULL THEN '' ELSE b.program_name END AS program_name ";
		sql_page+=sql.toString();
		List<Map<String,Object>> showList=jdbcTemplate.queryForList(sql_page, conList.toArray());
		pageinfo.setItems(showList);  
		return pageinfo;
	}
}
