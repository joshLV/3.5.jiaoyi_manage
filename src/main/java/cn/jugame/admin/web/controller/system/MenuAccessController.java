package cn.jugame.admin.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.util.Cache;
import cn.jugame.util.Config;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.GlobalsKeys;

@Controller
public class MenuAccessController {
	Logger logger = LoggerFactory.getLogger(MenuAccessController.class);
	private static final String MENU_ACCESS_CACHE_KEY = "menu_access_cache_key_"+Math.round(100000+Math.random()*100000);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取用户访问菜单的统计数据,Extjs实现前端
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/system/menuAccessExt")
	public String memberListExt(HttpServletRequest request, Model model) {
		model.addAttribute("modList", jdbcTemplate.queryForList("select MOD_ID,MODULE_NAME from sys_module where FIRST_PAGE!='javascript:' and STATUS=1 "));
		String contextAllPath = getContextAllPath(request);
		model.addAttribute("contextPath", contextAllPath);
		model.addAttribute("resourcesPath", Config.getValue("static.resources.path"));
		model.addAttribute("indexPath", contextAllPath+"/app/view/menuAccess/index.js");
		return "bodyStyleExt4";
	}
	
	/**
	 * 获取应用请求地址，如：http://localhost:8080
	 * @param request
	 * @return
	 */
	public static String getContextAllPath(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
		String path = sb.toString();
		sb = null;
		return path;
	}
	
	/**
	 * 获取点击菜单的历史统计记录,Extjs实现前端
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/system/menuAccess_listDataExt")
	@ResponseBody
	public JSONObject listDataExt(HttpServletRequest request,HttpServletResponse response,Model model) {
		// 拼接sql
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select sm.PARENT_ID,sm.MODULE_NAME mod_name,count(am.id) total ");
		sql.append("from sys_module sm left join access_menu am on (sm.MOD_ID=am.mod_id ");
		String menuId = request.getParameter("menuId");
		if(StringUtils.isNotBlank(menuId)){
			sql.append("and sm.MOD_ID=? ");
			params.add(menuId);
		}
		String startDate = request.getParameter("startDate");
		if(StringUtils.isNotBlank(startDate)){
			sql.append("and am.create_date>=? ");
			params.add(startDate);
		}
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotBlank(endDate)){
			sql.append("and am.create_date<=? ");
			params.add(endDate);
		}
		sql.append(") where STATUS=1 and PARENT_ID!=0 ");
		sql.append("GROUP BY sm.MOD_ID ");
		sql.append("order by count(am.id) desc,sm.MOD_ID ");
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(),params.toArray());
		setMenuParentName(dataList);
		JSONObject data = new JSONObject();
		data.put("items",JSONArray.fromObject(dataList));
		return data;
	}
	/**
	 * 设置菜单的父菜单名称
	 * @param dataList
	 */
	private void setMenuParentName(List<Map<String, Object>> dataList){
		Map<Integer,Map<String,Object>> menus = getMenus();
		Iterator<Map<String, Object>> ite = dataList.iterator();
		while(ite.hasNext()){
			Map<String, Object> dataRow = ite.next();
			Integer parentMenuId = 0;
			try{
				parentMenuId = Integer.parseInt(dataRow.get("PARENT_ID").toString());
			}catch(Exception e){
				logger.warn("转换dataList中的MOD_ID为Integer时异常", e);
				continue;
			}
			dataRow.remove("PARENT_ID");
			String parentMenuName = "";
			if(null != menus.get(parentMenuId) && null != menus.get(parentMenuId).get("thisMenuName")){
				parentMenuName = menus.get(parentMenuId).get("thisMenuName").toString();
			}
			dataRow.put("parent_mod_name", parentMenuName);
		}
	}
	/**
	 * 获取所有菜单
	 * @return
	 */
	private Map<Integer,Map<String,Object>> getMenus(){
		Map<Integer,Map<String,Object>> menusMap = Cache.get(MENU_ACCESS_CACHE_KEY);
		if(menusMap == null){
			menusMap = new HashMap<Integer,Map<String,Object>>();
			List<Map<String, Object>> menus = jdbcTemplate.queryForList("select MOD_ID,PARENT_ID,MODULE_NAME from sys_module");
			Iterator<Map<String, Object>> ite = menus.iterator();
			while(ite.hasNext()){
				Map<String, Object> menu = ite.next();
				Map<String,Object> menuInfo = new HashMap<String,Object>();
				menuInfo.put("parentMenuId", menu.get("PARENT_ID"));
				menuInfo.put("thisMenuName", menu.get("MODULE_NAME"));
				Integer menuId = 0;
				try{
					menuId = Integer.parseInt(menu.get("MOD_ID").toString());
				}catch(Exception e){
					logger.warn("转换MOD_ID为Integer异常", e);
					continue;
				}
				menusMap.put(menuId, menuInfo);
			}
			Cache.add(MENU_ACCESS_CACHE_KEY, 60, menusMap);
		}
		return menusMap;
	}
	
