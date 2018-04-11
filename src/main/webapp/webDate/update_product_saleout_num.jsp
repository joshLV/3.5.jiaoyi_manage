<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.vo.SysUserinfo"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.InputStream"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%!Logger log = LoggerFactory.getLogger("update_product_saleout_num.jsp");%>
<%


	SysUserinfo sys = (SysUserinfo)session.getAttribute(GlobalsKeys.ADMIN_KEY);
	String uid ="";
	if(sys ==null){
		response.sendRedirect("../nopermission.html");
	}
    JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
	String saleoutnum = request.getParameter("salenum");
	String oids = request.getParameter("oids");
	int count = 0;
    if(oids != null){	
	String[] ss = oids.split("\r\n");
	for(int i =0;i <ss.length;i++){
		//System.out.println("===ss======" + ss[i]);
		String upproduct = "UPDATE `product`  SET `product_soldout_number`= IF(product_soldout_number + ? >0,product_soldout_number +?,0) WHERE product_id = ? ";
		String uponsale  = "UPDATE `product_onsale`  SET `product_soldout_number`= IF(product_soldout_number + ? >0,product_soldout_number +?,0) WHERE product_id = ?";
		int j = jdbcTemplate.update(upproduct, saleoutnum.trim(),saleoutnum.trim(),ss[i].trim());
		//System.out.println("更新商品表结果 + "+ j);
		if(j > 0){
			int k = jdbcTemplate.update(uponsale, saleoutnum.trim(),saleoutnum.trim(),ss[i].trim());
			count++;
		}
	}
	response.sendRedirect("update_product_saleout_num_result.jsp?result="+count);
    }
	

	
%>


<html>
<body>

<form action="" >
<table>
<tr>
<td>
商品ID:
</td>
<td>
<textarea rows="20" cols="70" name="oids"></textarea>填入商品号 每行一个（ 填入数量最好不要超过50条）
</td>
</tr>
<tr>
<td>
增/减销量:
</td>
<td>
<input name="salenum" type="text"/>例如：填入2 增加销量2 ; 填入-1 减少销量1
</td>
</tr>

<tr>
<td><input type="submit" name="submit" value="提交"/></td>
<td></td>
</tr>
</table>

</form>

</body>


</html>