package cn.juhaowan.banner.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.Cache;
import cn.jugame.util.PageInfo;
import cn.juhaowan.banner.service.BannerImagesService;
import cn.juhaowan.banner.vo.PtBannerImages;

/**
 * 横幅图片
 * @author Administrator
 *
 */
@Service("BannerImagesService")
public class DefaultBannerImagesService implements BannerImagesService {
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public int insertPtBannerImg(PtBannerImages bannerImg) {
        int i = 0;
        if(null == bannerImg){
        	return 1;
        }
        String sql = "INSERT INTO `pt_banner_images`(`banner_id`,`name`,`image_url`,`alink`, `weight`," 
        	+ "`create_time`,`modify_time`,`status`,`up_time`,`down_time`)"
            + "VALUES (?, ?,?,?,?,now(), now(),?,?,?);";
        i = jdbcOperations.update(sql,bannerImg.getBannerId(),bannerImg.getName(),bannerImg.getImageUrl(),
        		bannerImg.getAlink(),bannerImg.getWeight(),bannerImg.getStatus(),bannerImg.getUpTime(),bannerImg.getDownTime());
		return i ^ 1;
	}

	@Override
	public int updatePtBannerImg(PtBannerImages bannerImg) {

		int i = 0;
		String sql = "UPDATE `pt_banner_images` SET `name` = ? ,`image_url` = ? ,`alink` = ? ,"
				+ "`weight` = ? ,`modify_time` = now(),`status` = ? ,"
				+ "`up_time` = ?,`down_time` = ? WHERE `id` = ? ";

		i = jdbcOperations.update(sql, bannerImg.getName(),bannerImg.getImageUrl(), bannerImg.getAlink(),
			    bannerImg.getWeight(),bannerImg.getStatus(), bannerImg.getUpTime(),bannerImg.getDownTime(),bannerImg.getId());
		return i ^ 1;
	}

	@Override
	public int deletePtBannerImg(int imgId) {
        int i = 0;
        //String sql = "DELETE FROM `pt_banner_images` WHERE `id` = ? ";
        String sql = "UPDATE `pt_banner_images` SET STATUS = 100 WHERE id = ?;";
        i = jdbcOperations.update(sql,imgId);
        
		return i ^ 1;
	}

	@Override
	public int modifyPtBannerImgWeight(int imgId, int weight) {
        int i = 0;
        if(imgId > 0){
        	String sql = "update pt_banner_images set weight = ? where id = ?";
        	i = jdbcOperations.update(sql,weight,imgId);
        }
		return i ^ 1;
	}

	@Override
	public PtBannerImages queryPtBannerImgById(int imgId) {
		PtBannerImages b = null;
		String sql = "select * from pt_banner_images where id = ?";
		JuRowCallbackHandler<PtBannerImages> callback = new JuRowCallbackHandler<PtBannerImages>(PtBannerImages.class);
		jdbcOperations.query(sql, callback, imgId);
		if (callback.getList().size() < 1) {
			return null;
		}
		b = callback.getList().get(0);
		return b;
	}

	@Override
	public int modifyPtBannerImgStatus(int imgId, int status) {
        int i = 0;
        if(imgId > 0){
        	String sql = "update pt_banner_images set status = ? where id = ?";
        	i = jdbcOperations.update(sql,status,imgId);
        }
		return i ^ 1;
	}

	@Override
	public PageInfo queryPtBannerImgForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
		List<Object> conList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String sqlCount = null;
		int count = 0;
		int firstResult = 1;

		if (null == sort) {
			sort = "create_time";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append("from pt_banner_images where 1 = 1 ");

		sql.append(" order by " + sort + " " + order);
		sqlCount = "select count(*) " + sql.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<PtBannerImages> pageinfo = new PageInfo<PtBannerImages>(count, pageSize);
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
		JuRowCallbackHandler<PtBannerImages> cb = new JuRowCallbackHandler<PtBannerImages>(PtBannerImages.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());

		return pageinfo;
	}

	@Override
	public List<PtBannerImages> listAllPtBannerImg() {
		List<PtBannerImages> list = null;
		String sql = "select * from pt_banner_images";
		JuRowCallbackHandler<PtBannerImages> callback = new JuRowCallbackHandler<PtBannerImages>(PtBannerImages.class);
		jdbcOperations.query(sql, callback);
		if(callback.getList().size() > 0){
			return callback.getList();
		}
		return list;
	}

	//加3分钟缓存
	@Override
	public List<PtBannerImages> queryListByBanberId(int bannerId) {
		List<PtBannerImages> list = null;
		String cachekey = "banner/queryListByBanberId/" + bannerId;
		list = Cache.get(cachekey);
		if(null == list){
		String sql = "select * from pt_banner_images where banner_id = ? and status = 1 order by weight asc";
		JuRowCallbackHandler<PtBannerImages> callback = new JuRowCallbackHandler<PtBannerImages>(PtBannerImages.class);
		jdbcOperations.query(sql, callback,bannerId);
		if(callback.getList().size() > 0){
			list = callback.getList();
			if(null != list){
				Cache.set(cachekey, 180, list);
			}
		}
		}
		return list;
	}

	@Override
	public int delByBannerId(int bannerId) {
		int i = 0;
        //String sql = "DELETE FROM `pt_banner_images` WHERE `banner_id` = ? ";
        String sql = "UPDATE `pt_banner_images` SET STATUS = 100 WHERE banner_id = ? ;";
        i = jdbcOperations.update(sql,bannerId);
        
		return i ^ 1;
	}

	@Override
	public List<PtBannerImages> queryListByBanberIdBack(int bannerId,int statusType) {
		List<PtBannerImages> list = null;
		String sql = "select * from pt_banner_images where banner_id = ? AND status != 100  order by weight asc";
		if(100 == statusType){
			sql = "select * from pt_banner_images where banner_id = ? AND status = 100  order by weight asc";
		}
		
		JuRowCallbackHandler<PtBannerImages> callback = new JuRowCallbackHandler<PtBannerImages>(PtBannerImages.class);
		jdbcOperations.query(sql, callback,bannerId);
		if(callback.getList().size() > 0){
			return callback.getList();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryListByTag(String tag) {
		List<Map<String,Object>> list = null;
		String sql ="SELECT  name, image_url,alink link,weight FROM `pt_banner_images` WHERE banner_id = (SELECT id FROM `pt_banner` WHERE tag = ? AND STATUS = 1) AND STATUS =1 ORDER BY weight DESC";
		list = jdbcOperations.queryForList(sql,tag);
		return list;
	}
	
	
	

}
