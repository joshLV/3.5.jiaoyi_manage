package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import cn.jugame.util.PageInfo;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.count.service.CountAccountService;
import cn.juhaowan.count.service.CountActiveService;
import cn.juhaowan.count.service.CountMoneyIoService;
import cn.juhaowan.count.service.CountOrderService;
import cn.juhaowan.count.service.CountProductService;
import cn.juhaowan.count.service.CountUserService;
import cn.juhaowan.count.vo.CountDayAccount;
import cn.juhaowan.count.vo.CountDayActive;
import cn.juhaowan.count.vo.CountDayMoneyIo;
import cn.juhaowan.count.vo.CountDayOrder;
import cn.juhaowan.count.vo.CountDayProduct;
import cn.juhaowan.count.vo.CountDayUser;

/**
 * 后台统计1
 * @author Administrator
 *
 */
@Controller
public class DataCountChartController {
	Logger logger = LoggerFactory.getLogger(DataCountChartController.class);

	//@Autowired
	//private CountService countService;
	
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
	 * 1.1用户统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/chartCountDayUser")
	public String chartCountDayUser(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map<String,Object> m = new HashMap<String,Object>();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayUser> pageInfo = null;
		pageInfo = countUserService.queryDayUserForPage(m, pageNo, pageSize, sort, order);

		if(null == pageInfo) {
			return "count/chartCountDayUser";
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String newdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map<String,Object> newMap = new HashMap<String,Object>();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
		
			newMap.put("count_date", newdate);
			newMap.put("reg_num", pageInfo.getItems().get(i).getRegNum());
			newMap.put("real_num", pageInfo.getItems().get(i).getRealNum());
			newMap.put("total_num", pageInfo.getItems().get(i).getTotalNum());
			//newMap.put("reg_uv", pageInfo.getItems().get(i).getRegUv());
			//newMap.put("reg_pv", pageInfo.getItems().get(i).getRegPv());
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "用户统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 当日用户注册数量
		StringBuffer regNum = new StringBuffer();
		// 当日用户实名数量
		StringBuffer realNum = new StringBuffer();
		// 当日注册用户访问UV
		//StringBuffer reg_uv = new StringBuffer();
		// 当日注册用户访问PV
		//StringBuffer reg_pv = new StringBuffer();
		//总用户数量
		StringBuffer totalNum = new StringBuffer();

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}] },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		regNum.append("{name: '当日用户注册数量',type: 'spline',data: [");
		realNum.append("{name: '当日用户实名数量',type: 'spline',data: [");
		totalNum.append("{name: '注册用户总数',type: 'spline',data: [");
		//reg_uv.append("{name: '当日注册用户访问UV',type: 'spline',data: [");
		//reg_pv.append("{name:'当日注册用户访问PV',type:'spline',data:[");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					regNum.append("{id:'a',y:" + newMap.get("reg_num") + "}]}");
					realNum.append("{id:'b',y:" + newMap.get("real_num") + "}]}");
					totalNum.append("{id:'c',y:" + newMap.get("total_num") + "}]}");
					//reg_uv.append("{id:'c',y:" + newMap.get("reg_uv")+ "}]}");
					//reg_pv.append("{id:'d',y:"+ newMap.get("reg_pv") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					regNum.append("{id:'a',y:" + newMap.get("reg_num") + "},");
					realNum.append("{id:'b',y:" + newMap.get("real_num") + "},");
					totalNum.append("{id:'c',y:" + newMap.get("total_num") + "},");
					//reg_uv.append("{id:'c',y:" + newMap.get("reg_uv")+ "},");
					//reg_pv.append("{id:'d',y:"+ newMap.get("reg_pv") + "},");
				}
			}
		}
		//data.append(reg_num + "," + real_num + "," + reg_uv + "," + reg_pv+ "]");
		data.append(regNum + "," + realNum + "," + totalNum + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayUser";
	}
	
	
	
	/**
	 * 2.1日活统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转
	 */
	@RequestMapping(value = "/count/chartCountDayActive")
	public String chartCountDayActive(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayActive> pageInfo = null;
		pageInfo = countActiveService.queryDayActiveForPage(m, pageNo, pageSize, sort, order);

		List<Map> result = new ArrayList<Map>();
		String newdate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map newMap = new HashMap();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
		
			newMap.put("count_date", newdate);
			newMap.put("reg_uv", pageInfo.getItems().get(i).getRegUv());
			newMap.put("no_reg_uv", pageInfo.getItems().get(i).getNoRegUv());
			newMap.put("reg_pv", pageInfo.getItems().get(i).getRegPv());
			newMap.put("no_reg_pv", pageInfo.getItems().get(i).getNoRegPv());
			newMap.put("pv_uv_rate", pageInfo.getItems().get(i).getPvUvRate());
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "日活统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 当日注册用户UV
		StringBuffer regUv = new StringBuffer();
		// 当日非注册用户UV
		StringBuffer noRegUv = new StringBuffer();
		// 当日注册用户PV
		StringBuffer regPv = new StringBuffer();
		// 当日非注册用户PV
		StringBuffer noRegPv = new StringBuffer();
		// 网站访问次数
		StringBuffer pvUvRate = new StringBuffer();
		

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}] },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		regUv.append("{name: '当日注册用户UV',type: 'spline',data: [");
		noRegUv.append("{name: '当日非注册用户UV',type: 'spline',data: [");
		regPv.append("{name: '当日注册用户PV',type: 'spline',data: [");
		noRegPv.append("{name:'当日非注册用户PV',type:'spline',data:[");
		pvUvRate.append("{name:'网站访问次数',type:'spline',data:[");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					regUv.append("{id:'a',y:" + newMap.get("reg_uv") + "}]}");
					noRegUv.append("{id:'b',y:" + newMap.get("no_reg_uv") + "}]}");
					regPv.append("{id:'c',y:" + newMap.get("reg_pv") + "}]}");
					noRegPv.append("{id:'d',y:" + newMap.get("no_reg_pv") + "}]}");
					pvUvRate.append("{id:'e',y:" + newMap.get("pv_uv_rate") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					regUv.append("{id:'a',y:" + newMap.get("reg_uv") + "},");
					noRegUv.append("{id:'b',y:" + newMap.get("no_reg_uv") + "},");
					regPv.append("{id:'c',y:" + newMap.get("reg_pv") + "},");
					noRegPv.append("{id:'d',y:" + newMap.get("no_reg_pv") + "},");
					pvUvRate.append("{id:'e',y:" + newMap.get("pv_uv_rate") + "},");
				}
			}
		}
		data.append(regUv + "," + noRegUv + "," + regPv + "," + noRegPv + "," + pvUvRate + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayActive";
	}
	

	/**
	 * 3.1用户账号统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/chartCountDayAccount")
	public String chartCountDayAccount(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayAccount> pageInfo = null;
		pageInfo = countAccountService.queryDayAccountForPage(m, pageNo, pageSize,sort, order);

		List<Map> result = new ArrayList<Map>();
		String newdate = null;
		double takeMoneySum = 0;
		double rechargeSum = 0;
		double blance1Sum = 0;
		double deposit = 0;
		double blance2Sum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map newMap = new HashMap();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
			if (0 != pageInfo.getItems().get(i).getTakeMoneySum()) {
				takeMoneySum = pageInfo.getItems().get(i).getTakeMoneySum();
			}
			if (0 != pageInfo.getItems().get(i).getRechargeSum()) {
				rechargeSum = pageInfo.getItems().get(i).getRechargeSum();
			}
			if (0 != pageInfo.getItems().get(i).getBalance1Sum()) {
				blance1Sum = pageInfo.getItems().get(i).getBalance1Sum();
			}
			if (0 != pageInfo.getItems().get(i).getDepositAmountSum()) {
				deposit = pageInfo.getItems().get(i).getDepositAmountSum();
			}
			if (0 != pageInfo.getItems().get(i).getBalance2Sum()) {
				blance2Sum = pageInfo.getItems().get(i).getBalance2Sum();
			}
			newMap.put("count_date", newdate);
			newMap.put("take_money_sum", takeMoneySum);
			newMap.put("recharge_sum", rechargeSum);
			newMap.put("balance1_sum", blance1Sum);
			newMap.put("deposit_amount_sum", deposit);
			newMap.put("balance2_sum", blance2Sum);
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "用户账号统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 提现金额
		StringBuffer takeM = new StringBuffer();
		// 充值金额
		StringBuffer recharge = new StringBuffer();
		// 账户现金
		StringBuffer cash = new StringBuffer();
		// 保证金额
		StringBuffer depositShow = new StringBuffer();
		// 不可提现金额
		StringBuffer untake = new StringBuffer();

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}]
		// },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		takeM.append("{name: '提现金额',type: 'spline',data: [");
		recharge.append("{name: '充值金额',type: 'spline',data: [");
		cash.append("{name: '账户现金',type: 'spline',data: [");
		depositShow.append("{name:'保证金额',type:'spline',data:[");
		untake.append("{name:'不可提现金额',type:'spline',data:[");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					takeM.append("{id:'a',y:" + newMap.get("take_money_sum") + "}]}");
					recharge.append("{id:'b',y:" + newMap.get("recharge_sum") + "}]}");
					cash.append("{id:'b',y:" + newMap.get("balance1_sum") + "}]}");
					depositShow.append("{id:'b',y:" + newMap.get("deposit_amount_sum") + "}]}");
					untake.append("{id:'b',y:" + newMap.get("balance2_sum") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					takeM.append("{id:'a',y:" + newMap.get("take_money_sum") + "},");
					recharge.append("{id:'b',y:" + newMap.get("recharge_sum") + "},");
					cash.append("{id:'b',y:" + newMap.get("balance1_sum") + "},");
					depositShow.append("{id:'b',y:" + newMap.get("deposit_amount_sum") + "},");
					untake.append("{id:'b',y:" + newMap.get("balance2_sum") + "},");
				}
			}
		}
		data.append(takeM + "," + cash + "," + recharge + "," + depositShow + "," + untake + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayAccount";
	}
	
	

	/**
	 * 4.1日金额支出统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/chartCountDayMoneyIO")
	public String chartCountDayMoneyIO(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayMoneyIo> pageInfo = null;
		pageInfo = countMoneyIoService.queryDayMoneyForPage(m, pageNo, pageSize, sort, order);

		List<Map> result = new ArrayList<Map>();
		String newdate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map newMap = new HashMap();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
		
			newMap.put("count_date", newdate);
			newMap.put("take_member_num", pageInfo.getItems().get(i).getTakeMemberNum());
			newMap.put("take_num", pageInfo.getItems().get(i).getTakeNum());
			newMap.put("take_member_suc_num", pageInfo.getItems().get(i).getTakeMemberSucNum());
			newMap.put("take_suc_num", pageInfo.getItems().get(i).getTakeSucNum());
			newMap.put("take_money_day_count", pageInfo.getItems().get(i).getTakeMoneyDayCount());
			newMap.put("recharge_member_num", pageInfo.getItems().get(i).getRechargeMemberNum());
			newMap.put("recharge_num", pageInfo.getItems().get(i).getRechargeNum());
			newMap.put("recharge_money_count", pageInfo.getItems().get(i).getRechargeMoneyCount());
			newMap.put("account_amount", pageInfo.getItems().get(i).getAccountAmount());
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "日金额支出统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 提现人数
		StringBuffer takeMemberNum = new StringBuffer();
		// 提现笔数 
		StringBuffer takeNum = new StringBuffer();
		// 提现成功人数 
		StringBuffer takeMemberSucNum = new StringBuffer();
		// 提现成功笔数 
		StringBuffer takeSucNum = new StringBuffer();
		// 提现金额
		StringBuffer takeMoneyDayCount = new StringBuffer();
		// 充值人数
		StringBuffer rechargeMemberNum = new StringBuffer();
		// 充值笔数
		StringBuffer rechargeNum = new StringBuffer();
		// 充值金额
		StringBuffer rechargeMoneyCount = new StringBuffer();
		// 账户余额
		StringBuffer accountAmount = new StringBuffer();
		

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}] },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		takeMemberNum.append("{name: '提现人数',type: 'spline',data: [");
		takeNum.append("{name: '提现笔数',type: 'spline',data: [");
		takeMemberSucNum.append("{name: '提现成功人数 ',type: 'spline',data: [");
		takeSucNum.append("{name:'提现成功笔数',type:'spline',data:[");
		takeMoneyDayCount.append("{name:'提现金额',type:'spline',data:[");
		rechargeMemberNum.append("{name:'充值人数',type:'spline',data:[");
		rechargeNum.append("{name:'充值笔数',type:'spline',data:[");
		rechargeMoneyCount.append("{name:'充值金额',type:'spline',data:[");
		accountAmount.append("{name:'账户余额',type:'spline',data:[");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					takeMemberNum.append("{id:'a',y:" + newMap.get("take_member_num") + "}]}");
					takeNum.append("{id:'b',y:" + newMap.get("take_num") + "}]}");
					takeMemberSucNum.append("{id:'c',y:" + newMap.get("take_member_suc_num") + "}]}");
					takeSucNum.append("{id:'d',y:" + newMap.get("take_suc_num") + "}]}");
					takeMoneyDayCount.append("{id:'e',y:" + newMap.get("take_money_day_count") + "}]}");
					rechargeMemberNum.append("{id:'f',y:" + newMap.get("recharge_member_num") + "}]}");
					rechargeNum.append("{id:'g',y:" + newMap.get("recharge_num") + "}]}");
					rechargeMoneyCount.append("{id:'h',y:" + newMap.get("recharge_money_count") + "}]}");
					accountAmount.append("{id:'i',y:" + newMap.get("account_amount") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					takeMemberNum.append("{id:'a',y:" + newMap.get("take_member_num") + "},");
					takeNum.append("{id:'b',y:" + newMap.get("take_num") + "},");
					takeMemberSucNum.append("{id:'c',y:" + newMap.get("take_member_suc_num") + "},");
					takeSucNum.append("{id:'d',y:" + newMap.get("take_suc_num") + "},");
					takeMoneyDayCount.append("{id:'e',y:" + newMap.get("take_money_day_count") + "},");
					rechargeMemberNum.append("{id:'f',y:" + newMap.get("recharge_member_num") + "},");
					rechargeNum.append("{id:'g',y:" + newMap.get("recharge_num") + "},");
					rechargeMoneyCount.append("{id:'h',y:" + newMap.get("recharge_money_count") + "},");
					accountAmount.append("{id:'i',y:" + newMap.get("account_amount") + "},");
				}
			}
		}
		data.append(takeMemberNum + "," + takeNum + "," + takeMemberSucNum + "," 
		+ takeSucNum + "," + takeMoneyDayCount + "," + rechargeMemberNum + "," + rechargeNum + "," 
		+ rechargeMoneyCount + "," + accountAmount + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayMoneyIO";
	}

	
	/**
	 * 5.1商品统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/chartCountDayProduct")
	public String chartCountDayProduct(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayProduct> pageInfo = null;
		pageInfo = countProductService.queryDayProductForPage(m, pageNo, pageSize, sort, order);

		List<Map> result = new ArrayList<Map>();
		String newdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map newMap = new HashMap();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
		
			newMap.put("count_date", newdate);
			newMap.put("on_member_num", pageInfo.getItems().get(i).getOnMemberNum());
			newMap.put("on_product_num", pageInfo.getItems().get(i).getOnProductNum());
			newMap.put("off_product_num", pageInfo.getItems().get(i).getOffProductNum());
			newMap.put("store_total", pageInfo.getItems().get(i).getStoreTotal());
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "商品统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 当日有上架商品的用户数量
		StringBuffer onMemberNum = new StringBuffer();
		// 当日上架商品的数量
		StringBuffer onProductNum = new StringBuffer();
		// 当日下架商品的数量
		StringBuffer offProductNum = new StringBuffer();
		// 当天为止库存
		StringBuffer storeTotal = new StringBuffer();
		

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}] },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		onMemberNum.append("{name: '上架商品用户',type: 'spline',data: [");
		onProductNum.append("{name: '上架商品量',type: 'spline',data: [");
		offProductNum.append("{name: '下架商品量',type: 'spline',data: [");
		storeTotal.append("{name:'商品剩余量',type:'spline',data:[");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					onMemberNum.append("{id:'a',y:" + newMap.get("on_member_num") + "}]}");
					onProductNum.append("{id:'b',y:" + newMap.get("on_product_num") + "}]}");
					offProductNum.append("{id:'c',y:" + newMap.get("off_product_num") + "}]}");
					storeTotal.append("{id:'d',y:" + newMap.get("store_total") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					onMemberNum.append("{id:'a',y:" + newMap.get("on_member_num") + "},");
					onProductNum.append("{id:'b',y:" + newMap.get("on_product_num") + "},");
					offProductNum.append("{id:'c',y:" + newMap.get("off_product_num") + "},");
					storeTotal.append("{id:'d',y:" + newMap.get("store_total") + "},");
				}
			}
		}
		data.append(onMemberNum + "," + onProductNum + "," + offProductNum + "," + storeTotal + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayProduct";
	}
	
	
	/**
	 * 6.1订单统计图表页面
	 * 
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/count/chartCountDayOrder")
	public String chartCountDayOrder(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 40); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "count_day"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");
		// 搜索条件
		Map m = new HashMap();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		if (null != btime && !"".equals(btime)) {
			m.put("beginTime", btime);
			model.addAttribute("btime", btime);
		}
		if (null != etime && !"".equals(etime)) {
			m.put("endTime", etime + " 23:59:59");
			model.addAttribute("etime", etime);
		}
		PageInfo<CountDayOrder> pageInfo = null;
		pageInfo = countOrderService.queryDayOrderForPage(m, pageNo, pageSize, sort, order);

		List<Map> result = new ArrayList<Map>();
		String newdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("result", result);
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			Map newMap = new HashMap();

			if (null != pageInfo.getItems().get(i).getCountDay()) {
				newdate = sdf.format(pageInfo.getItems().get(i).getCountDay());
			}
		
			newMap.put("count_date", newdate);
			newMap.put("order_suc_num", pageInfo.getItems().get(i).getOrderSucNum());
			newMap.put("order_faile_num", pageInfo.getItems().get(i).getOrderFaileNum());
			newMap.put("order_day_money_num", pageInfo.getItems().get(i).getOrderDayMoneyNum());
			result.add(newMap);
		}

		// 定义标题
		String title = "{text: '" + "交易统计" + "'}";
		// x轴数据
		StringBuffer xAxisCategories = new StringBuffer();
		// 当日成功订单数量
		StringBuffer orderSucNum = new StringBuffer();
		// 当日失败订单数量
		StringBuffer orderFaileNum = new StringBuffer();
		// 当日成交金额
		StringBuffer orderDayMoneyNum = new StringBuffer();
	
		

		StringBuffer data = new StringBuffer();
		xAxisCategories.append("[");
		data.append("[");
		// [{ name: '提现金额', data: [{id:'a',y:20.00}] },{name:'充值金额',data:[{id:'b',y:980.00}]}]
		orderSucNum.append("{name: '交易成功量',type: 'spline',data: [");
		orderFaileNum.append("{name: '交易失败',type: 'spline',data: [");
		orderDayMoneyNum.append("{name: '交易金额',type: 'spline',data: [");
		if (result != null && result.size() > 0) {
			for (int i = result.size() - 1; i >= 0; i--) {
				Map newMap = result.get(i);
				if (i == 0) {
					xAxisCategories.append("'" + newMap.get("count_date") + "']");
					orderSucNum.append("{id:'a',y:" + newMap.get("order_suc_num") + "}]}");
					orderFaileNum.append("{id:'b',y:" + newMap.get("order_faile_num") + "}]}");
					orderDayMoneyNum.append("{id:'c',y:" + newMap.get("order_day_money_num") + "}]}");
				} else {

					xAxisCategories.append("'" + newMap.get("count_date") + "',");
					orderSucNum.append("{id:'a',y:" + newMap.get("order_suc_num") + "},");
					orderFaileNum.append("{id:'b',y:" + newMap.get("order_faile_num") + "},");
					orderDayMoneyNum.append("{id:'c',y:" + newMap.get("order_day_money_num") + "},");
				}
			}
		}
		data.append(orderSucNum + "," + orderFaileNum + "," + orderDayMoneyNum + "]");

		model.addAttribute("title", title);
		model.addAttribute("xAxisCategories", xAxisCategories);
		model.addAttribute("data", data);

		return "count/chartCountDayOrder";
	}
	
	
	
	
    /**
     * 1.1.1当日用户注册量分时段
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayUser/chartUserRegNumByTime")
	public String chartUserRegNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countUserService.queryRegNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段用户注册数量" + "'}";
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

					source.append("{id:'a',y:" + idcount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'a',y:" + newMap.get("idcount") + "},");
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
		return "count/chartCountDayUser/chartUserRegNumByTime";
	}
	
	

    /**
     * 1.1.2【用户统计】当日用户实名分时段
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayUser/chartUserRealNumByTime")
	public String chartUserRealNumByTime(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countUserService.queryRegNumByTime(map);
		// 定义标题
		String title = "{text: '" + time + "各时段用户实名数量" + "'}";
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

					source.append("{id:'b',y:" + idcount + "}]}");
				} else {
					xAxisCategories.append("'" + newMap.get("hor") + "',");
					source.append("{id:'b',y:" + newMap.get("idcount") + "},");
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
		return "count/chartCountDayUser/chartUserRealNumByTime";
	}
	
	
    /**
     * 1.1.4【用户统计】注册用户PV 分时
     * @param request 
     * @param model 
     * @return 跳转路径
     */
	@RequestMapping(value = "count/chartCountDayUser/chartUserRegPVByTime")
	public String chartUserRegPVByTime1(HttpServletRequest request, Model model) {
		String btime = RequestUtils.getParameter(request, "btime", "");
		Map map = new HashMap(); // 存放参数的map集合

		String time = RequestUtils.getParameter(request, "time", "");
		if (time != null && !("").equals(time)) {
			model.addAttribute("time", time);
			map.put("time", time);
		}
		List<Map> result = countUserService.queryRegPVByTime(map);
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
		return "count/chartCountDayUser/chartUserRegPVByTime";
	}
	
}
