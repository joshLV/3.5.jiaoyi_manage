package cn.juhaowan.member.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.juhaowan.member.dao.MemberCashSettingDao;
import cn.juhaowan.member.vo.MemberCashSetting;

@Repository("MemberCashSettingDao")
public class MemberCashSettingDaoImpl implements MemberCashSettingDao {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	public String getTableName(){
		return "member_cash_setting";
	}
	@Override
	public int setTakeMoneyLimit(int uid, int dayLimit, int itemLimit) {
		String sql = "replace into " + getTableName() + "(uid,day_limit,item_limit) value(?,?,?)";
		return jdbcOperations.update(sql, uid,dayLimit,itemLimit);
	}

	@Override
	public MemberCashSetting findByUid(int uid) {
		String sql = "select * from " + getTableName() + " where uid = ?";
		JuRowCallbackHandler<MemberCashSetting> callbackHandler = new JuRowCallbackHandler<>(MemberCashSetting.class);
		jdbcOperations.query(sql, callbackHandler , uid);
		if (callbackHandler.getList().size() > 0){
			return callbackHandler.getList().get(0);
		}
		return null;
	}

}
