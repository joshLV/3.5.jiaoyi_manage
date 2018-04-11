package cn.jugame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.jugame.dao.SysParameterTypeDao;
import cn.jugame.service.ISysParameterTypeService;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysParameter;
import cn.jugame.vo.SysParameterType;
/**
 * 参数类型服务
 * @author houjt
 *
 */
@Service("SysParameterTypeService")
public class SysParameterTypeService implements ISysParameterTypeService{
	@Autowired
	@Qualifier("SysParameterTypeDao")
	private SysParameterTypeDao sysParameterTypeDao;
	@Override
	public List<SysParameterType> list(){
		return sysParameterTypeDao.queryForPage("from SysParameterType", 0, 20);
	} 
	@Override
	public void save(SysParameterType sysParameterType){ 
		sysParameterTypeDao.insert(sysParameterType);
	}
	@Override
	public SysParameterType findById(int id){
		return sysParameterTypeDao.findById(id);		
	}
	@Override
	public void update(SysParameterType sysParameterType) {
		sysParameterTypeDao.update(sysParameterType);
	}
	@Override
	public void delete(SysParameterType sysParameterType) {
		sysParameterTypeDao.delete(sysParameterType);
	}
	
	@Override
	public PageInfo<SysParameterType> queryParameterTypeList(SysParameterType condition,int pageSize, int pageNo, String sort, String order) {
		StringBuffer hql = new StringBuffer();
		hql.append("from SysParameterType where 1=1 ");
		

		
		hql.append(" order by " + sort + " " + order);
		//总记录数
		int count = sysParameterTypeDao.getRowCount(hql.toString());
		PageInfo<SysParameterType> pageInfo = new PageInfo<SysParameterType>("",count,pageSize);
		
		if (count == 0){
			return pageInfo;
		}
		if (pageNo < 1){
			pageNo = 1;
		}
		pageInfo.setPageno(pageNo);
		int firstResult = (pageNo - 1) * pageInfo.getPageSize();	
		List<SysParameterType> list = sysParameterTypeDao.queryForPage(hql.toString(),firstResult, pageInfo.getPageSize());
		pageInfo.setItems(list);
		
		return pageInfo;
	}
	
}
