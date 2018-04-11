package cn.jugame.admin.notify.impl;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.jugame.util.SpringFactory;
import cn.jugame.util.helper.net.HttpHelper;

public class AppKefuChatCount extends MenuCount {

	JdbcTemplate jdbcOperations = SpringFactory.getBean("jdbcTemplate");

	private static String getCountUrl ="";
	static{
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(AppKefuChatCount.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		getCountUrl = p.getProperty("basekefuurl");
	}
	@Override
	public int getCount(int uid, int userType) {
		if(getCountUrl ==""){
			return 0;
		}
		int count = 0;
		StringBuffer url = new StringBuffer(getCountUrl);
		url.append("interface/appmsg_unread_count.jsp");
		if (url.indexOf("?") < 0){
			url.append("?");
		}
		
		url.append("kefuId=" + uid);
		//url.append("&busiCode=B8868");
	
		HttpHelper httpHelper = new HttpHelper();
		String json =  httpHelper.doGet(url.toString());
		if(json!=null){
			//JSONObject jsonObject = JSONObject.fromObject(json);
			//count = jsonObject.getInt("unreadCount");
			try {
				count = Integer.parseInt(json.toString().trim());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

}
