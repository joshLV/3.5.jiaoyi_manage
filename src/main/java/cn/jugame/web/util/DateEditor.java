package cn.jugame.web.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期拦截转换
 * 
 * @author APxer
 * 
 */
public class DateEditor extends PropertyEditorSupport {
	private String format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @param text
	 *            字符
	 */
	@Override
	public void setAsText(String text) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setValue(date);
	}

	/**
	 * @param formatF
	 *            "yyyy-MM-dd HH:mm:ss"
	 */
	public void setFormat(String formatF) {
		this.format = formatF;
	}
	
	/**
	 * 计算两时间相差分钟
	 * @param d1 结束时间
	 * @param d2 开始时间
	 * @return 相差分钟 字符串
	 */
	public static String getMinuters(Date d1, Date d2){
		long diff = d1.getTime() - d2.getTime();
		long mi = diff / (1000 * 60);

		return String.valueOf(mi);
	}

	/**
	 * 计算两时间相差小时
	 * @param d1 结束时间
	 * @param d2 开始时间
	 * @return 相差小时数
	 */
	public static long getHor(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		long hor = diff / (1000 * 60 * 60);

		return hor;
	}

	/**
	 * 计算两时间相差天数
	 * @param d1 结束时间
	 * @param d2 开始时间
	 * @return 相差天数
	 */
	public static long getDay(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		long day = diff / (1000 * 60 * 60 * 24);

		return day;
	}

	/**
	 * 比较两时间大小
	 * @param d1 结束时间
	 * @param d2 开始时间
	 * @return  相差数
	 * @throws Exception 异常
	 */
	public static long compare(String d1, String d2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date btime = sdf.parse(d1);
		Date etime = sdf.parse(d2);
		long diff = btime.getTime() - etime.getTime();
		return diff;
	}
}
