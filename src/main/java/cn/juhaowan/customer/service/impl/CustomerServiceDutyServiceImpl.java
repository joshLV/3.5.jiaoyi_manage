package cn.juhaowan.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.record.formula.functions.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.juhaowan.customer.dao.CustomerServiceDutyDao;
import cn.juhaowan.customer.service.CustomerServiceDutyService;
import cn.juhaowan.customer.vo.CustomerServiceDuty;
import cn.juhaowan.customer.vo.DutyListForm;
import cn.juhaowan.customer.vo.SysUserinfo;


@Service("CustomerServiceDutyService")
public class CustomerServiceDutyServiceImpl implements CustomerServiceDutyService {
	private static Logger LOG = LoggerFactory.getLogger(CustomerServiceDutyService.class);
	@Autowired
	private CustomerServiceDutyDao customerServiceDutyDao;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
//	@Autowired
//	private ProductService productService;
//	
//	@Autowired
//	private OrdersService ordersService;
//	
//	@Autowired
//	private MemberService memberService;
//	
//	@Autowired
//	private OrdersC2cService ordersC2cService;
//	
//	@Autowired
//	private ProductVerifyFailReasonDao productVerifyFailReasonDao;
//	
//	@Autowired
//	private GameProductTypeService gameProductTypeService;
	

	
	public CustomerServiceDutyServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int insertDuty(CustomerServiceDuty duty) {
		int i = customerServiceDutyDao.insert(duty);
		return i;
	}

	@Override
	public int deleteDuty(int uid) {
        int i = customerServiceDutyDao.delete(uid);
		return i;
	}

	@Override
	public List<CustomerServiceDuty> queryByCSId(String SCId) {
	    List<CustomerServiceDuty> list = null;
		list = customerServiceDutyDao.queryByCSId(SCId);
		return list;
	}

	
	@Override
	public List<CustomerServiceDuty> queryByCSId(int dutyId, String SCId,int busType) {
	    List<CustomerServiceDuty> list = null;
		list = customerServiceDutyDao.queryByCSId(dutyId, SCId,busType);
		return list;
	}

	
	@Override
	public List<CustomerServiceDuty> queryByCSId(int dutyId, int subdutyId,String SCId) {
		 List<CustomerServiceDuty> list = null;
		 list = customerServiceDutyDao.queryByCSId(dutyId, subdutyId, SCId);
		 return list;
	}

	

	
	
//	
//    //商品审核失败原因  =====开始
//	@Override
//	public ProductVerifyFailReason findById(int id) {
//		return productVerifyFailReasonDao.findById(id);
//	}
//
//	@Override
//	public List<ProductVerifyFailReason> listAll() {
//		return productVerifyFailReasonDao.findByProperty();
//	}
//
//	@Override
//	public int insertReson(ProductVerifyFailReason reson) {
//		return productVerifyFailReasonDao.insertReson(reson);
//	}
//
//	@Override
//	public int updateReson(ProductVerifyFailReason reson) {
//		return productVerifyFailReasonDao.updateReson(reson);
//	}
//
//	@Override
//	public int deleteReson(int resonId) {
//		return productVerifyFailReasonDao.deleteReson(resonId);
//	}
//	@Override
//	public PageInfo queryResonForPage(Map<String, Object> conditions,int pageNo, int pageSize, String sort, String order) {
//		return productVerifyFailReasonDao.queryResonForPage(conditions, pageNo, pageSize, sort, order);
//	}
//    //商品审核失败原因  =====结束
	
