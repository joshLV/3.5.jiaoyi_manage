package cn.juhaowan.count.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.count.dao.StatisticsTOP100Dao;
import cn.juhaowan.count.vo.StatisticsTop100;

/**
 * 消费|销售Top100_DAO_IMPL
 * 
 * @author APXer
 * 
 */
@Repository("StatisticsTOP100Dao")
public class StatisticsTOP100DaoImpl implements StatisticsTOP100Dao {
	@Autowired
	private JdbcOperations jdbcOperations;

	@Override
	public List<StatisticsTop100> queryTOP100List(String fromDays,
			String toDays, String excludeUsers, int topType) {
		StringBuffer fromToSB = new StringBuffer();
		if (StringUtils.isBlank(fromDays)) {// 从...
			return Collections.emptyList();
		} else {
			fromToSB.append("and DATE_FORMAT(get_goods_time	,'%Y-%m-%d') >=?");
			if ("".equals(toDays)) {// 到...为止
				fromToSB.append(" AND DATE_FORMAT(get_goods_time,'%Y-%m-%d') <= DATE_FORMAT(now(),'%Y-%m-%d')");
			}
		}
		if (!StringUtils.isBlank(toDays)) {// 到...为止
			fromToSB.append("  AND DATE_FORMAT(get_goods_time,'%Y-%m-%d') <= DATE_FORMAT(?,'%Y-%m-%d') ");
		}

		JuRowCallbackHandler<StatisticsTop100> cb = new JuRowCallbackHandler<StatisticsTop100>(
				StatisticsTop100.class);
		StringBuffer sqlSB = new StringBuffer();
		if (0 == topType) {// 消费TOP100榜
			sqlSB.append("SELECT order_buy_uid uid,SUM(order_amount) amount,COUNT(order_id) items ");
			sqlSB.append("FROM orders WHERE order_status =6 ");
			sqlSB.append(fromToSB);
			if (!StringUtils.isBlank(excludeUsers)) {
				sqlSB.append("AND NOT order_buy_uid in(" + excludeUsers + ") ");
			}
			sqlSB.append("GROUP BY order_buy_uid ");
			sqlSB.append("ORDER BY amount DESC,items DESC LIMIT 0,100");
		} else if (1 == topType) {
			sqlSB.append("SELECT order_sell_uid uid,SUM(order_amount) amount,COUNT(order_id) items ");
			sqlSB.append("FROM orders where order_status =6 ");
			sqlSB.append(fromToSB);
			if (!StringUtils.isBlank(excludeUsers)) {
				sqlSB.append("AND NOT order_sell_uid in(" + excludeUsers + ") ");
			}
			sqlSB.append("GROUP BY order_sell_uid ");
			sqlSB.append("ORDER BY amount DESC,items DESC LIMIT 0,100");
		}
		if ("".equals(toDays)) {// 到...为止
			jdbcOperations.query(sqlSB.toString(), cb, fromDays);
		} else {
			jdbcOperations.query(sqlSB.toString(), cb, fromDays, toDays);
		}
		List<StatisticsTop100> top100List = cb.getList();
		if (!top100List.isEmpty()) {
			return top100List;
		}
		return null;
	}

}
