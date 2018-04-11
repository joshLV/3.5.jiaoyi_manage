package cn.jugame.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 * 
 * @author apxer
 * 
 */
public class DateUtil {
	/**
	 * @param endTime
	 *            结束时间
	 * @param beginTime
	 *            开始时间
	 * @return
	 */
	public static long timeDifferenceSec(Date endTime, Date beginTime) {
		SimpleDateFormat bsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat esdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long effectiveTime = 0;
		try {
			effectiveTime = (esdf.parse(esdf.format(endTime)).getTime() - bsdf
					.parse(bsdf.format(beginTime)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return effectiveTime;
	}
}
