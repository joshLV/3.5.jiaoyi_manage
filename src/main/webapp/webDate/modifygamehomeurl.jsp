<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.slf4j.Logger"%>
<%!Logger log = LoggerFactory.getLogger("update_product_saleout_num.jsp");%>
<%


	SysUserinfo sys = (SysUserinfo)session.getAttribute(GlobalsKeys.ADMIN_KEY);
	String uid ="";
	if(sys ==null){
		response.sendRedirect("../nopermission.html");
	}
    JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
    
    //更新渠道表
    String csql = "SELECT * FROM `channel`";
    String parsql = "SELECT * FROM sys_parameter WHERE para_code = ? ";
    String addcSql = "UPDATE `channel` SET channel_home_url = ? WHERE id = ?";
    List<Map<String,Object>> cList = jdbcTemplate.queryForList(csql);
    for(int i = 0;i <cList.size();i++){
    	String key ="channel.home.url."+cList.get(i).get("id");
    	 List<Map<String,Object>> pList = jdbcTemplate.queryForList(parsql,key);
    	 if(pList.size()>0){
    		 System.out.println("----"+cList.get(i).get("id") +"-----"+pList.get(0).get("para_value") +"___"+key);
    		 jdbcTemplate.update(addcSql,pList.get(0).get("para_value"),cList.get(i).get("id"));
    	 }
    	
    }
    
    
    //更新游戏表的官方网站字段
    String gsql = "SELECT * FROM game";
    String addgsql = "UPDATE game SET game_home_url = ? WHERE game_id=?";
    List<Map<String,Object>> gList = jdbcTemplate.queryForList(gsql);
    for(int j = 0;j <gList.size();j++){
    	String key2 ="game.home.url."+gList.get(j).get("game_id");
    	 List<Map<String,Object>> pList2 = jdbcTemplate.queryForList(parsql,key2);
    	 if(pList2.size()>0){
    		 System.out.println("----"+gList.get(j).get("game_id") +"-----"+pList2.get(0).get("para_value") +"___"+key2);
    		 jdbcTemplate.update(addgsql,pList2.get(0).get("para_value"),gList.get(j).get("game_id"));
    	 }
    	
    }

	
%>


<html>
<body>

<form action="" >
<table>
<tr>
<td><input type="submit" name="submit" value="开始更新"/></td>
<td></td>
</tr>
</table>

</form>

</body>


</html>