	/**
	 * 菜单数据,Extjs实现前端
	 * @return
	 */
	@RequestMapping(value = "/system/menuAccess_menuData")
	@ResponseBody
	public JSONObject menuData() {
		JSONObject data = new JSONObject();
		data.put("data",JSONArray.fromObject(jdbcTemplate.queryForList("select MOD_ID,MODULE_NAME from sys_module where FIRST_PAGE!='javascript:' order by order_no ")));
		return data;
	}
	
	/**
	 * 保存点击菜单记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/system/menuAccess_add")
	@ResponseBody
	public String addAccessData(HttpServletRequest request){
		SysUserinfo loginUser = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if(loginUser == null){
			return "";
		}
		int menuId = -1;
		try{
			menuId = Integer.parseInt(request.getParameter("menuId"));
		}catch(Exception e){
		}
		if(menuId == -1){
			return "";
		}
//		SysModule sysModule = sysModuleDao.findById(menuId);
		String menuName = request.getParameter("menuName");
		jdbcTemplate.update("insert into access_menu(uid,nick_name,mod_id,mod_name,create_date,create_time) values(?,?,?,?,now(),now())",
				loginUser.getUserId(),loginUser.getCustomerNickname(),menuId,menuName);
		return "";
	}
	
	/**
	 * 获取用户访问菜单的统计数据
	 * @param request
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "/system/menuAccess")
//	public String memberList(HttpServletRequest request, Model model) {
//		model.addAttribute("modList", jdbcTemplate.queryForList("select MOD_ID,MODULE_NAME from sys_module where FIRST_PAGE!='javascript:' "));
//		return "system/menuAccess";
//	}
	
	/**
	 * 获取点击菜单的历史统计记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "/system/menuAccess_listData")
//	@ResponseBody
//	public JSONObject listData(HttpServletRequest request,HttpServletResponse response,Model model) {
//		// 获取页面的页码和每页的行数
//		int pageNumber = RequestUtils.getParameterInt(request, "pageNum", 1);
//		int defaultPageSize = 10;
//		int pageSize = RequestUtils.getParameterInt(request, "pageSize", defaultPageSize);
//		if(pageSize<=0){
//			pageSize = defaultPageSize;
//		}
//		// 拼接sql
//		List<Object> params = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select mod_name,count(*) total from access_menu where 1=1 ");
//		String menuId = request.getParameter("menuId");
//		if(StringUtils.isNotBlank(menuId)){
//			sql.append("and mod_id=? ");
//			params.add(menuId);
//		}
//		String startDate = request.getParameter("startDate");
//		if(StringUtils.isNotBlank(startDate)){
//			sql.append("and create_date>=? ");
//			params.add(startDate);
//		}
//		String endDate = request.getParameter("endDate");
//		if(StringUtils.isNotBlank(endDate)){
//			sql.append("and create_date<=? ");
//			params.add(endDate);
//		}
//		sql.append("GROUP BY mod_id ");
//		// 以上sql为查询总数的（分页）
//		List<Map<String, Object>> dataListAll = jdbcTemplate.queryForList(sql.toString(),params.toArray());
//		int totalRow = dataListAll.size();
//		// 以下sql是拼接当前页的过滤条件
//		int offset = pageSize * (pageNumber - 1);
//		sql.append("order by count(*) desc ");
//		sql.append("limit ").append(offset).append(", ").append(pageSize);
//		List<Map<String, Object>> dataListPage = jdbcTemplate.queryForList(sql.toString(),params.toArray());
//		JSONObject data = new JSONObject();
//		data.put("pageNumber", pageNumber);
//		data.put("pageSize", pageSize);
//		int totalPage = (int) (totalRow / pageSize);
//		if (totalRow % pageSize != 0) {
//			totalPage++;
//		}
//		data.put("totalPage", totalPage);
//		data.put("totalRow", totalRow);
//		data.put("list",JSONArray.fromObject(dataListPage));
//		response.setContentType("application/json;charset=UTF-8");
//		return data;
//	}
	
}
