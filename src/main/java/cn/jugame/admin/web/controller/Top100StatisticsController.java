package cn.jugame.admin.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.count.service.StatisticsTop100Service;
import cn.juhaowan.count.vo.StatisticsTop100;

/**
 * 消费/销售Top100榜
 * 
 * @author APXer
 * 
 */
@Controller
@RequestMapping(value = "/statistics")
public class Top100StatisticsController {
	@Autowired
	private StatisticsTop100Service top100Service;

	@RequestMapping(value = "/consumptionTOP100View")
	public String consumptionTOP100View(HttpServletRequest request, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fiveDaysAgo = new Date().getTime() - 253800000;
		model.addAttribute("fromDays", sdf.format(new Date(fiveDaysAgo)));
		return "statistics/consumptionTOP100View";
	}

	@RequestMapping(value = "/salesTOP100View")
	public String salesTOP100View(HttpServletRequest request, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fiveDaysAgo = new Date().getTime() - 253800000;
		model.addAttribute("fromDays", sdf.format(new Date(fiveDaysAgo)));
		return "statistics/salesTOP100View";
	}

	@RequestMapping(value = "/consumptionTOP100List")
	@ResponseBody
	public JSONObject consumptionTOP100List(HttpServletRequest request,
			Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long threeDaysAgo = new Date().getTime() - 253800000;
		String fromDays = RequestUtils.getParameter(request, "fromDays",
				sdf.format(new Date(threeDaysAgo)));
		String toDays = request.getParameter("toDays");
		String excludeUsers = request.getParameter("excludeUsers");
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		List<StatisticsTop100> top100List = top100Service.queryTOP100List(
				fromDays, toDays, excludeUsers,
				StatisticsTop100Service.TOPTYPE_CONSUMPTION);
		if (null != top100List) {
			data.put("total", top100List.size());
			for (StatisticsTop100 top : top100List) {
				Map<String, Object> topMap = new HashMap<String, Object>();
				topMap.put("uid", top.getUid());
				topMap.put("amount", top.getAmount());
				topMap.put("items", top.getItems());
				showList.add(topMap);
			}
			JSONArray rows = JSONArray.fromObject(showList);
			data.put("rows", rows);
		} else {
			data.put("total", 0);
			data.put("rows", null);
		}
		return JSONObject.fromObject(data);
	}

	/**
	 * 导出TOP统计文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportTOP100StatisticsExcel")
	@ResponseBody
	public void exportTOP100StatisticsExcel(HttpServletRequest request,
			HttpServletResponse response) {
		String outputData = request.getParameter("outputData");
		if (!StringUtils.isBlank(outputData)) {
			return;
		}
		String fromDays = request.getParameter("fromDays");
		String toDays = request.getParameter("toDays");
		String excludeUsers = request.getParameter("excludeUsers");
		int showType = RequestUtils.getParameterInt(request, "showType", 0);
		String filename = "";
		int queryType = 0;
		if (showType == 0) {
			filename = "消费TOP100榜";
			queryType = StatisticsTop100Service.TOPTYPE_CONSUMPTION;
		} else if (showType == 1) {
			filename = "销售TOP100榜";
			queryType = StatisticsTop100Service.TOPTYPE_SALES;
		}
		List<StatisticsTop100> top100List = top100Service.queryTOP100List(
				fromDays, toDays, excludeUsers, queryType);
		ServletOutputStream sos = null;
		if (null != top100List) {
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/x-download");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String((filename + ".xls")
										.getBytes("utf-8"), "iso-8859-1"));
				sos = response.getOutputStream();
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet(filename);
				HSSFCellStyle style = book.createCellStyle();
				style.setFillForegroundColor(HSSFColor.TURQUOISE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				// thead
				HSSFRow field = sheet.createRow(0);
				HSSFCell sequenceTH = field.createCell((short) 0);
				sequenceTH.setCellValue(new HSSFRichTextString("序号"));
				sequenceTH.setCellStyle(style);
				HSSFCell uIDTH = field.createCell((short) (1));
				uIDTH.setCellValue(new HSSFRichTextString("UID"));
				uIDTH.setCellStyle(style);
				HSSFCell amountTH = field.createCell((short) (2));
				amountTH.setCellValue(new HSSFRichTextString("金额"));
				amountTH.setCellStyle(style);
				HSSFCell itemsTH = field.createCell((short) (3));
				itemsTH.setCellValue(new HSSFRichTextString("笔数"));
				itemsTH.setCellStyle(style);
				// tbody
				for (int i = 0; i < top100List.size(); i++) {
					HSSFCellStyle rowStyle = book.createCellStyle();
					rowStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
					rowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
					HSSFCell sequenceTD = row.createCell((short) 0);
					sequenceTD.setCellValue(i + 1);
					HSSFCell uIDTD = row.createCell((short) (1));
					uIDTD.setCellValue(top100List.get(i).getUid());
					HSSFCell amountTD = row.createCell((short) (2));
					amountTD.setCellValue(top100List.get(i).getAmount());
					HSSFCell itemsTD = row.createCell((short) (3));
					itemsTD.setCellValue(top100List.get(i).getItems());
					if (i % 2 == 0) {
						sequenceTD.setCellStyle(rowStyle);
						uIDTD.setCellStyle(rowStyle);
						amountTD.setCellStyle(rowStyle);
						itemsTD.setCellStyle(rowStyle);
					}
				}
				book.write(sos);
				sos.flush();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/salesTOP100List")
	@ResponseBody
	public JSONObject salesTOP100List(HttpServletRequest request, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long threeDaysAgo = new Date().getTime() - 253800000;
		String fromDays = RequestUtils.getParameter(request, "fromDays",
				sdf.format(new Date(threeDaysAgo)));
		String toDays = request.getParameter("toDays");
		String excludeUsers = request.getParameter("excludeUsers");
		JSONObject data = new JSONObject();
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		List<StatisticsTop100> top100List = top100Service.queryTOP100List(
				fromDays, toDays, excludeUsers,
				StatisticsTop100Service.TOPTYPE_SALES);
		if (null != top100List) {
			data.put("total", top100List.size());
			for (StatisticsTop100 top : top100List) {
				Map<String, Object> topMap = new HashMap<String, Object>();
				topMap.put("uid", top.getUid());
				topMap.put("amount", top.getAmount());
				topMap.put("items", top.getItems());
				showList.add(topMap);
			}
			JSONArray rows = JSONArray.fromObject(showList);
			data.put("rows", rows);
		} else {
			data.put("total", 0);
			data.put("rows", null);
		}
		return JSONObject.fromObject(data);
	}
}
