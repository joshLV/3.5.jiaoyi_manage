package cn.jugame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysParameterDao;
import cn.jugame.service.ISysParameterService;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysParameter;
/**
 * 系统参数服务
 * @author houjt
 *
 */
@Service("SysParameterService")
public class SysParameterService implements ISysParameterService{
	@Autowired
	@Qualifier("SysParameterDao")
	private SysParameterDao sysParameterDao;
	@Override
	public List<SysParameter> list(){
		return sysParameterDao.queryForPage("from SysParameter", 0, 20);
	} 
	@Override
	public void save(SysParameter sysParameter){ 
		sysParameterDao.insert(sysParameter);
	}
	@Override
	public SysParameter findById(int id){
		return sysParameterDao.findById(id);		
	}
	@Override
	public void update(SysParameter sysParameter) {
		sysParameterDao.update(sysParameter);
	}
	@Override
	public void delete(SysParameter sysParameter) {
		sysParameterDao.delete(sysParameter);
	}

	@Override
	public PageInfo<SysParameter> queryParameterList(SysParameter condition,
			int pageSize, int pageNo, String sort, String order) {
		StringBuffer hql = new StringBuffer();
		hql.append("from SysParameter where 1=1 ");
		
		List<String> conList = new ArrayList<String>();
		
		if (condition != null){
			if(StringUtils.isNotBlank(condition.getParaCode())){
				hql.append(" and paraCode like ?");
				conList.add("%"+condition.getParaCode()+"%");
			}
			if(StringUtils.isNotEmpty(condition.getParaValue())){
				hql.append(" and paraValue like ?");
				conList.add("%"+condition.getParaValue()+"%");
			}
			 
			if(StringUtils.isNotEmpty(condition.getDscr())){
				hql.append(" and dscr like ?");
				conList.add("%"+condition.getDscr()+"%");
			}
		}
		
		hql.append(" order by " + sort +" " + order);
		//总记录数
		int count = sysParameterDao.getRowCount(hql.toString(), conList.toArray());
		PageInfo<SysParameter> pageInfo = new PageInfo<SysParameter>("",count,pageSize);
		
		if (count == 0) return pageInfo;
		
		if (pageNo < 1 ) pageNo = 1;
		pageInfo.setPageno(pageNo);
		int firstResult = (pageNo-1) * pageInfo.getPageSize();	
		List<SysParameter> list = sysParameterDao.queryForPage(hql.toString(),firstResult, pageInfo.getPageSize(),conList.toArray());
		pageInfo.setItems(list);
		
		return pageInfo;
	}

	@Override
	public List<SysParameter> listByParaCode(String paraCode) {
		return sysParameterDao.findByProperty("paraCode", paraCode);
	}
	 
	
	
}
