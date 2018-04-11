package cn.jugame.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.util.PageInfo;
import cn.jugame.util.RequestUtils;
import cn.juhaowan.order.vo.OfCardOrder;

@Controller
public class OfCardOrderController {

	@Autowired
	private JdbcOperations jdbcOperations;
	
	@RequestMapping(value="/order/ofCardOrderList")
	public String ofCardOrderList() {
		return "/order/ofCardOrderList";
	}
	
    @RequestMapping(value="/order/ofCardOrderList_json")
    @ResponseBody
    public JSONObject ofCardOrderListJson(HttpServletRequest request) {
    	int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 100); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "order_number"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
		int uid = RequestUtils.getParameterInt(request, "uid", -1);
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		PageInfo<OfCardOrder> pageInfo = this.findOrderWithPage(uid,pageSize, pageNo, order,sort);
		if (pageInfo != null && pageInfo.getItems() != null) {
			List<OfCardOrder> list = pageInfo.getItems();
			data.put("total", pageInfo.getRecordCount());
			for (OfCardOrder oorder:list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",oorder.getId());
				map.put("orderNum", oorder.getOrderNum());
				String pType = "";
				if (oorder.getProductType() == 1) {
					pType = "直充";
					map.put("productType", pType);
				}
				if(oorder.getProductType() == 2) {
					pType = "卡密";
					map.put("productType", pType);
				}
				
				map.put("productValue", oorder.getProductValue());
				String pIsp = "";
				if (oorder.getProductIsp() == 1) {
					pIsp = "移动";
					map.put("productIsp", pIsp);
				}
				if (oorder.getProductIsp() == 2) {
					pIsp = "联通";
					map.put("productIsp", pIsp);
				}
				if (oorder.getProductIsp() == 3) {
					pIsp = "电信";
					map.put("productIsp", pIsp);
				}
				map.put("productPrice", oorder.getProductPrice());
				map.put("uid", oorder.getUid());
				map.put("mobileNum", oorder.getMobileNum() == null ? "" : oorder.getMobileNum());
				map.put("cardName", oorder.getCardName() == null ? "": oorder.getCardName());
				map.put("cardId", oorder.getCardId() == null ? "" : oorder.getCardId());
				String cardno = oorder.getCardNo();
				if (cardno != null) {
					if (cardno.length() >= 8) {
						try {
							String formatCardNo = cardno.substring(0, 4)+"***"+cardno.substring(cardno.length()-4, cardno.length());
							map.put("cardNo", formatCardNo);
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}else {
						map.put("cardNo", cardno);
					}
				}else{
					map.put("cardNo", "");
				}
				String cardpws = oorder.getCardPws();
				if (cardpws != null) {
					if (cardpws.length() >= 8) {
						try {
							String formatCardPws = cardpws.substring(0,4)+"***"+cardpws.substring(cardpws.length()-4, cardpws.length());
							map.put("cardPws", formatCardPws);
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}else {
						map.put("cardPws", cardpws);
					}
				}else {
					map.put("cardPws", "");
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (oorder.getCardExpiredTime() != null) {
					map.put("cardExpiredTime", sdf.format(oorder.getCardExpiredTime()));
				}else {
					map.put("cardExpiredTime", "");
				}
					
				map.put("inprice", oorder.getInprice());
				map.put("cpOrder", oorder.getCpOrder() == null ? ""  : oorder.getCpOrder());
				int oStatus = oorder.getOrderStatus();
				String ostuStr="";
				if (oStatus == 1) {
					ostuStr = "新建";
				}
				if (oStatus == 11) {
					ostuStr = "支付失败-余额不足";
				}
				if (oStatus == 12) {
					ostuStr = " 支付失败-没货";
				}
				if (oStatus == 13) {
					ostuStr = " 第三方错误";
				}
				if (oStatus == 14) {
					ostuStr = " 其它错误";
				}
				if (oStatus == 21) {
					ostuStr = "已支付";
				}
				if (oStatus == 88) {
					ostuStr = " 已退费";
				}
				if (oStatus == 89) {
					ostuStr = "退费失败";
				}
				if (oStatus == 98) {
					ostuStr = "充值中";
				}
				if (oStatus == 99) {
					ostuStr = "交易成功";
				}
				map.put("orderStatus", ostuStr);
				map.put("cTime", sdf.format(oorder.getcTime()));
				map.put("uTime", sdf.format(oorder.getuTime()));
				showList.add(map);
			}
			JSONArray rows = JSONArray.fromObject(showList);
			data.put("rows", rows);
		}
		return data;
    }
    
	public PageInfo<OfCardOrder> findOrderWithPage(int uid,int pageSize,int pageNo,String order,String sort) {
		StringBuffer sql = new StringBuffer();
		List<Object> mConditionList = new ArrayList<Object>();
		if (null == sort) {
			sort = "order_number";
		}
		if (null == order) {
			order = "desc";
		}
		sql.append(" from ofcard_order where 1=1 ");
		if (uid > 0) {
			sql.append(" and uid=?");
			mConditionList.add(uid);
		}

		String sqlcount = "select count(*)  " + sql.toString();

		int count = jdbcOperations.queryForInt(sqlcount,mConditionList.toArray());
		PageInfo<OfCardOrder> pageInfo = new PageInfo<OfCardOrder>(count, pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);

		if (count == 0) {
			return pageInfo;
		}

		if (pageNo <= 0) {
			pageNo = 1;
		}

		if (pageNo > pageInfo.getPageCount()) {
			pageNo = pageInfo.getPageCount();
		}

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0) {
			firstResult = 0;
		}
		sql.append(" order by " + sort + " " + order);
		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();

		JuRowCallbackHandler<OfCardOrder> rowCallbackHandler = new JuRowCallbackHandler<OfCardOrder>(OfCardOrder.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler,mConditionList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
		
	}
}
