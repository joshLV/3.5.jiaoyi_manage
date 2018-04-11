package cn.jugame.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author EwinLive
 * 
 */
public final class StringUtils {
	/**
	 * 默认的空值
	 */
	public static final String EMPTY = "";

	/**
	 * 检查字符串是否为空或null
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 是否数字
	 * 
	 * @param str 
	 * @return boolean
	 */
	public static boolean isInt(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 截取并保留标志位之前的字符串
	 * 
	 * @param str
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return 字符串
	 */
	public static String substringBefore(String str, String expr) {
		if (isEmpty(str) || expr == null) {
			return str;
		}
		if (expr.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(expr);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取并保留标志位之后的字符串
	 * 
	 * @param str
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return 字符串
	 */
	public static String substringAfter(String str, String expr) {
		if (isEmpty(str)) {
			return str;
		}
		if (expr == null) {
			return EMPTY;
		}
		int pos = str.indexOf(expr);
		if (pos == -1) {
			return EMPTY;
		}
		return str.substring(pos + expr.length());
	}

	/**
	 * 截取并保留最后一个标志位之前的字符串
	 * 
	 * @param str
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return 字符串
	 */
	public static String substringBeforeLast(String str, String expr) {
		if (isEmpty(str) || isEmpty(expr)) {
			return str;
		}
		int pos = str.lastIndexOf(expr);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取并保留最后一个标志位之后的字符串
	 * 
	 * @param str 
	 * @param expr 
	 *            分隔符
	 * @return  字符串
	 */
	public static String substringAfterLast(String str, String expr){
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(expr)) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(expr);
		if (pos == -1 || pos == (str.length() - expr.length())) {
			return EMPTY;
		}
		return str.substring(pos + expr.length());
	}

	/**
	 * 把字符串按分隔符转换为数组
	 * 
	 * @param string
	 *            字符串
	 * @param expr
	 *            分隔符
	 * @return 字符数组
	 */
	public static String[] stringToArray(String string, String expr) {
		return string.split(expr);
	}

	/**
	 * 去除字符串中的空格
	 * 
	 * @param str 
	 * @return 字符串
	 */
	public static String noSpace(String str){
		str = str.trim();
		str = str.replace(" ", "_");
		return str;
	}

	/**
	 * 产生一个随机字符串(由数字组成)
	 * 
	 * @param len
	 *            随机数长度
	 * @return 字符串
	 */
	public static String genRondomNum(int len) {
		StringBuffer vcode = new StringBuffer();
		Random ran = new Random();
		for (int i = 0; i < len; i++){
			vcode.append(ran.nextInt(10));
		}
			

		return vcode.toString();
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str 
	 * @return 过滤后的字符串
	 * @throws Exception 异常
	 */
	public static String stringFilter(String str) throws Exception{
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		if(str.contains("\\")){
			str = str.replace("\\", " ");
		}
		if(str.contains("\n")){
			str = str.replace("\n", " ");
		}
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.replaceAll("").trim();
	}
	
	/**
	 * 过滤一般特殊字符
	 */
	public static String stringFilterNormal(String str){
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		if(str.contains("\\")){
			str = str.replace("\\", "");
		}
		if(str.contains("\r\n")){
			str = str.replace("\r\n", "");
		}
        if(str.contains("\"")){
        	str = str.replace("\"", "");
        }
        if(str.contains("\t")){
        	str = str.replace("\t", "");
        }
       
		return str.trim();
	}
	

	/**
	 * 主方法 测试
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
//		String str = "\\\\\\\\-4234的发-给-对-方\n的46#！@#！@\\\\/";
//		
//		System.out.println(str);
//		System.out.println(str.replace("\\", " "));
//		System.out.println("==" + stringFilter(str));
		
//		List list = new ArrayList<>();
//		list.add("a");
//		list.add("a");
//		list.add("b");
//		list.add("b");
//		list.add("c");
//		list.add("d");
//		int count = 0;
//		for(int i = 0;i < list.size();i++){
//			if(list.get(i).equals("a")){
//				count ++;
//				System.out.println("==" + list.get(i));
//			}
//			if(list.get(i).equals("b")){
//				count ++;
//				System.out.println(list.get(i));
//			}
//			if(list.get(i).equals("c")){
//				count ++;
//				System.out.println(list.get(i));
//			}
//			if(list.get(i).equals("d")){
//				count ++;
//				System.out.println(list.get(i));
//			}
//			
//			
//		}
//		System.out.println(count);
	}


}