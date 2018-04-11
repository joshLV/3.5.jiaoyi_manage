package cn.jugame.admin.notify.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import cn.jugame.admin.notify.IMenuCount;
import cn.jugame.admin.notify.INotify;

@Service("INotify")
public class NotifyImpl implements INotify {

	@Autowired
	private JdbcOperations jdbcTemplate;
//	@Autowired
//	private OrdersC2cService ordersC2cService;
//
//	@Autowired
//	private ProductService productService;

	@Autowired
	private IMenuCount menuCount;

	@Override
	public List<Map<String, Integer>> getNotifyCount(int kefuId, int userType) {

		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();

		String sql = "select modId,className from sys_notify_count;";
		SqlRowSet re = jdbcTemplate.queryForRowSet(sql);
		while (re.next()) {
			Map<String, Integer> m = new HashMap<String, Integer>();
			String classname = re.getString(2);
			int modId = re.getInt(1);

			try {
				menuCount = (IMenuCount) Class.forName(classname).newInstance();
				m.put("modId", modId);
				m.put("count", menuCount.getCount(kefuId, userType));
			} catch (Exception e) {
				continue;
			}

			list.add(m);
		}

		return list;
	}

}
