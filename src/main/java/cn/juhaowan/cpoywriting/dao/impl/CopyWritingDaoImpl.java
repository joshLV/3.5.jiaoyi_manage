package cn.juhaowan.cpoywriting.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jugame.dao.impl.BaseDaoImpl;
import cn.juhaowan.cpoywriting.dao.CopyWritingDao;
import cn.juhaowan.cpoywriting.vo.CopyWriting;

/**
 * @author th
 * @fileName CopyWritingDaoImpl.java
 * @declaration 
 * @date 2018年8月23日上午10:33:33
 */
@Repository("copyWritingDao")
public class CopyWritingDaoImpl extends BaseDaoImpl<CopyWriting> implements CopyWritingDao{
	
	@Override
	public CopyWriting findById(int id) {
		return (CopyWriting)getSession().get(CopyWriting.class , id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CopyWriting> findList(String sql) {
		List<CopyWriting> list = getSession().createSQLQuery(sql).list();
		return list;
	}
	
}