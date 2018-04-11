//package cn.jugame.admin.web.controller;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.util.PageInfo;
//import cn.jugame.util.RequestUtils;
//import cn.juhaowan.count.service.MonthWorkRateStatisticsService;
//import cn.juhaowan.count.vo.MonthWorkRateForm;
//
///**
// * 月工时率统计
// * 
// * @author APXer
// * 
// */
//@Controller
//@RequestMapping(value = "/statistics")
//public class MonthWorkRateController {
//	@Autowired
//	private MonthWorkRateStatisticsService monthWorkRateStatistics;
//
//	/**
//	 * 查询月工时率统计List
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 转向地址
//	 */
//	@RequestMapping(value = "/monthWorkRate")
//	public String monthWorkRate(HttpServletRequest request, Model model) {
//		// model.addAttribute("pageInfo", pageInfo);
//		return "statistics/monthWorkRate";
//	}
//
//	/**
//	 * 查询月工时率统计列表
//	 * 
//	 * @param request
//	 *            请求
//	 * @param model
//	 *            模型驱动
//	 * @return 查询工时率统计列表JSON
//	 */
//	@RequestMapping(value = "/monthWorkRate_json")
//	@ResponseBody
//	public JSONObject monthWorkRateJson(HttpServletRequest request, Model model) {
//
//		JSONObject data = new JSONObject();
//		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 25); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "cs_id"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
//																			// desc
//
//		int customerType = RequestUtils.getParameterInt(request,
//				"customerType", -1);
//		String postNo = request.getParameter("postNo");
//		String fullname = request.getParameter("fullname");
//		String year = RequestUtils.getParameter(request, "year",
//				new SimpleDateFormat("yyyy").format(new Date()));
//		String month = RequestUtils.getParameter(request, "month",
//				new SimpleDateFormat("MM").format(new Date()));
//
//		Map<String, Object> conditions = new HashMap<String, Object>();
//		if (-1 != customerType) {
//			conditions.put("customerType", customerType);
//		}
//		if (!StringUtils.isBlank(postNo)) {
//			conditions.put("postNo", postNo);
//		}
//		if (!StringUtils.isBlank(fullname)) {
//			if (fullname.trim().length() > 0) {
//				conditions.put("fullname", fullname);
//			}
//		}
//		if (!StringUtils.isBlank(year) && !StringUtils.isBlank(month)) {
//			conditions.put("ym", year + "-" + month);
//		}
//		PageInfo<MonthWorkRateForm> pageInfo = //
//		monthWorkRateStatistics.queryMonthWorkRatePageInfo(conditions,
//				pageSize, pageNo, sort, order);
//		data.put("total", pageInfo.getRecordCount());
//		for (MonthWorkRateForm mwrf : pageInfo.getItems()) {
//			Map<String, Object> mwrMap = new HashMap<String, Object>();
//			mwrMap.put("csId", mwrf.getCsId());
//			mwrMap.put("postNo", mwrf.getServicePostNo());
//			mwrMap.put("fullname", mwrf.getFullname());
//			DecimalFormat mwrDF = new DecimalFormat("0.##%");
//			mwrMap.put("monthWorkRate", mwrDF.format(mwrf.getMonthWorkRate()));
//			mwrMap.put("wr1", mwrf.getWr1() + "%");
//			mwrMap.put("wr2", mwrf.getWr2() + "%");
//			mwrMap.put("wr3", mwrf.getWr3() + "%");
//			mwrMap.put("wr4", mwrf.getWr4() + "%");
//			mwrMap.put("wr5", mwrf.getWr5() + "%");
//			mwrMap.put("wr6", mwrf.getWr6() + "%");
//			mwrMap.put("wr7", mwrf.getWr7() + "%");
//			mwrMap.put("wr8", mwrf.getWr8() + "%");
//			mwrMap.put("wr9", mwrf.getWr9() + "%");
//			mwrMap.put("wr10", mwrf.getWr10() + "%");
//			mwrMap.put("wr11", mwrf.getWr11() + "%");
//			mwrMap.put("wr12", mwrf.getWr12() + "%");
//			mwrMap.put("wr13", mwrf.getWr13() + "%");
//			mwrMap.put("wr14", mwrf.getWr14() + "%");
//			mwrMap.put("wr15", mwrf.getWr15() + "%");
//			mwrMap.put("wr16", mwrf.getWr16() + "%");
//			mwrMap.put("wr17", mwrf.getWr17() + "%");
//			mwrMap.put("wr18", mwrf.getWr18() + "%");
//			mwrMap.put("wr19", mwrf.getWr19() + "%");
//			mwrMap.put("wr20", mwrf.getWr20() + "%");
//			mwrMap.put("wr21", mwrf.getWr21() + "%");
//			mwrMap.put("wr22", mwrf.getWr22() + "%");
//			mwrMap.put("wr23", mwrf.getWr23() + "%");
//			mwrMap.put("wr24", mwrf.getWr24() + "%");
//			mwrMap.put("wr25", mwrf.getWr25() + "%");
//			mwrMap.put("wr26", mwrf.getWr26() + "%");
//			mwrMap.put("wr27", mwrf.getWr27() + "%");
//			mwrMap.put("wr28", mwrf.getWr28() + "%");
//			mwrMap.put("wr29", mwrf.getWr29() + "%");
//			mwrMap.put("wr30", mwrf.getWr30() + "%");
//			mwrMap.put("wr31", mwrf.getWr31() + "%");
//			showList.add(mwrMap);
//		}
//		JSONArray rows = JSONArray.fromObject(showList);
//		data.put("rows", rows);
//		return JSONObject.fromObject(data);
//	}
//
//	/**
//	 * 导出月工时率统计
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/exportMonthWorkRateStatisticsExcel")
//	@ResponseBody
//	public void exportMonthWorkRateStatisticsExcel(HttpServletRequest request,
//			HttpServletResponse response) {
//		String outputData = request.getParameter("outputData");
//		if (!StringUtils.isBlank(outputData)) {
//			return;
//		}
//		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
//		int pageSize = RequestUtils.getParameterInt(request, "rows", 25); // 每页多少条记录
//		String sort = RequestUtils.getParameter(request, "sort", "cs_id"); // 排序字段
//		String order = RequestUtils.getParameter(request, "order", "desc"); // asc
//		int customerType = RequestUtils.getParameterInt(request,
//				"customerType", -1);
//		String postNo = request.getParameter("postNo");
//		String fullname = request.getParameter("fullname");
//		String year = RequestUtils.getParameter(request, "year",
//				new SimpleDateFormat("yyyy").format(new Date()));
//		String month = RequestUtils.getParameter(request, "month",
//				new SimpleDateFormat("MM").format(new Date()));
//
//		Map<String, Object> conditions = new HashMap<String, Object>();
//		if (-1 != customerType) {
//			conditions.put("customerType", customerType);
//		}
//		if (!StringUtils.isBlank(postNo)) {
//			conditions.put("postNo", postNo);
//		}
//		if (!StringUtils.isBlank(fullname)) {
//			if (fullname.trim().length() > 0) {
//				conditions.put("fullname", fullname);
//			}
//		}
//		if (!StringUtils.isBlank(year) && !StringUtils.isBlank(month)) {
//			conditions.put("ym", year + "-" + month);
//		}
//
//		String filename = year + "年" + month + "月工时率统计";
//		PageInfo<MonthWorkRateForm> pageInfo = //
//		monthWorkRateStatistics.queryMonthWorkRatePageInfo(conditions,
//				pageSize, pageNo, sort, order);
//		ServletOutputStream sos = null;
//		if (null != pageInfo.getItems()) {
//			try {
//				response.setCharacterEncoding("UTF-8");
//				response.setContentType("application/x-download");
//				response.setHeader(
//						"Content-Disposition",
//						"attachment;filename="
//								+ new String((filename + ".xls")
//										.getBytes("utf-8"), "iso-8859-1"));
//				sos = response.getOutputStream();
//				HSSFWorkbook book = new HSSFWorkbook();
//				HSSFSheet sheet = book.createSheet(filename);
//				HSSFCellStyle style = book.createCellStyle();
//				style.setFillForegroundColor(HSSFColor.TURQUOISE.index);
//				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//				// thead
//				HSSFRow field = sheet.createRow(0);
//				HSSFCell sequenceTH = field.createCell((short) 0);
//				sequenceTH.setCellValue(new HSSFRichTextString("序号"));
//				sequenceTH.setCellStyle(style);
//				HSSFCell postNoTH = field.createCell((short) (1));
//				postNoTH.setCellValue(new HSSFRichTextString("客服岗位号"));
//				postNoTH.setCellStyle(style);
//				HSSFCell fullNameTH = field.createCell((short) (2));
//				fullNameTH.setCellValue(new HSSFRichTextString("姓名"));
//				fullNameTH.setCellStyle(style);
//				HSSFCell monthWorkRateTH = field.createCell((short) (3));
//				monthWorkRateTH.setCellValue(new HSSFRichTextString("月工时率"));
//				monthWorkRateTH.setCellStyle(style);
//
//				for (int i = 1; i <= 31; i++) {
//					HSSFCell wrTH = field.createCell((short) (i + 3));
//					wrTH.setCellValue(new HSSFRichTextString(i + "号"));
//					wrTH.setCellStyle(style);
//				}
//
//				List<MonthWorkRateForm> mwrfList = pageInfo.getItems();
//				// tbody
//				int i = 0;
//				for (MonthWorkRateForm mwrf : mwrfList) {
//					HSSFCellStyle rowStyle = book.createCellStyle();
//					rowStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//					rowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
//					HSSFCell sequenceTD = row.createCell((short) 0);
//					sequenceTD.setCellValue(mwrf.getCsId());
//					HSSFCell postNoTD = row.createCell((short) (1));
//					postNoTD.setCellValue(new HSSFRichTextString(mwrf
//							.getServicePostNo()));
//					HSSFCell fullNameTD = row.createCell((short) (2));
//					fullNameTD.setCellValue(new HSSFRichTextString(mwrf
//							.getFullname()));
//					HSSFCell monthWorkRateTD = row.createCell((short) (3));
//					DecimalFormat mwrDF = new DecimalFormat("0.##%");
//					monthWorkRateTD.setCellValue(new HSSFRichTextString(mwrDF
//							.format(mwrf.getMonthWorkRate())));
//
//					for (int j = 1; j <= 31; j++) {
//						HSSFCell wrTD = row.createCell((short) (j + 3));
//						Method[] methods = mwrf.getClass().getMethods();
//						for (Method method : methods) {
//							if (method.getName().equals("getWr" + j)) {
//								try {
//									wrTD.setCellValue(new HSSFRichTextString(
//											method.invoke(mwrf) + "%"));
//								} catch (IllegalAccessException e) {
//									e.printStackTrace();
//								} catch (IllegalArgumentException e) {
//									e.printStackTrace();
//								} catch (InvocationTargetException e) {
//									e.printStackTrace();
//								}
//
//							}
//						}
//						if (i % 2 == 0) {
//							wrTD.setCellStyle(rowStyle);
//						}
//					}
//
//					if (i % 2 == 0) {
//						sequenceTD.setCellStyle(rowStyle);
//						postNoTD.setCellStyle(rowStyle);
//						fullNameTD.setCellStyle(rowStyle);
//						monthWorkRateTD.setCellStyle(rowStyle);
//					}
//				}
//
//				book.write(sos);
//				sos.flush();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					sos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//}
