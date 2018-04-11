package cn.juhaowan.customer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.customer.dao.CustomerServiceDutyDao;
import cn.juhaowan.customer.vo.CustomerServiceDuty;
import cn.juhaowan.customer.vo.DutyListForm;
import cn.juhaowan.customer.vo.SysUserinfo;


/**
 * 
 * @author Administrator
 * 
 */
@Repository("CustomerServiceDutyDao")
public class CustomerServiceDutyDaoImpl extends BaseDaoImplJdbc<Object>
		implements CustomerServiceDutyDao {
	private static Logger LOG = LoggerFactory.getLogger(CustomerServiceDutyDaoImpl.class);
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public int insert(CustomerServiceDuty duty) {
		String sql = "INSERT INTO `customer_service_duty`(`duty_id`,`duty_subid`,`customer_service_id`,`busness_type`) VALUES (?,?,?,?)";
		int i = jdbcOperations.update(sql, duty.getDutyId(),
				duty.getDutySubid(), duty.getCustomerServiceId(),
				duty.getBusnessType());
		return i ^ 1;
	}

	@Override
	public int delete(int uid) {
		String sql = "DELETE FROM `customer_service_duty` WHERE `customer_service_id` = ? ";
		int i = jdbcOperations.update(sql, uid);
		return i ^ 1;
	}

	@Override
	public List<CustomerServiceDuty> queryByCSId(String SCId) {
		List<CustomerServiceDuty> list = null;
		String sql = "SELECT * FROM `customer_service_duty` where customer_service_id = ? ";
		JuRowCallbackHandler<CustomerServiceDuty> jrb = new JuRowCallbackHandler<CustomerServiceDuty>(
				CustomerServiceDuty.class);
		jdbcOperations.query(sql, jrb, SCId);
		if (jrb.getList().size() > 0) {
			list = jrb.getList();
		}
		return list;
	}

	@Override
	public List<DutyListForm> queryByuId(String SCId) {
		List<DutyListForm> list = null;
		String sql = "SELECT * FROM `customer_service_duty` where customer_service_id = ? ";
		JuRowCallbackHandler<DutyListForm> jrb = new JuRowCallbackHandler<DutyListForm>(
				DutyListForm.class);
		jdbcOperations.query(sql, jrb, SCId);
		if (jrb.getList().size() > 0) {
			list = jrb.getList();
		}
		return list;
	}

	@Override
	public List<CustomerServiceDuty> queryByCSId(int dutyId, String SCId,
			int busType) {
		List<CustomerServiceDuty> list = null;
		String sql = "SELECT * FROM `customer_service_duty` where duty_id = ? and customer_service_id = ? and busness_type = ?";
		JuRowCallbackHandler<CustomerServiceDuty> jrb = new JuRowCallbackHandler<CustomerServiceDuty>(
				CustomerServiceDuty.class);
		jdbcOperations.query(sql, jrb, dutyId, SCId, busType);
		if (jrb.getList().size() > 0) {
			list = jrb.getList();
		}
		return list;
	}

	@Override
	public List<CustomerServiceDuty> queryByCSId(int dutyId, int subdutyId,
			String SCId) {
		List<CustomerServiceDuty> list = null;
		String sql = "SELECT * FROM `customer_service_duty` where duty_id = ? and duty_subid = ? and customer_service_id = ? ";
		JuRowCallbackHandler<CustomerServiceDuty> jrb = new JuRowCallbackHandler<CustomerServiceDuty>(
				CustomerServiceDuty.class);
		jdbcOperations.query(sql, jrb, dutyId, subdutyId, SCId);
		if (jrb.getList().size() > 0) {
			list = jrb.getList();
		}
		return list;
	}

	// 上班 并且可以接单的 客服/物服
	@Override
	public List<SysUserinfo> onDutyUserList(int isCustomer, int isObjectCustomer) {
		List<SysUserinfo> sysuserlist = null;
		String sql = "SELECT * FROM `sys_userinfo` WHERE (`is_customer` = ? OR `is_object_customer`= ?) AND  `is_on_duty` = 1 AND  `online_status`=2";
		JuRowCallbackHandler<SysUserinfo> jrb = new JuRowCallbackHandler<SysUserinfo>(
				SysUserinfo.class);
		jdbcOperations.query(sql, jrb, isCustomer, isObjectCustomer);
		if (jrb.getList().size() > 0) {
			sysuserlist = jrb.getList();
		}
		return sysuserlist;
	}

	// 传入uid 职责类型 职责值 判断
	@Override
	public int hasDuty(int dutyId, int subDutyId, int uid, int busType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT `duty_subid` ,GROUP_CONCAT(`customer_service_id`) FROM customer_service_duty WHERE duty_id = ? AND `busness_type`= ? GROUP BY duty_subid;");
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString(), dutyId,
				busType);
		while (rs.next()) {
			if (rs.getString(2).contains(String.valueOf(uid))
					&& rs.getInt(1) == subDutyId) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int findMinUIDProductC2C(String uidStr) {
		int i = 0;
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
//		sql.append("SELECT res.uid,res.coun FROM (SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID`, bb.`LOGINID`,bb.`FULLNAME` ,aa.* FROM  (SELECT * FROM  `product_c2c` WHERE is_verify =0) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr
//				+ ")) mm "
//				+ "GROUP BY mm.USER_ID ) res WHERE coun = (SELECT MIN(mk.coun) FROM(SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID`,aa.* FROM (SELECT * FROM  `product_c2c` WHERE is_verify =0) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr + ")) mm " + "GROUP BY mm.USER_ID ) mk)");
//		sql.append("SELECT customer_service_id uid,COUNT(customer_service_id) coun FROM product_c2c WHERE is_verify = 0 "
//                +"AND  customer_service_id IS NOT NULL AND customer_service_id IN ("+ uidStr +") GROUP BY customer_service_id  ORDER BY coun LIMIT 1"
//				);
		sql2.append("SELECT ");
		String[] str = null;
		if(uidStr.contains(",")){
			str = uidStr.split(",");
			
		}else{
			try {
				i = Integer.parseInt(uidStr);
				LOG.info("只有一个可派单客服id " + uidStr);
			} catch (Exception e) {
				LOG.error("只有一个可派单客服时错误" + uidStr);
			}
			return  i;
			
		}
		for(int t =0;t <str.length;t++){
			sql2.append(" COUNT( CASE WHEN customer_service_id ="+str[t]+" AND is_verify =0 AND DATE_SUB(CURDATE(), INTERVAL 2 DAY) <= DATE(assign_time)  THEN 1 ELSE NULL END ) AS "+str[t]+"_a");
			if(t+1 < str.length){
				sql2.append(",");
			}
		}
	
		sql2.append(" FROM product_c2c");
		
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql2.toString());
		List<Integer> result = new ArrayList<Integer>();
		Map<Integer,Integer> countMap  = new HashMap<Integer,Integer>();
		while (rs.next()) {
			for(int tt =0;tt <str.length;tt++){
				countMap.put(Integer.parseInt(str[tt]), rs.getInt(tt+1));
			}
		}
        //找到map里面最小计数的key列表
        result = getKeyByValue(countMap,getMinValue(countMap));
        if(result == null){
        	return 0;
        }
        
		// 最后接单的客服id
		int m = this.productLastAssign();
		// 只有一个客服 不对比
		if (result.size() == 1) {
			return result.get(0);
		}
		// 如果有多个客服 筛选掉已经分过单的客服
		for (int j = 0; j < result.size(); j++) {
			if (m == result.get(j)) {// 如果上一单已经存在分配客服 去除后随机选取一位
				result.remove(j);
			}
		}
		if (result.size() == 1) {
			return result.get(0);
		}
		for (int t = 0; t < result.size();) {
			Random r = new Random();
			int ran = r.nextInt(result.size());
			return result.get(ran);
		}

		return i;
	}

	// 最少单客服
	@Override
	public int findMinCustomerUIDOrderC2C(String uidStr) {
		int i = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(res.uid),res.coun FROM (SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
				+ "(SELECT * FROM(SELECT bb.`USER_ID` ,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status !=1 AND order_c2c_status !=5) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
				+ uidStr
				+ ")) mm "
				+ "GROUP BY mm.USER_ID ) res WHERE coun = (SELECT MIN(mk.coun) FROM(SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
				+ "(SELECT * FROM(SELECT bb.`USER_ID`,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status !=1 AND order_c2c_status !=5) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
				+ uidStr + ")) mm " + "GROUP BY mm.USER_ID ) mk)");
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());
		while (rs.next()) {
			i = rs.getInt(1);
		}

		return i;
	}

	// 最少单的物服
	@Override
	public int findMinObjectUIDOrderC2C(String uidStr) {
		int i = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(res.uid),res.coun FROM (SELECT COUNT(physic_service_id) coun,mm.USER_ID uid ,physic_service_id serid FROM "
				+ "(SELECT * FROM(SELECT bb.`USER_ID` ,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =1 OR order_c2c_status =4 OR order_c2c_status =5) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.physic_service_id)) oo WHERE oo.USER_ID IN("
				+ uidStr
				+ ")) mm "
				+ "GROUP BY mm.USER_ID ) res WHERE coun = (SELECT MIN(mk.coun) FROM(SELECT COUNT(physic_service_id) coun,mm.USER_ID uid ,physic_service_id serid FROM "
				+ "(SELECT * FROM(SELECT bb.`USER_ID`,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =1 OR order_c2c_status =4 OR order_c2c_status =5) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.physic_service_id)) oo WHERE oo.USER_ID IN("
				+ uidStr + ")) mm " + "GROUP BY mm.USER_ID ) mk)");
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString());
		while (rs.next()) {
			i = rs.getInt(1);
		}

		return i;
	}

	@Override
	public int assignProductCustomer(String productId, int customerId) {
		String sql = "UPDATE `product_c2c` SET `customer_service_id` = ? ,`assign_time` = NOW(),is_verify = 1 WHERE product_id = ?  ";
		int i = jdbcOperations.update(sql, customerId, productId);
		return i ^ 1;
	}

	@Override
	public int productLastAssign() {
		int i = 0;
		String sql = "SELECT b.customer_service_id FROM product_c2c b WHERE customer_service_id IS NOT NULL AND customer_service_id != 0 ORDER BY assign_time DESC LIMIT 1";
		SqlRowSet re = jdbcOperations.queryForRowSet(sql);
		while (re.next()) {
			i = re.getInt(1);
		}
		return i;
	}

	@Override
	public List<SysUserinfo> dutyUserList(int isCustomer, int isObjectCustomer) {
		List<SysUserinfo> sysuserlist = null;
		String sql = "SELECT * FROM `sys_userinfo` WHERE `is_customer` = ? and `is_object_customer`= ?";
		JuRowCallbackHandler<SysUserinfo> jrb = new JuRowCallbackHandler<SysUserinfo>(
				SysUserinfo.class);
		jdbcOperations.query(sql, jrb, isCustomer, isObjectCustomer);
		if (jrb.getList().size() > 0) {
			sysuserlist = jrb.getList();
		}
		return sysuserlist;
	}

