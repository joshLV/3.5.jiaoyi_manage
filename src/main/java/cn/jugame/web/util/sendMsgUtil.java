package cn.jugame.web.util;

import java.util.Map;
import java.util.TreeMap;

import cn.jugame.util.Config;
import cn.jugame.util.helper.misc.ChecksumHelper;

public class sendMsgUtil {
	public static String send(String mobile){
		try {
			Map<String,String> params = new TreeMap<String,String>();
			params.put("appKey", "admin");
			params.put("signName", "8868交易平台");
			params.put("smsId", "SMS_1445015");
			params.put("mobile", mobile);
			String vcode = ChecksumHelper.getChecksum(params, Config.getValue("msg.vkey"));
			params.put("vcode", vcode);
			//发送短信
			String url = Config.getValue("msg.url.send");
			String str = HttpRequestUtil.dohttpRequestByPost(url, params);
		    
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String verify(String code,String mobile){
		String str = "";
		try {
			Map<String,String> params = new TreeMap<String,String>();
			params.put("appKey", "admin");
			params.put("code", code);
			params.put("smsId", "SMS_1445015");
			params.put("mobile", mobile);
			String url = Config.getValue("msg.url.verify");
			str = HttpRequestUtil.dohttpRequestByPost(url, params);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	public static void main(String[] args) {
		//String str = send("13229999501");
		String str = verify("7249","13229999501");
		System.out.println(str);
	}
	
}