    //客服人员权限列表
	public List<DutyListForm> queryUserDuty(String uid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT m.* FROM (SELECT oo.* ,bb.FULLNAME ,bb.custom_service_id customecode ,bb.LOGINID login,bb.USER_ID sysuid,bb.is_on_duty onduty,bb.online_status dutystatus ,bb.`is_customer` customer,bb.`is_object_customer` objectcustomer FROM "
				+ "( SELECT aa.duty_id,GROUP_CONCAT(aa.duty_subid) subduty, aa.customer_service_id uid FROM customer_service_duty aa,sys_userinfo pp "
				+ "WHERE customer_service_id = ? AND  aa.customer_service_id= pp.USER_ID AND duty_id IN (SELECT DISTINCT(duty_id) FROM customer_service_duty where customer_service_id = ?) GROUP BY aa.duty_id)oo "
				+ "RIGHT JOIN sys_userinfo  bb ON (bb.USER_ID=oo.uid ) ) m WHERE m.sysuid = ?");

		List<DutyListForm> mapList = new ArrayList<DutyListForm>();
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString(), uid, uid, uid);
		DutyListForm dutyform = new DutyListForm();
		while (rs.next()) {
			if (rs.getInt("duty_id") == (CustomerServiceDutyDao.DUTYTYPE_BUSINESSTYPE)) {
				dutyform.setBusinessType(rs.getString("subduty"));
			}
			if (rs.getInt("duty_id") == (CustomerServiceDutyDao.DUTYTYPE_TRADEMODEL)) {
				dutyform.setBusinessModel(rs.getString("subduty"));
			}
//			if (rs.getInt("duty_id") == (CustomerServiceDutyDao.DUTYTYPE_PRODUCTTYPE)) {
//				dutyform.setProductTypeids(rs.getString("subduty"));
//			}
			if (rs.getInt("duty_id") == (CustomerServiceDutyDao.DUTYTYPE_MEMBERTYPE)) {
				dutyform.setUserType(rs.getString("subduty"));
			}
//			if (rs.getInt("duty_id") == (CustomerServiceDutyDao.DUTYTYPE_GAME)) {
//				dutyform.setGameids(rs.getString("subduty"));
//			}
			dutyform.setUids(rs.getString("sysuid"));
			dutyform.setFullName(rs.getString("FULLNAME"));
			dutyform.setLoginName(rs.getString("login"));
			dutyform.setCustomerServiceId(rs.getString("customecode"));
			dutyform.setOnDuty(rs.getInt("onduty"));
			dutyform.setOnlineDuty(rs.getInt("dutystatus"));
			dutyform.setIsCustomer(rs.getInt("customer"));
			dutyform.setIsObejectCustomer(rs.getInt("objectcustomer"));
		}
		mapList.add(dutyform);
		return mapList;
	}

	@Override
	public List<DutyListForm> queryUserListDuty(List<DutyListForm> listmap) {
		List<DutyListForm>  list = new ArrayList<DutyListForm>();
		for(int i = 0; i < listmap.size();i++){
			List<DutyListForm> mapList = new ArrayList<DutyListForm>();
			mapList = queryUserDuty((String)listmap.get(i).getUids());
			list.addAll(mapList);
		}
		return list;
	}
	
//	//派单=================================== 
//	@Override
//	public int assignProductToCS(String productId) {
//		Product p = productService.queryByProductId(productId);
//		return assignProductToCS(p);
//		
//	}
//	@Override
//	public int assignOrderToCs(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//		return assignOrderToCs(o);
//	}

//	@Override
//	public int assignOrderToOs(String orderId) {
//		Orders o = ordersService.queryOrderById(orderId);
//		return assignOrderToOs(o);
//	}

//	@Override
//	public int assignProductToCS(Product p) {
//		int result = -1 ;
//		if(null == p){
//			LOG.error("商品派单失败-1,商品不存在");
//			return -1;
//		}
//		//商品不是未审核状态
//		if(p.getProductStatus() != ProductDao.PRODUCT_STATUC_VERIFY){
//			LOG.error("商品派单失败-2,商品派单不是未审核状态");
//			return -2;
//		}
//		
//		//第一步：找出所有上班 并可以接单的客服
//		List<SysUserinfo> list = customerServiceDutyDao.onDutyUserList(1,1);
//		if(null == list){
//			LOG.error("商品派单失败-3,未分配客服/物服");
//		
//			return -3;
//		}
//		
//		StringBuffer uidStr = new StringBuffer("");
//		//第二步：根据商品信息 筛选有权限的客服物服
//		for(int c = 0;c < list.size();c++ ){
//		//1 业务过程 上架 （变更为主级别）
//		//int i = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_BUSINESSTYPE,1,list.get(c).getUserId());
//        
//		//2 交易模式 
//		int j = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_TRADEMODEL,p.getProductOnsaleModel(), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_VERIFY);
//		
//		//3 商品类型
//		//int typeSubduty = gameProductTypeService.queryByGameIdAndTypeId(p.getGameId(), p.getProductType());
//		//int k = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_PRODUCTTYPE, typeSubduty, list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_VERIFY);
//		
//		//4 用户类型
//		int l = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_MEMBERTYPE, memberService.isVIP(p.getUserId()), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_VERIFY);
//		//5 游戏
//		//int m = customerServiceDutyDao.hasDuty(customerServiceDutyDao.DUTYTYPE_GAME, p.getGameId(), list.get(c).getUserId(),customerServiceDutyDao.MAIN_DUTY_VERIFY);
//		//第三步：有权限的客服 再筛选接单最少的uid 
//		if( j * l  == 1){
//			uidStr.append(list.get(c).getUserId() + ",");
//		}
//		}
//		String str = uidStr.toString();
//		LOG.info("==商品派单符合条件的uid==>>" + str);
//		if (!"".equals(str) && null != str && str.contains(",")) {
//			str = str.substring(0, str.length() - 1);
//		}else{
//			LOG.error("商品派单失败-4，商品职责分类找到到匹配的客服，商品ID：" + p.getProductId());
//			return -4;
//		}
//		result = customerServiceDutyDao.findMinUIDProductC2C(str);
//		
//		//第四步:更新c2c 商品信息表  分派客服
//		int r = customerServiceDutyDao.assignProductCustomer(p.getProductId(),result);
//		LOG.info("pai" + p.getProductId());
//		if(r == 0){
//			LOG.info("商品派单成功 product_id="+p.getProductId()+",uid==" + result);
//			return result;
//		}else{
//			LOG.error("商品派单失败-5,商品c2c表信息不存在该商品,商品ID：" + p.getProductId());
//			return -5;
//		}
//	}

	@Override
	public int findMinUIDProductC2C(String uidStr) {
		int i = customerServiceDutyDao.findMinUIDProductC2C(uidStr);
		return i;
	}

	@Override
	public List<SysUserinfo> dutyUserList(int isCustomer, int isObjectCustomer) {
		return customerServiceDutyDao.dutyUserList(isCustomer, isObjectCustomer);
	}