//	@Override
//	public JSONObject queryAllOnlineServiceList() {
//		RowMapper<ServiceJSON> rowMapper = new RowMapper<ServiceJSON>() {
//			@Override
//			public ServiceJSON mapRow(ResultSet rs, int rowNum)
//					throws SQLException {
//				ServiceJSON serviceJSON = new ServiceJSON();
//				serviceJSON.setUserId(rs.getInt(1));
//				serviceJSON.setLoginId(rs.getString(2));
//				serviceJSON.setFullname(rs.getString(3));
//				serviceJSON.setServcieNickname(rs.getString(4));
//				int isService = rs.getInt(5);
//				int isObjectService = rs.getInt(6);
//				int isInvestmentServcie = rs.getInt(7);
//				List<Integer> serviceList = new ArrayList<Integer>();
//				if (isService > 0 && isObjectService > 0) {
//					serviceList.add(1);
//					serviceList.add(2);
//					serviceList.add(3);
//				} else if (isObjectService > 0) {
//					serviceList.add(2);
//				} else if (isService > 0) {
//					serviceList.add(1);
//				} else if (isInvestmentServcie > 0) {
//					serviceList.add(4);
//				}
//
//				int[] serviceType = new int[serviceList.size()];
//				for (int i = 0; i < serviceList.size(); i++) {
//					serviceType[i] = serviceList.get(i);
//				}
//				serviceJSON.setServiceType(serviceType);
//				return serviceJSON;
//			}
//		};
//		String serviceSQL = "SELECT user_id,loginid,fullname,customer_nickname,is_customer,is_object_customer,is_investment_service FROM `sys_userinfo` WHERE is_on_duty=1 ";
//		// if (CustomerServiceDutyDao.IS_SERVICE == serviceType) {
//		// serviceSQL.append(" `is_customer` = 1");
//		// } else if (CustomerServiceDutyDao.IS_OBJECT_SERVICE == serviceType) {
//		// serviceSQL.append(" `is_object_customer` = 1");
//		// } else if (CustomerServiceDutyDao.IS_AUDITOR == serviceType) {
//		// serviceSQL
//		// .append(" (`is_customer` = 1 and `is_object_customer` = 1)");
//		// } else if (CustomerServiceDutyDao.IS_INVESTMENT_SERVICE ==
//		// serviceType) {
//		// serviceSQL.append(" `is_investment_service` = 1");
//		// } else {
//		// serviceSQL
//		// .append(" (`is_customer` = 1 or `is_object_customer` = 1 or `is_investment_service` = 1)");
//		// }
//		List<ServiceJSON> objList = jdbcOperations.query(serviceSQL.toString(),
//				rowMapper);
//		JSONObject json = new JSONObject();
//		if (!objList.isEmpty()) {
//			json.put("code", 0);
//			json.put("msg", "ok");
//			json.put("data", JSONArray.fromObject(objList));
//			return json;
//		} else {
//			json.put("code", 1);
//			json.put("msg", "data is empty");
//			json.put("data", Collections.emptyList());
//			return json;
//		}
//
//	}
	
    public static Object getMinValue(Map<Integer, Integer> map) {
        if (map == null) return null;
        Collection<Integer> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[0];
    }
    
    public static List getKeyByValue(Map map, Object value) {
    	List keys = new ArrayList();
    	Iterator it = map.entrySet().iterator();
    	while (it.hasNext()) {
    	Map.Entry entry = (Entry) it.next();
    	Object obj = entry.getValue();
    	if (obj != null && obj.equals(value)) {
    	keys.add(entry.getKey());
    	}

    	}
    	return keys;
    	}
}
