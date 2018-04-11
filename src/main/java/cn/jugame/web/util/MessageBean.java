package cn.jugame.web.util;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
public class MessageBean {
	/**
	 * 操作成功
	 */
	public static MessageBean SUCCESS = new MessageBean(true,"操作成功");
	/**
	 * 操作失败
	 */
	public static MessageBean FAIL = new MessageBean(false,"操作失败");
	
	private boolean success = true;
	
	private String msg = "操作成功";
	//扩展字段 ，可以存业务相关信息
	private JSONObject data = null;
	
	private Map<String,Object> map;
	
	public MessageBean(){}
	
	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public MessageBean(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
}
