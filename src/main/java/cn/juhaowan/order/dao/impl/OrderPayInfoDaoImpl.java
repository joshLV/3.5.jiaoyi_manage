package cn.juhaowan.order.dao.impl;


import org.springframework.stereotype.Repository;

import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.order.dao.OrderPayInfoDao;
import cn.juhaowan.order.vo.OrderPayInfo;
/**
 * 支付单接口实现
 * @author houjt
 *
 */
@Repository("OrderPayInfoDao")
public class OrderPayInfoDaoImpl extends BaseDaoImplJdbc<OrderPayInfo> implements
		OrderPayInfoDao {

}
