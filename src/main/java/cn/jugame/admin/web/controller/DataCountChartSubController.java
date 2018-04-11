package cn.jugame.admin.web.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.count.service.CountAccountService;
import cn.juhaowan.count.service.CountActiveService;
import cn.juhaowan.count.service.CountMoneyIoService;
import cn.juhaowan.count.service.CountOrderService;
import cn.juhaowan.count.service.CountProductService;
import cn.juhaowan.count.service.CountUserService;
/**后台统计续2
 * @author Administrator
 */
@Controller
public class DataCountChartSubController {
	Logger logger = LoggerFactory.getLogger(DataCountChartSubController.class);
	@Autowired
	private CountAccountService countAccountService;
	@Autowired
	private CountActiveService countActiveService;
	@Autowired
	private CountMoneyIoService countMoneyIoService;
	@Autowired
	private CountOrderService countOrderService;
	@Autowired
	private CountProductService countProductService;
	@Autowired
	private CountUserService countUserService;
    /**
     * 2.1.3【日活统计】当日注册用户PV分时段
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayActive/chartUserRegPVByTime")
	public String chartUserRegPVByTime(HttpServletRequest request, Model model) {
		Map<String,Object> map = new HashMap<String,Object>(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map<String,Object>> result = countUserService.queryRegPVByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段注册用户PV" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String loginPv = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("loginPv").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("loginPv")) {
					loginPv = newMap.get("loginPv").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + loginPv + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("loginPv") + "},");
				}
			}
			yAxisCategories.append("{name: '人/次(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayActive/chartUserRegPVByTime";
	}
    /**
     * 2.1.4【日活统计】当日未注册用户PV分时段
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayActive/chartUserNoRegPVByTime")
	public String chartUserNoRegPVByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合
		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countActiveService.queryNoRegPVByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段未注册用户PV" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String noLoginPv = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("noLoginPv").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("noLoginPv")) {
					noLoginPv = newMap.get("noLoginPv").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + noLoginPv + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("noLoginPv") + "},");
				}
			}
			yAxisCategories.append("{name: '人/次(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayActive/chartUserNoRegPVByTime";
	}
    /**
     * 3.1.1账户统计 日提现分时段
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayAccount/chartAccountByTime")
	public String chartAccountByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合
		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countAccountService.queryTakeByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段用户提现金额" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String amount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Float.parseFloat(newMap.get("amount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("amount")) {
					amount = newMap.get("amount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'a',y:" + amount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'a',y:" + newMap.get("amount") + "},");
				}
			}
			yAxisCategories.append("{name: '金额(total:" + total
					+ ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayAccount/chartAccountByTime";
	}
    /**
     *  5.1.1【商品统计】上架商品用户
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayProduct/chartProductOnUserByTime")
	public String chartProductOnUserByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countProductService.queryOnSaleUserNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段上架用户数量" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String idcount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("idcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("idcount")) {
					idcount = newMap.get("idcount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + idcount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("idcount") + "},");
				}
			}
			yAxisCategories.append("{name: '人数(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayProduct/chartProductOnUserByTime";
	}
    /**
     * 5.1.2【商品统计】上架商品数量
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayProduct/chartProductOnNumByTime")
	public String chartProductOnNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countProductService.queryOnSaleNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段商品上架数量" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String stockNum = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("stockNum").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("idcount")) {
					stockNum = newMap.get("stockNum").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + stockNum + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("stockNum") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayProduct/chartProductOnNumByTime";
	}
    /**
     * 5.1.3【商品统计】下架商品数量
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayProduct/chartProductOffNumByTime")
	public String chartProductOffNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countProductService.queryOnSaleNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段商品上架数量" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String stockNum = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("stockNum").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("idcount")) {
					stockNum = newMap.get("stockNum").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + stockNum + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("stockNum") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayProduct/chartProductOffNumByTime";
	}
    /**
     * 6.1.1【订单统计】交易成功量
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayOrder/chartOrderSucByTime")
	public String chartOrderSucByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countOrderService.queryOrderSucNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段交易成功量" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String oidcount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("oidcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("oidcount")) {
					oidcount = newMap.get("oidcount").toString();
				}
				if (i == result.size() - 1) {
					xAxisCategories.append("'" + hor + "']");
					source.append("{id:'d',y:" + oidcount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("oidcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayOrder/chartOrderSucByTime";
	}
    /**
     * 6.1.2【订单统计】交易失败量
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayOrder/chartOrderFaileByTime")
	public String chartOrderFaileByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countOrderService.queryOrderFaileNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段交易失败量" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String oidcount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("oidcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("oidcount")) {
					oidcount = newMap.get("oidcount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + oidcount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("oidcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayOrder/chartOrderFaileByTime";
	}
	

    	/**
    	 * 6.1.3【订单统计】交易金额
    	 * @param request 
    	 * @param model 
    	 * @return 跳转路径
    	 */
	@RequestMapping(value = "count/chartCountDayOrder/chartOrdermoneyByTime")
	public String chartOrdermoneyByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countOrderService.queryOrderMoneyByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段交易金额" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		Float total = 0f;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Float.parseFloat(newMap.get("order_amount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("order_amount")) {
					orderAmount = newMap.get("order_amount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("order_amount") + "},");
				}
			}
			yAxisCategories.append("{name: '金额(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayOrder/chartOrdermoneyByTime";
	}
    /**
     * 4.1.1【日金额支出统计】提现人数
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoTakeMemberNumByTime")
	public String chartMoneyIoTakeMemberNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryTakeMemberNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段提现人数" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("uidcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("uidcount")) {
					orderAmount = newMap.get("uidcount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("uidcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoTakeMemberNumByTime";
	}
    /**
     * 4.1.2【日金额支出统计】提现笔数
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoTakeNumByTime")
	public String chartMoneyIotakeMemberSucNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryTakeNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段提现笔数" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("idcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("idcount")) {
					orderAmount = newMap.get("idcount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("idcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoTakeNumByTime";
	}
    /**
     * 4.1.5【日金额支出统计】提现金额
     * @param request  
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoTakeByTime")
	public String chartMoneyIotakeSucNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合
		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryTakeMoneyDayCountByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段提现金额" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("amount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("amount")) {
					orderAmount = newMap.get("amount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("amount") + "},");
				}
			}
			yAxisCategories.append("{name: '金额(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoTakeByTime";
	}
    /**
     * 4.1.6【日金额支出统计】充值人数
     * @param request 
     * @param model 
     * @return 跳转
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoRechargeMemberByTime")
	public String chartMoneyIoRechargeMemberByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryRechargeMemberNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段充值人数" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("uidcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("uidcount")) {
					orderAmount = newMap.get("uidcount").toString();
				}
				if (i == result.size() - 1) {
					xAxisCategories.append("'" + hor + "']");
					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("uidcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}

		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoRechargeMemberByTime";
	}
    /**
     * 4.1.7【日金额支出统计】充值笔数
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoRechargeNumByTime")
	public String chartMoneyIoRechargeNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合
		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryRechargeNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段充值笔数" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("idcount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("idcount")) {
					orderAmount = newMap.get("idcount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("idcount") + "},");
				}
			}
			yAxisCategories.append("{name: '数量(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoRechargeNumByTime";
	}
    /**
     * 4.1.8 【日金额支出统计】充值金额
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayMoneyIo/chartMoneyIoRechargeMoneyByTime")
	public String chartMoneyIoRechargeMoneyByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合
		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countMoneyIoService.queryRechargeMoneyCountByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段充值金额" + "'}";
		// Y轴数据
		StringBuffer yAxisCategories = new StringBuffer();
		// X轴数据，时段
		StringBuffer xAxisCategories = new StringBuffer();
		StringBuffer source = new StringBuffer();
		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		int total = 0;
		String hor = "0";
		String orderAmount = "0";
		// [{ name: '激活人数', data: [{id:'a',y:20}] },{name:'活跃人数',data:[{id:'b',y:50}]}]
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map newMap = result.get(i);
				total += Integer.parseInt(newMap.get("amount").toString());
				if (null != newMap.get("hor")) {
					hor = newMap.get("hor").toString();
				}
				if (null != newMap.get("amount")) {
					orderAmount = newMap.get("amount").toString();
				}
				if (i == result.size() - 1) {

					xAxisCategories.append("'" + hor + "']");

					source.append("{id:'d',y:" + orderAmount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'d',y:" + newMap.get("amount") + "},");
				}
			}
			yAxisCategories.append("{name: '金额(total:" + total + ")',type: 'column',data: [" + source + "]");
			data.append(yAxisCategories);
		} else {
			xAxisCategories.append("]");
			data.append("]");
			title = "{text: '" + time + "暂无数据" + "'}";
		}
		// 将设置的值放至前台输出
		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);
		return "count/chartCountDayMoneyIo/chartMoneyIoRechargeMoneyByTime";
	}

}
