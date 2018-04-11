package cn.juhaowan.count.dao;

import java.util.List;
import java.util.Map;

import cn.jugame.dal.dao.BaseDao;
import cn.jugame.util.PageInfo;
import cn.juhaowan.count.vo.CountDayMoneyIo;
/**
 * 金额统计
 * @author Administrator
 *
 */
public interface CountDayMoneyIoDao extends BaseDao<CountDayMoneyIo>{
	/**
	 * 按id查找每日金额统计信息
	 * @param id 编号
	 * @return 日金额统计信息
	 */
	public CountDayMoneyIo findById(int id);
	
	
	
	   /** 按条件分页查询列表
	    * @param conditions 查询条件
	    * @param pageNo 页数
	    * @param pageSize 每页条数
	    * @param sort 排序字段
	    * @param order desc/asc
	    * @return 分页
	    * @pdOid 4f0ade49-6c39-4c50-9dff-4874dd2991eb */
	   PageInfo queryForPage(Map<String,Object> conditions, int pageNo, int pageSize, String sort, String order);
	   
	   /**
	    * 提现人数 分时段
	    * @param conditions 条件
	    * @return 提现人数 分时段列表信息
	    */
	   List<Map> queryTakeMemberNumByTime(Map<String,Object> conditions);
	   
	   /**
	    * 提现笔数 分时段
	    * @param conditions 条件
	    * @return 提现笔数 分时段列表信息
	    */
	   List<Map> queryTakeNumByTime(Map<String,Object> conditions);
	   
	   /**
	    * 提现成功人数 分时段
	    * @param conditions 条件
	    * @return 提现成功人数 分时段列表信息
	    */
	   List<Map> querytakeMemberSucNumByTime(Map conditions);
	   
	   /**
	    * 提现成功笔数 分时段
	    * @param conditions 条件
	    * @return 提现成功笔数 分时段列表信息
	    */
	   List<Map> queryTakeSucNumByTime(Map<String,Object> conditions);
	   
	   /**
	    * 提现金额 分时段
	    * @param conditions 条件
	    * @return 提现金额 分时段列表信息
	    */
	   List<Map> queryTakeMoneyDayCountByTime(Map<String,Object> conditions);
	   
	   /**
	    * 充值人数 分时段
	    * @param conditions 条件
	    * @return 充值人数 分时段列表信息
	    */
	   List<Map> queryRechargeMemberNumByTime(Map conditions);
	   
	   /**
	    * 充值笔数 分时段
	    * @param conditions 条件
	    * @return 充值笔数 分时段列表信息
	    */
	   List<Map> queryRechargeNumByTime(Map<String,Object> conditions);
	   
	   /**
	    * 充值金额 分时段
	    * @param conditions 条件
	    * @return 充值金额 分时段列表信息
	    */
	   List<Map> queryRechargeMoneyCountByTime(Map<String,Object> conditions);
	   
	   /**
	    * 账户余额 分时段
	    * @param conditions 条件
	    * @return 账户余额 分时段列表信息
	    */
	   List<Map> queryAccountAmountByTime(Map conditions);
}
