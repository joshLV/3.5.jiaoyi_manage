package cn.jugame.web.util;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import cn.jugame.pay.http.api.PayApiWrapper;
import cn.jugame.util.helper.misc.ChecksumHelper;

public class PayHttpApiUtil {
	public static Logger logger = LoggerFactory.getLogger(PayHttpApiUtil.class);
	private static PayApiWrapper w = new PayApiWrapper("pay-api.properties");
	/**
	 * 更新支付单信息
	 * @param orderId
	 * @param createTime
	 * @param expireTime
	 * @param totalAmount
	 * @param goodsName
	 * @param unitPrice
	 * @param goodsCount
	 * @param goodsArea
	 * @param buyerUid
	 * @param sellerUid
	 * @param remark
	 * @return
	 */
	public static int updateZhifuOrderInfo(String orderId, String createTime, String expireTime,
			String totalAmount, String goodsName, String unitPrice, String goodsCount,
			String goodsArea, String buyerUid, String sellerUid, String remark) {
		String jsonStr = w.updateZhifuOrderInfo(orderId, createTime, expireTime, totalAmount, goodsName, 
				unitPrice, goodsCount, goodsArea, buyerUid, sellerUid, remark);
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		if(jsonObject.isNullObject()) {
			logger.error("json 对象为null. jsonStr:" + jsonStr);
			return 1;
		}
		if(jsonObject.getInt("code") == 0) {
			logger.info("updateZhifuOrderInfo成功. order_id:" + orderId);
			return 0;
		}
		logger.error("updateZhifuOrderInfo失败. order_id:" + orderId
					+ ", code:" + jsonObject.getString("code")
					+ ", msg:" + jsonObject.getString("msg"));
		
		return 1;
	}	
	/**
	 * 根据订单号查询
	 * @param orderId
	 * @return
	 */
	public static  String queryZhifuOrderInfo(String orderId) {
		String jsonStr = w.queryZhifuOrderInfo(orderId);
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		if(jsonObject != null){
			logger.info("queryZhifuOrderInfo成功. order_id:" + orderId + "详细信息为:" + jsonObject.toString());
			return jsonObject.toString();
		}
		logger.info("根据订单号:" + orderId + "查询订单信息失败");
		return null;
	}
}
