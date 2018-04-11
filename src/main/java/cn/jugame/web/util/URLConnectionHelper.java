package cn.jugame.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import cn.jugame.util.MD5;

public class URLConnectionHelper {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String params) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + params;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段

			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				line = new String(line.getBytes(), "UTF-8");
				result += "\n" + line;
			}
			// System.out.println("result == length == " + result.length());
			// result = new String(result.toString().trim().getBytes(),"UTF-8");
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result.trim();
	}

	/**
	 * 根据参数获取查询结果
	 * 
	 * @param cmd
	 * @param data
	 * @return
	 */
	public static String getResult(String baseurl, String params) {
		String result = URLConnectionHelper.sendPost(baseurl, params);
		return result.trim();
	}

	/**
	 * 根据参数获取单点登录数据
	 * 
	 * @param cmd
	 * @param data
	 * @return
	 */
	public static String getssloginResult(String url, String ticket,
			String modId, String key) {

		String mySign = MD5.encode(ticket + key + modId);

		String params = "ticket=" + ticket + "&modId=" + modId + "&sign="
				+ mySign;

		System.out.println("params == " + params);
		String result = URLConnectionHelper.sendGet(url, params);
		return result;
	}

	/**
	 * utf8 转 gb2312 ，用于手机显示
	 * 
	 * @param strValue
	 *            String
	 * @return String
	 */
	public static String UTF8ToGB(String strValue) {
		if (strValue == null || strValue.trim().length() == 0) {
			return null;
		}
		StringBuffer strbuf = new StringBuffer();
		String[] strarr = strValue.split(";");
		int il = strarr.length;
		for (int i = 0; i < il; i++) {
			int pos = strarr[i].indexOf("&#");
			if (pos >= 0) {
				if (pos > 0) {
					strbuf.append(strarr[i].substring(0, pos));
				}

				String tmp = strarr[i].substring(pos + 2);
				if (tmp.startsWith("00")) {
					tmp = tmp.substring(2);
				}

				try {
					int l = Integer.valueOf(tmp);
					if ((l > 10000) || (l < 1000)) {
						strbuf.append((char) l);
					} else {
						strbuf.append("&#").append(tmp).append(";");
					}
				} catch (NumberFormatException e) {
					strbuf.append(tmp);
				}
			} else {
				strbuf.append(strarr[i]);
			}
		}
		return strbuf.toString();
	}

	/**
	 * 根据参数获取查询结果
	 * 
	 * @param cmd
	 * @param data
	 * @return
	 */
	public static String getApiResult(String cmd, String data) {
		String baseurl = GetPropertiesUtil.getPropertiesByKey("baseurl");
		String appId = "1000";
		String appKey = GetPropertiesUtil.getPropertiesByKey("appKey");
		String timeTemp = String.valueOf(System.currentTimeMillis());

		String sign = GetPropertiesUtil.getApiSign(data, appKey, timeTemp);
		String params = "cmd=" + cmd + "&data=" + data + "&sign=" + sign
				+ "&appId=" + appId + "&requestTime=" + timeTemp;
		String result = URLConnectionHelper.sendGet(baseurl, params);
		return result;
	}

	/**
	 * @param urlStr
	 *            指定URL网络地址
	 * @return URL
	 */
	public static int isConnect(String urlStr) {
		if (urlStr == null || urlStr.length() <= 0) {
			return 2;
		}
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			int state = con.getResponseCode();
			if (state == 200) {
				// System.out.println("URL可用！");
				return 0;
			} else {
				return 1;
			}
		} catch (Exception ex) {
			return 2;
		}
	}

	/**
	 * 　　* 判断链接是否有效
	 * 　　* 输入链接
	 * 　　* 返回true或者false
	 * 
	 */

	public static boolean isValid(String strLink) {
	URL url;
	try {
	url = new URL(strLink);
	HttpURLConnection connt = (HttpURLConnection)url.openConnection();
	connt.setRequestMethod("HEAD");
	String strMessage = connt.getResponseMessage();
	if (strMessage.compareTo("Not Found") == 0) {
	return false;
	}
   connt.disconnect();
	} catch (Exception e) {
	return false;
	}
	return true;
	}

	public static void main(String[] args) {
		// 获取订单详情
		// String result = getResult(
		// "http://192.168.0.222:8081/kefu_chat/interface/app_get_chat_record.jsp",
		// "kefu_id=1000&user_id=test1");
		// System.out.println("输出--->>>" + result.trim());
		int i = isConnect("http://filelx.liqucn.com//upload//2011/shadu//360MobileSafe.apk2");
		System.out.println("o=" + i);

	}

}
