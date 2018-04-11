package cn.jugame.admin.web.controller;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.count.vo.CountBuyData;
import cn.juhaowan.count.vo.CountBuySeller;
import cn.juhaowan.count.vo.CountDayUser;
import cn.juhaowan.count.vo.CountSellData;

@Controller
@RequestMapping(value = "/statistics")
public class CountBuySellerDataController {
	
	@Autowired
	private JdbcOperations jdbcOperations;

	@RequestMapping(value = "/buyerDataView")
	public String buyDataView(HttpServletRequest request, Model model) {
		return "statistics/buyerDataView";
	}
	
	@RequestMapping(value = "/sellerDataView")
	public String sellerDataView(HttpServletRequest request, Model model) {
		return "statistics/sellerDataView";
	}
	
	@RequestMapping(value = "/buyDataList")
	@ResponseBody
	public String getBuyData(HttpServletRequest request,Model model) {
		String startTime=RequestUtils.getParameter(request, "fromDays", "");
		String endTime=RequestUtils.getParameter(request, "toDays", "");
		String qtype=RequestUtils.getParameter(request, "type", "");
		JSONObject data = new JSONObject();
        
		List<CountDayUser>  listUser = this.queryCountUser(startTime, endTime + " 23:59:59");
		List<CountBuyData>  listBuy = this.queryCountBuy(startTime, endTime  + " 23:59:59");
		List<CountBuySeller> list = new ArrayList<CountBuySeller>();
		
		if (listUser!=null || listBuy!=null) {
			for (int i = 0; i < listUser.size(); i++) {
				CountBuySeller cbs=new  CountBuySeller();
				cbs.setDayUser(listUser.get(i));
				
				java.util.Date date = listUser.get(i).getCountDay();
				for(int j = 0; j < listBuy.size() ; j++){
					java.util.Date date2  = listBuy.get(j).getCreateDate();
					
					if (date.getDate() == date2.getDate() && date.getMonth() == date2.getMonth()) {
						cbs.setBuyData(listBuy.get(j));
					}
				}
				list.add(cbs);
			}
		}
		
		net.sf.json.JsonConfig config = new net.sf.json.JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new cn.jugame.web.util.DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONArray rows = JSONArray.fromObject(list,config);
		data.put("rows", rows);
		return data.toString();
		
		
		//return data;
	}

