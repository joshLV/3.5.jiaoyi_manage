package cn.jugame.admin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.admin.web.form.DutyForm;
import cn.jugame.service.ISysModulePermissionService;
import cn.jugame.service.ISysModuleService;
import cn.jugame.service.ISysUserinfoService;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.customer.dao.CustomerServiceDutyDao;
import cn.juhaowan.customer.service.CustomerServiceDutyService;
import cn.juhaowan.customer.service.OnlineCustomerMonitorService;
import cn.juhaowan.customer.vo.CustomerServiceDuty;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 用户职责管理
 * 
 * @author Administrator
 * 
 */
@Controller
public class CustomerDutyController {
	Logger logger = LoggerFactory.getLogger(CustomerDutyController.class);

	@Autowired
	private BackUserLogService backUserLogService;

	@Autowired
	private ISysUserinfoService sysUserinfoService;

//	@Autowired
//	private GameService gameService;
//
//	@Autowired
//	private ProductService productService;

	@Autowired
	private CustomerServiceDutyService customerServiceDutyService;

	@Autowired
	private OnlineCustomerMonitorService onlineCustomerMonitorService;

	@Autowired
	private ISysModulePermissionService modulePermissionService;

	@Autowired
	private ISysModuleService moduleService;

//	@Autowired
//	private GameProductTypeService gameProductTypeService;
//
//	@Autowired
//	private OrdersC2cService ordersC2cService;
//	
//	@Autowired
//	private ProductOnsaleModelService productOnsaleModelService;
	
	

//	/**
//	 * 列表
//	 * 
//	 * @param request
//	 * @param model
//	 * @return 跳转
//	 */
//	@RequestMapping(value = "/customerduty/customerList")
//	public String customerList(HttpServletRequest request, Model model) {
//		// 游戏列表
//		List<Game> gameList = gameService.listAllGame();
//		model.addAttribute("gameList", gameList);
//		// 商品类型
//		List<ProductType> productTypeList = productService.queryProductTypeListNoCache();
//		model.addAttribute("productTypeList", productTypeList);
//		//商品模式
//		List<ProductOnSaleModel> modelList= productOnsaleModelService.queryAll();
//		model.addAttribute("modelList", modelList);
//		return "customerduty/customerList";
//	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param model
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/customerduty/customerList_json")
	public String customerListJson(HttpServletRequest request, Model model) {
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 100);
//		String sort = RequestUtils.getParameter(request, "sort", "USER_ID");
//		String order = RequestUtils.getParameter(request, "order", "desc");
//		// 搜索条件
//		Map<String, Object> condition = new HashMap<String, Object>();
////		int roleBusType = RequestUtils.getParameterInt(request, "role_bus_type", -1);
////		int roleTrademodel = RequestUtils.getParameterInt(request, "role_trademodel", -1);
////		int roleProductType = RequestUtils.getParameterInt(request, "role_product_type", -1);
////		int roleUsertype = RequestUtils.getParameterInt(request, "role_usertype", -1);
//		int sysusertype = RequestUtils.getParameterInt(request, "sysusertype", -1);
//		String customerId = RequestUtils.getParameter(request, "customer_id", "");
//		String fullname = RequestUtils.getParameter(request, "fullname", "");
//		int isOnDuty = RequestUtils.getParameterInt(request, "isOnDuty", -1);
//		int isOnlineStatus = RequestUtils.getParameterInt(request, "isOnlineStatus", -1);
//
//		if (sysusertype >= 0) {
//			condition.put("customerType", sysusertype);
//		}
//		if (null != customerId && !("".equals(customerId))) {
//			condition.put("postNo", customerId);
//		}
//		if (null != fullname && !("".equals(fullname))) {
//			condition.put("fullname", fullname);
//		}
//		if (isOnDuty >= 0) {
//			condition.put("isOnDuty", isOnDuty);
//		}
//		if (isOnlineStatus > 0) {
//			condition.put("onlineStatus", isOnlineStatus);
//		}
//
//		PageInfo<SysUserinfo> pageInfo = customerServiceDutyService.queryCustomerPageInfo(condition, pageSize, pageNo, sort, order);
//		if (null == pageInfo || null == pageInfo.getItems()) {
//			return null;
//		}
//		int paycount = 0;
//		int unpaycount = 0;
//		int uid = 0;
//		String img ="";
//		
//		for(int i =0;i<pageInfo.getItems().size();i++){
//			try {
//				SysUserinfo f    = (SysUserinfo)pageInfo.getItems().get(i);
//				uid = f.getUserId();
//				paycount = ordersC2cService.payStatusCountByUid(String.valueOf(uid), 1);
//				unpaycount = ordersC2cService.payStatusCountByUid(String.valueOf(uid), 0);
//			} catch (Exception e) {
//				e.printStackTrace();
//				continue;
//			}
//			pageInfo.getItems().get(i).setPayCount(paycount);
//			pageInfo.getItems().get(i).setUnPayCount(unpaycount);
//			img = pageInfo.getItems().get(i).getHeadImg();
//			if(img == null){
//				pageInfo.getItems().get(i).setHeadImg("");
//			}
//		}
//
//		model.addAttribute("pageInfo", pageInfo);
		return "customerduty/customerList_json";
	}

	/**
	 * 用户信息 详情页面
	 * 
	 * @param request
	 * @return 用户信息
	 */
	@RequestMapping(value = "/customerduty/customerduty_json")
	@ResponseBody
	public DutyForm customerdutyJson(HttpServletRequest request,Model model) {
		String uids = RequestUtils.getParameter(request, "uids", "");
		DutyForm dutyform = null;
		// 选择多个
		if (uids.contains(",")) {
			String[] idArr = uids.split(",");
			StringBuffer listName = new StringBuffer("");
			StringBuffer rolelist = new StringBuffer("");
			String name = "";
			int roletype = -1;
			String typename = "";
			for (int i = 0; i < idArr.length; i++) {
				try {
					name = sysUserinfoService.findById(Integer.parseInt(idArr[i])).getFullname();
					roletype = onlineCustomerMonitorService.queryCustomerType(Integer.parseInt(idArr[i]));
					if (roletype == 0) {
						typename = "客服";
					} else if (roletype == 1) {
						typename = "物服";
					} else if (roletype == 2) {
						typename = "审核员";
					} else if (roletype == 3) {
						typename = "咨询客服";
					} else {
						typename = "--";
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				listName.append(name + "(" + typename + "),");
				rolelist.append(roletype + "-");

			}
			// 判断一批人的角色
			if (rolelist.toString().contains("0")) {
				roletype = 0;
			}
			if (rolelist.toString().contains("1")) {
				roletype = 1;
			}
			if (rolelist.toString().contains("0") && rolelist.toString().contains("1")) {
				roletype = 2;
			}

			dutyform = new DutyForm();

			int[] defual = { -1 };
			dutyform.setUids(uids);
			dutyform.setBusinessModel(defual);
			dutyform.setUserType(defual);
			dutyform.setSelectName(listName.toString());
			dutyform.setRoleType(roletype);

			dutyform.setBusinessModel2(defual);
			dutyform.setUserType2(defual);

		} else {// 选择单个
			int[] defual = { -1 };
			// 客服/物服姓名
			String perName = "";
			int roletype = -1;
			String headimg ="";
			try {
				cn.jugame.vo.SysUserinfo u = sysUserinfoService.findById(Integer.parseInt(uids));
				perName = u.getFullname();
				headimg = u.getHeadImg();
				roletype = onlineCustomerMonitorService.queryCustomerType(Integer.parseInt(uids));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			dutyform = new DutyForm();
			dutyform.setUids(uids);
			dutyform.setSelectName(perName);
			dutyform.setRoleType(roletype);
			dutyform.setHeadImg(headimg);
			model.addAttribute("headimg", headimg);
			// 业务类型
			List<CustomerServiceDuty> list1 = customerServiceDutyService.queryByCSId(CustomerServiceDutyDao.DUTYTYPE_BUSINESSTYPE, uids, 1);
			if (null != list1) {
				int[] j = new int[list1.size()];
				for (int i = 0; i < list1.size(); i++) {
					j[i] = list1.get(i).getDutySubid();
				}
				dutyform.setBusinessType(j);
			} else {
				dutyform.setBusinessType(defual);
			}
			// 【上架】售卖模式
			List<CustomerServiceDuty> list2 = customerServiceDutyService.queryByCSId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL, uids, 1);
			if (null != list2) {
				int[] j = new int[list2.size()];
				for (int i = 0; i < list2.size(); i++) {
					j[i] = list2.get(i).getDutySubid();
				}
				dutyform.setBusinessModel(j);
			} else {
				dutyform.setBusinessModel(defual);
			}
			// 【上架】用户类型
			List<CustomerServiceDuty> list4 = customerServiceDutyService.queryByCSId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE, uids, 1);
			if (null != list4) {
				int[] j = new int[list4.size()];
				for (int i = 0; i < list4.size(); i++) {
					j[i] = list4.get(i).getDutySubid();
				}
				dutyform.setUserType(j);
			} else {
				dutyform.setUserType(defual);
			}
			// 【售中】售卖模式
			List<CustomerServiceDuty> list22 = customerServiceDutyService.queryByCSId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL, uids, 2);
			if (null != list22) {
				int[] j = new int[list22.size()];
				for (int i = 0; i < list22.size(); i++) {
					j[i] = list22.get(i).getDutySubid();
				}
				dutyform.setBusinessModel2(j);
			} else {
				dutyform.setBusinessModel2(defual);
			}
			// 【售中】用户类型
			List<CustomerServiceDuty> list44 = customerServiceDutyService.queryByCSId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE, uids, 2);
			if (null != list44) {
				int[] j = new int[list44.size()];
				for (int i = 0; i < list44.size(); i++) {
					j[i] = list44.get(i).getDutySubid();
				}
				dutyform.setUserType2(j);
			} else {
				dutyform.setUserType2(defual);
			}
		}
		return dutyform;
	}

	/**
	 * 编辑
	 * 
	 * @param dutyForm
	 * @param request
	 * @return 提示信息
	 */
	@RequestMapping(value = "/customerduty/duty_edit")
	@ResponseBody
	public MessageBean dutyEdit(DutyForm dutyForm, HttpServletRequest request) {
		String uids = request.getParameter("uids");
		String pers = request.getParameter("pers");
		String persOnsale = request.getParameter("persOnsale");
		
		String imgUrl = RequestUtils.getParameter(request, "headImg", "");
		// per_17,per_18,per_19,per_8,per_9,per_10,mod_5,mod_10
		String[] perArr = pers.split(",");
		String[] perArrOnsale = persOnsale.split(",");

		// 批量更新权限
		if (uids.contains(",")) {
			String[] idArr = uids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				// 先删除改用户职责
				customerServiceDutyService.deleteDuty(Integer.parseInt(idArr[i]));
				// 1 业务模式
				if (dutyForm.getBusinessType() != null) {
					for (int j = 0; j < dutyForm.getBusinessType().length; j++) {
						if (dutyForm.getBusinessType()[j] != -1) {
							CustomerServiceDuty duty = new CustomerServiceDuty();
							duty.setCustomerServiceId(Integer.parseInt(idArr[i]));
							duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_BUSINESSTYPE);
							duty.setDutySubid(dutyForm.getBusinessType()[j]);
							customerServiceDutyService.insertDuty(duty);
						}
					}
				}
				// 【上架】 2 售卖模式
				if (dutyForm.getBusinessModel() != null) {
					for (int j = 0; j < dutyForm.getBusinessModel().length; j++) {
						if (dutyForm.getBusinessModel()[j] != -1) {
							CustomerServiceDuty duty = new CustomerServiceDuty();
							duty.setCustomerServiceId(Integer.parseInt(idArr[i]));
							duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL);
							duty.setDutySubid(dutyForm.getBusinessModel()[j]);
							duty.setBusnessType(1);// 上架
							customerServiceDutyService.insertDuty(duty);
						}
					}
				}

				// 【上架】 4 用户级别
				if (dutyForm.getUserType() != null) {
					for (int j = 0; j < dutyForm.getUserType().length; j++) {
						if (dutyForm.getUserType()[j] != -1) {
							CustomerServiceDuty duty = new CustomerServiceDuty();
							duty.setCustomerServiceId(Integer.parseInt(idArr[i]));
							duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE);
							duty.setDutySubid(dutyForm.getUserType()[j]);
							duty.setBusnessType(1);// 上架
							customerServiceDutyService.insertDuty(duty);
						}
					}
				}


				// 【售中】22 售卖模式
				if (dutyForm.getBusinessModel2() != null) {
					for (int j = 0; j < dutyForm.getBusinessModel2().length; j++) {
						if (dutyForm.getBusinessModel2()[j] != -1) {
							CustomerServiceDuty duty = new CustomerServiceDuty();
							duty.setCustomerServiceId(Integer.parseInt(idArr[i]));
							duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL);
							duty.setDutySubid(dutyForm.getBusinessModel2()[j]);
							duty.setBusnessType(2);// 售中
							customerServiceDutyService.insertDuty(duty);
						}
					}
				}

				// 【售中】44 用户级别
				if (dutyForm.getUserType2() != null) {
					for (int j = 0; j < dutyForm.getUserType2().length; j++) {
						if (dutyForm.getUserType2()[j] != -1) {
							CustomerServiceDuty duty = new CustomerServiceDuty();
							duty.setCustomerServiceId(Integer.parseInt(idArr[i]));
							duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE);
							duty.setDutySubid(dutyForm.getUserType2()[j]);
							duty.setBusnessType(2);// 售中
							customerServiceDutyService.insertDuty(duty);
						}
					}
				}


			}
		} else { // 只更新一个
				 // 先删除改用户职责
			customerServiceDutyService.deleteDuty(Integer.parseInt(uids));
			// 1 业务模式
			if (dutyForm.getBusinessType() != null) {
				for (int j = 0; j < dutyForm.getBusinessType().length; j++) {
					if (dutyForm.getBusinessType()[j] != -1) {
						CustomerServiceDuty duty = new CustomerServiceDuty();
						duty.setCustomerServiceId(Integer.parseInt(uids));
						duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_BUSINESSTYPE);
						duty.setDutySubid(dutyForm.getBusinessType()[j]);
						customerServiceDutyService.insertDuty(duty);
					}
				}
			}
			// 【上架】2 交易模式
			if (dutyForm.getBusinessModel() != null) {
				for (int j = 0; j < dutyForm.getBusinessModel().length; j++) {
					if (dutyForm.getBusinessModel()[j] != -1) {
						CustomerServiceDuty duty = new CustomerServiceDuty();
						duty.setCustomerServiceId(Integer.parseInt(uids));
						duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL);
						duty.setDutySubid(dutyForm.getBusinessModel()[j]);
						duty.setBusnessType(1);// 上架
						customerServiceDutyService.insertDuty(duty);
					}
				}
			}

			// 【上架】4 用户级别
			if (dutyForm.getUserType() != null) {
				for (int j = 0; j < dutyForm.getUserType().length; j++) {
					if (dutyForm.getUserType()[j] != -1) {
						CustomerServiceDuty duty = new CustomerServiceDuty();
						duty.setCustomerServiceId(Integer.parseInt(uids));
						duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE);
						duty.setDutySubid(dutyForm.getUserType()[j]);
						duty.setBusnessType(1);// 上架
						customerServiceDutyService.insertDuty(duty);
					}
				}
			}


			// 【售中】2 交易模式
			if (dutyForm.getBusinessModel2() != null) {
				for (int j = 0; j < dutyForm.getBusinessModel2().length; j++) {
					if (dutyForm.getBusinessModel2()[j] != -1) {
						CustomerServiceDuty duty = new CustomerServiceDuty();
						duty.setCustomerServiceId(Integer.parseInt(uids));
						duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL);
						duty.setDutySubid(dutyForm.getBusinessModel2()[j]);
						duty.setBusnessType(2);// 售中
						customerServiceDutyService.insertDuty(duty);
					}
				}
			}

			// 【售中】4 用户级别
			if (dutyForm.getUserType2() != null) {
				for (int j = 0; j < dutyForm.getUserType2().length; j++) {
					if (dutyForm.getUserType2()[j] != -1) {
						CustomerServiceDuty duty = new CustomerServiceDuty();
						duty.setCustomerServiceId(Integer.parseInt(uids));
						duty.setDutyId(CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE);
						duty.setDutySubid(dutyForm.getUserType2()[j]);
						duty.setBusnessType(2);// 售中
						customerServiceDutyService.insertDuty(duty);
					}
				}
			}
			
			//更新头像
			if(StringUtils.isNotBlank(imgUrl)){
				sysUserinfoService.updateHeadImg(uids, imgUrl);
			}

		}

		return new MessageBean(true, "操作成功");
	}

