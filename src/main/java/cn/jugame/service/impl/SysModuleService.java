package cn.jugame.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysModuleDao;
import cn.jugame.service.ISysModuleService;
import cn.jugame.vo.SysModule;
/**
 * 模块服务 
 * @author houjt
 *
 */
@Service("SysModuleService")
public class SysModuleService implements ISysModuleService{
	@Autowired
	@Qualifier("SysModuleDao")
	private SysModuleDao sysModuleDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<SysModule> findParentModule(){
		return sysModuleDao.queryForPage("from SysModule where parentId = 0 order by orderNo", 0, 5000);
	}
	@Override
	public List<SysModule> findAll(){
		return sysModuleDao.queryForPage("from SysModule order by orderNo", 0, 5000);
	} 
	@Override
	public void save(SysModule sysModule){ 
		sysModuleDao.insert(sysModule);
	}
	@Override
	public SysModule findById(int modId){
		return sysModuleDao.findById(modId);		
	}
	@Override
	public void update(SysModule sysModule) {
		sysModuleDao.update(sysModule);
	}
	@Override
	public void delete(SysModule sysModule) {
		sysModuleDao.delete(sysModule);
	}
	@Override
	public int delete(int modId,int parentId){
		StringBuffer sb = new StringBuffer();
		sb.append("delete from sys_module where mod_id=? ");
		if(parentId == 0){
			sb.append(" or  parent_id=?");
			return this.jdbcTemplate.update(sb.toString(), modId,modId);
		}else{
			sb.append(" and parent_id=?");
			return this.jdbcTemplate.update(sb.toString(), modId,parentId);
		}
	}
	@Override
	public int queryMaxId() {
		// TODO Auto-generated method stub
		int i = 0;
		String sql = "select max(mod_id) from sys_module ";
		SqlRowSet r = jdbcTemplate.queryForRowSet(sql);
		while(r.next()){
			i = r.getInt(1);
		}
		return i;
	}
	
	
}
