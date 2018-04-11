package cn.jugame.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.dao.SysUserinfoDao;
import cn.jugame.vo.SysUserinfo;
/**
 * 
 * @author Administrator
 *
 */
public class RequestUtils {
	public static Logger LOG = LoggerFactory.getLogger(RequestUtils.class);

	/**
	 * 读取request参数值
	 * @param request 
	 * @param paramName 
	 * @param defaultValue 
	 * @return 字符串
	 */
	public static String getParameter(HttpServletRequest request,
			String paramName, String defaultValue) {
		String value = request.getParameter(paramName);
		if (value != null){
			return value;
		}

		return defaultValue;
	}

	/**
	 * 读取request参数值
	 * @param request 
	 * @param paramName  
	 * @param defaultValue  
	 * @return 整型
	 */

	public static int getParameterInt(HttpServletRequest request,
			String paramName, int defaultValue) {
		String value = request.getParameter(paramName);
		// String value_b= value.trim();
		if (value == null){
			return defaultValue;
		}

		try {
			int r = Integer.valueOf(value).intValue();
			return r;
		} catch (Exception ee) {
            //LOG.error("整型参数转换失败");
		}
		return defaultValue;
	}
	
	/**
	 * 获取表单参数值
	 * @param request
	 * @param name
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static int getParametersInt(HttpServletRequest request,String name,int index,int defaultValue){
		String[] values=request.getParameterValues(name);
		if(null!=values){
			String value=values[index];
			if(!StringUtils.isBlank(value)){
				return Integer.parseInt(value);
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取表单参数值
	 * @param request
	 * @param name
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static String getParameters(HttpServletRequest request,String name,int index,String defaultValue){
		String[] values=request.getParameterValues(name);
		if(null!=values){
			String value=values[index];
			return value;
		}
		return defaultValue;
	}
	
    /**
     * 获取用户IP地址
     * @param request 
     * @return IP
     */
	public static String getUserIp(HttpServletRequest request) {
		if (request == null){
			return "";
		}

		String ip = request.getHeader("X-real-ip"); // nginx代理过来的头
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// logger.info("X-real-ip="+ request.getHeader("X-real-ip") +
		// ",x-forwarded-for="+request.getHeader("x-forwarded-for") +
		// ",Proxy-Client-IP="+ request.getHeader("Proxy-Client-IP")+
		// ",WL-Proxy-Client-IP="+request.getHeader("WL-Proxy-Client-IP")+
		// "，ip=" + ip);

		// 使用代理有可能会有多个ip的情况，只取第1个
		if (!StringUtils.isBlank(ip) && ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}
		return ip;
	}
	
	/**
	 * 判断当前用户是否有权限
	 * @param request 
	 * @param response
	 * @param str 权限方法完整路径 
	 * @return
	 * @throws Exception
	 */
	public static boolean isAuthority(HttpServletRequest request, HttpServletResponse response,String str) throws Exception {
		SysUserinfo userinfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (userinfo == null) {
			return false;
		}
		if (userinfo.getUsertype() != SysUserinfoDao.USER_TYPE_SUPER_ADMIN) {
			Map<String, String> perMap = (HashMap<String, String>) request.getSession().getAttribute(GlobalsKeys.ADMIN_PERMISSION_MAP);
            
			if(perMap.get(str.toLowerCase()) == null){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}
}
