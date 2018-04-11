package cn.juhaowan.order.dao;

import cn.jugame.dal.dao.BaseDao;
import cn.juhaowan.order.vo.OrderPayInfo;
/**
 * 支付单接口
 * @author houjt
 *
 */
public interface OrderPayInfoDao extends BaseDao<OrderPayInfo> {
	/**
	 * 资金类型:现金账户
	 */
	public int MONEY_TYPE_CASH  =  1;
	/**
	 * 资金类型:非现金
	 */
	public int MONEY_TYPE_NOT_CASH  =  2;
	/**
	 * 支付渠道：聚好玩(充值)
	 */
	public int PAY_PLATFORM_JUGAME = 0;
	/**
	 * 支付渠道：易宝
	 */
	public int PAY_PLATFORM_YB = 1;
	/**
	 * 支付渠道：银联
	 */
	public int PAY_PLATFORM_UNIONPAY = 2;
	/**
	 * 支付渠道：支付宝
	 */
	public int PAY_PLATFORM_ZFB = 4;
	/**
	 * 支付渠道 :易宝-信用卡
	 */
	public int PAY_PLATFORM_YB_CREDIT=3;
	/**
	 * 支付渠道：聚好玩(超时赔付)
	 */
	public int PAY_PLATFORM_JUGAME_PAY=5;
	/**
	 * 支付渠道：聚好玩(平台转账)
	 */
	public int PAY_PLATFORM_JUGAME_TRANS=6;
	/**
	 * 付款类型:订单支付
	 */
	public int PAY_TYPE_ORDER = 1;
	/**
	 * 付款类型:用户冲值
	 */
	public int PAY_TYPE_DEPOSIT = 2;
	/**
	 *支付方式：平台账户支付
	 */
	public int PAY_WAY_ACCOUNT = 1;
	/**
	 * 支付方式：手机卡支付
	 */
	public int PAY_WAY_PHONE = 2;
	/**
	 * 支付方式：银行卡支付
	 */
	public int PAY_WAY_BANKCARD = 3;
	/**
	 * 支付方式：信用卡支付
	 */
	public int PAY_WAY_CREDIT = 4;
	/**
	 * 支付方式: PC网银支付
	 */
	public int PAY_WAY_PCBANK = 5;
	/**
	 * 支付方式: 支付宝
	 */
	public int PAY_WAY_ALIPAY = 6;
	/**
	 * 支付方式: 银联支付
	 */
	public int PAY_WAY_UNION = 7;
	/**
	 * 支付终端类型:PC
	 */
	public String CLIENT_TYPE_PC = "PC";
	/**
	 * 支付终端类型:MOBILE
	 */
	public String CLIENT_TYPE_MOBILE = "MOBILE";
	/**
	 * 支付渠道：在线NET
	 */
	public String PAY_CHANNEL_NET = "NET";
	/**
	 * 支付渠道:非银行卡
	 */
	public String PAY_CHANNEL_NOCARD = "NOCARD";
	/**
	 * 支付渠道:WAP
	 */
	public String PAY_CHANNEL_WAP = "WAP";
	/**
	 * 支付渠道:REMIT
	 */
	public String PAY_CHANNEL_REMIT = "REMIT";
	/**
	 * 支付渠道：快捷支付
	 */
	public String PAY_CHANNEL_FASTPAY="FASTPAY";
	/**
	 * 支付渠道: 银联
	 */
	public String PAY_CHANNEL_UNION="UNION";
	/**
	 * 支付渠道: 支付宝
	 */
	public String PAY_CHANNEL_ALIPAY="ALIPAY";
	/**
	 * 支付银行卡类型：信用卡
	 */
	public String PAY_CARDTYPE_CREDIT="CREDIT";
	/**
	 * 支付银行卡类型：借记卡
	 */
	public String PAY_CARDTYPE_DEBIT="DEBIT";
	/**
	 * 冲值卡类型:SZX
	 */
	public String PAY_CARD_TYPE_SZX="SZX";
	/**
	 * 冲值卡类型:UNICOM
	 */
	public String PAY_CARD_TYPE_UNICOM="UNICOM";
	/**
	 * 冲值卡类型:TELECOM
	 */
	public String PAY_CARD_TYPE_TELECOM="TELECOM";
	
}
