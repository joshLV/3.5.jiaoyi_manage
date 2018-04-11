package cn.juhaowan.log.service;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.vo.FrontUserLog;

/**
 * 前台日志
 * **/
public interface FrontUserLogService {

	// 日志类型
	public static int MOBILE_BIND = 1; // 手机换绑
	public static int USER_REGISTER = 2; // 注册
	public static int AFFIRM_NAME = 3; // 实名认证确认
	public static int PAYMENT_CODE_SET = 4; // 支付密码设置
	public static int BANK_INFORMATION_ALTER = 5; // 修改个人银行信息
	public static int WITHDRAW_DEPOSIT_SET = 6; // 提现设置"
	public static int USER_LOGIN = 7; // 登录
	public static int RETRIEVE_PASS = 8; // 找回密码
	public static int POSTTRADE = 9; // 发布商品
	public static int CANCEL_COMMODITY = 10; // 下架商品
	public static int BUY_COMMODITY = 11; // 购买商品
	public static int RECHARGE = 12; // 充值
	public static int WITHDRAW_DEPOSIT = 13; // 提现
	public static int CELERITY_PSY = 14; // 快捷支付

	/**
	 * 写入日志
	 * 
	 * @param uid
	 *            操作人
	 * @param ip
	 *            操作ip
	 * @param type
	 *            日志类型
	 **/
	int addLog(int uid, String ip, int type, String logRemark);

	/**
	 * 查询日志列表
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageSize
	 *            每页显示数
	 * @param pageNo
	 *            显示页
	 * @param sort
	 *            排序字段
	 * @param order
	 *            排序规则
	 **/
	PageInfo<FrontUserLog> queryLogList(Map<String, Object> condition,
			int pageSize, int pageNo, String sort, String order);

}