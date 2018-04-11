package cn.juhaowan.admin.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.jugame.util.SpringFactory;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:spring-config.xml")
public class OrderKefuChatObserverTest {

	//@Test
	public void testNotify() {
		
//		JdbcOperations jdbcOperations = SpringFactory.getBean("jdbcTemplate");
//		
////		OrderKefuChatObserver chatObserver = new OrderKefuChatObserver();
//		chatObserver.setJdbcOperations(jdbcOperations);
//		
//		chatObserver.notify("ORD-140422-21155802625", 10, 1047, 1, "test", "test");
//		
	}

	@Test
	public void testMd5() {
		//76a9b37d2afd2e1b403cda4511fcf123
		String x = "B8868pcorderStatusNotify123ORD-140421-1652452033413982251557931.08868PPPAAA";
		String xx = DigestUtils.md5Hex(x);
		System.out.println("md5=" + xx);
	}
}
