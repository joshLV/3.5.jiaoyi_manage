<%@page import="cn.jugame.dal.callback.JuRowMapper"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="cn.juhaowan.order.vo.RechargeableCard"%>
<%@page import="java.util.List"%>
<%

String sql = "SELECT * FROM rechargeable_card";
JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
JuRowMapper<RechargeableCard> rm = new JuRowMapper<RechargeableCard>(RechargeableCard.class);
List<RechargeableCard> list = jdbcTemplate.query(sql, rm);
for (int i = 0; i < list.size(); i++) {
	String num = list.get(i).getNumber();
	String nNum = num.trim();
	String nNum2 = nNum.replace("    ", "");
	String nSql = "UPDATE rechargeable_card SET number=? WHERE number=?";
	int b = jdbcTemplate.update(nSql, nNum2, num);
	out.println(b);
}

%>