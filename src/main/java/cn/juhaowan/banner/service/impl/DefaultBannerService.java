package cn.juhaowan.banner.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.banner.service.BannerService;
import cn.juhaowan.banner.vo.PtBanner;
/**
 * 横幅
 * @author Administrator
 *
 */
@Service("BannerService")
public class DefaultBannerService implements BannerService {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public int insertPtBanner(PtBanner banner) {
		int i = 0;
		int j = 0;
		if (banner != null) {
			String sql = "INSERT INTO pt_banner( `name`,`tag`, `remark`,`create_time`,`modify_time`,`status`,`weight`)VALUES ("
                         + "?,?,?,now(),now(),0,?)";
			i = jdbcOperations.update(sql, banner.getName(), banner.getTag(), banner.getRemark(), banner.getWeight());
			
			if(i > 0){
				String sql2 = "SELECT max(id) from pt_banner;";
				SqlRowSet rs = jdbcOperations.queryForRowSet(sql2);

				while (rs.next()) {
					j =  rs.getInt(1);
				
				}
			}
		} 
		return j;
	}

	@Override
	public int updatePtBanner(PtBanner banner) {
       int i = 0;
       if(banner != null){
    	   String sql = "UPDATE pt_banner SET name = ?,tag = ?,remark = ?," 
    	   		+ "modify_time = now(),status = ?,weight = ? " 
                + "WHERE id = ?";
		i = jdbcOperations.update(sql, banner.getName(), banner.getTag(), banner.getRemark(),banner.getStatus(), banner.getWeight(),banner.getId());
       }
		return i ^ 1;
	}

	@Override
	public int deletePtBanner(int bannerId) {
		int i = 0;
		if(bannerId > 0){
			//String sql = "delete from pt_banner where id = ?";
			String sql = "UPDATE `pt_banner` SET `status` = 100 WHERE id = ?";
			i = jdbcOperations.update(sql,bannerId);
		}
		return i ^ 1;
	}

	@Override
	public int modifyPtBannerWeight(int bannerId, int weight) {
        int i = 0;
        if(bannerId > 0){
        	String sql = "update pt_banner set weight = ? where id = ?";
        	i = jdbcOperations.update(sql,weight,bannerId);
        }
		return i ^ 1;
	}

	@Override
	public PtBanner queryPtBannerById(int bannerId) {
		PtBanner b = null;
		String sql = "select * from pt_banner where id = ?";
		JuRowCallbackHandler<PtBanner> callback = new JuRowCallbackHandler<PtBanner>(PtBanner.class);
		jdbcOperations.query(sql, callback, bannerId);
		if (callback.getList().size() < 1) {
			return null;
		}
		b = callback.getList().get(0);
		return b;
	}

	@Override
	public int modifyPtBannerStatus(int bannerId, int status) {
        int i = 0;
        if(bannerId > 0){
        	String sql = "update pt_banner set status = ? where id = ?";
        	i = jdbcOperations.update(sql,status,bannerId);
        }
		return i ^ 1;
	}

	@Override
	public PageInfo<PtBanner> queryPtBannerForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {

		
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "weight";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from pt_banner where 1 = 1 ");

		if (null != conditions) {

			if (null != conditions.get("name")) {
				sql.append(" and name = ? ");
				conList.add(conditions.get("name"));
			}
		}

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<PtBanner> pageinfo = new PageInfo<PtBanner>(count, pageSize);
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
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sql.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sql.toString();
		JuRowCallbackHandler<PtBanner> cb = new JuRowCallbackHandler<PtBanner>(PtBanner.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@Override
	public List<PtBanner> listAllPtBanner() {
		List<PtBanner> list = null;
		String sql = "select * from pt_banner";
		JuRowCallbackHandler<PtBanner> callback = new JuRowCallbackHandler<PtBanner>(PtBanner.class);
		jdbcOperations.query(sql, callback);
		if(callback.getList().size() > 0){
			return callback.getList();
		}
		return list;
	}

	//加3分钟缓存
	@Override
	public PtBanner queryListByTag(String tag) {
		PtBanner p = null;
		String cachekey = "banner/queryListByTag/" + tag;
		p = Cache.get(cachekey);
		if (null == p) {

		String sql = "select * from pt_banner where tag = ? and status = 1 ";
		JuRowCallbackHandler<PtBanner> callback = new JuRowCallbackHandler<PtBanner>(PtBanner.class);
		jdbcOperations.query(sql, callback,tag);
		if(callback.getList().size() > 0){
			p = callback.getList().get(0);
			if (null != p) {
				Cache.add(cachekey, 180, p);
			}
		}
		}
		return p;
	}

	@Override
	public boolean tagExist(String tag) {
		String sql = "select * from pt_banner where tag = ? ";
		JuRowCallbackHandler<PtBanner> callback = new JuRowCallbackHandler<PtBanner>(PtBanner.class);
		jdbcOperations.query(sql, callback,tag);
		if(callback.getList().size() > 0){
			return true;
		}
		return false;
	}
	
	

}
