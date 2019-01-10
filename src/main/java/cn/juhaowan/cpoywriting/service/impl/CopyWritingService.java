package cn.juhaowan.cpoywriting.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.cpoywriting.dao.CopyWritingDao;
import cn.juhaowan.cpoywriting.service.ICopyWritingService;
import cn.juhaowan.cpoywriting.vo.CopyWriting;
import cn.juhaowan.cpoywriting.vo.CopyWritingType;

/**
 * @author th
 * @fileName CopyWritingService.java
 * @declaration 
 * @date 2018年8月23日上午10:22:43
 */
@Service("copyWritingService")
public class CopyWritingService implements ICopyWritingService{
	
	@Autowired
	private CopyWritingDao copyWritingDao;
	
	@Autowired
	private JdbcOperations jdbcTemplate;
	
	@Override
	public List<CopyWriting> findAll(int status,int platform,int type,String starTime,String endTime,String title,int startPageNum,int pageSize) {
		StringBuffer select = new StringBuffer("select * from copy_writing where 1=1 ");
		if(status > 0){
			select.append(" and status = " +  status);
		}
		if(platform >0){
			select.append(" and platform = " + platform);
		}
		if(type >0){
			select.append(" and type = " + type);
		}
		if(StringUtils.isNotBlank(starTime)){
			select.append(" and create_time > '" + starTime + " 00:00:00' ");
		}
		if(StringUtils.isNotBlank(endTime)){
			select.append(" and modify_time < '" + endTime + " 23:59:59' ");
		}
		if(StringUtils.isNotBlank(title)){
			select.append(" and title like '%" + title + "%'");
		}
		select.append(" order by weight ASC LIMIT ");
		select.append(startPageNum + ",");
		select.append(pageSize);
//		ParameterizedBeanPropertyRowMapper<CopyWriting> newInstance = ParameterizedBeanPropertyRowMapper.newInstance(CopyWriting.class);
		JuRowCallbackHandler<CopyWriting> callback = new JuRowCallbackHandler<CopyWriting>(CopyWriting.class);
		jdbcTemplate.query(select.toString(), callback);
		List<CopyWriting> findList = callback.getList();
		return findList;
	}
	

	@Override
	public int save(String title,String content,int type,int platform,int status,int weight) {
		String sql = "insert copy_writing(title,content,type,platform,create_time,modify_time,status,weight) values(?,?,?,?,NOW(),NOW(),?,?)";
		int update = jdbcTemplate.update(sql, title, content, type, platform, status, weight);
		return update;
	}

	@Override
	public int update(String title,String content,int type,int platform,int status,int weight,int id) {
		String sql = "update copy_writing set title=?,content=?,type=?,platform=?,modify_time=NOW(),status=?,weight=? where id = ?";
		int update = jdbcTemplate.update(sql, title, content, type, platform, status, weight, id);
		return update;
	}

	@Override
	public int delete(String[] ids) {
		StringBuffer sql = new StringBuffer("delete from copy_writing where id in (");
		if(ids.length < 0){
			return 0;
		}
		for (int i = 0; i < ids.length; i++) {
			sql.append(ids[i]);
			if(i < ids.length-1){
				sql.append(",");
			}else{
				sql.append(")");
			}
		}
		int update = jdbcTemplate.update(sql.toString());
		return update;
	}

	@Override
	public List<CopyWritingType> findList() {
		String sql = "select * from copy_writing_type order by weight ASC";
		JuRowCallbackHandler<CopyWritingType> callback = new JuRowCallbackHandler<CopyWritingType>(CopyWritingType.class);
		jdbcTemplate.query(sql, callback);
		List<CopyWritingType> findList = callback.getList();
		return findList;
	}

	@Override
	public int save(String name,int status,int weight) {
		String sql = "insert copy_writing_type(name,create_time,modify_time,status,weight) values(?,NOW(),NOW(),?,?)";
		int update = jdbcTemplate.update(sql, name, status, weight);
		return update;
	}

	@Override
	public int update(String name,int status,int weight,int id) {
		String sql = "update copy_writing_type set name = ?,modify_time=NOW(),status=?,weight=? where id = ?";
		int update = jdbcTemplate.update(sql, name, status, weight, id);
		return update;
	}

	@Override
	public int typeDelete(String[] ids) {
		StringBuffer sql = new StringBuffer("delete from copy_writing_type where id in (");
		for (int i = 0; i < ids.length; i++) {
			sql.append(ids[i]);
			if(i < ids.length-1){
				sql.append(",");
			}else{
				sql.append(")");
			}
		}
		int update = jdbcTemplate.update(sql.toString());
		return update;
	}

}
