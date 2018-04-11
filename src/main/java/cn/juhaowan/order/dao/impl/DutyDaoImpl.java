package cn.juhaowan.order.dao.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.order.dao.DutyDao;
import cn.juhaowan.order.vo.SysUserinfo;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("DutyDao")
public class DutyDaoImpl extends BaseDaoImplJdbc<Object> implements DutyDao {

	@Autowired
	private JdbcOperations jdbcOperations;

	// 上班 并且可以接单的 客服/物服
	@Override
	public List<SysUserinfo> onDutyUserList(int isCustomer, int isObjectCustomer) {
		List<SysUserinfo> sysuserlist = null;
		String sql = "SELECT * FROM `sys_userinfo` WHERE (`is_customer` = ? OR `is_object_customer`= ?) AND  `is_on_duty` = 1 AND  `online_status`=2";
		JuRowCallbackHandler<SysUserinfo> jrb = new JuRowCallbackHandler<SysUserinfo>(SysUserinfo.class);
		jdbcOperations.query(sql, jrb, isCustomer, isObjectCustomer);
		if (jrb.getList().size() > 0) {
			sysuserlist = jrb.getList();
		}
		return sysuserlist;
	}

	// 传入uid 职责类型 职责值 判断
	@Override
	public int hasDuty(int dutyId, int subDutyId, int uid,int busType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT `duty_subid` ,GROUP_CONCAT(`customer_service_id`) FROM customer_service_duty WHERE duty_id = ? AND `busness_type`= ? GROUP BY duty_subid;");
		// 查询的结果集
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql.toString(), dutyId,busType);
		while (rs.next()) {
			if (rs.getString(2).contains(String.valueOf(uid)) && rs.getInt(1) == subDutyId) {
				return 1;
			}
		}
		return 0;
	}

	// 最少单客服
	@Override
	public int findMinCustomerUIDOrderC2C(String uidStr) {
		int i = 0;
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT res.uid,res.coun FROM (SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID` ,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =2 OR order_c2c_status =5 OR order_c2c_status =6) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr
//				+ ")) mm "
//				+ "GROUP BY mm.USER_ID ) res WHERE coun = (SELECT MIN(mk.coun) FROM(SELECT COUNT(customer_service_id) coun,mm.USER_ID uid ,customer_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID`,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =2 OR order_c2c_status =5 OR order_c2c_status =6) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.customer_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr + ")) mm " + "GROUP BY mm.USER_ID ) mk)");
		StringBuffer sql2 = new StringBuffer();
		sql2.append("SELECT ");
		String[] str = null;
		if(uidStr.contains(",")){
			str = uidStr.split(",");
			
		}else{
			try {
				i = Integer.parseInt(uidStr);
				//LOG.info("只有一个可派单客服id " + uidStr);
			} catch (Exception e) {
				//LOG.error("只有一个可派单客服时错误" + uidStr);
			}
			return  i;
			
		}
		for(int t =0;t <str.length;t++){
			sql2.append(" COUNT( CASE WHEN customer_service_id ="+str[t]+"  AND (order_c2c_status =2 OR order_c2c_status =6 OR order_c2c_status =8 OR order_c2c_status =103) AND DATE_SUB(CURDATE(), INTERVAL 12 DAY) <= DATE(`customer_service_time`)   THEN 1 ELSE NULL END ) AS "+str[t]+"_a");
			if(t+1 < str.length){
				sql2.append(",");
			}
		}
	
		sql2.append(" FROM orders_c2c");
		
		
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
		int m = this.orderLastAssignCS();
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
		if (result.size() > 1) {

			Random r = new Random();
			int ran = r.nextInt(result.size());
			return result.get(ran);
		}

		return i;
	}

	// 最少单的物服
	@Override
	public int findMinObjectUIDOrderC2C(String uidStr) {
		int i = 0;
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT res.uid,res.coun FROM (SELECT COUNT(physic_service_id) coun,mm.USER_ID uid ,physic_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID` ,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =3 OR order_c2c_status =7 ) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.physic_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr
//				+ ")) mm "
//				+ "GROUP BY mm.USER_ID ) res WHERE coun = (SELECT MIN(mk.coun) FROM(SELECT COUNT(physic_service_id) coun,mm.USER_ID uid ,physic_service_id serid FROM "
//				+ "(SELECT * FROM(SELECT bb.`USER_ID`,aa.* FROM  (SELECT * FROM `orders_c2c` WHERE order_c2c_status =3 OR order_c2c_status =7) aa RIGHT JOIN `sys_userinfo` bb ON(bb.`USER_ID` =aa.physic_service_id)) oo WHERE oo.USER_ID IN("
//				+ uidStr + ")) mm " + "GROUP BY mm.USER_ID ) mk)");
		
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("SELECT ");
		String[] str = null;
		if(uidStr.contains(",")){
			str = uidStr.split(",");
			
		}else{
			try {
				i = Integer.parseInt(uidStr);
				//LOG.info("只有一个可派单客服id " + uidStr);
			} catch (Exception e) {
				//LOG.error("只有一个可派单客服时错误" + uidStr);
			}
			return  i;
			
		}
		for(int t =0;t <str.length;t++){
			sql2.append(" COUNT( CASE WHEN physic_service_id ="+str[t]+"  AND (order_c2c_status =3 OR order_c2c_status =7 OR order_c2c_status =102) AND DATE_SUB(CURDATE(), INTERVAL 12 DAY) <= DATE(`physic_service_time`)    THEN 1 ELSE NULL END ) AS "+str[t]+"_a");
			if(t+1 < str.length){
				sql2.append(",");
			}
		}
	
		sql2.append(" FROM orders_c2c");
		
		
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
		
		// 最后接单的物服id
		int m = this.orderLastAssignOS();
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
		if ( result.size() > 1) {
			Random r = new Random();
			int ran = r.nextInt(result.size());
			return result.get(ran);
		}
		
		return i;
	}
    //最后接单客服id
	@Override
	public int orderLastAssignCS() {
		int i = 0;
		String sql = "SELECT customer_service_id,customer_service_time FROM orders_c2c  ORDER BY customer_service_time DESC LIMIT 1";
		SqlRowSet re = jdbcOperations.queryForRowSet(sql);
		while(re.next()){
			i = re.getInt(1);
		}
		return i;
	}
    //最后接单物服id
	@Override
	public int orderLastAssignOS() {
		int i = 0;
		String sql = "SELECT physic_service_id,customer_service_time FROM orders_c2c  ORDER BY physic_service_time DESC LIMIT 1";
		SqlRowSet re = jdbcOperations.queryForRowSet(sql);
		while(re.next()){
			i = re.getInt(1);
		}
		return i;
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
	
    
    public static Object getMinValue(Map<Integer, Integer> map) {
        if (map == null) return null;
        Collection<Integer> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[0];
    }

}
