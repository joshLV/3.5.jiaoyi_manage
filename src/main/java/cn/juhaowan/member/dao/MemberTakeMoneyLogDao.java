package cn.juhaowan.member.dao;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.MemberTakeMoneyLog;

public interface MemberTakeMoneyLogDao{

	

	

	/**
	 * 分页查询
	 * @author Administrator
	 * @param condition 查询条件
	 * @param pageSize 页记录数
	 * @param pageNo 当前页
	 * @param sort 排序字段
	 * @param order 升序/降序 
	 * @return 分页信息
	 * 
	 * */
	public PageInfo<MemberTakeMoneyLog> findLogWithPage(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order);
	
	
}
