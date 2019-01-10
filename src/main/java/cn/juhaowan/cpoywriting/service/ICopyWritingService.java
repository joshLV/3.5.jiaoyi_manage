package cn.juhaowan.cpoywriting.service;

import java.util.List;

import cn.juhaowan.cpoywriting.vo.CopyWriting;
import cn.juhaowan.cpoywriting.vo.CopyWritingType;


/**
 * @author th
 * @fileName CopyWritingService.java
 * @declaration 
 * @date 2018年8月23日上午10:21:20
 */
public interface ICopyWritingService {
	/**
	 * 条件查询文案
	 * @return	
	 */
	List<CopyWriting> findAll(int status,int platform,int type,String starTime,String endTime,String title,int pageSize,int startPageNum);
	/**
	 * 保存文案
	 * @param copyWriting
	 */
	int save(String title,String content,int type,int platform,int status,int weight);
	/**
	 * 更新文案
	 * @param copyWriting
	 */
	int update(String title,String content,int type,int platform,int status,int weight,int id);
	/**
	 * 删除文案
	 * @param copyWriting
	 */
	int delete(String[] ids);
	/**
	 * 查询所有文案类别
	 * @return	
	 */
	List<CopyWritingType> findList();
	/**
	 * 保存文案类别
	 * @param copyWritingType
	 */
	int save(String name,int status,int weight);
	/**
	 * 更新文案类别
	 * @param copyWritingType
	 */
	int update(String name,int status,int weight,int id);
	/**
	 * 删除文案类别
	 * @param copyWritingType
	 */
	int typeDelete(String[] ids);
	
}