	@RequestMapping(value = "/sellerDataList")
	@ResponseBody
	public String getSellerData(HttpServletRequest request,Model model) {
		String startTime = RequestUtils.getParameter(request, "fromDays", "");
		String endTime = RequestUtils.getParameter(request, "toDays", "");
		String qtype = RequestUtils.getParameter(request, "type", "");
		JSONObject data = new JSONObject();
        
		List<CountDayUser>  listUser = this.queryCountUser(startTime, endTime + " 23:59:59");
		List<CountSellData>  listSeller = this.queryCountSeller(startTime, endTime + " 23:59:59");
		List<CountBuySeller> list = new ArrayList<CountBuySeller>();
		
		if (listUser != null || listSeller != null) {
			for (int i = 0; i < listUser.size(); i++) {
				CountBuySeller cbs=new  CountBuySeller();
				cbs.setDayUser(listUser.get(i));
				
				java.util.Date date = listUser.get(i).getCountDay();
				for(int j = 0;j < listSeller.size() ; j++){
					java.util.Date date2  = listSeller.get(j).getCreateDate();
					
					if (date.getDate() == date2.getDate() && date.getMonth() == date2.getMonth() ) {
						cbs.setSellData(listSeller.get(j));
					}
				}
				list.add(cbs);
			}
		}
		
		net.sf.json.JsonConfig config = new net.sf.json.JsonConfig();
		config.registerJsonValueProcessor(java.util.Date.class, new cn.jugame.web.util.DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONArray rows = JSONArray.fromObject(list,config);
		data.put("rows", rows);
		return data.toString();
	
	}
	
	@RequestMapping(value="/exportBuyDataStatisticsExcel")
	public void doExportBuyData(HttpServletRequest request,HttpServletResponse response) {
		String startTime = RequestUtils.getParameter(request, "fromDays", "");
		String endTime = RequestUtils.getParameter(request, "toDays", "");
		String qtype = RequestUtils.getParameter(request, "type", "");
		
		String fileName = "买家购买数据"+startTime+"~"+endTime;
		List<CountDayUser>  listUser = this.queryCountUser(startTime, endTime + " 23:59:59");
		List<CountBuyData>  listbuy= this.queryCountBuy(startTime, endTime + " 23:59:59");
		List<CountBuySeller> list = new ArrayList<CountBuySeller>();

		if (listUser != null || listbuy != null) {
			for (int i = 0; i < listUser.size(); i++) {
				CountBuySeller cbs=new  CountBuySeller();
				cbs.setDayUser(listUser.get(i));
				
				java.util.Date date = listUser.get(i).getCountDay();
				for(int j = 0;j < listbuy.size() ; j++){
					java.util.Date date2  = listbuy.get(j).getCreateDate();
					
					if (date.getDate() == date2.getDate() && date.getMonth() == date2.getMonth() ) {
						cbs.setBuyData(listbuy.get(j));
					}
				}
				list.add(cbs);
			}
		}
		
		ServletOutputStream sos = null;
		if (list != null) {
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/x-download");
				response.setHeader("Content-Disposition","attachment;filename="
										+ new String((fileName + ".xls").getBytes("utf-8"), "iso-8859-1"));
				sos=response.getOutputStream();
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet(fileName);
				sheet.setDefaultColumnWidth((short)18);
				HSSFCellStyle style = book.createCellStyle();
				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				HSSFCellStyle style1 = book.createCellStyle();
				style1.setFillForegroundColor(HSSFColor.TAN.index);
				style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				HSSFCellStyle style2 = book.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				HSSFCellStyle style3 = book.createCellStyle();
				style3.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
				style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				HSSFCellStyle style4 = book.createCellStyle();
				style4.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				HSSFRow field = sheet.createRow(0);
				HSSFCell timeTH = field.createCell((short) 0);
				timeTH.setCellValue(new HSSFRichTextString("日期"));
				timeTH.setCellStyle(style);
				if (qtype.indexOf("countUser") != -1) {
					HSSFCell regNumTH = field.createCell((short) 1);
					regNumTH.setCellValue(new HSSFRichTextString("新增注册用户数"));
					regNumTH.setCellStyle(style);
					HSSFCell realNumTH = field.createCell((short) (2));
					realNumTH.setCellValue(new HSSFRichTextString("实名认证用户数"));
					realNumTH.setCellStyle(style);
					HSSFCell totalNumTH = field.createCell((short) (3));
					totalNumTH.setCellValue(new HSSFRichTextString("累计注册用户数"));
					totalNumTH.setCellStyle(style);
					HSSFCell realTotalTH = field.createCell((short) (4));
					realTotalTH.setCellValue(new HSSFRichTextString("累计实名认证总数"));
					realTotalTH.setCellStyle(style);
				}
				
				if (qtype.indexOf("newBuyer") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell newsBuyerDayTH = field.createCell((short) (cellnum+1));
					newsBuyerDayTH.setCellValue(new HSSFRichTextString("当日新增买家数"));
					newsBuyerDayTH.setCellStyle(style1);
					HSSFCell buyerTotalTH = field.createCell((short) (cellnum+2));
					buyerTotalTH.setCellValue(new HSSFRichTextString("当日买家总数"));
					buyerTotalTH.setCellStyle(style1);
					HSSFCell isOrderTotalTH = field.createCell((short) (cellnum+3));
					isOrderTotalTH.setCellValue(new HSSFRichTextString("当日有下单人数"));
					isOrderTotalTH.setCellStyle(style1);
					HSSFCell buyerAccountingTH = field.createCell((short) (cellnum+4));
					buyerAccountingTH.setCellValue(new HSSFRichTextString("新增买家占比"));
					buyerAccountingTH.setCellStyle(style1);
					HSSFCell newsbuyerAvgItemsTH = field.createCell((short) (cellnum+5));
					newsbuyerAvgItemsTH.setCellValue(new HSSFRichTextString("当日新增买家人均购买笔数"));
					newsbuyerAvgItemsTH.setCellStyle(style1);
					HSSFCell buyerAvgItemsTH = field.createCell((short) (cellnum+6));
					buyerAvgItemsTH.setCellValue(new HSSFRichTextString("当日人均购买笔数"));
					buyerAvgItemsTH.setCellStyle(style1);
					HSSFCell newsBuyerAvgConsumptionTH = field.createCell((short) (cellnum+7));
					newsBuyerAvgConsumptionTH.setCellValue(new HSSFRichTextString("当日新增买家人均消费额"));
					newsBuyerAvgConsumptionTH.setCellStyle(style1);
					HSSFCell buyerAvgConsumptionTH = field.createCell((short) (cellnum+8));
					buyerAvgConsumptionTH.setCellValue(new HSSFRichTextString("当日人均消费额"));
					buyerAvgConsumptionTH.setCellStyle(style1);
				}
				if (qtype.indexOf("7buyData") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell zeroItems7TH = field.createCell((short) (cellnum+1));
					zeroItems7TH.setCellValue(new HSSFRichTextString("7日内无购买人数"));
					zeroItems7TH.setCellStyle(style2);
					HSSFCell aitems7TH = field.createCell((short) (cellnum+2));
					aitems7TH.setCellValue(new HSSFRichTextString("7日内购买1单人数"));
					aitems7TH.setCellStyle(style2);
					HSSFCell twoItems7TH = field.createCell((short) (cellnum+3));
					twoItems7TH.setCellValue(new HSSFRichTextString("7日内购买2单人数"));
					twoItems7TH.setCellStyle(style2);
					HSSFCell threeToFiveItems7TH = field.createCell((short) (cellnum+4));
					threeToFiveItems7TH.setCellValue(new HSSFRichTextString("7日内购买3~5单人数"));
					threeToFiveItems7TH.setCellStyle(style2);
					HSSFCell largeFiveItems7TH = field.createCell((short) (cellnum+5));
					largeFiveItems7TH.setCellValue(new HSSFRichTextString("7日内购买>5单人数"));
					largeFiveItems7TH.setCellStyle(style2);
					HSSFCell isOrderTotal7TH = field.createCell((short) (cellnum+6));
					isOrderTotal7TH.setCellValue(new HSSFRichTextString("7日内有下单人数"));
					isOrderTotal7TH.setCellStyle(style2);
					HSSFCell buyRate7TH = field.createCell((short) (cellnum+7));
					buyRate7TH.setCellValue(new HSSFRichTextString("7日内购买率"));
					buyRate7TH.setCellStyle(style2);
					HSSFCell repeatBuyRate7TH = field.createCell((short) (cellnum+8));
					repeatBuyRate7TH.setCellValue(new HSSFRichTextString("7日内重复购买率"));
					repeatBuyRate7TH.setCellStyle(style2);
					HSSFCell repeatBuyAvgItems7TH = field.createCell((short) (cellnum+9));
					repeatBuyAvgItems7TH.setCellValue(new HSSFRichTextString("7日内重复购买人均购买比数"));
					repeatBuyAvgItems7TH.setCellStyle(style2);
					HSSFCell repeatBuyAvgCon7TH = field.createCell((short) (cellnum+10));
					repeatBuyAvgCon7TH.setCellValue(new HSSFRichTextString("7日内重复购买人均消费额"));
					repeatBuyAvgCon7TH.setCellStyle(style2);
				}
				if (qtype.indexOf("15buyData") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell zeroItems15TH = field.createCell((short) (cellnum+1));
					zeroItems15TH.setCellValue(new HSSFRichTextString("15日内无购买人数"));
					zeroItems15TH.setCellStyle(style3);
					HSSFCell aitems15TH = field.createCell((short) (cellnum+2));
					aitems15TH.setCellValue(new HSSFRichTextString("15日内购买人数（1单）"));
					aitems15TH.setCellStyle(style3);
					HSSFCell twoItems15TH = field.createCell((short) (cellnum+3));
					twoItems15TH.setCellValue(new HSSFRichTextString("15日内购买人数（2~5单）"));
					twoItems15TH.setCellStyle(style3);
					HSSFCell sixToTenItems15TH = field.createCell((short) (cellnum+4));
					sixToTenItems15TH.setCellValue(new HSSFRichTextString("15日内购买人数（6~10单）"));
					sixToTenItems15TH.setCellStyle(style3);
					HSSFCell largeTenItems15TH = field.createCell((short) (cellnum+5));
					largeTenItems15TH.setCellValue(new HSSFRichTextString("15日内购买人数（>10单）"));
					largeTenItems15TH.setCellStyle(style3);
					HSSFCell isOrderTotal15TH = field.createCell((short) (cellnum+6));
					isOrderTotal15TH.setCellValue(new HSSFRichTextString("15日内有下单人数"));
					isOrderTotal15TH.setCellStyle(style3);
					HSSFCell buyRate15TH = field.createCell((short) (cellnum+7));
					buyRate15TH.setCellValue(new HSSFRichTextString("15日内购买率"));
					buyRate15TH.setCellStyle(style3);
					HSSFCell repeatBuyRate15TH = field.createCell((short) (cellnum+8));
					repeatBuyRate15TH.setCellValue(new HSSFRichTextString("15日内重复购买率"));
					repeatBuyRate15TH.setCellStyle(style3);
					HSSFCell repeatBuyAvgItems15TH = field.createCell((short) (cellnum+9));
					repeatBuyAvgItems15TH.setCellValue(new HSSFRichTextString("15日内重复购买人均购买笔数"));
					repeatBuyAvgItems15TH.setCellStyle(style3);
					HSSFCell repeatBuyAvgCon15TH = field.createCell((short) (cellnum+10));
					repeatBuyAvgCon15TH.setCellValue(new HSSFRichTextString("15日内重复购买人均消费额"));
					repeatBuyAvgCon15TH.setCellStyle(style3);
				}
				if (qtype.indexOf("30BuyData") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell zeroItems30TH = field.createCell((short) (cellnum+1));
					zeroItems30TH.setCellValue(new HSSFRichTextString("30日内无购买人数"));
					zeroItems30TH.setCellStyle(style4);
					HSSFCell aitems30TH = field.createCell((short) (cellnum+2));
					aitems30TH.setCellValue(new HSSFRichTextString("30日内购买人数（1单）"));
					aitems30TH.setCellStyle(style4);
					HSSFCell twoItems30TH = field.createCell((short) (cellnum+3));
					twoItems30TH.setCellValue(new HSSFRichTextString("30日内购买人数（2~8单）"));
					twoItems30TH.setCellStyle(style4);
					HSSFCell eightToFifItems30TH = field.createCell((short) (cellnum+4));
					eightToFifItems30TH.setCellValue(new HSSFRichTextString("30日内购买人数（8~15单）"));
					eightToFifItems30TH.setCellStyle(style4);
					HSSFCell largeFifItems30TH = field.createCell((short) (cellnum+5));
					largeFifItems30TH.setCellValue(new HSSFRichTextString("30日内购买人数（>15单）"));
					largeFifItems30TH.setCellStyle(style4);
					HSSFCell isOrderTotal30TH = field.createCell((short) (cellnum+6));
					isOrderTotal30TH.setCellValue(new HSSFRichTextString("30日内有下单人数"));
					isOrderTotal30TH.setCellStyle(style4);
					HSSFCell buyRate30TH = field.createCell((short) (cellnum+7));
					buyRate30TH.setCellValue(new HSSFRichTextString("30日内购买率"));
					buyRate30TH.setCellStyle(style4);
					HSSFCell repeatBuyRate30TH = field.createCell((short) (cellnum+8));
					repeatBuyRate30TH.setCellValue(new HSSFRichTextString("30日内重复购买率"));
					repeatBuyRate30TH.setCellStyle(style4);
					HSSFCell repeatBuyAvgItems30TH = field.createCell((short) (cellnum+9));
					repeatBuyAvgItems30TH.setCellValue(new HSSFRichTextString("30日内重复购买人均购买笔数"));
					repeatBuyAvgItems30TH.setCellStyle(style4);
					HSSFCell repeatBuyAvgCon30TH = field.createCell((short) (cellnum+10));
					repeatBuyAvgCon30TH.setCellValue(new HSSFRichTextString("30日内重复购买人均消费额"));
					repeatBuyAvgCon30TH.setCellStyle(style4);
				}
				
				for (int i = 0; i < list.size(); i++) {
					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
					DecimalFormat df=new DecimalFormat("0.00%");
					if (list.get(i).getDayUser() != null) {
						SimpleDateFormat sformat=new SimpleDateFormat("yyyy-MM-dd");
						HSSFCell timeTD = row.createCell((short) 0);
						timeTD.setCellValue(sformat.format(list.get(i).getDayUser().getCountDay()));
						timeTD.setCellStyle(style);
						if (qtype.indexOf("countUser") != -1) {
							CountDayUser countUser=list.get(i).getDayUser();
							if (countUser != null) {
								HSSFCell regNumTD = row.createCell((short) 1);
								regNumTD.setCellValue(countUser.getRegNum());
								regNumTD.setCellStyle(style);
								HSSFCell realNumTD = row.createCell((short) 2);
								realNumTD.setCellValue(countUser.getRealNum());
								realNumTD.setCellStyle(style);
								HSSFCell totalNumTD = row.createCell((short) 3);
								totalNumTD.setCellValue(countUser.getTotalNum());
								totalNumTD.setCellStyle(style);
								HSSFCell realTotalTD = row.createCell((short) (4));
								realTotalTD.setCellValue(countUser.getRealTotal());
								realTotalTD.setCellStyle(style);
							}
						}
					}
					if (qtype.indexOf("newBuyer") != -1) {
						 CountBuyData buyData=list.get(i).getBuyData();
						if ( buyData != null) {
							int isOrder=buyData.getIsOrderTotal();
							short cellnum=row.getLastCellNum();
							HSSFCell newsBuyerTD = row.createCell((short) (cellnum+1));
							newsBuyerTD.setCellValue(buyData.getNewsBuyerDay());
							newsBuyerTD.setCellStyle(style1);
							HSSFCell buyerTotalTD = row.createCell((short) (cellnum+2));
							buyerTotalTD.setCellValue(buyData.getBuyerTotal());
							buyerTotalTD.setCellStyle(style1);
							HSSFCell isOrderTotalTD = row.createCell((short) (cellnum+3));
							isOrderTotalTD.setCellValue(buyData.getIsOrderTotal());
							isOrderTotalTD.setCellStyle(style1);
							HSSFCell buyerAccountingTD = row.createCell((short)(cellnum+4));
							buyerAccountingTD.setCellValue(df.format(buyData.getBuyerAccounting())+"");
							buyerAccountingTD.setCellStyle(style1);
							HSSFCell newsbuyerAvgItemsTD = row.createCell((short) (cellnum+5));
							newsbuyerAvgItemsTD.setCellValue(buyData.getNewsbuyerAvgItems());
							newsbuyerAvgItemsTD.setCellStyle(style1);
							HSSFCell buyerAvgItemsTD = row.createCell((short) (cellnum+6));
							buyerAvgItemsTD.setCellValue(buyData.getBuyerAvgItems());
							buyerAvgItemsTD.setCellStyle(style1);
							HSSFCell newsBuyerAvgConTD = row.createCell((short) (cellnum+7));
							newsBuyerAvgConTD.setCellValue(buyData.getNewsBuyerAvgConsumption());
							newsBuyerAvgConTD.setCellStyle(style1);
							HSSFCell buyerAvgConTD = row.createCell((short) (cellnum+8));
							buyerAvgConTD.setCellValue(buyData.getBuyerAvgConsumption());
							buyerAvgConTD.setCellStyle(style1);
						}else {
							short cellnum=row.getLastCellNum();
							HSSFCell newsBuyerTD = row.createCell((short) (cellnum+1));
							newsBuyerTD.setCellValue(0);
							newsBuyerTD.setCellStyle(style1);
							HSSFCell buyerTotalTD = row.createCell((short) (cellnum+2));
							buyerTotalTD.setCellValue(0);
							buyerTotalTD.setCellStyle(style1);
							HSSFCell isOrderTotalTD = row.createCell((short) (cellnum+3));
							isOrderTotalTD.setCellValue(0);
							isOrderTotalTD.setCellStyle(style1);
							HSSFCell buyerAccountingTD = row.createCell((short)(cellnum+4));
							buyerAccountingTD.setCellValue(0);
							buyerAccountingTD.setCellStyle(style1);
							HSSFCell newsbuyerAvgItemsTD = row.createCell((short) (cellnum+5));
							newsbuyerAvgItemsTD.setCellValue(0);
							newsbuyerAvgItemsTD.setCellStyle(style1);
							HSSFCell buyerAvgItemsTD = row.createCell((short) (cellnum+6));
							buyerAvgItemsTD.setCellValue(0);
							buyerAvgItemsTD.setCellStyle(style1);
							HSSFCell newsBuyerAvgConTD = row.createCell((short) (cellnum+7));
							newsBuyerAvgConTD.setCellValue(0);
							newsBuyerAvgConTD.setCellStyle(style1);
							HSSFCell buyerAvgConTD = row.createCell((short) (cellnum+8));
							buyerAvgConTD.setCellValue(0);
							buyerAvgConTD.setCellStyle(style1);
						}
						
					}
					if (qtype.indexOf("7buyData") != -1) {
						 CountBuyData buyData = list.get(i).getBuyData();
						if ( buyData != null) {
							short cellnum=row.getLastCellNum();
							HSSFCell days7ZeroTD = row.createCell((short) (cellnum+1));
							days7ZeroTD.setCellValue(buyData.getDays7ZeroItems());
							days7ZeroTD.setCellStyle(style2);
							HSSFCell days7AitemsTD = row.createCell((short) (cellnum+2));
							days7AitemsTD.setCellValue(buyData.getDays7Aitems());
							days7AitemsTD.setCellStyle(style2);
							HSSFCell days7TwoItemsTD = row.createCell((short) (cellnum+3));
							days7TwoItemsTD.setCellValue(buyData.getDays7TwoItems());
							days7TwoItemsTD.setCellStyle(style2);
							HSSFCell days7ThreeToFiveItemsTD = row.createCell((short) (cellnum+4));
							days7ThreeToFiveItemsTD.setCellValue(buyData.getDays7ThreeToFiveItems());
							days7ThreeToFiveItemsTD.setCellStyle(style2);
							HSSFCell days7LargeFiveItemsTD = row.createCell((short) (cellnum+5));
							days7LargeFiveItemsTD.setCellValue(buyData.getDays7LargeFiveItems());
							days7LargeFiveItemsTD.setCellStyle(style2);
							HSSFCell days7isOrderTotalTD = row.createCell((short) (cellnum+6));
							days7isOrderTotalTD.setCellValue(buyData.getDays7IsOrderTotal());
							days7isOrderTotalTD.setCellStyle(style2);
							HSSFCell days7BuyRateTD = row.createCell((short) (cellnum+7));
							days7BuyRateTD.setCellValue(df.format(buyData.getDays7BuyRate())+"");
							days7BuyRateTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days7RepeatBuyRateTD.setCellValue(df.format(buyData.getDays7RepeatBuyRate())+"");
							days7RepeatBuyRateTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days7RepeatBuyAvgItemsTD.setCellValue(buyData.getDays7RepeatBuyAvgItems());
							days7RepeatBuyAvgItemsTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days7RepeatBuyAvgConTD.setCellValue(buyData.getDays7RepeatBuyAvgConsumption());
							days7RepeatBuyAvgConTD.setCellStyle(style2);
						}else {
							short cellnum = row.getLastCellNum();
							HSSFCell days7ZeroTD = row.createCell((short) (cellnum+1));
							days7ZeroTD.setCellValue(0);
							days7ZeroTD.setCellStyle(style2);
							HSSFCell days7AitemsTD = row.createCell((short) (cellnum+2));
							days7AitemsTD.setCellValue(0);
							days7AitemsTD.setCellStyle(style2);
							HSSFCell days7TwoItemsTD = row.createCell((short) (cellnum+3));
							days7TwoItemsTD.setCellValue(0);
							days7TwoItemsTD.setCellStyle(style2);
							HSSFCell days7ThreeToFiveItemsTD = row.createCell((short) (cellnum+4));
							days7ThreeToFiveItemsTD.setCellValue(0);
							days7ThreeToFiveItemsTD.setCellStyle(style2);
							HSSFCell days7LargeFiveItemsTD = row.createCell((short) (cellnum+5));
							days7LargeFiveItemsTD.setCellValue(0);
							days7LargeFiveItemsTD.setCellStyle(style2);
							HSSFCell days7isOrderTotalTD = row.createCell((short) (cellnum+6));
							days7isOrderTotalTD.setCellValue(0);
							days7isOrderTotalTD.setCellStyle(style2);
							HSSFCell days7BuyRateTD = row.createCell((short) (cellnum+7));
							days7BuyRateTD.setCellValue(0);
							days7BuyRateTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days7RepeatBuyRateTD.setCellValue(0);
							days7RepeatBuyRateTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days7RepeatBuyAvgItemsTD.setCellValue(0);
							days7RepeatBuyAvgItemsTD.setCellStyle(style2);
							HSSFCell days7RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days7RepeatBuyAvgConTD.setCellValue(0);
							days7RepeatBuyAvgConTD.setCellStyle(style2);
						}
					}
					if (qtype.indexOf("15buyData") != -1) {
						 CountBuyData buyData = list.get(i).getBuyData();
						if ( buyData != null) {
							short cellnum=row.getLastCellNum();
							HSSFCell days15ZeroTD = row.createCell((short) (cellnum+1));
							days15ZeroTD.setCellValue(buyData.getDays15ZeroItems());
							days15ZeroTD.setCellStyle(style3);
							HSSFCell days15AitemsTD = row.createCell((short) (cellnum+2));
							days15AitemsTD.setCellValue(buyData.getDays15Aitems());
							days15AitemsTD.setCellStyle(style3);
							HSSFCell days15TwoItemsTD = row.createCell((short) (cellnum+3));
							days15TwoItemsTD.setCellValue(buyData.getDays15TwoToFiveItems());
							days15TwoItemsTD.setCellStyle(style3);
							HSSFCell days15SixToTenItemsTD = row.createCell((short) (cellnum+4));
							days15SixToTenItemsTD.setCellValue(buyData.getDays15SixToTenItems());
							days15SixToTenItemsTD.setCellStyle(style3);
							HSSFCell days15LargeTenItemsTD = row.createCell((short) (cellnum+5));
							days15LargeTenItemsTD.setCellValue(buyData.getDays15LargeTenItems());
							days15LargeTenItemsTD.setCellStyle(style3);
							HSSFCell days15isOrderTotalTD = row.createCell((short) (cellnum+6));
							days15isOrderTotalTD.setCellValue(buyData.getDays15isOrderTotal());
							days15isOrderTotalTD.setCellStyle(style3);
							HSSFCell days15BuyRateTD = row.createCell((short) (cellnum+7));
							days15BuyRateTD.setCellValue(df.format(buyData.getDays15BuyRate())+"");
							days15BuyRateTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days15RepeatBuyRateTD.setCellValue(df.format(buyData.getDays15RepeatBuyRate())+"");
							days15RepeatBuyRateTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days15RepeatBuyAvgItemsTD.setCellValue(buyData.getDays15RepeatBuyAvgItems());
							days15RepeatBuyAvgItemsTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days15RepeatBuyAvgConTD.setCellValue(buyData.getDays15RepeatBuyAvgConsumption());
							days15RepeatBuyAvgConTD.setCellStyle(style3);
						}else {
							short cellnum = row.getLastCellNum();
							HSSFCell days15ZeroTD = row.createCell((short) (cellnum+1));
							days15ZeroTD.setCellValue(0);
							days15ZeroTD.setCellStyle(style3);
							HSSFCell days15AitemsTD = row.createCell((short) (cellnum+2));
							days15AitemsTD.setCellValue(0);
							days15AitemsTD.setCellStyle(style3);
							HSSFCell days15TwoItemsTD = row.createCell((short) (cellnum+3));
							days15TwoItemsTD.setCellValue(0);
							days15TwoItemsTD.setCellStyle(style3);
							HSSFCell days15SixToTenItemsTD = row.createCell((short) (cellnum+4));
							days15SixToTenItemsTD.setCellValue(0);
							days15SixToTenItemsTD.setCellStyle(style3);
							HSSFCell days15LargeTenItemsTD = row.createCell((short) (cellnum+5));
							days15LargeTenItemsTD.setCellValue(0);
							days15LargeTenItemsTD.setCellStyle(style3);
							HSSFCell days15isOrderTotalTD = row.createCell((short) (cellnum+6));
							days15isOrderTotalTD.setCellValue(0);
							days15isOrderTotalTD.setCellStyle(style3);
							HSSFCell days15BuyRateTD = row.createCell((short) (cellnum+7));
							days15BuyRateTD.setCellValue(0);
							days15BuyRateTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days15RepeatBuyRateTD.setCellValue(0);
							days15RepeatBuyRateTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days15RepeatBuyAvgItemsTD.setCellValue(0);
							days15RepeatBuyAvgItemsTD.setCellStyle(style3);
							HSSFCell days15RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days15RepeatBuyAvgConTD.setCellValue(0);
							days15RepeatBuyAvgConTD.setCellStyle(style3);
						}
					}
					
					if (qtype.indexOf("30BuyData") != -1) {
						 CountBuyData buyData=list.get(i).getBuyData();
						if ( buyData != null) {
							short cellnum = row.getLastCellNum();
							HSSFCell days30ZeroTD = row.createCell((short) (cellnum+1));
							days30ZeroTD.setCellValue(buyData.getDays30ZeroItems());
							days30ZeroTD.setCellStyle(style4);
							HSSFCell days30AitemsTD = row.createCell((short) (cellnum+2));
							days30AitemsTD.setCellValue(buyData.getDays30Aitems());
							days30AitemsTD.setCellStyle(style4);
							HSSFCell days30TwoItemsTD = row.createCell((short) (cellnum+3));
							days30TwoItemsTD.setCellValue(buyData.getDays30TwoToEightItems());
							days30TwoItemsTD.setCellStyle(style4);
							HSSFCell days30EightToFifItemsTD = row.createCell((short) (cellnum+4));
							days30EightToFifItemsTD.setCellValue(buyData.getDays30EightToFifteenItems());
							days30EightToFifItemsTD.setCellStyle(style4);
							HSSFCell days30LargeFifItemsTD = row.createCell((short) (cellnum+5));
							days30LargeFifItemsTD.setCellValue(buyData.getDays30LargeFifteenItems());
							days30LargeFifItemsTD.setCellStyle(style4);
							HSSFCell days30isOrderTotalTD = row.createCell((short) (cellnum+6));
							days30isOrderTotalTD.setCellValue(buyData.getDays30isOrderTotal());
							days30isOrderTotalTD.setCellStyle(style4);
							HSSFCell days30BuyRateTD = row.createCell((short) (cellnum+7));
							days30BuyRateTD.setCellValue(df.format(buyData.getDays30BuyRate())+"");
							days30BuyRateTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days30RepeatBuyRateTD.setCellValue(df.format(buyData.getDays30RepeatBuyRate())+"");
							days30RepeatBuyRateTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days30RepeatBuyAvgItemsTD.setCellValue(buyData.getDays30RepeatBuyAvgItems());
							days30RepeatBuyAvgItemsTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days30RepeatBuyAvgConTD.setCellValue(buyData.getDays30RepeatBuyAvgConsumption());
							days30RepeatBuyAvgConTD.setCellStyle(style4);
						}else {
							short cellnum = row.getLastCellNum();
							HSSFCell days30ZeroTD = row.createCell((short) (cellnum+1));
							days30ZeroTD.setCellValue(0);
							days30ZeroTD.setCellStyle(style4);
							HSSFCell days30AitemsTD = row.createCell((short) (cellnum+2));
							days30AitemsTD.setCellValue(0);
							days30AitemsTD.setCellStyle(style4);
							HSSFCell days30TwoItemsTD = row.createCell((short) (cellnum+3));
							days30TwoItemsTD.setCellValue(0);
							days30TwoItemsTD.setCellStyle(style4);
							HSSFCell days30EightToFifItemsTD = row.createCell((short) (cellnum+4));
							days30EightToFifItemsTD.setCellValue(0);
							days30EightToFifItemsTD.setCellStyle(style4);
							HSSFCell days30LargeFifItemsTD = row.createCell((short) (cellnum+5));
							days30LargeFifItemsTD.setCellValue(0);
							days30LargeFifItemsTD.setCellStyle(style4);
							HSSFCell days30isOrderTotalTD = row.createCell((short) (cellnum+6));
							days30isOrderTotalTD.setCellValue(0);
							days30isOrderTotalTD.setCellStyle(style4);
							HSSFCell days30BuyRateTD = row.createCell((short) (cellnum+7));
							days30BuyRateTD.setCellValue(0);
							days30BuyRateTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyRateTD = row.createCell((short) (cellnum+8));
							days30RepeatBuyRateTD.setCellValue(0);
							days30RepeatBuyRateTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyAvgItemsTD = row.createCell((short) (cellnum+9));
							days30RepeatBuyAvgItemsTD.setCellValue(0);
							days30RepeatBuyAvgItemsTD.setCellStyle(style4);
							HSSFCell days30RepeatBuyAvgConTD = row.createCell((short) (cellnum+10));
							days30RepeatBuyAvgConTD.setCellValue(0);
							days30RepeatBuyAvgConTD.setCellStyle(style4);
						}
					}
				}
				book.write(sos);
				sos.flush();
			} catch (java.io.UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}catch (IOException e2) {
				e2.printStackTrace();
			}finally {
				try {
					sos.close();
				} catch ( IOException e3) {
					e3.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 导出卖家上架销售数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/exportSellerDataStatisticsExcel")
	public void doExportSellerData(HttpServletRequest request, HttpServletResponse response) {
		String startTime = RequestUtils.getParameter(request, "fromDays", "");
		String endTime = RequestUtils.getParameter(request, "toDays", "");
		String qtype = RequestUtils.getParameter(request, "type", "");
		
		String fileNames = "卖家销售数据"+startTime+"~"+endTime;
		List<CountDayUser>  listUser = this.queryCountUser(startTime, endTime + " 23:59:59");
		List<CountSellData>  listSeller = this.queryCountSeller(startTime, endTime + " 23:59:59");
		List<CountBuySeller> list = new ArrayList<CountBuySeller>();
		
		if (listUser != null || listSeller != null) {
			for (int i = 0; i < listUser.size(); i++) {
				CountBuySeller cbs=new  CountBuySeller();
				cbs.setDayUser(listUser.get(i));
				
				java.util.Date date = listUser.get(i).getCountDay();
				for(int j = 0;j < listSeller.size() ; j++){
					java.util.Date date2  = listSeller.get(j).getCreateDate();
					
					if (date.getDate() == date2.getDate() && date.getMonth() == date2.getMonth() ) {
						cbs.setSellData(listSeller.get(j));
					}
				}
				list.add(cbs);
			}
		}
		
		ServletOutputStream os = null;
		if (list != null) {
			try {
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/x-download");
				response.setHeader("Content-Disposition","attachment;filename="
						+ new String((fileNames + ".xls").getBytes("utf-8"), "iso-8859-1"));
				os=response.getOutputStream();
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet(fileNames);
				sheet.setDefaultColumnWidth((short)18);
				HSSFCellStyle style = book.createCellStyle();
				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				
				HSSFCellStyle style1 = book.createCellStyle();
				style1.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				
				HSSFCellStyle style2 = book.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

				
				HSSFCellStyle style3 = book.createCellStyle();
				style3.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
				style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

				
				HSSFRow field = sheet.createRow(0);
				HSSFCell timeTH = field.createCell((short) 0);
				timeTH.setCellValue(new HSSFRichTextString("日期"));
				timeTH.setCellStyle(style);
				if (qtype.indexOf("countUser") != -1) {
					HSSFCell regNumTH = field.createCell((short) 1);
					regNumTH.setCellValue(new HSSFRichTextString("新增注册用户数"));
					regNumTH.setCellStyle(style);
					HSSFCell realNumTH = field.createCell((short) (2));
					realNumTH.setCellValue(new HSSFRichTextString("实名认证用户数"));
					realNumTH.setCellStyle(style);
					HSSFCell totalNumTH = field.createCell((short) (3));
					totalNumTH.setCellValue(new HSSFRichTextString("累计注册用户数"));
					totalNumTH.setCellStyle(style);
					HSSFCell realTotalTH = field.createCell((short) (4));
					realTotalTH.setCellValue(new HSSFRichTextString("累计实名认证总数"));
					realTotalTH.setCellStyle(style);
				}
				if (qtype.indexOf("newBuyer") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell newsSellerDayTH = field.createCell((short) (cellnum+1));
					newsSellerDayTH.setCellValue(new HSSFRichTextString("当日新增卖家数"));
					newsSellerDayTH.setCellStyle(style1);
					HSSFCell newsSellerTH = field.createCell((short) (cellnum+2));
					newsSellerTH.setCellValue(new HSSFRichTextString("当日新增卖家数（有销售）"));
					newsSellerTH.setCellStyle(style1);
//					HSSFCell onSellerProTotalTH = field.createCell((short) (cellnum+3));
//					onSellerProTotalTH.setCellValue(new HSSFRichTextString("当日新增卖家上架商品件数"));
//					onSellerProTotalTH.setCellStyle(style1);
//					HSSFCell avgOnSellerProTH = field.createCell((short) (cellnum+4));
//					avgOnSellerProTH.setCellValue(new HSSFRichTextString("当日新增卖家人均上架商品件数"));
//					avgOnSellerProTH.setCellStyle(style1);
//					HSSFCell onSellerProAmountTH = field.createCell((short) (cellnum+5));
//					onSellerProAmountTH.setCellValue(new HSSFRichTextString("当日新增卖家上架商品金额"));
//					onSellerProAmountTH.setCellStyle(style1);
//					HSSFCell avgOnSellerProAmountTH = field.createCell((short) (cellnum+6));
//					avgOnSellerProAmountTH.setCellValue(new HSSFRichTextString("当日新增卖家人均上架商品金额"));
//					avgOnSellerProAmountTH.setCellStyle(style1);
					HSSFCell sellProTotalTH = field.createCell((short) (cellnum+3));
					sellProTotalTH.setCellValue(new HSSFRichTextString("当日新增卖家销售商品件数"));
					sellProTotalTH.setCellStyle(style1);
					HSSFCell avgSellProTotalTH = field.createCell((short) (cellnum+4));
					avgSellProTotalTH.setCellValue(new HSSFRichTextString("当日新增卖家人均销售商品件数"));
					avgSellProTotalTH.setCellStyle(style1);
					HSSFCell sellProAmountTH = field.createCell((short) (cellnum+5));
					sellProAmountTH.setCellValue(new HSSFRichTextString("当日新增卖家销售商品金额"));
					sellProAmountTH.setCellStyle(style1);
					HSSFCell avgSellAmountTH = field.createCell((short) (cellnum+6));
					avgSellAmountTH.setCellValue(new HSSFRichTextString("当日新增卖家人均销售商品金额"));
					avgSellAmountTH.setCellStyle(style1);
				}
				
				if (qtype.indexOf("7buyData") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell d7zeroTH = field.createCell((short) (cellnum+1));
					d7zeroTH.setCellValue(new HSSFRichTextString("7日内无销售记录人数"));
					d7zeroTH.setCellStyle(style2);
					HSSFCell d7aItemTH = field.createCell((short) (cellnum+2));
					d7aItemTH.setCellValue(new HSSFRichTextString("7日内有销售记录人数（1单）"));
					d7aItemTH.setCellStyle(style2);
					HSSFCell d7TwoToFiveTH = field.createCell((short) (cellnum+3));
					d7TwoToFiveTH.setCellValue(new HSSFRichTextString("7日内有销售记录人数（2~5单）"));
					d7TwoToFiveTH.setCellStyle(style2);
					HSSFCell d7SixToTenTH = field.createCell((short) (cellnum+4));
					d7SixToTenTH.setCellValue(new HSSFRichTextString("7日内有销售记录人数（6~10单）"));
					d7SixToTenTH.setCellStyle(style2);
					HSSFCell d7largeTenTH = field.createCell((short) (cellnum+5));
					d7largeTenTH.setCellValue(new HSSFRichTextString("7日内有销售记录人数（>10单）"));
					d7largeTenTH.setCellStyle(style2);
					HSSFCell d7SaleRateTH = field.createCell((short) (cellnum+6));
					d7SaleRateTH.setCellValue(new HSSFRichTextString("7日内有销售记录人数占比"));
					d7SaleRateTH.setCellStyle(style2);
					HSSFCell d7avgSellItemTH = field.createCell((short) (cellnum+7));
					d7avgSellItemTH.setCellValue(new HSSFRichTextString("7日内人均销售笔数"));
					d7avgSellItemTH.setCellStyle(style2);
					HSSFCell d7avgSellAmountTH = field.createCell((short) (cellnum+8));
					d7avgSellAmountTH.setCellValue(new HSSFRichTextString("7日内人均销售金额"));
					d7avgSellAmountTH.setCellStyle(style2);
				}
				
				if (qtype.indexOf("15buyData") != -1) {
					short cellnum=field.getLastCellNum();
					HSSFCell d15zeroTH = field.createCell((short) (cellnum+1));
					d15zeroTH.setCellValue(new HSSFRichTextString("15日内无销售记录人数"));
					d15zeroTH.setCellStyle(style3);
					HSSFCell d15aItemTH = field.createCell((short) (cellnum+2));
					d15aItemTH.setCellValue(new HSSFRichTextString("15日内有销售记录人数（1单）"));
					d15aItemTH.setCellStyle(style3);
					HSSFCell d15twoToFiveTH = field.createCell((short) (cellnum+3));
					d15twoToFiveTH.setCellValue(new HSSFRichTextString("15日内有销售记录人数（2~10单）"));
					d15twoToFiveTH.setCellStyle(style3);
					HSSFCell d15eleToTwentyTH = field.createCell((short) (cellnum+4));
					d15eleToTwentyTH.setCellValue(new HSSFRichTextString("15日内有销售记录人数（11~20单）"));
					d15eleToTwentyTH.setCellStyle(style3);
					HSSFCell d15largeTwenTH = field.createCell((short) (cellnum+5));
					d15largeTwenTH.setCellValue(new HSSFRichTextString("15日内有销售记录人数（>20单）"));
					d15largeTwenTH.setCellStyle(style3);
					HSSFCell d15sellRateTH = field.createCell((short) (cellnum+6));
					d15sellRateTH.setCellValue(new HSSFRichTextString("15日内有销售记录人数占比"));
					d15sellRateTH.setCellStyle(style3);
					HSSFCell d15avgSellItemTH = field.createCell((short) (cellnum+7));
					d15avgSellItemTH.setCellValue(new HSSFRichTextString("15日内人均销售笔数"));
					d15avgSellItemTH.setCellStyle(style3);
					HSSFCell d15avgSellAmountTH = field.createCell((short) (cellnum+8));
					d15avgSellAmountTH.setCellValue(new HSSFRichTextString("15日内人均销售金额"));
					d15avgSellAmountTH.setCellStyle(style3);
					
				}
				for (int i = 0; i < list.size(); i++) {
					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
					DecimalFormat dformat=new DecimalFormat("0.00%");
					SimpleDateFormat sformat=new SimpleDateFormat("yyyy-MM-dd");
					HSSFCell timeTD = row.createCell((short) 0);
					timeTD.setCellValue(sformat.format(list.get(i).getDayUser().getCountDay()));
					timeTD.setCellStyle(style);
					if (qtype.indexOf("countUser") != -1) {
						CountDayUser countUser=list.get(i).getDayUser();
						if (countUser != null) {
							HSSFCell regNumTD = row.createCell((short) 1);
							regNumTD.setCellValue(countUser.getRegNum());
							regNumTD.setCellStyle(style);
							HSSFCell realNumTD = row.createCell((short) 2);
							realNumTD.setCellValue(countUser.getRealNum());
							realNumTD.setCellStyle(style);
							HSSFCell totalNumTD = row.createCell((short) 3);
							totalNumTD.setCellValue(countUser.getTotalNum());
							totalNumTD.setCellStyle(style);
							HSSFCell realTotalTD = row.createCell((short) (4));
							realTotalTD.setCellValue(countUser.getRealTotal());
							realTotalTD.setCellStyle(style);
						}else {
							HSSFCell regNumTD = row.createCell((short) 1);
							regNumTD.setCellValue(0);
							regNumTD.setCellStyle(style);
							HSSFCell realNumTD = row.createCell((short) 2);
							realNumTD.setCellValue(0);
							realNumTD.setCellStyle(style);
							HSSFCell totalNumTD = row.createCell((short) 3);
							totalNumTD.setCellValue(0);
							totalNumTD.setCellStyle(style);
							HSSFCell realTotalTD = row.createCell((short) (4));
							realTotalTD.setCellValue(0);
							realTotalTD.setCellStyle(style);
						}
					}
					if (qtype.indexOf("newBuyer") != -1) {
						CountSellData sellerData=list.get(i).getSellData();
						if (sellerData != null) {
							short cellnum=row.getLastCellNum();
							HSSFCell newsSellerDTH = row.createCell((short) (cellnum+1));
							newsSellerDTH.setCellValue(sellerData.getNewsSalerDayCount());
							newsSellerDTH.setCellStyle(style1);
							HSSFCell newsSellCountTD = row.createCell((short) (cellnum+2));
							newsSellCountTD.setCellValue(sellerData.getNewsSalerSellCount());
							newsSellCountTD.setCellStyle(style1);
//							HSSFCell onProTotalTD = row.createCell((short) (cellnum+3));
//							onProTotalTD.setCellValue(sellerData.getOnProductTotal());
//							onProTotalTD.setCellStyle(style1);
//							HSSFCell avgOnSaleProTD = row.createCell((short) (cellnum+4));
//							avgOnSaleProTD.setCellValue(sellerData.getAvgOnSaleProduct());
//							avgOnSaleProTD.setCellStyle(style1);
//							HSSFCell onSaleProAmountTD = row.createCell((short) (cellnum+5));
//							onSaleProAmountTD.setCellValue(sellerData.getOnSaleProAmount());
//							onSaleProAmountTD.setCellStyle(style1);
//							HSSFCell avgOnSaleProAmountTD = row.createCell((short) (cellnum+6));
//							avgOnSaleProAmountTD.setCellValue(sellerData.getAvgOnSaleProAmount());
//							avgOnSaleProAmountTD.setCellStyle(style1);
							HSSFCell sellProTotalTD = row.createCell((short) (cellnum+3));
							sellProTotalTD.setCellValue(sellerData.getSellProTotal());
							sellProTotalTD.setCellStyle(style1);
							HSSFCell avgSellProTotalTD = row.createCell((short) (cellnum+4));
							avgSellProTotalTD.setCellValue(sellerData.getAvgSellProTotal());
							avgSellProTotalTD.setCellStyle(style1);
							HSSFCell sellProAmountTD = row.createCell((short) (cellnum+5));
							sellProAmountTD.setCellValue(sellerData.getSellProAmount());
							sellProAmountTD.setCellStyle(style1);
							HSSFCell avgSellProAmountTD = row.createCell((short) (cellnum+6));
							avgSellProAmountTD.setCellValue(sellerData.getAvgSellProAmount());
							avgSellProAmountTD.setCellStyle(style1);
						}else {
							short cellnum=row.getLastCellNum();
							HSSFCell newsSellerDTH = row.createCell((short) (cellnum+1));
							newsSellerDTH.setCellValue(0);
							newsSellerDTH.setCellStyle(style1);
							HSSFCell newsSellCountTD = row.createCell((short) (cellnum+2));
							newsSellCountTD.setCellValue(0);
							newsSellCountTD.setCellStyle(style1);
//							HSSFCell onProTotalTD = row.createCell((short) (cellnum+3));
//							onProTotalTD.setCellValue(0);
//							onProTotalTD.setCellStyle(style1);
//							HSSFCell avgOnSaleProTD = row.createCell((short) (cellnum+4));
//							avgOnSaleProTD.setCellValue(0);
//							avgOnSaleProTD.setCellStyle(style1);
//							HSSFCell onSaleProAmountTD = row.createCell((short) (cellnum+5));
//							onSaleProAmountTD.setCellValue(0);
//							onSaleProAmountTD.setCellStyle(style1);
//							HSSFCell avgOnSaleProAmountTD = row.createCell((short) (cellnum+6));
//							avgOnSaleProAmountTD.setCellValue(0);
//							avgOnSaleProAmountTD.setCellStyle(style1);
							HSSFCell sellProTotalTD = row.createCell((short) (cellnum+3));
							sellProTotalTD.setCellValue(0);
							sellProTotalTD.setCellStyle(style1);
							HSSFCell avgSellProTotalTD = row.createCell((short) (cellnum+4));
							avgSellProTotalTD.setCellValue(0);
							avgSellProTotalTD.setCellStyle(style1);
							HSSFCell sellProAmountTD = row.createCell((short) (cellnum+5));
							sellProAmountTD.setCellValue(0);
							sellProAmountTD.setCellStyle(style1);
							HSSFCell avgSellProAmountTD = row.createCell((short) (cellnum+6));
							avgSellProAmountTD.setCellValue(0);
							avgSellProAmountTD.setCellStyle(style1);
						}
					}
					if (qtype.indexOf("7buyData") != -1) {
						CountSellData sellerData = list.get(i).getSellData();
						if (sellerData != null) {
							short cellnum=row.getLastCellNum();
							HSSFCell d7zeroSellDTH = row.createCell((short) (cellnum+1));
							d7zeroSellDTH.setCellValue(sellerData.getD7ZeroSellItems());
							d7zeroSellDTH.setCellStyle(style2);
							HSSFCell d7aSellDTH = row.createCell((short) (cellnum+2));
							d7aSellDTH.setCellValue(sellerData.getD7AsellItem());
							d7aSellDTH.setCellStyle(style2);
							
							HSSFCell d7twoToFiveSTH = row.createCell((short) (cellnum+3));
							d7twoToFiveSTH.setCellValue(sellerData.getD7TwoToFiveSellItems());
							d7twoToFiveSTH.setCellStyle(style2);
							HSSFCell d7sixToTenSTH = row.createCell((short) (cellnum+4));
							d7sixToTenSTH.setCellValue(sellerData.getD7sixToTenSellItems());
							d7sixToTenSTH.setCellStyle(style2);
							HSSFCell d7largeTenSTH = row.createCell((short) (cellnum+5));
							d7largeTenSTH.setCellValue(sellerData.getD7LargeTenSellItems());
							d7largeTenSTH.setCellStyle(style2);
							HSSFCell d7sellRateTH = row.createCell((short) (cellnum+6));
							d7sellRateTH.setCellValue(dformat.format(sellerData.getD7SellRate())+"");
							d7sellRateTH.setCellStyle(style2);
							HSSFCell d7avgSellItemTH = row.createCell((short) (cellnum+7));
							d7avgSellItemTH.setCellValue(sellerData.getD7AvgSellItems());
							d7avgSellItemTH.setCellStyle(style2);
							HSSFCell d7avgSellAmountTH = row.createCell((short) (cellnum+8));
							d7avgSellAmountTH.setCellValue(sellerData.getD7AvgSellAmount());
							d7avgSellAmountTH.setCellStyle(style2);
						}else {
							short cellnum=row.getLastCellNum();
							HSSFCell d7zeroSellDTH = row.createCell((short) (cellnum+1));
							d7zeroSellDTH.setCellValue(0);
							d7zeroSellDTH.setCellStyle(style2);
							HSSFCell d7aSellDTH = row.createCell((short) (cellnum+2));
							d7aSellDTH.setCellValue(0);
							d7aSellDTH.setCellStyle(style2);
							
							HSSFCell d7twoToFiveSTH = row.createCell((short) (cellnum+3));
							d7twoToFiveSTH.setCellValue(0);
							d7twoToFiveSTH.setCellStyle(style2);
							HSSFCell d7sixToTenSTH = row.createCell((short) (cellnum+4));
							d7sixToTenSTH.setCellValue(0);
							d7sixToTenSTH.setCellStyle(style2);
							HSSFCell d7largeTenSTH = row.createCell((short) (cellnum+5));
							d7largeTenSTH.setCellValue(0);
							d7largeTenSTH.setCellStyle(style2);
							HSSFCell d7sellRateTH = row.createCell((short) (cellnum+6));
							d7sellRateTH.setCellValue(0);
							d7sellRateTH.setCellStyle(style2);
							HSSFCell d7avgSellItemTH = row.createCell((short) (cellnum+7));
							d7avgSellItemTH.setCellValue(0);
							d7avgSellItemTH.setCellStyle(style2);
							HSSFCell d7avgSellAmountTH = row.createCell((short) (cellnum+8));
							d7avgSellAmountTH.setCellValue(0);
							d7avgSellAmountTH.setCellStyle(style2);
						}
					}
					if (qtype.indexOf("15buyData") != -1) {
						CountSellData sellData=list.get(i).getSellData();
						if (sellData != null) {
							short cellnum=row.getLastCellNum();
							HSSFCell d15zeroSellDTH = row.createCell((short) (cellnum+1));
							d15zeroSellDTH.setCellValue(sellData.getD15ZeroSellItems());
							d15zeroSellDTH.setCellStyle(style3);
							HSSFCell d15aitemDTH = row.createCell((short) (cellnum+2));
							d15aitemDTH.setCellValue(sellData.getD15Aselltems());
							d15aitemDTH.setCellStyle(style3);
							HSSFCell d15twoToTenTH = row.createCell((short) (cellnum+3));
							d15twoToTenTH.setCellValue(sellData.getD15TwoToTenSellItems());
							d15twoToTenTH.setCellStyle(style3);
							HSSFCell d15eleToTwenTH = row.createCell((short) (cellnum+4));
							d15eleToTwenTH.setCellValue(sellData.getD15EleToTwentySellItems());
							d15eleToTwenTH.setCellStyle(style3);
							HSSFCell d15largeTwenTH = row.createCell((short) (cellnum+5));
							d15largeTwenTH.setCellValue(sellData.getD15LargeTwentySellItems());
							d15largeTwenTH.setCellStyle(style3);
							
							HSSFCell d15sellRateTH = row.createCell((short) (cellnum+6));
							d15sellRateTH.setCellValue(dformat.format(sellData.getD15SellRate())+"");
							d15sellRateTH.setCellStyle(style3);
							HSSFCell d15avgSellItemTH = row.createCell((short) (cellnum+7));
							d15avgSellItemTH.setCellValue(sellData.getD15AvgSellItems());
							d15avgSellItemTH.setCellStyle(style3);
							HSSFCell d15avgSellAmountTH = row.createCell((short) (cellnum+8));
							d15avgSellAmountTH.setCellValue(sellData.getD15AvgSellAmount());
							d15avgSellAmountTH.setCellStyle(style3);
						}else {
							short cellnum=row.getLastCellNum();
							HSSFCell d15zeroSellDTH = row.createCell((short) (cellnum+1));
							d15zeroSellDTH.setCellValue(0);
							d15zeroSellDTH.setCellStyle(style3);
							HSSFCell d15aitemDTH = row.createCell((short) (cellnum+2));
							d15aitemDTH.setCellValue(0);
							d15aitemDTH.setCellStyle(style3);
							HSSFCell d15twoToTenTH = row.createCell((short) (cellnum+3));
							d15twoToTenTH.setCellValue(0);
							d15twoToTenTH.setCellStyle(style3);
							HSSFCell d15eleToTwenTH = row.createCell((short) (cellnum+4));
							d15eleToTwenTH.setCellValue(0);
							d15eleToTwenTH.setCellStyle(style3);
							HSSFCell d15largeTwenTH = row.createCell((short) (cellnum+5));
							d15largeTwenTH.setCellValue(0);
							d15largeTwenTH.setCellStyle(style3);
							
							HSSFCell d15sellRateTH = row.createCell((short) (cellnum+6));
							d15sellRateTH.setCellValue(0);
							d15sellRateTH.setCellStyle(style3);
							HSSFCell d15avgSellItemTH = row.createCell((short) (cellnum+7));
							d15avgSellItemTH.setCellValue(0);
							d15avgSellItemTH.setCellStyle(style3);
							HSSFCell d15avgSellAmountTH = row.createCell((short) (cellnum+8));
							d15avgSellAmountTH.setCellValue(0);
							d15avgSellAmountTH.setCellStyle(style3);
						}
					}
				}
				book.write(os);
				os.flush();
			} catch (java.io.UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}catch ( IOException e2) {
				e2.printStackTrace();
			}finally {
				try {
					os.close();
				} catch ( IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查询买家购买数据
	 * @param startTime
	 * @param endTime
	 * @return 买家购买数据
	 */
	public List<CountBuyData> queryCountBuy(String startTime,String endTime) {
        StringBuffer sb=new StringBuffer("select * from count_buy_data ");
		if (startTime!=null && endTime!=null) {
				sb.append(" where create_date between ? and ?");
			}
//		if() {
//			sb.append("and ?");
//		}
		sb.append("order by create_date desc");
		JuRowCallbackHandler<CountBuyData> cb = new JuRowCallbackHandler<CountBuyData>(CountBuyData.class);	
		jdbcOperations.query(sb.toString(), cb, startTime,endTime);
			
		List<CountBuyData> buyList=cb.getList();
		if (buyList!=null) {
			return buyList;
			}
		
        return null;
}
	/**
	 * 查询卖家上架销售数据
	 * @param startTime
	 * @param endTime
	 * @return 卖家上架销售数据
	 */
	
	public List<CountSellData> queryCountSeller(String startTime, String endTime) {
		StringBuffer sb=new StringBuffer("select * from count_sell_data ");
		if (startTime!=null) {
		sb.append(" where create_date between ?");
		}
		if(endTime!=null) {
			sb.append("and ?");
		}
		sb.append("order by create_date desc");
		JuRowCallbackHandler<CountSellData> cb = new JuRowCallbackHandler<CountSellData>(CountSellData.class);	
		jdbcOperations.query(sb.toString(), cb, startTime,endTime);
	
		List<CountSellData> sellerList=cb.getList();
		if (sellerList!=null) {
			return sellerList;
		}	
		return null;
	}


	/**
	 * 查询注册认证数据
	 * @param startTime
	 * @param endTime
	 * @return  注册认证数据列表
	 */
	
	public List<CountDayUser> queryCountUser(String startTime, String endTime) {
		StringBuffer sb=new StringBuffer("select * from count_day_user ");
		
		if (startTime != null) {
			sb.append(" where count_day between ? ");
		}
		if(endTime != null) {
			sb.append("and ? ");
		}
		sb.append("order by count_day desc");
		JuRowCallbackHandler<CountDayUser> cb = new JuRowCallbackHandler<CountDayUser>(CountDayUser.class);	
		jdbcOperations.query(sb.toString(), cb, startTime,endTime);
	
		List<CountDayUser> userList=cb.getList();
		if (userList!=null) {
			return userList;
		}
		return null;
		}
	}
