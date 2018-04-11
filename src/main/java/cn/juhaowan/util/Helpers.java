package cn.juhaowan.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.util.DES;
import cn.jugame.util.MD5;

/**
 * 通用工具类
 * 
 * @author cxb
 * @date 2013-11-12 下午9:06:27
 * 
 */
public class Helpers {
	public static Logger logger = LoggerFactory.getLogger(Helpers.class);
	
	public static String DES_KEY = "asdf7ads7fl3ha5sdf3asd0fh1ow";// DES加密key

	/**
	 * DES加密
	 * 
	 * @param str
	 *            明文字符
	 * @return 密文字符
	 * 
	 * **/
	public static String encryptByDES(String str) {
		if (null == str) {
			return null;
		}
		try {
			return DES.encode(str, DES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES解密
	 * 
	 * @param str
	 *            密文字符
	 * @return 明文字符
	 * 
	 * **/
	public static String decodeByDES(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		try {
			return DES.decode(str, DES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /* 这个方法跟encryptByMD5没有什么大区别，就是不使用MD5_KEY及两次md5 */
    public static String md5(String str) {
		if (null == str) {
			return null;
		}
		try {
			return MD5.encode(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String md5_stream(InputStream is) throws IOException {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] buffer = new byte[4096];
		int read = 0;
		while ((read = is.read(buffer)) > 0) {
			md5.update(buffer, 0, read);
		}
		byte[] digest = md5.digest();

		return asHex(digest);
	}

	public static String asHex(byte[] hash) {
		StringBuffer buf = new StringBuffer(hash.length * 2);

		for (int i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xFF, 16));
		}

		return buf.toString();
	}
	
	public static long convert2Long(Object o){
		if(o == null)
			return 0;
		if(o instanceof Long) return ((Long)o).longValue();
		if(o instanceof Integer){
			int tmp = ((Integer)o).intValue();
			return (long)tmp;
		}
		
		String s = o.toString();
		StringBuffer m = new StringBuffer();
		for(int i=0; i<s.length(); ++i){
			char c = s.charAt(i);
			if(c >= '0' && c <= '9'){
				m.append(c);
			}
			else break;
		}
		return Integer.parseInt(m.toString());
	}
	
	public static int convert2Int(Object o){
		long d = convert2Long(o);
		return (int)d;
	}
	
	public static double convert2Double(Object o){
		if(o == null)
			return 0.0;
		if(o instanceof Double){
			return ((Double)o).doubleValue();
		}
		if(o instanceof Float){
			float tmp = ((Float)o).floatValue();
			return (double)tmp;
		}
		
		String s = o.toString();
		StringBuffer m = new StringBuffer();
		boolean dot = false;
		for(int i=0; i<s.length(); ++i){
			char c = s.charAt(i);
			if(c >= '0' && c <= '9'){
				m.append(c);
			}else if(c == '.'){
				if(!dot){
					dot = true;
					m.append(c);
				}
				else break;
			}
			else break;
		}
		return Double.parseDouble(m.toString());
	}

	public static Date getTodayBegin(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return (Date)currentDate.getTime().clone();
	}
	
	public static String getTodayBeginString(String format){
		Date d = getTodayBegin();
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(d);
	}
	
	public static String getTodayBeginString(){
		return getTodayBeginString("yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date getTodayEnd(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return (Date)currentDate.getTime().clone();  
	}
	
	public static String getTodayEndString(String format){
		Date d = getTodayEnd();
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(d);
	}
	
	public static String getTodayEndString(){
		return getTodayEndString("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String now(String format){
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(now);
	}
	
	public static String now(){
		return now("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String urldecode(String str, String enc){
		try{
			return URLDecoder.decode(str, enc);
		}catch(UnsupportedEncodingException e){
			return null;
		}
	}
	
	public static String urlencode(String str, String enc){
		try{
			return URLEncoder.encode(str, enc);
		}catch(UnsupportedEncodingException e){
			return null;
		}
	}
	
	public static String mysql_escape_string(String s){
		if(s == null) return null;
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<s.length(); ++i){
			char c = s.charAt(i);
			switch(c){
			case 0:
                buf.append('0');
                break;
            case '\n':
                buf.append("\\n");
                break;
            case '\r':
                buf.append("\\r");
                break;
            case '\\':
            case '\'':
            case '"':
                buf.append("\\").append(c);
                break;
            case '\032':
            	buf.append("\\Z");
                break;
            default:
            	buf.append(c);
			}
		}
		return buf.toString();
	}
}
