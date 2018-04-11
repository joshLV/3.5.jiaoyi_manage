package cn.jugame.admin.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.admin.notify.INotify;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.GlobalsKeys;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;

/**
 * 后台菜单数量提示
 * 
 */
@Controller
public class NotifyController {
	Logger logger = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	private OnlineCustomerMonitorService onlineCustomerMonitorService;
	@Autowired
	private INotify iNotify;

	/**
	 * 
	 * @param kefuid
	 * @return
	 */
	@RequestMapping(value = "/api/notify")
	@ResponseBody
	public JSONObject notify(HttpServletRequest request) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (userInfo == null) {
			return null;
		}
		int userType = onlineCustomerMonitorService.queryCustomerType(userInfo.getUserId());
		JSONObject json = new JSONObject();
		List<Map<String, Integer>> list = iNotify.getNotifyCount(userInfo.getUserId(), userType);
		if (list != null) {
			json.put("notify", list);
		}
		return json;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// ApiController api = new ApiController();
		// String sign = MD5.encode("{}" + "12345678900").substring(0,8);
		// JSONObject j = api.api("test", "{}", sign, "1000");
		// System.out.println(j);
		// if (j.getInt("code") == 0){
		// System.out.println("操作成功");
		// }
		System.out.println(System.currentTimeMillis());

		String str = "中国大中的";
		byte[] b;
		try {
			String tt = URLEncoder.encode(str, "UTF-8");
			System.out.println(tt);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String st =

	}
}