//	@Override
//	public JSONObject queryAllOnlineServiceList() {
//		return customerServiceDutyDao.queryAllOnlineServiceList();
//	}
	
	@Override
	public PageInfo<SysUserinfo> queryCustomerPageInfo(Map<String, Object> condition, int pageSize, int pageNo,
			String sort, String order) {
		if (null == sort || ("").equals(sort)) {
			sort = "createTime";
		}
		if (null == order) {
			order = "DESC";
		}
		StringBuffer sqlBody = new StringBuffer(" from sys_userinfo");
		List<Object> conList = new ArrayList<Object>();
		if (null != condition) {
			if (null != condition.get("customerType")) {// 客服/物服/审核员
				Integer customerType = (Integer) condition.get("customerType");
				if (customerType == 0) {
					sqlBody.append(" WHERE is_customer !=0 ");
				} else if (customerType == 1) {
					sqlBody.append(" WHERE is_object_customer !=0 ");
				} else if (customerType == 2) {
					sqlBody.append(" WHERE (is_customer !=0 AND is_object_customer !=0) ");
				} else if (customerType == 3) {
					sqlBody.append(" WHERE (is_investment_service =1 or is_investment_service =10 )");
				} else if (customerType == 4) {
					sqlBody.append(" WHERE (is_investment_service =2 or is_investment_service =10 )");
				}else if (customerType == 5) {
					sqlBody.append(" WHERE (is_investment_service =10  )");
				}
			} else {
				sqlBody.append(" WHERE (is_customer !=0 OR is_object_customer !=0 OR is_investment_service !=0) ");
			}
			if (null != condition.get("fullname")) {// 姓名
				sqlBody.append(" and fullname like '");
				sqlBody.append("%" + condition.get("fullname") + "%'");
			}
			if (null != condition.get("postNo")) {// 岗位号
				sqlBody.append(" and custom_service_id like '");
				sqlBody.append("%" + condition.get("postNo") + "%'");
			}
			if (null != condition.get("isOnDuty")) {// 是否上班
				sqlBody.append(" and is_on_duty =? ");
				conList.add(condition.get("isOnDuty"));
			}
			if (null != condition.get("onlineStatus")) {// 在线状态
				sqlBody.append(" and online_status =? ");
				conList.add(condition.get("onlineStatus"));
			}
		} else {
			sqlBody.append(" WHERE (is_customer !=0 OR is_object_customer !=0 OR is_investment_service !=0) ");
		}

		String sqlcount = "from SysUserinfo " + sqlBody.toString();
		System.err.println("9.4" + sqlBody.toString());
		
        int count =0 ;
        int firstResult = 1;
		sqlBody.append(" order by " + sort + " " + order);
		String sqlCount = "select count(1) " + sqlBody.toString().toString();
		count = jdbcOperations.queryForInt(sqlCount, conList.toArray());
		PageInfo<SysUserinfo> pageinfo = new PageInfo<SysUserinfo>(count, pageSize);
		if (count == 0) {
			return pageinfo;
		}
		pageinfo.setPageno(pageNo);
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if (pageNo > pageinfo.getPageCount()) {
			pageNo = pageinfo.getPageCount();
		}
		firstResult = (pageNo - 1) * pageinfo.getPageSize();

		sqlBody.append(" limit " + firstResult + "," + pageinfo.getPageSize());

		String sqlPage = "select * " + sqlBody.toString();
		JuRowCallbackHandler<SysUserinfo> cb = new JuRowCallbackHandler<SysUserinfo>(SysUserinfo.class);

		jdbcOperations.query(sqlPage, cb, conList.toArray());
		pageinfo.setItems(cb.getList());
		
		return pageinfo;
	}

}
