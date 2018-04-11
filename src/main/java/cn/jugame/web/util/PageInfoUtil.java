package cn.jugame.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.jugame.util.PageInfo;

/**
 * 通用分页工具类
 * @author Clenky
 */
public class PageInfoUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PageInfoUtil.class);

	/**
	 * 获取分页查询对象
	 * @param jdbcTemplate
	 * @param querySQL
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static PageInfo<Map<String,Object>> getPageInfo(JdbcTemplate jdbcTemplate,
			String querySQL,List<Object> params, int pageNo, int pageSize){
		int count = 0;
		int startRow = 1;
		String countSQL = null;
		
		//查询SQL处理
		if(!StringUtils.isBlank(querySQL)){
			int index=querySQL.toLowerCase().indexOf(" from ");
			if(index!=-1){
				countSQL="Select Count(1) As cnt"+querySQL.substring(index);
			}
			int endIndex=countSQL.toLowerCase().lastIndexOf(" order by ");
			if(endIndex!=-1){
				countSQL=countSQL.substring(0,endIndex);
			}
		}
		
		Map<String,Object> rsMap = null;
		List<Map<String,Object>> rsList = null;
		
		//logger.info("countSQL="+countSQL);
		
		if(null!=params && !params.isEmpty()){
			rsMap = jdbcTemplate.queryForMap(countSQL, params.toArray());
		}else{
			rsMap = jdbcTemplate.queryForMap(countSQL);
		}
		
		if(null!=rsMap && !rsMap.isEmpty()){
			count=Integer.parseInt(rsMap.get("cnt").toString());
		}
		
		//分页计算处理
		PageInfo<Map<String,Object>> pageinfo = new PageInfo<Map<String,Object>>(count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		startRow = (pageNo - 1) * pageinfo.getPageSize();

		querySQL=querySQL+(" limit " + startRow + "," + pageinfo.getPageSize());

		logger.info("querySQL="+querySQL);
		
		if(null!=params && !params.isEmpty()){
			rsList=jdbcTemplate.queryForList(querySQL, params.toArray());
		}else{
			rsList=jdbcTemplate.queryForList(querySQL);
		}
		
		//针对JSON对象null值转为空字符串
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(null!=rsList && !rsList.isEmpty()){
			for(int i=0,n=rsList.size();i<n;i++){
				   Map<String, Object> map=new HashMap<String, Object>();
				   Map<String, Object> rowMap=rsList.get(i);
				   Iterator<Map.Entry<String, Object>> it=rowMap.entrySet().iterator();
				   while(it.hasNext()){
					   Map.Entry<String, Object> entry=it.next();
					   String key=entry.getKey();
					   Object value=entry.getValue();
					   if(null!=value){
						   if(value instanceof Date){
							   try{
								   map.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
							   }catch(Exception e){
								   try{
									   map.put(key, new SimpleDateFormat("yyyy-MM-dd").format(value));
								   }catch(Exception ex){
									   map.put(key, value);
								   }
							   }
						   }else{
							   map.put(key, value);
						   }
					   }else{
						   map.put(key, "");
					   }
				   }
				   list.add(map);
			}
		}
		
		pageinfo.setItems(list);
		
		return pageinfo;
	}
}
