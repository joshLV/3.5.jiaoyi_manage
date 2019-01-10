package cn.juhaowan.cpoywriting.dao;

import java.util.List;

import cn.jugame.dao.BaseDao;
import cn.juhaowan.cpoywriting.vo.CopyWriting;

/**
 * @author th
 * @fileName CopyWritingDao.java
 * @declaration 
 * @date 2018年8月23日上午10:33:11
 */
public interface CopyWritingDao extends BaseDao<CopyWriting>{
	
	/**
	 * 
	 * @author th
	 * @fileName CopyWritingDao.java
	 * @declaration 根据id查询
	 * @date 2018年8月23日上午10:41:02
	 * 
	 * @param id
	 * @return
	 */
	public CopyWriting findById(int id);
	
	public List<CopyWriting> findList(String sql);
}
