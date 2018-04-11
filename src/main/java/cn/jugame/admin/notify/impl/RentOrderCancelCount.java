package cn.jugame.admin.notify.impl;

import java.io.FileInputStream;
import java.util.Properties;

import net.sf.json.JSONObject;
import cn.jugame.util.helper.net.HttpHelper;

public class RentOrderCancelCount extends MenuCount {


	private static String getCountUrl ="";
	static{
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(RentOrderCancelCount.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		getCountUrl = p.getProperty("rent.base.url");
	}
	@Override
	public int getCount(int uid, int userType) {
		if(getCountUrl ==""){
			return 0;
		}
		int count = 0;
		StringBuffer url = new StringBuffer(getCountUrl);
		url.append("/order/get_user_cancel_num");
//		if (url.indexOf("?") < 0){
//			url.append("?");
//		}
		
	
		HttpHelper httpHelper = new HttpHelper();
		String json = "";
		if(json!=null){
			try {
				json = httpHelper.doGet(url.toString());
				
				JSONObject jsonObject = JSONObject.fromObject(json);
				count = jsonObject.getInt("count");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

}