//	/**
//	 * 获得所有商品状态类型
//	 * 
//	 * @param request 请求
//	 * @param model 模型驱动
//	 * @return 商品类型状态类型列表JSON
//	 */
//	@RequestMapping(value = "/customerduty/allproducttypeList_json")
//	@ResponseBody
//	public JSONObject allCategoryListJson(HttpServletRequest request, Model model) {
//		JSONObject data = new JSONObject();
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//
//		List<ProductType> productTypeList = productService.queryProductTypeListNoCache();
//		if (null == productTypeList) {
//			data.put("total", 0);
//			return null;
//		}
//		data.put("total", productTypeList.size());
//
//		for (ProductType pt : productTypeList) {
//			Map<String, Object> categoryMap = new HashMap<String, Object>();
//			categoryMap.put("id", pt.getId());
//			categoryMap.put("name", pt.getName());
//			showList.add(categoryMap);
//		}
//
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//
//		return JSONObject.fromObject(data);
//	}

	/**
	 * 游戏商品类型树结构
	 * 
	 * @param id
	 * @param model
	 * @return 跳转路径
	 */
//	@RequestMapping(value = "/customerduty/gpType_json")
//	public String permission(String id, Model model) {
//		if (!StringUtils.isBlank(id)) {
//			return "";
//		}
//		List<SysModule> parentModuleList = moduleService.findParentModule();
//		List<SysModule> moduleList = moduleService.findAll();
//		List<SysModulePermission> modulePermissions = modulePermissionService.findAll();
//
//		model.addAttribute("parentModuleList", parentModuleList);
//		model.addAttribute("moduleList", moduleList);
//		model.addAttribute("modulePermissions", modulePermissions);
//
//		// 游戏列表
//		List<Game> gameList = gameService.listAllGame();
//		model.addAttribute("gameList", gameList);
//		// 商品类型
//		List<GameProductType> productTypeList = gameProductTypeService.queryAllGameProductType();
//		if (null != productTypeList) {
//			for (int i = 0; i < productTypeList.size(); i++) {
//				ProductType pt = productService.findProductType(productTypeList.get(i).getProductTypeId());
//				if (null == pt) {
//					continue;
//				}
//				productTypeList.get(i).setProductTypeName(pt.getName());
//			}
//		}
//		model.addAttribute("gameproductTypeList", productTypeList);
//
//		return "customerduty/gpType_json";
//	}

}
