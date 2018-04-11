<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.jugame.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cn.jugame.dal.callback.JuRowCallbackHandler"%>
<%@page import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@page import="cn.jugame.util.SpringFactory"%>
<%@page import="javax.jms.JMSException"%>
<%@page import="javax.jms.MessageProducer"%>
<%@page import="javax.jms.Message"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="cn.jugame.web.util.GlobalsKeys"%>
<%@page import="cn.jugame.service.ISysUserinfoService"%>
<%@page import="javax.jms.Session"%>
<%@page import="org.apache.activemq.command.ActiveMQQueue"%>
<%@page import="javax.jms.Queue"%>
<%@page import="javax.jms.Connection"%>
<%@page import="org.apache.activemq.ActiveMQConnectionFactory"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%
String uid = request.getParameter("uid"); 
if(StringUtils.isBlank(uid)){
	out.println("请输入uid");
	return;
}

JdbcTemplate jdbcTemplate = SpringFactory.getBean("jdbcTemplate");
String sql = "SELECT * FROM sys_userinfo WHERE user_id >= "+uid+" AND user_id <= "+uid;
JuRowCallbackHandler<Member> jr = new JuRowCallbackHandler<Member>(Member.class);
jdbcTemplate.query(sql, jr);
List<Member> list = jr.getList();
for (int i = 0; i < list.size(); i++) {
	try {
		Thread.sleep(2000);
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		ISysUserinfoService iSysUserinfoService = SpringFactory.getBean("SysUserinfoService");
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.1.242:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Queue queue = new ActiveMQQueue("jiaoyi.kefu.status.kefusys");
		final Session session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		JSONObject json = JSONObject.fromObject(list.get(i));
		Message message = session2.createTextMessage(json.toString());
		MessageProducer producer = session2.createProducer(queue);
		out.println(json);
		producer.send(message);
		producer.close();
		connection.close();
	} catch (JMSException e) {
		e.printStackTrace();
		out.println("推送更新客服信息异常");
	}
}
%>