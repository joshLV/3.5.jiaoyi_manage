package cn.jugame.admin.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.MessageBean;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 人工发货
 * 
 */
@Controller
public class RedispatchController {
	private static String REHOST;
	private static String REPORT;
	private static String RESTATUS;
	private static String REPWD;
	private static String QUEUENAME;
	
	private static String APIQUEUENAME;
	private static String BROKERURL;
	static {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(//
					RedispatchController.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		REHOST = pro.getProperty("dispatch.host");
		REPORT = pro.getProperty("dispatch.port");
		RESTATUS = pro.getProperty("dispatch.status");
		REPWD = pro.getProperty("dispatch.pwd");
		QUEUENAME = pro.getProperty("dispatch.queueName");
		BROKERURL = pro.getProperty("dispatch.api.brokerurl");
		APIQUEUENAME = pro.getProperty("dispatch.api.queueName");
	}
	@Autowired
	private BackUserLogService backUserLogService;
	

	/**
	 * 人工发货页面
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/redispatchEdit")
	public String redispatchDisplay(HttpServletRequest request, Model model) {
		String orderId = request.getParameter("o");
		if (!StringUtils.isBlank(orderId)) {
			model.addAttribute("orderId", orderId);
		} else {
			model.addAttribute("orderId", "");
		}
		return "order/redispatch";
	}

	/**
	 * @param request
	 *            请求
	 * @param model
	 *            模型驱动
	 * @return 提示信息
	 */
	@RequestMapping(value = "/order/redispatchAdd")
	@ResponseBody
	public MessageBean redispatchAdd(HttpServletRequest request, Model model) {
		String orderId = request.getParameter("o");
		if (StringUtils.isBlank(orderId)) {
			return new MessageBean(false, "订单ID不能为空");
		}
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://" + REHOST + ":" + REPORT + "/" + QUEUENAME + "?o=" + orderId + "&s=" + RESTATUS + "&p=" + REPWD);
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();
			Map<String, String> map = BackUserLogUtil.findUserInfo(request);
			backUserLogService.addLog(
					Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
					map.get(BackUserLogUtil.USER_IP),
					BackUserLogService.ORDER_DISPATCH,
					"管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对订单号"
							+ orderId + "进行人工发货");
			if (entity != null) {

				InputStream instream = entity.getContent();
				instream.close();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new MessageBean();
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return 提示信息
	 */
	@RequestMapping(value = "/order/redispatchAddNew")
	@ResponseBody
	public MessageBean redispatchAddNew(HttpServletRequest request, Model model) {
		String orderId = request.getParameter("o");
		if (StringUtils.isBlank(orderId)) {
			return new MessageBean(false, "订单ID不能为空");
		}
        try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(BROKERURL);   
			Connection connection = factory.createConnection();   
			connection.start();   
			Queue queue = new ActiveMQQueue(APIQUEUENAME);   
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
			Message message = session.createTextMessage(orderId);   
			MessageProducer producer = session.createProducer(queue);   
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
			return new MessageBean(false,"发货异常");
		} 
        Map<String, String> map = BackUserLogUtil.findUserInfo(request);
        backUserLogService.addLog(
				Integer.parseInt(map.get(BackUserLogUtil.USER_ID)),
				map.get(BackUserLogUtil.USER_IP),
				BackUserLogService.ORDER_DISPATCH,
				"管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对订单号" + orderId + "进行人工发货");
		return new MessageBean();
	}
	


	
//    public static void main(String[] args) throws Exception {   
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.0.81:61616");   
//        Connection connection = factory.createConnection();   
//        connection.start();   
//        Queue queue = new ActiveMQQueue("jiaoyi.order.paysucess");   
//        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
//        Message message = session.createTextMessage("order_test_2");   
//        MessageProducer producer = session.createProducer(queue);   
//        producer.send(message);   
//        System.out.println("Send Message Completed!");   
//        MessageConsumer comsumer = session.createConsumer(queue);   
//        Message recvMessage = comsumer.receive();   
//        System.out.println(((TextMessage)recvMessage).getText());   
//    }   
}
