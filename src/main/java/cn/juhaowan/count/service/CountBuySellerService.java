package cn.juhaowan.count.service;
import java.util.*;

import cn.juhaowan.count.vo.CountBuyData;
import cn.juhaowan.count.vo.CountDayUser;
import cn.juhaowan.count.vo.CountSellData;

public interface CountBuySellerService {
	
	/**
	 * 当日买家购买数据
	 */
	public static int  NEW_BUYER=1;
	
	/**
	 * 7日内购买数据
	 */
	public static int  DAYS7_BUYER=2;
	
	/**
	 * 15日内购买数据
	 */
	public static int  DAYS15_BUYER=3;
	
	/**
	 * 30日内购买数据
	 */
	public static int  DAYS30_BUYER=4;
	
	/**
	 * 当日卖家上架销售数据
	 */
	public static int  NEW_SELLER=5;
	
	/**
	 * 7日内卖家销售数据
	 */
	public static int  DAYS7_SELLER=6;
	
	/**
	 * 15日内卖家销售数据
	 */
	public static int  DAYS15_SELLER=7;
	
	/**
	 * 注册认证数据
	 */
	
	public static int  COUNT_USER=8;
	
	List<CountBuyData> queryCountBuy(String startTime,String endTime);
	List<CountSellData> queryCountSeller(String startTime,String endTime);
	List<CountDayUser>  queryCountUser(String startTime,String endTime);

}
